import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  ElementRef,
  NgZone,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
  TemplateRef,
  ViewChild
} from '@angular/core';
import * as _ from 'lodash'
import {Select, Store} from "@ngxs/store";
import {
  applyAdjustment,
  deleteAdjsApplication,
  deleteAdjustment,
  dropAdjustment,
  extendPltSection,
  replaceAdjustement,
  saveAdjModification,
  saveAdjustment,
  saveAdjustmentApplication,
  saveAdjustmentInPlt
} from "../../store/actions";
import {first, map} from "rxjs/operators";
import {CalibrationState, PltMainState} from "../../store/states";
import {Observable} from "rxjs";
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from "ng-zorro-antd";
import {Table} from "primeng/table";
import * as fromWorkspaceStore from "../../store";
import {ActivatedRoute} from "@angular/router";


@Component({
  selector: 'app-workspace-calibration',
  templateUrl: './workspace-calibration.component.html',
  styleUrls: ['./workspace-calibration.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class WorkspaceCalibrationComponent implements OnInit, OnDestroy, OnChanges {
  searchAddress: string;
  listOfPlts: any[];
  listOfPltsData: any[];
  listOfPltsCache: any[];
  selectedListOfPlts: any[];
  listOfDeletedPlts: any[] = [];
  filterData: any;
  sortData;
  lastModifiedAdj;
  dataColumns = [];
  activeCheckboxSort: boolean;
  extended: boolean = true;
  inProgressCheckbox: boolean = true;
  checkedCheckbox: boolean = true;
  lockedCheckbox: boolean = true;
  collapsedTags: boolean = true;
  isVisible = false;
  singleValue: any;
  dragPlaceHolderId: any;
  dragPlaceHolderCol: any;
  dndBool: boolean = false;
  categorySelectedFromAdjustement: any;
  modalTitle: any;
  addAdjustement: any;
  modifyModal: any;
  idPlt: any;
  draggedAdjs: any;
  templateWidth = '120';
  tableWidth = '1200';
  @ViewChild('dt')
  @ViewChild('iconNote') iconNote: ElementRef;
  iconNotePosition: any;
  allAdjsArray: any[] = [];
  AdjustementType: any;
  categorySelected: any;
  adjsArray: any[] = [];
  appliedAdjutement = [];
  @Select(CalibrationState.getAdjustmentApplication) adjutmentApplication$;
  adjutmentApplication = [];
  singleValueArray: any[] = [];
  inputValueArray = [];
  columnPositionArray = [];
  linear: boolean = false;
  workspaceId: string;
  uwy: number;
  projects: any[];
  columnPosition: number;
  params: any;
  lastSelectedId;
  tagModalIndex: any = 0;
  mode = "calibration";
  @Select(CalibrationState) state$: Observable<any>;
  private currentDraggableEvent: DragEvent;
  pltColumns = [
    {
      sortDir: 1,
      fields: '',
      header: 'User Tags',
      width: '60',
      sorted: false,
      filtred: false,
      icon: null,
      type: 'checkbox',
      style: 'border: none !important',
      extended: true
    },
    {
      sortDir: 1,
      fields: 'pltId',
      header: 'PLT ID',
      width: '80',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      style: 'border: none !important',
      extended: true
    },
    {
      sortDir: 1,
      fields: 'pltName',
      header: 'PLT Name',
      width: '150',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      style: 'border: none !important',
      extended: true
    },
    {
      sortDir: 1,
      fields: 'peril',
      header: 'Peril',
      width: '60',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      style: 'border: none !important',
      extended: true
    },
    {
      sortDir: 1,
      fields: 'regionPerilCode',
      header: 'Region Peril Code',
      width: '80',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      style: 'border: none !important',
      extended: true
    },
    {
      sortDir: 1,
      fields: 'regionPerilName',
      header: 'Region Peril Name',
      width: '130',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      style: 'border: none !important',
      extended: true
    },
    {
      sortDir: 1,
      fields: 'grain',
      header: 'Grain',
      width: '160',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      style: 'border: none !important',
      extended: true
    },
    {
      sortDir: 1,
      fields: 'vendorSystem',
      header: 'Vendor System',
      width: '90',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      style: 'border: none !important',
      extended: true
    },
    {
      sortDir: 1,
      fields: 'rap',
      header: 'RAP',
      width: '70',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      style: 'border: none !important',
      extended: true
    },
    {
      sortDir: 1,
      fields: 'action',
      header: '',
      width: '25',
      sorted: false,
      filtred: false,
      icon: 'icon-focus-add',
      type: 'icon',
      style: 'border: none !important',
      extended: true
    },
    {
      sortDir: 1,
      fields: 'action',
      header: '',
      width: '25',
      sorted: false,
      filtred: false,
      icon: 'icon-note',
      type: 'icon',
      style: 'border: none !important',
      extended: true
    },
    {
      sortDir: 1,
      fields: 'overallLMF',
      header: 'Overall LMF',
      width: this.templateWidth,
      dragable: true,
      sorted: false,
      filtred: false,
      icon: null,
      type: 'field',
      style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
      extended: true
    },
    {
      sortDir: 1,
      fields: 'base',
      header: 'Base',
      width: this.templateWidth,
      dragable: true,
      sorted: false,
      filtred: false,
      icon: null,
      type: 'field',
      style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
      extended: true
    },
    {
      sortDir: 1,
      fields: 'default',
      header: 'Default',
      width: this.templateWidth,
      dragable: true,
      sorted: false,
      filtred: false,
      icon: null,
      type: 'field',
      style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
      extended: true
    },
    {
      sortDir: 1,
      fields: 'client',
      header: 'Client',
      width: this.templateWidth,
      dragable: true,
      sorted: false,
      filtred: false,
      icon: null,
      type: 'field',
      style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
      extended: true
    },
    {
      sortDir: 1,
      fields: 'inuring',
      header: 'Inuring',
      width: this.templateWidth,
      dragable: true,
      sorted: false,
      filtred: false,
      icon: null,
      type: 'field',
      style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
      extended: true
    },
    {
      sortDir: 1,
      fields: 'postInuring',
      header: 'Post-Inuring',
      width: this.templateWidth,
      dragable: true,
      sorted: false,
      filtred: false,
      icon: null,
      type: 'field',
      style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
      extended: true
    },
  ];
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
  }
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
  ]
  someItemsAreSelected: boolean;
  selectAll: boolean;
  drawerIndex: any;
  @Select(PltMainState.getUserTags) userTags$;
  @Select(PltMainState.getPlts) data$;
  @Select(PltMainState.getDeletedPlts) deletedPlts$;
  deletedPlts: any;
  loading: boolean;
  selectedUserTags: any;
  systemTagsMapping = {
    grouped: {
      regionPerilCode: 'Region Peril',
      currency: 'Currency',
      sourceModellingVendor: 'Modelling Vendor',
      sourceModellingSystem: 'Model System',
      targetRapCode: 'Target RAP',
      userOccurrenceBasis: 'User Occurence Basis',
      pltType: 'Loss Asset Type',
    },
    nonGrouped: {}
  };
  selectedPlt: any;
  addTagModalIndex: any;
  addTagModal: boolean;
  addModalInput: any;
  inputValue: any;
  addModalSelect: any;
  tagFormenu: any;
  fromPlts: any;
  colorPickerIsVisible: any;
  initColor: any;
  addTagModalPlaceholder: any;
  showDeleted: boolean;
  selectedItemForMenu: string;
  contextMenuItems = [
    {label: 'View Detail', icon: 'pi pi-search', command: (event) => this.openPltInDrawer(this.selectedPlt.pltId)},
    {
      label: 'Delete', icon: 'pi pi-trash', command: (event) => {
        this.store$.dispatch(new fromWorkspaceStore.deletePlt({pltId: this.selectedItemForMenu}))
      }
    },
    {
      label: 'Edit Tags', icon: 'pi pi-tags', command: (event) => {
        this.addTagModal = true;
        this.fromPlts = true;
        let d = [];

        _.forEach(this.listOfPltsData, (v, k) => {
          if (v.selected) d.push(v.userTags);
        })

        //this.selectedUserTags = _.keyBy(_.intersectionBy(...d, 'tagId'), 'tagId')

        this.addModalSelect = _.intersectionBy(...d, 'tagId');

      }
    }
  ];
  private dropdown: NzDropdownContextComponent;
  private Subscriptions: any[] = [];
  private table: Table;
  private pageSize: number = 20;
  private lastClick: string;
  private renamingTag: boolean;
  private modalInputCache: any;
  tagContextMenu = [
    {
      label: 'Delete Tag',
      icon: 'pi pi-trash',
      command: (event) => this.store$.dispatch(new fromWorkspaceStore.deleteUserTag(this.tagFormenu.tagId))
    }, {
      label: 'Rename Tag', icon: 'pi pi-pencil', command: (event) => {
        this.renamingTag = true;
        this.fromPlts = false;
        this.addModalInput = this.tagFormenu.tagName;
        this.modalInputCache = this.tagFormenu.tagName;
        this.addTagModal = true;
      }
    }
  ];

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
  }

  ngOnInit() {
    // console.log(this.iconNote.style.position);
    this.adjutmentApplication$.subscribe(data => this.adjutmentApplication = data);
    this.initDataColumns();
    this.extend();
    this.state$.subscribe((state: any) => {
      this.allAdjsArray = _.merge([], state.allAdjsArray);
      this.AdjustementType = _.merge([], state.adjustementType);
      this.adjsArray = _.merge([], state.adjustments);
    });
    this.Subscriptions.push(
      this.deletedPlts$.subscribe(d => {
        this.deletedPlts = d;
        this.detectChanges();

      }),
      this.data$.subscribe(data => {
        let d1 = [];
        let d2 = [];
        this.loading = false;
        this.systemTagsCount = {};

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
            })

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
        this.detectChanges();
      }),
      this.data$.subscribe(data => {
        this.selectAll = (this.selectedListOfPlts.length > 0 || (this.selectedListOfPlts.length == this.listOfPlts.length)) && this.listOfPltsData.length > 0;
        this.someItemsAreSelected = this.selectedListOfPlts.length < this.listOfPlts.length && this.selectedListOfPlts.length > 0;
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
        this.loading = true;
        this.store$.dispatch(new fromWorkspaceStore.loadAllPlts({
          params: {
            workspaceId: wsId, uwy: year
          }
        }));
      }),
      this.store$.select(PltMainState.getProjects()).subscribe((projects: any) => {
        this.projects = projects;
        this.detectChanges();
      }),
      this.getAttr('loading').subscribe(l => this.loading = l),
      this.userTags$.subscribe(userTags => {
        this.userTags = userTags || {};
        this.detectChanges();
      })
    );

  }

  initDataColumns() {
    this.dataColumns = [];
    _.forEach(this.pltColumns, (value, key) => {
      if (value.extended) {
        this.dataColumns.push(value);
      }
    });
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

  filter(key: string, value) {
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

  setFilter(filter: string, tag, section) {
    if (filter === 'userTag') {
      this.filters =
        _.findIndex(this.filters[filter], e => e == tag.tagId) < 0 ?
          _.merge({}, this.filters, {[filter]: _.merge([], this.filters[filter], {[this.filters[filter].length]: tag.tagId})}) :
          _.assign({}, this.filters, {[filter]: _.filter(this.filters[filter], e => e != tag.tagId)})

      this.userTags = _.map(this.userTags, t => t.tagId == tag.tagId ? {...t, selected: !t.selected} : t)

      this.store$.dispatch(new fromWorkspaceStore.setUserTagsFilters({
        filters: this.filters
      }))
    } else {
      const {
        systemTag
      } = this.filters;
      /*this.filters =
        _.findIndex(systemTag, e => e == tag.tagId) < 0 ?
          _.merge({},
            this.filters, {
            systemTag: _.merge([], systemTag, {
              [systemTag.length] : {section, }
            } )
          }) :
          _.assign({}, this.filters, {systemTag: _.filter(systemTag, e => e.section != section)})*/

      this.filters = _.findIndex(systemTag, sectionFilter => sectionFilter[tag] === section) < 0 ?
        _.merge({}, this.filters, {
          systemTag: _.merge([], systemTag, {
            [systemTag.length]: {[tag]: section}
          })
        }) :
        _.assign({}, this.filters, {systemTag: _.filter(systemTag, sectionFilter => sectionFilter[tag] != section)})
    }
  }

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

  openPltInDrawer(plt) {
    this.closePltInDrawer(this.sumnaryPltDetailsPltId);
    this.store$.dispatch(new fromWorkspaceStore.OpenPLTinDrawer({pltId: plt}));
    this.openDrawer(1);
    this.getTagsForSummary();
  }

  getColor(id, type) {
    let item;
    if (type === 'system') {
      item = this.systemTags.filter(tag => tag.tagId == id);
    } else {
      item = this.userTags.filter(tag => tag.tagId == id);
    }
    if (item.length > 0)
      return item[0].tagColor;
    return null;
  }

  getDragableColor(type) {
    switch (type) {
      case 'overallLMF':
        return '#F3F3F3';
      case 'default':
        return '#F3F3F3';
      case 'inuring':
        return '#FAEBD7';
      default:
        return 'none';

    }
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
        _.map(!this.showDeleted ? this.listOfPlts : this.listOfDeletedPlts, plt => plt),
        _.range(!this.showDeleted ? this.listOfPlts.length : this.listOfDeletedPlts.length).map(el => ({type: !this.selectAll && !this.someItemsAreSelected}))
      )
    );
  }

  toggleSelectPlts(plts: any) {
    this.store$.dispatch(new fromWorkspaceStore.ToggleSelectPlts({plts, forDeleted: this.showDeleted}));
  }

  selectSinglePLT(pltId: number, $event: boolean) {
    this.toggleSelectPlts({
      [pltId]: {
        type: $event
      }
    });
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
          _.map(!this.showDeleted ? this.listOfPlts : this.listOfDeletedPlts, plt => ({type: plt == pltId && (this.lastClick == 'withKey' || !isSelected)}))
        )
      );
      this.lastClick = null;
    }
  }
  selectProject(id: any) {

  }

  toDate(d) {
    return new Date(d);
  }

  handleOk() {

  }

  onSort($event: any) {
    console.log($event);
    const {
      multisortmeta
    } = $event;

    /*if(_.find(multisortmeta, col => col.field == 'selected' && col.order == 1)){
      this.table.reset();
    }
    this.multiSortMeta = multisortmeta;*/
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

  handlePopUpConfirm() {
    if (this.renamingTag) {
      if (this.addModalInput != this.modalInputCache) {
        this.store$.dispatch(new fromWorkspaceStore.editTag({
          ...this.tagFormenu,
          tagName: this.addModalInput
        }))
      }
    } else {
      if (this.addTagModalIndex === 1) {
        this.store$.dispatch(new fromWorkspaceStore.assignPltsToTag({
          plts: this.selectedListOfPlts,
          wsId: this.workspaceId,
          uwYear: this.uwy,
          tags: this.addModalSelect,
          type: 'many'
        }))
      }

      if (this.addTagModalIndex === 0) {
        this.store$.dispatch(new fromWorkspaceStore.assignPltsToTag({
          plts: this.fromPlts ? this.selectedListOfPlts : [],
          wsId: this.workspaceId,
          uwYear: this.uwy,
          tag: {
            tagName: this.addModalInput,
            tagColor: this.initColor
          }
        }))
      }
    }

    this.addTagModal = false;
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

  selectSystemTag(section, tag) {

    _.forEach(this.systemTagsCount, (s, sKey) => {
      _.forEach(s, (t, tKey) => {
        if (tag == tKey && section == sKey) {
          this.systemTagsCount[sKey][tKey] = {...t, selected: !t.selected}
          console.log(this.systemTagsCount[sKey][tKey]);
        } else {
          this.systemTagsCount[sKey][tKey] = {...t, selected: false}
          console.log(this.systemTagsCount[sKey][tKey]);
        }
      })
    })
  }

  toggleModal() {
    this.addTagModal = false;
    if (!this.addTagModal) {
      this.addModalInput = null;
      this.addModalSelect = null;
      this.addTagModalIndex = 0;
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

  initColorPicker() {
    this.colorPickerIsVisible = false;
    this.initColor = '#fe45cd'
  }

  sortChange(field: any, sortCol: any) {
    console.log(field, sortCol)
    if (!sortCol) {
      this.sortData[field] = 'asc';
    } else if (sortCol === 'asc') {
      this.sortData[field] = 'desc';
    } else if (sortCol === 'desc') {
      this.sortData[field] = null
    }
  }

  handlePopUpCancel() {
    this.addTagModal = false;
    this.addModalInput = '';
    this.addModalSelect = '';
  }

  extend() {
    this.extended = !this.extended;
    this.adjustExention();
    this.initDataColumns();
    this.store$.dispatch(new extendPltSection(this.extended));
  }

  adjustExention() {
    if (this.extended) {
      _.forIn(this.pltColumns, function (value: any, key) {
        value.extended = true;
      })
    } else {
      _.forIn(this.pltColumns, function (value: any, key) {
        if (value.header == "User Tags" || value.fields == "pltId" || value.fields == "pltName" || value.fields == "action" || value.dragable) {
          value.extended = true;
        } else {
          value.extended = false;
        }

      })
    }
  }

  clickButtonPlus(bool, data?: any) {
    this.modalTitle = "Add New Adjustment";
    this.modifyModal = false;
    this.categorySelectedFromAdjustement = null;
    this.inputValue = '';
    this.singleValue = null;
    if (!bool) {
      this.idPlt = data.pltId;
      this.addAdjustement = true;
    } else {
      this.addAdjustement = false;
    }
    this.isVisible = true;
  }

  handleCancel(): void {
    console.log('Button cancel clicked!');
    this.isVisible = false;
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.iconNotePosition = this.iconNote.nativeElement.style.position;
    console.log('change', changes);
    console.log(this.iconNote.nativeElement.style);
  }

  addAdjustmentFromPlusIcon(boolAdj, adjustementType?, adjustement?) {
    if (this.addAdjustement) {
      if (boolAdj) {
        this.isVisible = false;
      }
      this.store$.dispatch(new saveAdjustmentInPlt({
        adjustementType: this.singleValue,
        adjustement: adjustement,
        columnPosition: this.columnPosition,
        pltId: this.idPlt,
      }));
    } else {
      if (boolAdj) {
        this.isVisible = false;
      }
      console.log(this.columnPosition);
      this.store$.dispatch(new saveAdjustment({
        adjustementType: this.singleValue,
        adjustement: adjustement,
        columnPosition: this.columnPosition
      }));
      console.log('here 2')
    }
    this.adjustColWidth(adjustement);
  }

  selectCategory(p) {
    this.categorySelectedFromAdjustement = p;
    this.categorySelected = p.category;
    console.log(p);
  }

  selectBasis(adjustement) {
    if (adjustement.id == 1) {
      this.linear = true;
    } else {
      this.linear = false;
    }
  }

  saveAdjustementApplicaiton(pltId, adj) {
    this.appliedAdjutement.push({
      pltId: pltId,
      adj: adj
    });
    this.store$.dispatch(new saveAdjustmentApplication(this.appliedAdjutement));
  }

  filterArr(data, key) {
    return data.reduce((result, current) => {
      if (!result[current[key]]) {
        result[current[key]] = 1;
      } else {
        result[current[key]] += 1;
      }
      return result;
    }, {})
  }

  adjustColWidth(adj, dndDragover = 0) {
    this.adjutmentApplication$.pipe(first()).subscribe(data => {
      const colWidth = 110 * Math.max(..._.values(this.filterArr(_.filter(data, item =>
        item.adj.category === adj.category
      ), "pltId"))) + dndDragover;
      let index = _.findIndex(this.pltColumns, {fields: adj.category.toLowerCase()});
      this.pltColumns[index].width = colWidth.toString();
    });
  }
  applyToAll(adj) {
    console.log('applytoall');
    this.store$.dispatch(new applyAdjustment({
      adjustementType: this.singleValue,
      adjustement: adj,
      columnPosition: this.columnPosition,
      pltId: this.listOfPlts,
    }));
    this.adjustColWidth(adj);
  }

  replaceToAllAdjustement(adj) {
    this.store$.dispatch(new replaceAdjustement({
      adjustementType: this.singleValue,
      adjustement: adj,
      columnPosition: this.columnPosition,
      pltId: this.listOfPlts,
      all: true
    }));
    this.adjustColWidth(adj);
  }

  replaceToSelectedPlt(adj) {
    this.store$.dispatch(new replaceAdjustement({
      adjustementType: this.singleValue,
      adjustement: adj,
      columnPosition: this.columnPosition,
      pltId: this.selectedListOfPlts,
      all: false
    }));
    this.adjustColWidth(adj);
  }

  deleteAdjs(adj, pltId) {
    this.store$.dispatch(new deleteAdjsApplication({
      adjustement: adj,
      pltId: pltId
    }));
    this.adjustColWidth(adj);
  }

  applyToSelected(adj) {
    console.log('applytoselected');

    this.store$.dispatch(new applyAdjustment({
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
      this.singleValue = _.find(this.AdjustementType, {name: adj.value});
    } else {
      this.singleValue = _.find(this.AdjustementType, {name: "Linear"});
      this.columnPosition = adj.value;
    }
    this.isVisible = true
    this.adjustColWidth(adj);
  }

  DeleteAdjustement(adj) {
    this.store$.dispatch(new deleteAdjustment({
      adjustment: adj
    }))
  }

  saveAdjModification(adj) {
    adj.id = this.lastModifiedAdj;
    this.store$.dispatch(new saveAdjModification({
      adjustementType: this.singleValue,
      adjustement: adj,
      columnPosition: this.columnPosition
    }));
    this.isVisible = false;
  }

  onChangeAdjValue(adj, event) {
    adj.value = event.target.value;
    this.columnPosition = event.target.value;
    this.store$.dispatch(new saveAdjModification({
      adjustementType: this.singleValue,
      adjustement: adj,
      columnPosition: this.columnPosition
    }));
  }


  onDrop(pltId) {
    this.store$.dispatch(new dropAdjustment({
      pltId: pltId,
      adjustement: this.draggedAdjs
    }))
    this.adjustColWidth(this.draggedAdjs);
    this.dragPlaceHolderCol = null;
    this.dragPlaceHolderId = null;
  }

  onDragged(item: any) {
    this.deleteAdjs(item.adj, item.pltId);
  }

  onDragEnd(event, adj) {

  }

  onDragStart(event, data: any) {
    console.log(data);
    this.draggedAdjs = data;
  }

  ondragEnter(col, pltId) {
    console.log('ondragEnter');
    const width = +col.width;
    col.width = (width + 40).toString();
    this.dragPlaceHolderId = pltId;
    this.dragPlaceHolderCol = col.fields
  }

  ondragLeave(col) {
    console.log('ondragLeave');
    const width = +col.width;
    col.width = (width - 40).toString();
    this.dragPlaceHolderId = null;
    this.dragPlaceHolderCol = null;
  }
  private handlePLTClickWithKey(pltId: number, i: number, isSelected: boolean, $event: MouseEvent) {
    if ($event.ctrlKey) {
      this.selectSinglePLT(pltId, isSelected);
      this.lastSelectedId = i;
      return;
    }

    if ($event.shiftKey) {
      console.log(i, this.lastSelectedId);
      if (!this.lastSelectedId) this.lastSelectedId = 0;
      if (this.lastSelectedId || this.lastSelectedId == 0) {
        const max = _.max([i, this.lastSelectedId]);
        const min = _.min([i, this.lastSelectedId]);
        this.toggleSelectPlts(
          _.zipObject(
            _.map(this.listOfPlts, plt => plt),
            _.map(this.listOfPlts, (plt, i) => (i <= max && i >= min ? ({type: 'select'}) : ({type: 'unselect'}))),
          )
        );
      } else {
        this.lastSelectedId = i;
      }
      return;
    }
  }

  collapseTags() {
    this.collapsedTags = !this.collapsedTags;
  }
}
