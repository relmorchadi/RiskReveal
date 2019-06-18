import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  NgZone,
  OnDestroy,
  OnInit,
  TemplateRef,
  ViewChild
} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import * as _ from 'lodash';
import {Select, Store} from '@ngxs/store';
import * as fromWorkspaceStore from '../../store';
import {PltMainState} from '../../store';
import {map, mergeMap, tap} from 'rxjs/operators';
import {ActivatedRoute} from '@angular/router';
import {Table} from 'primeng/table';
import {combineLatest} from 'rxjs';
import {WorkspaceMainState} from '../../../core/store/states';

@Component({
  selector: 'app-workspace-plt-browser',
  templateUrl: './workspace-plt-browser.component.html',
  styleUrls: ['./workspace-plt-browser.component.scss']
})
export class WorkspacePltBrowserComponent implements OnInit, OnDestroy {

  private dropdown: NzDropdownContextComponent;
  private Subscriptions: any[] = [];
  searchAddress: string;
  listOfPlts: any[];
  listOfDeletedPlts: any[];
  listOfPltsData: any[];
  listOfDeletedPltsData: any[];
  listOfDeletedPltsCache: any[];
  listOfPltsCache: any[];
  selectedListOfPlts: any[];
  selectedListOfDeletedPlts: any[];
  filterData: any;
  sortData;
  activeCheckboxSort: boolean;
  @ViewChild('dt')
  private table: Table;

  workspaceId: string;
  uwy: number;
  projects: any[];

  params: any;
  lastSelectedId;

  contextMenuItems = [
    { label: 'View Detail', command: (event) => this.openPltInDrawer(this.selectedPlt) },
    { label: 'Delete', command: (event) =>
        this.store$.dispatch(new fromWorkspaceStore.deletePlt({pltIds: this.selectedListOfPlts.length > 0 ? this.selectedListOfPlts : [this.selectedItemForMenu]}))
    },
    { label: 'Manage Tags', command: (event) => {
        this.addTagModal= true;
        this.fromPlts= true;
        this.renamingTag= false;
        let d= _.map( this.selectedListOfPlts, k => _.find(this.listOfPltsData, e => e.pltId == k).userTags);

        /* _.forEach( this.listOfPltsData, (v,k) => {
           if(v.selected) d.push(v.userTags);
         })*/

        //this.selectedUserTags = _.keyBy(_.intersectionBy(...d, 'tagId'), 'tagId')

        this.addModalSelect= this.addModalSelectCache= _.intersectionBy(...d, 'tagId');
        this.oldSelectedTags= _.uniqBy(_.flatten(d), 'tagId');
        console.log(this.addModalSelectCache,this.oldSelectedTags,d)

      }
    },
    {
      label: 'Restore',
      command: () => {
        this.store$.dispatch(new fromWorkspaceStore.restorePlt({pltIds: this.selectedListOfDeletedPlts.length > 0 ? this.selectedListOfDeletedPlts : [this.selectedItemForMenu]}))
        this.showDeleted = !(this.listOfDeletedPlts.length === 0) ? this.showDeleted : false;
      }
    }
  ];

  tagContextMenu = [
    { label: 'Delete Tag', icon: 'pi pi-trash', command: (event) => this.store$.dispatch(new fromWorkspaceStore.deleteUserTag(this.tagFormenu.tagId))},
    { label: 'Rename Tag', icon: 'pi pi-pencil', command: (event) => {
        this.renamingTag= true;
        this.fromPlts = false;
        this.addModalInput = this.tagFormenu.tagName;
        this.modalInputCache = this.tagFormenu.tagName;
        this.addTagModal = true;
      }
    }
  ];

