import {ChangeDetectionStrategy, Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import EChartOption = echarts.EChartOption;

@Component({
  selector: 'app-ep-chart',
  templateUrl: './ep-chart.component.html',
  styleUrls: ['./ep-chart.component.scss']
})
export class EpChartComponent implements OnInit, OnChanges {

  @Input() chartOption: EChartOption;
  @Input() updateOption: EChartOption;

  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges): void {
  }

}
