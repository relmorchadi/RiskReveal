import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {contractsMockData} from './contracts.mock-data';
import * as _ from 'lodash';


@Component({
  selector: 'search-menu-item',
  templateUrl: './search-menu-item.component.html',
  styleUrls: ['./search-menu-item.component.scss']
})
export class SearchMenuItemComponent implements OnInit {

  @ViewChild('searchPopupBtn')
  searchPopupBtn: ElementRef;

  contractFilterFormGroup;

  contracts: any = contractsMockData;

  showResult = false ;

  filterUwy = _.uniqBy(contractsMockData.map((item: any) => ({text: item.uwYear, value: item.uwYear})), 'value');
  filterWorkspance = _.uniqBy(contractsMockData.map((item: any) => ({text: item.workspaceId, value: item.workspaceId})), 'value');

  actualGlobalKeyword = '';

  badgesList = [
    {icon: 'icon-window-section', title: 'Cedant', shortcut: 'c', backgroundColor: '#005738'},
    {icon: 'icon-globe', title: 'Country', shortcut: '', backgroundColor: '#008464'},
    {icon: 'icon-calendar_today_24px', title: 'Year', shortcut: '', backgroundColor: '#00A282'},
    {icon: 'icon-layers-alt', title: 'Program', shortcut: '', backgroundColor: '#00C4AA'},
    {icon: 'icon-book', title: 'Treaty', shortcut: '', backgroundColor: '#03DAC4'},
    {icon: 'icon-user', title: 'Analyst', shortcut: '', backgroundColor: '#F5A623'},
    {icon: 'icon-folder-check', title: 'Project ID', shortcut: '', backgroundColor: '#0700CF'},
    {icon: 'icon-box', title: 'PLT', shortcut: '', backgroundColor: '#C38FFF'},
  ];

  constructor(private _fb: FormBuilder) {
    this.contractFilterFormGroup = this._fb.group({
      globalKeyword: [],
      workspaceId: [],
      workspaceName: [],
      uwYear: [],
      treatyId: [],
      cedantName: []
    });
  }

  sortcutFormKeysMapper = {
    c: 'cedantName',
    uwy: 'uwYear',
    tid: 'treatyId',
    wn: 'workspaceName',
    wid: 'workspaceId'
  };

  ngOnInit() {

  }

  isSearchRoute() {
    return window.location.href.match('search');
  }

  examinateExpression(expression: string) {
    let regExp = /([a-zA-Z0-9]+):"([a-zA-Z0-9 ]+)"/g;
    let globalKeyword = expression.replace(regExp, (match, shortcut, keyword) => {
      console.log('toto', shortcut, keyword);
      let correspondingKey: string = this.sortcutFormKeysMapper[shortcut];
      correspondingKey ? this.contractFilterFormGroup.get(correspondingKey).patchValue(keyword) : null;
      return '';
    }).trim();
    this.actualGlobalKeyword = globalKeyword;
    this.contractFilterFormGroup.patchValue(globalKeyword);
    console.log('this is contract filter from group', this.contractFilterFormGroup.value);
    console.log('this is global keywork', globalKeyword);
    this.contracts = contractsMockData.filter((item: any) => this._specificSearch(item, _.omit(this.contractFilterFormGroup.value, 'globalKeyword')));
    if (globalKeyword && globalKeyword.length)
      this.contracts = this.contracts.filter(item => this._globalSearch(item, globalKeyword));
  };

  updateFilter(searchValues, key) {
    if (searchValues == null || searchValues.length == 0)
      this.contracts = contractsMockData;
    else
      this.contracts = _.filter(contractsMockData, (item) => _.includes(searchValues, item[key]));
  }

  private _specificSearch(item, filter) {
    let items = _.keys(filter).filter(key => filter[key] || false).map(key => new String(item[key]).toLowerCase().includes(new String(filter[key]).toLowerCase()));
    return !_.some(items, e => e == false);
  }

  filterContracts(keyboardEvent: KeyboardEvent) {
    console.log('Press', keyboardEvent);
    this._clearFilters();

    if (keyboardEvent.key == 'Enter') {
      let searchExpression = this.contractFilterFormGroup.get('globalKeyword').value;
      this.examinateExpression(searchExpression);
      this.searchPopupBtn.nativeElement.click();
      this.showResult = true;
    }else{
      this.showResult = false;
    }

    /*
    if (keyboardEvent.key == 'Enter' && this.contractFilterFormGroup.get('globalKeyword').value.match('([a-zA-Z0-9]+):([a-zA-Z0-9 ]+)')) {
      event.preventDefault();
      this.tabularPressHandler();
      return;
    }
    if (keyboardEvent.key == 'Tab' && this.contractFilterFormGroup.get('globalKeyword').value == 'clear') {
      event.preventDefault();
      return;
    }
    if (keyboardEvent.key == 'Enter') {
      this.contracts = contractsMockData.filter(item => this._globalSearch(item, this.contractFilterFormGroup.get('globalKeyword').value));
    }
    */
  }

  private _clearFilters() {
    this.contractFilterFormGroup.patchValue({
      //globalKeyword: '',
      workspaceId: '',
      workspaceName: '',
      uwYear: '',
      treatyId: '',
      cedantName: ''
    });
    this.contracts = contractsMockData;
  }

  private _globalSearch(item, keyword) {
    if (keyword == null || keyword == '')
      return true;
    return _.some(_.values(item), value => new String(value).toLowerCase().includes(new String(keyword).toLowerCase()));
  }

}
