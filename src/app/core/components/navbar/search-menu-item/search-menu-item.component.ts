import { Component, OnInit } from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {contractsMockData} from "./contracts.mock-data";
import  * as _ from "lodash"

@Component({
  selector: 'search-menu-item',
  templateUrl: './search-menu-item.component.html',
  styleUrls: ['./search-menu-item.component.scss']
})
export class SearchMenuItemComponent implements OnInit {

  contractFilterFormGroup;

  contracts:any = contractsMockData;

  filterUwy= [];
  filterWorkspance= [];

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

  tabularPressHandler() {
    let globalKeywoardFG = this.contractFilterFormGroup.get('globalKeyword');
    let [searchedValue, group1, group2] = globalKeywoardFG.value.match('([a-zA-Z0-9]+):([a-zA-Z0-9]+)');
    let correspondingKey: string = this.sortcutFormKeysMapper[group1];
    if (correspondingKey) {
      this.contractFilterFormGroup.get(correspondingKey).patchValue(group2);
      globalKeywoardFG.patchValue(globalKeywoardFG.value.replace(searchedValue, ''));
      this.contracts = contractsMockData.filter((item: any) => this._specificSearch(item, _.omit(this.contractFilterFormGroup.value, 'globalKeyword')));
    }
  };

  private _specificSearch(item, filter) {
    let items = _.keys(filter).filter(key => filter[key] || false).map(key => new String(item[key]).toLowerCase().includes(new String(filter[key]).toLowerCase()));
    return !_.some(items, e => e == false);
  }

  filterContracts(keyboardEvent: KeyboardEvent) {
    if (keyboardEvent.key == 'Tab' && this.contractFilterFormGroup.get('globalKeyword').value.match('([a-zA-Z0-9]+):([a-zA-Z0-9 ]+)')) {
      event.preventDefault();
      this.tabularPressHandler();
      return;
    }
    if (keyboardEvent.key == 'Tab' && this.contractFilterFormGroup.get('globalKeyword').value == 'clear') {
      event.preventDefault();
      this.contractFilterFormGroup.patchValue({
        globalKeyword: '',
        workspaceId: '',
        workspaceName: '',
        uwYear: '',
        treatyId: '',
        cedantName: ''
      });
      this.contracts = contractsMockData;
      return;
    }
    if (keyboardEvent.key == 'Enter') {
      this.contracts = contractsMockData.filter(item => this._globalSearch(item, this.contractFilterFormGroup.get('globalKeyword').value));
    }
  }

}
