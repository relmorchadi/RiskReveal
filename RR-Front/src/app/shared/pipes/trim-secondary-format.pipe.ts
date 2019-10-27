import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';

@Pipe({
  name: 'trimSecondaryFormat'
})
export class TrimSecondaryFormatPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return value.split('-')[0] + _.trimStart(value.split('-')[1], '0');
  }

}
