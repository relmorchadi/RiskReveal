import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import {Select, Store} from "@ngxs/store";
import {RiskLinkState} from "../../../workspace/store/states";
import {Observable} from "rxjs";
import {RiskLinkModel} from "../../../workspace/model/risk_link.model";
import * as _ from "lodash";

@Component({
  selector: 'app-user-preference',
  templateUrl: './user-preference.component.html',
  styleUrls: ['./user-preference.component.scss']
})
export class UserPreferenceComponent implements OnInit {
  defaultImport;

  search = ['Country',
    'Cedent Name',
    'Cedant Code',
    'Uw Year',
    'Workspace Name',
    'Workspace Context'
  ];

  @Select(RiskLinkState.getFinancialValidator)
  financialValidator$: Observable<any>;
  rmsInstance: any;
  financialPerspectiveELT: any;
  financialPerspectiveEPM: any;
  targetCurrency: any;

  dateFormat: any;
  timeFormat: any;

  constructor(public location: Location, public store$: Store) { }

  ngOnInit() {
    this.defaultImport = localStorage.getItem('importConfig');

    this.store$.select(RiskLinkState.getFinancialValidatorAttr('financialValidator', null)).subscribe(
      value => {
        this.rmsInstance = value.rmsInstance;
        this.financialPerspectiveELT = value.financialPerspectiveELT;
        this.financialPerspectiveEPM = value.financialPerspectiveEPM ;
        this.targetCurrency = value.targetCurrency;
      }
    );
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

}
