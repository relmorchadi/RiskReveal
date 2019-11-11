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
import {debounceTime, distinctUntilChanged, first, takeUntil} from 'rxjs/operators';
import {NotificationService} from '../../../../shared/notification.service';
import {Router} from '@angular/router';
import * as SearchActions from '../../../store/actions/search-nav-bar.action';
import {closeSearch, showSavedSearch} from '../../../store/actions/search-nav-bar.action';
import {Actions, ofActionDispatched, Store} from '@ngxs/store';
import {SearchNavBar} from '../../../model/search-nav-bar';
import * as _ from 'lodash';
import {Subject, Subscription} from "rxjs";
import {HelperService} from "../../../../shared/helper.service";
import {BadgesService } from "../../../service/badges.service";
import {ShortCut} from "../../../model/shortcut.model";
import {SearchNavBarState} from "../../../store/states";


@Component({
  selector: 'search-menu-item',
  templateUrl: './search-menu-item.component.html',
  styleUrls: ['./search-menu-item.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  host: {
    '(document:keydown)': 'onKeyPress($event)'
  }
})
export class SearchMenuItemComponent implements OnInit, OnDestroy {

  readonly componentName: string = 'search-pop-in';

  searchShortCuts: ShortCut[];

  @ViewChild('searchInput')
  searchInput: ElementRef;
  contractFilterFormGroup: FormGroup;
  subscriptions: Subscription;
  scrollParams;
  state: SearchNavBar = null;
  private unSubscribe$: Subject<void>;
  searchMode: string = 'Treaty';
  searchConfigPopInVisible: boolean = false;
  inputDisabled: boolean = true;
  mapTableNameToBadgeKey: any;
  showListOfShortCuts: boolean;
  possibleShortCuts: string[];

  @Input('state')
  set setState(value) {
    this.state = _.clone(value);
    this.calculateContractChoicesLength();
    this.detectChanges();
  }

  constructor(private _fb: FormBuilder, private _searchService: SearchService, private router: Router,
              private _notifcationService: NotificationService, private store: Store,
              private actions$: Actions, private cdRef: ChangeDetectorRef,
              private badgeService: BadgesService
  ) {
    this.contractFilterFormGroup = this._fb.group({
      expertModeToggle: [false],
      globalKeyword: ['']
    });
    this.subscriptions = new Subscription();
    this.scrollParams = {scrollTo: -1, listLength: 0, position: {i: 0, j: 0}};
    this.unSubscribe$ = new Subject<void>();
    HelperService.headerBarPopinChange$.subscribe(({from}) => {
      if (from != this.componentName)
        this.store.dispatch(new SearchActions.CloseSearchPopIns());
    });
    this.showListOfShortCuts = false;
    this.possibleShortCuts= [];
  }

  ngOnInit() {

    this.store.select(SearchNavBarState.getShortCuts).subscribe( shortCuts => {
      this.searchShortCuts = this.updateShortCuts(shortCuts);
      this.detectChanges();
    });

    this.store.select(SearchNavBarState.getMapTableNameToBadgeKey).pipe(first(v => v)).subscribe( mapTableNameToBadgeKey => {
      this.mapTableNameToBadgeKey = mapTableNameToBadgeKey;
      console.log(mapTableNameToBadgeKey);
      this.detectChanges();
    });

    this._subscribeGlobalKeywordChanges();
    this._subscribeToDistatchedEvents();
    this.contractFilterFormGroup.setValue({
      globalKeyword: '',
      expertModeToggle: true
    })
  }

  private updateShortCuts = _.memoize((shortCuts) => {
    return _.map(shortCuts,({shortCutLabel, shortCutAttribute, mappingTable}) => new ShortCut(shortCutLabel, shortCutAttribute, mappingTable));
  });

  private calculateContractChoicesLength() {
    if (this.state.data && this.state.data.length > 0) {
      this.scrollParams.listLength = _.reduce(this.state.data, (sum, n) => {
        return sum + (n.length > 5 ? 5 : n.length);
      }, 0) + this.possibleShortCuts.length;
    } else {
      this.scrollParams.listLength = this.possibleShortCuts.length;
    }
  }

  private _subscribeToDistatchedEvents() {
    this.actions$
      .pipe(
        takeUntil(this.unSubscribe$),
        ofActionDispatched(SearchActions.EnableExpertMode, SearchActions.DisableExpertMode))
      .subscribe(instance => {
        if (instance instanceof SearchActions.EnableExpertMode)
          this._notifcationService.createNotification('Information',
            'The expert mode is now enabled',
            'info', 'bottomRight', 2000);
        if (instance instanceof SearchActions.DisableExpertMode)
          this._notifcationService.createNotification('Information',
            'The expert mode is now disabled',
            'info', 'bottomRight', 2000);
      });
  }


