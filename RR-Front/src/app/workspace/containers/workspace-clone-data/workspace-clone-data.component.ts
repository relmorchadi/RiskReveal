import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Store} from '@ngxs/store';
import {MessageService} from 'primeng/api';
import {combineLatest, forkJoin} from 'rxjs';
import * as _ from 'lodash'
import {WorkspaceState} from '../../store/states';
import * as fromWS from '../../store'
import {PreviousNavigationService} from '../../services/previous-navigation.service';
import {take} from 'rxjs/operators';
import {BaseContainer} from '../../../shared/base';
import {StateSubscriber} from '../../model/state-subscriber';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {WsApi} from "../../services/workspace.api";

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

  constructor(
    private route$: ActivatedRoute,
    private prn: PreviousNavigationService,
    private _fb: FormBuilder,
    private wsApi: WsApi,
    _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef
  ) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.activeSubTitle= 0;
    this.cloningToItem= true;
    this.searchWorkSpaceModal= false;
    this.data= [
      {
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },{
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },{
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },{
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },{
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },{
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },];
    this.browesing= false;
    this.cloneConfig= {
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
    this.multiSteps= true;
    this.stepConfig= {
      wsId: '',
      uwYear: '',
      plts: []
    }
    this.initProjectForm();
    this.listOfProjects= [];
  }

  initProjectForm() {
    this.projectsForm= this._fb.group({
      projectStep: [-1],
      projectName: [''],
      projectDescription: [''],
      selectedProjectIndex: [-1, Validators.required],
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
    })
  }

  patchProjectForm(key, value) {
    this.projectsForm.patchValue({
      [key]: value
    })
  }

  getFormValueByKey(key){
    return this.projectsForm.get(key).value;
  }

  loadProjects(wsId, uwYear) {
    console.log('loading projects');
    forkJoin(
      this.wsApi.searchWorkspace(wsId,uwYear)
    ).subscribe( ([{projects}]: any) => {
      console.log(projects)
      this.listOfProjects =projects;
      this.detectChanges();
    })
  }

  subTitle= {
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
  subs: any[]= [];
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

  ngOnInit() {

    this.projectsForm.valueChanges.pipe(this.unsubscribeOnDestroy).subscribe( (change: any) => {
      console.log(this.projectsForm);
      switch (change.projectStep) {
        case -1:
        case 1:
          this._projectName.clearValidators();
          this._projectDescription.clearValidators();
          if(this._projectName.errors) this._projectName.updateValueAndValidity();
          if(this._projectDescription.errors) this._projectDescription.updateValueAndValidity();
          this.detectChanges();
          break;
        case 0:
          this._projectName.setValidators([Validators.required]);
          this._projectDescription.setValidators([Validators.required]);
          this.detectChanges();
          break;
        default:
          console.log("form group")
      }
      this.detectChanges();
    });

    this._to.valueChanges.pipe(this.unsubscribeOnDestroy).subscribe( (from) => {
      const {
        detail,
        plts,
        uwYear,
        wsId
      } = from;

      if(wsId && uwYear) {
        this.loadProjects(wsId, uwYear);
        this.detectChanges();
      }else {
        this.listOfProjects= [];
        this.detectChanges();
      }
    });
    this._projectStep.valueChanges.pipe(this.unsubscribeOnDestroy).subscribe( step => {
      if(step != 0 && (this._projectName.errors || this._projectDescription.errors)) {
        this._projectDescription.reset();
        this._projectName.reset();
        this.detectChanges();
      }
    });
    this.subs.push(
      combineLatest(
        this.select(WorkspaceState.getCloneConfig),
        this.route$.params,
        this.select(WorkspaceState.getCurrentWS).pipe(take(2))
      ).pipe(this.unsubscribeOnDestroy).subscribe(([navigationPayload, {wsId, year}, currentWS]: any) => {
        const url = this.prn.getPreviousUrl();
        this.workspaceId = wsId;
        this.uwy = year;
        console.log({
          prn: url,
          navigationPayload, wsId, year, currentWS
        });
        if (url == 'PltBrowser' && _.get(navigationPayload, 'payload.wsId', null) && _.get(navigationPayload, 'payload.uwYear', null)) {
          this.patchProjectForm('from', {
            ...navigationPayload.payload,
            detail: currentWS && currentWS.cedantName + ' | ' + currentWS.workspaceName + ' | ' + currentWS.uwYear + ' | ' + currentWS.wsId
          });
          this.patchProjectForm('to', {
            detail: '',
            plts: [],
            wsId: '',
            uwYear: ''
          });
          this.setCloneConfig('currentSourceOfItems', 'to');
          this.multiSteps = false;
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

        if(_.get(navigationPayload, 'payload.type', null) == 'cloneFrom') {
          this.setCloneConfig('currentSourceOfItems', 'from');
          this.multiSteps = true;
          this.stepConfig = {
            wsId: '',
            uwYear: '',
            plts: []
          };
          this.searchWorkSpaceModal = true;
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
    )
  }

  setSubTitle(number: number) {
    this.activeSubTitle= number;
  }

  handleModalClick() {
    if(!this.browesing) {
      this.browesing= true;
    }else {
      this.searchWorkSpaceModal= false;
      this.browesing= false;
    }
  }

  goToSearchWorkspace() {
    this.searchWorkSpaceModal=true;
    this.browesing= false;
  }

  cancelModal() {
    this.browesing= false;
    this.searchWorkSpaceModal= false;
  }

  getPlts() {
    this.browesing=true;
  }

  ngOnDestroy(): void {
    this.dispatch(new fromWS.setCloneConfig({
      cloneConfig: {},
      wsIdentifier: this.workspaceId + '-' + this.uwy
    }));
    this.destroy();
  }

  setSelectedWs(currentSourceOfItems: string,$event: any) {
    if(currentSourceOfItems == 'from') {
      this.patchProjectForm('from', {wsId: $event.workSpaceId, uwYear: $event.uwYear, detail: $event.cedantName+' | '+$event.workspaceName+' | '+$event.uwYear+' | '+$event.workSpaceId})
    }
    if(currentSourceOfItems == 'to') {
      this.patchProjectForm('to', { wsId: $event.workSpaceId, uwYear: $event.uwYear, detail: $event.cedantName+' | '+$event.workspaceName+' | '+$event.uwYear+' | '+$event.workSpaceId})
    }
  }

  summaryCache= {
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
    const t= {...this.getFormValueByKey('from')};
    this.patchProjectForm('from',this.getFormValueByKey('to'));
    this.patchProjectForm('to', t);

    if(this.getFormValueByKey('from').plts.length > 0) {
      this.cloneConfig= {
        ...this.cloneConfig,
        summary: {...this.summaryCache}
      }
    }else {
      const k= {};

      _.forEach(this.cloneConfig.summary, (v,key) => {
        k[key] = {...v, value: 0};
      })

      this.cloneConfig= {
        ...this.cloneConfig,
        summary: {...k}
      }
    }
  }

  setSelectedPlts(currentSourceOfItems: string, $event: any) {
    if(currentSourceOfItems == 'from') {
      this.patchProjectForm('from', {plts: $event});
    }
    if(currentSourceOfItems == 'to') {
      this.patchProjectForm('to', { plts: $event});
    }

    if(this.getFormValueByKey('from').plts.length > 0) {
      this.cloneConfig= {
        ...this.cloneConfig,
        summary: {...this.summaryCache}
      }
    }else {
      const k= {};

      _.forEach(this.cloneConfig.summary, (v,key) => {
        k[key] = {...v, value: 0};
      })

      this.cloneConfig= {
        ...this.cloneConfig,
        summary: {...k}
      }
    }
  }

  openSearchPopUp(destination: string = 'from') {
    this.multiSteps= destination == 'from';
    this.stepConfig = {
      wsId: '',
      uwYear: '',
      plts: []
    };
    this.searchWorkSpaceModal= true;
    this.setCloneConfig('currentSourceOfItems', destination);
  }

  setCloneConfig(key,value) {
    this.cloneConfig= { ...this.cloneConfig, [key]: value };
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
    this.multiSteps= true;
    this.setCloneConfig('currentSourceOfItems', 'from');
    this.searchWorkSpaceModal = true;
  }

  reset() {
    if(this.activeSubTitle === 0) {
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


      if(this.getFormValueByKey('from').plts.length > 0) {
        this.cloneConfig= {
          ...this.cloneConfig,
          summary: {...this.summaryCache}
        }
      }else {
        const k= {};

        _.forEach(this.cloneConfig.summary, (v,key) => {
          k[key] = {...v, value: 0};
        });

        this.cloneConfig= {
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



  patchState(state: any): void {
  }

  clone() {
    this._projectName.updateValueAndValidity();
    this._projectName.markAsDirty();
    this._projectDescription.updateValueAndValidity();
    this._projectDescription.markAsDirty();
  }

  cloneAndOpen() {

    this.clone();
  }
}
