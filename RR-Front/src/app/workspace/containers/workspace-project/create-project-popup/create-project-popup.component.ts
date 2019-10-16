import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';

import {Subject} from 'rxjs';
import {Store} from '@ngxs/store';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import * as _ from 'lodash';
import * as fromWS from '../../../store/actions/workspace.actions'

@Component({
  selector: 'app-create-project-popup',
  templateUrl: './create-project-popup.component.html',
  styleUrls: ['./create-project-popup.component.scss'],
})
export class CreateProjectPopupComponent implements OnInit, OnDestroy {

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

  constructor(private store: Store) {
    this.unSubscribe$ = new Subject<void>();
  }

  ngOnInit() {
    this.initNewProjectForm();
  }

  ngOnDestroy(): void {
    this.unSubscribe$.next();
    this.unSubscribe$.complete();
  }

  createUpdateProject() {
    let project = {...this.newProjectForm.value, projectId: null};
    console.log('This is data', this);
    if (this.newProjectForm.controls.projectId.value) {
      project = {
        ...project,
        linkFlag: true,
        cloneSourceProjectId: this.newProjectForm.value.projectId,
      };
    }
    this.store.dispatch(new fromWS.AddNewProject({
      id: _.get(this.workspace, 'id', null),
      wsId: this.workspace.wsId,
      uwYear: this.workspace.uwYear,
      project
    }));
  }

  cancelCreateProject() {
    this.initNewProjectForm();
    this.onCancelCreateProject.emit(false);
  }

  patchNewProject() {
    this.newProject && this.newProjectForm.patchValue(this.newProject);
  }

  initNewProjectForm() {
    console.log(this.workspace)
    this.newProjectForm = new FormGroup({
      assignedTo: new FormControl(null),
      cloneSourceProjectId: new FormControl(null),
      clonedFlag: new FormControl(false),
      createdBy: new FormControl("Nathalie Dulac", Validators.required),
      creationDate: new FormControl(null),
      deleted: new FormControl(false),
      deletedBy: new FormControl(null),
      deletedDue: new FormControl(null),
      deletedOn: new FormControl(null),
      dueDate: new FormControl(null),
      entity: new FormControl(0),
      linkFlag: new FormControl(false),
      linkedSourceProjectId: new FormControl(null),
      masterFlag: new FormControl(false),
      mgaFlag: new FormControl(false),
      postInuredFlag: new FormControl(false),
      projectDescription: new FormControl(null, Validators.required),
      projectId: new FormControl(null),
      projectImportRunId: new FormControl(null),
      projectName: new FormControl(null, Validators.required),
      publishFlag: new FormControl(null),
      receptionDate: new FormControl(null),
      rmsModelDataSourceId: new FormControl(null),
      workspaceId: new FormControl(null)
    })
  }
}
