import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'analysisByRdm'
})
export class AnalysisByRdmPipe implements PipeTransform {

  transform(value: any[], rdmName: string): any {
    if (value && rdmName && rdmName != 'All')
      return value.filter(item => item.rdmName == rdmName);
    return value;
  }

}
