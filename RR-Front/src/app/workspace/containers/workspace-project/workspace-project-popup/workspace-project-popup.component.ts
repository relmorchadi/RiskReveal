import {ChangeDetectorRef, Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import * as _ from 'lodash';
import {ActivatedRoute, Router} from '@angular/router';
import {Subject} from 'rxjs';
import {Actions, Select, Store} from '@ngxs/store';
import {WorkspaceMainState} from '../../../../core/store/states';
import {LazyLoadEvent, MessageService} from 'primeng/api';
import {SearchService} from '../../../../core/service';
import {Debounce} from '../../../../shared/decorators';
import {NotificationService} from '../../../../shared/notification.service';

@Component({
  selector: 'app-workspace-project-popup',
  templateUrl: './workspace-project-popup.component.html',
  styleUrls: ['./workspace-project-popup.component.scss'],
})
export class WorkspaceProjectPopupComponent implements OnInit, OnDestroy {

  @Output('onCancel')
  onCancel: EventEmitter<any> = new EventEmitter();
  @Output('onSelectProjectNext')
  onSelectProjectNext: EventEmitter<any> = new EventEmitter();
  @Output('onSelectProject')
  onSelectProject: EventEmitter<any> = new EventEmitter();
  @Output('onSelectWorkspace')
  onSelectWorkspace: EventEmitter<any> = new EventEmitter();
  unSubscribe$: Subject<void>;
  workspace: any;
  index: any;
  @Input()
  isVisible;
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
  loading;
  @Select(WorkspaceMainState.getData) data$;
  @Select(WorkspaceMainState.getProjects) projects$;


  constructor(private route: ActivatedRoute,
              private store: Store,
              private router: Router, private actions$: Actions, private notificationService: NotificationService,
              private changeDetector: ChangeDetectorRef, private searchService: SearchService
  ) {this.unSubscribe$ = new Subject<void>(); }

  ngOnInit() {}

  resetToMain() {
    this.stepSelectWorkspace = true;
    this.stepSelectProject = false;
  }

  ngOnDestroy(): void {
    this.unSubscribe$.next();
    this.unSubscribe$.complete();
  }

  openWorkspaceInSlider(event?) { console.log(event); }
  @Debounce(500)
  filterData($event, target) {
    this._filter = {...this._filter, [target]: $event || null};
    this._loadData();
  }

  private _loadData(offset = '0', size = '100') {
    this.loading = true;
    const params = {
      keyword: this.globalSearchItem,
       filter: this.filter,
      offset,
      size
    };
    this.searchService.expertModeSearch(params)
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
    this.onCancel.emit(false);
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
    this.onSelectWorkspace.emit(workspace);
    this.searchService.searchWorkspace(workspace.workSpaceId, workspace.uwYear).subscribe((data: any) => {
        this.selectedWorkspaceProjects = data.projects;
        if (data.projects.length) {
          this.stepSelectWorkspace = false;
          this.stepSelectProject = true;
        } else {
          this.notificationService.createNotification('Information',
            'This workspace contains no project',
            'error', 'topRight', 4000);
        }
        this.changeDetector.detectChanges();
      }
    );
  }
  selectProjectNext() {
    this.searchWorkspace = false;
    this.newProject = true;
    this.onSelectProjectNext.emit(_.omit(this.selectedProject, ['receptionDate', 'dueDate', 'createdBy']));
  }

  selectThisProject(project) {
    if (this.selectedProject === project) {
      this.selectedProject = null;
      return;
    }
    this.selectedProject = project;
  }

}
