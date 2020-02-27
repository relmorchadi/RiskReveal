import {ChangeDetectorRef, Injectable} from '@angular/core';
import {Select, Store} from "@ngxs/store";
import {GeneralConfigState} from "../../core/store/states";
import {BaseContainer} from "../base";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class UserPreferenceService extends BaseContainer {

  @Select(GeneralConfigState.getDateConfig) dateConfig$;
  dateConfig;

  constructor(_baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);

    this.dateConfig$.pipe().subscribe(value => {
      this.dateConfig = value;
      this.detectChanges();
    })
  }
}
