import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import * as _ from 'lodash';
import {SearchService} from "../../../service/search.service";
import {catchError, debounceTime, map} from "rxjs/operators";
import {NotificationService} from "../../../../shared/notification.service";

import { Router } from '@angular/router';
import {forkJoin} from "rxjs/internal/observable/forkJoin";


@Component({
  selector: 'search-menu-item',
  templateUrl: './search-menu-item.component.html',
  styleUrls: ['./search-menu-item.component.scss']
})
export class SearchMenuItemComponent implements OnInit {

  @ViewChild('searchInput')
  searchInput: ElementRef;
  contractFilterFormGroup: FormGroup;
  contracts: any;
  showResult = false;
  showLastSearch = false;
  visible = false;
  visibleSearch = false;
  deleteBlock = true;
  showClearIcon = false;
  actualGlobalKeyword = '';
  keywordBackup = '';
  searchValue = '';
  /* tslint:disable */

  /*readonly refsLoader  = {
    cedant: (keyword) => {
      return this._searchService.searchCedent(keyword || '');
    },
    country: (keyword) => {
      return this._searchService.searchCountry(keyword || '');
    },
    year: (keyword) => {
      return this._searchService.searchYear(keyword || '');
    },
    treaty: (keyword) => {
      return this._searchService.searchTreaty(keyword || '');
    },
    data: (keyword, size, table) => {
      return this._searchService.searchByTable(keyword || '',size || '', table || '');
    }
  };*/

  tables = ['CEDANT' , 'COUNTRY', 'TREATY', 'YEAR'];

  badges = [];
  data = [];
  recentSearch = [];
  showRecentSearch = [];

  savedSearch = [
    [ {key:'Cedant', value:'HDI Global'}, {key:'UW/Year', value:'2019'}],
    [ {key:'Cedant', value:'Tokio'}, {key:'Country', value:'Japan'}, {key:'UW/Year', value:'2019'}],
    [ {key:'Country', value:'Japan'},  {key:'Program', value:'Prog Name'}]
  ];

  tagShortcuts = [
    {tag:'Cedant', value:'To be defined'},
    {tag:'Country', value:'ctr:'},
    {tag:'Contract', value:'C:'},
    {tag:'Project', value:'C:'},
    {tag:'Program', value:'C:'},
    {tag:'PLT', value:'C:'},
    {tag:'Section', value:'C:'},
    {tag:'Subsidiary', value:'C:'},
    {tag:'Ledger', value:'C:'},
    {tag:'Bouquet', value:'C:'},
    {tag:'Workspace', value:'wid:'},
    {tag:'UW Year', value:'uwy:'},
    {tag:'UW Unit', value:'C:'},
  ];

  sortcutFormKeysMapper = {
    c: 'cedant',
    uwy: 'year',
    tid: 'treaty',
    wn: 'workspaceName',
    wid: 'workspaceId',
    ctr: 'country'
  };

  constructor(private _fb: FormBuilder, private _searchService: SearchService, private router:Router, private _notifcationService:NotificationService) {
    this.contractFilterFormGroup = this._fb.group({
      switchValue: false,
      globalKeyword: [],
      workspaceId: [],
      workspaceName: [],
      year: [],
      treaty: [],
      cedant: [],
      country: []
    });
  }

  ngOnInit() {
    this._loadContracts();
    this.contractFilterFormGroup.get("globalKeyword")
      .valueChanges
      .pipe(debounceTime(500))
      .subscribe((param) => {
        this._selectedSearch(param)
    });
    this.recentSearch = JSON.parse(localStorage.getItem('items')) || [];
    this.showRecentSearch = this.recentSearch.slice(0,3);
  }


  private _loadContracts(){
    this._searchService.searchContracts(this.contractFilterFormGroup.value)
      .subscribe((data:any) => this.contracts= data.content );
  }

  isSearchRoute() {
    return window.location.href.match('search');
  }

  examinateExpression(expression: string) {
    if (this.contractFilterFormGroup.value['switchValue']) {
      // let regExp = /([a-zA-Z0-9]+):"([^"]*)"/g;
      let regExp = /(\w*:){1}((\w*\s)*)/g
      let globalKeyword = `${expression} `.replace(regExp, (match, shortcut, keyword ) => {
        console.log({shortcut, keyword})
        return this.toBadges(_.trim(shortcut,':'), _.trim(keyword));
      }).trim();
      console.log('this is global keyword', globalKeyword);
      this.actualGlobalKeyword = globalKeyword;
    }
    else {
      console.log('this is global keyword', expression);
      this.actualGlobalKeyword = expression;
    }
    this._loadContracts();

    // let regExp = /([a-zA-Z]+):([a-zA-Z0-9]+)([^ ])/g
    // this.contracts = contractsMockData.filter((item: any) => this._specificSearch(item, _.omit(this.contractFilterFormGroup.value, 'globalKeyword')));
    // if (globalKeyword && globalKeyword.length)
    //   this.contracts = this.contracts.filter(item => this._globalSearch(item, globalKeyword));
  };

