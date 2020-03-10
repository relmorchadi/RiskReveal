import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment';

@Pipe({
  name: 'rRDate',
  pure: true
})
export class RRDatePipe implements PipeTransform {

  constructor() {}

  transform(value: any, dateFormat: any, timeOrDate, dateConfig): any {
    if(value) {
      if (dateFormat === 'shortFormat') {
        return moment(new Date(value), 'DD/MM/YYYY').format(timeOrDate === 'time' ? dateConfig.shortTime : dateConfig.shortDate);
      } else {
        return moment(new Date(value), 'DD/MM/YYYY').format(timeOrDate === 'time' ? dateConfig.longTime : dateConfig.longDate);
      }
    } else return "";
  }

}
