import {ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute, Router} from '@angular/router';
import {
    Actions,
    ofActionCompleted,
    ofActionDispatched,
    ofActionErrored,
    ofActionSuccessful,
    Select,
    Store
} from '@ngxs/store';
import {WorkspaceState} from '../../store/states';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import * as fromWs from '../../store/actions';
import {UpdateWsRouting} from '../../store/actions';
import {DataTables} from './data';
import {BaseContainer} from '../../../shared/base';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from '../../../core/store/actions/header.action';
import {Navigate} from '@ngxs/router-plugin';
import {ConfirmationService} from 'primeng/api';
import {forkJoin} from 'rxjs';
import {TableSortAndFilterPipe} from '../../../shared/pipes/table-sort-and-filter.pipe';
import {NotificationService} from '../../../shared/services';
import {debounceTime, take} from 'rxjs/operators';
import {SetCurrentTab} from '../../store/actions';
import {FormControl} from "@angular/forms";
import {Debounce} from "../../../shared/decorators";

@Component({
    selector: 'app-workspace-risk-link',
    templateUrl: './workspace-risk-link.component.html',
    styleUrls: ['./workspace-risk-link.component.scss'],
    providers: [ConfirmationService]
})
export class WorkspaceRiskLinkComponent extends BaseContainer implements OnInit, StateSubscriber {
    collapsehead;
    regionPeril;
    hyperLinks: string[] = ['RiskLink', 'File-Based'];
    hyperLinksRoutes: any = {
        'RiskLink': '/RiskLink',
        'File-Based': '/FileBasedImport'
    };
    hyperLinksConfig: {
        wsId: string,
        uwYear: string
    };

    wsIdentifier;
    workspaceInfo: any;

    tree = [];

    selectedRows: any;

    lastSelectedIndex = null;

    filterModalVisibility = false;
    linkingModalVisibility = false;

    managePopUpAnalysis = false;
    managePopUpPortfolio = false;

    radioValue = 'all';
    radioValueAnalysis = '';
    radioValueGroup = 'all';
    columnsForConfig;
    targetConfig;

    @ViewChild('kt')
    tables: any;
    @ViewChild('searchInput')
    searchInput: ElementRef;
    @ViewChild('obRes')
    dropDownFPOb: any;
    @ViewChild('rpRes')
    dropDownFP: any;
    @ViewChild('filters')
    filtersInput: any;

    displayDropdownRDMEDM = false;

    occurrenceBasis;

    scrollableColsAnalysis: any;
    frozenColsAnalysis: any;

    scrollableColsPortfolio: any;
    frozenColsPortfolio: any;

    @Select(WorkspaceState.getCurrentWorkspaces) ws$;
    ws: any;

    @Select(WorkspaceState.getSelectedProject) selectedProject$;
    selectedProject = null;

    @Select(WorkspaceState.getRiskLinkState) state$;
    state: any;
    tabStatus: any;
    wsStatus: any;

    @Select(WorkspaceState.getListEdmRdm) listEdmRdm$;
    listEdmRdm: any;

    @Select(WorkspaceState.getAnalysis) analysis$;
    analysis: any;
    allCheckedAnalysis: boolean;
    indeterminateAnalysis: boolean;

    @Select(WorkspaceState.getPortfolios) portfolios$;
    portfolios: any;
    allCheckedPortolfios: boolean;
    indeterminatePortfolio: boolean;

    @Select(WorkspaceState.getValidResults) validate$;
    showPopUp = false;

    @Select(WorkspaceState.getFlatSelectedAnalysisPortfolio)
    flatSelectedAnalysisPortfolio$;

    @Select(WorkspaceState.getSelectedAnalysisPortfolios)
    selectedAnalysisPortfolios$;

    @Select(WorkspaceState.getRiskLinkSummary)
    summary$;
    @Select(WorkspaceState.getRiskLinkSummarySelectionSize)
    summarySelectionSize$;

