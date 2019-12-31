import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Message} from "../../../../shared/message";

@Component({
  selector: 'app-return-period-pop-up',
  templateUrl: './return-period-pop-up.component.html',
  styleUrls: ['./return-period-pop-up.component.scss']
})
export class ReturnPeriodPopUpComponent implements OnInit {

  @Output() actionDispatcher: EventEmitter<Message> = new EventEmitter<Message>();

  @Input() returnPeriodConfig: {
    currentRPs: number[],
    newlyAdded: number[],
    showSuggestion: boolean,
    message: string
  };

  @Input() rpValidation: {
    isValid: boolean,
    upperBound: number,
    lowerBound: number
  };

  returnPeriodInput: number;

  constructor() { }

  ngOnInit() {

  }

  hide() {

  }

  addToReturnPeriod() {
    this.actionDispatcher.emit({
      type: "ADD Return period",
      payload: this.returnPeriodInput
    })
  }

  save() {
    this.actionDispatcher.emit({
      type: "Save return periods"
    })
  }
}
