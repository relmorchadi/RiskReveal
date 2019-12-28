import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Message} from "../../../../shared/message";
import {StatusFilter} from "../../../model/status-filter.model";
import * as fromWorkspaceStore from "../../../store";
import {Observable} from "rxjs";
import {Store} from "@ngxs/store";
import {tap} from "rxjs/operators";
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

  @Input() data: any[];
  @Input() epMetrics: any;
  @Input() adjustments: any;

  @Input() tableConfig: {
    view: 'adjustment' | 'analysis' | 'epMetrics',
    selectedCurveType: string,
    isExpanded: boolean,
    isGrouped: boolean
  };
  @Input() columnsConfig: {
    frozenColumns: any[],
    columns: any[],
    columnsLength: number
  };
  @Input() rowKeys: any;

  statusFilter: StatusFilter;
  selectedStatusFilter: any = {};
  lastClick: any = {
    pure_index: null,
    thread_index: null
  };
  lastSelectedId: any;
  workspaceId: any;
  uwy: any;
  selectOptions: any = {
    checkAll: false,
    indeterminate: false
  }


  /*selectedThreads: any = {

  }*/

  constructor(private _baseStore: Store, private route$: ActivatedRoute,) { }

  ngOnInit() {
    this.statusFilter = new StatusFilter();
    this.observeRouteParams().pipe().subscribe(() => {});
    setInterval(()=>{
      console.log(this.uwy, this.workspaceId)
    },3000)
  }

  statusFlilerCheckbox($event: any, type: string) {
    switch (type) {
      case 'inProgress':
        if (event.target['checked']) this.selectedStatusFilter = {...this.selectedStatusFilter, 'inProgress': true};
        else this.selectedStatusFilter = _.omit(this.selectedStatusFilter, ['inProgress']);
        break;
      case 'new':
        if (event.target['checked']) this.selectedStatusFilter = {...this.selectedStatusFilter, 'new': true};
        else this.selectedStatusFilter = _.omit(this.selectedStatusFilter, ['new']);
        break;
      case 'valid':
        if (event.target['checked']) this.selectedStatusFilter = {...this.selectedStatusFilter, 'valid': true};
        else this.selectedStatusFilter = _.omit(this.selectedStatusFilter, ['valid']);
        break;
      case 'locked':
        if (event.target['checked']) this.selectedStatusFilter = {...this.selectedStatusFilter, 'locked': true};
        else this.selectedStatusFilter = _.omit(this.selectedStatusFilter, ['locked']);
        break;
      case 'requiresRegeneration':
        if (event.target['checked']) this.selectedStatusFilter = {...this.selectedStatusFilter, 'requiresRegeneration': true};
        else this.selectedStatusFilter = _.omit(this.selectedStatusFilter, ['requiresRegeneration']);
        break;
      case 'failed':
        if (event.target['checked']) this.selectedStatusFilter = {...this.selectedStatusFilter, 'failed': true};
        else this.selectedStatusFilter = _.omit(this.selectedStatusFilter, ['failed']);
        break;
    }
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

  /*toggleSelectPlts(plts: any) {
    console.log(plts);
    this.dispatch(new fromWorkspaceStore.ToggleSelectPlts({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      plts,
      forDelete: this.getTableInputKey('showDeleted')
    }));
  }*/
  handlepltClick($event, threadId, pureId, pureIndex, threadIndex, clickType) {
    console.log(pureIndex);
    console.log(threadIndex);
    /*threadIndex = _.findIndex(this.data[pureIndex].threads, (row: any)=>{ row.pltId = threadId });*/
    let index = -1;
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
      console.log(this.data)
      //let plts: any = []
      this.data.forEach((pure, i) => {
        pure.threads.forEach((thread, j) => {
          if (pureIndex === i && threadIndex === j){
            thread.selected = !isSelected;
          } else {
            thread.selected = isSelected;
          }
        });
      })
      console.log(this.data)
      this.toggleSelectPlts(this.data);
      this.updateLastClick(pureIndex, threadIndex)
      this.selectOptions.indeterminate = true;
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
      const maxpure = this.lastClick.pure_index =! null ? Math.max(pureIndex, this.lastClick.pure_index) : 0;
      const minpure = this.lastClick.pure_index =! null ? Math.min(pureIndex, this.lastClick.pure_index) : 0;
      const maxthread = this.lastClick.thread_index =! null ? Math.max(threadIndex, this.lastClick.thread_index) : 0;
      const minthread = this.lastClick.thread_index  =! null ? Math.min(threadIndex, this.lastClick.thread_index) : 0;
      console.log(maxpure, minpure, maxthread, minthread)
      this.data.forEach((pure, i) => {
          pure.threads.forEach((thread, j) => {
            if ((i < maxpure && i > minpure)){
              thread.selected = true;
            } else if(i === maxpure || i === minpure) {
              if (j <= maxthread && j >= minthread) {
                thread.selected = true;
              }
            } else {
              thread.selected = false;
            }
          });
      });
      this.toggleSelectPlts(this.data);
    }
  }

  toggleSelectPlts(plts: any) {
    console.log(plts);
    this.dispatch(new fromWorkspaceStore.ToggleSelectCalibPlts({ // ToggleSelectPlts
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      plts: plts
    }));
  }

  protected dispatch(action: any | any[]): Observable<any> {
    return this._baseStore.dispatch(action);
  }

  observeRouteParams() {
    return this.route$.params.pipe(tap(({wsId, year}) => {
      this.workspaceId = wsId;
      this.uwy = year;
    }))
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
    const plts = this.data.forEach((pure) => {
      pure.threads.forEach((thread) => {
        if (isOneChecked) thread.selected = false;
        else thread.selected = true;
      })
    })
    this.toggleSelectPlts(plts);
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
}
