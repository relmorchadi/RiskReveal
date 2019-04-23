import {Action, NgxsOnInit, Selector, State, StateContext, Store} from '@ngxs/store';
import {
  AddBadgeSearchStateAction, ClearSearchValuesAction, LoadRecentSearchAction,
  PatchSearchStateAction,
  SearchContractsCountAction, SearchContractsCountErrorAction,
  SearchContractsCountSuccessAction
} from '../actions/search-nav-bar.state';
import {SearchService} from '../../service/search.service';
import {catchError, mergeMap} from 'rxjs/operators';
import {forkJoin, of} from 'rxjs';
import {SearchNavBar} from '../../model/search-nav-bar';
import {WorkspaceFilter} from '../../model';
import * as _ from 'lodash';

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
  recentSearch: [],
  showRecentSearch: [],
  tables: ['CEDANT_CODE', 'CEDANT_NAME', 'WORKSPACE_ID', 'WORKSPACE_NAME', 'YEAR', 'COUNTRY'],
  savedSearch: [
    // [{key: 'Cedant', value: 'HDI Global'}, {key: 'UW/Year', value: '2019'}],
    // [{key: 'Cedant', value: 'Tokio'}, {key: 'Country', value: 'Japan'}, {key: 'UW/Year', value: '2019'}],
    // [{key: 'Country', value: 'Japan'}, {key: 'Program', value: 'Prog Name'}]
  ],
  tagShortcuts: [
    {tag: 'Cedant', value: 'To be defined'},
    {tag: 'Country', value: 'ctr:'},
    {tag: 'Contract', value: 'C:'},
    {tag: 'Project', value: 'C:'},
    {tag: 'Program', value: 'C:'},
    {tag: 'PLT', value: 'C:'},
    {tag: 'Section', value: 'C:'},
    {tag: 'Subsidiary', value: 'C:'},
    {tag: 'Ledger', value: 'C:'},
    {tag: 'Bouquet', value: 'C:'},
    {tag: 'Workspace', value: 'wid:'},
    {tag: 'UW Year', value: 'uwy:'},
    {tag: 'UW Unit', value: 'C:'},
  ],
  sortcutFormKeysMapper: {
    c: 'cedant',
    uwy: 'year',
    tid: 'treaty',
    wn: 'workspaceName',
    wid: 'workspaceId',
    ctr: 'country'
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

  @Action(SearchContractsCountAction)
  searchContracts(ctx: StateContext<SearchNavBar>, {keyword}: SearchContractsCountAction) {
    ctx.dispatch(new PatchSearchStateAction({key: 'data', value: []}));
    forkJoin(
      ...ctx.getState().tables.map(
        tableName => this.searchLoader(keyword, tableName)
      )
    ).subscribe(payload => {
      ctx.dispatch(new PatchSearchStateAction({
        key: 'data',
        value: _.concat(ctx.getState().data, _.map(payload, 'content'))
      }));
    });
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
    ctx.patchState( {recentSearch: (JSON.parse(localStorage.getItem('items')) || []).slice(0, 3) });
  }

  private searchLoader(keyword, table) {
    return this._searchService.searchByTable(keyword || '', '5', table || '');
  }

  // @Action(SearchContractsCountSuccessAction)
  // searchContractsSuccess(ctx: StateContext<SearchNavBarState>,{result}: SearchContractsCountSuccessAction){
  //   ctx.patchState({data: result.content});
  // }


}
