import {Subject} from 'rxjs';
import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {WorkspaceFilter} from '../model/workspace-filter';
import {of} from 'rxjs';
import * as _ from 'lodash'

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

  constructor(private _http: HttpClient) {
  }

  searchContracts(filter: WorkspaceFilter, offset = '0', size = '20') {
    return this._http.post(`${this.api}workspace`, filter, {params: {offset,size}});
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

  searchRiskLinkData() {
    return this._http.get(`${environment.API_URI}risk-link/edm-rdm`);
  }

  searchRiskLinkAnalysis() {
    return this._http.get(`${environment.API_URI}risk-link/analysis`);
  }

  searchRiskLinkPortfolio() {
    return this._http.get(`${environment.API_URI}risk-link/portfolio`);
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
