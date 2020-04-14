import { Pipe, PipeTransform } from '@angular/core';
import {Store} from "@ngxs/store";
import {FormatNumbersService} from "../services/format-numbers.service";
import * as _ from 'lodash';

@Pipe({
  name: 'rrNumber',
  pure: true
})
export class RrNumberPipe implements PipeTransform {

  constructor(private store: Store, private formatNumber: FormatNumbersService) {}

  transform(number: any, config: any = { decimalThousandSeparator: ',', decimalSeparator: '.', numberOfDecimals: 2, negativeFormat: 'simple'}): any {
    return _.get(this.formatNumber.getNumberConfig(number, config.decimalThousandSeparator, config.decimalSeparator, config.numberOfDecimals, config.negativeFormat), 'newNumber', 0);
  }

}
