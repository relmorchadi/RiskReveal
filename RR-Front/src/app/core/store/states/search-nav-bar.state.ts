import {Action, NgxsOnInit, Selector, State, StateContext, Store} from '@ngxs/store';
import {
  ClearSearchValuesAction, CloseBadgeByIndexAction,
  DeleteAllBadgesAction,
  DeleteLastBadgeAction,
  DisableExpertMode,
  EnableExpertMode,
  LoadRecentSearchAction,
  PatchSearchStateAction,
  SearchContractsCountAction,
  SelectBadgeAction
} from '../actions';
import {forkJoin, of} from 'rxjs';
import {SearchNavBar} from '../../model/search-nav-bar';
import * as _ from 'lodash';
import {catchError,switchMap} from 'rxjs/operators';
import {SearchService} from "../../service";
import {Inject} from "@angular/core";
import produce from "immer";

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
    c: 'cedantName',
    cid: 'cedantCode',
    uwy: 'year',
    wn: 'workspaceName',
    wid: 'workspaceId',
    ctr: 'countryName'
  }
};


@State<SearchNavBar>({
  name: 'searchBar',
  defaults: initiaState
})
export class SearchNavBarState implements NgxsOnInit {

  ctx = null;

  constructor(@Inject(SearchService) private _searchService: SearchService) {

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
          data:  _.map(payload, 'content')
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
    ctx.patchState({badges: [...ctx.getState().badges, badge], showLastSearch: true, showResult: true, keywordBackup: keyword});
    if(keyword && keyword.length && ctx.getState().visibleSearch)
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
  enableExpertMode(ctx: StateContext<SearchNavBar>){
    ctx.patchState({visibleSearch: false});
  }

  @Action(DisableExpertMode)
  disableExpertMode(ctx: StateContext<SearchNavBar>){
    ctx.patchState({visibleSearch: true});
  }

  @Action(DeleteLastBadgeAction)
  deleteLastBadge(ctx: StateContext<SearchNavBar>){
    ctx.patchState(produce(ctx.getState(), draftState => {
      draftState.badges= draftState.badges.slice(0,draftState.badges.length-1 );
    }));
  }

  @Action(DeleteAllBadgesAction)
  deleteAllBadges(ctx: StateContext<SearchNavBar>){
    ctx.patchState(produce(ctx.getState(), draftState => {
      draftState.badges= [];
    }));
  }

  @Action(CloseBadgeByIndexAction)
  closeBadgeByIndex(ctx: StateContext<SearchNavBar>, {index, expertMode}:CloseBadgeByIndexAction){
    ctx.patchState(produce(ctx.getState(), draftState => {
      draftState.badges= _.toArray(_.omit(ctx.getState().badges, index));
    }));
  }

  private searchLoader(keyword, table) {
    return this._searchService.searchByTable(keyword || '', '5', table || '');
  }

}
