import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash'

@Pipe({
  name: 'frozenColsLength',
  pure: true
})
export class FrozenColsLengthPipe implements PipeTransform {

  transform(cols: any, args?: any): any {
    return  _.filter(cols, item => item.isFrozen).length > 0;
  }

}
