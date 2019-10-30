import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Subject} from 'rxjs';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Select, Store} from '@ngxs/store';
import * as _ from 'lodash';
import * as fromWS from '../../../store/actions/workspace.actions';
import {WorkspaceState} from '../../../store/states';

@Component({
  selector: 'app-create-fac-project-popup',
  templateUrl: './create-fac-project-popup.component.html',
  styleUrls: ['./create-fac-project-popup.component.scss']
})
export class CreateFacProjectPopupComponent implements OnInit {
  unSubscribe$: Subject<void>;
  @Output('onCancelCreateProject')
  onCancelCreateProject: EventEmitter<any> = new EventEmitter();
  @Input('workspace')
  workspace: any;
  @Input('isVisible')
  isVisible;
  @Input('newProject')
  newProject: any;
  newProjectForm: FormGroup;

  @Select(WorkspaceState.getFacSequence) facSequence$;
  facSequence;

  constructor(private store: Store) {
    this.unSubscribe$ = new Subject<void>();
  }

  ngOnInit() {
    this.initNewProjectForm();
    this.facSequence$.subscribe(value => this.facSequence = value);
  }

  ngOnDestroy(): void {
    this.unSubscribe$.next();
    this.unSubscribe$.complete();
  }

  createUpdateProject() {
    const projectId = 'P-000' + Math.floor(Math.random() * 100000);
    let project = {...this.newProjectForm.value,
      projectType: 'fac',
      id: 'CAR-00' + this.facSequence,
      projectId,
      lastUpdateDate: null,
      lastUpdatedBy: null,
      requestCreationDate: new Date(),
      requestedByFirstName: 'Nathalie',
      requestedByFullName: 'Nathalie Dulac',
      requestedByLastName: 'Dulac',
      uwAnalysis: '',
      projectFacSource: 'manual',
      uwanalysisContractBusinessType: 'Renewal',
      uwanalysisContractContractId: this.workspace.wsId,
      uwanalysisContractEndorsementNumber: 0,
      uwanalysisContractFacNumber: this.workspace.wsId,
      uwanalysisContractInsured: '200033218 @ Insureds',
      uwanalysisContractLabel: 'EL1400204',
      uwanalysisContractLob: '01 @ LOBs',
      uwanalysisContractOrderNumber: 0,
      uwanalysisContractSector: '610 @ Sectors',
      uwanalysisContractSubsidiary: '20 @ Subsidiaries',
      uwAnalysisProjectId: projectId,
      uwanalysisContractYear: 2016,
      cedantName: this.workspace.cedantName,
      contractName: 'ENNMG1800030 /ex ENEUR2800034',
      uwAnalysisContractDate:  this.workspace.uwYear,
      assignedAnalyst: 'Unassigned',
      carStatus: 'New',
      division: [
        {
          selected: false,
          divisionNo: 1,
          principal: true,
          lob: 'Property',
          coverage: 'PD, BI',
          currency: 'USD'
        },
        {
          selected: false,
          divisionNo: 2,
          principal: false,
          lob: 'Property',
          coverage: 'PD, BI',
          currency: 'USD'
        },
        {
          selected: false,
          divisionNo: 3,
          principal: false,
          lob: 'Property',
          coverage: 'PD, BI',
          currency: 'USD'
        }
      ],
      regionPeril: [
        {
          regionPerilCode: 'ACEQ-CR',
          regionPerilDesc: 'Central America (Costa Rica) Earthquake',
          isValidMinimumGrain: true,
          division: '3'
        },
        {
          regionPerilCode: 'ACEQ-GT',
          regionPerilDesc: 'Central America (Guatemala) Earthquake',
          isValidMinimumGrain: true,
          division: '1'
        },
        {
          regionPerilCode: 'ACEQ-SV',
          regionPerilDesc: 'Central America (El Salvador) Earthquake',
          isValidMinimumGrain: true,
          division: '2'
        },
        {
          regionPerilCode: 'AHEQ-CL',
          regionPerilDesc: 'South America (Chile) Earthquake',
          isValidMinimumGrain: true,
          division: '3'
        },
        {
          regionPerilCode: 'AHEQ-CO',
          regionPerilDesc: 'South America (Colombia) Earthquake',
          isValidMinimumGrain: true,
          division: '2'
        }
      ]
    };

    if (this.newProjectForm.controls.projectId.value) {
      project = {...project, linkFlag: true,
        sourceProjectId: this.newProjectForm.value.projectId,
        sourceProjectName: this.newProjectForm.value.name,
        sourceWsId: _.get(this.newProject, 'workspaceId', null),
        sourceWsName: _.get(this.newProject, 'workspaceName', null)
      };
    }

    this.store.dispatch(new fromWS.AddNewFacProject(project));
  }

  cancelCreateProject() {
    this.initNewProjectForm();
    this.onCancelCreateProject.emit(false);
  }

  patchNewProject() {
    this.newProject && this.newProjectForm.patchValue(this.newProject);
  }

  initNewProjectForm() {
    this.newProjectForm = new FormGroup({
      projectId: new FormControl(null),
      name: new FormControl(null, Validators.required),
      description: new FormControl(null),
      createdBy: new FormControl('Nathalie Dulac', Validators.required),
      receptionDate: new FormControl(new Date(), Validators.required),
      dueDate: new FormControl(new Date(), Validators.required),
      assignedTo: new FormControl(null),
      linkFlag: new FormControl(null),
      postInuredFlag: new FormControl(null),
      publishFlag: new FormControl(null),
      pltSum: new FormControl(0),
      pltThreadSum: new FormControl(0),
      regionPerilSum: new FormControl(0),
      xactSum: new FormControl(0),
      locking: new FormControl(null),
      sourceProjectId: new FormControl(null),
      sourceProjectName: new FormControl(null),
      sourceWsId: new FormControl(null),
      sourceWsName: new FormControl(null)
    });
  }

}
