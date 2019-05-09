import {Component, OnInit} from '@angular/core';
import * as _ from 'lodash';
import {style} from "@angular/animations";

@Component({
  selector: 'app-workspace-plt-browser',
  templateUrl: './workspace-plt-browser.component.html',
  styleUrls: ['./workspace-plt-browser.component.scss']
})
export class WorkspacePltBrowserComponent implements OnInit {


  sortName: string | null = null;
  sortValue: string | null = null;
  searchAddress: string;

  listOfName = [{ text: 'Joe', value: 'Joe' }, { text: 'Jim', value: 'Jim' }];
  listOfAddress = [{ text: 'London', value: 'London' }, { text: 'Sidney', value: 'Sidney' }];
  listOfSearchName: string[] = [];

  listOfPlts: Array<{
    pltId: number;
    systemTags: any;
    userTags: any;
    pathId: number; pltName: string;
    peril: string;
    regionPerilCode: string;
    regionPerilName: string;
    selected: boolean;
    grain: string;
    vendorSystem: string;
    rap: string;
    d: boolean;
    note: boolean;
    checked: boolean;
    [key: string]:any;}> = [
    {
      pltId: 1,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      pathId: 1,
      pltName: "NATC-USM_RL_Imf.T1",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: true,
      checked: false
    },
    {
      pltId: 2,
      systemTags: [],
      userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      pathId: 2,
      pltName: "NATC-USM_RL_Imf.T2",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: false,
      checked: true
    },
    {
      pltId: 3,
      systemTags: [{tagId: 1}, {tagId: 5}, {tagId: 7}],
      userTags: [{tagId: 3}],
      pathId: 3,
      pltName: "NATC-USM_RL_Imf.T3",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: false,
      checked: false
    },
    {
      pltId: 4,
      systemTags: [],
      userTags: [],
      pathId: 4,
      pltName: "NATC-USM_RL_Imf.T4",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: true,
      checked: true
    },
    {
      pltId: 5,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 5}],
      userTags: [{tagId: 1}, {tagId: 2}],
      pathId: 5,
      pltName: "NATC-USM_RL_Imf.T5",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: false,
      checked: false
    },
    {
      pltId: 6,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      pathId: 1,
      pltName: "NATC-USM_RL_Imf.T6",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: true,
      checked: false
    },
    {
      pltId: 7,
      systemTags: [{tagId: 5}, {tagId: 7}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}],
      pathId: 2,
      pltName: "NATC-USM_RL_Imf.T7",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: true,
      checked: false
    },
    {
      pltId: 8,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}],
      pathId: 3,
      pltName: "NATC-USM_RL_Imf.T8",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: true,
      checked: true
    },
    {
      pltId: 9,
      systemTags: [{tagId: 1}, {tagId: 5}, {tagId: 3}],
      userTags: [{tagId: 2}],
      pathId: 3,
      pltName: "NATC-USM_RL_Imf.T9",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: false,
      checked: false
    },
    {
      pltId: 10,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      pathId: 2,
      pltName: "NATC-USM_RL_Imf.T10",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: true,
      checked: false
    },
    {
      pltId: 11,
      systemTags: [],
      userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      pathId: 5,
      pltName: "NATC-USM_RL_Imf.T11",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: true,
      checked: false
    },
    {
      pltId: 12,
      systemTags: [{tagId: 5}, {tagId: 2}, {tagId: 7}],
      userTags: [{tagId: 2}, {tagId: 3}],
      pathId: 3,
      pltName: "NATC-USM_RL_Imf.T12",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: true,
      checked: true
    },
    {
      pltId: 13,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}],
      pathId: 4,
      pltName: "NATC-USM_RL_Imf.T13",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: true,
      checked: false
    }

  ];
  listOfDisplayPlts: Array<{
    pltId: number;
    systemTags: [];
    userTags: [];
    pathId: number; pltName: string;
    peril: string;
    regionPerilCode: string;
    regionPerilName: string;
    selected: boolean;
    grain: string;
    vendorSystem: string;
    rap: string;
    d: boolean;
    note: boolean;
    checked: boolean;
    [key: string]: any;}> = [
    ...this.listOfPlts
  ];

  sort(sort: { key: string; value: string }): void {
    this.sortName = sort.key;
    this.sortValue = sort.value;
    this.search();
  }

  filter(listOfSearchName: string[], searchAddress: string): void {
    this.listOfSearchName = listOfSearchName;
    this.searchAddress = searchAddress;
    this.search();
  }

  search(): void {
    /** filter data **/
    const filterFunc = (item: { name: string; age: number; address: string }) =>
      (this.searchAddress ? item.address.indexOf(this.searchAddress) !== -1 : true) &&
      (this.listOfSearchName.length ? this.listOfSearchName.some(name => item.name.indexOf(name) !== -1) : true);
    const data = this.listOfPlts.filter(item => filterFunc(item));
    /** sort data **/
    if (this.sortName && this.sortValue) {
      this.listOfDisplayPlts = data.sort((a, b) =>
        this.sortValue === 'ascend'
          ? a[this.sortName!] > b[this.sortName!]
          ? 1
          : -1
          : b[this.sortName!] > a[this.sortName!]
          ? 1
          : -1
      );
    } else {
      this.listOfDisplayPlts = data;
    }
  }

  pltColumns = [
    {fields:'' , header:'' , width: '3%', sorted: false, filtred: false, icon: null},
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


  currentPlt = null;
  notChecked = false;
  checked = true;

  epMetricsCurrencySelected: any = 'EUR';
  CalibrationImpactCurrencySelected: any = 'EUR';
  epMetricsFinancialUnitSelected: any = 'Million';
  CalibrationImpactFinancialUnitSelected: any = 'Million';

  currentPath:any = null;
  currentPathId:any = null;

  visible = false;
  size = 'large';
  currentSystemTag = null;
  currentUserTag = null;
  sumnaryPltDetails: any = null;

  pltDetailsPermission: boolean ;

  epMetricInputValue: string | null;



  plts: any = [
    {
      pltId: 1,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      pathId: 1,
      pltName: "NATC-USM_RL_Imf.T1",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: true,
      checked: false
    },
    {
      pltId: 2,
      systemTags: [],
      userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      pathId: 2,
      pltName: "NATC-USM_RL_Imf.T1",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: false,
      checked: true
    },
    {
      pltId: 3,
      systemTags: [{tagId: 1}, {tagId: 5}, {tagId: 7}],
      userTags: [{tagId: 3}],
      pathId: 3,
      pltName: "NATC-USM_RL_Imf.T1",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: false,
      checked: false
    },
    {
      pltId: 4,
      systemTags: [],
      userTags: [],
      pathId: 4,
      pltName: "NATC-USM_RL_Imf.T1",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: true,
      checked: true
    },
    {
      pltId: 5,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 5}],
      userTags: [{tagId: 1}, {tagId: 2}],
      pathId: 5,
      pltName: "NATC-USM_RL_Imf.T1",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: false,
      checked: false
    },
    {
      pltId: 6,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      pathId: 1,
      pltName: "NATC-USM_RL_Imf.T1",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: true,
      checked: false
    },
    {
      pltId: 7,
      systemTags: [{tagId: 5}, {tagId: 7}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}],
      pathId: 2,
      pltName: "NATC-USM_RL_Imf.T1",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: true,
      checked: false
    },
    {
      pltId: 8,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}],
      pathId: 3,
      pltName: "NATC-USM_RL_Imf.T1",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: true,
      checked: true
    },
    {
      pltId: 9,
      systemTags: [{tagId: 1}, {tagId: 5}, {tagId: 3}],
      userTags: [{tagId: 2}],
      pltName: "NATC-USM_RL_Imf.T1",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: false,
      checked: false
    },
    {
      pltId: 10,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      pathId: 2,
      pltName: "NATC-USM_RL_Imf.T1",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: true,
      checked: false
    },
    {
      pltId: 11,
      systemTags: [],
      userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      pathId: 5,
      pltName: "NATC-USM_RL_Imf.T1",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: true,
      checked: false
    },
    {
      pltId: 12,
      systemTags: [{tagId: 5}, {tagId: 2}, {tagId: 7}],
      userTags: [{tagId: 2}, {tagId: 3}],
      pathId: 3,
      pltName: "NATC-USM_RL_Imf.T1",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: true,
      checked: true
    },
    {
      pltId: 13,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}],
      pltName: "NATC-USM_RL_Imf.T1",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: true,
      checked: false
    }
  ];

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
    {tagId: '1', tagName: 'Pricing V1', tagColor: '#893eff', innerTagContent: '1', innerTagColor: '#ac78ff'},
    {tagId: '2', tagName: 'Pricing V2', tagColor: '#06b8ff', innerTagContent: '2', innerTagColor: '#51cdff'},
    {tagId: '3', tagName: 'Final Princing', tagColor: '#c38fff', innerTagContent: '5', innerTagColor: '#d5b0ff'}
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
      title: 'Base', cards: [
        {
          chip: 'ID: 222881',
          content: 'HDIGlobal_CC_IT1607_XCV_SV_SURPLUS_729',
          borderColor: '#6e6cc0',
          selected: false
        },
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
        {chip: '0.95', content: 'Cedant QI', borderColor: '#03dac4', selected: false},
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


  constructor() {
  }


  ngOnInit() {

    let c = 209;
    addEventListener('scroll', function () {
      let x =  document.getElementsByClassName("ant-drawer-content")[0].style = "position:absolute;top:" + c + "px";
      var y = 209 - pageYOffset
      c = y;
    });


    console.log(this.pltDetailsPermission)
  }


  openDrawer(): void {
    this.visible = true;
  }

  closeDrawer(): void {
    this.visible = false;
  }

  openPltInDrawer(plt) {
    let selectedPlts = this.listOfDisplayPlts.filter(pt => pt.selected === true);
    if (selectedPlts.length > 0) {
      if (selectedPlts[0] === plt) {
        plt.selected = false;
        this.visible = false;
        this.sumnaryPltDetails = null;
        this.pltDetailsPermission = false;
      } else {
        this.listOfDisplayPlts.forEach( pt => pt.selected = false);
        plt.selected = true;
        this.sumnaryPltDetails = plt;
      }
    } else {
      this.listOfDisplayPlts.forEach(pt => pt.selected = false);
      plt.selected = true;
      this.visible = true;
      this.sumnaryPltDetails = plt;
      this.pltDetailsPermission = true;
    }


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

  selectPath(path) {
    this.paths.forEach(pa => {
      pa.selected = false;
    })
    path.selected = true;
    this.currentPath = path;
    this.currentPathId = path.id
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
    this.currentSystemTag = null;
    this.currentUserTag = null;
  }

  resetPath(){
    this.paths.forEach(path => {
      path.selected = false;
    })
    this.currentPath = null;
    this.currentPathId = null;
  }

}


