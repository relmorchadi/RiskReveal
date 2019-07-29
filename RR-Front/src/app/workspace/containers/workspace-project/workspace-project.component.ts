import {ChangeDetectorRef, Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {HelperService, NotificationService} from '../../../shared';
import {ActivatedRoute, Router} from '@angular/router';
import {Actions, ofActionSuccessful, Store} from '@ngxs/store';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import {StateSubscriber} from "../../model/state-subscriber";
import * as fromHeader from "../../../core/store/actions/header.action";
import * as fromWs from "../../store/actions";
import {UpdateWsRouting} from "../../store/actions";
import {MessageService} from 'primeng/api';
import {BaseContainer} from "../../../shared/base";
import {Navigate} from "@ngxs/router-plugin";

@Component({
  selector: 'workspace-project',
  templateUrl: './workspace-project.component.html',
  styleUrls: ['./workspace-project.component.scss'],
  providers: [MessageService]
})
export class WorkspaceProjectComponent extends BaseContainer implements OnInit, OnDestroy, StateSubscriber {
  collapseWorkspaceDetail = true;
  selectedPrStatus = '1';
  private dropdown: NzDropdownContextComponent;
  state: WorkspaceMain = null;
  workspaceUrl: any;
  workspace: any;
  index: any;
  isVisible = false;
  wsIdentifier;

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
              private notificationService: NotificationService,
              _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef
  ) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  patchState({wsIdentifier, data}: any): void {
    this.workspace = data;
    this.wsIdentifier = wsIdentifier;

  }


  ngOnInit() {

    this.actions$.pipe(ofActionSuccessful(fromWs.AddNewProjectSuccess)).pipe(this.unsubscribeOnDestroy).subscribe(() => {
        this.newProject = false;
        this.notificationService.createNotification('Project added successfully', '',
          'success', 'topRight', 4000);
        setTimeout(()=> {this.newProject= false; this.detectChanges()},4000);
      }
    );
    this.actions$.pipe(ofActionSuccessful(fromWs.AddNewProjectFail, fromWs.DeleteProjectFails)).pipe(this.unsubscribeOnDestroy).subscribe(() => {
      this.notificationService.createNotification(' Error please try again', '',
        'error', 'topRight', 4000);
    })

    this.actions$.pipe(this.unsubscribeOnDestroy).pipe(ofActionSuccessful(fromWs.DeleteProjectSuccess)).subscribe(() => {
        this.notificationService.createNotification('Project deleted successfully', '',
          'success', 'topRight', 4000);
      }
    );
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

  selectProject(selectionEvent) {
    // console.log('[WsProjects] selection action --> ', selectionEvent);
    const {projectIndex} = selectionEvent;
    this.dispatch(new fromWs.ToggleProjectSelection({projectIndex, wsIdentifier: this.wsIdentifier}));
  }

  delete(project) {
    this.dispatch(new fromWs.DeleteProject({
      wsId: this.workspace.wsId, uwYear: this.workspace.uwYear, project,
    }));
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
    this.dispatch([
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
    this.dispatch(new fromHeader.UnPinWs({wsId, uwYear}));
    this.dispatch(new fromWs.MarkWsAsNonPinned({wsIdentifier: this.wsIdentifier}));
  }

  selectProjectNext(project) {
    this.searchWorkspace = false;
    this.newProject = true;
    this.selectedProject = project;
    console.log(project);
  }

  cancelCreateExistingProjectPopup() {
    this.searchWorkspace = false;
    this.selectedProject = null;
  }

  onCancelCreateProject() {
    this.newProject = false;
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  protected detectChanges() {
    super.detectChanges();
  }

  navigateFromHyperLink({route}) {
    const {wsId, uwYear} = this.workspace;
    this.dispatch(
      [new UpdateWsRouting(this.wsIdentifier, route),
        new Navigate(route ? [`workspace/${wsId}/${uwYear}/${route}`] : [`workspace/${wsId}/${uwYear}/projects`])]
    );
  }

}
