import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SearchService} from '../../../core/service/search.service';
import {debounceTime} from 'rxjs/operators';
import {HelperService} from '../../../shared/helper.service';
import {Router} from '@angular/router';
import {Location} from '@angular/common';
import * as _ from 'lodash';
import {LazyLoadEvent} from 'primeng/api';
import {Select, Store} from '@ngxs/store';
import {
  AppendNewWorkspaceMainAction,
  PatchWorkspaceMainStateAction
} from '../../../core/store/actions/workspace-main.action';
import {WorkspaceMainState} from '../../../core/store/states';
import * as fromWS from '../../../core/store';
import {Observable} from 'rxjs';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import {PatchSearchStateAction} from '../../../core/store';


@Component({
  selector: 'app-search-main',
  templateUrl: './search-main.component.html',
  styleUrls: ['./search-main.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchMainComponent implements OnInit {
  @ViewChild('dt') table;

  contractFilterFormGroup: FormGroup;
  expandWorkspaceDetails = false;
  contracts = [];
  paginationOption = {page: 0, size: 40, total: '-'};
  selectedWorkspace: any;
  loadingMore = false;
  sliceValidator = true;
  searchedItems = [];
  currentPage = 0;
  globalSearchItem = '';
  currentWorkspace = null;
  loading = true;
  columns = [
    {
      field: 'checkbox',
      header: '',
      width: '20px',
      display: true,
      sorted: false,
      filtered: false,
      type: 'checkbox',
      class: 'icon-check_24px',
    },
    {
      field: 'countryName',
      header: 'Country',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'innerCountryName'
    },
    {
      field: 'cedantName',
      header: 'Cedent Name',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'innerCedantName'
    },
    {
      field: 'cedantCode',
      header: 'Cedant Code',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'innerCedantCode'
    },
    {
      field: 'uwYear',
      header: 'Uw Year',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'innerYear'
    },
    {
      field: 'workspaceName',
      header: 'Workspace Name',
      width: '160px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'innerWorkspaceName'
    },
    {
      field: 'workSpaceId',
      header: 'Workspace Context',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'innerWorkspaceId'
    },
    {
      field: 'openInHere',
      header: '',
      width: '20px',
      type: 'icon',
      class: 'icon-fullscreen_24px',
      handler: (option) => option.forEach(dt => this.openWorkspace(dt.workSpaceId, dt.uwYear)),
      display: false,
      sorted: false,
      filtered: false
    },
    {
      field: 'openInPopup',
      header: '',
      width: '20px',
      type: 'icon',
      class: 'icon-open_in_new_24px',
      handler: (option) => option.forEach(dt => this.popUpWorkspace(dt.workSpaceId, dt.uwYear)),
      display: false,
      sorted: false,
      filtered: false
    }
  ];
  @Select(WorkspaceMainState)
  state$: Observable<WorkspaceMain>;
  state: WorkspaceMain = null;
  subscription

  constructor(private _fb: FormBuilder, private _searchService: SearchService, private _helperService: HelperService,
              private _router: Router, private _location: Location, private store: Store, private cdRef: ChangeDetectorRef) {
    this.initSearchForm();
  }

  ngOnInit() {
    this.store.dispatch(new PatchSearchStateAction({key: 'loading', value: true}));
    this.state$.subscribe(value => this.state = _.merge({}, value));
    this.searchedItems = this._searchService.searchedItems;
    this.globalSearchItem = this._searchService.globalSearchItem;
    this.store.select(fromWS.SearchNavBarState.getLoadingState).subscribe(l => {
      // console.log('loading', l);
      this.detectChanges();
    });
    this._searchService.items
      .pipe(debounceTime(200))
      .subscribe(
      () => {
        // console.log('Emitted')
        this.initSearchForm();
        this.globalSearchItem = '';
        this.searchedItems = [...this._searchService.searchedItems];
        this._loadContracts();
      }
    );

    this._searchService.globalSearch$.subscribe(
      () => {
        this.globalSearchItem = this._searchService.globalSearchItem;
        this.searchedItems = [];
        this.globalSearchItem !== '' ? this._loadContracts() : null;
      }
    );
  }

  initSearchForm() {
    this.contractFilterFormGroup = this._fb.group({
      globalKeyword: [],
      cedantCode: null,
      cedantName: null,
      countryName: null,
      innerCedantCode: null,
      innerCedantName: null,
      innerCountryName: null,
      innerWorkspaceId: null,
      innerWorkspaceName: null,
      innerYear: null,
      workspaceId: null,
      workspaceName: null,
      year: null
    });
    this.contractFilterFormGroup
      .valueChanges
      .pipe(debounceTime(500))
      .subscribe((param) => {
        // this.globalSearchItem !== '' ? this.globalSearchItem = '' : null;
        this.detectChanges();
        this._loadContracts();
      });
  }

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
  }


  private searchData(id, year) {
    return this._searchService.searchWorkspace(id || '', year || '2019');
  }

  loadMoreItems() {
    let {page, size} = this.paginationOption;
    this._loadContracts(String(page), String(size));
    this.loadingMore = true;
  }

  loadMore(event: LazyLoadEvent) {
    this.currentPage = event.first;
    this._loadContracts(String(event.first));
  }

  openWorkspace(wsId, year) {
    this.searchData(wsId, year).subscribe(
      (dt: any) => {
        const workspace = {
          workSpaceId: wsId,
          uwYear: year,
          selected: false,
          ...dt
        };
        workspace.projects = workspace.projects.map(prj => prj = {...prj, selected: false});
        const alreadyOpened = this.state.openedTabs.data.filter(ws => ws.workSpaceId === wsId && ws.uwYear == year);
        const index = _.findIndex(this.state.openedTabs.data, ws => ws.workSpaceId === wsId && ws.uwYear == year);
        if (alreadyOpened.length > 0) {
          this.store.dispatch(new PatchWorkspaceMainStateAction([{key: 'openedWs', value: alreadyOpened[0]},
            {key: 'openedTabs', value: {data: this.state.openedTabs.data, tabsIndex: index}}]));
          this.navigateToTab(this.state.openedTabs.data[this.state.openedTabs.tabsIndex]);
        } else {
          this.store.dispatch(new AppendNewWorkspaceMainAction(workspace));
          this._helperService.updateRecentWorkspaces();
          this._helperService.updateWorkspaceItems();
          this.navigateToTab(this.state.openedTabs.data[this.state.openedTabs.data.length - 1]);
        }
      }
    );
  }

  navigateToTab(value) {
    if (value.routing == 0) {
      this._router.navigate([`workspace/${value.workSpaceId}/${value.uwYear}`]);
    } else {
      this._router.navigate([`workspace/${value.workSpaceId}/${value.uwYear}/${value.routing}`]);
    }
  }

  openWorkspaceInSlider(contract) {
    this.currentWorkspace = contract;
    this.sliceValidator = true;
    this.expandWorkspaceDetails = true;
    this.searchData(contract.workSpaceId, contract.uwYear).subscribe(
      dt => {
        this.selectedWorkspace = dt;
        this.cdRef.detectChanges();
        console.log(this.selectedWorkspace);
      }
    );

  }

  popUpWorkspace(wsId, year) {
    this.searchData(wsId, year).subscribe(
      () => {
        window.open(`/workspace/${wsId}/${year}/PopOut`);
      }
    );
  }

  changeForm($event, target) {
    $event === '' ? this.contractFilterFormGroup.get(target).patchValue(null) : this.contractFilterFormGroup.get(target).patchValue($event);
  }

  private _loadContracts(offset = '0', size = '100') {
    // console.log('Load contracts');
    this.store.dispatch(new PatchSearchStateAction({key: 'visibleSearch', value: false}));
     if (!this._searchService.expertModeEnabled) {
      this.loading = true;
      const keys = [];
      const values = [];
      this.searchedItems.forEach(
        (e) => {
          keys.push(_.camelCase(e.key));
          values.push(e.value);
        }
      );
      keys.forEach(
        (e, index) => {
          this.contractFilterFormGroup.value[e] = values[index];
          // console.log(this.contractFilterFormGroup.value);
        }
      );
      this.store.dispatch(new PatchSearchStateAction({key: 'loading', value: true}));
      this._searchService.setLoading(true);
      this.globalSearchItem = this._searchService.globalSearchItem;
      const globalSearchParams = _.merge({keyword: this._searchService.globalSearchItem}, this.contractFilterFormGroup.value);
      if ( this.subscription ) {
         this.subscription.unsubscribe();
       }
      this.subscription = this._searchService.searchGlobal(globalSearchParams, offset, size)
        .subscribe((data: any) => {
          this.contracts = data.content.map(item => ({...item, selected: false}));
          this.loadingMore = false;
          this.loading = false;
          this.paginationOption = {page: data.number, size: data.numberOfElements, total: data.totalElements};
          this._searchService.setLoading(false);
          this.detectChanges();
        });
    } else {
      if ( this.subscription ) {
        this.subscription.unsubscribe();
      }
      this._searchService.setLoading(true);
      const filter = [...this._searchService.expertModeFilter, ...this.getInnerFilter()]
      this.subscription = this._searchService.expertModeSearch(_.merge(
        {keyword: this._searchService.keyword, filter, offset, size}))
        .subscribe((data: any) => {
          this.contracts = data.content.map(item => ({...item, selected: false}));
          this.loadingMore = false;
          this.loading = false;
          this.paginationOption = {page: data.number, size: data.numberOfElements, total: data.totalElements};
          this.store.dispatch(new PatchSearchStateAction({key: 'loading', value: false}));
          this.detectChanges();
        });
    }
  }

  navigateBack() {
    this._location.back();
  }

  clearChips() {
    this.closeGlobalSearch();
    this.searchedItems = [];
    this._searchService.expertModeFilter = [];
    this._searchService.keyword = null;
    this.initSearchForm();
    this._loadContracts();
  }

  closeSearchBadge(status, index, key?) {
    if (status) {
      if (key === 'Global Search') {
        this._searchService.keyword = null;
      } else if (this._searchService.expertModeEnabled) {
        this._searchService.expertModeFilter.splice(index, 1);
      }
      this.initSearchForm();
      this.searchedItems.splice(index, 1);
      this._loadContracts();
    }
  }

  closeSlider() {
    // console.log('this is outside');
    this.expandWorkspaceDetails = false;
  }

  getInnerFilter() {
    const filter = [];
    _.reduce(this.contractFilterFormGroup.value, (result, value, key) => {
      if (!_.isNil(value)) {
        const field = _(this.columns).filter(['filterParam', key]).map((o) => o.field).value()[0];
        filter.push({ field, value, operator: 'LIKE'}); }});
    return filter;
  }
  closeGlobalSearch() {
    this.globalSearchItem = '';
    this._searchService.globalSearchItem = '';
    this._loadContracts();
  }

}
