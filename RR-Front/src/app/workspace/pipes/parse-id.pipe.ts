import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';

@Pipe({
  name: 'parseId'
})
export class ParseIdPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    if (value === undefined || value === null) {
      return null;
    } else {
      return  _.trimStart(value.split('-')[1], '0');
    }
  }

}
