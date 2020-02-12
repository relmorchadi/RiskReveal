import { TableInterface } from '../interfaces/table.interface';
import { TableServiceInterface } from '../interfaces/table-service.interface';
import { TableHandlerInterface } from '../interfaces/table-handler.interface';
import { TableServiceImp } from '../implementations/table-service.imp';
import { TableHandlerImp } from '../implementations/table-handler.imp';
import { Column } from '../types/column.type';
import {Injector, OnInit, ChangeDetectorRef, ViewChild, AfterViewInit} from '@angular/core';
import {backendUrl} from "../api";


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
    this._handler.initApi(`${backendUrl()}plt/`);
  }

  getApiInjector = (key) => this._injectors[key]

  protected initTable() {
    this._handler.initTable({
      workspaceContextCode: "FA0044610",
      workspaceUwYear: 2019,
      entity: 1,
      pageNumber: 1,
      pageSize: 1000,
      selectionList: "",
      sortSelectedFirst: false,
      sortSelectedAction: ""
  });
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

  onContainerResize({newWidth, oldWidth}) {
    if(newWidth != oldWidth) this._handler.onContainerResize(newWidth);
  }

}
