import { Pipe, PipeTransform } from '@angular/core';
import * as _ from "lodash";

@Pipe({
  name: 'tableFilter'
})
export class TableFilterPipe implements PipeTransform {

  transform(data: any, filterData?: any): any {
    const filterDataKeys = _.keys(filterData);
    console.log('Filter Data',data,filterData);
    if(filterDataKeys.length > 0 ) {
      return _.filter(data, row =>
          _.every(
              filterDataKeys,
              filteredCol => _.some(
                  _.split(filterData[filteredCol],/[,;]/g), strs => {
                    if(_.startsWith(strs,"\"") && _.endsWith(strs,"\""))
                      return _.toLower(_.toString(row[filteredCol])) == _.toLower(_.toString(_.trim(strs, `\"`)));
                    return _.includes(_.toLower(_.toString(JSON.stringify(row[filteredCol]))), _.toLower(_.toString(strs)));
                  }
              )
          )
      )
    } else {
      return data;
    }
  }

}
