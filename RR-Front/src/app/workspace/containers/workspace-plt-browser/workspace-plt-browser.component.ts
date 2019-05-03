import {Component, OnInit} from '@angular/core';
import * as _ from 'lodash';

@Component({
  selector: 'app-workspace-plt-browser',
  templateUrl: './workspace-plt-browser.component.html',
  styleUrls: ['./workspace-plt-browser.component.scss']
})
export class WorkspacePltBrowserComponent implements OnInit {

  currentPlt = null;
  notChecked = false;
  checked = true;
  currencySelected: any;
  financialUnitSelected: any;
  visible = false;
  size = 'large';
  currentTag = null;
  sumnaryPltDetails: any = null;

  plts: any = [
    {
      pltId: 1,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
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
    {id: '1', icon: 'icon-assignment_24px', text: 'P-1686 Ameriprise 2018', selected: false},
    {id: '2', icon: 'icon-assignment_24px', text: 'P-1724 Trinity Kemper 2018', selected: false},
    {id: '3', icon: 'icon-assignment_24px', text: 'P-8592 TWIA 2018', selected: false},
    {id: '4', icon: 'icon-assignment_24px', text: 'P-9035 Post-insured PLTs', selected: false},
    {id: '5', icon: 'icon-sitemap', text: 'IP-1135 CXL2 Cascading 2018', selected: false}
  ];

  currencies = [
    {id: '1', name: 'Euro', label: 'EUR'},
    {id: '2', name: 'Us Dollar', label: 'USD'},
    {id: '3', name: 'Britsh Pound', label: 'GBP'},
    {id: '4', name: 'Canadian Dollar', label: 'CAD'},
    {id: '5', name: 'Moroccan Dirham', label: 'MAD'},
    {id: '5', name: 'Swiss Franc', label: 'CHF'},
    {id: '5', name: 'Saudi Riyal', label: 'SAR'},
    {id: '6', name: 'Bitcoin', label: 'XBT'}
  ];

  units = [
    {id: '1', label: 'Hundred'},
    {id: '2', label: 'Thousand'},
    {id: '3', label: 'Million'},
    {id: '4', label: 'Billion'},
    {id: '5', label: 'Trillion'}
  ];

  metrics = [
    {
      metricID: '1',
      retrunPeriod: '10000',
      OEP: '291621,790',
      AEP: '291621,790',
      TVaR_OEP: '321654789',
      TVaR_AEP: '458711620'
    },
    {
      metricID: '2',
      retrunPeriod: '5000',
      OEP: '291621,790',
      AEP: '291621,790',
      TVaR_OEP: '321654789',
      TVaR_AEP: '458711620'
    },
    {
      metricID: '3',
      retrunPeriod: '1000',
      OEP: '291621,790',
      AEP: '291621,790',
      TVaR_OEP: '321654789',
      TVaR_AEP: '458711620'
    },
    {
      metricID: '3',
      retrunPeriod: '500',
      OEP: '291621,790',
      AEP: '291621,790',
      TVaR_OEP: '321654789',
      TVaR_AEP: '458711620'
    },
    {
      metricID: '3',
      retrunPeriod: '250',
      OEP: '291621,790',
      AEP: '291621,790',
      TVaR_OEP: '321654789',
      TVaR_AEP: '458711620'
    },
    {
      metricID: '3',
      retrunPeriod: '100',
      OEP: '291621,790',
      AEP: '291621,790',
      TVaR_OEP: '321654789',
      TVaR_AEP: '458711620'
    },
    {
      metricID: '3',
      retrunPeriod: '50',
      OEP: '291621,790',
      AEP: '291621,790',
      TVaR_OEP: '321654789',
      TVaR_AEP: '458711620'
    },
    {
      metricID: '3',
      retrunPeriod: '25',
      OEP: '291621,790',
      AEP: '291621,790',
      TVaR_OEP: '321654789',
      TVaR_AEP: '458711620'
    },
    {
      metricID: '3',
      retrunPeriod: '10',
      OEP: '291621,790',
      AEP: '291621,790',
      TVaR_OEP: '321654789',
      TVaR_AEP: '458711620'
    },
    {
      metricID: '4',
      retrunPeriod: '5',
      OEP: '291621,790',
      AEP: '291621,790',
      TVaR_OEP: '321654789',
      TVaR_AEP: '458711620'
    },
    {
      metricID: '4',
      retrunPeriod: '2',
      OEP: '291621,790',
      AEP: '291621,790',
      TVaR_OEP: '321654789',
      TVaR_AEP: '458711620'
    }
  ];

  theads = [
    {
      title: 'Base', cards: [
        {
          chip: 'ID: 222881',
          content: 'HDIGlobal_CC_IT1607_XCV_SV_SURPLUS_729',
          borderColor: '#03dac4',
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
        {chip: '0.95', content: 'Cedant QI', borderColor: '#6e6cc0', selected: false},
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
  }


  openDrawer(): void {
    this.visible = true;
  }

  closeDrawer(): void {
    this.visible = false;
  }

  openPltInDrawer(plt) {
    this.plts.forEach(pt => pt.selected = false);
    plt.selected = true;
    this.visible = true;
    console.log(plt.pltId);
    this.sumnaryPltDetails = plt;
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
  }

  selectCardThead(card) {
    this.theads.forEach(thead => {
      thead.cards.forEach(card => {
        card.selected = false;
        console.log(card.selected)
      })
    })
    card.selected = true;
    console.log(card.selected)
    console.log(this.theads)
  }

  getCardBackground(selected) {
    if (selected) return "#FFF";
    else return "#f4f6fc";
  }

  deselectPlt(){
    this.plts.forEach(pt => pt.selected = false);
    this.visible = false;
    this.sumnaryPltDetails = null;
  }

}


