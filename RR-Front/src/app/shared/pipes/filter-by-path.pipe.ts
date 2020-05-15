import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'filterByPath'
})
export class FilterByPathPipe implements PipeTransform {

  transform(array: Array<any>, pathId): any {
    console.log('array', array.map(a => a.projectId));
    console.log('pathId', pathId);
    console.log('result', pathId ? array.filter(plt => plt.pathId === pathId ) : array);
    return pathId ? array.filter(plt => plt.projectId === pathId ) : array;
  }

}
