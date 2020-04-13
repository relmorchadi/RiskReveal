import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'cloneDataColumnManageFilter'
})
export class CloneDataColumnManageFilterPipe implements PipeTransform {

  transform(cols: Array<any>): Array<any> {
    console.log(cols);
    if (cols == null) return null;
    return cols.filter(c => c.header !== '');
  }

}
