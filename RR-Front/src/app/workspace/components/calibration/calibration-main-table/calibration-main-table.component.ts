import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  NgZone,
  OnDestroy,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import * as _ from "lodash";
import {DEPENDENCIES, SYSTEM_TAGS_MAPPING, UNITS} from "../../../containers/workspace-calibration/data";
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from "ng-zorro-antd";
import * as fromWorkspaceStore from "../../../store";
import {
  applyAdjustment,
  deleteAdjsApplication,
  deleteAdjustment,
  dropThreadAdjustment,
  PltMainState,
  replaceAdjustement,
  saveAdjModification,
  WorkspaceState
} from "../../../store";
import {Select, Store} from "@ngxs/store";
import {combineLatest, Observable} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {map, switchMap} from "rxjs/operators";
import {BaseContainer} from "../../../../shared/base";

@Component({
  selector: 'app-calibration-main-table',
  templateUrl: './calibration-main-table.component.html',
  styleUrls: ['./calibration-main-table.component.scss']
})
export class CalibrationMainTableComponent extends BaseContainer implements OnInit, OnDestroy {


  @Output('toggleSelectPlts') toggleSelectPltsEmitter: EventEmitter<any> = new EventEmitter();
  @Output('extend') extendEmitter: EventEmitter<any> = new EventEmitter();
  @Output('clickButtonPlus') clickButtonPlusEmitter: EventEmitter<any> = new EventEmitter();
  @Input('extended') extended: boolean;
  @Input('cm') cm: any;
  @Input('tableType') tableType: any;
  //columns
  @Input('pltColumns') pltColumns: any;
  @Input('EPMColumns') EPMColumns: any;
  @Input('frozenColumns') frozenColumns: any;
  @Input('dataColumns') dataColumns: any[];
  //generic width
  @Input('frozenWidth') frozenWidth: any;
  @Input('headerWidth') headerWidth: any;
  @Input('adjutmentApplication') adjutmentApplication: any;


