import {
  AfterViewInit,
  ChangeDetectionStrategy,
  Component, EventEmitter,
  Input,
  OnChanges,
  OnInit, Output,
  SimpleChanges,
  ViewChild
} from '@angular/core';
import EChartOption = echarts.EChartOption;
import {Message} from "../../../message";

@Component({
  selector: 'app-ep-chart',
  templateUrl: './ep-chart.component.html',
  styleUrls: ['./ep-chart.component.scss']
})
export class EpChartComponent implements OnInit, OnChanges {

  @Input() chartOption: EChartOption;
  @Input() updateOption: EChartOption;

  instance;

  @Output() actionDispatcher: EventEmitter<Message> = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges): void {
  }

  onLegendSelectionChange({ selected }) {
    this.actionDispatcher.emit({
      type: "On Legend Selection Change",
      payload: selected
    })
  }

  init(e){
    this.instance = e;
  }

}
