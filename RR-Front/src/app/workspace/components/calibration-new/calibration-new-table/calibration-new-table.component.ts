import {
  AfterViewChecked,
  AfterViewInit,
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output
} from '@angular/core';
import {Message} from "../../../../shared/message";
import * as fromWorkspaceStore from "../../../store";
import {Observable} from "rxjs";
import {Store} from "@ngxs/store";
import {ActivatedRoute} from "@angular/router";
import * as _ from 'lodash';
import * as tableStore from "../../../../shared/components/plt/plt-main-table/store";
@Component({
  selector: 'app-calibration-new-table',
  templateUrl: './calibration-new-table.component.html',
  styleUrls: ['./calibration-new-table.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CalibrationNewTableComponent implements OnInit, AfterViewInit, AfterViewChecked {

  @Output() actionDispatcher: EventEmitter<Message> = new EventEmitter<Message>();

  @Input() wsIdentifier: string;
  @Input() data: any[];
  @Input() epMetrics: any;
  @Input() adjustments: any;

  @Input() tableConfig: {
    view: 'adjustments' | 'analysis' | 'epMetrics',
    selectedCurveType: string,
    selectedFinancialUnit: string,
    selectedCurrency: string,
    isExpanded: boolean,
    expandedRowKeys: any,
    isGrouped: boolean,
    filterData: any,
    sortData: any,
    isDeltaByAmount: boolean,
    isExpandAll: boolean
  };

  @Input() exchangeRates: any;

  @Input() columnsConfig: {
    frozenColumns: any[],
    frozenWidth: string,
    columns: any[],
    columnsLength: number
  };

  @Input() constants: {
    financialUnits: string[],
    curveTypes: string[],
    currencies: string[]
  };

  @Input() status: any[];
  @Input() selectedStatusFilter: any;
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

  contextMenuItem : any[];

  constructor(private _baseStore: Store) { }

  ngOnInit() {
    this.contextMenuItem = [
      {label: 'Expand', command: (event)=>{this.expandAllPlts()}},
      {label: 'Collapse', command: (event)=>{this.collapseAllPlts()}},
    ]
  }
  ngAfterViewInit() {
  }
  ngAfterViewChecked(): void {

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
    let isAllSelected: boolean = true;
    let isNoOneSelected: boolean = true;
    this.data.forEach((pure)=>{
      pure.threads.forEach((thread)=>{
        if (pure.pltId === pureId && thread.pltId === threadId){
          thread.selected = event.target.checked;
        }
        if (!thread.selected) isAllSelected = false;
        if (thread.selected) isNoOneSelected = false;
      })
    })
    this.selectOptions.indeterminate = (isAllSelected || isNoOneSelected) ? false : true;
    this.selectOptions.checkAll = isAllSelected ? true : false;
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
      type: "Currency Change",
      payload: currency
    })
  }

  financialUnitChange(financialUnit: any) {
    this.actionDispatcher.emit({
      type: "Financial Unit Change",
      payload: financialUnit
    })
  }

  onColumnResize({delta, element}) {
    const isFrozen = element.attributes[6].value;
    const index = element.attributes[7].value;
    if(!this.tableConfig.isExpanded && isFrozen) {
      this.actionDispatcher.emit({
        type: "Resize frozen Column",
        payload: {
          delta,
          index
        }
      })
    }
  }

  openRPManager() {
    this.actionDispatcher.emit({
      type: "Open return periods manager"
    })
  }


  toggleStatusFilter(value, status: any) {
    this.actionDispatcher.emit({
      type: "Toggle Status Filter",
      payload: {
        value,
        status
      }
    })
  }

  stopPropagation(event) {
    event.stopPropagation();
  }


  expandAllPlts() {
    this.actionDispatcher.emit({
      type: "Expand All Plts"
    })
  }

  collapseAllPlts() {
    this.actionDispatcher.emit({
      type: "Collapse All Plts"
    })
  }

  onTableSeparatorResize(event) {
    Math.abs(event.edges.right) > 20 && this.actionDispatcher.emit({
      type: "Resize Table Separator",
      payload: event.edges.right
    });
    }

  filter = _.debounce((field: any, value: string) => {
    this.actionDispatcher.emit({
      type: "Filter Table",
      payload: value ? _.merge({}, this.tableConfig.filterData, {[field]: value}) : _.omit(this.tableConfig.filterData, `${field}`)
    })
  })

  exportEPMetrics() {
    this.actionDispatcher.emit( {
      type: "Export EP Metrics",
      payload: [ ...this.columnsConfig.frozenColumns, ...this.columnsConfig.columns ]
    } )
  }

  onDeltaChange(newValue) {
    console.log(this.tableConfig.isDeltaByAmount);
    this.actionDispatcher.emit({
      type: "Delta Change",
      payload: newValue
    })
  }

  sortChange(field: any, sortCol: any) {
    if (!sortCol) {
      this.actionDispatcher.emit({
        type: "Sort Table",
        payload: _.merge({}, this.tableConfig.sortData, {[field]: 'asc'})
      });
    } else if (sortCol === 'asc') {
      this.actionDispatcher.emit({
        type: "Sort Table",
        payload: _.merge({}, this.tableConfig.sortData, {[field]: 'desc'})
      });
    } else if (sortCol === 'desc') {
      this.actionDispatcher.emit({
        type: "Sort Table",
        payload: _.omit(this.tableConfig.sortData, `${field}`)
      });
    }
  }

}
