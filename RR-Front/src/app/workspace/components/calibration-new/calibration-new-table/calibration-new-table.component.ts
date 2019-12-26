import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Message} from "../../../../shared/message";

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

  constructor() { }

  ngOnInit() {

  }

  rerouteActions(action: Message) {
    this.actionDispatcher.emit(action);
  }

}
