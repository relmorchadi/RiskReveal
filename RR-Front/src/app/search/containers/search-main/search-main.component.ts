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
  loadingMore = false;
  searchedItems = [];
  currentWorkspace= null;

  constructor(private _fb: FormBuilder, private _searchService: SearchService, private _helperService: HelperService,
              private _router: Router,private _location: Location) {
      this.clearSearch();
  }

  ngOnInit() {
    this.searchedItems = this._searchService.getSearchedItems();
    this._searchService.items.subscribe(
      () =>  {
        this.searchedItems = [...this._searchService.getSearchedItems()];
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
      year: [],
      treaty: [],
      cedantCode: [],
      cedant: [],
      country: []
    });
  }

  loadMoreItems() {
    this._loadContracts(String(this.paginationOption.size + 20));
    this.loadingMore = true;
  }

  openWorkspace(contract) {
    this._helperService
      .openWorkspaces.next(contract);
    this._router.navigate(['/workspace']);
  }

  openWorkspaceInSlider(contract){
    this.currentWorkspace= contract;
    this.expandWorkspaceDetails= true;
  }

  popUpWorkspace(contract) {
    window.open('/workspace/' + contract.id);
  }

  private _loadContracts(size = '20') {
    console.log('Try to load contracts');
    this.clearSearch();
    if (this.searchedItems.length > 0 ) {
      let keys = [];
      let values = [];
      this.searchedItems.forEach(
        (e) => {
          if ( e.key === 'UW/Year') {
            keys.push('year');
          } else {
            keys.push(e.key.toLowerCase());
          }
          values.push(e.value);
        }
      );
      keys.forEach(
        (e , index) => {
          this.contractFilterFormGroup.value[e] = values[index];
        }
      );
    }
    console.log(this.contractFilterFormGroup.value);
    this._searchService.searchContracts(this.contractFilterFormGroup.value, size)
      .subscribe((data: any) => {
        this.contracts = data.content;
        this.loadingMore = false;
        this.paginationOption = {page: data.number, size: data.numberOfElements, total: data.totalElements};
    });
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
