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
import {DashboardState, GeneralConfigState} from "../../../core/store/states";
import {catchError} from "rxjs/operators";
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

  carStatus = {"1": 'NEW', "2": 'In Progress', "3": 'Archived'};

  immutableCols: any;

  manageNewPopUp = false;
  manageInProgressPopUp = false;
  manageArchivedPopUp = false;

  idSelected: number;
  tabIndex = 0;
  idTab = 0;
  rightSliderCollapsed = false;
  initDash = true;
  loadingWidget = false;
  closePrevent = false;

  previousUrl: string;

  showAdd: any;

  @Select(DashboardState.getFacData) facData$;
  @Select(DashboardState.getRefData) refWidget$;
  @Select(DashboardState.getSelectedTab) tabIndex$;

  dashboards: any;
  refWidget: any;
  editedWidget: any;

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
    super.ngOnInit();
    this.dispatch([new LoadReferenceWidget()]);

    this.refWidget$.pipe().subscribe( value => {
      this.refWidget = value;
      this.detectChanges();
    });

    this.tabIndex$.pipe().subscribe(value => {
      if (this.initDash && value !== 0) {
        this.tabIndex = value;
      }
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
          itemComponent.itemChanged
          this.changeWidgetHeight(item, itemComponent.$item);
        },
      },
      resizable: {
        enabled: true,
        stop: (item, itemComponent) => {
          this.changeWidgetHeight(item, itemComponent.$item);
        },
      },
      optionsChanged: (event) => {
        console.log(event);
      },
      swap: true,
      pushItems: true,
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
      return {...item, carRequestId: _.toInteger(item.carRequestId)}
    }), ['carRequestId']).reverse();
  }

  loadDashboardAction() {
    this.dashboardAPI.getDashboards().subscribe(data => {
      let dashboards = [];
      _.forEach(data, item => {
        dashboards = [...dashboards, this._formatData(item)]
      });
      this.dashboards = dashboards;
      if (this.initDash && this.dashboards !== null) {
        if (this.tabIndex === 0) {
          this.idSelected = _.get(_.filter(this.dashboards, item => item.visible)[0], 'id', this.dashboards[0].id);
          this.dashboardChange(this.idSelected);
        } else {
          this.dashboardChange(this.tabIndex);
        }
        this.initDash = false;
      }
    });
  }

  createNewDashboardAction(payload)
  {
    this.dashboardAPI.creatDashboards(payload).subscribe(data => {
          this.dashboards = [...this.dashboards, this._formatData(data)];
          this.idTab = this.dashboards.length - 1;
          this.idSelected = this.dashboards[this.dashboards.length - 1 ].id;
          this.selectedDashboard = this.dashboards[this.dashboards.length - 1];
          this.dispatch(new fromHD.ChangeSelectedDashboard({selectedDashboard: _.cloneDeep(this.selectedDashboard), tabIndex: this.idSelected}));
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
    const dashIndex = _.findIndex(this.dashboards, (item: any) => item.id === dashboardId);
    this.dashboards = _.merge(this.dashboards,  {[dashIndex]: {
       visible: updatedDashboard.visible,
       searchMode: updatedDashboard.searchMode,
       name: updatedDashboard.dashboardName
    }});
    this.selectedDashboard = _.find(this.dashboards, item => item.id === dashboardId);
    this.dispatch(new fromHD.ChangeSelectedDashboard({selectedDashboard: _.cloneDeep(this.selectedDashboard), tabIndex: dashboardId}));
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
      const dashIndex = _.findIndex(this.dashboards, (item: any) => item.id === selectedDashboard);
      this.dashboards = _.merge(this.dashboards, {[dashIndex]: {
          widgets: [...this.dashboards[dashIndex].widgets, {
            ...this._formatWidget(userDashboardWidget),
            columns: this._formatColumns(columns)
          }]
        }});
      this.selectedDashboard = _.find(this.dashboards, item => item.id === this.selectedDashboard.id);
      this.detectChanges();
    })
  }

  duplicateWidgetAction(payload) {
    this.dashboardAPI.duplicateWidget(payload).subscribe((data: any) => {
      const {userDashboardWidget, columns} = data;
      const dashIndex = _.findIndex(this.dashboards, (item: any) => item.id === this.selectedDashboard.id);
      this.dashboards = _.merge(this.dashboards, {[dashIndex]: {
          widgets: [...this.dashboards[dashIndex].widgets, {
            ...this._formatWidget(userDashboardWidget),
            columns: this._formatColumns(columns)
          }]
        }});
      this.selectedDashboard = _.find(this.dashboards, item => item.id === this.selectedDashboard.id);
      this.detectChanges();
    })
  }

  deleteWidgetAction(widgetId) {
    const dashIndex = _.findIndex(this.dashboards, (item: any) => item.id === this.selectedDashboard.id);
    _.assignIn(this.dashboards[dashIndex], {
        widgets: _.filter(this.dashboards[dashIndex].widgets,
                widgetItem => widgetItem.userDashboardWidgetId !== widgetId)
      });
    this.selectedDashboard = _.find(this.dashboards, item => item.id === this.selectedDashboard.id);
    this.dashboardAPI.deleteWidget(widgetId).subscribe()
  }

  deleteAllDashboardByRef(payload) {
    const {selectedDashboard, refId} = payload;
    const dashIndex = _.findIndex(this.dashboards, (item: any) => item.id === this.selectedDashboard.id);
    _.assignIn(this.dashboards[dashIndex],  {
        widgets: _.filter(this.dashboards[dashIndex].widgets,
            widgetItem => widgetItem.widgetId !== refId)
      });
    this.selectedDashboard = _.find(this.dashboards, item => item.id === this.selectedDashboard.id);
    this.dashboardAPI.deleteAllWidgetByRef(selectedDashboard.id, refId).subscribe();
  }

  updateWidgetsDataAction(payload) {
    const {updatedWidget, updatedDash} = payload;
    const widgetId = updatedWidget.userDashboardWidgetId;
    const dashIndex = _.findIndex(this.dashboards, (item: any) => item.id === this.selectedDashboard.id);
    const dashUpdated = _.find(this.dashboards, (item: any) => item.id === this.selectedDashboard.id);
    const widgetIndex = _.findIndex(dashUpdated.widgets, (item: any) => item.userDashboardWidgetId === widgetId);

    if(updatedDash !== null) {
      this.dashboards = _.merge(this.dashboards, {[dashIndex]: {
          widgets: _.merge(this.dashboards[dashIndex].widgets, {[widgetIndex]: {
            ...this.dashboards[dashIndex].widgets[widgetIndex], ...updatedDash}
          })
        }});
    }

    this.dashboardAPI.updateWidget(updatedWidget).subscribe();
    this.selectedDashboard = _.find(this.dashboards, item => item.id === this.selectedDashboard.id);
  }

  openRightSlider() {
    this._closePrevent();
    this.rightSliderCollapsed = true;
    const dashId = _.get(_.filter(this.dashboards, item => item.visible)[this.idTab], 'id', this.dashboards[0].id);
    this.selectTab(dashId);
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
    const indexDash = _.findIndex(this.dashboards, (item: any) => item.id === this.selectedDashboard.id);
    const dashUpdated = _.find(this.dashboards, (item: any) => item.id === this.selectedDashboard.id);
    const widgetIndex = _.findIndex(dashUpdated.widgets, (item: any) => item.userDashboardWidgetId === this.editedWidget.id);
    const updatedWidget = _.find(dashUpdated.widgets, (item: any) => item.userDashboardWidgetId === this.editedWidget.id);
    _.forEach(updatedWidget.columns, element => {
      if (_.findIndex(tableCols, item => item.columnId === element.columnId) === -1) {
        tableCols = [...tableCols, {columnId: element.columnId, order: tableCols.length + 1, visible: false}];
        event = [...event, {...element, visible: false}];
      }
    });
    this.dashboards = _.merge(this.dashboards,  { [indexDash]: {
        widgets: _.merge(this.dashboards[indexDash].widgets,  { [widgetIndex]: {columns: [...event]}
          })}
      });
    this.loadingWidget = true;
    this.dashboardAPI.manageColumnsWidget(tableCols).subscribe(() => {}, () => {}, () => {
      const carStatus = this.carStatus[updatedWidget.widgetId];
      const identifier = updatedWidget.id;
      this.dispatch(new fromHD.LoadDashboardFacDataAction({identifier ,  pageNumber: 1, carStatus})).subscribe(() => {}, () => {}, () => {
        this.loadingWidget = false;
        this.detectChanges();
      })
    });
    this.closePopUp();
  }

  emptyField() {
    this.newDashboardTitle = '';
  }

  focusInput() {
    // this.assetInput;
    setTimeout(dt => {
      this.searchInput.nativeElement.focus();
    })
  }

  addDashboard() {
    const item = {userId: 1, dashboardName: this.newDashboardTitle};
    if (!_.isEmpty(_.trim(item.dashboardName))) {
      this.createNewDashboardAction(item);
      this.emptyField();
    } else {
      this.notificationService.createNotification('Information',
          'An Error Occurred While Creating a New Dashboard Please Verify the name isn\'t Empty before creating a New Dashboard',
          'error', 'bottomRight', 6000);
      this.emptyField();
    }
  }

  visibilityCheck(event) {
      console.log(event);
      event ? this.closePrevent = true : setTimeout(() => {
          this.closePrevent = false; this.emptyField();
      }, 100);
  }

  updateDash(tab, newObject, option = null): void {
    const dashboard = {
      dashBoardSequence: tab.dashBoardSequence,
      dashboardName: tab.dashboardName,
      searchMode: tab.searchMode,
      userId: tab.userId,
      userDashboardId: tab.id,
      visible: tab.visible
    };

    this.updateDashboardAction({dashboardId: tab.id,
      updatedDashboard: _.merge({}, dashboard, newObject)});
    if (option === 'delete') {
      const visibleDash: any = _.filter(this.dashboards, item => item.visible && item.userDashboardId !== tab.id);
      visibleDash.length === 0 ? this.dashboardChange(this.dashboards[0].userDashboardId) :
          this.dashboardChange(visibleDash[0].userDashboardId);
    } else if (option === 'open') {
      this.dashboardChange(tab.id);
    }
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
      _.debounce(() => {
        this.updateDash(this.selectedDashboard, {dashboardName: name})
      }, 500);
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
        colPosition: item.position.col,
        colSpan: item.position.cols,
        minItemCols: item.position.minItemCols,
        minItemRows: item.position.minItemRows,
        rowPosition: item.position.row,
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
    const index = _.findIndex(this.dashboards, (item: any) => item.id === this.selectedDashboard.id);
    this.dashboards = _.merge(this.dashboards, { [index]: {
      widgets: _.map(this.dashboards[index].widgets, item => {
      return item.id === widgetId ? {...item, columns: dashCols} : item})
    }});
    this.detectChanges();
  }

  selectTab(id: any) {
    this.idSelected = id;
    this.selectedDashboard = _.find(this.dashboards, ds => ds.id === id);
    this.dispatch(new fromHD.ChangeSelectedDashboard({selectedDashboard: _.cloneDeep(this.selectedDashboard), tabIndex: id}));
  }

  dashboardChange(id: any) {
    this.idSelected = id;
    this.selectedDashboard = _.find(this.dashboards, ds => ds.id === id);
    this.dispatch(new fromHD.ChangeSelectedDashboard({selectedDashboard: _.cloneDeep(this.selectedDashboard), tabIndex: id}));
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

  detectClickOutside() {
      console.log('clicked', this.closePrevent)
      if (!this.closePrevent) {
          this.rightSliderCollapsed = false;
      }
  }

  getSelection(item) {
    return _.filter(_.get(this.selectedDashboard, 'widgets', []),
            items => items.widgetId === item.widgetId).length > 0;
  }

  private _closePrevent() {
      this.closePrevent = true;
      setTimeout(() => {
          this.closePrevent = false;
      }, 200)
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
