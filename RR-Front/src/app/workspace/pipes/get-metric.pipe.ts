import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash'

@Pipe({
  name: 'getMetric'
})
export class GetMetricPipe implements PipeTransform {

  transform(metric: any, curveType: string, pltId: number, rp: number): any {
    return _.get(metric, `${curveType}.${pltId}.${rp}`, 0);
  }

}
