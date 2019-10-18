import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash'
@Pipe({
  name: 'inputSearch'
})
export class InputSearchPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return  args[0] != '' && args[0] ? _.filter(value, el => _.some(_.keys(el), k => _.includes(el[k],args[0]))) : value;
  }

}