  pltColumns = [
    {sortDir: 1,fields: '', header: 'User Tags', width: '43px', sorted: false, filtred: false, icon: null, type: 'checkbox'},
    {sortDir: 1,fields: '', header: 'User Tags', width: '60px', sorted: false, filtred: false, icon: null, type: 'tags'},
    {sortDir: 1,fields: 'pltId', header: 'PLT ID', width: '65px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: 'pltName', header: 'PLT Name', width: '140px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: 'peril', header: 'Peril', width: '55px', sorted: true, filtred: true, icon: null, type: 'field', textAlign: 'center'},
    {sortDir: 1,fields: 'regionPerilCode', header: 'Region Peril Code', width: '75px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: 'regionPerilName', header: 'Region Peril Name', width: '130px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: 'grain', header: 'Grain', width: '160px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: 'deletedBy',forDelete: true, header: 'Deleted By', width: '70px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: 'deletedAt',forDelete:true, header: 'Deleted At', width: '70px', sorted: true, filtred: true, icon: null, type: 'date'},
    {sortDir: 1,fields: 'vendorSystem', header: 'Vendor System', width: '60px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: 'rap', header: 'RAP', width: '70px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: '', header: '', width: '25px', sorted: false, filtred: false, icon: 'icon-focus-add', type: 'icon'},
    {sortDir: 1,fields: '', header: '', width: '25px', sorted: false, filtred: false, icon: 'icon-note', type: 'icon'},
    {sortDir: 1,fields: '', header: '', width: '25px', sorted: false, filtred: false, icon: 'icon-focus-add', type: 'icon'},

  ];

  pltColumnsCache= this.pltColumns;

  epMetricsCurrencySelected: any = 'EUR';
  CalibrationImpactCurrencySelected: any = 'EUR';
  epMetricsFinancialUnitSelected: any = 'Million';
  CalibrationImpactFinancialUnitSelected: any = 'Million';

  currentPath: any = null;

  visible = false;
  size = 'large';
  filters: {
    systemTag: [],
    userTag: []
  };
  sumnaryPltDetailsPltId: any;

  epMetricInputValue: string | null;

  pltdetailsSystemTags: any = [];
  pltdetailsUserTags: any = [];

  systemTags: any;

  systemTagsCount: any;

  userTags: any;

  userTagsCount: any;

  currencies = [
    {id: '1', name: 'Euro', label: 'EUR'},
    {id: '2', name: 'Us Dollar', label: 'USD'},
    {id: '3', name: 'Britsh Pound', label: 'GBP'},
    {id: '4', name: 'Canadian Dollar', label: 'CAD'},
    {id: '5', name: 'Moroccan Dirham', label: 'MAD'},
    {id: '5', name: 'Swiss Franc', label: 'CHF'},
    {id: '5', name: 'Saudi Riyal', label: 'SAR'},
    {id: '6', name: 'Bitcoin', label: 'XBT'},
    {id: '7', name: 'Hungarian forint', label: 'HUF'},
    {id: '8', name: 'Singapore Dollars', label: 'SGD'}
  ];

  units = [
    {id: '3', label: 'Billion'},
    {id: '1', label: 'Thousands'},
    {id: '2', label: 'Million'},
    {id: '4', label: 'Unit'}
  ];

  metrics = [
    {
      metricID: '1',
      retrunPeriod: '10000',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '2',
      retrunPeriod: '5,000',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '4',
      retrunPeriod: '1,000',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '5',
      retrunPeriod: '500',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '6',
      retrunPeriod: '250',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '7',
      retrunPeriod: '100',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '8',
      retrunPeriod: '50',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '9',
      retrunPeriod: '25',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '10',
      retrunPeriod: '10',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '11',
      retrunPeriod: '5',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '12',
      retrunPeriod: '2',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    }
  ];

  theads = [
    {
      title: '', cards: [
        {
          chip: 'ID: 222881',
          content: 'HDIGlobal_CC_IT1607_XCV_SV_SURPLUS_729',
          borderColor: '#6e6cc0',
          selected: false
        },
      ]
    },
    {
      title: 'Base', cards: [
        {chip: '1.25', content: 'Portfolio Evolution', borderColor: '#03dac4', selected: false}
      ]
    },
    {
      title: 'Default', cards: [
        {chip: 'Event', content: 'Tsunami', borderColor: '#03dac4', selected: false}
      ]
    },
    {
      title: 'Analyst', cards: [
        {chip: '1.13', content: 'ALAE', borderColor: '#03dac4', selected: false}
      ]
    },
    {
      title: 'Client', cards: [
        {chip: '0.95', content: 'Cedant QI', borderColor: '#03dac4', selected: false}
      ]
    },
    {
      title: '', cards: [
        {chip: 'ID: 232896', content: 'JEPQ_RL_DefAdj_CC_IT1607_GGDHHT7766', borderColor: '#6e6cc0', selected: false}
      ]
    }
  ];

