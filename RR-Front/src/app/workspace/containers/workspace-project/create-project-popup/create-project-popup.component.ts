import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';

import {Subject} from 'rxjs';
import {Store} from '@ngxs/store';
import {AddNewProject} from '../../../../core/store/actions';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-create-project-popup',
  templateUrl: './create-project-popup.component.html',
  styleUrls: ['./create-project-popup.component.scss'],
})
export class CreateProjectPopupComponent implements OnInit, OnDestroy {

  unSubscribe$: Subject<void>;
  @Output('onCancelCreateProject')
  onCancelCreateProject: EventEmitter<any> = new EventEmitter();
  @Input()
  workspace: any;
  @Input()
  isVisible;
  @Input()
  newProject: any;
  newProjectForm: FormGroup;

  constructor(private store: Store) {this.unSubscribe$ = new Subject<void>(); }

  ngOnInit() {
    this.initNewProjectForm();
  }

  ngOnDestroy(): void {
    this.unSubscribe$.next();
    this.unSubscribe$.complete();
  }

  createUpdateProject() {
    let project = {...this.newProjectForm.value , projectId: null}
    if (this.newProjectForm.controls.projectId.value) {
      project = {...project, linkFlag: true};
    }
    this.store.dispatch(new AddNewProject({
        workspaceId: this.workspace.workSpaceId,
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
    });
  }
}
