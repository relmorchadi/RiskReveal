import {Injectable} from "@angular/core";
import * as _ from "lodash";
import {DecimalPipe} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class FormatNumbersService {

  constructor(private numberPipe: DecimalPipe) {}

  public getNumberConfig(originalNumber,  decimalThousandSeparator, decimalSeparator, numberOfDecimals, negativeFormat) {
    switch (negativeFormat) {
      case 'simple':
        return this.simple(originalNumber,decimalThousandSeparator, decimalSeparator, numberOfDecimals);
      case 'redHue':
        return this.redHue(originalNumber, decimalThousandSeparator, decimalSeparator, numberOfDecimals);
      case 'simpleRHue':
        return this.simpleRHue(originalNumber, decimalThousandSeparator, decimalSeparator, numberOfDecimals);
      case 'roundBrackets':
        return this.roundBrackets(originalNumber,decimalThousandSeparator, decimalSeparator, numberOfDecimals);
      default:
        return {
          color: 'black',
          newNumber: ''
        }
    }
  }

  simple(originalNumber, decimalThousandSeparator, decimalSeparator, numberOfDecimals) {
    let transformedNumber = this.numberPipe.transform(originalNumber, `1.0-${numberOfDecimals || 0}`);
    const decimalParts = _.split(transformedNumber, '.');
    const decimal = decimalParts[0];
    const decimalFraction = decimalParts[1];
    return ({ color: 'black' , newNumber: _.replace(decimal,/(,)/g, decimalThousandSeparator ) + (decimalFraction ? (decimalSeparator + decimalFraction) : '') })
  }

  redHue(originalNumber, decimalThousandSeparator, decimalSeparator, numberOfDecimals) {
    let transformedNumber = this.numberPipe.transform(Math.abs(originalNumber), `1.0-${numberOfDecimals|| 0}`);
    const decimalParts = _.split(transformedNumber, '.');
    const decimal = decimalParts[0];
    const decimalFraction = decimalParts[1];
    return ({ color: originalNumber > 0 ? 'black' : 'red', newNumber: _.replace(decimal,/(,)/g, decimalThousandSeparator ) + (decimalFraction ? (decimalSeparator + decimalFraction) : '') })
  }

  simpleRHue(originalNumber, decimalThousandSeparator, decimalSeparator, numberOfDecimals) {
    let transformedNumber = this.numberPipe.transform(originalNumber, `1.0-${numberOfDecimals || 0}`);
    const decimalParts = _.split(transformedNumber, '.');
    const decimal = decimalParts[0];
    const decimalFraction = decimalParts[1];
    return ({ color: originalNumber > 0 ? 'black' : 'red', newNumber: _.replace(decimal,/(,)/g, decimalThousandSeparator ) + (decimalFraction ? (decimalSeparator + decimalFraction) : '') })
  }

  roundBrackets(originalNumber, decimalThousandSeparator, decimalSeparator, numberOfDecimals) {
    let transformedNumber = this.numberPipe.transform(Math.abs(originalNumber), `1.0-${numberOfDecimals || 0}`);
    const decimalParts = _.split(transformedNumber, '.');
    const decimal = decimalParts[0];
    const decimalFraction = decimalParts[1];
    return ({ color: 'black' , newNumber: '(' + _.replace(decimal,/(,)/g, decimalThousandSeparator ) + (decimalFraction ? (decimalSeparator + decimalFraction) : '') + ')' });
  }
}