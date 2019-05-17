import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SearchService} from '../../../core/service/search.service';
import {debounceTime} from 'rxjs/operators';
import {HelperService} from '../../../shared/helper.service';
import {Router} from '@angular/router';
import {Location} from '@angular/common';
import * as _ from 'lodash';
import {LazyLoadEvent} from 'primeng/api';
import {Select, Store} from '@ngxs/store';
import {AppendNewWorkspaceMainAction} from '../../../core/store/actions/workspace-main.action';
import {WorkspaceMainState} from "../../../core/store/states";
import {Observable} from "rxjs";
import {WorkspaceMain} from "../../../core/model/workspace-main";


@Component({
  selector: 'app-search-main',
  templateUrl: './search-main.component.html',
  styleUrls: ['./search-main.component.scss']
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
  loading = false;
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
      field: 'cedantCode',
      header: 'Cedant',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'innerCedantCode'
    },
    {
      field: 'cedantName',
      header: '',
      width: '90px',
      display: false,
      sorted: false,
      filtered: false,
      filterParam: 'innerCedantName'
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
      field: 'workSpaceId',
      header: 'Workspace Context',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'innerWorkspaceId'
    },
    {
      field: 'workspaceName',
      header: '',
      width: '160px',
      display: false,
      sorted: false,
      filtered: false,
      filterParam: 'innerWorkspaceName'
    },
    {
      field: 'openInHere',
      header: '',
      width: '20px',
      type: 'icon',
      class: 'icon-fullscreen_24px',
      handler: (option) =>  option.forEach( dt => this.openWorkspace(dt.workSpaceId, dt.uwYear)),
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
      handler: (option) => option.forEach( dt => this.popUpWorkspace(dt.workSpaceId, dt.uwYear)),
      display: false,
      sorted: false,
      filtered: false
    }
  ];

  @Select(WorkspaceMainState)
  state$: Observable<WorkspaceMain>;
  state: WorkspaceMain = null;

  constructor(private _fb: FormBuilder, private _searchService: SearchService, private _helperService: HelperService,
              private _router: Router, private _location: Location, private store: Store) {
    this.initSearchForm();
  }

  ngOnInit() {
    this.state$.subscribe(value => this.state = _.merge({}, value));
    this.searchedItems = this._searchService.searchedItems;
    this.globalSearchItem = this._searchService.globalSearchItem;
    this._searchService.items.subscribe(
      () => {
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
        this.globalSearchItem !== '' ? this.globalSearchItem = '' : null;
        this._loadContracts();
      });
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
    this.currentPage = event.first ;
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
        this.store.dispatch(new AppendNewWorkspaceMainAction(workspace));
        this._helperService.updateRecentWorkspaces();
        this._helperService.updateWorkspaceItems();
        this.navigateToTab(this.state.openedTabs[0]);
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
        console.log(this.selectedWorkspace);
      }
    );
  }

  popUpWorkspace(wsId, year) {
    this.searchData(wsId, year).subscribe(
      () => {
        window.open('/workspace/' + wsId + '/' + year);
      }
    );
  }

  changeForm($event, target) {
    $event === '' ? this.contractFilterFormGroup.get(target).patchValue(null) : this.contractFilterFormGroup.get(target).patchValue($event);
  }

  private _loadContracts(offset = '0', size = '100') {
    this.loading = true;
    if (this.searchedItems.length > 0) {
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
          console.log(this.contractFilterFormGroup.value);
        }
      );
      this._searchService.searchContracts(this.contractFilterFormGroup.value, offset, size)
        .subscribe((data: any) => {
          this.contracts = data.content.map(item => ({...item, selected: false}));
          this.loadingMore = false;
          this.loading = false;
          this.paginationOption = {page: data.number, size: data.numberOfElements, total: data.totalElements};
        });
    } else if (this.globalSearchItem !== '') {
      this._searchService.searchGlobal(this.globalSearchItem)
        .subscribe((data: any) => {
            this.contracts = data.content.map(item => ({...item, selected: false}));
            this.loadingMore = false;
            this.paginationOption = {page: data.number, size: data.numberOfElements, total: data.totalElements};
            this.loading = false;
          }
        );
    } else {
      this._searchService.searchContracts(this.contractFilterFormGroup.value, offset, size)
        .subscribe((data: any) => {
          this.contracts = data.content.map(item => ({...item, selected: false}));
          this.loadingMore = false;
          this.loading = false;
          this.paginationOption = {page: data.number, size: data.numberOfElements, total: data.totalElements};
        });
    }
  }

  navigateBack() {
    this._location.back();
  }

  clearChips() {
    this.searchedItems = [];
    this.initSearchForm();
    this._loadContracts();
  }

  sliceContent(content: any, valid: boolean) {
    if (valid && content) {
      return content.slice(0, 3);
    } else {
      return content;
    }
  }

  closeSearchBadge(status, index) {
    if (status) {
      this.initSearchForm();
      this.searchedItems.splice(index, 1);
      this._loadContracts();
    }
  }

  closeSlider() {
    console.log('this is outside');
    this.expandWorkspaceDetails = false;
  }

}
