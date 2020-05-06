import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../../../environments/environment";
import {of} from "rxjs";
import {ExposuresMainTableConfig} from "../../model/exposures-main-table-config.model";
import {ExposuresHeaderConfig} from "../../model/exposures-header-config.model";
import {FAKEDATA, HEADERCONFIG} from "../../containers/workspace-exposures/fakeExposuresData";
import {map} from "rxjs/operators";
import {importUrl} from "../../../shared/api";

@Injectable({
    providedIn: 'root'
})
export class ExposuresApi {

    private readonly URL = `${importUrl()}exposure-manager/`;

    private readonly fakeTableConfig: ExposuresMainTableConfig;
    private readonly portfolioDetails: any;
    private readonly divisionDetails: any;

    constructor(private _http: HttpClient) {
        /* this.fakeTableConfig = FAKEDATA;*/
        this.portfolioDetails = 'Portfolio Details';
        this.divisionDetails = 'Division Details';
    }

    loadTableConfig(headerConfig) {
        return this._http.post(this.URL + 'exposure-manager-data', headerConfig)
    }

    loadTableColumnsConfig() {
        return of<any>(this.fakeTableConfig.columns);
    }

    loadRightMenuContent(type: any) {
        console.log('reached API ==> ', type);
        switch (type) {
            case 'portfolio': {
                return this.portfolioDetails;
            }
            case 'division': {
                return this.divisionDetails;
            }
        }
    }

    loadHeaderConfig(projectId) {
        console.log('reached API ==> ', projectId);
        return this._http.get(this.URL + 'references?projectId=' + projectId)

    }

    sortTableColumn(sortConfig) {

    }

    changeCurrency(currency: any) {
        console.log('reached API ==> ', currency);
        return of<ExposuresMainTableConfig>(this.fakeTableConfig);
    }

    changeFinancialUnit(financialUnit: any) {
        console.log('reached API ==> ', financialUnit);
        return of<ExposuresMainTableConfig>(this.fakeTableConfig);
    }

    changeDivision(division: any) {
        console.log('reached API ==> ', division);
        return of<ExposuresMainTableConfig>(this.fakeTableConfig);
    }

    changePortfolio(portfolio: any) {
        console.log('reached API ==> ', portfolio);
        return of<ExposuresMainTableConfig>(this.fakeTableConfig);
    }

    changeView(view: any) {
        console.log('reached API ==> ', view);
        return of<ExposuresMainTableConfig>(this.fakeTableConfig);
    }

    exportTable(headerConfig) {
        console.log(headerConfig);
        return this._http.post(this.URL + 'exposure-manager-export-data',headerConfig);
    }
}
