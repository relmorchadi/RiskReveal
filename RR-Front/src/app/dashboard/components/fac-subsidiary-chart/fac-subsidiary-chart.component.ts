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
    height: '360px'
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
          icon: 'path://M432.45,595.444c0,2.177-4.661,6.82-11.305,6.82c-6.475,0-11.306-4.567-11.306-6.82s4.852-6.812,11.306-6.812C427.841,588.632,432.452,593.191,432.45,595.444L432.45,595.444z M421.155,589.876c-3.009,0-5.448,2.495-5.448,5.572s2.439,5.572,5.448,5.572c3.01,0,5.449-2.495,5.449-5.572C426.604,592.371,424.165,589.876,421.155,589.876L421.155,589.876z M421.146,591.891c-1.916,0-3.47,1.589-3.47,3.549c0,1.959,1.554,3.548,3.47,3.548s3.469-1.589,3.469-3.548C424.614,593.479,423.062,591.891,421.146,591.891L421.146,591.891zM421.146,591.891',
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
    series: [],
    color: ['#F8E71C', '#F5A623', '#E70010', '#DDDDDD', '#7BBE31']
  };
  switch = false;
  alternateSeriesData: any;
  switchedSeriesData: any;

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

    console.log('chartInit');

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
        const percentage = _.filter(this.data, dt => dt.assignedAnalyst === item && dt.uwAnalysis === subsItem).length /
            _.filter(this.data, dt => dt.uwAnalysis === subsItem).length * 100;
        part = [...part, Math.floor(percentage * 100) / 100];
      });
      series = [...series, {name: _.isEmpty(_.trim(item)) ? 'Unassigned' :item, data: trad, type: 'bar', stack: 'one', itemStyle: this.itemStyle}];
      alternateSeries = [...alternateSeries, {name: _.isEmpty(_.trim(item)) ? 'Unassigned' : item, data: part, type: 'bar', stack: 'one', itemStyle: this.itemStyle}];
    });
    this.alternateSeriesData = alternateSeries;
    this.switchedSeriesData = series;

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
    this.switch = !this.switch;
    this.myChart.setOption({
      series: this.switch ? this.alternateSeriesData : this.switchedSeriesData,
      tooltip: this.switch ? {
        formatter: (params) => {
          return params.name + '<br/>' + `<span style="background-color: ${params.color}; height: 10px; width: 10px; display: inline-flex; border-radius: 20px;"></span> 
            ${params.seriesName}: ${params.data} %`;
        }
      } : {
        formatter: (params) => {
          return params.name + '<br/>' + `<span style="background-color: ${params.color}; height: 10px; width: 10px; display: inline-flex; border-radius: 20px;"></span> 
            ${params.seriesName}: ${params.data}`;
        }}
    });
  }

  setFilter(col: string, $event: {}) {
    this.mockData = _.filter(this.mockDataCache, (e) => $event ? e[col] === $event : true);
  }

}
