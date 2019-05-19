import {Component, OnInit} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute} from '@angular/router';
import {Select, Store} from '@ngxs/store';
import {Observable} from 'rxjs';
import {RiskLinkState} from '../../store/states';
import {RiskLinkModel} from '../../model/risk_link.model';
import {
  LoadRiskLinkDataAction,
  PatchRiskLinkAction,
  PatchRiskLinkCollapseAction, PatchRiskLinkDisplayAction,
  PatchRiskLinkFinancialPerspectiveAction, SelectRiskLinkEDMAndRDM, ToggleRiskLinkEDMAndRDM
} from '../../store/actions';

@Component({
  selector: 'app-workspace-risk-link',
  templateUrl: './workspace-risk-link.component.html',
  styleUrls: ['./workspace-risk-link.component.scss']
})
export class WorkspaceRiskLinkComponent implements OnInit {

  lastSelectedIndex = null;

  closePrevent = false;

  listEDM: any = [];
  listRDM: any = [];
  selectedEDMOrRDM: string;

  selectedAnalysis: any = [];
  selectedPortfolio: any = [];

  listEdmRdm: any = [
    {id: 1, name: 'AA2012_syntheticCurve_E', type: 'EDM', selected: false, scanned: false, Reference: '0/13'},
    {id: 2, name: 'AA2012_syntheticCurve_R', type: 'RDM', selected: false, scanned: false, Reference: '0/5'},
    {id: 3, name: 'AA2016_syntheticCurve_R', type: 'RDM', selected: false, scanned: false, Reference: '0/13'},
    {id: 4, name: 'AA2017_SEA_syntheticCurve_R', type: 'RDM', selected: false, scanned: false, Reference: '0/13'},
    {id: 5, name: 'ALMF_test_E', type: 'EDM', selected: false, scanned: false, Reference: '0/25'},
    {id: 6, name: 'ALMF_test_R', type: 'RDM', selected: false, scanned: false, Reference: '0/12'},
    {id: 7, name: 'Anxin_NMQSS_ZJ_Training_R', type: 'RDM', selected: false, scanned: false, Reference: '0/3'},
    {id: 8, name: 'Anxin_NMQSS_ZJ_Training_E', type: 'EDM', selected: false, scanned: false, Reference: '0/4'},
    {id: 9, name: 'Aon_IF_Flood_Example_E', type: 'EDM', selected: false, scanned: false, Reference: '0/2'},
    {id: 10, name: 'Brkr_PICC_CATXL_2018RNL_ToMkt_EDM', type: 'EDM', selected: false, scanned: false, Reference: '0/15'},
    {id: 11, name: 'Brkr_PICC_CATXL_2018RNL_ToMkt_RDM', type: 'RDM', selected: false, scanned: false, Reference: '0/13'},
    {id: 12, name: 'CC_ALMF_test_E', type: 'EDM', selected: false, scanned: false, Reference: '0/13'},
    {id: 13, name: 'CG1801_DEU_Allianz_DEFL_R', type: 'RDM', selected: false, scanned: false, Reference: '0/41'},
    {id: 14, name: 'CG1801_DEU_Allianz_DEFL_E', type: 'EDM', selected: false, scanned: false, Reference: '0/41'},
    {id: 15, name: 'Anxin_2018_NMQSS_ZJ_Training_R', type: 'RDM', selected: false, scanned: false, Reference: '0/15'},
    {id: 16, name: 'Anxin_2018_NMQSS_ZJ_Training_E', type: 'EDM', selected: false, scanned: false, Reference: '0/17'},
    {id: 17, name: 'CF1803_PORT_R', type: 'RDM', selected: false, scanned: false, Reference: '0/3'},
    {id: 18, name: 'CF1803_PORT_E', type: 'EDM', selected: false, scanned: false, Reference: '0/25'}
  ];

