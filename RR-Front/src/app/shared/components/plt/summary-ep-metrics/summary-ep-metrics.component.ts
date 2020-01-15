import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Message} from "../../../message";
import * as _ from 'lodash';

@Component({
  selector: 'app-summary-ep-metrics',
  templateUrl: './summary-ep-metrics.component.html',
  styleUrls: ['./summary-ep-metrics.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SummaryEpMetricsComponent implements OnInit {

  @Output() actionDispatcher: EventEmitter<Message> = new EventEmitter<Message>();

  @Input() constants: {
    financialUnits: string[];
    curveTypes: any[];
    currencies: string[];
  };

  @Input() summaryEpMetricsConfig: {
    selectedCurrency: string;
    selectedFinancialUnit: string;
    selectedCurveType: any[];
  };

  @Input() epMetricsTableConfig: {
    columns: any[];
  };

  @Input() epMetrics;

  @Input() rps;

  @Input() epMetricsLosses;

  constructor() { }

  ngOnInit() {

  }

  curveTypeSelectChange(curveTypes) {
    this.actionDispatcher.emit({
      type: curveTypes.length < this.summaryEpMetricsConfig.selectedCurveType.length ? "Hide Metric" : "Show Metric",
      payload: {
        curveTypes,
        difference: curveTypes.length < this.summaryEpMetricsConfig.selectedCurveType.length ? _.difference(this.summaryEpMetricsConfig.selectedCurveType, curveTypes) : _.difference(curveTypes, this.summaryEpMetricsConfig.selectedCurveType)
      }
    })
  }

  selectFinancialUnit(financialUnit: string) {
    this.actionDispatcher.emit({
      type: "Select Financial Unit",
      payload: financialUnit
    })
  }
}
