import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash'
@Pipe({
  name: 'tableSortAndFilter'
})
export class TableSortAndFilterPipe implements PipeTransform {

  transform(data: any, args?: any): any {
    const sortData= args[0];
    const sortDataKeys =_.keys(sortData);
    const filterData= args[1];
    const filterDataKeys = _.keys(filterData)
    let res=data;

    if(filterDataKeys.length > 0 ){
      _.forEach(filterDataKeys, (key) => {
        res = _.filter(data, (el) => _.includes(_.toLower(_.toString(el[key])), _.toLower(_.toString(filterData[key]))))
      })
    }

    if(filterDataKeys.length > 0 ) {
      res = _.filter(data, el => _.every(filterDataKeys, key => _.includes(_.toLower(_.toString(el[key])), _.toLower(_.toString(filterData[key])))))
    }

    if(sortDataKeys.length > 0){
        res = _.orderBy(res, [...sortDataKeys], [..._.values(sortData)])
    }

    return res;
  }

}
