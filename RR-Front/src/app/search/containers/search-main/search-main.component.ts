import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SearchService} from '../../../core/service/search.service';
import {debounceTime} from 'rxjs/operators';
import {HelperService} from '../../../shared/helper.service';
import {Router} from '@angular/router';
import {Location} from '@angular/common';
import * as _ from 'lodash';
import {LazyLoadEvent} from 'primeng/api';


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
      header: 'checkbox',
      width: '15px',
      display: false,
      sorted: false,
      filtered: false,
      type: 'checkbox',
      class: 'icon-check_24px',
    },
    {
      field: 'countryName',
      header: 'Country',
      width: '110px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'innerCountryName'
    },
    {
      field: 'cedantCode',
      header: 'Cedant',
      width: '110px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'innerCedantCode'
    },
    {
      field: 'cedantName',
      header: '',
      width: '110px',
      display: false,
      sorted: false,
      filtered: false,
      filterParam: 'innerCedantName'
    },
    {
      field: 'uwYear',
      header: 'Uw Year',
      width: '110px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'innerYear'
    },
    {
      field: 'workSpaceId',
      header: 'Workspace Context',
      width: '110px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'innerWorkspaceId'
    },
    {
      field: 'workspaceName',
      header: '',
      width: '110px',
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

  constructor(private _fb: FormBuilder, private _searchService: SearchService, private _helperService: HelperService,
              private _router: Router, private _location: Location) {
    this.initSearchForm();
  }

  ngOnInit() {
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
          uwYear: year,
          workSpaceId: wsId,
          cedantCode: dt.cedantCode,
          cedantName: dt.cedantName,
          ledgerName: dt.ledgerName,
          ledgerId: dt.subsidiaryLedgerId,
          subsidiaryName: dt.subsidiaryName,
          subsidiaryId: dt.subsidiaryId,
          expiryDate: dt.expiryDate,
          inceptionDate: dt.inceptionDate,
          treatySections: dt.treatySections,
          workspaceName: dt.worspaceName,
          years: dt.years
        };
        let usedWorkspaces = JSON.parse(localStorage.getItem('usedWorkspaces')) || [];
        usedWorkspaces.forEach(ws => {
          let filter = false;
          if (workspace.workSpaceId === ws.workSpaceId) {
            filter = true;
          }
          if (
            filter === true) {
            usedWorkspaces = usedWorkspaces.filter(items => items !== ws);
          }
        });
        usedWorkspaces.unshift(workspace);
        this._helperService.updateRecentWorkspaces(usedWorkspaces);
        this._helperService.affectItems([workspace]);
        this._router.navigate(['/workspace']);
      }
    );
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
      (dt: any) => {
        const workspace = {
          uwYear: year,
          workSpaceId: wsId,
          cedantCode: dt.cedantCode,
          cedantName: dt.cedantName,
          ledgerName: dt.ledgerName,
          subsidiaryId: dt.subsidiaryId,
          subsidiaryName: dt.subsidiaryName,
          treatySections: dt.treatySections,
          workspaceName: dt.worspaceName,
          years: dt.years
        };
        this._helperService.affectItems([workspace]);
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
          const key = _.camelCase(e.key);
          keys.push(key);
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
          // let body = this.table.containerViewChild.nativeElement.getElementsByClassName('ui-table-scrollable-body')[0];
          // body.scrollTop = 0;
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

}
