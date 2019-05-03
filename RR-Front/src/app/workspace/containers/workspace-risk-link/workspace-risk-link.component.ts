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

  RDM: any = [
    {id: 1, name: 'Brkr_PICC_CATXL_2018RNL_ToMkt_RDM', selected: false, Reference: '413'},
    {id: 2, name: 'Anxin_NMQSS_ZJ_Training_R', selected: false, Reference: '3'},
    {id: 3, name: 'CF1803_PORT_R', selected: false, Reference: '3'},
    {id: 4, name: 'Brkr_PICC_CATXL_2018RNL_ToMkt_RDM', selected: false, Reference: '425'},
    {id: 5, name: 'Anxin_NMQSS_ZJ_Training_R', selected: false, Reference: '12'},
    {id: 6, name: 'CF1803_PORT_R', selected: false, Reference: '4/25'},
    {id: 7, name: 'Anxin_NMQSS_ZJ_Training_R', selected: false, Reference: '4/12'},
    {id: 8, name: 'Anxin_NMQSS_ZJ_Training_R', selected: false, Reference: '7/15'}
  ];

  EDM: any = [
    {id: 1, name: 'Brkr_PICC_CATXL_2018RNL_ToMkt_EDM', selected: false, Reference: '413'},
    {id: 2, name: 'Anxin_NMQSS_ZJ_Training_E', selected: false, Reference: '2/3'},
    {id: 3, name: 'CF1803_PORT_E', selected: false, Reference: '33/4'},
    {id: 4, name: 'Brkr_PICC_CATXL_2018RNL_ToMkt_EDM', selected: false, Reference: '425'},
    {id: 5, name: 'Anxin_NMQSS_ZJ_Training_E', selected: false, Reference: '12/1'},
    {id: 6, name: 'CF1803_PORT_E', selected: false, Reference: '4/25'},
    {id: 7, name: 'Anxin_NMQSS_ZJ_Training_E', selected: false, Reference: '4/12'},
    {id: 8, name: 'Anxin_NMQSS_ZJ_Training_E', selected: false, Reference: '7/15'}
  ];
  /* tslint:disable */
  tableLeft: any = [
    {checked: false, id: '10', name: 'Europe All Lines, EP Wind Only', AlreadyImported: false, description: 'EUWS_EP_PLA_DLM110', engineVersion: '11.0.141 1.2', groupeType: 'Analysis', cedant: 'RMS_EUWS_industry'},
    {checked: false, id: '10', name: 'Europe All Lines, EP Wind Only', AlreadyImported: false, description: 'EUWS_EP_PLA_DLM110', engineVersion: '11.0.141 1.2', groupeType: 'Analysis', cedant: 'RMS_EUWS_industry'},
    {checked: false, id: '10', name: 'Europe All Lines, EP Wind Only', AlreadyImported: false, description: 'EUWS_EP_PLA_DLM110', engineVersion: '11.0.141 1.2', groupeType: 'Analysis', cedant: 'RMS_EUWS_industry'},
    {checked: false, id: '10', name: 'Europe All Lines, EP Wind Only', AlreadyImported: true ,description: 'EUWS_EP_PLA_DLM110', engineVersion: '11.0.141 1.2', groupeType: 'Analysis', cedant: 'RMS_EUWS_industry'},
    {checked: false, id: '10', name: 'Europe All Lines, EP Wind Only', AlreadyImported: false, description: 'EUWS_EP_PLA_DLM110', engineVersion: '11.0.141 1.2', groupeType: 'Analysis', cedant: 'RMS_EUWS_industry'},
    {checked: false, id: '10', name: 'Europe All Lines, EP Wind Only', AlreadyImported: true ,description: 'EUWS_EP_PLA_DLM110', engineVersion: '11.0.141 1.2', groupeType: 'Analysis', cedant: 'RMS_EUWS_industry'},
    {checked: false, id: '10', name: 'Europe All Lines, EP Wind Only', AlreadyImported: false, description: 'EUWS_EP_PLA_DLM110', engineVersion: '11.0.141 1.2', groupeType: 'Analysis', cedant: 'RMS_EUWS_industry'},
    {checked: false, id: '10', name: 'Europe All Lines, EP Wind Only', AlreadyImported: false, description: 'EUWS_EP_PLA_DLM110', engineVersion: '11.0.141 1.2', groupeType: 'Analysis', cedant: 'RMS_EUWS_industry'},
    {checked: false, id: '10', name: 'Europe All Lines, EP Wind Only', AlreadyImported: false, description: 'EUWS_EP_PLA_DLM110', engineVersion: '11.0.141 1.2', groupeType: 'Analysis', cedant: 'RMS_EUWS_industry'},
    {checked: false, id: '10', name: 'Europe All Lines, EP Wind Only', AlreadyImported: false, description: 'EUWS_EP_PLA_DLM110', engineVersion: '11.0.141 1.2', groupeType: 'Analysis', cedant: 'RMS_EUWS_industry'},
    {checked: false, id: '10', name: 'Europe All Lines, EP Wind Only', AlreadyImported: true ,description: 'EUWS_EP_PLA_DLM110', engineVersion: '11.0.141 1.2', groupeType: 'Analysis', cedant: 'RMS_EUWS_industry'},
    {checked: false, id: '10', name: 'Europe All Lines, EP Wind Only', AlreadyImported: true ,description: 'EUWS_EP_PLA_DLM110', engineVersion: '11.0.141 1.2', groupeType: 'Analysis', cedant: 'RMS_EUWS_industry'},
    {checked: false, id: '10', name: 'Europe All Lines, EP Wind Only', AlreadyImported: false ,description: 'EUWS_EP_PLA_DLM110', engineVersion: '11.0.141 1.2', groupeType: 'Analysis', cedant: 'RMS_EUWS_industry'},
    {checked: false, id: '10', name: 'Europe All Lines, EP Wind Only', AlreadyImported: false ,description: 'EUWS_EP_PLA_DLM110', engineVersion: '11.0.141 1.2', groupeType: 'Analysis', cedant: 'RMS_EUWS_industry'},
  ];
  /* tslint:enable */

  scrollableCols = [
    { field: 'description', header: 'Description', width: '150px' },
    { field: 'engineVersion', header: 'Engine Version', width: '110px' },
    { field: 'groupeType', header: 'Groupe Type', width: '110px' },
    { field: 'cedant', header: 'cedant', width: '110px' },
  ];

  frozenCols = [
    { field: 'checked', header: 'checked', width: '20px' },
    { field: 'id', header: 'id', width: '30px' },
    { field: 'name', header: 'name', width: '160px' },
    { field: 'AlreadyImported', header: 'Already Imported', width: '55px' }
  ];
  /* tslint:disable */
  SummaryInfo: any = [
    {status : true, portfolio: 'Portfolio 2', exposedCurrency: 'USD', TargetCurrency: 'USD', EDM: 'EDM2', importID: '1', dateImport: 'Wed Nov 14 13:08:59 CET 2018', User1 : 'Nathalie Dulac'},
    {status : false, portfolio: 'Portfolio 2', exposedCurrency: 'USD', TargetCurrency: 'USD', EDM: 'EDM2', importID: '1', dateImport: 'Wed Nov 14 13:08:59 CET 2018', User1 : 'Nathalie Dulac'}
  ];

  displayDropdownEDM: boolean = false;
  displayDropdownRDM: boolean = false;
  displayListRDM: boolean = false;
  displayListEDM: boolean = false;
  displayTable: boolean = false;
  displayImport: boolean = false;


  collapsehead: boolean = true;
  collapseright: boolean = true;
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

