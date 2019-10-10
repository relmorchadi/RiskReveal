import {Component, OnInit} from '@angular/core';
import {Regions} from '../../../shared/data/region-peril';
import {WsApi} from '../../../workspace/services/workspace.api';
import {Router} from '@angular/router';
import {Select, Store} from '@ngxs/store';
import * as fromWs from '../../../workspace/store/actions';
import {WorkspaceState} from '../../../workspace/store/states';

@Component({
  selector: 'app-submission-page',
  templateUrl: './submission-page.component.html',
  styleUrls: ['./submission-page.component.scss']
})
export class SubmissionPageComponent implements OnInit {
  uwAnalysis = 'Submission Data';
  contractId = 'FA0051168';
  uwYear = '2019';
  insured = 'Global Partners';
  contractLabel = '';
  subsidiary = {
    option: [
      'SCOR',
      'SCOR ASIA PACIF.',
      'SCOR CANADA',
      'SCOR GERMANY',
      'SCOR RE US',
      'SCOR REASS.',
      'SCOR SWITZERLAND',
      'SCOR UK'],
    value: 'SCOR REASS.'
  };
  businessType = {
    option: ['New Business', 'Renewal'],
    value: 'New Business'
  };
  lob = {
    option: ['Property', 'Construction'],
    value: 'Property'
  };
  sector = '';
  raisedBy = {
    option: [
      'Guillaume POULET',
      'Juan SANTANA',
      'Maelle DANIEL',
      'Melanie ROBINSON',
      'Richard DEEM',
      'Soon Ling MACK'],
    value: 'Richard DEEM'
  };
  data: any;
  metaData: any;

  coverageTemplate = [
    {field: 'divisionNo', header: 'Division No', width: '40px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
    {field: 'principal', header: 'Is Principal', width: '120px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: true},
    {field: 'lob', header: 'LOB', width: '120px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: true},
    {field: 'coverage', header: 'Coverage', width: '120px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: true},
    {field: 'currency', header: 'Currency', width: '120px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: true},
    {field: 'action', header: 'Action', width: '40px', type: 'action', sorted: false, filtered: false, highlight: false, visible: true, edit: false},
  ];

  dataCoverage = [
    {
      divisionNo: 1,
      principal: true,
      lob: 'Property',
      coverage: 'PD, BI',
      currency: 'USD'
    }
  ];

  @Select(WorkspaceState.getFacSequence) facSequence$;
  facSequence;

  constructor(private wsApi: WsApi, private router: Router,  private store: Store) {
  }

  ngOnInit() {
    this.data = Regions.regionPeril;
    this.facSequence$.subscribe(value => this.facSequence = value);
  }

  addRow() {
    this.dataCoverage = [...this.dataCoverage, {
      divisionNo: this.dataCoverage.length + 1,
      principal: false,
      lob: 'Property',
      coverage: 'PD, BI',
      currency: 'USD'
    }];
  }

  deleteRow(index) {
    if (this.dataCoverage.length !== 1) {
      const isPrincipal = this.dataCoverage[index].principal;
      if (isPrincipal) {
        if (index === this.dataCoverage.length - 1) {
          this.dataCoverage[index - 1].principal = true;
        } else {
          this.dataCoverage[index + 1].principal = true;
        }
        this.dataCoverage.splice(index, 1);
      } else {
        this.dataCoverage.splice(index, 1);
      }
    }
  }

  isPrincipalSet(event, index) {
    if (event) {
      this.dataCoverage = this.dataCoverage.map((item, iterator) => {
        if (iterator !== index) {
          return {...item, principal: false};
        } else {
          return {...item, principal: true};
        }
      });
    } else {
      if (this.dataCoverage.length === 1) {
        this.dataCoverage[0].principal = true;
      } else {
        if (index === this.dataCoverage.length - 1) {
          this.dataCoverage[index - 1].principal = true;
        } else {
          this.dataCoverage[index + 1].principal = true;
        }
        this.dataCoverage[index].principal = false;
      }
    }
  }

  submitData() {
    const data = {
      id: 'CAR-00' + this.facSequence,
      lastUpdateDate: null,
      lastUpdatedBy: null,
      requestCreationDate: new Date(),
      requestedByFirstName: this.raisedBy.value,
      requestedByFullName: this.raisedBy.value,
      requestedByLastName: this.raisedBy.value,
      uwanalysisContractBusinessType: this.businessType.value,
      uwanalysisContractContractId: this.contractId,
      uwanalysisContractEndorsementNumber: 0,
      uwanalysisContractFacNumber: this.contractId,
      uwanalysisContractInsured: this.insured,
      uwanalysisContractLabel: this.contractLabel,
      uwanalysisContractLob: this.lob.value,
      uwanalysisContractOrderNumber: 0,
      uwanalysisContractSector: this.sector,
      uwanalysisContractSubsidiary: this.subsidiary.value,
      uwanalysisContractYear: this.uwYear,
      cedantName: 'INGREDION, INC_',
      contractName: 'ENNMG1800030 /ex ENEUR2800034',
      uwAnalysisProjectId: 'P-000' + Math.floor(Math.random() * 100000),
      uwAnalysisContractDate: this.uwYear + '-01',
      assignedAnalyst: 'Amina Cheref',
      carStatus: 'New'
    };
/*    this.wsApi.postFacData(data).subscribe(dt => console.log(dt));*/
    this.store.dispatch(new fromWs.CreateNewFac(data));
    this.router.navigate(['dashboard']);
  }
}
