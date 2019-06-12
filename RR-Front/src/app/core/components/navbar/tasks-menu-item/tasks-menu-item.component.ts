import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {SearchService} from '../../../service/search.service';
import * as _ from 'lodash';
import {
  AppendNewWorkspaceMainAction,
  PatchWorkspaceMainStateAction, SetWsRoutingAction
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
  visible: boolean;
  typePointer = 'all';
  datePointer = 'all';
  savedTasksLocal: any;

  wsId: any;

  tasks = [
    {
      id: 1,
      selected: false,
      progress: 75,
      cedantName: 'SCOR SE',
      cedantCode: ' 22231',
      description: 'SBS-SCOR ASIA PACIF...',
      duration: '5 min remaining',
      jobId: '001',
      linkTo: 'RiskLink',
      date: 'today',
      workSpaceId: '00F0006',
      uwYear: 2018,
      workspaceName: 'SBS-SCOR ASIA PACIF.-BEIJING',
      jobOwner: 'Rim Benabbes',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      append: false,
      isPaused: false,
      pending: false,
      priority: 'low',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10',
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
      selected: false,
      progress: 50,
      cedantName: 'NFU MUTUAL',
      cedantCode: '11963',
      description: 'NFU Mtr & Liab XL',
      duration: '1 min remaining',
      jobId: '002',
      linkTo: 'Calibration',
      date: 'today',
      workSpaceId: '01P4134',
      uwYear: 2017,
      workspaceName: 'NFU Mtr & Liab XL',
      jobOwner: 'Amina Cheref',
      jobType: 'Calibration',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      append: false,
      isPaused: false,
      pending: false,
      priority: 'medium',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10',
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
      selected: false,
      progress: 25,
      cedantName: 'NFU MUTUAL',
      cedantCode: '11963',
      description: 'NFU PROPERTY CAT UK',
      duration: '1 min remaining',
      jobId: '003',
      linkTo: 'Inuring',
      date: 'yesterday',
      workSpaceId: '01P4466',
      uwYear: 2017,
      workspaceName: 'NFU PROPERTY CAT UK',
      jobOwner: 'Rim Benabbes',
      jobType: 'Inuring',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      append: false,
      isPaused: false,
      pending: false,
      priority: 'high',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10',
      status: {completed: 1, total: 2},
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
      selected: false,
      progress: 0,
      cedantName: 'CALIFORNIA EQ AUTHORITY',
      cedantCode: '72389',
      description: 'CEA Program: Private Plac',
      duration: '3 min remaining',
      jobId: '001',
      linkTo: 'RiskLink',
      date: 'lastWeek',
      workSpaceId: 'TP05413',
      uwYear: 2016,
      workspaceName: 'CEA Program: Private Placement',
      jobOwner: 'Rim Benabbes',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      append: false,
      isPaused: true,
      pending: true,
      priority: 'low',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10',
      status: {completed: 2, total: 3},
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
        }]
    },
    {
      id: 5,
      selected: false,
      progress: 0,
      cedantName: 'SCOR SE',
      cedantCode: '22231',
      description: 'CFS-SCOR ITALIA-SCOR',
      duration: '12 min remaining',
      jobId: '004',
      linkTo: 'Inuring',
      date: 'lastWeek',
      workSpaceId: 'TP05413',
      uwYear: 2019,
      workspaceName: 'CFS-SCOR ITALIA-SCOR ITALIA',
      jobOwner: 'Rim Benabbes',
      jobType: 'Inuring',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      append: false,
      isPaused: true,
      pending: true,
      priority: 'low',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10',
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
        }]
    },
    {
      id: 6,
      selected: false,
      progress: 30,
      cedantName: 'SCOR SE',
      cedantCode: '22231',
      description: 'CFS-SCOR ITALIA-SCOR',
      duration: '12 min remaining',
      jobId: '001',
      linkTo: 'Calibration',
      date: 'lastWeek',
      workSpaceId: 'TP05413',
      uwYear: 2019,
      workspaceName: 'CFS-SCOR ITALIA-SCOR ITALIA',
      jobOwner: 'Amine Cheref',
      jobType: 'Calibration',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      append: false,
      isPaused: false,
      pending: true,
      priority: 'high',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10',
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
        }]
    },
    {
      id: 7,
      selected: false,
      progress: 40,
      cedantName: 'SCOR SE',
      cedantCode: '22231',
      description: 'CFS-SCOR ITALIA-SCOR',
      duration: '12 min remaining',
      jobId: '001',
      linkTo: 'RiskLink',
      date: 'lastWeek',
      workSpaceId: 'TP05413',
      uwYear: 2019,
      workspaceName: 'CFS-SCOR ITALIA-SCOR ITALIA',
      jobOwner: 'Rim Benabbes',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      append: false,
      isPaused: false,
      pending: true,
      priority: 'high',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10',
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
        }]
    },
  ];


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
    this.savedTasksLocal = [...this.tasks];
    this.store.select(WorkspaceMainState.getCurrentWS).subscribe((ws) => {
      this.wsId = _.get(ws, 'workSpaceId', null);
      this.year = _.get(ws, 'uwYear', null);
    });
  }

  formatter = (_) => '';

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
    this.savedTasksLocal = this.tasks.filter(text => _.includes(_.toLower(text.cedantName), _.toLower(event.target.value)));
  }

  resumeJob(id) {
    this.tasks.map(dt => {
      if (dt.id === id) {
        dt.isPaused = false;
      }
    });
    this.tasks = [..._.sortBy(_.filter(this.tasks, (dt) => !dt.pending) , (dt) => dt.isPaused),
      ..._.filter(this.tasks, (dt) => dt.pending)];
    this.savedTasksLocal = [...this.tasks];
  }

  deleteJob(id) {
    this.tasks = this.tasks.filter(dt => dt.id !== id);
    this.savedTasksLocal = [...this.tasks];
  }

  pauseJob(id): void {
    this.tasks.map(dt => {
      if (dt.id === id) {
        dt.isPaused = true;
      }
    });
    this.tasks = [..._.sortBy(_.filter(this.tasks, (dt) => !dt.pending) , (dt) => dt.isPaused),
      ..._.filter(this.tasks, (dt) => dt.pending)];
    this.savedTasksLocal = [...this.tasks];
  }

  filterByDate(event) {
    event === 'all' ? this.savedTasksLocal = this.tasks :
      this.savedTasksLocal = this.tasks.filter(dt => dt.date === event);
  }

  filterByType(event) {
    event === 'all' ? this.savedTasksLocal = this.tasks :
      this.savedTasksLocal = this.tasks.filter(dt => dt.jobType === event);
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
          this.store.dispatch(new SetWsRoutingAction(_.merge({}, alreadyOpened[0], {routing: routerLink})));
          this.helperService.updateRecentWorkspaces();
          this.helperService.updateWorkspaceItems();
          this.navigateToTab(this.state.openedTabs.data[this.state.openedTabs.tabsIndex]);
        } else {
          this.store.dispatch(new AppendNewWorkspaceMainAction(workspace));
          alreadyOpened = this.state.openedTabs.data.filter(ws => ws.workSpaceId === wsId && ws.uwYear == year);
          index = _.findIndex(this.state.openedTabs.data, ws => ws.workSpaceId === wsId && ws.uwYear == year);
          this.store.dispatch(new SetWsRoutingAction(_.merge({}, alreadyOpened[0], {routing: routerLink})));
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
