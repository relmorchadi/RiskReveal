import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';

@Pipe({
  name: 'getIdentifiers'
})
export class GetIdentifiersPipe implements PipeTransform {

  transform(data: any, tabs: any): any {
    return data ? _.map(tabs,tab => tab.workspaceContextCode + '-' + tab.workspaceUwYear) : null;
  }

}
