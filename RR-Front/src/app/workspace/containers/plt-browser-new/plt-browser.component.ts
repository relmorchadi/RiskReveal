import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  NgZone,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import * as _ from 'lodash';
import {Actions, ofActionSuccessful, Store} from '@ngxs/store';
import * as fromWorkspaceStore from '../../store';
import {SelectProject, WorkspaceState} from '../../store';
import {switchMap, tap} from 'rxjs/operators';
import {ActivatedRoute, Router} from '@angular/router';
import {Message} from '../../../shared/message';
import * as rightMenuStore from '../../../shared/components/plt/plt-right-menu/store/';
import * as leftMenuStore from '../../../shared/components/plt/plt-left-menu/store';
import {Actions as rightMenuActions} from '../../../shared/components/plt/plt-right-menu/store/actionTypes'
import {PreviousNavigationService} from '../../services/previous-navigation.service';
import {BaseContainer} from '../../../shared/base';
import {SystemTagsService} from '../../../shared/services/system-tags.service';
import {StateSubscriber} from '../../model/state-subscriber';
import {ExcelService} from '../../../shared/services/excel.service';
import produce from "immer";
import {TableHandlerImp} from "../../../shared/implementations/table-handler.imp";
import {TableServiceImp} from "../../../shared/implementations/table-service.imp";
import {AllModules} from '@ag-grid-enterprise/all-modules';
import {PltApi} from "../../services/api/plt.api";
import {CustomBooleanFloatingFilter} from "../../../shared/components/grid/custom-boolean-floating-filter/custom-boolean-floating-filter.component";
import {CustomBooleanFilter} from "../../../shared/components/grid/custom-boolean-filter/custom-boolean-filter.component";
import {StatusCellRenderer} from "../../../shared/components/grid/status-cell-renderer/status-cell-renderer.component";
import {BooleanCellRenderer} from "../../../shared/components/grid/boolean-cell-renderer/boolean-cell-renderer.component";
import {NumberCellRenderer} from "../../../shared/components/grid/number-cell-renderer/number-cell-renderer.component";
import {DateCellRenderer} from "../../../shared/components/grid/date-cell-renderer/date-cell-renderer.component";

@Component({
  selector: 'plt-browser',
  templateUrl: './plt-browser.component.html',
  styleUrls: ['./plt-browser.component.scss'],
  providers: [ TableHandlerImp, TableServiceImp ],
  changeDetection: ChangeDetectionStrategy.Default
})
export class PltBrowserComponent extends BaseContainer implements OnInit, OnDestroy, StateSubscriber {
  //Grid
  private gridApi;
  private gridColumnApi;
  public modules = AllModules;
  gridParams: {
    rowModelType: 'serverSide' | 'infinite' | 'clientSide',
    rowData: any[],
    columnDefs: any[],
    defaultColDef: any,
    autoGroupColumnDef: any,
    getChildCount: Function,
    frameworkComponents: any
  };

  rightMenuInputs: rightMenuStore.Input;
  leftMenuInputs: leftMenuStore.Input;

  collapsedTags: boolean= true;
  @ViewChild('leftMenu') leftMenu: any;

  isColManagerVisible: boolean;

  private dropdown: NzDropdownContextComponent;
  searchAddress: string;
  workspaceId: string;
  uwy: number;
  params: any;
  lastSelectedId;
  managePopUp: boolean;
  size = 'large';
  drawerIndex: any;

  availableColumns: any[];
  visibleColumns: any[];

  selectedProject: any;

