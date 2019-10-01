import {ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {DataTables} from '../data';
import * as fromWs from '../../../store/actions';
import {ApplyRegionPerilAction, SaveEditAnalysisAction} from '../../../store/actions';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import {Select, Store} from '@ngxs/store';
import * as _ from 'lodash';
import {WorkspaceState} from '../../../store/states';
import {combineLatest} from 'rxjs';

@Component({
  selector: 'app-risk-link-res-summary',
  templateUrl: './risk-link-res-summary.component.html',
  styleUrls: ['./risk-link-res-summary.component.scss']
})
export class RiskLinkResSummaryComponent implements OnInit {
  filterModalVisibility = false;
  linkingModalVisibility = false;
  editRowPopUp = false;
  managePopUpSummaries = false;
  managePopUpResults = false;
  managePopUpLinkAnalysis = false;
  managePopUpLinkPortfolio = false;
  managePopUpFPAnalysis = false;
  managePopUpFPStandard = false;
  columnsForConfig;
  financialP = false;
  peqtP = false;

  selectedEDM = null;
  filterPopUp = null;
  radioValue = 'all';

  @ViewChild('searchInput')
  searchInput: ElementRef;
  @ViewChild('obRes')
  dropDownFPOb: any;
  @ViewChild('peqtt')
  tablepeqt: any;
  scopeForChanges = 'currentSelection';

  singleColEdit = false;
  editableRow = {item: null, target: null, title: null};

  lastSelectedIndexResult = null;
  lastSelectedIndexSummary = null;
  lastSelectedIndexFPstandard = null;
  lastSelectedIndexFPAnalysis = null;
  lastSelectedIndexLinkAnalysis = null;

  workingFSC: any;
  serviceSubscription: any;

  scrollableColsSummary: any;
  frozenColsSummary: any;

  scrollableColsResult: any;
  frozenColsResult: any;

  scrollableColsLinkingPortfolio: any;
  scrollableColsLinkingAnalysis: any;
  colsFinancialStandard: any;
  colsFinancialAnalysis: any;
  financialStandardContent: any;
  regionPerilDataTable: any;
  peqtDataTable: any;
  targetRapDataTable: any;

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

  occurrenceBasis;
  targetCurrency;
  proportion;
  unitMultiplier;

  targetOfEdit = 'currentSelection';

  tree = [];
  regionPeril;

  @Select(WorkspaceState.getRiskLinkState) state$;
  state: any;

  @Select(WorkspaceState.getLinkingData) linking$;
  linking: any;

  @Select(WorkspaceState.getSummaries) summaries$;

  @Select(WorkspaceState.getResults) results$;

  @Select(WorkspaceState.getCurrentTabStatus) tabStatus$;
  wsStatus: any;

  analysis = [];
  portfolio = [];
  updateMode = false;

  allCheckedAnalysis: boolean;
  indeterminateAnalysis: boolean;

  collapseDataSources = false;
  collapseFPDataTable = false;

  analysisResults;
  resultsSummary;

  expandedRows: {} = {};

  constructor(private store: Store, private cdRef: ChangeDetectorRef) {
  }

  ngOnInit() {
    combineLatest(
      this.store.select(WorkspaceState.getFinancialPerspective)
    ).subscribe(
      ([fp]: any) => {
        this.workingFSC = fp;
      }
    );
    this.serviceSubscription = [
      this.state$.subscribe(value => {
        this.state = _.merge({}, value);
        this.detectChanges();
      }),
      this.results$.pipe().subscribe( value => {
          this.analysisResults = _.toArray(value);
          this.detectChanges();
        }
      ),
      this.tabStatus$.subscribe(value => {
        this.wsStatus = value;
        this.detectChanges();
      }),
      this.summaries$.pipe().subscribe(value => {
          this.resultsSummary = _.toArray(value);
          this.detectChanges();
        }
      ),
      this.linking$.pipe().subscribe(value => {
        this.linking = _.merge({}, value);
        if (this.linking.analysis !== null) {
          if (_.get(this.linking.analysis, `${this.linking.rdm.selected.id}`, null) !== null) {
            this.analysis = _.toArray(this.linking.analysis[this.linking.rdm.selected.id].data);
            this.allCheckedAnalysis = this.linking.analysis[this.linking.rdm.selected.id].allChecked;
            this.indeterminateAnalysis = this.linking.analysis[this.linking.rdm.selected.id].indeterminate;
          }
        }
        if (this.linking.portfolio !== null) {
          if (_.get(this.linking, 'portfolio', null) !== null) {
            this.portfolio = _.toArray(this.linking.portfolio.data);
          }
        }
        if (this.linking.portfolio === null && this.linking.analysis === null ) {
          this.portfolio = this.analysis = [];
        }
        this.detectChanges();
      })
    ];

    this.scrollableColsSummary = DataTables.scrollableColsSummary;
    this.frozenColsSummary = DataTables.frozenColsSummary;
    this.scrollableColsResult = DataTables.scrollableColsResults;
    this.frozenColsResult = DataTables.frozenColsResults;
    this.scrollableColsLinkingPortfolio = DataTables.scrollableColsLinking;
    this.scrollableColsLinkingAnalysis = DataTables.scrollableColsLinking;
    this.colsFinancialStandard = DataTables.colsFinancialStandard;
    this.colsFinancialAnalysis = DataTables.colsFinancialAnalysis;
    this.financialStandardContent = DataTables.financialStandarContent;
    this.regionPerilDataTable = DataTables.regionPerilDataTable;
    this.peqtDataTable = DataTables.peqtDatatable;
    this.targetRapDataTable = DataTables.targetRapDataTable;
  }

  /** Manage Columns Method's */

  saveColumns(event, scope) {
    this.closePopUp();
    if (scope === 'summaries') {
      this.scrollableColsSummary = [...event];
    } else if (scope === 'results') {
      this.scrollableColsResult = [...event];
    } else if (scope === 'linkPortfolio') {
      this.scrollableColsLinkingPortfolio = [...event];
    } else if (scope === 'linkAnalysis') {
      this.scrollableColsLinkingAnalysis = [...event];
    } else if (scope === 'FPAnalysis') {
      this.colsFinancialAnalysis = [...event];
    } else if (scope === 'FPStandard') {
      this.colsFinancialStandard = [...event];
    }
  }

  closePopUp() {
    this.managePopUpLinkAnalysis = false;
    this.managePopUpLinkPortfolio = false;
    this.managePopUpResults = false;
    this.managePopUpResults = false;
    this.managePopUpFPAnalysis = false;
  }

  cloneData(data) {
    if (data === undefined) {
      return null;
    }
    return [...data];
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.columnsForConfig, event.previousIndex, event.currentIndex);
  }

  /** */
  getTableHeight() {
    return this.collapseDataSources ? '315px' : '203px';
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
    return _.filter(_.toArray(this.linking.rdm.data), dt => dt.selected);
  }

  selectLinkedRDM(item) {
    this.store.dispatch(new fromWs.LoadAnalysisForLinkingAction(item));
  }

  toggleForLinkingEDM() {
    const item = _.filter(this.dataList(this.linking.edm), dt => dt.name === this.selectedEDM)[0];
    this.detectChanges();
    this.store.dispatch(new fromWs.LoadPortfolioForLinkingAction(item));
  }

  toggleForLinkingRDM(items) {
    this.store.dispatch(new fromWs.ToggleAnalysisForLinkingAction({item: items, selected: !items.selected}));
  }

  editSingleColRes(target, $event = null, row = null) {
    if (target === 'Rp') {
      this.singleColEdit = false;
      this.store.dispatch(new fromWs.PatchResultsAction({target: 'regionPeril'}));
    } else if (target === 'Ob') {
      this.singleColEdit = false;
      this.store.dispatch(new fromWs.PatchResultsAction({
        id: row.id, target: 'occurrenceBasis', value: this.dropDownFPOb.value,
        scope: this.scopeForChanges === 'currentSelection' ? 'Single' : 'All'
      }));
    } else if (target === 'TC') {
      if ($event !== null && row !== null) {
        this.store.dispatch(new fromWs.PatchResultsAction({id: row.id, target: 'targetCurrency', value: $event}));
      }
    } else if (target === 'UM') {
      this.store.dispatch(new fromWs.PatchResultsAction({id: row.id, target: 'unitMultiplier', value: $event}));
    } else if (target === 'Peqt') {
      this.store.dispatch(new fromWs.SaveEditPEQTAction({data: this.tree}));
      this.peqtP = false;
    }
  }

  updateAllChecked(value, scope) {
    const selection = value ? 'selectAll' : 'unselectAll';
    if (scope === 'summaries') {
      const selected = _.filter(this.resultsSummary, item => item.selected).length;
      if (selected === 0) {
        this.store.dispatch(new fromWs.ToggleRiskLinkSummaryAction({action: 'selectAll'}));
      } else {
        this.state.summaries.allChecked = true;
        this.store.dispatch(new fromWs.ToggleRiskLinkSummaryAction({action: 'unselectAll'}));
      }
    } else if (scope === 'results') {
      const selected = _.filter(this.analysisResults, item => item.selected).length;
      if (selected === 0) {
        this.store.dispatch(new fromWs.ToggleRiskLinkResultAction({action: 'selectAll'}));
      } else {
        this.state.results.allChecked = true;
        this.store.dispatch(new fromWs.ToggleRiskLinkResultAction({action: 'unselectAll'}));
      }
    } else if (scope === 'FPAnalysis') {
      const selected = _.filter(this.dataList(this.workingFSC.analysis.data), item => item.selected).length;
      if (selected === 0) {
        this.store.dispatch(new fromWs.ToggleRiskLinkFPAnalysisAction({action: 'selectAll'}));
      } else {
        this.workingFSC.analysis.allChecked = true;
        this.store.dispatch(new fromWs.ToggleRiskLinkFPAnalysisAction({action: 'unselectAll'}));
      }
    } else if (scope === 'FPStandard') {
      const selected = _.filter(this.dataList(this.workingFSC.standard.data), item => item.selected).length;
      if (selected === 0) {
        this.store.dispatch(new fromWs.ToggleRiskLinkFPStandardAction({action: 'selectAll'}));
      } else {
        this.workingFSC.standard.allChecked = true;
        this.store.dispatch(new fromWs.ToggleRiskLinkFPStandardAction({action: 'unselectAll'}));
      }
    } else if (scope === 'linkingAnalysis') {
      const wrapperEDM = this.linking.rdm.selected;
      const selected = _.filter(this.dataList(this.state.results.data), item => item.selected).length;
      if (selected === 0) {
        this.store.dispatch(new fromWs.ToggleAnalysisLinkingAction({action: 'selectAll', wrapper: wrapperEDM}));
      } else {
        this.allCheckedAnalysis = true;
        this.store.dispatch(new fromWs.ToggleAnalysisLinkingAction({action: 'unselectAll', wrapper: wrapperEDM}));
      }
    }
  }

  checkRow(event, rowData, target) {
    if (target === 'R') {
      this.store.dispatch(new fromWs.ToggleRiskLinkResultAction({action: 'selectOne', value: event, item: rowData}));
    } else if (target === 'S') {
      console.log(event, rowData, target);
      this.store.dispatch(new fromWs.ToggleRiskLinkSummaryAction({action: 'selectOne', value: event, item: rowData}));
    } else if (target === 'fpS') {
      this.store.dispatch(new fromWs.ToggleRiskLinkFPStandardAction({action: 'selectOne', value: event, item: rowData}));
    } else if (target === 'fpA') {
      this.store.dispatch(new fromWs.ToggleRiskLinkFPAnalysisAction({action: 'selectOne', value: event, item: rowData}));
    } else if (target === 'linkAnalysis') {
      const wrapperEDM = this.linking.rdm.selected;
      this.store.dispatch(new fromWs.ToggleAnalysisLinkingAction({action: 'selectOne', wrapper: wrapperEDM, value: event, item: rowData}));
    } else if (target === 'linkPortfolio') {
      this.store.dispatch(new fromWs.TogglePortfolioLinkingAction({action: 'unselectAll'}));
      this.store.dispatch(new fromWs.TogglePortfolioLinkingAction({action: 'selectOne', value: event, item: rowData}));
    }
  }

  private _selectRowsProvider(row, index, lastIndex, action: any, target) {
    if ((window as any).event.ctrlKey) {
      this.store.dispatch(action({action: 'selectOne', value: true, item: row}));
    } else if ((window as any).event.shiftKey) {
      event.preventDefault();
      if (lastIndex || lastIndex === 0) {
        this._selectSection(Math.min(index, lastIndex), Math.max(index, lastIndex), target);
      } else {
        this.store.dispatch(action({action: 'selectOne', value: true, item: row}));
      }
    } else {
      this.store.dispatch(action({action: 'unselectAll'}));
      this.store.dispatch(action({action: 'selectOne', value: true, item: row}));
    }
  }

  selectRows(row: any, index: number, target, source = null) {
    if (target === 'R') {
      const action = (payload) => new fromWs.ToggleRiskLinkResultAction(payload);
      this._selectRowsProvider(row, index, this.lastSelectedIndexResult, action, 'R');
      this.lastSelectedIndexResult = index;
    } else if (target === 'S') {
      const action = (payload) => new fromWs.ToggleRiskLinkSummaryAction(payload);
      this._selectRowsProvider(row, index, this.lastSelectedIndexSummary, action, 'S');
      this.lastSelectedIndexSummary = index;
    } else if (target === 'fpS') {
      const action = (payload) => new fromWs.ToggleRiskLinkFPStandardAction(payload);
      this._selectRowsProvider(row, index, this.lastSelectedIndexFPstandard, action, 'fpS');
      this.lastSelectedIndexFPstandard = index;
    } else if (target === 'fpA') {
      const action = (payload) => new fromWs.ToggleRiskLinkFPAnalysisAction(payload);
      this._selectRowsProvider(row, index, this.lastSelectedIndexFPAnalysis, action, 'fpA');
      this.lastSelectedIndexFPAnalysis = index;
    }  else if (target === 'linkAnalysis') {
      const wrapperEDM = this.linking.rdm.selected;
      if ((window as any).event.ctrlKey) {
        this.store.dispatch(new fromWs.ToggleAnalysisLinkingAction({action: 'selectOne',
          wrapper: wrapperEDM, value: true, item: row}));
      } else if ((window as any).event.shiftKey) {
        event.preventDefault();
        if (this.lastSelectedIndexLinkAnalysis || this.lastSelectedIndexLinkAnalysis === 0) {
          this._selectSection(Math.min(index, this.lastSelectedIndexLinkAnalysis),
            Math.max(index, this.lastSelectedIndexLinkAnalysis), target);
        } else {
          this.store.dispatch(new fromWs.ToggleAnalysisLinkingAction({action: 'selectOne',
            wrapper: wrapperEDM, value: true, item: row}));
        }
      } else {
        this.store.dispatch(new fromWs.ToggleAnalysisLinkingAction({action: 'unselectAll', wrapper: wrapperEDM}));
        this.store.dispatch(new fromWs.ToggleAnalysisLinkingAction({action: 'selectOne', wrapper: wrapperEDM, value: true, item: row}));
      }
      this.lastSelectedIndexLinkAnalysis = index;
    } else if (target === 'linkPortfolio') {
      this.store.dispatch(new fromWs.TogglePortfolioLinkingAction({action: 'unselectAll'}));
      this.store.dispatch(new fromWs.TogglePortfolioLinkingAction({action: 'selectOne', value: true, item: row}));
    }
  }

  private _selectSectionProvider(from, to, action: any, state) {
    if (from === to) {
      this.store.dispatch(action({action: 'unselectAll'}));
      this.store.dispatch(action({
        action: 'selectOne', value: true, item: this.dataList(state)[from]
      }));
    } else {
      this.store.dispatch(action({
        action: 'chunk', from, to
      }));
    }
  }

  private _selectSection(from, to, target) {
    if (target === 'R') {
      const action = (payload) => new fromWs.ToggleRiskLinkResultAction(payload);
      this._selectSectionProvider(from, to, action, this.state.results.data);
    } else if (target === 'S') {
      const action = (payload) => new fromWs.ToggleRiskLinkSummaryAction(payload);
      this._selectSectionProvider(from, to, action, this.state.summaries.data);
    } else if (target === 'fpS') {
      const action = (payload) => new fromWs.ToggleRiskLinkFPStandardAction(payload);
      this._selectSectionProvider(from, to, action, this.workingFSC.standard.data);
    } else if (target === 'fpA') {
      const action = (payload) => new fromWs.ToggleRiskLinkFPAnalysisAction(payload);
      this._selectSectionProvider(from, to, action, this.workingFSC.analysis.data);
    } else if (target === 'linkAnalysis') {
      const wrapperEDM = this.linking.rdm.selected;
      if (from === to) {
        this.store.dispatch(new fromWs.ToggleAnalysisLinkingAction({action: 'unselectAll', wrapper: wrapperEDM}));
        this.store.dispatch(new fromWs.ToggleAnalysisLinkingAction({
          action: 'selectOne', wrapper: wrapperEDM, value: true, item: this.linking.analysis[wrapperEDM.id].data[from]
        }));
      } else {
        this.store.dispatch(new fromWs.ToggleAnalysisLinkingAction({
          action: 'chunk', wrapper: wrapperEDM, from, to
        }));
      }
    }
  }

  unselectAllTable(scope) {
    if (scope === 'R') {
      this.store.dispatch(new fromWs.ToggleRiskLinkResultAction({action: 'unselectAll'}));
    } else if (scope === 'S') {
      this.store.dispatch(new fromWs.ToggleRiskLinkSummaryAction({action: 'unselectAll'}));
    } else if (scope === 'fpS') {
      this.store.dispatch(new fromWs.ToggleRiskLinkFPStandardAction({action: 'unselectAll'}));
    } else if (scope === 'fpA') {
      this.store.dispatch(new fromWs.ToggleRiskLinkFPAnalysisAction({action: 'unselectAll'}));
    } else if (scope === 'linkAnalysis') {
      const wrapperEDM = this.linking.rdm.selected;
      this.store.dispatch(new fromWs.ToggleAnalysisLinkingAction({action: 'unselectAll', wrapper: wrapperEDM}));
    } else if (scope === 'linkPortfolio') {
      this.store.dispatch(new fromWs.TogglePortfolioLinkingAction({action: 'unselectAll'}));
    }
  }

  changeCollapse(value) {
    this.store.dispatch(new fromWs.PatchRiskLinkCollapseAction({key: value}));
  }

  sortChange(field: any, sortCol: any, scope) {
    console.log(field, sortCol);
    if (scope === 'Analysis') {
      if (sortCol === '') {
        this.analysisResults.sort();
      } else if (sortCol === 'asc') {

      } else if (sortCol === 'desc') {

      }
    }
  }

  dataList(data, filter = null) {
    const array = _.toArray(data);
    if (filter === null) {
      return array;
    } else {
      return array.filter(dt => dt.type === filter);
    }
  }

  deleteFromBasket(id, target) {
    this.store.dispatch(new fromWs.DeleteFromBasketAction({id: id, scope: target}));
  }

  openFinancialP(row) {
    this.financialP = true;
    this.store.dispatch(new fromWs.LoadFinancialPerspectiveAction(this.financialStandardContent));
    this.selectRows(row, null, 'fpA');
    this.lastSelectedIndexFPstandard = null;
    this.lastSelectedIndexFPAnalysis = null;
  }

  changeTargetFP(event) {
    this.store.dispatch(new fromWs.PatchTargetFPAction(event));
  }

  changeFinancialPTarget(target) {
  }

  updateFpOverride(value, row) {
    this.store.dispatch(new fromWs.ApplyRegionPerilAction({row: row, scope: 'single', value: value}));
  }

  handleCancel() {
    this.filterModalVisibility = false;
    this.linkingModalVisibility = false;
  }

  replaceFinancialP() {
    this.store.dispatch(new fromWs.ApplyFinancialPerspectiveAction('replace'));
  }

  saveFinancialP() {
    this.store.dispatch(new fromWs.SaveFinancialPerspectiveAction());
    this.financialP = false;
  }

  addFinancialP() {
    this.store.dispatch(new fromWs.ApplyFinancialPerspectiveAction('add'));
  }

  removeFP(item, fp) {
    this.store.dispatch(new fromWs.RemoveFinancialPerspectiveAction({item, fp}));
  }

  updatePeqt() {
    this.peqtP = true;
    this.getTreeApp();
  }

  updateRow(item, target) {
    let title;
    if (target === 'Rp') {
      title = 'Override Region Peril';
    } else if (target === 'Ob') {
      title = 'Override Occurrence Basis';
    }
    this.scopeForChanges = 'currentSelection';
    this.editableRow = {item: item, target: target, title: title};
    this.singleColEdit = true;
  }

  saveEdit() {
    this.store.dispatch(new SaveEditAnalysisAction({
      occurenceBasis: this.occurrenceBasis,
      targetCurrency: this.targetCurrency,
      proportion: this.proportion,
      unitMultiplier: this.unitMultiplier,
      scope: this.targetOfEdit
    }));
    this.editRowPopUp = false;
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

  applyRpToAll(row) {
    this.store.dispatch(new ApplyRegionPerilAction({row: row, scope: 'all'}));
  }

  getTreeApp() {
    const data = _.toArray(this.state.results.data);
    const regions = _.unionBy(data.map(dt => dt.regionPeril));
    this.tree = regions.map((dt, key) => {
      return ({
        id: key,
        title: dt, expanded: true, selected: false, children: [],
        selectedItems: [{title: 'RL_EUWS_Mv11.2_S-1003-LTR-Scor27c72u', selected: false},
          {title: 'RL_EUWS_Mv11.2_S-65-LTR', selected: false},
          {title: 'RL_EUWS_Mv11.2_S-66-LTR-Clue', selected: false}]
      });
    });
    this.tree.forEach(dt => {
      dt.children = [...this.getInnerTree(dt.title)];
    });

    this.tree.forEach(
      dt => {
        dt.selectedItems.forEach( (st, key) => {
          const filtered = _.filter(dt.children, child => child.selectedItems[key].selected);
          if (filtered.length === dt.children.length) {
            st.selected = true;
          }
        });
      }
    );

    const thisRef = this;
    this.tree.forEach((car) => {
      thisRef.expandedRows[car.id] = true;
    });

    this.expandedRows = Object.assign({}, this.expandedRows);
  }

  getInnerTree(target) {
    const data = _.toArray(this.state.results.data);
    let array = [];
    data.forEach(dt => {
      if (target === dt.regionPeril) {
        array = [...array, {
          title: `${dt.analysisId} | ${dt.analysisName} | ${dt.description}`,
          analysisId: dt.analysisId,
          analysisName: dt.analysisName,
          selected: false,
          selectedItems: _.cloneDeep(dt.peqt)
        }];
      }
    });
    return array;
  }

  getCodeFp(item) {
    return _.filter(this.financialStandardContent, dt => dt.code === item)[0].financialPerspective;
  }

  getSelectedPeqt(row) {
    return _.filter(this.state.results.data[row.id].peqt, ws => ws.selected === true).length;
  }

  changePeqt(parent, target, selected) {
    _.forEach(parent.children, dt => {
      _.forEach(dt.selectedItems, kt => {
        if (kt.title === target) {
          kt.selected = !selected;
        }
      });
    });
  }

  changeInnerPeqt(parent, child, target, selected) {
    const data = _.filter(parent.children, dt => _.filter(dt.selectedItems, kt => kt.title === target)[0].selected === true);
    if (data.length === parent.children.length - 1 && selected === false) {
      _.forEach(parent.selectedItems, dt => {
        if (dt.title === target) {
          dt.selected = true;
        }
      });
    } else {
      _.forEach(parent.selectedItems, dt => {
        if (dt.title === target) {
          dt.selected = false;
        }
      });
    }
  }

  setLinkingState() {
    this.linkingModalVisibility = true;
    const firstLink = _.toArray(this.linking.edm)[0];
    this.selectedEDM === null ? this.selectedEDM = firstLink.name : null;
  }

  patchLinkingMode() {
    this.store.dispatch(new fromWs.PatchLinkingModeAction());
  }

  createLinking() {
    this.store.dispatch(new fromWs.CreateLinkingAction(this.linking.rdm.selected));
  }

  selectLink(link) {
    this.store.dispatch(new fromWs.UpdateStatusLinkAction({link: link, select: !link.selected, updated: link.update}));
  }

  initUpdateLink(link) {
    this.updateMode = true;
    this.store.dispatch(new fromWs.UpdateStatusLinkAction({link: link, select: true, updated: true}));
  }

  updateLinking() {
    this.updateMode = false;
  }

  cancelUpdate() {
    this.updateMode = false;
  }

  deleteLink(item) {
    this.store.dispatch(new fromWs.DeleteLinkAction(item));
  }

  deleteComponentLink(link, item, target) {
    this.store.dispatch(new fromWs.DeleteInnerLinkAction({link: link, item: item, target: target}));
  }

  rowTrackBy = (index, item) => {
    return item.id;
  }

  detectChanges() {
    if (!this.cdRef['destroyed']) {
      this.cdRef.detectChanges();
    }
  }
}
