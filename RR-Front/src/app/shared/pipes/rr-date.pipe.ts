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

    const dateConfig = this.userPref.getDateConfig();
    this.shortDate = dateConfig.shortDate;
    this.longDate = dateConfig.longDate;
    this.shortTime = dateConfig.shortTime;
    this.longTime = dateConfig.longTime;
  }

  transform(value: any, dateFormat: any, timeOrDate): any {
    if (dateFormat === 'shortFormat') {
      return moment(value, 'DD/MM/YYYY').format(timeOrDate === 'time' ? this.shortTime : this.shortDate);
    } else {
      return moment(value, 'DD/MM/YYYY').format(timeOrDate === 'time' ? this.longTime : this.longDate);
    }
  }

}
