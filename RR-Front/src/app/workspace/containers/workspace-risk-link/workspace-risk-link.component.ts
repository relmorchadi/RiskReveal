import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, OnDestroy} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute} from '@angular/router';
import {Select, Store} from '@ngxs/store';
import {Observable} from 'rxjs';
import {RiskLinkState} from '../../store/states';
import {RiskLinkModel} from '../../model/risk_link.model';
import {
  SearchRiskLinkEDMAndRDMAction, ToggleRiskLinkAnalysisAction,
  ToggleRiskLinkEDMAndRDMSelectedAction, ToggleRiskLinkPortfolioAction
} from '../../store/actions/risk_link.actions';
import {
  LoadRiskLinkDataAction,
  PatchRiskLinkCollapseAction,
  PatchRiskLinkDisplayAction,
  PatchRiskLinkFinancialPerspectiveAction,
  SelectRiskLinkEDMAndRDMAction,
  ToggleRiskLinkEDMAndRDMAction
} from '../../store/actions';

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

  inputSwitch = true;

  displayDropdownRDMEDM = false;
  displayListRDMEDM = false;
  closePrevent = false;

  serviceSubscription: any;

  listEdmRdm: any = [];

  tableLeftAnalysis: any;

  tableLeftProtfolio: any;

  scrollableColsAnalysis = [
    {field: 'description', header: 'Description', width: '150px', type: 'text'},
    {field: 'engineVersion', header: 'Engine Version', width: '110px', type: 'text'},
    {field: 'groupType', header: 'Group Type', width: '110px', type: 'text'},
    {field: 'cedant', header: 'Cedant', width: '110px', type: 'text'},
    {field: 'lobName', header: 'LOB', width: '110px', type: 'text'},
    {field: 'engineType', header: 'Engine Type', width: '110px', type: 'text'},
    {field: 'runDate', header: 'Run Date', width: '110px', type: 'text'},
    {field: 'typeName', header: 'Type', width: '110px', type: 'text'},
    {field: 'peril', header: 'Peril', width: '110px', type: 'text'},
    {field: 'subPeril', header: 'Sub Peril', width: '110px', type: 'text'},
    {field: 'lossAmplification', header: 'Loss Amplification', width: '110px', type: 'text'},
    {field: 'region', header: 'Region', width: '110px', type: 'text'},
    {field: 'modeName', header: 'Mode', width: '110px', type: 'text'},
    {field: 'user1', header: 'User 1', width: '110px', type: 'text'},
    {field: 'user2', header: 'User 2', width: '110px', type: 'text'},
    {field: 'user3', header: 'User 3', width: '110px', type: 'text'},
    {field: 'user4', header: 'User 4', width: '110px', type: 'text'},
    {field: 'sourceCurrency', header: 'Source Currency', width: '110px', type: 'text'},
    {field: 'regionName', header: 'Region Name', width: '110px', type: 'text'},
    {field: 'statusDescription', header: 'Status Description', width: '110px', type: 'text'},
    {field: 'grouping', header: 'Grouping', width: '110px', type: 'text'},
  ];

  frozenColsAnalysis = [
    {field: 'selected', header: 'selected', width: '20px', type: 'select'},
    {field: 'analysisId', header: 'id', width: '30px', type: 'text'},
    {field: 'analysisName', header: 'name', width: '190px', type: 'text'}
  ];

  scrollableColsPortfolio = [
    {field: 'dataSourceName', header: 'Name', width: '150px', type: 'text'},
    {field: 'creationDate', header: 'Creation Date', width: '180px', type: 'date'},
    {field: 'descriptionType', header: 'Description Type', width: '180px', type: 'text'},
    {field: 'type', header: 'Type', width: '180px', type: 'text'},
    {field: 'agCedent', header: 'Cedant', width: '120px', type: 'text'},
    {field: 'agCurrency', header: 'Currency', width: '120px', type: 'text'},
    {field: 'agSource', header: 'Source', width: '120px', type: 'text'},
    {field: 'peril', header: 'Peril', width: '120px', type: 'text'},
  ];

  frozenColsPortfolio = [
    {field: 'selected', header: 'selected', width: '20px', type: 'select'},
    {field: 'dataSourceId', header: 'id', width: '30px', type: 'text'},
    {field: 'number', header: 'Number', width: '190px', type: 'text'}
  ];

  summaryInfo: any = [
    {
      scanned: true,
      status: 100,
      id: '286',
      number: 'EUGU_CHIPZCP_CDSC_PR',
      name: 'AUWS_PR_20170930',
      exposedLocation: true,
      exposedCurrency: 'USD',
      targetCurrency: 'USD',
      unitMultiplier: 1.0,
      proportion: '100%',
      EDM: 'AA2012_SyntheticCurve_E'
    },
    {
      scanned: true,
      status: 60,
      id: '325',
      number: 'EUGU_CHIPZCP_CDSC_PR',
      name: 'AUWS_PR_20170930',
      exposedLocation: true,
      exposedCurrency: 'USD',
      targetCurrency: 'USD',
      unitMultiplier: 1.0,
      proportion: '100%',
      EDM: 'AA2012_SyntheticCurve_E'
    },
    {
      scanned: true,
      status: 0,
      id: '284',
      number: 'EUGU_CHIPZCP_CDSC_PR',
      name: 'AUWS_PR_20170930',
      exposedLocation: true,
      exposedCurrency: 'USD',
      targetCurrency: 'USD',
      unitMultiplier: 1.0,
      proportion: '100%',
      EDM: 'AA2012_SyntheticCurve_E'
    },
  ];

  resultsInfo: any = [
    {
      scanned: false,
      status: 100,
      id: '286',
      name: 'Europe All Lines, EP Wind Only',
      description: 'Europe All Lines, EP Wind Only',
      regionPeril: 'DEFL',
      sourceCurrency: 'USD',
      targetCurrency: 'USD',
      ELT: 'GR',
      occurrenceBasis: 'PerEvent',
      unitMultiplier: 1.0
    },
    {
      scanned: false,
      status: 50,
      id: '285',
      name: 'Europe All Lines, EP Wind Only',
      description: 'Europe All Lines, EP Wind Only',
      regionPeril: 'DEFL',
      sourceCurrency: 'USD',
      targetCurrency: 'USD',
      ELT: 'GR',
      occurrenceBasis: 'PerEvent',
      unitMultiplier: 1.0
    },
    {
      scanned: false,
      status: 20,
      id: '284',
      name: 'Europe All Lines, EP Wind Only',
      description: 'Europe All Lines, EP Wind Only',
      regionPeril: 'DEFL',
      sourceCurrency: 'USD',
      targetCurrency: 'USD',
      ELT: 'GR',
      occurrenceBasis: 'PerEvent',
      unitMultiplier: 1.0
    },
  ];

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
        this.tableLeftProtfolio = dt;
        this.detectChanges();
      }),
      this.store.select(st => st.RiskLinkModel.listEdmRdm).subscribe(dt => {
        this.detectChanges();
      })
    ];
  }

  ngOnDestroy() {
    if (this.serviceSubscription)
      this.serviceSubscription.forEach(sub => sub.unsubscribe());
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
  }

  /*  selectRow(row: any, index: number) {
      if ((window as any).event.ctrlKey) {
        row.selected = !row.selected;
      }
      if ((window as any).event.shiftKey) {
        event.preventDefault();
        if (this.lastSelectedIndex) {
          this.selectSection(Math.min(index, this.lastSelectedIndex), Math.max(index, this.lastSelectedIndex));
          this.lastSelectedIndex = null;
        } else {
          this.lastSelectedIndex = index;
        }
      }
      // this.currentSelectedItem = row;
      const selectedRowsAnalysis = this.tableLeftAnalysis.filter(ws => ws.selected === true);
      const selectedRowsPortfolio = this.tableLeftProtfolio.filter(ws => ws.selected === true);

    }

    selectSection(from, to) {
      let tableUpdated: any;
      if (this.state.listEdmRdm.selectedEDMOrRDM === 'rdm') {
        tableUpdated = this.tableLeftAnalysis;
      } else {
        tableUpdated = this.tableLeftProtfolio;
      }
      if (from === to) {
        tableUpdated[from].selected = !tableUpdated[from].selected;
      } else {
        for (let i = from; i <= to; i++) {
          tableUpdated[i].selected = !tableUpdated[i].selected;
        }
      }
    }*/

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
    const {id} = _.filter(this.state.listEdmRdm.selectedListEDMAndRDM, (dt) => dt.selected === true)[0];
    if (this.state.selectedEDMOrRDM === 'rdm') {
      this.tableLeftAnalysis = _.get(this.tableLeftAnalysis, `${id}.data`, this.tableLeftAnalysis);
      return _.toArray(this.tableLeftAnalysis);
    } else {
      this.tableLeftProtfolio = _.get(this.tableLeftProtfolio, `${id}.data`, this.tableLeftProtfolio);
      return _.toArray(this.tableLeftProtfolio);
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
      this.lastSelectedIndex = index;
    } else if ((window as any).event.shiftKey) {
      event.preventDefault();
      if (this.lastSelectedIndex || this.lastSelectedIndex === 0) {
        this.selectSection(Math.min(index, this.lastSelectedIndex), Math.max(index, this.lastSelectedIndex));
        // this.lastSelectedIndex = null;
      } else {
        this.lastSelectedIndex = index;
        // row.selected = true;
      }
    } else {
      this.store.dispatch(new ToggleRiskLinkEDMAndRDMAction({action: 'unselectAll'}));
      this.lastSelectedIndex = index;
      row.selected = true;
    }
    // this.selectedRows = this.listOfData.filter(ws => ws.selected === true);
    // this.isIndeterminate();
  }

  private selectSection(from, to) {
    // this.store.dispatch(new ToggleRiskLinkEDMAndRDMAction({action: 'unselectAll'}));
    if (from === to) {
      // this.store.dispatch(new ToggleRiskLinkEDMAndRDMAction({RDM, action: 'selectOne'}));
      // this.listOfData[from].selected = true;
    } else {
      for (let i = from; i <= to; i++) {
        // this.store.dispatch(new ToggleRiskLinkEDMAndRDMAction({RDM, action: 'selectOne'}));
        // this.listOfData[i].selected = true;
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
