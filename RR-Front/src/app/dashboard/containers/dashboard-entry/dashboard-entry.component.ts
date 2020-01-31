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
import {catchError, first, tap, timeout} from "rxjs/operators";
import {LoadReferenceWidget} from "../../../core/store/actions";
import {Actions, ofActionDispatched} from '@ngxs/store';
import {DashboardApi} from "../../../core/service/api/dashboard.api";
import {of} from "rxjs";

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
              private router: Router, _baseStore: Store, private action: Actions, private dashboardAPI: DashboardApi,
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

/*    this.dashboards$.pipe().subscribe(value => {
      this.dashboards = value;
      if (this.initDash && this.dashboards !== null) {
        this.idSelected = this.dashboards[0].id;
        this.dashboardChange(this.idSelected);
        this.initDash = false;
      }
      this.detectChanges();
    });*/
    this.loadDashboardAction();

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
        stop: (item, itemComponent) => {
          console.log(item, itemComponent);
        }
      },
      resizable: {
        enabled: true,
        stop: (item, itemComponent) => {
          console.log(item, itemComponent);
          this.changeWidgetHeight(itemComponent.$item);
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

  loadDashboardAction() {
    this.dashboardAPI.getDashboards(1).subscribe(data => {
      let dashboards = [];
      _.forEach(data, item => {
        dashboards = [...dashboards, this._formatData(item)]
      });
      this.dashboards = dashboards;
      if (this.initDash && this.dashboards !== null) {
        this.idSelected = this.dashboards[0].id;
        this.dashboardChange(this.idSelected);
        this.initDash = false;
      }
    });
  }

  createNewDashboardAction(payload) {
    this.dashboardAPI.creatDashboards(payload).subscribe(data => {
          this.dashboards = [...this.dashboards, this._formatData(data)];
          this.idTab = this.dashboards.length - 1;
          this.idSelected = this.dashboards[this.dashboards.length - 1 ].id;
          this.selectedDashboard = this.dashboards[this.dashboards.length - 1];
        }
    )
  }

  deleteDashboardAction(payload) {
    const {dashboardId} = payload;
    this.dashboardAPI.deleteDashboards(dashboardId).subscribe(data => {
      this.dashboards = _.filter(this.dashboards, item => item.id !== dashboardId);
      this.idSelected = _.get(this.dashboards, '[0].id', '');
      this.dashboardChange(this.idSelected);
    })
  }

  updateDashboardAction(payload) {
    const {dashboardId, updatedDashboard} = payload;
    this.dashboardAPI.updateDashboard(dashboardId, updatedDashboard).subscribe(
        tap(data => {
          const frontData = {
            ...updatedDashboard,
            name: updatedDashboard.dashboardName
          };
          this.dashboards = _.map(this.dashboards, item => {
            return item.id === dashboardId ? _.merge({}, item, frontData) : item;
          });
        }),
        catchError(err => {
          return of();
        })
    )
  }

  createNewWidgetAction(payload) {
    const {selectedDashboard, widget} = payload;
    this.dashboardAPI.createWidget(widget).subscribe((data: any) => {
      const {userDashboardWidget, columns} = data;
      this.dashboards = _.map(this.dashboards, item => {
        if (item.id === selectedDashboard) {
          return {
            ...item, widgets: [...item.widgets, {
              ...this._formatWidget(userDashboardWidget),
              columns: this._formatColumns(columns)
            }]
          };
        }
        return item;
      });
      this.selectedDashboard = _.find(this.dashboards, item => item.id === this.selectedDashboard.id);
      this.detectChanges();
    })
  }

  duplicateWidgetAction(payload) {
    this.dashboardAPI.duplicateWidget(payload).subscribe((data: any) => {
      const {userDashboardWidget, columns} = data;
      this.dashboards = _.map(this.dashboards, item => {
        if (item.id === this.selectedDashboard.id) {
          return {
            ...item, widgets: [...item.widgets, {
              ...this._formatWidget(userDashboardWidget),
              columns: this._formatColumns(columns)
            }]
          };
        }
        return item;
      });
      this.selectedDashboard = _.find(this.dashboards, item => item.id === this.selectedDashboard.id);
      this.detectChanges();
    })
  }

  deleteWidgetAction(payload) {
    this.dashboards = _.map(this.dashboards, item => {
      if (item.id === this.selectedDashboard.id) {
        return {...item, widgets: _.filter(item.widgets, widgetItem =>
              widgetItem.userDashboardWidgetId !== payload)};
      }
      return item;
    });
    this.selectedDashboard = _.find(this.dashboards, item => item.id === this.selectedDashboard.id);
    this.dashboardAPI.deleteWidget(payload).subscribe()
  }

  deleteAllDashboardByRef(payload) {
    const {selectedDashboard, refId} = payload;
    this.dashboards = _.map(this.dashboards, item => {
      if (item.id === selectedDashboard.id) {
        return {...item, widgets: _.filter(item.widgets, widgetItem =>
              widgetItem.widgetId !== refId)};
      }
      return item;
    });
    this.selectedDashboard = _.find(this.dashboards, item => item.id === this.selectedDashboard.id);
    this.dashboardAPI.deleteAllWidgetByRef(selectedDashboard.id, refId).subscribe();
  }

  updateWidgetsDataAction(payload) {
    const  {columns, userDashboardWidget, widgetId, selectedDashboard} = payload;
    this.dashboards = _.map(this.dashboards, item => {
      if (item.id === selectedDashboard.id) {
        return {...item, widgets: _.map(item.widgets, widget => {
          if (widget.userDashboardWidgetId === widgetId) {
            return {...userDashboardWidget, columns}
          } else {return widget}
        })}
      } else {return item}
    });
    this.selectedDashboard = _.find(this.dashboards, item => item.id === this.selectedDashboard.id);
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

  changeWidgetHeight(position) {
    const newPosition = {
      colSpan: position.cols,
      rowSpan: position.rows,
      colPosition: position.x,
      rowPosition: position.y,
      minItemRows: position.minItemRows,
      minItemCols: position.minItemCols,
    }
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
      this.createNewDashboardAction(item);
      this.emptyField();
      /*.pipe(first()).subscribe(()=>{},()=>{},()=>{});*/
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
    this.updateDashboardAction({dashboardId: tab.id,
      updatedDashboard: _.merge({}, dashboard, newObject)});
    redirect ? this.selectedDashboard = this.dashboards.filter(ds => ds.id === tab.id)[0] : null;
  }

  deleteDashboard() {
    if (this.dashboards.length > 1) {
      this.deleteDashboardAction({dashboardId : this.idSelected});
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
      this.createNewWidgetAction({selectedDashboard: this.selectedDashboard.id,
      widget: {
        dashoardId: this.selectedDashboard.id,
        referenceWidgetId:item.widgetId,
        userId: 1
      }});
    } else {
      this.deleteAllDashboardByRef({selectedDashboard: this.selectedDashboard, refId: item.widgetId})
    }
  }

  deleteItem(dashboardId: any, itemId: any) {

  }

  changeWidgetName({item, newName}: any) {
    const updatedWidget = {
      userDashboardWidget: {
        /*colPosition: item.position.,
        colSpan: item.position.,
        minItemCols: item.position.minItemCols,
        minItemRows: item.position.minItemRows,
        rowPosition: item.position.,,
        rowSpan: item.position.,
        userAssignedName: newName,
        userDashboardId: 0,
        userDashboardWidgetId: 0,
        userID: 0,
        widgetId: 0*/
      }
    }
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

  duplicate(widgetId) {
    this.duplicateWidgetAction(widgetId);
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

  private _formatData(data) {
    const userDashboard = _.get(data, 'userDashboard', data);
    return {
      ...userDashboard,
      id: userDashboard.userDashboardId,
      name: userDashboard.dashboardName,
      widgets: _.map(_.get(data, 'widgets', []), (dashWidget: any) => {
        const widget = dashWidget.userDashboardWidget;
        return {
          ...this._formatWidget(widget),
          columns: this._formatColumns(dashWidget.columns)
        }
      }),
    }
  }

  private _formatWidget(widget) {
    return {
      ...widget,
      id: widget.userDashboardWidgetId,
      name: widget.userAssignedName,
      position: {
        cols: widget.colSpan,
        rows: widget.rowSpan,
        x: widget.colPosition,
        y: widget.rowPosition,
        minItemRows: widget.minItemRows,
        minItemCols: widget.minItemCols
      }
    }
  }

  private _formatColumns(columns) {
    return _.map(_.orderBy(columns, col => col.dashboardWidgetColumnOrder, 'asc'), item => ({
      ...item,
      columnId: item.userDashboardWidgetColumnId,
      WidgetId: item.userDashboardWidgetId,
      field: item.dashboardWidgetColumnName,
      header: item.columnHeader,
      width: item.dashboardWidgetColumnWidth + 'px',
      type: item.dataColumnType,
      display: item.visible,
      filtered: true,
      sorted: true,
    }))
  }
}
