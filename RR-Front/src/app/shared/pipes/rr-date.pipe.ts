import { Pipe, PipeTransform } from '@angular/core';
import {UserPreferenceService} from "../services/user-preference.service";
import * as moment from 'moment';

@Pipe({
  name: 'rRDate'
})
export class RRDatePipe implements PipeTransform {
  shortDate: any;
  longDate: any;
  shortTime: any;
  longTime: any;

  constructor(private userPref: UserPreferenceService) {
    this.shortDate = this.userPref.dateConfig.shortDate;
    this.longDate = this.userPref.dateConfig.longDate;
    this.shortTime = this.userPref.dateConfig.shortTime;
    this.longTime = this.userPref.dateConfig.longTime;
  }

  transform(value: any, dateFormat: any, timeOrDate): any {
    if (dateFormat === 'shortFormat') {
      return moment(value, timeOrDate === 'time' ? this.shortTime : this.shortDate);
    } else {
      return moment(value, timeOrDate === 'time' ? this.longTime : this.longDate);
    }
  }

}
