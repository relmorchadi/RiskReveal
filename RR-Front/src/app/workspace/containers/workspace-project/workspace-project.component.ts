import {Component, EventEmitter, Input, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {HelperService} from '../../../shared';
import * as _ from 'lodash';
import {ActivatedRoute, Router} from '@angular/router';
import {Subject} from 'rxjs';
import {Select, Store} from '@ngxs/store';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import {PatchWorkspace, SelectProjectAction} from '../../../core/store/actions/workspace-main.action';
import * as moment from 'moment'
import {takeUntil} from "rxjs/operators";
import {StateSubscriber} from "../../model/state-subscriber";
import * as fromHeader from "../../../core/store/actions/header.action";
import * as fromWs from "../../store/actions";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {
  AddNewProjectFail,
  AddNewProjectSuccess,
  DeleteProject,
  DeleteProjectFail,
  DeleteProjectSuccess,
  PatchWorkspace,
  SelectProjectAction
} from '../../../core/store/actions/workspace-main.action';
import * as moment from 'moment';
import {takeUntil} from 'rxjs/operators';
import {MessageService} from 'primeng/api';
import {NotificationService} from '../../../shared/notification.service';

@Component({
  selector: 'workspace-project',
  templateUrl: './workspace-project.component.html',
  styleUrls: ['./workspace-project.component.scss'],
  providers: [MessageService]
})
export class WorkspaceProjectComponent implements OnInit, OnDestroy, StateSubscriber {
  actionsEmitter: EventEmitter<any>;
  // unSubscribe$: Subject<void>;
  collapseWorkspaceDetail = true;
  selectedPrStatus = '1';
  private dropdown: NzDropdownContextComponent;
  state: WorkspaceMain = null;
  workspaceUrl: any;
  workspace: any;
  index: any;
  isVisible = false;
  wsIdentifier;
  //@Select(WorkspaceState.getData)
  // data$= of();
  //@Select(Workspacetate.getProjects)
  // projects$= of();


  newProject = false;
  existingProject = false;
  mgaProject = false;
  searchWorkspace = false;

  selectedWs: any;

  receptionDate: any;
  dueDate: any;
  contextMenuProject: any = null;
  description: any;
  hyperLinks: string[]= ['Projects', 'Contract', 'Activity'];
  hyperLinksRoutes: any= {
    'Projects': '',
    'Contract': '/Contract',
    'Activity': '/Activity'
  };
  hyperLinksConfig: {
    wsId: string,
    uwYear: string
  };


  constructor(private _helper: HelperService, private route: ActivatedRoute,
              private nzDropdownService: NzDropdownService, private store: Store,
              private router: Router, private actions$: Actions, private messageService: MessageService,
              private changeDetector: ChangeDetectorRef
  ) {
    console.log('init project');
    this.actionsEmitter = new EventEmitter();
  }

  patchState({wsIdentifier, data}: any): void {
    this.workspace = data;
    this.wsIdentifier = wsIdentifier;
  }


  ngOnInit() {

    this.actions$.pipe(ofActionSuccessful(AddNewProjectSuccess)).subscribe(() => {
        this.newProject = false;
        this.notificationService.createNotification('Project added successfully', '',
          'success', 'topRight', 4000);
        this._helper.updateWorkspaceItems();
        this.detectChanges();
      }
    );
    this.actions$.pipe(ofActionSuccessful(AddNewProjectFail, DeleteProjectFail)).subscribe(() => {
      this.notificationService.createNotification(' Error please try again', '',
        'error', 'topRight', 4000);
      this.detectChanges();
    })

    this.actions$.pipe(ofActionSuccessful(DeleteProjectSuccess)).subscribe(() => {
        this.notificationService.createNotification('Project deleted successfully', '',
          'success', 'topRight', 4000);
        this._helper.updateWorkspaceItems();
        this.detectChanges();
      }
    );
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
    this.actionsEmitter.emit(new SelectProjectAction(project));
  }

  delete(project) {
    this.store.dispatch(new DeleteProject({
      workspaceId: this.workspace.workSpaceId, uwYear: this.workspace.uwYear, project,
    }));
    this.dropdown.close();
  }

  contextMenu($event: MouseEvent, template: TemplateRef<void>, project): void {
    this.contextMenuProject = project;
    this.dropdown = this.nzDropdownService.create($event, template);
  }

  close(e: NzMenuItemDirective): void {
    this.dropdown.close();
  }

  pinWorkspace() {
    const {wsId, uwYear, workspaceName, programName, cedantName} = this.workspace;
    this.actionsEmitter.emit([
      new fromHeader.PinWs({
        wsId,
        uwYear,
        workspaceName,
        programName,
        cedantName
      }), new fromWs.MarkWsAsPinned({wsIdentifier: this.wsIdentifier})]);
  }

  unPinWorkspace() {
    const {wsId, uwYear} = this.workspace;
    this.actionsEmitter.emit([
      new fromHeader.UnPinWs({wsId, uwYear}),
      new fromWs.MarkWsAsNonPinned({wsIdentifier: this.wsIdentifier})
    ])
  }

  ngOnDestroy(): void {
    this.unSubscribe$.next();
    this.unSubscribe$.complete();
  }

  selectProjectNext(project) {
    this.searchWorkspace = false;
    this.newProject = true;
    this.selectedProject = project;
  }

  cancelCreateExistingProjectPopup() {
    this.searchWorkspace = false;
    this.selectedProject = null;
  }

  onCancelCreateProject() {
    this.newProject = false;
  }

  formatDateTime(dateTime: any) {
    moment(dateTime).format('x');
  }

}
