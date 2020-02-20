/*
 * Date : 20/2/2020. 
 * Author : Reda El Morchadi
 */

import {Injectable} from '@angular/core';
import {Observable, BehaviorSubject, forkJoin, of} from "rxjs";
import {ExposuresApi} from "../../api/exposures.api";
import {ExposuresRightMenuConfig} from "../../../model/exposures-right-menu-config.model";

@Injectable()
export class ExposuresRightMenuService {


    rightMenuConfig$: BehaviorSubject<ExposuresRightMenuConfig>;
    private rightMenuConfig: any[];


    constructor(private _api: ExposuresApi) {
        this.rightMenuConfig$ = new BehaviorSubject<ExposuresRightMenuConfig>(new ExposuresRightMenuConfig())
    }


    public loadRightMenuContent(type: any) {
        return this._api.loadRightMenuContent(type);
    }

    public constructRightMenuConfig(type: any) {
        return of<ExposuresRightMenuConfig>({
            type: type,
            content: this.loadRightMenuContent(type),
            visibility: true
        })
    }
    public destructRightMenuConfig() {
        return of<ExposuresRightMenuConfig>({
            type: null,
            content: null,
            visibility: false
        })
    }

    public sortTableColumn(sortConfig) {
        this._api.sortTableColumn(sortConfig);
    }



}