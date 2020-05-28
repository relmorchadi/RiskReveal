import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output
} from '@angular/core';
import {Message} from "../../../message";
import * as _ from 'lodash';
import EChartOption = echarts.EChartOption;
import {GeneralConfigState} from "../../../../core/store/states";
import {Store} from "@ngxs/store";

@Component({
  selector: 'app-summary-ep-metrics',
  templateUrl: './summary-ep-metrics.component.html',
  styleUrls: ['./summary-ep-metrics.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SummaryEpMetricsComponent implements OnInit {

  currencyFilter: string = '';
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

  @Input() exchangeRates;

  @Input() sourceCurrency;

  @Input() returnPeriodConfig: {
    showSuggestion: boolean,
    returnPeriodInput: number,
    message: string
  };

  @Input() rpValidation: {
    isValid: boolean,
    upperBound: number,
    lowerBound: number
  };

  @Input() chartOption: EChartOption;
  @Input() updateOption: EChartOption;

  numberConfig: {
    numberOfDecimals,
    decimalSeparator,
    decimalThousandSeparator,
    negativeFormat
  }

  constructor(private store: Store, private cdr: ChangeDetectorRef) { }

  ngOnInit() {
    this.store
        .select(GeneralConfigState.getNumberFormatConfig)
        .subscribe(({ numberOfDecimals, decimalSeparator, decimalThousandSeparator, negativeFormat }) => {
              this.numberConfig = {
                numberOfDecimals,
                decimalSeparator,
                decimalThousandSeparator,
                negativeFormat
              };
              this.detectChanges();
            }
        );
  }

  detectChanges(){
    if (!this.cdr['destroyed'])
      this.cdr.detectChanges();
  }

  curveTypeSelectChange(curveTypes) {
    let res = curveTypes;

    if(!curveTypes.length) {
      res= [ this.summaryEpMetricsConfig.selectedCurveType[0] ];
    }

    this.actionDispatcher.emit({
      type: "Selected CurveTypes Change",
      payload: res
    })
  }

  selectFinancialUnit(financialUnit: string) {
    this.actionDispatcher.emit({
      type: "Select Financial Unit",
      payload: financialUnit
    })
  }

  currencyChange(currency) {
    event.stopPropagation();
    this.actionDispatcher.emit({
      type: "Currency Change",
      payload: currency
    });
  }

  inputChange = _.debounce((newValue) => {
    this.actionDispatcher.emit({
      type: "Return period input change",
      payload: newValue
    })
  }, 150);

  addToReturnPeriod() {
    this.actionDispatcher.emit({
      type: "ADD Return period",
      payload: this.returnPeriodConfig.returnPeriodInput
    })
  }

  removeReturnPeriod(rp) {
    this.actionDispatcher.emit({
      type: "Delete RP",
      payload: rp
    })
  }

  handleChartActions(action: Message) {
    this.actionDispatcher.emit(action);
  }

  onFilterCurrencies(e) {
    console.log(e);
    this.currencyFilter = e;
  }

  preventPropagation(){
    event.stopPropagation();
  }

}
