import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'inuring-datatable',
  templateUrl: './inuring-datatable.component.html',
  styleUrls: ['./inuring-datatable.component.scss']
})
export class InuringDatatableComponent implements OnInit {

  @Input('data') data;

  cols= [
    { field: 'id', header: 'ID' },
    { field: 'name', header: 'Name' },
    { field: 'description', header: 'Description' },
    { field: 'statsExchangeRate', header: 'Stats Exchange Rate' },
    { field: 'status', header: 'Status' },
    { field: 'creationDate', header: 'Creation Date' },
    { field: 'lastModifiedDate', header: 'Last Modified Date' },
    { field: 'lock', header: '' },
    { field: 'actions', header: '' },
  ];

  constructor() { }

  ngOnInit() {
  }



}
