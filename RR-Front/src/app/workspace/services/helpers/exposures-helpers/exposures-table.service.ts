import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject, forkJoin } from "rxjs";

import {ExposuresMainTableConfig} from "../../../model/exposures-main-table-config.model";
import {ExposuresApi} from "../../api/exposures.api";

@Injectable()
export class ExposuresTableService  {



    private tableConfig: any[];
    tableConfig$: BehaviorSubject<ExposuresMainTableConfig>;


    constructor(private _api:ExposuresApi) {
        this.tableConfig$ = new BehaviorSubject<ExposuresMainTableConfig>(new ExposuresMainTableConfig())
    }


    public loadTableConfig() {
        return this._api.loadTableConfig();
    }

    public sortTableColumn(sortConfig) {
         this._api.sortTableColumn(sortConfig);
    }

    initTable() {
        forkJoin(
            this.loadTableConfig()
        ).subscribe( ([tableConfig]: any) => {
            this.tableConfig = tableConfig;

            this.tableConfig$.next(tableConfig);
        })
    }

}