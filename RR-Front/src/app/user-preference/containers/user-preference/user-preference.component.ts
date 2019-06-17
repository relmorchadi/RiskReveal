import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import {Select, Store} from '@ngxs/store';
import {RiskLinkState} from '../../../workspace/store/states';
import {Observable} from 'rxjs';
import {RiskLinkModel} from '../../../workspace/model/risk_link.model';
import * as _ from 'lodash';
import {GeneralConfigState, SearchNavBarState} from '../../../core/store/states';
import {PatchNumberFormatAction, PatchSearchStateAction} from '../../../core/store/actions';
import {GeneralConfig} from '../../../core/model';

@Component({
  selector: 'app-user-preference',
  templateUrl: './user-preference.component.html',
  styleUrls: ['./user-preference.component.scss']
})
export class UserPreferenceComponent implements OnInit {

  searchTarget: any;
  testNumber: 1231024;

  search = ['Country',
    'Cedent Name',
    'Cedant Code',
    'Uw Year',
    'Workspace Name',
    'Workspace Context'
  ];

  check1 = false;
  check2 = false;
  numberCollapse = false;
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

  numberFormat() {
    return `1.${this.state.general.numberFormat.numberOfDecimals}-${this.state.general.numberFormat.numberOfDecimals}`;
  }

  number_format(num, decimals, decPoint, thousandsSep) {
    const newNum = !isFinite(+num) ? 0 : +num,
      prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
      sep = (typeof thousandsSep === 'undefined') ? ',' : thousandsSep,
      dec = (typeof decPoint === 'undefined') ? '.' : decPoint,
      toFixedFix = (n, v) => {
        // Fix for IE parseFloat(0.55).toFixed(0) = 0;
        const k = Math.pow(10, v);
        return Math.round(n * k) / k;
      },
      s = (prec ? toFixedFix(newNum, prec) : Math.round(newNum)).toString().split('.');
    if (s[0].length > 3) {
      s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
    }
    if ((s[1] || '').length < prec) {
      s[1] = s[1] || '';
      s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
  }

  changePerspective() {
  }

  changeNumberFormat(event, target) {
    this.store$.dispatch(new PatchNumberFormatAction({target: target, value: event}));
  }

  changeContract(event, target) {

  }

  changeSearch(event) {
    this.store$.dispatch(new PatchSearchStateAction({key: 'searchTarget', value: event}));
  }

}
