import { Pipe, PipeTransform } from '@angular/core';
import {Store} from "@ngxs/store";
import {GeneralConfigState} from "../../core/store/states";
import {DecimalPipe} from "@angular/common";
import {map} from "rxjs/operators";

@Pipe({
  name: 'rrNumber',
  pure: true
})
export class RrNumberPipe implements PipeTransform {

  constructor(private store: Store, private numberPipe: DecimalPipe) {}

  transform(number: any, isShort: any): any {
    return this.store.select(GeneralConfigState.getNumberFormatConfig)
        .pipe(
            map(({ numberOfDecimals, decimalSeparator, decimalThousandSeparator, negativeFormat, numberHistory }) => {
              return this.numberPipe.transform(number, `1.0-${numberOfDecimals}`);
            })
        );
  }

}
