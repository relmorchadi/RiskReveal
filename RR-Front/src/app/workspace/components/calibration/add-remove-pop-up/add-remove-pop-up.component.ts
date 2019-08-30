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

@Component({
  selector: 'app-add-remove-pop-up',
  templateUrl: './add-remove-pop-up.component.html',
  styleUrls: ['./add-remove-pop-up.component.scss']
})
export class AddRemovePopUpComponent extends BaseContainer implements OnInit, OnDestroy {

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
  @Input('userTags') userTags: string;
  @Input('projects') projects: string;
  @Input() multiSteps: boolean;
  @Input() workspaceId: any;
  @Input() uwy: any;
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
  Inputs: {
    contextMenuItems: any,
    filterInput: string;
    pltColumns: any[];
    listOfPltsData: [];
    listOfDeletedPltsData: [];
    listOfPltsCache: [];
    listOfDeletedPltsCache: [];
    listOfPlts: [];
    listOfDeletedPlts: [];
    selectedListOfPlts: any;
    selectedListOfDeletedPlts: any;
    selectAll: boolean;
    someItemsAreSelected: boolean;
    showDeleted: boolean;
    filterData: any;
    filters: {
      systemTag: any,
      userTag: []
    };
    sortData: any;
    _tagModalVisible: boolean;
    _modalSelect: [];
    tagForMenu: any;
    _editingTag: boolean;
    wsId: string;
    uwYear: string;
    projects: any[];
    addTagModalIndex: number;
    fromPlts: boolean;
    deletedPltsLength: number;
    userTags: any[];
    systemTagsCount: any;
    wsHeaderSelected: boolean;
    pathTab: boolean;
    selectedItemForMenu: any;
  };
  tableInputs = ['filterInput', 'pltColumns', 'listOfPltsData', 'listOfDeletedPltsData', 'listOfPltsCache', 'listOfDeletedPltsCache', 'listOfPlts', 'listOfDeletedPlts', 'selectedListOfPlts', 'selectedListOfDeletedPlts', 'selectAll', 'someItemsAreSelected', 'showDeleted', 'filterData', 'filters', 'sortData', 'contextMenuItems'];
  menuInputs = ['_tagModalVisible', '_modalSelect', 'tagForMenu', '_editingTag', 'wsId', 'uwYear', 'projects', 'showDeleted', 'filterData', 'filters', 'addTagModalIndex', 'fromPlts', 'deletedPltsLength', 'userTags', 'selectedListOfPlts', 'systemTagsCount', 'wsHeaderSelected', 'pathTab', 'selectedItemForMenu'];
  systemTagsMapping = {
    grouped: {
      peril: 'Peril',
      regionDesc: 'Region',
      currency: 'Currency',
      sourceModellingVendor: 'Modelling Vendor',
      sourceModellingSystem: 'Modelling System',
      targetRapCode: 'Target RAP',
      userOccurrenceBasis: 'User Occurence Basis',
      xActAvailable: 'Published To Pricing',
      xActUsed: 'Priced',
      accumulated: 'Accumulated',
      financial: 'Financial Perspectives'
    },
    nonGrouped: {}
  };
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
    this.Inputs = {
      contextMenuItems: [
        {
          label: 'View Detail', command: (event) => {
            this.openPltInDrawer(this.selectedPlt)
          }
        },
      ],
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
      listOfPlts: [],
      listOfDeletedPlts: [],
      selectedListOfPlts: [],
      selectedListOfDeletedPlts: [],
      selectAll: false,
      someItemsAreSelected: false,
      showDeleted: false,
      filterData: {},
      filters: {
        systemTag: {},
        userTag: []
      },
      sortData: {},
      _tagModalVisible: false,
      _modalSelect: [],
      tagForMenu: {},
      _editingTag: false,
      wsId: '',
      uwYear: '',
      projects: [],
      addTagModalIndex: 0,
      fromPlts: false,
      deletedPltsLength: 0,
      userTags: [],
      systemTagsCount: {},
      wsHeaderSelected: true,
      pathTab: true,
      selectedItemForMenu: null
    };
    this.unSubscribe$ = new Subject<void>();
    this.keywordFormGroup = new FormGroup({
      keyword: new FormControl(null)
    });
    this.browesing = false;
    this.rightMenuInputs = {
      basket: [],
      pltDetail: null,
      selectedTab: {
        index: 0,
        title: 'basket',
      },
      tabs: {'basket': true, 'pltDetail': true},
      visible: false,
      mode: "pop-up"
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
    this.setInputs('userTags', this.userTags);
    // this.setInputs('projects',this.projects);
  }

