import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterByUserTag'
})
export class FilterByUserTagPipe implements PipeTransform {

  transform(array:Array<any>, badge): any {
    return badge ?  array.filter(plt => plt.userTags.find(item => badge.tagId==item.tagId)) : array;
  }

}
