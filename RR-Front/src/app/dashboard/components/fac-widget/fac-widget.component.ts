import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, TemplateRef} from '@angular/core';
import {GeneralConfigState} from '../../../core/store/states';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import * as _ from 'lodash';
import {Store} from '@ngxs/store';
import {Data} from '../../../core/model/data';
import * as moment from 'moment';
import {dashData} from '../../../shared/data/dashboard-data';
import {OpenWS} from "../../../workspace/store/actions";
import * as workspaceActions from '../../../workspace/store/actions/workspace.actions';
import {WsApi} from '../../../workspace/services/workspace.api';

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

  @Input()
  itemName = 'Car Widget';
  @Input()
  dashboard: any;
  @Input()
  identifier: number;
  newDashboard: any;
  editName = false;

  cols = [
    {field: 'id', header: 'CAR ID', width: '40px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwanalysisContractFacNumber', header: 'Project ID', width: '60px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwanalysisContractInsured', header: 'Insured', width: '80px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwanalysisContractYear', header: 'UW Year', width: '40px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwanalysisContractContractId', header: 'Contract ID', width: '60px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwanalysisContractLabel', header: 'UW Analysis', width: '80px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'contractName', header: 'Contract Name', width: '70px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwanalysisContractSubsidiary', header: 'Subsidiary', width: '50px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwanalysisContractSector', header: 'Sector', width: '50px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwanalysisContractBusinessType', header: 'Business Type', width: '70px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'reauestedByFirstName', header: 'Assigned Analyst', width: '70px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'carStatus', header: 'CAR Status', width: '50px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'requestCreationDate', header: 'Created At', width: '80px', display: true, sorted: true, filtered: true, type: 'date'},
    {field: 'lastUpdateDate', header: 'Updated At', width: '80px', display: true, sorted: true, filtered: true, type: 'date'},
    /*{field: 'rrStatus', header: '', width: '20px', display: true, sorted: false, type: 'icon'},
    {field: 'plt', header: '', width: '20px', display: true, sorted: false, type: 'icon'},
    {field: 'publishedToPricing', header: '', width: '20px', display: true, sorted: false, type: 'icon'},
    {field: 'usedInPricing', header: '', width: '20px', display: true, sorted: false, type: 'icon'},
    {field: 'publishedToAccumulation', header: '', width: '20px', display: true, sorted: false, type: 'text'}*/
  ];
  mockData = [];
  private defaultCountry: string;
  private defaultUwUnit: string;
  Countries = Data.coutryAlt;
  private mockDataCache;

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
    this.wsApi.searchFacWidget().subscribe((ws: any) => {
      this.data = ws.content;
      console.log(this.data);
    });
    this.store.select(GeneralConfigState.getGeneralConfigAttr('contractOfInterest', {
      country: '',
      uwUnit: ''
    })).subscribe(coi => {
      this.defaultCountry = coi.defaultCountry;
      this.defaultUwUnit = coi.defaultUwUnit;
      this.detectChanges();
    });

    /*this.api().subscribe( data => {
        this.mockData= data;
        this.detectChanges();
      })*/

  }

  getDataFacWidget() {

  }

  openFacItem(event) {
    this.store.dispatch(new workspaceActions.OpenFacWS({wsId: event.uwanalysisContractFacNumber,
      uwYear: event.uwanalysisContractYear, route: 'Project', type: 'fac'}));
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
