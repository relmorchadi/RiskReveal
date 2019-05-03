import {Component, OnDestroy, OnInit} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-workspace-risk-link',
  templateUrl: './workspace-risk-link.component.html',
  styleUrls: ['./workspace-risk-link.component.scss']
})
export class WorkspaceRiskLinkComponent implements OnInit {
  leftNavbarIsCollapsed: boolean = false;
  collapseWorkspaceDetail: boolean = true;
  componentSubscription: any = [];
  selectedPrStatus = '1';

  list1: any = ['RMS Instance', 'item 1', 'item 2'];
  list2: any = ['Financial Perspective', 'item 1', 'item 2'];
  list3: any = ['Currency', 'item 1', 'item 2'];
  list4: any = ['Currently Imported', 'item 1', 'item 2'];
  list5: any = ['Add calibration', 'item 1', 'item 2'];

  list1value: string = 'RMS Instance';
  list2value: string = 'Financial Perspective';
  list3value: string = 'Currency';
  list4value: string = 'Currently Imported';
  list5value: string = 'Add calibration';

  listEDM: any = [];
  listRDM: any = [];

  listEdmRdm: any = [
    {id: 1, name: 'AA2012_syntheticCurve_E', type: 'EDM', selected: false, Reference: '0/13'},
    {id: 2, name: 'AA2012_syntheticCurve_R', type: 'RDM', selected: false, Reference: '0/5'},
    {id: 3, name: 'AA2016_syntheticCurve_R', type: 'RDM', selected: false, Reference: '0/13'},
    {id: 4, name: 'AA2017_SEA_syntheticCurve_R', type: 'RDM', selected: false, Reference: '0/13'},
    {id: 5, name: 'ALMF_test_E', type: 'EDM', selected: false, Reference: '0/25'},
    {id: 6, name: 'ALMF_test_R', type: 'RDM', selected: false, Reference: '0/12'},
    {id: 7, name: 'Anxin_NMQSS_ZJ_Training_R', type: 'RDM', selected: false, Reference: '0/3'},
    {id: 8, name: 'Anxin_NMQSS_ZJ_Training_E', type: 'EDM', selected: false, Reference: '0/4'},
    {id: 9, name: 'Aon_IF_Flood_Example_E', type: 'EDM', selected: false, Reference: '0/2'},
    {id: 10, name: 'Brkr_PICC_CATXL_2018RNL_ToMkt_EDM', type: 'EDM', selected: false, Reference: '0/15'},
    {id: 11, name: 'Brkr_PICC_CATXL_2018RNL_ToMkt_RDM', type: 'RDM', selected: false, Reference: '0/13'},
    {id: 12, name: 'CC_ALMF_test_E', type: 'EDM', selected: false, Reference: '0/13'},
    {id: 13, name: 'CG1801_DEU_Allianz_DEFL_R', type: 'RDM', selected: false, Reference: '0/41'},
    {id: 14, name: 'CG1801_DEU_Allianz_DEFL_E', type: 'EDM', selected: false, Reference: '0/41'},
    {id: 15, name: 'Anxin_2018_NMQSS_ZJ_Training_R', type: 'RDM', selected: false, Reference: '0/15'},
    {id: 16, name: 'Anxin_2018_NMQSS_ZJ_Training_E', type: 'EDM', selected: false, Reference: '0/17'},
    {id: 17, name: 'CF1803_PORT_R', type: 'RDM', selected: false, Reference: '0/3'},
    {id: 18, name: 'CF1803_PORT_E', type: 'EDM', selected: false, Reference: '0/25'}
  ];

  /* tslint:disable */
  tableLeft: any = [
    {
      checked: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      checked: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      checked: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      checked: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: true,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      checked: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      checked: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: true,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      checked: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      checked: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      checked: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      checked: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      checked: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: true,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      checked: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: true,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      checked: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
    {
      checked: false,
      id: '10',
      name: 'Europe All Lines, EP Wind Only',
      AlreadyImported: false,
      description: 'EUWS_EP_PLA_DLM110',
      engineVersion: '11.0.141 1.2',
      groupeType: 'Analysis',
      cedant: 'RMS_EUWS_industry'
    },
  ];
  /* tslint:enable */

  scrollableCols = [
    {field: 'description', header: 'Description', width: '150px'},
    {field: 'engineVersion', header: 'Engine Version', width: '110px'},
    {field: 'groupeType', header: 'Groupe Type', width: '110px'},
    {field: 'cedant', header: 'cedant', width: '110px'},
  ];

  frozenCols = [
    {field: 'checked', header: 'checked', width: '20px'},
    {field: 'id', header: 'id', width: '30px'},
    {field: 'name', header: 'name', width: '160px'},
    {field: 'AlreadyImported', header: 'Already Imported', width: '55px'}
  ];
  /* tslint:disable */
  SummaryInfo: any = [
    {
      status: true,
      portfolio: 'Portfolio 2',
      exposedCurrency: 'USD',
      TargetCurrency: 'USD',
      EDM: 'EDM2',
      importID: '1',
      dateImport: 'Wed Nov 14 13:08:59 CET 2018',
      User1: 'Nathalie Dulac'
    },
    {
      status: false,
      portfolio: 'Portfolio 2',
      exposedCurrency: 'USD',
      TargetCurrency: 'USD',
      EDM: 'EDM2',
      importID: '1',
      dateImport: 'Wed Nov 14 13:08:59 CET 2018',
      User1: 'Nathalie Dulac'
    }
  ];

  displayDropdownRDMEDM: boolean = false;
  displayListRDMEDM: boolean = false;
  displayTable: boolean = false;
  displayImport: boolean = false;


  collapsehead: boolean = true;
  collapsefooter: boolean = true;
  /* tslint:enable */
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
    let nbrSelected = 0;
    this.listRDM.forEach((e) => {
        e.selected === true ? nbrSelected = nbrSelected + 1 : null;
      }
    );
    this.listEDM.forEach((e) => {
        e.selected === true ? nbrSelected = nbrSelected + 1 : null;
      }
    );
    if (nbrSelected === 0) {
      RDM.selected = true;
      this.displayTable = true;
    } else {
      if (!RDM.selected) {
        this.listRDM.forEach((e) => {
            e.selected = false;
          }
        );
        this.listEDM.forEach((e) => {
            e.selected = false;
          }
        );
        RDM.selected = true;
      } else {
        this.listRDM.forEach((e) => {
            e.selected = false;
          }
        );
        this.listEDM.forEach((e) => {
            e.selected = false;
          }
        );
        this.displayTable = false;
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
      if (e.selected === true && e.type === 'EDM') {
        const newItem = {id: e.id, name: e.name, type: e.type, selected: false, synchronized: false, Reference: e.Reference};
        this.listEDM = [...this.listEDM, newItem];
      } else if (e.selected === true && e.type === 'RDM') {
        const newItem = {id: e.id, name: e.name, type: e.type, selected: false, synchronized: false, Reference: e.Reference};
        this.listRDM = [...this.listRDM, newItem];
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
    item.synchronized = true;
    setTimeout(() =>
      item.synchronized = false, 1000
    );
  }

  selectStep(value) {
    if (value === 0 && this.currentStep === 1) {
      this.currentStep = 0;
      this.listEDM = [];
      this.listRDM = [];
      this.displayListRDMEDM = false;
      this.displayTable = false;
      this.unselectAll();
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

  collapseHead() {
    this.collapsehead = !this.collapsehead;
  }

  collapseFooter() {
    this.collapsefooter = !this.collapsefooter;
  }
}
