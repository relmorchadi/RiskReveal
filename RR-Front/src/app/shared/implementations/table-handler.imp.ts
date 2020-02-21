import { Injectable } from '@angular/core';
import {Observable, BehaviorSubject, forkJoin, of} from 'rxjs';
import { TableHandlerInterface } from '../interfaces/table-handler.interface';
import { TableServiceInterface } from '../interfaces/table-service.interface';
import { Column } from '../types/column.type';
import * as _ from 'lodash';
import {catchError, debounceTime, exhaustMap, map, mergeMap, share, switchMap, tap} from 'rxjs/operators';
import {FetchViewContextDataRequest} from "../types/fetchviewcontextdatarequest.type";
import * as tableStore from "../components/plt/plt-main-table/store";

@Injectable()
export class TableHandlerImp implements TableHandlerInterface {

  offset: number= 10;
  private _api: TableServiceInterface;

  _data: any[];
  data$: BehaviorSubject<any[]>;

  loading$: BehaviorSubject<boolean>;

  _selectedIds: any;
  selectedIds$: BehaviorSubject<any>;

  selectionConfig: { lastSelectedId: number, lastClick: string };

  _sortSelectedAction: string;
  sortSelectedAction$: BehaviorSubject<string>;

  _sortSelectedFirst: boolean;
  sortSelectedFirst$: BehaviorSubject<boolean>;

  _selectAll: boolean;
  selectAll$: BehaviorSubject<boolean>;

  _indeterminate: boolean;
  indeterminate$: BehaviorSubject<boolean>;

  totalRecords: number;
  totalRecords$: BehaviorSubject<number>;

  _columns: Column[];
  columns$: BehaviorSubject<Column[]>;

  _availableColumns: Column[];
  availableColumns$: BehaviorSubject<Column[]>;

  _visibleColumns: Column[];
  visibleColumns$: BehaviorSubject<Column[]>;

  _totalColumnWidth: number;
  totalColumnWidth$: BehaviorSubject<number>;

  params: any;

  config = {
    pageNumber: 1,
    pageSize: 8,
    entity: 1
  };

  protected containerWidth: number;

  constructor() {
    this.columns$ = new BehaviorSubject([]);
    this.visibleColumns$ = new BehaviorSubject([]);
    this.availableColumns$ = new BehaviorSubject([]);

    this.data$ = new BehaviorSubject([]);
    this.loading$ = new BehaviorSubject(false);
    this.totalRecords$ = new BehaviorSubject(0);
    this.totalColumnWidth$ = new BehaviorSubject(1);

    this.selectedIds$ = new BehaviorSubject({});
    this._selectedIds = {};
    this.selectionConfig = {
      lastSelectedId: null,
      lastClick: null
    };
    this.sortSelectedAction$ = new BehaviorSubject('');
    this._sortSelectedAction ='';
    this.sortSelectedFirst$= new BehaviorSubject(false);
    this._sortSelectedFirst= false;
    this.selectAll$= new BehaviorSubject(false);
    this._selectAll= false;
    this.indeterminate$= new BehaviorSubject(false);
    this._indeterminate= false;
  }

  init(attrs) {
    _.forEach(attrs, (v, k) => {
      this[k] = v;
    })
  }

  initApi(url) {
    this._api.setUrl(url);
  }

  public loadData(request: FetchViewContextDataRequest) {
    return this._api.getData(request);
  }

  public loadColumns() {
    return this._api.getColumns();
  }

  public setPageSize(rows) {
    // this.config.pageSize = rows;
    //
    // this.loadChunk(this.config.pageNumber * rows, rows);
  }

  private loadSelectedIds () {
    return this._api.getIDs(this.params);
  }

  loadColsSubscription() {
    this.loadColumns().subscribe((columns) => {
      this.updateColumns(columns);
      this.updateTotalColumnWidth(columns);
    })
  }

