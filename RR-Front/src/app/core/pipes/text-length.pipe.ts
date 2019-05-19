import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash'
@Pipe({
  name: 'textLength'
})
export class TextLengthPipe implements PipeTransform {

  transform(value: any, args?: any): any {

    const length = args[0];

    return _.truncate(value, {
      length
    });
  }

}
