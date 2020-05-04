import {Directive, ElementRef, Input, OnChanges, Renderer2, SimpleChanges} from '@angular/core';
import {DecimalPipe} from "@angular/common";
import * as _ from 'lodash';
import {FormatNumbersService} from "../services/format-numbers.service";

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

  constructor(private el: ElementRef, private renderer: Renderer2, private formatNumber: FormatNumbersService) {}

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
    } = this.formatNumber.getNumberConfig(this.number, this.config.decimalThousandSeparator, this.config.decimalSeparator,this.config.numberOfDecimals, this.config.negativeFormat);

    return `<span class="text-ellipsis" style="color:${color}">${newNumber}</span>`
  }

}
