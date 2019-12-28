import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Message} from "../../../../shared/message";
import {StatusFilter} from "../../../model/status-filter.model";
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

  @Input() mode: 'default' | 'grouped';
  @Input() tableConfig: {
    mode: 'default' | 'grouped',
    view: 'adjustment' | 'analysis' | 'epMetrics',
    selectedCurveType: string,
    isExpanded: boolean
  };
  @Input() columnsConfig: {
    frozenColumns: any[],
    columns: any[],
    columnsLength: number
  };
  @Input() rowKeys: any;
  isGrouped = false;

  statusFilter: StatusFilter;
  selectedStatusFilter: any = {};

  constructor() { }

  ngOnInit() {
    this.statusFilter = new StatusFilter();
    /*setInterval(()=>{
      console.log(this.statusFilter);
    },3000)*/
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
      type: 'View Change',
      payload: newView
    })
  }

  getColumnsState(cols) {
    return _.filter(cols, item => item.isFrozen).length > 0;
  }

  groupByPure() {
    this.isGrouped = !this.isGrouped;
  }
}
