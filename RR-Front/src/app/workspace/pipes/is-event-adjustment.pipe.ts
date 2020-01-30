import { Pipe, PipeTransform } from '@angular/core';
import { includes } from 'lodash'

@Pipe({
  name: 'isEventAdjustment'
})
export class IsEventAdjustmentPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return includes(value, 'Event');
  }

}
