import { Component, OnInit, Input, Output } from '@angular/core';
import { LazyLoadEvent } from 'primeng/primeng';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})
export class TableComponent implements OnInit {

  loading: boolean;

  @Input()
  totalRecords: number;
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

  filter(listOfSearchName: string[], searchAddress: string): void {
    /*this.listOfSearchName = listOfSearchName;
    this.searchAddress = searchAddress;
    this.search();*/
  }

  search(): void {
    /* filter data
    const filterFunc = (item: { name: string; age: number; address: string }) =>
      (this.searchAddress ? item.address.indexOf(this.searchAddress) !== -1 : true) &&
      (this.listOfSearchName.length ? this.listOfSearchName.some(name => item.name.indexOf(name) !== -1) : true);
    const data = this.listOfData.filter(item => filterFunc(item));
    /** sort data
    if (this.sortName && this.sortValue) {
      this.listOfData = data.sort((a, b) =>
        this.sortValue === 'ascend'
          ? a[this.sortName!] > b[this.sortName!]
          ? 1
          : -1
          : b[this.sortName!] > a[this.sortName!]
          ? 1
          : -1
      );
    } else {
      this.listOfData = data;
    }*/
  }

  loadDataOnScroll(event: LazyLoadEvent) {
    this.loading = true;
    setTimeout(() => {
      //last chunk
      if (event.first === 249980)
        this.listOfData = this.loadChunk(event.first, 20);
      else
        this.listOfData = this.loadChunk(event.first, event.rows);

      this.loading = false;
    }, 250);
  }
  loadChunk(index, length) {
    let chunk = [];
    for (let i = 0; i < length; i++) {
      chunk[i] = {...this.listOfData[i]};
    }

    return chunk;
  }
}
