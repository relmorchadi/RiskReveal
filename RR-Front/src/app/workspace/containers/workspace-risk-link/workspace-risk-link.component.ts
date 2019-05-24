import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, OnDestroy} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute} from '@angular/router';
import {Select, Store} from '@ngxs/store';
import {Observable} from 'rxjs';
import {RiskLinkState} from '../../store/states';
import {RiskLinkModel} from '../../model/risk_link.model';
import {
  SearchRiskLinkEDMAndRDMAction,
  ToggleRiskLinkEDMAndRDMSelectedAction
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

  inputSwitch = true;

  closePrevent = false;

  listEDM: any = [];
  listRDM: any = [];
  serviceSubscription: any;

  listEdmRdm: any = [
    {id: 1, name: 'AA2012_syntheticCurve_E', type: 'EDM', selected: false, scanned: false, Reference: '0/13'},
    {id: 2, name: 'AA2012_syntheticCurve_R', type: 'RDM', selected: false, scanned: false, Reference: '0/5'},
    {id: 3, name: 'AA2016_syntheticCurve_R', type: 'RDM', selected: false, scanned: false, Reference: '0/13'},
    {id: 4, name: 'AA2017_SEA_syntheticCurve_R', type: 'RDM', selected: false, scanned: false, Reference: '0/13'},
    {id: 5, name: 'ALMF_test_E', type: 'EDM', selected: false, scanned: false, Reference: '0/25'},
    {id: 6, name: 'ALMF_test_R', type: 'RDM', selected: false, scanned: false, Reference: '0/12'},
    {id: 7, name: 'Anxin_NMQSS_ZJ_Training_R', type: 'RDM', selected: false, scanned: false, Reference: '0/3'},
    {id: 8, name: 'Anxin_NMQSS_ZJ_Training_E', type: 'EDM', selected: false, scanned: false, Reference: '0/4'},
    {id: 9, name: 'Aon_IF_Flood_Example_E', type: 'EDM', selected: false, scanned: false, Reference: '0/2'},
    {
      id: 10,
      name: 'Brkr_PICC_CATXL_2018RNL_ToMkt_EDM',
      type: 'EDM',
      selected: false,
      scanned: false,
      Reference: '0/15'
    },
    {
      id: 11,
      name: 'Brkr_PICC_CATXL_2018RNL_ToMkt_RDM',
      type: 'RDM',
      selected: false,
      scanned: false,
      Reference: '0/13'
    },
    {id: 12, name: 'CC_ALMF_test_E', type: 'EDM', selected: false, scanned: false, Reference: '0/13'},
    {id: 13, name: 'CG1801_DEU_Allianz_DEFL_R', type: 'RDM', selected: false, scanned: false, Reference: '0/41'},
    {id: 14, name: 'CG1801_DEU_Allianz_DEFL_E', type: 'EDM', selected: false, scanned: false, Reference: '0/41'},
    {id: 15, name: 'Anxin_2018_NMQSS_ZJ_Training_R', type: 'RDM', selected: false, scanned: false, Reference: '0/15'},
    {id: 16, name: 'Anxin_2018_NMQSS_ZJ_Training_E', type: 'EDM', selected: false, scanned: false, Reference: '0/17'},
    {id: 17, name: 'CF1803_PORT_R', type: 'RDM', selected: false, scanned: false, Reference: '0/3'},
    {id: 18, name: 'CF1803_PORT_E', type: 'EDM', selected: false, scanned: false, Reference: '0/25'}
  ];

  tableLeftAnalysis: any = [];

  tableLeftProtfolio: any = [];

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
    {field: 'region', header: 'User 4', width: '110px', type: 'text'},
    {field: 'modeName', header: 'Mode', width: '110px', type: 'text'},
    {field: 'user1', header: 'User 1', width: '110px', type: 'text'},
    {field: 'user2', header: 'User 2', width: '110px', type: 'text'},
    {field: 'user3', header: 'User 3', width: '110px', type: 'text'},
    {field: 'user4', header: 'User 4', width: '110px', type: 'text'},
    {field: 'analysisCurrency', header: 'Analysis Currency', width: '110px', type: 'text'},
    {field: 'regionName', header: 'User 4', width: '110px', type: 'text'},
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
      analysisCurrency: 'USD',
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
      analysisCurrency: 'USD',
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
      analysisCurrency: 'USD',
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
      this.store.select(st => st.RiskLinkModel.selectedAnalysisAndPortoflio.selectedAnalysis.data).subscribe(dt => {
        this.tableLeftAnalysis = _.toArray(dt);
        this.detectChanges();
      }),
      this.store.select(st => st.RiskLinkModel.selectedAnalysisAndPortoflio.selectedPortfolio.data).subscribe(dt => {
        this.tableLeftProtfolio = _.toArray(dt);
        this.detectChanges();
      }),
      this.store.select(st => st.RiskLinkModel.listEdmRdm).subscribe(dt => {
        this.detectChanges();
      })
    ];
  }

  ngOnDestroy() {
    if(this.serviceSubscription)
      this.serviceSubscription.forEach(sub => sub.unsubscribe());
  }

  dataList(data, filter = null) {
    const array = _.toArray(data);
    if (filter === null) {
      return array;
    } else {
      return array.filter(dt => dt.type === filter );
    }
  }

  toggleItems(RDM) {
    this.store.dispatch(new ToggleRiskLinkEDMAndRDMAction({RDM, action: 'selectOne'}));
    this.closePrevent = false;
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

  unselectEDMRDM() {
  }

  refreshAll() {
    this.listEdmRdm.forEach((e) => {
      e.scanned = false;
      e.selected = false;
    });
  }

  openCloseDropdown() {
    this.store.dispatch(new PatchRiskLinkDisplayAction({
      key: 'displayDropdownRDMEDM',
      value: !this.state.display.displayDropdownRDMEDM
    }));
  }

  closeDropdown() {
    if (this.state.display.displayDropdownRDMEDM && this.closePrevent)
      this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayDropdownRDMEDM', value: false}));
    this.closePrevent = true;
  }

  fillLists() {
    this.store.dispatch(new SelectRiskLinkEDMAndRDMAction());
  }

  selectedItem() {
    this.fillLists();
    this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayDropdownRDMEDM', value: false}));
    const array = _.toArray(this.state.listEdmRdm.selectedListEDMAndRDM);
    if (array.length > 0) {
      this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayListRDMEDM', value: true}));
    } else {
      this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayListRDMEDM', value: false}));
    }
  }

  scanItem(item) {
    item.scanned = false;
    setTimeout(() =>
      item.scanned = true, 1000
    );
  }

  selectStep(value) {
  }

  displayImported() {
    if (this.state.currentStep === 1) {
      this.store.dispatch(new PatchRiskLinkDisplayAction({key: 'displayImport', value: true}));
    }
  }

  selectRow(row: any, index: number) {
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
  }

  getScrollableCols() {
    if (this.state.listEdmRdm.selectedEDMOrRDM === 'rdm') {
      return this.scrollableColsAnalysis;
    } else {
      return this.scrollableColsPortfolio;
    }
  }

  getFrozenCols() {
    if (this.state.listEdmRdm.selectedEDMOrRDM === 'rdm') {
      return this.frozenColsAnalysis;
    } else {
      return this.frozenColsPortfolio;
    }
  }

  getTableData() {
    if (this.state.listEdmRdm.selectedEDMOrRDM === 'rdm') {
      return this.tableLeftAnalysis;
    } else {
      return this.tableLeftProtfolio;
    }
  }

  onInputSearch(event) {
    console.log('the searched Item is:', event.target.value);
    if (event.target.value.length > 2) {
      this.store.dispatch(new SearchRiskLinkEDMAndRDMAction({keyword: event.target.value, size: '20'}));
    } else {
      this.store.dispatch(new SearchRiskLinkEDMAndRDMAction({keyword: '', size: '20'}));
    }
  }

  loadItemsLazy(event) {
    let sizePage = '';
    if (event.first + event.rows > this.state.listEdmRdm.totalNumberElement) {
      sizePage  = this.state.listEdmRdm.totalNumberElement.toString();
    } else {
      sizePage = event.first === 0 ? '20' : (event.first + event.rows).toString();
    }
    if (this.state.listEdmRdm.dataLength < event.first + event.rows) {
      console.log('you called for :' + sizePage);
      this.store.dispatch(new SearchRiskLinkEDMAndRDMAction({keyword: this.state.listEdmRdm.searchValue, size: sizePage}));
    }

  }

  changeCollapse(value) {
    this.store.dispatch(new PatchRiskLinkCollapseAction({key: value}));
  }

  changeFinancialValidator(value, item) {
    this.store.dispatch(new PatchRiskLinkFinancialPerspectiveAction({key: value, value: item}));
  }

  detectChanges() {
    if (!this.cdRef['destroyed']) {
      this.cdRef.detectChanges();
    }
  }
}
