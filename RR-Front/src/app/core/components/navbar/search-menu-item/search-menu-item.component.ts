import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  ElementRef,
  OnInit,
  ViewChild,
  Input,
  OnDestroy
} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SearchService} from '../../../service';
import {debounceTime} from 'rxjs/operators';
import {NotificationService} from '../../../../shared/notification.service';
import {Router} from '@angular/router';
import {
  ClearSearchValuesAction, CloseBadgeByIndexAction,
  DeleteAllBadgesAction,
  DeleteLastBadgeAction,
  DisableExpertMode,
  EnableExpertMode, ExpertModeSearchAction,
  PatchSearchStateAction,
  SearchContractsCountAction, SearchInputFocusAction, SearchInputValueChange,
  SelectBadgeAction, SearchAction
} from '../../../store/index';
import {Store, Actions, ofActionDispatched} from '@ngxs/store';
import {SearchNavBar} from '../../../model/search-nav-bar';
import * as _ from 'lodash';
import {Subscription} from "rxjs";


@Component({
  selector: 'search-menu-item',
  templateUrl: './search-menu-item.component.html',
  styleUrls: ['./search-menu-item.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchMenuItemComponent implements OnInit, OnDestroy {

  @ViewChild('searchInput')
  searchInput: ElementRef;
  contractFilterFormGroup: FormGroup;

  state: SearchNavBar = null;

  @Input('state')
  set setState(value) {
    this.state = _.clone(value);
    this.calculateContractChoicesLength();
    this.detectChanges();
  }

  subscriptions: Subscription = new Subscription();

  scrollTo: number;
  private listLength: number;
  private pos: any;

  constructor(private _fb: FormBuilder, private _searchService: SearchService, private router: Router,
              private _notifcationService: NotificationService, private store: Store,
              private actions$: Actions, private cdRef: ChangeDetectorRef) {
    this.contractFilterFormGroup = this._fb.group({
      expertModeToggle: [false],
      globalKeyword: ['']
    });
    this.scrollTo = -1;
    this.listLength = 0;
    this.pos = {
      i: 0,
      j: 0
    };
  }

  ngOnInit() {
    this._subscribeGlobalKeywordChanges();
    this._subscribeToDistatchedEvents();
  }

  private calculateContractChoicesLength() {
    if (this.state.data && this.state.data.length > 0) {
      this.listLength = _.reduce(this.state.data, (sum, n) => {
        return sum + (n.length > 5 ? 5 : n.length);
      }, 0);
    }
  }

  private _subscribeToDistatchedEvents() {
    this.actions$
      .pipe(ofActionDispatched(EnableExpertMode, DisableExpertMode))
      .subscribe(instance => {
        if (instance instanceof EnableExpertMode)
          this._notifcationService.createNotification('Information',
            'The expert mode is now enabled',
            'info', 'bottomRight', 2000);
        if (instance instanceof DisableExpertMode)
          this._notifcationService.createNotification('Information',
            'The expert mode is now disabled',
            'info', 'bottomRight', 2000);
      });
  }


  private _subscribeGlobalKeywordChanges() {
    this._unsubscribeToFormChanges();
    this.subscriptions= this.contractFilterFormGroup.get('globalKeyword')
      .valueChanges
      .pipe(debounceTime(500))
      .subscribe((value) => {
        this.store.dispatch(new SearchInputValueChange(this.isExpertMode, value))
        this._contractChoicesSearch(value);
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

  onEnter(evt: KeyboardEvent) {
    evt.preventDefault();
    if (this.isExpertMode) {
      this.store.dispatch(new ExpertModeSearchAction(this.globalKeyword));
    } else {
      this.store.dispatch(new SearchAction(this.state.badges, this.globalKeyword));
    }
    this.clearSearchValue();
  }

  onSpace(evt: KeyboardEvent) {
    if (this.pos && this.scrollTo >= 0) {
      this.selectSearchBadge(this.stringUpdate(this.state.tables[this.pos.i]), this.state.data[this.pos.i][this.pos.j].label);
      this.scrollTo = -1;
    }
  }

  onArrowUp(evt: KeyboardEvent) {
    evt.preventDefault();
    if (this.scrollTo > 0)
      this.scrollTo = this.scrollTo - 1;
  }

  onArrowDown(evt: KeyboardEvent) {
    evt.preventDefault();
    if (this.scrollTo < this.listLength)
      this.scrollTo = this.scrollTo + 1;
  }

  onBackspace(evt: KeyboardEvent) {
    if (this.globalKeyword == '')
      this.store.dispatch(new DeleteLastBadgeAction());
  }

  onDelete(evt: KeyboardEvent) {
    this.store.dispatch(new DeleteAllBadgesAction());
  }


  selectSearchBadge(key, value) {
    this.contractFilterFormGroup.patchValue({globalKeyword: ''});
    this.store.dispatch(new SelectBadgeAction({key, value}, this.globalKeyword));
    this.searchInput.nativeElement.focus();
  }

  private _contractChoicesSearch(keyword) {
    if (keyword && keyword.length && this.state.visibleSearch)
      this.store.dispatch(new SearchContractsCountAction(keyword));
  }

  addBadgeFromResultList(key) {
    this.selectSearchBadge(this.stringUpdate(key), this.state.actualGlobalKeyword);
  }

  closeSearchBadge(status, index) {
    this.store.dispatch(new CloseBadgeByIndexAction(index, this.isExpertMode));
  }

  expertModeChange() {
    this.store.dispatch(this.isExpertMode ? new EnableExpertMode() : new DisableExpertMode());
  }

  appendSearchBadges(items) {
    this.store.dispatch(new PatchSearchStateAction({key: 'badges', value: items}));
    this.router.navigate(['/search']);
  }


  selectSearchAndRedirect(items){
    this.appendSearchBadges(items);
    this.onEnter(event as any);
  }

  clearValue(): void {
    this.store.dispatch(new ClearSearchValuesAction());
  }

  focusInput(event) {
    this._searchService.setvisibleDropdown(false);
    this.store.dispatch(new SearchInputFocusAction(this.isExpertMode, event.target.value));
  }

  openClose(): void {
    this.store.dispatch(new PatchSearchStateAction([{key: 'visibleSearch', value: false}, {
      key: 'visible',
      value: !this.state.visible
    }]));
  }

  setPos($event) {
    this.pos = $event;
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

  private clearSearchValue(){
    this._unsubscribeToFormChanges();
    this.contractFilterFormGroup.get('globalKeyword').patchValue('');
    this._subscribeGlobalKeywordChanges();
  }

  private _unsubscribeToFormChanges(){
    this.subscriptions ? this.subscriptions.unsubscribe(): null;
  }

  ngOnDestroy(): void {
    this._unsubscribeToFormChanges();
  }

}
