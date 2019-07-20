import {ChangeDetectorRef, Component, ElementRef, EventEmitter, OnInit, ViewChild} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute, Router} from '@angular/router';
import {Select, Store} from '@ngxs/store';
import {combineLatest, Observable} from 'rxjs';
import {RiskLinkState} from '../../store/states';
import {RiskLinkModel} from '../../model/risk_link.model';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import {
  AddToBasketAction,
  DeleteEdmRdmaction,
  PatchAddToBasketStateAction,
  SearchRiskLinkEDMAndRDMAction,
  ToggleRiskLinkAnalysisAction,
  ToggleRiskLinkEDMAndRDMSelectedAction,
  ToggleRiskLinkPortfolioAction
} from '../../store/actions/risk_link.actions';
import {
  LoadRiskLinkDataAction,
  PatchRiskLinkCollapseAction,
  PatchRiskLinkDisplayAction,
  PatchRiskLinkFinancialPerspectiveAction,
  SelectRiskLinkEDMAndRDMAction,
  ToggleRiskLinkEDMAndRDMAction, UpdateWsRouting
} from '../../store/actions';
import * as fromWs from '../../store/actions';
import {DataTables} from './data';
import {BaseContainer} from '../../../shared/base';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from '../../../core/store/actions/header.action';
import {Navigate} from "@ngxs/router-plugin";

@Component({
  selector: 'app-workspace-risk-link',
  templateUrl: './workspace-risk-link.component.html',
  styleUrls: ['./workspace-risk-link.component.scss'],
})
export class WorkspaceRiskLinkComponent extends BaseContainer implements OnInit, StateSubscriber {

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

  wsIdentifier;
  workspaceInfo: any;

  tree = [];

  lastSelectedIndex = null;

  filterModalVisibility = false;
  linkingModalVisibility = false;

  managePopUp = false;

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

  scrollableColsResult: any;

  colsFinancialStandard: any;
  financialStandardContent: any;
  workingFSC: any;
  colsFinancialAnalysis: any;

  selectedEDM = null;
  scrollableColslinking: any;

  contextSelectedItem: any;

  @Select(RiskLinkState)
  state$: Observable<RiskLinkModel>;
  state: RiskLinkModel = null;

  constructor(
    private _helper: HelperService,
    private route: ActivatedRoute,
    _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef
  ) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    combineLatest(
      this.select(RiskLinkState.getFinancialPerspective)
    ).pipe(this.unsubscribeOnDestroy).subscribe(
      ([fp]: any) => {
        this.workingFSC = fp;
      }
    );
    this.serviceSubscription = [
      this.state$.pipe(this.unsubscribeOnDestroy).subscribe(value => this.state = _.merge({}, value)),
      this.select(st => st.RiskLinkModel.analysis).pipe(this.unsubscribeOnDestroy).subscribe(dt => {
        this.tableLeftAnalysis = dt;
        this.detectChanges();
      }),
      this.select(st => st.RiskLinkModel.portfolios).pipe(this.unsubscribeOnDestroy).subscribe(dt => {
        this.tableLeftPortfolio = dt;
        this.detectChanges();
      }),
      this.select(st => st.RiskLinkModel.listEdmRdm).pipe(this.unsubscribeOnDestroy).subscribe(dt => {
        this.detectChanges();
      }),
      this.route.params.pipe(this.unsubscribeOnDestroy).subscribe(({wsId, year}) => {
        this.hyperLinksConfig = {
          wsId,
          uwYear: year
        };
        this.dispatch(new LoadRiskLinkDataAction());
        this.detectChanges();
      })
    ];

    this.scrollableColsAnalysis = DataTables.scrollableColsAnalysis;
    this.frozenColsAnalysis = DataTables.frozenColsAnalysis;
    this.scrollableColsPortfolio = DataTables.scrollableColsPortfolio;
    this.frozenColsPortfolio = DataTables.frozenColsPortfolio;
    this.scrollableColslinking = DataTables.scrollableColsLinking;
    this.colsFinancialStandard = DataTables.colsFinancialStandard;
    this.colsFinancialAnalysis = DataTables.colsFinancialAnalysis;
    this.financialStandardContent = DataTables.financialStandarContent;
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

  dataList(data, filter = null) {
    const array = _.toArray(data);
    if (filter === null) {
      return array;
    } else {
      return array.filter(dt => dt.type === filter);
    }
  }

  toggleItemsListRDM(RDM) {
    this.dispatch(new ToggleRiskLinkEDMAndRDMSelectedAction(RDM));
  }

  /** Select EDM & RDM DropDown Method's */
  toggleItems(RDM, event, source) {
    this.dispatch(new ToggleRiskLinkEDMAndRDMAction({RDM, action: 'selectOne', source}));
    if (event !== null) {
      event.stopPropagation();
    }
  }

