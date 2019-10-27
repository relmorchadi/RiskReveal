import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Subject} from "rxjs";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Store} from '@ngxs/store';
import * as _ from 'lodash';
import * as fromWS from "../../../store/actions/workspace.actions";

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
      project = {...project, linkFlag: true,
        sourceProjectId: this.newProjectForm.value.projectId,
        sourceProjectName: this.newProjectForm.value.name,
        sourceWsId: _.get(this.newProject, 'workspaceId', null),
        sourceWsName: _.get(this.newProject, 'workspaceName', null)
      };
    }
    console.log(project);
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
