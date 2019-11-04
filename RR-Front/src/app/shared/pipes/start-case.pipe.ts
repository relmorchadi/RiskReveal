import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';

@Pipe({
  name: 'startCase',
  pure: true
})
export class StartCasePipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return _.startCase(value);
  }

}