  constructor(
    private nzDropdownService: NzDropdownService,
    private zone: NgZone,
    private route$: ActivatedRoute,
    private prn: PreviousNavigationService,
    private systemTagService: SystemTagsService,
    private excel: ExcelService,
    _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,
    private actions$: Actions,
    private handler: TableHandlerImp,
    private pltApi: PltApi

  ) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.gridParams = {
      rowModelType: "serverSide",
      defaultColDef: {
        flex: 1,
        minWidth: 160,
        resizable: true,
        sortable: true,
        columnGroupShow: 'open',
        enableRowGroup: true,
        floatingFilter: true,
        filter: 'agTextColumnFilter',
      },
      autoGroupColumnDef: {
        headerName: 'Group',
        cellRenderer: 'agGroupCellRenderer',
      },
      columnDefs: [{field:"pltId", cellStyle: { textAlign: 'center' }, filter: "agNumberColumnFilter"},{field:"pltName"},{field:"pltType"},{field:"pltStatus", filter: "agSetColumnFilter", filterParams: { values: [ "Valid", 'In progress','Locked', 'Fail', 'Pending', 'Requires Regeneration' ] }, cellRenderer: 'statusCellRenderer'},{field:"groupedPlt",floatingFilterComponent: 'customBooleanFloatingFilter', filter: 'customBooleanFilter', cellRenderer: 'booleanCellRenderer'},{field:"grain"},{field:"arcPublication",floatingFilterComponent: 'customBooleanFloatingFilter', filter: 'customBooleanFilter', cellRenderer: 'booleanCellRenderer'},{field:"perilGroupCode"},{field:"regionPerilCode"},{field:"regionPerilDesc"},{field:"minimumGrainRPCode"},{field:"minimumGrainRPDescription"},{field:"financialPerspective"},{field:"targetRAPCode"},{field:"targetRAPDesc"},{field:"rootRegionPeril"},{field:"vendorSystem"},{field:"modellingDataSource"},{field:"analysisId", cellStyle: { textAlign: 'center' }},{field:"analysisName"},{field:"defaultOccurenceBasis"},{field:"occurenceBasis"},{field:"baseAdjustment",floatingFilterComponent: 'customBooleanFloatingFilter', filter: 'customBooleanFilter', cellRenderer: 'booleanCellRenderer'},{field:"defaultAdjustment",floatingFilterComponent: 'customBooleanFloatingFilter', filter: 'customBooleanFilter', cellRenderer: 'booleanCellRenderer'},{field:"clientAdjustment",floatingFilterComponent: 'customBooleanFloatingFilter', filter: 'customBooleanFilter', cellRenderer: 'booleanCellRenderer'},{field:"projectId", cellStyle: { textAlign: 'center' }},{field:"projectName"},{field:"workspaceContextCode"},{field:"client"},{field:"uwYear"},{field:"clonedPlt",floatingFilterComponent: 'customBooleanFloatingFilter', filter: 'customBooleanFilter', cellRenderer: 'booleanCellRenderer'},{field:"clonedSourcePlt"},{field:"clonedSourceProject"},{field:"clonedSourceWorkspace"},{field:"pltCcy"},{field:"aal", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"cov", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"stdDev", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"oep10", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"oep50", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"oep100", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"oep250", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"oep500", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"oep1000", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"aep10", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"aep50", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"aep100", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"aep250", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"aep500", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"aep1000", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"createdDate", filter: 'agDateColumnFilter', cellRenderer: 'dateCellRenderer'},{field:"importedBy"},{field:"publishedBy"},{field:"archived"},{field:"archivedDate", filter: 'agDateColumnFilter', cellRenderer: 'dateCellRenderer'},{field:"deletedBy"},{field:"deletedDue"},{field:"deletedOn"},{field:"xactPublicationDate", filter: 'agDateColumnFilter', cellRenderer: 'dateCellRenderer'},{field:"xactPublication",floatingFilterComponent: 'customBooleanFloatingFilter', filter: 'customBooleanFilter', cellRenderer: 'booleanCellRenderer'},{field:"xactPriced",floatingFilterComponent: 'customBooleanFloatingFilter', filter: 'customBooleanFilter', cellRenderer: 'booleanCellRenderer'}],
      rowData: null,
      getChildCount: (data) => data ? data.childCount : undefined,
      frameworkComponents: {
        customBooleanFloatingFilter: CustomBooleanFloatingFilter,
        customBooleanFilter: CustomBooleanFilter,
        statusCellRenderer: StatusCellRenderer,
        booleanCellRenderer: BooleanCellRenderer,
        numberCellRenderer: NumberCellRenderer,
        dateCellRenderer: DateCellRenderer
      }
    };
    this.lastSelectedId = null;
    this.drawerIndex = 0;
    this.addTagModalPlaceholder = 'Select a Tag';
    this.managePopUp = false;
    this.rightMenuInputs = {
      basket: [],
      pltHeaderId: '',
      selectedTab: {
        index: 0,
        title: 'pltDetail',
      },
      tabs: [{title: "Summary"}, {title: "EP Metrics"}],
      visible: false,
      mode: "default"
    };
    this.leftMenuInputs= {
      wsId: this.workspaceId,
      uwYear: this.uwy,
      projects: [],
      showDeleted: false,
      filterData: {},
      filters: { systemTag: {}, userTag: []},
      deletedPltsLength: 0,
      userTags: [],
      selectedListOfPlts: [],
      systemTagsCount: {},
      wsHeaderSelected: false,
      pathTab: true,
      isTagsTab: false
    };

