import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'slider-right',
  templateUrl: './slider-right.component.html',
  styleUrls: ['./slider-right.component.scss']
})
export class SliderRightComponent implements OnInit {
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



  selectedDashboard=this.dashboardsMockData[0];
  dashboardTitle="Dashboard N°1";
  tabs = [1, 2, 3];

  rightSliderCollapsed = false;

  constructor() {
  }

  ngOnInit() {
  }

  dashboardChange(param){
    this.dashboardTitle= param;
  }
}
