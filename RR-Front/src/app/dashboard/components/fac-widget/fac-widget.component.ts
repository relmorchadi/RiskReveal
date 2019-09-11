import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, TemplateRef} from '@angular/core';
import {GeneralConfigState} from "../../../core/store/states";
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from "ng-zorro-antd";
import * as _ from 'lodash';
import {Store} from '@ngxs/store';
import {Data} from '../../../core/model/data';
import * as moment from 'moment';
import {dashData} from '../../../shared/data/dashboard-data';
import {OpenWS} from "../../../workspace/store/actions";
import * as workspaceActions from "../../../workspace/store/actions/workspace.actions";

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
  renewalPeriod = '1';
  uwyUnits;
  activeProject = '1';
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
    {field: 'carId', header: 'CAR ID', width: '40px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'projectId', header: 'Project ID', width: '60px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'insured', header: 'Insured', width: '80px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwYear', header: 'UW Year', width: '40px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'contractId', header: 'Contract ID', width: '60px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'uwAnalysis', header: 'UW Analysis', width: '80px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'contractName', header: 'Contract Name', width: '70px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'subsidiary', header: 'Subsidiary', width: '50px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'sector', header: 'Sector', width: '50px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'businessType', header: 'Business Type', width: '70px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'AssignedAnalyst', header: 'Assigned Analyst', width: '70px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'carStatus', header: 'CAR Status', width: '50px', display: true, sorted: true, filtered: true, type: 'text'},
    {field: 'createdAt', header: 'Created At', width: '80px', display: true, sorted: true, filtered: true, type: 'date'},
    {field: 'UpdatedAt', header: 'Updated At', width: '80px', display: true, sorted: true, filtered: true, type: 'date'},
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
  activeCountry: any;
  activeUwUnit: any;
  uwUnits: any;
  private mockDataCache;

  data = [
    {
      carId: '101',
      projectId: '00F0006',
      insured: 'Global Partners',
      uwYear: '2019',
      contractId: '02F091155',
      uwAnalysis: 'Submission Data',
      contractName: '',
      subsidiary: 'SCOR',
      sector: '',
      businessType: 'New Business',
      AssignedAnalyst: 'Juan SANTANA',
      carStatus: '',
      createdAt: moment(Date.now()).format('MM/DD/YYYY'),
      UpdateAt: '',
      selected: false,
    },
    {
      carId: '102',
      projectId: '00F0007',
      insured: 'Global Partners',
      uwYear: '2019',
      contractId: '02F091156',
      uwAnalysis: 'Submission Data',
      contractName: '',
      subsidiary: '',
      sector: '',
      businessType: 'New Business',
      AssignedAnalyst: 'Richard DEEM',
      carStatus: '',
      createdAt: moment(Date.now()).format('MM/DD/YYYY'),
      UpdateAt: '',
      selected: false,
    },
    {
      carId: '103',
      projectId: '00C0022',
      insured: 'AXA ASSURANCE',
      uwYear: '2019',
      contractId: '02F091155',
      uwAnalysis: 'Submission Data',
      contractName: '',
      subsidiary: '',
      sector: '',
      businessType: 'New Business',
      AssignedAnalyst: 'Richard DEEM',
      carStatus: '',
      createdAt: moment(Date.now()).format('MM/DD/YYYY'),
      UpdateAt: '',
      selected: false,
    },
    {
      carId: '104',
      projectId: '00C0023',
      insured: 'Global Partners',
      uwYear: '2018',
      contractId: '02F091155',
      uwAnalysis: 'Submission Data',
      contractName: '',
      subsidiary: '',
      sector: '',
      businessType: 'Renewal',
      AssignedAnalyst: 'Melanie ROBINSON',
      carStatus: '',
      createdAt: moment(Date.now()).format('MM/DD/YYYY'),
      UpdateAt: '',
      selected: false,
    },
    {
      carId: '105',
      projectId: '00C0024',
      insured: 'AXA ASSURANCE',
      uwYear: '2018',
      contractId: '02F091157',
      uwAnalysis: 'Submission Data',
      contractName: '',
      subsidiary: '',
      sector: '',
      businessType: 'Renewal',
      AssignedAnalyst: 'Melanie ROBINSON',
      carStatus: '',
      createdAt: moment(Date.now()).format('MM/DD/YYYY'),
      UpdateAt: '',
      selected: false,
    },
    {
      carId: '106',
      projectId: '00C0025',
      insured: 'AXA ASSURANCE',
      uwYear: '2018',
      contractId: '02F091154',
      uwAnalysis: 'Submission Data',
      contractName: '',
      subsidiary: '',
      sector: '',
      businessType: 'Renewal',
      AssignedAnalyst: 'Melanie ROBINSON',
      carStatus: '',
      createdAt: moment(Date.now()).format('MM/DD/YYYY'),
      UpdateAt: '',
      selected: false,
    },
  ];

  /*    {year: 2019, country: 'Italy', cedant: '383477 Lorem Ipsum Colores',
        treaty: '334675 Donec et nulla', pricingVersion: 'Final', owner: 'Nathalie Dulac', date: '18/12/23'
      },
      {year: 2019, country: 'Italy', cedant: '383477 Lorem Ipsum Colores',
        treaty: '334675 Donec et nulla', pricingVersion: 'Final', owner: 'Nathalie Dulac', date: '18/12/23'
      },
      {year: 2019, country: 'Italy', cedant: '383477 Lorem Ipsum Colores',
        treaty: '334675 Donec et nulla', pricingVersion: 'Final', owner: 'Nathalie Dulac', date: '18/12/23'
      },
      {year: 2019, country: 'Italy', cedant: '383477 Lorem Ipsum Colores',
        treaty: '334675 Donec et nulla', pricingVersion: 'Final', owner: 'Nathalie Dulac', date:'18/12/23'
      },
      {year: 2019, country: 'Italy', cedant: '383477 Lorem Ipsum Colores',
        treaty: '334675 Donec et nulla', pricingVersion: 'Final', owner: 'Nathalie Dulac', date: '18/12/23'
      },
      {year: 2019, country: 'Italy', cedant: '383477 Lorem Ipsum Colores',
        treaty: '334675 Donec et nulla', pricingVersion: 'Final', owner: 'Nathalie Dulac', date: '18/12/23'
      }*/

  constructor(private nzDropdownService: NzDropdownService, private store: Store, private cdRef: ChangeDetectorRef) {
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

    /*this.api().subscribe( data => {
        this.mockData= data;
        this.detectChanges();
      })*/

  }

  openFacItem(event) {
    this.store.dispatch(new workspaceActions.OpenWS({wsId: event.projectId, uwYear: event.uwYear, route: 'Project', type: 'fac'}));
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
