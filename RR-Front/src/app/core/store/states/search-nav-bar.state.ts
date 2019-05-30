import {Action, NgxsOnInit, Selector, State, StateContext, Store} from '@ngxs/store';
import {
  AddBadgeSearchStateAction, ClearSearchValuesAction, LoadRecentSearchAction,
  PatchSearchStateAction, SearchContractsCountAction
} from '../actions';
import {SearchService} from '../../service/search.service';
import {forkJoin, of} from 'rxjs';
import {SearchNavBar} from '../../model/search-nav-bar';
import * as _ from 'lodash';
import {catchError, debounceTime, distinctUntilChanged, mergeMap, switchMap, tap} from 'rxjs/operators';

const initiaState: SearchNavBar = {
  contracts: null,
  showResult: false,
  showLastSearch: false,
  visible: false,
  visibleSearch: false,
  deleteBlock: true,
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

  constructor(private _searchService: SearchService) {

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
    ctx.dispatch(new PatchSearchStateAction({key: 'data', value: []}));
    ctx.patchState({
      loading: true
    });
    return forkJoin(
      ...ctx.getState().tables.map(tableName => this.searchLoader(keyword, tableName))
    ).pipe(
      switchMap(payload => {
        ctx.patchState({
          loading: false
        });
        return ctx.dispatch(new PatchSearchStateAction({
          key: 'data',
          value: _.map(payload, 'content')
        }));
      }),
      catchError(err => {
        //@TODO Handle error case
        console.error('Failed to search contracts Count');
        return of();
      })
    );
  }

  @Action(AddBadgeSearchStateAction)
  addBadge(ctx: StateContext<SearchNavBar>, {badge}: AddBadgeSearchStateAction) {
    ctx.patchState({badges: [...ctx.getState().badges, badge]});
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

  private searchLoader(keyword, table) {
    return this._searchService.searchByTable(keyword || '', '5', table || '');
  }

  // @Action(SearchContractsCountSuccessAction)
  // searchContractsSuccess(ctx: StateContext<SearchNavBarState>,{result}: SearchContractsCountSuccessAction){
  //   ctx.patchState({data: result.content});
  // }


}
