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
  AddNewProject, AddNewProjectFail,
  AddNewProjectSuccess, DeleteProject, DeleteProjectFail, DeleteProjectSuccess,
  PatchWorkspace,
  SelectProjectAction
} from '../../../core/store/actions/workspace-main.action';
import * as moment from 'moment';
import {takeUntil} from 'rxjs/operators';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {LazyLoadEvent, MessageService} from 'primeng/api';
import {SearchService} from '../../../core/service';
import {Debounce} from '../../../shared/decorators';
import {SearchNavBarState} from '../../../core/store/states';

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
  selectedWorkspace: any = null;
  selectedWorkspaceProjects: any = null;
  selectExistingProject = false;
  selectedProject: any = null;
  stepSelectWorkspace = true;
  stepSelectProject = false;
  selectedWs: any;
  globalSearchItem = '';
  private _filter = {};
  columns = [
    {
      field: 'countryName',
      header: 'Country',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'countryName'
    },
    {
      field: 'cedantName',
      header: 'Cedent Name',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'cedantName'
    },
    {
      field: 'cedantCode',
      header: 'Cedant Code',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'cedantCode'
    },
    {
      field: 'uwYear',
      header: 'Uw Year',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'year'
    },
    {
      field: 'workspaceName',
      header: 'Workspace Name',
      width: '160px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'workspaceName'
    },
    {
      field: 'workSpaceId',
      header: 'Workspace Context',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'workspaceId'
    }
  ];
  paginationOption = {currentPage: 0, page: 0, size: 40, total: '-'};
  contracts = [];
  loading
  receptionDate: any;
  dueDate: any;
  contextMenuProject: any = null;
  description: any;
  newProjectForm: FormGroup;
  @Select(WorkspaceMainState.getData) data$;
  @Select(WorkspaceMainState.getProjects) projects$;


  constructor(private _helper: HelperService, private route: ActivatedRoute,
              private nzDropdownService: NzDropdownService, private store: Store,
              private router: Router, private actions$: Actions, private messageService: MessageService,
              private changeDetector: ChangeDetectorRef, private _searchService: SearchService
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
    this.initNewProjectForm();
    this.actions$.pipe(ofActionSuccessful(AddNewProjectSuccess)).subscribe(() => {
      this.newProject = false;
      this.messageService.add({severity: 'info', summary: 'Project added successfully'});
      this.initNewProjectForm();
      this._helper.updateWorkspaceItems();
      }
    );
    this.actions$.pipe(ofActionSuccessful(AddNewProjectFail, DeleteProjectFail)).subscribe(() => {
        this.messageService.add({severity: 'error', summary: ' Error please try again'});
        this.changeDetector.detectChanges();
      })

    this.actions$.pipe(ofActionSuccessful(DeleteProjectSuccess)).subscribe(() => {
          this.messageService.add({severity: 'info', summary: 'Project deleted successfully'});
          this._helper.updateWorkspaceItems();
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
    this.stepSelectWorkspace = true;
    this.stepSelectProject = false;
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

  onChangeDate(event) {

  }
  createUpdateProject() {
    if (this.newProject) {
      console.log(this.newProjectForm.value);
      this.store.dispatch(new AddNewProject({
        workspaceId: this.workspace.workSpaceId,
        uwYear: this.workspace.uwYear,
        project: {...this.newProjectForm.value , projectId: null},
      }));
    }
  }

  cancelCreateProject() {
    this.initNewProjectForm();
    this.newProject = false;
  }

  formatDateTime(dateTime: any) {
    moment(dateTime).format('x');
  }

  initNewProjectForm() {
    this.newProjectForm = new FormGroup({
      projectId: new FormControl(null),
      name: new FormControl(null, Validators.required),
      description: new FormControl(null),
      createdBy: new FormControl('Nathalie Dulac', Validators.required),
      receptionDate: new FormControl(new Date(), Validators.required),
      dueDate: new FormControl(new Date(), Validators.required),
      assignedTo: new FormControl(null),
      linkFlag: new FormControl(null),
      postInuredFlag: new FormControl(null),
      publishFlag: new FormControl(null),
      pltSum: new FormControl(0),
      pltThreadSum: new FormControl(0),
      regionPerilSum: new FormControl(0),
      xactSum: new FormControl(0),
      locking: new FormControl(null),
    });
  }

  openWorkspaceInSlider(event?) { console.log(event); }
  @Debounce(500)
  filterData($event, target) {
    this._filter = {...this._filter, [target]: $event || null};
    this._loadData();
  }
  openWorkspace(workSpaceId, uwYear) {}

  private _loadData(offset = '0', size = '100') {
    this.loading = true;
    const params = {
      keyword: this.globalSearchItem,
       filter: this.filter,
      offset,
      size
    };
    this._searchService.expertModeSearch(params)
      .subscribe((data: any) => {
        this.contracts = data.content.map(item => ({...item, selected: false}));
        this.loading = false;
        this.paginationOption = {
          ...this.paginationOption,
          page: data.number,
          size: data.numberOfElements,
          total: data.totalElements
        };
        this.changeDetector.detectChanges();
      });
  }

  loadMore(event: LazyLoadEvent) {
    this.paginationOption.currentPage = event.first;
    this._loadData(String(event.first));
  }

  get filter() {
    // let tags = _.isString(this.searchContent) ? [] : (this.searchContent || []);
    const tableFilter = _.map(this._filter, (value, key) => ({key, value}));
    // return _.concat(tags ,  tableFilter).filter(({value}) => value).map((item: any) => ({
    return tableFilter.filter(({value}) => value).map((item: any) => ({
      ...item,
      field: _.camelCase(item.key),
      operator: item.operator || 'LIKE'
    }));
  }

  cancelCreateExistingProject() {
    this.searchWorkspace = false;
    this.selectedWorkspace = null;
    this.stepSelectWorkspace = true;
    this.stepSelectProject = false;

  }
  onRowSelect(event) {
   this.selectedWorkspace = event.data;
  }
  onRowUnselect(event) {
    // this.selectedWorkspace = null;
  }
  getWorkspaceProjects(workspace) {
    this._searchService.searchWorkspace(workspace.workSpaceId, workspace.uwYear).subscribe((data: any) => {
        this.selectedWorkspaceProjects = data.projects;
        if (data.projects.length) {
          this.stepSelectWorkspace = false;
          this.stepSelectProject = true;
        } else {
          this.messageService.add({severity: 'info', summary: 'This workspace contains no project'});
        }
        this.changeDetector.detectChanges();
      }
    );
  }
  selectProjectNext() {
    this.searchWorkspace = false;
    this.newProject = true;
    this.newProjectForm.patchValue(_.omit(this.selectedProject, ['receptionDate', 'dueDate', 'createdBy']));
  }

  selectThisProject(project) {
    if (this.selectedProject === project) {
      this.selectedProject = null;
      return;
    }
    this.selectedProject = project;
  }

}
