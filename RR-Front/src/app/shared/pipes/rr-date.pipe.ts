import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment';
import * as _ from 'lodash';

@Pipe({
  name: 'rRDate',
  pure: true
})
export class RRDatePipe implements PipeTransform {

  constructor() {}

  transform(value: any, dateFormat: any, timeOrDate, dateConfig): any {
    if(value) {
      const valueFormatted = _.isString(value) ? value : new Date(value);
      if (dateFormat === 'shortFormat') {
        return moment(valueFormatted, 'DD-MM-YYYY').format(timeOrDate === 'time' ? dateConfig.shortTime : dateConfig.shortDate);
      } else {
        return moment(valueFormatted, 'DD-MM-YYYY').format(timeOrDate === 'time' ? dateConfig.longTime : dateConfig.longDate);
      }
    } else return "";
  }

}
