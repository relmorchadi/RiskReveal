import { Injectable } from '@angular/core';
import {Observable, BehaviorSubject, forkJoin, of} from 'rxjs';
import { TableHandlerInterface } from '../interfaces/table-handler.interface';
import { TableServiceInterface } from '../interfaces/table-service.interface';
import { Column } from '../types/column.type';
import * as _ from 'lodash';
import {catchError, mergeMap} from 'rxjs/operators';
import {FetchViewContextDataRequest} from "../types/fetchviewcontextdatarequest.type";

@Injectable()
export class TableHandlerImp implements TableHandlerInterface {

  private _api: TableServiceInterface;

  _data: any[];
  data$: BehaviorSubject<any[]>;

  _columns: Column[];
  columns$: BehaviorSubject<Column[]>;

  _availableColumns: Column[];
  availableColumns$: BehaviorSubject<Column[]>;

  _visibleColumns: Column[];
  visibleColumns$: BehaviorSubject<Column[]>;

  _totalColumnWidth: number;
  totalColumnWidth$: BehaviorSubject<number>;

  _data: any[];
  data$: BehaviorSubject<any[]>;
  _request: any;

  protected containerWidth: number;

  constructor() {
    this.columns$ = new BehaviorSubject([]);
    this.visibleColumns$ = new BehaviorSubject([]);
    this.availableColumns$ = new BehaviorSubject([]);


    this.data$ = new BehaviorSubject([]);
    this.totalColumnWidth$ = new BehaviorSubject(1);
  }

  init(attrs) {
    console.log(attrs);
    _.forEach(attrs, (v, k) => {
      this[k] = v;
    })
  }

  initApi(url) {
    console.log(url);
    this._api.setUrl(url);
  }

  public loadData(request: FetchViewContextDataRequest) {
    return this._api.getData(request);
  }

  public loadColumns() {
    return this._api.getColumns();
  }

  initTable(request: FetchViewContextDataRequest) {
    this._request = request;
    forkJoin(
        this.loadColumns(),
        this.loadData(request)
    ).subscribe( ([columns, data]: any) => {
      const {totalCount, plts} = data;
      console.log({
        plts,
        columns
      });
      this.updateColumns(columns);
      this.updateTotalColumnWidth(columns);
      this.updateData(plts);
    })
  }

  onColumnResize({element: { id: index }, delta }) {

    //call API
    //Mocking API behavior

    const col =

    this._api.updateColumnWidth(this._request)
        .pipe(
            mergeMap( () => this.reloadColumns())
        )
        .subscribe(
            () => {
              //On Success

              if(this._columns[index]) {
                const column = this._columns[index];
                const currentColumnWidth = column.width;
                const newColumnWidth = currentColumnWidth + delta;
                const newColumns = _.merge([], this._columns, { [index]: { width: newColumnWidth}});
                this.updateColumns(newColumns);
                this.updateTotalColumnWidth(newColumns);
              }
            },
            () => {
              //On Error
            }
        )
  }

  onManageColumns(columns: Column[]){
    console.log(columns);
  }
  onFilter(column: Column, filter: string){}
  onSort(column: Column, direction){}
  onRowSelect(rowId: number){}

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
    const totalWidth = _.reduce(columns, (acc, curr) => acc + curr.width, 1);
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

  onContainerResize(newWidth) {
    this.containerWidth = newWidth;
    this.updateTotalColumnWidth(this._columns);
  }

  private reloadColumns() {
    return this.loadColumns()
  }

}
