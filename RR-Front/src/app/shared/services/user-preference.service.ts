import {ChangeDetectorRef, Injectable} from '@angular/core';
import {Select, Store} from "@ngxs/store";
import {GeneralConfigState} from "../../core/store/states";
import {BaseContainer} from "../base";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class UserPreferenceService {

  @Select(GeneralConfigState.getDateConfig) dateConfig$;
  dateConfig = {
    shortDate: '',
    shortTime: '',
    longDate: '',
    longTime: ''
  };

  constructor() {
    this.dateConfig$.pipe().subscribe(value => {
      this.dateConfig = value;
    })
  }

  getDateConfig() {
    return this.dateConfig;
  }
}
