import {Component, OnInit} from '@angular/core';
import {GridsterConfig, GridType} from 'angular-gridster2';
import {RenewalContractScopeComponent} from '../../components/renewal-contract-scope/renewal-contract-scope.component';
import * as _ from 'lodash';
import { NzMessageService } from 'ng-zorro-antd';

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
      name: 'Dashboard N°1',
      visible: true,
      items: [
        {
          id: 1,
          name: 'Renewal Contract Scope',
          selected: true,
          icon: 'icon-window-section',
          componentName: 'RenewalContractScopeComponent',
          position: {cols: 10, rows: 5, col: 0, row: 0}
        },
        {
          id: 2,
          name: 'Priced PLTs Changed',
          icon: 'icon-sliders-v-alt',
          selected: false,
          componentName: 'Priced PLTs Changed',
          position: {cols: 10, rows: 5, col: 0, row: 0}
        },
        {
          id: 3,
          name: 'Contract Scope Changed',
          icon: 'icon-adjust-circle',
          selected: false,
          componentName: 'Contract Scope Changed',
          position: {cols: 10, rows: 5, col: 0, row: 0}
        },
        {
          id: 4,
          name: 'Latest Published PLTs',
          icon: 'icon-window-grid',
          selected: false,
          componentName: 'Latest Published PLTs',
          position: {cols: 10, rows: 5, col: 0, row: 0}
        },
        {
          id: 5,
          name: 'Renewal Tracker',
          icon: 'icon-history-alt',
          selected: false,
          componentName: 'Renewal Tracker',
          position: {cols: 10, rows: 5, col: 0, row: 0}
        }
      ],
      fac: [
        {id: 1, icon: 'icon-camera-focus', title: 'Car Status Widget',
          componentName: 'RenewalContractScopeComponent', selected: false}
      ]
    },
    {
      id: 1,
      name: 'Dashboard N°2',
      visible: true,
      items: [
        {
          id: 1,
          name: 'Renewal Contract Scope',
          selected: true,
          icon: 'icon-window-section',
          componentName: 'RenewalContractScopeComponent',
          position: {cols: 10, rows: 5, col: 0, row: 0}
        },
        {
          id: 2,
          name: 'Priced PLTs Changed',
          icon: 'icon-sliders-v-alt',
          selected: false,
          componentName: 'Priced PLTs Changed',
          position: {cols: 10, rows: 5, col: 0, row: 0}
        },
        {
          id: 3,
          name: 'Contract Scope Changed',
          icon: 'icon-adjust-circle',
          selected: false,
          componentName: 'Contract Scope Changed',
          position: {cols: 10, rows: 5, col: 0, row: 0}
        },
        {
          id: 4,
          name: 'Latest Published PLTs',
          icon: 'icon-window-grid',
          selected: false,
          componentName: 'Latest Published PLTs',
          position: {cols: 10, rows: 5, col: 0, row: 0}
        },
        {
          id: 5,
          name: 'Renewal Tracker',
          icon: 'icon-history-alt',
          selected: false,
          componentName: 'Renewal Tracker',
          position: {cols: 10, rows: 5, col: 0, row: 0}
        }
      ],
      fac: [
        {id: 1, icon: 'icon-camera-focus', title: 'Car Status Widget',
          componentName: 'RenewalContractScopeComponent', selected: false}
      ]
    },
  ];

  dashboardsMockData = [];
  dashboardTitle = 'Dashboard N°1';
  tabs = [1, 2, 3];
  idSelected = 0;
  idTab = 0;
  rightSliderCollapsed = false;

  widgetsMockData = {
    treaty: [
      {id: 1, icon: 'icon-window-section', title: 'Renewal Contract Scope',
       componentName: 'RenewalContractScopeComponent', selected: true},
      {id: 2, icon: 'icon-sliders-v-alt', title: 'Priced PLTs Changed',
      componentName: 'Priced PLTs Changed', selected: true},
      {id: 3, icon: 'icon-adjust-circle', title: 'Contract Scope Changed',
       componentName: 'Contract Scope Changed', selected: true},
      {id: 4, icon: 'icon-window-grid', title: 'Latest Published PLTs',
       componentName: 'Latest Published PLTs', selected: true},
      {id: 5, icon: 'icon-history-alt', title: 'Renewal Tracker',
       componentName: 'Renewal Tracker', selected: true},
    ],
    fac: [
      {id: 1, icon: 'icon-camera-focus', title: 'Car Status Widget',
       componentName: 'RenewalContractScopeComponent', selected: false}
    ]
  };

  dashboardComparator = (a, b) => (a && b) ? a.id == b.id : false;



  constructor(private nzMessageService: NzMessageService) {
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

    this.dashboards = JSON.parse(localStorage.getItem('dashboard')) || this.dashboards;
    this.updateDashboardMockData();
    this.dashboardChange(this.idSelected);
  }

  addDashboard() {
    const {treaty, fac}: any = this.widgetsMockData;
    const selectedTreatyComponent = _.filter(treaty, (e: any) => e.selected);
    const selectedFacComponent = _.filter(fac, (e: any) => e.selected);
    const item = {
      id: this.dashboards.length,
      name: this.newDashboardTitle,
      visible: true,
      items: _.concat(_.map(selectedTreatyComponent,
        ({componentName, title, icon}: any, key) => ({id: key, componentName, name: title, icon: icon, selected: true, position: {rows: 5, cols: 5}})),
        _.map(selectedFacComponent,
          ({componentName, title, icon}: any, key) => ({id: key, componentName, name: title, icon: icon, selected: true, position: {rows: 5, cols: 5}}))
      )
    };
    if (item.name != null) {
      this.dashboards = [...this.dashboards, item];
      this.dashboardTitle = this.newDashboardTitle || '';
      this.updateDashboardMockData();
      this.newDashboardTitle = '';
    }
  }

  deleteDashboard() {
    this.dashboards = _.filter(this.dashboards, (e: any) => this.idSelected != e.id);
    localStorage.setItem('dashboard', JSON.stringify(this.dashboards));
    this.updateDashboardMockData();

    this.idSelected = _.get(this.dashboards, '[0].id', '');
    this.dashboardChange(this.idSelected);
    this.nzMessageService.info('delete dashboard Success');
  }

  changeDashboardName(name) {
    if (name != '') {
      const dashIndex = _.findIndex(this.dashboards, {id: this.idSelected});
      if (dashIndex != -1) this.dashboards[dashIndex].name = name;
      localStorage.setItem('dashboard', JSON.stringify(this.dashboards));
      this.updateDashboardMockData();
    }
  }

  updateDashboardMockData() {
    this.dashboardsMockData = _.map(this.dashboards, (e) => _.pick(e, 'id', 'name', 'visible', 'items'));
  }

  itemChanges() {
    localStorage.setItem('dashboard', JSON.stringify(this.dashboards));
  }

  dashboardChange(id: any) {
    const name: any =  _.get(_.find(this.dashboards, {id}), 'name');
    this.dashboardTitle = name || '';
    this.idSelected = id;
    if (this.dashboards[id].visible === true) {
      let idSel = 0;
      this.dashboards.forEach(
        ds => {
          if (ds.visible === true && ds.id < id) {
            ++idSel;
          }
        },
      );
      console.log('2', id, this.idSelected, this.dashboardsMockData[this.idSelected], idSel);
      this.idTab = idSel;
    }
  }

  tabChange(tabSelected) {
    console.log(tabSelected.index);
    let idSel = -1;
    let overall = -1;
    this.dashboards.forEach(
      ds => {
        ++overall;
        if (ds.visible === true) {
          ++idSel;
          // console.log(idSel === tabSelected.index, overall, this.idSelected);
        }
        if (idSel === tabSelected.index) {
          this.dashboardChange(overall);
        }
      }
    );
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

  activeView(tab) {
    this.dashboards.forEach(ds => ds.id === tab.id ? ds.visible = true : null);
    localStorage.setItem('dashboard', JSON.stringify(this.dashboards));
    this.updateDashboardMockData();
    this.idSelected = tab.id;
    this.idTab = tab.id;
  }

  closeTab(tab): void {
    this.dashboards.forEach(ds => ds.id === tab.id ? ds.visible = false : null);
    localStorage.setItem('dashboard', JSON.stringify(this.dashboards));
    this.updateDashboardMockData();
  }

  addRemoveItem(item) {
    item.selected = !item.selected;
  }

  onEnterAdd(keyEvent) {
    if (keyEvent.key === 'Enter' ) {
      keyEvent.target.value !== '' ? this.addDashboard() : null;
    }
  }

}
