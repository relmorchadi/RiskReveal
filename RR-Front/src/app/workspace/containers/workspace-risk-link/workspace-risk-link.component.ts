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
import {Observable} from 'rxjs';
import {RiskLinkState} from '../../store/states';
import {RiskLinkModel} from '../../model/risk_link.model';
import {
  AddToBasketAction, DeleteFromBasketAction, LoadPortfolioForLinkingAction,
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
  ToggleRiskLinkEDMAndRDMAction
} from '../../store/actions';
import { DataTables } from './data';

@Component({
  selector: 'app-workspace-risk-link',
  templateUrl: './workspace-risk-link.component.html',
  styleUrls: ['./workspace-risk-link.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class WorkspaceRiskLinkComponent implements OnInit, OnDestroy {

  lastSelectedIndex = null;
  filterModalVisibility = false;
  linkingModalVisibility = false;
  radioValue = 'all';

  @ViewChild('searchInput')
  searchInput: ElementRef;

  displayDropdownRDMEDM = false;
  displayListRDMEDM = false;

  serviceSubscription: any;

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

  selectedEDM: any;
  scrollableColslinking: any;

  @Select(RiskLinkState)
  state$: Observable<RiskLinkModel>;
  state: RiskLinkModel = null;

  constructor(private _helper: HelperService, private route: ActivatedRoute, private store: Store, private cdRef: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.store.dispatch(new LoadRiskLinkDataAction());
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
  }

  ngOnDestroy() {
    if (this.serviceSubscription)
      this.serviceSubscription.forEach(sub => sub.unsubscribe());
  }

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

  toggleItems(RDM) {
    this.store.dispatch(new ToggleRiskLinkEDMAndRDMAction({RDM, action: 'selectOne'}));
  }

  toggleItemsLink(RDM) {
  }

  toggleItemsListRDM(RDM) {
    this.store.dispatch(new ToggleRiskLinkEDMAndRDMSelectedAction(RDM));
  }

  selectAll() {
    this.store.dispatch(new ToggleRiskLinkEDMAndRDMAction({action: 'selectAll'}));
  }

  unselectAll() {
    this.store.dispatch(new ToggleRiskLinkEDMAndRDMAction({action: 'unselectAll'}));
  }

  refreshAll() {

  }

  closeDropdown() {
    this.displayDropdownRDMEDM = false;
  }

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
    this.store.dispatch(new DeleteFromBasketAction({id: id, scope: target}));
  }

  toggleForLinkingEDM(items) {
    this.store.dispatch(new LoadPortfolioForLinkingAction(items));
  }

  toggleForLinkingRDM(items) {
    items.selected = !items.selected;
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
/*    const {id} = _.filter(this.state.linking.edm, (dt) => dt.selected === true)[0];
    const dataTable = _.get(this.state.linking, `analysis[${id}].data`, null);
    return _.toArray(dataTable);*/
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

  getNumberElement(item, source) {
    if (source === 'portfolio') {
      if (this.state.portfolios ===  null ) {
        return 0;
      } else {
        return this.state.portfolios[item.id].totalNumberElement;
      }
    } else if (source === 'analysis') {
      if (this.state.analysis ===  null) {
        return 0;
      } else {
        return this.state.analysis[item.id].numberOfElement;
      }
    }
  }

  getNumberOfSelected(item, source) {
    if (source === 'portfolio') {
      if (this.state.portfolios ===  null ) {
        return 0;
      } else {
        return _.filter(_.toArray(this.state.portfolios[item.id].data), dt => dt.selected).length;
      }
    } else if (source === 'analysis') {
      if (this.state.analysis ===  null) {
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

  selectRows(row: any, index: number) {
    if ((window as any).event.ctrlKey) {
      if (this.state.selectedEDMOrRDM === 'rdm') {
        this.store.dispatch(new ToggleRiskLinkAnalysisAction({action: 'selectOne', value: true, item: row}));
      } else {
        this.store.dispatch(new ToggleRiskLinkPortfolioAction({action: 'selectOne', value: true, item: row}));
      }
      this.lastSelectedIndex = index;
    } else if ((window as any).event.shiftKey) {
      event.preventDefault();
      if (this.lastSelectedIndex || this.lastSelectedIndex === 0) {
        this.selectSection(Math.min(index, this.lastSelectedIndex), Math.max(index, this.lastSelectedIndex));
      } else {
        this.lastSelectedIndex = index;
        if (this.state.selectedEDMOrRDM === 'rdm') {
          this.store.dispatch(new ToggleRiskLinkAnalysisAction({action: 'selectOne', value: true, item: row}));
        } else {
          this.store.dispatch(new ToggleRiskLinkPortfolioAction({action: 'selectOne', value: true, item: row}));
        }
      }
    } else {
      if (this.state.selectedEDMOrRDM === 'rdm') {
        this.store.dispatch(new ToggleRiskLinkAnalysisAction({action: 'unselectAll'}));
        this.store.dispatch(new ToggleRiskLinkAnalysisAction({action: 'selectOne', value: true, item: row}));
      } else {
        this.store.dispatch(new ToggleRiskLinkPortfolioAction({action: 'unselectAll'}));
        this.store.dispatch(new ToggleRiskLinkPortfolioAction({action: 'selectOne', value: true, item: row}));
      }
      this.lastSelectedIndex = index;
    }
    this.store.dispatch(new PatchAddToBasketStateAction());
  }

  private selectSection(from, to) {
    if (this.state.selectedEDMOrRDM === 'rdm') {
      this.store.dispatch(new ToggleRiskLinkAnalysisAction({action: 'unselectAll'}));
    } else {
      this.store.dispatch(new ToggleRiskLinkPortfolioAction({action: 'unselectAll'}));
    }
    if (from === to) {
      if (this.state.selectedEDMOrRDM === 'rdm') {
        this.store.dispatch(new ToggleRiskLinkAnalysisAction({action: 'selectOne', value: true, item: this.getTableData()[from]}));
      } else {
        this.store.dispatch(new ToggleRiskLinkPortfolioAction({action: 'selectOne', value: true, item: this.getTableData()[from]}));
      }
    } else {
      for (let i = from; i <= to; i++) {
        if (this.state.selectedEDMOrRDM === 'rdm') {
          this.store.dispatch(new ToggleRiskLinkAnalysisAction({action: 'selectOne', value: true, item: this.getTableData()[i]}));
        } else {
          this.store.dispatch(new ToggleRiskLinkPortfolioAction({action: 'selectOne', value: true, item: this.getTableData()[i]}));
        }
      }
    }
  }

  checkRow(event, rowData) {
    if (this.state.selectedEDMOrRDM === 'edm') {
      this.store.dispatch(new ToggleRiskLinkPortfolioAction({action: 'selectOne', value: event, item: rowData}));
    } else {
      this.store.dispatch(new ToggleRiskLinkAnalysisAction({action: 'selectOne', value: event, item: rowData}));
    }
    console.log(event);
  }

  changeCollapse(value) {
    this.store.dispatch(new PatchRiskLinkCollapseAction({key: value}));
  }

  changeFinancialValidator(value, item) {
    this.store.dispatch(new PatchRiskLinkFinancialPerspectiveAction({key: value, value: item}));
  }

  handleCancel() {
    this.filterModalVisibility = false;
    this.linkingModalVisibility = false;
  }

  detectChanges() {
    if (!this.cdRef['destroyed']) {
      this.cdRef.detectChanges();
    }
  }
}
