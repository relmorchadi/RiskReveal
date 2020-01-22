import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';

@Pipe({
  name: 'filterRP'
})
export class FilterRPPipe implements PipeTransform {

  transform(rps: any, deletedOnes: any): any {
    return _.filter(rps, rp => !_.find(deletedOnes, deletedRP => deletedRP == rp));
  }

}
