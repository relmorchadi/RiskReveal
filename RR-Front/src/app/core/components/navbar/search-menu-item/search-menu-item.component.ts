import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import * as _ from 'lodash';
import {SearchService} from "../../../service/search.service";
import {catchError, debounceTime, map} from "rxjs/operators";
import { NzNotificationService } from 'ng-zorro-antd';
import { forkJoin } from 'rxjs';
import { Router } from '@angular/router';


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
  choices = [];
  actualGlobalKeyword = '';
  keywordBackup = '';
  searchvalue = '';
  /* tslint:disable */
  searchBadges = [
    {id: 'cedant', icon: 'icon-window-section', title: 'Cedant', shortcut: 'c', backgroundColor: '#005738', badgeBackgroundColor: '#64D4C6', badgeIcon: 'icon-bookmark_24px'},
    {id: 'country', icon: 'icon-globe', title: 'Country', shortcut: 'ctr', backgroundColor: '#008464', badgeBackgroundColor: '#64D4C6', badgeIcon: 'icon-bookmark_24px'},
    {id: 'year', icon: 'icon-calendar_today_24px', title: 'Year', shortcut: 'uwy', backgroundColor: '#00A282', badgeBackgroundColor: '#64D4C6', badgeIcon: 'icon-bookmark_24px'},
    {id: 'program', icon: 'icon-layers-alt', title: 'Program', shortcut: 'p', backgroundColor: '#00C4AA', badgeBackgroundColor: '#64D4C6', badgeIcon: 'icon-bookmark_24px'},
    {id: 'treaty', icon: 'icon-book', title: 'Treaty', shortcut: 't', backgroundColor: '#03DAC4', badgeBackgroundColor: '#64D4C6', badgeIcon: 'icon-bookmark_24px'},
    {id: 'analyst', icon: 'icon-user', title: 'Analyst', shortcut: 'a', backgroundColor: '#F5A623', badgeBackgroundColor: '#64D4C6', badgeIcon: 'icon-bookmark_24px'},
    {id: 'project', icon: 'icon-folder-check', title: 'Project ID', shortcut: 'prj', backgroundColor: '#0700CF', badgeBackgroundColor: '#64D4C6', badgeIcon: 'icon-bookmark_24px'},
    {id: 'plt', icon: 'icon-box', title: 'PLT', shortcut: 'plt', backgroundColor: '#C38FFF', badgeBackgroundColor: '#64D4C6', badgeIcon: 'icon-bookmark_24px'},
  ];

  readonly refsLoader  = {
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
  };

  tables = ['CEDANT' , 'COUNTRY', 'TREATY', 'YEAR'];

  Badges = [];
  data = [];
  RecentSearch = [];
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

  constructor(private _fb: FormBuilder, private _searchService: SearchService, private router:Router, private notification: NzNotificationService) {
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
    this.RecentSearch = JSON.parse(localStorage.getItem('items'));
    this.showRecentSearch = this.RecentSearch.slice(0,3);
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
      this.Badges.push(instance);
    }
    else {
      this.notification.config({
        nzPlacement: 'bottomRight'
      })
      this.notification.create(
          'error', 'Information',
          'some shortcuts were false please check the shortcuts or change them!',
      );
    }
    this.contractFilterFormGroup.value['globalKeyword']=''
    return '';
  }

  private _specificSearch(item, filter) {
    let items = _.keys(filter).filter(key => filter[key] || false).map(key => new String(item[key]).toLowerCase().includes(new String(filter[key]).toLowerCase()));
    return !_.some(items, e => e == false);
  }

  filterContracts(keyboardEvent) {
    this._clearFilters();
    if (keyboardEvent.key == 'Enter') {
      if(this.contractFilterFormGroup.value['switchValue']){
        let searchExpression = this.contractFilterFormGroup.get('globalKeyword').value;
        this.examinateExpression(searchExpression);
      }
      event.preventDefault();
      this.redirectTosearchPage();
    }
    if (this.deleteBlock === true){
      if (keyboardEvent.key == 'Backspace' && keyboardEvent.target.value === '') {
        this.deleteBlock = false;
        console.log(this.deleteBlock);
      }
    } else {
      if (keyboardEvent.key == 'Backspace' && keyboardEvent.target.value === '' ) {
        this.Badges.pop();
        this.deleteBlock = true;
        console.log(this.deleteBlock);
      }
    }
    if (keyboardEvent.key === 'Delete'  && keyboardEvent.target.value === '') {
      this.Badges.pop();
    }
    /* if(this.currentBadge)
    this.selectChoice({label: this.contractFilterFormGroup.get('globalKeyword').value})*/
  }

  redirectTosearchPage(){
    if (this.Badges.length > 0)
    {
      this.RecentSearch = [[...this.Badges], ...this.RecentSearch];
      console.log([...this.Badges]);
      this._searchService.affectItems([...this.Badges]);
      localStorage.setItem('items', JSON.stringify(this.RecentSearch));
      this.showRecentSearch = this.RecentSearch.slice(0,3);
    }
    this.Badges = [];
    this.contractFilterFormGroup.patchValue({globalKeyword: ''});
    this.visible = false;
    this.visibleSearch = false;
    this.router.navigate(['/search']);
  }

  redirectwithsearch(items) {
    console.log(items);
    this._searchService.affectItems(items);
    this.visibleSearch = false;
    this.visible = false;
    this.router.navigate(['/search']);
  }

  searchLoader (keyword, table) {
    return this._searchService.searchByTable(keyword || '', '5', table || '');
  };

  selectSearchBadge(key, value) {
    let item = {key: key,value: value};
    this.showLastSearch = true;
    this.showResult = false;
    this.Badges.push(item);
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
      payload.forEach(element => {
        this.data = [...this.data, element.content];
      });
      let len = this.data.length;
      this.data = this.data.slice(len - 4, len);
      console.log(this.data)
    })
  }



  closeSearchBadge(status, index) {
    if(status){
      this.Badges.splice(index, 1);
    }
  }

  enableExpertMode() {
    this.notification.config({
      nzPlacement: 'bottomRight'
    })
    if(this.contractFilterFormGroup.value['switchValue']){
      this.visibleSearch = false;
      this.notification.create(
        'info', 'Information',
        'the export mode is now enabled',
        { nzDuration: 2000 }
      );
    } else {
      this.notification.create(
        'info', 'Information',
        'the export mode is now disabled',
        { nzDuration: 2000 }
      );
    }
  }

  /*
  selectChoice(choice) {
    let searchExpression:string = `${this.keywordBackup?this.keywordBackup+' ':''}${this.currentBadge.shortcut}:"${choice.label}"`;
    this.contractFilterFormGroup.patchValue({globalKeyword: searchExpression});
    this.currentBadge=null;
  }*/

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
    } else {
      this.visibleSearch = false;
    }
    this.visible = false;
  }

  openclose(): void {
    this.visible = !this.visible;
  }
  clearvalue(): void {
    this.searchvalue = '';
    this.showResult = false;
    this.visibleSearch = false;
    this.visible = false;
    this.showClearIcon = false;
    this._clearFilters();
    this.Badges = [];
  }
}
