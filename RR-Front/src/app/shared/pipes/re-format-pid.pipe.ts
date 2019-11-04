import {Pipe, PipeTransform} from '@angular/core';
import * as _ from 'lodash';

@Pipe({
  name: 'reFormatPID'
})
export class ReFormatPIDPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    const str = _.split(value, '-');
    return _.join([str[0], _.trimStart(str[1], '0')], '-');
  }

}
