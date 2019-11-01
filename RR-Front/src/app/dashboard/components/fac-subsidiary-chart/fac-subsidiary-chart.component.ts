import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from "ng-zorro-antd";
import {Data} from '../../../core/model/data';
import {Select, Store} from '@ngxs/store';
import {WorkspaceState} from '../../../workspace/store/states';
import {WsApi} from '../../../workspace/services/workspace.api';
import * as _ from 'lodash';
import {GeneralConfigState} from "../../../core/store/states";
import * as workspaceActions from "../../../workspace/store/actions/workspace.actions";

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
          icon: `image://https://image.flaticon.com/icons/svg/263/263135.svg`,
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
  seriesDataPercentage: any;
  seriesDataNumber: any;
  activeSwitch = false;

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

  subsidiaryList: any;
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
      this.chartOption.xAxis.data = _.uniq(value.map(item => item.uwanalysisContractSubsidiary));
      this.chartOption.legend.data = _.uniq(value.map(item => item.assignedAnalyst));
      this.subsidiaryList = [..._.uniq(value.map(item => item.uwanalysisContractSubsidiary)), 'ALL'];
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
    let series = [];
    let alternateSeries = [];
    const {data} = this.chartOption.legend;
    _.forEach(data, item => {
      let trad = [];
      let part = [];
      _.forEach(this.subsidiaryList, subsItem => {
        const percentage = (_.filter(this.data, dt => dt.assignedAnalyst === item && dt.uwanalysisContractSubsidiary === subsItem).length /
          _.filter(this.data, dt => dt.uwanalysisContractSubsidiary === subsItem).length) * 100;
        trad = [...trad, _.filter(this.data, dt =>
          dt.assignedAnalyst === item && dt.uwanalysisContractSubsidiary === subsItem).length];
        part = [...part, percentage.toFixed(2)];
      });
      series = [...series, {name: item, data: trad, type: 'bar', stack: 'one', itemStyle: this.itemStyle}];
      alternateSeries = [...alternateSeries, {name: item, data: part, type: 'bar', stack: 'one', itemStyle: this.itemStyle}];
    });
    this.seriesDataPercentage = alternateSeries;
    this.seriesDataNumber = series;
    this.chartOption.series = series;
  }

  onChartInit(instance) {
    this.myChart = instance;
  }

  switchData() {
    if (!this.activeSwitch) {
      this.myChart.setOption({
        tooltip: {
          formatter: (params, ticket) => {
            const numberContract = _.filter(this.data,
                item => item.assignedAnalyst === params.seriesName && item.uwanalysisContractSubsidiary === params.name).length;
            return `<span style="display: flex; align-items: center">Assigned Analyst: <span
                style="display:flex; border-radius: 20px; width:10px; height: 10px; margin: 0 2px; background: ${params.color}">
                </span>${params.seriesName}</span>
                Percentage: ${params.data} %
                </br>Number: ${numberContract}`;
          }
        },
        series: this.seriesDataPercentage
      });
    } else {
      this.myChart.setOption({
        tooltip: {},
        series: this.seriesDataNumber
      });
    }
    this.activeSwitch = !this.activeSwitch;
  }

  setFilter(col: string, $event: {}) {
    this.mockData = _.filter(this.mockDataCache, (e) => $event ? e[col] === $event : true);
  }

}
