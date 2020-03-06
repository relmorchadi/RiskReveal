import {Injectable} from "@angular/core";
import {Store} from "@ngxs/store";
import {GeneralConfigState} from "../../core/store/states";
import * as _ from "lodash";
import {DecimalPipe} from "@angular/common";
import {RRDatePipe} from "../pipes";

@Injectable({
  providedIn: 'root'
})
export class ColumnsFormatterService {

  numberConfig: {
    numberOfDecimals: number;
    decimalSeparator: string;
    decimalThousandSeparator: string;
    negativeFormat: string;
  };

  constructor(private store: Store, private numberPipe: DecimalPipe, private datePipe: RRDatePipe) {
    this.store.select(GeneralConfigState.getNumberFormatConfig).subscribe( ({ numberOfDecimals, decimalSeparator, decimalThousandSeparator, negativeFormat, numberHistory }) => {
      this.numberConfig = { numberOfDecimals, decimalSeparator, decimalThousandSeparator, negativeFormat };
    })
  }

  format(value, type) {
    switch (type) {
      case "number":
        return this.formatNumber(value);

      case "text" || "id":
        return this.formatText(value);

      case "date":
        return this.formatDate(value);

      case "indicator":
        return this.formatIndicator(value);

      default:
        return this.formatText(value);
    }
  }

  formatNumber(n) {
    switch (this.numberConfig.negativeFormat) {
      case 'simple':
        return this.simple(n);
      case 'redHue':
        return this.redHue(n);
      case 'simpleRHue':
        return this.simpleRHue(n);
      case 'roundBrackets':
        return this.roundBrackets(n);
      default:
        return ''
    }
  }

  formatText(t) {
    return t.toString();
  }

  formatDate(d) {
    return this.datePipe.transform(d, 'longFormat', 'date');
  }

  formatIndicator(i) {
    return i ? "True" : "False"
  }

  simple(originalNumber) {
    let transformedNumber = this.numberPipe.transform(originalNumber, `1.0-${this.numberConfig.numberOfDecimals || 0}`);
    const decimalParts = _.split(transformedNumber, '.');
    const decimal = decimalParts[0];
    const decimalFraction = decimalParts[1];
    return _.replace(decimal,/(,)/g, this.numberConfig.decimalThousandSeparator ) + (decimalFraction ? (this.numberConfig.decimalSeparator + decimalFraction) : '')
  }

  redHue(originalNumber) {
    let transformedNumber = this.numberPipe.transform(Math.abs(originalNumber), `1.0-${this.numberConfig.numberOfDecimals || 0}`);
    const decimalParts = _.split(transformedNumber, '.');
    const decimal = decimalParts[0];
    const decimalFraction = decimalParts[1];
    return _.replace(decimal,/(,)/g, this.numberConfig.decimalThousandSeparator ) + (decimalFraction ? (this.numberConfig.decimalSeparator + decimalFraction) : '')
  }

  simpleRHue(originalNumber) {
    let transformedNumber = this.numberPipe.transform(originalNumber, `1.0-${this.numberConfig.numberOfDecimals || 0}`);
    const decimalParts = _.split(transformedNumber, '.');
    const decimal = decimalParts[0];
    const decimalFraction = decimalParts[1];
    return _.replace(decimal,/(,)/g, this.numberConfig.decimalThousandSeparator ) + (decimalFraction ? (this.numberConfig.decimalSeparator + decimalFraction) : '')
  }

  roundBrackets(originalNumber) {
    let transformedNumber = this.numberPipe.transform(Math.abs(originalNumber), `1.0-${this.numberConfig.numberOfDecimals || 0}`);
    const decimalParts = _.split(transformedNumber, '.');
    const decimal = decimalParts[0];
    const decimalFraction = decimalParts[1];
    return '(' + _.replace(decimal,/(,)/g, this.numberConfig.decimalThousandSeparator ) + (decimalFraction ? (this.numberConfig.decimalSeparator + decimalFraction) : '') + ')';
  }

}