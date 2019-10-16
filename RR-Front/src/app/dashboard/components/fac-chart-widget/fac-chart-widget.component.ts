import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, TemplateRef} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import {Data} from '../../../core/model/data';
import {Select, Store} from '@ngxs/store';
import {WorkspaceState} from '../../../workspace/store/states';
import {WsApi} from '../../../workspace/services/workspace.api';
import {dashData} from '../../../shared/data/dashboard-data';
import * as _ from 'lodash';
import * as moment from 'moment';
import {GeneralConfigState} from '../../../core/store/states';
import * as workspaceActions from '../../../workspace/store/actions/workspace.actions';
import { EChartOption } from 'echarts';

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

  filterNew = false;
  filterCurrent = false;
  filterArchive = false;

  xAxisData = [];
  data1 = [];
  data2 = [];

  /*  filterAssignedNew = false;
    filterAssignedCurrent = false;
    filterAssignedArchive = false;*/

  chartOption: EChartOption = {};

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

  cols = [
    {field: 'favorite', header: '', width: '20px', display: true, sorted: false, filtered: false, type: 'favStatus'},
    {field: 'id', header: 'CAR ID', width: '60px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'contractName', header: 'Contract Name', width: '70px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwAnalysisProjectId', header: 'Project ID', width: '60px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwanalysisContractInsured', header: 'Insured', width: '80px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwAnalysisContractDate', header: 'UW Year', width: '50px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwanalysisContractContractId', header: 'Contract ID', width: '60px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwanalysisContractLabel', header: 'UW Analysis', width: '80px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwanalysisContractSubsidiary', header: 'Subsidiary', width: '80px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwanalysisContractSector', header: 'Sector', width: '60px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwanalysisContractBusinessType', header: 'Business Type', width: '70px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'assignedAnalyst', header: 'Assigned Analyst', width: '80px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'carStatus', header: 'CAR Status', width: '50px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'requestCreationDate', header: 'Created At', width: '80px', display: true, sorted: true, filtered: true, type: 'date'},
    {field: 'lastUpdateDate', header: 'Updated At', width: '80px', display: true, sorted: true, filtered: true, type: 'date'},
  ];
  mockData = [];
  private defaultCountry: string;
  private defaultUwUnit: string;
  Countries = Data.coutryAlt;
  private mockDataCache;
  tabIndex = 1;

  @Select(WorkspaceState.getFacData) facData$;
  data: any[];

  constructor(private nzDropdownService: NzDropdownService, private store: Store,
              private cdRef: ChangeDetectorRef,
              private wsApi: WsApi) {
    this.mockData = dashData.mockData;

    this.mockDataCache = this.mockData = _.map(this.mockData,
      (e, i) => ({
        ...e,
        assignedTo: 'Rim BENABBES',
        lastModifiedBy: 'Rim BENABBES',
        lastModifiedDate: moment(Date.now()).format('MM/DD/YYYY'),
        country: this.Countries[i].COUNTRYSHORTNAME,
        visible: true,
        cedant: this.cedants[i].CLIENTSHORTNAME
      }));

    this.uwyUnits = _.uniqBy(this.mockData, 'UNDERWRITINGUNITCODE');
  }

  ngOnInit() {
    this.newDashboard = this.dashboard;
    this.facData$.subscribe(value => {
      this.data = value;
    });
    this.store.select(GeneralConfigState.getGeneralConfigAttr('contractOfInterest', {
      country: '',
      uwUnit: ''
    })).subscribe(coi => {
      this.defaultCountry = coi.defaultCountry;
      this.defaultUwUnit = coi.defaultUwUnit;
      this.detectChanges();
    });
    this.setFilters();

    for (let i = 0; i < 100; i++) {
      this.xAxisData.push('category' + i);
      this.data1.push((Math.sin(i / 5) * (i / 5 - 10) + i / 6) * 5);
      this.data2.push((Math.cos(i / 5) * (i / 5 - 10) + i / 6) * 5);
    }

    this.chartOption = {
      legend: {
        data: ['bar', 'bar2'],
        align: 'left'
      },
      tooltip: {},
      xAxis: {
        data: this.xAxisData,
        silent: false,
        splitLine: {
          show: false
        }
      },
      yAxis: {
      },
      series: [{
        name: 'bar',
        type: 'bar',
        data: this.data1,
        animationDelay: (idx) => {
          return idx * 10;
        }
      }, {
        name: 'bar2',
        type: 'bar',
        data: this.data2,
        animationDelay: (idx) => {
          return idx * 10 + 100;
        }
      }],
      animationEasing: 'elasticOut',
      animationDelayUpdate: (idx) => {
        return idx * 5;
      }
    };
  }

  setFilters() {
    if (this.itemName === 'New CARs') {
      this.filterNew = true;
    } else if (this.itemName === 'In Progress CARs') {
      this.filterCurrent = true;
    } else if (this.itemName === 'Archived CARs') {
      this.filterArchive = true;
    }
  }

  selectTab(index) {
    this.tabIndex = index;
  }

  applyFilters(data) {
    let filteredData = [...(data || [])];
    if (this.filterCurrent) {
      filteredData = _.filter(filteredData, item => item.carStatus === 'In Progress');
    } else if (this.filterNew) {
      filteredData = _.filter(filteredData, item => item.carStatus === 'New');
    } else if (this.filterArchive) {
      filteredData = _.filter(filteredData, item => item.carStatus === 'SuperSeeded' || item.carStatus === 'Completed' || item.carStatus === 'Canceled');
    }
    return filteredData;
  }

  openFacItem(event) {
    console.log(event);
    this.store.dispatch(new workspaceActions.OpenFacWS({wsId: event.uwanalysisContractFacNumber,
      uwYear: event.uwAnalysisContractDate, route: 'Project', type: 'fac', item: event}));
    this.store.dispatch(new workspaceActions.LoadProjectForWs({wsId: event.uwanalysisContractFacNumber,
      uwYear: event.uwAnalysisContractDate}));
  }

  filterAssign() {
    return _.filter(this.data, item => item.assignedAnalyst === 'Amina Cheref');
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
      /*  const newItem = this.newDashboard.items.filter(ds => ds.id === id);
        const copy = Object.assign({}, newItem[0], {
          name: this.itemName,
          id: this.newDashboard.items.length + 1
        });
        this.newDashboard.items.push(copy);
        newItem[0].selected = false;
        this.editName = false;*/
    }
  }

  activeEdit() {
    this.editName = true;
  }

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
  }

  setFilter(col: string, $event: {}) {
    this.mockData = _.filter(this.mockDataCache, (e) => $event ? e[col] === $event : true);
  }
}
