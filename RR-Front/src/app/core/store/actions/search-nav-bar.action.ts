import {WorkspaceFilter} from '../../model';

export class SearchContractsCountAction {
  static readonly type = '[Search Nav Bar] Search contacts with count';

  constructor(public keyword: string) {
  }
}

export class SearchContractsCountSuccessAction {
  static readonly type = '[Search Nav Bar] Search contacts with count Success';

  constructor(public result: any) {
  }
}

export class SearchContractsCountErrorAction {
  static readonly type = '[Search Nav Bar] Search contacts with count Error';

  constructor(public error: any) {
  }
}


export class PatchSearchStateAction {
  static readonly type = '[Search Nav Bar] Patch Search State';

  constructor(public payload: { key: string, value: any } | { key: string, value: any }[]) {
  }
}


export class ClearSearchValuesAction {
  static readonly type = '[Search Nav Bar] Clear Search values';

  constructor() {
  }
}

export class LoadRecentSearchAction {
  static readonly type = '[Search Nav Bar] Load Recent Search from storage';

  constructor() {
  }
}

export class SetLoadingState {
  static readonly type = '[Search Nav Bar] Set Loading State';

  constructor(public payload?: any) {
  }
}

export class EnableExpertMode {
  static readonly type = '[Search Nav Bar] Enable Expert Mode';

  constructor() {
  }
}

export class DisableExpertMode {
  static readonly type = '[Search Nav Bar] Disable Expert Mode';

  constructor() {
  }
}

export class SelectBadgeAction {
  static readonly type = '[Search Nav Bar] Select badge';

  constructor(public badge: { key: string, value: string }, public keyword: string) {
  }
}


export class DeleteLastBadgeAction {
  static readonly type = '[Search Nav Bar] Delete last badge';

  constructor() {
  }
}

export class DeleteAllBadgesAction {
  static readonly type = '[Search Nav Bar] Delete All Badges';

  constructor() {
  }
}

export class CloseBadgeByIndexAction {
  static readonly type = '[Search Nav Bar] Delete Badge by index';

  constructor(public index: number, public expertMode: boolean) {
  }
}

export class SearchInputFocusAction {
  static readonly type = '[Search Nav Bar] Search Input focus';

  constructor(public expertMode: boolean, public inputValue: string) {
  }
}

export class SearchInputValueChange {
  static readonly type = '[Search Nav Bar] Search input value change';

  constructor(public expertMode: boolean, public value: string) {
  }
}

export class ExpertModeSearchAction {
  static readonly type = '[Search Nav Bar] do expert mode search action';

  constructor(public expression: string) {
  }

}

export class SearchAction {
  static readonly type = '[Search Nav Bar] do search';
  constructor(public bages: any[], public keyword:string) {}
}

export class CloseTagByIndexAction {
  static readonly type = '[Search Nav Bar] Close tag by index';
  constructor(public index:number) {}
}

export class CloseAllTagsAction{
  static readonly type= '[Search Nav Bar] Close all search tag';
  constructor() {}
}

export class CloseGlobalSearchAction {
  static readonly type = '[Search Nav Bar] Close global search tag';
  constructor() {}
}



