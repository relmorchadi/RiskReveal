/*
 * Date : 20/2/2020.
 * Author : Reda El Morchadi
 */

import {Injectable} from '@angular/core';
import {Observable, BehaviorSubject, forkJoin, of} from "rxjs";
import {ExposuresApi} from "../../api/exposures.api";
import {ExposuresHeaderConfig} from "../../../model/exposures-header-config.model";

@Injectable()
export class ExposuresHeaderService {


    headerConfig$: BehaviorSubject<ExposuresHeaderConfig>;


    constructor(private _api: ExposuresApi) {
        this.headerConfig$ = new BehaviorSubject<ExposuresHeaderConfig>(new ExposuresHeaderConfig())
    }

    loadHeaderConfig() {
        return this._api.loadHeaderConfig();
    }


}