/*  ngOnDestroy (): void {
    _.forEach(this.componentSubscription, (e) => _.invoke(e, 'unsubscribe'));
  }*/

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

  selectAll(value) {
    if (value === 1) {
      this.EDM.forEach((e) => {
        e.selected = true;
      });
    } else {
      this.RDM.forEach((e) => {
        e.selected = true;
      });
    }
  }
  unselectAll(value) {
    if (value === 1) {
      this.EDM.forEach((e) => {
        e.selected = false;
      });
    } else {
      this.RDM.forEach((e) => {
        e.selected = false;
      });
    }
  }

  openCloseDropdown(value) {
    if (value === 1) {
      this.displayDropdownEDM = !this.displayDropdownEDM;
    } else {
      this.displayDropdownRDM = !this.displayDropdownRDM;
    }
  }

  closeDropdown(value) {
    if (value === 1) {
      this.displayDropdownEDM = false;
    } else {
      this.displayDropdownRDM = false;
    }
  }

  selectedItem(value) {
    if (value === 1) {
      this.listEDM = [];
      this.EDM.forEach((e) => {
        if (e.selected === true) {
          const newItem = {id: e.id, name: e.name, selected: false, Reference: e.Reference};
          this.listEDM = [...this.listEDM , newItem];
        }
      });
      this.displayDropdownEDM = false;
      this.listEDM.length === 0 ? this.displayListEDM = false : this.displayListEDM = true;
    } else {
      this.listRDM = [];
      this.RDM.forEach((e) => {
        if (e.selected === true) {
          const newitem = {id: e.id, name: e.name, selected: false, Reference: e.Reference};
          this.listRDM = [...this.listRDM, newitem];
        }
      });
      this.displayDropdownRDM = false;
      this.listRDM.length === 0 ? this.displayListRDM = false : this.displayListRDM = true;
      this.listRDM.length > 0 && this.listEDM.length > 0 ? this.currentStep = 1 : this.currentStep = 0;
    }
    this.collapseright = true;
  }
  selectStep(value) {
    if (value === 0 && this.currentStep === 1) {
      this.currentStep = 0;
      this.listEDM = [];
      this.listRDM = [];
      this.displayListEDM = false;
      this.displayListRDM = false;
      this.displayTable = false;
      this.unselectAll(1);
      this.unselectAll(2);
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
      this.displayImport =  false;
      this.displayTable = false;
    }
    if (value === 0 && this.currentStep === 2) {
      this.selectStep(1);
      this.selectStep(0);
    }
  }
  displayImported() {
    if ( this.currentStep === 1) {
      this.currentStep = 2;
      this.displayImport = true;
    }
  }
  collapseHead() {
    this.collapsehead = !this.collapsehead;
  }
  collapseRight() {
    this.collapseright = !this.collapseright;
  }
  collapseFooter() {
    this.collapsefooter = !this.collapsefooter;
  }
}
