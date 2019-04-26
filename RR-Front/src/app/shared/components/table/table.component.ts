import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import { LazyLoadEvent } from 'primeng/primeng';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})
export class TableComponent implements OnInit {
  @Output('filterData') filterData: any = new EventEmitter<any>();
  @Output('selectOne') selectOne: any = new EventEmitter<any>();
  @Output('loadMore') loadMore: any = new EventEmitter<any>();

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

  constructor() { }

  ngOnInit() {
  }

  sort(sort: { key: string; value: string }): void {
    /*this.sortName = sort.key;
    this.sortValue = sort.value;
    this.search();*/
  }

  filterCol(searchValue: string, searchAddress: string): void {
    this.filterData.emit({searchValue: searchValue, searchAddress: searchAddress});
  }

  loadDataOnScroll(event: LazyLoadEvent) {
      this.loading = true;
      console.log({event});
      setTimeout(() => {
          if (event.first == this.totalRecords) {
            this.loadMore.emit(20);
          } else {
            this.loadMore.emit(20);
          }
          this.loading = false;
        }, 250);
  }

  selectRow(row: any) {
    this.currentSelectedItem = row;
    this.selectOne.emit(row);
  }
}
