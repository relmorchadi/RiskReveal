import {Component, OnDestroy, OnInit} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from "lodash"

@Component({
  selector: 'app-workspace-main',
  templateUrl: './workspace-main.component.html',
  styleUrls: ['./workspace-main.component.scss']
})
export class WorkspaceMainComponent implements OnInit,OnDestroy {
  leftNavbarIsCollapsed:boolean = false;
  tabs = [ 1, 2, 3 ];
  collapseWorkspaceDetail:boolean = true;
  componentSubscription:any = []
  constructor(private _helper:HelperService) { }

  ngOnInit() {

      this._helper.collapseLeftMenu$.subscribe((e)=>{
        console.log("heyefkep")
        this.leftNavbarIsCollapsed = !this.leftNavbarIsCollapsed
      })

  }

  ngOnDestroy(): void {
    _.forEach(this.componentSubscription,(e)=>_.invoke(e,'unsubscribe'));
  }



}
