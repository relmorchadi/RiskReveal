import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Message} from "../../../../shared/message";
import {StatusFilter} from "../../../model/status-filter.model";
import * as fromWorkspaceStore from "../../../store";
import {Observable} from "rxjs";
import {Store} from "@ngxs/store";
import {ActivatedRoute} from "@angular/router";
declare  const _;
@Component({
  selector: 'app-calibration-new-table',
  templateUrl: './calibration-new-table.component.html',
  styleUrls: ['./calibration-new-table.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CalibrationNewTableComponent implements OnInit {

  @Output() actionDispatcher: EventEmitter<Message> = new EventEmitter<Message>();

  @Input() wsIdentifier: string;
  @Input() data: any[];
  @Input() epMetrics: any;
  @Input() adjustments: any;

  @Input() tableConfig: {
    view: 'adjustment' | 'analysis' | 'epMetrics',
    selectedCurveType: string,
    selectedFinancialUnit: string,
    isExpanded: boolean,
    expandedRowKeys: any,
    isGrouped: boolean
  };
  @Input() columnsConfig: {
    frozenColumns: any[],
    frozenWidth: string,
    columns: any[],
    columnsLength: number
  };
  @Input() rowKeys: any;

  @Input() constants: {
    financialUnits: string[],
    curveTypes: string[]
  };

  statusFilter: StatusFilter;
  selectedStatusFilter: any = {

  };
  lastClick: any = {
    pure_index: null,
    thread_index: null
  };
  currentClick: any = {
    pure_index: null,
    thread_index: null
  };
  lastSelectedId: any;
  selectOptions: any = {
    checkAll: false,
    indeterminate: false
  };
  selectedCurrencie: any = 'EUR';
  currencies = {
    data: [
      {id: '1', name: 'Euro', label: 'EUR'},
      {id: '2', name: 'Us Dollar', label: 'USD'},
      {id: '3', name: 'Britsh Pound', label: 'GBP'},
      {id: '4', name: 'Canadian Dollar', label: 'CAD'},
      {id: '5', name: 'Moroccan Dirham', label: 'MAD'},
      {id: '5', name: 'Swiss Franc', label: 'CHF'},
      {id: '5', name: 'Saudi Riyal', label: 'SAR'},
      {id: '6', name: 'Bitcoin', label: 'XBT'},
      {id: '7', name: 'Hungarian forint', label: 'HUF'},
      {id: '8', name: 'Singapore Dollars', label: 'SGD'}
    ],
    selected: {id: '1', name: 'Euro', label: 'EUR'}
  };
  statusOptions:any = [
    {title: 'In Progress', field: 'inProgress', class: 'icon-history-alt iconYellow'},
    {title: 'New', field: 'new', class: 'icon-star iconBlue'},
    {title: 'Valid', field: 'valid', class: 'icon-check-circle iconGreen'},
    {title: 'Locked', field: 'locked', class: 'icon-lock-alt iconRed'},
    {title: 'Requires regeneration', field: 'requiresRegeneration', class: 'icon-report_problem_24px iconYellow2'},
    {title: 'Failed', field: 'failed', class: 'icon-error_24px iconRed2'}
  ]



  constructor(private _baseStore: Store, private route$: ActivatedRoute,) { }

  ngOnInit() {
    this.statusFilter = new StatusFilter();
    this.selectedStatusFilter = this.statusFilter;
  }

  statusFlilerCheckbox(event: any, type: string) {
    this.selectedStatusFilter = {...this.selectedStatusFilter, [type]: event};
  }

  unexpandColumns() {
    this.actionDispatcher.emit({
      type: "Expand columns OFF"
    })
  }

  expandColumns() {
    this.actionDispatcher.emit({
      type: "Expand columns ON"
    })
  }

  onViewChange(newView) {
    this.actionDispatcher.emit({
      type: "View Change",
      payload: newView
    })
  }

  toggleGrouping() {
    this.actionDispatcher.emit({
      type: "Toggle Grouping"
    })
  }

  handlepltClick($event, pureId, threadId, pureIndex, threadIndex, clickType) {
    let isSelected;
    _.forEach(this.data, (plt, i)=>{
      _.forEach(plt.threads, (thread, j)=>{
        if (thread.pltId == threadId){
          isSelected = thread.selected;
        }
      })
    });
    if ($event.ctrlKey || $event.shiftKey) {
      this.handlePLTClickWithKey(pureId, pureIndex, threadId, threadIndex, !isSelected, $event);
    } else {
      this.lastSelectedId = threadIndex;
      this.data.forEach((pure, i) => {
        pure.threads.forEach((thread, j) => {
          if (pureIndex === i && threadIndex === j){
            thread.selected = !isSelected;
          } else {
            thread.selected = false;
          }
        });
      })
      this.toggleSelectPlts(this.data);
      this.updateLastClick(pureIndex, threadIndex)
      this.selectOptions.indeterminate = isSelected ?  false : true;
    }
  }

  private handlePLTClickWithKey(pureId, pureIndex, threadId: any, threadIndex: any, isSelected: boolean, $event: any) {
    if ($event.ctrlKey){
      this.data.forEach((pure, i) => {
        pure.threads.forEach((thread, j) => {
          if (pureIndex === i && threadIndex === j){
            thread.selected = isSelected;
          }
        });
      })
      this.toggleSelectPlts(this.data);
      this.updateLastClick(pureIndex, threadIndex);
    } else if($event.shiftKey){
      this.currentClick.pure_index = pureIndex;
      this.currentClick.thread_index = threadIndex;
      this.lastClick.pure_index = this.lastClick.pure_index != null ? this.lastClick.pure_index : 0;
      this.lastClick.thread_index = this.lastClick.thread_index != null ? this.lastClick.thread_index : 0;
      let maxPure = _.maxBy([this.lastClick, this.currentClick], (o)=>{ return o.pure_index })
      let minPure = _.minBy([this.lastClick, this.currentClick], (o)=>{ return o.pure_index })
      _.forEach(this.data, (pure, i)=>{
        _.forEach(pure.threads, (thread, j)=>{
          if (i<maxPure.pure_index && i>minPure.pure_index){
            thread.selected = true;
          } else if (i == maxPure.pure_index && i == minPure.pure_index){
            let maxThread = _.max([this.lastClick.thread_index, this.currentClick.thread_index]);
            let minThread = _.min([this.lastClick.thread_index, this.currentClick.thread_index]);
            if (j <= maxThread && j >= minThread){
              thread.selected = true;
            }else{
              thread.selected = false;
            }
          } else if(i == maxPure.pure_index){
            if (j <= maxPure.thread_index){
              thread.selected = true;
            } else {
              thread.selected = false;
            }
          } else if(i == minPure.pure_index){
            if (j >= minPure.thread_index){
              thread.selected = true;
            } else {
              thread.selected = false;
            }
          } else {
            thread.selected = false;
          }
        })
      })
      this.toggleSelectPlts(this.data);
    }
  }

  toggleSelectPlts(plts: any) {
    this.dispatch(new fromWorkspaceStore.ToggleSelectCalibPlts({
      wsIdentifier: this.wsIdentifier,
      plts: plts
    }));
  }

  protected dispatch(action: any | any[]): Observable<any> {
    return this._baseStore.dispatch(action);
  }

  dispatchTableActions(payload) {
    let type = payload.type;
    switch (type) {
      case 'check_all':
        this.checkAll();
        break;
      case 'single_check':
        let event = payload.event;
        let pureId = payload.pureId;
        let threadId = payload.threadId;
        this.singleCheck(event, pureId, threadId);
        break;
    }
  }

  checkAll(){
    let isOneChecked: boolean = false;
    this.data.forEach((pure)=>{
      pure.threads.forEach((thread)=>{
        if (thread.selected != 'undefined' && thread.selected == true){
          isOneChecked = true;
          return;
        }
      });
    })
    this.data.forEach((pure) => {
      pure.threads.forEach((thread) => {
        if (isOneChecked) thread.selected = false;
        else thread.selected = true;
      })
    })
    this.toggleSelectPlts(this.data);
    this.selectOptions.checkAll = isOneChecked ? false : true;
    this.selectOptions.indeterminate = false;
  }

  singleCheck(event, pureId, threadId){
    this.data.forEach((pure)=>{
      pure.threads.forEach((thread)=>{
        if (pure.pltId === pureId && thread.pltId === threadId){
          thread.selected = event.target.checked;
        }
      })
    })
    this.toggleSelectPlts(this.data);
  }

  private updateLastClick(pureIndex: any, threadIndex: any) {
    this.lastClick.pure_index = pureIndex;
    this.lastClick.thread_index = threadIndex;
  }

  viewAdjustmentDetail(adjustment) {
    this.actionDispatcher.emit({
      type: "View Adjustment Detail",
      payload: adjustment
    })
  }

  curveTypeChange(curveType) {
    this.actionDispatcher.emit({
      type: "Curve Type Change",
      payload: curveType
    })
  }

  changeCurrencie(currency: any) {
    this.actionDispatcher.emit({
      type: "Financial Unit Change",
      payload: currency
    })
  }

  financialUnitChange(financialUnit: any) {
    this.actionDispatcher.emit({
      type: "Financial Unit Change",
      payload: financialUnit
    })
  }

  onColumnResize({delta, element: {id}}) {
    if(!this.tableConfig.isExpanded) {
      this.actionDispatcher.emit({
        type: "Resize frozen Column",
        payload: delta
      })
    }
  }

  openRPManager() {
    this.actionDispatcher.emit({
      type: "Open return periods manager"
    })
  }

  displa(col: any) {
    console.log(col)
  }
}
