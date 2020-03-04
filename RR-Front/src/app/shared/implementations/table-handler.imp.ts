import {Injectable, OnDestroy} from '@angular/core';
import {Observable, BehaviorSubject, forkJoin, of, iif, Subject} from 'rxjs';
import { TableHandlerInterface } from '../interfaces/table-handler.interface';
import { TableServiceInterface } from '../interfaces/table-service.interface';
import { Column } from '../types/column.type';
import * as _ from 'lodash';
import {
  catchError,
  debounceTime,
  exhaustMap,
  map,
  mergeMap,
  share,
  switchMap,
  take,
  takeUntil,
  tap
} from 'rxjs/operators';
import {FetchViewContextDataRequest} from "../types/fetchviewcontextdatarequest.type";
import * as tableStore from "../components/plt/plt-main-table/store";
import {ExcelService} from "../services/excel.service";
import {Store} from "@ngxs/store";
import {WorkspaceState} from "../../workspace/store/states";
import {SaveGlobalTableColumns, SaveGlobalTableData, SaveGlobalTableSelection} from "../../workspace/store/actions";

@Injectable()
export class TableHandlerImp implements TableHandlerInterface, OnDestroy {

  unsubscribe$: Subject<void>;

  offset: number= 10;
  private _api: TableServiceInterface;

  _data: any[];
  data$: BehaviorSubject<any[]>;

  _rows: number;
  rows$: BehaviorSubject<number>;