  tableLeftAnalysis: any = [
    {
      selected: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      selected: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      selected: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      selected: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: true,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      selected: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      selected: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: true,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      selected: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      selected: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      selected: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      selected: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      selected: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: true,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      selected: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: true,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      selected: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      selected: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
  ];

  tableLeftProtfolio: any = [
    {
      selected: false,
      id: '760',
      number: 'FA0020553_01',
      name: 'FA0020553_01',
      creationDate: '2019-01-03T00:57:10.840Z',
      descriptionType: 'DET',
      cedant: 'RMS_EUWS_industry'
    },
    {
      selected: false,
      id: '761',
      number: 'FA0056486_01',
      name: 'FA0056486_01',
      creationDate: '2019-01-03T00:57:10.840Z',
      descriptionType: 'DET',
      cedant: 'RMS_EUWS_industry'
    },
    {
      selected: false,
      id: '762',
      number: 'FA0040287_01',
      name: 'FA0040287_01',
      creationDate: '2019-01-03T00:57:10.840Z',
      descriptionType: 'DET',
      cedant: 'RMS_EUWS_industry'
    },
    {
      selected: false,
      id: '763',
      number: 'FA0023693_01',
      name: 'FA0023693_01',
      creationDate: '2019-01-03T00:57:10.840Z',
      descriptionType: 'DET',
      cedant: 'RMS_EUWS_industry'
    },
  ]

  scrollableColsAnalysis = [
    {field: 'description', header: 'Description', width: '150px'},
    {field: 'engineVersion', header: 'Engine Version', width: '110px'},
    {field: 'groupeType', header: 'Groupe Type', width: '110px'},
    {field: 'cedant', header: 'cedant', width: '110px'},
  ];

  frozenColsAnalysis = [
    {field: 'selected', header: 'selected', width: '20px'},
    {field: 'id', header: 'id', width: '30px'},
    {field: 'name', header: 'name', width: '190px'}
  ];

  scrollableColsPortfolio = [
    {field: 'name', header: 'Name', width: '150px'},
    {field: 'creationDate', header: 'Creation Date', width: '180px'},
    {field: 'descriptionType', header: 'Description Type', width: '180px'},
    {field: 'cedant', header: 'cedant', width: '120px'},
  ];

  frozenColsPortfolio = [
    {field: 'selected', header: 'selected', width: '20px'},
    {field: 'id', header: 'id', width: '30px'},
    {field: 'number', header: 'Number', width: '190px'}
  ];

  summaryInfo: any = [
    {
      scanned: true,
      status: 100,
      id: '286',
      number: 'EUGU_CHIPZCP_CDSC_PR',
      name: 'AUWS_PR_20170930',
      exposedLocation: true,
      exposedCurrency: 'USD',
      targetCurrency: 'USD',
      unitMultiplier: 1.0,
      proportion: '100%',
      EDM: 'AA2012_SyntheticCurve_E'
    },
    {
      scanned: true,
      status: 60,
      id: '325',
      number: 'EUGU_CHIPZCP_CDSC_PR',
      name: 'AUWS_PR_20170930',
      exposedLocation: true,
      exposedCurrency: 'USD',
      targetCurrency: 'USD',
      unitMultiplier: 1.0,
      proportion: '100%',
      EDM: 'AA2012_SyntheticCurve_E'
    },
    {
      scanned: true,
      status: 0,
      id: '284',
      number: 'EUGU_CHIPZCP_CDSC_PR',
      name: 'AUWS_PR_20170930',
      exposedLocation: true,
      exposedCurrency: 'USD',
      targetCurrency: 'USD',
      unitMultiplier: 1.0,
      proportion: '100%',
      EDM: 'AA2012_SyntheticCurve_E'
    },
  ];

  resultsInfo: any = [
    {
      scanned: false,
      status: 100,
      id: '286',
      name: 'Europe All Lines, EP Wind Only',
      description: 'Europe All Lines, EP Wind Only',
      regionPeril: 'DEFL',
      analysisCurrency: 'USD',
      targetCurrency: 'USD',
      ELT: 'GR',
      occurrenceBasis: 'PerEvent',
      unitMultiplier: 1.0
    },
    {
      scanned: false,
      status: 50,
      id: '285',
      name: 'Europe All Lines, EP Wind Only',
      description: 'Europe All Lines, EP Wind Only',
      regionPeril: 'DEFL',
      analysisCurrency: 'USD',
      targetCurrency: 'USD',
      ELT: 'GR',
      occurrenceBasis: 'PerEvent',
      unitMultiplier: 1.0
    },
    {
      scanned: false,
      status: 20,
      id: '284',
      name: 'Europe All Lines, EP Wind Only',
      description: 'Europe All Lines, EP Wind Only',
      regionPeril: 'DEFL',
      analysisCurrency: 'USD',
      targetCurrency: 'USD',
      ELT: 'GR',
      occurrenceBasis: 'PerEvent',
      unitMultiplier: 1.0
    },
  ];

  currentStep = 0;

  @Select(RiskLinkState)
  state$: Observable<RiskLinkModel>;
  state: RiskLinkModel = null;

  constructor(private _helper: HelperService, private route: ActivatedRoute, private store: Store) {
  }

  ngOnInit() {
    this.store.dispatch(new LoadRiskLinkDataAction());
    this.state$.subscribe(value => this.state = _.merge({}, value));
  }

  dataList(data = null) {
    if (data) {
      return _.toArray(data);
    }
    return _.toArray(this.state.listEdmRdm.data);
  }

  toggleItems(RDM) {
    this.store.dispatch(new ToggleRiskLinkEDMAndRDM({RDM}));
    this.closePrevent = false;
  }

  toggleItemsListRDM(RDM) {
    const selectedRDMItems = this.listRDM.filter( RM => RM.selected);
    const selectedEDMItems = this.listEDM.filter( EM => EM.selected);
    const nbrSelected = selectedEDMItems.length + selectedRDMItems.length;
    if (nbrSelected === 0) {
      RDM.selected = true;
      this.selectedEDMOrRDM = RDM.type;
      this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayTable', value: true}));
    } else {
      if (!RDM.selected) {
        this.unselectEDMRDM();
        RDM.selected = true;
        this.selectedEDMOrRDM = RDM.type;
      } else {
        this.unselectEDMRDM();
        this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayTable', value: false}));
        this.selectedEDMOrRDM = null;
      }
    }
  }

  selectAll() {
    this.listEdmRdm.forEach((e) => {
      e.selected = true;
    });
  }

  unselectAll() {
    this.listEdmRdm.forEach((e) => {
      e.selected = false;
    });
  }

  unselectEDMRDM() {
    this.listRDM.forEach((e) => {
        e.selected = false;
      }
    );
    this.listEDM.forEach((e) => {
        e.selected = false;
      }
    );
  }

  refreshAll() {
    this.listEdmRdm.forEach((e) => {
      e.scanned = false;
      e.selected = false;
    });
  }

  openCloseDropdown() {
    this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayDropdownRDMEDM', value: !this.state.display.displayDropdownRDMEDM}));
  }

  closeDropdown() {
    if(this.state.display.displayDropdownRDMEDM && this.closePrevent)
      this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayDropdownRDMEDM', value: false}));
    this.closePrevent = true;
  }

  fillLists() {
    this.store.dispatch(new SelectRiskLinkEDMAndRDM());
  }

  selectedItem() {
    this.fillLists();
    this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayDropdownRDMEDM', value: false}));
    if (this.listRDM.length > 0 && this.listEDM.length > 0) {
      this.currentStep = 1;
    } else {
      this.currentStep = 0;
    }
    const array = _.toArray(this.state.listEdmRdm.selectedListEDMAndRDM);
    if (array.length > 0)  {
      this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayListRDMEDM', value: true}));
    } else {
      this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayListRDMEDM', value: false}));
    }
  }

  scanItem(item) {
    console.log('i here');
    item.scanned = false;
    setTimeout(() =>
      item.scanned = true, 1000
    );
  }

  selectStep(value) {
    if (value === 0 && this.currentStep === 1) {
      this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayTable', value: false}));
      this.refreshAll();
      this.selectedItem();
    }
    if (value === 1 && this.currentStep === 2) {
      this.currentStep = 1;
      this.listEDM.forEach((e) => {
          e.selected = false;
        }
      );
      this.listRDM.forEach((e) => {
          e.selected = false;
        }
      );
      this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayImport', value: false}));
      this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayTable', value: false}));
    }
    if (value === 0 && this.currentStep === 2) {
      this.selectStep(1);
      this.selectStep(0);
    }
  }

  displayImported() {
    if (this.currentStep === 1) {
      this.currentStep = 2;
      this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayImport', value: true}));
    }
  }

  selectRow(row: any, index: number) {
    if ((window as any).event.ctrlKey) {
      row.selected = !row.selected;
    }
    if ((window as any).event.shiftKey) {
      event.preventDefault();
      if (this.lastSelectedIndex) {
        this.selectSection(Math.min(index, this.lastSelectedIndex), Math.max(index, this.lastSelectedIndex));
        this.lastSelectedIndex = null;
      } else {
        this.lastSelectedIndex = index;
      }
    }
    // this.currentSelectedItem = row;
    const selectedRowsAnalysis = this.tableLeftAnalysis.filter(ws => ws.selected === true);
    const selectedRowsPortfolio = this.tableLeftProtfolio.filter(ws => ws.selected === true);
    this.selectedEDMOrRDM === 'RDM' ? this.selectedAnalysis = selectedRowsAnalysis : this.selectedPortfolio = selectedRowsPortfolio;
  }

  selectSection(from, to) {
    let tableUpdated: any ;
    if (this.selectedEDMOrRDM === 'RDM') {
      tableUpdated = this.tableLeftAnalysis;
    } else {
      tableUpdated = this.tableLeftProtfolio;
    }
    if (from === to) {
      tableUpdated[from].selected = !tableUpdated[from].selected;
    } else {
      for (let i = from; i <= to; i++) {
        tableUpdated[i].selected = !tableUpdated[i].selected;
      }
    }
  }

  getScrollableCols() {
    if (this.selectedEDMOrRDM === 'RDM') { return this.scrollableColsAnalysis; } else { return this.scrollableColsPortfolio; }
  }

  getFrozenCols() {
    if (this.selectedEDMOrRDM === 'RDM') { return this.frozenColsAnalysis; } else { return this.frozenColsPortfolio; }
  }

  getTableData() {
    if (this.selectedEDMOrRDM === 'RDM') { return this.tableLeftAnalysis; } else { return this.tableLeftProtfolio; }
  }

  changeCollapse(value) {
    this.store.dispatch(new PatchRiskLinkCollapseAction({key: value}));
  }

  changeFinancialValidator(value, item) {
    this.store.dispatch(new PatchRiskLinkFinancialPerspectiveAction({key: value, value: item}));
  }
}
