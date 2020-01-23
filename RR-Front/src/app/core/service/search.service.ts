import {Observable, Subject} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {WorkspaceFilter} from '../model/workspace-filter';
import * as _ from 'lodash'
import {backendUrl, importUrl} from "../../shared/api";
import set = Reflect.set;
import {filter} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private visibleDropdown = false;
  private _searchedItems = [];
  private _globalSearchItem = '';
  public expertModeFilter: any[] = [];
  public keyword = null;
  public expertModeEnabled;

  public infodropdown = new Subject<any>();
  public items = new Subject<any>();
  public globalSearch$ = new Subject<any>();

  public loading$ = new Subject<any>();
  public loading = false;

  private readonly api = backendUrl() + 'search/';

  constructor(private _http: HttpClient) {
  }

  searchContracts(filter: WorkspaceFilter, offset = '0', size = '20') {
    return this._http.post(`${this.api}workspace`, filter, {params: {offset, size}});
  }

  searchByTable(keyword = '', size = '5', table = 'country') {
    return this._http.get(`${this.api}searchcount`, {params: {keyword, size, table}});
  }

  searchGlobal(filter, offset = '0', size = '100') {
    return this._http.get(`${this.api}workspace`, {params: _.pickBy({...filter, offset, size}, _.identity())});
  }

  expertModeSearch(filter) {
    return this._http.post(`${this.api}workspace/expert-mode`, filter);
  }

  loadShort(): Observable<any> {
    return this._http.get(`${this.api}shortcuts`);
  }

  getMostRecentSearch() {
    return this._http.get(`${this.api}recent?userId=1`);
  }

  getSavedSearch(payload) : Observable<any> {
    return this._http.get(`${this.api}saved-search`, { params: payload })
  }

  getMostUsedSavedSearch(payload) : Observable<any> {
    return this._http.get(`${this.api}saved-search/most`, { params: payload })
  }

  deleteSavedSearch(payload) : Observable<any> {
    return this._http.delete(`${this.api}saved-search/`, { params: payload })
  }

  saveSearch(payload): Observable<any> {
    return this._http.post(this.api, payload);
  }

  affectItems(item) {
    this._searchedItems = item;
    this.items.next();
  }

  get globalSearchItem(): string {
    return this._globalSearchItem;
  }

  set globalSearchItem(value: string) {
    this._globalSearchItem = value;
    this.globalSearch$.next();
  }

  getvisibleDropdown() {
    return this.visibleDropdown;
  }

  setvisibleDropdown(value: boolean) {
    this.visibleDropdown = value;
    this.infodropdown.next();
  }


}