  selectAll() {
    this.dispatch(new ToggleRiskLinkEDMAndRDMAction({action: 'selectAll', source: 'solo'}));
  }

  unselectAll() {
    this.dispatch(new ToggleRiskLinkEDMAndRDMAction({action: 'unselectAll', source: 'solo'}));
  }

  refreshAll() {

  }

  closeDropdown() {
    this.displayDropdownRDMEDM = false;
  }

  /** */
  fillLists() {
    this.dispatch(new SelectRiskLinkEDMAndRDMAction());
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
    this.dispatch(new PatchRiskLinkDisplayAction({key: 'displayImport', value: true}));
    this.dispatch(new AddToBasketAction());
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
    this.dispatch(new DeleteEdmRdmaction({id: item.id, target: target}));
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
      this.dispatch(new SearchRiskLinkEDMAndRDMAction({keyword: event.target.value, size: '20'}));
    } else {
      this.dispatch(new SearchRiskLinkEDMAndRDMAction({keyword: '', size: '20'}));
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
      this.dispatch(new SearchRiskLinkEDMAndRDMAction({
        keyword: this.state.listEdmRdm.searchValue,
        size: sizePage,
      }));
    }

  }

  selectOne(row) {
    if (this.state.selectedEDMOrRDM === 'rdm') {
      this.dispatch(new ToggleRiskLinkAnalysisAction({action: 'selectOne', value: true, item: row}));
    } else {
      this.dispatch(new ToggleRiskLinkPortfolioAction({action: 'selectOne', value: true, item: row}));
    }
  }

  selectWithUnselect(row) {
    if (this.state.selectedEDMOrRDM === 'rdm') {
      this.dispatch(new ToggleRiskLinkAnalysisAction({action: 'unselectAll'}));
      this.dispatch(new ToggleRiskLinkAnalysisAction({action: 'selectOne', value: true, item: row}));
    } else {
      this.dispatch(new ToggleRiskLinkPortfolioAction({action: 'unselectAll'}));
      this.dispatch(new ToggleRiskLinkPortfolioAction({action: 'selectOne', value: true, item: row}));
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
      this.dispatch(new PatchAddToBasketStateAction());
    }
  }

  private selectSection(from, to, target) {
    if (target === 'A&P') {
      if (this.state.selectedEDMOrRDM === 'rdm') {
        this.dispatch(new ToggleRiskLinkAnalysisAction({action: 'unselectAll'}));
      } else {
        this.dispatch(new ToggleRiskLinkPortfolioAction({action: 'unselectAll'}));
      }
      if (from === to) {
        if (this.state.selectedEDMOrRDM === 'rdm') {
          this.dispatch(new ToggleRiskLinkAnalysisAction({
            action: 'selectOne',
            value: true,
            item: this.getTableData()[from]
          }));
        } else {
          this.dispatch(new ToggleRiskLinkPortfolioAction({
            action: 'selectOne',
            value: true,
            item: this.getTableData()[from]
          }));
        }
      } else {
        for (let i = from; i <= to; i++) {
          if (this.state.selectedEDMOrRDM === 'rdm') {
            this.dispatch(new ToggleRiskLinkAnalysisAction({
              action: 'selectOne',
              value: true,
              item: this.getTableData()[i]
            }));
          } else {
            this.dispatch(new ToggleRiskLinkPortfolioAction({
              action: 'selectOne',
              value: true,
              item: this.getTableData()[i]
            }));
          }
        }
      }
    }
  }

  checkRow(event, rowData, target) {
    if (target === 'A&P') {
      if (this.state.selectedEDMOrRDM === 'edm') {
        this.dispatch(new ToggleRiskLinkPortfolioAction({action: 'selectOne', value: event, item: rowData}));
      } else {
        this.dispatch(new ToggleRiskLinkAnalysisAction({action: 'selectOne', value: event, item: rowData}));
      }
    }
  }

  changeCollapse(value) {
    this.dispatch(new PatchRiskLinkCollapseAction({key: value}));
  }

  changeFinancialValidator(value, item) {
    this.dispatch(new PatchRiskLinkFinancialPerspectiveAction({key: value, value: item}));
  }

  handleCancel() {
    this.filterModalVisibility = false;
    this.linkingModalVisibility = false;
  }

  navigateFromHyperLink({route}){
    const {wsId, uwYear}= this.workspaceInfo;
    this.dispatch(
      [new UpdateWsRouting(this.wsIdentifier, route),
        new Navigate(route ? [`workspace/${wsId}/${uwYear}/${route}`] : [`workspace/${wsId}/${uwYear}/projects`])]
    );
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  protected detectChanges() {
    super.detectChanges();
  }

}
