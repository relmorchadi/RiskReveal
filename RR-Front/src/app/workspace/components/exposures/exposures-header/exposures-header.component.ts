/*
 * Date : 20/2/2020.
 * Author : Reda El Morchadi
 */

import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from "@angular/core";
import * as _ from 'lodash';
import {ExposuresHeaderConfig} from "../../../model/exposures-header-config.model";

@Component({
    selector: 'exposures-header',
    templateUrl: './exposures-header.component.html',
    styleUrls: ['./exposures-header.component.scss']
})
export class ExposuresHeaderComponent implements OnInit, OnDestroy {
    @Output("actionDispatcher") actionDispatcher: EventEmitter<any> = new EventEmitter<any>();

    @Input("selectedHeaderConfig") selectedHeaderConfig: any;
    @Input("headerConfig") headerConfig: any;

    constructor() {

    }

    ngOnInit(): void {

    }

    ngOnDestroy(): void {

    }

    changeCurrency(currency: any) {
        this.actionDispatcher.emit({type: 'changeCurrency', payload: currency});
    }

    changeFinancialUnit(financialUnit: any) {
        this.actionDispatcher.emit({type: 'changeFinancialUnit', payload: financialUnit});
    }

    changeFinancialPerspecctive(financialPerspective: any) {
        this.actionDispatcher.emit({type: 'changeFinancialPerspecctive', payload: financialPerspective});
    }

    changeDivision(division: any, id: number) {
        this.actionDispatcher.emit({type: 'changeDivision', payload: {division, divisionNum: id}});
    }

    changePortfolio(portfolio: any) {
        this.actionDispatcher.emit({type: 'changePortfolio', payload: portfolio});
    }

    changeView(view: any) {
        this.actionDispatcher.emit({type: 'changeView', payload: view});
    }

    openPortfolioDetails() {
        this.actionDispatcher.emit({type: 'openPortfolioDetails', payload: null});
    }

    openDivisionDetails() {
        this.actionDispatcher.emit({type: 'openDivisionDetails', payload: null});
    }

    exportExposuresTable() {
        this.actionDispatcher.emit({type: 'exportExposuresTable', payload: null})
    }

    downloadIhubFiles() {
        this.actionDispatcher.emit({type: 'downloadIhubFiles', payload: null})
    }

    downloadYoyReport() {
        this.actionDispatcher.emit({type: 'downloadYoyReport', payload: null})
    }

    filterPortfoliosByDivision() {
        return  _.filter(this.headerConfig.portfolios, item => {
            const portfolios = _.keys(_.get(this.headerConfig.portfoliosAndCurrenciesByDivision, `${this.selectedHeaderConfig.division.divisionNumber}`));
            return _.includes(portfolios, item)
        })
    }
}