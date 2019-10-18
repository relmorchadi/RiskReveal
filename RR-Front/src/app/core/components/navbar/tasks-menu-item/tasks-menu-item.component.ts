import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {SearchService} from '../../../service/search.service';
import * as _ from 'lodash';
import {Select, Store} from '@ngxs/store';
import {HeaderState, WorkspaceMainState} from '../../../store/states';
import {Observable} from 'rxjs';
/*import {WorkspaceMain} from '../../../model/workspace-main';*/
import {HelperService} from '../../../../shared/helper.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ConfirmationService} from 'primeng/api';
import {DeleteTask, PauseTask, ResumeTask} from '../../../store/actions/header.action';
import * as workspaceActions from '../../../../workspace/store/actions/workspace.actions';
import {WorkspaceState} from '../../../../workspace/store/states';

@Component({
  selector: 'tasks-menu-item',
  templateUrl: './tasks-menu-item.component.html',
  styleUrls: ['./tasks-menu-item.component.scss'],
  providers: [ConfirmationService]
})
export class TasksMenuItemComponent implements OnInit {

  readonly componentName: string = 'job-queue-pop-in';

  visible: boolean;
  typePointer = 'all';
  datePointer = 'all';
  savedTasksLocal: any;

  wsId: any;

/*  @Select(WorkspaceMainState)
  state$: Observable<WorkspaceMain>;
  state: WorkspaceMain = null;*/

  @Select(HeaderState.getJobs) jobs$;
  jobs: any;

  private year: any;

  constructor(private _searchService: SearchService,
              private route: ActivatedRoute, private store: Store,
              private helperService: HelperService, private router: Router,
              private cdRef: ChangeDetectorRef, private confirmationService: ConfirmationService) {
  }

  ngOnInit() {
    this.jobs$.subscribe(value => {
      this.jobs = _.toArray(_.merge({}, value));
      this.savedTasksLocal = [..._.sortBy(_.filter(this.jobs, (dt) => !dt.pending), (dt) => dt.isPaused),
        ..._.filter(this.jobs, (dt) => dt.pending)];
    });
    this._searchService.infodropdown.subscribe(dt => this.visible = this._searchService.getvisibleDropdown());
    this.store.select(WorkspaceState.getCurrentWS).subscribe((ws) => {
      this.wsId = _.get(ws, 'workSpaceId', null);
      this.year = _.get(ws, 'uwYear', null);
    });
    HelperService.headerBarPopinChange$.subscribe( ({from}) => {
      if (from != this.componentName) {
        this.visible = false;
      }
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
    this.savedTasksLocal = this.jobs.filter(item =>
      _.includes(_.toLower(item.cedantName), _.toLower(event.target.value)) ||
      _.includes(_.toLower(item.uwYear + ''), _.toLower(event.target.value)) ||
      _.includes(_.toLower(item.cedantCode), _.toLower(event.target.value)) ||
      _.includes(_.toLower(item.cedantName), _.toLower(event.target.value)) ||
      _.includes(_.toLower(item.workSpaceId), _.toLower(event.target.value)) ||
      _.includes(_.toLower(item.jobOwner), _.toLower(event.target.value)) ||
      _.includes(_.toLower(item.description), _.toLower(event.target.value)) ||
      _.includes(_.toLower(item.workspaceName), _.toLower(event.target.value))
    );
  }

  resumeJob(id) {
    this.store.dispatch(new ResumeTask({id: id}));
  }

  deleteJob(id) {
    this.store.dispatch(new DeleteTask({id: id}));
  }

  pauseJob(id): void {
    this.store.dispatch(new PauseTask({id: id}));
  }

  filterByDate(event) {
    event === 'all' ? this.savedTasksLocal = this.jobs :
      this.savedTasksLocal = this.jobs.filter(dt => dt.date === event);
  }

  filterByType(event) {
    event === 'all' ? this.savedTasksLocal = this.jobs :
      this.savedTasksLocal = this.jobs.filter(dt => dt.jobType === event);
  }

  cancel(): void {

  }

  openWorkspace(wsId, year, routerLink) {
    this.store.dispatch(new workspaceActions.OpenWS({wsId, uwYear: year, route: routerLink, type: 'treaty'}));
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

  togglePopup() {
    HelperService.headerBarPopinChange$.next({from: this.componentName});
  }
}
