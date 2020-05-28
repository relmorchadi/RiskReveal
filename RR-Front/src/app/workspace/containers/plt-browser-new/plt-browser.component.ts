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
import {mergeMap, share, switchMap, tap} from 'rxjs/operators';
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
import {GroupedCellRenderer} from "../../../shared/components/grid/grouped-cell-renderer/grouped-cell-renderer.component";
import {GridApi, ColumnApi, RowNode} from 'ag-grid-community';
import {PltTableService} from "../../services/helpers/plt-table.service";
import {iif} from "rxjs";

@Component({
  selector: 'plt-browser',
  templateUrl: './plt-browser.component.html',
  styleUrls: ['./plt-browser.component.scss'],
  providers: [ TableHandlerImp, TableServiceImp ]
})
export class PltBrowserComponent extends BaseContainer implements OnInit, OnDestroy, StateSubscriber {
  //Grid
  private tableContext: string = 'PLT Manager';
  private tableName: string = 'main';

  private gridApi: GridApi;
  private gridColumnApi: ColumnApi;
  private gridInitialized: boolean;
  public modules = AllModules;
  gridParams: {
    rowModelType: 'serverSide' | 'infinite' | 'clientSide',
    rowData: any[],
    immutableColumns: boolean,
    columnDefs: any[],
    defaultColDef: any,
    autoGroupColumnDef: any,
    getChildCount: Function,
    frameworkComponents: any,
    rowSelection: 'multiple' | 'single',
    onSelectionChanged: Function,
    onColumnRowGroupChanged: Function,
    getRowClass: Function,
    sideBare: any,
    getContextMenuItems: Function
  };

  isModalVisible: boolean;

  visibleList = [];

