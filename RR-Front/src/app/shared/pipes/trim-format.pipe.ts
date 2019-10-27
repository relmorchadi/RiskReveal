import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';

@Pipe({
  name: 'trimFormat'
})
export class TrimFormatPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    if (value === undefined) {
      return null;
    } else {
      return value.split('-')[0] + '-' + _.trimStart(value.split('-')[1], '0');
    };
  }

}
