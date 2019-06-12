import {Injectable} from '@angular/core';
import * as _ from "lodash";

@Injectable({
  providedIn: 'root'
})
export class BadgesService {

  readonly regularExpession = /(\w*:){1}(((\w|\")*\s)*)/g;

  readonly shortcuts = {
    c: 'Cedant Name',
    cid: 'Cedant Code',
    uwy: 'Year',
    wn: 'Workspace Name',
    wid: 'Workspace Id',
    ctr: 'Country Name'
  };

  constructor() {
  }

  public generateBadges(expression, shortcuts = this.shortcuts): string | Array<{ key, value, operator }> {
    if (!`${expression} `.match(this.regularExpession))
      return expression;
    let badges = [];
    `${expression} `.replace(this.regularExpession, (match, shortcut, keyword) => {
      let kw: string = _.first(expression.trim().split(" "));
      if (kw.indexOf(':') > -1) kw = null;
      let key = shortcuts[_.trim(shortcut, ':')];
      let badge = {
        key,
        value: _.trim(_.trim(_.trim(keyword), '"')),
        operator: this.getOperator(_.trim(keyword), key)
      };
      badges.push(badge);
      return '';
    });
    return badges;
  }

  private getOperator(str: string, field: string) {
    if (str.endsWith('\"') && str.indexOf('\"') === 0) {
      return 'EQUAL';
    } else {
      return 'LIKE';
    }
  }


}
