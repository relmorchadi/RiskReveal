import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
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
  @Output('pinWs') pinWorkspaceAction: any = new EventEmitter<any>();
  @Output('unpinWs') unpinWorkspaceAction: any = new EventEmitter<any>();
  @Input('active') active;
  @Input('workspace') workspace;
  @Input('index') index;

  constructor(private store: Store) {
  }

  ngOnInit() {
  }

  pinWorkspace() {
    if (this.active) {
      this.unpinWorkspaceAction.emit();
    } else {
      this.pinWorkspaceAction.emit();
    }
  }

}
