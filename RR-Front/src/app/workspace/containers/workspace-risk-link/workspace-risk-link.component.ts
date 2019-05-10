import {Component, OnInit} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-workspace-risk-link',
  templateUrl: './workspace-risk-link.component.html',
  styleUrls: ['./workspace-risk-link.component.scss']
})
export class WorkspaceRiskLinkComponent implements OnInit {
  leftNavbarIsCollapsed = false;
  lastSelectedIndex = null;
  checkedARC = false;
  checkedPricing = false;

  list1 = ['AZU-P-RL17-SQL14', 'AZU-P-RL17-SQL15'];
  list2 = ['Net Loss Pre Cat (RL)', 'Gross Loss (GR)', 'Net Cat (NC)'];
  list3 = ['Facultative Reinsurance Loss', 'Ground UP Loss (GU)', 'Variante Reinsurance Loss'];
  list4 = ['MLC (USD)', 'MLC (EUR)', 'YEN'];
  list6: any = ['Add calibration', 'item 1', 'item 2'];

  selectedRMS = 'AZU-P-RL17-SQL14';
  selectedPrELT = 'Net Loss Pre Cat (RL)';
  selectedPrEPM = 'Facultative Reinsurance Loss';
  selectedTarget = 'MLC (USD)';
  list5value = 'Add calibration';

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

  tableLeft: any = [
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

  scrollableCols = [
    {field: 'description', header: 'Description', width: '150px'},
    {field: 'engineVersion', header: 'Engine Version', width: '110px'},
    {field: 'groupeType', header: 'Groupe Type', width: '110px'},
    {field: 'cedant', header: 'cedant', width: '110px'},
  ];

  frozenCols = [
    {field: 'selected', header: 'selected', width: '20px'},
    {field: 'id', header: 'id', width: '30px'},
    {field: 'name', header: 'name', width: '190px'}
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

  displayDropdownRDMEDM = false;
  displayListRDMEDM = false;
  displayTable = false;
  displayImport = false;


  collapseHead = true;
  collapseAnalysis = true;
  collapseResult = true;

  currentStep = 0;

  constructor(private _helper: HelperService, private route: ActivatedRoute) {

  }

  ngOnInit() {
    this._helper.collapseLeftMenu$.subscribe((e) => {
      this.leftNavbarIsCollapsed = !this.leftNavbarIsCollapsed;
    });
  }

  toggleItems(RDM) {
    RDM.selected = !RDM.selected;
  }

  toggleItemsListRDM(RDM) {
    const selectedRDMItems = this.listRDM.filter( RM => RM.selected);
    const selectedEDMItems = this.listEDM.filter( EM => EM.selected);
    const nbrSelected = selectedEDMItems.length + selectedRDMItems.length;
    if (nbrSelected === 0) {
      RDM.selected = true;
      this.selectedEDMOrRDM = RDM.type;
      this.displayTable = true;
    } else {
      if (!RDM.selected) {
        this.unselectEDMRDM();
        RDM.selected = true;
        this.selectedEDMOrRDM = RDM.type;
      } else {
        this.unselectEDMRDM();
        this.displayTable = false;
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
    this.displayDropdownRDMEDM = !this.displayDropdownRDMEDM;
  }

  closeDropdown() {
    this.displayDropdownRDMEDM = false;
  }

  fillLists() {
    this.listEDM = [];
    this.listRDM = [];
    this.listEdmRdm.forEach((e) => {
      if (e.selected === true || e.scanned === true) {
        if (e.type === 'EDM') {
          const newItem = {
            id: e.id,
            name: e.name,
            type: e.type,
            selected: false,
            scanned: true,
            Reference: e.Reference
          };
          this.listEDM = [...this.listEDM, newItem];
        } else {
          const newItem = {
            id: e.id,
            name: e.name,
            type: e.type,
            selected: false,
            scanned: true,
            Reference: e.Reference
          };
          this.listRDM = [...this.listRDM, newItem];
        }
        e.scanned = true;
      }
    });
  }

  selectedItem() {
    this.fillLists();
    this.displayDropdownRDMEDM = false;
    this.listRDM.length > 0 && this.listEDM.length > 0 ? this.currentStep = 1 : this.currentStep = 0;
    this.listRDM.length > 0 || this.listEDM.length > 0 ? this.displayListRDMEDM = true : this.displayListRDMEDM = false;
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
      this.displayTable = false;
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
      this.displayImport = false;
      this.displayTable = false;
    }
    if (value === 0 && this.currentStep === 2) {
      this.selectStep(1);
      this.selectStep(0);
    }
  }

  displayImported() {
    if (this.currentStep === 1) {
      this.currentStep = 2;
      this.displayImport = true;
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
    const selectedRows = this.tableLeft.filter(ws => ws.selected === true);
    this.selectedEDMOrRDM === 'RDM' ? this.selectedAnalysis = selectedRows : this.selectedPortfolio = selectedRows;
  }

  selectSection(from, to) {
    if (from === to) {
      this.tableLeft[from].selected = !this.tableLeft[from].selected;
    } else {
      for (let i = from; i <= to; i++) {
        this.tableLeft[i].selected = !this.tableLeft[i].selected;
      }
    }
  }
}
