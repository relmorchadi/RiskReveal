import {Component, HostListener, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {SearchService} from "../../../core/service/search.service";
import {debounceTime} from "rxjs/operators";
import {HelperService} from "../../../shared/helper.service";
import {Router} from "@angular/router";
import {Location} from '@angular/common';


@Component({
  selector: 'app-search-main',
  templateUrl: './search-main.component.html',
  styleUrls: ['./search-main.component.scss']
})
export class SearchMainComponent implements OnInit {

  contractFilterFormGroup: FormGroup;
  expandWorkspaceDetails = false;
  contracts = [];
  paginationOption = {page: 0, size: 20, total: null};
  selectedWorkspace: any;
  loadingMore = false;
  searchedItems = [];
  currentWorkspace = null;
  columns = [
    { field: 'id', header: 'Id', width: '150px', display:true, sorted:false, filtered:false },
    { field: 'countryName', header: 'Country', width: '110px', display:true, sorted:false, filtered:true },
    { field: 'cedantCode', header: 'Cedant', width: '110px', display:true, sorted:false, filtered:true  },
    { field: 'cedantName', header: '', width: '110px', display:false, sorted:false, filtered:false  },
    { field: 'uwYear', header: 'Uw Year', width: '110px', display:true, sorted:false, filtered:true  },
    { field: 'workSpaceId', header: 'Workspace Context', width: '110px', display:true, sorted:false, filtered:true  },
    { field: 'workspaceName', header: '', width: '110px', display:false, sorted:false, filtered:false  },
  ];

  constructor(private _fb: FormBuilder, private _searchService: SearchService, private _helperService: HelperService,
              private _router: Router,private _location: Location) {
      this.clearSearch();
  }

  ngOnInit() {
    this.searchedItems = this._searchService.searchedItems;
    this._searchService.items.subscribe(
      () =>  {
        this.searchedItems = [...this._searchService.searchedItems];
        this._loadContracts();
      }
    );
    this._loadContracts();
    // this.contractFilterFormGroup
    //   .valueChanges
    //   .pipe(debounceTime(500))
    //   .subscribe(() => this._loadContracts());
  }

  clearSearch() {
    this.contractFilterFormGroup = this._fb.group({
      globalKeyword: [],
      workspaceId: [],
      workspaceName: [],
      programId: [],
      year: [],
      treaty: [],
      cedantCode: [],
      cedant: [],
      country: []
    });
  }

  private searchData(id, year) {
    return this._searchService.searchWorkspace(id || '', year || '2019');
  }

  loadMoreItems() {
    this._loadContracts(String(this.paginationOption.size + 20));
    this.loadingMore = true;
  }

  openWorkspace(wsId, year) {
    this.searchData(wsId, year).subscribe(
      (dt:any) => {
        let workspace = {
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
        console.log(workspace);
        this._helperService.affectItems([workspace])
        this._router.navigate(['/workspace']);
      }
    );
  }

  openWorkspaceInSlider(contract) {
    this.currentWorkspace = contract;
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
      (dt:any) => {
        let workspace = {
          uwYear: wsId,
          workSpaceId: year,
          cedantCode: dt.cedantCode,
          cedantName: dt.cedantName,
          ledgerName: dt.ledgerName,
          subsidiaryId: dt.subsidiaryId,
          subsidiaryName: dt.subsidiaryName,
          treatySections: dt.treatySections,
          workspaceName: dt.worspaceName,
          years: dt.years
        };
        console.log(workspace);
        this._helperService.affectItems([workspace])
        window.open('/workspace/' + wsId);
      }
    );

  }

  private _loadContracts(size = '20') {
    console.log('Try to load contracts');
    console.log(this.searchedItems);
    this.clearSearch();
    if (this.searchedItems.length > 0 ) {
      let keys = [];
      let values = [];
      this.searchedItems.forEach(
        (e) => {
          if ( e.key === 'UW/Year')
            keys.push('year');
          else if ( e.key === 'PROGRAM')
            keys.push('programId')
          else
            keys.push(e.key.toLowerCase());
          values.push(e.value);
        }
      );
      keys.forEach(
        (e , index) => {
          this.contractFilterFormGroup.value[e] = values[index];
        }
      );
    }
    this._searchService.searchContracts(this.contractFilterFormGroup.value, size)
      .subscribe((data: any) => {
        this.contracts = data.content;
        this.loadingMore = false;
        this.paginationOption = {page: data.number, size: data.numberOfElements, total: data.totalElements};
    });
    console.log(this.contracts);
  }

  navigateBack() {
    this._location.back();
  }

  clearChips() {
    this.searchedItems = [];
    this._loadContracts();
  }

  closeSearchBadge(status, index) {
    if (status) {
      this.searchedItems.splice(index, 1);
      this._loadContracts();
    }
  }

}
