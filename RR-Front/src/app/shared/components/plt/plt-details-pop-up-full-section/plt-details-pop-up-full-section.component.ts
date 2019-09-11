import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Message} from "../../../message";
import * as pltDetailsPopUpItemStore from '../plt-details-pop-up-item/store'

@Component({
  selector: 'app-plt-details-pop-up-full-section',
  templateUrl: './plt-details-pop-up-full-section.component.html',
  styleUrls: ['./plt-details-pop-up-full-section.component.scss']
})
export class PltDetailsPopUpFullSectionComponent implements OnInit {

  @Input() inputs: any;
  @Input() plt: any;

  @Output() actionDispatcher: EventEmitter<Message> = new EventEmitter<Message>();

  constructor() { }

  ngOnInit() {
  }

  closeSection() {
    this.actionDispatcher.emit({
      type: pltDetailsPopUpItemStore.closeSection,
    })
  }
}
