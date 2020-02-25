/*
 * Date : 20/2/2020.
 * Author : Reda El Morchadi
 */

import {Injectable} from '@angular/core';
import {Observable, BehaviorSubject, forkJoin, of} from "rxjs";
import {ExposuresApi} from "../../api/exposures.api";
import {ExposuresHeaderConfig} from "../../../model/exposures-header-config.model";

@Injectable()
export class ExposuresHeaderService {


    headerConfig$: BehaviorSubject<ExposuresHeaderConfig>;


    constructor(private _api: ExposuresApi) {
        this.headerConfig$ = new BehaviorSubject<ExposuresHeaderConfig>(new ExposuresHeaderConfig())
    }

    loadHeaderConfig() {
        return this._api.loadHeaderConfig();
    }

    changeCurrency(currency: any) {
        return this._api.changeCurrency(currency);
    }

    changeFinancialUnit(financialUnit: any) {
        return this._api.changeFinancialUnit(financialUnit);
    }

    changeDivision(division: any) {
        return this._api.changeDivision(division);
    }

    changePortfolio(portfolio: any) {
        return this._api.changePortfolio(portfolio);
    }

    changeView(view: any) {
        return this._api.changeView(view);
    }

}