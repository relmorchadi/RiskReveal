import {Component, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
import * as _ from 'lodash';

@Component({
  selector: 'app-table-virtual',
  templateUrl: './table-virtual.component.html',
  styleUrls: ['./table-virtual.component.scss']
})
export class TableVirtualComponent implements OnInit {
  @Output('filterData') filterData: any = new EventEmitter<any>();
  @Output('selectOne') selectOne: any = new EventEmitter<any>();
  @Output('loadMore') loadMore: any = new EventEmitter<any>();
  @Output('dbClickItem') doubleClick: any = new EventEmitter<any>();
  @Output('onRowSelect') onRowSelect: any = new EventEmitter<any>();
  @Output('onRowUnselect') onRowUnselect: any = new EventEmitter<any>();
  @Output('updateFavStatus') updateStatus: any = new EventEmitter<any>();
  @Output('sortDataChange') sortDataChange: any = new EventEmitter<any>();
  @Output('resizeChange') resizeTable: any = new EventEmitter<any>();
  @Output('heightChange') heightChange: any = new EventEmitter<any>();

  @Input()
  rows: any;
  @Input()
  columns: any;
  @Input()
  rowHeight: any;
  @Input()
  headerHeight: any;

  @Input()
  fromSearch: boolean = true;

  selectedRows: any = [];
  lastSelectedIndex = null;
  allChecked = false;
  indeterminate = false;

  constructor() { }

  ngOnInit() {
    this.columns = _.map(this.columns, item => ({...item, name: item.header}));
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
      this.rows.forEach(res => res.selected = false);
      this.lastSelectedIndex = index;
      row.selected = true;
    }
    this.selectedRows = this.rows.filter(ws => ws.selected === true);
    this.isIndeterminate();
  }

  private selectSection(from, to) {
    this.rows.forEach(dt => dt.selected = false);
    if (from === to) {
      this.rows[from].selected = true;
    } else {
      for (let i = from; i <= to; i++) {
        this.rows[i].selected = true;
      }
    }
  }

  rowSelect($event) {
    this.onRowSelect.emit($event);
  }

  rowUnselect($event) {
    this.onRowUnselect.emit($event);
  }

  uncheckRow() {
    this.selectedRows = this.rows.filter(ws => ws.selected === true);
    this.isIndeterminate();
  }

  isIndeterminate() {
    if (this.selectedRows) {
      if (this.selectedRows.length === this.rows.length) {
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

  doubleClickRow(rowData) {
    this.doubleClick.emit(rowData);
  }

  handler(tableColumn, row) {
    this.selectedRows = this.rows.filter(dt => dt.selected);
    const data = this.selectedRows.filter(dt => dt === row) || [];
    data.length === 0 ? this.selectedRows = [...this.selectedRows, row] : null;
    row.selected = true;
    tableColumn.handler(this.selectedRows);
  }

/*  @HostListener('wheel', ['$event']) onElementScroll(event) {
    if (this.contextMenu !== undefined) {
      this.virtualScroll ? this.contextMenu.hide() :
          this.nvContextMenu.hide();
    }
  }*/

}
