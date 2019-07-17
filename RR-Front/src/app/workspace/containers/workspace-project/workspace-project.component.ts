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
    })

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
