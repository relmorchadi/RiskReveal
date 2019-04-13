import { Component, OnInit, Input, Output } from '@angular/core';
import { LazyLoadEvent } from 'primeng/primeng';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})
export class TableComponent implements OnInit {
  sortName: string | null = null;
  sortValue: string | null = null;
  @Input()
  listOfData: any[];
  @Input()
  scrolable: any[];
  @Input()
  Frozen: any[];
  @Input()
  tableColumn: any[];
  @Input()
  tableHeight: string;
  @Input()
  tableWidth: string;

  constructor() { }

  ngOnInit() {
  }
  loadDataOnScroll(event: LazyLoadEvent) {
    // this.loading = true;

    // for demo purposes keep loading the same dataset
    // in a real production application, this data should come from server by building the query with LazyLoadEvent options
    /* setTimeout(() => {
        //last chunk
        if (event.first === 249980)
            this.virtualCars = this.loadChunk(event.first, 20);
        else
            this.virtualCars = this.loadChunk(event.first, event.rows);
        this.loading = false;
    }, 250);   */
}
}
