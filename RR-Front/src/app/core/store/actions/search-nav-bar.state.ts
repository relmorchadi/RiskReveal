import {WorkspaceFilter} from "../../model";

export class SearchContractsCountAction {
  static readonly type='[Search Nav Bar] Search contacts with count';
  constructor(public keyword:string){}
}

export class SearchContractsCountSuccessAction {
  static readonly type='[Search Nav Bar] Search contacts with count Success';
  constructor(public result:any){}
}

export class SearchContractsCountErrorAction {
  static readonly type='[Search Nav Bar] Search contacts with count Error';
  constructor(public error:any){}
}


export class PatchSearchStateAction{
  static readonly type= '[Search Nav Bar] Patch Search State';
  constructor(public payload: {key:string, value: any} | {key:string, value: any}[]){}
}


export class AddBadgeSearchStateAction{
  static readonly type= '[Search Nav Bar] Add new search badge';
  constructor(public badge: {key:string, value: any}){}
}


export class ClearSearchValuesAction{
  static readonly type= '[Search Nav Bar] Clear Search values';
  constructor(){}
}

export class LoadRecentSearchAction{
  static readonly type= '[Search Nav Bar] Load Recent Search from storage';
  constructor(){}
}



