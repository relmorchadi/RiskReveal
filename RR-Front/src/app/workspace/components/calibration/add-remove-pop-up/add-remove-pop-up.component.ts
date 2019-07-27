import {ChangeDetectorRef, Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {combineLatest, Observable, of, Subject, Subscription} from "rxjs";
import {Actions, ofActionDispatched, Select, Store} from "@ngxs/store";
import * as rightMenuStore from "../../../../shared/components/plt/plt-right-menu/store";
import {Actions as rightMenuActions} from "../../../../shared/components/plt/plt-right-menu/store";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {NotificationService} from "../../../../shared/notification.service";
import {SearchService} from "../../../../core/service";
import {WorkspaceState} from "../../../store/states";
import * as fromWorkspaceStore from "../../../store";
import * as _ from "lodash";
import {mergeMap} from "rxjs/operators";
import {Debounce} from "../../../../shared/decorators";
import {LazyLoadEvent} from "primeng/api";
import {Message} from "../../../../shared/message";

@Component({
  selector: 'app-add-remove-pop-up',
  templateUrl: './add-remove-pop-up.component.html',
  styleUrls: ['./add-remove-pop-up.component.scss']
})
export class AddRemovePopUpComponent implements OnInit, OnDestroy {

  data$: Observable<any>;
  deletedPlts$: Observable<any>;
  subscriptions: Subscription;
  unSubscribe$: Subject<void>;
  @Output('onVisibleChange') onVisibleChange: EventEmitter<any> = new EventEmitter();
  @Output('onSelectProjectNext') onSelectProjectNext: EventEmitter<any> = new EventEmitter();
  @Output('onSelectItems') onSelectItems: EventEmitter<any> = new EventEmitter();
  @Output('onSelectWorkspace') onSelectWorkspace: EventEmitter<any> = new EventEmitter();
  @Input() isVisible;
  @Input('selectionStep') selectionStep: string;
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
  ) {
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
      visible: true,
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

  }

  getBrowesingItemsDirectly() {
    this.browesing = true;
    this.data$ = this.store$.select(WorkspaceState.getPltsForPlts(this.getInputs('wsId') + '-' + this.getInputs('uwYear')));
    this.deletedPlts$ = this.store$.select(WorkspaceState.getDeletedPltsForPlt(this.getInputs('wsId') + '-' + this.getInputs('uwYear')));

    this.pltTableSubscription = combineLatest(
      this.data$,
      this.deletedPlts$
    ).subscribe(([data, deletedData]: any) => {
      let d1 = [];
      let dd1 = [];
      let d2 = [];
      let dd2 = [];
      this.Inputs['systemTagsCount'] = {};

      if (data) {
        if (_.keys(this.Inputs['systemTagsCount']).length == 0) {
          _.forEach(data, (v, k) => {
            //Init Tags Counters

            //Grouped Sys Tags
            _.forEach(this.systemTagsMapping.grouped, (sectionName, section) => {
              this.Inputs['systemTagsCount'][sectionName] = this.Inputs['systemTagsCount'][sectionName] || {};
              const tag = _.toString(v[section]);
              if (tag) {
                this.Inputs['systemTagsCount'][sectionName][tag] = {selected: false, count: 0, max: 0}
              }
            });

            //NONE grouped Sys Tags
            _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
              this.Inputs['systemTagsCount'][sectionName] = this.Inputs['systemTagsCount'][sectionName] || {};
              this.Inputs['systemTagsCount'][sectionName][section] = {selected: false, count: 0};
              this.Inputs['systemTagsCount'][sectionName]['non-' + section] = {selected: false, count: 0, max: 0};
            });

          });
        }

        _.forEach(data, (v, k) => {
          d1.push({...v, pltId: k});
          d2.push(k);

          /*if (v.visible) {*/
          //Grouped Sys Tags
          _.forEach(this.systemTagsMapping.grouped, (sectionName, section) => {
            const tag = _.toString(v[section]);
            if (tag) {
              if (this.Inputs['systemTagsCount'][sectionName][tag] || this.Inputs['systemTagsCount'][sectionName][tag].count === 0) {
                const {
                  count,
                  max
                } = this.Inputs['systemTagsCount'][sectionName][tag];

                this.Inputs['systemTagsCount'][sectionName][tag] = {
                  ...this.Inputs['systemTagsCount'][sectionName][tag],
                  count: v.visible ? count + 1 : count,
                  max: max + 1
                };
              }
            }
          })

          //NONE grouped Sys Tags
          _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
            const tag = v[section];
            if (this.Inputs['systemTagsCount'][sectionName][section] || this.Inputs['systemTagsCount'][sectionName][section] == 0) {
              const {
                max,
                count
              } = this.Inputs['systemTagsCount'][sectionName][section];
              this.Inputs['systemTagsCount'][sectionName][section] = {
                ...this.Inputs['systemTagsCount'][sectionName][section],
                count: v.visible ? count + 1 : count,
                max: max + 1
              };
            }
            if (this.Inputs['systemTagsCount'][sectionName]['non-' + section] || this.Inputs['systemTagsCount'][sectionName]['non-' + section].count == 0) {
              const {
                count,
                max
              } = this.Inputs['systemTagsCount'][sectionName]['non-' + section];
              this.Inputs['systemTagsCount'][sectionName]['non-' + section] = {
                ...this.Inputs['systemTagsCount'][sectionName]['non-' + section],
                count: v.visible ? count + 1 : count,
                max: max + 1
              };
            }
          })
          /*}*/

        });

        this.setInputs('listOfPlts', d2);
        this.setInputs('listOfPltsData', d1);
        this.setInputs('listOfPltsCache', d1);
        this.setInputs('selectedListOfPlts', []);
        this.updateMenuKey('basket', []);

        _.forEach(d2, k => {
          if (data[k].calibrate) {
            this.setInputs('selectedListOfPlts', _.concat(this.getInputs('selectedListOfPlts'), k));
            this.updateMenuKey('basket', _.concat(this.getRightMenuKey('basket'), {
              pltId: k,
              ...data[k]
            }));
          }
        })

        console.log(this.getRightMenuKey('basket'), this.getInputs('selectedListOfPlts'))
      }

      if (deletedData) {
        _.forEach(deletedData, (v, k) => {
          dd1.push({...v, pltId: k});
          dd2.push(k);
        });

        this.setInputs('listOfDeletedPlts', dd2);
        this.setInputs('listOfDeletedPltsData', dd1);
        this.setInputs('listOfDeletedPltsCache', dd1);
        this.setInputs('selectedListOfDeletedPlts', _.filter(dd2, k => deletedData[k].selected))
      }

      this.setInputs(
        'selectAll',
        !this.getInputs('showDeleted')
          ?
          (this.getInputs('selectedListOfPlts').length > 0 || (this.getInputs('selectedListOfPlts').length == this.getInputs('listOfPlts').length)) && this.getInputs('listOfPltsData').length > 0
          :
          (this.getInputs('selectedListOfDeletedPlts').length > 0 || (this.getInputs('selectedListOfDeletedPlts').length == this.getInputs('listOfDeletedPlts').length)) && this.getInputs('listOfDeletedPltsData').length > 0
      );

      this.setInputs(
        'someItemsAreSelected',
        !this.getInputs('showDeleted') ?
          this.getInputs('selectedListOfPlts').length < this.getInputs('listOfPlts').length && this.getInputs('selectedListOfPlts').length > 0
          :
          this.getInputs('selectedListOfDeletedPlts').length < this.getInputs('listOfDeletedPlts').length && this.getInputs('selectedListOfDeletedPlts').length > 0
      );
      console.log('END');
      this.detectChanges();
    });

    this.d = this.actions$.pipe(
      ofActionDispatched(fromWorkspaceStore.loadAllPltsSuccess),
      mergeMap(() => {
        this.calibrateSelectPlts(_.zipObject(
          _.map(_.map(this.stepConfig.plts, id => _.find(this.getInputs('listOfPltsData'), plt => id == plt.pltId)), plt => plt.pltId),
          _.map(this.stepConfig.plts, plt => ({type: true}))
        ));
        return of(null);
      })
    ).subscribe(() => this.d.unsubscribe());

    this.pltProjectSubscription = this.store$.select(WorkspaceState.getProjects).subscribe((projects: any) => {
      this.setInputs('projects', _.map(projects, p => ({...p, selected: false})));
      this.detectChanges();
    });

    this.pltUserTagsSubscription = this.store$.select(WorkspaceState.getUserTagsPlt).subscribe(userTags => {
      this.setInputs('userTags', userTags || {});
      console.log(this.getInputs('userTags'))
      this.detectChanges();
    });
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
    this.store$.dispatch(new fromWorkspaceStore.calibrateSelectPlts({
      wsIdentifier: this.getInputs('wsId') + '-' + this.getInputs('uwYear'),
      plts,
      forDeleted: this.getInputs('showDeleted')
    }));
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
