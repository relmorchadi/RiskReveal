import {Injectable} from '@angular/core';
import * as _ from "lodash";
import {ShortCut} from "../model/shortcut.model";

@Injectable({
  providedIn: 'root'
})
export class BadgesService {

  readonly regularExpession = /(\w*:){1}(((\w|\"|\*)*\s)*)/g;

  readonly shortcuts = {
    c: 'CedantName',
    cid: 'CedantCode',
    uwy: 'UWYear',
    w: 'WorkspaceName',
    wid: 'WorkspaceId',
    ctr: 'CountryName'
  };

  constructor() {

  }

  public generateBadges(expression, shortcuts = this.shortcuts): string | Array<{ key, value, operator }> {
    if (!`${expression} `.match(this.regularExpession))
      return expression;
    let badges = [];
    console.log(expression);
    `${expression} `.replace(this.regularExpession, (match, shortcut, keyword) => {
      console.log(match, shortcut, keyword);
      //let key = shortcuts[_.toLower(_.trim(shortcut, ':'))];
      let key = _.trim(shortcut, ':');
      let badge = {
        key,
        value: _.trim(keyword),
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

  public transformKeyword(expr: any) {
    return expr.replace(/(\w*:){1}/g, (match, shortcut, keyword) => {
      let shortCutExist = this.shortcuts[_.toLower(_.trim(shortcut, ':'))];
      return shortCutExist ? shortCutExist + ":" : shortcut;
    });
  }

  public initMappers(shortCuts: ShortCut[]) {
    let mapTableNameToBadgeKey= {};
    shortCuts.forEach( shortCut => {
      mapTableNameToBadgeKey[shortCut.mappingTable] = shortCut.shortCutLabel;
    })
    return mapTableNameToBadgeKey;
  }

  public clearString(expr) {
    return _.replace(expr, /["]/g, '')
  }

  public parseAsterisk(expr: string) {
    if(!_.includes(expr, "*")) {
      return this.padWithLike('e', this.padWithLike('s', expr));
    } else {
      return _.replace(expr, /[*]/g, '%');
    }
  }

  public padWithLike(t, expr) {
    return expr && (t == 's' ? _.padStart : _.padEnd)(expr, expr.length + 1, "%");
  }
}
