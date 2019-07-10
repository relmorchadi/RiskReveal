import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  ElementRef,
  OnDestroy,
  OnInit,
  ViewChild
} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute} from '@angular/router';
import {Select, Store} from '@ngxs/store';
import {combineLatest, Observable} from 'rxjs';
import {RiskLinkState} from '../../store/states';
import {RiskLinkModel} from '../../model/risk_link.model';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import {
  AddToBasketAction,
  ApplyFinancialPerspectiveAction, DeleteEdmRdmaction,
  DeleteFromBasketAction,
  LoadAnalysisForLinkingAction,
  LoadFinancialPerspectiveAction,
  LoadPortfolioForLinkingAction,
  PatchAddToBasketStateAction,
  PatchResultsAction,
  PatchTargetFPAction,
  RemoveFinancialPerspectiveAction,
  SaveFinancialPerspectiveAction,
  SearchRiskLinkEDMAndRDMAction,
  ToggleAnalysisForLinkingAction,
  ToggleRiskLinkAnalysisAction,
  ToggleRiskLinkEDMAndRDMSelectedAction,
  ToggleRiskLinkFPAnalysisAction,
  ToggleRiskLinkFPStandardAction,
  ToggleRiskLinkPortfolioAction,
  ToggleRiskLinkResultAction,
  ToggleRiskLinkSummaryAction
} from '../../store/actions/risk_link.actions';
import {
  LoadRiskLinkDataAction,
  PatchRiskLinkCollapseAction,
  PatchRiskLinkDisplayAction,
  PatchRiskLinkFinancialPerspectiveAction,
  SelectRiskLinkEDMAndRDMAction,
  ToggleRiskLinkEDMAndRDMAction
} from '../../store/actions';
import {DataTables} from './data';

@Component({
  selector: 'app-workspace-risk-link',
  templateUrl: './workspace-risk-link.component.html',
  styleUrls: ['./workspace-risk-link.component.scss'],
})
export class WorkspaceRiskLinkComponent implements OnInit, OnDestroy {

  regionPeril;
  hyperLinks: string[] = ['Risk link', 'File-based'];
  hyperLinksRoutes: any = {
    'Risk link': '/RiskLink',
    'File-based': '/FileBasedImport'
  };
  hyperLinksConfig: {
    wsId: string,
    uwYear: string
  };

  tree = [];
  targetRap = [
    {title: 'RL_EUWS_Mv11.2_S-1003-LTR-Scor27c72u', selected: false},
    {title: 'RL_EUWS_Mv11.2_S-65-LTR', selected: false},
    {title: 'RL_EUWS_Mv11.2_S-66-LTR-Clue', selected: false},
  ];

  globalRap = [
    {title: 'RL_EUWS_Mv11.2_S-1003-LTR-Scor27c72u', selected: false},
    {title: 'RL_EUWS_Mv11.2_S-65-LTR', selected: false},
    {title: 'RL_EUWS_Mv11.2_S-66-LTR-Clue', selected: false},
  ];

  lastSelectedIndex = null;
  lastSelectedIndexResult = null;
  lastSelectedIndexSummary = null;
  lastSelectedIndexFPstandard = null;
  lastSelectedIndexFPAnalysis = null;

  financialP = false;
  filterModalVisibility = false;
  linkingModalVisibility = false;
  editRowPopUp = false;

  manual = false;
  suggested = false;
  managePopUp = false;

  singleColEdit = false;
  editableRow = {item: null, target: null, title: null};

  radioValue = 'all';
  columnsForConfig;
  targetConfig;

  @ViewChild('searchInput')
  searchInput: ElementRef;
  @ViewChild('obRes')
  dropDownFPOb: any;
  @ViewChild('rpRes')
  dropDownFP: any;
  scopeForChanges = 'currentSelection';

  displayDropdownRDMEDM = false;
  displayListRDMEDM = false;

  serviceSubscription: any;

  occurrenceBasis;

  listEdmRdm: any = [];

  tableLeftAnalysis: any;
  tableAnalysisLinking: any;
  tableLeftPortfolio: any;
  tablePortfolioLinking: any;

