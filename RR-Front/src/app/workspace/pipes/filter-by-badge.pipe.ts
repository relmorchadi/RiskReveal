import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterByBadge'
})
export class FilterByBadgePipe implements PipeTransform {

  transform(array:Array<any>, badge): any {
    console.log('This is array : ', array);
    console.log('This is badge : ', badge);
    return badge ?  array.filter(plt => plt.systemTags.find(item => badge.tagId==item.tagId)) : array;
  }

}
