import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import * as _ from "lodash";

@Component({
  selector: 'inuring-datatable',
  templateUrl: './inuring-datatable.component.html',
  styleUrls: ['./inuring-datatable.component.scss']
})
export class InuringDatatableComponent implements OnInit {

  @Output('dbClickItem') doubleClick: any = new EventEmitter<any>();
  @Output('onRowSelect') onRowSelect: any = new EventEmitter<any>();
  @Output('onRowUnselect') onRowUnselect: any = new EventEmitter<any>();

  @ViewChild('dt') table;
  @ViewChild('cm') contextMenu;

  @Input()
  virtualScroll: boolean;
  @Input()
  tableColumn: any[];
  @Input()
  tableHeight: string;
  @Input()
  tableWidth: string;

  @Input()
  totalRecords;
  @Input()
  sortable = false;
  @Input()
  listOfData: any[];

  FilterData: any = {};
  sortData: any = {};
  currentSelectedItem: any;
  selectedRows: any = [];

  allChecked = false;
  indeterminate = false;
  lastSelectedIndex = null;
  selectedRow: any;

  constructor() { }

  ngOnInit() {
  }

  sortChange(field: any, sortCol: any) {
    if (!sortCol) {
      this.sortData = _.merge({}, this.sortData, {[field]: 'asc'});
    } else if (sortCol === 'asc') {
      this.sortData = _.merge({}, this.sortData, {[field]: 'desc'});
    } else if (sortCol === 'desc') {
      this.sortData =  _.omit(this.sortData, `${field}`);
    }
  }

  handler(tableColumn, row) {
    this.selectedRows = this.listOfData.filter(dt => dt.selected);
    const data = this.selectedRows.filter(dt => dt === row) || [];
    data.length === 0 ? this.selectedRows = [...this.selectedRows, row] : null;
    row.selected = true;
    tableColumn.handler(this.selectedRows);

  }

  filter(key: string, value) {
    if (value) {
      this.FilterData =  _.merge({}, this.FilterData, {[key]: value}) ;
    } else {
      this.FilterData =  _.omit(this.FilterData, [key]);
    }
    console.log('this.FilterData', this.FilterData);
  }

  doubleClickRow(rowData) {
    this.doubleClick.emit(rowData);
  }

  uncheckRow() {
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

  updateFavValue = (d)=>{};

  rowSelect($event) {
    this.onRowSelect.emit($event);
  }

  rowUnselect($event) {
    this.onRowUnselect.emit($event);
  }

}
