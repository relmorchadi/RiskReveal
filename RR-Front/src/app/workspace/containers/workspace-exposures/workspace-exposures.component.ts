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
    tableConfig: ExposuresMainTableConfig;
    tableColumnsConfig$: Observable<any>;
    rightMenuConfig$: Observable<ExposuresRightMenuConfig>;
    headerConfig$: Observable<ExposuresHeaderConfig>;
    sortConfig: any;
    selectedHeaderConfig: any;
    projectId: any;
    regionPerilFilter: string;

    constructor(_baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,
                private exposuresTableService: ExposuresTableService,
                private exposuresRightMenuService: ExposuresRightMenuService,
                private exposuresHeaderService: ExposuresHeaderService) {
        super(_baseRouter, _baseCdr, _baseStore);
        this.tableConfig$ = new BehaviorSubject<ExposuresMainTableConfig>(new ExposuresMainTableConfig());
        this.rightMenuConfig$ = new BehaviorSubject<ExposuresRightMenuConfig>(new ExposuresRightMenuConfig());
        this.headerConfig$ = of<ExposuresHeaderConfig>({
            exposureViews: [],
            financialPerspectives: [],
            currencies: [],
            divisions: [],
            portfolios: [],
            summariesDefinitions: [],
            financialUnits: []
        });
        this.sortConfig = {};
        this.selectedHeaderConfig = {
            division: null,
            portfolio: null,
            currency: null,
            exposureView: null,
            financialPerspective: null,
            financialUnits: null
        }
        this.tableConfig = new ExposuresMainTableConfig();
    }

    ngOnInit() {
        super.ngOnInit();
        this.selectedProject$.pipe(this.unsubscribeOnDestroy).subscribe(project => {
            if (project != undefined) {
                this.projectId = project.projectId;
                this.headerConfig$ = this.exposuresHeaderService.loadHeaderConfig(this.projectId);
                this.headerConfig$.subscribe(headerConfig => {
                    if (headerConfig)
                        this.initSelectedHeaderConfig(headerConfig);
                }, () => {
                }, () => {
                    this.tableConfig$ = this.exposuresTableService.loadTableConfig({
                        ...this.selectedHeaderConfig,
                        projectId: this.projectId
                    });
                    this.detectChanges();
                })
            }
        });

        /*this.tableConfig$.pipe(first()).subscribe(tableConfig => {
            _.forEach([...tableConfig.frozenColumns, ...tableConfig.columns], column => {
                this.sortConfig[column.field] = 0;
            })
        })*/


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

    initSelectedHeaderConfig(headerConfig) {
        if (headerConfig.divisions[0])
        this.selectedHeaderConfig = {
            division: headerConfig.divisions[0],
            portfolio: headerConfig.portfolios[0],
            currency: headerConfig.divisions[0].currency,
            exposureView: 'Tiv',
            financialPerspective: 'GU',
            financialUnits: 'Unit'
        }
    }

    sortTableColumn() {

    }

    mainTableActionDispatcher($event: any) {
        switch ($event.type) {
            case 'sortTableColumn': {
                this.regionPerilFilter = $event.payload;
                this.tableConfig$ = this.exposuresTableService.sortTableColumn({
                    ...this.selectedHeaderConfig,
                    regionPerilFilter: this.regionPerilFilter,
                    projectId: this.projectId
                });
                break;
            }
            case 'filterRowRegionPeril': {
                this.tableConfig$ = this.exposuresTableService.loadTableConfig({
                    ...this.selectedHeaderConfig,
                    regionPerilFilter: this.regionPerilFilter,
                    projectId: this.projectId
                }).pipe(map((tableConfig: any) =>
                    this.exposuresTableService.filterRowRegionPeril(tableConfig, $event.payload)
                ));
                break;
            }
            case 'removeFilter': {
                this.tableConfig$ = this.exposuresTableService.loadTableConfig({
                    ...this.selectedHeaderConfig,
                    regionPerilFilter: this.regionPerilFilter,
                    projectId: this.projectId
                });
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
                this.tableConfig$ = this.exposuresTableService.loadTableConfig(
                    {
                        ...this.selectedHeaderConfig,
                        projectId: this.projectId
                    }
                );
                break;
            }
            case 'changeFinancialUnit' : {
                this.selectedHeaderConfig.financialUnits = $event.payload.header;
                /*this.tableConfig$ = this.exposuresTableService.loadTableConfig(
                    {
                        ...this.selectedHeaderConfig,
                        projectId: this.projectId
                    }
                );*/
                break;
            }
            case 'changeFinancialPerspecctive' : {
                /* this.selectedHeaderConfig.financialPerspective = $event.payload;
                 this.tableConfig$ = this.exposuresTableService.loadTableConfig(
                     {
                         ...this.selectedHeaderConfig,
                         projectId: this.projectId
                     }
                 );*/
                break;
            }
            case 'changeDivision' : {
                this.selectedHeaderConfig.division = $event.payload;
                this.tableConfig$ = this.exposuresTableService.loadTableConfig(
                    {
                        ...this.selectedHeaderConfig,
                        projectId: this.projectId
                    }
                );
                break;
            }
            case 'changePortfolio' : {
                this.selectedHeaderConfig.portfolio = $event.payload;
                this.tableConfig$ = this.exposuresTableService.loadTableConfig(
                    {
                        ...this.selectedHeaderConfig,
                        projectId: this.projectId
                    }
                );
                break;
            }
            case 'changeView' : {
                this.selectedHeaderConfig.exposureView = $event.payload.header;
                this.tableConfig$ = this.exposuresTableService.loadTableConfig(
                    {
                        ...this.selectedHeaderConfig,
                        projectId: this.projectId
                    }
                );
                break;
            }
            case 'openPortfolioDetails': {
                this.rightMenuConfig$ = this.exposuresRightMenuService.constructRightMenuConfig('portfolio');
                break;
            }
            case 'openDivisionDetails' : {
                this.rightMenuConfig$ = this.exposuresRightMenuService.constructRightMenuConfig('division', this.selectedHeaderConfig.division);
                break;
            }
            case 'exportExposuresTable' : {
                console.log('here');
                this.exposuresTableService.exportTable({
                    ...this.selectedHeaderConfig,
                    projectId: this.projectId
                }).subscribe(
                    (response: any) => {
                        let contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
                        let blob = new Blob([response.body], {type: contentType});
                        let fileName = response.headers.get('Content-Disposition')
                            .split(';')[1].trim().split('=')[1];
                        let url = window.URL.createObjectURL(blob);
                        let anchor = document.createElement("a");
                        anchor.download = fileName;
                        anchor.href = url;
                        anchor.click();
                    },
                    (err) => {

                    }
                );
                break;
            }
        }
    }

}
