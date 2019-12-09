import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  ElementRef,
  NgZone,
  OnDestroy,
  OnInit,
  ViewChild
} from '@angular/core';
import * as _ from 'lodash'
import {Actions, ofActionDispatched, Select, Store} from "@ngxs/store";
import {
  applyAdjustment,
  collapseTags,
  deleteAdjsApplication,
  deleteAdjustment,
  dropAdjustment,
  dropThreadAdjustment,
  extendPltSection,
  replaceAdjustement,
  saveAdjModification,
  saveAdjustment, SetCurrentTab
} from "../../store/actions";
import {switchMap, tap} from 'rxjs/operators';
import {WorkspaceState} from "../../store/states";
import {Observable} from 'rxjs';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from "ng-zorro-antd";
import * as fromWorkspaceStore from "../../store";
import {ActivatedRoute, Router} from "@angular/router";
import {StateSubscriber} from "../../model/state-subscriber";
import {BaseContainer} from "../../../shared/base";
import {CURRENCIES, DEPENDENCIES, EPM_COLUMNS, EPMS, PLT_COLUMNS, TEMPLATES, UNITS} from "./data";
import {SystemTagsService} from "../../../shared/services/system-tags.service";
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import {Message} from "../../../shared/message";
import * as leftMenuStore from "../../../shared/components/plt/plt-left-menu/store";
import * as tagsStore from "../../../shared/components/plt/plt-tag-manager/store";


@Component({
  selector: 'app-workspace-calibration',
  templateUrl: './workspace-calibration.component.html',
  styleUrls: ['./workspace-calibration.component.scss'],
  // changeDetection: ChangeDetectionStrategy.OnPush
})
export class WorkspaceCalibrationComponent extends BaseContainer implements OnInit, OnDestroy, StateSubscriber {

  leftMenuInputs: leftMenuStore.Input;
  tagsInputs: tagsStore.Input;
  private dragBool: boolean = false;

  someItemsAreSelected = false;
  groupedByPure = false;
  allRowsExpanded = true;
  selectAll = false;
  listOfPlts = [];
  listOfPltsData = [];
  listOfPltsThread = [];
  selectedListOfPlts = [];
  template = {id: 0, type: '', name: 'none', description: '', adjs: []};
  templateList = [this.template, ...TEMPLATES];
  drawerIndex = 0;
  params = {};
  loading = true;
  filters = {
    systemTag: [],
    userTag: []
  };
  filterData = {};
  sortData = {};
  addTagModalIndex = 0;
  systemTagsCount: any;
  fromPlts = false;
  showDeleted = false;
  tagForMenu = {
    tagId: null,
    tagName: '',
    tagColor: '#0700e4'
  };
  searchAddress: string;
  listOfPltsCache: any[];
  saveTemplate = false;
  loadTemplate = false;
  templateName: string;
  templateType: string = 'Local';
  templateDesc: string;
  listOfPltsThreadCache: any[];
  listOfDeletedPlts: any[] = [];
  frozenColumns: any[] = [];
  frozenColumnsCache: any[] = [];
  extraFrozenColumns: any[] = [];
  extraFrozenColumnsCache: any[] = [];
  frozenWidth: any = '513';
  headerWidth: any = '453px';
  genericWidth: any = ['409px', '33px', '157px'];
  selectedAdjustment: any;
  shownDropDown: any;
  rowKeys: any = [];
  lastModifiedAdj;
  dataColumns = [];
  dataColumnsCache = [];
  extraDataColumnsCache = [];
  extraDataColumns = [];
  extended: boolean = false;
  tableType = 'adjustments';
  EPMetricsTable = false;
  EPMS = EPMS;
  selectedEPM = "AEP";
  EPMDisplay = 'percentage';
  manageColumn = false;
  collapsedTags: boolean;
  filterInput: string = "";
  addRemoveModal: boolean = false;
  isVisible = false;
  singleValue: any;
  global: any;
  categorySelectedFromAdjustement: any;
  modalTitle: any;
  addAdjustement: any;
  modifyModal: any;
  idPlt: any;
  draggedAdjs: any;
  allAdjsArray: any[] = [];
  AdjustementType: any;
  categorySelected: any;
  adjsArray: any[] = [];
  leftNavbarIsCollapsed: boolean;
  adjustmentApplication = [];
  linear: boolean = false;
  dropdownVisible = false;
  workspaceId: string;
  uwy: number;
  projects: any[];
  columnPosition: number;
  tagModalIndex: any = 0;
  mode = "calibration";
  pltColumns = PLT_COLUMNS;
  EPMColumns = EPM_COLUMNS;
  visible = false;
  size = 'large';
  sumnaryPltDetailsPltId: any;
  pltdetailsSystemTags: any = [];
  pltdetailsUserTags: any = [];
  systemTags: any;
  userTags: any;
  units = UNITS;
  dependencies = DEPENDENCIES;
  deletedPlts: any;
  selectedPlt: any;
  inputValue: any;
  selectedItemForMenu: string;
  oldSelectedTags: any;
  private dropdown: NzDropdownContextComponent;
  listOfDeletedPltsData: any;
  listOfDeletedPltsCache: any;
  selectedListOfDeletedPlts: any;
  contextMenuItems = [
    {
      label: 'Regenerate', command: (event) => {
        this.openPltInDrawer(this.selectedPlt.pltId)
      }
    },
    {
      label: 'Add New Adjustment', command: (event) => {
        this.clickButtonPlus(false, event)
      }
    },
    {
      label: 'Create', command: (event) => {
        this.openPltInDrawer(this.selectedPlt.pltId)
      }
    },
    {
      label: 'Clone',
      command: (event) => {
        this.router$.navigateByUrl(`workspace/${this.workspaceId}/${this.uwy}/CloneData`, {state: {from: 'pltManager'}})
      }
    },
    {separator: true},
    {
      label: 'Publish to Pricing', command: (event) => {
        this.openPltInDrawer(this.selectedPlt.pltId)
      }
    },
    {
      label: 'Publish to Accumulation', command: (event) => {
        this.openPltInDrawer(this.selectedPlt.pltId)
      }
    },
    {
      label: 'Inuring', command: (event) => {
        this.openPltInDrawer(this.selectedPlt.pltId)
      }
    },
    {
      label: 'Add ro Comparer', command: (event) => {
        this.openPltInDrawer(this.selectedPlt.pltId)
      }
    },
    {
      label: 'Export', command: (event) => {
        this.openPltInDrawer(this.selectedPlt.pltId)
      }
    },
    {separator: true},
    {
      label: 'View Detail', command: (event) => {
        this.openPltInDrawer(this.selectedPlt.pltId)
      }
    },
    {
      label: 'Manage Tags', command: (event) => {
        this.tagModalVisible = true;
        this.tagForMenu = {
          ...this.tagForMenu,
          tagName: ''
        };
        this.fromPlts = true;
        this.editingTag = false;
        let d = _.map(this.selectedListOfPlts, k => _.find(this.listOfPltsData, e => e.pltId == k).userTags);
        this.modalSelect = _.intersectionBy(...d, 'tagId');
        this.oldSelectedTags = _.uniqBy(_.flatten(d), 'tagId');
      }
    },
    {
      label: 'Remove', command: (event) => {
        this.dispatch(new fromWorkspaceStore.deletePlt({
          wsIdentifier: this.workspaceId + '-' + this.uwy,
          pltIds: this.selectedListOfPlts.length > 0 ? this.selectedListOfPlts : [this.selectedItemForMenu]
        }));
      }
    },
    {
      label: 'Delete', command: (event) => {
        this.dispatch(new fromWorkspaceStore.deletePlt({
          wsIdentifier: this.workspaceId + '-' + this.uwy,
          pltIds: this.selectedListOfPlts.length > 0 ? this.selectedListOfPlts : [this.selectedItemForMenu]
        }));
      }
    }
  ];
  contextMenuItemsCache = this.contextMenuItems;
  tagModalVisible: boolean;
  editingTag: boolean;
  tagContextMenu = [
    {
      label: 'Delete Tag',
      icon: 'pi pi-trash',
      command: (event) => this.dispatch(new fromWorkspaceStore.deleteUserTag({
        wsIdentifier: this.workspaceId + '-' + this.uwy,
        userTagId: this.tagForMenu.tagId
      }))
    },
    {
      label: 'Edit Tag', icon: 'pi pi-pencil', command: (event) => {
        this.editingTag = true;
        this.fromPlts = false;
        this.tagModalVisible = true;
      }
    }
  ];
  wsHeaderSelected: boolean;
  modalSelect: any;
  randomMetaData = {};
  financialUnits = {
    data: UNITS,
    selected: {id: '2', label: 'Million'}
  };
  currencies = {
    data: CURRENCIES,
    selected: {id: '1', name: 'Euro', label: 'EUR'}
  };
  manageReturnPeriods = false;
  manageAdjColumn = false;
  rowGroupMetadata = {
    'SPLTH-000735433': [],
    'SPLTH-000735434': [],
    'SPLTH-000735435': []
  };


