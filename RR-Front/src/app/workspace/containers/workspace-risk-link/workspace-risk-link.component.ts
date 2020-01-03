import {ChangeDetectionStrategy, ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute, Router} from '@angular/router';
import {Actions, ofActionDispatched, ofActionSuccessful, Select, Store} from '@ngxs/store';
import {WorkspaceState} from '../../store/states';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import * as fromWs from '../../store/actions';
import {PatchRiskLinkDisplayAction, UpdateWsRouting} from '../../store/actions';
import {DataTables} from './data';
import {BaseContainer} from '../../../shared/base';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from '../../../core/store/actions/header.action';
import {Navigate} from '@ngxs/router-plugin';
import {ConfirmationService, LazyLoadEvent} from 'primeng/api';
import * as moment from 'moment';
import {combineLatest, forkJoin} from 'rxjs';
import {of} from 'rxjs/internal/observable/of';
import {TableSortAndFilterPipe} from '../../../shared/pipes/table-sort-and-filter.pipe';
import {NotificationService} from '../../../shared/services';
import {debounceTime, take, takeUntil, withLatestFrom} from 'rxjs/operators';
import {SetCurrentTab} from '../../store/actions';
import {FormControl} from "@angular/forms";

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
  divisionTag = {
    'Division N°1': '_01',
    'Division N°2': '_02',
    'Division N°3': '_03'
  };

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

  serviceSubscription: any;

  occurrenceBasis;

  scrollableColsAnalysis: any;
  frozenColsAnalysis: any;

  scrollableColsPortfolio: any;
  frozenColsPortfolio: any;

  @Select(WorkspaceState.getCurrentWorkspaces) ws$;
  ws: any;

  @Select(WorkspaceState.getSelectedProject) selectedProject$;

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

  filterAnalysis = {
    rlId: '',
    analysisName: '',
    analysisDescription: '',
    engineType: '',
    runDate: '',
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
    name: '',
    description: '',
    created:  null,
    type: '',
    agCurrency: '',
    agCedent: '',
    agSource: '',
    peril: '',
  };

  datasourceKeywordFc: FormControl;

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
    this.dispatch(new fromWs.LoadRiskLinkDataAction());
    setTimeout(()=>{
      this.dispatch(new fromWs.SearchRiskLinkEDMAndRDMAction({
        instanceId: this.state.financialValidator.rmsInstance.selected,
        keyword: '',
        offset: 0,
        size: 100,
      }));
    },500);

    this.serviceSubscription = [
      this.state$.pipe().subscribe(value => {
        this.state = _.merge({}, value);
        this.detectChanges();
      }),
      this.listEdmRdm$.pipe().subscribe(value => {
        this.listEdmRdm = _.merge({}, value);
        this.detectChanges();
      }),
      this.ws$.pipe().subscribe(value => {
        this.ws = _.merge({}, value);
        this.wsStatus = this.ws.workspaceType;
        this.detectChanges();
      }),
      this.analysis$.pipe().subscribe(value => {
        this.analysis = _.merge({}, value);
        this.detectChanges();
      }),
      this.portfolios$.pipe().subscribe(value => {
        this.portfolios = _.merge({}, value);
        this.detectChanges();
      }),
      this.selectedProject$.pipe().subscribe(value => {
        this.tabStatus = _.get(value, 'projectType', null);
        this.detectChanges();
        if (this.wsStatus === 'fac') {
          if (this.tabStatus === 'fac') {
            this.dispatch([new fromWs.LoadFacDataAction()]);
          } else if (this.tabStatus === 'treaty') {
            this.dispatch(new fromWs.LoadRiskLinkDataAction());
          }
        }
      }),
      this.route.params.pipe().subscribe(({wsId, year}) => {
        this.hyperLinksConfig = {wsId, uwYear: year};
        this.dispatch(new fromWs.LoadRiskLinkDataAction());
        if (this.wsStatus === 'fac') {
          if (this.tabStatus === 'fac') {
            this.dispatch([new fromWs.LoadFacDataAction()]);
          } else if (this.tabStatus === 'treaty') {
            this.dispatch(new fromWs.LoadRiskLinkDataAction());
          }
        }
        this.detectChanges();
      }),
      this.actions$
        .pipe(
          ofActionDispatched(SetCurrentTab)
        ).subscribe(({payload}) => {
        if (payload.wsIdentifier != this.wsIdentifier) this.destroy();
        this.detectChanges();
      })
    ];

    this.scrollableColsAnalysis = DataTables.scrollableColsAnalysis;
    this.frozenColsAnalysis = DataTables.frozenColsAnalysis;
    this.scrollableColsPortfolio = DataTables.scrollableColsPortfolio;
    this.frozenColsPortfolio = DataTables.frozenColsPortfolio;

    this.datasourceKeywordFc.valueChanges
      .pipe(debounceTime(500))
      .subscribe(val => {
        this.onInputSearch(val);
      })
  }

  patchState({wsIdentifier, data}: any): void {
    this.workspaceInfo = data;
    this.wsIdentifier = wsIdentifier;
  }


  lazyLoadDataSources(lazyLoadEvent) {
    console.log('Datasources lazy load', lazyLoadEvent);
    const {first, rows} = lazyLoadEvent;
    if (first + rows < this.state.listEdmRdm.totalElements) {
      this.dispatch(new fromWs.SearchRiskLinkEDMAndRDMAction({
        instanceId: this.state.financialValidator.rmsInstance.selected,
        keyword: this.datasourceKeywordFc.value,
        offset: first,
        size: rows,
      }));
    }
  }

  autoLinkAnalysis() {
    this.dispatch(new fromWs.PatchRiskLinkDisplayAction({key: 'displayImport', value: true}));
    this.dispatch(new fromWs.AddToBasketDefaultAction());
    this.validate$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      if (value !== undefined && value !== null) {
        this.showPopUp = !value;
      }
    });
  }

  setFilterDivision() {
    if (this.state.selectedEDMOrRDM === 'RDM') {
      this.filterAnalysis['analysisName'] = this.ws.wsId + this.divisionTag[this.state.financialValidator.division.selected];
    } else {
      this.filterPortfolio['number'] = this.ws.wsId + this.divisionTag[this.state.financialValidator.division.selected];
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
      instanceId: this.state.financialValidator.rmsInstance.selected,
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

  saveEDMAndRDMSelection() {
    this.dispatch(new fromWs.SaveEDMAndRDMSelectionAction());
    this.notification.createNotification('Information',
      'The Current EDM And RDM Selection is Saved.',
      'info', 'bottomRight', 4000);
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

  /** */
  focusInput() {
    this.displayDropdownRDMEDM = !this.displayDropdownRDMEDM;
    this.searchInput.nativeElement.focus();
  }

  toggleItemsListRDM(datasource) {
    this.selectedProject$.pipe(take(1))
      .subscribe(p => {
        const {projectId} = p;
        const {rmsId, type} = datasource;
        this.dispatch([new fromWs.ToggleRiskLinkEDMAndRDMSelectedAction({
          instanceId: this.state.financialValidator.rmsInstance.selected,
          projectId,
          rmsId,
          type
        }), new fromWs.PatchAddToBasketStateAction()]);
      });
    if (this.tabStatus === 'FAC') {
      this.setFilterDivision();
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
        this.dispatch(new fromWs.DatasourceScanAction({
          instanceId: this.state.financialValidator.rmsInstance.selected,
          selectedDS,
          projectId
        }));
      });
    this.closeDropdown();
  }

  rescanItem(datasource) {
    console.log('Rescan Datasource', datasource)
    datasource.scanned = false;
    this.selectedProject$.pipe(take(1))
      .subscribe(p => {
        const projectId = p.projectId;
        this.dispatch(new fromWs.ReScanDataSource({
          instanceId: this.state.financialValidator.rmsInstance.selected,
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
    ).subscribe(data => {
      const [p, analysisPortfolioSelection] = data;
      console.log('Those are selected section', analysisPortfolioSelection);
      console.log('Project', p);
      const {analysis, portfolios} = analysisPortfolioSelection;
      this.dispatch(new fromWs.RunDetailedScanAction({
        instanceId: this.state.financialValidator.rmsInstance.selected,
        projectId: p.projectId,
        analysis,
        portfolios
      }));
    });

  }

  getScrollableCols() {
    if (this.state.selectedEDMOrRDM === 'RDM') {
      return this.scrollableColsAnalysis;
    } else {
      return this.scrollableColsPortfolio;
    }
  }

  getFrozenCols() {
    if (this.state.selectedEDMOrRDM === 'RDM') {
      return this.frozenColsAnalysis;
    } else {
      return this.frozenColsPortfolio;
    }
  }

  getTableData() {
    if (this.state.selectedEDMOrRDM === 'RDM') {
      return this.analysis || [];
    } else {
      return this.portfolios || [];
    }
  }

  getSelection(event) {
    console.log('Analysis Portfolio selection', event);
    if (event.length > 1) {
      if (this.state.selectedEDMOrRDM === 'RDM') {
        this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'unSelectChunk', data: this.filterData( this.getTableData())}));
        this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'selectChunk', data: event}));
      } else {
        this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'unSelectChunk', data: this.filterData( this.getTableData())}));
        this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'selectChunk', data: event}));
      }
    }
    this.UpdateCheckboxStatus();
  }

  getIndeterminate() {
    const data = this.filterData(this.getTableData());
    const selection = _.filter(data, item => item.selected);
    if (this.state.selectedEDMOrRDM === 'RDM') {
      this.indeterminateAnalysis = (selection.length < data.length && selection.length > 0);
    } else {
      this.indeterminatePortfolio = (selection.length < data.length && selection.length > 0);
    }
    this.detectChanges();
  }

  getCheckedData() {
    const data = this.filterData(this.getTableData());
    const selection = _.filter(data, item => item.selected);
    if (this.state.selectedEDMOrRDM === 'RDM') {
      this.allCheckedAnalysis = selection.length === data.length && selection.length > 0;
    } else {
      this.allCheckedPortolfios = selection.length === data.length && selection.length > 0;
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
    this.dispatch(new fromWs.DeleteEdmRdmAction({rmsId: item.rmsId, target: target}));
  }

  synchronizeEDMAndRDMSelection() {
    this.dispatch(new fromWs.SynchronizeEDMAndRDMSelectionAction());
  }

  removeEDMAndRDMSelection() {
    this.dispatch(new fromWs.RemoveEDMAndRDMSelectionAction());
  }

  getNumberElement(item, source) {
    if (source === 'portfolio') {
      if (this.state.portfolios === null) {
        return 0;
      } else {
        if (typeof this.state.portfolios !== 'undefined') {
          return this.state.portfolios.length;
        } else {
          return null;
        }
      }
    } else if (source === 'analysis') {
      if (this.state.analysis === null) {
        return 0;
      } else {
        if (typeof this.state.analysis !== 'undefined') {
          return this.state.analysis.length;
        } else {
          return null;
        }
      }
    }
  }

  getNumberOfSelected(item, source) {
    if (source === 'portfolio') {
      if (this.state.portfolios === null) {
        return 0;
      } else {
        return _.filter(this.state.portfolios, dt => dt.selected).length;
      }
    } else if (source === 'analysis') {
      if (this.state.analysis === null) {
        return 0;
      } else {
        return _.filter(this.state.analysis, dt => dt.selected).length;
      }
    }
  }


  selectOne(row) {
    if (this.state.selectedEDMOrRDM === 'RDM') {
      this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'selectOne', value: true, item: row}));
    } else {
      this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'selectOne', value: true, item: row}));
    }
  }

  unselectAllTable(scope) {
    if (scope === 'analysis') {
      this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'unselectAll'}));
    } else if (scope === 'portfolio') {
      this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'unselectAll'}));
    }
    this.UpdateCheckboxStatus();
  }

  updateAllChecked(scope) {
    // const selected = _.filter(this.getTableData(), item => item.selected).length;
    const selectedInChunk = _.filter(this.filterData(this.getTableData()), item => item.selected).length;
    if (scope === 'analysis') {
      if (selectedInChunk === 0) {
        this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({
          action: 'selectChunk',
          data: this.filterData(this.getTableData())
        }));
      } else {
        this.allCheckedAnalysis = true;
        this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({
          action: 'unSelectChunk',
          data: this.filterData(this.getTableData())
        }));
      }
    } else if (scope === 'portfolio') {
      if (selectedInChunk === 0) {
        this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({
          action: 'selectChunk',
          data: this.filterData(this.getTableData())
        }));
      } else {
        this.allCheckedPortolfios = true;
        this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({
          action: 'unSelectChunk',
          data: this.filterData(this.getTableData())
        }));
      }
    }
    this.UpdateCheckboxStatus();
  }

  selectWithUnselect(row) {
    if (this.state.selectedEDMOrRDM === 'RDM') {
      this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'unSelectChunk', data: this.filterData( this.getTableData())}));
      this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'selectOne', value: true, item: row}));
    } else {
      this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'unSelectChunk', data: this.filterData( this.getTableData())}));
      this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'selectOne', value: true, item: row}));
    }
  }

  selectRows(row: any, index: number) {
    console.log(this.filterAnalysis, this.filterPortfolio);
    if (!(window as any).event.ctrlKey && !(window as any).event.shiftKey) {
      this.selectWithUnselect(row);
      this.lastSelectedIndex = index;
    }
    this.UpdateCheckboxStatus();
  }

  checkRow(event, rowData) {
    if (this.state.selectedEDMOrRDM === 'EDM') {
      this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'selectOne', value: event, item: rowData}));
    } else {
      this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'selectOne', value: event, item: rowData}));
    }
    this.UpdateCheckboxStatus();
  }

  changeCollapse(value) {
    this.dispatch(new fromWs.PatchRiskLinkCollapseAction({key: value}));
  }

  changeFinancialValidator(value, item) {
    this.dispatch(new fromWs.PatchRiskLinkFinancialPerspectiveAction({key: value, value: item}));
    console.log(value, value === 'division');
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
    return item.id;
  };

  ngOnDestroy(): void {
    this.destroy();
  }

  triggerImport() {
    const analysisConfigFields = ['financialPerspectives', 'projectId', 'proportion', 'rlAnalysisId', 'targetCurrency', 'targetRAPCodes', 'targetRegionPeril', 'unitMultiplier'];
    const portfolioConfigFields = ['analysisRegions', 'importLocationLevel', 'projectId', 'proportion', 'rlPortfolioId', 'targetCurrency', 'unitMultiplier'];

    forkJoin(
      [
        this.selectedProject$,
        this.summary$
      ]
        .map(item => item.pipe(take(1)))
    ).subscribe(data => {
      const [p, summary] = data;
      const {projectId} = p;
      this.dispatch(new fromWs.TriggerImportAction({
        instanceId: this.state.financialValidator.rmsInstance.selected,
        projectId,
        userId: 1,
        analysisConfig: this.transformAnalysisResultForImport(summary.analysis, projectId, analysisConfigFields),
        portfolioConfig: this.transformPortfolioResultForImport(summary.portfolios, projectId, portfolioConfigFields)
      }));
    });
  }

  private transformAnalysisResultForImport(analysis, projectId, toBePicked) {
    return _.map(
      _.map(
        analysis,
          an => ({
              ...an,
              targetRAPCodes: an.targetRaps,
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


}
