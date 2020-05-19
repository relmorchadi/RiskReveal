import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import {
  ClearSearchValuesAction,
  CloseAllTagsAction,
  CloseBadgeByIndexAction,
  CloseGlobalSearchAction,
  closeSearch,
  CloseSearchPopIns,
  CloseTagByIndexAction,
  DeleteAllBadgesAction,
  DeleteLastBadgeAction,
  ExpertModeSearchAction, LoadMostUsedSavedSearch,
  LoadRecentSearchAction, LoadSavedSearch, LoadShortCuts,
  PatchSearchStateAction,
  saveSearch,
  SearchAction,
  SearchContractsCountAction,
  SearchInputFocusAction,
  SearchInputValueChange,
  SelectBadgeAction,
  showSavedSearch,
  toggleSavedSearch,
  UpdateBadges,
  DeleteSearchItem, LoadRecentSearch, SwitchSearchType
} from '../actions';
import {forkJoin, of} from 'rxjs';
import {SearchNavBar} from '../../model/search-nav-bar';
import * as _ from 'lodash';
import {catchError, mergeMap, switchMap, tap} from 'rxjs/operators';
import {SearchService} from '../../service';
import {Inject} from '@angular/core';
import produce from 'immer';
import {BadgesService} from '../../service/badges.service';
import {Navigate} from "@ngxs/router-plugin";
import {ShortCut} from "../../model/shortcut.model";
import {DashboardState} from "./dashboard.state";
import {DashboardModel} from "../../model/dashboard.model";

const initSearchItem = {
  contracts: null,
  showResult: false,
  showLastSearch: false,
  visible: false,
  visibleSearch: false,
  showClearIcon: false,
  actualGlobalKeyword: '',
  keywordBackup: '',
  searchValue: '',
  searchTarget: 'Fac',
  badges: [],
  data: {},
  loading: false,
  recentSearch: [],
  showRecentSearch: [],
  emptyResult: false,
  showSavedSearch: false,
  savedSearch: [],
  mostUsedSavedSearch: [],
  shortcuts: [],
  mapTableNameToBadgeKey: {},
  shortcutFormKeysMapper: {},
  searchContent: {value: null}
};
const initiaState: SearchNavBar = {
  globalSearch: initSearchItem,
  cloneDataWsSearch: initSearchItem,
  actualSearchType: 'globalSearch',
  };
@State<SearchNavBar>({
  name: 'searchBar',
  defaults: initiaState
})
export class SearchNavBarState implements NgxsOnInit {

  ctx = null;

  constructor(@Inject(SearchService) private _searchService: SearchService,
              @Inject(BadgesService) private _badgesService: BadgesService) {

  }

  ngxsOnInit(ctx?: StateContext<SearchNavBarState>): void | any {
    this.ctx = ctx;
  }

  /**
   * Selectors
   */
  @Selector()
  static getSearchNavbarState(state: SearchNavBar) {
    return state;
  }

  @Selector()
  static getLoadingState(state: SearchNavBar) {
    const searchType = state.actualSearchType;
    return state[searchType].loading;
  }

  @Selector()
  static getData(state: SearchNavBar) {
    const searchType = state.actualSearchType;
    return state[searchType].data;
  }

  @Selector()
  static getSearchContent(state: SearchNavBar) {
    const searchType = state.actualSearchType;
    return state[searchType].searchContent;
  }

  @Selector()
  static getSavedSearch(state: SearchNavBar) {
    const searchType = state.actualSearchType;
    return state[searchType].savedSearch;
  }

  @Selector()
  static getBadges(state: SearchNavBar) {
    const searchType = state.actualSearchType;
    return state[searchType].badges;
  }

  @Selector()
  static getSearchTarget(state: SearchNavBar) {
    const searchType = state.actualSearchType;
    return state[searchType].searchTarget;
  }

  @Selector()
  static getShowSavedSearch(state: SearchNavBar) {
    const searchType = state.actualSearchType;
    return state[searchType].showSavedSearch;
  }

  @Selector()
  static getShortCuts(state: SearchNavBar) {
    const searchType = state.actualSearchType;
    return state[searchType].shortcuts;
  }

