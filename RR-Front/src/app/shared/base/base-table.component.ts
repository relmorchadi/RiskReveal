import { TableInterface } from '../interfaces/table.interface';
import { TableServiceInterface } from '../interfaces/table-service.interface';
import { TableHandlerInterface } from '../interfaces/table-handler.interface';
import { TableServiceImp } from '../implementations/table-service.imp';
import { TableHandlerImp } from '../implementations/table-handler.imp';
import { Column } from '../types/column.type';
import {
  Injector,
  OnInit,
  ChangeDetectorRef,
  ViewChild,
  AfterViewInit,
  Input,
  OnChanges,
  SimpleChanges
} from '@angular/core';
import {backendUrl} from "../api";
import * as _ from 'lodash';
import {Observable} from "rxjs";


export class BaseTable implements TableInterface , OnInit, AfterViewInit, OnChanges {

  @Input() params: any;

  @ViewChild('tableContainer') container;
  containerWidth: number;

  loading$: Observable<boolean>;

  data: any[];
  columns: Column[];

  selectedIds: any;
  selectAll: boolean;
  sortSelectedAction: string;

  totalRecords: number;
  totalColumnWidth: number;

  _injectors: any;
  _handler: TableHandlerInterface;

  constructor(private injector: Injector, private cdRef: ChangeDetectorRef) {
    this._injectors = {
      'plt-manager': ({
        api: this.injector.get<TableServiceImp>(TableServiceImp),
        handler: this.injector.get<TableHandlerImp>(TableHandlerImp)
      })
    };

    this.selectedIds= {};
  }

  ngOnInit() {

  }

  ngOnChanges(changes: SimpleChanges): void {
    const {
      params
    } = changes;
    if(!params.previousValue && params.currentValue) {
      this.initTable();
    }
  }

  ngAfterViewInit(): void {
    this.containerWidth = this.container.nativeElement.clientWidth;
  }

  injectDependencies(key) {
    const {
      api,
      handler
    } = this._injectors[key]

    //this._api = api;
    this._handler = handler;
    this._handler.init({
      _api: api
    });
    this._handler.initApi(`${backendUrl()}plt/`);
  }

  protected initTable() {
    this._handler.initTable(this.params);
  }

  onColumnResize(event) {
    this._handler.onColumnResize(event);
  }

  onManageColumns(oldColumns: Column[], newColumns: Column[]) {}

  onFilter = _.debounce((index, filter: string) => {
    this._handler.onFilter(index, filter);
  }, 500);

  onSort(index) {
    this._handler.onSort(index);
  }

  onRowSelect(i: number) {
    this._handler.onRowSelect(i);
  }

  onCheckAll() {
    this._handler.onCheckAll();
  }

  onContainerResize({newWidth, oldWidth}) {
    if(newWidth != oldWidth) this._handler.onContainerResize(newWidth);
  }

  onVirtualScroll(event) {
    this._handler.onVirtualScroll(event);
  }

  protected detectChanges() {
    if (!this.cdRef['destroyed']) this.cdRef.detectChanges();
  }

}
