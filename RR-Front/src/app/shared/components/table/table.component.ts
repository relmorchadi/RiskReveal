import {
  ChangeDetectionStrategy,
  Component, ElementRef,
  EventEmitter,
  HostListener,
  Input,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import {LazyLoadEvent} from 'primeng/primeng';
import * as _ from 'lodash';

import {fromEvent, of, Subject} from 'rxjs';
import {
  debounceTime,
  map,
  distinctUntilChanged,
  filter
} from "rxjs/operators";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
  //changeDetection: ChangeDetectionStrategy.OnPush
})
export class TableComponent implements OnInit {
  get sortedData(): any {
    return this._sortedData;
  }
  @Output('filterData') filterData: any = new EventEmitter<any>();
  @Output('selectOne') selectOne: any = new EventEmitter<any>();
  @Output('loadMore') loadMore: any = new EventEmitter<any>();
  @Output('dbClickItem') doubleClick: any = new EventEmitter<any>();
  @Output('onRowSelect') onRowSelect: any = new EventEmitter<any>();
  @Output('onRowUnselect') onRowUnselect: any = new EventEmitter<any>();
  @Output('updateFavStatus') updateStatus: any = new EventEmitter<any>();
  @Output('sortDataChange') sortDataChange: any = new EventEmitter<any>();
  @Output('resizeChange') resizeTable: any = new EventEmitter<any>();

  @ViewChild('dt') table;
  @ViewChild('cm') contextMenu;
  @ViewChild('cmL') nvContextMenu;

  input: ElementRef;
  @ViewChild('input') set assetInput(elRef: ElementRef) {
    this.input = elRef;
  };

  contextSelectedItem: any;
  contextSelectedItemNv: any;
  FilterData: any = {};

  @Input('sortData') sortData;
  private _sortedData: any;
  filterInput: any;

  @Input()
  loading: boolean;
  @Input()
  secondaryLoader: boolean;
  @Input()
  rows;
  @Input()
  totalRecords;
  @Input()
  sortable = false;
  @Input()
  listOfData: any[];
  @Input()
  virtualScroll: boolean;
  @Input()
  tableColumn: any[];
  @Input()
  tableHeight: string;
  @Input()
  tableWidth: string;
  @Input()
  selectionMode: string = null;
  @Input()
  filterModeFront: boolean = true;
  @Input()
  fromSearch: boolean = true;
  @Input()
  sortList = [];
  @Input()
  activateContextMenu = false;

  _activateContextMenu: boolean;

  filterQueryChanged: Subject<any> = new Subject<any>();
  currentSelectedItem: any;
  dataCashed: any;
  allChecked = false;
  indeterminate = false;
  selectedRow: any;

  items = [
    {
      label: 'View Detail', icon: 'pi pi-eye', command: (event) => {
        this.currentSelectedItem = this.virtualScroll ? this.contextSelectedItem : this.contextSelectedItemNv;
        this.selectOne.emit(this.contextSelectedItem);
      }
    },
    {
      label: 'Select item',
      icon: 'pi pi-check',
      command: () => this.selectRow(this.virtualScroll ? this.contextSelectedItem : this.contextSelectedItemNv , 0)
    },
    {
      label: 'Open item',
      icon: 'pi pi-eject',
      command: () => this.handler(_.filter(this.tableColumn, e => e.field === 'openInHere')[0], this.virtualScroll ? this.contextSelectedItem : this.contextSelectedItemNv)
    },
    {
      label: 'Pop Out',
      icon: 'pi pi-eject',
      command: () => this.handler(_.filter(this.tableColumn, e => e.field === 'openInPopup')[0], this.virtualScroll ? this.contextSelectedItem : this.contextSelectedItemNv)
    },
  ];

  event: any;
  selectedRows: any = [];
  lastSelectedIndex = null;

  constructor() {
  }

  ngOnInit() {
    this.filterQueryChanged.pipe(
        debounceTime(500),
        distinctUntilChanged()
    ).subscribe(model => {
      const {value, colId} = model;
      this.filterData.emit({filteredValue: value, colId});
    });
  }

  getItems() {
    const selected = this.listOfData.filter(dt => dt.selected);
    if (selected.length > 1) {
      return this.items.filter(item => item.label !== 'View Detail');
    } else {
      return this.items;
    }
  }

  sort(): void {
    if (this.dataCashed) {
      this.listOfData = this.dataCashed;
      this.dataCashed = null;
    } else {
      this.dataCashed = this.listOfData;
      this.listOfData = _.sortBy(this.listOfData, [(o) => {
        return !o.selected;
      }]);
    }
  }

  updateAllChecked() {
    this.indeterminate = false;
    this.allChecked ? this.selectAll() : this.unselectAll();
  }

  selectAll() {
    this.listOfData.forEach(
        ws => ws.selected = true
    );
  }

  unselectAll() {
    this.listOfData.forEach(
        ws => ws.selected = false
    );
  }