  public loadDataSubscription(){
    this.loadData({
      ...this.params,
      ...this.config,
      selectionList: _.join(this._selectedIds, ','),
      sortSelectedFirst: this._sortSelectedFirst,
      sortSelectedAction: this._sortSelectedAction
    }).subscribe( data => {
      const {totalCount, plts} = data;
      this.updateTotalRecords(totalCount);
      this.updateData(plts);
      this.updateLoading(false);
    });
  }

  loadSelectionIdsSubscription() {
    this.loadSelectedIds().subscribe((ids) => {
      let newIds = {};

      _.forEach(ids, e => {
        newIds[e.pltId] = this._selectedIds[e.pltId] || false;
      });

      const newSelection = { ...this._selectedIds, ...newIds };
      this.updateSelectedIDs(newSelection);
    })
  }

  initTable(params) {
    this.updateLoading(true);
    this.params = params;

    this.loadColsSubscription();
    this.loadDataSubscription();
    this.loadSelectionIdsSubscription();
  }

  reloadTable(request: FetchViewContextDataRequest) {
    return forkJoin(
        this.loadColumns(),
        this.loadData(request)
    )
  }

  onColumnResize({element: { id: index }, delta }) {

    //call API
    //Mocking API behavior

    const col = this._visibleColumns[index];

    let newWidth = col.width + delta;

    if(newWidth > col.maxWidth) {
      newWidth = col.maxWidth;
    } else if( newWidth < col.minWidth) {
      newWidth = col.minWidth;
    } else {
      newWidth = col.width + delta;
    }

    this.onApiSuccessLoadColumns(() => this._api.updateColumnWidth({
      viewContextColumnId: col.viewContextColumnId,
      userCode: null,
      width: newWidth
    }));
  }

  onManageColumns(columns: Column[]){
    this.onApiSuccessLoadColumns(
        () => this._api.updateColumnsOrderAndVisibility({
          viewContextId: 2,
          columnsList: _.join(_.map(columns, col => col.viewContextColumnId + ',' + col.isVisible + ',' + col.columnOrder), ';')
        }))
  }

  onFilter(index: number, filterCriteria: string){
    const filter$ = this._api.updateColumnFilter({
      ..._.pick(this._visibleColumns[index], ['viewContextColumnId', 'viewContextId']),
      filterCriteria
    }).pipe(share());


    filter$.pipe(
        switchMap( () => this.reloadTable({
          ...this.params,
          ...this.config,
          selectionList: _.join(this._selectedIds, ','),
          sortSelectedFirst: this._sortSelectedFirst,
          sortSelectedAction: this._sortSelectedAction
        })),
    ).subscribe(
            ([columns, data]: any) => {
              const {totalCount, plts} = data;
              this.updateTotalRecords(totalCount);
              this.updateColumns(columns);
              this.updateData(plts);
            },
            (error) => {
              console.error(error);
            }
        );

    filter$.pipe(
        switchMap( () => this.loadSelectedIds())
    ).subscribe((ids) => {
      let newIds = {};

      _.forEach(ids, e => {
        newIds[e.pltId] = this._selectedIds[e.pltId] || false;
      });

      const newSelection = { ...this._selectedIds, ...newIds };
      this.updateSelectedIDs(newSelection);
    })

  }

  onResetFilter() {
    this.onApiSuccessLoadDataAndColumns(() => this._api.resetColumnFilter(_.pick(this._visibleColumns[0], ['viewContextId'])))
  }

  onSort(index: number){
    if(index == -1) {
      this.updateSortSelectionAction();
      this.updateSortSelectionFirst();
    }

    this.onApiSuccessLoadDataAndColumns(
        () => this._api.updateColumnSort(_.pick(this._visibleColumns[index], ['viewContextColumnId', 'viewContextId'])).pipe(debounceTime(500)),
    );
  }

  onResetSort() {
    this.onApiSuccessLoadDataAndColumns(() => this._api.resetColumnSort(_.pick(this._visibleColumns[0], ['viewContextId'])));
  }

