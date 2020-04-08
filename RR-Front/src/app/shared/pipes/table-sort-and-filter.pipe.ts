import {Pipe, PipeTransform} from '@angular/core';
import * as _ from 'lodash'

@Pipe({
  name: 'tableSortAndFilter'
})
export class TableSortAndFilterPipe implements PipeTransform {

  transform(data: any, args?: any): any {

    const sortData = args[0];
    const sortDataKeys =_.keys(sortData);
    const filterData= args[1];
    const filterDataKeys = _.keys(filterData);
    let res=data;


    if(filterDataKeys.length > 0 ) {

      res = _.filter(data, row =>
        _.every(
          filterDataKeys,
            filteredCol => _.some(
              _.split(filterData[filteredCol],/[,;]/g), strs => {
                  if(_.startsWith(strs,"\"") && _.endsWith(strs,"\""))
                    return _.toLower(_.toString(row[filteredCol])) == _.toLower(_.toString(_.trim(strs, `\"`)));
                  return _.includes(_.toLower(_.toString(row[filteredCol])), _.toLower(_.toString(strs)));
                }
              )
            )
        )
    }
    if(sortDataKeys.length > 0){
        res = _.orderBy(res, [...sortDataKeys], [..._.values(sortData)])
    }

    return res;
  }

}