  maxRows: number;

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
    pageSize: 10000,
    entity: 1
  };

  protected containerWidth: number;

  constructor(private excel: ExcelService, private store: Store) {
    this.unsubscribe$ =  new Subject<void>();

    this.columns$ = new BehaviorSubject([]);
    this.visibleColumns$ = new BehaviorSubject([]);
    this.availableColumns$ = new BehaviorSubject([]);

    this.data$ = new BehaviorSubject([]);
    this.loading$ = new BehaviorSubject(false);
    this.totalRecords$ = new BehaviorSubject(0);
    this._rows = 3;
    this.maxRows = 10000;
    this.rows$ = new BehaviorSubject(3);
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
      pageSize: this.config.pageSize ? this.config.pageSize : this.maxRows,
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

  private getInitializedFromStore({workspaceContextCode, workspaceUwYear}) {
    return this.store.select(WorkspaceState.isGlobalTableInitialized(workspaceContextCode+'-'+workspaceUwYear))
  }

  private getDataFromStore({workspaceContextCode, workspaceUwYear}) {
    return this.store.select(WorkspaceState.getGlobalTableData(workspaceContextCode+'-'+workspaceUwYear))
  }

  private resolveData = data => {
    const {totalCount, plts} = data;
    this.updateTotalRecords(totalCount);
    this.updateData(plts,);
    this.updateLoading(false);

    //save to store
    this.saveData(this.params, data);
  };

  private getColumnsFromStore({workspaceContextCode, workspaceUwYear}) {
    return this.store.select(WorkspaceState.getGlobalTableColumns(workspaceContextCode+'-'+workspaceUwYear))
  }

  private resolveColumns = (columns) => {
    this.updateColumns(columns);
    this.updateTotalColumnWidth(columns);

    //save to store
    this.saveColumns(this.params, columns);

  }

  private getSelectionFromStore({workspaceContextCode, workspaceUwYear}) {
    return this.store.select(WorkspaceState.getGlobalTableSelection(workspaceContextCode+'-'+workspaceUwYear))
  }

  private resolveSelection = (ids) => {
    let newIds = {};

    _.forEach(ids, e => {
      newIds[e.pltId] = this._selectedIds[e.pltId] || false;
    });

    const newSelection = { ...this._selectedIds, ...newIds };
    this.updateSelectedIDs(newSelection);
    const numberOfSelectedRows = _.filter(this._selectedIds, v => v).length;
    this.updateSelectAll(this.totalRecords == numberOfSelectedRows || numberOfSelectedRows > 0);
    this.updateIndeterminate(this.totalRecords > numberOfSelectedRows && numberOfSelectedRows > 0);

    //save to store
    this.saveSelection(this.params, this._selectedIds);
  };

  private saveData({workspaceContextCode, workspaceUwYear}, data) {
    this.store.dispatch(new SaveGlobalTableData({
      data,
      wsIdentifier: workspaceContextCode+'-'+workspaceUwYear
    }))
  }

  private saveColumns({workspaceContextCode, workspaceUwYear}, columns) {
    this.store.dispatch(new SaveGlobalTableColumns({
      columns,
      wsIdentifier: workspaceContextCode+'-'+workspaceUwYear
    }))
  }

  private saveSelection({workspaceContextCode, workspaceUwYear}, selectedIds) {
    this.store.dispatch(new SaveGlobalTableSelection({
      selectedIds,
      wsIdentifier: workspaceContextCode+'-'+workspaceUwYear
    }))
  }

  initTable(params, selectedProject) {
    this.params = params;

    const init$ = this._api.filterByProject({
      projectId: selectedProject.projectId
    }).pipe(
        take(1),
        share()
    );

    const initialized$ = this.getInitializedFromStore(this.params)
        .pipe(
            take(1),
            share()
        );


    this.updateLoading(true);

    initialized$
        .pipe(
            mergeMap(
                isInitialized =>
                    iif(() => isInitialized,
                        forkJoin(
                            this.getDataFromStore(this.params).pipe(take(1)),
                            this.getSelectionFromStore(this.params).pipe(take(1))
                        ),
                        init$.pipe(
                            switchMap( () => forkJoin(
                                this.loadData({
                                  ...this.params,
                                  ...this.config,
                                  pageSize: this.config.pageSize ? this.config.pageSize : this.maxRows,
                                  selectionList: _.join(this._selectedIds, ','),
                                  sortSelectedFirst: this._sortSelectedFirst,
                                  sortSelectedAction: this._sortSelectedAction
                                }),
                                this.loadSelectedIds()
                            ))
                        )
                    )
            )
        ).subscribe(([data, ids]) => {
          this.resolveData(data);
          this.resolveSelection(ids);
          this.updateLoading(false);
    });

    initialized$
        .pipe(
            mergeMap(
                isInitialized =>
                    iif(() => isInitialized,
                        this.getColumnsFromStore(this.params).pipe(take(1)),
                        init$.pipe(
                            switchMap( () => this.loadColumns())
                        )
                    )
            )
        ).subscribe((data) => {
      this.resolveColumns(data);
      this.updateLoading(false);
    });
  }

  reloadTable(request: FetchViewContextDataRequest) {
    return forkJoin(
        this.loadColumns(),
        this.loadData(request)
    )
  }

  onColumnResize({element: { id: index }, delta }) {

    //call API

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
    }).pipe(
        take(1),
        share()
    );


    filter$.pipe(
        switchMap( () => this.reloadTable({
          ...this.params,
          ...this.config,
          pageSize: this.config.pageSize ? this.config.pageSize : this.maxRows,
          selectionList: _.join(this._selectedIds, ','),
          sortSelectedFirst: this._sortSelectedFirst,
          sortSelectedAction: this._sortSelectedAction
        })),
    ).subscribe(
            ([columns, data]: any) => {
              this.resolveColumns(columns);
              this.resolveData(data);
            },
            (error) => {
              console.error(error);
            }
        );

    filter$.pipe(
        switchMap( () => this.loadSelectedIds())
    ).subscribe((ids) => {
      this.resolveSelection(ids);
    })

  }

  onResetFilter() {
    const filter$ = this._api.resetColumnFilter(_.pick(this._visibleColumns[0], ['viewContextId'])).pipe(take(1),share());


    filter$.pipe(
        switchMap( () => forkJoin(
            this.loadColumns(),
            this.loadData({
              ...this.params,
              ...this.config,
              pageSize: this.config.pageSize ? this.config.pageSize : this.maxRows,
              selectionList: _.join(this._selectedIds, ','),
              sortSelectedFirst: this._sortSelectedFirst,
              sortSelectedAction: this._sortSelectedAction
            }),
            this.loadSelectedIds()
        )),
    ).subscribe(
        ([columns, data, ids]: any) => {
          this.resolveColumns(columns);
          this.resolveData(data);
          this.resolveSelection(ids);
        },
        (error) => {
          console.error(error);
        }
    );

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

  onRowSelect(id: number, index: number, $event: MouseEvent, byCheckBox) {
    const isSelected = _.findIndex(this._selectedIds, (v, k) => k == id) >= 0;
    if ($event.ctrlKey || $event.shiftKey) {
      this.selectionConfig.lastClick = 'withKey';
      this.handlePLTClickWithKey(id, index, !isSelected, $event);
    } else {
      this.selectionConfig.lastSelectedId = index;
      let newIds = {};
      _.forEach(this._selectedIds, (v,k: number) => {
        newIds[k] = k == id ? (byCheckBox ? !v : true) : (byCheckBox ? v : false);
      });
      this.updateSelectedIDs(newIds);
      const numberOfSelectedRows = _.filter(this._selectedIds, v => v).length;
      this.updateSelectAll(this.totalRecords == numberOfSelectedRows || numberOfSelectedRows > 0);
      this.updateIndeterminate(this.totalRecords > numberOfSelectedRows && numberOfSelectedRows > 0);
      this.selectionConfig.lastClick = null;
    }
    this.saveSelection(this.params, this._selectedIds);
  }

  onContainerResize(newWidth) {
    this.containerWidth = newWidth;
    this.updateTotalColumnWidth(this._columns);
  }

  onCheckAll() {
    let newIds = {};

    _.forEach(this._selectedIds, (v,k) => {
      newIds[k] = !this._indeterminate && !this._selectAll;
    });

    this.updateSelectedIDs(newIds);
    const numberOfSelectedRows = _.filter(this._selectedIds, v => v).length;
    this.updateSelectAll(this.totalRecords == numberOfSelectedRows);
    this.updateIndeterminate(this.totalRecords > numberOfSelectedRows && numberOfSelectedRows > 0);
    this.saveSelection(this.params, this._selectedIds);
  }

  public setPageSize(rows) {
    // this.config.pageSize = rows;
    // this.loadChunk(this.config.pageNumber * rows, rows);
  }

  onVirtualScroll(event) {
    const {
      pageNumber,
      pageSize
    } = this.config;

    if(event.first != (pageNumber * pageSize) || this.config.pageSize != event.rows) {
      this.config.pageSize= event.rows;
      if (event.first === this.totalRecords)
        this.loadChunk(event.first, 3);
      else
        this.loadChunk(event.first, Math.floor(this.config.pageSize));
    }

  }

  onExport() {
    this._api.getData({
      ...this.params,
      pageNumber: 1,
      pageSize: this.totalRecords,
      entity: 1,
      selectionList: _.join(_.filter(_.keys(this._selectedIds), id => this._selectedIds[id]), ','),
      sortSelectedFirst: this._sortSelectedFirst,
      sortSelectedAction: this._sortSelectedAction
    }).subscribe(({plts, totalCount}) => {
      let filters = [];

      _.forEach(_.filter(this._visibleColumns, column => column.filterCriteria), (col: Column) => {
        filters.push({
          column: col.displayName,
          value: col.filterCriteria
        })
      });

      this.excel.exportAsExcelFile(
          [
            { sheetData: plts, sheetName: "Data"},
            { sheetData: filters, sheetName: "Filters"}
          ],
          `PLTList-${this.params.workspaceContextCode}-${this.params.workspaceUwYear}`
      )
    })
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
      pageSize: this.config.pageSize ? this.config.pageSize : this.maxRows,
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

  filterByProjectId(projectId: number) {
    const projectIdColumnIndex = _.findIndex(this._columns, col => col.columnName == 'projectId');

    console.log(projectIdColumnIndex, this._columns);


    const filter$ = this._api.updateColumnFilter({
      ..._.pick(this._columns[projectIdColumnIndex], ['viewContextColumnId', 'viewContextId']),
      filterCriteria: projectId
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

  private handlePLTClickWithKey(id: number, i: number, isSelected: boolean, $event: MouseEvent) {
    if ($event.ctrlKey) {
      const newIds = { ...this._selectedIds, [id] : true};
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


        const newIds = {};

        _.forEach(this._data, (plt, i) => {
          newIds[plt.pltId] =  i <= max && i >= min;
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
    // if(t < this.maxRows && t) {
    //   this.updateRows(t);
    // }
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

  private updateRows(r) {
    this._rows = r;
    this.rows$.next(this._rows);
  }

  private onApiSuccessLoadDataAndColumns = (api) => {
    this.loading$.next(true);
    api()
        .pipe(
            take(1),
            switchMap( () => this.reloadTable({
              ...this.params,
              ...this.config,
              pageSize: this.config.pageSize ? this.config.pageSize : this.maxRows,
              selectionList: _.join(_.filter(_.keys(this._selectedIds), id => this._selectedIds[id]), ','),
              sortSelectedFirst: this._sortSelectedFirst,
              sortSelectedAction: this._sortSelectedAction
            })),
        )
        .subscribe(
            ([columns, data]: any) => {
              this.resolveColumns(columns);
              this.resolveData(data);
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
            takeUntil(this.unsubscribe$),
            switchMap( () => this.loadData({
              ...this.params,
              ...this.config,
              pageSize: this.config.pageSize ? this.config.pageSize : this.maxRows,
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
  };

  private onApiSuccessLoadColumns = (api) => {
    api()
        .pipe(
            take(1),
            switchMap( () => this.loadColumns())
        )
        .subscribe(
            (columns) => {
              this.resolveColumns(columns);
            },
            (error) => {
              console.error(error);
            }
        )
  };

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

}
