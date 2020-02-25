/*
 * Date : 20/2/2020.
 * Author : Reda El Morchadi
 */

import {AfterViewInit, Component, EventEmitter, Input, OnDestroy, OnInit, Output} from "@angular/core";
import {Store} from "@ngxs/store";
import * as _ from 'lodash';
import {ExposuresMainTableConfig} from "../../../model/exposures-main-table-config.model";

@Component({
    selector: 'exposures-main-table',
    templateUrl: './exposures-main-table.component.html',
    styleUrls: ['./exposures-main-table.component.scss']
})

export class ExposuresMainTableComponent implements OnInit, AfterViewInit, OnDestroy {


    @Input('tableConfig') tableConfig: ExposuresMainTableConfig;
    @Output('actionDispatcher') actionDispatcher: EventEmitter<any> = new EventEmitter<any>();
    @Input('sortConfig') sortConfig: any;



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
        let order = this.sortConfig[$event.field];
        this.actionDispatcher.emit({
            type: 'sortTableColumn',
            payload: {field: [$event.field][0], order: this.incrementSort(order)}
        })
        $event.data.sort((data1, data2) => {
            let value1 = data1[$event.field];
            let value2 = data2[$event.field];
            let result = null;
            if (value1 == null && value2 != null)
                result = -1;
            else if (value1 != null && value2 == null)
                result = 1;
            else if (value1 == null && value2 == null)
                result = 0;
            else if (typeof value1 === 'string' && typeof value2 === 'string')
                result = value1.localeCompare(value2);
            else
                result = (value1 < value2) ? -1 : (value1 > value2) ? 1 : 0;
            return (this.incrementSort(order)* result);
        });
    }

    incrementSort(order) {
        switch (order) {
            case 0 :
                return 1;
            case 1 :
                return -1;
            case -1 :
                return 0;
        }
    }
}