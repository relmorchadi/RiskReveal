import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SearchService} from '../../../service/search.service';
import {debounceTime} from 'rxjs/operators';
import {NotificationService} from '../../../../shared/notification.service';

import {Router} from '@angular/router';
import {
  AddBadgeSearchStateAction, ClearSearchValuesAction, LoadRecentSearchAction,
  PatchSearchStateAction,
  SearchContractsCountAction
} from '../../../store/index';

import {Select, Store} from '@ngxs/store';
import {SearchNavBar} from '../../../model/search-nav-bar';
import {} from '../../../store/actions/search-nav-bar.state';
import * as _ from 'lodash';
import {Observable} from 'rxjs';
import {SearchNavBarState} from '../../../store/index';


@Component({
  selector: 'search-menu-item',
  templateUrl: './search-menu-item.component.html',
  styleUrls: ['./search-menu-item.component.scss']
})
export class SearchMenuItemComponent implements OnInit {

  @ViewChild('searchInput')
  searchInput: ElementRef;
  contractFilterFormGroup: FormGroup;

  @Select(SearchNavBarState)
  state$: Observable<SearchNavBar>;
  state: SearchNavBar = null;

  constructor(private _fb: FormBuilder, private _searchService: SearchService, private router: Router,
              private _notifcationService: NotificationService, private store: Store) {
    this.contractFilterFormGroup = this._fb.group({
      switchValue: false,
      globalKeyword: '',
      cedantCode: '',
      cedantName: '',
      countryName: '',
      innerCedantCode: '',
      innerCedantName: '',
      innerCountryName: '',
      innerWorkspaceId: '',
      innerWorkspaceName: '',
      innerYear: '',
      workspaceId: '',
      workspaceName: '',
      year: ''
    });
  }

  ngOnInit() {
    this.state$.subscribe(value => this.state = _.merge({}, value));
    this.contractFilterFormGroup.get('globalKeyword')
      .valueChanges
      .pipe(debounceTime(500))
      .subscribe((param) => {
        this._selectedSearch(param);
      });
    this.store.dispatch(new LoadRecentSearchAction());
    this.contractFilterFormGroup.get('globalKeyword').valueChanges.pipe(debounceTime(500))
      .subscribe(value => this.store.dispatch(new PatchSearchStateAction({key: 'searchValue', value: value})));
  }

  stringUpdate(value) {
    let newString = _.lowerCase(value);
    newString = newString.split(' ').map(_.upperFirst).join(' ');
    return newString;
  }

  isSearchRoute() {
    return window.location.href.match('search');
  }

  examinateExpression(expression: string) {
    if (this.contractFilterFormGroup.get('switchValue').value) {
      const regExp = /(\w*:){1}((\w*\s)*)/g;
      const globalKeyword = `${expression} `.replace(regExp, (match, shortcut, keyword) => {
        console.log({shortcut, keyword});
        return this.toBadges(_.trim(shortcut, ':'), _.trim(keyword));
      }).trim();
      this.store.dispatch(new PatchSearchStateAction({key: 'actualGlobalKeyword', value: globalKeyword}));
    } else {
      this.store.dispatch(new PatchSearchStateAction({key: 'actualGlobalKeyword', value: expression}));
    }
  }

  toBadges(shortcut, keyword) {
    const correspondingKey: string = this.state.sortcutFormKeysMapper[shortcut];
    if (correspondingKey) {
      this.contractFilterFormGroup.get(correspondingKey).patchValue(keyword);
      const instance = {key: correspondingKey, value: keyword};
      this.state.badges.push(instance);
      this.store.dispatch(new PatchSearchStateAction({key: 'badges', value: this.state.badges}));
    } else {
      this._notifcationService.createNotification('Information',
        'some shortcuts were false please check the shortcuts or change them!',
        'error', 'bottomRight', 4000);
    }
    this.contractFilterFormGroup.get('globalKeyword').patchValue('');
    return '';
  }

  filterContracts(keyboardEvent) {
    this._clearFilters();
    if (keyboardEvent.key === 'Enter') {
      const searchExpression = this.contractFilterFormGroup.get('globalKeyword').value;
      if (this.contractFilterFormGroup.get('switchValue').value) {
        this.examinateExpression(searchExpression);
      } else {
        this._searchService.globalSearchItem = searchExpression;
      }
      event.preventDefault();
      this.redirectToSearchPage();
    }
    if (this.state.deleteBlock === true) {
      if (keyboardEvent.key === 'Backspace' && keyboardEvent.target.value === '') {
        this.state.deleteBlock = false;
        this.store.dispatch(new PatchSearchStateAction({key: 'deleteBlock', value: false}));
        console.log(this.state.deleteBlock);
      }
    } else {
      if (keyboardEvent.key === 'Backspace' && keyboardEvent.target.value === '') {
        this.state.badges.pop();
        this.state.deleteBlock = true;
        this.store.dispatch(new PatchSearchStateAction({key: 'deleteBlock', value: true}));
        this.store.dispatch(new PatchSearchStateAction({key: 'badges', value: this.state.badges}));

        console.log(this.state.deleteBlock);
      }
    }
    if (keyboardEvent.key === 'Delete' && keyboardEvent.target.value === '') {
      this.state.badges.pop();
      this.store.dispatch(new PatchSearchStateAction({key: 'badges', value: this.state.badges}));
    }
    /* if(this.currentBadge)
    this.selectChoice({label: this.contractFilterFormGroup.get('globalKeyword').value})*/
  }

