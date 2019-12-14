import { Pipe, PipeTransform } from '@angular/core';

import * as _ from 'lodash';

@Pipe({
  name: 'rmsDatasource'
})
export class RmsDatasourceTypePipe implements PipeTransform {

  transform(datasources: Object, type?: string): any {
    let array:any[]= _.toArray(datasources);
    if(type)
      return array.filter(ds => ds.type == type);
    else
      return array;
  }

}