  searchAddress: string;
  listOfPlts: any[];
  listOfPltsData: any[];
  listOfPltsCache: any[];
  selectedListOfPlts: any[];
  listOfDeletedPlts: any[] = [];
  selectedAdjustment: any;
  filterData: any;
  shownDropDown: any;
  sortData;
  lastModifiedAdj;
  activeCheckboxSort: boolean;
  selectedEPM = "OEP - Delta % & Actual Basis";
  inProgressCheckbox: boolean = true;
  checkedCheckbox: boolean = true;
  lockedCheckbox: boolean = true;
  failedCheckbox: boolean = true;
  requiresRegenerationCheckbox: boolean = true;
  collapsedTags: boolean = false;
  filterInput: string = "";
  addRemoveModal: boolean = false;
  isVisible = false;
  singleValue: any;
  global: any;
  dragPlaceHolderId: any;
  dragPlaceHolderCol: any;
  categorySelectedFromAdjustement: any;
  modalTitle: any;
  addAdjustement: any;
  modifyModal: any;
  idPlt: any;
  draggedAdjs: any;
  allAdjsArray: any[] = [];
  AdjustementType: any;
  adjsArray: any[] = [];
  leftNavbarIsCollapsed: boolean;
  linear: boolean = false;
  dropdownVisible = false;
  workspaceId: string;
  uwy: number;
  projects: any[];
  columnPosition: number;
  params: any;
  lastSelectedId;
  mode = "calibration";
  visible = false;
  size = 'large';
  filters: {
    systemTag: [],
    userTag: []
  }
  sumnaryPltDetailsPltId: any;
  pltdetailsSystemTags: any = [];
  pltdetailsUserTags: any = [];
  systemTags: any;
  systemTagsCount: any;
  userTags: any;
  userTagsCount: any;
  units = UNITS;
  dependencies = DEPENDENCIES;
  someItemsAreSelected: boolean;
  selectAll: boolean;
  drawerIndex: any;
  data$;
  deletedPlts$;
  deletedPlts: any;
  loading: boolean;
  selectedUserTags: any;
  systemTagsMapping = SYSTEM_TAGS_MAPPING;
  selectedPlt: any;
  addTagModalIndex: any;
  addTagModal: boolean;
  inputValue: any;
  fromPlts: any;
  colorPickerIsVisible: any;
  initColor: any;
  addTagModalPlaceholder: any;
  showDeleted: boolean;
  selectedItemForMenu: string;
  oldSelectedTags: any;
  tagForMenu: any;
  listOfDeletedPltsData: any;
  listOfDeletedPltsCache: any;
  selectedListOfDeletedPlts: any;
  contextMenuItemsCache = this.contextMenuItems;
  tagModalVisible: boolean;
  editingTag: boolean;
  wsHeaderSelected: boolean;
  modalSelect: any;
  contextMenuItems = [
    {
      label: 'View Detail', command: (event) => {
        this.openPltInDrawer(this.selectedPlt.pltId)
      }
    },
    {
      label: 'Delete', command: (event) => {
        this.dispatch(new fromWorkspaceStore.deletePlt({
          wsIdentifier: this.workspaceId + '-' + this.uwy,
          pltIds: this.selectedListOfPlts.length > 0 ? this.selectedListOfPlts : [this.selectedItemForMenu]
        }));
      }
    },
    {
      label: 'Clone To',
      command: (event) => {
        this.router$.navigateByUrl(`workspace/${this.workspaceId}/${this.uwy}/CloneData`, {state: {from: 'pltManager'}})
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
      label: 'Restore',
      command: () => {
        this.dispatch(new fromWorkspaceStore.restorePlt({
          wsIdentifier: this.workspaceId + '-' + this.uwy,
          pltIds: this.selectedListOfDeletedPlts.length > 0 ? this.selectedListOfDeletedPlts : [this.selectedItemForMenu]
        }))
        this.showDeleted = !(this.listOfDeletedPlts.length === 0) ? this.showDeleted : false;
        this.generateContextMenu(this.showDeleted);
      }
    }
  ];
  @Select(WorkspaceState.getUserTags) userTags$;
  @Select(WorkspaceState) state$: Observable<any>;
  @ViewChild('dt')
  @ViewChild('iconNote') iconNote: ElementRef;
  private dropdown: NzDropdownContextComponent;
  private Subscriptions: any[] = [];
  private lastClick: string;

  constructor(
    private nzDropdownService: NzDropdownService,
    private store$: Store,
    private zone: NgZone,
    private cdRef: ChangeDetectorRef,
    private router$: Router,
    private route$: ActivatedRoute) {
    super(router$, cdRef, store$);
    this.someItemsAreSelected = false;
    this.selectAll = false;
    this.listOfPlts = [];
    this.listOfPltsData = [];
    this.selectedListOfPlts = [];
    this.lastSelectedId = null;
    this.drawerIndex = 0;
    this.params = {};
    this.loading = true;
    this.filters = {
      systemTag: [],
      userTag: []
    }
    this.filterData = {};
    this.sortData = {};
    this.activeCheckboxSort = false;
    this.loading = true;
    this.addTagModal = false;
    this.addTagModalIndex = 0;
    this.systemTagsCount = {};
    this.userTagsCount = {};
    this.fromPlts = false;
    this.selectedUserTags = {};
    this.initColor = '#fe45cd'
    this.colorPickerIsVisible = false;
    this.addTagModalPlaceholder = 'Select a Tag';
    this.showDeleted = false;
    this.tagForMenu = {
      tagId: null,
      tagName: '',
      tagColor: '#0700e4'
    }
  }

  ngOnInit() {
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
      this.select(WorkspaceState.getProjects()).subscribe((projects: any) => {
        this.projects = projects;
        this.detectChanges();
      }),
      this.getAttr('loading').subscribe(l => this.loading = l),
    );
  }


  getAttr(path) {
    return this.select(PltMainState.getAttr).pipe(map(fn => fn(path)));
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
    // this.dispatch(new fromWorkspaceStore.ClosePLTinDrawer({pltId}));
  }

