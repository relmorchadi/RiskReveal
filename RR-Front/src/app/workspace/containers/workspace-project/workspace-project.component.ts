import {ChangeDetectorRef, Component, EventEmitter, Input, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {HelperService, NotificationService} from '../../../shared';
import {ActivatedRoute, Router} from '@angular/router';
import {Actions, ofActionSuccessful, Select, Store} from '@ngxs/store';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import * as moment from 'moment'
import {StateSubscriber} from "../../model/state-subscriber";
import * as fromHeader from "../../../core/store/actions/header.action";
import * as fromWs from "../../store/actions";
import {
  AddNewProjectFail,
  AddNewProjectSuccess,
  DeleteProject,
  DeleteProjectFail,
  DeleteProjectSuccess,
  SelectProjectAction
} from '../../../core/store/actions/workspace-main.action';
import {MessageService} from 'primeng/api';
import {BaseComponent, BaseContainer} from "../../../shared/base";
import {HeaderState} from "../../../core/store/states";
import {WorkspaceState} from "../../store/states";
import * as _ from "lodash";

@Component({
  selector: 'workspace-project',
  templateUrl: './workspace-project.component.html',
  styleUrls: ['./workspace-project.component.scss'],
  providers: [MessageService]
})
export class WorkspaceProjectComponent extends BaseContainer implements OnInit, OnDestroy, StateSubscriber  {
  actionsEmitter: EventEmitter<any>;
  // unSubscribe$: Subject<void>;
  collapseWorkspaceDetail = true;
  selectedPrStatus = '1';
  private dropdown: NzDropdownContextComponent;
  state: WorkspaceMain = null;
  workspaceUrl: any;

  index: any;
  isVisible = false;
  wsIdentifier;
  //@Select(WorkspaceState.getData)
  // data$= of();
  //@Select(Workspacetate.getProjects)
  // projects$= of();

  @Select(WorkspaceState.getWorkspaces) workspaces$;
  @Select(WorkspaceState.getCurrentTab) current$;

  current: any = null;
  workspace: any = null;
  pinned: boolean;

  newProject = false;
  existingProject = false;
  mgaProject = false;
  searchWorkspace = false;

  selectedWs: any;

  receptionDate: any;
  dueDate: any;
  contextMenuProject: any = null;
  description: any;
  hyperLinks: string[] = ['Projects', 'Contract', 'Activity'];
  hyperLinksRoutes: any = {
    'Projects': '/projects',
    'Contract': '/Contract',
    'Activity': '/Activity'
  };
  hyperLinksConfig: {
    wsId: string,
    uwYear: string
  };

  selectedProject;

  constructor(private _helper: HelperService,
              private route: ActivatedRoute,
              private nzDropdownService: NzDropdownService,
              private actions$: Actions,
              private messageService: MessageService,
              private changeDetector: ChangeDetectorRef,
              private notificationService:NotificationService,
              _baseStore:Store,_baseRouter: Router, _baseCdr: ChangeDetectorRef
  ) {
    super(_baseRouter, _baseCdr, _baseStore);
    console.log('init project');
    this.actionsEmitter = new EventEmitter();
  }

  patchState({wsIdentifier, data}: any): void {
    this.workspace = data;
    console.log('this is ws data', data);
    this.wsIdentifier = wsIdentifier;
  }


  ngOnInit() {
    this.current$.subscribe(value => this.current = _.merge({}, value));
    this.workspaces$.subscribe(value => {
      this.workspace = _.merge({}, value);
      this.pinned = _.get(_.merge({}, value), `${this.current.wsIdentifier}.isPinned`);
      console.log(_.get(this.workspace, `${this.current.wsIdentifier}`));
    });

    this.actions$.pipe(ofActionSuccessful(AddNewProjectSuccess)).pipe(this.unsubscribeOnDestroy).subscribe(() => {
        this.newProject = false;
        this.notificationService.createNotification('Project added successfully', '',
          'success', 'topRight', 4000);
        this._helper.updateWorkspaceItems();
        // this.detectChanges();
      }
    );
    this.actions$.pipe(ofActionSuccessful(AddNewProjectFail, DeleteProjectFail)).pipe(this.unsubscribeOnDestroy).subscribe(() => {
      this.notificationService.createNotification(' Error please try again', '',
        'error', 'topRight', 4000);
      // this.detectChanges();
    });

    this.actions$.pipe(this.unsubscribeOnDestroy).pipe(ofActionSuccessful(DeleteProjectSuccess)).subscribe(() => {
        this.notificationService.createNotification('Project deleted successfully', '',
          'success', 'topRight', 4000);
        this._helper.updateWorkspaceItems();
        // this.detectChanges();
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
    this.dispatch(new DeleteProject({
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
    const {wsId, uwYear, workspaceName, programName, cedantName} = _.get(this.workspace, `${this.current.wsIdentifier}`);
    console.log(this.pinned);
    this.dispatch(new fromHeader.PinWs({
      wsId, uwYear, workspaceName, programName, cedantName}));
    this.dispatch(new fromWs.MarkWsAsPinned({wsIdentifier: this.current.wsIdentifier}));
    this.detectChanges();
  }

  unPinWorkspace() {
    const {wsId, uwYear} = _.get(this.workspace, `${this.current.wsIdentifier}`);
    this.dispatch(new fromHeader.UnPinWs({wsId, uwYear}));
    this.dispatch(new fromWs.MarkWsAsNonPinned({wsIdentifier: this.current.wsIdentifier}));
    this.detectChanges();
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

  ngOnDestroy(): void {
    this.destroy();
  }

  protected detectChanges() {
    super.detectChanges();
  }

}
