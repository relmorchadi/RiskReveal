import {Directive, ElementRef, Input, OnChanges, Renderer2, SimpleChanges} from '@angular/core';
import {DecimalPipe} from "@angular/common";
import * as _ from 'lodash';

@Directive({
  selector: '[appRrNumber]'
})
export class RrNumberDirective implements OnChanges {

  @Input() number: number;

  @Input() config: {
    numberOfDecimals: number;
    decimalSeparator: string;
    decimalThousandSeparator: string;
    negativeFormat: string;
  };

  constructor(private el: ElementRef, private renderer: Renderer2, private numberPipe: DecimalPipe) {}

  ngOnChanges(changes: SimpleChanges): void {
    this.renderer.setProperty(
        this.el.nativeElement,
        'innerHTML',
        this.getFormattedNumber()
    );
  }

  getFormattedNumber() {

    const {
      color,
      newNumber
    } = this.getNumberConfig(this.number, this.config.decimalThousandSeparator, this.config.decimalSeparator, this.config.negativeFormat);

    return `<span class="text-ellipsis" style="color:${color}">${newNumber}</span>`
  }

  getNumberConfig(originalNumber,  decimalThousandSeparator, decimalSeparator, negativeFormat) {
    switch (negativeFormat) {
      case 'simple':
        return this.simple(originalNumber,decimalThousandSeparator, decimalSeparator);
      case 'redHue':
        return this.redHue(originalNumber, decimalThousandSeparator, decimalSeparator);
      case 'simpleRHue':
        return this.simpleRHue(originalNumber, decimalThousandSeparator, decimalSeparator);
      case 'roundBrackets':
        return this.roundBrackets(originalNumber,decimalThousandSeparator, decimalSeparator);
      default:
        return {
          color: 'black',
          newNumber: ''
        }
    }
  }

  simple(originalNumber, decimalThousandSeparator, decimalSeparator) {
    let transformedNumber = this.numberPipe.transform(originalNumber, `1.0-${this.config.numberOfDecimals || 0}`);
    const decimalParts = _.split(transformedNumber, '.');
    const decimal = decimalParts[0];
    const decimalFraction = decimalParts[1];
    return ({ color: 'black' , newNumber: _.replace(decimal,/(,)/g, decimalThousandSeparator ) + (decimalFraction ? (decimalSeparator + decimalFraction) : '') })
  }

  redHue(originalNumber, decimalThousandSeparator, decimalSeparator) {
    let transformedNumber = this.numberPipe.transform(Math.abs(originalNumber), `1.0-${this.config.numberOfDecimals || 0}`);
    const decimalParts = _.split(transformedNumber, '.');
    const decimal = decimalParts[0];
    const decimalFraction = decimalParts[1];
    return ({ color: originalNumber > 0 ? 'black' : 'red', newNumber: _.replace(decimal,/(,)/g, decimalThousandSeparator ) + (decimalFraction ? (decimalSeparator + decimalFraction) : '') })
  }

  simpleRHue(originalNumber, decimalThousandSeparator, decimalSeparator) {
    let transformedNumber = this.numberPipe.transform(originalNumber, `1.0-${this.config.numberOfDecimals || 0}`);
    const decimalParts = _.split(transformedNumber, '.');
    const decimal = decimalParts[0];
    const decimalFraction = decimalParts[1];
    return ({ color: originalNumber > 0 ? 'black' : 'red', newNumber: _.replace(decimal,/(,)/g, decimalThousandSeparator ) + (decimalFraction ? (decimalSeparator + decimalFraction) : '') })
  }

  roundBrackets(originalNumber, decimalThousandSeparator, decimalSeparator) {
    let transformedNumber = this.numberPipe.transform(Math.abs(originalNumber), `1.0-${this.config.numberOfDecimals || 0}`);
    const decimalParts = _.split(transformedNumber, '.');
    const decimal = decimalParts[0];
    const decimalFraction = decimalParts[1];
    return ({ color: 'black' , newNumber: '(' + _.replace(decimal,/(,)/g, decimalThousandSeparator ) + (decimalFraction ? (decimalSeparator + decimalFraction) : '') + ')' });
  }

}
