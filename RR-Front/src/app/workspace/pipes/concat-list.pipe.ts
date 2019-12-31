import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';
@Pipe({
  name: 'concatList'
})
export class ConcatListPipe implements PipeTransform {

  transform(arr: any, anotherArr): any {
    return _.concat(arr, anotherArr);
  }

}
