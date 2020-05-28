import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash'
@Pipe({
  name: 'includes'
})
export class IncludesPipe implements PipeTransform {

  transform(data: any[], filter: string): any {

    if(data && data.length) {
      if(!_.isEmpty(filter)) return _.filter(data, e => _.includes(_.lowerCase(e), _.lowerCase(filter)));
      else return data;
    } else return [];

  }

}
