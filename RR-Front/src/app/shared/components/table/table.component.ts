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

  loading: boolean;
  currentSelectedItem: any;

  @Input()
  totalRecords;
  @Input()
  sortable: boolean = false;
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

  sort(sort: { key: string; value: string }): void {
    /*this.sortName = sort.key;
    this.sortValue = sort.value;
    this.search();*/
  }

  filterCol(searchValue: string, searchAddress: string): void {
    this.event.first = 0;
    let body = this.table.containerViewChild.nativeElement.getElementsByClassName('ui-table-scrollable-body')[0];
    body.scrollTop = 0;
    this.filterData.emit({searchValue: searchValue, searchAddress: searchAddress});
  }

  loadDataOnScroll(event: LazyLoadEvent) {
      console.log('lazy load', event);
      this.event = event;
      this.loadMore.emit(event);
  }

  selectRow(row: any, index: number) {
    if ((window as any).event.ctrlKey) {
      row.selected = !row.selected;
    }
    if ((window as any).event.shiftKey) {
      event.preventDefault();
      if (this.lastSelectedIndex) {
        this.selectSection(Math.min(index, this.lastSelectedIndex), Math.max(index, this.lastSelectedIndex));
      } else {
        this.lastSelectedIndex = index;
      }
    }
    this.selectedRows = this.listOfData.filter(ws => ws.selected === true);
    this.selectOne.emit(row);
  }

  private selectSection(from, to) {
    if (from == to) {
      this.listOfData[from].selected = true;
    } else {
      for (let i = from; i <= to; i++) {
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