  private _subscribeGlobalKeywordChanges() {
    this._unsubscribeToFormChanges();
    this.subscriptions = this.contractFilterFormGroup.get('globalKeyword')
      .valueChanges
      .pipe(takeUntil(this.unSubscribe$), debounceTime(500))
      .subscribe((value) => {
        value && value.length > 1 && this.updatePossibleShortCuts(value);
        this.store.dispatch(new SearchActions.SearchInputValueChange(this.isExpertMode, value))
        this._contractChoicesSearch(value);
      });
  }

  updatePossibleShortCuts(expr) {
    if( !_.includes(expr, ":")) {
      this.possibleShortCuts = _.map(_.filter(this.searchShortCuts, shortCut => _.includes(_.toLower(shortCut.shortCutLabel), _.toLower(expr))), shortCut => shortCut.shortCutLabel);
    } else {
      this.possibleShortCuts = [];
    }
    this.showListOfShortCuts = this.possibleShortCuts.length > 0;
  }

  isSearchRoute() {
    return window.location.href.match('search');
  }

  onDoublePoints = _.debounce(($event: KeyboardEvent) => {
    this.contractFilterFormGroup.get('globalKeyword').patchValue(this.badgeService.transformKeyword(this.globalKeyword));
  }, 350);

  onEnter(evt: KeyboardEvent) {
    evt.preventDefault();
    this.store.dispatch(new SearchActions.ExpertModeSearchAction(this.convertBadgeToExpression(this.state.badges) + " " + this.globalKeyword));
    this.contractFilterFormGroup.get('globalKeyword').patchValue('');
  }

  onSpace(evt: KeyboardEvent) {
    if (this.scrollParams.position && this.scrollParams.scrollTo >= 0) {
      evt.preventDefault();
      let {i, j} = this.scrollParams.position;
      //this.selectSearchBadge(this.mapTableNameToBadgeKey[this.state.tables[i]], this.state.data[i][j].label);
      this.scrollParams.scrollTo = -1;
    }
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
    let globalExpression = "";
    let index;
    _.forEach(badges, (badge, i: number) => {
      index = this.searchShortCuts.findIndex(row => {
        return row.shortCutLabel == badge.key;
      });
      if(i == badges.length - 1) {
        globalExpression += this.searchShortCuts[index].shortCutLabel + ":" + badge.value;
      } else {
        globalExpression += this.searchShortCuts[index].shortCutLabel + ":" + badge.value + " ";
      }
    });
    return globalExpression;
  }

  convertExpressionToBadge(expression) {
    const foundShortCut = _.find(this.searchShortCuts, shortCut => _.includes(expression, shortCut.shortCutLabel));
    return foundShortCut ? { key: foundShortCut.shortCutLabel, value: expression.substring(foundShortCut.shortCutLabel.length + 1) } : null;
  }

  selectSearchBadge(key, value) {
    event.preventDefault();
    this.contractFilterFormGroup.patchValue({globalKeyword: ''});
    this.store.dispatch(new SearchActions.SelectBadgeAction({key, value}, this.globalKeyword));
    this.searchInput.nativeElement.focus();
  }

  private _contractChoicesSearch(keyword) {
    if (keyword && keyword.length && this.state.visibleSearch)
      this.store.dispatch(new SearchActions.SearchContractsCountAction(keyword));
  }

  addBadgeFromResultList(key) {
    this.selectSearchBadge(this.mapTableNameToBadgeKey[key], this.state.actualGlobalKeyword);
  }

  closeSearchBadge(status, index) {
    this.store.dispatch(new SearchActions.CloseBadgeByIndexAction(index, this.isExpertMode));
  }

  expertModeChange() {
    this.store.dispatch(this.isExpertMode ? new SearchActions.EnableExpertMode() : new SearchActions.DisableExpertMode());
  }

  appendSearchBadges(items) {
    this.store.dispatch(new SearchActions.PatchSearchStateAction({key: 'badges', value: items}));
    this.searchInput.nativeElement.focus();
  }

  selectSearchAndRedirect(items) {
    this.appendSearchBadges(items);
    this.onEnter(event as any);
  }

  focusInput(event) {
    if(!this.state.visibleSearch) {
      if (this.searchConfigPopInVisible) this.searchConfigPopInVisible = false;
      this._searchService.setvisibleDropdown(false);
      this.store.dispatch(new SearchActions.SearchInputFocusAction(this.isExpertMode, event.target.value));
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

  get isExpertMode() {
    return this.contractFilterFormGroup.get('expertModeToggle').value;
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
    if (this.isExpertMode) {
      let index = _.findIndex(this.state.badges, row => row.key == badge.key);
      this.store.dispatch(new SearchActions.CloseBadgeByIndexAction(index, this.isExpertMode));
      if (this.globalKeyword.length > 0) {
        this.store.dispatch(new SearchActions.SelectBadgeAction(this.convertExpressionToBadge(this.globalKeyword), this.globalKeyword));
      }
      this.contractFilterFormGroup.patchValue({globalKeyword: this.convertBadgeToExpression([badge])});
    }

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
}
