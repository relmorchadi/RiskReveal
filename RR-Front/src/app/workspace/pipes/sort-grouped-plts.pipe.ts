import { Pipe, PipeTransform } from '@angular/core';
import * as _ from "lodash";

@Pipe({
  name: 'sortGroupedPlts'
})
export class SortGroupedPltsPipe implements PipeTransform {

  transform(pures: any, sorts: any): any {
    const sortDataKeys =_.keys(sorts);

    if(sortDataKeys.length > 0){

      return _.map(_.orderBy(pures, [...sortDataKeys], [..._.values(sorts)]), (pure: any) => ({
          ...pure,
        threads: _.orderBy(pure.threads, [...sortDataKeys], [..._.values(sorts)])
      }));

    }

    return pures;
  }

}
