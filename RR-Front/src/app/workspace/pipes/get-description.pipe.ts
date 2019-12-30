import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';
@Pipe({
  name: 'getDescription',
  pure: true
})
export class GetDescriptionPipe implements PipeTransform {

  transform(basisShortName: string, basis: any[]): any {
    return _.find(basis, item => item.basisShortName == basisShortName).description || '';
  }

}
