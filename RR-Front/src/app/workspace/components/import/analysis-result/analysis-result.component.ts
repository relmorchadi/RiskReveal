import {Component, Input, OnInit} from '@angular/core';
import {Store} from "@ngxs/store";
import * as fromRiskLink from "../../../store/actions/risk_link.actions";

@Component({
  selector: 'analysis-result',
  templateUrl: './analysis-result.component.html',
  styleUrls: ['./analysis-result.component.scss']
})
export class AnalysisResultComponent implements OnInit {

  @Input('data')
  analysis;

  isCollapsed;
  scrollableColsResults = [
    {field: '', header: '', width: '80px', type: '', filtered: false, visible: false, highlight: false, edit: false},
    {
      field: 'rpCode',
      header: 'Region Peril',
      width: '80px',
      type: 'Rp',
      sorting: '',
      filtered: true,
      highlight: true,
      visible: true,
      edit: false
    },
    {
      field: 'analysisCurrency',
      header: 'Source Currency',
      width: '90px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      edit: false
    },
    {
      field: 'targetCurrency',
      header: 'Target Currency',
      width: '80px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: true,
      visible: true,
      edit: true
    },
    {
      field: 'financialPerspective',
      header: 'ELT FIN PERSP',
      width: '80px',
      type: 'multiple',
      sorting: '',
      filtered: true,
      highlight: true,
      visible: true,
      edit: false
    },
    // {
    //   field: 'occurrenceBasis',
    //   header: 'Occurrence Basis',
    //   width: '100px',
    //   type: 'Ob',
    //   sorting: '',
    //   filtered: true,
    //   highlight: true,
    //   visible: true,
    //   edit: false
    // },
    // {
    //   field: 'targetRap',
    //   header: 'Target RAP',
    //   width: '180px',
    //   type: 'text',
    //   sorting: '',
    //   filtered: true,
    //   highlight: false,
    //   visible: true,
    //   edit: true
    // },
    {
      field: 'peqt',
      header: 'PEQT',
      width: '80px',
      type: 'Peqt',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      edit: false
    },
    {
      field: 'unitMultiplier',
      header: 'Unit Multiplier',
      width: '80px',
      type: 'number',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      edit: false
    },
    {
      field: 'proportion',
      header: 'Proportion',
      width: '70px',
      type: 'percentage',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      edit: false
    },
    {
      field: 'rdmName',
      header: 'RDM',
      width: '210px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      edit: false
    },
    {
      field: 'importId',
      header: 'Import ID',
      width: '80px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      edit: false
    },
    {
      field: 'dateImport',
      header: 'Date Import',
      width: '70px',
      type: 'date',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      edit: false
    },
    {
      field: 'user',
      header: 'User',
      width: '70px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      edit: false
    },
    {
      field: 'publishAcc',
      header: 'Publish For Accumulation',
      width: '30px',
      type: 'check',
      icon: 'icon-focus-add',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      edit: false
    },
    {
      field: 'publishPri',
      header: 'Publish For Pricing',
      width: '30px',
      type: 'check',
      icon: 'icon-note',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      edit: false
    },
    /*  {field: 'action', header: '', width: '25px', type: 'icon', sorting: ', filtered: false, highlight: false},*/
  ];

  frozenColsResults = [
    {
      field: 'selected',
      header: '',
      width: '25px',
      type: 'selection',
      sorting: '',
      filtered: false,
      highlight: false,
      visible: true,
      edit: false
    },
    {
      field: 'scan',
      header: '',
      width: '25px',
      type: 'scan',
      sorting: '',
      filtered: false,
      highlight: false,
      visible: true,
      edit: false
    },
    {
      field: 'status',
      header: 'Status',
      width: '40px',
      type: 'progress',
      sorting: '',
      filtered: false,
      highlight: false,
      visible: true,
      edit: false
    },
    {
      field: 'analysisId',
      header: 'ID',
      width: '40px',
      type: 'text',
      sorting: '',
      filtered: false,
      highlight: false,
      visible: true,
      edit: false
    },
    {
      field: 'analysisName',
      header: 'Name',
      width: '170px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      edit: false
    },
    {
      field: 'analysisDescription',
      header: 'Description',
      width: '200px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      edit: false
    },
  ];

  refs = {
    currencies: [
      {label: "Main Liablity Currency (MLC)", value: "USD"},
      {label: "Underlying Analysis Currency (UAC)", value: "UAC"},
      {label: "AED", value: "AED"},
      {label: "AFA", value: "AFA"},
      {label: "AFN", value: "AFN"},
      {label: "ALK", value: "ALK"},
      {label: "ALL", value: "ALL"},
      {label: "BBT", value: "BBT"},
      {label: "BBL", value: "BBL"},
      {label: "BMD", value: "BMD"},
      {label: "MGA", value: "MGA"},
      {label: "USD", value: "USD"},
      {label: "XXC", value: "XXC"}
    ],
    targetRaps: [
      {value: 'RL_ACEQ_Mv9.0_Ev10.0_S-56-LTR (Default)'},
      {value: 'RL_ACEQ_Mv9.0_Ev11.0_S-56-LTR (Default)'},
      {value: 'RL_ACEQ_Mv9.0_Ev13.0_S-56-LTR (Default)'},
      {value: 'RL_ACEQ_Mv9.0_Ev13.1_S-56-LTR (Default)'},
      {value: 'RL_ACEQ_Mv9.0_Ev15.0_S-56-LTR (Default)'},
      {value: 'RL_ACEQ_Mv9.0_Ev16.0_S-56-LTR (Default)'},
      {value: 'RL_ACEQ_Mv9.0_Ev9.0_S-56-LTR (Default)'},
      {value: 'RL_AHEQ_Mv9.0_Ev10.0_S-60-LTR (Default)'},
      {value: 'RL_AHEQ_Mv9.0_Ev11.0_S-60-LTR (Default)'},
      {value: 'RL_EUEQ_Mv11.0_Ev11.0_S-61-LTR (Default)'},
      {value: 'RL_EUWS_Mv11.2_Ev11.0_S-65-LTR'},
    ]
  };

  targetRapDataTable = [
    {
      field: 'selected',
      header: '',
      width: '20px',
      type: 'select',
      sorting: false,
      filtered: false,
      highlight: false,
      visible: true
    },
    {
      field: 'value',
      header: 'target Rap',
      width: '160px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true
    },
  ];

  showUpdatePEQTModal = false;

  lastUpdatedAnalyisPEQTIndex=null;

  constructor(private store: Store) {
  }

  ngOnInit() {
  }

  updateAllChecked(evt, target) {

  }

  sortChange(field, sorting, target) {

  }

  updateRowData(key, value, index) {
    this.store.dispatch(new fromRiskLink.PatchAnalysisResultAction({
      key, value, index
    }))
  }

  openFinancialP(fp) {

  }

  selectRows(rowData, i) {
    this.updateRowData('selected', rowData.selected, i);
  }

  updateRow(rowData, type) {

  }

  updatePEQT() {
    this.updateRowData('peqt',
      this.refs.targetRaps.filter(item => item.selected).map(item => item.value),
      this.lastUpdatedAnalyisPEQTIndex);
    this.showUpdatePEQTModal = false;
  }

  openPEQTModal(i) {
    this.lastUpdatedAnalyisPEQTIndex=i;
    this.showUpdatePEQTModal=true;
  }

  onPEQTModalShow(){
    console.log('On PEQT modal show');
    this.refs.targetRaps= _.map(this.refs.targetRaps, item => ({
      ...item,
      selected: false
    }));
  }

}
