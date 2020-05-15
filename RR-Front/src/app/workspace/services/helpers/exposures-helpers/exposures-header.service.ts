/*
 * Date : 20/2/2020.
 * Author : Reda El Morchadi
 */

import {Injectable} from '@angular/core';
import {Observable, BehaviorSubject, forkJoin, of} from "rxjs";
import {ExposuresApi} from "../../api/exposures.api";
import {ExposuresHeaderConfig} from "../../../model/exposures-header-config.model";
import {HEADERCONFIG} from "../../../containers/workspace-exposures/fakeExposuresData";
import {first, map} from "rxjs/operators";

@Injectable()
export class ExposuresHeaderService {


    headerConfig$: BehaviorSubject<ExposuresHeaderConfig>;


    constructor(private _api: ExposuresApi) {
        this.headerConfig$ = new BehaviorSubject<ExposuresHeaderConfig>(new ExposuresHeaderConfig())
    }

    loadHeaderConfig(projectId){
        return this._api.loadHeaderConfig(projectId).pipe(map((headerConfigApi: any) =>
            ({
                ...headerConfigApi,
                exposureViews: HEADERCONFIG.exposureViews,
                financialUnits:HEADERCONFIG.financialUnits,
                financialPersp:['GU']
            })
        ))
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

    handleHeaderConfig(apiData: any) {
        return
    }

}