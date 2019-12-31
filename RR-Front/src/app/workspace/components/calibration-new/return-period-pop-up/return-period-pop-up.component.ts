import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-return-period-pop-up',
  templateUrl: './return-period-pop-up.component.html',
  styleUrls: ['./return-period-pop-up.component.scss']
})
export class ReturnPeriodPopUpComponent implements OnInit {

  @Input() manageReturnPeriods: any;
  returnPeriods: any = [10000, 5000, 1000, 500, 100, 50, 25, 10, 5, 2];
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
