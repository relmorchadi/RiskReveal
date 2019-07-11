import {Pipe, PipeTransform} from "@angular/core";
import * as _ from "lodash";

@Pipe({
  name: 'calibrate'
})
export class CalibratePipe implements PipeTransform {

  transform(data: any, args?: any): any {
    let res = {};
    _.forEach(data, (value, key) => {
      if (value.calibrate) {
        res[key] = value;
      }
    })
    return res;
  }

}
