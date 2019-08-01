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
import {Select, Store} from "@ngxs/store";
import {
  applyAdjustment,
  deleteAdjsApplication,
  deleteAdjustment,
  dropThreadAdjustment,
  extendPltSection,
  replaceAdjustement,
  saveAdjModification,
  saveAdjustment
} from "../../store/actions";
import {switchMap} from 'rxjs/operators';
import {WorkspaceState} from "../../store/states";
import {combineLatest, Observable} from 'rxjs';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from "ng-zorro-antd";
import * as fromWorkspaceStore from "../../store";
import {ActivatedRoute, Router} from "@angular/router";
import {StateSubscriber} from "../../model/state-subscriber";
import {BaseContainer} from "../../../shared/base";
import {CURRENCIES, DEPENDENCIES, EPM_COLUMNS, EPMS, PLT_COLUMNS, SYSTEM_TAGS_MAPPING, UNITS} from "./data";


@Component({
  selector: 'app-workspace-calibration',
  templateUrl: './workspace-calibration.component.html',
  styleUrls: ['./workspace-calibration.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class WorkspaceCalibrationComponent extends BaseContainer implements OnInit, OnDestroy, StateSubscriber {

  someItemsAreSelected = false;
  selectAll = false;
  listOfPlts = [];
  listOfPltsData = [];
  selectedListOfPlts = [];
  drawerIndex = 0;
  params = {};
  loading = true;
  filters = {
    systemTag: [],
    userTag: []
  }
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
  }

  searchAddress: string;
  listOfPltsCache: any[];
  listOfDeletedPlts: any[] = [];
  frozenColumns: any[] = [];
  frozenWidth: any = '463px';
  headerWidth: any = '403px';
  genericWidth: any = ['409px', '33px', '157px'];
  selectedAdjustment: any;
  shownDropDown: any;
  lastModifiedAdj;
  dataColumns = [];
  extended: boolean = false;
  tableType = 'adjustments';
  EPMetricsTable = false;
  EPMS = EPMS;
  selectedEPM = "AEP";

  normalDisplay = true;
  deltaBasisDisplay = true;
  deltaActualDisplay = true;

  EPMDisplay = 'percentage';

  collapsedTags: boolean = false;
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
  adjutmentApplication = [];
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
  data$;
  deletedPlts$;
  deletedPlts: any;
  systemTagsMapping = SYSTEM_TAGS_MAPPING;
  selectedPlt: any;
  inputValue: any;
  selectedItemForMenu: string;
  oldSelectedTags: any;
  private dropdown: NzDropdownContextComponent;
  private Subscriptions: any[] = [];
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

  @Select(WorkspaceState.getUserTags) userTags$;
  @Select(WorkspaceState) state$: Observable<any>;
  @Select(WorkspaceState.getAdjustmentApplication) adjutmentApplication$;
  @Select(WorkspaceState.getLeftNavbarIsCollapsed()) leftNavbarIsCollapsed$;
  @ViewChild('dt')
  @ViewChild('iconNote') iconNote: ElementRef;

  constructor(
    private nzDropdownService: NzDropdownService,
    private store$: Store,
    private zone: NgZone,
    private cdRef: ChangeDetectorRef,
    private router$: Router,
    private route$: ActivatedRoute) {
    super(router$, cdRef, store$);
  }

  ngOnInit() {
    this.initDataColumns();
    this.Subscriptions.push(
      this.route$.params.pipe(
        switchMap(({wsId, year}) => {
          this.workspaceId = wsId;
          this.uwy = year;
          this.loading = true;
          this.data$ = this.select(WorkspaceState.getPlts(this.workspaceId + '-' + this.uwy));
          this.deletedPlts$ = this.select(WorkspaceState.getDeletedPlts(this.workspaceId + '-' + this.uwy));
          this.dispatch(new fromWorkspaceStore.loadAllPltsFromCalibration({
            params: {
              workspaceId: wsId, uwy: year
            }
          }));
          return combineLatest(
            this.data$,
            this.deletedPlts$
          )
        }),
        this.unsubscribeOnDestroy
      ).subscribe(([data, deletedData]: any) => {
        let d1 = [];
        let dd1 = [];
        let d2 = [];
        let dd2 = [];
        this.loading = false;
        this.systemTagsCount = {};
        if (data) {
          if (_.keys(this.systemTagsCount).length == 0) {
            _.forEach(data, (v, k) => {
              //Init Tags Counters

              //Grouped Sys Tags
              _.forEach(this.systemTagsMapping.grouped, (sectionName, section) => {
                this.systemTagsCount[sectionName] = this.systemTagsCount[sectionName] || {};
                const tag = _.toString(v[section]);
                if (tag) {
                  this.systemTagsCount[sectionName][tag] = {selected: false, count: 0, max: 0}
                }
              });

              //NONE grouped Sys Tags
              _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
                this.systemTagsCount[sectionName] = this.systemTagsCount[sectionName] || {};
                this.systemTagsCount[sectionName][section] = {selected: false, count: 0};
                this.systemTagsCount[sectionName]['non-' + section] = {selected: false, count: 0, max: 0};
              })

            })
          }

          _.forEach(data, (v, k) => {
            d1.push({...v, pltId: k});
            d2.push(k);

            /*if (v.visible) {*/
            //Grouped Sys Tags
            _.forEach(this.systemTagsMapping.grouped, (sectionName, section) => {
              const tag = _.toString(v[section]);
              if (tag) {
                if (this.systemTagsCount[sectionName][tag] || this.systemTagsCount[sectionName][tag].count === 0) {
                  const {
                    count,
                    max
                  } = this.systemTagsCount[sectionName][tag];

                  this.systemTagsCount[sectionName][tag] = {
                    ...this.systemTagsCount[sectionName][tag],
                    count: v.visible ? count + 1 : count,
                    max: max + 1
                  };
                }
              }
            })

            //NONE grouped Sys Tags
            _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
              const tag = v[section];
              if (this.systemTagsCount[sectionName][section] || this.systemTagsCount[sectionName][section] == 0) {
                const {
                  max,
                  count
                } = this.systemTagsCount[sectionName][section];
                this.systemTagsCount[sectionName][section] = {
                  ...this.systemTagsCount[sectionName][section],
                  count: v.visible ? count + 1 : count,
                  max: max + 1
                };
              }
              if (this.systemTagsCount[sectionName]['non-' + section] || this.systemTagsCount[sectionName]['non-' + section].count == 0) {
                const {
                  count,
                  max
                } = this.systemTagsCount[sectionName]['non-' + section];
                this.systemTagsCount[sectionName]['non-' + section] = {
                  ...this.systemTagsCount[sectionName]['non-' + section],
                  count: v.visible ? count + 1 : count,
                  max: max + 1
                };
              }
            })
            /*}*/

          });

          this.listOfPlts = d2;
          this.listOfPltsData = this.listOfPltsCache = d1;
          this.selectedListOfPlts = _.filter(d2, k => data[k].selected);
          _.forEach(data, (v, k) => {
            if (v.opened) {
              this.sumnaryPltDetailsPltId = k;
            }
          });
        }

        if (deletedData) {
          _.forEach(deletedData, (v, k) => {
            dd1.push({...v, pltId: k});
            dd2.push(k);
          });

          this.listOfDeletedPlts = dd2;
          this.listOfDeletedPltsData = this.listOfDeletedPltsCache = dd1;
          this.selectedListOfDeletedPlts = _.filter(dd2, k => deletedData[k].selected);
        }

        this.selectAll =
          !this.showDeleted
            ?
            (this.selectedListOfPlts.length > 0 || (this.selectedListOfPlts.length == this.listOfPlts.length)) && this.listOfPltsData.length > 0
            :
            (this.selectedListOfDeletedPlts.length > 0 || (this.selectedListOfDeletedPlts.length == this.listOfDeletedPlts.length)) && this.listOfDeletedPltsData.length > 0

        this.someItemsAreSelected =
          !this.showDeleted ?
            this.selectedListOfPlts.length < this.listOfPlts.length && this.selectedListOfPlts.length > 0
            :
            this.selectedListOfDeletedPlts.length < this.listOfDeletedPlts.length && this.selectedListOfDeletedPlts.length > 0;
        this.detectChanges();
      }),
      this.select(WorkspaceState.getProjects).subscribe((projects: any) => {
        this.projects = projects;
        this.detectChanges();
      }),
    );
    this.initRandomMetaData()
  }

  initRandomMetaData() {
    const cols: any[] = ['AAL', 'EPM2', 'EPM5', 'EPM10', 'EPM25', 'EPM50', 'EPM100', 'EPM250', 'EPM500', 'EPM1000', 'EPM5000', 'EPM10000'];
    _.forEach(this.listOfPlts, value => {
      this.randomMetaData[value] = {}
    })
    _.forEach(this.listOfPlts, value => {
      _.forEach(cols, col => {
        if (col == 'AAL') {
          this.randomMetaData[value][col] = [
            Math.floor(Math.random() * (200000000 - 1000000 + 1)) + 1000000,
            Math.floor((Math.random() - 0.5) * (1000000 - 1000 + 1)) + 1000,
            Math.floor(Math.random() * 199) - 99
          ];
        } else {
          this.randomMetaData[value][col] = [
            Math.floor(Math.random() * (2000000 - 10000 + 1)) + 10000,
            Math.floor((Math.random() - 0.5) * (1000000 - 1000 + 1)) + 1000,
            Math.floor(Math.random() * 199) - 99
          ];
        }
      })
    })
    console.log(this.randomMetaData)
    this.initDataList();
  }

  patchState(state: any): void {
    const path = state.data.calibration;
    this.leftNavbarIsCollapsed = path.leftNavbarIsCollapsed;
    this.adjutmentApplication = _.merge({}, path.adjustmentApplication);
    this.allAdjsArray = _.merge([], path.allAdjsArray);
    this.AdjustementType = _.merge([], path.adjustementType);
    this.adjsArray = _.merge([], path.adjustments);
    this.userTags = _.merge({}, path.userTags);
    this.detectChanges();
  }

  initDataList() {
    console.log('DATA INIIIIT')
    this.listOfPltsData = _.filter(this.listOfPltsData, value => value.calibrate)
  }

  initDataColumns() {
    this.dataColumns = [];
    this.frozenColumns = [];
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
      if (this.tableType == 'adjustments') {
        _.forEach(this.pltColumns, (value, key) => {
          if (value.extended && !value.frozen) {
            this.dataColumns.push(value);
          }
          if (value.extended && value.frozen) {
            this.frozenColumns.push(value);
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
        });
      }
    }
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
    console.log(this.listOfPltsData);
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

  sortChange(field: any, sortCol: any) {
    if (!sortCol) {
      this.sortData[field] = 'asc';
    } else if (sortCol === 'asc') {
      this.sortData[field] = 'desc';
    } else if (sortCol === 'desc') {
      this.sortData[field] = null
    }
  }

  extend() {
    this.extended = !this.extended;
    if (this.extended) {
      this.headerWidth = '1013px'
      this.frozenWidth = '0px'
      this.genericWidth = ['1019px', '33px', '157px'];
    } else {
      this.headerWidth = '403px';
      this.tableType == 'adjustments' ? this.frozenWidth = '463px' : this.frozenWidth = '403px';
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
      })
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

      })
      _.forIn(this.EPMColumns, function (value: any, key) {
        if (value.header == "User Tags" || value.fields == "pltId" || value.fields == "checkbox" || value.fields == "pltName" || value.fields == "action" || value.dragable) {
          value.extended = true;
        } else {
          value.extended = false;
        }

      })
    }
  }

  clickButtonPlus(bool, data?: any) {
    this.global = bool;
    this.modalTitle = "Add New Adjustment";
    this.modifyModal = false;
    this.categorySelectedFromAdjustement = null;
    this.inputValue = '';
    this.singleValue = null;
    if (!bool) {
      this.idPlt = data.pltId;
      this.addAdjustement = true;
    } else {
      this.addAdjustement = false;
    }
    this.isVisible = true;
  }

  handleCancel(): void {
    this.singleValue = null;
    this.columnPosition = null;
    this.isVisible = false;
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
        pltId: [this.idPlt],
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

  adjustColWidth(dndDragover = 10) {
    let countBase = 0;
    let countClient = 0;
    const baseLengthArray = [];
    const clientLengthArray = [];
    _.forEach(this.adjutmentApplication, (plt, pKey) => {
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
    let baseWidth = 120 * Math.max(...baseLengthArray);
    let clientWidth = 120 * Math.max(...clientLengthArray);
    clientWidth == 0 ? clientWidth = 120 : null;
    baseWidth == 0 ? baseWidth = 120 : null;
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
    this.dispatch(new applyAdjustment({
      adjustementType: this.singleValue,
      adjustement: adj,
      columnPosition: this.columnPosition,
      pltId: this.listOfPlts,
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
      pltId: this.listOfPlts,
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
      pltId: this.selectedListOfPlts,
    }));
    this.adjustColWidth(adj);
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


  onDrop(col, pltId) {
    /* this.dispatch(new dropAdjustment({
       pltId: pltId,
       adjustement: this.draggedAdjs
     }))*/
    this.adjustColWidth(this.draggedAdjs);
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
    this.collapsedTags = !this.collapsedTags;
  }


  log(columns) {
    console.log(columns);
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
        (this.selectedListOfPlts.length > 0 || (this.selectedListOfPlts.length == this.listOfPlts.length)) && this.listOfPltsData.length > 0
        :
        (this.selectedListOfDeletedPlts.length > 0 || (this.selectedListOfDeletedPlts.length == this.listOfDeletedPlts.length)) && this.listOfDeletedPltsData.length > 0

    this.someItemsAreSelected =
      !this.showDeleted ?
        this.selectedListOfPlts.length < this.listOfPlts.length && this.selectedListOfPlts.length > 0
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

  onTableTypeChange($event) {
    this.tableType = $event ? 'EP Metrics' : 'adjustments';
    this.initDataColumns();
    this.headerWidth = '403px';
    this.tableType == 'adjustments' ? this.frozenWidth = '463px' : this.frozenWidth = '403px';
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
    let res: any = {};
    _.forEach(this.listOfPltsData, plt => {
      if (plts.includes(plt.pltId)) {
        res[plt.pltId] = plt;
      }
    })
    this.listOfPltsData = res;
  }
}
