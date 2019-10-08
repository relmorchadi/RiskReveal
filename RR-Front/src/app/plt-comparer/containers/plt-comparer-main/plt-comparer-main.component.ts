import {ChangeDetectorRef, Component, NgZone, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import {Select, Store} from '@ngxs/store';
import * as _ from 'lodash';
import {Table} from "primeng/table";
import {FormBuilder, FormGroup} from '@angular/forms';
import * as tableStore from "../../../shared/components/plt/plt-main-table/store";
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
  listOfSelectedValues = ['OEP', 'AEP'];
  colorSwitcher = ['#A96EFE', '#06B8FF', '#F5A623', '#03DAC4', '#E3B8FF', '#0700CF', '#ADFEFA', '#1C607C'];
  isVisible = false;

  dropdown: NzDropdownContextComponent;
  searchAddress: string;
  @ViewChild('dt')
  private table: Table;

  workspaceId: string;
  uwy: number;
  projects: any[];

  params: any;
  lastSelectedId;

  selectedPlt: any;

  defaultImport = 'Baseline';

  visible = false;
  size = 'large';

  selectedCurrency = 'EUR';

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
  ];

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
    this.loading = true;
    this.multiSteps = true;
    this.stepConfig = {
      wsId: '', uwYear: '', plts: []
    };
  }

  ngOnInit() {
    this.initForm();
    this.colorThePlt();
  }

  initForm() {
    this.form = this._fb.group({
      defaultImport: [ {workSpaceId: '', uwYear: ''} ]
    });
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
    this.detectChanges();
  }

  log(e) {
    console.log(e)
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

  getPlt() {
    return _.filter(this.cardContainer, dt => dt.selected)[0];
  }
  changeCurrency(currency){
    this.selectedCurrency = currency;
  }
  changeUnit(unit){
    this.selectedUnit = unit;

  }

  updateTableDr(event) {
    if (this.listOfSelectedValues.length === 1 && event.length === 0) {
    } else {
      this.listOfSelectedValues = event;
    }
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
