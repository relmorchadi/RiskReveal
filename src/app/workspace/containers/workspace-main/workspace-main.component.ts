import {Component, OnDestroy, OnInit} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute} from '@angular/router';
let staticTabs:any = [{title:'02PY376',year:'2019'},
  {title:'02PY376',year:'2018'},
  {title:'02PY376',year:'2019'},
  {title:'06YE736',year:'2018'},
  {title:'19IT762',year:'2018'}
];
@Component({
  selector: 'app-workspace-main',
  templateUrl: './workspace-main.component.html',
  styleUrls: ['./workspace-main.component.scss']
})
export class WorkspaceMainComponent implements OnInit, OnDestroy {
  leftNavbarIsCollapsed: boolean = false;
  tabs:any =staticTabs;
  collapseWorkspaceDetail: boolean = true;
  componentSubscription: any = [];
  selectedPrStatus = '1'

  constructor(private _helper: HelperService, private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      if(params.id){
        this.tabs = [staticTabs[0]];
      }else {
        this.tabs= staticTabs;
      }
    })
    this._helper.openWorkspaces.subscribe(workspaces => this.tabs = workspaces);
  }

  ngOnInit() {
    this._helper.collapseLeftMenu$.subscribe((e) => {
      this.leftNavbarIsCollapsed = !this.leftNavbarIsCollapsed;
    });
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


}
