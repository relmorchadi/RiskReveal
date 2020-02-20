import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';


@Pipe({
  name: 'filterGroupedPlts'
})
export class FilterGroupedPltsPipe implements PipeTransform {

  transform(pures: any, filter: any): any {
    const filterKeys =_.keys(filter);
    if(filterKeys.length) {
      let data = _.cloneDeep(pures);
      return _.filter(data, pure => {
        pure.threads = _.filter(pure.threads, thread => _.every(filterKeys, key => _.some(_.split(filter[key], ','), keyword => {
          const trimmedKeyword = _.toString(_.trim(keyword));
          return trimmedKeyword ? _.includes(_.toString(thread[key]), trimmedKeyword) : false;
        })));
        return pure.threads.length;
      })
    } else {
      return pures;
    }


  }

}
