import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Select, Store} from '@ngxs/store';
import {Observable} from 'rxjs';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import * as _ from 'lodash';
import {WorkspaceMainState} from "../../../core/store/states/workspace-main.state";
import {SelectWorkspaceAction} from "../../../core/store/actions/workspace-main.action";


@Component({
  selector: 'app-left-menu',
  templateUrl: './left-menu.component.html',
  styleUrls: ['./left-menu.component.scss']
})
export class LeftMenuComponent implements OnInit {
  @Input('isCollapsed') isCollapsed  = false;
  @Select(WorkspaceMainState)
  state$: Observable<WorkspaceMain>;
  state: WorkspaceMain = null;
  constructor(private _router: Router, private store: Store) { }
  ngOnInit() {
    this.state$.subscribe(value => this.state = _.merge({}, value));
  }

  collapse($event){
    $event.stopPropagation();
    $event.preventDefault();
    this.isCollapsed = !this.isCollapsed;
  }

  routerNavigate(routerLink) {
    if (routerLink ) {
      this._router.navigate([`workspace/${this.state.openedWs.workSpaceId}/${this.state.openedWs.uwYear}/${routerLink}`]);
      const patchRouting = _.merge({}, this.state.openedWs, {routing: routerLink});
      this.store.dispatch(new SelectWorkspaceAction(patchRouting));
    } else {
      this._router.navigate([`workspace/${this.state.openedWs.workSpaceId}/${this.state.openedWs.uwYear}`]);
      const patchRouting = _.merge({}, this.state.openedWs, {routing: ''});
      this.store.dispatch(new SelectWorkspaceAction(patchRouting));
    }
  }

}