  @Selector()
  static getMapTableNameToBadgeKey(state: SearchNavBar) {
    const searchType = state.actualSearchType;
    return state[searchType].mapTableNameToBadgeKey;
  }

  @Selector()
  static getActualGlobalKeyword(state: SearchNavBar) {
    const searchType = state.actualSearchType;
    return state[searchType].actualGlobalKeyword;
  }


  /**
   * Commands
   */

  @Action(LoadShortCuts)
  loadShortCuts(ctx: StateContext<SearchNavBarState>, action : LoadShortCuts) {
    return this._searchService.loadShort()
      .pipe(
        tap( (shortCuts: any[]) => {
          ctx.patchState(produce(ctx.getState(), (draft: SearchNavBar) => {
             draft[draft.actualSearchType].shortcuts = _.filter(shortCuts, shortCut => !_.includes(["SECTION_NAME", "UW_UNIT"], shortCut.mappingTable) && (shortCut.mappingTable !== "PROJECT_ID" || shortCut.type !== 'TTY')
                 && (shortCut.mappingTable !== "CLIENT_NAME" || shortCut.type !== 'FAC') && (shortCut.mappingTable !== "PLT" || shortCut.type !== 'TTY'));
             draft[draft.actualSearchType].mapTableNameToBadgeKey = this._badgesService.initMappers(_.filter(shortCuts, shortCut => !_.includes(["SECTION_NAME", "UW_UNIT"], shortCut.mappingTable) && (shortCut.mappingTable !== "PROJECT_ID" || shortCut.type !== 'TTY')
                 && (shortCut.mappingTable !== "CLIENT_NAME" || shortCut.type !== 'FAC') && (shortCut.mappingTable !== "PLT" || shortCut.type !== 'TTY')));
             draft[draft.actualSearchType].shortcutFormKeysMapper = this._badgesService.initShortCutsFromKeysMapper(_.map(shortCuts,({shortCutLabel, shortCutAttribute, mappingTable, type}) => new ShortCut(shortCutLabel, shortCutAttribute, mappingTable, type)))
          }));
        })
      )
  }

  @Action(PatchSearchStateAction)
  patchSearchState(ctx: StateContext<SearchNavBar>, {payload}: PatchSearchStateAction) {
    if (_.isArray(payload))
      payload.forEach(item => ctx.patchState({[item.key]: item.value}));
    else
      ctx.patchState({[payload.key]: payload.value});
  }

  @Action(SearchContractsCountAction, {cancelUncompleted: true})
  searchContracts(ctx: StateContext<SearchNavBar>, {payload}: SearchContractsCountAction) {
    const {keyword, searchMode} = payload;
    let expression: any = keyword;
    const state = ctx.getState();

    const facShortcuts = _.filter(state[state.actualSearchType].shortcuts, (stc: any) => stc.type === 'FAC');
    const treatyShortcuts = _.filter(state[state.actualSearchType].shortcuts, (stc: any) => stc.type === 'TTY');
    const searchShortCut = searchMode === 'Treaty' ? treatyShortcuts : facShortcuts;
    const checkShortCut: any[] = this.checkShortCut(searchShortCut, expression) || [];

    if (checkShortCut.length > 0) {
      expression = checkShortCut[1];
    }

    ctx.patchState({
      [state.actualSearchType]: {
        ...state[state.actualSearchType],
        data: [],
        emptyResult: false,
        loading: true,
      }
    });

    const api = (expr, mapping) => (searchMode === 'Treaty' ? this.searchLoader(expr, mapping) : this.searchLoaderFac(expr, mapping));

    return (!checkShortCut[0] ? forkJoin(
      ...(searchMode == 'Treaty' ? treatyShortcuts : facShortcuts).map(shortCut => api(expression, shortCut.mappingTable))
    ) : api(expression, checkShortCut[0]))
      .pipe(
        switchMap(payload => {
          let data= {};
          let result = _.get(payload, 'result', null);
          let mappingTable = _.get(payload, 'mappingTable', null);

          if(!checkShortCut[0]) {
            _.forEach(payload, table => {
              let result = _.get(table, 'result');
              let mappingTable = _.get(table, 'mappingTable');

              if(result.content.length) {
                data[mappingTable] = result.content
              }

            });
          } else {
            if (checkShortCut.length > 0) {
              data[mappingTable] = result.content
            }
          }

          return of(ctx.patchState({
            [state.actualSearchType]: {
              ...state[state.actualSearchType],
              loading: false,
              searchValue: keyword,
              emptyResult: checkShortCut[0] ? _.isEmpty( _.flatten(result.content) ) : _.every(data, table => _.isEmpty( _.flatten(table) )),
              data
            }
          }));
        }),
        catchError(err => {
          //@TODO Handle error case
          console.error('Failed to search contracts Count');
          return of();
        })
      )
  }

