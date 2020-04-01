import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';

@Pipe({
  name: 'hasStatus'
})
export class HasStatusPipe implements PipeTransform {

  transform(value: any, status: any): any {
    return _.includes(value, status);
  }

}
