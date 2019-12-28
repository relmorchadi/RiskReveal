import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';
@Pipe({
  name: 'getDescription',
  pure: true
})
export class GetDescriptionPipe implements PipeTransform {

  transform(basisShotName: string, basis: any[]): any {
    console.log(basisShotName, basis);
    return _.find(basis, item => item.basisShotName == basisShotName).description || '';
  }

}
