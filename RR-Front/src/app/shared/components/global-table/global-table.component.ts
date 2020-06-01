import {AfterViewInit, ChangeDetectorRef, Component, Injector, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {BaseTable} from "../../base/base-table.component";
import {Router} from "@angular/router";
import {Store} from "@ngxs/store";

@Component({
  selector: 'd-table',
  templateUrl: './global-table.component.html',
  styleUrls: ['./global-table.component.scss']
})
export class GlobalTableComponent extends BaseTable implements OnInit, AfterViewInit, OnChanges {

  isModalVisible: boolean;

  visibleList = [];

  availableList = [];

  contextMenu = [
    {
      label: 'View Detail', command: () => {console.log(this.selectedItem); this.actionDispatcher.emit({ type: 'View Detail', payload: this.selectedItem });}
    }
    ];

  constructor(_injector: Injector, _baseRouter: Router, _baseCdr: ChangeDetectorRef,  _baseStore: Store) {
    super(_injector, _baseRouter, _baseCdr, _baseStore);
    this.injectDependencies('plt-manager');
    this.data = [];
    this.columns= [];
    this.visibleList= [];
    this.availableList= [];
    this.totalColumnWidth = 0;
  }

  ngOnInit() {
    super.ngOnInit();

    this.loading$ = this._handler.loading$;

    this._handler.data$.subscribe(d => {
      console.log(d);
      this.data= d;
      this.detectChanges();
    });

    this._handler.totalRecords$.subscribe(t => {
      this.totalRecords= t;
      this.detectChanges();
    });

    this._handler.visibleColumns$.subscribe(c => {
      console.log(c);
      this.isModalVisible = false;
      this.columns= [
        ...c
      ];
      this.visibleList= c;
      this.detectChanges();
    });

    this._handler.availableColumns$.subscribe(c => {
      this.availableList= c;
      this.detectChanges();
    });

    this._handler.totalColumnWidth$.subscribe(totalColumnWidth => {
      this.totalColumnWidth = totalColumnWidth;
      this.detectChanges();
    });

    this._handler.selectedIds$.subscribe( s => {
      this.selectedIds = s;
      this.detectChanges();
    });

    this._handler.sortSelectedAction$.subscribe( s => {
      this.sortSelectedAction = s;
      this.detectChanges();
    });

    this._handler.selectAll$.subscribe( s => {
      this.selectAll = s;
      this.detectChanges()
    });

    this._handler.indeterminate$.subscribe(i => {
      this.indeterminate = i;
      this.detectChanges();
    });

    this._handler.rows$.subscribe(r => {
      this.rows = r;
      this.detectChanges();
    })

  }

  ngAfterViewInit(): void {
    super.ngAfterViewInit();
  }

  detectChanges() {
    super.detectChanges();
  }

  ngOnChanges(changes: SimpleChanges): void {
    super.ngOnChanges(changes);
  }

  showDialog() {
    this.isModalVisible = true;
  }

  handleManageColumnsActions(action) {
    switch (action.type) {

      case "Manage Columns":
        this._handler.onManageColumns(action.payload);
        break;

      case "Close":
        this.isModalVisible= false;
        break;

      default:
        console.log(action);
    }
  }
  cloneFrom() {
    this.actionDispatcher.emit({type: 'Commit clone'})
  }
}