  dependencies = [
    {id: 1, title: 'ETL', content: 'RDM: CC_IT1607_XYZ_Surplus_R', chip: 'Analysis ID: 149'},
    {id: 2, title: 'PTL', content: 'ID 9867', chip: 'Pure PLT'},
    {id: 2, title: 'PTL', content: 'ID 9888', chip: 'Thead PLT'},
    {id: 2, title: 'PTL', content: 'ID 9901', chip: 'Cloned PLT'}
  ];
  someItemsAreSelected: boolean;
  selectAll: boolean;
  drawerIndex: any;
  private pageSize: number = 20;
  private lastClick: string;
  renamingTag: boolean;
  private modalInputCache: any;


  constructor(
    private nzDropdownService: NzDropdownService,
    private store$: Store,
    private zone: NgZone,
    private cdRef: ChangeDetectorRef,
    private route$: ActivatedRoute) {
    this.someItemsAreSelected = false;
    this.selectAll = false;
    this.listOfPlts = [];
    this.listOfPltsData = [];
    this.listOfDeletedPltsData = [];
    this.selectedListOfPlts = [];
    this.selectedListOfDeletedPlts = [];
    this.lastSelectedId = null;
    this.drawerIndex = 0;
    this.params = {};
    this.loading = true;
    this.filters = {
      systemTag: [],
      userTag: []
    };
    this.filterData = {};
    this.sortData = {};
    this.activeCheckboxSort = false;
    this.loading = true;
    this.addTagModal = false;
    this.tagModalIndex = 0;
    this.systemTagsCount = {};
    this.userTagsCount = {};
    this.fromPlts = false;
    this.renamingTag = false;
    this.selectedUserTags = {};
    this.initColor = '#fe45cd'
    this.colorPickerIsVisible = false;
    this.addTagModalPlaceholder = 'Select a Tag';
    this.showDeleted= false;
    this.wsHeaderSelected= true;
    this.generateContextMenu(this.showDeleted);
    this.generateColumns(this.showDeleted);
  }

  @Select(PltMainState.getUserTags) userTags$;
  @Select(PltMainState.getPlts) data$;
  @Select(PltMainState.getDeletedPlts) deletedPlts$;
  deletedPlts: any;
  loading: boolean;
  selectedUserTags: any;

  generateColumns= (showDelete) =>  this.pltColumns = showDelete ? _.omit(_.omit(this.pltColumnsCache, '7'), '7') : this.pltColumnsCache;

  systemTagsMapping = {
    grouped: {
      peril: 'Peril',
      regionDesc: 'Region',
      currency: 'Currency',
      sourceModellingVendor: 'Modelling Vendor',
      sourceModellingSystem: 'Model System',
      targetRapCode: 'Target RAP',
      userOccurrenceBasis: 'User Occurence Basis',
      xActAvailable: 'Published To Pricing',
      xActUsed: 'Priced',
      accumulated: 'Accumulated',
      financial: 'Financial Perspectives'
    },
    nonGrouped: {}
  };

  contextMenuItemsCache= this.contextMenuItems;

  generateContextMenu(toRestore) {
    this.contextMenuItems = _.filter(this.contextMenuItemsCache, e => e.label != (!toRestore ? 'Restore' : 'Delete'))
  }

