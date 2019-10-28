import {ChangeDetectorRef, Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Observable, Subject, Subscription} from "rxjs";
import {Actions, Store} from "@ngxs/store";
import * as rightMenuStore from "../../../../shared/components/plt/plt-right-menu/store";
import {Actions as rightMenuActions} from "../../../../shared/components/plt/plt-right-menu/store";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {NotificationService} from "../../../../shared/notification.service";
import {SearchService} from "../../../../core/service";
import {WorkspaceState} from "../../../store/states";
import * as fromWorkspaceStore from "../../../store";
import {toCalibratePlts} from "../../../store";
import * as _ from "lodash";
import {switchMap, tap} from "rxjs/operators";
import {Debounce} from "../../../../shared/decorators";
import {LazyLoadEvent} from "primeng/api";
import {Message} from "../../../../shared/message";
import {BaseContainer} from "../../../../shared/base";
import {SystemTagsService} from "../../../../shared/services/system-tags.service";
import * as leftMenuStore from "../../../../shared/components/plt/plt-left-menu/store";
import * as tagsStore from "../../../../shared/components/plt/plt-tag-manager/store";
import * as tableStore from "../../../../shared/components/plt/plt-main-table/store";
import {Actions as tableActions} from "../../../../shared/components/plt/plt-main-table/store";

@Component({
  selector: 'app-add-remove-pop-up',
  templateUrl: './add-remove-pop-up.component.html',
  styleUrls: ['./add-remove-pop-up.component.scss']
})
export class AddRemovePopUpComponent extends BaseContainer implements OnInit, OnDestroy {

  leftMenuInputs: leftMenuStore.Input;
  tagsInputs: tagsStore.Input;
  tableInputs: tableStore.Input;

  data$: Observable<any>;
  deletedPlts$: Observable<any>;
  subscriptions: Subscription;
  unSubscribe$: Subject<void>;
  @Output('onVisibleChange') onVisibleChange: EventEmitter<any> = new EventEmitter();
  @Output('onSelectProjectNext') onSelectProjectNext: EventEmitter<any> = new EventEmitter();
  @Output('onSelectItems') onSelectItems: EventEmitter<any> = new EventEmitter();
  @Output('onSelectWorkspace') onSelectWorkspace: EventEmitter<any> = new EventEmitter();
  @Output('onSave') onSaveEmitter: EventEmitter<any> = new EventEmitter();

  @Input() isVisible;
  @Input('selectionStep') selectionStep: string;
  @Input('userTags') userTags;
  @Input('projects') projects: string;
  @Input() multiSteps: boolean;
  workspaceId: any;
  uwy: any;
  @Input() stepConfig: {
    wsId: string,
    uwYear: string,
    plts: any[]
  };
  rightMenuInputs: rightMenuStore.Input;
  workspace: any;
  index: any;
  listOfPltsThread: any;
  searchWorkspace = false;
  selectedWorkspace: any = null;
  keywordFormGroup: FormGroup;
  paginationOption = {currentPage: 0, page: 0, size: 40, total: '-'};
  contracts = [];
  loading;
  browesing: boolean;
  private pltTableSubscription: Subscription;
  private pltProjectSubscription: Subscription;
  private pltUserTagsSubscription: Subscription;
  private d: Subscription;
  private selectedPlt: string;

