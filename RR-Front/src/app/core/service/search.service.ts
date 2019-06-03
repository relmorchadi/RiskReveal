import {Observable, Subject} from 'rxjs';
import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {WorkspaceFilter} from '../model/workspace-filter';
import {of} from 'rxjs';
import * as _ from 'lodash'
import {Select, Selector, Store} from '@ngxs/store';
import {PatchSearchStateAction, SetLoadingState} from '../store/actions';
import {NotificationService} from "../../shared/notification.service";

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

  private readonly api = environment.API_URI + 'search/';

  constructor(private _http: HttpClient, private store: Store, private _notifcationService:NotificationService) {
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

  searchGlobal(filter, offset = '0', size = '100') {
    return this._http.get(`${this.api}workspace`, {params: _.pickBy({...filter, offset, size}, _.identity())});
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
    this.store.dispatch(new SetLoadingState(value));
  }

  addSearchedItems(itm) {
    this._searchedItems.push(itm);
    this.items.next();
  }

  resetSearchedItems() {
    this._searchedItems = [];
    this.items.next();
  }

  examinateExpression(isExpertMode:boolean, expression: string, formGroup, shortcutMapper) {
    let badges = [];
    if (isExpertMode) {
      const regExp = /(\w*:){1}(((\w|\")*\s)*)/g;
      const globalKeyword = `${expression} `.replace(regExp, (match, shortcut, keyword) => {
        this.keyword = expression.trim().split(" ")[0];
        if (this.keyword.indexOf(':') > -1) this.keyword = null;
        let field = shortcutMapper[_.trim(shortcut, ':')];
        this.expertModeFilter.push({
          field : shortcutMapper[_.trim(shortcut, ':')],
          value: _.trim(_.trim(_.trim(keyword),'"')),
          operator: this.getOperator(_.trim(keyword), field)});
        badges.push({
          key : shortcutMapper[_.trim(shortcut, ':')],
          value: _.trim(_.trim(_.trim(keyword),'"')),
          operator: this.getOperator(_.trim(keyword), field)
        }) ;
        return this.toBadges(_.trim(shortcut, ':'), _.trim(keyword), formGroup,shortcutMapper,badges );
      }).trim();
      console.log('My badges', badges);
      this.store.dispatch(new PatchSearchStateAction({key: 'badges', value: badges}));
      if (this.keyword)
        setTimeout(() => this.addSearchedItems({key: 'Global Search', value: this.keyword}));
      this.store.dispatch(new PatchSearchStateAction({key: 'actualGlobalKeyword', value: globalKeyword}));
    } else {
      this.store.dispatch(new PatchSearchStateAction({key: 'actualGlobalKeyword', value: expression}));
    }
  }

  toBadges(shortcut, keyword, formGroup, shortcutMapper,badges) {
    const correspondingKey: string = shortcutMapper[shortcut];
    if (correspondingKey) {
      formGroup.get(correspondingKey).patchValue(keyword);
      const instance = {key: this.stringUpdate(correspondingKey), value: keyword};
      console.log('Those are badges', badges);
      badges= [...badges,instance];
      this.store.dispatch(new PatchSearchStateAction({key: 'badges', value: badges}));
    } else {
      this._notifcationService.createNotification('Information',
        'some shortcuts were false please check the shortcuts or change them!',
        'error', 'bottomRight', 4000);
    }
    formGroup.get('globalKeyword').patchValue('');
    return '';
  }

  getOperator(str: string, field: string) {
    if (str.endsWith('\"') && str.indexOf('\"') === 0) {
      return 'EQUAL';
    } else { return 'LIKE'; }
  }

  stringUpdate(value) {
    let newString = _.lowerCase(value);
    newString = newString.split(' ').map(_.upperFirst).join(' ');
    return newString;
  }

}
