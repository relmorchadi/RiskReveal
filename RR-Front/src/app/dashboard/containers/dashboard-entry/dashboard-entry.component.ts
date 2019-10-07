import {Component, OnInit} from '@angular/core';
import {GridsterConfig, GridType} from 'angular-gridster2';
import {RenewalContractScopeComponent} from '../../components/renewal-contract-scope/renewal-contract-scope.component';
import * as _ from 'lodash';
import {NzMessageService} from 'ng-zorro-antd';
import {NotificationService} from '../../../shared/notification.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-dashboard-entry',
  templateUrl: './dashboard-entry.component.html',
  styleUrls: ['./dashboard-entry.component.scss']
})
export class DashboardEntryComponent implements OnInit {
  protected options: GridsterConfig;
  protected item: any = {x: 0, y: 0, cols: 3, rows: 2};
  newDashboardTitle: any;
  selectedDashboard: any;
  searchMode = 'Treaty';
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
          position: {cols: 3, rows: 2, col: 0, row: 0}
        },
        {
          id: 2,
          name: 'Priced PLTs Changed',
          icon: 'icon-sliders-v-alt',
          selected: false,
          componentName: 'Priced PLTs Changed',
          position: {cols: 3, rows: 2, col: 0, row: 0}
        },
        {
          id: 3,
          name: 'Contract Scope Changed',
          icon: 'icon-adjust-circle',
          selected: false,
          componentName: 'Contract Scope Changed',
          position: {cols: 3, rows: 2, col: 0, row: 0}
        },
        {
          id: 4,
          name: 'Latest Published PLTs',
          icon: 'icon-window-grid',
          selected: false,
          componentName: 'Latest Published PLTs',
          position: {cols: 3, rows: 2, col: 0, row: 0}
        },
        {
          id: 4,
          name: 'Renewal Tracker',
          icon: 'icon-history-alt',
          selected: false,
          componentName: 'Renewal Tracker',
          position: {cols: 3, rows: 2, col: 0, row: 0}
        }
      ],
      fac: [
        {
          id: 99, icon: 'icon-camera-focus', name: 'New Car Status Widget',
          componentName: 'facWidgetComponent', selected: false,
          position: {cols: 3, rows: 2, col: 0, row: 0}
        },
        {
          id: 100, icon: 'icon-camera-focus', name: 'In Progress Car Status Widget',
          componentName: 'facWidgetComponent', selected: false,
          position: {cols: 3, rows: 2, col: 0, row: 0}
        },
        {
          id: 101, icon: 'icon-camera-focus', name: 'Archived Car Status Widget',
          componentName: 'facWidgetComponent', selected: false,
          position: {cols: 3, rows: 2, col: 0, row: 0}
        }
      ]
    },
    {
      id: 1,
      name: 'Fac Dashboard',
      visible: true,
      items: [
        {
          id: 1,
          name: 'Renewal Contract Scope',
          selected: false,
          icon: 'icon-window-section',
          componentName: 'RenewalContractScopeComponent',
          position: {cols: 3, rows: 2, col: 0, row: 0}
        },
        {
          id: 2,
          name: 'Priced PLTs Changed',
          icon: 'icon-sliders-v-alt',
          selected: false,
          componentName: 'Priced PLTs Changed',
          position: {cols: 3, rows: 2, col: 0, row: 0}
        },
        {
          id: 3,
          name: 'Contract Scope Changed',
          icon: 'icon-adjust-circle',
          selected: false,
          componentName: 'Contract Scope Changed',
          position: {cols: 3, rows: 2, col: 0, row: 0}
        },
        {
          id: 4,
          name: 'Latest Published PLTs',
          icon: 'icon-window-grid',
          selected: false,
          componentName: 'Latest Published PLTs',
          position: {cols: 3, rows: 2, col: 0, row: 0}
        },
        {
          id: 4,
          name: 'Renewal Tracker',
          icon: 'icon-history-alt',
          selected: false,
          componentName: 'Renewal Tracker',
          position: {cols: 3, rows: 2, col: 0, row: 0}
        }
      ],
      fac: [
        {
          id: 99, icon: 'icon-camera-focus', name: 'New Car Status Widget',
          componentName: 'facWidgetComponent', selected: true,
          position: {cols: 3, rows: 2, col: 0, row: 0}
        },
        {
          id: 100, icon: 'icon-camera-focus', name: 'In Progress Car Status Widget',
          componentName: 'facWidgetComponent', selected: false,
          position: {cols: 3, rows: 2, col: 0, row: 0}
        },
        {
          id: 101, icon: 'icon-camera-focus', name: 'Archived Car Status Widget',
          componentName: 'facWidgetComponent', selected: false,
          position: {cols: 3, rows: 2, col: 0, row: 0}
        }
      ]
    },
  ];

  dashboardsMockData = [];
  dashboardTitle = 'Dashboard N°1';
  tabs = [1, 2, 3];
  idSelected: number;
  idTab = 0;
  rightSliderCollapsed = false;

  widgetsMockData = {
    treaty: [
      {
        id: 1, icon: 'icon-window-section', title: 'Renewal Contract Scope',
        componentName: 'RenewalContractScopeComponent', selected: true,
        position: {cols: 3, rows: 2, col: 0, row: 0}
      },
      {
        id: 2, icon: 'icon-sliders-v-alt', title: 'Priced PLTs Changed',
        componentName: 'Priced PLTs Changed', selected: true,
        position: {cols: 3, rows: 2, col: 0, row: 0}
      },
      {
        id: 3, icon: 'icon-adjust-circle', title: 'Contract Scope Changed',
        componentName: 'Contract Scope Changed', selected: true,
        position: {cols: 3, rows: 2, col: 0, row: 0}
      },
      {
        id: 4, icon: 'icon-window-grid', title: 'Latest Published PLTs',
        componentName: 'Latest Published PLTs', selected: true,
        position: {cols: 3, rows: 2, col: 0, row: 0}
      },
      {
        id: 5, icon: 'icon-history-alt', title: 'Renewal Tracker',
        componentName: 'Renewal Tracker', selected: true,
        position: {cols: 3, rows: 2, col: 0, row: 0}
      },
    ],
    fac: [
      {
        id: 99, icon: 'icon-camera-focus', title: 'New Car Status Widget',
        componentName: 'facWidgetComponent', selected: true,
        position: {cols: 3, rows: 2, col: 0, row: 0}
      },
      {
        id: 100, icon: 'icon-camera-focus', title: 'In Progress Car Status Widget',
        componentName: 'facWidgetComponent', selected: true,
        position: {cols: 3, rows: 2, col: 0, row: 0}
      },
      {
        id: 101, icon: 'icon-camera-focus', title: 'Archived Car Status Widget',
        componentName: 'facWidgetComponent', selected: true,
        position: {cols: 3, rows: 2, col: 0, row: 0}
      }
    ]
  };

  dashboardComparator = (a, b) => (a && b) ? a.id == b.id : false;


  constructor(private nzMessageService: NzMessageService, private notificationService: NotificationService,
              private router: Router) {
  }

  ngOnInit() {
    this.options = {
      gridType: GridType.VerticalFixed,
      enableEmptyCellDrop: true,
      emptyCellDropCallback: () => {
      },
      pushItems: true,
      swap: true,
      pushDirections: {north: true, east: true, south: true, west: true},
      resizable: {enabled: true},
      // itemChangeCallback: this.changeItemPosition.bind(this),
      draggable: {
        enabled: true,
        ignoreContent: true,
        dropOverItems: true,
        dragHandleClass: 'drag-handler',
        ignoreContentClass: 'no-drag',
      },
      displayGrid: 'always',
      minCols: 3,
      minRows: 10,
      maxItemRows: 3,
      //minItemRows:4,
      maxCols: 3,
    };
    this.dashboards = JSON.parse(localStorage.getItem('dashboard')) || this.dashboards;
    this.updateDashboardMockData();
    this.idSelected = this.dashboardsMockData[0].id;
    this.dashboardChange(this.idSelected);
  }

  addDashboard() {
    const {treaty, fac}: any = this.widgetsMockData;
    const selectedTreatyComponent = _.filter(treaty, (e: any) => e.selected);
    const selectedFacComponent = _.filter(fac, (e: any) => e.selected);
    const item = {
      id: this.dashboards[this.dashboards.length - 1 ].id + 1,
      name: this.newDashboardTitle,
      visible: true,
      items: _.map(selectedTreatyComponent,
        ({componentName, title, icon}: any, key) => ({
          id: key,
          componentName,
          name: title,
          icon: icon,
          selected: false,
          position: {rows: 2, cols: 3}
        })
      ),
      fac: _.map(selectedFacComponent,
        ({componentName, title, icon}: any, key) => ({
          id: key,
          componentName,
          name: title,
          icon: icon,
          selected: false,
          position: {rows: 2, cols: 3}
        }))
    };
    if (item.name != null) {
      this.dashboards = [...this.dashboards, item];
      console.log(this.dashboards);
      this.dashboardTitle = this.newDashboardTitle || '';
      this.updateDashboardMockData();
      this.newDashboardTitle = '';
      this.idTab = this.dashboards.length - 1;
      this.idSelected = this.dashboards[this.dashboards.length - 1 ].id;
      this.selectedDashboard = this.dashboards[this.dashboards.length - 1];
    }
  }

  deleteDashboard() {
    if (this.dashboards.length > 1) {
      this.dashboards = _.filter(this.dashboards, (e: any) => this.idSelected != e.id);
      localStorage.setItem('dashboard', JSON.stringify(this.dashboards));
      this.updateDashboardMockData();

      this.idSelected = _.get(this.dashboards, '[0].id', '');
      this.dashboardChange(this.idSelected);
      this.notificationService.createNotification('Information',
        'The dashboard has been deleted successfully.',
        'info', 'bottomRight', 4000);
    } else {
      this.notificationService.createNotification('Information',
        'you can not delete this dashboard you need at least one!',
        'error', 'bottomRight', 4000);
    }

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
    this.dashboardsMockData = this.dashboards; // _.map(this.dashboards, (e) => _.pick(e, 'id', 'name', 'visible', 'items'));
    console.log('here 2');
  }

  routerNavigate() {
    this.router.navigate(['/CreateNewFile']);
  }

  changeItemPosition() {
    let rowEmpty = true;
    this.selectedDashboard.items.forEach(ds => {
      if (ds.selected && ds.position.y === 0 ) {
        rowEmpty = false;
      }
    });
    if (rowEmpty) {
      const selectedItem = this.selectedDashboard.items.filter(ds => ds.selected === true);
      this.selectedDashboard.items.forEach(ds => {
        if  (ds.id === selectedItem[0].id) {
          ds.position.y = 0;
        }
      });
    }
    this.itemChanges();
  }

  itemChanges() {
    localStorage.setItem('dashboard', JSON.stringify(this.dashboards));
  }

  selectTab(id: any) {
    this.idSelected = id;
    const filterData = this.dashboards.filter(ds => ds.id === id);
    this.selectedDashboard = filterData[0];
  }

  dashboardChange(id: any) {
    const name: any = _.get(_.find(this.dashboardsMockData, {id}), 'name');
    this.dashboardTitle = name || '';
    this.idSelected = id;
    const filterData = this.dashboardsMockData.filter(ds => ds.id === id);
    this.selectedDashboard = filterData[0];
    if (filterData[0].visible === true) {
      let idSel = 0;
      this.dashboardsMockData.forEach(
        ds => {
          if (ds.visible === true && ds.id < id) {
            ++idSel;
          }
        },
      );
      this.idTab = idSel;
    }
  }

  deleteItem(dashboardId: any, itemId: any) {
    /*   this.dashboards[id].items = _.filter(this.dashboards[id].items, (e: any) => e.id != item.id);
       this.dashboards[id].items = _.map(this.dashboards[id].items, (e, id) => ({...e, id}));*/
    const dashboard: any = this.dashboards.filter(ds => ds.id === this.selectedDashboard.id)[0];
    if (itemId > 5) {
      dashboard.items = dashboard.items.filter(ds => ds.id !== itemId);
    } else {
      dashboard.items[itemId - 1].selected = false;
    }
    localStorage.setItem('dashboard', JSON.stringify(this.dashboards));
    this.updateDashboardMockData();
  }

  changeName(dashboardId: any, {itemId, newName}: any) {
    const dashboard: any = this.dashboards.filter(ds => ds.id === this.selectedDashboard.id)[0];
    if (itemId <= 5) {
      const newItem = dashboard.items.filter(ds => ds.id === itemId);
      const copy = Object.assign({}, newItem[0], {
        name: newName,
        id: dashboard.items.length + 1
      });
      dashboard.items.push(copy);
      newItem[0].selected = false;
    } else {
      let index = _.findIndex(dashboard.items, {id: itemId});
      dashboard.items = _.merge(dashboard.items, {[index]: {name: newName}});
    }
    localStorage.setItem('dashboard', JSON.stringify(this.dashboards));
    this.updateDashboardMockData();
    // this.editName = false;
  }

  duplicate(dashboardId: any, itemName: any) {
    const dashboard: any = this.dashboards.filter(ds => ds.id === this.selectedDashboard.id)[0];
    const duplicatedItem: any = dashboard.items.filter(ds => ds.name === itemName);
    const copy = Object.assign({}, duplicatedItem[0], {
      id: dashboard.items.length + 1,
      selected: true,
    });
    dashboard.items = [...dashboard.items, copy];
    localStorage.setItem('dashboard', JSON.stringify(this.dashboards));
    this.updateDashboardMockData();
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
    const filterData = this.dashboards.filter(ds => ds.id === tab.id);
    this.selectedDashboard = filterData[0];
  }

  addRemoveItem(item, dashboard) {
    dashboard.items.forEach(ds => {
      if (ds.id === item.id) {
        ds.selected = !ds.selected;
      }
    });
    _.forEach(this.dashboards , ds => {
      if (ds.id === dashboard.id) {
        ds = dashboard;
      }
    });
    this.updateDashboardMockData();
  }

  addRemoveItemFac(item, dashboard) {
    dashboard.fac.forEach(ds => {
      if (ds.id === item.id) {
        ds.selected = !ds.selected;
      }
    });
    console.log(dashboard.fac);
    _.forEach(this.dashboards , ds => {
      if (ds.id === dashboard.id) {
        ds = dashboard;
      }
    });
    this.updateDashboardMockData();
  }

  onEnterAdd(keyEvent) {
    if (keyEvent.key === 'Enter') {
      keyEvent.target.value !== '' ? this.addDashboard() : null;
    }
  }

  loopOverDashboard(dashboard: any) {
    return _.concat(dashboard.items, dashboard.fac) ;
  }
}
