import {Component, OnInit, Input, Output, EventEmitter, ViewChild, HostListener} from '@angular/core';
import { LazyLoadEvent } from 'primeng/primeng';
import * as _ from 'lodash';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})
export class TableComponent implements OnInit {
  @Output('filterData') filterData: any = new EventEmitter<any>();
  @Output('selectOne') selectOne: any = new EventEmitter<any>();
  @Output('loadMore') loadMore: any = new EventEmitter<any>();

  @ViewChild('dt') table;

  contextSelectedItem: any;

  loading: boolean;
  currentSelectedItem: any;
  dataCashed: any;
  allChecked = false;
  indeterminate = false;

  items = [
    { label: 'View Detail', icon: 'pi pi-search', command: (event) => {
        this.currentSelectedItem = this.contextSelectedItem;
        this.selectOne.emit(this.contextSelectedItem);
      } },
    { label: 'Open', icon: 'pi pi-times', command: (event) => this.handler('openInHere', this.contextSelectedItem) },
    { label: 'Pop Out', icon: 'pi pi-times', command: (event) => this.handler('openInPopup', this.contextSelectedItem) },
    ];

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

  event: any;
  selectedRows: any = [];
  lastSelectedIndex = null;

  constructor() { }

  ngOnInit() {
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

  selectRow(row: any, index: number) {
    if ((window as any).event.ctrlKey) {
      row.selected = !row.selected;
    } else if ((window as any).event.shiftKey) {
      event.preventDefault();
      if (this.lastSelectedIndex || this.lastSelectedIndex === 0) {
        this.selectSection(Math.min(index, this.lastSelectedIndex), Math.max(index, this.lastSelectedIndex));
        this.lastSelectedIndex = null;
      } else {
        this.lastSelectedIndex = index;
        row.selected = true;
      }
    } else {
      this.listOfData.forEach( res => res.selected = false);
      row.selected = true;
    }
    this.selectedRows = this.listOfData.filter(ws => ws.selected === true);
    this.isIndeterminate();
  }

  isIndeterminate() {
    if (this.selectedRows) {
      if ( this.selectedRows.length === this.listOfData.length ) {
        this.allChecked = true;
        this.indeterminate = false;
      } else if ( this.selectedRows.length === 0) {
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
    if (from == to) {
      // this.listOfData[from].selected = !this.listOfData[from].selected;
      this.listOfData[from].selected = true;
    } else {
      for (let i = from; i <= to; i++) {
        // this.listOfData[i].selected = !this.listOfData[i].selected;
        this.listOfData[i].selected = true;
      }
    }
  }

  handler(tableColumn, row) {
    this.selectedRows = this.listOfData.filter(dt =>  dt.selected);
    const data = this.selectedRows.filter(dt => dt === row) || [];
    data.length === 0 ? this.selectedRows = [...this.selectedRows, row] : null;
    console.log(this.selectedRows);
    row.selected = true;
    tableColumn.handler(this.selectedRows);
  }

/*  @HostListener('keyup', ['$event']) keyup(e) {
    console.log('Keyup', JSON.stringify(e.code) );
    if (e.code.match('Shift')) {
      this.lastSelectedIndex = null;
    }
  }*/
}
