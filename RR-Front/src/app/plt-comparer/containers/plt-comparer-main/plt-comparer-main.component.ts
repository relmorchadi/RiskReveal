import {ChangeDetectorRef, Component, NgZone, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import * as fromWorkspaceStore from "../../../workspace/store";
import {WorkspaceState} from "../../../workspace/store";
import {Select, Store} from '@ngxs/store';
import * as _ from 'lodash';
import {Table} from "primeng/table";
import {map, switchMap} from 'rxjs/operators';
import {combineLatest, Observable, of} from 'rxjs';
import {WorkspaceMain} from "../../../core/model";
import {FormBuilder, FormGroup} from '@angular/forms';
import * as tableStore from "../../../shared/components/plt/plt-main-table/store";
import {Message} from "../../../shared/message";
import {Actions as tableActions} from "../../../shared/components/plt/plt-main-table/store";
import {BaseContainer} from "../../../shared/base";
import {Router} from "@angular/router";
import {SystemTagsService} from "../../../shared/services/system-tags.service";


@Component({
  selector: 'app-plt-comparer-main',
  templateUrl: './plt-comparer-main.component.html',
  styleUrls: ['./plt-comparer-main.component.scss']
})
export class PltComparerMainComponent extends BaseContainer implements OnInit {

  tableInputs: tableStore.Input;

  multiSteps: boolean;
  stepConfig: {
    wsId: string,
    uwYear: string,
    plts: any[]
  };
  
  switchValue = false;
  listOfSelectedValues = ['AEP'];
  colorSwitcher = ['#A96EFE', '#06B8FF', '#F5A623', '#03DAC4', '#E3B8FF', '#0700CF', '#ADFEFA', '#1C607C'];
  isVisible = false;

  dropdown: NzDropdownContextComponent;
  searchAddress: string;
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

  tagContextMenu = [
    { label: 'Delete Tag', icon: 'pi pi-trash', command: (event) => this.dispatch(new fromWorkspaceStore.deleteUserTag(this.tagFormenu.tagId))},
    { label: 'Rename Tag', icon: 'pi pi-pencil', command: (event) => {
        this.renamingTag = true;
        this.fromPlts = false;
        this.addModalInput = this.tagFormenu.tagName;
        this.modalInputCache = this.tagFormenu.tagName;
        this.addTagModal = true;
      }}
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

  drawerIndex: any;
  private pageSize: number = 20;
  private lastClick: string;

  loading: boolean;
  selectedUserTags: any;
  listOfWs: any = null;


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
    private zone: NgZone,
    private _fb: FormBuilder,
    private systemTagService: SystemTagsService,
    _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef
    ) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.lastSelectedId = null;
    this.drawerIndex = 0;
    this.params = {};
    this.loading = true;
    this.filters = {
      systemTag: [],
      userTag: []
    };
    this.activeCheckboxSort = false;
    this.loading = true;
    this.addTagModal = false;
    this.tagModalIndex = 0;
    this.systemTagsCount = {};
    this.userTagsCount = {};
    this.fromPlts = false;
    this.renamingTag = false;
    this.selectedUserTags = {};
    this.initColor = '#fe45cd';
    this.colorPickerIsVisible = false;
    this.addTagModalPlaceholder = 'Select a Tag';
    this.showDeleted = false;
    this.wsHeaderSelected = true;
    // this.generateContextMenu(this.showDeleted);
    this.tableInputs = {
      scrollHeight: 'calc(100vh - 480px)',
      dataKey: "pltId",
      openedPlt: "",
      contextMenuItems: [],
      filterData: {},
      filters: {
        userTag: [],
        systemTag: {}
      },
      sortData: {},
      selectAll: false,
      selectAllDeletedPlts: false,
      someItemsAreSelected: false,
      someDeletedItemsAreSelected: false,
      showDeleted: false,
      pltColumns: [
        {
          sortDir: 1,
          fields: '',
          header: '',
          sorted: false,
          filtred: false,
          resizable: false,
          width: '25%',
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
          width: '80%',
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
          width: '25px',
          icon: 'icon-note',
          type: 'icon',
          active: true,
          tooltip: "Published for Pricing"
        },
        {
          sortDir: 1,
          fields: '',
          header: '',
          sorted: false,
          filtred: false,
          width: '25px',
          icon: 'icon-dollar-alt',
          type: 'icon',
          active: true,
          tooltip: "Priced"
        },
        {
          sortDir: 1,
          fields: '',
          header: '',
          sorted: false,
          filtred: false,
          width: '25px',
          icon: 'icon-focus-add',
          type: 'icon',
          active: true,
          tooltip: "Published for Accumulation"
        },
      ],
      filterInput: '',
      listOfDeletedPltsCache: [],
      listOfDeletedPltsData: [],
      listOfPltsCache: [],
      listOfPltsData: [],
      selectedListOfDeletedPlts: [],
      selectedListOfPlts: [],
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
      }
    };
    this.multiSteps= true;
    this.stepConfig= {
      wsId: '',
      uwYear: '',
      plts: []
    }
  }

  getOpenedWorkspaces(){
    return this.select(WorkspaceState.getWorkspaces).pipe(switchMap((workspaces: any) => of(_.map(workspaces, ws => ({
      workSpaceId: ws.wsId,
      uwYear: ws.uwYear
    })))))
  }

  getPlts() {
    return this.select(WorkspaceState.getPltsForPlts(this.workspaceId + '-' + this.uwy));
  }

  getProjects() {
    return this.select(WorkspaceState.getProjectsPlt(this.workspaceId + '-' + this.uwy));
  }

  getUserTags() {
    return this.select(WorkspaceState.getUserTagsPlt(this.workspaceId + '-' + this.uwy));
  }

  observeFormChanges() {
    return this.form.valueChanges.pipe(
      switchMap( ({defaultImport}) => {
        this.workspaceId = defaultImport.workSpaceId;
        this.uwy = defaultImport.uwYear;
        return of(null);
      })
    )
  }

  observeFormInputsWithSelector(operator) {
    return this.observeFormChanges()
      .pipe(
        switchMap(() => operator()),
        this.unsubscribeOnDestroy
      )
  }

  ngOnInit() {
    this.initForm();
    this.colorThePlt();

    this.getOpenedWorkspaces().subscribe( (workspaces: any) => {
      this.listOfWs= workspaces;
      console.log(workspaces)

      this.detectChanges();
    });

    this.observeFormChanges().subscribe( () => {
      this.dispatch(new fromWorkspaceStore.loadAllPlts({
        params: {
          workspaceId: this.workspaceId, uwy: this.uwy
        },
        wsIdentifier: this.workspaceId + '-' + this.uwy
      }));
    })

    this.observeFormInputsWithSelector(() => this.getProjects()).subscribe((projects: any) => {
      this.projects = projects;

      this.detectChanges();
    });

    this.observeFormInputsWithSelector( () => this.getUserTags()).subscribe(userTags => {
      this.userTags = userTags || {};

      this.detectChanges();
    });

    this.observeFormInputsWithSelector(() => this.getPlts()).subscribe((data) => {
      this.systemTagsCount = this.systemTagService.countSystemTags(data);
      this.updateTable('listOfPltsCache', _.map(data, (v, k) => ({...v, pltId: k})));
      this.updateTable('listOfPltsData', [...this.getTableInputKey('listOfPltsCache')]);
      this.updateTable('selectedListOfPlts', _.filter(data, (v, k) => v.selected));

      this.detectChanges();
    });

    this.observeFormInputsWithSelector(() => this.getPlts()).subscribe(data => {
      this.updateTable('selectAll',
        (this.getTableInputKey('selectedListOfPlts').length > 0 || (this.getTableInputKey('selectedListOfPlts').length == this.getTableInputKey('listOfPltsData').length))
        &&
        this.getTableInputKey('listOfPltsData').length > 0);
      this.updateTable("someItemsAreSelected", this.getTableInputKey('selectedListOfPlts').length < this.getTableInputKey('listOfPltsData').length && this.getTableInputKey('selectedListOfPlts').length > 0);

      this.detectChanges();
    });


  }

  initForm() {
    this.form = this._fb.group({
      defaultImport: [ {workSpaceId: '', uwYear: ''} ]
    })
  }

  colorThePlt() {
    while (this.cardContainer.length > this.colorSwitcher.length) {
      this.colorSwitcher = this.colorSwitcher.concat(this.colorSwitcher);
    }
    for (let i = 0; i < this.cardContainer.length; i++) {
      this.cardContainer[i].color = this.colorSwitcher[i];

    }
  }

  showModal(): void {
    this.isVisible = true;
  }

  toggleSelectPlts(plts: any) {
    this.dispatch(new fromWorkspaceStore.ToggleSelectPlts({wsIdentifier: this.workspaceId+'-'+this.uwy,plts}));
  }

  selectSinglePLT($event) {
    this.toggleSelectPlts($event);
  }

  handleCancel(): void {
    this.isVisible = false;
  }

  contextMenu($event: MouseEvent, template: TemplateRef<void>): void {
    this.dropdown = this.nzDropdownService.create($event, template);

  }

  close(e: NzMenuItemDirective): void {

    this.dropdown.close();
  }

  detectChanges() {
    super.detectChanges();
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
    this.dispatch(new fromWorkspaceStore.ClosePLTinDrawer({wsIdentifier: this.workspaceId + '-' + this.uwy, pltId}));
  }
  getTagsForSummary() {
    this.pltdetailsSystemTags = this.systemTags;
    this.pltdetailsUserTags = this.userTags;
  }

  openPltInDrawer(plt) {
    this.closePltInDrawer(this.sumnaryPltDetailsPltId);
    this.dispatch(new fromWorkspaceStore.OpenPLTinDrawer({wsIdentifier: this.workspaceId + '-' + this.uwy, pltId: plt}));
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
    this.dispatch(new fromWorkspaceStore.editTag({wsIdentifier: this.workspaceId + '-' + this.uwy, ...$event}));
  }
  resetPath() {
    //this.filterData = _.omit(this.filterData, 'project');
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
    this.dispatch(new fromWorkspaceStore.createOrAssignTags({wsIdentifier: this.workspaceId+'-'+this.uwy,...$event}))
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
  emitFilters(filters: any) {
    this.dispatch(new fromWorkspaceStore.setUserTagsFilters({
      wsIdentifier: this.workspaceId+'-'+this.uwy,
      filters: filters
    }))
  }

  setWsHeaderSelect($event: any) {
    this.wsHeaderSelected = $event;
  }

  setModalSelectedItems($event: any) {
    this.addModalSelect = $event;
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
        this.selectedItemForMenu = action.payload;
        this.selectedPlt = action.payload;
        break;

      case tableStore.toggleSelectedPlts:
        console.log(action.payload);
        this.toggleSelectPlts(action.payload);
        break;

      case tableStore.filterByStatus:
        const status= this.getTableInputKey('status');

        this.updateTable('status', {
          ...status,
          [action.payload]: {
            selected: !status[action.payload].selected
          }
        });
        this.dispatch(new fromWorkspaceStore.FilterPltsByStatus({
          wsIdentifier: this.workspaceId+"-"+this.uwy,
          status: this.getTableInputKey('status')
        }));
        break;
      default:
        console.log('table action dispatcher')
    }
  }

  updateTable(key: string, value: any) {
    this.tableInputs = tableActions.updateKey.handler(this.tableInputs, key, value);
  }

  getTableInputKey(key) {
    return _.get(this.tableInputs, key);
  }

  setSelectedWs($event: any) {
    //TO DO
  }

  setSelectedPlts($event: any) {
    //TO DO
  }

}
