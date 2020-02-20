import {Component, EventEmitter, HostListener, Input, OnDestroy, OnInit, Output} from '@angular/core';

import {Subject} from 'rxjs';
import {Store} from '@ngxs/store';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import * as _ from 'lodash';
import * as fromWS from '../../../store/actions/workspace.actions'
import {EventListener} from "@angular/core/src/debug/debug_node";

@Component({
  selector: 'app-create-project-popup',
  templateUrl: './create-project-popup.component.html',
  styleUrls: ['./create-project-popup.component.scss'],
})
export class CreateProjectPopupComponent implements OnInit, OnDestroy {

  unSubscribe$: Subject<void>;
  @Output('onCancelCreateProject')
  onCancelCreateProject: EventEmitter<any> = new EventEmitter();
  @Input('workspace') workspace: any;
  @Input('isVisible') isVisible;
  @Input('newProject') newProject: any;
  @Input('edit') editOption: any;
  @Input('editForm') projectForm: any;

  editValue = 'Nathalie Dulac';
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

  updateProject() {
    this.store.dispatch(new fromWS.EditProject({
      data: {
        projectName: this.projectForm.projectName || '',
        assignedTo: 1,
        projectId: this.projectForm.projectId || '',
        projectDescription: this.projectForm.projectDescription || ''
      },
    }))
  }

  checkValid() {
    return this.projectForm.projectName !== '' && this.projectForm.assignedTo !== '';
  }

  cancelCreateProject() {
    this.initNewProjectForm();
    this.onCancelCreateProject.emit(false);
  }

  patchNewProject() {
    this.newProject && this.newProjectForm.patchValue(this.newProject);
  }

  @HostListener('document: keydown.enter', ['$event']) keyBoardEnter() {
    this.editOption ? this.updateProject() : this.createUpdateProject();
  }

  initNewProjectForm() {
    this.newProjectForm = new FormGroup({
      assignedTo: new FormControl(null),
      cloneSourceProjectId: new FormControl(null),
      clonedFlag: new FormControl(false),
      createdBy: new FormControl("Nathalie Dulac", Validators.required),
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
      projectName: new FormControl(null, Validators.required),
      publishFlag: new FormControl(null),
      receptionDate: new FormControl(new Date(), Validators.required),
      rmsModelDataSourceId: new FormControl(null),
      workspaceId: new FormControl(null)
    })
  }
}
