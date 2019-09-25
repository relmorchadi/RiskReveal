import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
  ViewEncapsulation
} from '@angular/core';
import {BaseContainer} from "../../../base";
import {Actions, ofActionDispatched, Store} from "@ngxs/store";
import {Router} from "@angular/router";
import * as fromWorkspaceStore from "../../../../workspace/store";
import {WorkspaceState} from "../../../../workspace/store";
import * as _ from "lodash";
import * as rightMenuStore from "../plt-right-menu/store";
import {Actions as rightMenuActions} from "../plt-right-menu/store";
import {debounceTime, mergeMap} from "rxjs/operators";
import {of} from "rxjs";
import {FormControl, FormGroup} from "@angular/forms";
import {SystemTagsService} from "../../../services/system-tags.service";
import {SearchService} from "../../../../core/service";
import {Debounce} from "../../../decorators";
import {LazyLoadEvent} from "primeng/api";
import * as tableStore from "../plt-main-table/store";
import {Actions as tableActions} from "../plt-main-table/store";
import {Message} from "../../../message";
import * as leftMenuStore from "../plt-left-menu/store";
import * as tagsStore from "../plt-tag-manager/store";
import {ResizedEvent} from "angular-resize-event";

@Component({
  selector: 'app-plt-selection-pop-up',
  templateUrl: './plt-selection-pop-up.component.html',
  styleUrls: ['./plt-selection-pop-up.component.scss']
})
export class PltSelectionPopUpComponent extends BaseContainer implements OnInit {

  leftMenuInputs: leftMenuStore.Input;
  collapsedTags: boolean= true;
  tagsInputs: tagsStore.Input;
  tableInputs: tableStore.Input;
  @ViewChild('leftMenu') leftMenu: any;

  @Input() isVisible;
  @Input() stepConfig: {
    wsId: string,
    uwYear: string,
    plts: any[]
  };
  @Input() multiSteps: boolean;

  @Output('onVisibleChange') onVisibleChange: EventEmitter<any> = new EventEmitter();
  @Output('onSelectWorkspace') onSelectWorkspace: EventEmitter<any> = new EventEmitter();
  @Output('onSelectItems') onSelectItems: EventEmitter<any> = new EventEmitter();

  keywordFormGroup: FormGroup;
  browesing: boolean;
  selectedPlt: string;
  searchWorkspace = false;
  selectedWorkspace: any = null;
  _filter = {};
  loading;
  paginationOption = {currentPage: 0, page: 0, size: 40, total: '-'};
  contracts = [];

  selectWsTable = [
    {
      field: 'countryName',
      header: 'Country',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'countryName'
    },
    {
      field: 'cedantName',
      header: 'Cedent Name',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'cedantName'
    },
    {
      field: 'cedantCode',
      header: 'Cedant Code',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'cedantCode'
    },
    {
      field: 'uwYear',
      header: 'Uw Year',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'year'
    },
    {
      field: 'workspaceName',
      header: 'Workspace Name',
      width: '160px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'workspaceName'
    },
    {
      field: 'workSpaceId',
      header: 'Workspace Context',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'workspaceId'
    }
  ];
  
  d: any;

  rightMenuInputs: rightMenuStore.Input;

