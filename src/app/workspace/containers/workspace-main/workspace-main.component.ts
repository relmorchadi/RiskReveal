import {Component, OnDestroy, OnInit} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-workspace-main',
  templateUrl: './workspace-main.component.html',
  styleUrls: ['./workspace-main.component.scss']
})
export class WorkspaceMainComponent implements OnInit, OnDestroy {
  leftNavbarIsCollapsed: boolean = false;
  tabs = [1, 2, 3];
  collapseWorkspaceDetail: boolean = true;
  componentSubscription: any = [];

  constructor(private _helper: HelperService, private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      if(params.id){
        this.tabs = [1];
      }else {
        this.tabs= [1, 2, 3];
      }
    })
    this._helper.openWorkspaces.subscribe(workspaces => this.tabs = workspaces);
  }

  ngOnInit() {
    this._helper.collapseLeftMenu$.subscribe((e) => {
      console.log('heyefkep');
      this.leftNavbarIsCollapsed = !this.leftNavbarIsCollapsed;
    });
  }
  close(item){
    this.tabs = _.filter(this.tabs,(i)=> i != item)
  }
  ngOnDestroy(): void {
    _.forEach(this.componentSubscription, (e) => _.invoke(e, 'unsubscribe'));
  }


}
