
/*
 * Date : 20/2/2020.
 * Author : Reda El Morchadi
 */

import {AfterViewInit, Component, EventEmitter, Input, OnDestroy, OnInit, Output} from "@angular/core";
import {Store} from "@ngxs/store";
import {Observable, Subscription} from "rxjs";
import {ExposuresMainTableConfig} from "../../../model/exposures-main-table-config.model";

@Component({
    selector: 'exposures-main-table',
    templateUrl: './exposures-main-table.component.html',
    styleUrls: ['./exposures-main-table.component.scss']
})

export class ExposuresMainTableComponent implements OnInit,AfterViewInit, OnDestroy {


    @Input('tableConfig') tableConfig:ExposuresMainTableConfig;
    @Output('actionDispatcher') actionDispatcher: EventEmitter<any> = new EventEmitter<any>();




    constructor(private store: Store) {

    }


    ngOnInit(): void {

    }

    ngAfterViewInit(): void {

    }

    ngOnDestroy(): void {

    }


    onMouseEnter(rowData) {
        rowData.hover = true;
    }

    onMouseLeave(rowData) {
        rowData.hover = false;
    }

    onSort($event: any) {
        this.actionDispatcher.emit({field: $event.field, order: $event.order})
    }
}