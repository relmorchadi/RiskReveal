import {Pipe, PipeTransform, Sanitizer, SecurityContext} from '@angular/core';

@Pipe({
  name: 'boldText'
})
export class BoldPipe implements PipeTransform {

  constructor(
    private sanitizer: Sanitizer
  ) {}

  transform(value: string, regex): any {
    return '<b>' + value + '</b>';
  }

  replace(str, regex) {
    return str.replace(new RegExp(`(${regex})`, 'gi'), );
  }

  sanitize(str) {
    return this.sanitizer.sanitize(SecurityContext.HTML, str);
  }
}
