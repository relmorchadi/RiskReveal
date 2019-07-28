import {Component, EventEmitter, HostListener, Input, OnInit, Output, ViewChild} from '@angular/core';
import {LazyLoadEvent} from 'primeng/primeng';
import * as _ from 'lodash';
import * as tableStore from './store';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
})
export class TableComponent implements OnInit {
  @Output('filterData') filterData: any = new EventEmitter<any>();
  @Output('selectOne') selectOne: any = new EventEmitter<any>();
  @Output('loadMore') loadMore: any = new EventEmitter<any>();
  @Output('dbClickItem') doubleClick: any = new EventEmitter<any>();
  @Output('onRowSelect') onRowSelect: any = new EventEmitter<any>();
  @Output('onRowUnselect') onRowUnselect: any = new EventEmitter<any>();

  @ViewChild('dt') table;
  @ViewChild('cm') contextMenu;

  contextSelectedItem: any;
  @Input() tableInputs: tableStore.Input;

  @Input() loading: boolean;
  _activateContextMenu: boolean;
  get activateContextMenu(): boolean {
    return this._activateContextMenu;
  }
  @Input('activateContextMenu')
  set activateContextMenu(value: boolean) {
    if (!_.isNil(value)) { this._activateContextMenu = value;  } else { this._activateContextMenu = true; }
  }
  currentSelectedItem: any;
  dataCashed: any;
  allChecked = false;
  indeterminate = false;

  selectedRow: any;

  items = [
    {
      label: 'View Detail', icon: 'pi pi-eye', command: (event) => {
        this.currentSelectedItem = this.contextSelectedItem;
        this.selectOne.emit(this.contextSelectedItem);
      }
    },
    {
      label: 'Select item',
      icon: 'pi pi-check',
      command: () => this.selectRow(this.contextSelectedItem , 0)
    },
    {
      label: 'Open item',
      icon: 'pi pi-eject',
      command: () => this.handler(_.filter(this.tableColumn, e => e.field === 'openInHere')[0], this.contextSelectedItem)
    },
    {
      label: 'Pop Out',
      icon: 'pi pi-eject',
      command: () => this.handler(_.filter(this.tableColumn, e => e.field === 'openInPopup')[0], this.contextSelectedItem)
    },
  ];

  @Input()
  totalRecords;
  @Input()
  sortable = true;
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

  event: any;
  selectedRows: any = [];
  lastSelectedIndex = null;

  constructor() {
  }

  ngOnInit() {
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

  filterCol(searchValue: string, searchAddress: string): void {
    this.event.first = 0;
    let body = this.table.containerViewChild.nativeElement.getElementsByClassName('ui-table-scrollable-body')[0];
    body.scrollTop = 0;
    this.filterData.emit({searchValue: searchValue, searchAddress: searchAddress});
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
    this.contextMenu.hide();
  }

  rowSelect($event) {
    this.onRowSelect.emit($event);
  }
  rowUnselect($event) {
    this.onRowUnselect.emit($event);
  }
  getContextMenu() {
      return _.isNil(this.activateContextMenu) || this.activateContextMenu ? this.contextMenu : null;
  }

}
