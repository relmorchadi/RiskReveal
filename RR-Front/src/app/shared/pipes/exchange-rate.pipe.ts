import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';

@Pipe({
  name: 'exchangeRate'
})
export class ExchangeRatePipe implements PipeTransform {

  mainCurrencies = {
    'USD': {
      currencyCode: 'USD'
    },
    'EUR': {
      currencyCode: 'EUR'
    },
    'CAD': {
      currencyCode: 'CAD'
    },
    'GBP': {
      currencyCode: 'GBP'
    },
    'SGD': {
      currencyCode: 'SGD'
    },
  };

  transform(value: any, exchangeRates, source, target): any {

    if(exchangeRates[source]) {
      if (this.mainCurrencies[_.upperCase(target)]) {
        return value * exchangeRates[source][_.lowerCase(target) + '_Rate'];
      } else {

        let middleCurrency = 'EUR';

        if(exchangeRates[source] && exchangeRates[target]) {

          if(exchangeRates[source][_.lowerCase(middleCurrency) +'_Rate'] && exchangeRates[target][_.lowerCase(middleCurrency) +'_Rate']) {
            return value * exchangeRates[source][_.lowerCase(middleCurrency) +'_Rate'] / exchangeRates[target][_.lowerCase(middleCurrency) +'_Rate'];
          } else {

            const res = _.find(this.mainCurrencies, (item,curr) => exchangeRates[source][_.lowerCase(curr) +'_Rate'] && exchangeRates[target][_.lowerCase(curr) +'_Rate']);
            if(res) {
              middleCurrency = res.currencyCode;
              return value * exchangeRates[source][_.lowerCase(middleCurrency) +'_Rate'] / exchangeRates[target][_.lowerCase(middleCurrency) +'_Rate'];
            } else {
              return value;
            }

          }

        } else {
          return value;
        }

      }
    } else {
      return value;
    }
  }

}