  onRowSelect(id: number, index: number, $event: MouseEvent) {
    const isSelected = _.findIndex(this._selectedIds, (v, k) => k == id) >= 0;
    if ($event.ctrlKey || $event.shiftKey) {
      this.selectionConfig.lastClick = 'withKey';
      this.handlePLTClickWithKey(id, index, !isSelected, $event);
    } else {
      this.selectionConfig.lastSelectedId = index;
      const newIds = { ...this._selectedIds, [id] : !this._selectedIds[id]};
      this.updateSelectedIDs(newIds);
      const numberOfSelectedRows = _.filter(this._selectedIds, v => v).length;
      this.updateSelectAll(this.totalRecords == numberOfSelectedRows || numberOfSelectedRows > 0);
      this.updateIndeterminate(this.totalRecords > numberOfSelectedRows && numberOfSelectedRows > 0);
      this.selectionConfig.lastClick = null;
    }
  }

  private handlePLTClickWithKey(id: number, i: number, isSelected: boolean, $event: MouseEvent) {
    if ($event.ctrlKey) {
      const newIds = { ...this._selectedIds, [id] : !this._selectedIds[id]};
      this.updateSelectedIDs(newIds);
      const numberOfSelectedRows = _.filter(this._selectedIds, v => v).length;
      this.updateSelectAll(this.totalRecords == numberOfSelectedRows || numberOfSelectedRows > 0);
      this.updateIndeterminate(this.totalRecords > numberOfSelectedRows && numberOfSelectedRows > 0);
      this.selectionConfig.lastSelectedId = i;
      return;
    }

    if ($event.shiftKey) {
      if (!this.selectionConfig.lastSelectedId) this.selectionConfig.lastSelectedId = 0;
      if (this.selectionConfig.lastSelectedId  >= 0) {
        const max = _.max([i, this.selectionConfig.lastSelectedId]);
        const min = _.min([i, this.selectionConfig.lastSelectedId]);

        //Filter concerned range of data
        const data = _.filter(this._data, (plt, i) => i <= max && i >= min);
        const newIds = {};

        //Select all filtered PLTs
        _.forEach(data, plt => {
          newIds[plt.pltId] = true;
        });

        this.updateSelectedIDs(newIds);
        const numberOfSelectedRows = _.filter(this._selectedIds, v => v).length;
        this.updateSelectAll(this.totalRecords == numberOfSelectedRows || numberOfSelectedRows > 0);
        this.updateIndeterminate(this.totalRecords > numberOfSelectedRows && numberOfSelectedRows > 0);
      } else {
        this.selectionConfig.lastSelectedId = i;
      }
      return;
    }
  }

  onContainerResize(newWidth) {
    this.containerWidth = newWidth;
    this.updateTotalColumnWidth(this._columns);
  }

  onCheckAll() {
    const newIds = {};

    _.forEach(this._selectedIds, (v,k) => {
      newIds[k] = this._selectAll ? false : !this._indeterminate;
    });

    this.updateSelectedIDs(newIds);
    const numberOfSelectedRows = _.filter(this._selectedIds, v => v).length;
    this.updateSelectAll(this.totalRecords == numberOfSelectedRows);
    this.updateIndeterminate(this.totalRecords > numberOfSelectedRows && numberOfSelectedRows > 0);
  }

  onVirtualScroll(event) {
    const {
      pageNumber,
      pageSize
    } = this.config;

    if(event.first != (pageNumber * pageSize) || this.config.pageSize != event.rows) {
      this.config.pageSize= event.rows;
      if (event.first === this.totalRecords)
        this.loadChunk(event.first, this.config.pageSize);
      else
        this.loadChunk(event.first, this.config.pageSize);
    }

  }

  loadChunk(offset, size) {
    this.config = {
      pageSize: size,
      pageNumber: Math.floor( offset / size),
      entity: 1
    };
    this.loading$.next(true);
    this.loadData({
      ...this.params,
      ...this.config,
      selectionList: _.join(_.filter(_.keys(this._selectedIds), id => this._selectedIds[id]), ','),
      sortSelectedFirst: this._sortSelectedFirst,
      sortSelectedAction: this._sortSelectedAction
    }).subscribe(({totalCount, plts}) => {
      this.updateTotalRecords(totalCount);
      this.updateData(plts);
      this.updateLoading(false);
      this.loading$.next(false);
    })
  }

