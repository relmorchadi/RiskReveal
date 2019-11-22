import {Pipe, PipeTransform} from '@angular/core';
import * as _ from 'lodash';

@Pipe({
  name: 'textLength'
})
export class TextLengthPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return value ? _.trimStart(_.toString(value).split('-')[1], '0') : value;
  }

}
