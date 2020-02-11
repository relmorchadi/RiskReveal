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
  newDashboardTitle: any;
  selectedDashboard: any;
  searchMode = 'Treaty';

  immutableCols: any;

  manageNewPopUp = false;
  manageInProgressPopUp = false;
  manageArchivedPopUp = false;

  tabs = [1, 2, 3];
  idSelected: number;
  idTab = 0;
  rightSliderCollapsed = false;
  initDash = true;

  previousUrl: string;

  showAdd: any;

  @Select(DashboardState.getFacData) facData$;
  @Select(DashboardState.getRefData) refWidget$;

  dashboards: any;
  refWidget: any;
  editedWidget: any;

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
    this.dispatch([new LoadReferenceWidget(), new fromHD.LoadDashboardFacDataAction()]);
    this.facData$.pipe().subscribe(value => {
      this.newFacCars = this.dataParam(_.get(value, 'new', []));
      this.inProgressFacCars = this.dataParam(_.get(value, 'inProgress', []));
      this.archivedFacCars = this.dataParam(_.get(value, 'archived', []));
    });

    this.refWidget$.pipe().subscribe( value => {
      this.refWidget = value;
      this.detectChanges();
    });

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
          //this.changeWidgetHeight(item, itemComponent);
        }
      },
      resizable: {
        enabled: true,
        stop: (item, itemComponent) => {
          this.changeWidgetHeight(item, itemComponent.$item);
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
    const frontData = {
      ...updatedDashboard,
      name: updatedDashboard.dashboardName
    };
    this.dashboards = _.map(this.dashboards, item => {
      return item.id === dashboardId ? _.merge({}, item, frontData) : item;
    });
    this.selectedDashboard = _.find(this.dashboards, item => item.id === dashboardId);
    this.dispatch(new fromHD.ChangeSelectedDashboard({selectedDashboard: _.cloneDeep(this.selectedDashboard)}));
    this.dashboardAPI.updateDashboard(dashboardId, updatedDashboard).subscribe(data => {},
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

  deleteWidgetAction(widgetId) {
    this.dashboards = _.map(this.dashboards, item => {
      if (item.id === this.selectedDashboard.id) {
        return {...item, widgets: _.filter(item.widgets, widgetItem =>
              widgetItem.userDashboardWidgetId !== widgetId)};
      }
      return item;
    });
    this.selectedDashboard = _.find(this.dashboards, item => item.id === this.selectedDashboard.id);
    this.dashboardAPI.deleteWidget(widgetId).subscribe()
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
    const {updatedWidget, updatedDash} = payload;
    const widgetId = updatedWidget.userDashboardWidgetId;

    if(updatedDash !== null) {
      this.dashboards = _.map(this.dashboards, item => {
        if (item.id === this.selectedDashboard.id) {
          return {...item, widgets: _.map(item.widgets, widget => {
              if (widget.userDashboardWidgetId === widgetId) {
                return {...widget, ...updatedDash}
              } else {return widget}
            })}
        } else {return item}
      });
    }

    this.dashboardAPI.updateWidget(updatedWidget).subscribe();
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

  changeWidgetHeight(widgetItem, position) {
    const resizedWidget = _.find(this.selectedDashboard.widgets, item => item.id === widgetItem.widgetId);

    const newPosition = {
      colSpan: position.cols,
      rowSpan: position.rows,
      colPosition: position.x,
      rowPosition: position.y,
      minItemRows: position.minItemRows,
      minItemCols: position.minItemCols,
      userAssignedName: resizedWidget.name,
      userDashboardId: resizedWidget.userDashboardId,
      userDashboardWidgetId: resizedWidget.userDashboardWidgetId,
      userID: resizedWidget.userID,
      widgetId: resizedWidget.widgetId
    };

    this.updateWidgetsDataAction({updatedWidget: newPosition, updatedDash: null})
  }

  openPopUp(event) {
    const {scope, widgetCol, widgetId} = event;
    switch (scope) {
      case 'newCar':
        this.manageNewPopUp = true;
        this.immutableCols = [...widgetCol];
        break;
      case 'inProgressCar':
        this.manageInProgressPopUp = true;
        this.immutableCols = [...widgetCol];
        break;
      case 'archivedCar':
        this.manageArchivedPopUp = true;
        this.immutableCols = [...widgetCol];
        break;
    }
    this.editedWidget = _.find(this.selectedDashboard.widgets , item => item.id === widgetId)
  }

  closePopUp() {
    this.manageNewPopUp = false;
    this.manageInProgressPopUp = false;
    this.manageArchivedPopUp = false;
  }

  saveColumns(event) {
    let tableCols = _.map(event, (item, index: number) => {
      return {columnId: item.columnId,
        order: index + 1,
        visible: true}
    });
    event = _.map(event , item => ({...item, visible: true, display: true}));
    this.dashboards = _.map(this.dashboards, item => {
      if (item.id === this.selectedDashboard.id) {
        return {...item, widgets: _.map(item.widgets, widget => {
            if (widget.userDashboardWidgetId === this.editedWidget.id) {
              _.forEach(widget.columns, element => {
                if (_.findIndex(tableCols, item => item.columnId === element.columnId) === -1) {
                  tableCols = [...tableCols, {columnId: element.columnId, order: 0, visible: false}];
                  event = [...event, {element, visible: false}];
                }
              });
              return {...widget, columns: [...event]}
            } else {return widget}
          })}
      } else {return item}
    });
    this.dashboardAPI.manageColumnsWidget(tableCols).subscribe();
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
    if (!_.isEmpty(_.trim(name))) {
      this.updateDash(this.selectedDashboard, {dashboardName: name});
      this.selectedDashboard.name = name;
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

  changeWidgetName({item, newName}: any) {
    if (_.isEmpty(_.trim(newName))) {
      this.notificationService.createNotification('Information',
          'The Widget Name Cannot Be Empty!',
          'error', 'bottomRight', 6000);
    } else {
      const updatedWidget = {
        colPosition: item.position.x,
        colSpan: item.position.cols,
        minItemCols: item.position.minItemCols,
        minItemRows: item.position.minItemRows,
        rowPosition: item.position.y,
        rowSpan: item.position.rows,
        userAssignedName: newName,
        userDashboardId: item.userDashboardId,
        userDashboardWidgetId: item.userDashboardWidgetId,
        userID: item.userID,
        widgetId: item.widgetId,
      };

      const updatedDash = {
        name: newName
      };

      this.updateWidgetsDataAction({updatedWidget, updatedDash});
    }
  }

  updateColsListener($event) {
    const {widgetId, dashCols} = $event;
    this.dashboards = _.map(this.dashboards, dash => {
      return dash.id === this.selectedDashboard.id ? {...dash, widgets: _.map(dash.widgets, item => {
      return item.id === widgetId ? {...item, columns: dashCols} : item})
    } : dash });

    console.log(_.find(this.dashboards, item => item.id === this.selectedDashboard.id))
  }

  selectTab(id: any) {
    this.idSelected = id;
    this.selectedDashboard = _.find(this.dashboards, ds => ds.id === id);
    this.dispatch(new fromHD.ChangeSelectedDashboard({selectedDashboard: _.cloneDeep(this.selectedDashboard)}));
  }

  dashboardChange(id: any) {
    this.idSelected = id;
    this.selectedDashboard = _.find(this.dashboards, ds => ds.id === id);
    this.dispatch(new fromHD.ChangeSelectedDashboard({selectedDashboard: _.cloneDeep(this.selectedDashboard)}));
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
        col: widget.colPosition,
        row: widget.rowPosition,
        minItemRows: widget.minItemRows,
        minItemCols: widget.minItemCols,
        widgetId: widget.userDashboardWidgetId,
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
      widthNumber: item.dashboardWidgetColumnWidth,
      type: item.dataColumnType,
      display: item.visible,
      filtered: true,
      sorted: true,
    }))
  }
}
