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
  DisableExpertMode,
  EnableExpertMode,
  ExpertModeSearchAction,
  LoadRecentSearchAction, LoadShortCuts,
  PatchSearchStateAction,
  saveSearch,
  SearchAction,
  SearchContractsCountAction,
  SearchInputFocusAction,
  SearchInputValueChange,
  SelectBadgeAction,
  showSavedSearch,
  toggleSavedSearch,
  UpdateBadges
} from '../actions';
import {forkJoin, of} from 'rxjs';
import {SearchNavBar} from '../../model/search-nav-bar';
import * as _ from 'lodash';
import {catchError, mergeMap, switchMap, tap} from 'rxjs/operators';
import {SearchService} from '../../service';
import {Inject} from '@angular/core';
import produce from 'immer';
import {BadgesService} from '../../service/badges.service';
import {Navigate} from '@ngxs/router-plugin';
import {ShortCut} from "../../model/shortcut.model";

const initiaState: SearchNavBar = {
  contracts: null,
  showResult: false,
  showLastSearch: false,
  visible: false,
  visibleSearch: false,
  showClearIcon: false,
  actualGlobalKeyword: '',
  keywordBackup: '',
  searchValue: '',
  searchTarget: 'treaty',
  badges: [],
  data: {},
  loading: false,
  recentSearch: [],
  showRecentSearch: [],
  emptyResult: false,
  showSavedSearch: false,
  savedSearch: [
    // [{key: 'Cedant', value: 'HDI Global'}, {key: 'UW/Year', value: '2019'}],
    // [{key: 'Cedant', value: 'Tokio'}, {key: 'Country', value: 'Japan'}, {key: 'UW/Year', value: '2019'}],
    // [{key: 'Country', value: 'Japan'}, {key: 'Program', value: 'Prog Name'}]
  ],
  shortcuts: [],
  mapTableNameToBadgeKey: {},
  sortcutFormKeysMapper: {
    c: 'Cedant Name',
    cid: 'Cedant Code',
    uwy: 'Year',
    w: 'Workspace Name',
    wid: 'Workspace Id',
    ctr: 'Country Name'
  },
  searchContent: {value: null}
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
    ctx.dispatch(new LoadShortCuts());
    ctx.dispatch(new LoadRecentSearchAction);
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
    return state.loading;
  }

  @Selector()
  static getData(state: SearchNavBar) {
    return state.data;
  }

  @Selector()
  static getSearchContent(state: SearchNavBar) {
    return state.searchContent;
  }

  @Selector()
  static getSavedSearch(state: SearchNavBar) {
    return state.savedSearch;
  }

  @Selector()
  static getBadges(state: SearchNavBar) {
    return state.badges;
  }

  @Selector()
  static getSearchTarget(state: SearchNavBar) {
    return state.searchTarget;
  }

  @Selector()
  static getShowSavedSearch(state: SearchNavBar) {
    return state.showSavedSearch;
  }

  @Selector()
  static getShortCuts(state: SearchNavBar) {
    return state.shortcuts;
  }

  @Selector()
  static getMapTableNameToBadgeKey(state: SearchNavBar) {
    return state.mapTableNameToBadgeKey;
  }


  /**
   * Commands
   */

  @Action(LoadShortCuts)
  loadShortCuts(ctx: StateContext<SearchNavBar>, action : LoadShortCuts) {
    return this._searchService.loadShort()
      .pipe(
        tap( (shortCuts: any[]) => {
          ctx.patchState(produce(ctx.getState(), draft => {
             draft.shortcuts = _.filter(shortCuts, shortCut => !_.includes(["UW YEAR", "PLT", "PROJECT",  "SECTION_NAME", "UW_UNIT"], shortCut.mappingTable));
             draft.mapTableNameToBadgeKey = this._badgesService.initMappers(_.filter(shortCuts, shortCut => !_.includes(["UW YEAR", "PLT", "PROJECT", "SECTION_NAME", "UW_UNIT"], shortCut.mappingTable)));
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
  searchContracts(ctx: StateContext<SearchNavBar>, {keyword}: SearchContractsCountAction) {
    let expression: any = keyword;
    const checkShortCut: any[] = this.checkShortCut(ctx.getState().shortcuts, expression) || [];
    if (checkShortCut.length > 0) {
      expression = checkShortCut[1];
    }
    ctx.patchState({
      data: [],
      emptyResult: false,
      loading: true
    });

    return (!checkShortCut[0] ? forkJoin(
      ...ctx.getState().shortcuts.map(shortCut => this.searchLoader(expression, shortCut.mappingTable))
    ) : this.searchLoader(expression, checkShortCut[0]))
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
            loading: false,
            searchValue: expression,
            emptyResult: checkShortCut[0] ? _.isEmpty( _.flatten(result.content) ) : _.every(data, table => _.isEmpty( _.flatten(table) )),
            data
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
  searchInputFocus(ctx: StateContext<SearchNavBar>, {expertMode, inputValue}) {
    ctx.patchState(produce(ctx.getState(), draftState => {
      draftState.showLastSearch = inputValue === '' || inputValue.length < 2;
      draftState.showResult = !(inputValue === '' || inputValue.length < 2) ? true : draftState.showResult;
      draftState.visibleSearch = true;
      draftState.visible = false;
    }));
  }

  @Action(SelectBadgeAction)
  addBadge(ctx: StateContext<SearchNavBar>, {badge, keyword}: SelectBadgeAction) {
    if (badge !== null) {
      ctx.patchState({
        badges: [...ctx.getState().badges, badge],
        showLastSearch: true,
        showResult: true,
        keywordBackup: keyword
      });
      if (keyword && keyword.length && ctx.getState().visibleSearch)
        ctx.dispatch(new SearchContractsCountAction(keyword));
    }
  }

  @Action(UpdateBadges)
  updateBadges(ctx: StateContext<SearchNavBar>, {payload}: UpdateBadges) {
    const badges = payload;
    ctx.patchState({
      badges: [...badges]
    });
  }


  @Action(ClearSearchValuesAction)
  clearSearchValues(ctx: StateContext<SearchNavBar>) {
    ctx.patchState({
      searchValue: '',
      showResult: false,
      visibleSearch: false,
      visible: false,
      showClearIcon: false,
      badges: []
    });
  }

  @Action(LoadRecentSearchAction)
  loadRecentSearch(ctx: StateContext<SearchNavBar>) {
    ctx.patchState({recentSearch: (JSON.parse(localStorage.getItem('items')) || []).slice(0, 5)});
  }

  @Action(EnableExpertMode)
  enableExpertMode(ctx: StateContext<SearchNavBar>) {
    // ctx.patchState({visibleSearch: false});
  }

  @Action(DisableExpertMode)
  disableExpertMode(ctx: StateContext<SearchNavBar>) {
    // ctx.patchState({visibleSearch: true});
  }

  @Action(DeleteLastBadgeAction)
  deleteLastBadge(ctx: StateContext<SearchNavBar>) {
    ctx.patchState(produce(ctx.getState(), draftState => {
      draftState.badges = draftState.badges.slice(0, draftState.badges.length - 1);
    }));
  }

  @Action(DeleteAllBadgesAction)
  deleteAllBadges(ctx: StateContext<SearchNavBar>) {
    ctx.patchState(produce(ctx.getState(), draftState => {
      draftState.badges = [];
    }));
  }

  @Action(CloseBadgeByIndexAction)
  closeBadgeByIndex(ctx: StateContext<SearchNavBar>, {index, expertMode}: CloseBadgeByIndexAction) {
    ctx.patchState(produce(ctx.getState(), draftState => {
      draftState.badges = _.toArray(_.omit(ctx.getState().badges, index));
    }));
  }

  @Action(SearchInputValueChange)
  searchInputValueChange(ctx: StateContext<SearchNavBar>, {expertMode, value}) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.searchValue = value;
      draft.showLastSearch = value === '' || value.length < 2;
      draft.showResult = !(value === '' || value.length < 2);
      draft.visibleSearch = value !== '';
      draft.visible = false;
    }));
  }

  @Action(showSavedSearch)
  showSavedSearch(ctx: StateContext<SearchNavBar>, {payload}: showSavedSearch) {
    const search = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.visibleSearch= false;
      draft.showSavedSearch = true
    }));
  }

  @Action(ExpertModeSearchAction)
  doExpertModeSearch(ctx: StateContext<SearchNavBar>, {expression}) {
    ctx.patchState(produce(ctx.getState(), draft => {
      if (!_.isEmpty(expression)) {
        draft.searchContent = {value: this._badgesService.generateBadges(expression, draft.sortcutFormKeysMapper)};
        console.log(draft.searchContent);
        draft.badges = _.isArray(draft.searchContent.value) ? draft.searchContent.value : [];
      }
      if (_.isArray(draft.searchContent.value)) {
        // draft.badges= draft.badges;
        draft.recentSearch = _.uniqWith([[...draft.badges], ...draft.recentSearch].slice(0, 5), _.isEqual).filter(item => !_.isEmpty(item));
      }
      draft.visibleSearch = false;
      localStorage.setItem('items', JSON.stringify(draft.recentSearch));
    }));
    ctx.dispatch(new Navigate(['/search']));
  }

  @Action(SearchAction)
  doSearch(ctx: StateContext<SearchNavBar>, {bages, keyword}) {
    if (_.isEmpty(bages) && _.isEmpty(keyword)) {
      ctx.dispatch(new Navigate(['/search']));
      throw new Error('Search without keyword or value');
    }
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.searchContent = {value: _.isEmpty(bages) ? keyword : bages};
      draft.recentSearch = _.uniqWith([[...draft.badges], ...draft.recentSearch].slice(0, 5), _.isEqual).filter(item => !_.isEmpty(item));
      localStorage.setItem('items', JSON.stringify(draft.recentSearch));
      draft.visibleSearch = false;
    }));
    ctx.dispatch(new Navigate(['/search']));
  }

  private pushBadgesToLocalStorage(badges) {

  }

  @Action(CloseTagByIndexAction)
  closeTagByIndexAction(ctx: StateContext<SearchNavBar>, {index}) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.searchContent.value = _.toArray(_.omit(draft.searchContent.value, index));
      draft.badges = draft.searchContent.value;
    }))
  }

  @Action([CloseGlobalSearchAction, CloseAllTagsAction])
  closeGlobalSearchAction(ctx: StateContext<SearchNavBar>) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.searchContent.value = null;
      draft.badges = [];
    }));
  }

  @Action(CloseSearchPopIns)
  closeSearchPopins(ctx: StateContext<SearchNavBar>) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.visible = false;
      draft.visibleSearch = false;
    }));
  }

  @Action(saveSearch)
  saveSearchList(ctx: StateContext<SearchNavBar>, {payload}: saveSearch) {
    const search = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.savedSearch = [...draft.savedSearch, search];
    }));
  }

  @Action(toggleSavedSearch)
  toggleSavedSearch(ctx: StateContext<SearchNavBar>, {payload}: toggleSavedSearch) {
    const search = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.showSavedSearch = !draft.showSavedSearch
    }));
  }

  @Action(closeSearch)
  closeSearch(ctx: StateContext<SearchNavBar>, {payload}: closeSearch) {
    const search = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.visibleSearch = false;
    }));
  }

  private checkShortCut(shortCuts: ShortCut[], keyword: string) {
    const foundShortCut = _.find(shortCuts, shortCut => _.includes(keyword, shortCut.shortCutLabel));
    return foundShortCut ? [ foundShortCut.mappingTable , foundShortCut ? keyword.substring(foundShortCut.shortCutLabel.length + 1) : keyword ] : null;
  }

  private searchLoader(keyword, table) {
    return this._searchService.searchByTable( this._badgesService.clearString(this._badgesService.parseAsterisk(keyword)) || '', '5', table || '');
  }

}
