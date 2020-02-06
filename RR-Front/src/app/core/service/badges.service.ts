import {Injectable} from '@angular/core';
import * as _ from "lodash";
import {ShortCut} from "../model/shortcut.model";

@Injectable({
  providedIn: 'root'
})
export class BadgesService {

  readonly regularExpession = /(\w*:){1}(((\w|\"|\*)*\s)*)/g;

  shortcuts = {};

  constructor() {

  }

  public initShortCuts(shortCuts: ShortCut[], searchMode) {
    let newShortCuts = {};

    _.forEach(_.filter(shortCuts, item => searchMode === 'Treaty' ? item.type === 'TTY' : item.type === 'FAC'), shortCut => {
      newShortCuts[_.trim(shortCut.shortCutAttribute, ':')] = shortCut.shortCutLabel;
    });

    this.shortcuts = newShortCuts;
  }

  public generateBadges(expression, shortcuts = this.shortcuts): { newExpr: string, badges: any[]} {
     console.log(expression);
    if (!`${expression} `.match(this.regularExpession))
      return {
        newExpr: expression,
        badges: []
      };
    let badges = [];
    let newExpr = `${expression} `.replace(this.regularExpession, (match, shortcut, keyword) => {
      let key = _.trim(shortcut, ':');
      let badge = {
        key,
        value: _.trim(keyword),
        operator: this.getOperator(_.trim(keyword), key)
      };
      badges.push(badge);
      return '';
    }) || '';
    return {
      newExpr: _.trim(newExpr) ,
      badges
    };
  }

  private getOperator(str: string, field: string) {
    if (str.endsWith('\"') && str.indexOf('\"') === 0) {
      return 'EQUAL';
    } else {
      return 'LIKE';
    }
  }

  public transformKeyword(expr: any, searchMode) {
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

  public initShortCutsFromKeysMapper(shortCuts: ShortCut[]) {
     let newMapper= {};

     _.forEach(shortCuts, shortCut => {
       newMapper[_.trim(shortCut.shortCutAttribute, ':')] = _.startCase(shortCut.shortCutLabel);
     })

    return newMapper;
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
