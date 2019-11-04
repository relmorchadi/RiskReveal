import {Pipe, PipeTransform, Sanitizer, SecurityContext} from '@angular/core';
import * as _ from 'lodash'

@Pipe({
  name: 'boldSpan'
})
export class BoldSpanPipe implements PipeTransform {

  constructor(
    private sanitizer: Sanitizer
  ) {}

  transform(value: string, regex): any {
    return this.sanitize(this.replace(value, regex));
  }

  replace(str, regex) {
    return str.replace(new RegExp(`(${this.escapeAsterisk(regex)})`, 'gi'), '<b>$1</b>');
  }

  sanitize(str) {
    return this.sanitizer.sanitize(SecurityContext.HTML, str);
  }

  escapeAsterisk(s) {
    return _.replace(s, "*", '')
  }
}
