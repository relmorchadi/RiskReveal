import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';
@Pipe({
  name: 'pickKeys'
})
export class PickKeysPipe implements PipeTransform {

  transform(value: any, keys?: any): any {
    return _.pick(value, keys);
  }

}
