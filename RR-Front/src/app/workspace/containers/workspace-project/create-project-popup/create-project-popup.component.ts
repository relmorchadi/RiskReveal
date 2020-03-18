import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  HostListener,
  Input,
  OnDestroy,
  OnInit,
  Output
} from '@angular/core';

import {Subject} from 'rxjs';
import {Select, Store} from '@ngxs/store';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import * as _ from 'lodash';
import * as fromWS from '../../../store/actions/workspace.actions'
import {EventListener} from "@angular/core/src/debug/debug_node";
import {WsApi} from "../../../services/api/workspace.api";
import {BaseContainer} from "../../../../shared/base";
import {Router} from "@angular/router";
import {AuthState, GeneralConfigState} from "../../../../core/store/states";

@Component({
  selector: 'app-create-project-popup',
  templateUrl: './create-project-popup.component.html',
  styleUrls: ['./create-project-popup.component.scss'],
})
export class CreateProjectPopupComponent extends BaseContainer implements OnInit, OnDestroy {

  unSubscribe$: Subject<void>;
  @Output('onCancelCreateProject')
  onCancelCreateProject: EventEmitter<any> = new EventEmitter();
  @Input('workspace') workspace: any;
  @Input('isVisible') isVisible;
  @Input('newProject') newProject: any;
  @Input('edit') editOption: any;
  @Input('editForm') projectForm: any;

  @Select(GeneralConfigState.getAllUsers) users$;
  @Select(AuthState.getUser) user$;

  currentUser: any;
  projectFormInit: any;
  newProjectForm: FormGroup;
  editCreateBLock = false;
  users: any[];

  constructor(private store: Store, private wsApi: WsApi,
              _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.unSubscribe$ = new Subject<void>();
  }

  ngOnInit() {
    this.initNewProjectForm();
    this.users$.pipe().subscribe(value => {
      this.users = value;
      this.detectChanges();
    });

    this.user$.pipe().subscribe(value => {
      this.currentUser = value;
      this.initNewProjectForm();
      this.detectChanges();
    })
  }

  ngOnDestroy(): void {
    this.unSubscribe$.next();
    this.unSubscribe$.complete();
  }

  createUpdateProject() {
    let project = {...this.newProjectForm.value, projectId: null};
    if (this.newProjectForm.controls.projectId.value) {
      project = {
        ...project,
        isLinked: true,
        cloneSourceProjectId: this.newProjectForm.value.projectId,
      };
    }

/*    const secondProject = {
      workspaceByFkWorkspaceId: {
        clientName: '',
        entity: 0,
        workspaceContext: '',
        workspaceContextCode: '',
        workspaceId: 0,
        workspaceMarketChannel: '',
        workspaceName: '',
        workspaceUwYear: 0
      },
      workspaceId: 0
  };*/

    this.store.dispatch(new fromWS.AddNewProject({
      id: _.get(this.workspace, 'id', null),
      wsId: this.workspace.wsId,
      uwYear: this.workspace.uwYear,
      project
    }));
  }

  updateProject() {
    this.store.dispatch(new fromWS.EditProject({
      data: {
        projectName: this.projectForm.projectName || '',
        assignedTo: this.projectForm.assignedTo,
        projectId: this.projectForm.projectId || '',
        projectDescription: this.projectForm.projectDescription || '',
        dueDate: new Date(this.projectForm.dueDate) || new Date()
      }
    }))
  }

  checkValid() {
    return _.isEmpty(_.trim(this.projectForm.projectName))
  }

  checkChange() {
    return _.isEqual(this.projectFormInit, this.projectForm);
  }

  checkGlobalValid() {
    return this.checkValid() ? false : !this.checkChange();
  }

  cancelCreateProject() {
    this.initNewProjectForm();
    this.onCancelCreateProject.emit(false);
  }

  patchNewProject() {
    this.editCreateBLock = false;
    this.newProject && this.newProjectForm.patchValue(this.newProject);
    console.log(this.currentUser, this.projectForm, this.newProject);
    if (this.editOption) {
      this.projectForm.assignedTo = _.toInteger(this.projectForm.assignedTo);
      this.projectFormInit = {...this.projectForm};
    }
  }

  @HostListener('document: keydown.enter', ['$event']) keyBoardEnter() {
    if (!this.editCreateBLock) {
      this.editCreateBLock = true;
      if (this.editOption) {
        this.checkGlobalValid() ? this.updateProject() : null;
      } else {
        this.newProjectForm.valid ? this.createUpdateProject() : null;
      }
    }
  }

  initNewProjectForm() {
    this.newProjectForm = new FormGroup({
      assignedTo: new FormControl(null),
      cloneSourceProjectId: new FormControl(null),
      clonedFlag: new FormControl(false),
      createdBy: new FormControl(this.currentUser, Validators.required),
      creationDate: new FormControl(new Date()),
      deleted: new FormControl(false),
      deletedBy: new FormControl(null),
      deletedDue: new FormControl(null),
      deletedOn: new FormControl(null),
      dueDate: new FormControl(new Date(), Validators.required),
      entity: new FormControl(0),
      linkFlag: new FormControl(false),
      linkedSourceProjectId: new FormControl(null),
      masterFlag: new FormControl(false),
      mgaFlag: new FormControl(false),
      postInuredFlag: new FormControl(false),
      projectDescription: new FormControl(null),
      projectId: new FormControl(null),
      projectImportRunId: new FormControl(null),
      projectName: new FormControl(null, [Validators.required, Validators.pattern('^(?!\\s*$).+')]),
      publishFlag: new FormControl(null),
      receptionDate: new FormControl(new Date(), Validators.required),
      rmsModelDataSourceId: new FormControl(null),
      workspaceId: new FormControl(null)
    })
  }
}
