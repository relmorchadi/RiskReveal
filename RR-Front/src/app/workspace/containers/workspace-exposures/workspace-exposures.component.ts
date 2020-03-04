import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {BaseContainer} from '../../../shared/base';
import {Select, Store} from '@ngxs/store';
import {Router} from '@angular/router';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from "../../../core/store/actions/header.action";
import {BehaviorSubject, Observable, of} from "rxjs";
import {ExposuresMainTableConfig} from "../../model/exposures-main-table-config.model";
import {ExposuresTableService} from "../../services/helpers/exposures-helpers/exposures-table.service";
import {ExposuresRightMenuConfig} from "../../model/exposures-right-menu-config.model";
import {ExposuresRightMenuService} from "../../services/helpers/exposures-helpers/exposures-right-menu.service";
import {ExposuresHeaderConfig} from "../../model/exposures-header-config.model";
import {ExposuresHeaderService} from "../../services/helpers/exposures-helpers/exposures-header.service";
import {WorkspaceState} from "../../store/states";
import * as _ from "lodash";
import {first, map} from "rxjs/operators";

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
    tableColumnsConfig$:Observable<any>;
    rightMenuConfig$: Observable<ExposuresRightMenuConfig>;
    headerConfig$: Observable<ExposuresHeaderConfig>;
    sortConfig:any;
    selectedHeaderConfig:any;

    constructor(_baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,
                private exposuresTableService: ExposuresTableService,
                private exposuresRightMenuService: ExposuresRightMenuService,
                private exposuresHeaderService: ExposuresHeaderService) {
        super(_baseRouter, _baseCdr, _baseStore);
        this.tableConfig$ = new BehaviorSubject<ExposuresMainTableConfig>(new ExposuresMainTableConfig());
        this.rightMenuConfig$ = new BehaviorSubject<ExposuresRightMenuConfig>(new ExposuresRightMenuConfig());
        this.headerConfig$ = of<ExposuresHeaderConfig>( {exposureViews:[], financialPerspectives:[], currencies:[], divisions:[], portfolios:[], summariesDefinitions:[]});
        this.sortConfig = {};
        this.initSelectedHeaderConfig();
    }

    ngOnInit() {
        this.tableColumnsConfig$ = this.exposuresTableService.loadTableColumnsConfig();
        this.tableConfig$ = this.exposuresTableService.loadTableConfig();
        this.selectedProject$.pipe(this.unsubscribeOnDestroy).subscribe(project => {
            if (project) {
                this.headerConfig$ = this.exposuresHeaderService.loadHeaderConfig(project.projectId);
                this.initSelectedHeaderConfig();
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

    initSelectedHeaderConfig() {
        this.selectedHeaderConfig = {
            division:null,
            portfolio:null,
            currency:null,
            exposureView:null,
            financialPerspective:null
        }
    }
    sortTableColumn() {

    }

    mainTableActionDispatcher($event: any) {
        switch ($event.type) {
            case 'sortTableColumn': {
                this.sortConfig[$event.payload.field] = $event.payload.order;
                break;
            }
            case 'filterRowRegionPeril': {
                this.tableColumnsConfig$ = this.exposuresTableService.filterRowRegionPeril($event.payload);
                break;
            }
            case 'removeFilter': {
                this.tableColumnsConfig$ = this.exposuresTableService.loadTableColumnsConfig();
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
                this.selectedHeaderConfig.currency = $event.payload;
                this.tableConfig$ = this.exposuresHeaderService.changeCurrency($event.payload);
                break;
            }
            case 'changeFinancialUnit' : {
                this.selectedHeaderConfig.financialPerspective = $event.payload;
                this.tableConfig$ = this.exposuresHeaderService.changeFinancialUnit($event.payload);
                break;
            }
            case 'changeDivision' : {
                this.selectedHeaderConfig.division = $event.payload;
                this.tableConfig$ = this.exposuresHeaderService.changeDivision($event.payload);
                break;
            }
            case 'changePortfolio' : {
                this.selectedHeaderConfig.portfolio = $event.payload;
                this.tableConfig$ = this.exposuresHeaderService.changePortfolio($event.payload);
                break;
            }
            case 'changeView' : {
                this.selectedHeaderConfig.exposureView = $event.payload.header;
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
