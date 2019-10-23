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
import {debounceTime, takeUntil} from 'rxjs/operators';
import {NotificationService} from '../../../../shared/notification.service';
import {Router} from '@angular/router';
import * as SearchActions from '../../../store/actions/search-nav-bar.action';
import {Actions, ofActionDispatched, Store} from '@ngxs/store';
import {SearchNavBar} from '../../../model/search-nav-bar';
import * as _ from 'lodash';
import {Subject, Subscription} from "rxjs";
import {HelperService} from "../../../../shared/helper.service";


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

  searchShortCuts = [
    {name: "Cedant Name", shortcut: "c:"},
    {name: "Cedant Code", shortcut: "cid:"},
    {name: "Country", shortcut: "ctr:"},
    {name: "Year", shortcut: "uwy:"},
    {name: "Project", shortcut: "p:"},
    {name: "Workspace Name", shortcut: "w:"},
    {name: "Workspace Code", shortcut: "wid:"},
    {name: "PLT", shortcut: "plt:"},
    {name: "Section Name", shortcut: "s:"},
    {name: "UW Unit", shortcut: "uwu:"}
  ];

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


  @Input('state')
  set setState(value) {
    this.state = _.clone(value);
    this.calculateContractChoicesLength();
    this.detectChanges();
  }

  constructor(private _fb: FormBuilder, private _searchService: SearchService, private router: Router,
              private _notifcationService: NotificationService, private store: Store,
              private actions$: Actions, private cdRef: ChangeDetectorRef) {
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
  }

  ngOnInit() {
    this._subscribeGlobalKeywordChanges();
    this._subscribeToDistatchedEvents();
    this.contractFilterFormGroup.setValue({
      globalKeyword: '',
      expertModeToggle: true
    })
  }

  private calculateContractChoicesLength() {
    if (this.state.data && this.state.data.length > 0) {
      this.scrollParams.listLength = _.reduce(this.state.data, (sum, n) => {
        return sum + (n.length > 5 ? 5 : n.length);
      }, 0);
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
        this.store.dispatch(new SearchActions.SearchInputValueChange(this.isExpertMode, value))
        this._contractChoicesSearch(value);
      });
  }

  isSearchRoute() {
    return window.location.href.match('search');
  }

  onEnter(evt: KeyboardEvent) {
    evt.preventDefault();
    console.log(this.isExpertMode);
    if (this.isExpertMode) {
      this.store.dispatch(new SearchActions.ExpertModeSearchAction(this.convertBadgeToExpression(this.state.badges) + this.globalKeyword));
    } else {
      this.store.dispatch(new SearchActions.SearchAction(this.state.badges, this.globalKeyword));
    }
    // this.clearSearchValue();
  }

  onSpace(evt: KeyboardEvent) {
    if (this.scrollParams.position && this.scrollParams.scrollTo >= 0) {
      evt.preventDefault();
      let {i, j} = this.scrollParams.position;
      this.selectSearchBadge(HelperService.upperFirstWordsInSetence(this.state.tables[i]), this.state.data[i][j].label);
      this.scrollParams.scrollTo = -1;
    }
  }

  convertBadgeToExpression(badges) {
    let globalExpression = "";
    let expression;
    _.forEach(badges, badge => {

      switch (badge.key) {
        case "Cedant Name":
          expression = "c:" + badge.value;
          break;
        case "Cedant Code":
          expression = "cid:" + badge.value;
          break;
        case "Country":
          expression = "ctr:" + badge.value;
          break;
        case "Year":
          expression = "uwy:" + badge.value;
          break;
        case "Workspace Name":
          expression = "w:" + badge.value;
          break;
        case "Workspace Code":
          expression = "wid:" + badge.value;
          break;
        case "Project":
          expression = "p:" + badge.value;
          break;
        case "PLT":
          expression = "plt:" + badge.value;
          break;
        case "Section Name":
          expression = "s:" + badge.value;
          break;
        case "UW Unit":
          expression = "uwu:" + badge.value;
          break;
        default:
          expression = badge.value;
      }

      globalExpression += expression + " ";
    })
    return globalExpression;
  }

  onBackspace(evt: KeyboardEvent) {
    if (this.globalKeyword == '')
      this.store.dispatch(new SearchActions.DeleteLastBadgeAction());
  }

  onDelete(evt: KeyboardEvent) {
    this.store.dispatch(new SearchActions.DeleteAllBadgesAction());
  }

  selectSearchBadge(key, value) {
    key = HelperService.upperFirstWordsInSetence(key);
    this.contractFilterFormGroup.patchValue({globalKeyword: ''});
    this.store.dispatch(new SearchActions.SelectBadgeAction({key, value}, this.globalKeyword));
    this.searchInput.nativeElement.focus();
  }

  private _contractChoicesSearch(keyword) {
    if (keyword && keyword.length && this.state.visibleSearch)
      this.store.dispatch(new SearchActions.SearchContractsCountAction(keyword));
  }

  addBadgeFromResultList(key) {
    this.selectSearchBadge(HelperService.upperFirstWordsInSetence(key), this.state.actualGlobalKeyword);
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
    if (this.searchConfigPopInVisible)
      this.searchConfigPopInVisible = false;
    this._searchService.setvisibleDropdown(false);
    this.store.dispatch(new SearchActions.SearchInputFocusAction(this.isExpertMode, event.target.value));
    this.togglePopup();
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
      this.contractFilterFormGroup.patchValue({globalKeyword: this.convertBadgeToExpression([badge])});
    }
  }

  toggleInput(event: boolean, item) {
    console.log(event);
    this.inputDisabled = event;
    this.contractFilterFormGroup.patchValue({globalKeyword: item.key + ':' + item.value})
  }

  onKeyPress($event: KeyboardEvent) {
    if ($event.shiftKey && $event.ctrlKey && $event.code == "KeyS") {
      console.log('shift + ctrl + s');
      this.searchInput.nativeElement.focus();
      this.searchInput.nativeElement.click();
    }
  }
}