  scrollableColsAnalysis: any;
  frozenColsAnalysis: any;

  scrollableColsPortfolio: any;
  frozenColsPortfolio: any;

  scrollableColsSummary: any;
  frozenColsSummary: any;

  scrollableColsResult: any;
  frozenColsResult: any;

  colsFinancialStandard: any;
  financialStandardContent: any;
  workingFSC: any;
  colsFinancialAnalysis: any;

  selectedEDM = null;
  scrollableColslinking: any;

  contextSelectedItem: any;
  itemCm = [
    {
      label: 'Edit', icon: 'pi pi-pencil', command: (event) => {
        this.editRowPopUp = true;
      }
    },
    {
      label: 'Delete Item',
      icon: 'pi pi-trash',
      command: () => this.deleteFromBasket(this.contextSelectedItem.id, 'results')
    },
  ];

  @Select(RiskLinkState)
  state$: Observable<RiskLinkModel>;
  state: RiskLinkModel = null;

  constructor(private _helper: HelperService, private route: ActivatedRoute, private store: Store, private cdRef: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.store.dispatch(new LoadRiskLinkDataAction());
    combineLatest(
      this.store.select(RiskLinkState.getFinancialPerspective)
    ).subscribe(
      ([fp]: any) => {
        this.workingFSC = fp;
      }
    );
    this.serviceSubscription = [
      this.state$.subscribe(value => this.state = _.merge({}, value)),
      this.store.select(st => st.RiskLinkModel.analysis).subscribe(dt => {
        this.tableLeftAnalysis = dt;
        this.detectChanges();
      }),
      this.store.select(st => st.RiskLinkModel.portfolios).subscribe(dt => {
        this.tableLeftPortfolio = dt;
        this.detectChanges();
      }),
      this.store.select(st => st.RiskLinkModel.listEdmRdm).subscribe(dt => {
        this.detectChanges();
      }),
      this.route.params.subscribe(({wsId, year}) => {
        this.hyperLinksConfig = {
          wsId,
          uwYear: year
        };
        this.detectChanges();
      })
    ];

    this.scrollableColsAnalysis = DataTables.scrollableColsAnalysis;
    this.frozenColsAnalysis = DataTables.frozenColsAnalysis;
    this.scrollableColsPortfolio = DataTables.scrollableColsPortfolio;
    this.frozenColsPortfolio = DataTables.frozenColsPortfolio;
    this.scrollableColsSummary = DataTables.scrollableColsSummary;
    this.frozenColsSummary = DataTables.frozenColsSummary;
    this.scrollableColsResult = DataTables.scrollableColsResults;
    this.frozenColsResult = DataTables.frozenColsResults;
    this.scrollableColslinking = DataTables.scrollableColsLinking;
    this.colsFinancialStandard = DataTables.colsFinancialStandard;
    this.colsFinancialAnalysis = DataTables.colsFinancialAnalysis;
    this.financialStandardContent = DataTables.financialStandarContent;
  }

  ngOnDestroy() {
    if (this.serviceSubscription)
      this.serviceSubscription.forEach(sub => sub.unsubscribe());
  }

  changeFinancialPTarget(target) {
  }

  /** Manage Columns Method's */
  toggleColumnsManager(target) {
    this.managePopUp = !this.managePopUp;
    if (this.managePopUp) {
      if (target === 'summaries') {
        this.columnsForConfig = [...this.scrollableColsSummary];
      } else if (target === 'results') {
        this.columnsForConfig = [...this.scrollableColsResult];
      }
      this.targetConfig = target;
    }
  }

