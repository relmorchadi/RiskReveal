import {Component, OnDestroy, OnInit} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute} from '@angular/router';
let staticTabs: any = [{title: '02PY376', year: '2019'},
  {title: '02PY376', year: '2018'},
  {title: '02PY376', year: '2019'},
  {title: '06YE736', year: '2018'},
  {title: '19IT762', year: '2018'}
];
@Component({
  selector: 'app-workspace-risk-link',
  templateUrl: './workspace-risk-link.component.html',
  styleUrls: ['./workspace-risk-link.component.scss']
})
export class WorkspaceRiskLinkComponent implements OnInit {
  leftNavbarIsCollapsed: boolean = false;
  tabs:any =staticTabs;
  collapseWorkspaceDetail: boolean = true;
  componentSubscription: any = [];
  selectedPrStatus = '1';

  list1: any = ['RMS Instance', 'item 1', 'item 2'];
  list2: any = ['Financial Perspective', 'item 1', 'item 2'];
  list3: any = ['Currency', 'item 1', 'item 2'];

  list1value: string = 'RMS Instance';
  list2value: string = 'Financial Perspective';
  list3value: string = 'Currency';

  listEDM:any = [];
  listRDM:any = [];

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
    {id: 1, name: 'Brkr_PICC_CATXL_2018RNL_ToMkt_RDM', selected: false, Reference: '413'},
    {id: 2, name: 'Anxin_NMQSS_ZJ_Training_R', selected: false, Reference: '2/3'},
    {id: 3, name: 'CF1803_PORT_R', selected: false, Reference: '33/4'},
    {id: 4, name: 'Brkr_PICC_CATXL_2018RNL_ToMkt_RDM', selected: false, Reference: '425'},
    {id: 5, name: 'Anxin_NMQSS_ZJ_Training_R', selected: false, Reference: '12/1'},
    {id: 6, name: 'CF1803_PORT_R', selected: false, Reference: '4/25'},
    {id: 7, name: 'Anxin_NMQSS_ZJ_Training_R', selected: false, Reference: '4/12'},
    {id: 8, name: 'Anxin_NMQSS_ZJ_Training_R', selected: false, Reference: '7/15'}
  ];

  displaydropdownEDM: boolean = false;
  displaydropdownRDM: boolean = false;
  displaylist: boolean = false;
  displaylistRDM: boolean = false;
  displaylistEDM: boolean = false;

  constructor(private _helper: HelperService, private route: ActivatedRoute) {

  }

  ngOnInit() {
    this._helper.collapseLeftMenu$.subscribe((e) => {
      this.leftNavbarIsCollapsed = !this.leftNavbarIsCollapsed;
    });
    this._helper.openWorkspaces.subscribe(workspaces => this.tabs = workspaces);
    const pathName: any = window.location.pathname || '';
    if (pathName.includes('workspace')) {
      const workspaceId: any = pathName[pathName.length - 1];
      if(workspaceId !=null ) this.tabs = [staticTabs[0]];
    }

  }
  close(item){
    this.tabs = _.filter(this.tabs,(i)=> i != item)
  }
  ngOnDestroy(): void {
    _.forEach(this.componentSubscription, (e) => _.invoke(e, 'unsubscribe'));
  }

  addWs(title,year){
    this.tabs = [...this.tabs,{title,year}]
  }

  generateYears(year){
    return _.range(year-1,2013)
  }

  toggleitemsRDM(RDM) {
    RDM.selected = !RDM.selected;
  }

  toggleitemsEDM(EDM) {
    EDM.selected = !EDM.selected;
  }

  selectAll(value) {
    if (value === 1) {
      this.EDM.forEach((e) => {
        e.selected = true;
      })
    } else {
      this.RDM.forEach((e) => {
        e.selected = true;
      })
    }
  }
  unselectAll(value) {
    if (value === 1) {
      this.EDM.forEach((e) => {
        e.selected = false;
      })
    } else {
      this.RDM.forEach((e) => {
        e.selected = false;
      })
    }
  }

  openclosedropdown(value) {
    if (value === 1) {
      this.displaydropdownEDM = !this.displaydropdownEDM;
    } else {
      this.displaydropdownRDM = !this.displaydropdownRDM;
    }
  }

  selecteditem(value){
    let listitems = []
    if (value === 1) {
      this.EDM.forEach((e) => {
        if (e.selected === true) {
          listitems = [...listitems , e]
        }
      });
      listitems.forEach((e) => {
        e.selected = false;
      });
      this.listEDM = listitems;
      this.displaydropdownEDM = false;
      this.listEDM.length === 0 ? this.displaylistEDM = false : this.displaylistEDM = true;
    } else {
      this.RDM.forEach((e) => {
        if (e.selected === true) {
          listitems = [...listitems , e]
        }
      });
      listitems.forEach((e) => {
        e.selected = false;
      });
      this.listRDM = listitems;
      this.displaydropdownRDM = false;
      this.listRDM.length === 0 ? this.displaylistRDM = false : this.displaylistRDM = true;
    }
  }
}
