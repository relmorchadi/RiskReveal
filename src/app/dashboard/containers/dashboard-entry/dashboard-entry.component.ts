import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-entry',
  templateUrl: './dashboard-entry.component.html',
  styleUrls: ['./dashboard-entry.component.scss']
})
export class DashboardEntryComponent implements OnInit {
  tabs = [1, 2, 3];

  rightSliderCollapsed= false;

  constructor() { }

  ngOnInit() {
  }

}
