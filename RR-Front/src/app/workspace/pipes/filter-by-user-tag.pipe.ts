import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterByUserTag'
})
export class FilterByUserTagPipe implements PipeTransform {

  transform(array:Array<any>, badge): any {
    return array
  }

}
