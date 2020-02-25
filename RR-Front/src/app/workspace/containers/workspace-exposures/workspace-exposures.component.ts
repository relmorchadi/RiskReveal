import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {BaseContainer} from '../../../shared/base';
import {Store} from '@ngxs/store';
import {Router} from '@angular/router';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from "../../../core/store/actions/header.action";
import * as fromWs from "../../store/actions";
import {BehaviorSubject, Observable} from "rxjs";
import {ExposuresMainTableConfig} from "../../model/exposures-main-table-config.model";
import {ExposuresTableService} from "../../services/helpers/exposures-helpers/exposures-table.service";
import {ExposuresRightMenuConfig} from "../../model/exposures-right-menu-config.model";
import {ExposuresRightMenuService} from "../../services/helpers/exposures-helpers/exposures-right-menu.service";
import {ExposuresHeaderConfig} from "../../model/exposures-header-config.model";
import {ExposuresHeaderService} from "../../services/helpers/exposures-helpers/exposures-header.service";

@Component({
    selector: 'app-workspace-exposures',
    templateUrl: './workspace-exposures.component.html',
    styleUrls: ['./workspace-exposures.component.scss'],
    providers: [ExposuresTableService, ExposuresRightMenuService,ExposuresHeaderService]
})
export class WorkspaceExposuresComponent extends BaseContainer implements OnInit, StateSubscriber {
    wsIdentifier;
    workspaceInfo: any;
    tableConfig$: Observable<ExposuresMainTableConfig>;
    rightMenuConfig$: Observable<ExposuresRightMenuConfig>;
    headerConfig$: Observable<ExposuresHeaderConfig>;

    constructor(_baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,
                private exposuresTableService: ExposuresTableService,
                private exposuresRightMenuService: ExposuresRightMenuService,
                private exposuresHeaderService:ExposuresHeaderService) {
        super(_baseRouter, _baseCdr, _baseStore);
        this.tableConfig$ = new BehaviorSubject<ExposuresMainTableConfig>(new ExposuresMainTableConfig());
        this.rightMenuConfig$ = new BehaviorSubject<ExposuresRightMenuConfig>(new ExposuresRightMenuConfig());
        this.headerConfig$ = new BehaviorSubject<ExposuresHeaderConfig>(new ExposuresHeaderConfig());
    }

    ngOnInit() {
        this.tableConfig$ = this.exposuresTableService.loadTableConfig();
        this.headerConfig$ = this.exposuresHeaderService.loadHeaderConfig();
    }

    patchState({wsIdentifier, data}: any): void {
        this.workspaceInfo = data;
        this.wsIdentifier = wsIdentifier;
    }

    pinWorkspace() {
        this.dispatch([new fromHeader.TogglePinnedWsState({
            "userId": 1,
            "workspaceContextCode": this.workspaceInfo.wsId,
            "workspaceUwYear": this.workspaceInfo.uwYear
        })]);
    }

    ngOnDestroy(): void {
        this.destroy();
    }

    protected detectChanges() {
        super.detectChanges();
    }


    mainTableActionDispatcher($event: any) {
        this.exposuresTableService.sortTableColumn($event);
    }

    onRightMenuCall(type: any) {
        this.rightMenuConfig$ = this.exposuresRightMenuService.constructRightMenuConfig(type)
    }

    onRightMenuClose() {
        this.rightMenuConfig$ = this.exposuresRightMenuService.destructRightMenuConfig();
    }

    rightMenuActionDispatcher($event: any) {
        switch ($event.type) {
            case 'closeRightMenu' : {
                this.onRightMenuClose();
                break;
            }
        }
    }

    headerActionDispatcher($event: any) {
        switch ($event.type) {
            case 'openPortfolioDetails': {
                this.onRightMenuCall('portfolio');
                break;
            }
            case 'openDivisionDetails' : {
                this.onRightMenuCall('division');
                break;
            }
        }

    }
}
