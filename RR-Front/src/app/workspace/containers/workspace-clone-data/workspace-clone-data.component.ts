import {ChangeDetectionStrategy, ChangeDetectorRef, Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ActivatedRoute, NavigationStart, Router} from '@angular/router';
import {debounceTime, filter, map, switchMap} from 'rxjs/operators';
import * as _ from 'lodash';
import * as fromWorkspaceStore from '../../store';
import {PltMainState} from '../../store';
import {Select, Store} from '@ngxs/store';
import {combineLatest, Observable, Subscription} from 'rxjs';
import {FormControl, FormGroup} from '@angular/forms';
import {SearchService} from '../../../core/service';
import {Debounce} from '../../../shared/decorators';
import {LazyLoadEvent} from 'primeng/api';

@Component({
  selector: 'app-workspace-clone-data',
  templateUrl: './workspace-clone-data.component.html',
  styleUrls: ['./workspace-clone-data.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class WorkspaceCloneDataComponent implements OnInit {

  @Select(PltMainState.getUserTags) userTags$;
  userTags: any;
  keywordFormGroup: FormGroup;
  paginationOption = {currentPage: 0, page: 0, size: 40, total: '-'};
  contracts = [];
  subscriptions: Subscription;
  columns = [
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
  loading;
  selectedWorkspace: any = null;
  private _filter = {};

  onRowSelect(event) {
    this.selectedWorkspace = event.data;
  }

  onRowUnselect(event) {
    // this.selectedWorkspace = null;
  }

  openWorkspaceInSlider(event?) { console.log(event); }

  @Debounce(500)
  filterDataa($event, target) {
    this._filter = {...this._filter, [target]: $event || null};
    this._loadData();
  }

  @Output('onSelectWorkspace')
  onSelectWorkspace: EventEmitter<any> = new EventEmitter();

  loadMore(event: LazyLoadEvent) {
    this.paginationOption.currentPage = event.first;
    this._loadData(String(event.first));
  }



  show() {
    this.subscriptions = this.keywordFormGroup.get('keyword')
      .valueChanges
      .pipe(debounceTime(400))
      .subscribe((value) => {
        this._loadData();
      });
  }

  onEnter() { this._loadData(); }
  search() { this._loadData(); }
  clearSearchValue() {
    this.keywordFormGroup.get('keyword').setValue(null);
    this._loadData();
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

  private _loadData(offset = '0', size = '100') {
    const keyword = this.keywordFormGroup.get('keyword').value === '' ? null : this.keywordFormGroup.get('keyword').value;
    const params = {
      keyword,
      filter: this.filter,
      offset,
      size
    };
    this.searchService.expertModeSearch(params)
      .subscribe((data: any) => {
        this.contracts = data.content.map(item => ({...item, selected: false}));
        this.paginationOption = {
          ...this.paginationOption,
          page: data.number,
          size: data.numberOfElements,
          total: data.totalElements
        };
        this.detectChanges();
      });
  }

  constructor(
    private router$: Router,
    private route$: ActivatedRoute,
    private store$: Store,
    private cdRef: ChangeDetectorRef,
    private searchService: SearchService
  ) {
    this.keywordFormGroup = new FormGroup({
      keyword: new FormControl(null)
    });
    this.activeSubTitle= 0;
    this.cloningFromItem= false;
    this.cloningToItem= true;
    this.projectForClone= -1;
    this.searchWorkSpaceModal= false;
    this.cols= [
      {
        field: 'id',
        header: 'ID',
        width: '60',
        sorted: false,
        filtred: false,
        icon: null,
        type: 'id',active: true
      },
      {
        field: 'country',
        header: 'Country',
        width: '60',
        sorted: false,
        filtred: true,
        icon: null,
        type: 'text',active: true
      },
      {
        field: 'cedant',
        header: 'Cedant',
        width: '60',
        sorted: false,
        filtred: true,
        icon: null,
        type: 'text',active: true
      },
      {
        field: 'cedantName',
        header: '',
        width: '60',
        sorted: false,
        filtred: true,
        icon: null,
        type: 'text',active: true
      },
      {
        field: 'year',
        header: 'UW Year',
        width: '60',
        sorted: false,
        filtred: true,
        icon: null,
        type: 'text',active: true
      },
      {
        field: 'wsContext',
        header: 'Workspace Context',
        width: '60',
        sorted: false,
        filtred: false,
        icon: null,
        type: 'text',active: true
      },
    ];
    this.data= [
      {
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },{
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },{
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },{
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },{
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },{
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },];
    this.browesing= false;
    this.showDeleted= false;
    this.tagForMenu = {
      tagId: null,
      tagName: '',
      tagColor: '#0700e4'
    }
    this.filters= {
      systemTag: {},
      userTag: []
    };
    this.wsHeaderSelected = true;
  }

  subTitle= {
    0: 'Clone Workspaces Assets',
    1: 'Source Workspace Selection',
    2: 'Target Workspace Selection'
  };

  activeSubTitle: number;
  cloningFromItem: boolean;
  cloningToItem: boolean;
  projectForClone: number;
  searchWorkSpaceModal: boolean;
  cols: any[];
  data: any[];
  from: any;
  to: any;
  browesing: boolean;
  showDeleted: boolean;
  workspaceId: string;
  uwy: number;
  listOfPlts: any[];
  listOfPltsData: any[];
  listOfDeletedPlts: any [];
  selectAll: boolean;
  someItemsAreSelected: boolean;
  data$: Observable<any>;
  deletedPlts$: Observable<any>;
  systemTagsCount: any;
  tagForMenu: any;
  filters: any;
  filterData = {};
  sortData = {};
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

  listOfPltsCache: any[];
  selectedListOfPlts: any[];
  sumnaryPltDetailsPltId: any;
  listOfDeletedPltsData: any[];
  listOfDeletedPltsCache: any[];
  selectedListOfDeletedPlts: any[];
  wsHeaderSelected: boolean;
  projects: any[];
  filterInput: string = "";
  pltColumns = [
    {
      width: '60',
      filtred: false,
      icon: null,
      type: 'checkbox',active: true
    },
    {fields: 'pltId', header: 'PLT ID', width: '80', sorted: false, filtred: true, icon: null, type: 'id',active: true},
    {
      fields: 'pltName',
      header: 'PLT Name',
      width: '160',
      sorted: false,
      filtred: true,
      icon: null,
      type: 'field',active: true
    },
    {
      fields: 'peril',
      header: 'Peril',
      width: '40',
      sorted: false,
      filtred: false,
      icon: null,
      type: 'field',
      textAlign: 'center',active: true
    },
    {
      fields: 'regionPerilCode',
      header: 'Region Peril Code',
      width: '70',
      sorted: false,
      filtred: false,
      icon: null,
      type: 'field',active: true
    },
    {
      fields: 'regionPerilName',
      header: 'Region Peril Name',
      width: '160',
      sorted: false,
      filtred: false,
      icon: null,
      type: 'field',active: true
    },
    {sortDir: 1, fields: 'grain', header: 'Grain', width: '90', sorted: false, filtred: false, icon: null, type: 'field',active: true},
    {
      sortDir: 1,
      fields: 'vendorSystem',
      header: 'Vendor System',
      width: '90',
      sorted: false,
      filtred: false,
      icon: null,
      type: 'field',active: true
    },
    {sortDir: 1, fields: 'rap', header: 'RAP', width: '52', sorted: false, filtred: false, icon: null, type: 'field',active: true}
  ];

  contextMenuItems = [
    {
      label: 'View Detail', command: (event) => {

      }
    },
    {
      label: 'Delete', command: (event) => {

      }
    },
    {
      label: 'Clone To',
      command: (event) => {

      }
    },
    {
      label: 'Manage Tags', command: (event) => {

      }
    },
    {
      label: 'Restore',
      command: () => {

      }
    }
  ];

  ngOnInit() {
    this.router$.events.pipe(
      filter(e => e instanceof NavigationStart),
      map(() => this.router$.getCurrentNavigation().extras.state)
    ).subscribe( d => console.log(d))
    this.route$.params.pipe(
      switchMap(({wsId, year}) => {
        this.workspaceId = wsId;
        this.uwy = year;
        this.data$ = this.store$.select(PltMainState.getPlts(this.workspaceId + '-' + this.uwy));
        this.deletedPlts$ = this.store$.select(PltMainState.getDeletedPlts(this.workspaceId + '-' + this.uwy));
        this.store$.dispatch(new fromWorkspaceStore.loadAllPlts({
          params: {
            workspaceId: wsId, uwy: year
          }
        }));
        return combineLatest(
          this.data$,
          this.deletedPlts$
        )
      })
    ).subscribe(([data, deletedData]: any) => {
      let d1 = [];
      let dd1 = [];
      let d2 = [];
      let dd2 = [];
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
    })

    this.store$.select(PltMainState.getProjects()).subscribe((projects: any) => {
      this.projects = _.map(projects, p => ({...p, selected: false}));
      this.detectChanges();
    })

    this.userTags$.subscribe(userTags => {
      this.userTags = userTags || {};
      this.detectChanges();
    })

  }

  setSubTitle(number: number) {
    this.activeSubTitle= number;
  }

  onRadioChange($event) {
    this.projectForClone= $event;
  }

  handleModalClick() {
    if(!this.browesing) {
      this.browesing= true;
    }else {
      this.searchWorkSpaceModal= false;
      this.browesing= false;
    }
  }

  goToSearchWorkspace() {
    this.searchWorkSpaceModal=true;
    this.browesing= false;
  }

  cancelModal() {
    this.browesing= false;
    this.searchWorkSpaceModal= false;
  }

  checkAll($event) {
    this.toggleSelectPlts(
      _.zipObject(
        _.map(!$event ? this.listOfPlts : this.listOfDeletedPlts, plt => plt),
        _.range(!$event ? this.listOfPlts.length : this.listOfDeletedPlts.length).map(el => ({type: !this.selectAll && !this.someItemsAreSelected}))
      )
    );
  }

  toggleSelectPlts(plts: any) {
    this.store$.dispatch(new fromWorkspaceStore.ToggleSelectPlts({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      plts,
      forDeleted: this.showDeleted
    }));
  }

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
  }

  getPlts() {
    console.log('hey')
  }
}