  redirectToSearchPage() {
    this._searchService.setvisibleDropdown( false );
    if (this.state.badges.length > 0) {
      this.store.dispatch(new PatchSearchStateAction({
        key: 'recentSearch',
        value: [[...this.state.badges], ...this.state.recentSearch]
      }));
      this._searchService.affectItems([...this.state.badges]);
      localStorage.setItem('items', JSON.stringify(this.state.recentSearch));
    }
    this.contractFilterFormGroup.patchValue({globalKeyword: ''});
    this.clearValue();
    this.router.navigate(['/search']);
  }

  redirectWithSearch(items) {
    this._searchService.affectItems(items);
    this.clearValue();
    this.router.navigate(['/search']);
  }

  searchLoader(keyword, table) {
    return this._searchService.searchByTable(keyword || '', '5', table || '');
  }

  selectSearchBadge(key, value) {
    const item = {key: key, value: value};
    this.contractFilterFormGroup.patchValue({globalKeyword: ''});
    this.store.dispatch([
      new PatchSearchStateAction([
        {key: 'showLastSearch', value: true},
        {key: 'showResult', value: true},
        {key: 'keywordBackup', value: this.contractFilterFormGroup.get('globalKeyword').value}
      ]),
      new AddBadgeSearchStateAction(item)]
    );
    this._selectedSearch(this.contractFilterFormGroup.get('globalKeyword').value);
  }

  private _selectedSearch(keyword) {
    this.store.dispatch(new SearchContractsCountAction(keyword));
  }

  closeSearchBadge(status, index) {
    if (status) {
      this.state.badges.splice(index, 1);
      this.store.dispatch(new PatchSearchStateAction({key: 'badges', value: this.state.badges}));
    }
  }

  enableExpertMode() {
    if (this.contractFilterFormGroup.get('switchValue').value) {
      this.store.dispatch(new PatchSearchStateAction({key: 'visibleSearch', value: false}));
      // this.state.visibleSearch = false;
      this._notifcationService.createNotification('Information',
        'the export mode is now enabled',
        'info', 'bottomRight', 2000);
    } else {
      this._notifcationService.createNotification('Information',
        'the export mode is now disabled',
        'info', 'bottomRight', 2000);
    }
  }

  private _clearFilters() {
    this.contractFilterFormGroup.patchValue({
      // globalKeyword: '',
      cedantCode: '',
      cedantName: '',
      countryName: '',
      innerCedantCode: '',
      innerCedantName: '',
      innerCountryName: '',
      innerWorkspaceId: '',
      innerWorkspaceName: '',
      innerYear: '',
      workspaceId: '',
      workspaceName: '',
      year: ''
    });
  }

  // private _globalSearch(item, keyword) {
  //   if (keyword == null || keyword == '')
  //     return true;
  //   return _.some(_.values(item), value => new String(value).toLowerCase().includes(new String(keyword).toLowerCase()));
  // }

  focusInput(event) {
    this._searchService.setvisibleDropdown( false );
    if (this.contractFilterFormGroup.get('switchValue').value) {
      this.store.dispatch(new PatchSearchStateAction([{key: 'showLastSearch', value: true}, {
        key: 'showResult',
        value: false
      }]));
    } else {
      if (event.target.value === '' || event.target.value.length < 2) {
        this.store.dispatch(new PatchSearchStateAction({key: 'showLastSearch', value: true}));
      } else {
        this.store.dispatch(new PatchSearchStateAction([{key: 'showResult', value: true}, {
          key: 'showLastSearch',
          value: false
        }]));
      }
    }
    this.store.dispatch(new PatchSearchStateAction({key: 'visible', value: false}));
  }

  onInput(event) {
    event.target.value === '' ? this.state.showClearIcon = false : this.state.showClearIcon = true;
    if (!this.contractFilterFormGroup.get('switchValue').value) {
      if (event.target.value === '' || event.target.value.length < 2) {
        this.store.dispatch(new PatchSearchStateAction([{key: 'showLastSearch', value: true}, {
          key: 'showResult',
          value: false
        }]));
      } else {
        this.store.dispatch(new PatchSearchStateAction({key: 'showLastSearch', value: false}));
        const searchExpression = this.contractFilterFormGroup.get('globalKeyword').value;
        this.examinateExpression(searchExpression);
        this.store.dispatch(new PatchSearchStateAction({key: 'showResult', value: true}));
      }
      this.store.dispatch(new PatchSearchStateAction({key: 'visibleSearch', value: true}));
    } else {
      this.store.dispatch(new PatchSearchStateAction({key: 'visibleSearch', value: false}));
    }
    this.store.dispatch(new PatchSearchStateAction({key: 'visible', value: false}));
  }

  openClose(): void {
    this.store.dispatch(new PatchSearchStateAction({key: 'visible', value: !this.state.visible}));
  }

  clearValue(): void {
    this.store.dispatch(new ClearSearchValuesAction);
    this._clearFilters();
  }
}
