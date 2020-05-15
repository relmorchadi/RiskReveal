import {ChangeDetectionStrategy, ChangeDetectorRef, Component, ComponentFactoryResolver, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Actions, ofActionDispatched, Select, Store} from '@ngxs/store';
import {MessageService} from 'primeng/api';
import {combineLatest, forkJoin, Observable} from 'rxjs';
import * as _ from 'lodash'
import {WorkspaceState} from '../../store/states';
import * as fromWS from '../../store'
import {LoadWS, SetCurrentTab} from '../../store'
import {PreviousNavigationService} from '../../services/previous-navigation.service';
import {BaseContainer} from '../../../shared/base';
import {StateSubscriber} from '../../model/state-subscriber';
import {AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {WsApi} from "../../services/api/workspace.api";
import {$e} from "codelyzer/angular/styles/chars";
import {CloneDataApi} from "../../services/api/cloneData.api";
import {LoadProjectByWorkspace} from "../../store";
import {GeneralConfigState} from "../../../core/store/states";
import {GetTablePreference} from "../../../core/store/actions";

interface SourceData {
  plts: any[];
  detail: string;
  wsId: string;
  uwYear: string
}

@Component({
  selector: 'app-workspace-clone-data',
  templateUrl: './workspace-clone-data.component.html',
  styleUrls: ['./workspace-clone-data.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [MessageService]
})
export class WorkspaceCloneDataComponent extends BaseContainer implements OnInit, StateSubscriber {

  @Select(WorkspaceState.getCurrentTab) currentTab$;
  @Select(WorkspaceState.getCloningStatus) cloningStatus$;

  cloningStatus = '';

  constructor(
    private route$: ActivatedRoute,
    private store: Store,
    private prn: PreviousNavigationService,
    private _fb: FormBuilder,
    private wsApi: WsApi,
    private cloneDataApi: CloneDataApi,
    private actions$: Actions,
    _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef
  ) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.activeSubTitle = 0;
    this.cloningToItem = true;
    this.searchWorkSpaceModal = false;
    this.data = [
      {
        id: 77897,
        year: 2019,
        wsContext: "02PY376",
        cedantName: "A.M.M.A",
        cedant: 87989,
        country: "Belgium"
      }, {
        id: 77897,
        year: 2019,
        wsContext: "02PY376",
        cedantName: "A.M.M.A",
        cedant: 87989,
        country: "Belgium"
      }, {
        id: 77897,
        year: 2019,
        wsContext: "02PY376",
        cedantName: "A.M.M.A",
        cedant: 87989,
        country: "Belgium"
      }, {
        id: 77897,
        year: 2019,
        wsContext: "02PY376",
        cedantName: "A.M.M.A",
        cedant: 87989,
        country: "Belgium"
      }, {
        id: 77897,
        year: 2019,
        wsContext: "02PY376",
        cedantName: "A.M.M.A",
        cedant: 87989,
        country: "Belgium"
      }, {
        id: 77897,
        year: 2019,
        wsContext: "02PY376",
        cedantName: "A.M.M.A",
        cedant: 87989,
        country: "Belgium"
      },];
    this.browesing = false;
    this.multiSteps = true;
    this.stepConfig = {
      wsId: '',
      uwYear: '',
      plts: []
    }
    this.cloneConfig = {
      currentSourceOfItems: 'to',
      summary: {
        'Pre-Inured PLTs': {
          'icon': 'icon-assignment_24px',
          'color': '#c38fff',
          'value': 0
        },
        'Post-Inured PLTs': {
          'icon': 'fa fa-code-fork',
          'color': '#c38fff',
          'value': 0
        },
        'Inuring Packages': {
          'icon': 'icon-layer-group',
          'color': '#f5a623',
          'value': 0
        },
        'Sources Projects': {
          'icon': 'icon-assignment_24px',
          'color': '#33d0bb',
          'value': 0
        }
      }
    };
    this.initProjectForm();
    this.listOfProjects = [];
  }

  initProjectForm() {
    this.projectsForm = this._fb.group({
      projectStep: [-1],
      projectName: [''],
      projectDescription: [''],
      selectedProjectId: [-1, Validators.required],
      from: {
        detail: '',
        plts: [],
        uwYear: '',
        wsId: ''
      },
      to: {
        detail: '',
        plts: [],
        uwYear: '',
        wsId: ''
      }
    });
  }

  patchProjectForm(key, value) {
    this.projectsForm.patchValue({
      [key]: value
    });
  }

  getFormValueByKey(key) {
    return this.projectsForm.get(key).value;
  }

  loadProjects(wsId, uwYear) {
    forkJoin(
      this.wsApi.searchWorkspace(wsId, uwYear)
    ).subscribe(([{projects}]: any) => {
      this.listOfProjects = projects;
      this.detectChanges();

    });
  }

  subTitle = {
    0: 'Clone Workspaces Assets',
    1: 'Source Workspace Selection',
    2: 'Target Workspace Selection'
  };

  activeSubTitle: number;
  cloningToItem: boolean;
  searchWorkSpaceModal: boolean;
  data: any[];
  fromCache: SourceData;
  toCache: SourceData;
  browesing: boolean;
  showDeleted: boolean;
  workspaceId: string;
  uwy: number;
  subs: any[] = [];
  cloneConfig: {
    currentSourceOfItems: string,
    summary: any;
  }
  multiSteps: boolean;
  stepConfig: {
    wsId: string,
    uwYear: string,
    plts: any[]
  };
  projectsForm: FormGroup;
  listOfProjects: any[];
  data$: Observable<any>;

  ngOnInit() {

    this.cloningStatus$.subscribe(status => this.cloningStatus = status);

 //   this.data$ = this.store.select(GeneralConfigState.getTablePreference('pageTst', 'tabblename'));
   //   this.data$.subscribe(t => console.log(t));
    //this.dispatch(new GetTablePreference({uIPage: 'pageTst', tableName: 'tabblename'}));

    this.currentTab$.subscribe(c => {

      this.store.dispatch(new LoadProjectByWorkspace({wsId: c.wsIdentifier.split('-')[0], uwYear: c.wsIdentifier.split('-')[1]}));
    });
    this._to.valueChanges.pipe(this.unsubscribeOnDestroy).subscribe((from) => {
      const {
        detail,
        plts,
        uwYear,
        wsId
      } = from;

      if (wsId && uwYear) {
        this.loadProjects(wsId, uwYear);
        this.detectChanges();
      } else {
        this.listOfProjects = [];
        this.detectChanges();
      }
    });

    this._projectStep.valueChanges.pipe(this.unsubscribeOnDestroy).subscribe(step => {
      switch (step) {
        case -1:
        case 1:

          this._projectName.clearValidators();
          this._projectName.reset();
          this._projectDescription.clearValidators();
          this._projectDescription.reset();
          if (step === 1) {
            this._selectedProjectId.setValidators([this.selectedProjectValidator()]);
          } else {
            this._selectedProjectId.clearValidators();
          }
          break;
        case 0:
          this._projectName.setValidators([Validators.required]);
          this._projectDescription.setValidators([Validators.required]);
          this._selectedProjectId.clearValidators();
          break;
        default:
          console.log("form group");
      }
      this.detectChanges();
    });

    this.actions$
      .pipe(
        ofActionDispatched(SetCurrentTab)
      ).subscribe(({payload}) => {

      if (payload.wsIdentifier != this.workspaceId + "-" + this.uwy) this.destroy();
    });

    combineLatest(
      this.select(WorkspaceState.getCloneConfig),
      this.route$.params,
      this.select(WorkspaceState.getCurrentWS)
    ).pipe(this.unsubscribeOnDestroy).subscribe(([navigationPayload, {wsId, year}, currentWS]: any) => {
      this.workspaceId = wsId;
      this.uwy = year;
      let navigationWsId = _.get(navigationPayload, 'payload.wsId', null);
      let navigationUwYear = _.get(navigationPayload, 'payload.uwYear', null);

      if (_.get(navigationPayload, 'from', null) == 'pltBrowser' && navigationWsId && navigationWsId == wsId && navigationUwYear && navigationUwYear == year) {
        if (_.get(navigationPayload, 'type', null) == 'cloneFrom') {
          this.patchProjectForm('from', {
            wsId: '',
            uwYear: '',
            plts: [],
            detail: ''
          });
          this.patchProjectForm('to', {
            wsId: wsId,
            uwYear: year,
            plts: [],
            detail: currentWS && currentWS.cedantName + ' | ' + currentWS.workspaceName + ' | ' + currentWS.uwYear + ' | ' + currentWS.wsId
          });
          this.setCloneConfig('currentSourceOfItems', 'from');
          this.multiSteps = true;
        } else {
          this.patchProjectForm('from', {
            ...navigationPayload.payload,
            detail: currentWS && currentWS.cedantName + ' | ' + currentWS.workspaceName + ' | ' + currentWS.uwYear + ' | ' + currentWS.wsId
          });
          this.patchProjectForm('to', {
            wsId: '',
            uwYear: '',
            plts: [],
            detail: ''
          });
          this.setCloneConfig('currentSourceOfItems', 'to');
          this.multiSteps = false;
        }

        this.stepConfig = {
          wsId: '',
          uwYear: '',
          plts: []
        };
        this.searchWorkSpaceModal = true;

      } else {
        this.patchProjectForm('to', {
          wsId: wsId,
          uwYear: year,
          plts: [],
          detail: currentWS && currentWS.cedantName + ' | ' + currentWS.workspaceName + ' | ' + currentWS.uwYear + ' | ' + currentWS.wsId
        });
        this.patchProjectForm('from', {
          detail: '',
          plts: [],
          wsId: '',
          uwYear: ''
        });
      }

      if (this.getFormValueByKey('from').plts.length > 0) {
        this.cloneConfig = {
          ...this.cloneConfig,
          summary: {...this.summaryCache}
        }
      } else {
        const k = {};

        _.forEach(this.cloneConfig.summary, (v, key) => {
          k[key] = {...v, value: 0};
        })

        this.cloneConfig = {
          ...this.cloneConfig,
          summary: {...k}
        }
      }
      this.fromCache = {...this.getFormValueByKey('from')};
      this.toCache = {...this.getFormValueByKey('to')};
      this.detectChanges();
    })
  }

  setSubTitle(number: number) {
    this.activeSubTitle = number;
  }

  handleModalClick() {
    if (!this.browesing) {
      this.browesing = true;
    } else {
      this.searchWorkSpaceModal = false;
      this.browesing = false;
    }
  }

  goToSearchWorkspace() {
    this.searchWorkSpaceModal = true;
    this.browesing = false;
  }

  cancelModal() {
    this.browesing = false;
    this.searchWorkSpaceModal = false;
  }

  getPlts() {
    this.browesing = true;
  }

  ngOnDestroy(): void {
    this.dispatch(new fromWS.setCloneConfig({
      cloneConfig: {},
      wsIdentifier: this.workspaceId + '-' + this.uwy
    }));
    this.destroy();

  }

  setSelectedWs(currentSourceOfItems: string, $event: any) {

    if (currentSourceOfItems == 'from') {
      this.patchProjectForm('from', {
        wsId: $event.workspaceContextCode,
        uwYear: $event.uwYear,
        detail: $event.client + ' | ' + $event.workspaceName + ' | ' + $event.uwYear + ' | ' + $event.workspaceContextCode
      })
    }
    if (currentSourceOfItems == 'to') {
      this.patchProjectForm('to', {
        wsId: $event.workSpaceId,
        uwYear: $event.uwYear,
        detail: $event.client + ' | ' + $event.workspaceName + ' | ' + $event.uwYear + ' | ' + $event.workspaceContextCode
      })
    }

  }

  summaryCache = {
    'Pre-Inured PLTs': {
      'icon': 'icon-assignment_24px',
      'color': '#c38fff',
      'value': 14
    },
    'Post-Inured PLTs': {
      'icon': 'fa fa-code-fork',
      'color': '#c38fff',
      'value': 7
    },
    'Inuring Packages': {
      'icon': 'icon-layer-group',
      'color': '#f5a623',
      'value': 5
    },
    'Sources Projects': {
      'icon': 'icon-assignment_24px',
      'color': '#33d0bb',
      'value': 8
    }
  }

  swapCloneItems() {
    const t = {...this.getFormValueByKey('from')};
    this.patchProjectForm('from', this.getFormValueByKey('to'));
    this.patchProjectForm('to', t);

    if (this.getFormValueByKey('from').plts.length > 0) {
      this.cloneConfig = {
        ...this.cloneConfig,
        summary: {...this.summaryCache}
      }
    } else {
      const k = {};

      _.forEach(this.cloneConfig.summary, (v, key) => {
        k[key] = {...v, value: 0};
      })

      this.cloneConfig = {
        ...this.cloneConfig,
        summary: {...k}
      }
    }
  }

  setSelectedPlts(currentSourceOfItems: string, $event: any) {
    if (currentSourceOfItems == 'from') {
      this.patchProjectForm('from', {...this._from.value, plts: $event});
    }
    if (currentSourceOfItems == 'to') {
      this.patchProjectForm('to', {...this._to.value, plts: $event});
    }



    if (this.getFormValueByKey('from').plts.length > 0) {
      this.summaryCache['Pre-Inured PLTs'] = {
        ...this.summaryCache['Pre-Inured PLTs'],
        value : this.getFormValueByKey('from').plts.length
      };
      this.cloneConfig = {
        ...this.cloneConfig,
        summary: {...this.summaryCache}
      }
    } else {
      const k = {};

      _.forEach(this.cloneConfig.summary, (v, key) => {
        k[key] = {...v, value: 0};
      })

      this.cloneConfig = {
        ...this.cloneConfig,
        summary: {...k}
      }
    }
  }

  openSearchPopUp(destination: string = 'from') {
    this.multiSteps = destination == 'from';
    this.stepConfig = {
      wsId: '',
      uwYear: '',
      plts: []
    };
    this.searchWorkSpaceModal = true;
    this.setCloneConfig('currentSourceOfItems', destination);
  }

  setCloneConfig(key, value) {
    this.cloneConfig = {...this.cloneConfig, [key]: value};
  }

  getCloneConfig(key) {
    return this.cloneConfig[key];
  }

  protected detectChanges() {
    super.detectChanges();
  }

  editPlts() {

    const {
      wsId,
      uwYear,
      plts
    } = this.getFormValueByKey('from');

    this.stepConfig = {
      wsId,
      uwYear,
      plts
    };
    this.multiSteps = true;
    this.setCloneConfig('currentSourceOfItems', 'from');
    this.searchWorkSpaceModal = true;
  }

  reset() {
    if (this.activeSubTitle === 0) {
      this.patchProjectForm('from', {
        detail: '',
        plts: [],
        wsId: '',
        uwYear: ''
      });
      this.patchProjectForm('to', {
        detail: '',
        plts: [],
        wsId: '',
        uwYear: ''
      });


      if (this.getFormValueByKey('from').plts.length > 0) {
        this.cloneConfig = {
          ...this.cloneConfig,
          summary: {...this.summaryCache}
        }
      } else {
        const k = {};

        _.forEach(this.cloneConfig.summary, (v, key) => {
          k[key] = {...v, value: 0};
        });

        this.cloneConfig = {
          ...this.cloneConfig,
          summary: {...k}
        }
      }
    }
  }

  get _projectStep(): AbstractControl {
    return this.projectsForm.get('projectStep');
  }

  get _projectName(): AbstractControl {
    return this.projectsForm.get('projectName');
  }

  get _projectDescription(): AbstractControl {
    return this.projectsForm.get('projectDescription');
  }

  get _from(): AbstractControl {

    return this.projectsForm.get('from');
  }

  get _to(): AbstractControl {
    return this.projectsForm.get('to');
  }

  get _selectedProjectId(): AbstractControl {
    return this.projectsForm.get('selectedProjectId');
  }

  selectedProjectValidator = (): ValidatorFn => (control: AbstractControl): { [key: string]: any } | null => control.value >= 0 ? null : ({noSelectedProject: {value: control.value}})


  patchState(state: any): void {

  }

  clone() {
    let body = {
      pltIds: this.getFormValueByKey('from').plts,
      cloningType: '',
      newProjectName: '',
      newProjectDescription: '',
      existingProjectId: '',
      targetWorkspaceContextCode: this._to.value.wsId,
      targetWorkspaceUwYear: this._to.value.uwYear
    };



    switch (this.projectsForm.value.projectStep) {
      case -1:
        body = {
          ...body,
          cloningType: 'KEEP_PROJECT_NAME'
        };
        break;
      case 0:
        body = {
          ...body,
          cloningType: 'NEW_PROJECT',
          newProjectName: this.projectsForm.value.projectName,
          newProjectDescription: this.projectsForm.value.projectDescription
        };
        break;
      case 1:
        body = {
          ...body,
          cloningType: 'EXISTING_PROJECT',
          existingProjectId: this.projectsForm.value.selectedProjectId
        };
        break;
    }


    this.dispatch(new fromWS.CommitClone(body));

    if (this._projectStep.value === 0) {
      this._projectName.markAsDirty();
      this._projectDescription.markAsDirty();
    }
    if (this._projectStep.value === 1) {
      this._selectedProjectId.markAsDirty();
    }
    this.detectChanges();
  }

  cloneAndOpen() {

    this.clone();

    if (this.projectsForm.valid) this.navigate([`workspace/${this.getFormValueByKey('to').wsId}/${this.getFormValueByKey('to').uwYear}/PltBrowser`]);
  }
}
