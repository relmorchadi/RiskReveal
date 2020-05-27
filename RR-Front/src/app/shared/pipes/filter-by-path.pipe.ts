import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'filterByPath'
})
export class FilterByPathPipe implements PipeTransform {

  transform(array: Array<any>, pathId): any {
    if(pathId == 0)
      return array;
    return pathId ? array.filter(plt => plt.projectId === pathId ) : array;
  }

}
