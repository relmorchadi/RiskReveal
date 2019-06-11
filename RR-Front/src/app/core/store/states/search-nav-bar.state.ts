import {Action, NgxsOnInit, Selector, State, StateContext, Store} from '@ngxs/store';
import {
  ClearSearchValuesAction, CloseAllTagsAction, CloseBadgeByIndexAction, CloseGlobalSearchAction, CloseTagByIndexAction,
  DeleteAllBadgesAction,
  DeleteLastBadgeAction,
  DisableExpertMode,
  EnableExpertMode, ExpertModeSearchAction,
  LoadRecentSearchAction,
  PatchSearchStateAction, SearchAction,
  SearchContractsCountAction, SearchInputFocusAction, SearchInputValueChange,
  SelectBadgeAction
} from '../actions';
import {forkJoin, of} from 'rxjs';
import {SearchNavBar} from '../../model/search-nav-bar';
import * as _ from 'lodash';
import {catchError, switchMap} from 'rxjs/operators';
import {SearchService} from "../../service";
import {Inject} from "@angular/core";
import produce from "immer";
import {BadgesService} from "../../service/badges.service";
import {Navigate} from "@ngxs/router-plugin";
import {NavigationExtras} from "@angular/router";

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
  badges: [],
  data: [],
  loading: false,
  recentSearch: [],
  showRecentSearch: [],
  tables: ['YEAR', 'CEDANT_NAME', 'WORKSPACE_ID', 'WORKSPACE_NAME', 'COUNTRY', 'CEDANT_CODE'],
  savedSearch: [
    // [{key: 'Cedant', value: 'HDI Global'}, {key: 'UW/Year', value: '2019'}],
    // [{key: 'Cedant', value: 'Tokio'}, {key: 'Country', value: 'Japan'}, {key: 'UW/Year', value: '2019'}],
    // [{key: 'Country', value: 'Japan'}, {key: 'Program', value: 'Prog Name'}]
  ],
  tagShortcuts: [
    {tag: 'Cedant Name', value: 'c:'},
    {tag: 'Cedant Code', value: 'cid:'},
    {tag: 'Country', value: 'ctr:'},
    {tag: 'UW Year', value: 'uwy:'},
    {tag: 'Workspace Name', value: 'wn:'},
    {tag: 'Workspace Code', value: 'wid:'},
    {tag: 'Program', value: 'C:'},
    {tag: 'PLT', value: 'C:'},
    {tag: 'Section', value: 'C:'},
    {tag: 'Subsidiary', value: 'C:'},
    {tag: 'Ledger', value: 'C:'},
    {tag: 'Bouquet', value: 'C:'},
    {tag: 'Contract', value: 'C:'},
    {tag: 'UW Unit', value: 'C:'}
  ],
  sortcutFormKeysMapper: {
    c: 'Cedant Name',
    cid: 'Cedant Code',
    uwy: 'Year',
    wn: 'Workspace Name',
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


  /**
   * Commands
   */
  @Action(PatchSearchStateAction)
  patchSearchState(ctx: StateContext<SearchNavBarState>, {payload}: PatchSearchStateAction) {
    if (_.isArray(payload))
      payload.forEach(item => ctx.patchState({[item.key]: item.value}));
    else
      ctx.patchState({[payload.key]: payload.value});
  }

  @Action(SearchContractsCountAction, {cancelUncompleted: true})
  searchContracts(ctx: StateContext<SearchNavBar>, {keyword}: SearchContractsCountAction) {
    ctx.patchState({
      data: [],
      loading: true
    });
    return forkJoin(
      ...ctx.getState().tables.map(tableName => this.searchLoader(keyword, tableName))
    ).pipe(
      switchMap(payload => {
        return of(ctx.patchState({
          loading: false,
          data: _.map(payload, 'content')
        }));
      }),
      catchError(err => {
        //@TODO Handle error case
        console.error('Failed to search contracts Count');
        return of();
      })
    );
  }

  @Action(SelectBadgeAction)
  addBadge(ctx: StateContext<SearchNavBar>, {badge, keyword}: SelectBadgeAction) {
    ctx.patchState({
      badges: [...ctx.getState().badges, badge],
      showLastSearch: true,
      showResult: true,
      keywordBackup: keyword
    });
    if (keyword && keyword.length && ctx.getState().visibleSearch)
      ctx.dispatch(new SearchContractsCountAction(keyword));
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
    ctx.patchState({visibleSearch: false});
  }

  @Action(DisableExpertMode)
  disableExpertMode(ctx: StateContext<SearchNavBar>) {
    ctx.patchState({visibleSearch: true});
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

  @Action(SearchInputFocusAction)
  searchInputFocus(ctx: StateContext<SearchNavBar>, {expertMode, inputValue}) {
    ctx.patchState(produce(ctx.getState(), draftState => {
      draftState.showLastSearch = expertMode ? true : ((inputValue === '' || inputValue.length < 2) ? true : false);
      draftState.showResult = expertMode ? false : (!(inputValue === '' || inputValue.length < 2) ? true : draftState.showResult);
      draftState.visibleSearch = true;
      draftState.visible = false;
    }));
  }

  @Action(SearchInputValueChange)
  searchInputValueChange(ctx: StateContext<SearchNavBar>, {expertMode, value}) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.searchValue = value;
      draft.showResult = !(value === '');
      if (!expertMode) {
        if (value === '' || value.length < 2) {
          draft = _.merge(draft, {showLastSearch: true, showResult: false})
        } else {
          draft.showLastSearch = false;
          draft.showResult = true;
        }
        draft.visibleSearch = true;
      } else {
        draft.visibleSearch = false;
      }
      draft.visible = false;
    }));
  }

  @Action(ExpertModeSearchAction)
  doExpertModeSearch(ctx: StateContext<SearchNavBar>, {expression}) {
    ctx.patchState(produce(ctx.getState(), draft => {
      if(! _.isEmpty(expression) ){
        draft.searchContent = {value: this._badgesService.generateBadges(expression, draft.sortcutFormKeysMapper)};
        draft.badges= draft.searchContent.value;
      }
      if(_.isArray(draft.searchContent.value)){
        // draft.badges= draft.badges;
        draft.recentSearch=_.uniqWith([[...draft.badges], ...draft.recentSearch].slice(0, 5), _.isEqual).filter(item => ! _.isEmpty(item) );
      }
      draft.visibleSearch=false;
      localStorage.setItem('items', JSON.stringify(draft.recentSearch));
    }));
    ctx.dispatch(new Navigate(['/search']));
  }

  @Action(SearchAction)
  doSearch(ctx: StateContext<SearchNavBar>, {bages, keyword}) {
    if (_.isEmpty(bages) && _.isEmpty(keyword))
      throw new Error('Search without keyword or value')
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.searchContent = {value: _.isEmpty(bages) ? keyword : bages};
      draft.recentSearch=_.uniqWith([[...draft.badges], ...draft.recentSearch].slice(0, 5), _.isEqual).filter(item => ! _.isEmpty(item) );
      localStorage.setItem('items', JSON.stringify(draft.recentSearch));
      draft.visibleSearch=false;
    }));
    ctx.dispatch(new Navigate(['/search']));
  }

  private pushBadgesToLocalStorage(badges){

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
    }))
  }

  private searchLoader(keyword, table) {
    return this._searchService.searchByTable(keyword || '', '5', table || '');
  }


}
