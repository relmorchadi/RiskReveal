import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'getEventAdjustmentParams'
})
export class GetEventAdjustmentParamsPipe implements PipeTransform {

  transform(factors: any, args?: any): any {
    return factors && factors.length && factors[0];
  }

}
