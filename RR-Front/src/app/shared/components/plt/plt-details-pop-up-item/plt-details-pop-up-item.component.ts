import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import * as pltDetailsPopUpItemStore from './store'
import {Message} from "../../../message";

@Component({
  selector: 'app-plt-details-pop-up-item',
  templateUrl: './plt-details-pop-up-item.component.html',
  styleUrls: ['./plt-details-pop-up-item.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PltDetailsPopUpItemComponent implements OnInit {

  @Input() inputs: pltDetailsPopUpItemStore.Input;
  @Input() plt: any;

  @Output() actionDispatcher: EventEmitter<Message>= new EventEmitter<Message>();

  constructor() {

  }

  ngOnInit() {
  }

  openSection(title) {
    this.actionDispatcher.emit({
      type: pltDetailsPopUpItemStore.openSection,
      payload: title
    })
  }
}
