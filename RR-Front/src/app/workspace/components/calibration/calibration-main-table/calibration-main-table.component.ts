import {
  ChangeDetectionStrategy,
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
import {DEPENDENCIES, UNITS} from "../../../containers/workspace-calibration/data";
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from "ng-zorro-antd";
import * as fromWorkspaceStore from "../../../store";
import {dropThreadAdjustment, WorkspaceState} from "../../../store";
import {Select, Store} from "@ngxs/store";
import {Observable} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {BaseContainer} from "../../../../shared/base";

@Component({
  selector: 'app-calibration-main-table',
  templateUrl: './calibration-main-table.component.html',
  styleUrls: ['./calibration-main-table.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CalibrationMainTableComponent extends BaseContainer implements OnInit, OnDestroy {


  @Output('toggleSelectPlts') toggleSelectPltsEmitter: EventEmitter<any> = new EventEmitter();
  @Output('extend') extendEmitter: EventEmitter<any> = new EventEmitter();
  @Output('clickButtonPlus') clickButtonPlusEmitter: EventEmitter<any> = new EventEmitter();
  @Output('initAdjutmentApplication') initAdjutmentApplicationEmitter: EventEmitter<any> = new EventEmitter();
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
  // @Input('adjutmentApplication') adjutmentApplication: any;
  // @Input('adjutmentApplicationSubs') adjutmentApplication$: Observable<any>;
  @Input('adjutmentApplication') adjutmentApplication: any;
  @Input('systemTagsCount') systemTagsCount: any;
  //PLTS
  @Input('listOfPlts') listOfPlts: any;
  @Input('listOfPltsData') listOfPltsData: any;
  @Input('listOfDeletedPlts') listOfDeletedPlts: any;
  @Input('listOfDeletedPltsData') listOfDeletedPltsData: any;
  @Input('showDeleted') showDeleted: any;
  @Input('deletedPlts') deletedPlts: any;
  @Input('selectedAdjustment') selectedAdjustment: any;
  @Input('selectedEPM') selectedEPM: any;

  @Input('EPMDisplay') EPMDisplay: any;
  @Input('randomMetaData') randomMetaData: any;

  returnPeriods = [10000, 5000, 1000, 500, 100, 50, 25, 10, 5, 2];
  someItemsAreSelected = false;
  selectAll = false;
  selectedListOfPlts = [];
  lastSelectedId = null;
  params = {};
  loading = true;
  filters = {
    systemTag: [],
    userTag: []
  }
  filterData = {};
  sortData = {};
  activeCheckboxSort = false;
  addTagModalIndex = 0;
  fromPlts = false;
  tagForMenu = {
    tagId: null,
    tagName: '',
    tagColor: '#0700e4'
  }
  searchAddress: string;
  listOfPltsCache: any[];
  shownDropDown: any;
  inProgressCheckbox: boolean = true;
  checkedCheckbox: boolean = true;
  lockedCheckbox: boolean = true;
  failedCheckbox: boolean = true;
  requiresRegenerationCheckbox: boolean = true;
  filterInput: string = "";
  singleValue: any;
  global: any;
  dragPlaceHolderId: any;
  dragPlaceHolderCol: any;
  draggedAdjs: any;
  adjsArray: any[] = [];
  leftNavbarIsCollapsed: boolean;
  linear: boolean = false;
  dropdownVisible = false;
  workspaceId: string;
  uwy: number;
  projects: any[];
  columnPosition: number;
  mode = "calibration";
  visible = false;
  size = 'large';
  systemTags: any;
  userTags: any;
  units = UNITS;
  dependencies = DEPENDENCIES;
  selectedPlt: any;
  selectedItemForMenu: string;
  oldSelectedTags: any;
  selectedListOfDeletedPlts: any;
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
  contextMenuItemsCache = this.contextMenuItems;
  tagModalVisible: boolean;
  editingTag: boolean;
  wsHeaderSelected: boolean;
  modalSelect: any;
  initAdjutmentApplication: any;
  randomPercentage: any;
  randomAmount: number;
  AALNumber: number = null;

  @Select(WorkspaceState.getUserTags) userTags$;
  @Select(WorkspaceState) state$: Observable<any>;
  @ViewChild('dt')
  @ViewChild('iconNote') iconNote: ElementRef;
  private dropdown: NzDropdownContextComponent;
  private lastClick: string;
  returnPeriodInput: any;
  hoveredRow: any;
  private isVisible: boolean;

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

  }

  closePltInDrawer(pltId) {
    // this.dispatch(new fromWorkspaceStore.ClosePLTinDrawer({pltId}));
  }

  openPltInDrawer(plt) {

  }

  getTagsForSummary() {

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
    this.cdRef.detectChanges();
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

  adjustColWidth(dndDragover = 10) {

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

  applyToAll(adj) {

  }

  replaceToAllAdjustement(adj) {

  }

  replaceToSelectedPlt(adj) {

  }

  deleteAdjs(adj, index, pltId) {

  }

  applyToSelected(adj) {

  }

  ModifyAdjustement(adj) {

  }

  DeleteAdjustement(adj) {

  }

  onChangeAdjValue(adj, event) {

  }

  // Tags Component
  assignPltsToTag($event: any) {

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
  }

  log(columns) {
    console.log(columns);
  }

  createTag($event: any) {

  }

  editTag() {

  }

  getRandomInt(min = 0, max = 4, randomCase = false, col = null) {
    if (randomCase) {
      this.randomAmount = Math.floor((Math.random() - 0.5) * (max - min + 1)) + min;
      return this.randomAmount;
    } else {
      const result = Math.floor(Math.random() * (max - min + 1)) + min
      this.AALNumber = col == 'EPM10000' ? result * 8 : this.AALNumber;
      return result;
    }

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

  getPercentage() {
    let random = Math.floor(Math.random() * 199) - 99;
    this.randomPercentage = random;
    console.log(this.randomPercentage)
    return random;
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

  openMetricModal() {
    this.isVisible = true;
  }

  hide() {
    this.isVisible = false;
  }

  addToReturnPeriods() {
    if (this.returnPeriodInput != null && this.returnPeriodInput != undefined) {
      this.returnPeriods.push(this.returnPeriodInput)
      this.returnPeriodInput = null;
    }

  }

  removeReturnPeriod(rowData) {
    let index = _.findIndex(this.returnPeriods, row => row == rowData);
    this.returnPeriods.splice(index, 1);
  }
}