  @Action(SearchInputFocusAction)
  searchInputFocus(ctx: StateContext<SearchNavBar>, {inputValue}) {
    ctx.patchState(produce(ctx.getState(), draftState => {
      draftState[draftState.actualSearchType].showLastSearch = inputValue === '' || inputValue.length < 2;
      draftState[draftState.actualSearchType].showResult = !(inputValue === '' || inputValue.length < 2) ? true : draftState[draftState.actualSearchType].showResult;
      draftState[draftState.actualSearchType].visibleSearch = true;
      draftState[draftState.actualSearchType].visible = false;
    }));
  }

  @Action(SelectBadgeAction)
  addBadge(ctx: StateContext<SearchNavBar>, {badge, keyword, searchMode}: SelectBadgeAction) {

    const state = ctx.getState();
    if (badge !== null) {
      ctx.patchState({
        [state.actualSearchType]: {
          ...state[state.actualSearchType],
          badges: [...state[state.actualSearchType].badges, badge],
          showLastSearch: true,
          showResult: true,
          keywordBackup: keyword
        }
      });
      if (keyword && keyword.length && state[state.actualSearchType].visibleSearch)
        ctx.dispatch(new SearchContractsCountAction({keyword, searchMode}));
    }
  }

  @Action(UpdateBadges)
  updateBadges(ctx: StateContext<SearchNavBar>, {payload}: UpdateBadges) {
    const badges = payload;
    ctx.patchState({
      [ctx.getState().actualSearchType]: {
        ...ctx.getState()[ctx.getState().actualSearchType],
        badges: [...badges]
      }
    });
  }


  @Action(ClearSearchValuesAction)
  clearSearchValues(ctx: StateContext<SearchNavBar>) {
    ctx.patchState({
      [ctx.getState().actualSearchType]: {
        ...ctx.getState()[ctx.getState().actualSearchType],
        searchValue: '',
        showResult: false,
        visibleSearch: false,
        visible: false,
        showClearIcon: false,
        badges: []
      }
    });
  }

  @Action(LoadRecentSearchAction)
  loadRecentSearchFromLocalStorage(ctx: StateContext<SearchNavBar>) {
    ctx.patchState({
      [ctx.getState().actualSearchType]: {
        ...ctx.getState()[ctx.getState().actualSearchType],
        recentSearch: (JSON.parse(localStorage.getItem('items')) || []).slice(0, 5)
      }
    });
  }

  @Action(DeleteLastBadgeAction)
  deleteLastBadge(ctx: StateContext<SearchNavBar>) {
    ctx.patchState(produce(ctx.getState(), draftState => {
      draftState[draftState.actualSearchType].badges = draftState[draftState.actualSearchType].badges.slice(0, draftState[draftState.actualSearchType].badges.length - 1);
    }));
  }

  @Action(DeleteAllBadgesAction)
  deleteAllBadges(ctx: StateContext<SearchNavBar>) {
    ctx.patchState(produce(ctx.getState(), draftState => {
      draftState[draftState.actualSearchType].badges = [];
    }));
  }

  @Action(CloseBadgeByIndexAction)
  closeBadgeByIndex(ctx: StateContext<SearchNavBar>, {index}: CloseBadgeByIndexAction) {
    ctx.patchState(produce(ctx.getState(), draftState => {
      draftState[draftState.actualSearchType].badges = _.toArray(_.omit(draftState[draftState.actualSearchType].badges, index));
    }));
  }

