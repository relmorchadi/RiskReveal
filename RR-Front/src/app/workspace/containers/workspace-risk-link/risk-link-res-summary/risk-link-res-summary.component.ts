import {ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {DataTables} from '../data';
import * as fromWs from '../../../store/actions';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import {Select, Store} from '@ngxs/store';
import * as _ from 'lodash';
import {RiskLinkState, WorkspaceState} from '../../../store/states';
import {combineLatest, Observable} from 'rxjs';
import {RiskLinkModel} from '../../../model/risk_link.model';

@Component({
  selector: 'app-risk-link-res-summary',
  templateUrl: './risk-link-res-summary.component.html',
  styleUrls: ['./risk-link-res-summary.component.scss']
})
export class RiskLinkResSummaryComponent implements OnInit {
  filterModalVisibility = false;
  linkingModalVisibility = false;
  editRowPopUp = false;
  manual = false;
  suggested = false;
  managePopUp = false;
  columnsForConfig;
  targetConfig;
  financialP = false;

  selectedEDM = null;

  @ViewChild('searchInput')
  searchInput: ElementRef;
  @ViewChild('obRes')
  dropDownFPOb: any;
  @ViewChild('rpRes')
  dropDownFP: any;
  scopeForChanges = 'currentSelection';

  singleColEdit = false;
  editableRow = {item: null, target: null, title: null};

  lastSelectedIndexResult = null;
  lastSelectedIndexSummary = null;
  lastSelectedIndexFPstandard = null;
  lastSelectedIndexFPAnalysis = null;

  workingFSC: any;
  serviceSubscription: any;

  scrollableColsSummary: any;
  frozenColsSummary: any;

  scrollableColsResult: any;
  frozenColsResult: any;

  scrollableColslinking: any;
  colsFinancialStandard: any;
  colsFinancialAnalysis: any;
  financialStandardContent: any;
  regionPerilDataTable: any;

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
  tree = [];
  regionPeril;

  @Select(WorkspaceState.getRiskLinkState) state$;
  state: any;

  @Select(WorkspaceState.getLinkingData) linking$;
  linking: any;

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
      }),
      this.linking$.pipe().subscribe(value => {
        this.linking = _.merge({}, value);
        this.detectChanges();
      })
    ];
    this.scrollableColsSummary = DataTables.scrollableColsSummary;
    this.frozenColsSummary = DataTables.frozenColsSummary;
    this.scrollableColsResult = DataTables.scrollableColsResults;
    this.frozenColsResult = DataTables.frozenColsResults;
    this.scrollableColslinking = DataTables.scrollableColsLinking;
    this.colsFinancialStandard = DataTables.colsFinancialStandard;
    this.colsFinancialAnalysis = DataTables.colsFinancialAnalysis;
    this.financialStandardContent = DataTables.financialStandarContent;
    this.regionPerilDataTable = DataTables.regionPerilDataTable;
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

  getChecked(item) {
    if (this.selectedEDM !== null) {
      if (item === null) {
        return false;
      } else {
        return item.name === this.selectedEDM;
      }
    }
  }

  getLinkingPortfolio() {
    if (this.linking.portfolio === null) {
      return null;
    } else {
      return _.toArray(this.linking.portfolio.data);
    }
  }

  getLinkingAnalysis() {
    if (this.linking.analysis === null) {
      return null;
    } else {
      return _.toArray(this.linking.analysis[this.linking.rdm.selected.id].data);
    }
  }

  getSelectedRDM() {
    return _.filter(_.toArray(this.linking.rdm.data), dt => dt.selected);
  }

  selectLinkedRDM(item) {
    console.log('action Load Analysis');
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
      this.store.dispatch(new fromWs.PatchResultsAction({
        id: row.id,
        target: 'regionPeril',
        value: this.dropDownFP.value,
        scope: this.scopeForChanges === 'currentSelection' ? 'Single' : 'All'
      }));
    } else if (target === 'Ob') {
      this.singleColEdit = false;
      this.store.dispatch(new fromWs.PatchResultsAction({
        id: row.id,
        target: 'occurrenceBasis',
        value: this.dropDownFPOb.value,
        scope: this.scopeForChanges === 'currentSelection' ? 'Single' : 'All'
      }));
    } else if (target === 'TC') {
      if ($event !== null && row !== null) {
        this.store.dispatch(new fromWs.PatchResultsAction({id: row.id, target: 'targetCurrency', value: $event}));
      }
    } else if (target === 'UM') {
      this.store.dispatch(new fromWs.PatchResultsAction({id: row.id, target: 'unitMultiplier', value: $event}));
    } else if (target === 'peqt') {

    }
  }

  checkRow(event, rowData, target) {
    if (target === 'R') {
      this.store.dispatch(new fromWs.ToggleRiskLinkResultAction({action: 'selectOne', value: event, item: rowData}));
    } else if (target === 'S') {
      this.store.dispatch(new fromWs.ToggleRiskLinkSummaryAction({action: 'selectOne', value: event, item: rowData}));
    } else if (target === 'fpS') {
      this.store.dispatch(new fromWs.ToggleRiskLinkFPStandardAction({
        action: 'selectOne',
        value: event,
        item: rowData
      }));
    } else if (target === 'fpA') {
      this.store.dispatch(new fromWs.ToggleRiskLinkFPAnalysisAction({
        action: 'selectOne',
        value: event,
        item: rowData
      }));
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

  selectRows(row: any, index: number, target) {
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
      this._selectRowsProvider(row, index, this.lastSelectedIndexFPAnalysis, action, 'fpS');
      this.lastSelectedIndexFPAnalysis = index;
    }
  }

  private _selectSectionProvider(from, to, action: any, state) {
    this.store.dispatch(action({action: 'unselectAll'}));
    if (from === to) {
      this.store.dispatch(action({
        action: 'selectOne', value: true, item: this.dataList(state)[from]
      }));
    } else {
      for (let i = from; i <= to; i++) {
        this.store.dispatch(action({
          action: 'selectOne', value: true, item: this.dataList(state)[i]
        }));
      }
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
      this._selectSectionProvider(from, to, action, this.workingFSC.standard);
    } else if (target === 'fpA') {
      const action = (payload) => new fromWs.ToggleRiskLinkFPAnalysisAction(payload);
      this._selectSectionProvider(from, to, action, this.workingFSC.analysis.data);
    }
  }

  changeCollapse(value) {
    this.store.dispatch(new fromWs.PatchRiskLinkCollapseAction({key: value}));
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

  openFinancialP() {
    this.financialP = true;
    this.store.dispatch(new fromWs.LoadFinancialPerspectiveAction(this.financialStandardContent));
    this.lastSelectedIndexFPstandard = null;
    this.lastSelectedIndexFPAnalysis = null;
  }

  changeTargetFP(event) {
    this.store.dispatch(new fromWs.PatchTargetFPAction(event));
  }

  changeFinancialPTarget(target) {
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
    console.log(item, fp);
    this.store.dispatch(new fromWs.RemoveFinancialPerspectiveAction({item, fp}));
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

  getTreeApp() {
    const data = _.toArray(this.state.results.data);
    const regions = _.unionBy(data.map(dt => dt.regionPeril));
    this.tree = regions.map(dt => {
      return ({
        title: dt, expanded: true, selected: false, children: [],
        selectedItems: [{title: 'RL_EUWS_Mv11.2_S-1003-LTR-Scor27c72u', selected: false},
          {title: 'RL_EUWS_Mv11.2_S-65-LTR', selected: false},
          {title: 'RL_EUWS_Mv11.2_S-66-LTR-Clue', selected: false}]
      });
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
          selectedItems: [{title: 'RL_EUWS_Mv11.2_S-1003-LTR-Scor27c72u', selected: false},
            {title: 'RL_EUWS_Mv11.2_S-65-LTR', selected: false},
            {title: 'RL_EUWS_Mv11.2_S-66-LTR-Clue', selected: false}]
        }];
      }
    });
    return array;
  }

  getCodeFp(item) {
    return _.filter(this.financialStandardContent, dt => dt.code === item)[0].financialPerspective;
  }

  getSelectedPeqt(row) {
    return _.filter(_.filter(_.toArray(this.state.results.data), dt => dt.id === row.id)[0].peqt, ws => ws.selected === true).length;
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
    console.log(data.length === parent.children.length, selected);
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

  }

  detectChanges() {
    if (!this.cdRef['destroyed']) {
      this.cdRef.detectChanges();
    }
  }
}