    this.setRightMenuSelectedTab('pltDetail');
    this.isColManagerVisible= false;
  }

  observeRouteParams() {
    return this.route$.params.pipe(
        tap(({wsId, year}) => {
          this.workspaceId = wsId;
          this.uwy = year;
          this.updateLeftMenuInputs('wsId', this.workspaceId);
          this.updateLeftMenuInputs('uwYear', this.uwy);
          this.updateMenuKey('wsIdentifier', this.workspaceId+"-"+this.uwy);
        })
    )
  }

  observeRouteParamsWithSelector(operator) {
    return this.observeRouteParams()
      .pipe(
        switchMap(() => operator()),
        this.unsubscribeOnDestroy
      )
  }

  getProjects() {
    return this.select(WorkspaceState.getProjectsPlt(this.workspaceId + '-' + this.uwy));
  }

  getOpenedPlt() {
    return this.select(WorkspaceState.getOpenedPlt(this.workspaceId + '-' + this.uwy));
  }

  getSelectedProject() {
    return this.select(WorkspaceState.getSelectedProject);
  }

  openedPlt: any;

  ngOnInit() {

    this.handler.visibleColumns$.subscribe((cols) => {
      this.visibleColumns = cols;
      this.detectChanges();
    });

    this.handler.availableColumns$.subscribe((cols) => {
      this.availableColumns = cols;
      this.detectChanges();
    });

    this.observeRouteParams().pipe(
      this.unsubscribeOnDestroy
    ).subscribe(() => {
      /*this.dispatch(new fromWorkspaceStore.loadAllPlts({
        params: {
          workspaceId: this.workspaceId, uwy: this.uwy
        },
        wsIdentifier: this.workspaceId + '-' + this.uwy
      }));*/
    });

    this.observeRouteParamsWithSelector(() => this.getProjects()).subscribe((projects: any) => {
      this.updateLeftMenuInputs('projects', projects);
      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getOpenedPlt()).subscribe(openedPlt => {
      this.updateMenuKey('visible', openedPlt && !openedPlt.pltId ? false : this.getRightMenuKey('visible'));
      this.detectChanges();
    });

    this.actions$
      .pipe(
        ofActionSuccessful(fromWorkspaceStore.AddNewTag)
      ).subscribe( (userTag) => {
      this.detectChanges();
    });

    this.actions$
      .pipe(
        ofActionSuccessful(fromWorkspaceStore.DeleteTag)
      ).subscribe( userTag => {
      this.detectChanges();
    })
  }

  selectedPlt: any;
  tagForMenu: any;
  addTagModalPlaceholder: any;
  showDeleted: boolean;
  selectedItemForMenu: string;
  leftIsHidden: any;

  resetPath() {
    this.updateLeftMenuInputs('projects', _.map(this.leftMenuInputs.projects, p => ({...p, selected: false})));
    this.updateTableAndLeftMenuInputs('showDeleted', false);
  }

  setSelectedProjects($event) {
    this.dispatch(new SelectProject({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      projectId: $event
    }))
    //this.updateLeftMenuInputs('projects', $event);
  }

  close(e: NzMenuItemDirective): void {
    this.dropdown.close();
  }


  selectSystemTag({section, tag}) {

    this.updateLeftMenuInputs('systemTagsCount', produce( this.leftMenuInputs.systemTagsCount, draft => {
      _.forEach(this.leftMenuInputs.systemTagsCount, (s, sKey) => {
        _.forEach(s, (t, tKey) => {
          if (tag == tKey && section == sKey) {
            draft[sKey][tKey] = {...t, selected: !t.selected}
          }
        })
      });
    }));
  }

  emitFilters(filters: any) {
    this.dispatch(new fromWorkspaceStore.setUserTagsFilters({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      filters: filters
    }))
  }

  setUserTags($event) {
    this.updateLeftMenuInputs('userTags', $event);
  }

  setWsHeaderSelect($event: any) {
    this.updateLeftMenuInputs('wsHeaderSelected', $event);
  }

  resetFilters() {
    this.updateTableAndLeftMenuInputs('filterData', {});
    this.emitFilters({
      userTag: [],
      systemTag: []
    });
    this.updateLeftMenuInputs('userTags', _.map(this.leftMenuInputs.userTags, t => ({...t, selected: false})));
    this.updateTableAndLeftMenuInputs("filters", {
      userTag: [],
      systemTag: {}
    });
  }

  createTag($event: any) {
    this.dispatch(new fromWorkspaceStore.createOrAssignTags({
      ...$event,
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      type: 'createTag'
    }))
  }

  rightMenuActionDispatcher(action: Message) {
    switch (action.type) {
      case rightMenuStore.closeDrawer:
        this.closeDrawer(false);
        break;

      case rightMenuStore.setSelectedTabByIndex:
        this.updateMenuKey('selectedTab', action.payload);
        break;

      default:
        console.log('default right menu action');
    }
  }

  closeDrawer(outside){
    this.updateMenuKey('visible', false);
    this.updateMenuKey('pltHeaderId', null);
  }

  setRightMenuSelectedTab(tab) {
    this.rightMenuInputs = rightMenuActions.setSelectedTab.handler(this.rightMenuInputs, tab)
  }

  getRightMenuKey(key) {
    return _.get(this.rightMenuInputs, key);
  }

  updateMenuKey(key: string, value: any) {
    this.rightMenuInputs = rightMenuActions.updateKey.handler(this.rightMenuInputs, key, value);
  }


  ngOnDestroy(): void {
    this.destroy();
  }

  patchState({data: {leftNavbarCollapsed, wsId, uwYear, projects}}): void {
    this.leftIsHidden = leftNavbarCollapsed;
    this.params = {
      workspaceContextCode: wsId,
      workspaceUwYear: uwYear
    };
    this.selectedProject = _.find(projects, project => project.selected);
  }

  protected detectChanges() {
    super.detectChanges();
  }

  toggleColumnsManager() {
    this.isColManagerVisible= !this.isColManagerVisible;
  }

  resetColumns() {
    this.managePopUp = false;
  }

  leftMenuActionDispatcher(action: Message) {

    switch (action.type) {

      case leftMenuStore.emitFilters:
        this.emitFilters(action.payload);
        break;
      case leftMenuStore.resetPath:
        this.resetPath();
        break;
      case leftMenuStore.headerSelection:
        this.setWsHeaderSelect(action.payload);
        break;
      case leftMenuStore.onSelectProjects:
        this.setSelectedProjects(action.payload);
        break;
      case leftMenuStore.onSelectSysTagCount:
        this.selectSystemTag(action.payload);
        break;
      case leftMenuStore.onSetSelectedUserTags:
        this.setUserTags(action.payload);
        break;

      case 'Detect Parent Changes':
        this.detectChanges();
    }
  }

  updateLeftMenuInputs(key, value) {
    this.leftMenuInputs= {...this.leftMenuInputs, [key]: value};
  }

  updateTableAndLeftMenuInputs(key, value) {
    this.updateLeftMenuInputs(key, value);
  }

  collapseLeftMenu() {
    this.collapsedTags= !this.collapsedTags;
    this.detectChanges();
  }


  resizing() {
    this.detectChanges();
  }

  handleTableActions(action: Message) {
    switch (action.type) {
      case 'View Detail':
        this.openPltInDrawer(action.payload);
    }
  }

  closePltInDrawer() {
    this.dispatch(new fromWorkspaceStore.ClosePLTinDrawer({
      wsIdentifier: this.workspaceId + '-' + this.uwy
    }));
  }

  openPltInDrawer(plt) {
    this.openedPlt = plt;
    this.updateMenuKey('pltHeaderId', plt.pltId);
    this.updateMenuKey('visible', true);
  }

  //Grid
  onGridReady(params) {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;

    let datasource = {
      getRows: (params) => {
        const valueCols = _.filter(this.gridColumnApi.getColumnState(), col => !_.includes(col.colId, "ag-grid") && !_.isNumber(col.rowGroupIndex))
        console.log('[Datasource] - rows requested by grid: ', params.request);
        _.forEach(params.request.rowGroupCols, col => {
          this.gridColumnApi.setColumnVisible(col.field, false);
        });
        this.pltApi.getGroupedPLTs({
            ...params.request,
          filterModel: this.transformFilterModel(params.request.filterModel)
        }).subscribe( (d: any) => {
          let response = {
            rows: d.rows,
            lastRow: d.lastRow,
            success: true
          };
          if (response.success) {
            //this.gridApi.purgeServerSideCache();
            params.successCallback(response.rows, response.lastRow);
          } else {
            params.failCallback();
          }
        });
      },
    };
    params.api.setServerSideDatasource(datasource);
  }

  transformFilterModel(filterModel: any) {
    let filters= [];

    _.map(filterModel, (f, key) => {
      const {
        filterType,
        type,
        filter,
        condition1,
        condition2,
        dateFrom,
        dateTo,
        operator,
        values
      } = f;
      let conditions;
      let overriddenOperator;

      if(filterType == 'set') {
        overriddenOperator = 'OR';
        conditions = _.map(values, val => this.getFieldFilter("EQ", val, key, "TEXT"));
      } else if(filterType == 'date') {
        overriddenOperator = operator;
        conditions = [this.getFieldFilter(this.comparisonOperatorMapper[type], new Date(dateFrom).getTime(), key, "DATE")];
      } else {
        overriddenOperator = operator;
        conditions = operator ?
            _.map([condition1, condition2], cond => this.getFieldFilter(this.comparisonOperatorMapper[cond.type], cond.filter, key, _.upperCase(filterType)))
            :
            [this.getFieldFilter(this.comparisonOperatorMapper[type], filterType == 'date' ? new Date(dateFrom).getTime() : filter, key, _.upperCase(filterType))]
      }
      filters.push(
          this.getColumnFilter(
              key,
              _.upperCase(filterType == 'set' ? 'text' : filterType),
              overriddenOperator,
              conditions
          )
      )
    })

    return [
      this.getColumnFilter(
          'workspaceContextCode',
          'TEXT',
          null,
          [
              this.getFieldFilter("EQ", this.params.workspaceContextCode, 'workspaceContextCode', 'TEXT')
          ]
      ),
      this.getColumnFilter(
          'uwYear',
          'TEXT',
          null,
          [
            this.getFieldFilter("EQ", this.params.workspaceUwYear, 'uwYear', 'TEXT')
          ]
      ),
        ...filters
    ]
  }

  getColumnFilter(key, filterType, operator, conditions) {
    return {
      key: key,
      filterType: filterType,
      operator: operator,
      conditions: conditions
    }
  }

  getFieldFilter(comparisonOperator, value, key, filterType) {
    return {
      key,
      value,
      comparisonOperator,
      filterType
    }
  }

  comparisonOperatorMapper = {
    "equals": "EQ",
    "notEqual": "NOT_EQUAL",
    contains: "CONTAINS",
    notContains: "NOT_CONTAINS",
    startsWith: "STARTS_WITH",
    endsWith: "ENDS_WITH",
    lessThan: "LT",
    lessThanOrEqual: "LT_OR_EQ",
    greaterThan: "GT",
    greaterThanOrEqual: "GT_OR_EQ",
    equalsBoolean: "EQ_BOOL"
  };



  onColumnRowGroupChanged($event) {
    //this.gridColumnApi.setColumnVisible($event.column.coldId, !$event.rowGroupActive)
  }

}
