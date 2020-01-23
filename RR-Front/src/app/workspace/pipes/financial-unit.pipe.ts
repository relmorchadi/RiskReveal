import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'financialUnit'
})
export class FinancialUnitPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    let financialUnit = {
      "Unit": Math.pow(10,0),
      "Thousands": Math.pow(10,3),
      "Million": Math.pow(10,6),
      "Billion": Math.pow(10,9),
    };
    return value / financialUnit[args];
  }

}
