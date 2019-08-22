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
import {DEPENDENCIES, PURE, UNITS} from "../../../containers/workspace-calibration/data";
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
  @Output('closeReturnPeriods') closeReturnPeriodsEmitter: EventEmitter<any> = new EventEmitter();
  @Output('sortChange') sortChangeEmitter: EventEmitter<any> = new EventEmitter();
  @Output('expandAll') expandEmitter: EventEmitter<any> = new EventEmitter();

  @Input('extended') extended: boolean;
  // @Input('cm') cm: any;
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
  @Input('listOfPltsData') listOfPltsData: any;
  @Input('listOfPltsThread') listOfPltsThread: any;
  @Input('listOfDeletedPlts') listOfDeletedPlts: any;
  @Input('listOfDeletedPltsData') listOfDeletedPltsData: any;
  @Input('showDeleted') showDeleted: any;
  @Input('selectedListOfPlts') selectedListOfPlts: any;
  @Input('deletedPlts') deletedPlts: any;
  @Input('selectedAdjustment') selectedAdjustment: any;
  @Input('selectedEPM') selectedEPM: any;

  @Input('EPMDisplay') EPMDisplay: any;
  @Input('randomMetaData') randomMetaData: any;
  @Input('someItemsAreSelected') someItemsAreSelected: any;
  @Input('selectAll') selectAll: any;
  @Input('manageReturnPeriods') manageReturnPeriods: boolean;
  @Input('rowGroupMetadata') rowGroupMetadata: any;
  @Input('groupedByPure') groupedByPure: any;
  @Input('rowKeys') rowKeys: any;
  @Input('allRowsExpanded') allRowsExpanded: any;
  returnPeriods = [10000, 5000, 1000, 500, 100, 50, 25, 10, 5, 2];
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
  listOfPltsDataCache: any[];
  shownDropDown: any;
  inProgressCheckbox: boolean = true;
  newCheckbox: boolean = true;
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
      },
      items: [
        {
          label: 'New Default Thread', command: (event) => {
            this.openPltInDrawer(this.selectedPlt.pltId)
          }
        },
        {
          label: 'New Non-default Thread', command: (event) => {
            this.openPltInDrawer(this.selectedPlt.pltId)
          }
        },
      ]
    },
    {
      label: 'Clone',
      command: (event) => {
        this.router$.navigateByUrl(`workspace/${this.workspaceId}/${this.uwy}/CloneData`, {state: {from: 'pltManager'}})
      },
      items: [
        {
          label: 'New Default Thread', command: (event) => {
            this.openPltInDrawer(this.selectedPlt.pltId)
          }
        },
        {
          label: 'New Non-default Thread', command: (event) => {
            this.openPltInDrawer(this.selectedPlt.pltId)
          }
        },
      ]
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
      },
      items: [
        {
          label: 'Add to New Inuring Package', command: (event) => {
            this.openPltInDrawer(this.selectedPlt.pltId)
          }
        },
        {
          label: 'Add to Existing Inuring Package', command: (event) => {
            this.openPltInDrawer(this.selectedPlt.pltId)
          }
        },
      ]
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
        let d = _.map(this.selectedListOfPlts, k => _.find(this.listOfPltsThread, e => e.pltId == k).userTags);
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
  wsHeaderSelected: boolean;
  modalSelect: any;
  initAdjutmentApplication: any;
  randomPercentage: any;
  randomAmount: number;
  AALNumber: number = null;
  pure = PURE;
  @Select(WorkspaceState.getUserTags) userTags$;
  @Select(WorkspaceState) state$: Observable<any>;
  @ViewChild('dt')
  @ViewChild('iconNote') iconNote: ElementRef;
  private dropdown: NzDropdownContextComponent;
  private lastClick: string;
  returnPeriodInput: any;
  clickedDropdown: any;
  private userTagsLength: number = 10000;


  constructor(
    private nzDropdownService: NzDropdownService,
    private store$: Store,
    private zone: NgZone,
    private changeRef: ChangeDetectorRef,
    private router$: Router,
    private route$: ActivatedRoute) {
    super(router$, changeRef, store$);

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
    this.changeRef.detectChanges();
  }

  checkAll($event) {
    this.toggleSelectPlts(
      _.zipObject(
        _.map(this.listOfPltsThread, plt => plt.pltId),
        _.range(this.listOfPltsThread.length).map(el => ({selected: !this.selectAll && !this.someItemsAreSelected}))
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
        _.map(this.listOfPltsData, plt => plt.pltId),
        _.range(this.listOfPltsData.length).map(el => ({selected: false}))
      )
    );
  }

  handlePLTClick(pltId, i: number, $event: MouseEvent) {
    i = _.findIndex(this.listOfPltsThread, (row: any) => row.pltId == pltId);
    let index = -1;
    let isSelected;
    _.forEach(this.listOfPltsThread, (plt, i) => {
      if (plt.pltId == pltId) {
        console.log('opla')
        isSelected = plt.selected
      }
    });
    console.log('isSelected', isSelected)
    console.log(this.listOfPltsThread)
    // this.selectSinglePLT(pltId,!_.find(this.listOfPltsThread, plt => plt.pltId == pltId).selected);

    console.log(this.selectedListOfPlts);
    if ($event.ctrlKey || $event.shiftKey) {
      this.lastClick = "withKey";
      this.handlePLTClickWithKey(pltId, i, !isSelected, $event);
    } else {
      this.lastSelectedId = i;
      this.toggleSelectPlts(
        _.zipObject(
          _.map(this.listOfPltsThread, plt => plt.pltId),
          _.map(this.listOfPltsThread, plt => ({selected: plt.pltId == pltId && (this.lastClick == 'withKey' || !isSelected)}))
        )
      );
      this.lastClick = null;
    }
  }

  adjustColWidth(dndDragover = 10) {

  }

  onSort($event: any) {
    const {} = $event;
    console.log('test')
  }

  checkBoxsort() {
    this.activeCheckboxSort = !this.activeCheckboxSort;
    if (this.activeCheckboxSort) {
      this.listOfPltsThread = _.sortBy(this.listOfPltsThread, [(o) => {
        return !o.selected;
      }]);
    } else {
      this.listOfPltsThread = this.listOfPltsDataCache;
    }
  }

  sortChange(field: any, sortCol: any) {
    console.log(field, sortCol);
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
      case 'new':
        return this.newCheckbox;
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

  numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  }


  hide() {
    this.closeReturnPeriodsEmitter.emit()
  }

  addToReturnPeriods() {
    if (this.returnPeriodInput != null && this.returnPeriodInput != undefined) {
      this.returnPeriods.push(this.returnPeriodInput)
      this.returnPeriodInput = null;
    }
    this.returnPeriods.sort((a, b) => b - a)
  }

  removeReturnPeriod(rowData) {
    let index = _.findIndex(this.returnPeriods, row => row == rowData);
    this.returnPeriods.splice(index, 1);
  }

  changeDropdownDisplay($event: boolean, pltId: any) {
    if ($event) {
      this.clickedDropdown = pltId;
    } else {
      this.clickedDropdown = null;
    }
  }

  expandAll(expand: boolean) {
    this.expandEmitter.emit(expand);
  }

  onColResize(event: any) {
    const {
      innerText,
      scrollWidth
    } = event.element;
    console.log(event)

    if (innerText == "User Tags") {
      console.log(_.floor(scrollWidth / 18));
      this.userTagsLength = _.floor(scrollWidth / 18);
      this.detectChanges();
    }
  }

  protected detectChanges() {
    if (!this.changeRef['destroyed'])
      this.changeRef.detectChanges();
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
        console.log('min-max ==> ', min, max);
        this.toggleSelectPlts(
          _.zipObject(
            _.map(this.listOfPltsThread, plt => plt.pltId),
            _.map(this.listOfPltsThread, (plt, i) => ({selected: i <= max && i >= min})),
          )
        );
      } else {
        this.lastSelectedId = i;
      }
      console.log(this.lastSelectedId);
      return;
    }
  }
}
