/*
 * Date : 19/6/2020.
 * Author : Reda El Morchadi
 */

import { Component, OnInit } from '@angular/core';
import {ICellRendererAngularComp} from "@ag-grid-community/angular";
import {IAfterGuiAttachedParams, ICellRendererParams} from '@ag-grid-community/core';
import {Select, Store} from "@ngxs/store";
import {GeneralConfigState} from "../../../../core/store/states";

@Component({
    selector: 'app-datetime-cell-renderer',
    templateUrl: './datetime-cell-renderer.component.html',
    styleUrls: ['./datetime-cell-renderer.component.scss']
})
export class DateTimeCellRenderer implements ICellRendererAngularComp {
    params: any;

    dateConfig: {
        shortDate: '',
        shortTime: '',
        longDate: '',
        longTime: ''
    };

    constructor(private _store: Store) {
        this.dateConfig$.subscribe(v => this.dateConfig =v);
    }

    @Select(GeneralConfigState.getDateConfig) dateConfig$;

    afterGuiAttached(params?: IAfterGuiAttachedParams): void {
    }

    agInit(params: ICellRendererParams): void {
        this.params = params;
    }

    refresh(params: any): boolean {
        return false;
    }

}
