import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import {Select, Store} from '@ngxs/store';
import {RiskLinkState} from '../../../workspace/store/states';
import {Observable} from 'rxjs';
import {RiskLinkModel} from '../../../workspace/model/risk_link.model';
import * as _ from 'lodash';
import {GeneralConfigState, SearchNavBarState} from '../../../core/store/states';
import {PatchSearchStateAction} from '../../../core/store/actions';
import {GeneralConfig} from '../../../core/model';

@Component({
  selector: 'app-user-preference',
  templateUrl: './user-preference.component.html',
  styleUrls: ['./user-preference.component.scss']
})
export class UserPreferenceComponent implements OnInit {

  searchTarget: any;

  extraDetailDate = false;
  extraDetailTime = false;

  search = ['Country',
    'Cedent Name',
    'Cedant Code',
    'Uw Year',
    'Workspace Name',
    'Workspace Context'
  ];


  defaultImport;

  @Select(GeneralConfigState)
  state$: Observable<GeneralConfig>;
  state: GeneralConfig = null;

  constructor(public location: Location, public store$: Store) { }

  ngOnInit() {
    this.defaultImport = localStorage.getItem('importConfig');

    this.store$.select(SearchNavBarState.getSearchTarget).subscribe(
      value => this.searchTarget = value
    );

    this.state$.subscribe(value => this.state = _.merge({}, value));
  }

  navigateBack() {
    this.location.back();
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.search, event.previousIndex, event.currentIndex);
  }

  defaultImportChange() {
    localStorage.setItem('importConfig', this.defaultImport);
  }

  changePerspective() {
  }

  returnToConf() {
    this.extraDetailDate = false;
    this.extraDetailTime = false;
  }

  changeSearch(event) {
    this.store$.dispatch(new PatchSearchStateAction({key: 'searchTarget', value: event}));
  }

}
