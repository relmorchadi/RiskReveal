import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import * as _ from 'lodash';

@Component({
  selector: 'override-region-peril-dialog',
  templateUrl: './override-region-peril-dialog.component.html',
  styleUrls: ['./override-region-peril-dialog.component.scss']
})
export class OverrideRegionPerilDialogComponent implements OnInit {

  @Input('visible')
  isVisible;

  @Input('data')
  analyses;

  @Input('regionPerilByAnalysis')
  regionPerilByAnalysis = {
    9: [
      'EUET',
      'EUET-AT'
    ],
    10: [
      'EUET',
      'EUET-BE',
      'EUET-CH'
    ]
  };

  @Output('close')
  closeEmitter = new EventEmitter();

  @Output('override')
  overrideEmitter = new EventEmitter();

  overrideChanges;

  regionPerilDataTable = [
    {
      field: 'rlAnalysisId',
      header: 'ID',
      width: '50px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true
    },
    {
      field: 'analysisName',
      header: 'Analysis Name',
      width: '90px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true
    },
    {
      field: 'rpCode',
      header: 'Region Peril',
      width: '80px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true
    },
    {
      field: 'override',
      header: 'Override',
      width: '250px',
      type: 'override',
      sorting: '',
      filtered: false,
      highlight: false,
      visible: true
    },
    {
      field: 'copy',
      header: 'Copy',
      width: '50px',
      type: 'function',
      sorting: '',
      filtered: false,
      highlight: false,
      visible: true
    },
    {
      field: 'reason',
      header: 'Reason',
      width: '170px',
      type: 'comment',
      sorting: '',
      filtered: false,
      highlight: false,
      visible: true
    },
  ];

  constructor() {

  }

  ngOnInit() {
    this.overrideChanges = _.reduce(this.analyses, (result, value, index) => {
      const {rpCode, rpOccurrenceBasis, rlAnalysisId} = value;
      return _.merge(result, {[rlAnalysisId]: {rpCode, rpOccurrenceBasis}})
    }, {});
  }

  unselectAll() {

  }

  selectRows(rowData, index) {

  }

  overrideRegionPeril(value, key) {
    this.overrideChanges[key].rpCode = value;
  }

  doCopyRegionPeril(rlAnalysisId) {
    const targetRpCode = this.overrideChanges[rlAnalysisId].rpCode;
    _.keys(this.overrideChanges)
      .filter(key => {
        return this.canOverride(key, targetRpCode);
      })
      .forEach(key => this.overrideChanges[key].rpCode = targetRpCode);
  }

  canOverride(analysisId, targetRegionPeril): boolean {
    return _.indexOf(this.regionPerilByAnalysis[analysisId], targetRegionPeril) != -1;
  }

  closeDialog() {
    this.closeEmitter.emit();
  }

  applyOverride() {
    this.overrideEmitter.emit(this.overrideChanges);
  }

}
