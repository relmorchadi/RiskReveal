import {ChangeDetectorRef, Component, ElementRef, NgZone, OnDestroy, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from "ng-zorro-antd";
import * as _ from 'lodash'
import {Observable, of} from 'rxjs';
import {Select, Store} from "@ngxs/store";
import * as fromWorkspaceStore from '../../store'
import {filter, mapTo, mergeMap} from 'rxjs/operators';

@Component({
  selector: 'app-workspace-plt-browser',
  templateUrl: './workspace-plt-browser.component.html',
  styleUrls: ['./workspace-plt-browser.component.scss']
})
export class WorkspacePltBrowserComponent implements OnInit,OnDestroy {

  private dropdown: NzDropdownContextComponent;
  private Subscriptions: any[] = [];
  searchAddress: string;

  @Select(state => state.pltMainModel.data) listOfPlts$: Observable<any[]>;
  listOfPlts: any[];
  selectedListOfPlts: any[];
  filterData;
  sortData;
  mapSelectedIds: any;
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

  sort(sort: { key: string, value: string }): void {
    if(sort.value){
      this.sortData= _.merge({},this.sortData, {
        [sort.key]: sort.value === "descend" ? 'desc' : 'asc'
      })
    }else{
      this.sortData= _.omit(this.sortData, [sort.key])
    }
  }

  filter(key: string, value) {
    if(value){
      this.filterData= _.merge({},this.filterData, {
        [key]: value
      })
    }else{
      this.filterData= _.omit(this.filterData, [key])
    }
  }

  pltColumns = [
    {fields:'' , header:'User Tags' , width: '6%', sorted: false, filtred: false, icon: null},
    {fields:'pltId' , header:'PLT ID' , width: '6%', sorted: true, filtred: true, icon: null},
    {fields:'pltName' , header:'PLT Name' , width: '14%', sorted: true, filtred: true, icon: null},
    {fields:'peril' , header:'Peril' , width: '7%', sorted: true, filtred: true, icon: null},
    {fields:'regionPerilCode' , header:'Region Peril Code' , width: '13%', sorted: true, filtred: true, icon: null},
    {fields:'regionPerilName' , header:'Region Peril Name' , width: '13%', sorted: true, filtred: true, icon: null},
    {fields:'grain' , header:'Grain' , width: '9%', sorted: true, filtred: true, icon: null},
    {fields:'vendorSystem' , header:'Vendor System' , width: '11%', sorted: true, filtred: true, icon: null},
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
    currentSystemTag: any,
    currentUserTag: any
  }
  sumnaryPltDetails: any = null;

  pltDetailsPermission: boolean ;

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


  constructor( private nzDropdownService: NzDropdownService, private store$: Store,private zone: NgZone, private cdRef: ChangeDetectorRef) {
    this.filters = {
      currentSystemTag: null,
        currentUserTag: null
    }

    this.sortData={};
    this.filterData={};
    this.someItemsAreSelected= false;
    this.selectAll= false;
    this.listOfPlts= [];
    this.selectedListOfPlts= [];
    this.lastSelectedId = null;
  }


  ngOnInit(){
    this.Subscriptions.push(
      this.store$.select( state => state.pltMainModel.data ).subscribe( data => {
        this.listOfPlts = data;
        this.detectChanges();
      }),
      this.store$.select(state => state.pltMainModel.data).pipe(
        mergeMap( (plts) => of(_.filter(plts, ['selected', true])))
      ).subscribe( data => {
        this.selectedListOfPlts = data;
        this.selectAll = this.selectedListOfPlts.length > 0 || this.selectedListOfPlts.length == this.listOfPlts.length;
        this.someItemsAreSelected = this.selectedListOfPlts.length < this.listOfPlts.length && this.selectedListOfPlts.length > 0;
        console.log(this.selectAll,this.someItemsAreSelected);
        this.detectChanges();
      })
    )
    this.store$.dispatch(new fromWorkspaceStore.LoadPltData());
  };


  openDrawer(): void {
    this.visible = true;
  }

  closeDrawer(): void {
    this.visible = false;
  }

  openPltInDrawer(plt) {
    let selectedPlts = this.listOfPlts.filter(pt => pt.selected === true);
    if (selectedPlts.length > 0) {
      if (selectedPlts[0] === plt) {
        plt.selected = false;
        this.visible = false;
        this.sumnaryPltDetails = null;
        this.pltDetailsPermission = false;
      } else {
        this.listOfPlts = this.listOfPlts.map( pt => pt.selected = false);
        plt.selected = true;
        this.sumnaryPltDetails = plt;
      }
    } else {
      this.listOfPlts =this.listOfPlts.map(pt => pt.selected = false);
      plt.selected = true;
      this.visible = true;
      this.sumnaryPltDetails = plt;
      this.pltDetailsPermission = true;
    }

    this.getTagsForSummary(plt);

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

  getTagsForSummary(plt){
    this.pltdetailsSystemTags = this.pltdetailsUserTags = [];
    let findSysTags = plt.systemTags.map(item => item.tagId);
    let findUserTags = plt.userTags.map(item => item.tagId);
    findSysTags.forEach(
      sysTag => {
        this.pltdetailsSystemTags = [...this.pltdetailsSystemTags, this.systemTags.filter(
          item => item.tagId == sysTag
        )[0]]
      }
    )
    findUserTags.forEach(
      uTag => {
        this.pltdetailsUserTags = [...this.pltdetailsUserTags, this.userTags.filter(
          item => item.tagId == uTag
        )[0]]
      }
    )
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
      currentSystemTag: null,
      currentUserTag: null
    })
  }

  resetPath(){
    this.currentPath = null;
    _.forEach(this.paths, el => _.set(el, 'selected', false))
  }

  contextMenuPltTable($event: MouseEvent, template: TemplateRef<void>): void {
    console.log(template,$event);
    this.dropdown = this.nzDropdownService.create($event, template);
  }

  close(e: NzMenuItemDirective): void {
    console.log(e);
    this.dropdown.close();
  }

  setFilter(filter: string, tag) {
    _.set(this.filters, filter, tag);
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
        _.map(this.listOfPlts, (plt,i) => i),
        _.range(this.listOfPlts.length).map(el => ({type : !this.selectAll && !this.someItemsAreSelected ? 'select' : 'unselect'}))
      )
    )
  }

  toggleSelectPlts(plts: any){
    this.store$.dispatch(new fromWorkspaceStore.ToggleSelectPlts({plts}))
  }

  selectSinglePLT(i: number, $event: boolean) {
    this.toggleSelectPlts({
      [i]: {
        type: $event ? 'select' : 'unselect'
      }
    })
  }

  handlePLTClick(pltIndex: number, isSelected: boolean, $event: MouseEvent) {
    if($event.ctrlKey || $event.shiftKey) {
      this.handlePLTClickWithKey(pltIndex,isSelected, $event);
    }else{
      this.toggleSelectPlts(
        _.zipObject(
          _.map(this.listOfPlts, (plt,i) => i),
          _.range(this.listOfPlts.length).map((el,i) =>
            ( i == pltIndex ? ({type: 'select'}) : ({type: 'unselect'}) )
          )
        )
      )
    }
  }

  private handlePLTClickWithKey(i: number,isSelected: boolean, $event: MouseEvent) {
    if($event.ctrlKey){
      this.selectSinglePLT(i,isSelected);
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
            _.map(this.listOfPlts, (plt,i) => i),
            _.range(this.listOfPlts.length).map((el,i) => ( i <= max  && i >= min ? ({type: 'select'}) : ({type: 'unselect'}) )
            )
          )
        )
      }else{
        this.lastSelectedId= i;
      }
      return;
    }
  }
}


