import {ChangeDetectionStrategy, ChangeDetectorRef, Component, NgZone, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import * as _ from 'lodash';
import {Select, Store} from '@ngxs/store';
import * as fromWorkspaceStore from '../../store';
import {PltMainState} from '../../store';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-workspace-plt-browser',
  templateUrl: './workspace-plt-browser.component.html',
  styleUrls: ['./workspace-plt-browser.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class WorkspacePltBrowserComponent implements OnInit,OnDestroy {

  private dropdown: NzDropdownContextComponent;
  private Subscriptions: any[] = [];
  searchAddress: string;
  listOfPlts: any[];
  selectedListOfPlts: any[];
  sortData;
  params: any;
  sortMap = {
    pltId: null,
    systemTags: null,
    userTags: null,
    pathId: null,
    pltName: null,
    peril: null,
    regionPerilCode: null,
    regionPerilName: null,
    selected: null,
    grain: null,
    vendorSystem: null,
    rap: null,
    d: null,
    note: null,
  };
  lastSelectedId;

  pltColumns = [
    {fields:'' , header:'User Tags' , width: '4%', sorted: false, filtred: false, icon: null},
    {fields:'pltId' , header:'PLT ID' , width: '9%', sorted: true, filtred: true, icon: null},
    {fields:'pltName' , header:'PLT Name' , width: '20%', sorted: true, filtred: true, icon: null},
    {fields:'peril' , header:'Peril' , width: '8%', sorted: true, filtred: true, icon: null},
    {fields:'regionPerilCode' , header:'Region Peril Code' , width: '10%', sorted: true, filtred: true, icon: null},
    {fields:'regionPerilName' , header:'Region Peril Name' , width: '10%', sorted: true, filtred: true, icon: null},
    {fields:'grain' , header:'Grain' , width: '10%', sorted: true, filtred: true, icon: null},
    {fields:'vendorSystem' , header:'Vendor System' , width: '9%', sorted: true, filtred: true, icon: null},
    {fields:'rap' , header:'RAP' , width: '9%', sorted: true, filtred: true, icon: null},
    {fields:'' , header:'' , width: '3%', sorted: false, filtred: false, icon: "icon-focus-add"},
    {fields:'' , header:'' , width: '3%', sorted: false, filtred: false, icon: "icon-note"},
    {fields:'' , header:'' , width: '3%', sorted: false, filtred: false, icon: "icon-focus-add"},
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
  sumnaryPltDetailsPltId: any = null;

  epMetricInputValue: string | null;

  pltdetailsSystemTags: any = [];
  pltdetailsUserTags: any = [];

  systemTags = [
    {tagId: '1', tagName: 'TC', tagColor: '#7bbe31', innerTagContent: '1', innerTagColor: '#a2d16f', selected: false},
    {
      tagId: '2',
      tagName: 'NATC-USM',
      tagColor: '#7bbe31',
      innerTagContent: '2',
      innerTagColor: '#a2d16f',
      selected: false
    },
    {
      tagId: '3',
      tagName: 'Post-Inured',
      tagColor: '#006249',
      innerTagContent: '9',
      innerTagColor: '#4d917f',
      selected: false
    },
    {
      tagId: '4',
      tagName: 'Pricing',
      tagColor: '#009575',
      innerTagContent: '0',
      innerTagColor: '#4db59e',
      selected: false
    },
    {
      tagId: '5',
      tagName: 'Accumulation',
      tagColor: '#009575',
      innerTagContent: '2',
      innerTagColor: '#4db59e',
      selected: false
    },
    {
      tagId: '6',
      tagName: 'Default',
      tagColor: '#06b8ff',
      innerTagContent: '1',
      innerTagColor: '#51cdff',
      selected: false
    },
    {
      tagId: '7',
      tagName: 'Non-Default',
      tagColor: '#f5a623',
      innerTagContent: '0',
      innerTagColor: '#f8c065',
      selected: false
    },
  ];

  userTags = [
    {tagId: '1', tagName: 'Pricing V1', tagColor: '#893eff', innerTagContent: '1', innerTagColor: '#ac78ff', selected: false},
    {tagId: '2', tagName: 'Pricing V2', tagColor: '#06b8ff', innerTagContent: '2', innerTagColor: '#51cdff', selected: false},
    {tagId: '3', tagName: 'Final Princing', tagColor: '#c38fff', innerTagContent: '5', innerTagColor: '#d5b0ff', selected: false}
  ];

  paths = [
    {id: 1, icon: 'icon-assignment_24px', text: 'P-1686 Ameriprise 2018', selected: false},
    {id: 2, icon: 'icon-assignment_24px', text: 'P-1724 Trinity Kemper 2018', selected: false },
    {id: 3, icon: 'icon-assignment_24px', text: 'P-8592 TWIA 2018', selected: false },
    {id: 4, icon: 'icon-assignment_24px', text: 'P-9035 Post-insured PLTs', selected: false },
    {id: 5, icon: 'icon-sitemap', text: 'IP-1135 CXL2 Cascading 2018', selected: false }
  ];

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
    {id: '1', label: 'Thousands'},
    {id: '2', label: 'Million'},
    {id: '3', label: 'Billion'}
  ];

  metrics = [
    {
      metricID: '1',
      retrunPeriod: '10000',
      OEP: '291.621,790',
      AEP: '291.621,790',
      TVaR_OEP: '321.654.789',
      TVaR_AEP: '458.711.620'
    },
    {
      metricID: '2',
      retrunPeriod: '5.000',
      OEP: '291.621,790',
      AEP: '291.621,790',
      TVaR_OEP: '321.654.789',
      TVaR_AEP: '458.711.620'
    },
    {
      metricID: '4',
      retrunPeriod: '1.000',
      OEP: '291.621,790',
      AEP: '291.621,790',
      TVaR_OEP: '321.654.789',
      TVaR_AEP: '458.711.620'
    },
    {
      metricID: '5',
      retrunPeriod: '500',
      OEP: '291.621,790',
      AEP: '291.621,790',
      TVaR_OEP: '321.654.789',
      TVaR_AEP: '458.711.620'
    },
    {
      metricID: '6',
      retrunPeriod: '250',
      OEP: '291.621,790',
      AEP: '291.621,790',
      TVaR_OEP: '321.654.789',
      TVaR_AEP: '458.711.620'
    },
    {
      metricID: '7',
      retrunPeriod: '100',
      OEP: '291.621,790',
      AEP: '291.621,790',
      TVaR_OEP: '321.654.789',
      TVaR_AEP: '458.711.620'
    },
    {
      metricID: '8',
      retrunPeriod: '50',
      OEP: '291.621,790',
      AEP: '291.621,790',
      TVaR_OEP: '321.654.789',
      TVaR_AEP: '458.711.620'
    },
    {
      metricID: '9',
      retrunPeriod: '25',
      OEP: '291.621,790',
      AEP: '291.621,790',
      TVaR_OEP: '321.654.789',
      TVaR_AEP: '458.711.620'
    },
    {
      metricID: '10',
      retrunPeriod: '10',
      OEP: '291.621,790',
      AEP: '291.621,790',
      TVaR_OEP: '321.654.789',
      TVaR_AEP: '458.711.620'
    },
    {
      metricID: '11',
      retrunPeriod: '5',
      OEP: '291.621,790',
      AEP: '291.621,790',
      TVaR_OEP: '321.654.789',
      TVaR_AEP: '458.711.620'
    },
    {
      metricID: '12',
      retrunPeriod: '2',
      OEP: '291.621,790',
      AEP: '291.621,790',
      TVaR_OEP: '321.654.789',
      TVaR_AEP: '458.711.620'
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
    {id: 1, title: 'ETL', content: 'RDM: CC_IT1607_XYZ_Surplus_R', chip: 'Analisis ID: 149'},
    {id: 2, title: 'PTL', content: 'ID 9867', chip: 'Pure PLT'},
    {id: 2, title: 'PTL', content: 'ID 9888', chip: 'Thead PLT'},
    {id: 2, title: 'PTL', content: 'ID 9901', chip: 'Cloned PLT'}
  ]
  someItemsAreSelected: boolean;
  selectAll: boolean;
  drawerIndex: any;
  private pageSize: number = 20;


  constructor( private nzDropdownService: NzDropdownService, private store$: Store,private zone: NgZone, private cdRef: ChangeDetectorRef) {
    this.someItemsAreSelected= false;
    this.selectAll= false;
    this.listOfPlts= [];
    this.selectedListOfPlts= [];
    this.lastSelectedId = null;
    this.drawerIndex= 0;
    this.params= {};
    this.loading= true;
    this.filters= {
      systemTag: [],
      userTag: []
    }
  }

  @Select(PltMainState.data) data$;
  loading: boolean;

  ngOnInit(){
    this.Subscriptions.push(
      this.data$.subscribe( data => {
        this.listOfPlts = _.keys(data);
        this.selectedListOfPlts = _.filter(_.keys(data), k => data[k].selected);
        this.sumnaryPltDetailsPltId = _.filter(this.selectedListOfPlts, k => data[k].opened)[0] || null;
        this.selectAll = this.selectedListOfPlts.length > 0 || this.selectedListOfPlts.length == this.listOfPlts.length;
        this.someItemsAreSelected = this.selectedListOfPlts.length < this.listOfPlts.length && this.selectedListOfPlts.length > 0;
        this.detectChanges();
      }),
      this.getAttr('loading').subscribe( l => this.loading =l)
    )
    this.store$.dispatch(new fromWorkspaceStore.loadAllPlts({}));
  }

  getAttr(path){
    return this.store$.select(PltMainState.getAttr).pipe(map( fn => fn(path)))
  }

  sort(sort: { key: string, value: string }): void {
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
  },500);

  setFilter(filter: string, tag) {
      this.filters =
        _.findIndex(this.filters[filter], e => e == tag.tagId) < 0 ?
        _.merge({}, this.filters, { [filter]: _.merge([], this.filters[filter], {[this.filters[filter].length] : tag.tagId} ) }) :
        _.assign({}, this.filters, {[filter]: _.filter(this.filters[filter], e => e != tag.tagId)})

    console.log(this.filters)

      this.store$.dispatch(new fromWorkspaceStore.setFilterPlts({
        filters: this.filters
      }))
  }

  selectPltById(pltId){
    return this.store$.select(state => _.get(state, `pltMainModel.data.${pltId}`))
  }

  openDrawer(index): void {
    this.visible = true;
    this.drawerIndex= index;
  }

  closeDrawer(): void {
    this.visible = false;
  }

  closePltInDrawer(){
    this.store$.dispatch(new fromWorkspaceStore.ClosePLTinDrawer())
  }

  openPltInDrawer(plt) {
    this.closePltInDrawer();
    if(_.findIndex(this.selectedListOfPlts,el => el == plt) == -1) {
      this.toggleSelectPlts(
        _.zipObject(
          this.selectedListOfPlts,
          _.range(this.selectedListOfPlts.length).map(el => ({type : 'unselect'}))
        )
      )
    }
    this.store$.dispatch(new fromWorkspaceStore.OpenPLTinDrawer({pltId: plt}))
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
    return null
  }

  getTagsForSummary(){
    this.pltdetailsSystemTags = this.systemTags;
    this.pltdetailsUserTags= this.userTags;
  }

  selectPath(path) {
    this.currentPath = path;

    _.forEach(this.paths, (el) => {
      _.set(el, 'selected',el.id === path.id)
    })

  }

  selectCardThead(card) {
    this.theads.forEach(thead => {
      thead.cards.forEach(card => {
        card.selected = false;
      })
    })
    card.selected = true;
  }

  getCardBackground(selected) {
    if (selected) return "#FFF";
    else return "#f4f6fc";
  }

  getBoxShadow(selected){
    if (selected) return "0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)";
    else return "none"
  }

  resetFilterByTags(){
    this.filters = _.assign({}, this.filters, {
      systemTag: null,
      userTag: null
    })
  }

  resetPath(){
    this.currentPath = null;
    _.forEach(this.paths, el => _.set(el, 'selected', false))
  }

  contextMenuPltTable($event: MouseEvent, template: TemplateRef<void>): void {
    this.dropdown = this.nzDropdownService.create($event, template);
  }

  close(e: NzMenuItemDirective): void {
    console.log(e);
    this.dropdown.close();
  }

  runInNewConext(task) {
    this.zone.runOutsideAngular(() => {
      task();
      this.detectChanges()
    });
  }

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
  }

  ngOnDestroy(): void {
    this.Subscriptions && _.forEach(this.Subscriptions, el => el.unsubscribe())
  }

  checkAll($event: boolean) {
    this.toggleSelectPlts(
      _.zipObject(
        _.map(this.listOfPlts, plt => plt),
        _.range(this.listOfPlts.length).map(el => ({type : !this.selectAll && !this.someItemsAreSelected ? 'select' : 'unselect'}))
      )
    )
  }

  toggleSelectPlts(plts: any){
    this.store$.dispatch(new fromWorkspaceStore.ToggleSelectPlts({plts}))
  }

  selectSinglePLT(pltId: number, $event: boolean) {
    this.toggleSelectPlts({
      [pltId]: {
        type: $event ? 'select' : 'unselect'
      }
    })
  }

  handlePLTClick(pltId,i: number, $event: MouseEvent) {
    const isSelected= _.findIndex(this.selectedListOfPlts, el => el == pltId) >= 0;
    if($event.ctrlKey || $event.shiftKey) {
      this.handlePLTClickWithKey(pltId,i,!isSelected, $event);
    }else{
      this.toggleSelectPlts(
        _.zipObject(
          _.map(this.listOfPlts, plt => plt),
          _.map(this.listOfPlts, plt =>  plt == pltId ? ({type: 'select'}) : ({type: 'unselect'}) )
        )
      )
    }
  }

  private handlePLTClickWithKey(pltId: number,i: number,isSelected: boolean, $event: MouseEvent) {
    if($event.ctrlKey){
      this.selectSinglePLT(pltId,isSelected);
      this.lastSelectedId=i;
      return;
    }

    if($event.shiftKey) {
      if(!this.lastSelectedId) this.lastSelectedId =0;
      if(this.lastSelectedId || this.lastSelectedId == 0) {
        const max = _.max([i, this.lastSelectedId])
        const min = _.min([i, this.lastSelectedId])
        this.toggleSelectPlts(
          _.zipObject(
            _.map(this.listOfPlts, plt => plt),
            _.map(this.listOfPlts,(plt,i) => ( i <= max  && i >= min ? ({type: 'select'}) : ({type: 'unselect'}) )),
          )
        )
      }else{
        this.lastSelectedId= i;
      }
      return;
    }
  }

  private loadData(params: any) {
    console.log(params)
    this.store$.dispatch(new fromWorkspaceStore.loadAllPlts({params}))
  }
}


