import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'boldKeyword'
})
export class BoldKeywordPipe implements PipeTransform {

  transform(value: any, keyword): any {
    if(value)
      return new String(value).replace(keyword, `<span class="search-highlighted-kw">${keyword}</span>`);
    return value;
  }

}
