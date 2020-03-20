import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'frozenColumnsFilter'
})
export class FrozenColumnsFilterPipe implements PipeTransform {

  transform(cols: Array<any>): Array<any> {
    if (cols == null) return null;
    const statusCols = cols.filter(c => c.field == 'status' );
    return cols.slice(0, cols.indexOf(statusCols[0]) + 1);
  }

}
