import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {SearchService} from '../../../core/service/search.service';
import {Debounce, HelperService} from '../../../shared';
import {Router} from '@angular/router';
import {Location} from '@angular/common';
import * as _ from 'lodash';
import {LazyLoadEvent} from 'primeng/api';
import {Select, Store} from '@ngxs/store';
import {DashboardState, SearchNavBarState} from '../../../core/store/states';
import {
  CloseAllTagsAction, CloseGlobalSearchAction,
  CloseTagByIndexAction, DeleteSearchItem, LoadMostUsedSavedSearch,
  saveSearch,
  toggleSavedSearch,
  UpdateBadges
} from '../../../core/store';
import {BaseContainer} from '../../../shared/base';
import * as workspaceActions from '../../../workspace/store/actions/workspace.actions';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import * as SearchActions from "../../../core/store/actions/search-nav-bar.action";
import {BadgesService} from "../../../core/service/badges.service";
import {catchError, distinctUntilChanged, takeUntil, tap} from "rxjs/operators";
import {LoadRecentSearch} from "../../../core/store/actions";
import {combineLatest, of} from "rxjs";
import {falseIfMissing} from "protractor/built/util";


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

  @Select(SearchNavBarState.getSearchTarget)
  selectedDashboard$;

  sortData: any;

  savedSearch;
  searchMode: any = 'Treaty';
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
    {field: 'checkbox', header: '', width: '20px', visible: true, display: true, sorted: true, filtered: false, type: 'checkbox', class: 'icon-check_24px',},
    {field: 'countryName', header: 'Country', width: '90px', type: 'text', visible: true, display: true, sorted: true, filtered: true, queryParam: 'CountryName'},
    {field: 'cedantName', header: 'Cedent Name', width: '90px', type: 'text', visible: true, display: true, sorted: true, filtered: true, queryParam: 'CedantName'},
    {field: 'cedantCode', header: 'Cedant Code', width: '90px', type: 'text', visible: true, display: true, sorted: true, filtered: true, queryParam: 'CedantCode'},
    {field: 'uwYear', header: 'Uw Year', width: '90px', type: 'text', visible: true, display: true, sorted: true, filtered: true, queryParam: 'UwYear'},
    {field: 'workspaceName', header: 'Workspace Name', width: '160px', type: 'text', visible: true, display: true, sorted: true, filtered: true, queryParam: 'WorkspaceName'},
    {field: 'workSpaceId', header: 'Workspace Context', width: '90px', type: 'text', visible: true, display: true, sorted: true, filtered: true, queryParam: 'WorkspaceId'},
    {field: 'openInHere', header: '', width: '20px', type: 'icon', class: 'icon-fullscreen_24px', visible: true, handler: (option) => option.forEach(dt => this.openWorkspace(dt.workSpaceId, dt.uwYear)), display: false, sorted: false, filtered: false},
    {field: 'openInPopup', header: '', width: '20px', type: 'icon', class: 'icon-open_in_new_24px', visible: true, handler: (option) => option.forEach(dt => this.popUpWorkspace(dt.workSpaceId, dt.uwYear)), display: false, sorted: false, filtered: false}
  ];
  columnsCache = [];
  columnsFacCache = [];
  extraColumns = [];
  extraColumnsFac = [];
  extraColumnsCache = [];
  extraColumnsCacheFac = [];

  columnsFac = [
    {field: 'checkbox', header: '', width: '20px', visible: true, display: true, sorted: true, filtered: false, type: 'checkbox', class: 'icon-check_24px',},
    {field: 'client', header: 'Client', width: '90px', type: 'text', visible: true, display: true, sorted: true, filtered: true, queryParam: 'CountryName'},
    {field: 'uwYear', header: 'Uw Year', width: '90px', type: 'text', visible: true, display: true, sorted: true, filtered: true, queryParam: 'UwYear'},
    {field: 'workspaceName', header: 'Contract Code', width: '160px', type: 'text', visible: true, display: true, sorted: true, filtered: true, queryParam: 'WorkspaceName'},
    {field: 'workSpaceContextCode', header: 'Contract Name', width: '90px', type: 'text', visible: true, display: true, sorted: true, filtered: true, queryParam: 'workSpaceContextCode'},
    {field: 'uwAnalysis', header: 'Uw Analysis', width: '90px', type: 'text', visible: true, display: true, sorted: true, filtered: true, queryParam: 'uwAnalysis'},
    {field: 'carequestId', header: 'CAR ID', width: '70px', type: 'text', visible: true, display: true, sorted: true, filtered: true, queryParam: 'carequestId'},
    {field: 'carStatus', header: 'CAR Status', width: '90px', type: 'text', visible: true, display: true, sorted: true, filtered: true, queryParam: 'carStatus'},
    {field: 'assignedTo', header: 'Assigned User', width: '90px', type: 'text', visible: true, display: true, sorted: true, filtered: true, queryParam: 'assignedTo'},
    {field: 'openInHere', header: '', width: '20px', type: 'icon', class: 'icon-fullscreen_24px', visible: true, handler: (option) => option.forEach(dt => this.openWorkspace(dt.workspaceName, dt.uwYear)), display: false, sorted: false, filtered: false},
    {field: 'openInPopup', header: '', width: '20px', type: 'icon', class: 'icon-open_in_new_24px', visible: true, handler: (option) => option.forEach(dt => this.popUpWorkspace(dt.workspaceName, dt.uwYear)), display: false, sorted: false, filtered: false}
  ];

  private _filter = {};
  searchContent;
  manageColumns: boolean = false;
  savedSearchVisibility: boolean;
  saveSearchPopup: boolean = false;
  searchLabel: any;
  mapTableNameToBadgeKey: any;
  fromSavedSearch: boolean;
  searchSub$;

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

    this.selectedDashboard$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.searchMode !== value ? this._loadData() : null;
      this.searchMode = value;
      this.detectChanges();
    });

    this.badges$.pipe(this.unsubscribeOnDestroy).subscribe(badges => {
      this.badges = badges;
      this.detectChanges();
    });

    this.savedSearch$.pipe(this.unsubscribeOnDestroy).subscribe(savedSearch => {
      this.savedSearch = savedSearch;
      this.detectChanges();
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
    this.columnsFacCache = _.merge([], this.columnsFac);
    this.extraColumnsCacheFac = _.merge([], this.extraColumnsFac);
  }

  openWorkspace(wsId, year) {
    if (this.searchMode === 'Treaty') {
      this.dispatch(new workspaceActions.OpenWS({wsId, uwYear: year, route: 'projects', type: 'TTY'}));
    } else {
      this.dispatch(new workspaceActions.OpenWS({wsId, uwYear: year, route: 'projects', type: 'FAC'}));
    }
  }

  openWorkspaceInSlider(contract) {
    /*    this.currentWorkspace = contract;
        this.sliceValidator = true;
        this.expandWorkspaceDetails = true;*/
  }

  popUpWorkspace(wsId, year) {
    window.open(`/workspace/${wsId}/${year}/projects`);
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  private _checkSearchContent(value: string | any[]) {
    if (_.isString(value) || value == null) {
      this.searchContent = [];
    } else {
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
    this.store.dispatch(new CloseGlobalSearchAction());
  }

  get filter() {
    let tags= [];
    let keyword= "";

    _.forEach(_.isString(this.searchContent) ? [] : (this.searchContent || []), item => {
      if(item.key == "global search") {
        keyword = item.value;
      } else {
        tags.push({
          ...item,
          key: this._badgeService.transformToMapping(item.key),
          value: this._badgeService.clearString(this._badgeService.parseAsterisk(item.value))
        })
      }
    });

    let tableFilter = _.map(this._filter, (value, key) => ({key: this._badgeService.transformToMapping(key),
      value: this._badgeService.clearString(this._badgeService.parseAsterisk(value))}));
    return {
      filters: _.concat(tags, tableFilter).filter(({value}) => value).map((item: any) => ({
        ...item,
        field: item.key,
        operator: item.operator || 'LIKE'
      })),
      keyword
    }
  }

  loadMore(event: LazyLoadEvent) {
    this._loadData(event.first, event.rows);
  }

  getSortColumns(sortData) {
    return _.map(sortData, (order, col) => ({columnName: this.mapColToQuery(col), order: _.toUpper(order)}))
  }

  mapColToQuery(col) {
    if (this.searchMode === 'Treaty') {
      return _.find(this.columns, column => column.field == col).queryParam;
    } else {
      return _.find(this.columnsFac, column => column.field == col).queryParam;
    }
  }

  private _loadData(offset = 0, size = 100, filter: boolean = false) {
    this.searchSub$ && this.searchSub$.unsubscribe();
    this.loading = true;
    const {
      filters,
      keyword
    } = this.filter;

    setTimeout(() => {
      let params = {
        keyword: this._badgeService.clearString(this._badgeService.parseAsterisk(_.trim(keyword))),
        filter: filters,
        sort: this.getSortColumns(this.sortData),
        offset,
        size: size,
        fromSavedSearch: this.fromSavedSearch,
        type: _.toUpper(this.searchMode)
      };

      this.searchSub$ = this._searchService.expertModeSearch(params)
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

            // if (totalElements == 1 && !filter) this.openWorkspace(data.content[0].workSpaceId, data.content[0].uwYear);


            this.dispatch(new LoadRecentSearch());
            if(this.fromSavedSearch) {
              this.dispatch(new LoadMostUsedSavedSearch());
              this.fromSavedSearch = false;
            }
            this.detectChanges();
          });
    }, 1000);

  }

  dropColumn(event: CdkDragDrop<any>) {
    const {
      previousContainer,
      container
    } = event;

    if (previousContainer === container) {
      if (container.id == "usedListOfColumns") {
        if (this.searchMode === 'Treaty') {
          moveItemInArray(
              this.columnsCache,
              event.previousIndex + 1,
              event.currentIndex + 1
          );
        } else {
          moveItemInArray(
              this.columnsFacCache,
              event.previousIndex + 1,
              event.currentIndex + 1
          );
        }
      }
    } else {
      if (this.searchMode === 'Treaty') {
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
      } else {
        if (this.extraColumnsCacheFac.length > 0) {
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
  }

  openPopUp() {
    this.manageColumns = true;
    this.initColumns();
  }

  saveColumns() {
    if(this.searchMode === 'Treaty') {
      this.columns = [...this.columnsCache];
      this.extraColumns = [...this.extraColumnsCache];
      this.columns.splice(_.findIndex(this.columns, row => row.field == 'checkbox'), 1);
      this.columns.unshift({
        field: 'checkbox',
        header: '',
        width: '20px',
        visible: true,
        display: true,
        sorted: true,
        filtered: false,
        type: 'checkbox',
        class: 'icon-check_24px',
      });
    } else {
      this.columnsFac = [...this.columnsFacCache];
      this.extraColumnsFac = [...this.extraColumnsCacheFac];
      this.columnsFac.splice(_.findIndex(this.columnsFac, row => row.field == 'checkbox'), 1);
      this.columnsFac.unshift({
        field: 'checkbox',
        header: '',
        width: '20px',
        visible: true,
        display: true,
        sorted: true,
        filtered: false,
        type: 'checkbox',
        class: 'icon-check_24px',
      });
    }
    console.log(this.columnsFac, this.extraColumnsFac);
    this.manageColumns = false;
    this.detectChanges();
  }

  dropAll(side: string) {
    if (side == 'right') {
      if (this.searchMode === 'Treaty') {
        this.columnsCache = [...this.columnsCache, ...this.extraColumnsCache];
        this.extraColumnsCache = [];
      } else {
        this.columnsFacCache = [...this.columnsFacCache, ...this.extraColumnsCacheFac];
        this.extraColumnsCacheFac = [];
      }

    } else if (side == 'left') {
      if (this.searchMode === 'Treaty') {
        this.extraColumnsCache = [...this.extraColumnsCache, ...this.columnsCache];
        this.columnsCache = [];
      } else {
        this.extraColumnsCacheFac = [...this.extraColumnsCacheFac, ...this.columnsFacCache];
        this.columnsFacCache = [];
      }
    }
  }

  closeManageColumns() {
    this.manageColumns = false;
    if (this.searchMode === 'Treaty') {
      this.columnsCache = this.columns;
      this.extraColumnsCache = this.extraColumns;
    } else {
      this.columnsFacCache = this.columnsFac;
      this.extraColumnsCacheFac = this.extraColumnsFac;
    }
  }

  openSavedSearch() {
    this.saveSearchPopup = true;
  }

  saveSearch() {
    if (this.searchContent.length > 0) {
      this.store.dispatch(new saveSearch({
        searchType: this.searchMode === 'Treaty' ? "TREATY" : "FAC",
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

  deleteSearchItem(id) {
    event.preventDefault();
    event.stopPropagation();
    this.store.dispatch(new DeleteSearchItem({id}))
  }
}
