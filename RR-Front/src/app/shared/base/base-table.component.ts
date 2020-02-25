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
  SimpleChanges, Output, EventEmitter
} from '@angular/core';
import {backendUrl} from "../api";
import * as _ from 'lodash';
import {Observable} from "rxjs";
import {Message} from "../message";


export class BaseTable implements TableInterface , OnInit, AfterViewInit, OnChanges {

  @Input() params: any;
  @Input() selectedProject: any;
  @Output() actionDispatcher: EventEmitter<Message> = new EventEmitter();

  @ViewChild('tableContainer') container;
  containerWidth: number;

  loading$: Observable<boolean>;

  data: any[];
  columns: Column[];
  rows: number;

  selectedIds: any;
  selectedItem: any;
  selectAll: boolean;
  indeterminate: boolean;
  sortSelectedAction: string;

  totalRecords: number;
  totalColumnWidth: number;

  _injectors: any;
  _handler: TableHandlerInterface;

  tableInitialized: boolean;

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
      params,
      selectedProject
    } = changes;
    this.initTable(params, selectedProject);
    this.selectedProjectFilter(selectedProject);
  }

  ngAfterViewInit(): void {
    this.containerWidth = this.container.nativeElement.clientWidth;
    window.dispatchEvent(new Event('resize'));
  }

  injectDependencies(key) {
    const {
      api,
      handler
    } = this._injectors[key];

    this._handler = handler;
    this._handler.init({
      _api: api
    });
    this._handler.initApi(`${backendUrl()}plt/`);
  }

  initTable(params, selectedProject) {
    if(this.params && selectedProject && selectedProject.previousValue != selectedProject.currentValue) {
      this.tableInitialized = true;
      this._handler.initTable(this.params, selectedProject.currentValue);
    }
  }

  selectedProjectFilter(selectedProject) {
    if(this.tableInitialized && selectedProject && selectedProject.previousValue && selectedProject.previousValue != selectedProject.currentValue) {
      console.log("selected project");
      console.log(selectedProject);
      this._handler.filterByProjectId(selectedProject.currentValue.projectId);
    }
  }

  onColumnResize(event) {
    this._handler.onColumnResize(event);
  }

  onManageColumns(oldColumns: Column[], newColumns: Column[]) {}

  onFilter = _.debounce((index, filter: string) => {
    this._handler.onFilter(index, filter);
  }, 500);

  onResetFilter() {
    this._handler.onResetFilter();
  }

  onSort(index) {
    this._handler.onSort(index);
  }

  onResetSort() {
    this._handler.onResetSort();
  }

  onRowSelect(id: number, index: number, $event: MouseEvent) {
    this._handler.onRowSelect(id, index, $event, false);
  }

  onCheckBox(id: number, index: number, $event: MouseEvent) {
    console.log("checkbox")
    this._handler.onRowSelect(id, index, $event, true);
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

  onExport() {
    this._handler.onExport();
  }

  rowsChange(rows) {
    if(rows != this.rows && rows) {
      console.log("Resized : ", rows);
      this._handler.setPageSize(rows);
      this.rows = rows;
    }
  }

  protected detectChanges() {
    if (!this.cdRef['destroyed']) this.cdRef.detectChanges();
  }

}
