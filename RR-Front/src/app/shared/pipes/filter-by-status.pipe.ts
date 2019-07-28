import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';
@Pipe({
  name: 'filterByStatus'
})
export class FilterByStatusPipe implements PipeTransform {

  mappingColumns= {
    'Published': 'isScorCurrent',
    'Priced': 'isScorDefault',
    'Accumulated': 'isScorGenerated'
  }

  transform(plts: any, args?: any): any {
    const status = args[0];
    let activeStatus = {};

    _.forEach(status, (v,k) => {
      if(v.selected) {
        activeStatus[k]= v;
      }
    });

    console.log(status, activeStatus, _.keys(activeStatus).length ? _.filter(plts, plt => _.some(activeStatus, (v,statue) => plt[this.mappingColumns[statue]])) : plts)

    return _.keys(activeStatus).length ? _.filter(plts, plt => _.some(activeStatus, (v,statue) => plt[this.mappingColumns[statue]])) : plts;
  }

}
