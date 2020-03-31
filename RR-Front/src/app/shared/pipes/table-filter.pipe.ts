import { Pipe, PipeTransform } from '@angular/core';
import * as _ from "lodash";

@Pipe({
  name: 'tableFilter'
})
export class TableFilterPipe implements PipeTransform {

  readonly fieldMapper= {
    'financialPerspective': 'financialPerspectives'
  };

  transform(data: any, filterData?: any): any {
    const filterDataKeys = _.keys(filterData);
    if(filterDataKeys.length > 0 ) {
      return _.filter(data, row =>
          _.every(
              filterDataKeys,
              filteredCol => _.some(
                  [filterData[filteredCol]], strs => {
                    if(_.startsWith(strs,"\"") && _.endsWith(strs,"\""))
                      return _.toLower(_.toString(row[filteredCol])) == _.toLower(_.toString(_.trim(strs, `\"`)));
                    const toFilter = row[ this.fieldMapper[filteredCol] || filteredCol ];
                    return _.includes(_.toLower(_.toString(JSON.stringify(toFilter))), _.toLower(_.toString(strs)));
                  }
              )
          )
      )
    } else {
      return data;
    }
  }

}
