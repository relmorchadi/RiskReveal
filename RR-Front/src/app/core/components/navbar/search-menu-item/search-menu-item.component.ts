import {ChangeDetectionStrategy, ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SearchService} from '../../../service/search.service';
import {debounceTime} from 'rxjs/operators';
import {NotificationService} from '../../../../shared/notification.service';
import {Router} from '@angular/router';
import {
  AddBadgeSearchStateAction, ClearSearchValuesAction, LoadRecentSearchAction,
  PatchSearchStateAction, SearchContractsCountAction
} from '../../../store/index';
import {Select, Store} from '@ngxs/store';
import {SearchNavBar} from '../../../model/search-nav-bar';
import * as _ from 'lodash';
import {Observable} from 'rxjs';
import {SearchNavBarState} from '../../../store/index';


@Component({
  selector: 'search-menu-item',
  templateUrl: './search-menu-item.component.html',
  styleUrls: ['./search-menu-item.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchMenuItemComponent implements OnInit {

  @ViewChild('searchInput')
  searchInput: ElementRef;
  contractFilterFormGroup: FormGroup;

  @Select(SearchNavBarState)
  state$: Observable<SearchNavBar>;
  state: SearchNavBar = null;
  loading: any;
  scrollTo: number;
  private listLength: number;
  private pos:any;


  constructor(private _fb: FormBuilder, private _searchService: SearchService, private router: Router,
              private _notifcationService: NotificationService, private store: Store, private cdRef: ChangeDetectorRef) {
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
    this.scrollTo = -1;
    this.listLength = 0;
    this.pos = {
      i: 0,
      j: 0
    };
  }


  ngOnInit() {
    this.state$.subscribe(value => {
      if (value.data && _.every(value.data, d => d && d.length == 0)) {
        this.state = _.merge({}, {
          ...value,
          data: []
        });

      } else {
        this.state = _.merge({}, value);
      }
      if (this.state.data && this.state.data.length > 0) {
        this.listLength = _.reduce(this.state.data, (sum, n) => {
          return sum + (n.length > 5 ? 5 : n.length);
        }, 0);

      }
      this.detectChanges();
    });
    this._subscribeGlobalKeywordChanges();
    this.store.dispatch(new LoadRecentSearchAction());
    this.contractFilterFormGroup.get('globalKeyword').valueChanges.pipe(debounceTime(500))
      .subscribe(value => {
        this.store.dispatch(new PatchSearchStateAction({key: 'searchValue', value: value}));
        this.detectChanges();
      });
    this.store.select(st => st.searchBar.data).subscribe(dt => {
      this.detectChanges();
    });

    this.store.select(SearchNavBarState.getLoadingState).subscribe( l => {
      this.loading = l;
      this.detectChanges();
    });
  }

  private _subscribeGlobalKeywordChanges() {
    this.contractFilterFormGroup.get('globalKeyword')
      .valueChanges
      .pipe(debounceTime(500))
      .subscribe((param) => {
        this._selectedSearch(param);
      });
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
      const regExp = /(\w*:){1}(((\w|\")*\s)*)/g;
      const globalKeyword = `${expression} `.replace(regExp, (match, shortcut, keyword) => {
        // console.log({shortcut, keyword});
        this._searchService.keyword = expression.trim().split(" ")[0];
        if (this._searchService.keyword.indexOf(':') > -1) this._searchService.keyword = null;
        let field = this.state.sortcutFormKeysMapper[_.trim(shortcut, ':')];
        this._searchService.expertModeFilter.push({
          field : this.state.sortcutFormKeysMapper[_.trim(shortcut, ':')],
          value: _.trim(_.trim(_.trim(keyword),'"')),
          operator: this.getOperator(_.trim(keyword), field)});
        return this.toBadges(_.trim(shortcut, ':'), _.trim(keyword));
      }).trim();
      if (this._searchService.keyword)
        setTimeout(() => this._searchService.addSearchedItems({key: 'Global Search', value: this._searchService.keyword}));
      this.store.dispatch(new PatchSearchStateAction({key: 'actualGlobalKeyword', value: globalKeyword}));
    } else {
      this.store.dispatch(new PatchSearchStateAction({key: 'actualGlobalKeyword', value: expression}));
    }
  }

  toBadges(shortcut, keyword) {
    const correspondingKey: string = this.state.sortcutFormKeysMapper[shortcut];
    if (correspondingKey) {
      this.contractFilterFormGroup.get(correspondingKey).patchValue(keyword);
      const instance = {key: this.stringUpdate(correspondingKey), value: keyword};
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

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
  }

  filterContracts(keyboardEvent) {
    this._clearFilters();
    if (keyboardEvent.key === ' ' && this.scrollTo >= 0) {
      if (this.pos) {
        this.selectSearchBadge(this.stringUpdate(this.state.tables[this.pos.i]), this.state.data[this.pos.i][this.pos.j].label );
        this.scrollTo = -1;
      }
    }
    if (keyboardEvent.key === 'Enter') {
      this._searchService.expertModeFilter = [];
      this._searchService.resetSearchedItems();
      const searchExpression = this.contractFilterFormGroup.get('globalKeyword').value;
      if (this.contractFilterFormGroup.get('switchValue').value) {
        this.examinateExpression(searchExpression);
      } else {
        this._searchService.globalSearchItem = searchExpression;
      }
      event.preventDefault();
      this.redirectToSearchPage();
    } else if (keyboardEvent.key === 'Delete' && keyboardEvent.target.value === '') {
      this.state.badges.pop();
      this.store.dispatch(new PatchSearchStateAction({key: 'badges', value: this.state.badges}));
    }
    if (this.state.deleteBlock === true) {
      if (keyboardEvent.key === 'Backspace' && keyboardEvent.target.value === '') {
        this.state.deleteBlock = false;
        this.store.dispatch(new PatchSearchStateAction({key: 'deleteBlock', value: false}));
      }
    } else {
      if (keyboardEvent.key === 'Backspace' && keyboardEvent.target.value === '') {
        this.state.badges.pop();
        this.store.dispatch(new PatchSearchStateAction({key: 'badges', value: this.state.badges}));
        this.state.deleteBlock = true;
        this.store.dispatch(new PatchSearchStateAction({key: 'deleteBlock', value: true}));
      }
    }
  }

  redirectToSearchPage() {
    this._openWorkspaceIfSuffisantBadges();
    this._searchService.setvisibleDropdown( false );
    if (this.state.badges.length > 0) {
      this.store.dispatch(new PatchSearchStateAction({
        key: 'recentSearch',
        value: _.uniqWith([[...this.state.badges], ...this.state.recentSearch].slice(0, 5), _.isEqual)
      }));
      this._searchService.affectItems([...this.state.badges]);
      localStorage.setItem('items', JSON.stringify(this.state.recentSearch));
    }
    this.router.navigate(['/search']);
  }

  private _openWorkspaceIfSuffisantBadges(): boolean {
    let yearBadge = this.state.badges.find(badge => badge.key == 'Year');
    let workpaceNameBadge = this.state.badges.find(badge => badge.key == 'Workspace Id');
    if (yearBadge && workpaceNameBadge) {
      window.open(`/workspace/${workpaceNameBadge.value}/${yearBadge.value}`);
      return true;
    }
    return false;
  }


  redirectWithSearch(items) {
    this._searchService.affectItems(items);
    this.store.dispatch(new PatchSearchStateAction({key: 'badges', value: items}));
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
    this.searchInput.nativeElement.focus();
    this._selectedSearch(this.contractFilterFormGroup.get('globalKeyword').value);
  }

  private _selectedSearch(keyword) {
    if (keyword && keyword.length)
      this.store.dispatch(new SearchContractsCountAction(keyword));
  }

  addBadgeFromResultList(key) {
    this.selectSearchBadge(this.stringUpdate(key), this.state.actualGlobalKeyword );
  }

  closeSearchBadge(status, index) {
    if (status) {
      if (this._searchService.expertModeEnabled) {
        this.state.badges.splice(index, 1);
        this._searchService.expertModeFilter.splice(index, 1);
      } else {
        const badges = _.toArray(_.omit(this.state.badges, index));
        this.store.dispatch(new PatchSearchStateAction({key: 'badges', value: badges}));
      }
    }
  }

  enableExpertMode() {
    if (this.contractFilterFormGroup.get('switchValue').value) {
      this.store.dispatch(new PatchSearchStateAction({key: 'visibleSearch', value: false}));
      // this.state.visibleSearch = false;
      this._searchService.expertModeEnabled = true;
      this._notifcationService.createNotification('Information',
        'the expert mode is now enabled',
        'info', 'bottomRight', 2000);
    } else {
      this._searchService.expertModeEnabled = false;
      this._notifcationService.createNotification('Information',
        'the expert mode is now disabled',
        'info', 'bottomRight', 2000);
    }
  }

  clearValue(): void {
    this.store.dispatch(new ClearSearchValuesAction());
    this._clearFilters();
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
    this.store.dispatch(new PatchSearchStateAction([{key: 'visibleSearch', value: true}, {key: 'visible', value: false}]));
  }

  onInput(event)  {
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
    this.store.dispatch(new PatchSearchStateAction([{key: 'visibleSearch', value: false}, {key: 'visible', value: !this.state.visible}]));
  }

  handleScroll($event: KeyboardEvent) {
    // console.log($event);
  }


  setPos($event) {
    this.pos = $event;
  }

  getOperator(str: string, field: string) {
    if (str.endsWith('\"') && str.indexOf('\"') === 0) {
      return 'EQUAL';
    } else { return 'LIKE'; }
  }
}
