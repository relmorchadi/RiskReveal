import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import {Data} from '../../../core/model/data';
import {Select, Store} from '@ngxs/store';
import {WorkspaceState} from '../../../workspace/store/states';
import {WsApi} from '../../../workspace/services/api/workspace.api';
import * as _ from 'lodash';
import {DashboardState, GeneralConfigState} from '../../../core/store/states';
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

  @Select(DashboardState.getFacData) facData$;
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
    this.facData$.subscribe(rValue => {
      const newD = _.get(rValue, 'new', []);
      const inPD = _.get(rValue, 'inProgress', []);
      const archiveD = _.get(rValue, 'archived', []);
      const value = [...newD, ...inPD, ...archiveD];
      this.data = value;
      this.filteredData = value;
      this.analystList = [..._.uniq(value.map(item => item.assignedAnalyst)), 'ALL'];
      this.detectChanges();
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

  onChartInit(instance) {
    setTimeout(() => {
      //instance._api. offsetHeight = 450;
      this.myChart = instance;
      this.setValues();
    }, 1000);
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
    const dataChart = _.uniq(this.filteredData.map(item => item.assignedAnalyst)) || [];
    let newData = [];
    let inProgressData = [];
    let cancelledData = [];
    let supersededData = [];
    let completedData = [];
    let pricedData = [];
    _.forEach(dataChart,
        item => newData = [...newData, _.filter(this.filteredData, fac =>
                fac.assignedAnalyst == item && fac.carStatus === 'New').length]);
    _.forEach(dataChart,
        item => inProgressData = [...inProgressData, _.filter(this.filteredData, fac =>
                fac.assignedAnalyst == item && fac.carStatus === 'In Progress').length]);
    _.forEach(dataChart,
        item => cancelledData = [...cancelledData, _.filter(this.filteredData, fac =>
                fac.assignedAnalyst == item && fac.carStatus === 'Cancelled').length]);
    _.forEach(dataChart,
        item => supersededData = [...supersededData, _.filter(this.filteredData, fac =>
                fac.assignedAnalyst == item && fac.carStatus === 'Superseded').length]);
    _.forEach(dataChart,
        item => completedData = [...completedData, _.filter(this.filteredData, fac =>
                fac.assignedAnalyst == item && fac.carStatus === 'Completed').length]);
    _.forEach(dataChart,
        item => pricedData = [...pricedData, _.filter(this.filteredData, fac =>
                fac.assignedAnalyst == item && fac.carStatus === 'Priced').length]);
    this.myChart.setOption({
      xAxis: {
        data: dataChart
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
