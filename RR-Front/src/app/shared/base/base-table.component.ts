import { TableInterface } from '../interfaces/table.interface';
import { TableServiceInterface } from '../interfaces/table-service.interface';
import { TableHandlerInterface } from '../interfaces/table-handler.interface';
import { TableServiceImp } from '../implementations/table-service.imp';
import { TableHandlerImp } from '../implementations/table-handler.imp';
import { Column } from '../types/column.type';
import {Injector, OnInit, ChangeDetectorRef, ViewChild, AfterViewInit} from '@angular/core';


export class BaseTable implements TableInterface , OnInit, AfterViewInit {

  @ViewChild('tableContainer') container;
  containerWidth: number;

  data: any[];
  columns: Column[];

  _injectors: any;

  _api: TableServiceInterface;
  _handler: TableHandlerInterface;

  constructor(private injector: Injector, private cdRef: ChangeDetectorRef) {
    this._injectors = {
      'plt-manager': ({
        api: this.injector.get<TableServiceImp>(TableServiceImp),
        handler: this.injector.get<TableHandlerImp>(TableHandlerImp)
      })
    }
  }

  ngOnInit() {
    this.initTable();
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
  }

  getApiInjector = (key) => this._injectors[key]

  protected initTable() {
    this._handler.initTable();
  }

  onColumnResize(event) {
    this._handler.onColumnResize(event);
  }

  onManageColumns(oldColumns: Column[], newColumns: Column[]) {}

  onFilter(column: Column, filter: string) {}

  onSort(column: Column, direction) {}

  onRowSelect(rowId: number) {}

  protected detectChanges() {
    if (!this.cdRef['destroyed']) this.cdRef.detectChanges();
  }

  ngAfterViewInit(): void {
    this.containerWidth = this.container.nativeElement.clientWidth;
  }

  initCustom() {
    this._handler.init({
      containerWidth: this.containerWidth
    })
  }

}
