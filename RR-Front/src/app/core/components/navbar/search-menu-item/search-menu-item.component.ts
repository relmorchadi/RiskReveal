import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  ViewChild
} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SearchService} from '../../../service';
import { first, takeUntil} from 'rxjs/operators';
import {NotificationService} from '../../../../shared/notification.service';
import {Router} from '@angular/router';
import * as SearchActions from '../../../store/actions/search-nav-bar.action';
import {closeSearch, showSavedSearch} from '../../../store/actions/search-nav-bar.action';
import {Actions, Select, Store} from '@ngxs/store';
import {SearchNavBar} from '../../../model/search-nav-bar';
import * as _ from 'lodash';
import {Subject, Subscription} from "rxjs";
import {HelperService} from "../../../../shared/helper.service";
import {BadgesService } from "../../../service/badges.service";
import {ShortCut} from "../../../model/shortcut.model";
import {DashboardState, SearchNavBarState} from "../../../store/states";
import {BaseContainer} from "../../../../shared/base";
import {DashboardApi} from "../../../service/api/dashboard.api";
import {PatchSearchStateAction} from "../../../store/actions/search-nav-bar.action";


@Component({
  selector: 'search-menu-item',
  templateUrl: './search-menu-item.component.html',
  styleUrls: ['./search-menu-item.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  host: {
    '(document:keydown)': 'onKeyPress($event)'
  }
})
export class SearchMenuItemComponent extends BaseContainer implements OnInit, OnDestroy {

  readonly componentName: string = 'search-pop-in';

  searchShortCuts: ShortCut[];

  @ViewChild('searchInput') searchInput: ElementRef;

  @Select(DashboardState.getSelectedDashboard) selectedDashboard$;

  contractFilterFormGroup: FormGroup;
  subscriptions: Subscription;
  scrollParams;
  state: SearchNavBar = null;
  private unSubscribe$: Subject<void>;
  searchMode: string = null;
  mainSearchMode: string = 'Fac';
  resetToDash = false;
  searchConfigPopInVisible: boolean = false;
  inputDisabled: boolean = true;
  mapTableNameToBadgeKey: any;
  showListOfShortCuts: boolean;
  possibleShortCuts: {shortCutLabel: any, type: any}[];

  @Input('state')
  set setState(value) {
    this.state = _.clone(value);
    this.detectChanges();
  }

