
/*
 * Date : 20/2/2020.
 * Author : Reda El Morchadi
 */

import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from "@angular/core";

@Component({
    selector: 'exposures-left-menu',
    templateUrl: './exposures-left-menu.component.html',
    styleUrls: ['./exposures-left-menu.component.scss']
})
export class ExposuresLeftMenuComponent implements OnInit, OnDestroy {

    @Input("leftMenuConfig") leftMenuConfig: any;
    @Output('actionDispatcher') actionDispatcher: EventEmitter<any> = new EventEmitter<any>();

    constructor(){

    }


    ngOnInit(): void {
    }

    ngOnDestroy(): void {
    }


    filter(projectId: string, param2, projectId2: any) {

    }

    toDate(creationDate: any) {

    }

    toggleDeletedPlts() {

    }

    resetPath() {

    }
}