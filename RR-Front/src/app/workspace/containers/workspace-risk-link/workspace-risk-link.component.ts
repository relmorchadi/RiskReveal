import {ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute, Router} from '@angular/router';
import {Select, Store} from '@ngxs/store';
import {WorkspaceState} from '../../store/states';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import * as fromWs from '../../store/actions';
import {PatchRiskLinkDisplayAction, UpdateWsRouting} from '../../store/actions';
import {DataTables} from './data';
import {BaseContainer} from '../../../shared/base';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from '../../../core/store/actions/header.action';
import {Navigate} from '@ngxs/router-plugin';
import {ConfirmationService, LazyLoadEvent} from "primeng/api";
import * as moment from 'moment';
import {combineLatest} from 'rxjs';
import {of} from 'rxjs/internal/observable/of';
import {TableSortAndFilterPipe} from "../../../shared/pipes/table-sort-and-filter.pipe";
import {NotificationService} from "../../../shared/services";

@Component({
  selector: 'app-workspace-risk-link',
  templateUrl: './workspace-risk-link.component.html',
  styleUrls: ['./workspace-risk-link.component.scss'],
  providers: [ConfirmationService]
})
export class WorkspaceRiskLinkComponent extends BaseContainer implements OnInit, StateSubscriber {

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

  @Select(WorkspaceState.getRiskLinkState) state$;
  state: any;

  @Select(WorkspaceState.getCurrentTabStatus) tabStatus$;
  tabStatus: any;

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

  filterAnalysis = {};

  filterPortfolio = {
    dataSourceId: '',
    number: '',
    dataSourceName: '',
    creationDate: '',
    descriptionType: '',
    type: '',
    agCedent: '',
    agCurrency: '',
    agSource: '',
    peril: '',
  };