  filterCol(searchValue: string, searchAddress: string, key): void {
    if(this.virtualScroll) {
      this.event.first = 0;
      let body = this.table.containerViewChild.nativeElement.getElementsByClassName('ui-table-scrollable-body')[0];
      body.scrollTop = 0;
    }
    if (searchValue) {
      this.FilterData =  _.merge({}, this.FilterData, {[key]: searchValue}) ;
    } else {
      this.FilterData =  _.omit(this.FilterData, [key]);
    }
    this.filterData.emit({searchValue: searchValue, searchAddress: searchAddress});
  }

  updateFavValue(row) {
    this.updateStatus.emit(row);
  }

  loadDataOnScroll(event: LazyLoadEvent) {
    this.event = event;
    this.loadMore.emit(event);
    this.selectedRows = null;
    this.isIndeterminate();
  }

  doubleClickRow(rowData) {
    this.doubleClick.emit(rowData);
  }

  selectRow(row: any, index: number) {
    if ((window as any).event.ctrlKey) {
      row.selected = !row.selected;
      this.lastSelectedIndex = index;
    } else if ((window as any).event.shiftKey) {
      event.preventDefault();
      if (this.lastSelectedIndex || this.lastSelectedIndex === 0) {
        this.selectSection(Math.min(index, this.lastSelectedIndex), Math.max(index, this.lastSelectedIndex));
        // this.lastSelectedIndex = null;
      } else {
        this.lastSelectedIndex = index;
        row.selected = true;
      }
    } else {
      this.listOfData.forEach(res => res.selected = false);
      this.lastSelectedIndex = index;
      row.selected = true;
    }
    this.selectedRows = this.listOfData.filter(ws => ws.selected === true);
    this.isIndeterminate();
  }

  isIndeterminate() {
    if (this.selectedRows) {
      if (this.selectedRows.length === this.listOfData.length) {
        this.allChecked = true;
        this.indeterminate = false;
      } else if (this.selectedRows.length === 0) {
        this.allChecked = false;
        this.indeterminate = false;
      } else {
        this.indeterminate = true;
      }
    }
  }

  uncheckRow() {
    this.selectedRows = this.listOfData.filter(ws => ws.selected === true);
    this.isIndeterminate();
  }

  private selectSection(from, to) {
    this.listOfData.forEach(dt => dt.selected = false);
    if (from === to) {
      this.listOfData[from].selected = true;
    } else {
      for (let i = from; i <= to; i++) {
        this.listOfData[i].selected = true;
      }
    }
  }

  handler(tableColumn, row) {
    this.selectedRows = this.listOfData.filter(dt => dt.selected);
    const data = this.selectedRows.filter(dt => dt === row) || [];
    data.length === 0 ? this.selectedRows = [...this.selectedRows, row] : null;
    row.selected = true;
    tableColumn.handler(this.selectedRows);

  }

  @HostListener('wheel', ['$event']) onElementScroll(event) {
    if (this.contextMenu !== undefined) {
      this.virtualScroll ? this.contextMenu.hide() :
          this.nvContextMenu.hide();
    }
  }

  rowSelect($event) {
    this.onRowSelect.emit($event);
  }

  rowUnselect($event) {
    this.onRowUnselect.emit($event);
  }

  getContextMenu() {
    if (this.activateContextMenu) {
      return this.virtualScroll ? this.contextMenu : this.nvContextMenu;
    } else return null;
  }

  sortChange(field: any, sortCol: any, columnId) {
    if(this.fromSearch) {
      if (!sortCol) {
        this.sortDataChange.emit(_.merge({}, this.sortData, {[field]: 'asc'}));
      } else if (sortCol === 'asc') {
        this.sortDataChange.emit(_.merge({}, this.sortData, {[field]: 'desc'}));
      } else if (sortCol === 'desc') {
        this.sortDataChange.emit(_.omit(this.sortData, `${field}`));
      }
    } else {
      if (!sortCol) {
        this.sortDataChange.emit({newSort: _.merge({}, this.sortData, {[field]: 'asc'}),
          newSortingList: [...this.sortList, {columnId, sortType: 'asc'}],
          columnId, sortType: 'asc'});
      } else if (sortCol === 'asc') {
        this.sortDataChange.emit({newSort: _.merge({}, this.sortData, {[field]: 'desc', columnId}),
          newSortingList: _.map(this.sortList, item => {return item.columnId === columnId ? {columnId, sortType: 'desc'} : item}),
          columnId, sortType: 'desc'});
      } else if (sortCol === 'desc') {
        this.sortDataChange.emit({newSort: _.omit(this.sortData, `${field}`),
          newSortingList: _.filter(this.sortList, item => item.columnId !== columnId),
          columnId, sortType: ''});
      }
    }
  }

  filter(key: string, event, colId) {
    const value = event.target.value;
    console.log(value);
    if (value) {
      this.FilterData =  _.merge({}, this.FilterData, {[key]: value}) ;
    } else {
      this.FilterData =  _.omit(this.FilterData, [key]);
    }
    this.filterQueryChanged.next({key, value, colId});

  }

  resize(event) {
    this.resizeTable.emit(event);
  }

  log(dt: HTMLElement) {
    console.log(dt);
  }
}
