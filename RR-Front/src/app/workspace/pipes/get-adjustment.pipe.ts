import { Pipe, PipeTransform } from '@angular/core';
import * as _ from "lodash";

@Pipe({
  name: 'getAdjustment'
})
export class GetAdjustmentPipe implements PipeTransform {

  transform(adjustments: any, pltId: number, adjustmentType: string): any {
    console.log(adjustments, pltId, adjustmentType);
    const t = _.get(adjustments, `${adjustmentType}.${pltId}`, null);
    console.log(t)
    return t;
  }

}
