import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import {Data} from '../../../core/model/data';
import {Select, Store} from '@ngxs/store';
import {WorkspaceState} from '../../../workspace/store/states';
import {WsApi} from '../../../workspace/services/api/workspace.api';
import * as _ from 'lodash';
import {DashboardState, GeneralConfigState} from '../../../core/store/states';
import * as workspaceActions from '../../../workspace/store/actions/workspace.actions';
import {DashboardApi} from "../../../core/service/api/dashboard.api";

@Component({
  selector: 'app-fac-chart-widget',
  templateUrl: './fac-chart-widget.component.html',
  styleUrls: ['./fac-chart-widget.component.scss']
})
export class FacChartWidgetComponent implements OnInit {
  @Output('delete') delete: any = new EventEmitter<any>();
  @Output('duplicate') duplicate: any = new EventEmitter<any>();
  @Output('changeName') changeName: any = new EventEmitter<any>();

  updateVisibility = false;

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

  chartOption: any;
  myChart: any;

  @ViewChild('chartA') chart;

  @Input()
  itemName = 'Car Widget';
  @Input()
  dashboard: any;
  @Input()
  itemWidget: any;
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

  data: any[];
  filteredData: any[];

  analystList: any[] = [];
  assignedAnalyst: any[] = [];
  selectedAnalyst = [];

  dateTo: any;
  dateFrom: any;

  constructor(private nzDropdownService: NzDropdownService, private store: Store,
              private cdRef: ChangeDetectorRef, private dashboardAPI: DashboardApi,
              private wsApi: WsApi) {
    this.uwyUnits = _.uniqBy(this.mockData, 'UNDERWRITINGUNITCODE');
  }

  ngOnInit() {
    this.chartOption = {
      legend: {
        data: ['New', 'In Progress', 'Cancelled', 'Superseded', 'Completed', 'Priced'],
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
      style: {

      },
      series: [],
      color: ['#F8E71C', '#F5A623', '#E70010', '#DDDDDD', '#7BBE31', 'rgb(0, 118, 66)']
    };
    console.log('loader', this.chartOption);
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

/*  onChartInit(instance) {
    this.myChart = instance;
    this.dashboardAPI.getWidgetCarStatusCount().subscribe((data: any) => {
      this.assignedAnalyst = _.map(data.assignedAnalyst, item => item === '' ? 'Unassigned' : item);
      this.analystList = [...this.assignedAnalyst, 'ALL'];

      let newData = [];
      let inProgressData = [];
      let cancelledData = [];
      let supersededData = [];
      let completedData = [];
      let pricedData = [];

      _.forEach(data.content, item => {
        newData = [...newData, _.get(item, 'new', 0)];
        inProgressData = [...inProgressData, _.get(item, 'inProgress', 0)];
        cancelledData = [...cancelledData, _.get(item, 'cancelled', 0)];
        supersededData = [...supersededData, _.get(item, 'superseded', 0)];
        completedData = [...completedData, _.get(item, 'completed', 0)];
        pricedData = [...pricedData, _.get(item, 'priced', 0)];
      });

      this.setValues({newData, inProgressData, cancelledData, supersededData, completedData, pricedData});
    })
  }*/

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
      this.analystList = [..._.uniq(data.content.map(item => item.assignedAnalyst))];
      this.assignedAnalyst = [..._.map(_.uniq(data.content.map(item => item.assignedAnalyst)), (item: string) => _.isEmpty(_.trim(item)) ? ({label: 'Unassigned', value: item}) : ({label: item, value: item}))];
      this.detectChanges();
      this.myChart = instance;
      this.setValues();
    });
  }

  selectTab(index) {
    this.tabIndex = index;
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

  filterDataByUser() {
    this.setValues();
  }

/*  setValues(chartData) {
    this.myChart.setOption({
      xAxis: {
        data: this.assignedAnalyst
      },
      series: [
        {
          name: 'New',
          data: chartData.newData,
          type: 'bar',
          stack: 'one',
          itemStyle: this.itemStyle,
        },
        {
          name: 'In Progress',
          data: chartData.inProgressData,
          type: 'bar',
          stack: 'one',
          itemStyle: this.itemStyle,
        },
        {
          name: 'Cancelled',
          data: chartData.cancelledData,
          type: 'bar',
          stack: 'one',
          itemStyle: this.itemStyle,
        },
        {
          name: 'Superseded',
          data: chartData.supersededData,
          type: 'bar',
          stack: 'one',
          itemStyle: this.itemStyle,
        },
        {
          name: 'Completed',
          data: chartData.completedData,
          type: 'bar',
          stack: 'one',
          itemStyle: this.itemStyle,
        },
        {
          name: 'Priced',
          data: chartData.pricedData,
          type: 'bar',
          stack: 'one',
          itemStyle: this.itemStyle,
        }
      ],
    });
  }*/

  setValues() {
    let newData = [];
    let inProgressData = [];
    let cancelledData = [];
    let supersededData = [];
    let completedData = [];
    let pricedData = [];
    _.forEach(this.analystList,
        item => newData = [...newData, _.filter(this.filteredData, fac =>
            fac.assignedAnalyst == item && fac.carStatus === 'New').length]);
    _.forEach(this.analystList,
        item => inProgressData = [...inProgressData, _.filter(this.filteredData, fac =>
            fac.assignedAnalyst == item && fac.carStatus === 'In Progress').length]);
    _.forEach(this.analystList,
        item => cancelledData = [...cancelledData, _.filter(this.filteredData, fac =>
            fac.assignedAnalyst == item && fac.carStatus === 'Cancelled').length]);
    _.forEach(this.analystList,
        item => supersededData = [...supersededData, _.filter(this.filteredData, fac =>
            fac.assignedAnalyst == item && fac.carStatus === 'Superseded').length]);
    _.forEach(this.analystList,
        item => completedData = [...completedData, _.filter(this.filteredData, fac =>
            fac.assignedAnalyst == item && fac.carStatus === 'Completed').length]);
    _.forEach(this.analystList,
        item => pricedData = [...pricedData, _.filter(this.filteredData, fac =>
            fac.assignedAnalyst == item && fac.carStatus === 'Priced').length]);
    this.myChart.setOption({
      xAxis: {
        data: _.map(this.analystList, item => _.isEmpty(_.trim(item)) ? 'Unassigned' : item)
      },
      series: [
        {
          name: 'New',
          data: newData,
          type: 'bar',
          stack: 'one',
          itemStyle: this.itemStyle,
        },
        {
          name: 'In Progress',
          data: inProgressData,
          type: 'bar',
          stack: 'one',
          itemStyle: this.itemStyle,
        },
        {
          name: 'Cancelled',
          data: cancelledData,
          type: 'bar',
          stack: 'one',
          itemStyle: this.itemStyle,
        },
        {
          name: 'Superseded',
          data: supersededData,
          type: 'bar',
          stack: 'one',
          itemStyle: this.itemStyle,
        },
        {
          name: 'Completed',
          data: completedData,
          type: 'bar',
          stack: 'one',
          itemStyle: this.itemStyle,
        },
        {
          name: 'Priced',
          data: pricedData,
          type: 'bar',
          stack: 'one',
          itemStyle: this.itemStyle,
        }
      ],
    });
  }

  setFilter(col: string, $event: {}) {
    this.mockData = _.filter(this.mockDataCache, (e) => $event ? e[col] === $event : true);
  }
}