  @Select(WorkspaceState.getUserTags) userTags$;
  @Select(WorkspaceState) state$: Observable<any>;
  @Select(WorkspaceState.getAdjustmentApplication) adjutmentApplication$;
  @Select(WorkspaceState.getLeftNavbarIsCollapsed()) leftNavbarIsCollapsed$;
  @Select(WorkspaceState.getCurrentWorkspaces) ws$;
  @Select(WorkspaceState.getSelectedProject) selectedProject$;
  @Select(WorkspaceState.getExtentState) extentState$;
  @ViewChild('dt')
  @ViewChild('iconNote') iconNote: ElementRef;
  activeCheckboxSort: boolean = false;
  descriptionDropDown: any = false;
  deltaSwitch: boolean = false;
  searchSelectedTemplate: any = this.template;
  localTemplates: any = [];
  globalTemplates: any = [];
  wsStatus: any;
  tabStatus: any;
  wsIdentifier;

  // @ViewChild('templateNameInput') templateNameInput: ElementRef;
  dropAll = (param) => null;


  constructor(
    private nzDropdownService: NzDropdownService,
    private store$: Store,
    private zone: NgZone,
    private actions$: Actions,
    private cdRef: ChangeDetectorRef,
    private router$: Router,
    private systemTagService: SystemTagsService,
    private route$: ActivatedRoute) {
    super(router$, cdRef, store$);

    this.leftMenuInputs= {
      wsId: this.workspaceId,
      uwYear: this.uwy,
      projects: this.projects,
      showDeleted: this.showDeleted,
      filterData: this.filterData,
      filters: this.filters,
      deletedPltsLength: 0,
      userTags: this.userTags,
      selectedListOfPlts: this.selectedListOfPlts,
      systemTagsCount: this.systemTagsCount,
      wsHeaderSelected: this.wsHeaderSelected,
      pathTab: false
    };
    this.tagsInputs= {
      _tagModalVisible: false,
      toRemove: [],
      toAssign: [],
      assignedTags: [],
      assignedTagsCache: [],
      operation: null,
      selectedTags: [],
      allTags: [],
      suggested: [],
      usedInWs: []
    }
  }

  observeRouteParams() {
    return this.route$.params.pipe(tap(({wsId, year}) => {
      this.workspaceId = wsId;
      this.uwy = year;
    }))
  }

  observeRouteParamsWithSelector(operator) {
    return this.observeRouteParams()
      .pipe(
        switchMap(() => operator()),
        this.unsubscribeOnDestroy
      )
  }

  getPlts() {
    return this.select(WorkspaceState.getPltsForCalibration(this.workspaceId + '-' + this.uwy));
  }