  constructor(_baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,
              private systemTagService: SystemTagsService,
              private searchService: SearchService,
              private actions$: Actions,
              ) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.rightMenuInputs = {
      basket: [],
      pltDetail: null,
      selectedTab: {
        index: 0,
        title: 'basket',
      },
      tabs: {'basket': true,'pltDetail': true},
      visible: false,
      mode: "pop-up"
    };
    this.tableInputs= {
      dataKey: 'pltId',
      scrollConfig: {
        containerHeight: null,
        containerGap: '62px'
      },
      contextMenuItems: [
        {
          label: 'View Detail', command: (event) => {
            this.openPltInDrawer(this.selectedPlt);
          }
        },
      ],
      filterInput: '',
      pltColumns: [{
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
        }],
      listOfPltsData: [],
      listOfDeletedPltsData: [],
      listOfPltsCache: [],
      listOfDeletedPltsCache: [],
      selectedListOfPlts: [],
      selectedListOfDeletedPlts: [],
      selectAll: false,
      selectAllDeletedPlts: false,
      someItemsAreSelected: false,
      someDeletedItemsAreSelected: false,
      showDeleted: false,
      filterData: {},
      filters: {
        systemTag: {},
        userTag: []
      },
      sortData: {},
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
      },
      openedPlt: "",
    };
    this.leftMenuInputs= {
      wsId: '',
      uwYear: 0,
      projects: [],
      showDeleted: this.tableInputs['showDeleted'],
      filterData: this.tableInputs['filterData'],
      filters: this.tableInputs['filters'],
      deletedPltsLength: 0,
      userTags: [],
      selectedListOfPlts: this.tableInputs['selectedListOfPlts'],
      systemTagsCount: {},
      wsHeaderSelected: true,
      pathTab: true,
    };
  }

  getPlts() {
    return this.select(WorkspaceState.getPltsForPlts(this.getInputs('wsId') + '-' + this.getInputs('uwYear')));
  }

  getDeletedPlts() {
    return this.select(WorkspaceState.getDeletedPltsForPlt(this.getInputs('wsId') + '-' + this.getInputs('uwYear')));
  }

  getUserTags() {
    return this.select(WorkspaceState.getUserTagsPlt(this.getInputs('wsId') + '-' + this.getInputs('uwYear')));
  }

  getProjects() {
    return this.select(WorkspaceState.getProjectsPlt(this.getInputs('wsId') + '-' + this.getInputs('uwYear')));
  }

  getOpenedPlt() {
    return this.select(WorkspaceState.getOpenedPlt(this.getInputs('wsId') + '-' + this.getInputs('uwYear')));
  }

  ngOnInit() {
    this.keywordFormGroup = new FormGroup({
      keyword: new FormControl(null)
    });
  }

  onHide() {
    this.onVisibleChange.emit(false);
    this.destroy();
    this.tableInputs= {
      dataKey: 'pltId',
      scrollConfig: {
        containerHeight: null,
        containerGap: '106px'
      },
      contextMenuItems: [
        {
          label: 'View Detail', command: (event) => {
            this.openPltInDrawer(this.selectedPlt);
          }
        },
      ],
      filterInput: '',
      pltColumns: [
        {
          sortDir: 1,
          fields: '',
          header: '',
          sorted: false,
          filtred: false,
          resizable: false,
          width: '40',
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
          resizable: false,
          width: '24%',
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
          width: '28%',
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
          width: '100%',
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
          width: '22%',
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
          width: '35%',
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
          width: '60%',
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
          width: '70%',
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
          width: '25%',
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: '',
          header: '',
          sorted: false,
          filtred: false,
          icon: 'icon-note',
          type: 'icon',
          width: '50px',
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
          width: '50px',
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
          width: '50px',
          active: true,
          tooltip: "Published for Accumulation",
          highlight: 'Accumulated'
        },
      ],
      listOfPltsData: [],
      listOfDeletedPltsData: [],
      listOfPltsCache: [],
      listOfDeletedPltsCache: [],
      selectedListOfPlts: [],
      selectedListOfDeletedPlts: [],
      selectAll: false,
      selectAllDeletedPlts: false,
      someItemsAreSelected: false,
      someDeletedItemsAreSelected: false,
      showDeleted: false,
      filterData: {},
      filters: {
        systemTag: {},
        userTag: []
      },
      sortData: {},
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
      },
      openedPlt: "",
    };
    this.searchWorkspace = false;
    this.selectedWorkspace = null;
    this._filter = {};
    this.browesing = false;
    this.rightMenuInputs = {
      basket: [],
      pltDetail: null,
      selectedTab: {
        index: 0,
        title: 'basket',
      },
      tabs: {'basket': true,'pltDetail': true},
      visible: false,
      mode: "pop-up"
    };

    this.leftMenuInputs= {
      wsId: '',
      uwYear: 0,
      projects: [],
      showDeleted: this.tableInputs['showDeleted'],
      filterData: this.tableInputs['filterData'],
      filters: this.tableInputs['filters'],
      deletedPltsLength: 0,
      userTags: [],
      selectedListOfPlts: this.tableInputs['selectedListOfPlts'],
      systemTagsCount: {},
      wsHeaderSelected: true,
      pathTab: true,
    };
  }

  onShow() {
    this.tableInputs= {
      dataKey: 'pltId',
      scrollConfig: {
        containerHeight: null,
        containerGap: null
      },
      contextMenuItems: [
        {
          label: 'View Detail', command: (event) => {
            this.openPltInDrawer(this.selectedPlt);
          }
        },
      ],
      filterInput: '',
      pltColumns: [{
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
        }],
      listOfPltsData: [],
      listOfDeletedPltsData: [],
      listOfPltsCache: [],
      listOfDeletedPltsCache: [],
      selectedListOfPlts: [],
      selectedListOfDeletedPlts: [],
      selectAll: false,
      selectAllDeletedPlts: false,
      someItemsAreSelected: false,
      someDeletedItemsAreSelected: false,
      showDeleted: false,
      filterData: {},
      filters: {
        systemTag: {},
        userTag: []
      },
      sortData: {},
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
      },
      openedPlt: "",
    };
    this.browesing=false;
    this.keywordFormGroup.get('keyword')
      .valueChanges
      .pipe(debounceTime(400))
      .pipe(this.unsubscribeOnDestroy)
      .subscribe((value) => {
        this.loading = true;
        this._loadData();
      });

    if (this.stepConfig.uwYear && this.stepConfig.wsId) {
      this.updateTableAndLefMenuInputs('wsId', this.stepConfig.wsId);
      this.updateTableAndLefMenuInputs('uwYear', this.stepConfig.uwYear);
      this.getBrowesingItemsDirectly();
    }

  }

  getBrowesingItemsDirectly() {
    this.browesing= true;

    this.dispatch(new fromWorkspaceStore.loadAllPlts({
      params: {
        workspaceId: this.getInputs('wsId'), uwy: this.getInputs('uwYear')
      },
      wsIdentifier: this.getInputs('wsId') + '-' + this.getInputs('uwYear')
    }));

    this.getPlts().subscribe((data) => {
      this.updateTableAndLefMenuInputs('systemTagsCount', this.systemTagService.countSystemTags(data));

      this.setInputs('listOfPltsCache', _.map(data, (v, k) => ({...v, pltId: k})));
      this.setInputs('listOfPltsData', [...this.getTableInputKey('listOfPltsCache')]);
      this.updateMenuKey('basket', []);
      this.setInputs('selectedListOfPlts', []);

      _.forEach(data, (v, k) => {
        if (v.selected) {
          this.setInputs('selectedListOfPlts', _.concat(this.getInputs('selectedListOfPlts'), v.pltId));
          this.updateMenuKey('basket', _.concat(this.getRightMenuKey('basket'), {
            pltId: v.pltId,
            ...v
          }));
        }
      })

      this.detectChanges();
    });

    this.getDeletedPlts()
      .subscribe((deletedData) => {
        this.setInputs('listOfDeletedPltsCache', _.map(deletedData, (v, k) => ({...v, pltId: k})));
        this.setInputs('listOfDeletedPltsData', [...this.getTableInputKey('listOfDeletedPltsCache')]);
        this.setInputs('selectedListOfDeletedPlts', _.filter(deletedData, (v, k) => v.selected));

        this.detectChanges();
      });

    this.getPlts().subscribe(data => {
      this.setInputs('selectAll',
        (this.getTableInputKey('selectedListOfPlts').length > 0 || (this.getTableInputKey('selectedListOfPlts').length == this.getTableInputKey('listOfPltsData').length))
        &&
        this.getTableInputKey('listOfPltsData').length > 0);

      this.setInputs("someItemsAreSelected", this.getTableInputKey('selectedListOfPlts').length < this.getTableInputKey('listOfPltsData').length && this.getTableInputKey('selectedListOfPlts').length > 0);
      this.detectChanges();
    });

    this.getDeletedPlts().subscribe(deletedPlts => {
      this.setInputs('selectAllDeletedPlts',
        (this.getTableInputKey('selectedListOfDeletedPlts').length > 0 || (this.getTableInputKey('selectedListOfDeletedPlts').length == this.getTableInputKey('listOfDeletedPltsData').length))
        &&
        this.getTableInputKey('listOfDeletedPltsData').length > 0);

      this.setInputs("someDeletedItemsAreSelected", this.getTableInputKey('selectedListOfDeletedPlts').length < this.getTableInputKey('listOfDeletedPltsData').length && this.getTableInputKey('selectedListOfDeletedPlts').length > 0);
      this.detectChanges();
    });

    this.d = this.actions$.pipe(
      ofActionDispatched(fromWorkspaceStore.loadAllPltsSuccess),
      mergeMap( () => {
        this.toggleSelectPlts(_.zipObject(
          _.map(_.map(this.stepConfig.plts, id => _.find(this.getInputs('listOfPltsData'), plt => id == plt.pltId)), plt => plt.pltId),
          _.map(this.stepConfig.plts, plt =>   ({type: true }))
        ));
        return of(null);
      })
    ).subscribe( () => this.d.unsubscribe());

    this.getProjects().subscribe((projects: any) => {
      this.updateTableAndLefMenuInputs('projects', _.map(projects, p => ({...p, selected: false})));
      this.detectChanges();
    });

    this.getUserTags().subscribe(userTags => {
      this.updateTableAndLefMenuInputs('userTags', userTags || {});
      this.detectChanges();
    })
  }

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

  _loadData(offset = '0', size = '100') {
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

  goToSearchWorkspace() {
    this._filter = {};
    this._loadData();
    this.browesing= false;
  }

  onEnter() { this._loadData(); }
  search() { this._loadData(); }

  clearSearchValue() {
    this.keywordFormGroup.get('keyword').setValue(null);
    this._loadData();
  }

  openWorkspaceInSlider(event?) { console.log(event); }
  @Debounce(500)
  filterData($event, target) {
    this._filter = {...this._filter, [target]: $event || null};
    this._loadData();
  }

  loadMore(event: LazyLoadEvent) {
    this.paginationOption.currentPage = event.first;
    this._loadData(String(event.first));
  }

  onRowSelect(event) {
    this.selectedWorkspace = event;
    this.updateTableAndLefMenuInputs('wsId', event.workSpaceId);
    this.updateTableAndLefMenuInputs('uwYear', event.uwYear);
  }

  onRowUnselect(event) {
    // this.selectedWorkspace = null;
  }

  getBrowesingItems(workspace) {
    this.onSelectWorkspace.emit(workspace);
    this.browesing = false;

      if(this.multiSteps) {
        this.browesing = true;

        this.dispatch(new fromWorkspaceStore.loadWorkSpaceAndPlts({
          params: {
            workspaceId: this.getInputs('wsId'), uwy: this.getInputs('uwYear')
          },
          wsIdentifier: this.getInputs('wsId') + '-' +this.getInputs('uwYear')
        }));

        this.getPlts().subscribe((data) => {
          this.updateTableAndLefMenuInputs('systemTagsCount', this.systemTagService.countSystemTags(data));

          this.setInputs('listOfPltsCache', _.map(data, (v, k) => ({...v, pltId: k})));
          this.setInputs('listOfPltsData', [...this.getTableInputKey('listOfPltsCache')]);
          this.updateMenuKey('basket', []);
          this.setInputs('selectedListOfPlts', []);

          _.forEach(data, (v, k) => {
            if (v.selected) {
              this.setInputs('selectedListOfPlts', _.concat(this.getInputs('selectedListOfPlts'), v.pltId));
              this.updateMenuKey('basket', _.concat(this.getRightMenuKey('basket'), {
                pltId: v.pltId,
                ...v
              }));
            }
          })

          this.detectChanges();
        });

        this.getDeletedPlts()
          .subscribe((deletedData) => {
            this.setInputs('listOfDeletedPltsCache', _.map(deletedData, (v, k) => ({...v, pltId: k})));
            this.setInputs('listOfDeletedPltsData', [...this.getTableInputKey('listOfDeletedPltsCache')]);
            this.setInputs('selectedListOfDeletedPlts', _.filter(deletedData, (v, k) => v.selected));

            this.detectChanges();
          });

        this.getPlts().subscribe(data => {
          this.setInputs('selectAll',
            (this.getTableInputKey('selectedListOfPlts').length > 0 || (this.getTableInputKey('selectedListOfPlts').length == this.getTableInputKey('listOfPltsData').length))
            &&
            this.getTableInputKey('listOfPltsData').length > 0);

          this.setInputs("someItemsAreSelected", this.getTableInputKey('selectedListOfPlts').length < this.getTableInputKey('listOfPltsData').length && this.getTableInputKey('selectedListOfPlts').length > 0);
          this.detectChanges();
        });

        this.getDeletedPlts().subscribe(deletedPlts => {
          this.setInputs('selectAllDeletedPlts',
            (this.getTableInputKey('selectedListOfDeletedPlts').length > 0 || (this.getTableInputKey('selectedListOfDeletedPlts').length == this.getTableInputKey('listOfDeletedPltsData').length))
            &&
            this.getTableInputKey('listOfDeletedPltsData').length > 0);

          this.setInputs("someDeletedItemsAreSelected", this.getTableInputKey('selectedListOfDeletedPlts').length < this.getTableInputKey('listOfDeletedPltsData').length && this.getTableInputKey('selectedListOfDeletedPlts').length > 0);
          this.detectChanges();
        });

        this.getProjects().subscribe((projects: any) => {
          this.updateTableAndLefMenuInputs('projects', _.map(projects, p => ({...p, selected: false})));
          this.detectChanges();
        });

        this.getUserTags().subscribe(userTags => {
          this.updateTableAndLefMenuInputs('userTags', userTags || {});
          this.detectChanges();
        })

        this.getOpenedPlt().subscribe(openedPlt => {
          this.setInputs('pltDetail', openedPlt);
          this.updateTable('openedPlt', openedPlt && openedPlt.pltId);
          this.updateMenuKey('visible', openedPlt && !openedPlt.pltId ? false : this.getRightMenuKey('visible'));
          this.detectChanges();
        });
      } else {
        this.onVisibleChange.emit(false);
      }
  }

  setBrowesingItems() {
      this.onSelectWorkspace.emit(this.selectedWorkspace);
      this.onSelectItems.emit(this.getInputs('selectedListOfPlts'));
      this.onVisibleChange.emit(false);
  }

  toggleSelectPlts(plts: any) {
    this.dispatch(new fromWorkspaceStore.ToggleSelectPlts({
      wsIdentifier: this.getInputs('wsId') + '-' + this.getInputs('uwYear'),
      plts,
      forDeleted: this.getInputs('showDeleted')
    }));
  }

  openPltInDrawer(plt) {
    if(this.getRightMenuKey('pltDetail')) {
      this.closePltInDrawer();
    }
    this.dispatch(new fromWorkspaceStore.OpenPLTinDrawer({
      wsIdentifier: this.getInputs('wsId') + '-' + this.getInputs('uwYear'),
      pltId: plt
    }));
    this.updateMenuKey('pltDetail', _.find(this.getInputs('listOfPltsData'), e => e.pltId == plt))
    this.updateMenuKey('visible', true);
    this.setRightMenuSelectedTab('pltDetail');
  }

  closePltInDrawer() {
    this.dispatch(new fromWorkspaceStore.ClosePLTinDrawer({
      wsIdentifier: this.getInputs('wsId') + '-' + this.getInputs('uwYear'),
    }));
    this.updateMenuKey('pltDetail', null);
    this.setRightMenuSelectedTab('basket');
  }

  setFilters($event) {
    this.updateTableAndLefMenuInputs('filters', $event)
  }

  setUserTags($event) {
    this.updateTableAndLefMenuInputs('userTags', $event)
  }

  emitFilters(filters: any) {
    this.dispatch(new fromWorkspaceStore.setUserTagsFilters({
      wsIdentifier: this.getInputs('wsId') + '-' + this.getInputs('uwYear'),
      filters: filters
    }))
  }

  setWsHeaderSelect($event: any) {
    this.updateTableAndLefMenuInputs('wsHeaderSelected', $event);
  }

  unCheckAll() {
    this.toggleSelectPlts(
      _.zipObject(
        _.map([...this.getInputs('listOfPlts'), ...this.getInputs('listOfDeletedPlts')], plt => plt),
        _.range(this.getInputs('listOfPlts').length + this.getInputs('listOfDeletedPlts').length).map(el => ({type: false}))
      )
    );
  }

  setSelectedProjects($event) {
    this.updateTableAndLefMenuInputs('projects', $event);
  }

  filterPlts(filterData) {
    this.updateTableAndLefMenuInputs('filterData', filterData);
  }

  getInputs(key){
    return this.tableInputs[key];
  }

  setInputs(key, value) {
    this.tableInputs= {...this.tableInputs, [key]: value };
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

  updateTable(key: string, value: any) {
    this.tableInputs = tableActions.updateKey.handler(this.tableInputs, key, value);
  }

  getTableInputKey(key) {
    return _.get(this.tableInputs, key);
  }

  rightMenuActionDispatcher(action: Message) {
    console.log(action);
    switch (action.type) {
      case rightMenuStore.closeDrawer:
        this.updateMenuKey('visible', false);
        this.closePltInDrawer();
        break;
      case rightMenuStore.openDrawer:
        this.updateMenuKey('visible', true);
        break;
      case  rightMenuStore.unselectPlt:
        this.toggleSelectPlts(
          _.zipObject(
            _.map([action.payload], plt => plt),
            _.range([action.payload].length).map(el => ({type: false}))
          )
        );
        break;
      case rightMenuStore.setSelectedTabByIndex:
        this.setSelectedPltByIndex(action.payload);
        break;
      default:
        console.log('default right menu action');
    }
  }

  setSelectedPltByIndex(index: any) {
    this.rightMenuInputs = rightMenuActions.setSelectedTabByIndex.handler(this.rightMenuInputs, index);
  }

  tableActionDispatcher(action: Message) {
    switch (action.type) {
      case tableStore.filterData:
        this.updateTable('filterData', action.payload);
        break;
      case tableStore.setFilters:
        this.updateTable('filters', action.payload);
        break;
      case tableStore.sortChange:
        this.updateTable('sortData', action.payload);
        break;
      case tableStore.checkBoxSort:
        console.log(action.payload);
        this.updateTable('listOfPltsData', action.payload);
        break;
      case tableStore.onCheckAll:
        this.toggleSelectPlts(
          _.zipObject(
            _.map(!action.payload ? this.getTableInputKey('listOfPltsData') : this.getTableInputKey('listOfDeletedPltsData'), plt => plt.pltId),
            _.range(!action.payload ? this.getTableInputKey('listOfPltsData').length : this.getTableInputKey('listOfDeletedPltsData').length).map(el => ({type: !this.getTableInputKey('showDeleted') ? !this.getTableInputKey('selectAll') && !this.getTableInputKey("someItemsAreSelected") : !this.getTableInputKey('selectAllDeletedPlts') && !this.getTableInputKey("someDeletedItemsAreSelected")}))
          )
        );
        break;
      case tableStore.setSelectedMenuItem:
        this.selectedPlt = action.payload;
        break;

      case tableStore.toggleSelectedPlts:
        this.toggleSelectPlts(action.payload);
        break;
      default:
        console.log('table action dispatcher')
    }
  }

  filterD(filterData) {
    this.updateTableAndLefMenuInputs('filterData', filterData);
  }
  resetPath() {
    this.updateTableAndLefMenuInputs('filterData', _.omit(this.getTableInputKey('filterData'), 'project'));
    this.updateLeftMenuInput('projects', _.map(this.leftMenuInputs.projects, p => ({...p, selected: false})));
    this.updateTableAndLefMenuInputs('showDeleted', false);
  }

  leftMenuActionDispatcher(action: Message) {

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
      case leftMenuStore.resetPath:
        this.resetPath();
        break;
      case leftMenuStore.headerSelection:
        this.setWsHeaderSelect(action.payload);
        break;
      case leftMenuStore.filterByProject:
        this.filterD(action.payload);
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
    this.tagsInputs= {...this.tagsInputs, [key]: value};
  }

  updateLeftMenuInput(key, value) {
    this.leftMenuInputs= {...this.leftMenuInputs, [key]: value};
  }

  updateTableAndLefMenuInputs(key, value) {
    this.updateLeftMenuInput(key, value);
    this.updateTable(key,value);
  }

  resizing() {
    this.detectChanges();
  }

  collapseLeftMenu() {
    this.collapsedTags= !this.collapsedTags;
    this.detectChanges();
  }

  onTableContainerResize($event: ResizedEvent) {
    console.log($event)
    this.updateTable('scrollConfig', {
      containerHeight: $event.newHeight,
      containerGap: '62px',
    })
  }

}
