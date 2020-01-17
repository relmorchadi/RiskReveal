import {Pipe, PipeTransform} from '@angular/core';
import * as _ from 'lodash'
import {every} from "rxjs/operators";

@Pipe({
    name: 'calibrationSortAndFilter'
})
export class CalibrationSortAndFilterPipe implements PipeTransform {

    transform(data: any, args?: any): any {
     /*   console.log(data, args)
        let res = data;
        let filterData = args[0];
        let filterDataKeys = _.keys(filterData);
        res = _.forEach(data, (pure) => {
          _.filter(pure.threads, row => {
            _.every(filterDataKeys, filteredCol => {
              _.some(
                  _.split(filterData[filteredCol],/[,;]/g), strs =>
                      _.includes(_.toLower(_.toString(row[filteredCol])), _.toLower(_.toString(strs)))
              )
            })
          })
        })*/
        return data
    }

}
