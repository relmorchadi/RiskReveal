import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {SearchService} from '../../../core/service/search.service';
import {Debounce, HelperService} from '../../../shared';
import {Router} from '@angular/router';
import {Location} from '@angular/common';
import * as _ from 'lodash';
import {LazyLoadEvent} from 'primeng/api';
import {Select, Store} from '@ngxs/store';
import {SearchNavBarState} from '../../../core/store/states';
import {
  CloseAllTagsAction,
  CloseTagByIndexAction, LoadMostUsedSavedSearch,
  saveSearch,
  toggleSavedSearch,
  UpdateBadges
} from '../../../core/store';
import {BaseContainer} from '../../../shared/base';
import * as workspaceActions from '../../../workspace/store/actions/workspace.actions';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import * as SearchActions from "../../../core/store/actions/search-nav-bar.action";
import {BadgesService} from "../../../core/service/badges.service";
import {distinctUntilChanged, takeUntil} from "rxjs/operators";


@Component({
  selector: 'app-search-main',
  templateUrl: './search-main.component.html',
  styleUrls: ['./search-main.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchMainComponent extends BaseContainer implements OnInit, OnDestroy {
  @ViewChild('dt') table;

  @Select(SearchNavBarState.getSearchContent)
  searchContent$;

  @Select(SearchNavBarState.getBadges)
  badges$;

  @Select(SearchNavBarState.getSavedSearch)
  savedSearch$;

  @Select(SearchNavBarState.getShowSavedSearch)
  savedSearchVisibility$;

  sortData: any;

  savedSearch;
  badges;

  expandWorkspaceDetails = false;
  contracts = [];
  paginationOption = {
    offset: 0,
    pageNumber: 0,
    pageSize: 100,
    size: 100,
    totalPages: 0,
    total: '-'
  };
  selectedWorkspace: any;
  sliceValidator = true;
  globalSearchItem = '';
  currentWorkspace = null;
  loading = true;
  columns = [
    {
      field: 'checkbox',
      header: '',
      width: '20px',
      display: true,
      sorted: true,
      filtered: false,
      type: 'checkbox',
      class: 'icon-check_24px',
    },
    {
      field: 'countryName',
      header: 'Country',
      width: '90px',
      display: true,
      sorted: true,
      filtered: true,
      queryParam: 'CountryName'
    },
    {
      field: 'cedantName',
      header: 'Cedent Name',
      width: '90px',
      display: true,
      sorted: true,
      filtered: true,
      queryParam: 'CedantName'
    },
    {
      field: 'cedantCode',
      header: 'Cedant Code',
      width: '90px',
      display: true,
      sorted: true,
      filtered: true,
      queryParam: 'CedantCode'
    },
    {
      field: 'uwYear',
      header: 'Uw Year',
      width: '90px',
      display: true,
      sorted: true,
      filtered: true,
      queryParam: 'UwYear'
    },
    {
      field: 'workspaceName',
      header: 'Workspace Name',
      width: '160px',
      display: true,
      sorted: true,
      filtered: true,
      queryParam: 'WorkspaceName'
    },
    {
      field: 'workSpaceId',
      header: 'Workspace Context',
      width: '90px',
      display: true,
      sorted: true,
      filtered: true,
      queryParam: 'WorkspaceId'
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
  columnsCache = [];
  extraColumns = [];
  extraColumnsCache = [];

  private _filter = {};
  searchContent;
  manageColumns: boolean = false;
  savedSearchVisibility: boolean;
  saveSearchPopup: boolean = false;
  searchLabel: any;
  mapTableNameToBadgeKey: any;
  fromSavedSearch: boolean;

  constructor(private _searchService: SearchService,private _badgeService: BadgesService, private _helperService: HelperService,
              private _router: Router, private _location: Location, private store: Store, private cdRef: ChangeDetectorRef) {
    super(_router, cdRef, store);
    this.sortData = {};
    this.fromSavedSearch= false;
  }

  ngOnInit() {
    this.store.select(SearchNavBarState.getMapTableNameToBadgeKey).pipe(this.unsubscribeOnDestroy,distinctUntilChanged()).subscribe( mapTableNameToBadgeKey => {
      this.mapTableNameToBadgeKey = mapTableNameToBadgeKey;
      this.detectChanges();
    });
    this.searchContent$
      .pipe(this.unsubscribeOnDestroy)
      .subscribe(({value}) => {
        this._checkSearchContent(value);
        this._loadData();
        this.detectChanges();
      });
    this.badges$.pipe(this.unsubscribeOnDestroy).subscribe(badges => {
      this.badges = badges;
    });
    this.savedSearch$.pipe(this.unsubscribeOnDestroy).subscribe(savedSearch => {
      this.savedSearch = savedSearch;
    });
    this.savedSearchVisibility$.pipe(this.unsubscribeOnDestroy).subscribe(savedSearchVisibility => {
      this.savedSearchVisibility = savedSearchVisibility;
      this.cdRef.detectChanges();
    });
    this.initColumns();
  }

  initColumns() {
    this.columnsCache = _.merge([], this.columns);
    this.extraColumnsCache = _.merge([], this.extraColumns);
  }

  openWorkspace(wsId, year) {
    this.dispatch(new workspaceActions.OpenWS({wsId, uwYear: year, route: 'projects', type: 'treaty'}));
  }

  navigateToTab(value) {
    if (value.routing == 0) {
      this._router.navigate([`workspace/${value.workSpaceId}/${value.uwYear}/projects`]);
    } else {
      this._router.navigate([`workspace/${value.workSpaceId}/${value.uwYear}/${value.routing}`]);
    }
  }

  openWorkspaceInSlider(contract) {
    this.currentWorkspace = contract;
    this.sliceValidator = true;
    this.expandWorkspaceDetails = true;
    this.searchData(contract.workSpaceId, contract.uwYear)
      .pipe(this.unsubscribeOnDestroy)
      .subscribe(
        dt => {
          this.selectedWorkspace = dt;
          this.cdRef.detectChanges();
        }
      );

  }

  popUpWorkspace(wsId, year) {
    window.open(`/workspace/${wsId}/${year}/projects`);
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  private _checkSearchContent(value: string | any[]) {
    if (_.isString(value) || value == null) {
      this.globalSearchItem = (value as string);
      this.searchContent = [];
    } else {
      this.globalSearchItem = null;
      this.searchContent = value;
    }
  }

  @Debounce(500)
  filterData($event, target) {
    this._filter = {...this._filter, [target]: $event || null};
    this._loadData(0, 100, true);
  }

  navigateBack() {
    this._location.back();
  }

  clearChips() {
    this.store.dispatch(new CloseAllTagsAction());
  }

  closeSearchBadge(status, index, key?) {
    this.store.dispatch(new CloseTagByIndexAction(index));
  }

  closeGlobalSearch() {
    this.store.dispatch(new CloseAllTagsAction());
  }

  get filter() {
    let tags = _.isString(this.searchContent) ? [] : (this.searchContent || []).map(item => ({
      ...item,
      value: this._badgeService.clearString(this._badgeService.parseAsterisk(item.value)),
    }));
    let tableFilter = _.map(this._filter, (value, key) => ({key, value}));
    return _.concat(tags, tableFilter).filter(({value}) => value).map((item: any) => ({
      ...item,
      field: _.camelCase(item.key),
      operator: item.operator || 'LIKE'
    }));
  }

  private searchData(id, year) {
    return this._searchService.searchWorkspace(id || '', year || '2019');
  }

  loadMore(event: LazyLoadEvent) {
    this._loadData(event.first, event.rows);
  }

  getSortColumns(sortData) {
    return _.map(sortData, (order, col) => ({columnName: this.mapColToQuery(col), order}))
  }

  mapColToQuery(col) {
    return _.find(this.columns, column => column.field == col).queryParam;
  }

  private _loadData(offset = 0, size = 100, filter: boolean = false) {
    this.loading = true;
    let params = {
      keyword: this.globalSearchItem,
      filter: this.filter,
      sort: this.getSortColumns(this.sortData),
      offset,
      size: size,
      fromSavedSearch: this.fromSavedSearch
    };
    this._searchService.expertModeSearch(params)
      .pipe(this.unsubscribeOnDestroy)
      .subscribe((data: any) => {
        this.contracts = _.map(data.content, item => ({...item, selected: false}));
        this.loading = false;
        const {
          pageable: {
            offset,
            pageNumber,
            pageSize
          },
          size,
          totalElements,
          totalPages
        } = data;

        this.paginationOption = {
          offset,
          pageNumber: pageNumber + 1,
          pageSize,
          size,
          total: totalElements,
          totalPages: totalPages
        };

        if (totalElements == 1 && !filter) this.openWorkspace(data.content[0].workSpaceId, data.content[0].uwYear);
        if(this.fromSavedSearch) {
          this.dispatch(new LoadMostUsedSavedSearch());
          this.fromSavedSearch = false;
        }
        this.detectChanges();
      });
  }

  dropColumn(event: CdkDragDrop<any>) {
    console.log(event);
    const {
      previousContainer,
      container
    } = event;

    if (previousContainer === container) {
      if (container.id == "usedListOfColumns") {
        moveItemInArray(
          this.columnsCache,
          event.previousIndex + 1,
          event.currentIndex + 1
        );
        console.log(container.id, this.columnsCache);
      }
    } else {
      if (this.extraColumnsCache.length > 0) {
        transferArrayItem(
          event.previousContainer.data,
          event.container.data,
          event.previousIndex,
          event.currentIndex
        );
      } else {
        transferArrayItem(
          event.previousContainer.data,
          event.container.data,
          event.previousIndex + 1,
          event.currentIndex + 1
        );
      }
    }
  }

  saveColumns() {
    this.columns = this.columnsCache;
    this.extraColumns = this.extraColumnsCache;
    this.columns.splice(_.findIndex(this.columns, row => row.field == 'checkbox'), 1);
    this.columns.unshift({
      field: 'checkbox',
      header: '',
      width: '20px',
      display: true,
      sorted: true,
      filtered: false,
      type: 'checkbox',
      class: 'icon-check_24px',
    })
    this.manageColumns = false;
    this.cdRef.detectChanges();
    console.log('columns==>', this.columns)
    console.log('extraColumns==>', this.extraColumns)
  }

  dropAll(side: string) {
    if (side == 'right') {
      this.columnsCache = [...this.columnsCache, ...this.extraColumnsCache];
      this.extraColumnsCache = [];
    } else if (side == 'left') {
      this.extraColumnsCache = [...this.extraColumnsCache, ...this.columnsCache];
      this.columnsCache = [];
    }
  }

  closeManageColumns() {
    this.manageColumns = false;
    this.columnsCache = this.columns;
    this.extraColumnsCache = this.extraColumns;
  }

  openSavedSearch() {
    this.saveSearchPopup = true;
  }
  saveSearch() {
    if (this.searchContent.length > 0) {
      this.store.dispatch(new saveSearch({
        searchType: "TREATY",
        label: this.searchLabel,
        items: this.searchContent
      }));
      this.searchLabel = null;
      this.saveSearchPopup = false;
    }
  }


  toggleSavedSearch() {
    this.store.dispatch(new toggleSavedSearch());
  }

  applySearch(search: any) {
    this.fromSavedSearch = true;
    this.store.dispatch(new UpdateBadges(search.badges));
    this.store.dispatch(new SearchActions.SearchAction(search.badges, ''));
  }

  closeSaveSearchPup() {
    this.saveSearchPopup = false;
    this.searchLabel = null;
  }

  sortChange(sortData) {
    this.sortData = sortData;
    this._loadData(this.paginationOption.offset);
  }
}