  constructor(
    private _helper: HelperService,
    private route: ActivatedRoute,
    private tableFilter: TableSortAndFilterPipe,
    private confirmationService: ConfirmationService,
    private notification: NotificationService,
    _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef
  ) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    this.serviceSubscription = [
      this.state$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
        this.state = _.merge({}, value);
        this.detectChanges();
      }),
      this.listEdmRdm$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
        this.listEdmRdm = _.merge({}, value);
        this.detectChanges();
      }),
      this.analysis$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
        this.analysis = _.merge({}, value);
        this.detectChanges();
      }),
      this.tabStatus$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
        this.tabStatus = value;
        this.detectChanges();
      }),
      this.portfolios$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
        this.portfolios = _.merge({}, value);
        this.detectChanges();
      }),
      this.ws$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
        this.ws = _.merge({}, value);
      }),
      this.route.params.pipe(this.unsubscribeOnDestroy).subscribe(({wsId, year}) => {
        this.hyperLinksConfig = {wsId, uwYear: year};
        this.dispatch(new fromWs.LoadRiskLinkDataAction());
        if (this.tabStatus === 'fac') {
          this.dispatch([new fromWs.LoadFacDataAction()]);
        }
        this.detectChanges();
      })
    ];

    this.scrollableColsAnalysis = DataTables.scrollableColsAnalysis;
    this.frozenColsAnalysis = DataTables.frozenColsAnalysis;
    this.scrollableColsPortfolio = DataTables.scrollableColsPortfolio;
    this.frozenColsPortfolio = DataTables.frozenColsPortfolio;
  }

  patchState({wsIdentifier, data}: any): void {
    this.workspaceInfo = data;
    this.wsIdentifier = wsIdentifier;
  }

  setFilterDivision() {
    if (this.state.selectedEDMOrRDM === 'rdm') {
      this.filterAnalysis['analysisName'] = this.ws.wsId +  this.divisionTag[this.state.financialValidator.division.selected];
    } else {
      this.filterPortfolio['number'] = this.ws.wsId +  this.divisionTag[this.state.financialValidator.division.selected];
    }
  }

  pinWorkspace() {
    const {wsId, uwYear, workspaceName, programName, cedantName} = this.workspaceInfo;
    this.dispatch([
      new fromHeader.PinWs({
        wsId,
        uwYear,
        workspaceName,
        programName,
        cedantName
      }), new fromWs.MarkWsAsPinned({wsIdentifier: this.wsIdentifier})]);
  }

  unPinWorkspace() {
    const {wsId, uwYear} = this.workspaceInfo;
    this.dispatch([
      new fromHeader.UnPinWs({wsId, uwYear}),
      new fromWs.MarkWsAsNonPinned({wsIdentifier: this.wsIdentifier})
    ]);
  }

  loadDataOnScroll(event) {
    console.log(event);
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

  getDateValue(date) {
    if (_.isNumber(date)) {
      const newDate = moment(date);
      return newDate.format('DD/MM/YYYY');
    }
    const updateDate = date.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
    const dateFormat = moment(updateDate, 'MMM DD YYYY HH:mm');
    if (dateFormat.isValid()) {
      return dateFormat.format('DD/MM/YYYY');
    } else {
      const regularDate = moment(date);
      return regularDate.format('DD/MM/YYYY');
    }
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.columnsForConfig, event.previousIndex, event.currentIndex);
  }

  toggleCol(i: number) {
    this.columnsForConfig = _.merge(
      [],
      this.columnsForConfig,
      {[i]: {...this.columnsForConfig[i], visible: !this.columnsForConfig[i].visible}}
    );
  }

  /** */
  focusInput() {
    this.displayDropdownRDMEDM = !this.displayDropdownRDMEDM;
    this.searchInput.nativeElement.focus();
  }

  dataList(data, filter = null) {
    const array = _.toArray(data);
    if (filter === null) {
      return array;
    } else {
      return array.filter(dt => dt.type === filter);
    }
  }

  toggleItemsListRDM(RDM) {
    this.dispatch([new fromWs.ToggleRiskLinkEDMAndRDMSelectedAction(RDM), new fromWs.PatchAddToBasketStateAction()]);
    if (this.tabStatus === 'fac') {
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
    console.log(this.tables);
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

  /** */
  fillLists() {
    this.dispatch(new fromWs.SelectRiskLinkEDMAndRDMAction());
  }

  fillFacLists() {

  }

  selectedItem() {
    this.fillLists();
    this.closeDropdown();
    const array = [..._.toArray(this.state.listEdmRdm.selectedListEDMAndRDM.edm), ..._.toArray(this.state.listEdmRdm.selectedListEDMAndRDM.rdm)];
    if (array.length > 0) {
      this.dispatch(new PatchRiskLinkDisplayAction({key: 'displayListRDMEDM', value: true}));
    } else {
      this.dispatch(new PatchRiskLinkDisplayAction({key: 'displayListRDMEDM', value: true}));
    }
  }

  scanItem(item) {
    item.scanned = false;
    setTimeout(() =>
      item.scanned = true, 1000
    );
  }

  displayImported() {
    this.dispatch(new fromWs.PatchRiskLinkDisplayAction({key: 'displayImport', value: true}));
    this.dispatch(new fromWs.AddToBasketAction());
    if (this.tabStatus === 'fac') {
      this.confirmationService.confirm({
        message: 'You are attempting to import multiple analysis for the following region peril/division',
        rejectVisible: false,
        header: 'Warning',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
        }
      });
    }
  }

  getScrollableCols() {
    if (this.state.selectedEDMOrRDM === 'rdm') {
      return this.scrollableColsAnalysis;
    } else {
      return this.scrollableColsPortfolio;
    }
  }

  getFrozenCols() {
    if (this.state.selectedEDMOrRDM === 'rdm') {
      return this.frozenColsAnalysis;
    } else {
      return this.frozenColsPortfolio;
    }
  }

  getTableData() {
    if (this.state.selectedEDMOrRDM === 'rdm') {
      return _.toArray(_.get(this.analysis, 'data', null));
    } else {
      return _.toArray(_.get(this.portfolios, 'data', null));
    }
  }

  getSelection(event) {
    if (event.length > 1) {
      if (this.state.selectedEDMOrRDM === 'rdm') {
        this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'unSelectChunk', data: this.filterData( this.getTableData())}));
        this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'chunk', data: event}));
      } else {
        this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'unSelectChunk', data: this.filterData( this.getTableData())}));
        this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'chunk', data: event}));
      }
    }
    this.UpdateCheckboxStatus();
  }

  getIndeterminate() {
    const data = this.filterData(this.getTableData());
    const selection = _.filter(data, item => item.selected);
    // return (selection.length < data.length && selection.length > 0);
    if (this.state.selectedEDMOrRDM === 'rdm') {
      this.indeterminateAnalysis = (selection.length < data.length && selection.length > 0);
    } else {
      this.indeterminatePortfolio = (selection.length < data.length && selection.length > 0);
    }
    this.detectChanges();
  }

  getCheckedData() {
    const data = this.filterData(this.getTableData());
    const selection = _.filter(data, item => item.selected);
    if (this.state.selectedEDMOrRDM === 'rdm') {
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

  getTableRecords() {
    if (this.state.selectedEDMOrRDM === 'rdm') {
      return _.get(this.analysis, 'totalNumberElement', 0);
    } else {
      return _.get(this.portfolios, 'totalNumberElement', 0);
    }
  }

  getTitle() {
    return this.state.selectedEDMOrRDM === 'rdm' ? 'Analysis' : 'Portfolio';
  }

  filterData(data) {
    if (this.state.selectedEDMOrRDM === 'rdm') {
      return this.tableFilter.transform(data, [{}, this.filterAnalysis]);
    } else {
      return this.tableFilter.transform(data, [{}, this.filterPortfolio]);
    }
  }

  clearSelection(item, target) {
    this.dispatch(new fromWs.DeleteEdmRdmAction({id: item.id, target: target}));
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
        if (typeof this.state.portfolios[item.id] !== 'undefined') {
          return this.state.portfolios[item.id].totalNumberElement;
        } else {
          return null;
        }
      }
    } else if (source === 'analysis') {
      if (this.state.analysis === null) {
        return 0;
      } else {
        if (typeof this.state.analysis[item.id] !== 'undefined') {
          return this.state.analysis[item.id].numberOfElement;
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
        return _.filter(_.toArray(this.state.portfolios[item.id].data), dt => dt.selected).length;
      }
    } else if (source === 'analysis') {
      if (this.state.analysis === null) {
        return 0;
      } else {
        return _.filter(_.toArray(this.state.analysis[item.id].data), dt => dt.selected).length;
      }
    }
  }

  onInputSearch(event) {
    if (event.target.value.length > 2) {
      this.dispatch(new fromWs.SearchRiskLinkEDMAndRDMAction({keyword: event.target.value, size: '20'}));
    } else {
      this.dispatch(new fromWs.SearchRiskLinkEDMAndRDMAction({keyword: '', size: '20'}));
    }
    this.detectChanges();
  }

  loadItemsLazy(event) {
    let sizePage = '';
    if (event.first + event.rows > this.state.listEdmRdm.totalNumberElement) {
      sizePage = this.state.listEdmRdm.totalNumberElement.toString();
    } else {
      sizePage = event.first === 0 ? '20' : (event.first + event.rows).toString();
    }
    if (this.state.listEdmRdm.numberOfElement < event.first + event.rows) {
      this.dispatch(new fromWs.SearchRiskLinkEDMAndRDMAction({
        keyword: this.state.listEdmRdm.searchValue,
        size: sizePage,
      }));
    }

  }

  selectOne(row) {
    if (this.state.selectedEDMOrRDM === 'rdm') {
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
    const selectedInChunk = _.filter(this.filterData( this.getTableData()), item => item.selected).length;
    if (scope === 'analysis') {
      if (selectedInChunk === 0) {
        this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'chunk', data: this.filterData( this.getTableData())}));
      } else {
        this.allCheckedAnalysis = true;
        this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'unSelectChunk', data: this.filterData( this.getTableData())}));
      }
    } else if (scope === 'portfolio') {
      if (selectedInChunk === 0) {
        this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'chunk', data: this.filterData( this.getTableData())}));
      } else {
        this.allCheckedPortolfios = true;
        this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'unSelectChunk', data: this.filterData( this.getTableData())}));
      }
    }
    this.UpdateCheckboxStatus();
  }

  selectWithUnselect(row) {
    if (this.state.selectedEDMOrRDM === 'rdm') {
      this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'unSelectChunk', data: this.filterData( this.getTableData())}));
      this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'selectOne', value: true, item: row}));
    } else {
      this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'unSelectChunk', data: this.filterData( this.getTableData())}));
      this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'selectOne', value: true, item: row}));
    }
  }

  selectRows(row: any, index: number, target) {
    if (!(window as any).event.ctrlKey && !(window as any).event.shiftKey) {
      this.selectWithUnselect(row);
      this.lastSelectedIndex = index;
    }
    this.UpdateCheckboxStatus();
  }

  checkRow(event, rowData, target) {
    if (this.state.selectedEDMOrRDM === 'edm') {
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
  }

  ngOnDestroy(): void {
    this.destroy();
  }
}
