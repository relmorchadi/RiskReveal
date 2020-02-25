import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {BaseContainer} from '../../../shared/base';
import {Select, Store} from '@ngxs/store';
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
import {WorkspaceState} from "../../store/states";
import * as _ from "lodash";
import {first} from "rxjs/operators";

@Component({
    selector: 'app-workspace-exposures',
    templateUrl: './workspace-exposures.component.html',
    styleUrls: ['./workspace-exposures.component.scss'],
    providers: [ExposuresTableService, ExposuresRightMenuService, ExposuresHeaderService]
})
export class WorkspaceExposuresComponent extends BaseContainer implements OnInit, StateSubscriber {

    @Select(WorkspaceState.getSelectedProject) selectedProject$;
    wsIdentifier;
    workspaceInfo: any;
    tableConfig$: Observable<ExposuresMainTableConfig>;
    rightMenuConfig$: Observable<ExposuresRightMenuConfig>;
    headerConfig$: Observable<ExposuresHeaderConfig>;
    sortConfig:any;

    constructor(_baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,
                private exposuresTableService: ExposuresTableService,
                private exposuresRightMenuService: ExposuresRightMenuService,
                private exposuresHeaderService: ExposuresHeaderService) {
        super(_baseRouter, _baseCdr, _baseStore);
        this.tableConfig$ = new BehaviorSubject<ExposuresMainTableConfig>(new ExposuresMainTableConfig());
        this.rightMenuConfig$ = new BehaviorSubject<ExposuresRightMenuConfig>(new ExposuresRightMenuConfig());
        this.headerConfig$ = new BehaviorSubject<ExposuresHeaderConfig>(new ExposuresHeaderConfig());
        this.sortConfig = {};
    }

    ngOnInit() {
        this.tableConfig$ = this.exposuresTableService.loadTableConfig();
        this.headerConfig$ = this.exposuresHeaderService.loadHeaderConfig();
        this.selectedProject$.pipe(first()).subscribe(project => {
            if (project) {
               let {tableConfig, headerConfig} = this.exposuresTableService.changeProject(project);
               this.tableConfig$ = tableConfig;
               this.headerConfig$ = headerConfig;
            }
        });
        this.tableConfig$.pipe(first()).subscribe(tableConfig => {
            _.forEach([...tableConfig.frozenColumns, ...tableConfig.columns], column => {
                this.sortConfig[column.field] = 0;
            })
        })
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

    sortTableColumn() {

    }

    mainTableActionDispatcher($event: any) {
        switch ($event.type) {
            case 'sortTableColumn': {
                this.sortConfig[$event.payload.field] = $event.payload.order;
                break;
            }
        }
    }



    rightMenuActionDispatcher($event: any) {
        switch ($event.type) {
            case 'closeRightMenu' : {
                this.rightMenuConfig$ = this.exposuresRightMenuService.destructRightMenuConfig();
                break;
            }
        }
    }

    headerActionDispatcher($event: any) {
        switch ($event.type) {
            case 'changeCurrency' : {
                this.tableConfig$ = this.exposuresHeaderService.changeCurrency($event.payload);
                break;
            }
            case 'changeFinancialUnit' : {
                this.tableConfig$ = this.exposuresHeaderService.changeFinancialUnit($event.payload);
                break;
            }
            case 'changeDivision' : {
                this.tableConfig$ = this.exposuresHeaderService.changeDivision($event.payload);
                break;
            }
            case 'changePortfolio' : {
                this.tableConfig$ = this.exposuresHeaderService.changePortfolio($event.payload);
                break;
            }
            case 'changeView' : {
                this.tableConfig$ = this.exposuresHeaderService.changeView($event.payload);
                break;
            }
            case 'openPortfolioDetails': {
                this.rightMenuConfig$ = this.exposuresRightMenuService.constructRightMenuConfig('portfolio');
                break;
            }
            case 'openDivisionDetails' : {
                this.rightMenuConfig$ = this.exposuresRightMenuService.constructRightMenuConfig('division');
                break;
            }
            case 'exportExposuresTable' : {
                this.exposuresTableService.exportTable();
                break;
            }
        }

    }
}