  @Action(SearchInputValueChange)
  searchInputValueChange(ctx: StateContext<SearchNavBar>, {value}) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft[draft.actualSearchType].searchValue = value;
      draft[draft.actualSearchType].showLastSearch = value === '' || value.length < 2;
      draft[draft.actualSearchType].showResult = !(value === '' || value.length < 2);
      draft[draft.actualSearchType].visibleSearch = value !== '';
      draft[draft.actualSearchType].visible = false;
    }));
  }

  @Action(showSavedSearch)
  showSavedSearch(ctx: StateContext<SearchNavBar>, action: showSavedSearch) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft[draft.actualSearchType].visibleSearch= false;
      draft[draft.actualSearchType].showSavedSearch = true
    }));
  }

  @Action(ExpertModeSearchAction)
  doExpertModeSearch(ctx: StateContext<SearchNavBar>, {expression}) {
    ctx.patchState(produce(ctx.getState(), draft => {
      if (!_.isEmpty(expression)) {
        const {
          badges,
          newExpr
        } = this._badgesService.generateBadges(expression, draft[draft.actualSearchType].shortcutFormKeysMapper);
        console.log({
          badges,
          newExpr
        });
        draft[draft.actualSearchType].actualGlobalKeyword = newExpr;
        let newBadges = [];
        if(newExpr) {
          newBadges = [{key: "global search", operator: "LIKE", value: newExpr}, ...badges];
        } else {
          let oldGlobalKeyword = _.find(draft[draft.actualSearchType].badges, e => e.key == "global search");
          if(oldGlobalKeyword) {
            newBadges = [{key: "global search", operator: "LIKE", value: oldGlobalKeyword.value}, ...badges]
          } else {
            newBadges = badges;
          }
        }
        draft[draft.actualSearchType].badges = newBadges;
        draft[draft.actualSearchType].searchContent = {value: newBadges};
      }
      draft[draft.actualSearchType].visibleSearch = false;
    }));
    ctx.dispatch(new Navigate(['/search']));
  }

  @Action(SearchAction)
  doSearch(ctx: StateContext<SearchNavBar>, {badges, keyword}) {
    if (_.isEmpty(badges) && _.isEmpty(keyword)) {
      ctx.dispatch(new Navigate(['/search']));
      throw new Error('Search without keyword or value');
    }
    ctx.patchState(produce(ctx.getState(), draft => {
      draft[draft.actualSearchType].searchContent = {value: _.isEmpty(badges) ? keyword : badges};
      draft[draft.actualSearchType].recentSearch = _.uniqWith([[...draft[draft.actualSearchType].badges], ...draft[draft.actualSearchType].recentSearch].slice(0, 5), _.isEqual).filter(item => !_.isEmpty(item));
      localStorage.setItem('items', JSON.stringify(draft[draft.actualSearchType].recentSearch));
      draft[draft.actualSearchType].visibleSearch = false;
    }));
    ctx.dispatch(new Navigate(['/search']));
  }

  @Action(CloseTagByIndexAction)
  closeTagByIndexAction(ctx: StateContext<SearchNavBar>, {index}) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft[draft.actualSearchType].searchContent.value = _.toArray(_.omit(draft[draft.actualSearchType].searchContent.value, index));
      draft[draft.actualSearchType].badges = draft[draft.actualSearchType].searchContent.value;
    }))
  }

  @Action(CloseGlobalSearchAction)
  closeGlobalSearchAction(ctx: StateContext<SearchNavBar>) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft[draft.actualSearchType].searchValue= '';
      draft[draft.actualSearchType].actualGlobalKeyword= '';
    }));
  }

  @Action(CloseAllTagsAction)
  closeAllTagsAction(ctx: StateContext<SearchNavBar>) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft[draft.actualSearchType].searchContent.value = null;
      draft[draft.actualSearchType].badges = [];
    }));
  }

  @Action(CloseSearchPopIns)
  closeSearchPopins(ctx: StateContext<SearchNavBar>) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft[draft.actualSearchType].visible = false;
      draft[draft.actualSearchType].visibleSearch = false;
    }));
  }

  @Action(LoadSavedSearch)
  loadSavedSearch(ctx: StateContext<SearchNavBar>, {payload}: LoadSavedSearch) {
    return this._searchService.getSavedSearch({
      searchType: _.upperCase(ctx.getState()[ctx.getState().actualSearchType].searchTarget)
    })
      .pipe(
        tap( savedSearch => {
          ctx.patchState({
            [ctx.getState().actualSearchType]: {
              ...ctx.getState()[ctx.getState().actualSearchType],
              savedSearch: _.map(savedSearch, item => ({
                ..._.pick(item, ['label', 'userId', 'id']),
                badges: item.items
              }))
            }
          })
        })
      )
  }

  @Action(DeleteSearchItem)
  deleteSavedSearch(ctx: StateContext<SearchNavBar>, {payload}: DeleteSearchItem) {
    return this._searchService.deleteSavedSearch({
      id: payload.id
    }).pipe(
      mergeMap( () => ctx.dispatch(new LoadSavedSearch()))
    )
  }

  @Action(LoadRecentSearch)
  loadRecentSearch(ctx: StateContext<SearchNavBar>, {payload}: LoadRecentSearch) {
    const {
      searchTarget
    } = payload;
    return this._searchService.getMostRecentSearch({
      searchType: _.upperCase(searchTarget)
    })
      .pipe(
        tap(
          (recentSearch: any) => ctx.patchState({
            [ctx.getState().actualSearchType]: {
              ...ctx.getState()[ctx.getState().actualSearchType],
              searchTarget,
              recentSearch: recentSearch
            }
          })
        )
      )
  }

  @Action(LoadMostUsedSavedSearch)
  loadMostUsedSavedSearch(ctx: StateContext<SearchNavBar>, {payload}: LoadMostUsedSavedSearch) {
    return this._searchService.getMostUsedSavedSearch({
      searchType: _.upperCase(ctx.getState()[ctx.getState().actualSearchType].searchTarget)
    })
      .pipe(
        tap( mostUsedSavedSearch => {
          ctx.patchState({
            [ctx.getState().actualSearchType]: {
              ...ctx.getState()[ctx.getState().actualSearchType],
              mostUsedSavedSearch: _.map(mostUsedSavedSearch, item => ({
                ..._.pick(item, ['label', 'userId', 'id']),
                badges: item.items
              }))
            }
          })
        })
      )
  }

  @Action(saveSearch)
  saveSearchList(ctx: StateContext<SearchNavBar>, {payload}: saveSearch) {
    const {
      searchType,
      items,
      label
    } = payload;

    return this._searchService.saveSearch({...payload, userId: 1})
      .pipe(
        tap( searchItem => {
          ctx.patchState(produce(ctx.getState(), draft => {
            draft[draft.actualSearchType].savedSearch = [...draft[draft.actualSearchType].savedSearch, {..._.pick(searchItem, ['label', 'userId', 'id']), badges: searchItem.items}];
          }))
        })
      )
  }

  @Action(toggleSavedSearch)
  toggleSavedSearch(ctx: StateContext<SearchNavBar>, {payload}: toggleSavedSearch) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft[draft.actualSearchType].showSavedSearch = !draft[draft.actualSearchType].showSavedSearch
    }));
  }

  @Action(closeSearch)
  closeSearch(ctx: StateContext<SearchNavBar>, {payload}: closeSearch) {
    const search = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft[draft.actualSearchType].visibleSearch = false;
    }));
  }

  @Action(SwitchSearchType)
  switchSearchType(ctx: StateContext<SearchNavBar>, {payload}: SwitchSearchType) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.actualSearchType = payload;
    }));
  }

  private checkShortCut(shortCuts: ShortCut[], keyword: string) {
    const foundShortCut = _.find(shortCuts, shortCut => _.includes(keyword, _.camelCase(shortCut.shortCutLabel)));
    return foundShortCut ? [ foundShortCut.mappingTable , foundShortCut ? keyword.substring(_.camelCase(foundShortCut.shortCutLabel).length + 1) : keyword ] : null;
  }

  private searchLoader(keyword, table = '') {
    return this._searchService.searchByTable( this._badgesService.clearString(this._badgesService.parseAsterisk(keyword)) || '', '5', table || '');
  }

  private searchLoaderFac(keyword, table = '') {
    return this._searchService.searchByTableFac( this._badgesService.clearString(this._badgesService.parseAsterisk(keyword)) || '', '5', table || '');
  }

}
