import {ChangeDetectorRef, Component, ContentChild, ElementRef, OnInit, QueryList, ViewChild} from '@angular/core';
import {CompactType, DisplayGrid, GridsterConfig, GridType} from 'angular-gridster2';
import {RenewalContractScopeComponent} from '../../components/renewal-contract-scope/renewal-contract-scope.component';
import * as _ from 'lodash';
import {NzMessageService} from 'ng-zorro-antd';
import {NotificationService} from '../../../shared/notification.service';
import {NavigationStart, Router} from '@angular/router';
import {BaseContainer} from "../../../shared/base";
import {ofActionCompleted, Select, Store} from "@ngxs/store";
import * as fromHD from "../../../core/store/actions";
import {DashboardState} from "../../../core/store/states";
import {DashData} from "./data";
import {first, timeout} from "rxjs/operators";
import {LoadReferenceWidget} from "../../../core/store/actions";
import {Actions, ofActionDispatched} from '@ngxs/store';

@Component({
  selector: 'app-dashboard-entry',
  templateUrl: './dashboard-entry.component.html',
  styleUrls: ['./dashboard-entry.component.scss']
})
export class DashboardEntryComponent extends BaseContainer implements OnInit {
  protected options: GridsterConfig;
  protected item: any = {x: 0, y: 0, cols: 3, rows: 2};
  newDashboardTitle: any;
  selectedDashboard: any;
  searchMode = 'Treaty';

  newCols: any;
  inProgressCols: any;
  archivedCols: any;

  immutableNew: any;
  immutableInProgress: any;
  immutableArchive: any;

  manageNewPopUp = false;
  manageInProgressPopUp = false;
  manageArchivedPopUp = false;

  dashboardsMockData = [];
  tabs = [1, 2, 3];
  idSelected: number;
  idTab = 0;
  rightSliderCollapsed = false;
  initDash = true;

  previousUrl: string;

  showAdd: any;

  @Select(DashboardState.getFacData) facData$;
  @Select(DashboardState.getDashboards) dashboards$;
  @Select(DashboardState.getRefData) refWidget$;

  dashboards: any;
  dashboardCopy: any;
  refWidget: any;

  newFacCars: any;
  inProgressFacCars: any;
  archivedFacCars: any;

  searchInput: ElementRef;
  @ViewChild('searchInput') set assetInput(elRef: ElementRef) {
    this.searchInput = elRef;
  }

  @ViewChild('popConfirm') confirmPopUp;

  dashboardComparator = (a, b) => (a && b) ? a.id == b.id : false;

