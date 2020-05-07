/*
 * Date : 20/2/2020.
 * Author : Reda El Morchadi
 */

import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from "@angular/core";
import {Observable} from "rxjs";
import {ExposuresRightMenuConfig} from "../../../model/exposures-right-menu-config.model";


@Component({
    selector: 'exposures-right-menu',
    templateUrl: './exposures-right-menu.component.html',
    styleUrls: ['./exposures-right-menu.component.scss']
})
export class ExposuresRightMenuComponent implements OnInit, OnDestroy {

    @Input('rightMenuConfig') rightMenuConfig: ExposuresRightMenuConfig;
    @Output('actionDispatcher') actionDispatcher: EventEmitter<any> = new EventEmitter<any>();
    private first = true;


    constructor() {

    }


    ngOnInit(): void {

    }

    ngOnDestroy(): void {

    }


    closeRightMenu() {
        if (this.first) {
            this.first = false;
        }else {
            this.actionDispatcher.emit({type:'closeRightMenu'});
            this.first = true;
        }

    }
}