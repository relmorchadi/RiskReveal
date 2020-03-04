
/*
 * Date : 20/2/2020.
 * Author : Reda El Morchadi
 */

import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from "@angular/core";
import {ExposuresHeaderConfig} from "../../../model/exposures-header-config.model";

@Component({
    selector: 'exposures-header',
    templateUrl: './exposures-header.component.html',
    styleUrls: ['./exposures-header.component.scss']
})
export class ExposuresHeaderComponent implements OnInit, OnDestroy {
    
    @Input("headerConfig") headerConfig: ExposuresHeaderConfig;
    @Output("actionDispatcher") actionDispatcher: EventEmitter<any> = new EventEmitter<any>();

    constructor(){

    }


    ngOnInit(): void {

    }

    ngOnDestroy(): void {
    }


    changeCurrency(currency: any) {
        
    }

    financialUnitChange(financialUnit: any) {
        
    }

    changeDivision(division: any) {
        
    }

    changePortfolio(portfolio: any) {
        
    }

    changeView(view: any) {

    }

    openPortfolioDetails() {
        this.actionDispatcher.emit({type:'openPortfolioDetails'});
    }

    openDivisionDetails() {
        this.actionDispatcher.emit({type:'openDivisionDetails'});
    }


}