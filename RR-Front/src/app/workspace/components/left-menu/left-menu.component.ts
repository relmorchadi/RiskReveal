import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Select, Store} from '@ngxs/store';
import {Observable} from 'rxjs';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import * as _ from 'lodash';
import {WorkspaceMainState} from "../../../core/store/states/workspace-main.state";
import { SelectWorkspaceAction, PatchWorkspaceMainStateAction } from '../../../core/store/actions/workspace-main.action';
import {SearchService} from "../../../core/service/search.service";
import {HelperService} from "../../../shared/helper.service";


@Component({
  selector: 'app-left-menu',
  templateUrl: './left-menu.component.html',
  styleUrls: ['./left-menu.component.scss']
})
export class LeftMenuComponent implements OnInit {

  @Select(WorkspaceMainState)
  state$: Observable<WorkspaceMain>;
  state: WorkspaceMain = null;
  constructor(private _router: Router, private _helper: HelperService, private store: Store) { }
  ngOnInit() {
    this.state$.subscribe(value => this.state = _.merge({}, value));
  }

  collapse($event){
    $event.stopPropagation();
    $event.preventDefault();
    this.store.dispatch(new PatchWorkspaceMainStateAction({key: 'leftNavbarIsCollapsed', value: !this.state.leftNavbarIsCollapsed}));
  }

  routerNavigate(routerLink) {
    let patchRouting;
    if (routerLink ) {
      this._router.navigate([`workspace/${this.state.openedWs.workSpaceId}/${this.state.openedWs.uwYear}/${routerLink}`]);
      patchRouting = _.merge({}, this.state.openedWs, {routing: routerLink});
    } else {
      this._router.navigate([`workspace/${this.state.openedWs.workSpaceId}/${this.state.openedWs.uwYear}`]);
      patchRouting = _.merge({}, this.state.openedWs, {routing: ''});
    }
    this.store.dispatch(new SelectWorkspaceAction(patchRouting));
    this._helper.updateWorkspaceItems();
  }

}
