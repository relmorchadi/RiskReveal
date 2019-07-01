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
import {map, mapTo, mergeMap, switchMap, tap} from 'rxjs/operators';
import {ActivatedRoute, Router} from '@angular/router';
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
    {
      label: 'View Detail', command: (event) => {
        console.log(this.selectedPlt)
        this.openPltInDrawer(this.selectedPlt.pltId)
      }
    },
    {
      label: 'Delete', command: (event) => {
        this.store$.dispatch(new fromWorkspaceStore.deletePlt({
          wsIdentifier: this.workspaceId + '-' + this.uwy,
          pltIds: this.selectedListOfPlts.length > 0 ? this.selectedListOfPlts : [this.selectedItemForMenu]
        }));
      }
    },
    {
      label: 'Clone To',
      command: (event) => {
        console.log('cloning')
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
        let d = _.map(this.selectedListOfPlts.length > 0 ? this.selectedListOfPlts : [this.selectedItemForMenu], k => _.find(this.listOfPltsData, e => e.pltId == k).userTags);
        this.modalSelect = _.intersectionBy(...d, 'tagId');
        this.oldSelectedTags = _.uniqBy(_.flatten(d), 'tagId');
      }
    },
    {
      label: 'Restore',
      command: () => {
        this.store$.dispatch(new fromWorkspaceStore.restorePlt({
          wsIdentifier: this.workspaceId + '-' + this.uwy,
          pltIds: this.selectedListOfDeletedPlts.length > 0 ? this.selectedListOfDeletedPlts : [this.selectedItemForMenu]
        }))
        this.showDeleted = !(this.listOfDeletedPlts.length === 0) ? this.showDeleted : false;
        this.generateContextMenu(this.showDeleted);
      }
    }
  ];

  tagContextMenu = [
    {
      label: 'Delete Tag',
      icon: 'pi pi-trash',
      command: (event) => this.store$.dispatch(new fromWorkspaceStore.deleteUserTag({
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

  pltColumns = [
    {
      sortDir: 1,
      fields: '',
      header: 'tata',
      width: '60',
      sorted: false,
      filtred: false,
      icon: null,
      type: 'checkbox'
    },
    {sortDir: 1, fields: '', header: 'User Tags', width: '60', sorted: false, filtred: false, icon: null, type: 'tags'},
    {sortDir: 1, fields: 'pltId', header: 'PLT ID', width: '80', sorted: true, filtred: true, icon: null, type: 'id'},
    {
      sortDir: 1,
      fields: 'pltName',
      header: 'PLT Name',
      width: '60',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field'
    },
    {
      sortDir: 1,
      fields: 'peril',
      header: 'Peril',
      width: '80',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      textAlign: 'center'
    },
    {
      sortDir: 1,
      fields: 'regionPerilCode',
      header: 'Region Peril Code',
      width: '130',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field'
    },
    {
      sortDir: 1,
      fields: 'regionPerilName',
      header: 'Region Peril Name',
      width: '160',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field'
    },
    {sortDir: 1, fields: 'grain', header: 'Grain', width: '90', sorted: true, filtred: true, icon: null, type: 'field'},
    {
      sortDir: 1,
      fields: 'deletedBy',
      forDelete: true,
      header: 'Deleted By',
      width: '50',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field'
    },
    {
      sortDir: 1,
      fields: 'deletedAt',
      forDelete: true,
      header: 'Deleted On',
      width: '50',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'date'
    },
    {
      sortDir: 1,
      fields: 'vendorSystem',
      header: 'Vendor System',
      width: '90',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field'
    },
    {sortDir: 1, fields: 'rap', header: 'RAP', width: '52', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1, fields: '', header: '', width: '25', sorted: false, filtred: false, icon: 'icon-note', type: 'icon'},
    {
      sortDir: 1,
      fields: '',
      header: '',
      width: '25',
      sorted: false,
      filtred: false,
      icon: 'icon-dollar-alt',
      type: 'icon'
    },
    {
      sortDir: 1,
      fields: '',
      header: '',
      width: '25',
      sorted: false,
      filtred: false,
      icon: 'icon-focus-add',
      type: 'icon'
    },
  ];

  pltColumnsCache = this.pltColumns;

  epMetricsCurrencySelected: any = 'EUR';
  CalibrationImpactCurrencySelected: any = 'EUR';
  epMetricsFinancialUnitSelected: any = 'Million';
  CalibrationImpactFinancialUnitSelected: any = 'Million';

  drawerVisible = false;
  size = 'large';
  filters: {
    systemTag: {},
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
  editingTag: boolean;
  private modalInputCache: any;


  constructor(
    private nzDropdownService: NzDropdownService,
    private store$: Store,
    private zone: NgZone,
    private cdRef: ChangeDetectorRef,
    private router$: Router,
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
      systemTag: {},
      userTag: []
    };
    this.filterData = {};
    this.sortData = {};
    this.activeCheckboxSort = false;
    this.loading = true;
    this.tagModalVisible = false;
    this.tagModalIndex = 0;
    this.systemTagsCount = {};
    this.userTagsCount = {};
    this.fromPlts = false;
    this.editingTag = false;
    this.selectedUserTags = {};
    this.colorPickerIsVisible = false;
    this.addTagModalPlaceholder = 'Select a Tag';
    this.showDeleted = false;
    this.wsHeaderSelected = true;
    this.tagForMenu = {
      tagId: null,
      tagName: '',
      tagColor: '#0700e4'
    }
    this.generateContextMenu(this.showDeleted);
    this.generateColumns(this.showDeleted);
  }

  @Select(PltMainState.getUserTags) userTags$;
  data$: any;
  deletedPlts$: any;
  deletedPlts: any;
  loading: boolean;
  selectedUserTags: any;

  generateColumns = (showDelete) => this.pltColumns = showDelete ? _.omit(_.omit(this.pltColumnsCache, '7'), '7') : this.pltColumnsCache;

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

  contextMenuItemsCache = this.contextMenuItems;

  generateContextMenu(toRestore) {
    const t = ['Delete', 'Manage Tags', 'Clone To'];
    this.contextMenuItems = _.filter(this.contextMenuItemsCache, e => !toRestore ? ('Restore' != e.label) : !_.find(t, el => el == e.label))
  }

  ngOnInit() {
    this.Subscriptions.push(
      this.route$.params.pipe(
        switchMap(({wsId, year}) => {
          this.workspaceId = wsId;
          this.uwy = year;
          this.loading = true;
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
      this.store$.select(PltMainState.getProjects()).subscribe((projects: any) => {
        this.projects = _.map(projects, p => ({...p, selected: false}));
        this.detectChanges();
      }),
      this.getAttr('loading').subscribe(l => this.loading = l),
      this.userTags$.subscribe(userTags => {
        this.userTags = userTags || {};
        this.detectChanges();
      }),
      this.store$.select(WorkspaceMainState.getLeftNavbarIsCollapsed).subscribe(l => {
        this.leftIsHidden = l;
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
    this.filterData = filterData;
  }

  selectedPlt: any;
  tagModalIndex: any;
  tagModalVisible: boolean;
  modalSelect: any;
  modalSelectCache: any;
  oldSelectedTags: any;
  tagForMenu: any;
  fromPlts: any;
  colorPickerIsVisible: any;
  initColor: any;
  addTagModalPlaceholder: any;
  showDeleted: boolean;
  selectedItemForMenu: string;
  wsHeaderSelected: boolean;
  leftIsHidden: any;
  filterInput: string = "";

  selectPltById(pltId) {
    return this.store$.select(state => _.get(state, `pltMainModel.data.${pltId}`));
  }

  openDrawer(index): void {
    this.drawerVisible = true;
    this.drawerIndex = index;
  }

  closeDrawer(): void {
    this.drawerVisible = false;
  }

  closePltInDrawer(pltId) {
    this.store$.dispatch(new fromWorkspaceStore.ClosePLTinDrawer({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      pltId
    }));
  }

  deletePlt() {
    this.store$.dispatch(new fromWorkspaceStore.deletePlt({pltIds: this.selectedListOfPlts.length > 0 ? this.selectedListOfPlts : [this.selectedItemForMenu]}))
  }

  editTags() {
    this.tagModalVisible = true;
    this.fromPlts = true;
    let d = _.map(this.selectedListOfPlts, k => _.find(this.listOfPltsData, e => e.pltId == k).userTags);

    /* _.forEach( this.listOfPltsData, (v,k) => {
       if(v.selected) d.push(v.userTags);
     })*/

    //this.selectedUserTags = _.keyBy(_.intersectionBy(...d, 'tagId'), 'tagId')

    this.modalSelect = this.modalSelectCache = _.intersectionBy(...d, 'tagId');
    this.oldSelectedTags = _.uniqBy(_.flatten(d), 'tagId');
    console.log(this.modalSelectCache, this.oldSelectedTags, d);
  }

  restore() {
    this.store$.dispatch(new fromWorkspaceStore.restorePlt({pltIds: this.selectedListOfDeletedPlts.length > 0 ? this.selectedListOfDeletedPlts : [this.selectedItemForMenu]}))
    this.showDeleted = !(this.listOfDeletedPlts.length === 0) ? this.showDeleted : false;
  }

  openPltInDrawer(plt) {
    this.closePltInDrawer(this.sumnaryPltDetailsPltId);
    this.store$.dispatch(new fromWorkspaceStore.OpenPLTinDrawer({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      pltId: plt
    }));
    this.openDrawer(1);
    this.getTagsForSummary(plt);
  }

  getTagsForSummary(pltId) {
    //this.pltdetailsSystemTags = this.systemTags;
    this.pltdetailsUserTags = _.find(this.listOfPltsData, e => e.pltId == pltId).userTags;
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

  resetPath() {
    this.filterData = _.omit(this.filterData, 'project')
    this.projects = _.map(this.projects, p => ({...p, selected: false}))
    this.showDeleted = false;
  }

  setSelectedProjects($event) {
    this.projects = $event;
  }

  close(e: NzMenuItemDirective): void {
    this.dropdown.close();
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
    this.store$.dispatch(new fromWorkspaceStore.ToggleSelectPlts({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      plts,
      forDeleted: this.showDeleted
    }));
  }

  selectSinglePLT($event) {
    this.toggleSelectPlts($event);
  }

  checkBoxSort($event) {
    this.listOfPltsData = $event;
  }

  editTag() {
    this.store$.dispatch(new fromWorkspaceStore.editTag({
      tag: this.tagForMenu,
      workspaceId: this.workspaceId,
      uwy: this.uwy
    }))
  }


  selectSystemTag({section, tag}) {
    _.forEach(this.systemTagsCount, (s, sKey) => {
      _.forEach(s, (t, tKey) => {
        if (tag == tKey && section == sKey) {
          this.systemTagsCount[sKey][tKey] = {...t, selected: !t.selected}
        }
      })
    })

  }

  sortChange(sortData) {
    this.sortData = sortData;
  }


  setSelectedMenuItem($event: any) {
    this.selectedPlt = $event;
    this.selectedItemForMenu = $event;
  }

  setFilters(filters) {
    this.filters = filters;
  }

  emitFilters(filters: any) {
    this.store$.dispatch(new fromWorkspaceStore.setUserTagsFilters({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      filters: filters
    }))
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

  assignPltsToTag($event: any) {
    const {
      selectedTags
    } = $event;
    console.log('TAGS', $event);
    this.store$.dispatch(new fromWorkspaceStore.createOrAssignTags({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      ...$event,
      selectedTags: _.map(selectedTags, (el: any) => el.tagId),
      unselectedTags: _.map(_.differenceBy(this.oldSelectedTags, selectedTags, 'tagId'), (e: any) => e.tagId),
      type: 'assignOrRemove'
    }))

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
    this.modalSelect = $event;
  }

  resetSort() {
    this.sortData = {};
  }

  resetFilters() {
    this.filterData = {};
    this.emitFilters({
      userTag: [],
      systemTag: []
    })
    this.userTags = _.map(this.userTags, t => ({...t, selected: false}))
    this.filters = {
      userTag: [],
      systemTag: {}
    }
    this.filterInput = "";
  }

  createTag($event: any) {
    this.store$.dispatch(new fromWorkspaceStore.createOrAssignTags({
      ...$event,
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      type: 'createTag'
    }))
  }
}
