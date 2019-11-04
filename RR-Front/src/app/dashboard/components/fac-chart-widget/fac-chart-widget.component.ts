import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import {Data} from '../../../core/model/data';
import {Select, Store} from '@ngxs/store';
import {WorkspaceState} from '../../../workspace/store/states';
import {WsApi} from '../../../workspace/services/api/workspace.api';
import * as _ from 'lodash';
import {GeneralConfigState} from '../../../core/store/states';
import * as workspaceActions from '../../../workspace/store/actions/workspace.actions';

@Component({
  selector: 'app-fac-chart-widget',
  templateUrl: './fac-chart-widget.component.html',
  styleUrls: ['./fac-chart-widget.component.scss']
})
export class FacChartWidgetComponent implements OnInit {
  @Output('delete') delete: any = new EventEmitter<any>();
  @Output('duplicate') duplicate: any = new EventEmitter<any>();
  @Output('changeName') changeName: any = new EventEmitter<any>();

  private dropdown: NzDropdownContextComponent;
  uwyUnits;
  cedants = Data.cedant;

  /*  filterAssignedNew = false;
    filterAssignedCurrent = false;
    filterAssignedArchive = false;*/

  itemStyle = {
    normal: {
    },
    emphasis: {
      barBorderWidth: 1,
      shadowBlur: 10,
      shadowOffsetX: 0,
      shadowOffsetY: 0,
      shadowColor: 'rgba(0,0,0,0.5)'
    }
  };

