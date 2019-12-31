import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import * as _ from 'lodash';

@Component({
  selector: 'override-peqt-dialog',
  templateUrl: './override-peqt-dialog.component.html',
  styleUrls: ['./override-peqt-dialog.component.scss']
})
export class OverridePeqtDialogComponent implements OnInit, OnChanges {

  @Input('data')
  data: { analysis, targetRaps };

  @Input('visible')
  isVisible;

  @Output('loadTargetRaps')
  loadTargetRapsEmitter = new EventEmitter();

  @Output('close')
  closeEmitter= new EventEmitter();

  @Output('override')
  overrideEmitter = new EventEmitter();

  currentAnalysis=null;

  peqtDataTable = [
    {
      field: 'expand',
      header: '',
      width: '20px',
      type: 'expand',
      sorting: false,
      filtered: false,
      highlight: false,
      visible: true
    },
    {
      field: 'title',
      header: 'Region Peril',
      width: '80px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true
    },
    {
      field: 'selectedItems',
      header: 'Selection',
      width: '210px',
      type: 'info',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true
    },
  ];
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
      field: 'targetRapCode',
      header: 'target Rap',
      width: '150px',
      type: 'text',
      sorting: '',
      filtered: true,
      highlight: false,
      visible: true
    },
  ];

  expandedRows: any = {};
  tree: any[] = [];
  changes={};

  constructor() {
  }

  ngOnInit() {
    this.tree = this._loadRpAnalysisTree();
    _.forEach(this.data.analysis,item => {
      this.changes[item.rlAnalysisId]=item.targetRaps || [];
    });
  }

  ngOnChanges(simpleChanges: SimpleChanges): void {
    if(simpleChanges.data){
      this._synchronizeTargetRapSelection();
    }
  }

  selectBranch(item: any) {
    this._unselectAllAnalysis();
    item.selected = !item.selected;
    const {rlAnalysisId}= item;
    this.loadTargetRapsEmitter.emit(rlAnalysisId);
    this.currentAnalysis= item.selected ? item : null;
    this._synchronizeTargetRapSelection();
  }

  toggleTargetRapSelection(rowData){
    rowData.selected= !rowData.selected;
    this.targetRapSelectionChange(rowData, rowData.selected);
  }

  targetRapSelectionChange(rowData, selection){
    const {rlAnalysisId}=this.currentAnalysis;
    if(selection){
      if(this.changes[rlAnalysisId])
        this.changes[rlAnalysisId].push(rowData);
      else
        this.changes[rlAnalysisId]= [rowData];
    }else{
      this.changes[rlAnalysisId]= this.changes[rlAnalysisId].filter(i => i.targetRapCode != rowData.targetRapCode);
    }
  }

  unselectTargetRap(peqt: any, rlAnalysisId) {
    event.stopPropagation();
    this.changes[rlAnalysisId]= _.filter(this.changes[rlAnalysisId], item => item.targetRapCode != peqt.targetRapCode);
    this._synchronizeTargetRapSelection();
  }

  applyChanges() {
    this.overrideEmitter.emit(this.changes);
  }

  close(){
    this.closeEmitter.emit();
  }

  toggleRowExpansion(rowData) {
    rowData.expanded= !rowData.expanded;
    console.log('Toggle row expansion', rowData);
  }

  private _loadRpAnalysisTree(): any[] {
    const distinctRegionPerils = _.uniq(_.map(this.data.analysis, item => item.rpCode));
    return _.map(distinctRegionPerils, (rpCode,i) => ({
      expanded: false,
      title: rpCode,
      children: _.map(
        _.filter(this.data.analysis, item => item.rpCode == rpCode),
        item => ({
          selected: false,
          rlAnalysisId: item.rlAnalysisId,
          title: item.analysisName,
        })
      )
    }))
  }

  private _unselectAllAnalysis(){
    _.forEach(this.tree, item => {
      _.forEach(item.children, child => {
        child.selected=false;
      })
    })
  }

  private _synchronizeTargetRapSelection(){
    if(! this.currentAnalysis){
      console.error("No selected Analysis !! failed selection synchronisation");
      return;
    }
    const selectedTargetRaps= _.map(this.changes[this.currentAnalysis.rlAnalysisId], item => item.targetRapCode);
    this.data.targetRaps.forEach(targetRap => {
      targetRap.selected= _.find(selectedTargetRaps, i => i == targetRap.targetRapCode) != null;
    });
  }

}
