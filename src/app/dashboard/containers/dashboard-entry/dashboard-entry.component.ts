import {Component, OnInit} from '@angular/core';
import {GridsterConfig, GridType} from 'angular-gridster2';

@Component({
  selector: 'app-dashboard-entry',
  templateUrl: './dashboard-entry.component.html',
  styleUrls: ['./dashboard-entry.component.scss']
})
export class DashboardEntryComponent implements OnInit {
  protected options: GridsterConfig;
  protected item:any = {x:0,y:0,cols:10,rows:5}

  dashboardsMockData = [
    {id: 1, title: 'Dashboard N°1'},
    {id: 2, title: 'Dashboard N°2'},
    {id: 3, title: 'Dashboard N°3'},
    {id: 4, title: 'Dashboard N°4'}
  ];

  widgetsMockData ={
    treaty: [
      {id :1, icon: 'icon-window-section', title:'Renewal Contract Scope', selected: true},
      {id :2, icon: 'icon-sliders-v-alt', title:'Priced PLTs Changed', selected: true},
      {id :3, icon: 'icon-adjust-circle', title:'Contract Scope Changed', selected: true},
      {id :4, icon: 'icon-window-grid', title:'Latest Published PLTs', selected: true},
      {id :5, icon: 'icon-history-alt', title:'Renewal Tracker', selected: true},
    ],
    fac: [
      {id :1, icon: 'icon-camera-focus', title:'Car Status Widget', selected: false}
    ]
  };

  dashboardComparator = (a,b) => (a && b) ? a.id == b.id : false;

  selectedDashboard;
  dashboardTitle="Dashboard N°1";
  tabs = [1, 2, 3];

  rightSliderCollapsed = false;

  constructor() {
    this.selectedDashboard = this.dashboardsMockData[0];
  }

  ngOnInit() {
    this.options = {
      gridType: GridType.Fit,
      enableEmptyCellDrop: true,
      emptyCellDropCallback: ()=>{}, //this.onDrop,
      pushItems: true,
      swap: true,
      pushDirections: { north: true, east: true, south: true, west: true },
      resizable: { enabled: true },
      itemChangeCallback: ()=>{} ,//this.itemChange.bind(this),
      draggable: {
        enabled: true,
      //  ignoreContent: true,
      //  dropOverItems: true,
       // dragHandleClass: 'drag-handler',
       // ignoreContentClass: 'no-drag',
      },

      displayGrid: 'always',
      minCols: 10,
      minRows: 10
    };
  }

  dashboardChange(param){
    this.dashboardTitle= param ? param.title : null;
  }


}