  availableList = [];

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
    private pltApi: PltApi,
    private pltTableService: PltTableService

  ) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.gridParams = {
      rowModelType: "clientSide",
      immutableColumns: false,
      defaultColDef: {
        resizable: true,
        sortable: true,
        columnGroupShow: 'open',
        enableRowGroup: true,
        floatingFilter: true,
        filter: 'agTextColumnFilter',
        headerCheckboxSelection: function(params) {
          let displayedColumns = params.columnApi.getAllDisplayedColumns();
          return displayedColumns[0] === params.column;
        },
        checkboxSelection: function(params) {
          let displayedColumns = params.columnApi.getAllDisplayedColumns();
          return displayedColumns[0] === params.column;
        },
        headerCheckboxSelectionFilteredOnly: true
      },
      autoGroupColumnDef: {
        headerName: 'Group',
        cellRenderer: 'agGroupCellRenderer',
        cellRendererParams: {
          checkbox: false,
        }
      },
      columnDefs: [],
      rowData: null,
      getChildCount: (data) => data ? data.childCount : undefined,
      frameworkComponents: {
        customBooleanFloatingFilter: CustomBooleanFloatingFilter,
        customBooleanFilter: CustomBooleanFilter,
        statusCellRenderer: StatusCellRenderer,
        booleanCellRenderer: BooleanCellRenderer,
        numberCellRenderer: NumberCellRenderer,
        dateCellRenderer: DateCellRenderer,
        groupCellRenderer: GroupedCellRenderer
      },
      rowSelection: "multiple",
      onSelectionChanged: (e) => {
        this.gridApi.redrawRows();
      },
      onColumnRowGroupChanged: ($event) => {
        this.gridColumnApi.setColumnsVisible(_.map(this.gridColumnApi.getRowGroupColumns(), col => col.getColId()), false);
      },
      getRowClass: ({node}: {node: RowNode}) => {
        if(!node.group) {
          if(node.isSelected()) return 'grid-selected-row';
        }
      },
      sideBare: {
        toolPanels: [
          {
            id: 'columns',
            labelDefault: 'Columns',
            labelKey: 'columns',
            iconKey: 'columns',
            toolPanel: 'agColumnsToolPanel',
            toolPanelParams: {
              suppressRowGroups: false,
              suppressValues: true,
              suppressPivots: true,
              suppressPivotMode: true,
              suppressSideButtons: false,
              suppressColumnFilter: false,
              suppressColumnSelectAll: true,
              suppressColumnExpandAll: false,
            },
          },
        ],
        defaultToolPanel: 'columns',
        hiddenByDefault: true
      },
      getContextMenuItems: (params) => {
        return !params.node.group ? [{
          name: 'View Detail',
          action: () => {
            this.openedPlt = params.node.data;
            this.updateMenuKey('pltHeaderId', params.node.data.pltId);
            this.updateMenuKey('visible', true);
            console.log(this.rightMenuInputs);
            this.detectChanges();
          },
        }] : [];
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
      selectedProject: null,
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
      if(this.gridInitialized) {
        const selectedProject = _.find(projects, project => project.selected);
        const filterModel = this.gridApi.getFilterModel();
        this.gridApi.setFilterModel(selectedProject ? { ...filterModel, projectId: { type: 'equals', filter: selectedProject.projectId } } : _.omit(filterModel, 'projectId'));
        this.gridApi.onFilterChanged();
        this.updateLeftMenuInputs('wsHeaderSelected', !!selectedProject);
        this.updateLeftMenuInputs('selectedProject', selectedProject);
      }
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

      case 'filterByProject':
        this.filterByProject(action.payload);
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

    const api$ = this.pltApi.getGroupedPLTs({
      ...params.request,
      filterModel: this.transformFilterModel([]),
      rowGroupCols: [],
      groupKeys: [],
      valueCols: [],
      pivotCols: [],
      pivotMode: false,
      sortModel: [],
      pureIds: [],
    }).pipe(share());

    api$.subscribe( (d: any) => {
      this.gridApi.setRowData(d.rows);
    });

    api$.pipe(
        switchMap(() => this.pltApi.checkConfig({tableName: this.tableName, tableContext: this.tableContext})),
        switchMap((exist: any) => this.pltApi.saveTableConfig({tableName: this.tableName, tableContext: this.tableContext, config: JSON.stringify(PltTableService.config)})),
        tap(e => console.log(e))
    ).subscribe( (payload: any) => {
      let config = payload ? JSON.parse(_.get(payload, 'columns')) : PltTableService.config;
      this.gridApi.setColumnDefs(config.columns);
      this.gridApi.setSortModel(config.sort);
      this.gridInitialized= false;
      const selectedProject = this.leftMenuInputs.projects && this.leftMenuInputs.projects.length ? _.find(this.leftMenuInputs.projects, project => project.selected) : null;
      const filterModel = selectedProject ? { ...config.filter, projectId: { filterType: 'number', type: 'equals', filter: selectedProject.projectId } } : config.filter;
      this.updateLeftMenuInputs('wsHeaderSelected', selectedProject);
      this.updateLeftMenuInputs('selectedProject', selectedProject);
      this.gridApi.setFilterModel(filterModel);
      this.gridInitialized= false;
    });

    // let datasource = {
    //   getRows: (params) => {
    //     const valueCols = _.filter(this.gridColumnApi.getColumnState(), col => !_.includes(col.colId, "ag-grid") && !_.isNumber(col.rowGroupIndex))
    //     console.log('[Datasource] - rows requested by grid: ', params.request);
    //     _.forEach(params.request.rowGroupCols, col => {
    //       this.gridColumnApi.setColumnVisible(col.field, false);
    //     });
    //     this.pltApi.getGroupedPLTs({
    //         ...params.request,
    //       filterModel: this.transformFilterModel(params.request.filterModel)
    //     }).subscribe( (d: any) => {
    //       let response = {
    //         rows: d.rows,
    //         lastRow: d.lastRow,
    //         success: true
    //       };
    //       if (response.success) {
    //         params.successCallback(response.rows, response.lastRow);
    //         this.gridColumnApi.autoSizeColumns(_.map(this.gridColumnApi.getAllColumns(), (col: any) => col.colId), false);
    //       } else {
    //         params.failCallback();
    //       }
    //     });
    //   },
    // };
    // params.api.setServerSideDatasource(datasource);
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

  filterByProject(project) {
    if(project) {
      this.dispatch(new SelectProject({
        wsIdentifier: this.workspaceId + '-' + this.uwy,
        projectId: project.projectId
      }));
      this.updateLeftMenuInputs('wsHeaderSelected', false);
    } else {
      this.updateLeftMenuInputs('wsHeaderSelected', true);
      this.filterTable(project);
    }
  }

  filterTable(project) {
    const filterModel = this.gridApi.getFilterModel();
    this.gridApi.setFilterModel(project ? { ...filterModel, projectId: { type: 'equals', filter: project.projectId } } : _.omit(filterModel, 'projectId'));
    this.gridApi.onFilterChanged();
  }

  onResetFilter() {
    this.gridApi.setFilterModel({});
  }

  onResetSort() {
    this.gridApi.setSortModel({});
  }

  onExport() {
    let data = [];
    let columnsHeader = _.map(this.gridColumnApi.getAllColumns(), col => col.getColDef().field);
    let filters = _.map(this.gridApi.getFilterModel(), (v, k) => {
      const {
        condition1,
        condition2,
        operator,
        filter,
        filterTo,
        type
      } = v;
      let s = '';

      if(filter) s = _.upperCase(type) + ' ' + ( filterTo ? filter + '-' + filterTo : filter );
      else {
        s = _.upperCase(condition1.type) + ' ' + ( condition1.filterTo ? condition1.filter + '-' + condition1.filterTo : condition1.filter );
        s+= ' ' + _.upperCase(operator);
        s+= ' ' + _.upperCase(condition2.type) + ' ' + ( condition2.filterTo ? condition2.filter + '-' + condition2.filterTo : condition2.filter );
      }
      return ({
        Column: k,
        Filter: s
      })
    });

    this.gridApi.forEachNodeAfterFilterAndSort(function(rowNode, index) {
      if(rowNode.data) data.push(rowNode.data);
    });

    this.excel.exportAsExcelFile(
        [
          { sheetData: data, sheetName: "Data", headerOptions: columnsHeader},
          { sheetData: filters, sheetName: "Filters" ,headerOptions: ["Column", "Filter"]}
        ],
        `PLTList-${this.params.workspaceContextCode}-${this.params.workspaceUwYear}`
    );
  }

  showDialog() {
    console.log(this.gridColumnApi.getColumnState());
    const groupedCols = _.groupBy(this.gridColumnApi.getColumnState(), 'hide');

    this.availableList = groupedCols['true'] || [];
    this.visibleList = groupedCols['false'] || [];

    this.isModalVisible = true;
  }

  resetGrid() {
    this.gridApi.setColumnDefs(PltTableService.config.columns);
    this.saveNewColumnsState(null);
  }

  saveNewColumnsState(e) {
    if((e && e.type == "columnResized") ? e.finished : this.gridInitialized) {
      let order = {};

      _.forEach(this.gridColumnApi.getColumnState(), (col, i) => {
        order[col.colId] = i;
      });

      let cols = _.map(this.gridColumnApi.getAllColumns(), col => {
        const {
          cellStyle,
          field,
          filter,
          cellRenderer
        } = col.getColDef();
        return ({
          cellStyle,
          cellRenderer,
          field,
          filter,
          width: col.getActualWidth(),
          maxWidth: col.getMaxWidth(),
          minWidth: col.getMinWidth(),
          pinned: col.getPinned(),
          hide: !col.isVisible()
        });
      });
      cols.sort( (a,b) => order[a.field] - order[b.field]);

      this.pltApi.saveTableConfig({
        tableName: this.tableName, tableContext: this.tableContext,
        config: JSON.stringify({
          columns: cols,
          sort: this.gridApi.getSortModel(),
          filter: this.gridApi.getFilterModel()
        })
      }).subscribe( (s: any) => {});
    }
    this.gridInitialized = true;
  }

  handleManageColumnsActions(action) {
    switch (action.type) {

      case "Manage Columns":
        this.onManageColumns(action.payload);
        break;

      case "Close":
        this.isModalVisible= false;
        break;

      default:
        console.log(action);
    }
  }

  onManageColumns({availableList , visibleList}) {
    this.gridColumnApi.setColumnState([...visibleList, ...availableList])
    this.isModalVisible= false;
    this.availableList = [];
    this.visibleList = [];
  }

}