  chartOption: any = {
    legend: {
      data: ['New', 'In Progress', 'Canceled', 'Superseded', 'Completed'],
      align: 'left',
      left: 10
    },
    tooltip: {},
    toolbox: {
      feature: {
        magicType: {
          title: {
            stack: 'stack',
            tiled: 'tiled',
          },
          type: ['stack', 'tiled']
        },
      },
    },
    xAxis: {
      type: 'category',
      data: []
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: 'New',
        data: [],
        type: 'bar',
        stack: 'one',
        itemStyle: this.itemStyle,
      },
      {
        name: 'In Progress',
        data: [],
        type: 'bar',
        stack: 'one',
        itemStyle: this.itemStyle,
      },
      {
        name: 'Canceled',
        data: [],
        type: 'bar',
        stack: 'one',
        itemStyle: this.itemStyle,
      },
      {
        name: 'Superseded',
        data: [],
        type: 'bar',
        stack: 'one',
        itemStyle: this.itemStyle,
      },
      {
        name: 'Completed',
        data: [],
        type: 'bar',
        stack: 'one',
        itemStyle: this.itemStyle,
      }
    ],
    color: ['#F8E71C', '#F5A623', '#E70010', '#DDDDDD', '#7BBE31']
  };

  @ViewChild('chart') chart;

  @Input()
  itemName = 'Car Widget';
  @Input()
  dashboard: any;
  @Input()
  identifier: number;
  @Input()
  widgetIndex: number;
  newDashboard: any;
  editName = false;

  mockData = [];
  private defaultCountry: string;
  private defaultUwUnit: string;
  Countries = Data.coutryAlt;
  private mockDataCache;
  tabIndex = 1;

  @Select(WorkspaceState.getFacData) facData$;
  data: any[];
  filteredData: any[];

  analystList: any;
  selectedAnalyst = 'ALL';

  dateTo: any;
  dateFrom: any;

  constructor(private nzDropdownService: NzDropdownService, private store: Store,
              private cdRef: ChangeDetectorRef,
              private wsApi: WsApi) {
    this.uwyUnits = _.uniqBy(this.mockData, 'UNDERWRITINGUNITCODE');
  }

  ngOnInit() {
    this.newDashboard = this.dashboard;
    this.facData$.subscribe(value => {
      this.data = value;
      this.filteredData = value;
      this.chartOption.xAxis.data = _.uniq(value.map(item => item.assignedAnalyst));
      this.analystList = [..._.uniq(value.map(item => item.assignedAnalyst)), 'ALL'];
      this.setValues();
    });
    this.store.select(GeneralConfigState.getGeneralConfigAttr('contractOfInterest', {
      country: '',
      uwUnit: ''
    })).subscribe(coi => {
      this.defaultCountry = coi.defaultCountry;
      this.defaultUwUnit = coi.defaultUwUnit;
      this.detectChanges();
    });
  }

  selectTab(index) {
    this.tabIndex = index;
  }

  openFacItem(event) {
    this.store.dispatch(new workspaceActions.OpenFacWS({wsId: event.uwanalysisContractFacNumber,
      uwYear: event.uwAnalysisContractDate, route: 'Project', type: 'fac', item: event}));
    this.store.dispatch(new workspaceActions.LoadProjectForWs({wsId: event.uwanalysisContractFacNumber,
      uwYear: event.uwAnalysisContractDate}));
  }

  valueFavChange(event) {
    this.store.dispatch(new workspaceActions.MarkFacWsAsFavorite(event));
  }

  duplicateItem(itemName: any): void {
    this.duplicate.emit(itemName);
  }

  deleteItem(id): void {
    this.delete.emit(id);
  }

  contextMenu($event: MouseEvent, template: TemplateRef<void>): void {
    this.dropdown = this.nzDropdownService.create($event, template);
  }

  close(e: NzMenuItemDirective): void {
    this.dropdown.close();
  }

  validateName(keyboardMap, id) {

    if (keyboardMap.key === 'Enter') {
      this.changeName.emit({itemId: id, newName: keyboardMap.target.value});
      this.editName = false;
    }
  }

  activeEdit() {
    this.editName = true;
  }

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
  }

  changeUserFilter(value) {
    if (value === 'ALL') {
      this.filteredData = [...this.data];
      this.chart.xAxis.data = _.uniq(this.data.map(item => item.assignedAnalyst));
    } else {
      this.filteredData = _.filter(this.data, item => item.assignedAnalyst === value);
      this.chart.xAxis.data = [value];
      this.setValues();
    }
  }

  setValues() {
    _.forEach(this.chartOption.xAxis.data,
        item => this.chartOption.series[0].data =
          [...this.chartOption.series[0].data, _.filter(this.filteredData, fac =>
            fac.assignedAnalyst === item && fac.carStatus === 'New').length]);
    _.forEach(this.chartOption.xAxis.data,
      item => this.chartOption.series[1].data =
        [...this.chartOption.series[1].data, _.filter(this.filteredData, fac =>
          fac.assignedAnalyst === item && fac.carStatus === 'In Progress').length]);
    _.forEach(this.chartOption.xAxis.data,
      item => this.chartOption.series[2].data =
        [...this.chartOption.series[2].data, _.filter(this.filteredData, fac =>
          fac.assignedAnalyst === item && fac.carStatus === 'Canceled').length]);
    _.forEach(this.chartOption.xAxis.data,
      item => this.chartOption.series[3].data =
        [...this.chartOption.series[3].data, _.filter(this.filteredData, fac =>
          fac.assignedAnalyst === item && fac.carStatus === 'Superseded').length]);
    _.forEach(this.chartOption.xAxis.data,
      item => this.chartOption.series[4].data =
        [...this.chartOption.series[4].data, _.filter(this.filteredData, fac =>
          fac.assignedAnalyst === item && fac.carStatus === 'Completed').length]);
  }

  drawBarChart(divId: string, data, data2, data3, data4) {
    const dom: any = document.getElementById(divId);
    const myChart = echarts.init(dom);
    let option = null;
    option = {
      title: {
        text: 'Routing Time'
      },
      tooltip : {
        trigger: 'axis',
        axisPointer: {
          type: 'cross',
          label: {
            backgroundColor: '#6495ED'
          }
        },
        formatter: (params) => {
          const colorSpan = color => {
            return '<span style="display:inline-block;border-radius:10px;width:9px;height:9px;background-color:' + color + '"></span>';
          };
          let rez = params[0].axisValue + '</br>';
          // console.log(params); //quite useful for debug
          params.forEach(item => {
            // console.log(item); //quite useful for debug
            const xx = colorSpan(item.color) + ' ' + item.seriesName + ': ' + item.data + 's' + '</br>';
            rez += xx;
          });
          return rez;
        },
      },
      legend: {
        data: ['Max Time', 'Average Time', 'Min Time']
      },
      dataZoom: [{
        type: 'inside'
      }, {
        type: 'slider'
      }],
      toolbox: {
        show: true,
        orient: 'horizontal',
        left: 'right',
        top: 'center',
        itemSize: 25,
        itemGap: 15,
        feature: {
          saveAsImage: {show: true, title: 'Save'},
        }
      },
      grid: {
        bottom: 60
      },
      xAxis : [
        {
          type : 'category',
          boundaryGap : false,
          data : data,
        }
      ],
      yAxis: [{
        type: 'value',
        axisLabel: {
          formatter: '{value} s'
        }
      }],
      series : [
        {
          name: 'Max Time',
          type: 'line',
          color: '#4682B4',
          areaStyle: {color: '#4682B4'},
          label: {
            normal: {
              show: true
              //     position: 'top'
            }
          },
          data: data2,
        },
        {
          name: 'Average Time',
          type: 'line',
          color: '#008080',
          areaStyle: {color: '#008080' },
          data: data3,
        },
        {
          name: 'Min Time',
          type: 'line',
          color: '#FFD700',
          areaStyle: {color: '#FFD700'},
          zlevel: 2,
          data: data4,
        }
      ]
    };
    if (option && typeof option === 'object') {
      myChart.setOption(option, true);
    }
  }

  setFilter(col: string, $event: {}) {
    this.mockData = _.filter(this.mockDataCache, (e) => $event ? e[col] === $event : true);
  }
}
