import {ChangeDetectorRef, Component, NgZone, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import * as fromWorkspaceStore from "../../../workspace/store";
import {WorkspaceState} from '../../../workspace/store';
import {Select, Store} from '@ngxs/store';
import * as _ from 'lodash';
import {Table} from "primeng/table";
import {map, switchMap} from 'rxjs/operators';
import {combineLatest, Observable, Subscription} from 'rxjs';
import {WorkspaceMainState} from "../../../core/store/states";
import {WorkspaceMain} from "../../../core/model";
import {FormBuilder, FormGroup} from '@angular/forms';


@Component({
  selector: 'app-plt-comparer-main',
  templateUrl: './plt-comparer-main.component.html',
  styleUrls: ['./plt-comparer-main.component.scss']
})
export class PltComparerMainComponent implements OnInit {
  switchValue = false;
  listOfSelectedValues = ['AEP'];
  colorSwitcher = ['#A96EFE', '#06B8FF', '#F5A623', '#03DAC4', '#E3B8FF', '#0700CF', '#ADFEFA', '#1C607C'];
  isVisible = false;

  dropdown: NzDropdownContextComponent;
  Subscriptions: any[] = [];
  searchAddress: string;
  listOfPlts: any[];
  listOfPltsData: any[];
  listOfPltsCache: any[];
  selectedListOfPlts: any[];
  selectedListOfDeletedPlts: any[];
  listOfDeletedPlts: any[];
  listOfDeletedPltsData = [];
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

  renamingTag: boolean;
  modalInputCache: any;

  selectedPlt: any;
  tagModalIndex: any;
  addModalSelectCache: any;
  oldSelectedTags: any;
  addTagModal: boolean;
  addModalInput: any;
  inputValue: null;
  addModalSelect: any;
  tagFormenu: any = {
    tagColor: 'white',
    tagName: ''
  };
  fromPlts: any;
  colorPickerIsVisible: any;
  initColor: any;
  addTagModalPlaceholder: any;
  showDeleted: boolean;
  selectedItemForMenu: string;


  defaultImport;


  pltColumns = [
    {sortDir: 1, fields: '', header: 'User Tags', width: '60px', sorted: false, filtred: false, icon: null, type: 'checkbox'},
    {sortDir: 1, fields: 'pltId', header: 'PLT ID', width: '65px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1, fields: 'pltName', header: 'PLT Name', width: '140px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1, fields: 'peril', header: 'Peril', width: '55px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1, fields: 'regionPerilCode', header: 'Region Peril Code', width: '75px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1, fields: 'regionPerilName', header: 'Region Peril Name', width: '130px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1, fields: 'grain', header: 'Grain', width: '160px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1, fields: 'deletebBy', header: 'Deleted By', width: '70px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1, fields: 'deletebAt', header: 'Deleted At', width: '70px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1, fields: 'vendorSystem', header: 'Vendor System', width: '60px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1, fields: 'rap', header: 'RAP', width: '70px', sorted: true, filtred: true, icon: null, type: 'field'},
    {sortDir: 1, fields: '', header: '', width: '25px', sorted: false, filtred: false, icon: 'icon-focus-add', type: 'icon'},
    {sortDir: 1, fields: '', header: '', width: '25px', sorted: false, filtred: false, icon: 'icon-note', type: 'icon'},
    {sortDir: 1, fields: '', header: '', width: '25px', sorted: false, filtred: false, icon: 'icon-focus-add', type: 'icon'},
  ];

  tagContextMenu = [
    { label: 'Delete Tag', icon: 'pi pi-trash', command: (event) => this.store$.dispatch(new fromWorkspaceStore.deleteUserTag(this.tagFormenu.tagId))},
    { label: 'Rename Tag', icon: 'pi pi-pencil', command: (event) => {
        this.renamingTag = true;
        this.fromPlts = false;
        this.addModalInput = this.tagFormenu.tagName;
        this.modalInputCache = this.tagFormenu.tagName;
        this.addTagModal = true;
      }}
  ];

  contextMenuItems = [
    { label: 'View Detail', icon: 'pi pi-search', command: (event) => this.openPltInDrawer(this.selectedPlt.pltId) },
    { label: 'Delete', icon: 'pi pi-trash', command: (event) =>
        this.store$.dispatch(new fromWorkspaceStore.deletePlt({pltId : this.selectedItemForMenu}))
    },
    { label: 'Edit Tags', icon: 'pi pi-tags', command: (event) => {
        this.addTagModal = true;
        this.fromPlts = true;
        let d = [];

        _.forEach( this.listOfPltsData, (v,k) => {
          if(v.selected) d.push(v.userTags);
        });

        // this.selectedUserTags = _.keyBy(_.intersectionBy(...d, 'tagId'), 'tagId')

        this.addModalSelect = _.intersectionBy(...d, 'tagId');

      }
    }
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

  selectedCurrency = 'EUR';
  sumnaryPltDetailsPltId: any;

  epMetricInputValue: string | null;

  pltdetailsSystemTags: any = [];
  pltdetailsUserTags: any = [];
  listOfDeletedPltsCache: any[];

  systemTags: any;
  wsHeaderSelected: boolean;

  systemTagsCount: any;

  userTags: any;

  userTagsCount: any;

  selectedUnit = 'Million';

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

  tableInputs = {
    contextMenuItems: [],
    pltColumns: this.pltColumns,
    listOfPltsData: [],
    listOfPltsCache: [],
    listOfPlts: [],
    selectedPlt: null,
    selectedListOfPlts: null,
    deletedPlts: null,
    selectAll: false,
    someItemsAreSelected: false,
    showDeleted: false,
    filterData: null,
    filters: {
      systemTag: [],
      userTag: []
    },
    sortData: null
  };

  @Select(WorkspaceState.getUserTags) userTags$;
  data$: Observable<any>;
  deletedPlts$: Observable<any>;

  deletedPlts: any;
  loading: boolean;
  selectedUserTags: any;

  @Select(WorkspaceMainState)
  state$: Observable<WorkspaceMain>;
  listOfWs: any = null;

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


  cardContainer = [

    {
      title: 'NFU MUTUAL',
      subtitle: '2017 - NFU & Liab XL',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      color: '',
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%']
      ],
      result: [
        ['323.849', '323.849', '323.849', '323.849'],
        ['5,806.767', '5,806.767', '5,806.767', '5,806.767'],
        ['17.83', '17.83', '17.83', '17.83']
      ]
    },
    {
      title: 'CALIFORNIA EQ AUTHORITY',
      subtitle: '2016 - CEA Program: Private Plac',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      color: '',
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%']
      ],
      result: [
        ['323.849', '323.849', '323.849', '323.849'],
        ['5,806.767', '5,806.767', '5,806.767', '5,806.767'],
        ['17.83', '17.83', '17.83', '17.83']
      ]
    },
    {
      title: 'NFU MUTUAL',
      subtitle: '2017 - NFU PROPERTY CAT UK',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      color: '',
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%']
      ],
      result: [
        ['323.849', '323.849', '323.849', '323.849'],
        ['5,806.767', '5,806.767', '5,806.767', '5,806.767'],
        ['17.83', '17.83', '17.83', '17.83']
      ]
    },
    {
      title: 'SCOR SE',
      subtitle: '2017 - SBS - SCOR ASIA PACIFIC',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      color: '',
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%']
      ],
      result: [
        ['323.849', '323.849', '323.849', '323.849'],
        ['5,806.767', '5,806.767', '5,806.767', '5,806.767'],
        ['17.83', '17.83', '17.83', '17.83']
      ]
    },{
      title: 'NFU MUTUAL',
      subtitle: '2017 - NFU & Liab XL',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      color: '',
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%']
      ],
      result: [
        ['323.849', '323.849', '323.849', '323.849'],
        ['5,806.767', '5,806.767', '5,806.767', '5,806.767'],
        ['17.83', '17.83', '17.83', '17.83']
      ]
    },
    {
      title: 'CALIFORNIA EQ AUTHORITY',
      subtitle: '2016 - CEA Program: Private Plac',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      color: '',
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%']
      ],
      result: [
        ['323.849', '323.849', '323.849', '323.849'],
        ['5,806.767', '5,806.767', '5,806.767', '5,806.767'],
        ['17.83', '17.83', '17.83', '17.83']
      ]
    },
    {
      title: 'NFU MUTUAL',
      subtitle: '2017 - NFU PROPERTY CAT UK',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      color: '',
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%']
      ],
      result: [
        ['323.849', '323.849', '323.849', '323.849'],
        ['5,806.767', '5,806.767', '5,806.767', '5,806.767'],
        ['17.83', '17.83', '17.83', '17.83']
      ]
    },
    {
      title: 'SCOR SE',
      subtitle: '2017 - SBS - SCOR ASIA PACIFIC',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      color: '',
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%']
      ],
      result: [
        ['323.849', '323.849', '323.849', '323.849'],
        ['5,806.767', '5,806.767', '5,806.767', '5,806.767'],
        ['17.83', '17.83', '17.83', '17.83']
      ]
    },{
      title: 'NFU MUTUAL',
      subtitle: '2017 - NFU & Liab XL',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      color: '',
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%']
      ],
      result: [
        ['323.849', '323.849', '323.849', '323.849'],
        ['5,806.767', '5,806.767', '5,806.767', '5,806.767'],
        ['17.83', '17.83', '17.83', '17.83']
      ]
    },
    {
      title: 'CALIFORNIA EQ AUTHORITY',
      subtitle: '2016 - CEA Program: Private Plac',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      color: '',
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%']
      ],
      result: [
        ['323.849', '323.849', '323.849', '323.849'],
        ['5,806.767', '5,806.767', '5,806.767', '5,806.767'],
        ['17.83', '17.83', '17.83', '17.83']
      ]
    },
    {
      title: 'NFU MUTUAL',
      subtitle: '2017 - NFU PROPERTY CAT UK',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      color: '',
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%']
      ],
      result: [
        ['323.849', '323.849', '323.849', '323.849'],
        ['5,806.767', '5,806.767', '5,806.767', '5,806.767'],
        ['17.83', '17.83', '17.83', '17.83']
      ]
    },
    {
      title: 'SCOR SE',
      subtitle: '2017 - SBS - SCOR ASIA PACIFIC',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      color: '',
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%'],
        ['1', '1', '1', '1', '100%']
      ],
      result: [
        ['323.849', '323.849', '323.849', '323.849'],
        ['5,806.767', '5,806.767', '5,806.767', '5,806.767'],
        ['17.83', '17.83', '17.83', '17.83']
      ]
    }
  ];
  form: FormGroup;


  constructor(
    private nzDropdownService: NzDropdownService,
    private store$: Store,
    private zone: NgZone,
    private cdRef: ChangeDetectorRef,
    private _fb: FormBuilder
    ) {
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
    this.showDeleted = false;
    this.wsHeaderSelected = true;
    // this.generateContextMenu(this.showDeleted);
  }

  ngOnInit() {
    this.initForm();
    this.colorThePlt();
    this.Subscriptions.push(
      this.state$.subscribe(value => this.listOfWs = _.merge({}, value.openedTabs)),
      this.store$.select(WorkspaceState.getProjects()).subscribe((projects: any) => {
        this.projects = projects;
        this.detectChanges();
      }),
      this.getAttr('loading').subscribe( l => this.loading = l),
      this.userTags$.subscribe( userTags => {
        this.userTags = userTags || {};
        this.detectChanges();
      }),
      this.form.valueChanges.pipe(
        switchMap(({defaultImport}) => {
          this.workspaceId = defaultImport.workSpaceId;
          this.uwy = defaultImport.uwYear;
          this.loading = true;
          this.data$ = this.store$.select(WorkspaceState.getPlts(this.workspaceId+'-'+this.uwy));
          this.deletedPlts$ = this.store$.select(WorkspaceState.getDeletedPlts(this.workspaceId+'-'+this.uwy));
          this.store$.dispatch(new fromWorkspaceStore.loadAllPlts({
            params: {
              workspaceId: this.workspaceId, uwy: this.uwy
            }}));
          return combineLatest(
            this.data$,
            this.deletedPlts$
          )
        })
      ).subscribe( ([data, deletedData]: any) => {
        let d1 = [];
        let dd1 = [];
        let d2 = [];
        let dd2 = [];
        this.loading = false;
        this.systemTagsCount = {};

        if (data) {
          if (_.keys(this.systemTagsCount).length == 0) {
            _.forEach(data, (v, k) => {
              // Init Tags Counters

              // Grouped Sys Tags
              _.forEach(this.systemTagsMapping.grouped, (sectionName, section) => {
                this.systemTagsCount[sectionName] = this.systemTagsCount[sectionName] || {};
                const tag = _.toString(v[section]);
                if (tag) {
                  this.systemTagsCount[sectionName][tag] = {selected: false, count: 0, max: 0};
                }
              });

              // NONE grouped Sys Tags
              _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
                this.systemTagsCount[sectionName] = this.systemTagsCount[sectionName] || {};
                this.systemTagsCount[sectionName][section] = {selected: false, count: 0};
                this.systemTagsCount[sectionName]['non-' + section] = {selected: false, count: 0, max: 0};
              });

            });
          }

          _.forEach(data, (v, k) => {
            d1.push({...v, pltId: k});
            d2.push(k);

            /*if (v.visible) {*/
            // Grouped Sys Tags
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
            });

            // NONE grouped Sys Tags
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
            });
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
    );
  }

  initForm() {
    this.form = this._fb.group({
      defaultImport: [ {workSpaceId: '', uwYear: ''} ]
    })
  }



  getAttr(path) {
    return this.store$.select(WorkspaceState.getAttr).pipe(map( fn => fn(path)));
  }

  colorThePlt() {
    while (this.cardContainer.length > this.colorSwitcher.length) {
      this.colorSwitcher = this.colorSwitcher.concat(this.colorSwitcher);
      console.log(this.colorSwitcher);
    }
    for (let i = 0; i < this.cardContainer.length; i++) {
      this.cardContainer[i].color = this.colorSwitcher[i];

    }
  }
  showModal(): void {
    this.isVisible = true;
  }

  toggleSelectPlts(plts: any) {
    this.store$.dispatch(new fromWorkspaceStore.ToggleSelectPlts({wsIdentifier: this.workspaceId+'-'+this.uwy,plts}));
  }
  checkBoxSort($event) {
    this.listOfPltsData= $event;
  }
  selectSinglePLT($event) {
    this.toggleSelectPlts($event);
  }
  sortChange(sortData) {
    this.sortData= sortData;
  }
  checkAll() {
    this.toggleSelectPlts(
      _.zipObject(
        _.map(this.listOfPlts, plt => plt),
        _.range(this.listOfPlts.length).map(el => ({type : !this.selectAll && !this.someItemsAreSelected ? 'select' : 'unselect'}))
      )
    );
  }


  handleCancel(): void {
    this.isVisible = false;
  }

  contextMenu($event: MouseEvent, template: TemplateRef<void>): void {
    this.dropdown = this.nzDropdownService.create($event, template);

  }

  close(e: NzMenuItemDirective): void {
    console.log(e);

    this.dropdown.close();
  }

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
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

  filter({key, filterData, projectId}) {
    if (key == 'project') {
      this.projects = _.map(this.projects, t => {
        if (t.projectId === projectId) {
          return ({...t, selected: !t.selected});
        } else if (t.selected) {
          return ({...t, selected: false});
        } else return t;
      });
    }
    this.filterData= filterData;
  }

  selectFirst(event) {
    if (event)
      this.cardContainer[0].selected = true;
    else
      this.cardContainer.forEach(dt =>  dt.selected = false);
  }
  selectItem(selected){
    this.cardContainer.forEach(dt => dt.selected = false);

  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.cardContainer, event.previousIndex, event.currentIndex);
    console.log(this.listOfSelectedValues.length);
  }

  deleteItem(i) {
    this.cardContainer.splice(i , 1);
  }

  openDrawer(index): void {
    this.visible = true;
    this.drawerIndex = index;
  }

  closeDrawer(): void {
    this.visible = false;
  }
  closePltInDrawer(pltId) {
    this.store$.dispatch(new fromWorkspaceStore.ClosePLTinDrawer({wsIdentifier: this.workspaceId + '-' + this.uwy, pltId}));
  }
  getTagsForSummary() {
    this.pltdetailsSystemTags = this.systemTags;
    this.pltdetailsUserTags = this.userTags;
  }

  openPltInDrawer(plt) {
    this.closePltInDrawer(this.sumnaryPltDetailsPltId);
    this.store$.dispatch(new fromWorkspaceStore.OpenPLTinDrawer({wsIdentifier: this.workspaceId + '-' + this.uwy, pltId: plt}));
    this.openDrawer(1);
    this.getTagsForSummary();
  }
  setSelectedMenuItem($event: any) {
    this.selectedItemForMenu = $event;
  }

  setFilters($event) {
    this.filters = $event;
  }
  renameTag($event) {
    this.store$.dispatch(new fromWorkspaceStore.editTag({wsIdentifier: this.workspaceId + '-' + this.uwy, ...$event}));
  }
  resetPath() {
    this.filterData = _.omit(this.filterData, 'project');
    this.projects = _.map(this.projects, p => ({...p, selected: false}));
    this.showDeleted = false;
  }
  setSelectedProjects($event) {
    this.projects = $event;
  }
  selectSystemTag({section, tag}) {

    _.forEach(this.systemTagsCount, (s, sKey) => {
      _.forEach(s, (t, tKey) => {
        this.systemTagsCount[sKey][tKey] = tag == tKey && section == sKey ? {...t, selected: !t.selected} : {...t, selected: false};
      });
    });
  }

  getPlt() {
    return _.filter(this.cardContainer, dt => dt.selected)[0];
  }

  setFromPlts($event) {
    this.fromPlts = $event;
  }

  setSysTags($event) {
    this.systemTags = $event;
  }

  setUserTags($event) {
    this.userTags = $event;
  }

  setModalIndex($event) {
    this.tagModalIndex = $event;
  }

  assignPltsToTag($event: any) {
    console.log($event)
    this.store$.dispatch(new fromWorkspaceStore.createOrAssignTags({wsIdentifier: this.workspaceId+'-'+this.uwy,...$event}))
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
  changeCurrency(currency){
    this.selectedCurrency = currency;
  }
  changeUnit(unit){
    this.selectedUnit = unit;

  }
  toggleDeletePlts() {
    this.showDeleted = !this.showDeleted;
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
  emitFilters(filters: any) {
    this.store$.dispatch(new fromWorkspaceStore.setUserTagsFilters({
      wsIdentifier: this.workspaceId+'-'+this.uwy,
      filters: filters
    }))
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
  deletePlt() {
    this.store$.dispatch(new fromWorkspaceStore.deletePlt({wsIdentifier: this.workspaceId+'-'+this.uwy,pltIds: this.selectedListOfPlts.length > 0 ? this.selectedListOfPlts : [this.selectedItemForMenu]}))
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
    this.store$.dispatch(new fromWorkspaceStore.restorePlt({wsIdentifier: this.workspaceId+'-'+this.uwy,pltIds: this.selectedListOfDeletedPlts.length > 0 ? this.selectedListOfDeletedPlts : [this.selectedItemForMenu]}))
    this.showDeleted = !(this.listOfDeletedPlts.length === 0) ? this.showDeleted : false;
  }

}