  saveColumns() {
    this.toggleColumnsManager(this.targetConfig);
    if (this.targetConfig === 'summaries') {
      this.scrollableColsSummary = [...this.columnsForConfig];
    } else if (this.targetConfig === 'results') {
      this.scrollableColsResult = [...this.columnsForConfig];
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

  getChecked(item) {
    if (this.selectedEDM !== null) {
      if (item === null) {
        return false;
      } else {
        return item.name === this.selectedEDM;
      }
    }
  }

  getSelectedRDM() {
    return _.filter(_.toArray(this.state.linking.rdm), dt => dt.selected);
  }

  selectLinkedRDM(item) {
    console.log(item);
    this.store.dispatch(new LoadAnalysisForLinkingAction(item));
  }

  resetToMain() {
    this.manual = this.suggested = false;
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
    this.store.dispatch(new ToggleRiskLinkEDMAndRDMSelectedAction(RDM));
  }

  /** Select EDM & RDM DropDown Method's */
  toggleItems(RDM, event, source) {
    this.store.dispatch(new ToggleRiskLinkEDMAndRDMAction({RDM, action: 'selectOne', source}));
    if (event !== null) {
      event.stopPropagation();
    }
  }

  selectAll() {
    this.store.dispatch(new ToggleRiskLinkEDMAndRDMAction({action: 'selectAll', source: 'solo'}));
  }

  unselectAll() {
    this.store.dispatch(new ToggleRiskLinkEDMAndRDMAction({action: 'unselectAll', source: 'solo'}));
  }

  refreshAll() {

  }

  closeDropdown() {
    this.displayDropdownRDMEDM = false;
  }

  /** */
  fillLists() {
    this.store.dispatch(new SelectRiskLinkEDMAndRDMAction());
  }

  selectedItem() {
    this.fillLists();
    this.closeDropdown();
    const array = _.toArray(this.state.listEdmRdm.selectedListEDMAndRDM);
    array.length > 0 ? this.displayListRDMEDM = true : this.displayListRDMEDM = false;
  }

  scanItem(item) {
    item.scanned = false;
    setTimeout(() =>
      item.scanned = true, 1000
    );
  }

  displayImported() {
    this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayImport', value: true}));
    this.store.dispatch(new AddToBasketAction());
  }

  deleteFromBasket(id, target) {
    console.log(id, target)
    this.store.dispatch(new DeleteFromBasketAction({id: id, scope: target}));
  }

  toggleForLinkingEDM() {
    const item = _.filter(this.dataList(this.state.linking.edm), dt => dt.name === this.selectedEDM)[0];
    this.detectChanges();
    console.log(item, this.selectedEDM);
    this.store.dispatch(new LoadPortfolioForLinkingAction(item));
  }

  toggleForLinkingRDM(items) {
    this.store.dispatch(new ToggleAnalysisForLinkingAction({item: items, selected: !items.selected}));
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

  getLinkingPortfolio() {
    if (this.state.linking.portfolio === null) {
      return null;
    } else {
      return _.toArray(this.state.linking.portfolio.data);
    }
  }

  getLinkingAnalysis() {
    if (this.state.linking.analysis === null) {
      return null;
    } else {
      return _.toArray(this.state.linking.analysis.data);
    }
  }

  getTableData() {
    let dataTable;
    if (this.state.selectedEDMOrRDM === 'rdm') {
      const {id} = _.filter(this.state.listEdmRdm.selectedListEDMAndRDM.rdm, (dt) => dt.selected === true)[0];
      dataTable = _.get(this.tableLeftAnalysis, `${id}.data`, this.tableLeftAnalysis);
      return _.toArray(dataTable);
    } else {
      const {id} = _.filter(this.state.listEdmRdm.selectedListEDMAndRDM.edm, (dt) => dt.selected === true)[0];
      dataTable = _.get(this.tableLeftPortfolio, `${id}.data`, this.tableLeftPortfolio);
      return _.toArray(dataTable);
    }
  }

  getTitle() {
    return this.state.selectedEDMOrRDM === 'rdm' ? 'Analysis' : 'Portfolio';
  }

  clearSelection(item, target) {
    this.store.dispatch(new DeleteEdmRdmaction({id: item.id, target: target}));
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
      this.store.dispatch(new SearchRiskLinkEDMAndRDMAction({keyword: event.target.value, size: '20'}));
    } else {
      this.store.dispatch(new SearchRiskLinkEDMAndRDMAction({keyword: '', size: '20'}));
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
      console.log('you called for :' + sizePage);
      this.store.dispatch(new SearchRiskLinkEDMAndRDMAction({
        keyword: this.state.listEdmRdm.searchValue,
        size: sizePage
      }));
    }

  }

  selectOne(row) {
    if (this.state.selectedEDMOrRDM === 'rdm') {
      this.store.dispatch(new ToggleRiskLinkAnalysisAction({action: 'selectOne', value: true, item: row}));
    } else {
      this.store.dispatch(new ToggleRiskLinkPortfolioAction({action: 'selectOne', value: true, item: row}));
    }
  }

  selectWithUnselect(row) {
    if (this.state.selectedEDMOrRDM === 'rdm') {
      this.store.dispatch(new ToggleRiskLinkAnalysisAction({action: 'unselectAll'}));
      this.store.dispatch(new ToggleRiskLinkAnalysisAction({action: 'selectOne', value: true, item: row}));
    } else {
      this.store.dispatch(new ToggleRiskLinkPortfolioAction({action: 'unselectAll'}));
      this.store.dispatch(new ToggleRiskLinkPortfolioAction({action: 'selectOne', value: true, item: row}));
    }
  }

  selectRows(row: any, index: number, target) {
    if (target === 'A&P') {
      if ((window as any).event.ctrlKey) {
        this.selectOne(row);
        this.lastSelectedIndex = index;
      } else if ((window as any).event.shiftKey) {
        event.preventDefault();
        if (this.lastSelectedIndex || this.lastSelectedIndex === 0) {
          this.selectSection(Math.min(index, this.lastSelectedIndex), Math.max(index, this.lastSelectedIndex), 'A&P');
        } else {
          this.selectOne(row);
          this.lastSelectedIndex = index;
        }
      } else {
        this.selectWithUnselect(row);
        this.lastSelectedIndex = index;
      }
      this.store.dispatch(new PatchAddToBasketStateAction());
    } else if (target === 'R') {
      if ((window as any).event.ctrlKey) {
        this.store.dispatch(new ToggleRiskLinkResultAction({action: 'selectOne', value: true, item: row}));
        this.lastSelectedIndexResult = index;
      } else if ((window as any).event.shiftKey) {
        event.preventDefault();
        if (this.lastSelectedIndexResult || this.lastSelectedIndexResult === 0) {
          this.selectSection(Math.min(index, this.lastSelectedIndexResult), Math.max(index, this.lastSelectedIndexResult), 'R');
        } else {
          this.store.dispatch(new ToggleRiskLinkResultAction({action: 'selectOne', value: true, item: row}));
          this.lastSelectedIndexResult = index;
        }
      } else {
        this.store.dispatch(new ToggleRiskLinkResultAction({action: 'unselectAll'}));
        this.store.dispatch(new ToggleRiskLinkResultAction({action: 'selectOne', value: true, item: row}));
        this.lastSelectedIndexResult = index;
      }
    } else if (target === 'S') {
      if ((window as any).event.ctrlKey) {
        this.store.dispatch(new ToggleRiskLinkSummaryAction({action: 'selectOne', value: true, item: row}));
        this.lastSelectedIndexSummary = index;
      } else if ((window as any).event.shiftKey) {
        event.preventDefault();
        if (this.lastSelectedIndexSummary || this.lastSelectedIndexSummary === 0) {
          this.selectSection(Math.min(index, this.lastSelectedIndexSummary), Math.max(index, this.lastSelectedIndexSummary), 'S');
        } else {
          this.store.dispatch(new ToggleRiskLinkSummaryAction({action: 'selectOne', value: true, item: row}));
          this.lastSelectedIndexSummary = index;
        }
      } else {
        this.store.dispatch(new ToggleRiskLinkSummaryAction({action: 'unselectAll'}));
        this.store.dispatch(new ToggleRiskLinkSummaryAction({action: 'selectOne', value: true, item: row}));
        this.lastSelectedIndexSummary = index;
      }
    } else if (target === 'fpS') {
      if ((window as any).event.ctrlKey) {
        this.store.dispatch(new ToggleRiskLinkFPStandardAction({action: 'selectOne', value: true, item: row}));
        this.lastSelectedIndexFPstandard = index;
      } else if ((window as any).event.shiftKey) {
        event.preventDefault();
        if (this.lastSelectedIndexFPstandard || this.lastSelectedIndexFPstandard === 0) {
          this.selectSection(Math.min(index, this.lastSelectedIndexFPstandard), Math.max(index, this.lastSelectedIndexFPstandard), 'fpS');
        } else {
          this.store.dispatch(new ToggleRiskLinkFPStandardAction({action: 'selectOne', value: true, item: row}));
          this.lastSelectedIndexFPstandard = index;
        }
      } else {
        this.store.dispatch(new ToggleRiskLinkFPStandardAction({action: 'unselectAll'}));
        this.store.dispatch(new ToggleRiskLinkFPStandardAction({action: 'selectOne', value: true, item: row}));
        this.lastSelectedIndexFPstandard = index;
      }
    } else if (target === 'fpA') {
      if ((window as any).event.ctrlKey) {
        this.store.dispatch(new ToggleRiskLinkFPAnalysisAction({action: 'selectOne', value: true, item: row}));
        this.lastSelectedIndexFPAnalysis = index;
      } else if ((window as any).event.shiftKey) {
        event.preventDefault();
        if (this.lastSelectedIndexFPAnalysis || this.lastSelectedIndexFPAnalysis === 0) {
          this.selectSection(Math.min(index, this.lastSelectedIndexFPAnalysis), Math.max(index, this.lastSelectedIndexFPAnalysis), 'fpA');
        } else {
          this.store.dispatch(new ToggleRiskLinkFPAnalysisAction({action: 'selectOne', value: true, item: row}));
          this.lastSelectedIndexFPAnalysis = index;
        }
      } else {
        this.store.dispatch(new ToggleRiskLinkFPAnalysisAction({action: 'unselectAll'}));
        this.store.dispatch(new ToggleRiskLinkFPAnalysisAction({action: 'selectOne', value: true, item: row}));
        this.lastSelectedIndexFPAnalysis = index;
      }
    }
  }

  private selectSection(from, to, target) {
    if (target === 'A&P') {
      if (this.state.selectedEDMOrRDM === 'rdm') {
        this.store.dispatch(new ToggleRiskLinkAnalysisAction({action: 'unselectAll'}));
      } else {
        this.store.dispatch(new ToggleRiskLinkPortfolioAction({action: 'unselectAll'}));
      }
      if (from === to) {
        if (this.state.selectedEDMOrRDM === 'rdm') {
          this.store.dispatch(new ToggleRiskLinkAnalysisAction({
            action: 'selectOne',
            value: true,
            item: this.getTableData()[from]
          }));
        } else {
          this.store.dispatch(new ToggleRiskLinkPortfolioAction({
            action: 'selectOne',
            value: true,
            item: this.getTableData()[from]
          }));
        }
      } else {
        for (let i = from; i <= to; i++) {
          if (this.state.selectedEDMOrRDM === 'rdm') {
            this.store.dispatch(new ToggleRiskLinkAnalysisAction({
              action: 'selectOne',
              value: true,
              item: this.getTableData()[i]
            }));
          } else {
            this.store.dispatch(new ToggleRiskLinkPortfolioAction({
              action: 'selectOne',
              value: true,
              item: this.getTableData()[i]
            }));
          }
        }
      }
    } else if (target === 'R') {
      this.store.dispatch(new ToggleRiskLinkResultAction({action: 'unselectAll'}));
      if (from === to) {
        this.store.dispatch(new ToggleRiskLinkResultAction({
          action: 'selectOne',
          value: true,
          item: this.dataList(this.state.results.data)[from]
        }));
      } else {
        for (let i = from; i <= to; i++) {
          this.store.dispatch(new ToggleRiskLinkResultAction({
            action: 'selectOne',
            value: true,
            item: this.dataList(this.state.results.data)[i]
          }));
        }
      }
    } else if (target === 'S') {
      this.store.dispatch(new ToggleRiskLinkSummaryAction({action: 'unselectAll'}));
      if (from === to) {
        this.store.dispatch(new ToggleRiskLinkSummaryAction({
          action: 'selectOne',
          value: true,
          item: this.dataList(this.state.summaries.data)[from]
        }));
      } else {
        for (let i = from; i <= to; i++) {
          this.store.dispatch(new ToggleRiskLinkSummaryAction({
            action: 'selectOne',
            value: true,
            item: this.dataList(this.state.summaries.data)[i]
          }));
        }
      }
    } else if (target === 'fpS') {
      this.store.dispatch(new ToggleRiskLinkFPStandardAction({action: 'unselectAll'}));
      if (from === to) {
        this.store.dispatch(new ToggleRiskLinkFPStandardAction({
          action: 'selectOne',
          value: true,
          item: this.dataList(this.workingFSC.standard)[from]
        }));
      } else {
        for (let i = from; i <= to; i++) {
          this.store.dispatch(new ToggleRiskLinkFPStandardAction({
            action: 'selectOne',
            value: true,
            item: this.dataList(this.workingFSC.standard)[i]
          }));
        }
      }
    } else if (target === 'fpA') {
      this.store.dispatch(new ToggleRiskLinkFPAnalysisAction({action: 'unselectAll'}));
      if (from === to) {
        this.store.dispatch(new ToggleRiskLinkFPAnalysisAction({
          action: 'selectOne',
          value: true,
          item: this.dataList(this.workingFSC.analysis.data)[from]
        }));
      } else {
        for (let i = from; i <= to; i++) {
          this.store.dispatch(new ToggleRiskLinkFPAnalysisAction({
            action: 'selectOne',
            value: true,
            item: this.dataList(this.workingFSC.analysis.data)[i]
          }));
        }
      }
    }

  }

  checkRow(event, rowData, target) {
    if (target === 'A&P') {
      if (this.state.selectedEDMOrRDM === 'edm') {
        this.store.dispatch(new ToggleRiskLinkPortfolioAction({action: 'selectOne', value: event, item: rowData}));
      } else {
        this.store.dispatch(new ToggleRiskLinkAnalysisAction({action: 'selectOne', value: event, item: rowData}));
      }
    } else if (target === 'fpS') {
      this.store.dispatch(new ToggleRiskLinkFPStandardAction({action: 'selectOne', value: event, item: rowData}));
    } else if (target === 'fpA') {
      this.store.dispatch(new ToggleRiskLinkFPAnalysisAction({action: 'selectOne', value: event, item: rowData}));
    }
    console.log(event);
  }

  changeCollapse(value) {
    this.store.dispatch(new PatchRiskLinkCollapseAction({key: value}));
  }

  changeFinancialValidator(value, item) {
    this.store.dispatch(new PatchRiskLinkFinancialPerspectiveAction({key: value, value: item}));
  }

  openFinancialP() {
    this.financialP = true;
    this.store.dispatch(new LoadFinancialPerspectiveAction(this.financialStandardContent));
    this.lastSelectedIndexFPstandard = null;
    this.lastSelectedIndexFPAnalysis = null;
  }

  changeTargetFP(event) {
    this.store.dispatch(new PatchTargetFPAction(event));
  }

  handleCancel() {
    this.filterModalVisibility = false;
    this.linkingModalVisibility = false;
  }

  replaceFinancialP() {
    this.store.dispatch(new ApplyFinancialPerspectiveAction('replace'));
  }

  saveFinancialP() {
    this.store.dispatch(new SaveFinancialPerspectiveAction());
    this.financialP = false;
  }

  addFinancialP() {
    this.store.dispatch(new ApplyFinancialPerspectiveAction('add'));
  }

  removeFP(item, fp) {
    console.log(item, fp);
    this.store.dispatch(new RemoveFinancialPerspectiveAction({item, fp}));
  }

  updateRow(item, target) {
    let title;
    if (target === 'Rp') {
      title = 'Override Region Peril';
    } else if (target === 'Ob') {
      title = 'Override Occurrence Basis';
    } else if (target === 'Peqt') {
      title = 'Target Rap Selection';
      this.getTreeApp();
    }
    this.scopeForChanges = 'currentSelection';
    this.editableRow = {item: item, target: target, title: title};
    this.singleColEdit = true;
  }

  getTreeApp() {
    const data = _.toArray(this.state.results.data);
    const regions = _.unionBy(data.map(dt => dt.regionPeril));
    this.tree = regions.map(dt => {
      return ({title: dt, expanded: true, selected: false, children: []});
    });
    this.tree.forEach(dt => {
      dt.children = [...this.getInnerTree(dt.title)];
    });
  }

  getInnerTree(target) {
    const data = _.toArray(this.state.results.data);
    let array = [];
    data.forEach(dt => {
      if (target === dt.regionPeril) {
        array = [...array, {
          title: `${dt.analysisId} | ${dt.analysisName} | ${dt.description}`,
          selected: false,
          selectedItems: this.targetRap
        }];
      }
    });
    return array;
  }

  getCheckedValue(item) {
    let tab = _.filter(this.tree, dt => dt.selected === true);
    if (tab.length === 0) {
      this.tree.forEach(dt => {
        tab = _.filter(dt.children, ws => ws.selected);
      });
      if (tab.length === 0) {
        this.globalRap.forEach(dt => {
          if (dt.title === item) {
            return dt.selected;
          }
        });
      } else {
        tab[0].selectedItems.forEach(dt => {
          if (dt.title === item) {
            return dt.selected;
          }
        });
      }
    } else {
      let selected = true;
      tab[0].children.forEach(dt => dt.selectedItems.forEach(ws => {
        if (ws.title === item) {
          if (ws.selected === false) {
            selected = false;
          }
        }
      }));
      return selected;
    }
  }

  checkTargetRap(item, value) {
    let tab = _.filter(this.tree, dt => dt.selected === true);
    console.log(tab);
    if (tab.length === 0) {
      this.tree.forEach(dt => {
        tab = _.filter(dt.children, ws => ws.selected);
      });
      console.log(tab);
      if (tab.length === 0) {
        this.tree.forEach(dt => dt.children.forEach(ws => ws.selectedItems.forEach(kt => {
          if (kt.title === item) {
            kt.selected = !value;
          }
        })));
      } else {
        this.tree.forEach(dt => {
          dt.children.forEach(ws => {
            if (ws.title === tab[0].title) {
              ws.selectedItems.forEach(kt => {
                if (kt.title === item) {
                  kt.selected = !value;
                }
              });
            }
          });
        });
      }
    } else {
      this.tree.forEach(kt => {
          if (kt.title === tab[0].title) {
            kt.children.forEach(dt => dt.selectedItems.forEach(ws => {
              if (ws.title === item) {
                ws.selected = !value;
              }
            }));
          }
        }
      );
    }
    console.log(this.tree);
  }

  hideChild(item) {
    item.expanded = !item.expanded;
  }

  selectBranch(item) {
    this.tree.forEach(dt => {
      dt.selected = false;
      dt.children.forEach(ws => ws.selected = false);
    });
    item.selected = true;
  }

  saveChangesForResult() {

  }

  editSingleColRes(target, $event = null, row = null) {
    if (target === 'Rp') {
      this.singleColEdit = false;
      this.store.dispatch(new PatchResultsAction({
        id: row.id,
        target: 'regionPeril',
        value: this.dropDownFP.value,
        scope: this.scopeForChanges === 'currentSelection' ? 'Single' : 'All'
      }));
    } else if (target === 'Ob') {
      this.singleColEdit = false;
      this.store.dispatch(new PatchResultsAction({
        id: row.id,
        target: 'occurrenceBasis',
        value: this.dropDownFPOb.value,
        scope: this.scopeForChanges === 'currentSelection' ? 'Single' : 'All'
      }));
    } else if (target === 'TC') {
      if ($event !== null && row !== null) {
        this.store.dispatch(new PatchResultsAction({id: row.id, target: 'targetCurrency', value: $event}));
      }
    } else if (target === 'UM') {
      this.store.dispatch(new PatchResultsAction({id: row.id, target: 'unitMultiplier', value: $event}));
    }
  }

  detectChanges() {
    if (!this.cdRef['destroyed']) {
      this.cdRef.detectChanges();
    }
  }
}
