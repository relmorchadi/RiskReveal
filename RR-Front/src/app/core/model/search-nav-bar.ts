import {ShortCut} from "./shortcut.model";

export class SearchNavBar {
  contracts: any;
  showResult: boolean;
  showLastSearch: boolean;
  visible: boolean;
  visibleSearch: boolean;
  showClearIcon: boolean;
  actualGlobalKeyword: string;
  keywordBackup: string;
  searchValue: string;
  searchTarget: any;
  badges: any[];
  data: any;
  loading: boolean;
  recentSearch: any[];
  showRecentSearch: any[];
  savedSearch: any[];
  mostUsedSavedSearch: any[];
  shortcuts: ShortCut[];
  mapTableNameToBadgeKey: any;
  shortcutFormKeysMapper: any;
  searchContent: any;
  emptyResult: boolean;
  showSavedSearch: boolean;
}


