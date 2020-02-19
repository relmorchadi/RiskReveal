import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from "@angular/core";
import {ExposuresHeaderConfig} from "../../../model/exposures-header-config.model";

@Component({
    selector: 'exposures-header',
    templateUrl: './exposures-header.component.html',
    styleUrls: ['./exposures-header.component.scss']
})
export class ExposuresHeaderComponent implements OnInit, OnDestroy {
    
    @Input("headerConfig") headerConfig: ExposuresHeaderConfig;
    @Output('actionDispatcher') actionDispatcher: EventEmitter<any> = new EventEmitter<any>();

    constructor(){

    }


    ngOnInit(): void {
    }

    ngOnDestroy(): void {
    }


    changeCurrencie(currency: any) {
        
    }

    financialUnitChange(financialUnit: any) {
        
    }

    changeDivision(division: any) {
        
    }

    changePortfolio(portfolio: any) {
        
    }

    onViewChange(view: string) {
        
    }
}