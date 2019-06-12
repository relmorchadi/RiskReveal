import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {SearchService} from '../../../core/service/search.service';
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
import {SearchNavBarState, WorkspaceMainState} from '../../../core/store/states';
import {Observable} from 'rxjs';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import {Debounce} from "../../../shared";
import {CloseTagByIndexAction, CloseAllTagsAction} from "../../../core/store";


@Component({
  selector: 'app-search-main',
  templateUrl: './search-main.component.html',
  styleUrls: ['./search-main.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchMainComponent implements OnInit {
  @ViewChild('dt') table;

  @Select(SearchNavBarState.getSearchContent)
  searchContent$;

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
      filterParam: 'countryName'
    },
    {
      field: 'cedantName',
      header: 'Cedent Name',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'cedantName'
    },
    {
      field: 'cedantCode',
      header: 'Cedant Code',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'cedantCode'
    },
    {
      field: 'uwYear',
      header: 'Uw Year',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'year'
    },
    {
      field: 'workspaceName',
      header: 'Workspace Name',
      width: '160px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'workspaceName'
    },
    {
      field: 'workSpaceId',
      header: 'Workspace Context',
      width: '90px',
      display: true,
      sorted: false,
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
  @Select(WorkspaceMainState)
  state$: Observable<WorkspaceMain>;
  state: WorkspaceMain = null;
  private _filter = {};
  private _searchContent;

  constructor(private _searchService: SearchService, private _helperService: HelperService,
              private _router: Router, private _location: Location, private store: Store, private cdRef: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.state$.subscribe(value => this.state = _.merge({}, value));
    this.searchContent$.subscribe(({value}) => {
      this._checkSearchContent(value);
      this._loadData();
      this.detectChanges();
    })
  }

  private _checkSearchContent(value:string|any[]){
    if(_.isString(value) || value == null){
      this.globalSearchItem = (value as string);
      this._searchContent = [];
    } else {
      this.globalSearchItem = null;
      this._searchContent=value;
    }
  }

  private _loadData(offset = '0', size = '100') {
    this.loading = true;
    let params = {
      keyword: this.globalSearchItem,
      filter: this.filter,
      offset,
      size
    };
    this._searchService.expertModeSearch(params)
      .subscribe((data: any) => {
        this.contracts = data.content.map(item => ({...item, selected: false}));
        this.loading = false;
        this.paginationOption = {
          ...this.paginationOption,
          page: data.number,
          size: data.numberOfElements,
          total: data.totalElements
        };
        this.detectChanges();
      });
  }


  loadMore(event: LazyLoadEvent) {
    this.paginationOption.currentPage = event.first;
    this._loadData(String(event.first));
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

  @Debounce(500)
  filterData($event, target) {
    this._filter = {...this._filter, [target]: $event || null};
    this._loadData();
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
    let tags = _.isString(this._searchContent) ? [] : (this._searchContent || []);
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

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
  }

}
