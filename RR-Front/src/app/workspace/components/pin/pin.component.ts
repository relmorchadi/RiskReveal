import {Component, Input, OnInit} from '@angular/core';
import {PatchWorkspace} from '../../../core/store/actions';
import * as _ from 'lodash';
import * as moment from 'moment';
import {Store} from '@ngxs/store';

@Component({
  selector: 'app-pin',
  templateUrl: './pin.component.html',
  styleUrls: ['./pin.component.scss']
})
export class PinComponent implements OnInit {

  @Input('workspace') workspace;
  @Input('index') index;
  @Input('active') active;

  constructor(private store: Store) { }

  ngOnInit() {
  }

  pinWorkspace() {
    this.store.dispatch(new PatchWorkspace({
      key: ['pinged', 'lastPModified'],
      value: [!this.workspace.pinged, moment().format('x')],
      ws: this.workspace,
      k: this.index
    }));

    let workspaceMenuItem = JSON.parse(localStorage.getItem('workSpaceMenuItem')) || {};

    if (this.workspace.pinged) {
      workspaceMenuItem[this.workspace.workSpaceId + '-' + this.workspace.uwYear] = {
        ...this.workspace,
        pinged: true,
        lastPModified: moment().format('x')
      };
    } else {
      workspaceMenuItem = {
        ...workspaceMenuItem,
        [this.workspace.workSpaceId + '-' + this.workspace.uwYear]: _.omit(workspaceMenuItem[this.workspace.workSpaceId + '-' + this.workspace.uwYear], ['pinged', 'lastPModified'])
      };
    }
    localStorage.setItem('workSpaceMenuItem', JSON.stringify(workspaceMenuItem));
  }

}
