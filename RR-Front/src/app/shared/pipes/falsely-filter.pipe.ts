import { Pipe, PipeTransform } from '@angular/core';
import * as _ from "lodash";

@Pipe({
  name: 'falselyFilter'
})
export class FalselyFilterPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    const col = args;
    let activeStatus = {};

    _.forEach(col, (v,k) => {
      if(v.selected) {
        activeStatus[k]= v;
      }
    });

    const willFilter = _.keys(activeStatus).length;

    return _.filter(value, (plt,k) => willFilter ? _.some(activeStatus, (v,statue) => plt[statue]) : true);
  }

}
