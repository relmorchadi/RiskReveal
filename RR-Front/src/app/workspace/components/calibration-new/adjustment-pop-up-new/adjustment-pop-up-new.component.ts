import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Message} from "../../../../shared/message";

@Component({
  selector: 'app-adjustment-pop-up-new',
  templateUrl: './adjustment-pop-up-new.component.html',
  styleUrls: ['./adjustment-pop-up-new.component.scss']
})
export class AdjustmentPopUpNewComponent implements OnInit {

  @Input() adjustment: any;
  @Input() basis: any;
  @Input() adjustmentTypes: any;

  @Output() actionDispatcher: EventEmitter<Message> = new EventEmitter<Message>();

  constructor() { }

  ngOnInit() {

  }

}
