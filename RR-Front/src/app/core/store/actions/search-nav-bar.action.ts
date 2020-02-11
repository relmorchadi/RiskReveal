export class SearchContractsCountAction {
  static readonly type = '[Search Nav Bar] Search contacts with count';
  constructor(public payload: {keyword: string, searchMode: string}) {}
}

export class PatchSearchStateAction {
  static readonly type = '[Search Nav Bar] Patch Search State';
  constructor(public payload: { key: string, value: any } | { key: string, value: any }[]) {}
}

export class ClearSearchValuesAction {
  static readonly type = '[Search Nav Bar] Clear Search values';
  constructor() {}
}

export class LoadRecentSearchAction {
  static readonly type = '[Search Nav Bar] Load Recent Search from storage';
  constructor() {}
}

export class SelectBadgeAction {
  static readonly type = '[Search Nav Bar] Select badge';
  constructor(public badge: { key: string, value: string }, public keyword: string, public searchMode: string) {}
}

export class UpdateBadges {
  static readonly type = '[Search Nav Bar] Update badges';
  constructor(public payload: any) {}
}

export class DeleteLastBadgeAction {
  static readonly type = '[Search Nav Bar] Delete last badge';
  constructor() {}
}

export class DeleteAllBadgesAction {
  static readonly type = '[Search Nav Bar] Delete All Badges';
  constructor() {}
}

export class CloseBadgeByIndexAction {
  static readonly type = '[Search Nav Bar] Delete Badge by index';
  constructor(public index: number) {}
}

export class SearchInputFocusAction {
  static readonly type = '[Search Nav Bar] Search Input focus';
  constructor(public inputValue: string) {}
}

export class SearchInputValueChange {
  static readonly type = '[Search Nav Bar] Search input value change';
  constructor(public value: string) {}
}

export class ExpertModeSearchAction {
  static readonly type = '[Search Nav Bar] do expert mode search action';
  constructor(public expression: string) {}
}

export class SearchAction {
  static readonly type = '[Search Nav Bar] do search';
  constructor(public badges: any[], public keyword: string) {}
}

export class CloseTagByIndexAction {
  static readonly type = '[Search Nav Bar] Close tag by index';
  constructor(public index: number) {}
}

export class CloseAllTagsAction {
  static readonly type = '[Search Nav Bar] Close all search tag';
  constructor() {}
}

export class CloseGlobalSearchAction {
  static readonly type = '[Search Nav Bar] Close global search tag';
  constructor() {}
}

export class CloseSearchPopIns {
  static readonly type = '[Search Nav Bar] Close search Popins';
  constructor() {}
}

export class LoadSavedSearch {
  static readonly type = '[Search Nav Bar] Load Saved Search';
  constructor(public payload?: any) {}
}

export class LoadMostUsedSavedSearch {
  static readonly type = '[Search Nav Bar] Load Saved Search';
  constructor(public payload?: any) {}
}

export class saveSearch {
  static readonly type = '[Search Nav Bar] Save Search';
  constructor(public payload?: any) {}
}

export class showSavedSearch {
  static readonly type = '[Search Nav Bar] Show Saved Search';
  constructor(public payload?: any) {}
}

export class toggleSavedSearch {
  static readonly type = '[Search Nav Bar] toggle Saved Search';
  constructor(public payload?: any) {}
}

export class closeSearch {
  static readonly type = '[Search Nav Bar] Close Search DropDown';
  constructor(public payload?: any) {}
}

export class LoadShortCuts {
  static readonly type = '[Search Nav Bar] Load ShortCuts';
  constructor(public payload?: any) {}
}

export class DeleteSearchItem {
  static readonly type = '[Search Nav Bar] Delete Saved Search';
  constructor(public payload?: any) {}
}

export class LoadRecentSearch {
  static readonly type = '[Search Nav Bar] Load Recent Search';
  constructor(public payload?: any) {}
}



