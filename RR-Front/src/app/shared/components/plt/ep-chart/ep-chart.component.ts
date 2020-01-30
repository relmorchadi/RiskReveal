import {ChangeDetectionStrategy, Component, Input, OnInit} from '@angular/core';
import EChartOption = echarts.EChartOption;

@Component({
  selector: 'app-ep-chart',
  templateUrl: './ep-chart.component.html',
  styleUrls: ['./ep-chart.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EpChartComponent implements OnInit {

  @Input() chartOption: EChartOption;
  @Input() updateOption: EChartOption;

  constructor() { }

  ngOnInit() {
  }

}
