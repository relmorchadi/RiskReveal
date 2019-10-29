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
  CloseTagByIndexAction,
  saveSearch,
  toggleSavedSearch,
  UpdateBadges
} from '../../../core/store';
import {BaseContainer} from '../../../shared/base';
import * as workspaceActions from '../../../workspace/store/actions/workspace.actions';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import * as SearchActions from "../../../core/store/actions/search-nav-bar.action";
import {mapBadgeShortCutToBadgeKey, mapTableNameToBadgeKey} from "../../../core/service/badges.service";


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

  savedSearch;
  badges;

  expandWorkspaceDetails = false;
  contracts = [];
  paginationOption = {currentPage: 0, page: 0, size: 40, total: '-'};
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
      filterParam: 'countryName'
    },
    {
      field: 'cedantName',
      header: 'Cedent Name',
      width: '90px',
      display: true,
      sorted: true,
      filtered: true,
      filterParam: 'cedantName'
    },
    {
      field: 'cedantCode',
      header: 'Cedant Code',
      width: '90px',
      display: true,
      sorted: true,
      filtered: true,
      filterParam: 'cedantCode'
    },
    {
      field: 'uwYear',
      header: 'Uw Year',
      width: '90px',
      display: true,
      sorted: true,
      filtered: true,
      filterParam: 'year'
    },
    {
      field: 'workspaceName',
      header: 'Workspace Name',
      width: '160px',
      display: true,
      sorted: true,
      filtered: true,
      filterParam: 'workspaceName'
    },
    {
      field: 'workSpaceId',
      header: 'Workspace Context',
      width: '90px',
      display: true,
      sorted: true,
      filtered: true,
      filterParam: 'workspaceId'
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
  mapBadgeShortCutToBadgeKey: any;
  mapTableNameToBadgeKey: any;

  constructor(private _searchService: SearchService, private _helperService: HelperService,
              private _router: Router, private _location: Location, private store: Store, private cdRef: ChangeDetectorRef) {
    super(_router, cdRef, store);
    this.mapBadgeShortCutToBadgeKey= mapBadgeShortCutToBadgeKey;
    this.mapTableNameToBadgeKey = mapTableNameToBadgeKey;
  }

  ngOnInit() {
    this.searchContent$
      .pipe(this.unsubscribeOnDestroy)
      .subscribe(({value}) => {
        this._checkSearchContent(value);
        this._loadData();
        this.detectChanges();
      });
    this.badges$.pipe(this.unsubscribeOnDestroy).subscribe(badges => {
      this.badges = badges;
    })
    this.savedSearch$.pipe(this.unsubscribeOnDestroy).subscribe(savedSearch => {
      this.savedSearch = savedSearch;
    })
    this.savedSearchVisibility$.pipe(this.unsubscribeOnDestroy).subscribe(savedSearchVisibility => {
      this.savedSearchVisibility = savedSearchVisibility;
      this.cdRef.detectChanges();
    })
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


  loadMore(event: LazyLoadEvent) {
    this.paginationOption.currentPage = event.first;
    this._loadData(String(event.first));
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
    this._loadData('0', '100', true);
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
    let tags = _.isString(this.searchContent) ? [] : (this.searchContent || []);
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

  private _loadData(offset = '0', size = '100', filter: boolean = false) {
    this.loading = true;
    let params = {
      keyword: this.globalSearchItem,
      filter: this.filter,
      offset,
      size
    };
    console.log('EXPERT');
    this._searchService.expertModeSearch(params)
      .pipe(this.unsubscribeOnDestroy)
      .subscribe((data: any) => {
        this.contracts = data.content.map(item => ({...item, selected: false}));
        this.loading = false;
        this.paginationOption = {
          ...this.paginationOption,
          page: data.number,
          size: data.numberOfElements,
          total: data.totalElements
        };
        if (data.totalElements == 1) {
          if (!filter)
            this.openWorkspace(data.content[0].workSpaceId, data.content[0].uwYear)
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
        date: new Date().toLocaleDateString(),
        label: this.searchLabel,
        badges: this.searchContent
      }));
      this.searchLabel = null;
      this.saveSearchPopup = false;
    }
  }


  toggleSavedSearch() {
    this.store.dispatch(new toggleSavedSearch());
  }

  applySearch(search: any) {
    this.store.dispatch(new UpdateBadges(search.badges));
    this.store.dispatch(new SearchActions.SearchAction(search.badges, ''));
  }

  closeSaveSearchPup() {
    this.saveSearchPopup = false;
    this.searchLabel = null;
  }
}
