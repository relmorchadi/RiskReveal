import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Store} from '@ngxs/store';

@Component({
  selector: 'app-pin',
  templateUrl: './pin.component.html',
  styleUrls: ['./pin.component.scss']
})
export class PinComponent implements OnInit {

  @Output('onPin') onPin: any = new EventEmitter<any>();

  @Input('pinned') pinned;
  @Input('asIcon') asIcon;

  constructor(private store: Store) {
  }

  ngOnInit() {
  }

  pin() {
    this.onPin.emit();
  }

}
