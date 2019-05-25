import {Observable, Subject} from 'rxjs';
import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {WorkspaceFilter} from '../model/workspace-filter';
import {of} from 'rxjs';
import * as _ from 'lodash'
import {Store} from '@ngxs/store';
import {SetLoadingState} from '../store/actions';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private visibleDropdown = false;
  private _searchedItems = [];
  private _globalSearchItem = '';
  public expertModeFilter: any[] = [];
  public expertModeEnabled = false;
  public keyword = null;

  public infodropdown = new Subject<any>();
  public items = new Subject<any>();
  public globalSearch$ = new Subject<any>();

  public loading$ = new Subject<any>();
  public loading = false;

  private readonly api = environment.API_URI + 'search/';

  constructor(private _http: HttpClient, private store$: Store) {
  }

  searchContracts(filter: WorkspaceFilter, offset = '0', size = '20') {
    return this._http.post(`${this.api}workspace`, filter, {params: {offset, size}});
  }

  searchByTable(keyword = '', size = '5', table = 'country') {
    return this._http.get(`${this.api}searchcount`, {params: {keyword, size, table}});
  }

  searchWorkspace(id = '', year = '') {
    return this._http.get(`${this.api}worspace/${id}/${year}`);
  }

  searchGlobal(filter, offset= '0', size = '100') {
    return this._http.get(`${this.api}workspace`, {params: _.pickBy({...filter, offset, size},_.identity())});
  }

  expertModeSearch(filter) {
    return this._http.post(`${this.api}workspace/expert-mode`, filter);
  }

  affectItems(item) {
    this._searchedItems = item;
    this.items.next();
  }

  get globalSearchItem(): string {
    return this._globalSearchItem;
  }

  set globalSearchItem(value: string) {
    console.log(this._globalSearchItem);
    this._globalSearchItem = value;
    this.globalSearch$.next();
  }

  get searchedItems(): any[] {
    return this._searchedItems;
  }

  getvisibleDropdown() {
    return this.visibleDropdown;
  }

  setvisibleDropdown(value: boolean) {
    this.visibleDropdown = value;
    this.infodropdown.next();
  }

  setLoading(value) {
    this.store$.dispatch(new SetLoadingState(value))
  }
  addSearchedItems(itm) {
    this._searchedItems.push(itm);
    this.items.next();
  }
}