  ngOnInit() {
    this.Subscriptions.push(
      combineLatest(
        this.data$,
        this.deletedPlts$
      ).subscribe(([data, deletedData]) => {
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
                  this.systemTagsCount[sectionName][tag] = {selected: false, count: 0}
                }
              });

              //NONE grouped Sys Tags
              _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
                this.systemTagsCount[sectionName] = this.systemTagsCount[sectionName] || {};
                this.systemTagsCount[sectionName][section] = {selected: false, count: 0};
                this.systemTagsCount[sectionName]['non-' + section] = {selected: false, count: 0};
              })

            })
          }

          _.forEach(data, (v, k) => {
            d1.push({...v, pltId: k});
            d2.push(k);

            if (v.visible) {
              //Grouped Sys Tags
              _.forEach(this.systemTagsMapping.grouped, (sectionName, section) => {
                const tag = _.toString(v[section]);
                if (tag) {
                  if (this.systemTagsCount[sectionName][tag] || this.systemTagsCount[sectionName][tag].count === 0) {
                    this.systemTagsCount[sectionName][tag] = {
                      ...this.systemTagsCount[sectionName][tag],
                      count: this.systemTagsCount[sectionName][tag].count + 1
                    };
                  }
                }
              })

              //NONE grouped Sys Tags
              _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
                const tag = v[section];
                if (this.systemTagsCount[sectionName][section] || this.systemTagsCount[sectionName][section] == 0) {
                  this.systemTagsCount[sectionName][section] = {
                    ...this.systemTagsCount[sectionName][section],
                    count: this.systemTagsCount[sectionName][section].count + 1
                  };
                }
                if (this.systemTagsCount[sectionName]['non-' + section] || this.systemTagsCount[sectionName]['non-' + section].count == 0) {
                  this.systemTagsCount[sectionName]['non-' + section] = {
                    ...this.systemTagsCount[sectionName]['non-' + section],
                    count: this.systemTagsCount[sectionName]['non-' + section].count + 1
                  };
                }
              })
            }

          });

          this.listOfPlts = d2;
          this.listOfPltsData = this.listOfPltsCache = d1;
          this.selectedListOfPlts = _.filter(d2, k => data[k].selected);
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
      this.data$.subscribe(data => {
        _.forEach(data, (v, k) => {
          if (v.opened) {
            this.sumnaryPltDetailsPltId = k;
          }
        });
        this.detectChanges();
      }),
      this.route$.params.subscribe(({wsId, year}) => {
        this.workspaceId = wsId;
        this.uwy = year;
        this.loading= true;
        this.store$.dispatch(new fromWorkspaceStore.loadAllPlts({
          params: {
            workspaceId: wsId, uwy: year
          }}));
      }),
      this.store$.select(PltMainState.getProjects()).subscribe((projects: any) => {
        this.projects = _.map(projects, p => ({...p, selected: false}));
        this.detectChanges();
      }),
      this.getAttr('loading').subscribe( l => this.loading = l),
      this.userTags$.subscribe( userTags => {
        this.userTags = userTags || {};
        this.detectChanges();
      }),
      this.store$.select(WorkspaceMainState.getLeftNavbarIsCollapsed).subscribe(l => {
        this.leftIsHidden=l;
        this.detectChanges();
      })
    );
  }

  getAttr(path) {
    return this.store$.select(PltMainState.getAttr).pipe(map(fn => fn(path)));
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

  filter(filterData) {
    this.filterData= filterData;
  }

  selectedPlt: any;
  tagModalIndex: any;
  addTagModal: boolean;
  addModalInput: any;
  addModalSelect: any;
  addModalSelectCache: any;
  oldSelectedTags: any;
  tagFormenu: any;
  fromPlts: any;
  colorPickerIsVisible: any;
  initColor: any;
  addTagModalPlaceholder: any;
  showDeleted: boolean;
  selectedItemForMenu: string;
  wsHeaderSelected: boolean;
  leftIsHidden: any;

  selectPltById(pltId) {
    return this.store$.select(state => _.get(state, `pltMainModel.data.${pltId}`));
  }

  openDrawer(index): void {
    this.visible = true;
    this.drawerIndex = index;
  }

  closeDrawer(): void {
    this.visible = false;
  }

  closePltInDrawer(pltId) {
    this.store$.dispatch(new fromWorkspaceStore.ClosePLTinDrawer({pltId}));
  }

  deletePlt() {
    this.store$.dispatch(new fromWorkspaceStore.deletePlt({pltIds: this.selectedListOfPlts.length > 0 ? this.selectedListOfPlts : [this.selectedItemForMenu]}))
  }

  editTags() {
    this.addTagModal = true;
    this.fromPlts = true;
    let d = _.map(this.selectedListOfPlts, k => _.find(this.listOfPltsData, e => e.pltId == k).userTags);

    /* _.forEach( this.listOfPltsData, (v,k) => {
       if(v.selected) d.push(v.userTags);
     })*/

    //this.selectedUserTags = _.keyBy(_.intersectionBy(...d, 'tagId'), 'tagId')

    this.addModalSelect = this.addModalSelectCache = _.intersectionBy(...d, 'tagId');
    this.oldSelectedTags = _.uniqBy(_.flatten(d), 'tagId');
    console.log(this.addModalSelectCache, this.oldSelectedTags, d);
  }

  restore() {
    this.store$.dispatch(new fromWorkspaceStore.restorePlt({pltIds: this.selectedListOfDeletedPlts.length > 0 ? this.selectedListOfDeletedPlts : [this.selectedItemForMenu]}))
    this.showDeleted = !(this.listOfDeletedPlts.length === 0) ? this.showDeleted : false;
  }

  openPltInDrawer(plt) {
    this.closePltInDrawer(this.sumnaryPltDetailsPltId);
    this.store$.dispatch(new fromWorkspaceStore.OpenPLTinDrawer({pltId: plt}));
    this.openDrawer(1);
    this.getTagsForSummary();
  }

  getTagsForSummary() {
    this.pltdetailsSystemTags = this.systemTags;
    this.pltdetailsUserTags = this.userTags;
  }

  selectCardThead(card) {
    this.theads.forEach(thead => {
      thead.cards.forEach(card => {
        card.selected = false;
      });
    });
    card.selected = true;
  }

  getCardBackground(selected) {
    if (selected) return '#FFF';
    else return '#f4f6fc';
  }

  getBoxShadow(selected) {
    if (selected) return '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)';
    else return 'none';
  }

  resetFilterByTags() {
    this.filters = {
      systemTag: [],
      userTag: []
    }
    this.userTags = _.map(this.userTags, t => ({...t, selected: false}));
    this.systemTags = _.map(this.systemTags, t => ({...t, selected: false}));
    this.store$.dispatch(new fromWorkspaceStore.setUserTagsFilters({filters: this.filters}));
  }

  resetPath() {
    this.filterData = _.omit(this.filterData, 'project')
    this.projects = _.map(this.projects, p => ({...p, selected: false}))
    this.showDeleted = false;
  }

  setSelectedProjects($event) {
    this.projects = $event;
  }

  contextMenuPltTable($event: MouseEvent, template: TemplateRef<void>): void {
    this.dropdown = this.nzDropdownService.create($event, template);
  }

  close(e: NzMenuItemDirective): void {
    this.dropdown.close();
  }

  runInNewConext(task) {
    this.zone.runOutsideAngular(() => {
      task();
      this.detectChanges();
    });
  }

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
  }

  ngOnDestroy(): void {
    this.Subscriptions && _.forEach(this.Subscriptions, el => el.unsubscribe());
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
    this.store$.dispatch(new fromWorkspaceStore.ToggleSelectPlts({plts, forDeleted: this.showDeleted}));
  }

  selectSinglePLT($event) {
    this.toggleSelectPlts($event);
  }

  checkBoxSort($event) {
    this.listOfPltsData = $event;
  }

  renameTag($event) {
    this.store$.dispatch(new fromWorkspaceStore.renameTag($event))
  }

  selectUserTag(k) {
    event.stopPropagation();
    event.preventDefault();
    if (_.find(this.selectedUserTags, t => t.tagId == k)) {
      this.selectedUserTags = _.omit(this.selectedUserTags, k);
    } else {
      this.selectedUserTags[k] = this.userTags[k];
    }
    const l = _.toArray(this.selectedUserTags).length;

    if (l == 0) {
      //this.addTagModalPlaceholder= {'Select a Tag': {tagName: 'Select a Tag'}}
      this.addTagModalPlaceholder = 'Seleetc'
    }
    if (l == 1) {
      //this.addTagModalPlaceholder = _.toArray(this.selectedUserTags)[0]
      this.addTagModalPlaceholder = 'hey'
    }
    if (l > 1) {
      //this.addTagModalPlaceholder = {'multiple': {tagName: 'multiple'}}
      this.addTagModalPlaceholder = 'multiple'
    }
    //this.addModalSelect = this.userTags[k];
    console.log(this.addTagModalPlaceholder)

  }

  selectSystemTag({section, tag}) {
    _.forEach(this.systemTagsCount, (s,sKey) => {
      _.forEach(s, (t,tKey) => {
        if(tag == tKey && section == sKey){
          this.systemTagsCount[sKey][tKey] = {...t,selected: !t.selected}
        }else{
          this.systemTagsCount[sKey][tKey] = {...t,selected: false}
        }
      })
    })

  }

  toggleModal() {
    this.addTagModal = false;
    if (!this.addTagModal) {
      this.addModalInput = null;
      this.addModalSelect = null;
      this.tagModalIndex = 0;
      this.renamingTag = false;
    }
  }

  toggleColorPicker(from?: string) {
    if (from == 'color') {
      event.stopPropagation();
      event.preventDefault();
    }
    this.colorPickerIsVisible = !this.colorPickerIsVisible;
    if (!this.colorPickerIsVisible) this.initColor = '#fe45cd';
  }

  sortChange(sortData) {
    this.sortData = sortData;
  }


  setSelectedMenuItem($event: any) {
    this.selectedPlt= $event;
    this.selectedItemForMenu= $event;
  }

  setFilters(filters){
    this.filters= filters;
  }

  emitFilters(filters: any) {
    this.store$.dispatch(new fromWorkspaceStore.setUserTagsFilters({
      filters: filters
    }))
  }

  setFromPlts($event) {
    this.fromPlts = $event;
  }

  setSysTags($event){
    this.setSysTags= $event;
  }

  setUserTags($event){
    this.userTags= $event;
  }

  setModalIndex($event) {
    this.tagModalIndex = $event;
  }

  assignPltsToTag($event: any) {
    const {
      plts,
      tags: newSelectedTags
    } = $event;

    console.log(newSelectedTags, plts);
    let newTags = [];
    let tt= {
      toDelete: [],
      toAdd: []
    };
    let pp= {
      toDelete: _.differenceBy(this.oldSelectedTags, newSelectedTags, 'tagId'),
      toAdd: newSelectedTags
    };

    console.log(this.oldSelectedTags,newSelectedTags)

    _.forEach(_.uniqBy([...this.oldSelectedTags,...newSelectedTags], 'tagId'), tag => {
      let isSelected= _.find(newSelectedTags, newTag => tag.tagId == newTag.tagId);
      let headers = !isSelected
        ?
        _.filter(tag.pltHeaders,pltHeader => !(_.findIndex(plts, pltId => pltId == pltHeader.id) >= 0))
        :
        _.uniqBy([...tag.pltHeaders, ..._.map(plts, plt => ({id : plt}))], e => e.id)

      /*newTags.push({
        ...tag,
        pltHeaders: headers,
        count: headers.length
      })*/

      newTags.push({
        ...tag,
        pltHeaders: headers,
        count: headers.length
      })
    })

    console.log(newTags);
    /*
    _.forEach(_.uniqBy([...this.oldSelectedTags,...newSelectedTags], 'tagId'), tag => {
      let newHeaders = _.find(newTags, t => t.tagId === tag.tagId).pltHeaders;

      console.log(newHeaders,tag.pltHeaders);

      tt['toAdd'] = [...tt['toAdd'], ..._.differenceBy(newHeaders, tag.pltHeaders, 'id')];
      tt['toDelete'] = [...tt['toDelete'], ..._.differenceBy(tag.pltHeaders, newHeaders, 'id')];
    })

    console.log(tt);*/

    this.store$.dispatch(new fromWorkspaceStore.assignPltsToTag({
      ...$event,
      type: 'assignOrRemove',
      tags: newTags
    }))
  }

  assignPltsToSingleTag($event) {
    this.store$.dispatch(new fromWorkspaceStore.assignPltsToTag($event))
  }

  setTagModal($event: any) {
    this.addTagModal = $event;
  }

  setTagForMenu($event: any) {
    this.tagFormenu = $event;
  }

  setRenameTag($event: any) {
    this.renamingTag = $event;
  }

  toggleDeletePlts() {
    this.showDeleted= !this.showDeleted;
    console.log(this.showDeleted);
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

  unCheckAll() {
    this.toggleSelectPlts(
      _.zipObject(
        _.map([...this.listOfPlts, ...this.listOfDeletedPlts], plt => plt),
        _.range(this.listOfPlts.length + this.listOfDeletedPlts.length).map(el => ({type: false}))
      )
    );
  }

  setWsHeaderSelect($event: any) {
    this.wsHeaderSelected = $event;
  }

  setModalSelectedItems($event: any) {
    this.addModalSelect = $event;
  }
}