  constructor(
    private route: ActivatedRoute,
    private store$: Store,
    private router: Router,
    private actions$: Actions,
    private notificationService: NotificationService,
    private cdRef: ChangeDetectorRef,
    private searchService: SearchService,
    private fb: FormBuilder,
    private systemTagService: SystemTagsService,
  ) {
    super(router, cdRef, store$);
    this.unSubscribe$ = new Subject<void>();
    this.keywordFormGroup = new FormGroup({
      keyword: new FormControl(null)
    });
    this.browesing = false;
    this.tableInputs = {
      scrollConfig: {
        containerHeight: null,
        containerGap: '49px',
      },
      dataKey: "pltId",
      openedPlt: "",
      contextMenuItems: null,
      filterData: {},
      filters: {
        userTag: [],
        systemTag: {}
      },
      sortData: {},
      selectAll: false,
      selectAllDeletedPlts: false,
      someItemsAreSelected: false,
      someDeletedItemsAreSelected: false,
      showDeleted: false,
      pltColumns: [
        {
          sortDir: 1,
          fields: '',
          header: '',
          sorted: false,
          filtred: false,
          resizable: false,
          width: '40',
          unit: 'px',
          icon: null,
          type: 'checkbox',
          active: true
        },
        {
          sortDir: 1,
          fields: '',
          header: 'User Tags',
          sorted: false,
          filtred: false,
          resizable: true,
          width: '66',
          unit: 'px',
          icon: null,
          type: 'tags',
          active: true
        },
        {
          sortDir: 1,
          fields: 'pltId',
          header: 'PLT ID',
          sorted: true,
          filtred: true,
          resizable: true,
          icon: null,
          width: '28',
          unit: '%',
          type: 'id',
          active: true
        },
        {
          sortDir: 1,
          fields: 'pltName',
          header: 'PLT Name',
          sorted: true,
          filtred: true,
          resizable: true,
          width: '80',
          unit: '%',
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: 'peril',
          header: 'Peril',
          sorted: true,
          filtred: true,
          resizable: false,
          width: '53',
          unit: 'px',
          icon: null,
          type: 'field',
          textAlign: 'center',
          active: true
        },
        {
          sortDir: 1,
          fields: 'regionPerilCode',
          header: 'Region Peril Code',
          sorted: true,
          filtred: true,
          resizable: true,
          width: '35',
          unit: '%',
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: 'regionPerilName',
          header: 'Region Peril Name',
          sorted: true,
          filtred: true,
          resizable: true,
          width: '60',
          unit: '%',
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: 'grain',
          header: 'Grain',
          sorted: true,
          filtred: true,
          resizable: true,
          width: '70',
          unit: '%',
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: 'deletedBy',
          forDelete: true,
          header: 'Deleted By',
          sorted: true,
          filtred: true,
          resizable: true,
          icon: null,
          type: 'field', active: false
        },
        {
          sortDir: 1,
          fields: 'deletedAt',
          forDelete: true,
          header: 'Deleted On',
          sorted: true,
          filtred: true,
          resizable: true,
          icon: null,
          type: 'date', active: false
        },
        {
          sortDir: 1,
          fields: 'vendorSystem',
          header: 'Vendor System',
          sorted: true,
          filtred: true,
          resizable: true,
          width: '25%',
          icon: null,
          type: 'field', active: true
        },
        {
          sortDir: 1,
          fields: 'rap',
          header: 'RAP',
          sorted: true,
          filtred: true,
          resizable: true,
          width: '25',
          unit: '%',
          icon: null,
          type: 'field',
          active: true
        },{
          sortDir: 1,
          fields: '',
          header: '',
          sorted: false,
          filtred: false,
          icon: 'icon-note',
          type: 'icon',
          width: '50',
          unit: 'px',
          active: true,
          tooltip: "Published for Pricing",
          highlight: 'Published'
        },
        {
          sortDir: 1,
          fields: '',
          header: '',
          sorted: false,
          filtred: false,
          icon: 'icon-dollar-alt',
          type: 'icon',
          width: '50',
          unit: 'px',
          active: true,
          tooltip: "Priced",
          highlight: 'Priced'
        },
        {
          sortDir: 1,
          fields: '',
          header: '',
          sorted: false,
          filtred: false,
          icon: 'icon-focus-add',
          type: 'icon',
          width: '50',
          unit: 'px',
          active: true,
          tooltip: "Published for Accumulation",
          highlight: 'Accumulated'
        },
      ],
      filterInput: '',
      listOfDeletedPltsCache: [],
      listOfDeletedPltsData: [],
      listOfPltsCache: [],
      listOfPltsData: [],
      selectedListOfDeletedPlts: [],
      selectedListOfPlts: [],
      status: {
        Published: {
          selected: false
        },
        Priced: {
          selected: false
        },
        Accumulated: {
          selected: false
        },
      }
    };
    this.rightMenuInputs = {
      basket: [],
      pltDetail: null,
      pltHeaderId: '',
      selectedTab: {
        index: 0,
        title: 'pltDetail',
      },
      tabs: {'basket': true,'pltDetail': true},
      visible: false,
      mode: "pop-up",
      summary: {}
    };
    this.leftMenuInputs= {
      wsId: this.workspaceId,
      uwYear: this.uwy,
      projects: [],
      showDeleted: false,
      filterData: this.filterData,
      filters: {
        systemTag: [],
        userTag: []
      },
      deletedPltsLength: 0,
      userTags: [],
      selectedListOfPlts: [],
      systemTagsCount: {},
      wsHeaderSelected: true,
      pathTab: true
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
    };
    this.setRightMenuSelectedTab('basket');
  }

