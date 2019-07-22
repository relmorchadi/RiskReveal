import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
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

  constructor(private store: Store) {
  }

  ngOnInit() {
  }

  pinWorkspace() {
    if (!this.active) {
      this.unpinWorkspaceAction.emit();
    } else {
      this.pinWorkspaceAction.emit();
    }
  }

}
