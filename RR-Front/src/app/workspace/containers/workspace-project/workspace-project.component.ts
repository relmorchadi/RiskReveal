import {ChangeDetectorRef, Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {HelperService, NotificationService} from '../../../shared';
import {ActivatedRoute, Router} from '@angular/router';
import {Actions, ofActionSuccessful, Select, Store} from '@ngxs/store';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from '../../../core/store/actions/header.action';
import * as fromWs from '../../store/actions';
import {UpdateWsRouting} from '../../store/actions';
import {ConfirmationService, MessageService} from 'primeng/api';
import {BaseContainer} from '../../../shared/base';
import {Navigate} from '@ngxs/router-plugin';
import {debounceTime} from 'rxjs/operators';
import {WorkspaceState} from "../../store/states";

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
  newFacProject = false;
  editOption = false;
  searchWorkspace = false;

  projectForm: any;

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

  @Select(WorkspaceState.getCurrentTabStatus) status$;
  status;

  constructor(private _helper: HelperService,
              private route: ActivatedRoute,
              private nzDropdownService: NzDropdownService,
              private actions$: Actions,
              private messageService: MessageService,
              private changeDetector: ChangeDetectorRef,
              private notificationService: NotificationService,
              private confirmationService: ConfirmationService,
              _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef
  ) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  patchState({wsIdentifier, data}: any): void {
    console.log(data);
    this.workspace = data;
    this.wsIdentifier = wsIdentifier;
  }


  ngOnInit() {
    this.actions$.pipe(ofActionSuccessful(fromWs.AddNewProjectSuccess))
      .pipe(this.unsubscribeOnDestroy, debounceTime(1000))
      .subscribe(() => {
          this.newProject = false;
          this.newFacProject = false;
          this.detectChanges();
          this.notificationService.createNotification('Project added successfully', '',
            'success', 'topRight', 4000);
          // this.detectChanges()
        }
      );
    this.actions$.pipe(ofActionSuccessful(fromWs.EditProject))
      .pipe(this.unsubscribeOnDestroy, debounceTime(1000))
      .subscribe(() => {
          this.newProject = false;
          this.newFacProject = false;
          this.editOption = false;
          this.detectChanges();
          this.notificationService.createNotification('Project Edit successful', '',
            'success', 'topRight', 4000);
          // this.detectChanges()
        }
      );
    this.status$.subscribe(value => this.status = value);
    this.actions$.pipe(ofActionSuccessful(fromWs.AddNewProjectFail, fromWs.DeleteProjectFails)).pipe(this.unsubscribeOnDestroy).subscribe(() => {
      this.notificationService.createNotification(' Error please try again', '',
        'error', 'topRight', 4000);
    });
    this.actions$.pipe(this.unsubscribeOnDestroy).pipe(ofActionSuccessful(fromWs.DeleteProjectSuccess)).subscribe(() => {
        this.notificationService.createNotification('Project deleted successfully', '',
          'success', 'topRight', 4000);
      }
    );
  }

  selectProject(selectionEvent) {
    // console.log('[WsProjects] selection action --> ', selectionEvent);
    const {projectIndex} = selectionEvent;
    this.dispatch(new fromWs.ToggleProjectSelection({projectIndex, wsIdentifier: this.wsIdentifier}));
  }

  delete(projectId) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete this project?',
      accept: () => {
        this.dispatch(new fromWs.DeleteProject({
          wsId: this.workspace.wsId, uwYear: this.workspace.uwYear, projectId,
        }));
      }
    });
  }

  deleteFacProject(item) {
    this.dispatch(new fromWs.DeleteFacProject(item.project));
  }

  edit(project$) {
    this.editOption = true;
    this.newProject = true;
    this.projectForm = {...project$.project, dueDate: new Date(project$.project.dueDate), receptionDate: new Date(project$.project.receptionDate)};
  }

  contextMenu($event: MouseEvent, template: TemplateRef<void>, project): void {
    this.contextMenuProject = project;
    this.dropdown = this.nzDropdownService.create($event, template);
  }

  close(e: NzMenuItemDirective): void {
    this.dropdown.close();
  }

  pinWorkspace() {
    this.dispatch([new fromHeader.TogglePinnedWsState({
      "userId": 1,
      "workspaceContextCode": this.workspace.wsId,
      "workspaceUwYear": this.workspace.uwYear
    })]);
  }

  selectProjectNext(project) {
    this.searchWorkspace = false;
    this.newProject = true;
    this.selectedProject = project;
    console.log(project);
  }

  cancelCreateExistingProjectPopup(param) {
    console.log('Cancel', param);
    this.searchWorkspace = false;
  }

  onCancelCreateProject() {
    this.newProject = false;
    this.newFacProject = false;
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