  getPlts() {
    return this.select(WorkspaceState.getPltsForCalibration(this.workspaceId + '-' + this.uwy));
  }
  getBrowesingItemsDirectly() {

    this.observeRouteParamsWithSelector(() => this.getPlts()).subscribe((data) => {
      this.setInputs('systemTagsCount', this.systemTagService.countSystemTags(data));
      this.setInputs('listOfPltsCache', _.map(data, (v, k) => ({...v, pltId: k})));
      this.setInputs('listOfPltsData', [...this.Inputs.listOfPltsCache]);
      this.initThreadsData();
      this.updateMenuKey('basket', _.filter(this.listOfPltsThread, row => row.calibrate));
      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getPlts()).subscribe(data => {
      this.setInputs('selectAll', this.Inputs.selectedListOfPlts.length > 0 || (this.Inputs.selectedListOfPlts.length == this.listOfPltsThread.length) && this.listOfPltsThread.length > 0);

      this.setInputs('someItemsAreSelected', this.Inputs.selectedListOfPlts.length < this.listOfPltsThread.length && this.Inputs.selectedListOfPlts.length > 0);
      this.detectChanges();
    });

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
    if (this.Inputs.listOfPltsData) {
      this.listOfPltsThread = Array.prototype.concat.apply([], this.Inputs.listOfPltsData.map((row: any) => row.threads));
      this.setInputs('selectedListOfPlts', _.filter(this.listOfPltsThread, (v, k) => v.calibrate).map(e => e.pltId));
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
    this.Inputs = {
      ...this.Inputs,
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
      listOfPlts: [],
      listOfDeletedPlts: [],
      selectedListOfPlts: [],
      selectedListOfDeletedPlts: [],
      selectAll: false,
      someItemsAreSelected: false,
      showDeleted: false,
      filterData: {},
      filters: {
        systemTag: {},
        userTag: []
      },
      sortData: {},
      _tagModalVisible: false,
      _modalSelect: [],
      tagForMenu: {},
      _editingTag: false,
      wsId: '',
      uwYear: '',
      projects: [],
      addTagModalIndex: 0,
      fromPlts: false,
      deletedPltsLength: 0,
      userTags: [],
      systemTagsCount: {},
      wsHeaderSelected: true,
      pathTab: true,
      selectedItemForMenu: null
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
    _.forEach(this.Inputs.selectedListOfPlts, pltId => {
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
    this.setInputs('wsId', event.workSpaceId)
  }


  onShow() {

    if (this.stepConfig.uwYear && this.stepConfig.wsId) {
      this.setInputs('wsId', this.stepConfig.wsId);
      this.setInputs('uwYear', this.stepConfig.uwYear);
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
        _.map(!$event ? this.getInputs('listOfPlts') : this.getInputs('listOfDeletedPlts'), plt => plt),
        _.range(!$event ? this.getInputs('listOfPlts').length : this.getInputs('listOfDeletedPlts').length).map(el => ({type: !this.getInputs('selectAll') && !this.getInputs('someItemsAreSelected')}))
      )
    );
  }

  calibrateSelectPlts(plts: any) {
    console.log('selected   ************************==> ', this.Inputs.selectedListOfPlts);
    this.store$.dispatch(new fromWorkspaceStore.calibrateSelectPlts({plts: plts}));
  }

  selectSinglePLT($event) {
    this.calibrateSelectPlts($event);
  }

  filterPlts(filterData) {
    console.log(filterData);
    this.setInputs('filterData', filterData);
  }

  getInputs(key) {
    return this.Inputs[key];
  }

  setInputs(key, value) {
    this.Inputs = {...this.Inputs, [key]: value};
  }

  setFilters($event) {
    console.log($event);
    this.setInputs('filters', $event)
  }

  setUserTags($event) {
    console.log($event)
    this.setInputs('userTags', $event)
  }

  emitFilters(filters: any) {
    console.log(filters);
    this.store$.dispatch(new fromWorkspaceStore.setUserTagsFiltersFromCalibration({
      wsIdentifier: this.getInputs('wsId') + '-' + this.getInputs('uwYear'),
      filters: filters
    }))
  }

  setWsHeaderSelect($event: any) {
    this.setInputs('wsHeaderSelected', $event);
  }

  unCheckAll() {
    console.log('uncheck');
    this.calibrateSelectPlts(
      _.zipObject(
        _.map([...this.getInputs('listOfPlts'), ...this.getInputs('listOfDeletedPlts')], plt => plt),
        _.range(this.getInputs('listOfPlts').length + this.getInputs('listOfDeletedPlts').length).map(el => ({type: false}))
      )
    );
  }

  setSelectedProjects($event) {
    this.setInputs('projects', $event);
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
      wsIdentifier: this.getInputs('wsId') + '-' + this.getInputs('uwYear'),
      pltId
    }));
  }

  openPltInDrawer(plt) {
    if (this.getRightMenuKey('pltDetail')) {
      this.closePltInDrawer(this.getRightMenuKey('pltDetail').pltId)
    }
    this.store$.dispatch(new fromWorkspaceStore.OpenPLTinDrawer({
      wsIdentifier: this.getInputs('wsId') + '-' + this.getInputs('uwYear'),
      pltId: plt
    }));
    this.updateMenuKey('pltDetail', _.find(this.getInputs('listOfPltsData'), e => e.pltId == plt))
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
    const isSelected = _.findIndex(!this.Inputs.showDeleted ? this.Inputs.selectedListOfPlts : this.Inputs.selectedListOfDeletedPlts, el => el == pltId) >= 0;
    if ($event.ctrlKey || $event.shiftKey) {
      this.lastClick = "withKey";
      this.handlePLTClickWithKey(pltId, i, !isSelected, $event);
    } else {
      this.lastSelectedId = i;
      this.calibrateSelectPlts(
        _.zipObject(
          _.map(!this.Inputs.showDeleted ? this.Inputs.listOfPlts : this.Inputs.listOfDeletedPlts, plt => plt),
          _.map(!this.Inputs.showDeleted ? this.Inputs.listOfPlts : this.Inputs.listOfDeletedPlts, plt => ({type: plt == pltId && (this.lastClick == 'withKey' || !isSelected)}))
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
            _.map(!this.Inputs.showDeleted ? this.Inputs.listOfPlts : this.Inputs.listOfDeletedPlts, plt => plt),
            _.map(!this.Inputs.showDeleted ? this.Inputs.listOfPlts : this.Inputs.listOfDeletedPlts, (plt, i) => ({type: i <= max && i >= min})),
          )
        )
      } else {
        this.lastSelectedId = i;
      }
      return;
    }
  }

}