  toBadges(shortcut, keyword) {
    let correspondingKey: string = this.sortcutFormKeysMapper[shortcut];
    if (correspondingKey) {
      this.contractFilterFormGroup.get(correspondingKey).patchValue(keyword)
      let instance = {key: correspondingKey, value: keyword};
      this.badges.push(instance);
    }
    else {
      this._notifcationService.createNotification('Information',
        'some shortcuts were false please check the shortcuts or change them!',
        'error','bottomRight',4000);
    }
    this.contractFilterFormGroup.value['globalKeyword']=''
    return '';
  }

  filterContracts(keyboardEvent) {
    this._clearFilters();
    if (keyboardEvent.key == 'Enter') {
      if(this.contractFilterFormGroup.value['switchValue']){
        let searchExpression = this.contractFilterFormGroup.get('globalKeyword').value;
        this.examinateExpression(searchExpression);
      }
      event.preventDefault();
      this.redirectToSearchPage();
    }
    if (this.deleteBlock === true){
      if (keyboardEvent.key == 'Backspace' && keyboardEvent.target.value === '') {
        this.deleteBlock = false;
        console.log(this.deleteBlock);
      }
    } else {
      if (keyboardEvent.key == 'Backspace' && keyboardEvent.target.value === '' ) {
        this.badges.pop();
        this.deleteBlock = true;
        console.log(this.deleteBlock);
      }
    }
    if (keyboardEvent.key === 'Delete'  && keyboardEvent.target.value === '') {
      this.badges.pop();
    }
    /* if(this.currentBadge)
    this.selectChoice({label: this.contractFilterFormGroup.get('globalKeyword').value})*/
  }

  redirectToSearchPage(){
    if (this.badges.length > 0)
    {
      this.recentSearch = [[...this.badges], ...this.recentSearch];
      console.log([...this.badges]);
      this._searchService.affectItems([...this.badges]);
      localStorage.setItem('items', JSON.stringify(this.recentSearch));
      this.showRecentSearch = this.recentSearch.slice(0,3);
    }
    this.contractFilterFormGroup.patchValue({globalKeyword: ''});
    this.clearValue();
    this.router.navigate(['/search']);
  }

  redirectWithSearch(items) {
    this._searchService.affectItems(items);
    this.clearValue();
    this.router.navigate(['/search']);
  }

  searchLoader (keyword, table) {
    return this._searchService.searchByTable(keyword || '', '5', table || '');
  };

  selectSearchBadge(key, value) {
    let item = {key: key,value: value};
    this.showLastSearch = true;
    this.showResult = false;
    this.badges.push(item);
    // let {globalKeyword} = this.contractFilterFormGroup.value
    this.keywordBackup= this.contractFilterFormGroup.get('globalKeyword').value;
    this.contractFilterFormGroup.patchValue({globalKeyword: ''});
    this._selectedSearch(this.contractFilterFormGroup.get('globalKeyword').value);
  }

  private _selectedSearch(keyword) {
    this.data = [];
    forkJoin(
      ...this.tables.map(
        tableName => this.searchLoader(keyword, tableName)
        )
    ).subscribe( payload => {
      this.data = _.concat(this.data,_.map(payload,'content'));
      let len = this.data.length;
      this.data = this.data.slice(len - 4, len);
      console.log(this.data)
    })
  }

  closeSearchBadge(status, index) {
    if(status){
      this.badges.splice(index, 1);
    }
  }

  enableExpertMode() {
    if(this.contractFilterFormGroup.value['switchValue']){
      this.visibleSearch = false;
      this._notifcationService.createNotification('Information',
        'the export mode is now enabled',
        'info','bottomRight',2000);
    } else {
      this._notifcationService.createNotification('Information',
        'the export mode is now disabled',
        'info','bottomRight',2000);
    }
  }

  private _clearFilters() {
    this.contractFilterFormGroup.patchValue({
      // globalKeyword: '',
      workspaceId: '',
      workspaceName: '',
      year: '',
      treaty: '',
      cedant: '',
      country: ''
    });
  }

  private _globalSearch(item, keyword) {
    if (keyword == null || keyword == '')
      return true;
    return _.some(_.values(item), value => new String(value).toLowerCase().includes(new String(keyword).toLowerCase()));
  }

  focusInput(event) {
    if (this.contractFilterFormGroup.value['switchValue']) {
      this.showLastSearch = true;
      this.showResult = false;
    } else {
      if (event.target.value === '' || event.target.value.length < 2){
        this.showLastSearch = true;
      } else {
        this.showResult = true;
        this.showLastSearch = false;
      }
    }
    this.visible = false;
  }

  onInput(event) {
    event.target.value === '' ? this.showClearIcon = false : this.showClearIcon =  true;
    if (!this.contractFilterFormGroup.value['switchValue']){
      if(event.target.value === '' || event.target.value.length < 2) {
        this.showLastSearch = true;
        this.showResult = false;
      } else {
        this.showLastSearch = false;
        let searchExpression = this.contractFilterFormGroup.get('globalKeyword').value;
        this.examinateExpression(searchExpression);
        this.showResult = true;
      }
      this.visibleSearch = true;
    } else {
      this.visibleSearch = false;
    }
    this.visible = false;
  }

  openClose(): void {
    this.visible = !this.visible;
  }

  clearValue(): void {
    this.searchValue = '';
    this.showResult = false;
    this.visibleSearch = false;
    this.visible = false;
    this.showClearIcon = false;
    this._clearFilters();
    this.badges = [];
  }
}
