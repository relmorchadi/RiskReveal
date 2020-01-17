import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Message} from "../../../../shared/message";
import * as _ from 'lodash';
@Component({
  selector: 'app-return-period-pop-up',
  templateUrl: './return-period-pop-up.component.html',
  styleUrls: ['./return-period-pop-up.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ReturnPeriodPopUpComponent implements OnInit {

  @Output() actionDispatcher: EventEmitter<Message> = new EventEmitter<Message>();

  @Input() returnPeriodConfig: {
    currentRPs: number[],
    newlyAdded: number[],
    deletedRPs: number[],
    showSuggestion: boolean,
    returnPeriodInput: number,
    message: string
  };

  @Input() rpValidation: {
    isValid: boolean,
    upperBound: number,
    lowerBound: number
  };

  constructor() { }

  ngOnInit() {

  }

  inputChange = _.debounce((newValue) => {
    this.actionDispatcher.emit({
      type: "Return period popup input change",
      payload: newValue
    })
  }, 150);

  addToReturnPeriod() {
    this.actionDispatcher.emit({
      type: "ADD Return period",
      payload: this.returnPeriodConfig.returnPeriodInput
    })
  }

  save() {
    this.actionDispatcher.emit({
      type: "Save return periods"
    })
  }

  cancel() {
    this.actionDispatcher.emit({
      type: "Cancel Changes"
    })
  }

  removeReturnPeriod(rp) {
    this.actionDispatcher.emit({
      type: "Delete RP",
      payload: rp
    })
  }
}
