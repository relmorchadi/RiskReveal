import {Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute, Router} from '@angular/router';

import {combineLatest, Subject} from 'rxjs';
import {Actions, ofActionSuccessful, Select, Store} from '@ngxs/store';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import {WorkspaceMainState} from '../../../core/store/states/workspace-main.state';

import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import {
  AddNewProject, AddNewProjectFail,
  AddNewProjectSuccess,
  PatchWorkspace,
  SelectProjectAction
} from '../../../core/store/actions/workspace-main.action';
import * as moment from 'moment';
import {takeUntil} from 'rxjs/operators';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-workspace-project',
  templateUrl: './workspace-project.component.html',
  styleUrls: ['./workspace-project.component.scss'],
  providers: [MessageService]
})
export class WorkspaceProjectComponent implements OnInit, OnDestroy {

  unSubscribe$: Subject<void>;
  leftNavbarIsCollapsed = false;
  collapseWorkspaceDetail = true;
  selectedPrStatus = '1';
  private dropdown: NzDropdownContextComponent;
  state: WorkspaceMain = null;
  workspaceUrl: any;
  workspace: any;
  index: any;
  isVisible = false;

  newProject = false;
  existingProject = false;
  mgaProject = false;
  searchWorkspace = false;

  selectedWs: any;

  receptionDate: any;
  dueDate: any;

  description: any;
  newProjectForm: FormGroup;
  @Select(WorkspaceMainState.getData) data$;
  @Select(WorkspaceMainState.getProjects) projects$;


  constructor(private _helper: HelperService, private route: ActivatedRoute,
              private nzDropdownService: NzDropdownService, private store: Store,
              private router: Router, private actions$: Actions, private messageService: MessageService,
  ) {
    console.log('init project');
    this.unSubscribe$ = new Subject<void>();
  }

  ngOnInit() {
    combineLatest(
      this.data$,
      this.route.params
    ).pipe(takeUntil(this.unSubscribe$))
      .subscribe(([data, {wsId, year}]: any) => {

        this.workspaceUrl = {
          wsId,
          uwYear: year
        };

        this.workspace = _.find(data, dt => dt.workSpaceId == wsId && dt.uwYear == year);
        this.index = _.findIndex(data, (dt: any) => dt.workSpaceId == wsId && dt.uwYear == year);
      });
    this.newProjectForm = new FormGroup({
      projectName: new FormControl(null, Validators.required),
      description: new FormControl(null),
      createdBy: new FormControl(null, Validators.required),
      receptionDate: new FormControl(null, Validators.required),
      dueDate: new FormControl(null, Validators.required),
    });
    this.actions$.pipe(ofActionSuccessful(AddNewProjectSuccess)).subscribe(() => {
      this.isVisible = false;
      this.messageService.add({severity: 'info', summary: 'Project added successfully'});
      this.newProjectForm.reset();
      }
    );
    this.actions$.pipe(ofActionSuccessful(AddNewProjectFail)).subscribe(() =>
      this.messageService.add({severity: 'error', summary: ' Error please try again'}));
  }

  handleOk(): void {
    this.isVisible = false;
  }

  handleCancel(): void {
    this.isVisible = false;
    this.resetToMain();
  }

  resetToMain() {
    this.newProject = false;
    this.existingProject = false;
    this.mgaProject = false;
    this.searchWorkspace = false;
  }

  selectProject(project) {
    this.store.dispatch(new SelectProjectAction(project));
  }

  contextMenu($event: MouseEvent, template: TemplateRef<void>): void {
    this.dropdown = this.nzDropdownService.create($event, template);
  }

  close(e: NzMenuItemDirective): void {
    console.log(e);
    this.dropdown.close();
  }

  pinWorkspace() {
    this.store.dispatch(new PatchWorkspace({
      key: ['pinged', 'lastPModified'],
      value: [!this.workspace.pinged, moment().format('x')],
      ws: this.workspace,
      k: this.index
    }));

    let workspaceMenuItem = JSON.parse(localStorage.getItem('workSpaceMenuItem')) || {};

    if (this.workspace.pinged) {
      workspaceMenuItem[this.workspace.workSpaceId + '-' + this.workspace.uwYear] = {
        ...this.workspace,
        pinged: true,
        lastPModified: moment().format('x')
      };
    } else {
      workspaceMenuItem = {
        ...workspaceMenuItem,
        [this.workspace.workSpaceId + '-' + this.workspace.uwYear]: _.omit(workspaceMenuItem[this.workspace.workSpaceId + '-' + this.workspace.uwYear], ['pinged', 'lastPModified'])
      };
    }
    localStorage.setItem('workSpaceMenuItem', JSON.stringify(workspaceMenuItem));
  }

  ngOnDestroy(): void {
    this.unSubscribe$.next();
    this.unSubscribe$.complete();
  }

  onChangeDate(event) {

  }
  createUpdateProject() {
    if (this.newProject) {
      console.log(this.newProjectForm.value);
      this.store.dispatch(new AddNewProject({
        workspaceId: this.workspace.workSpaceId,
        uwYear: this.workspace.uwYear,
        project: {...this.newProjectForm.value, receptionDate: this.formatDateTime(this.newProjectForm.value.receptionDate),
          dueDate: this.formatDateTime(this.newProjectForm.value.dueDate)},
      }));
    }
  }

  cancelCreateProject() {
    this.newProjectForm.reset();
    this.isVisible = false;
  }

  formatDateTime(dateTime: any) {
    moment(dateTime).format('x');
  }

}