  private updateColumns (col) {
    this._columns = col;
    const groupedColumnsByVisibility = _.groupBy(col, 'isVisible');
    this.updateVisibleColumns(_.orderBy(groupedColumnsByVisibility['true'],  ['columnOrder'], ['asc']));
    this.updateAvailableColumns(_.orderBy(groupedColumnsByVisibility['false'],  ['columnOrder'], ['asc']));
  }

  private updateData (data) {
    this._data = data;
    this.data$.next(data);
  }

  private updateTotalColumnWidth(columns) {
    const totalWidth = _.reduce(columns, (acc, curr) => acc + curr.width, 40);
    this._totalColumnWidth = totalWidth > this.containerWidth ? this.containerWidth : totalWidth;
    this.totalColumnWidth$.next(this._totalColumnWidth);
  }

  private updateVisibleColumns(c) {
    this._visibleColumns = c;
    this.visibleColumns$.next(c);
  }

  private updateAvailableColumns(c) {
    this._availableColumns = c;
    this.availableColumns$.next(c);
  }

  private updateTotalRecords(t) {
    this.totalRecords= t;
    this.totalRecords$.next(t);
  }

  private updateLoading(l) {
    this.loading$.next(l);
  }

  private updateSelectedIDs(s) {
    this._selectedIds = s;
    this.selectedIds$.next(s);
  }

  private updateSortSelectionAction() {
    if(this._sortSelectedAction == '') {
      this._sortSelectedAction = 'ASC';
    } else if(this._sortSelectedAction == 'ASC') {
      this._sortSelectedAction = 'DESC'
    } else {
      this._sortSelectedAction = '';
    }
    this.sortSelectedAction$.next(this._sortSelectedAction);
  }

  private updateSortSelectionFirst() {
    this._sortSelectedFirst = this._sortSelectedAction != '';
    this.sortSelectedFirst$.next(this._sortSelectedFirst);
  }

  private updateSelectAll(s) {
    this._selectAll = s;
    this.selectAll$.next(this._selectAll);
  }

  private updateIndeterminate(i) {
    this._indeterminate = i;
    this.indeterminate$.next(this._indeterminate);
  }

  private onApiSuccessLoadDataAndColumns = (api) => {
    this.loading$.next(true);
    api()
        .pipe(
            switchMap( () => this.reloadTable({
              ...this.params,
              ...this.config,
              selectionList: _.join(_.filter(_.keys(this._selectedIds), id => this._selectedIds[id]), ','),
              sortSelectedFirst: this._sortSelectedFirst,
              sortSelectedAction: this._sortSelectedAction
            })),
        )
        .subscribe(
            ([columns, data]: any) => {
              const {totalCount, plts} = data;
              this.updateTotalRecords(totalCount);
              this.updateColumns(columns);
              this.updateData(plts);
              this.loading$.next(false);
            },
            (error) => {
              console.error(error);
            }
        )
  };

  private onApiSuccessLoadData = (api) => {
    api()
        .pipe(
            switchMap( () => this.loadData({
              ...this.params,
              ...this.config,
              selectionList: _.join(_.filter(_.keys(this._selectedIds), id => this._selectedIds[id]), ','),
              sortSelectedFirst: this._sortSelectedFirst,
              sortSelectedAction: this._sortSelectedAction
            }))
        )
        .subscribe(
            (data) => {
              const {totalCount, plts} = data;
              this.updateTotalRecords(totalCount);
              this.updateData(plts);
            },
            (error) => {
              console.error(error);
            }
        )
  }

  private onApiSuccessLoadColumns = (api) => {
    api()
        .pipe(
            switchMap( () => this.loadColumns())
        )
        .subscribe(
            (columns) => {
              this.updateColumns(columns);
            },
            (error) => {
              console.error(error);
            }
        )
  }

}
