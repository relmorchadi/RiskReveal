import { Injectable } from '@angular/core';
import {Observable, BehaviorSubject, forkJoin, of} from 'rxjs';
import { TableHandlerInterface } from '../interfaces/table-handler.interface';
import { TableServiceInterface } from '../interfaces/table-service.interface';
import { Column } from '../types/column.type';
import * as _ from 'lodash';
import {catchError} from 'rxjs/operators';

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

  protected containerWidth: number;

  constructor() {
    this.columns$ = new BehaviorSubject([]);
    this.visibleColumns$ = new BehaviorSubject([]);
    this.availableColumns$ = new BehaviorSubject([]);


    this.data$ = new BehaviorSubject([]);
    this.totalColumnWidth$ = new BehaviorSubject(1);
  }

  init(attrs) {
    _.forEach(attrs, (v, k) => {
      this[k] = v;
    })
  }

  public loadData() {
    return this._api.loadData();
  }

  public loadColumns() {
    return this._api.loadColumns();
  }

  initTable() {
    forkJoin(
        this.loadColumns(),
        this.loadData()
    ).subscribe( ([columns, data]: any) => {
      this.updateColumns(columns);
      this.updateTotalColumnWidth(columns);
      this.updateData(data);
    })
  }

  onColumnResize({element: { id: index }, delta }) {

    //call API
    //Mocking API behavior

    of([1])
        .subscribe(
            () => {
              //On Success

              if(this._columns[index]) {
                const column = this._columns[index];
                const currentColumnWidth = column.defaultWidth;
                const newColumnWidth = currentColumnWidth + delta;
                const newColumns = _.merge([], this._columns, { [index]: { defaultWidth: newColumnWidth}});
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
    const totalWidth = _.reduce(columns, (acc, curr) => acc + curr.defaultWidth, 1);
    this._totalColumnWidth = totalWidth > this.containerWidth ? this.containerWidth : totalWidth;
    this.totalColumnWidth$.next(this._totalColumnWidth);
  }

  private updateVisibleColumns(c) {
    console.log(c);
    this._visibleColumns = c;
    this.visibleColumns$.next(c);
  }

  private updateAvailableColumns(c) {
    this._availableColumns = c;
    this.availableColumns$.next(c);
  }

}