  private _filter = {};
  private lastClick: string;
  private lastSelectedId: number;

  get filter() {
    // let tags = _.isString(this.searchContent) ? [] : (this.searchContent || []);
    const tableFilter = _.map(this._filter, (value, key) => ({key, value}));
    // return _.concat(tags ,  tableFilter).filter(({value}) => value).map((item: any) => ({
    return tableFilter.filter(({value}) => value).map((item: any) => ({
      ...item,
      field: _.camelCase(item.key),
      operator: item.operator || 'LIKE'
    }));
  }

  ngOnInit() {
    this.updateTable('userTags', this.userTags);
    // this.updateTable('projects',this.projects);
  }

  getPlts() {
    return this.select(WorkspaceState.getPltsForCalibration(this.workspaceId + '-' + this.uwy));
  }

  getProjects() {
    return this.select(WorkspaceState.getProjectsPlt(this.workspaceId + '-' + this.uwy));
  }

  getUserTags() {
    return this.select(WorkspaceState.getUserTagsPlt(this.workspaceId + '-' + this.uwy));
  }
  getBrowesingItemsDirectly() {

    this.observeRouteParamsWithSelector(() => this.getPlts()).subscribe((data) => {
      this.updateTable('systemTagsCount', this.systemTagService.countSystemTags(data));
      this.updateTable('listOfPltsCache', _.map(data, (v, k) => ({...v, pltId: k})));
      this.updateTable('listOfPltsData', [...this.tableInputs.listOfPltsCache]);
      this.initThreadsData();
      this.updateMenuKey('basket', _.filter(this.listOfPltsThread, row => row.calibrate));
      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getPlts()).subscribe(data => {
      this.updateTable('selectAll', this.tableInputs.selectedListOfPlts.length > 0 || (this.tableInputs.selectedListOfPlts.length == this.listOfPltsThread.length) && this.listOfPltsThread.length > 0);

      this.updateTable('someItemsAreSelected', this.tableInputs.selectedListOfPlts.length < this.listOfPltsThread.length && this.tableInputs.selectedListOfPlts.length > 0);
      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getProjects()).subscribe((projects: any) => {
      this.updateLeftMenuInputs('projects', _.map(projects, p => ({...p, selected: false})));
      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getUserTags()).subscribe(userTags => {
      this.updateLeftMenuInputs('userTags', userTags);
      this.detectChanges();
    });

    const pltToCalibrate = [];
    _.forEach(this.listOfPltsThread, row => {
      pltToCalibrate[row.pltId] = {calibrate: row.toCalibrate};
    })
    console.log('&&&&&&&&&&&&&&&&&&&&&&&&&&', pltToCalibrate);
    this.calibrateSelectPlts(pltToCalibrate);

  }

  observeRouteParamsWithSelector(operator) {
    console.log(operator);
    return this.observeRouteParams()
      .pipe(
        switchMap(() => operator()),
        this.unsubscribeOnDestroy
      )
  }

  observeRouteParams() {
    return this.route.params.pipe(tap(({wsId, year}) => {
      this.workspaceId = wsId;
      this.uwy = year;
    }))
  }
  initThreadsData() {
    if (this.tableInputs.listOfPltsData) {
      this.listOfPltsThread = Array.prototype.concat.apply([], this.tableInputs.listOfPltsData.map((row: any) => row.threads));
      this.updateTable('selectedListOfPlts', _.filter(this.listOfPltsThread, (v, k) => v.calibrate).map(e => e.pltId));
    }
  }
  ngOnDestroy(): void {
    this.unSubscribe$.next();
    this.unSubscribe$.complete();
  }

  openWorkspaceInSlider(event?) {
    console.log(event);
  }

  @Debounce(500)
  filterData($event, target) {
    this._filter = {...this._filter, [target]: $event || null};
    this._loadData();
  }

  loadMore(event: LazyLoadEvent) {
    this.paginationOption.currentPage = event.first;
    this._loadData(String(event.first));
  }

  onHide() {
    this.subscriptions && this.subscriptions.unsubscribe();
    this.pltTableSubscription && this.pltTableSubscription.unsubscribe();
    this.pltProjectSubscription && this.pltProjectSubscription.unsubscribe();
    this.pltProjectSubscription && this.pltProjectSubscription.unsubscribe();
    this.searchWorkspace = false;
    this.selectedWorkspace = null;
    this._filter = {};
    this.browesing = false;
    this.onVisibleChange.emit(false);
    this.tableInputs = {
      
      ...this.tableInputs,
      filterInput: '',
      pltColumns: [
        {
          width: '60',
          filtred: false,
          icon: null,
          type: 'checkbox', active: true
        },
        {
          fields: '',
          header: 'User Tags',
          width: '60',
          sorted: false,
          filtred: false,
          icon: null,
          type: 'tags',
          active: true
        },
        {
          fields: 'pltId',
          header: 'PLT ID',
          width: '80',
          sorted: false,
          filtred: true,
          icon: null,
          type: 'id',
          active: true
        },
        {
          fields: 'pltName',
          header: 'PLT Name',
          width: '160',
          sorted: false,
          filtred: true,
          icon: null,
          type: 'field', active: true
        },
        {
          fields: 'peril',
          header: 'Peril',
          width: '40',
          sorted: false,
          filtred: false,
          icon: null,
          type: 'field',
          textAlign: 'center', active: true
        },
        {
          fields: 'regionPerilCode',
          header: 'Region Peril Code',
          width: '70',
          sorted: false,
          filtred: false,
          icon: null,
          type: 'field', active: true
        },
        {
          fields: 'regionPerilName',
          header: 'Region Peril Name',
          width: '160',
          sorted: false,
          filtred: false,
          icon: null,
          type: 'field', active: true
        },
        {
          sortDir: 1,
          fields: 'grain',
          header: 'Grain',
          width: '90',
          sorted: false,
          filtred: false,
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: 'vendorSystem',
          header: 'Vendor System',
          width: '90',
          sorted: false,
          filtred: false,
          icon: null,
          type: 'field', active: true
        },
        {
          sortDir: 1,
          fields: 'rap',
          header: 'RAP',
          width: '52',
          sorted: false,
          filtred: false,
          icon: null,
          type: 'field',
          active: true
        }
      ],
      listOfPltsData: [],
      listOfDeletedPltsData: [],
      listOfPltsCache: [],
      listOfDeletedPltsCache: [],
      selectedListOfPlts: [],
      selectedListOfDeletedPlts: [],
      selectAll: false,
      someItemsAreSelected: false,
      showDeleted: false,
      filterData: {},
      filters: {
        systemTag: [],
        userTag: []
      },
      sortData: {}
    };
  }

  onSave() {
    // this.onSaveEmitter.emit(this.Inputs.selectedListOfPlts);
    /* _.forEach(plts, pltId => {
      this.listOfPltsData[pltId].toCalibrate = false;
    });
     console.log(this.listOfPltsData);
     this.store$.dispatch(new toCalibratePlts( {
       plts:this.listOfPltsData,
       wsIdentifier:this.workspaceId + "-" + this.uwy
     }))*/
    const result: any = {};
    _.forEach(this.listOfPltsThread, (plt: any) => {
      result[plt.pltId] = {toCalibrate: false};
    })
    _.forEach(this.tableInputs.selectedListOfPlts, pltId => {
      result[pltId].toCalibrate = true;
    })
    console.log(result);
    this.store$.dispatch(new toCalibratePlts({
      plts: result,
      wsIdentifier: this.stepConfig.wsId + "-" + this.stepConfig.uwYear
    }))
    this.onHide();
  }

  onRowSelect(event) {
    console.log(event);
    this.selectedWorkspace = event;
    this.onSelectWorkspace.emit(event);
    this.updateTable('wsId', event.workSpaceId)
  }


  onShow() {

    if (this.stepConfig.uwYear && this.stepConfig.wsId) {
      this.updateTableAndLeftMenuInputs('wsId', this.stepConfig.wsId);
      this.updateTableAndLeftMenuInputs('uwYear', this.stepConfig.uwYear);
      this.getBrowesingItemsDirectly();
    } else {
      this._loadData();
    }


  }

  onEnter() {
    this._loadData();
  }

  search() {
    this._loadData();
  }

  clearSearchValue() {
    this.keywordFormGroup.get('keyword').setValue(null);
    this._loadData();
  }

  goToSearchWorkspace() {
    this._filter = {};
    this._loadData();
    this.browesing = false;
  }

  checkAll($event) {
    this.calibrateSelectPlts(
      _.zipObject(
        _.map(!$event ? this.getTableInputKey('listOfPlts') : this.getTableInputKey('listOfDeletedPlts'), plt => plt),
        _.range(!$event ? this.getTableInputKey('listOfPlts').length : this.getTableInputKey('listOfDeletedPlts').length).map(el => ({type: !this.getTableInputKey('selectAll') && !this.getTableInputKey('someItemsAreSelected')}))
      )
    );
  }

  calibrateSelectPlts(plts: any) {
    console.log('selected   ************************==> ', this.tableInputs.selectedListOfPlts);
    this.store$.dispatch(new fromWorkspaceStore.calibrateSelectPlts({
      plts: plts,
      ws: this.workspaceId + "-" + this.uwy
    }));
  }

  selectSinglePLT($event) {
    this.calibrateSelectPlts($event);
  }

  filterPlts(filterData) {
    console.log(filterData);
    this.updateTable('filterData', filterData);
  }

  setFilters($event) {
    console.log($event);
    this.updateTable('filters', $event)
  }

  setUserTags($event) {
    console.log($event)
    this.updateTable('userTags', $event)
  }

  emitFilters(filters: any) {
    console.log(filters);
    this.store$.dispatch(new fromWorkspaceStore.setUserTagsFiltersFromCalibration({
      wsIdentifier: this.getTableInputKey('wsId') + '-' + this.getTableInputKey('uwYear'),
      filters: filters
    }))
  }

  setWsHeaderSelect($event: any) {
    this.updateTable('wsHeaderSelected', $event);
  }

  unCheckAll() {
    console.log('uncheck');
    this.calibrateSelectPlts(
      _.zipObject(
        _.map([...this.getTableInputKey('listOfPlts'), ...this.getTableInputKey('listOfDeletedPlts')], plt => plt),
        _.range(this.getTableInputKey('listOfPlts').length + this.getTableInputKey('listOfDeletedPlts').length).map(el => ({type: false}))
      )
    );
  }

  setSelectedProjects($event) {
    this.updateTable('projects', $event);
  }

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
  }

  clear() {
    // this.messageService.clear();
  }

  closePltInDrawer(pltId) {
    this.store$.dispatch(new fromWorkspaceStore.ClosePLTinDrawer({
      wsIdentifier: this.getTableInputKey('wsId') + '-' + this.getTableInputKey('uwYear'),
      pltId
    }));
  }

  openPltInDrawer(plt) {
    if (this.getRightMenuKey('pltDetail')) {
      this.closePltInDrawer(this.getRightMenuKey('pltDetail').pltId)
    }
    this.store$.dispatch(new fromWorkspaceStore.OpenPLTinDrawer({
      wsIdentifier: this.getTableInputKey('wsId') + '-' + this.getTableInputKey('uwYear'),
      pltId: plt
    }));
    this.updateMenuKey('pltDetail', _.find(this.getTableInputKey('listOfPltsData'), e => e.pltId == plt))
    this.updateMenuKey('visible', true);
  }

  rightMenuActionDispatcher(action: Message) {
    console.log(action);
    switch (action.type) {
      case rightMenuStore.closeDrawer:
        if (this.getRightMenuKey('pltDetail')) {
          this.closePltInDrawer(this.getRightMenuKey('pltDetail').pltId);
        }
        this.updateMenuKey('visible', false);
        break;
      case rightMenuStore.openDrawer:
        this.updateMenuKey('visible', true);
        break;
      case  rightMenuStore.unselectPlt:
        this.calibrateSelectPlts(
          _.zipObject(
            _.map([action.payload], plt => plt),
            _.range([action.payload].length).map(el => ({type: false}))
          )
        );
        break;
      default:
        console.log('default right menu action');
    }
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

  getTableInputKey(key) {
    return _.get(this.tableInputs, key);
  }

  setSelectedPlt($event: any) {
    this.selectedPlt = $event;
  }

  private _loadData(offset = '0', size = '100') {
    this.loading = true;
    const keyword = this.keywordFormGroup.get('keyword').value === '' ? null : this.keywordFormGroup.get('keyword').value
    const params = {
      keyword,
      filter: this.filter,
      offset,
      size
    };
    this.searchService.expertModeSearch(params)
      .subscribe((data: any) => {
        this.contracts = data.content.map(item => ({...item, selected: false}));
        this.loading = false;
        this.paginationOption = {
          ...this.paginationOption,
          page: data.number,
          size: data.numberOfElements,
          total: data.totalElements
        };
        this.detectChanges();
      });
  }

  handlePLTClick(pltId, i: number, $event: MouseEvent) {
    const isSelected = _.findIndex(!this.tableInputs.showDeleted ? this.tableInputs.selectedListOfPlts : this.tableInputs.selectedListOfDeletedPlts, el => el == pltId) >= 0;
    if ($event.ctrlKey || $event.shiftKey) {
      this.lastClick = "withKey";
      this.handlePLTClickWithKey(pltId, i, !isSelected, $event);
    } else {
      this.lastSelectedId = i;
      this.calibrateSelectPlts(
        _.zipObject(
          _.map(!this.tableInputs.showDeleted ? this.tableInputs.listOfPltsData : this.tableInputs.listOfDeletedPltsData, plt => plt),
          _.map(!this.tableInputs.showDeleted ? this.tableInputs.listOfPltsData : this.tableInputs.listOfDeletedPltsData, plt => ({type: plt == pltId && (this.lastClick == 'withKey' || !isSelected)}))
        )
      );
      this.lastClick = null;
    }
  }

  private handlePLTClickWithKey(pltId: number, i: number, isSelected: boolean, $event: MouseEvent) {
    if ($event.ctrlKey) {
      this.selectSinglePLT(pltId);
      this.lastSelectedId = i;
      return;
    }

    if ($event.shiftKey) {
      console.log(i, this.lastSelectedId);
      if (!this.lastSelectedId) this.lastSelectedId = 0;
      if (this.lastSelectedId || this.lastSelectedId == 0) {
        const max = _.max([i, this.lastSelectedId]);
        const min = _.min([i, this.lastSelectedId]);
        this.calibrateSelectPlts(
          _.zipObject(
            _.map(!this.tableInputs.showDeleted ? this.tableInputs.listOfPltsData : this.tableInputs.listOfDeletedPltsData, plt => plt),
            _.map(!this.tableInputs.showDeleted ? this.tableInputs.listOfPltsData : this.tableInputs.listOfDeletedPltsData, (plt, i) => ({type: i <= max && i >= min})),
          )
        )
      } else {
        this.lastSelectedId = i;
      }
      return;
    }
  }

  leftMenuActionDispatcher(action: Message) {
    console.log(action);

    switch (action.type) {

      case leftMenuStore.unCkeckAllPlts:
        this.unCheckAll();
        break;
      case leftMenuStore.emitFilters:
        this.emitFilters(action.payload);
        break;
      case leftMenuStore.setFilters:
        this.setFilters(action.payload);
        break;
      case leftMenuStore.headerSelection:
        this.setWsHeaderSelect(action.payload);
        break;
      case leftMenuStore.onSelectProjects:
        this.setSelectedProjects(action.payload);
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
    this.updateTable(key,value);
  }

  addNewTag(tag) {
    /*this.updateTagsInput('toAssign', _.concat(this.leftMenuInput.toAssign, tag));
    this.updateTagsInput('assignedTags', _.concat(this.leftMenuInput.assignedTags, tag));*/
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
    this.dispatch(new fromWorkspaceStore.AssignPltsToTag({
      userId: 1,
      wsId: this.workspaceId,
      uwYear: this.uwy,
      selectedTags: this.tagsInputs.toAssign,
      unselectedTags: _.differenceBy(this.tagsInputs.assignedTagsCache, this.tagsInputs.assignedTags, 'tagId')
    }));
    this.tagsInputs = {
      ...this.tagsInputs,
      _tagModalVisible: false,
      assignedTags: [],
      assignedTagsCache: [],
      usedInWs: [],
      allTags: [],
      suggested: [],
      selectedTags: {},
      operation: null
    };
  }

  updateLeftMenuInputs(key, value) {
    this.leftMenuInputs= {...this.leftMenuInputs, [key]: value};
  }

  updateTable(key: string, value: any) {
    this.tableInputs = tableActions.updateKey.handler(this.tableInputs, key, value);
  }

  updateTableAndLeftMenuInputs(key, value) {
    this.updateLeftMenuInputs(key, value);
    this.updateTable(key,value);
  }

}