  openPltInDrawer(plt) {
    this.closePltInDrawer(this.sumnaryPltDetailsPltId);
    // this.dispatch(new fromWorkspaceStore.OpenPLTinDrawer({pltId: plt}));
    this.openDrawer(1);
    this.getTagsForSummary();
  }

  getTagsForSummary() {
    this.pltdetailsSystemTags = this.systemTags;
    this.pltdetailsUserTags = this.userTags;
  }

  close(e: NzMenuItemDirective): void {
    this.dropdown.close();
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  toggleSelectPlts(plts: any) {
    this.toggleSelectPltsEmitter.emit({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      plts,
      forDeleted: this.showDeleted
    });
  }

  checkAll($event) {
    this.toggleSelectPlts(
      _.zipObject(
        _.map(!this.showDeleted ? this.listOfPlts : this.listOfDeletedPlts, plt => plt),
        _.range(!this.showDeleted ? this.listOfPlts.length : this.listOfDeletedPlts.length).map(el => ({selected: !this.selectAll && !this.someItemsAreSelected}))
      )
    );
  }

  selectSinglePLT(pltId: number, $event?: boolean) {
    this.toggleSelectPlts({
      [pltId]: {
        selected: $event
      }
    });
  }

  unCheckAll() {
    this.toggleSelectPlts(
      _.zipObject(
        _.map([...this.listOfPlts, ...this.listOfDeletedPlts], plt => plt),
        _.range(this.listOfPlts.length + this.listOfDeletedPlts.length).map(el => ({selected: false}))
      )
    );
  }

  handlePLTClick(pltId, i: number, $event: MouseEvent) {
    const isSelected = _.findIndex(!this.showDeleted ? this.selectedListOfPlts : this.listOfDeletedPlts, el => el == pltId) >= 0;
    if ($event.ctrlKey || $event.shiftKey) {
      this.lastClick = "withKey";
      this.handlePLTClickWithKey(pltId, i, !isSelected, $event);
    } else {
      this.lastSelectedId = i;
      this.toggleSelectPlts(
        _.zipObject(
          _.map(!this.showDeleted ? this.listOfPlts : this.listOfDeletedPlts, plt => plt),
          _.map(!this.showDeleted ? this.listOfPlts : this.listOfDeletedPlts, plt => ({selected: plt == pltId && (this.lastClick == 'withKey' || !isSelected)}))
        )
      );
      this.lastClick = null;
    }
  }

  onSort($event: any) {
    const {} = $event;

  }

  checkBoxsort() {
    this.activeCheckboxSort = !this.activeCheckboxSort;
    if (this.activeCheckboxSort) {
      this.listOfPltsData = _.sortBy(this.listOfPltsData, [(o) => {
        return !o.selected;
      }]);
    } else {
      this.listOfPltsData = this.listOfPltsCache;
    }
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
    this.extendEmitter.emit();
  }

  clickButtonPlus(bool, data?: any) {
    this.clickButtonPlusEmitter.emit({bool: bool, data: data})
  }

  handleCancel(): void {
    this.isVisible = false;
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

  setUserTags($event) {
    this.userTags = $event;
  }

  generateContextMenu(toRestore) {
    const t = ['Delete', 'Manage Tags', 'Clone To'];
    this.contextMenuItems = _.filter(this.contextMenuItemsCache, e => !toRestore ? ('Restore' != e.label) : !_.find(t, el => el == e.label))
  }

  setWsHeaderSelect($event: any) {
    this.wsHeaderSelected = $event;
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

  statusFilterActive(status) {
    switch (status) {
      case 'in progress':
        return this.inProgressCheckbox;
      case 'valid':
        return this.checkedCheckbox;
      case 'locked':
        return this.lockedCheckbox;
      case 'failed':
        return this.failedCheckbox;
      case 'requires regeneration':
        return this.requiresRegenerationCheckbox
    }
  }

  onLeaveAdjustment(id) {
    this.shownDropDown = this.dropdownVisible ? id : null;

  }

  trackby(row, index) {
    return row.pltId;
  }

  private handlePLTClickWithKey(pltId: number, i: number, isSelected: boolean, $event: MouseEvent) {
    if ($event.ctrlKey) {
      this.selectSinglePLT(pltId, isSelected);
      this.lastSelectedId = i;
      return;
    }

    if ($event.shiftKey) {
      if (!this.lastSelectedId) this.lastSelectedId = 0;
      if (this.lastSelectedId || this.lastSelectedId == 0) {
        const max = _.max([i, this.lastSelectedId]);
        const min = _.min([i, this.lastSelectedId]);
        this.toggleSelectPlts(
          _.zipObject(
            _.map(!this.showDeleted ? this.listOfPlts : this.listOfDeletedPlts, plt => plt),
            _.map(!this.showDeleted ? this.listOfPlts : this.listOfDeletedPlts, (plt, i) => ({selected: i <= max && i >= min})),
          )
        );
      } else {
        this.lastSelectedId = i;
      }
      return;
    }
  }


  /*
  pltColumns = PLT_COLUMNS;
  EPMColumns = EPM_COLUMNS;
  @Input() tableType;
  @Input() listOfPlts;
  @Input() listOfDeletedPlts;
  dataColumns;
  frozenColumns;
  lastSelectedId;
  lastClick: string;
  frozenWidth: any = '463px';
  @Input() showDeleted;
  @Input() deletedPlts;
  @Input() listOfPltsData;
  @Input() sortData;
  @Input() filterData;
  @Input() filters;
  @Input() cm;
  @Input() extended;
  @Input() addRemoveModal;
  @Input() selectedListOfPlts;
  @Input() genericWidth;
  @Input() adjsArray;
  @Input() shownDropDown;
  @Input() selectedAdjustment;
  @Input() dropdownVisible;
  @Input() selectAll;
  @Input() someItemsAreSelected;
  @Input() inProgressCheckbox;
  @Input() checkedCheckbox;
  @Input() lockedCheckbox;
  @Input() requiresRegenerationCheckbox;
  @Input() failedCheckbox;
  @Input() selectedItemForMenu;
  @Input() adjutmentApplication;
  @Input() draggedAdjs;
  @Input() dragPlaceHolderId;
  @Input() dragPlaceHolderCol;
  @Input() selectedEPM;

  @Output('onSort') onSortEmitter: EventEmitter<any> = new EventEmitter();
  @Output('extend') extendEmitter: EventEmitter<any> = new EventEmitter();
  @Output('clickButtonPlus') clickButtonPlusEmitter: EventEmitter<any> = new EventEmitter();
  @Output('onLeaveAdjustment') onLeaveAdjustmentEmitter: EventEmitter<any> = new EventEmitter();
  @Output('selectedAdjust') selectedAdjustEmitter: EventEmitter<any> = new EventEmitter();
  @Output('dropThreadAdjustment') dropThreadAdjustmentEmitter: EventEmitter<any> = new EventEmitter();
  @Output('ModifyAdjustement') ModifyAdjustementEmitter: EventEmitter<any> = new EventEmitter();
  @Output('applyToSelected') applyToSelectedEmitter: EventEmitter<any> = new EventEmitter();
  @Output('applyToAll') applyToAllEmitter: EventEmitter<any> = new EventEmitter();
  @Output('replaceToSelectedPlt') replaceToSelectedPltEmitter: EventEmitter<any> = new EventEmitter();
  @Output('replaceToAllAdjustement') replaceToAllAdjustementEmitter: EventEmitter<any> = new EventEmitter();
  @Output('onChangeAdjValue') onChangeAdjValueEmitter: EventEmitter<any> = new EventEmitter();
  @Output('checkAll') checkAllEmitter: EventEmitter<any> = new EventEmitter();
  @Output('checkBoxsort') checkBoxsortEmitter: EventEmitter<any> = new EventEmitter();
  @Output('sortChange') sortChangeEmitter: EventEmitter<any> = new EventEmitter();
  @Output('filter') filterEmitter: EventEmitter<any> = new EventEmitter();
  @Output('onDrop') onDropEmitter: EventEmitter<any> = new EventEmitter();
  @Output('handlePLTClick') handlePLTClickEmitter: EventEmitter<any> = new EventEmitter();
  @Output('handlePLTClickWithKey') handlePLTClickWithKeyEmitter: EventEmitter<any> = new EventEmitter();
  @Output('selectSinglePLT') selectSinglePLTEmitter: EventEmitter<any> = new EventEmitter();
  @Output('statusFilterActive') statusFilterActiveEmitter: EventEmitter<any> = new EventEmitter();


  constructor() {
  }

  ngOnInit() {
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

  onSort($event: any) {

  }

  extend() {

  }

  clickButtonPlus(b: boolean) {

  }

  onLeaveAdjustment(id: any) {
    this.shownDropDown = this.dropdownVisible ? id : null;
  }

  selectedAdjust(id: any) {
    if (this.selectedAdjustment == id) {
      this.selectedAdjustment = null
    } else {
      this.selectedAdjustment = id;
    }
  }

  dropThreadAdjustment() {

  }

  ModifyAdjustement(adj) {

  }

  DeleteAdjustement(adj) {

  }

  applyToSelected(adj) {

  }

  applyToAll(adj) {

  }

  replaceToSelectedPlt(adj) {

  }

  replaceToAllAdjustement(adj) {

  }

  onChangeAdjValue(adj, $event: Event) {

  }

  checkAll($event: boolean) {
    this.checkAllEmitter.emit(
      _.zipObject(
        _.map(!this.showDeleted ? this.listOfPlts : this.listOfDeletedPlts, plt => plt),
        _.range(!this.showDeleted ? this.listOfPlts.length : this.listOfDeletedPlts.length).map(el => ({selected: !this.selectAll && !this.someItemsAreSelected}))
      )
    );
  }

  checkBoxsort() {

  }

  sortChange(fields: any, sortDatum: any) {

  }

  filter(fields: any, value: any) {

  }

  onDrop(col, pltId: any) {

  }

  handlePLTClick(pltId: any, i, $event: MouseEvent) {
    const isSelected = _.findIndex(!this.showDeleted ? this.selectedListOfPlts : this.listOfDeletedPlts, el => el == pltId) >= 0;
    if ($event.ctrlKey || $event.shiftKey) {
      this.lastClick = "withKey";
      this.handlePLTClickWithKey(pltId, i, !isSelected, $event);
    } else {
      this.lastSelectedId = i;
      this.handlePLTClickEmitter.emit(
        _.zipObject(
          _.map(!this.showDeleted ? this.listOfPlts : this.listOfDeletedPlts, plt => plt),
          _.map(!this.showDeleted ? this.listOfPlts : this.listOfDeletedPlts, plt => ({selected: plt == pltId && (this.lastClick == 'withKey' || !isSelected)}))
        )
      );
      this.lastClick = null;
    }
  }

  selectSinglePLT(pltId: any, $event: boolean) {
    this.selectSinglePLTEmitter.emit({
      [pltId]: {
        selected: $event
      }
    });
  }

  statusFilterActive(status: any) {
    return false;
  }

  private handlePLTClickWithKey(pltId: any, i: any, isSelected: boolean, $event: MouseEvent) {
    if ($event.ctrlKey) {
      this.selectSinglePLT(pltId, isSelected);
      this.lastSelectedId = i;
      return;
    }
    if ($event.shiftKey) {
      if (!this.lastSelectedId) this.lastSelectedId = 0;
      if (this.lastSelectedId || this.lastSelectedId == 0) {
        const max = _.max([i, this.lastSelectedId]);
        const min = _.min([i, this.lastSelectedId]);
        this.handlePLTClickWithKeyEmitter.emit(
          _.zipObject(
            _.map(!this.showDeleted ? this.listOfPlts : this.listOfDeletedPlts, plt => plt),
            _.map(!this.showDeleted ? this.listOfPlts : this.listOfDeletedPlts, (plt, i) => ({selected: i <= max && i >= min})),
          )
        );
      } else {
        this.lastSelectedId = i;
      }
      return;
    }
  }*/
}
