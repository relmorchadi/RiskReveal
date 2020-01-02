import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'financialUnit'
})
export class FinancialUnitPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return null;
  }

}
