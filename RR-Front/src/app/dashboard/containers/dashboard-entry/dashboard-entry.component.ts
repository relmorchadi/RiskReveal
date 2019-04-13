import {Component, OnInit} from '@angular/core';
import {GridsterConfig, GridType} from 'angular-gridster2';
import {RenewalContractScopeComponent} from '../../components/renewal-contract-scope/renewal-contract-scope.component';
import * as _ from 'lodash';
@Component({
  selector: 'app-dashboard-entry',
  templateUrl: './dashboard-entry.component.html',
  styleUrls: ['./dashboard-entry.component.scss']
})
export class DashboardEntryComponent implements OnInit {
  protected options: GridsterConfig;
  protected item: any = {x: 0, y: 0, cols: 10, rows: 5};
  newDashboardTitle: any;
  dashboards: any = [
    {
    id: 0,
    name: 'Dashboard 1',
    items: [
      {
        id: 1,
        name: 'Renewal Contract Scope',
        componentName: 'RenewalContractScopeComponent',
        position: {cols: 10, rows: 5, col: 0, row: 0}
      },
    ]
  },
    {
      id: 1,
      name: 'Dashboard 2',
      items: [
        {
          id: 1,
          name: 'Renewal Contract Scope',
          componentName: 'RenewalContractScopeComponent',
          position: {cols: 10, rows: 5, col: 0, row: 3}
        },

      ]
    },
  ];

  dashboardsMockData = [];
  selectedDashboard: any = 0;

  widgetsMockData = {
    treaty: [
      {id: 1, icon: 'icon-window-section', title: 'Renewal Contract Scope',
       componentName: 'RenewalContractScopeComponent', selected: true},
      {id: 2, icon: 'icon-sliders-v-alt', title: 'Priced PLTs Changed',
      componentName: 'RenewalContractScopeComponent', selected: true},
      {id: 3, icon: 'icon-adjust-circle', title: 'Contract Scope Changed',
       componentName: 'RenewalContractScopeComponent', selected: true},
      {id: 4, icon: 'icon-window-grid', title: 'Latest Published PLTs',
       componentName: 'RenewalContractScopeComponent', selected: true},
      {id: 5, icon: 'icon-history-alt', title: 'Renewal Tracker',
       componentName: 'RenewalContractScopeComponent', selected: true},
    ],
    fac: [
      {id: 1, icon: 'icon-camera-focus', title: 'Car Status Widget',
       componentName: 'RenewalContractScopeComponent', selected: false}
    ]
  };

  dashboardComparator = (a, b) => (a && b) ? a.id == b.id : false;

  dashboardTitle = 'Dashboard N°1';
  tabs = [1, 2, 3];

  rightSliderCollapsed = false;

  constructor() {
  }
  deleteDashboard() {
    this.dashboards = _.filter(this.dashboards, (e: any) => this.selectedDashboard != e.id);
    localStorage.setItem('dashboard',JSON.stringify(this.dashboards));
    this.updateDashboardMockData();

    this.selectedDashboard = _.get(this.dashboards, '[0].id', '');
    console.log({id:this.selectedDashboard});
    this.dashboardChange(this.selectedDashboard);

  }
  addDashboard() {
    let {treaty, fac}: any = this.widgetsMockData;
    let selectedTreatyComponent = _.filter(treaty, (e: any) => e.selected);
    let selectedFacComponent = _.filter(fac, (e: any) => e.selected);

    this.dashboards.push({
      id: this.dashboards.length,
      name: this.newDashboardTitle,
      items:_.concat(_.map(selectedTreatyComponent,
        ({componentName, title}: any, key) => ({id: key, componentName, name: title, position: {rows: 5, cols: 5}})),
        _.map(selectedFacComponent,
          ({componentName, title}: any, key) => ({id: key, componentName, name: title, position: {rows: 5, cols: 5}}))
        )
    })
    this.updateDashboardMockData()
    this.newDashboardTitle = '';
  }
  ngOnInit() {
    this.options = {
      gridType: GridType.Fit,
      enableEmptyCellDrop: true,
      emptyCellDropCallback: () => {},
      pushItems: true,
      swap: true,
      pushDirections: { north: true, east: true, south: true, west: true },
      resizable: { enabled: true },
      itemChangeCallback: this.itemChanges.bind(this),
      draggable: {
        enabled: true,
        ignoreContent: true,
        dropOverItems: true,
        dragHandleClass: 'drag-handler',
        ignoreContentClass: 'no-drag',
      },
      displayGrid: 'always',
      minCols: 10,
      minRows: 10
    };
    this.dashboards = JSON.parse(localStorage.getItem('dashboard')) || this.dashboards
    this.updateDashboardMockData();
    this.dashboardChange(this.selectedDashboard);
  }
  changeDashboardName(name) {
    let dashIndex = _.findIndex(this.dashboards,{id:this.selectedDashboard})
    if(dashIndex != -1) this.dashboards[dashIndex].name = name
    localStorage.setItem('dashboard', JSON.stringify(this.dashboards))
    this.updateDashboardMockData();
  }
  updateDashboardMockData(){
    this.dashboardsMockData = _.map(this.dashboards, (e) => _.pick(e, 'id', 'name'));
  }
  itemChanges() {
    localStorage.setItem('dashboard', JSON.stringify(this.dashboards))
  }
  dashboardChange(id: any) {
    let name: any =  _.get(_.find(this.dashboards, {id}), 'name');
    this.dashboardTitle = name || '';
  }
  delete(id, item) {
    this.dashboards[id].items = _.filter(this.dashboards[id].items, (e: any) => e.id != item.id);
    this.dashboards[id].items = _.map(this.dashboards[id].items, (e, id) => ({...e, id}));
    localStorage.setItem('dashboard', JSON.stringify(this.dashboards));
  }
  duplicate(id, item) {
    this.dashboards[id].items = _.concat(this.dashboards[id].items, [{...item, id: this.dashboards[id].items.length + 1}])
    this.dashboards[id].items = _.map(this.dashboards[id].items, (e, id) => ({...e, id}));
    localStorage.setItem('dashboard', JSON.stringify(this.dashboards));
  }


}
