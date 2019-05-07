import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'filterByPath'
})
export class FilterByPathPipe implements PipeTransform {

  transform(array: Array<any>, pathId): any {

    console.log(array);
    console.log(pathId);
    return pathId ? array.filter(plt => plt.pathId == pathId ): array;
  }

}
