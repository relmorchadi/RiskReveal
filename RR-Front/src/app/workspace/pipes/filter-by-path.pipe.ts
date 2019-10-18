import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'filterByPath'
})
export class FilterByPathPipe implements PipeTransform {

  transform(array: Array<any>, pathId): any {
    return pathId ? array.filter(plt => plt.pathId == pathId ): array;
  }

}
