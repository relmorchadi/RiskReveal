import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {SearchService} from '../../../service/search.service';
import * as _ from 'lodash';
import {
  AppendNewWorkspaceMainAction,
  PatchWorkspaceMainStateAction, SelectWorkspaceAction
} from '../../../store/actions/workspace-main.action';
import {Select, Store} from '@ngxs/store';
import {WorkspaceMainState} from '../../../store/states';
import {Observable} from 'rxjs';
import {WorkspaceMain} from '../../../model/workspace-main';
import {Location} from '@angular/common';
import {HelperService} from '../../../../shared/helper.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ConfirmationService} from 'primeng/api';

@Component({
  selector: 'tasks-menu-item',
  templateUrl: './tasks-menu-item.component.html',
  styleUrls: ['./tasks-menu-item.component.scss'],
  providers: [ConfirmationService]
})
export class TasksMenuItemComponent implements OnInit {

  formatter = (_) => '';
  visible: boolean;
  typePointer = 'all';
  datePointer = 'all';
  savedTasksLocal: any;

  wsId: any;

  tasks = {
    active: [
      {
        id: 1,
        progress: 75,
        name: 'SCOR SE',
        year: '2018',
        description: 'SBS-SCOR ASIA PACIF...',
        duration: '5 min remaining',
        specific: {type: 'Import', link: 'RiskLink', date: 'today',  id: '00F0006'},
        append: false,
        isPaused: false,
        pending: false,
        data: {
          workSpaceId: '00F0006',
          uwYear: 2018,
          workspaceName: 'SBS-SCOR ASIA PACIF.-BEIJING',
          cedantCode: ' 22231',
          cedantName: 'SCOR SE',
        },
        status: {completed: 0, total: 3},
        content: [{
            progress: 90,
            projectId: 'P-000004970',
            contentId: 'For ARC',
            contentName: 'For ARC',
            createdAt: 1542882354617,
            duration: '1 min remaining',
            createdBy: 'Ghada CHOUK'
          },
          {
            progress: 60,
            projectId: 'P-000004971',
            contentId: 'For ARC',
            contentName: 'Clone',
            createdAt: 1542882615080,
            duration: '3 min remaining',
            createdBy: 'Ghada CHOUK'
          },
          {
            progress: 50,
            projectId: 'P-000004971',
            contentId: 'For ARC',
            contentName: 'Clone',
            createdAt: 1542882354617,
            duration: '5 min remaining',
            createdBy: 'Ghada CHOUK'
          }]
      },
      {
        id: 2,
        progress: 50,
        name: 'NFU MUTUAL',
        year: '2017',
        description: 'NFU Mtr & Liab XL',
        duration: '1 min remaining',
        specific: {type: 'Calibration', link: 'Calibration', date: 'today', id: '01P4134'},
        append: false,
        isPaused: false,
        pending: false,
        data: {
          workSpaceId: '01P4134',
          uwYear: 2017,
          workspaceName: 'NFU Mtr & Liab XL',
          cedantCode: '11963',
          cedantName: 'NFU MUTUAL'
        },
        status: {completed: 0, total: 1},
        content: [{
            progress: 50,
            projectId: 'P-000004971',
            contentId: 'For ARC',
            contentName: 'Clone',
            createdAt: 1542882354617,
            duration: '5 min remaining',
            createdBy: 'Ghada CHOUK'
          }]
      },
      {
        id: 3,
        progress: 25,
        name: 'NFU MUTUAL',
        year: '2017',
        description: 'NFU PROPERTY CAT UK',
        duration: '1 min remaining',
        specific: {type: 'Inuring', link: 'Inuring', date: 'yesterday', id: '01P4466'},
        append: false,
        isPaused: false,
        pending: false,
        data: {
          workSpaceId: '01P4466',
          uwYear: 2017,
          workspaceName: 'NFU PROPERTY CAT UK',
          cedantCode: '11963',
          cedantName: 'NFU MUTUAL',
        },
        status: {completed: 1, total:2},
        content: [{
            progress: 0,
            projectId: 'P-000004971',
            contentId: 'For ARC',
            contentName: 'Clone',
            createdAt: 1542882354617,
            duration: '10 min remaining',
            createdBy: 'Ghada CHOUK'
          },
          {
            progress: 100,
            projectId: 'P-000004971',
            contentId: 'For ARC',
            contentName: 'Clone',
            createdAt: 1542882354617,
            duration: 'Processed',
            createdBy: 'Ghada CHOUK'
          }]
      },
      {
        id: 4,
        progress: 0,
        name: 'CALIFORNIA EQ AUTHORITY',
        year: '2016',
        description: 'CEA Program: Private Plac',
        duration: '3 min remaining',
        specific: {type: 'Import', link: 'RiskLink', date: 'lastWeek', id: 'TP05413 '},
        append: false,
        isPaused: true,
        pending: true,
        data: {
          workSpaceId: 'TP05413',
          uwYear: 2016,
          workspaceName: 'CEA Program: Private Placement',
          cedantCode: '72389',
          cedantName: 'CALIFORNIA EQ AUTHORITY',
        },
        status: {completed: 2, total:3},
        content: [{
            progress: 60,
            projectId: 'P-000004971',
            contentId: 'For ARC',
            contentName: 'Clone',
            createdAt: 1542882354617,
            duration: '5 min remaining',
            createdBy: 'Ghada CHOUK'
          },
          {
            progress: 100,
            projectId: 'P-000004971',
            contentId: 'For ARC',
            contentName: 'Clone',
            createdAt: 1542882354617,
            duration: 'Processed',
            createdBy: 'Ghada CHOUK'
          },
          {
            progress: 100,
            projectId: 'P-000004971',
            contentId: 'For ARC',
            contentName: 'Clone',
            createdAt: 1542882354617,
            duration: 'Processed',
            createdBy: 'Ghada CHOUK'
          }],
      },
      {
        id: 5,
        progress: 0,
        name: 'SCOR SE',
        year: '2019',
        description: 'CFS-SCOR ITALIA-SCOR',
        duration: '12 min remaining',
        specific: {type: 'Calibration', link: 'Calibration', date: 'lastWeek', id: '00C0024'},
        append: false,
        isPaused: true,
        pending: true,
        data: {
          workSpaceId: '00C0024',
          uwYear: 2019,
          workspaceName: 'CFS-SCOR ITALIA-SCOR ITALIA',
          cedantCode: '22231',
          cedantName: 'SCOR SE',
        },
        status: {completed: 0, total: 2},
        content: [{
            progress: 75,
            projectId: 'P-000004971',
            contentId: 'For ARC',
            contentName: 'Clone',
            createdAt: 1542882354617,
            duration: '2 min remaining',
            createdBy: 'Ghada CHOUK'
          },
          {
            progress: 20,
            projectId: 'P-000004971',
            contentId: 'For ARC',
            contentName: 'Clone',
            createdAt: 1542882354617,
            duration: '7 min remaining',
            createdBy: 'Ghada CHOUK'
          }],
      },
    ]
  };

