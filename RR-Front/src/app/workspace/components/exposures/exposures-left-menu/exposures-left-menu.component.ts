import {Component, Input, OnDestroy, OnInit} from "@angular/core";

@Component({
    selector: 'exposures-left-menu',
    templateUrl: './exposures-left-menu.component.html',
    styleUrls: ['./exposures-left-menu.component.scss']
})
export class ExposuresLeftMenuComponent implements OnInit, OnDestroy {

    @Input("leftMenuConfig") leftMenuConfig: any;


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