    @Select(WorkspaceState.getRiskLinkAnalysisSummary)
    analysisSummary$;

    @Select(WorkspaceState.getRiskLinkPortfolioSummary)
    portfolioSummary$;

    filterAnalysis = {
        rlId: '',
        analysisName: '',
        analysisDescription: '',
        engineType: '',
        //runDate: '',
        analysisType: '',
        peril: '',
        subPeril: '',
        lossAmplification: '',
        region: '',
        analysisMode: '',
        analysisCurrency: ''
    };

    filterPortfolio = {
        rlId: '',
        number: '',
        name: '',
        description: '',
        //created: null,
        type: '',
        agCurrency: '',
        agCedent: '',
        agSource: '',
        peril: '',
    };

    datasourceKeywordFc: FormControl;

    readonly analysisConfigFields = ['financialPerspectives', 'projectId', 'proportion', 'rlAnalysisId', 'targetCurrency', 'targetRAPCodes', 'targetRegionPeril', 'unitMultiplier', 'divisions', 'occurrenceBasis', 'occurrenceBasisOverrideReason'];
    readonly portfolioConfigFields = ['analysisRegions', 'importLocationLevel', 'projectId', 'proportion', 'rlPortfolioId', 'targetCurrency', 'unitMultiplier', 'divisions'];

    constructor(
        private _helper: HelperService,
        private route: ActivatedRoute,
        private actions$: Actions,
        private tableFilter: TableSortAndFilterPipe,
        private confirmationService: ConfirmationService,
        private notification: NotificationService,
        _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef
    ) {
        super(_baseRouter, _baseCdr, _baseStore);
        this.datasourceKeywordFc = new FormControl(['']);
    }

