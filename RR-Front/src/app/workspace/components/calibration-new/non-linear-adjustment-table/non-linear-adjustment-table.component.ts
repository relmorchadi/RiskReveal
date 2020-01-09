import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Message} from "../../../../shared/message";

@Component({
  selector: 'app-non-linear-adjustment-table',
  templateUrl: './non-linear-adjustment-table.component.html',
  styleUrls: ['./non-linear-adjustment-table.component.scss']
})
export class NonLinearAdjustmentTableComponent implements OnInit {

  @Output() actionDispatcher: EventEmitter<Message> = new EventEmitter<Message>();

  @Input() factors;

  constructor() { }

  ngOnInit() {
  }

}
