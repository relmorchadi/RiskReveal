import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Message} from "../../../../shared/message";

@Component({
  selector: 'app-calibration-new-table',
  templateUrl: './calibration-new-table.component.html',
  styleUrls: ['./calibration-new-table.component.scss']
})
export class CalibrationNewTableComponent implements OnInit {

  @Output() actionDispatcher: EventEmitter<Message> = new EventEmitter<Message>();

  @Input() data: any[];

  @Input() mode: 'default' | 'grouped';
  @Input() columns: any[];
  @Input() rowKeys: any;

  constructor() { }

  ngOnInit() {
  }

}
