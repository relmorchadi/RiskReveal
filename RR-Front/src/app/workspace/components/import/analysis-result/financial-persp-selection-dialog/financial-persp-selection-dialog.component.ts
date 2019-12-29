import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import * as _ from "lodash";

@Component({
  selector: 'financial-persp-selection-dialog',
  templateUrl: './financial-persp-selection-dialog.component.html',
  styleUrls: ['./financial-persp-selection-dialog.component.scss']
})
export class FinancialPerspSelectionDialogComponent implements OnInit, OnChanges {

  @Input('visible')
  isVisible = false;

  @Input('data')
  data: { analysis, epCurves };

  @Output('close')
  closeEmitter = new EventEmitter();

  @Output('loadEpCurves')
  loadEpCurvesEmitter = new EventEmitter();

  @Output('override')
  overrideEmitter= new EventEmitter();

  rdmFilter = 'All';

  distinctRdms = [];

  changes = {analysis: {}, epCurves: {}, fpApplication: 'currentSelection'};


  colsFinancialAnalysis = [
    {
      field: 'selected',
      header: '',
      width: '25px',
      type: 'select',
      sorting: '',
      filtered: false,
      highlight: false,
      visible: true
    },
    {
      field: 'rlId',
      header: 'ID',
      width: '35px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true
    },
    {
      field: 'analysisName',
      header: 'Name',
      width: '100px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true
    },
    {
      field: 'analysisDescription',
      header: 'Description',
      width: '150px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true
    },
    {
      field: 'rpCode',
      header: 'Region Peril',
      width: '70px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true
    },
    {
      field: 'ty',
      header: 'TY',
      width: '40px',
      type: 'iconIndicator',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true
    },
    {
      field: 'financialPerspectives',
      header: 'Financial Perspective',
      width: '150px',
      type: 'tags',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true
    },
  ];
  colsFinancialStandard = [
    {
      field: 'selected',
      header: '',
      width: '25px',
      type: 'select',
      sorting: '',
      filtered: false,
      highlight: false,
      visible: true
    },
    {
      field: 'financialPerspective',
      header: 'Code',
      width: '40px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true
    },
    {
      field: 'description',
      header: 'Financial Perspective',
      width: '300px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true
    },
    {
      field: 'ccy',
      header: 'CCY',
      width: '60px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true
    },
    {
      field: 'purePremium',
      header: 'AAL',
      width: '100px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      number: true
    },
    {
      field: 'stdDev',
      header: 'STD DEV',
      width: '120px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      number: true
    },
    {
      field: 'oep50',
      header: 'OEP 50',
      width: '50px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      number: true
    },
    {
      field: 'oep100',
      header: 'OEP 100',
      width: '50px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      number: true
    },
    {
      field: 'oep250',
      header: 'OEP 250',
      width: '50px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true,
      number: true
    },
  ];

  constructor() {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.data) {
      this._loadEpCurvesChanges();
    }
  }


  ngOnInit() {
    this._loadAnalysisChanges();
    this.distinctRdms = ['All', ..._.uniq(_.map(this.data.analysis, analysis => analysis.rdmName))];
    this.changes.analysis[0].selected = true;
    this.loadEpCurvesEmitter.emit(this.data.analysis[0].rlAnalysisId);
  }


  selectRdm(item) {
    console.log('select RDM', item);
  }

  toggleAnalysisSelection(rowIndex) {
    this.changes.analysis[rowIndex].selected = !this.changes.analysis[rowIndex].selected;
    if(this._countSelectedAnalysis() == 1)
      this.loadEpCurvesEmitter.emit(this.data.analysis[rowIndex].rlAnalysisId)
  }

  toggleEpCurveSelection(rowIndex) {
    this.changes.epCurves[rowIndex].selected = !this.changes.epCurves[rowIndex].selected;
  }

  onFpApplicationChange(evt) {
    console.log('fp application change', evt);
  }

  applyChanges() {
    this.overrideEmitter.emit(this.changes.analysis);
  }

  close() {
    this.closeEmitter.emit();
  }

  removeFP(rowIndex, value: any) {
    event.stopPropagation();
    this.changes.analysis[rowIndex].financialPerspectives = _.filter(
      this.changes.analysis[rowIndex].financialPerspectives,
      item => item != value);
  }

  addFinancialPersp() {
    const selectedFps = this._getSelectedFps();
    switch (this.changes.fpApplication) {
      case 'All':
        this._addFpToAllAnalysis(selectedFps);
        break;
      case 'currentRDM':
        if (this.rdmFilter != 'All')
          _.forEach(this.changes.analysis, (analysis: any, index) => {
            if (analysis.rdmName == this.rdmFilter)
              analysis.financialPerspectives = _.uniq([...analysis.financialPerspectives, ...selectedFps]);
          });
        else
          this._addFpToAllAnalysis(selectedFps);
        break;
      case 'currentSelection':
        _.forEach(
          _.filter(this.changes.analysis, (item: any) => item.selected),
          (analysis: any) => {
            analysis.financialPerspectives = _.uniq([...analysis.financialPerspectives, ...selectedFps]);
          });
        break;
    }
  }

  replaceFinancialPersp() {
    const selectedFps = this._getSelectedFps();
    switch (this.changes.fpApplication) {
      case 'All':
        this._replaceFpToAllAnalysis(selectedFps);
        break;
      case 'currentRDM':
        if (this.rdmFilter != 'All')
          _.forEach(this.changes.analysis, (analysis: any) => {
            if (analysis.rdmName == this.rdmFilter)
              analysis.financialPerspectives = _.uniq([...selectedFps]);
          });
        else
          this._addFpToAllAnalysis(selectedFps);
        break;
      case 'currentSelection':
        _.forEach(
          _.filter(this.changes.analysis, (item: any) => item.selected),
          (analysis: any) => {
            analysis.financialPerspectives = _.uniq([...selectedFps]);
          });
        break;
    }
  }

  private _addFpToAllAnalysis(selectedFps: string[]) {
    _.forEach(this.changes.analysis, (analysis: any) => {
      analysis.financialPerspectives = _.uniq([...analysis.financialPerspectives, ...selectedFps]);
    });
  }

  private _replaceFpToAllAnalysis(selectedFps: string[]) {
    _.forEach(this.changes.analysis, (analysis: any) => {
      analysis.financialPerspectives = _.uniq([...selectedFps]);
    });
  }

  private _getSelectedFps(): string[] {
    return _.toArray(this.changes.epCurves)
      .filter((item: any) => item.selected)
      .map((item: any) => item.code);
  }


  private _loadAnalysisChanges() {
    this.changes.analysis = _.reduce(this.data.analysis, (result, value, index) => {
      const {financialPerspectives} = value;
      return _.merge(result, {[index]: {selected: false, financialPerspectives, rdmName: value.rdmName}})
    }, {});
  }

  private _loadEpCurvesChanges() {
    this.changes.epCurves = _.reduce(this.data.epCurves, (result, value, index) => {
      return _.merge(result, {[index]: {selected: false, code: value.financialPerspective}})
    }, {});
  }

  private _countSelectedAnalysis(){
    return _.toArray(this.changes.analysis).filter((item:any) => item.selected).length;
  }

}
