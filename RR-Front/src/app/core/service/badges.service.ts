import {Injectable} from '@angular/core';
import * as _ from "lodash";

@Injectable({
  providedIn: 'root'
})
export class BadgesService {

  readonly regularExpession = /(\w*:){1}(((\w|\")*\s)*)/g;

  readonly shortcuts = {
    c: 'CedantName',
    cid: 'CedantCode',
    uwy: 'Year',
    wn: 'WorkspaceName',
    wid: 'WorkspaceId',
    ctr: 'CountryName'
  };

  /*readonly shortcuts = {
    c: 'Cedant Name',
    cid: 'Cedant Code',
    uwy: 'Year',
    wn: 'Workspace Name',
    wid: 'Workspace Id',
    ctr: 'Country Name'
  };*/


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
}

export const mapTableNameToBadgeKey= {
  "CEDANT_NAME": "CedantName",
  "YEAR": "Year",
  "WORKSPACE_ID": "WorkspaceId",
  "WORKSPACE_NAME": "WorkspaceName",
  "COUNTRY": "Country",
  "CEDANT_CODE": "CedentCode"
};

export const mapBadgeShortCutToBadgeKey= {
  "CedantName": "Cedant Name",
  "Year": "Year",
  "WorkspaceId": "Workspace Id",
  "WorkspaceName": "Workspace Name",
  "Country": "Country",
  "CedantCode": "Cedent Code"
};
