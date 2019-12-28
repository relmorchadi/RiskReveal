import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'getFactor'
})
export class GetFactorPipe implements PipeTransform {

  transform(factors: any, adjustmentType: any): any {
    return (factors && factors.length &&  factors[0].adjustmentFactor) || '';
  }

}