    ngOnInit() {
        this.importInit();
        this.state$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
            this.state = _.merge({}, value);
            this.detectChanges();
        });
        this.listEdmRdm$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
            this.listEdmRdm = _.merge({}, value);
            this.detectChanges();
        });
        this.ws$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
            this.ws = _.merge({}, value);
            this.wsStatus = this.ws.workspaceType;
            this.detectChanges();
        });
        this.analysis$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
            this.analysis = _.merge({}, value);
            this.detectChanges();
        });
        this.portfolios$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
            this.portfolios = _.merge({}, value);
            this.detectChanges();
        });
        this.selectedProject$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
            this.selectedProject = value;
            this.tabStatus = _.get(value, 'projectType', null);
            this.importInit();
            this.loadSummaryOrDefaultDataSources();
            this.detectChanges();
        });
        this.route.params.pipe(this.unsubscribeOnDestroy).subscribe(({wsId, year}) => {
            this.hyperLinksConfig = {wsId, uwYear: year};
            this.importInit();
            this.detectChanges();
        });
        this.actions$
            .pipe(
                this.unsubscribeOnDestroy,
                ofActionDispatched(SetCurrentTab)
            ).subscribe(({payload}) => {
            if (payload.wsIdentifier != this.wsIdentifier) this.destroy();
            this.detectChanges();
        });

        this.actions$
            .pipe(
                this.unsubscribeOnDestroy,
                ofActionDispatched(fromWs.SaveDefaultDataSourcesSuccessAction)
            ).subscribe(() => {
            this.notification.createNotification('Information',
                'The Current EDM And RDM Selection is Saved.',
                'info', 'bottomRight', 4000);
        });

        this.actions$
            .pipe(
                this.unsubscribeOnDestroy,
                ofActionDispatched(fromWs.SaveDefaultDataSourcesErrorAction)
            ).subscribe(() => {
            this.notification.createNotification('Error',
                'The Current EDM And RDM Selection is not Saved.',
                'error', 'bottomRight', 4000);
        });
        this.actions$
            .pipe(
                this.unsubscribeOnDestroy,
                ofActionDispatched(fromWs.ClearDefaultDataSourcesSuccessAction)
            ).subscribe(() => {
            this.notification.createNotification('Information',
                'The EDM And RDM Selection is reset to default.',
                'info', 'bottomRight', 4000);
        });

        this.actions$
            .pipe(
                this.unsubscribeOnDestroy,
                ofActionDispatched(fromWs.AutoAttachEndsAction)
            ).subscribe((p) => {
            const {withDetailScan, allAnalysis, allPortfolios} = p.payload;
            if (withDetailScan && _.isEmpty(allAnalysis) && _.isEmpty(allPortfolios))
                alert('No auto attach suggestions available on this Workspace');
        });

        this.actions$
            .pipe(
                this.unsubscribeOnDestroy,
                ofActionCompleted(fromWs.SaveImportConfigurationAction)
            ).subscribe(() => {
            this.notification.createNotification('Information',
                'Import configuration saved',
                'info', 'bottomRight', 4000);
        });
        this.actions$
            .pipe(
                this.unsubscribeOnDestroy,
                ofActionSuccessful(fromWs.TriggerImportAction)
            ).subscribe(() => {
            this.notification.createNotification('Information',
                'Import Job submitted !',
                'info', 'bottomRight', 4000);
        });
        this.actions$
            .pipe(
                this.unsubscribeOnDestroy,
                ofActionErrored(fromWs.TriggerImportAction)
            ).subscribe(() => {
            this.notification.createNotification('Error',
                'Error while submitting the import job!',
                'error', 'bottomRight', 4000);
        });

        this.actions$
            .pipe(
                this.unsubscribeOnDestroy,
                ofActionSuccessful(fromWs.DeleteFromImportBasketAction)
            ).subscribe(({payload}) => {
            console.log('Delete from import successful', payload);
            if (payload.type == 'ANALYSIS') {
                this.getRiskLinkAnalysis();
            } else if (payload.type == 'PORTFOLIO') {
                this.getRiskLinkPortfolio();
            }
        });


        this.scrollableColsAnalysis = DataTables.scrollableColsAnalysis;
        this.frozenColsAnalysis = DataTables.frozenColsAnalysis;
        this.scrollableColsPortfolio = DataTables.scrollableColsPortfolio;
        this.frozenColsPortfolio = DataTables.frozenColsPortfolio;

        this._autoSelectFirstDataSource();

        this.datasourceKeywordFc.valueChanges
            .pipe(this.unsubscribeOnDestroy)
            .pipe(debounceTime(500))
            .subscribe(val => {
                this.onInputSearch(val);
            });
        //@Todo refactor
        if (this.tabStatus === 'FAC') {
            const keyword = `"${this.ws.wsId}_${("0" + this.state.financialValidator.division.selected.divisionNumber).slice(-2)}"`;
            if (this.state.selectedEDMOrRDM === 'RDM') {
                this.filterAnalysis.analysisName = keyword;
            } else {
                this.filterPortfolio.number = keyword;
            }
        }

    }

    private _autoSelectFirstDataSource() {
        this.actions$
            .pipe(
                this.unsubscribeOnDestroy,
                ofActionSuccessful(fromWs.LoadSummaryOrDefaultDataSourcesAction, fromWs.BasicScanEDMAndRDMAction),
                debounceTime(500)
            ).subscribe(p => {
            const {edms, rdms, currentDataSource} = this.state.selection;
            const dataSources = [..._.values(edms), ..._.values(rdms)];
            if (!_.isEmpty(dataSources) && !currentDataSource) {
                this.toggleItemsListRDM(_.first(dataSources));
            }
        });
    }

    loadSummaryOrDefaultDataSources() {
        setTimeout(() => {
            if (this.selectedProject)
                this.dispatch(new fromWs.LoadSummaryOrDefaultDataSourcesAction({
                    projectId: this.selectedProject.projectId,
                    instanceId: this.state.financialValidator.rmsInstance.selected.instanceId,
                    userId: 1
                }));
        }, 500);
    }

    saveDefaultSelection() {
        this.dispatch(new fromWs.SaveDefaultDataSourcesAction({empty: false}));
    }

    clearDefaultSelection() {
        this.dispatch(new fromWs.SaveDefaultDataSourcesAction({empty: true}));
    }

    patchState({wsIdentifier, data}: any): void {
        this.workspaceInfo = data;
        this.wsIdentifier = wsIdentifier;
    }


    lazyLoadDataSources(lazyLoadEvent) {
        const {first, rows} = lazyLoadEvent;
        if (first + rows < this.state.listEdmRdm.totalElements) {
            this.dispatch(new fromWs.SearchRiskLinkEDMAndRDMAction({
                instanceId: this.state.financialValidator.rmsInstance.selected.instanceId,
                keyword: this.datasourceKeywordFc.value,
                offset: first,
                size: rows,
            }));
        }
    }

    toggleDatasourcesDisplay() {
        this.displayDropdownRDMEDM = !this.displayDropdownRDMEDM;
        if (this.displayDropdownRDMEDM) {
            this.dispatch(new fromWs.SearchRiskLinkEDMAndRDMAction({
                instanceId: this.state.financialValidator.rmsInstance.selected.instanceId,
                keyword: '',
                offset: 0,
                size: 100,
            }));
        }
        setTimeout(() => {
            this.searchInput.nativeElement.focus();
        })
    }

    setFilterDivision() {
        if (!this.state.financialValidator.division.selected) {
            console.error('No selected Division');
            return;
        }
        const keyword = `"${this.ws.wsId}_${("0" + this.state.financialValidator.division.selected.divisionNumber).slice(-2)}"`;
        if (this.state.selectedEDMOrRDM === 'RDM') {
            this.filterAnalysis.analysisName = keyword;
            this.getRiskLinkAnalysis(0);
        } else {
            this.filterPortfolio.number = keyword;
            this.getRiskLinkPortfolio(0);
        }
    }

    pinWorkspace() {
        this.dispatch([new fromHeader.TogglePinnedWsState({
            "userId": 1,
            "workspaceContextCode": this.workspaceInfo.wsId,
            "workspaceUwYear": this.workspaceInfo.uwYear
        })]);
    }

    onInputSearch(keyword) {
        this.dispatch(new fromWs.SearchRiskLinkEDMAndRDMAction({
            instanceId: this.state.financialValidator.rmsInstance.selected.instanceId,
            keyword, offset: 0, size: '100'
        }));
        this.detectChanges();
    }

    /** Manage Columns Method's */

    saveColumns(event, scope) {
        if (scope === 'analysis') {
            this.scrollableColsAnalysis = [...event];
        } else if (this.targetConfig === 'portfolio') {
            this.scrollableColsPortfolio = [...event];
        }
        this.closePopUp();
    }

    closePopUp() {
        this.managePopUpPortfolio = false;
        this.managePopUpAnalysis = false;
    }

    cloneData(data) {
        if (data !== undefined) {
            return [...data];
        }
    }

    drop(event: CdkDragDrop<string[]>) {
        moveItemInArray(this.columnsForConfig, event.previousIndex, event.currentIndex);
    }

    toggleItemsListRDM(datasource) {
        const {rmsId, type} = datasource;
        this.dispatch([new fromWs.ToggleRiskLinkEDMAndRDMSelectedAction({
            instanceId: datasource.instanceId,
            projectId: this.selectedProject.projectId,
            rmsId,
            type
        }), new fromWs.PatchAddToBasketStateAction()]);
        if (this.tabStatus === 'FAC') {
            this.setFilterDivision();
        } else {
            if (type === 'RDM') {
                this.getRiskLinkAnalysis(0);
            } else {
                this.getRiskLinkPortfolio(0);
            }
        }
        this.UpdateCheckboxStatus();
    }

    /** Select EDM & RDM DropDown Method's */
    toggleItems(RDM, event, source) {
        this.dispatch(new fromWs.ToggleRiskLinkEDMAndRDMAction({RDM, action: 'selectOne', source}));
        if (event !== null) {
            event.stopPropagation();
        }
    }

    selectAll() {
        this.dispatch(new fromWs.ToggleRiskLinkEDMAndRDMAction({action: 'selectAll', source: 'solo'}));
    }

    unselectAll() {
        this.dispatch(new fromWs.ToggleRiskLinkEDMAndRDMAction({action: 'unselectAll', source: 'solo'}));
    }

    resetAll() {
        this.tables.reset();
        this.filtersInput.nativeElement.value = '';
    }

    resetFilters() {
        this.tables.filters = {};
        this.tables.filteredValue = null;
        this.filtersInput.nativeElement.value = '';
    }

    resetsorts() {
        this.tables.sortOrder = this.tables.defaultSortOrder;
        this.tables.sortField = '';
        this.tables.multiSortMeta = null;
        this.tables.tableService.onSort(null);
    }

    closeDropdown() {
        this.displayDropdownRDMEDM = false;
    }

    scanDataSources() {
        this.selectedProject$.pipe(take(1))
            .subscribe(p => {
                const projectId = p.projectId;
                const selectedDS = _.toArray(this.listEdmRdm.data).filter(ds => ds.selected);
                const {instanceId, instanceName} = this.state.financialValidator.rmsInstance.selected;
                this.dispatch(new fromWs.DatasourceScanAction({
                    selectedDS: _.map(selectedDS, item => ({...item, instanceId, instanceName})),
                    projectId
                }));
            });
        this.closeDropdown();
    }

    rescanItem(datasource) {
        datasource.scanning = true;
        this.selectedProject$.pipe(take(1))
            .subscribe(p => {
                const projectId = p.projectId;
                this.dispatch(new fromWs.ReScanDataSource({
                    datasource,
                    projectId
                }));
            });
    }

    runDetailedScan() {
        forkJoin(
            [
                this.selectedProject$,
                this.selectedAnalysisPortfolios$
            ].map(item => item.pipe(take(1)))
        ).pipe(this.unsubscribeOnDestroy)
            .subscribe(data => {
                const [p, analysisPortfolioSelection] = data;
                const {analysis, portfolios} = analysisPortfolioSelection;
                this.dispatch([
                    new fromWs.AddToImportBasket({analysis, portfolios, context: this.tabStatus}),
                    new fromWs.RunDetailedScanAction({
                        instanceId: this.state.financialValidator.rmsInstance.selected.instanceId,
                        projectId: p.projectId,
                        analysis,
                        portfolios
                    })]);
            });
    }

    getIndeterminate() {
        const data = [];  //this.filterData(this.getTableData());
        const selection = _.filter(data, item => item.selected);
        if (this.state.selectedEDMOrRDM === 'RDM') {
            this.indeterminateAnalysis = (selection.length < data.length && selection.length > 0);
        } else {
            this.indeterminatePortfolio = (selection.length < data.length && selection.length > 0);
        }
        this.detectChanges();
    }

    getCheckedData() {
        if (this.state.selectedEDMOrRDM === 'EDM') {
            const selection = _.filter(this.state.portfolios.data, item => item.selected);
            this.allCheckedAnalysis = selection.length === this.state.portfolios.data.length && selection.length > 0;
        } else {
            const selection = _.filter(this.state.analysis.data, item => item.selected);
            this.allCheckedPortolfios = selection.length === this.state.analysis.data.length && selection.length > 0;
        }
        this.detectChanges();
    }

    UpdateCheckboxStatus() {
        this.getCheckedData();
        this.getIndeterminate();
    }

    getTitle() {
        return this.state.selectedEDMOrRDM === 'RDM' ? 'Analysis' : 'Portfolio';
    }

    filterData(data) {
        if (this.state.selectedEDMOrRDM === 'RDM') {
            return this.tableFilter.transform(data, [{}, this.filterAnalysis]);
        } else {
            return this.tableFilter.transform(data, [{}, this.filterPortfolio]);
        }
    }

    clearSelection(item, target) {
        // console.log("clear selection ", {item, target})
        this.dispatch(new fromWs.DeleteEdmRdmAction({
            rlDataSourceId: item.rlDataSourceId,
            rmsId: item.rmsId,
            target: target
        }));
    }

    getNumberOfSelected(item, source) {
        const selectionPortfolio = _.get(this.state.selection.portfolios, `${item.rmsId}`, null);
        const selectionAnalysis = _.get(this.state.selection.analysis, `${item.rmsId}`, null);
        if (source === 'portfolio') {
            return selectionPortfolio === null ? 0 : _.toArray(selectionPortfolio).length;
        } else if (source === 'analysis') {
            return selectionAnalysis === null ? 0 : _.toArray(selectionAnalysis).length;
        }
    }

    getSelection(data) {
        if (data.length > 1) {
            if (this.state.selectedEDMOrRDM === 'RDM') {
                this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({
                    action: 'unSelectChunk',
                    data: this.getSelectedAnalysisIds()
                }));
                this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({
                    action: 'selectChunk',
                    data: _.map(data, a => a.rlAnalysisId)
                }));
            } else {
                this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({
                    action: 'unSelectChunk',
                    data: this.getSelectedPortfolioIds()
                }));
                this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({
                    action: 'selectChunk',
                    data: _.map(data, p => p.rlPortfolioId)
                }));
            }
        }
        this.UpdateCheckboxStatus();
    }

    selectOne(row) {
        if (this.state.selectedEDMOrRDM === 'RDM') {
            this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({
                action: 'selectOne',
                value: true,
                rlAnalysisId: row.rlAnalysisId
            }));
        } else {
            this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({
                action: 'selectOne',
                value: true,
                rlPortfolioId: row.rlPortfolioId
            }));
        }
    }

    unselectAllTable(scope) {
        if (scope === 'analysis') {
            this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({
                action: 'unselectAll',
                data: this.getSelectedAnalysisIds()
            }));
        } else if (scope === 'portfolio') {
            this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({
                action: 'unselectAll',
                data: this.getSelectedPortfolioIds()
            }));
        }
        this.UpdateCheckboxStatus();
    }

    @Debounce()
    onPortfolioFilter(param) {
        this.getRiskLinkPortfolio(0);
    }

    @Debounce()
    onAnalysisFilter(param) {
        this.getRiskLinkAnalysis(0);
    }

    @Debounce()
    lazyloadAnalysis({first, rows}) {
        const page = Math.round(first / rows);
        this.getRiskLinkAnalysis(page, rows);
    }

    @Debounce()
    lazyloadPortfolios({first, rows}) {
        const page = Math.round(first / rows);
        this.getRiskLinkPortfolio(page, rows);
    }

    private getRiskLinkAnalysis(page = this.state.analysis.page, size = this.state.analysis.size) {
        return this.dispatch(new fromWs.GetRiskLinkAnalysisAction({
            rdmId: this.state.selection.currentDataSource,
            paginationParams: {page, size},
            projectId: this.selectedProject.projectId,
            instanceId: this.state.selection.rdms[this.state.selection.currentDataSource].instanceId,
            filter: this.parseFilter(this.filterAnalysis),
            userId: 1
        }));
    }

    private getRiskLinkPortfolio(page = this.portfolios.page, size = this.portfolios.size) {
        return this.dispatch(new fromWs.GetRiskLinkPortfolioAction({
            edmId: this.state.selection.currentDataSource,
            paginationParams: {page, size},
            projectId: this.selectedProject.projectId,
            instanceId: this.state.selection.edms[this.state.selection.currentDataSource].instanceId,
            filter: this.parseFilter(this.filterPortfolio),
            userId: 1
        }));
    }

    private parseFilter(filter) {
        let finalFilter = {...filter};
        _.keys(finalFilter).filter(key => !finalFilter[key]).forEach(key => delete finalFilter[key]);
        return finalFilter;
    }

    updateAllChecked(scope) {
        const selectedInChunk = 0; // _.filter(this.filterData(this.getTableData()), item => item.selected).length;
        /**
         if (scope === 'analysis') {
            if (selectedInChunk === 0) {
                this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({
                    action: 'selectChunk',
                    data: _.map(this.filterData(this.getTableData()), a => a.rlAnalysisId)
                }));
            } else {
                this.allCheckedAnalysis = true;
                this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({
                    action: 'unSelectChunk',
                    data: this.getSelectedAnalysisIds()
                }));
            }
        } else if (scope === 'portfolio') {
            if (selectedInChunk === 0) {
                this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({
                    action: 'selectChunk',
                    data: _.map(this.filterData(this.getTableData()), p => p.rlPortfolioId)
                }));
            } else {
                this.allCheckedPortolfios = true;
                this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({
                    action: 'unSelectChunk',
                    data: this.getSelectedPortfolioIds()
                }));
            }
        }
         */
        this.UpdateCheckboxStatus();
    }

    selectWithUnselect(row) {
        if (this.state.selectedEDMOrRDM === 'RDM') {
            this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({
                action: 'unSelectChunk',
                data: this.getSelectedAnalysisIds()
            }));
            this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({
                action: 'selectOne',
                value: true,
                rlAnalysisId: row.rlAnalysisId
            }));
        } else {
            this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({
                action: 'unSelectChunk',
                data: this.getSelectedPortfolioIds()
            }));
            this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({
                action: 'selectOne',
                value: true,
                rlPortfolioId: row.rlPortfolioId
            }));
        }
    }

    selectRows(row: any, index: number) {
        if (!(window as any).event.ctrlKey && !(window as any).event.shiftKey) {
            this.selectWithUnselect(row);
            this.lastSelectedIndex = index;
        }
        this.UpdateCheckboxStatus();
    }

    checkRow(event, rowData) {
        if (this.state.selectedEDMOrRDM === 'EDM') {
            this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({
                action: 'selectOne',
                rlPortfolioId: rowData.rlPortfolioId
            }));
        } else {
            this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({
                action: 'selectOne',
                rlAnalysisId: rowData.rlAnalysisId
            }));
        }
        this.UpdateCheckboxStatus();
    }

    changeCollapse(value) {
        this.dispatch(new fromWs.PatchRiskLinkCollapseAction({key: value}));
    }

    changeFinancialValidator(value, item) {
        this.dispatch(new fromWs.PatchRiskLinkFinancialPerspectiveAction({key: value, value: item}));
        if (value === 'division') {
            this.UpdateCheckboxStatus();
            this.setFilterDivision();
        }
    }

    navigateFromHyperLink({route}) {
        const {wsId, uwYear} = this.workspaceInfo;
        this.dispatch(
            [new UpdateWsRouting(this.wsIdentifier, route),
                new Navigate(route ? [`workspace/${wsId}/${uwYear}/${route}`] : [`workspace/${wsId}/${uwYear}/projects`])]
        );
    }

    handleCancel() {
        this.filterModalVisibility = false;
        this.linkingModalVisibility = false;
    }

    rowTrackBy = (index, item) => {
        return item.rlId;
    };

    ngOnDestroy(): void {
        this.destroy();
    }

    autoAttach() {
        const divisionsIds = _.map(this.state.financialValidator.division.data, d => d.divisionNumber);
        const edmIds = _.map(_.values(this.state.selection.edms), item => item.rlDataSourceId);
        const rdmIds = _.map(_.values(this.state.selection.rdms), item => item.rlDataSourceId);
        if (_.size(edmIds) == 0 && _.size(rdmIds) == 0) {
            alert('No available Edms / Rdms to attach');
            return;
        }
        if (_.size(divisionsIds) == 0) {
            alert('No available Edms / Rdms to attach');
            return;
        }

        this.dispatch(new fromWs.AutoAttachAction({
            instanceId: this.state.financialValidator.rmsInstance.selected.instanceId,
            projectId: this.selectedProject.projectId,
            divisionsIds,
            edmIds,
            rdmIds,
            wsId: this.ws.wsId,
            withDetailScan: true
        }))
    }

    saveConfiguration(type: string) {
        const projectId = this.selectedProject.projectId;
        if (type == 'ANALYSIS') {
            this.analysisSummary$
                .pipe(this.unsubscribeOnDestroy)
                .subscribe(analysisConfig => {
                    this.dispatch(new fromWs.SaveImportConfigurationAction({
                        type,
                        analysisConfig: this.transformAnalysisResultForImport(analysisConfig, projectId, this.analysisConfigFields)
                    }))
                });
        } else if (type == 'PORTFOLIO') {
            this.portfolioSummary$
                .pipe(this.unsubscribeOnDestroy)
                .subscribe(portfolioConfig => {
                    this.dispatch(new fromWs.SaveImportConfigurationAction({
                        type,
                        portfolioConfig: this.transformPortfolioResultForImport(portfolioConfig, projectId, this.portfolioConfigFields)
                    }))
                });
        } else {
            console.error('Unknown configuration type :', type);
        }
    }

    triggerImport() {
        forkJoin(
            [
                this.selectedProject$,
                this.summary$
            ].map(item => item.pipe(take(1)))
        )
            .pipe(this.unsubscribeOnDestroy)
            .subscribe(data => {
                const [p, summary] = data;
                const {projectId} = p;
                if (this.tabStatus == 'FAC' && this._nonUniqDivisionPerAnalysis(summary.analysis)) {
                    alert('You cannot import multiple Analysis Region Peril for the same divisions !');
                    return;
                }
                this.dispatch(new fromWs.TriggerImportAction({
                    instanceId: this.state.financialValidator.rmsInstance.selected.instanceId,
                    projectId,
                    userId: 1,
                    analysisConfig: this.transformAnalysisResultForImport(summary.analysis, projectId, this.analysisConfigFields),
                    portfolioConfig: this.transformPortfolioResultForImport(summary.portfolios, projectId, this.portfolioConfigFields)
                }));
            });
    }

    private _nonUniqDivisionPerAnalysis(analysis): boolean {
        let data = {};
        for (let a of analysis) {
            if (data[a.rpCode])
                data[a.rpCode].push(...a.divisions);
            else
                data[a.rpCode] = [...a.divisions];
            if (_.size(data[a.rpCode]) > 1)
                return true;
        }
        return false;
    }

    private transformAnalysisResultForImport(analysis, projectId, toBePicked) {
        return _.map(
            _.map(
                analysis,
                an => ({
                    ...an,
                    targetRAPCodes: _.map(an.targetRaps, item => item.targetRapCode),
                    targetRegionPeril: an.rpCode,
                    projectId
                })
            ),
            item => _.pick(item, toBePicked)
        );
    }

    private transformPortfolioResultForImport(portfolios, projectId, toBePicked) {
        return _.map(portfolios, p => _.pick({
                ...p,
                projectId
            }, toBePicked)
        );
    }

    private importInit() {
        if (!this.selectedProject) {
            console.error('No selected Project');
            return;
        }
        if (this.tabStatus == 'FAC') {
            const carId = this.selectedProject.carRequestId;
            this.dispatch(new fromWs.LoadRiskLinkDataAction({type: this.tabStatus, carId}));
        } else {
            this.dispatch(new fromWs.LoadRiskLinkDataAction({type: this.tabStatus, carId: null}));
        }
    }

    private getSelectedAnalysisIds() {
        return _.keys(this.state.selection.analysis[this.state.selection.currentDataSource]);
    }

    private getSelectedPortfolioIds() {
        return _.keys(this.state.selection.portfolios[this.state.selection.currentDataSource]);
    }

}