  ngOnInit() {
    this.initDataColumns();
    this.initFrozenWidth();
    this.initTemplateList();
    this.observeRouteParams().pipe(
      this.unsubscribeOnDestroy
    ).subscribe(() => {
      this.dispatch(new fromWorkspaceStore.loadAllPltsFromCalibration({
        params: {
          workspaceId: this.workspaceId, uwy: this.uwy
        },
        wsIdentifier: this.workspaceId + '-' + this.uwy
      }));
      this.dispatch(new fromWorkspaceStore.LoadAllDefaultAdjustmentApplication());
      /*this.dispatch(new fromWorkspaceStore.loadAllAdjustmentApplication({
        params: {
          workspaceId: this.workspaceId, uwy: this.uwy
        },
        wsIdentifier: this.workspaceId + '-' + this.uwy
      }));*/
    });

    this.ws$.pipe().subscribe(value => {
      this.wsStatus = _.get(value, 'workspaceType', null);
      this.detectChanges();
    });

    this.extentState$.pipe().subscribe(value => {
      this.extended = value;
      this.detectChanges();
    });

    this.selectedProject$.pipe().subscribe(value => {
      this.tabStatus = _.get(value, 'projectType', null);
      this.tabStatus === 'FAC' ? this.extend('init') : null;
      this.detectChanges();
    });

    this.actions$
      .pipe(
        ofActionDispatched(SetCurrentTab)
      ).subscribe(({payload}) => {
        this.extendRebase();
        if (payload.wsIdentifier != this.wsIdentifier) this.destroy();
        this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getPlts()).subscribe((data) => {
      this.systemTagsCount = this.systemTagService.countSystemTags(data);
      this.listOfPltsCache = _.map(data, (v, k) => ({...v, pltId: k}));
      this.listOfPltsData = [...this.listOfPltsCache];
      this.listOfPltsData = _.filter(this.listOfPltsData, pure => _.some(pure.threads, thread => thread.toCalibrate));
      this.initThreadsData();
      // console.log('pltThread', Array.prototype.concat.apply([],this.listOfPltsData.map(row => row.threads)))
      this.detectChanges();
      // console.log(data);
      _.forEach(this.listOfPltsData, row => {
        this.rowKeys[row.pltId] = true;
      });
      // this.rowKeys = this.listOfPltsData.map(e => e.pltId)
      // console.log('rowKey ===> ', this.rowKeys);
    });

    this.observeRouteParamsWithSelector(() => this.getPlts()).subscribe(data => {
      this.selectAll = this.selectedListOfPlts.length > 0 || (this.selectedListOfPlts.length == this.listOfPltsThread.length) && this.listOfPltsThread.length > 0;

      this.someItemsAreSelected = this.selectedListOfPlts.length < this.listOfPltsThread.length && this.selectedListOfPlts.length > 0;
      this.detectChanges();
    });
    this.listOfPltsData.sort(this.dynamicSort("pureId"));
    this.updateRowGroupMetaData();
    this.adjustColWidth();
    // this.initAdjApplication();
  }

  initAdjApplication() {
    this.dispatch(new applyAdjustment({
      adjustementType: this.singleValue,
      adjustement: false,
      columnPosition: this.columnPosition,
      pltId: this.listOfPltsThread.filter(row => row.status != 'locked'),
    }));
  }

  initTemplateList() {
   // console.log('template List ======> ', this.templateList);
    _.forEach(this.templateList, (row: any) => {
      if (row.type == 'Global' && !this.globalTemplates.includes(row)) {
        this.globalTemplates.push(row)
      } else if (row.type == 'Local' && !this.localTemplates.includes(row)) {
        this.localTemplates.push(row)
      }
    });
   // console.log('local List ======> ', this.localTemplates)
   // console.log('global List ======> ', this.globalTemplates)
  }

  initThreadsData() {
    if (this.listOfPltsData) {
      this.listOfPltsThread = Array.prototype.concat.apply([], this.listOfPltsData.map(row => row.threads));
      this.listOfPltsThreadCache = _.filter(this.listOfPltsThread, row => row.toCalibrate);
      this.listOfPltsThread = [...this.listOfPltsThreadCache];
      this.selectedListOfPlts = _.filter(this.listOfPltsThread, (v, k) => v.selected).map(e => e.pltId);
    }
  }

  initRandomMetaData() {
    const cols: any[] = ['AAL', 'EPM2', 'EPM5', 'EPM10', 'EPM25', 'EPM50', 'EPM100', 'EPM250', 'EPM500', 'EPM1000', 'EPM5000', 'EPM10000'];
    _.forEach(this.listOfPltsThread, value => {
      this.randomMetaData[value.pltId] = {}
    });
    _.forEach(this.listOfPltsThread, value => {
      _.forEach(cols, col => {
        if (col == 'AAL') {
          this.randomMetaData[value.pltId][col] = [
            Math.floor(Math.random() * (200000000 - 1000000 + 1)) + 1000000,
            Math.floor((Math.random() - 0.5) * (1000000 - 1000 + 1)) + 1000,
            Math.floor(Math.random() * 199) - 99
          ];
        } else {
          this.randomMetaData[value.pltId][col] = [
            Math.floor(Math.random() * (2000000 - 10000 + 1)) + 10000,
            Math.floor((Math.random() - 0.5) * (1000000 - 1000 + 1)) + 1000,
            Math.floor(Math.random() * 199) - 99
          ];
        }
      })
    })
  }

  patchState(state: any): void {
    const {wsIdentifier} = state;
    this.wsIdentifier = wsIdentifier;
    const path = state.data.calibration;
    this.leftNavbarIsCollapsed = path.leftNavbarIsCollapsed;
    this.collapsedTags = path.collapseTags;
   // console.log(path);
    this.adjustmentApplication = _.merge({}, path.adjustmentApplication);
    this.allAdjsArray = _.merge([], path.allAdjsArray).sort(this.dynamicSort("name"));
    this.AdjustementType = _.merge([], path.adjustementType);
    this.adjsArray = _.merge([], path.adjustments);
    this.userTags = _.merge({}, path.userTags);
    this.detectChanges();
  }

  dynamicSort(property) {
    let sortOrder = 1;
    if (property[0] === "-") {
      sortOrder = -1;
      property = property.substr(1);
    }
    return function (a, b) {
      /* next line works with strings and numbers,
       * and you may want to customize it to your needs
       */
      let result = (a[property] < b[property]) ? -1 : (a[property] > b[property]) ? 1 : 0;
      return result * sortOrder;
    }
  }

  initDataColumns() {
    this.dataColumns = [];
    this.frozenColumns = [];
    this.extraFrozenColumns = [];
    if (this.extended) {
      if (this.tableType == 'adjustments') {
        _.forEach(this.pltColumns, (value, key) => {
          if (value.extended) {
            this.dataColumns.push(value);
          }
        });
      } else {
        _.forEach(this.EPMColumns, (value, key) => {
          if (value.extended) {
            this.dataColumns.push(value);
          }
        });
      }

    } else {
      if (this.tableType == 'adjustments' || this.tableType == 'Impacts') {
        _.forEach(this.pltColumns, (value, key) => {
          if (value.extended && !value.frozen) {
            this.dataColumns.push(value);
          }
          if (value.extended && value.frozen) {
            this.frozenColumns.push(value);
          }
          if (!value.extended && value.frozen) {
            this.extraFrozenColumns.push(value)
          }
        });
      } else {
        _.forEach(this.EPMColumns, (value, key) => {
          if (value.extended && !value.frozen) {
            this.dataColumns.push(value);
          }
          if (value.extended && value.frozen) {
            this.frozenColumns.push(value);
          }
          if (!value.extended && value.frozen) {
            this.extraFrozenColumns.push(value)
          }
        });
      }
    }
    this.frozenColumnsCache = _.merge([], this.frozenColumns);
    this.extraFrozenColumnsCache = _.merge([], this.extraFrozenColumns);
    this.dataColumnsCache = _.merge([], this.dataColumns);
    this.extraDataColumns = _.merge([], this.extraDataColumnsCache);
    this.initRandomMetaData();
   // console.log('dataColumns ==> ', this.dataColumns);
   // console.log('frozenColumns ==> ', this.frozenColumns);
   // console.log('extraFrozenColumns ==> ', this.extraFrozenColumns);
  }

  sort(sort: { key: string, value: string }): void {
    if (sort.value) {
      this.sortData = _.merge({}, this.sortData, {
        [sort.key]: sort.value === 'descend' ? 'desc' : 'asc'
      });
    } else {
      this.sortData = _.omit(this.sortData, [sort.key]);
    }
  }

  filter(key: string, value?: any) {
    if (key == 'project') {
      if (this.filterData['project'] && this.filterData['project'] != '' && value == this.filterData['project']) {
        this.filterData = _.omit(this.filterData, [key]);
      } else {
        this.filterData = _.merge({}, this.filterData, {[key]: value});
      }
      this.projects = _.map(this.projects, t => {
        if (t.projectId == value) {
          return ({...t, selected: !t.selected})
        } else if (t.selected) {
          return ({...t, selected: false})
        } else return t;
      })

    } else {
      if (value) {
        this.filterData = _.merge({}, this.filterData, {[key]: value})
      } else {
        this.filterData = _.omit(this.filterData, [key]);
      }
    }
  }

  openDrawer(index): void {
    this.visible = true;
    this.drawerIndex = index;
  }

  closePltInDrawer(pltId) {
    this.dispatch(new fromWorkspaceStore.ClosePLTinDrawer({pltId}));
  }

  openPltInDrawer(plt) {
    this.closePltInDrawer(this.sumnaryPltDetailsPltId);
    this.dispatch(new fromWorkspaceStore.OpenPLTinDrawer({pltId: plt}));
    this.openDrawer(1);
    this.getTagsForSummary();
  }

  getTagsForSummary() {
    this.pltdetailsSystemTags = this.systemTags;
    this.pltdetailsUserTags = this.userTags;
  }

  resetPath() {
    this.filterData = _.omit(this.filterData, 'project')
    this.projects = _.map(this.projects, p => ({...p, selected: false}))
    this.showDeleted = false;
  }

  close(e: NzMenuItemDirective): void {
    this.dropdown.close();
  }

  ngOnDestroy(): void {
    this.destroy();
  }


  toggleSelectPlts(event: any) {
    this.dispatch(new fromWorkspaceStore.ToggleSelectPltsFromCalibration({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      plts: event.plts,
      forDeleted: this.showDeleted
    }));
    this.initThreadsData();
   // console.log(this.selectedListOfPlts);
    this.cdRef.detectChanges()

  }


  selectSystemTag(section, tag) {
    _.forEach(this.systemTagsCount, (s, sKey) => {
      _.forEach(s, (t, tKey) => {
        if (tag == tKey && section == sKey) {
          this.systemTagsCount[sKey][tKey] = {...t, selected: !t.selected}
        } else {
          this.systemTagsCount[sKey][tKey] = {...t, selected: false}
        }
      })
    })
  }

  sortChange(sortData) {
    this.sortData = sortData;
   // console.log(this.sortData);
  }

  extendRebase() {
    if (this.extended) {
      this.headerWidth = '1013px';
      this.frozenWidth = '0';
      this.genericWidth = ['1019px', '33px', '157px'];
    } else {
      this.headerWidth = '453px';
      this.tableType == 'adjustments' || this.tableType == 'Impacts' ? this.frozenWidth = '513' : this.frozenWidth = '453';
      this.genericWidth = ['409px', '33px', '157px '];
    }
    this.adjustExention();
    this.initDataColumns();
  }

  extend(scope = 'toggle') {
    this.dispatch(new fromWorkspaceStore.ExtendStateToggleAction({scope: scope}));
    if (this.extended) {
      this.headerWidth = '1013px';
      this.frozenWidth = '0';
      this.genericWidth = ['1019px', '33px', '157px'];
    } else {
      this.headerWidth = '453px';
      this.tableType == 'adjustments' || this.tableType == 'Impacts' ? this.frozenWidth = '513' : this.frozenWidth = '453';
      this.genericWidth = ['409px', '33px', '157px '];
    }
    this.adjustExention();
    this.initDataColumns();
    this.dispatch(new extendPltSection(this.extended));
  }

  adjustExention() {
    if (this.extended) {
      _.forIn(this.pltColumns, function (value: any, key) {
        value.extended = true;
      });
      _.forIn(this.EPMColumns, function (value: any, key) {
        value.extended = true;
      })
    } else {
      _.forIn(this.pltColumns, function (value: any, key) {
        if (value.header == "User Tags" || value.fields == "pltId" || value.fields == "checkbox" || value.fields == "pltName" || value.fields == "action" || value.dragable) {
          value.extended = true;
        } else {
          value.extended = false;
        }

      });
      _.forIn(this.EPMColumns, function (value: any, key) {
        value.extended = (value.header == "User Tags" || value.fields == "pltId" || value.fields == "checkbox" || value.fields == "pltName" || value.fields == "action" || value.dragable)
      })
    }
  }

  groupByPure() {
    this.groupedByPure = !this.groupedByPure;
  }

  clickButtonPlus(bool, data?: any) {
    this.global = bool;
    this.modalTitle = "Add New Adjustment";
    this.modifyModal = false;
    this.categorySelectedFromAdjustement = null;
    this.inputValue = '';
    this.singleValue = null;
    this.columnPosition = null;

    if (!bool) {
      this.idPlt = data.pltId;
      this.addAdjustement = true;
    } else {
      this.addAdjustement = false;
    }
    this.isVisible = true;
    this.cdRef.detectChanges();
  }

  handleCancel(): void {
    this.singleValue = null;
    this.columnPosition = null;
    this.isVisible = false;
    this.cdRef.detectChanges();
  }

  addAdjustment($event) {
    let boolAdj = $event.status;
    let adjustementType = $event.singleValue;
    let adjustement = $event.category;
    let columnPosition = $event.columnPosition;

    if (this.addAdjustement) {
      if (boolAdj) {
        this.isVisible = false;
      }
      this.dispatch(new applyAdjustment({
        adjustementType: adjustementType,
        adjustement: adjustement,
        columnPosition: columnPosition,
        pltId: this.listOfPltsThread.filter(row => row.pltId == this.idPlt),
      }));
    } else {
      if (boolAdj) {
        this.isVisible = false;
      }
      this.dispatch(new saveAdjustment({
        adjustementType: adjustementType,
        adjustement: adjustement,
        columnPosition: columnPosition
      }));
    }
    this.singleValue = null;
    this.columnPosition = null;
  }

  selectCategory(p) {
    this.categorySelectedFromAdjustement = p;
    this.categorySelected = p.category;
  }

  adjustColWidth(dndDragover = 0) {
    let countBase = 0;
    let countClient = 0;
    const baseLengthArray = [];
    const clientLengthArray = [];
    _.forEach(this.adjustmentApplication, (plt, pKey) => {
      countBase = 0;
      countClient = 0;
      _.forEach(plt, (adj, aKey) => {
        if (adj.category === 'Base') {
          countBase++
        }
        if (adj.category === 'Client') {
          countClient++
        }
        baseLengthArray.push(countBase);
        clientLengthArray.push(countClient);
      });
    });
    let baseWidth = 125 * Math.max(...baseLengthArray) + dndDragover;
    let clientWidth = 125 * Math.max(...clientLengthArray) + dndDragover;
    clientWidth < 250 ? clientWidth = 250 : null;
    baseWidth < 250 ? baseWidth = 250 : null;
    let indexBase = _.findIndex(this.dataColumns, col => col.fields == 'base');
    let indexClient = _.findIndex(this.dataColumns, col => col.fields == 'client');
    this.dataColumns[indexBase].width = baseWidth.toString();
    this.dataColumns[indexClient].width = clientWidth.toString();
  }


  applyToAll(adj) {
    if (adj.linear) {
      this.singleValue = _.find(this.AdjustementType, {abv: adj.value});
    } else {
      this.singleValue = _.find(this.AdjustementType, {name: "Linear"});
      this.columnPosition = adj.value;
    }
   // console.log(this.listOfPltsThread);
    this.dispatch(new applyAdjustment({
      adjustementType: this.singleValue,
      adjustement: adj,
      columnPosition: this.columnPosition,
      pltId: this.listOfPltsThread.filter(row => row.status != 'locked'),
    }));
    this.adjustColWidth(adj);
    this.singleValue = null;
    this.columnPosition = null;
  }

  replaceToAllAdjustement(adj) {
    if (adj.linear) {
      this.singleValue = _.find(this.AdjustementType, {abv: adj.value});
    } else {
      this.singleValue = _.find(this.AdjustementType, {name: "Linear"});
      this.columnPosition = adj.value;
    }
    this.dispatch(new replaceAdjustement({
      adjustementType: this.singleValue,
      adjustement: adj,
      columnPosition: this.columnPosition,
      pltId: this.listOfPltsData,
      all: true
    }));
    this.adjustColWidth(adj);
  }

  replaceToSelectedPlt(adj) {
    if (adj.linear) {
      this.singleValue = _.find(this.AdjustementType, {abv: adj.value});
    } else {
      this.singleValue = _.find(this.AdjustementType, {name: "Linear"});
      this.columnPosition = adj.value;
    }
    this.dispatch(new replaceAdjustement({
      adjustementType: this.singleValue,
      adjustement: adj,
      columnPosition: this.columnPosition,
      pltId: this.selectedListOfPlts,
      all: false
    }));
    this.adjustColWidth(adj);
  }

  deleteAdjs(adj, index, pltId) {
    this.dispatch(new deleteAdjsApplication({
      index: index,
      pltId: pltId
    }));
    this.adjustColWidth(adj);
  }

  applyToSelected(adj) {
    if (adj.linear) {
      this.singleValue = _.find(this.AdjustementType, {abv: adj.value});
    } else {
      this.singleValue = _.find(this.AdjustementType, {name: "Linear"});
      this.columnPosition = adj.value;
    }
    this.dispatch(new applyAdjustment({
      adjustementType: this.singleValue,
      adjustement: adj,
      columnPosition: this.columnPosition,
      pltId: this.listOfPltsThread.filter(row => row.selected),
    }));
    // this.adjustColWidth(adj);
  }

  ModifyAdjustement(adj) {
    this.modalTitle = "Modify Adjustment";
    this.modifyModal = true;
    this.lastModifiedAdj = adj.id;
    this.categorySelectedFromAdjustement = _.find(this.allAdjsArray, {name: adj.name});
    if (adj.linear) {
      this.singleValue = _.find(this.AdjustementType, {abv: adj.value});
    } else {
      this.singleValue = _.find(this.AdjustementType, {name: "Linear"});
      this.columnPosition = adj.value;
    }
    this.isVisible = true;
   // console.log(this.categorySelectedFromAdjustement)
  }

  DeleteAdjustement(adj) {
    this.dispatch(new deleteAdjustment({
      adjustment: adj
    }))
  }

  saveAdjModification(event) {
    event.category.id = this.lastModifiedAdj;
    let adj = event.category;
    let value = event.value;
    let columnPosition = event.columnPosition;
    this.dispatch(new saveAdjModification({
      adjustementType: value,
      adjustement: adj,
      columnPosition: columnPosition
    }));
    this.singleValue = null;
    this.columnPosition = null;

  }

  onChangeAdjValue(adj, event) {
    adj.value = event.target.value;
    this.columnPosition = event.target.value;
    this.dispatch(new saveAdjModification({
      adjustementType: this.singleValue,
      adjustement: adj,
      columnPosition: this.columnPosition
    }));
  }

  onDrop(col, pltId, draggedAdjs, lastpltId, application) {
   // console.log(this.draggedAdjs);
   // console.log(this.adjsArray);
   // console.log(_.findIndex(this.adjsArray, row => row.id == this.draggedAdjs.id));
    if (_.findIndex(this.adjsArray, row => row.id == this.draggedAdjs.id) > -1) {
      this.dispatch(new applyAdjustment({
        adjustementType: this.singleValue,
        adjustement: this.draggedAdjs,
        columnPosition: this.columnPosition,
        pltId: pltId,
      }));
    } else {
      this.dispatch(new dropAdjustment({
        pltId: pltId,
        adjustement: this.draggedAdjs,
        lastpltId: lastpltId,
        application: application,
      }))
    }
    this.adjustColWidth();
    /*this.dragPlaceHolderCol = null;
    this.dragPlaceHolderId = null;*/
  }

  emitFilters(filters: any) {
    this.dispatch(new fromWorkspaceStore.setUserTagsFiltersFromCalibration({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      filters: filters
    }))
  }

  collapseTags() {
    this.store$.dispatch(new collapseTags(!this.collapsedTags))
    this.cdRef.detectChanges();
  }


  log(columns) {
   // console.log(columns);
  }

  // Tags Component
  assignPltsToTag($event: any) {
    const {
      selectedTags
    } = $event;
    this.dispatch(new fromWorkspaceStore.createOrAssignTags({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      ...$event,
      selectedTags: _.map(selectedTags, (el: any) => el.tagId),
      unselectedTags: _.map(_.differenceBy(this.oldSelectedTags, selectedTags, 'tagId'), (e: any) => e.tagId),
      type: 'assignOrRemove'
    }))
  }

  createTag($event: any) {
    this.dispatch(new fromWorkspaceStore.createOrAssignTags({
      ...$event,
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      type: 'createTag'
    }))
  }

  editTag() {
    this.dispatch(new fromWorkspaceStore.editTag({
      tag: this.tagForMenu,
      workspaceId: this.workspaceId,
      uwy: this.uwy
    }))
  }

  setSelectedProjects($event) {
    this.projects = $event;
  }

  setFilters(filters) {
    this.filters = filters;
  }

  setFromPlts($event) {
    this.fromPlts = $event;
  }

  setUserTags($event) {
    this.userTags = $event;
  }

  setModalIndex($event) {
    this.tagModalIndex = $event;
  }

  toggleDeletePlts($event) {
    this.showDeleted = $event;
    this.generateContextMenu(this.showDeleted);
    this.selectAll =
      !this.showDeleted
        ?
        (this.selectedListOfPlts.length > 0 || (this.selectedListOfPlts.length == this.listOfPltsData.length)) && this.listOfPltsData.length > 0
        :
        (this.selectedListOfDeletedPlts.length > 0 || (this.selectedListOfDeletedPlts.length == this.listOfDeletedPlts.length)) && this.listOfDeletedPltsData.length > 0

    this.someItemsAreSelected =
      !this.showDeleted ?
        this.selectedListOfPlts.length < this.listOfPltsData.length && this.selectedListOfPlts.length > 0
        :
        this.selectedListOfDeletedPlts.length < this.listOfDeletedPlts.length && this.selectedListOfDeletedPlts.length > 0;
    // this.generateContextMenu(this.showDeleted);
  }

  setTagModal($event: any) {
    this.tagModalVisible = $event;
  }

  setTagForMenu($event: any) {
    this.tagForMenu = $event;
  }

  setRenameTag($event: any) {
    this.editingTag = $event;
  }

  generateContextMenu(toRestore) {
    const t = ['Delete', 'Manage Tags', 'Clone To'];
    this.contextMenuItems = _.filter(this.contextMenuItemsCache, e => !toRestore ? ('Restore' != e.label) : !_.find(t, el => el == e.label))
  }


  setWsHeaderSelect($event: any) {
    this.wsHeaderSelected = $event;
  }

  setModalSelectedItems($event: any) {
    this.modalSelect = $event;
  }

  dropThreadAdjustment() {
    this.dispatch(new dropThreadAdjustment({adjustmentArray: this.adjsArray}))
  }

  selectedAdjust(adjId) {
    if (this.selectedAdjustment == adjId) {
      this.selectedAdjustment = null
    } else {
      this.selectedAdjustment = adjId;
    }
  }

  onLeaveAdjustment(id) {
    this.shownDropDown = this.dropdownVisible ? id : null;

  }

  onTableTypeChange() {
    // this.tableType = $event ? 'EP Metrics' : 'adjustments';
    this.initDataColumns();
    this.headerWidth = '453px';
    this.tableType == 'adjustments' || this.tableType == 'Impacts' ? this.frozenWidth = '513' : this.frozenWidth = '453';
  }

  changeEPM(epm) {
    this.selectedEPM = epm;
  }

  changeFinancialUnit(financialUnit: any) {
    this.financialUnits.selected = financialUnit
  }

  changeCurrency(currency: any) {
    this.currencies.selected = currency
  }

  addRemoveOnSave(plts: any) {
    /*_.forEach(plts, pltId => {
      this.listOfPltsData[pltId].toCalibrate = false;
    });
   // console.log(this.listOfPltsData);
    this.store$.dispatch(new toCalibratePlts( {
      plts:this.listOfPltsData,
      wsIdentifier:this.workspaceId + "-" + this.uwy
    }))*/
  }

  toggleColumnsManager(adj = true) {
    if (adj) {
      this.manageColumn = false;
    } else {
      this.manageAdjColumn = false;
    }
  }

  dropColumn(event: CdkDragDrop<any>) {
   // console.log(event);
    const {
      previousContainer,
      container
    } = event;

    if (previousContainer === container) {
      if (container.id == "usedListOfColumns") {
        moveItemInArray(
          this.frozenColumns,
          event.previousIndex + 1,
          event.currentIndex + 1
        );
       // console.log(container.id, this.frozenColumns);
      }
    } else {
      if (this.extraFrozenColumnsCache.length > 0) {
        transferArrayItem(
          event.previousContainer.data,
          event.container.data,
          event.previousIndex,
          event.currentIndex
        );
      } else {
        transferArrayItem(
          event.previousContainer.data,
          event.container.data,
          event.previousIndex + 1,
          event.currentIndex + 1
        );
      }
    }
  }

  initFrozenWidth() {
    let resultWidth = 0;
    _.forEach(this.frozenColumns, value => {
      resultWidth += +value.width
    })
   // console.log(resultWidth);
    this.frozenWidth = resultWidth;
    this.headerWidth = this.tableType == 'adjustments' ? resultWidth - 60 + 'px' : resultWidth + 'px';
  }

  saveColumns(adj = true) {
    if (adj) {
      this.frozenColumns = this.frozenColumnsCache;
      this.extraFrozenColumns = this.extraFrozenColumnsCache;
      this.initFrozenWidth();
      this.manageColumn = false;
    } else {
      this.dataColumns = this.dataColumnsCache;
      this.extraDataColumns = this.extraDataColumnsCache;
      this.manageAdjColumn = false;
    }

  }

  updateRowGroupMetaData() {
    this.rowGroupMetadata = {
      'SPLTH-000735433': [],
      'SPLTH-000735434': [],
      'SPLTH-000735435': []
    };
    if (this.listOfPltsData) {
      _.forEach(this.listOfPltsData, (value, i) => {
        if (!this.rowGroupMetadata[value.pureId]) {
          this.rowGroupMetadata[value.pureId] = [i]
        }
        this.rowGroupMetadata[value.pureId].push(value.pltId)

      })
     // console.log(this.rowGroupMetadata);
    }
  }

  expandAll(expand) {
    if (expand) {
      _.forEach(this.listOfPltsData, row => {
        this.rowKeys[row.pltId] = true;
      })
      this.allRowsExpanded = true;
    } else {
      _.forEach(this.listOfPltsData, row => {
        this.rowKeys[row.pltId] = false;
      })
      this.allRowsExpanded = false;
    }
  }

  checkBoxsort() {
    this.activeCheckboxSort = !this.activeCheckboxSort;
   // console.log('checked', this.activeCheckboxSort)
    if (this.activeCheckboxSort) {
      this.listOfPltsThread = _.sortBy(this.listOfPltsThread, [(o) => !o.selected]);
    } else {
      this.listOfPltsThread = this.listOfPltsThreadCache;
    }
  }

  closeSaveTemplate() {
    this.saveTemplate = false;
    this.templateName = null;
    this.templateType = "Local";
    this.templateDesc = null;
  }

  openSaveTemplate(save: boolean) {
    if (save) {
      if (this.template.id == 0) {
        this.saveTemplate = true;
      } else {
        // Save adjustment into current here
        this.template.adjs = this.adjsArray;
      }
    } else {
      if (this.template.id == 0) {
        this.saveTemplate = true;
      } else {
        this.saveTemplate = true;
        this.templateName = this.template.name;
        this.templateType = this.template.type;
        this.templateDesc = this.template.description;
        /*document.getElementById('templateNameInput').focus();
        // this.templateNameInput.nativeElement.select();
        // this.cdRef.detectChanges();
       // console.log(this.templateNameInput);*/
      }

    }
  }

  saveCurrentTemplate() {
    let today = new Date();
    let numberAdjs = today.getMilliseconds() + today.getSeconds() + today.getHours();
    const currentTemplate = {
      id: numberAdjs,
      type: this.templateType,
      name: this.templateName,
      description: this.templateDesc,
      adjs: this.adjsArray
    };
    this.template = currentTemplate
    this.templateList.push(currentTemplate);
    this.initTemplateList();
    this.closeSaveTemplate();
  }

  leftMenuActionDispatcher(action: Message) {
   // console.log(action);

    switch (action.type) {
      case leftMenuStore.emitFilters:
        this.emitFilters(action.payload);
        break;
      case leftMenuStore.setFilters:
        this.setFilters(action.payload);
        break;
      case leftMenuStore.resetPath:
        this.resetPath();
        break;
      case leftMenuStore.headerSelection:
        this.setWsHeaderSelect(action.payload);
        break;
      case leftMenuStore.filterByProject:
        this.filter(action.payload);
        break;
      case leftMenuStore.onSelectProjects:
        this.setSelectedProjects(action.payload);
        break;
      case leftMenuStore.toggleDeletedPlts:
        this.toggleDeletePlts(action.payload);
        break;
      case leftMenuStore.onSelectSysTagCount:
        this.selectSystemTag(action.payload.section, action.payload.tag);
        break;
      case leftMenuStore.onSetSelectedUserTags:
        this.setUserTags(action.payload);
        break;
    }
  }

  updateTagsInput(key, value) {
    this.tagsInputs = {...this.tagsInputs, [key]: value};
  }

  updateTableAndTagsInputs(key, value) {
    this.updateTagsInput(key, value);
  }

  addNewTag(tag) {
    /*this.updateTagsInput('toAssign', _.concat(this.tagsInputs.toAssign, tag));
    this.updateTagsInput('assignedTags', _.concat(this.tagsInputs.assignedTags, tag));*/
    this.updateTagsInput('assignedTags', _.concat(this.tagsInputs.assignedTags, tag));
    this.updateTagsInput('toAssign', _.concat(this.tagsInputs.toAssign, tag));
  }

  toggleTag({i, operation, source}) {
    if (operation == this.tagsInputs['operation']) {
      if (!_.find(this.tagsInputs.selectedTags, tag => tag.tagId == this.tagsInputs[source][i].tagId)) {
        this.updateTagsInput('selectedTags', _.merge({}, this.tagsInputs.selectedTags, {[this.tagsInputs[source][i].tagId]: {...this.tagsInputs[source][i]}}));
      } else {
        this.updateTagsInput('selectedTags', _.omit(this.tagsInputs.selectedTags, this.tagsInputs[source][i].tagId));
      }
    } else {
      this.updateTagsInput('operation', operation);
      this.updateTagsInput('selectedTags', _.merge({}, {[this.tagsInputs[source][i].tagId]: {...this.tagsInputs[source][i]}}));
    }

    if (!_.keys(this.tagsInputs.selectedTags).length) this.updateTagsInput('operation', null);
  }

  confirmSelection() {
    const tags = _.map(this.tagsInputs.selectedTags, t => ({...t, type: 'full'}));
    if (this.tagsInputs.operation == 'assign') {
      this.updateTagsInput('toAssign', _.uniqBy(_.concat(this.tagsInputs.toAssign, tags), 'tagId'))
      this.updateTagsInput('assignedTags', _.uniqBy(_.concat(this.tagsInputs.assignedTags, tags), 'tagId'))
    }

    if (this.tagsInputs.operation == 'de-assign') {
      this.updateTagsInput('toAssign', _.filter(this.tagsInputs.toAssign, tag => !_.find(tags, t => tag.tagId == t.tagId)));
      this.updateTagsInput('assignedTags', _.filter(this.tagsInputs.assignedTags, tag => !_.find(tags, t => tag.tagId == t.tagId)));
    }

    this.clearSelection();
  }

  /*checkTagType( tag ) {
    return _.every(this.getTableInputKey('selectedListOfPlts'), (plt) =>  _.some(plt.userTags, t => t.tagId == tag.tagId)) ? 'full' : 'half';
  }

  updateTagsType(d) {
    return _.map(d, tag => ({...tag, type: this.checkTagType(tag)}))
  }*/

  clearSelection() {
    this.updateTagsInput('selectedTags', {});
    this.updateTagsInput('operation', null);
  }

  save() {
    // console.log({
    //   selectedTags: this.tagsInputs.toAssign,
    //   unselectedTags: _.differenceBy(this.tagsInputs.assignedTagsCache, _.uniqBy([...this.tagsInputs.assignedTags, ...this.tagsInputs.toAssign], t => t.tagId || t.tagName), 'tagName')
    // })
    this.dispatch(new fromWorkspaceStore.AssignPltsToTag({
      userId: 1,
      wsId: this.workspaceId,
      uwYear: this.uwy,
      plts: _.map(this.selectedListOfPlts, plt => plt.pltId),
      selectedTags: this.tagsInputs.toAssign,
      unselectedTags: _.differenceBy(this.tagsInputs.assignedTagsCache, _.uniqBy([...this.tagsInputs.assignedTags, ...this.tagsInputs.toAssign], t => t.tagId || t.tagName), 'tagName')
    }));
    this.tagsInputs = {
      ...this.tagsInputs,
      _tagModalVisible: false,
      assignedTags: [],
      assignedTagsCache: [],
      usedInWs: [],
      allTags: [],
      suggested: [],
      toAssign: [],
      toRemove: [],
      selectedTags: {},
      operation: null
    };
  }

  resizing() {
    this.detectChanges();
  }

  tagsActionDispatcher(action: Message) {

    switch (action.type) {
      case tagsStore.setTagModalVisibility:
        this.setTagModal(action.payload);
        break;
      case tagsStore.addNewTag:
        this.addNewTag(action.payload)
        break;
      case tagsStore.toggleTag:
        this.toggleTag(action.payload);
        break;
      case tagsStore.confirmSelection:
        this.confirmSelection();
        break;
      case tagsStore.clearSelection:
        this.clearSelection();
        break;
      case tagsStore.save:
        this.save();
        break;
    }
  }

  onDeltaChange($event) {
    if ($event) {
      this.EPMDisplay = 'amount';
    } else {
      this.EPMDisplay = 'percentage';
    }
  }

  openLoadTemplate() {
    this.loadTemplate = true;
  }

  closeLoadTemplate() {
    this.loadTemplate = false;
  }

  loadCurrentTemplate() {
    this.template = this.searchSelectedTemplate;
    this.closeLoadTemplate();
    this.store$.dispatch(new fromWorkspaceStore.loadAdjsArray({adjustmentArray: this.searchSelectedTemplate.adjs}));
    this.cdRef.detectChanges();
  }

  onDragStart(adj: any) {
   // console.log('***********DRAG START***********', adj);
    let today = new Date();
    this.draggedAdjs = adj;
    let numberAdjs = today.getMilliseconds() + today.getSeconds() + today.getHours();
    // this.draggedAdjs = adj.map({ref:adj.id});
    // _.map(this.draggedAdjs, {ref:adj.id});
    if (!this.draggedAdjs['ref']) {
      this.draggedAdjs['ref'] = adj.id
    }
    this.draggedAdjs.id = numberAdjs;
   // console.log('Dragged ADJ ************', this.draggedAdjs)
    // this.cdRef.detectChanges();
  }

  togglePureRow($event: any) {
   // console.log('***************************************', $event, '***********************************************')
    this.rowKeys = $event;
    if (_.filter(this.rowKeys, row => row = true).length == this.rowKeys.length) {
      this.allRowsExpanded = true;
    } else if (_.filter(this.rowKeys, row => row = false).length == this.rowKeys.length) {
      this.allRowsExpanded = false;
    }
  }
}
