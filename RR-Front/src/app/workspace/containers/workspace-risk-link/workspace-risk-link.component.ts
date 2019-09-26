import {ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute, Router} from '@angular/router';
import {Select, Store} from '@ngxs/store';
import {WorkspaceState} from '../../store/states';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import * as fromWs from '../../store/actions';
import {LoadFacDataAction, PatchRiskLinkDisplayAction, UpdateWsRouting} from '../../store/actions';
import {DataTables} from './data';
import {BaseContainer} from '../../../shared/base';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from '../../../core/store/actions/header.action';
import {Navigate} from '@ngxs/router-plugin';
import {LazyLoadEvent} from "primeng/api";

@Component({
  selector: 'app-workspace-risk-link',
  templateUrl: './workspace-risk-link.component.html',
  styleUrls: ['./workspace-risk-link.component.scss'],
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

  constructor(
    private _helper: HelperService,
    private route: ActivatedRoute,
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
        this.allCheckedAnalysis = this.analysis.allChecked;
        this.indeterminateAnalysis = this.analysis.indeterminate;
        this.detectChanges();
      }),
      this.tabStatus$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
        this.tabStatus = value;
        this.detectChanges();
      }),
      this.portfolios$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
        this.portfolios = _.merge({}, value);
        this.allCheckedPortolfios = this.portfolios.allChecked;
        this.indeterminatePortfolio = this.portfolios.indeterminate;
        this.detectChanges();
      }),
      this.route.params.pipe(this.unsubscribeOnDestroy).subscribe(({wsId, year}) => {
        this.hyperLinksConfig = {wsId, uwYear: year};
        this.dispatch(new fromWs.LoadRiskLinkDataAction());
        if (this.tabStatus === 'fac') {
          this.dispatch(new LoadFacDataAction());
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

  closePopUp() {
    this.managePopUpPortfolio = false;
    this.managePopUpAnalysis = false;
  }

  cloneData(data) {
    return [...data];
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
    this.dispatch(new fromWs.ToggleRiskLinkEDMAndRDMSelectedAction(RDM));
    this.detectChanges();
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
      return _.toArray(this.analysis.data);
    } else {
      return _.toArray(this.portfolios.data);
    }
  }

  getTableRecords() {
    if (this.state.selectedEDMOrRDM === 'rdm') {
      return this.analysis.totalNumberElement;
    } else {
      return this.portfolios.totalNumberElement;
    }
  }

  getTitle() {
    return this.state.selectedEDMOrRDM === 'rdm' ? 'Analysis' : 'Portfolio';
  }

  clearSelection(item, target) {
    this.dispatch(new fromWs.DeleteEdmRdmAction({id: item.id, target: target}));
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
  }

  updateAllChecked(scope) {
    const selected = _.filter(this.getTableData(), item => item.selected).length;
    if (scope === 'analysis') {
      if (selected === 0) {
        this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'selectAll'}));
      } else {
        this.allCheckedAnalysis = true;
        this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'unselectAll'}));
      }
    } else if (scope === 'portfolio') {
      if (selected === 0) {
        this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'selectAll'}));
      } else {
        this.allCheckedPortolfios = true;
        this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'unselectAll'}));
      }
    }
  }

  selectWithUnselect(row) {
    if (this.state.selectedEDMOrRDM === 'rdm') {
      this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'unselectAll'}));
      this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'selectOne', value: true, item: row}));
    } else {
      this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'unselectAll'}));
      this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'selectOne', value: true, item: row}));
    }
  }

  selectRows(row: any, index: number, target) {
    if ((window as any).event.ctrlKey) {
      this.selectOne(row);
      this.lastSelectedIndex = index;
    } else if ((window as any).event.shiftKey) {
      event.preventDefault();
      if (this.lastSelectedIndex || this.lastSelectedIndex === 0) {
        this.selectSection(Math.min(index, this.lastSelectedIndex), Math.max(index, this.lastSelectedIndex));
      } else {
        this.selectOne(row);
        this.lastSelectedIndex = index;
      }
    } else {
      this.selectWithUnselect(row);
      this.lastSelectedIndex = index;
    }
    this.dispatch(new fromWs.PatchAddToBasketStateAction());
  }

  checkRow(event, rowData, target) {
    if (this.state.selectedEDMOrRDM === 'edm') {
      this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'selectOne', value: event, item: rowData}));
    } else {
      this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'selectOne', value: event, item: rowData}));
    }
  }

  changeCollapse(value) {
    this.dispatch(new fromWs.PatchRiskLinkCollapseAction({key: value}));
  }

  changeFinancialValidator(value, item) {
    this.dispatch(new fromWs.PatchRiskLinkFinancialPerspectiveAction({key: value, value: item}));
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

  private selectSection(from, to) {
    if (from === to) {
      if (this.state.selectedEDMOrRDM === 'rdm') {
        this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({action: 'unselectAll'}));
        this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({
          action: 'selectOne', value: true, item: this.getTableData()[from]
        }));
      } else {
        this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({action: 'unselectAll'}));
        this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({
          action: 'selectOne', value: true, item: this.getTableData()[from]
        }));
      }
    } else {
      if (this.state.selectedEDMOrRDM === 'rdm') {
        this.dispatch(new fromWs.ToggleRiskLinkAnalysisAction({
          action: 'chunk', from, to
        }));
      } else {
        this.dispatch(new fromWs.ToggleRiskLinkPortfolioAction({
          action: 'chunk', from, to
        }));
      }
    }
  }

}
