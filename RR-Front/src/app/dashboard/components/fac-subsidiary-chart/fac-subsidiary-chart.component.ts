import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from "ng-zorro-antd";
import {Data} from "../../../core/model/data";
import {Select, Store} from "@ngxs/store";
import {WorkspaceState} from "../../../workspace/store/states";
import {WsApi} from "../../../workspace/services/api/workspace.api";
import * as _ from 'lodash';
import {DashboardState, GeneralConfigState} from "../../../core/store/states";
import * as workspaceActions from "../../../workspace/store/actions/workspace.actions";
import {DashboardApi} from "../../../core/service/api/dashboard.api";

@Component({
  selector: 'app-fac-subsidiary-chart',
  templateUrl: './fac-subsidiary-chart.component.html',
  styleUrls: ['./fac-subsidiary-chart.component.scss']
})
export class FacSubsidiaryChartComponent implements OnInit {
  @Output('delete') delete: any = new EventEmitter<any>();
  @Output('duplicate') duplicate: any = new EventEmitter<any>();
  @Output('changeName') changeName: any = new EventEmitter<any>();

  myChart: any;

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

  initOps = {
    height: '400px',
    width: '1600px'
  };

  chartOption: any = {
    legend: {
      data: [],
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
        myCostumeTool: {
          show: true,
          title: 'rate',
          // icon: 'image://http://echarts.baidu.com/images/favicon.png',
          onclick: () => {
            this.switchData();
          }
        }
      },
      itemSize: 20
    },
    xAxis: {
      type: 'category',
      data: []
    },
    yAxis: {
      type: 'value'
    },
    visualMap: {
      type: 'continuous',
      dimension: 1,
      text: ['High', 'Low'],
      itemHeight: 200,
      calculable: true,
      min: 0,
      max: 10,
      top: 60,
      left: 10,
      inRange: {
        colorLightness: [0.4, 0.8]
      },
      outOfRange: {
        color: '#bbb'
      },
      controller: {
        inRange: {
          color: '#2f4554'
        }
      }
    },
    series: [],
    color: ['#F8E71C', '#F5A623', '#E70010', '#DDDDDD', '#7BBE31']
  };
  alternateSeriesData: any;

  @ViewChild('chartS') chart;

  @Input()
  itemName = 'Car Widget';
  @Input()
  dashboard: any;
  @Input()
  identifier: number;
  @Input()
  widgetIndex: number;
  @Input()
  itemWidget: any;
  newDashboard: any;
  editName = false;

  mockData = [];
  private defaultCountry: string;
  private defaultUwUnit: string;
  Countries = Data.coutryAlt;
  private mockDataCache;

  data: any[];
  filteredData: any[];

  subsidiaryList: any;
  assignedAnalystList: any;
  selectedSubsidiary = [];

  dateTo: any;
  dateFrom: any;

  constructor(private nzDropdownService: NzDropdownService, private store: Store,
              private cdRef: ChangeDetectorRef, private dashboardAPI: DashboardApi,
              private wsApi: WsApi) {
    this.uwyUnits = _.uniqBy(this.mockData, 'UNDERWRITINGUNITCODE');
  }

  ngOnInit() {
    this.newDashboard = this.dashboard;
    this.store.select(GeneralConfigState.getGeneralConfigAttr('contractOfInterest', {
      country: '',
      uwUnit: ''
    })).subscribe(coi => {
      this.defaultCountry = coi.defaultCountry;
      this.defaultUwUnit = coi.defaultUwUnit;
      this.detectChanges();
    });
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
      this.changeName.emit({item: this.itemWidget, newName: keyboardMap.target.value});
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

  onChartInit(instance) {
    const dataParams = {
      carStatus: 'Chart',
      entity: 1,
      pageNumber: 1,
      pageSize: 1000,
      selectionList: '',
      sortSelectedAction: '',
      sortSelectedFirst: false,
      userCode: "DEV",
      userDashboardWidgetId: 0
    };

    this.dashboardAPI.getFacDashboardResources(dataParams).subscribe( data => {
      this.data = data.content;
      this.filteredData = data.content;
      this.subsidiaryList = [..._.map(_.uniq(data.content.map(item => item.uwAnalysis)), (item: string) => _.isEmpty(_.trim(item)) ? ({label: 'Unassigned', value: item}) : ({label: item, value: item}))];
      this.selectedSubsidiary = [..._.uniq(data.content.map(item => item.uwAnalysis))];
      this.detectChanges();
      this.myChart = instance;
      this.setValues();
    });
  }

/*
  onChartInit(instance) {
    this.myChart = instance;
    this.dashboardAPI.getWidgetAssignedCountByUwAnalysis().subscribe((data: any) => {
      this.subsidiaryList = [..._.map(data.uwAnalysis, item => ({label: item, value: item}))];
      this.selectedSubsidiary = [...data.uwAnalysisList];
      this.assignedAnalystList = [...data.assignedAnalystList];

      let series = [];
      let alternateSeries = [];
      _.forEach(this.assignedAnalystList, assigned => {
        let trad = [];
        let part = [];
        _.forEach(data.content, item => {
          trad = [...trad, _.get(item, `${assigned}`, 0)];
          part = [...part, _.get(item, `${assigned}`, 0) / _.get(item, 'count', 1)];
        });
        series = [...series, {name: assigned, data: trad, type: 'bar', stack: 'one', itemStyle: this.itemStyle}];
        alternateSeries = [...alternateSeries, {name: assigned, data: part, type: 'bar', stack: 'one', itemStyle: this.itemStyle}];
      });
      this.alternateSeriesData = alternateSeries;
      this.setValues(series);
    })
  }*/

  setValues() {
    const legendData = _.uniq(this.filteredData.map(item => item.assignedAnalyst)) || [];
    let series = [];
    let alternateSeries = [];
    _.forEach(legendData, item => {
      let trad = [];
      let part = [];
      _.forEach(this.selectedSubsidiary, subsItem => {
        trad = [...trad, _.filter(this.data, dt =>
            dt.assignedAnalyst === item && dt.uwAnalysis === subsItem).length];
        part = [...part, (_.filter(this.data, dt => dt.assignedAnalyst === item && dt.uwAnalysis === subsItem).length /
            _.filter(this.data, dt => dt.uwAnalysis === subsItem).length) * 100];
      });
      series = [...series, {name: _.isEmpty(_.trim(item)) ? 'Unassigned' :item, data: trad, type: 'bar', stack: 'one', itemStyle: this.itemStyle}];
      alternateSeries = [...alternateSeries, {name: _.isEmpty(_.trim(item)) ? 'Unassigned' : item, data: part, type: 'bar', stack: 'one', itemStyle: this.itemStyle}];
    });
    this.alternateSeriesData = alternateSeries;

    this.myChart.setOption({
      xAxis: {
        data: this.selectedSubsidiary
      },
      legend: {
        data: _.map(legendData, item => _.isEmpty(_.trim(item)) ? 'Unassigned' : item)
      },
      series: series
    });
  }

/*  setValues(series) {
    this.myChart.setOption({
      xAxis: {
        data: this.selectedSubsidiary
      },
      legend: {
        data: this.assignedAnalystList
      },
      series: series
    });
  }*/

  filterDataBySubsidiary() {
    this.setValues();
  }

  switchData() {
    // console.log('switch data');
    const switchedSeries = this.chartOption.series;
    this.myChart.setOption({
      series: this.alternateSeriesData
    });
    this.alternateSeriesData = switchedSeries;
  }

  setFilter(col: string, $event: {}) {
    this.mockData = _.filter(this.mockDataCache, (e) => $event ? e[col] === $event : true);
  }

}
