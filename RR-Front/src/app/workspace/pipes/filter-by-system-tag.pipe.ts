import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterBySystemTag'
})
export class FilterBySystemTagPipe implements PipeTransform {

  transform(array:Array<any>, badge): any {

    return badge ?  array.filter(plt => plt.systemTags.find(item => badge.tagId==item.tagId)) : array;
  }

}