  constructor(private nzMessageService: NzMessageService, private notificationService: NotificationService,
              private router: Router, _baseStore: Store, private action: Actions,
              _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    this.dispatch([new LoadReferenceWidget(),
      new fromHD.LoadDashboardsAction({userId: 1}), new fromHD.LoadDashboardFacDataAction()]);
    this.facData$.pipe().subscribe(value => {
      this.newFacCars = this.dataParam(_.get(value, 'new', []));
      this.inProgressFacCars = this.dataParam(_.get(value, 'inProgress', []));
      this.archivedFacCars = this.dataParam(_.get(value, 'archived', []));
    });

    this.refWidget$.pipe().subscribe( value => {
      this.refWidget = value;
      this.detectChanges();
    });

    this.dashboards$.pipe().subscribe(value => {
      this.dashboards = value;
      if (this.initDash && this.dashboards !== null) {
        this.idSelected = this.dashboards[0].id;
        this.dashboardChange(this.idSelected);
        this.initDash = false;
      }
      this.detectChanges();
    });

    this.options = {
      gridType: GridType.VerticalFixed,
      compactType: 'compactUp',
      margin: 10,
      outerMargin: true,
      outerMarginTop: null,
      outerMarginRight: null,
      outerMarginBottom: null,
      outerMarginLeft: null,
      // useTransformPositioning: true,
      // mobileBreakpoint: 640,
      minCols: 1,
      maxCols: 3,
      minRows: 1,
      maxRows: 15,
      maxItemCols: 3,
      minItemCols: 1,
      maxItemRows: 100,
      minItemRows: 1,
      maxItemArea: 2500,
      minItemArea: 1,
      defaultItemCols: 1,
      defaultItemRows: 1,
      // fixedColWidth: 105,
      fixedRowHeight: 245,
      keepFixedHeightInMobile: true,
      // keepFixedWidthInMobile: false,
      scrollVertical: true,
      disableScrollHorizontal: true,
      scrollSensitivity: 10,
      scrollSpeed: 20,
      enableEmptyCellClick: false,
      enableEmptyCellContextMenu: false,
      enableEmptyCellDrop: false,
      enableEmptyCellDrag: false,
      enableOccupiedCellDrop: false,
      ignoreMarginInRow: false,
      draggable: {
        enabled: true,
        ignoreContent: true,
        dropOverItems: true,
        dragHandleClass: 'drag-handler',
        ignoreContentClass: 'no-drag',
        stop: (item, itemComponent, event) => {
          console.log(item, itemComponent, event);
        }
      },
      resizable: {
        enabled: true,
        stop: (item, itemComponent, event) => {
          console.log(item, itemComponent, event);
          this.changeWidgetHeight(itemComponent.$item.rows);
        }
      },
      swap: true,
      pushItems: true,
      disablePushOnDrag: true,
      disablePushOnResize: false,
      pushDirections: {north: true, east: true, south: true, west: true},
      pushResizeItems: true,
      displayGrid: DisplayGrid.Always,
      disableWindowResize: true,
      disableWarnings: false,
      scrollToNewItems: false
    };

    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        window.localStorage.setItem('previousUrl', this.router.url);
      }
    });
    this.setTabValue();
  }

  resetImmutable() {
/*    this.immutableNew = [...this.newCols];
    this.immutableInProgress = [...this.inProgressCols];
    this.immutableArchive = [...this.archivedCols];*/
  }

  dataParam(data) {
    return _.sortBy(_.map(data, item => {
      return {...item, carRequestId: _.toInteger(_.split(item.carRequestId, '-')[1])}
    }), ['carRequestId']).reverse();
  }

  setTabValue() {
/*    const visibleDash = _.filter(this.dashboards, item => item.visible);
    if (visibleDash.length > 1) {
      setTimeout(() => {
        this.idTab = 1;
        const idDash = _.get(this.dashboards[1], 'id', 1);
        this.dashboardChange(idDash);
      }, 500);
    }*/
  }

  openPopUp(event) {
    switch (event) {
      case 'newCar':
        this.manageNewPopUp = true;
        break;
      case 'inProgressCar':
        this.manageInProgressPopUp = true;
        break;
      case 'archivedCar':
        this.manageArchivedPopUp = true;
        break;
    }
  }

  changeWidgetHeight(rows) {

  }

  closePopUp() {
    this.manageNewPopUp = false;
    this.manageInProgressPopUp = false;
    this.manageArchivedPopUp = false;
    this.resetImmutable();
  }

  saveColumns(event, scope) {
    if (scope === 'new') {
      this.newCols = [...event];
    } else if (scope === 'inProgress') {
      this.inProgressCols = [...event];
    } else if (scope === 'archived') {
      this.archivedCols = [...event];
    }
    this.closePopUp();
  }

  emptyField() {
    this.newDashboardTitle = ''
  }

  focusInput() {
    // this.assetInput;
    setTimeout(dt => {
      console.log(this.searchInput);
      this.searchInput.nativeElement.focus();
    })
  }

  addDashboard() {
    const item = {userId: 1, dashboardName: this.newDashboardTitle};
    if (!_.isEmpty(_.trim(item.dashboardName))) {
      this.dispatch(new fromHD.CreateNewDashboardAction(item)).pipe(first()).subscribe(()=>{},()=>{},()=>{
        this.idTab = this.dashboards.length - 1;
        this.idSelected = this.dashboards[this.dashboards.length - 1 ].id;
        this.selectedDashboard = this.dashboards[this.dashboards.length - 1];
      });
      this.emptyField();
    } else {
      this.notificationService.createNotification('Information',
          'An Error Occurred While Creating a New Dashboard Please Verify the name isn\'t Empty before creating a New Dashboard',
          'error', 'bottomRight', 6000);
      this.emptyField();
    }
  }

  updateDash(tab, newObject, redirect = false): void {
    const dashboard = {
      dashBoardSequence: tab.dashBoardSequence,
      dashboardName: tab.dashboardName,
      searchMode: tab.searchMode,
      userDashboardId: tab.id,
      userId: 1,
      visible: tab.visible
    };
    this.dispatch(new fromHD.UpdateDashboardAction({dashboardId: tab.id,
      updatedDashboard: _.merge({}, dashboard, newObject)}));
    redirect ? this.selectedDashboard = this.dashboards.filter(ds => ds.id === tab.id)[0] : null;
  }

  deleteDashboard() {
    if (this.dashboards.length > 1) {
      this.dispatch(new fromHD.DeleteDashboardAction({dashboardId : this.idSelected}));
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
    if (!_.isEmpty(_.trim(name.target.value))) {
      this.updateDash(this.selectedDashboard, {dashboardName: name.target.value});
    } else {
      this.notificationService.createNotification('Information',
          'The Name of the dashboard shouldn\'t Be Empty!',
          'error', 'bottomRight', 4000);
    }
  }

  addRemoveItem(item) {
    const selectedWidget =
    _.filter(this.selectedDashboard.widgets, (itemWidget: any) => item.widgetId === itemWidget.widgetId);
    if (selectedWidget.length === 0) {
      this.dispatch(new fromHD.CreateWidgetAction({selectedDashboard: this.selectedDashboard.id,
      widget: {
        dashoardId: this.selectedDashboard.id,
        referenceWidgetId:item.widgetId,
        userId: 1
      }}))
    } else {
      this.dispatch(new fromHD.DeleteWidgetAction({dashboardWidgetId: selectedWidget[0].userDashboardWidgetId,
      selectedDashboard: this.selectedDashboard
      }))
    }
  }

  updateDashboardMockData() {
    this.dashboardsMockData = this.dashboards; // _.map(this.dashboards, (e) => _.pick(e, 'id', 'name', 'visible', 'items'));
  }

  changeItemPosition() {
  }

  selectTab(id: any) {
    this.idSelected = id;
    const filterData = this.dashboards.filter(ds => ds.id === id);
    this.selectedDashboard = filterData[0];
  }

  dashboardChange(id: any) {
    this.idSelected = id;
    this.selectedDashboard = this.dashboards.filter(ds => ds.id === id)[0];
    if (_.get(this.selectedDashboard, 'visible', false)) {
      let idSel = 0;
      this.dashboards.forEach(
        ds => {
          if (ds.visible === true && ds.id < id) {
            ++idSel;
          }
        },
      );
      this.idTab = idSel;
    }
    this.detectChanges();
  }

  deleteItem(dashboardId: any, itemId: any) {
    /*   this.dashboards[id].items = _.filter(this.dashboards[id].items, (e: any) => e.id != item.id);
       this.dashboards[id].items = _.map(this.dashboards[id].items, (e, id) => ({...e, id}));*/
/*    const dashboard: any = this.dashboards.filter(ds => ds.id === this.selectedDashboard.id)[0];
    if (itemId > 5) {
      dashboard.items = dashboard.items.filter(ds => ds.id !== itemId);
    } else {
      dashboard.items = dashboard.items.map(item => {
        if (item.id === itemId) {
          return {...item, selected: false};
        } else {
          return item;
        }
      });
    }
    localStorage.setItem('dashboard', JSON.stringify(this.dashboards));
    this.updateDashboardMockData();*/
  }

  deleteItemFac(dashboardId: any, deletedItem: any) {
/*    const dashboard: any = this.dashboards.filter(ds => ds.id === this.selectedDashboard.id)[0];
    const {persistent, id} = deletedItem;
    console.log(deletedItem, dashboard);
    if (!persistent) {
      dashboard.fac = dashboard.fac.filter(ds => ds.id !== id);
    } else {
      dashboard.fac = dashboard.fac.map(item => {
        return item.id === id ? {...item, selected: false} : item;
      });
    }
    localStorage.setItem('dashboard', JSON.stringify(this.dashboards));
    this.updateDashboardMockData();*/
  }

  changeWidgetName(dashboardId: any, {itemId, newName}: any) {
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
/*    const dashboard: any = this.dashboards.filter(ds => ds.id === this.selectedDashboard.id)[0];
    const duplicatedItem: any = dashboard.items.filter(ds => ds.name === itemName);
    const copy = Object.assign({}, duplicatedItem[0], {
      id: dashboard.items.length + 1,
      selected: true,
    });
    dashboard.items = [...dashboard.items, copy];
    localStorage.setItem('dashboard', JSON.stringify(this.dashboards));
    this.updateDashboardMockData();*/
  }

  onEnterAdd(keyEvent) {
    if (keyEvent.key === 'Enter') {
      this.showAdd = false;
      keyEvent.target.value !== '' ? this.addDashboard() : null;
    }
  }

  getSelection(item) {
    return _.filter(_.get(this.selectedDashboard, 'widgets', []),
            items => items.widgetId === item.widgetId).length > 0;
  }
}
