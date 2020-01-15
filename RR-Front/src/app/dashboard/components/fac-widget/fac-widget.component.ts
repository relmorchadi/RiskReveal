import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, TemplateRef} from '@angular/core';
import {GeneralConfigState} from '../../../core/store/states';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import * as _ from 'lodash';
import {Select, Store} from '@ngxs/store';
import {Data} from '../../../core/model/data';
import * as moment from 'moment';
import {dashData} from '../../../shared/data/dashboard-data';
import {OpenWS} from '../../../workspace/store/actions';
import * as workspaceActions from '../../../workspace/store/actions/workspace.actions';
import {WsApi} from '../../../workspace/services/api/workspace.api';
import {WorkspaceState} from '../../../workspace/store/states';

@Component({
  selector: 'app-fac-widget',
  templateUrl: './fac-widget.component.html',
  styleUrls: ['./fac-widget.component.scss']
})
export class FacWidgetComponent implements OnInit {
  @Output('delete') delete: any = new EventEmitter<any>();
  @Output('duplicate') duplicate: any = new EventEmitter<any>();
  @Output('changeName') changeName: any = new EventEmitter<any>();

  private dropdown: NzDropdownContextComponent;
  uwyUnits;
  cedants = Data.cedant;

  filterNew = false;
  filterCurrent = false;
  filterArchive = false;

  @Input()
  itemName = 'Car Widget';
  @Input()
  dashboard: any;
  @Input()
  identifier: number;
  @Input()
  widgetIndex: number;
  @Input()
  type: any;
  @Input()
  data: any;
  @Input()
  tableHeight: any;
  newDashboard: any;
  editName = false;

  cols = [
    {field: 'favorite', header: '', width: '20px', display: true, sorted: false, filtered: false, type: 'favStatus'},
    {field: 'carRequestId', header: 'CAR ID', width: '60px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'contractID', header: 'Contract Name', width: '70px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'projectId', header: 'Project ID', width: '60px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwanalysisContractInsured', header: 'Insured', width: '80px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwYear', header: 'UW Year', width: '50px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'contractName', header: 'Contract ID', width: '60px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwAnalysis', header: 'UW Analysis', width: '80px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'subsidiary', header: 'Subsidiary', width: '80px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'sector', header: 'Sector', width: '60px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'businessType', header: 'Business Type', width: '70px', display: true, sorted: true, filtered: true, type: 'text'},
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

  filters = {};

  newSort = {};
  globalSort = {};

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
    this.store.dispatch(new workspaceActions.OpenWS({
      wsId: event.contractName,
      uwYear: event.uwYear,
      route: 'projects',
      type: 'FAC'
    }))
  }

  filterAssign() {
    return _.filter(this.data, item => item.assignedAnalyst === 'Nathalie Dulac');
  }

  valueFavChange(event) {

  }

  sortChange(event) {
    console.log(event);
    this.type === 'newCar' ? this.newSort = event : this.globalSort = event;
  }

  filterData($event) {
    if ($event[_.keys($event)[0]]) {
      this.filters =  _.merge({}, this.filters, $event) ;
    } else {
      this.filters =  _.omit(this.filters, _.keys($event)[0]);
    }
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

  setFilter(col: string, $event: {}) {
    this.mockData = _.filter(this.mockDataCache, (e) => $event ? e[col] === $event : true);
  }
}
