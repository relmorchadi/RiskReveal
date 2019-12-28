import { Pipe, PipeTransform } from '@angular/core';
import * as _ from "lodash";

@Pipe({
  name: 'getAdjustment'
})
export class GetAdjustmentPipe implements PipeTransform {

  transform(adjustments: any, pltId: number, adjustmentType: string): any {
    return _.get(adjustments, `${adjustmentType}.${pltId}`, null);
  }

}
