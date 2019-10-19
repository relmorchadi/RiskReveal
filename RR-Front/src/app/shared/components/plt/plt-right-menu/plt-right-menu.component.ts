import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Message} from '../../../message';
import * as rightMenuStore from './store';
import * as pltDetailsPopUpItemStore from '../plt-details-pop-up-item/store';
import * as _ from 'lodash';

@Component({
  selector: 'app-plt-right-menu',
  templateUrl: './plt-right-menu.component.html',
  styleUrls: ['./plt-right-menu.component.scss']
})
export class PltRightMenuComponent implements OnInit {

  @Input('Inputs') inputs: rightMenuStore.Input;
  pltDetailsItemsInput: pltDetailsPopUpItemStore.Input;

  pltPopUpItemConfig: pltDetailsPopUpItemStore.Input[];

  _input(key): any {
    return this.inputs[key];
  }

  @Output() actionDispatcher: EventEmitter<Message>= new EventEmitter<Message>();

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

  epMetricsCurrencySelected: any = 'EUR';
  CalibrationImpactCurrencySelected: any = 'EUR';
  epMetricsFinancialUnitSelected: any = 'Million';
  CalibrationImpactFinancialUnitSelected: any = 'Million';
  fullViewActive: boolean;
  currentFullView: any;

  constructor() {
    this.pltPopUpItemConfig= [
      {
        title: "Header",
        cols: {
          summary: [{header: "PLT Status", field: "pltStatus"},{header: "PLT Name", field: "pltName"},{header: "PLT ID", field: "pltId"},{header: "PLT Type", field: "pltType"}],
          sections: [{title: "PLT Detail" , headers: [{header: "PLT Status", field: "pltStatus"},{header: "PLT Name", field: "pltName"},{header: "PLT ID", field: "pltId"},{header: "PLT Type", field: "pltType"}]}]
        }
      },
      {
        title: "PLT Thread",
        cols: {
          summary: [{header: "Target RAP Code", field: "targetRAPCode"}, {header: "Target RAP Desc", field: "targetRAPDesc"}, {header: "Root Region Peril Code", field: "minimumGrainRPCode"}, {header: "Root Region Peril Desc", field: ""}, {header: "Minimum Grain Grain RP Code", field: "minimumGrainRPCode"}, {header: "Minimum Grain RP Desc", field: ""}, {header: "Region Peril Code", field: "regionPerilCode"}, {header: "Region Peril Desc", field: "regionPerilDesc"}, {header: "Peril Group Code", field: "perilGroupCode"}, {header: "Peril Group Desc", field: "perilGroupDesc"}, {header: "Grain", field: "grain"}, {header: "Grouped PLT", field: "groupedPlt"},{header: "Cloned PLT", field: "clonedPlt"}, {header: "Occurence Basis Default", field: "defaultOccurenceBasis"}, {header: "OccurenceBasis", field: "occurenceBasis"}, {header: "Base Adjusted", field: "baseAdjustment"}, {header: "Default Adjusted", field: "defaultAdjustment"}, {header: "Client Adjusted", field: "clientAdjustment"}, {header: "PLT Ccy", field: "pltCcy"}, {header: "Create Date", field: "createDate"}, {header: "Created By", field: "createdBy"}, {header: "Archived", field: "archived"}, {header: "Archived Date", field: "archivedDate"}]
        }
      },
      {
        title: "EP & Statistics",
        cols: {
          summary: [
            {header: "AEP 10", field: "aep10"},
            {header: "AEP 50", field: "aep50"},
            {header: "AEP 100", field: "aep100"},
            {header: "AEP 250", field: "aep250"},
            {header: "AEP 500", field: "aep500"},
            {header: "AEP 1000", field: "aep1000"},
            {header: "OEP 10", field: "oep10"},
            {header: "OEP 50", field: "oep50"},
            {header: "OEP 100", field: "oep100"},
            {header: "OEP 250", field: "oep250"},
            {header: "OEP 500", field: "oep500"},
            {header: "OEP 1000", field: "oep1000"},
            {header: "AAL", field: "aal"},
            {header: "CoV", field: "cov"},
            {header: "Std Dev", field: "stdDev"},
          ]
        }
      },
      {
        title: "Cloning Source",
        cols: {
          summary: [
            {header: "Clone Source PLT", field: "ClonedSourcePlt"}, {header: "Clone Source Project", field: "ClonedSourceProject"}, {header: "Clone Source Workspace", field: "ClonedSourceWorkspace"}
          ]
        }
      },
      {
        title: "Project",
        cols: {
          summary: [
            {header: "Project ID", field: "projectId"}, {header: "Project Name", field: "projectName"}, {header: "Project Description", field: "projectDescription"}, {header: "Project Type", field: "projectType"}, {header: "Assigned Analyst", field: "assignedTo"}, {header: "Created Date", field: "createdDate"}, {header: "Created By", field: "createdBy"}, {header: "Car ID", field: "carId"}, {header: "CAR Source System", field: "carSourceSystem"}, {header: "CAR Source System Ref", field: "carSourceSystemRef"}, {header: "CAR Raised Ref", field: "carRaisedRef"}, {header: "Master Project ID", field: "masterProjectId"}, {header: "Master Project Name", field: "masterProjectName"}, {header: "Master Project Description", field: "masterProjectDescription"}, {header: "Master Project Workspace", field: "masterProjectWorkspace"}, {header: "Master Project Client", field: "masterProjectClient"}, {header: "MGA Details", field: ""}
          ]
        }
      },
      {
        title: "Publishing Status",
        cols: {
          summary: [
            {header: "(X) Publishied to xAct", field: "xActPublication"}, {header: "Published to Pricing Date", field: "xActPublicationDate"}, {header: "Published to Pricing By", field: "publishedBy"}, {header: "(P) Priced PLT", field: "xActPriced"}, {header: "(A) Published to ARC", field: "arcPublication"}, {header: "Published to ARC Date", field: ""}, {header: "Published to ARC By", field: ""}
          ]
        }
      },
      {
        title: "Pure PLT",
        cols: {
          summary: [
            {header: "Pure PLT ID", field: "pureId"}, {header: "Pure PLT Name", field: "pureName"},
          ]
        }
      },
      {
        title: "Group PLT",
        cols: {
          summary: [
            {header: "Group PLT ID", field: ""}, {header: "Group PLT Name", field: ""}, {header: "Created Date", field: ""}, {header: "Created By", field: ""}, {header: "Source PLT Count", field: ""}, {header: "PLT Ccy", field: ""}, {header: "Source PLT ID", field: ""}, {header: "Source PLT Ccy", field: ""}, {header: "Source PLT Name", field: ""},
          ]
        }
      },
      {
        title: "Source Loss Table",
        cols: {
          summary: [
            {header: "Loss Table Type", field: "lossTableType"}, {header: "Loss Table Id", field: "lossTypeId"}, {header: "Vendor System", field: "vendorSystem"}, {header: "Modelling Data Source", field: "modellingDataSource"}, {header: "Source Analaysis Id", field: "sourceAnalysisId"}, {header: "Source Analysis Name", field: "sourceAnalysisName"}, {header: "Source Analysis Description", field: "sourceAnalysisDescription"}, {header: "Source Financial Perspective", field: "sourceFinancialPerspective"}
          ]
        }
      },
      {
        title: "Inuring Package",
        cols: {
          summary: [
            {header: "ID", field: "inuringPackageId"}, {header: "Name", field: "inuringPackageName"}, {header: "Description", field: "inuringPackageDescription"}, {header: "Package Status", field: "inuringPackageStatus"}, {header: "Locked Indicator", field: "inuringPackageLocked"}, {header: "Created On", field: "inuringCreatedOn"}, {header: "Last Updated", field: "inuringLastModifiedOn"}, {header: "Last Updated By", field: "inuringLastModifiedBy"},
          ]
        }
      }
    ];
    this.fullViewActive= false;
    this.currentFullView= {

    };
  }

  ngOnInit() {
  }

  closeDrawer() {
    this.actionDispatcher.emit({
      type: this.inputs['visible'] ? rightMenuStore.closeDrawer : rightMenuStore.openDrawer
    })
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

  unselectPlt(pltId: any) {
    this.actionDispatcher.emit({
      type: rightMenuStore.unselectPlt,
      payload: pltId
    })
  }

  popupActionHandler(action: Message) {
    switch (action.type) {

      case pltDetailsPopUpItemStore.openSection:
        this.openSection(action.payload);
        break;

      case pltDetailsPopUpItemStore.closeSection:
        this.closeSection();
        break;
    }
  }

  openSection(title) {
    this.fullViewActive= true;
    console.log(_.find(this.pltPopUpItemConfig, ['title', title]));
    this.currentFullView = _.find(this.pltPopUpItemConfig, ['title', title]).cols.sections;
  }

  closeSection() {
    this.fullViewActive= false;
    this.currentFullView= {};
  }

  selectTab($event: void) {
    this.actionDispatcher.emit({
      type: rightMenuStore.setSelectedTabByIndex,
      payload: $event
    })
  }

}
