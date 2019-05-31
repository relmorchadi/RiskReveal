import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash'

@Pipe({
  name: 'showLast'
})
export class ShowLastPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return _.slice(value, 0, args[0]);
  }

}