  @Select(WorkspaceMainState)
  state$: Observable<WorkspaceMain>;
  state: WorkspaceMain = null;
  private year: any;
  constructor(private _searchService: SearchService,
              private route: ActivatedRoute, private store: Store,
              private helperService: HelperService, private router: Router,
              private cdRef: ChangeDetectorRef, private confirmationService: ConfirmationService) {
  }

  ngOnInit() {
    this.state$.subscribe(value => this.state = _.merge({}, value));
    this._searchService.infodropdown.subscribe(dt => this.visible = this._searchService.getvisibleDropdown());
    this.savedTasksLocal = { active: [...this.tasks.active]};
    this.store.select(WorkspaceMainState.getCurrentWS).subscribe( (ws) => {
      this.wsId = _.get(ws, 'workSpaceId', null);
      this.year = _.get(ws, 'uwYear', null);
    })
  }

  private searchData(id, year) {
    return this._searchService.searchWorkspace(id || '', year || '2019');
  }

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
  }

  toggleActiveTask(activeTask) {
    activeTask.append = !activeTask.append;
  }

  searchJobs(event) {
    this.savedTasksLocal.active = this.tasks.active.filter(text => _.includes(text.name, event.target.value));
  }

  resumeJob(id) {
    this.tasks.active.map(dt => {
      if (dt.id === id) {
        dt.isPaused = false;
      }
    });
    this.tasks.active = _.sortBy(this.tasks.active, (dt) => dt.isPaused);
    this.savedTasksLocal = { active: [...this.tasks.active]};
  }

  deleteJob(id) {
    this.tasks.active = this.tasks.active.filter(dt => dt.id !== id);
    this.savedTasksLocal = { active: [...this.tasks.active]};
  }

  pauseJob(id): void {
    this.tasks.active.map(dt => {
      if (dt.id === id) {
        dt.isPaused = true;
      }
    });
    this.tasks.active = _.sortBy(this.tasks.active, (dt) => dt.isPaused);
    this.savedTasksLocal = { active: [...this.tasks.active]};
  }

  filterByDate(event) {
    event === 'all' ? this.savedTasksLocal.active = this.tasks.active :
      this.savedTasksLocal.active = this.tasks.active.filter(dt => dt.specific.date === event);
  }

  filterByType(event) {
    event === 'all' ? this.savedTasksLocal.active = this.tasks.active :
      this.savedTasksLocal.active = this.tasks.active.filter(dt => dt.specific.type === event);
  }

  cancel(): void {

  }

  openWorkspace(wsId, year, routerLink) {
    this.searchData(wsId, year).subscribe(
      (dt: any) => {
        const workspace = {
          workSpaceId: wsId,
          uwYear: year,
          selected: false,
          ...dt
        };
        workspace.projects = workspace.projects.map(prj => prj = {...prj, selected: false});
        let alreadyOpened = this.state.openedTabs.data.filter(ws => ws.workSpaceId === wsId && ws.uwYear == year);
        let index = _.findIndex(this.state.openedTabs.data, ws => ws.workSpaceId === wsId && ws.uwYear == year);
        if (alreadyOpened.length > 0) {
          this.store.dispatch(new PatchWorkspaceMainStateAction([
            {key: 'openedWs', value: _.merge({}, alreadyOpened[0], {routing: routerLink})},
            {key: 'openedTabs', value: {data: this.state.openedTabs.data, tabsIndex: index}}]));
          this.store.dispatch(new SelectWorkspaceAction(_.merge({}, alreadyOpened[0], {routing: routerLink})));
          this.helperService.updateRecentWorkspaces();
          this.helperService.updateWorkspaceItems();
          this.navigateToTab(this.state.openedTabs.data[this.state.openedTabs.tabsIndex]);
        } else {
          this.store.dispatch(new AppendNewWorkspaceMainAction(workspace));
          alreadyOpened = this.state.openedTabs.data.filter(ws => ws.workSpaceId === wsId && ws.uwYear == year);
          index = _.findIndex(this.state.openedTabs.data, ws => ws.workSpaceId === wsId && ws.uwYear == year);
          this.store.dispatch(new SelectWorkspaceAction(_.merge({}, alreadyOpened[0], {routing: routerLink})));
          this.helperService.updateRecentWorkspaces();
          this.helperService.updateWorkspaceItems();
          this.navigateToTab(this.state.openedTabs.data[this.state.openedTabs.data.length - 1]);
        }
        this.visible = false;
      }
    );
  }

  navigateToTab(value) {
    if (value.routing == 0) {
      this.router.navigate([`workspace/${value.workSpaceId}/${value.uwYear}`]);
    } else {
      this.router.navigate([`workspace/${value.workSpaceId}/${value.uwYear}/${value.routing}`]);
    }
  }

  navigateToJOBManager() {
    this.router.navigateByUrl(`/jobManager`);
    this.visible = false;
  }
}
