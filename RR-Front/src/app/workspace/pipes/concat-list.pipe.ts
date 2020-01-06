import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';
@Pipe({
  name: 'concatList'
})
export class ConcatListPipe implements PipeTransform {

  transform(arr: any, anotherArr): any {
    console.log(arr, anotherArr);
    return _.sortBy(_.concat(arr, anotherArr), (a) => _.toNumber(a));
  }

}
