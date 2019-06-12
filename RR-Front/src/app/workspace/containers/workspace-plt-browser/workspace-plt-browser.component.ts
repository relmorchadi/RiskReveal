import {ChangeDetectionStrategy, ChangeDetectorRef, Component, NgZone, OnDestroy, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import * as _ from 'lodash';
import {Select, Store} from '@ngxs/store';
import * as fromWorkspaceStore from '../../store';
import {pltMainModel, PltMainState} from '../../store';
import {map, mergeMap, tap} from 'rxjs/operators';
import {ActivatedRoute} from '@angular/router';
import {Table} from 'primeng/table';

@Component({
  selector: 'app-workspace-plt-browser',
  templateUrl: './workspace-plt-browser.component.html',
  styleUrls: ['./workspace-plt-browser.component.scss']
})
export class WorkspacePltBrowserComponent implements OnInit,OnDestroy {

  private dropdown: NzDropdownContextComponent;
  private Subscriptions: any[] = [];
  searchAddress: string;
  listOfPlts: any[];
  listOfPltsData: any[];
  listOfPltsCache: any[];
  selectedListOfPlts: any[];
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
    { label: 'View Detail', icon: 'pi pi-search', command: (event) => this.openPltInDrawer(this.selectedPlt.pltId) },
    { label: 'Delete', icon: 'pi pi-trash', command: (event) =>
        this.store$.dispatch(new fromWorkspaceStore.deletePlt({pltId : this.selectedItemForMenu}))
    },
    { label: 'Edit Tags', icon: 'pi pi-tags', command: (event) => {
        this.addTagModal= true;
        this.fromPlts= true;
        let d= [];

        _.forEach( this.listOfPltsData, (v,k) => {
          if(v.selected) d.push(v.userTags);
        })

        //this.selectedUserTags = _.keyBy(_.intersectionBy(...d, 'tagId'), 'tagId')

        this.addModalSelect = _.intersectionBy(...d, 'tagId');

      }
    }
  ];

  tagContextMenu = [
    { label: 'Delete Tag', icon: 'pi pi-trash', command: (event) => this.store$.dispatch(new fromWorkspaceStore.deleteUserTag(this.tagFormenu.tagId))},
    { label: 'Rename Tag', icon: 'pi pi-pencil', command: (event) => {
        this.renamingTag= true;
        this.fromPlts = false;
        this.addModalInput = this.tagFormenu.tagName;
        this.modalInputCache= this.tagFormenu.tagName;
        this.addTagModal= true;
      }}
  ];

  pltColumns = [
    {sortDir: 1,fields: '', header: 'User Tags', width: '60px', sorted: false, filtred: false, icon: null, type: 'checkbox'},
    {sortDir: 1,fields: 'pltId', header: 'PLT ID', width: '65px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: 'pltName', header: 'PLT Name', width: '140px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: 'peril', header: 'Peril', width: '55px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: 'regionPerilCode', header: 'Region Peril Code', width: '75px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: 'regionPerilName', header: 'Region Peril Name', width: '130px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: 'grain', header: 'Grain', width: '160px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: 'deletebBy', header: 'Deleted By', width: '70px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: 'deletebAt', header: 'Deleted At', width: '70px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: 'vendorSystem', header: 'Vendor System', width: '60px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: 'rap', header: 'RAP', width: '70px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1,fields: '', header: '', width: '25px', sorted: false, filtred: false, icon: 'icon-focus-add', type: 'icon'},
    {sortDir: 1,fields: '', header: '', width: '25px', sorted: false, filtred: false, icon: 'icon-note', type: 'icon'},
    {sortDir: 1,fields: '', header: '', width: '25px', sorted: false, filtred: false, icon: 'icon-focus-add', type: 'icon'},
    ];

  epMetricsCurrencySelected: any = 'EUR';
  CalibrationImpactCurrencySelected: any = 'EUR';
  epMetricsFinancialUnitSelected: any = 'Million';
  CalibrationImpactFinancialUnitSelected: any = 'Million';

  currentPath:any = null;

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
  private pageSize: number = 20;
  private lastClick: string;
  private renamingTag: boolean;
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
    this.sortData= {};
    this.activeCheckboxSort = false;
    this.loading = true;
    this.addTagModal = false;
    this.tagModalIndex= 0;
    this.systemTagsCount= {};
    this.userTagsCount= {};
    this.fromPlts = false;
    this.renamingTag= false;
    this.selectedUserTags= {};
    this.initColor = '#fe45cd'
    this.colorPickerIsVisible = false;
    this.addTagModalPlaceholder = 'Select a Tag';
    this.showDeleted= false;
  }

  @Select(PltMainState.getUserTags) userTags$;
  @Select(PltMainState.getPlts) data$;
  @Select(PltMainState.getDeletedPlts) deletedPlts$;
  deletedPlts: any;
  loading: boolean;
  selectedUserTags: any;

  systemTagsMapping = {
    grouped: {
      peril: 'Peril',
      regionPerilCode: 'Region',
      currency: 'Currency',
      sourceModellingVendor: 'Modelling Vendor',
      sourceModellingSystem: 'Model System',
      targetRapCode: 'Target RAP',
      userOccurrenceBasis: 'User Occurence Basis',
      pltType: 'Loss Asset Type',
    },
    nonGrouped: {
    }
  };

  ngOnInit() {
    this.Subscriptions.push(
      this.deletedPlts$.subscribe( d => {
        this.deletedPlts= d;
        console.log('deleted',d);
        this.detectChanges();
      }),
      this.data$.subscribe( data => {
        let d1= [];
        let d2= [];
        this.loading= false;
        this.systemTagsCount = {};

        if(_.keys(this.systemTagsCount).length == 0 ) {
          _.forEach(data, (v,k) => {
            //Init Tags Counters

            //Grouped Sys Tags
            _.forEach(this.systemTagsMapping.grouped, (sectionName,section) => {
              this.systemTagsCount[sectionName] = this.systemTagsCount[sectionName] || {};
              const tag = _.toString(v[section]);
              if(tag){
                this.systemTagsCount[sectionName][tag] = {selected: false,count:0}
              }
            })

            //NONE grouped Sys Tags
            _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
              this.systemTagsCount[sectionName] = this.systemTagsCount[sectionName] || {};
              this.systemTagsCount[sectionName][section] = {selected: false,count:0};
              this.systemTagsCount[sectionName]['non-'+section] = {selected: false,count:0};
            })

          })
        }

        _.forEach(data, (v,k) => {
          d1.push({...v,pltId: k});
          d2.push(k);

          if(v.visible) {
            //Grouped Sys Tags
            _.forEach(this.systemTagsMapping.grouped, (sectionName,section) => {
              const tag = _.toString(v[section]);
              if(tag){
                if( this.systemTagsCount[sectionName][tag] || this.systemTagsCount[sectionName][tag].count === 0){
                  this.systemTagsCount[sectionName][tag] = {...this.systemTagsCount[sectionName][tag],count: this.systemTagsCount[sectionName][tag].count + 1};
                }
              }
            })

            //NONE grouped Sys Tags
            _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
              const tag = v[section];
              if(this.systemTagsCount[sectionName][section] || this.systemTagsCount[sectionName][section] == 0){
                this.systemTagsCount[sectionName][section] = {...this.systemTagsCount[sectionName][section],count: this.systemTagsCount[sectionName][section].count + 1};
              }
              if(this.systemTagsCount[sectionName]['non-'+section] || this.systemTagsCount[sectionName]['non-'+section].count == 0){
                this.systemTagsCount[sectionName]['non-'+section] = {...this.systemTagsCount[sectionName]['non-'+section],count: this.systemTagsCount[sectionName]['non-'+section].count + 1};
              }
            })
          }

        });
        this.listOfPlts = d2;
        this.listOfPltsData = this.listOfPltsCache = d1;
        this.selectedListOfPlts = _.filter(d2, k => data[k].selected);
        this.detectChanges();
      }),
      this.data$.subscribe( data => {
        this.selectAll = (this.selectedListOfPlts.length > 0 || (this.selectedListOfPlts.length == this.listOfPlts.length)) && this.listOfPltsData.length > 0 ;
        this.someItemsAreSelected = this.selectedListOfPlts.length < this.listOfPlts.length && this.selectedListOfPlts.length > 0;
        this.detectChanges();
      }),
      this.data$.subscribe( data => {
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
          this.projects = projects;
          this.detectChanges();
        }),
      this.getAttr('loading').subscribe( l => this.loading = l),
      this.userTags$.subscribe( userTags => {
        this.userTags = userTags || {};
        this.detectChanges();
      })
    );
  }

  getAttr(path) {
    return this.store$.select(PltMainState.getAttr).pipe(map( fn => fn(path)));
  }

  /*sort(sort: { key: string, value: string }): void {
    let sortField = sort.key;
    let sortOrder = sort.value;
    if (sortField && sortOrder) {
      this.sortMap[sort.key] = sort.key;
      (sortOrder === 'ascend') ? sortOrder = 'asc' : sortOrder = 'desc';
      this.params = {
        ...this.params,
        pageNumber: 0,
        pageSize: this.pageSize,
        sort: sortField + "," + sortOrder,
      };
      //this.loadData(this.params);
    } else {
      this.params = {...this.params, sort: 'pltId,desc', sortCompany: '', pageSize: this.pageSize};
      //this.loadData(this.params);
    }
    this.loadData(this.params)
  }

  filter = _.debounce( (key: string, value) => {
    if(value){
      this.params= _.merge({},this.params, {[key]: value })
    }else{
      this.params= _.omit(this.params, [key])
    }
    this.loadData(this.params)
  },500);*/

  sort(sort: { key: string, value: string }): void {
    if (sort.value) {
      this.sortData = _.merge({}, this.sortData, {
        [sort.key]: sort.value === 'descend' ? 'desc' : 'asc'
      });
    } else {
      this.sortData = _.omit(this.sortData, [sort.key]);
    }
  }

  filter({key, filterData, projectId}) {
    if (key == 'project') {
      this.projects = _.map(this.projects, t => {
        if(t.projectId == projectId){
            return ({...t,selected: !t.selected})
        }else if(t.selected) {
          return ({...t,selected: false})
        }else return t;
      })
    }
    this.filterData= filterData;
  }

  selectedPlt: any;
  tagModalIndex: any;
  addTagModal: boolean;
  addModalInput: any;
  inputValue: null;
  addModalSelect: any;
  tagFormenu: any;
  fromPlts: any;
  colorPickerIsVisible: any;
  initColor: any;
  addTagModalPlaceholder: any;
  showDeleted: boolean;
  selectedItemForMenu: string;

  setFilter(filter: string, tag,section) {
      if(filter === 'userTag'){
        this.filters =
          _.findIndex(this.filters[filter], e => e == tag.tagId) < 0 ?
            _.merge({}, this.filters, { [filter]: _.merge([], this.filters[filter], {[this.filters[filter].length] : tag.tagId} ) }) :
            _.assign({}, this.filters, {[filter]: _.filter(this.filters[filter], e => e != tag.tagId)})

        this.userTags = _.map(this.userTags, t => t.tagId == tag.tagId ? {...t,selected: !t.selected} : t)

        this.store$.dispatch(new fromWorkspaceStore.setFilterPlts({
          filters: this.filters
        }))
      }else{
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

        this.filters = _.findIndex(systemTag, sectionFilter => sectionFilter[tag] === section ) < 0 ?
          _.merge({},this.filters,{
            systemTag: _.merge([], systemTag, {
              [systemTag.length]: { [tag] : section }
            })
          }):
          _.assign({}, this.filters,{ systemTag: _.filter( systemTag, sectionFilter => sectionFilter[tag] != section)})
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
    this.store$.dispatch(new fromWorkspaceStore.setFilterPlts({filters: this.filters}));
  }

  resetPath(){
    this.filterData = _.omit(this.filterData, 'project')
    this.projects = _.map(this.projects, p => ({...p, selected: false}))
    this.showDeleted= false;
  }

  setSelectedProjects($event){
    this.projects= $event;
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

  checkAll() {
    this.toggleSelectPlts(
      _.zipObject(
        _.map(this.listOfPlts, plt => plt),
        _.range(this.listOfPlts.length).map(el => ({type : !this.selectAll && !this.someItemsAreSelected ? 'select' : 'unselect'}))
      )
    );
  }

  toggleSelectPlts(plts: any) {
    this.store$.dispatch(new fromWorkspaceStore.ToggleSelectPlts({plts}));
  }

  selectSinglePLT($event) {
    this.toggleSelectPlts($event);
  }

  handleOk(){

  }

  checkBoxSort($event) {
    this.listOfPltsData= $event;
  }

  renameTag($event){
    this.store$.dispatch(new fromWorkspaceStore.renameTag($event))
  }

  selectUserTag(k) {
    event.stopPropagation();
    event.preventDefault();
    if(_.find(this.selectedUserTags, t => t.tagId == k)){
      this.selectedUserTags = _.omit(this.selectedUserTags,k);
    }else{
      this.selectedUserTags[k] = this.userTags[k];
    }
    const l = _.toArray(this.selectedUserTags).length;

    if(l == 0) {
      //this.addTagModalPlaceholder= {'Select a Tag': {tagName: 'Select a Tag'}}
      this.addTagModalPlaceholder= 'Seleetc'
    }
    if(l == 1 ) {
      //this.addTagModalPlaceholder = _.toArray(this.selectedUserTags)[0]
      this.addTagModalPlaceholder= 'hey'
    }
    if(l > 1) {
      //this.addTagModalPlaceholder = {'multiple': {tagName: 'multiple'}}
      this.addTagModalPlaceholder= 'multiple'
    }
    //this.addModalSelect = this.userTags[k];
    console.log(this.addTagModalPlaceholder)

  }

  selectSystemTag({section, tag}) {

    _.forEach(this.systemTagsCount, (s,sKey) => {
      _.forEach(s, (t,tKey) => {
        this.systemTagsCount[sKey][tKey] = tag == tKey && section == sKey ? {...t,selected: !t.selected} : {...t,selected: false};
      })
    })
  }

  toggleModal(){
    this.addTagModal = false;
    if(!this.addTagModal){
      this.addModalInput=null;
      this.addModalSelect=null;
      this.tagModalIndex=0;
      this.renamingTag= false;
    }
  }

  toggleColorPicker(from?: string){
    if(from == 'color') {
      event.stopPropagation();
      event.preventDefault();
    }
    this.colorPickerIsVisible=!this.colorPickerIsVisible;
    if(!this.colorPickerIsVisible) this.initColor= '#fe45cd';
  }

  initColorPicker(){
    this.colorPickerIsVisible = false;
    this.initColor = '#fe45cd'
  }

  sortChange(field: any, sortCol: any) {
    console.log(field,sortCol)
    if(!sortCol){
      this.sortData[field] = 'asc';
    }else if(sortCol === 'asc'){
      this.sortData[field] = 'desc';
    } else if(sortCol === 'desc') {
      this.sortData = _.omit(this.sortData, `${field}`)
    }
  }

  handlePopUpCancel() {
    this.addTagModal=false;
    this.addModalInput='';
    this.addModalSelect='';
    this.renamingTag= false;
  }

  setSelectedMenuItem($event: any) {
    this.selectedItemForMenu= $event;
  }
  
  setFilters($event){
    this.filters= $event;
  }

  setFromPlts($event){
    this.fromPlts= $event;
  }
  
  setSysTags($event){
    this.systemTags= $event;
  }
  
  setUserTags($event){
    this.userTags= $event;
  }

  setModalIndex($event){
    this.tagModalIndex= $event;
  }

  assignPltsToTag($event: any) {
    console.log($event)
    this.store$.dispatch(new fromWorkspaceStore.assignPltsToTag($event))
  }

  setTagModal($event: any) {
    this.addTagModal= $event;
  }

  setTagForMenu($event: any) {
    this.tagFormenu=$event;
  }

  setRenameTag($event: any) {
    this.renamingTag = $event;
  }
}