  constructor(private _fb: FormBuilder, private _searchService: SearchService, private router: Router,
              private _notifcationService: NotificationService, private store: Store,
              private actions$: Actions, private cdRef: ChangeDetectorRef,
              private badgeService: BadgesService, _baseStore: Store,
              _baseRouter: Router, _baseCdr: ChangeDetectorRef
  ) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.contractFilterFormGroup = this._fb.group({
      globalKeyword: ['']
    });
    this.subscriptions = new Subscription();
    this.unSubscribe$ = new Subject<void>();
    HelperService.headerBarPopinChange$.subscribe(({from}) => {
      if (from != this.componentName)
        this.store.dispatch(new SearchActions.CloseSearchPopIns());
    });
    this.showListOfShortCuts = false;
    this.possibleShortCuts = [];
  }

  ngOnInit() {
    this.selectedDashboard$.pipe().subscribe(value => {
      this.searchMode = _.get(value, 'searchMode', null);
      this.resetToDash = this.searchMode !== null;
      this.resetToDash ? this.mainSearchMode = this.searchMode : null;
      this.dispatch(new PatchSearchStateAction({key: 'searchTarget', value: this.useAlternative()}));
      this.badgeService.initShortCuts(this.searchShortCuts, this.useAlternative());
      this.detectChanges();
    });

    this.store.select(SearchNavBarState.getShortCuts).subscribe( shortCuts => {
      this.searchShortCuts = this.updateShortCuts(shortCuts);
      this.badgeService.initShortCuts(this.searchShortCuts, this.useAlternative());
      this.detectChanges();
    });

    this.store.select(SearchNavBarState.getMapTableNameToBadgeKey).pipe(first(v => v)).subscribe( mapTableNameToBadgeKey => {
      this.mapTableNameToBadgeKey = mapTableNameToBadgeKey;
      console.log(mapTableNameToBadgeKey);
      this.detectChanges();
    });

    this._subscribeGlobalKeywordChanges();
  }

  private updateShortCuts = _.memoize((shortCuts) => {
    return _.map(shortCuts,({shortCutLabel, shortCutAttribute, mappingTable, type}) =>
        new ShortCut(shortCutLabel, shortCutAttribute, mappingTable, type));
  });

  private _subscribeGlobalKeywordChanges() {
    this._unsubscribeToFormChanges();
    this.subscriptions = this.contractFilterFormGroup.get('globalKeyword')
      .valueChanges
      .pipe(takeUntil(this.unSubscribe$))
      .subscribe((value) => {
        value && value.length > 1 && this.updatePossibleShortCuts(value);
        this.store.dispatch(new SearchActions.SearchInputValueChange(value));
        this._contractChoicesSearch(value);
      });
  }

  updatePossibleShortCuts(expr) {
    if( !_.includes(expr, ":")) {
      this.possibleShortCuts = _.map(_.filter(this.searchShortCuts, shortCut => _.includes(_.toLower(shortCut.shortCutLabel), _.toLower(expr))), shortCut => ({shortCutLabel :shortCut.shortCutLabel, type: shortCut.type}));
    } else {
      this.possibleShortCuts = [];
    }
    this.showListOfShortCuts = this.possibleShortCuts.length > 0;
  }

  isSearchRoute() {
    return window.location.href.match('search');
  }

  onDoublePoints = _.debounce(($event: KeyboardEvent) => {
    // console.log(this.badgeService.transformKeyword(this.globalKeyword));
    this.contractFilterFormGroup.get('globalKeyword').patchValue(this.badgeService.transformKeyword(this.globalKeyword, this.useAlternative()));
  }, 350);

  onEnter(evt) {
    evt.preventDefault();
    const globalKeywordBadge = this.convertExpressionToBadge(this.globalKeyword);
    const expr = this.convertBadgeToExpression(globalKeywordBadge ? [...this.state.badges, globalKeywordBadge ] : this.state.badges);
    this.store.dispatch(new SearchActions.ExpertModeSearchAction(expr));
    this.contractFilterFormGroup.get('globalKeyword').patchValue('');
  }

  onBackspace(evt: KeyboardEvent) {
    if (this.globalKeyword == '')
      this.store.dispatch(new SearchActions.DeleteLastBadgeAction());
  }

  onDelete(evt: KeyboardEvent) {
    this.store.dispatch(new SearchActions.DeleteAllBadgesAction());
  }

  onKeyPress($event: KeyboardEvent) {
    if ($event.shiftKey && $event.ctrlKey && $event.code == "KeyS") {
      this.searchInput.nativeElement.focus();
      this.searchInput.nativeElement.click();
    }
  }

  convertBadgeToExpression(badges) {
    let expression = "";
    let globalExprLength = 0;
    let index;
    console.log(badges);
    _.forEach(badges, (badge, i: number) => {
      if(! (badge.key == "global search") ) {
        index = this.searchShortCuts.findIndex(row => {
          return row.shortCutLabel == badge.key;
        });
        if(i == badges.length - 1) {
          expression += this.searchShortCuts[index].shortCutLabel + ":" + badge.value;
        } else {
          expression += this.searchShortCuts[index].shortCutLabel + ":" + badge.value + " ";
        }
      } else {
        expression = expression.substring(globalExprLength);
        globalExprLength = badge.value.length;
        expression = badge.value + " " + expression;
      }
    });
    return expression;
  }

  convertExpressionToBadge(expression) {
    const foundShortCut = _.find(this.searchShortCuts, shortCut => _.includes(expression, shortCut.shortCutLabel));
    return foundShortCut ? { key: foundShortCut.shortCutLabel, operator: "LIKE", value: expression.substring(foundShortCut.shortCutLabel.length + 1) } : ( expression ? { key: 'global search', operator: "LIKE", value: expression } : null);
  }

  selectSearchBadge(key, value) {
    event.preventDefault();
    this.contractFilterFormGroup.patchValue({globalKeyword: ''});
    this.store.dispatch(new SearchActions.SelectBadgeAction({key, value}, this.globalKeyword, this.useAlternative()));
    this.searchInput.nativeElement.focus();
  }

  private _contractChoicesSearch(keyword) {
    if (keyword && keyword.length && this.state.visibleSearch)
      this.store.dispatch(new SearchActions.SearchContractsCountAction({keyword, searchMode: this.useAlternative()}));
  }

  addBadgeFromResultList(key) {
    this.selectSearchBadge(this.mapTableNameToBadgeKey[key], this.state.actualGlobalKeyword);
  }

  closeSearchBadge(status, index) {
    this.store.dispatch(new SearchActions.CloseBadgeByIndexAction(index));
  }

  appendSearchBadges(items, globalKeyword) {
    this.store.dispatch(new SearchActions.PatchSearchStateAction({key: 'badges', value: _.map(items, e => e.key == 'global search' ? e : ({...e, key: _.upperFirst(e.key)}))}));
    this.store.dispatch(new SearchActions.PatchSearchStateAction([{key : 'actualGlobalKeyword', value: globalKeyword}]));
    this.searchInput.nativeElement.focus();
  }

  selectSearchAndRedirect(items, globalKeyword) {
    this.appendSearchBadges(items, globalKeyword);
    this.onEnter(event as any);
  }

  focusInput(event) {
    if(!this.state.visibleSearch) {
      if (this.searchConfigPopInVisible) this.searchConfigPopInVisible = false;
      this._searchService.setvisibleDropdown(false);
      this.store.dispatch(new SearchActions.SearchInputFocusAction(event.target.value));
      this.togglePopup();
    }
  }

  openClose(): void {
    if (this.searchConfigPopInVisible)
      this.searchConfigPopInVisible = false;
    if (this.state.visibleSearch)
      this.store.dispatch(new SearchActions.PatchSearchStateAction([{key: 'visibleSearch', value: false}, {
        key: 'visible',
        value: !this.state.visible
      }]));
    this.togglePopup();
  }

  filterBySearchMode(data) {
    return _.filter(data, item => item.type === (this.useAlternative() === 'Treaty' ? 'TTY' : 'FAC'));
  }

  get globalKeyword() {
    return this.contractFilterFormGroup.get('globalKeyword').value;
  }

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
  }

  private clearSearchValue() {
    this._unsubscribeToFormChanges();
    this.contractFilterFormGroup.get('globalKeyword').patchValue('');
    this._subscribeGlobalKeywordChanges();
    this.store.dispatch(new SearchActions.ClearSearchValuesAction());
    this.searchInput.nativeElement.focus();
  }

  private _unsubscribeToFormChanges() {
    this.subscriptions ? this.subscriptions.unsubscribe() : null;
  }

  ngOnDestroy(): void {
    this.unSubscribe$.next();
    this.unSubscribe$.complete();
  }

  togglePopup() {
    HelperService.headerBarPopinChange$.next({from: this.componentName});
  }

  onChangeTagValue(badge) {
    let index = _.findIndex(this.state.badges, row => row.key == badge.key);
    this.store.dispatch(new SearchActions.CloseBadgeByIndexAction(index));
    if (this.globalKeyword.length > 0) {
      this.store.dispatch(new SearchActions.SelectBadgeAction(this.convertExpressionToBadge(this.globalKeyword), this.globalKeyword, this.useAlternative()));
    }
    this.contractFilterFormGroup.patchValue({globalKeyword: this.convertBadgeToExpression([badge])});
  }

  toggleInput(event: boolean, item) {
    console.log(event);
    this.inputDisabled = event;
    this.contractFilterFormGroup.patchValue({globalKeyword: item.key + ':' + item.value})
  }

  seeAllSavedSearch() {
    if (this.router.url == '/search') {
      this.store.dispatch(new showSavedSearch())
    } else {
      this.router.navigate(["/search"]);
      this.store.dispatch(new showSavedSearch())
    }
    this.cdRef.detectChanges();
  }

  closeSearch() {
    if (!this.state.visibleSearch) {
      this.store.dispatch(new closeSearch())
    }
  }

  replaceExpressionWithShortCut(possibleShortCut: string) {
    event.preventDefault();
    this.contractFilterFormGroup.get('globalKeyword').patchValue(possibleShortCut + ":");
  }

  dispatchSearchMode($event) {
    this.resetToDash = false;
    this.dispatch(new PatchSearchStateAction({key: 'searchTarget', value: $event}));
    this.badgeService.initShortCuts(this.searchShortCuts, $event);
  }

  useAlternative() {
    return this.resetToDash ? this.searchMode : this.mainSearchMode;
  }
}
