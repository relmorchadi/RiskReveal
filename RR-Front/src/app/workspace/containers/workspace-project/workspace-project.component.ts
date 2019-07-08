import {ChangeDetectorRef, Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute, Router} from '@angular/router';

import {combineLatest, Subject} from 'rxjs';
import {Actions, ofActionSuccessful, Select, Store} from '@ngxs/store';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import {WorkspaceMainState} from '../../../core/store/states/workspace-main.state';

import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import {
  AddNewProjectFail,
  AddNewProjectSuccess, DeleteProject, DeleteProjectFail, DeleteProjectSuccess,
  PatchWorkspace,
  SelectProjectAction
} from '../../../core/store/actions/workspace-main.action';
import * as moment from 'moment';
import {takeUntil} from 'rxjs/operators';
import {LazyLoadEvent, MessageService} from 'primeng/api';
import {NotificationService} from '../../../shared/notification.service';

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
  newProject = false;
  searchWorkspace = false;
  selectedProject: any = null;
  contextMenuProject: any = null;
  description: any;
  @Select(WorkspaceMainState.getData) data$;
  @Select(WorkspaceMainState.getProjects) projects$;
  hyperLinks: string[]= ['projects', 'contract', 'activity'];
  hyperLinksRoutes: any= {
    'projects': '',
    'contract': '/Contract',
    'activity': '/Activity'
  };
  hyperLinksConfig: {
    wsId: string,
    uwYear: string
  };


  constructor(private _helper: HelperService, private route: ActivatedRoute,
              private nzDropdownService: NzDropdownService, private store: Store,
              private router: Router, private actions$: Actions, private notificationService: NotificationService,
              private changeDetector: ChangeDetectorRef
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
    this.actions$.pipe(ofActionSuccessful(AddNewProjectSuccess)).subscribe(() => {
      this.newProject = false;
      this.notificationService.createNotification('Project added successfully', '',
        'success', 'topRight', 4000);
      this._helper.updateWorkspaceItems();
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
        }
    );
  }

  selectProject(project) {
    this.store.dispatch(new SelectProjectAction(project));
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

  detectChanges() {
    if (!this.changeDetector['destroyed']) {
      this.changeDetector.detectChanges();
    }
  }

}
