import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-return-period-pop-up',
  templateUrl: './return-period-pop-up.component.html',
  styleUrls: ['./return-period-pop-up.component.scss']
})
export class ReturnPeriodPopUpComponent implements OnInit {

  @Input() currentRPs: any;
  returnPeriodInput: any;

  constructor() { }

  ngOnInit() {

  }

  hide() {

  }

  removeReturnPeriod(rowData) {

  }

  addToReturnPeriods() {

  }
}
