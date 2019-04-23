import { Subject } from 'rxjs';
import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {WorkspaceFilter} from '../model/workspace-filter';
import {of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private visibleDropdown = false;
  private _searchedItems = [];
  private _globalSearchItem = '';

  public infodropdown = new Subject<any>();
  public items = new Subject<any>();
  public globalSearch$ = new Subject<any>();

  private readonly api = environment.API_URI + 'search/';

  constructor(private _http: HttpClient) { }

  searchCedent(keyword= '', size= '20') {
    return this._http.get(`${this.api}cedant`, {params: {keyword, size}});
  }

  searchCountry(keyword= '', size= '20') {
    return this._http.get(`${this.api}/country`, {params: {keyword, size}});
  }

  searchTreaty(keyword= '', size= '20') {
    return this._http.get(`${this.api}treaty`, {params: {keyword, size}});
  }

  searchYear(keyword= '', size= '20') {
    return this._http.get(`${this.api}year`, {params: {keyword, size}});
  }

  searchContracts(filter: WorkspaceFilter, size= '20') {
    return this._http.post(`${this.api}workspace`, filter, {params: {size}});
  }

  searchByTable(keyword = '', size = '5', table = 'country') {
    return this._http.get(`${this.api}searchcount`, {params: {keyword, size, table}});
  }

  searchWorkspace(id = '', year = '') {
    return this._http.get(`${this.api}worspace/${id}/${year}`);
  }

  searchGlobal(keyword, size = '20') {
    return this._http.get(`${this.api}workspace`, {params: {keyword, size}});
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
}
