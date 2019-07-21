import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {Location} from '@angular/common';
import {LazyLoadEvent} from 'primeng/api';
import * as _ from 'lodash';
import {SearchService} from '../../../core/service';
import {Select, Store} from '@ngxs/store';
import {HeaderState, WorkspaceMainState} from '../../../core/store/states';
import {Observable} from 'rxjs';
import {WorkspaceMain} from '../../../core/model';
import {HelperService} from '../../../shared/helper.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DeleteTask, PauseTask, ResumeTask} from "../../../core/store/actions/header.action";
import * as workspaceActions from "../../../workspace/store/actions/workspace.actions";

@Component({
  selector: 'app-workspace-job-manager',
  templateUrl: './workspace-job-manager.component.html',
  styleUrls: ['./workspace-job-manager.component.scss']
})
export class WorkspaceJobManagerComponent implements OnInit {
  loading = false;
  contextSelectedItem = null;
  lastSelectedIndex = null;
  Users = 'all';
  selectedRows = [];

  @ViewChild('dt') table;
  @ViewChild('cm') contextMenu;

  items = [
    {
      label: 'Pause',
      icon: 'pi pi-times',
      command: () => { this.pauseJob(this.contextSelectedItem.id); }
    },
    {
      label: 'Resume',
      icon: 'pi pi-check',
      command: () => { this.resumeJob(this.contextSelectedItem.id); }
    },
    {
      label: 'Delete',
      icon: 'pi pi-trash',
      command: () => { this.deleteJob(this.contextSelectedItem.id); }
    },
    {
      label: 'View Detail',
      icon: 'pi pi-eye',
      command: () => { this.contextSelectedItem.append = true; }
    },
    {
      label: 'Select item',
      icon: 'pi pi-check',
      command: () => { this.contextSelectedItem.selected = true; }
    },
    {
      label: 'Open item',
      icon: 'pi pi-eject',
      command: () => {this.openWorkspace(this.contextSelectedItem.workSpaceId, this.contextSelectedItem.uwYear, this.contextSelectedItem.linkTo);
      }
    },
    {
      label: 'Pop Out',
      icon: 'pi pi-eject',
      command: () => {
      }
    },
  ];

  savedTask: any;

  tableColumn = [
    {
      field: 'checkbox',
      header: '',
      width: '25px',
      display: true,
      sorted: false,
      filtered: false,
      type: 'checkbox',
      class: 'icon-check_24px',
    },
    {
      field: 'progress',
      header: 'State',
      width: '90px',
      display: true,
      sorted: false,
      filtered: false,
      type: 'progress',
      filterParam: 'state'
    },
    {
      field: 'jobId',
      header: 'Job',
      width: '40px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'text',
      filterParam: 'job'
    },
    {
      field: 'jobOwner',
      header: 'Job Owner',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'text',
      filterParam: 'jobOwner'
    },
    {
      field: 'jobType',
      header: 'Job Type',
      width: '70px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'text',
      filterParam: 'jobType'
    },
    {
      field: 'context',
      header: 'Context',
      width: '150px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'object',
      filterParam: 'innerCedantName'
    },
    {
      field: 'priority',
      header: 'Priority',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'status',
      filterParam: 'innerCedantCode'
    },
    {
      field: 'submittedTime',
      header: 'Submitted Time',
      width: '110px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'text',
      filterParam: 'submittedTime'
    },
    {
      field: 'startTime',
      header: 'Start Time',
      width: '110px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'text',
      filterParam: 'innerYear'
    },
    {
      field: 'elapsedTime',
      header: 'Elapsed Time',
      width: '110px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'text',
      filterParam: 'elapsedTime'
    },
    {
      field: 'completionTime',
      header: 'Completion Time',
      width: '110px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'text',
      filterParam: 'completionTime'
    },
    {
      field: 'menuIcon',
      header: '',
      width: '30px',
      display: false,
      sorted: false,
      filtered: false,
      type: 'icon',
      filterParam: ''
    }
  ];

  tableColumnDetail = [
    {
      field: 'taskNumber',
      header: 'Task N°',
      width: '50px',
      display: true,
      sorted: false,
      filtered: false,
      type: 'text',
      filterParam: 'state'
    },
    {
      field: 'taskName',
      header: 'Task Name',
      width: '200px',
      display: true,
      sorted: false,
      filtered: false,
      type: 'text',
      filterParam: 'job'
    },
    {
      field: 'status',
      header: 'Status',
      width: '120px',
      display: true,
      sorted: false,
      filtered: false,
      type: 'text',
      filterParam: 'jobOwner'
    },
    {
      field: 'startDate',
      header: 'Start Date',
      width: '120px',
      display: true,
      sorted: false,
      filtered: false,
      type: 'text',
      filterParam: 'jobType'
    },
    {
      field: 'completedDate',
      header: 'Completed Date',
      width: '120px',
      display: true,
      sorted: false,
      filtered: false,
      type: 'text',
      filterParam: 'completedDate'
    },
  ];
  detailData = [
    {
      taskNumber: '1',
      taskName: 'Import Portfolio XYZ from "EDM Name"',
      status: 'In progress',
      startDate: '2019-01-03 T 09:57:10',
      completedDate: '2019-01-03 T 09:57:10',
    },
    {
      taskNumber: '2',
      taskName: 'Import Analysis ABC (ID 30) from "RDM Name"',
      status: 'Pending',
      startDate: '2019-01-03 T 09:57:10',
      completedDate: '2019-01-03 T 09:57:10',
    }
  ];

  @Select(HeaderState.getJobs) jobs$;
  jobs: any;

  @Select(WorkspaceMainState)
  state$: Observable<WorkspaceMain>;
  state: WorkspaceMain = null;

  constructor(public location: Location, private _searchService: SearchService, private store: Store,
              private helperService: HelperService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.jobs$.subscribe(value => {
      this.jobs = _.merge([], value);
      this.savedTask = [...this.jobs];
    });
    this.state$.subscribe(value => this.state = _.merge({}, value));
  }

  private searchData(id, year) {
    return this._searchService.searchWorkspace(id || '', year || '2019');
  }

  expandRow(row, expand) {
    row.append = !row.append;
    return true;
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

  filterByUser(event) {
    console.log(event);
    event === 'all' ? this.savedTask = this.jobs :
      this.savedTask = this.jobs.filter(dt => dt.jobOwner === event);
  }

  uncheckRow(row) {
    row.selected = !row.selected;
    this.selectedRows = this.jobs.filter(ws => ws.selected === true);
  }

  openWorkspace(wsId, year, routerLink) {
    this.store.dispatch(new workspaceActions.openWS({wsId, uwYear: year, route: routerLink}));
  }

  multiSelectRow(row, index) {
    if ((window as any).event.ctrlKey) {
      row.selected = !row.selected;
      this.lastSelectedIndex = index;
    } else if ((window as any).event.shiftKey) {
      event.preventDefault();
      if (this.lastSelectedIndex || this.lastSelectedIndex === 0) {
        this.selectSection(Math.min(index, this.lastSelectedIndex), Math.max(index, this.lastSelectedIndex));
        // this.lastSelectedIndex = null;
      } else {
        this.lastSelectedIndex = index;
        row.selected = true;
      }
    } else {
      this.jobs.forEach(res => res.selected = false);
      this.lastSelectedIndex = index;
      row.selected = true;
    }
    this.selectedRows = this.jobs.filter(ws => ws.selected === true);
  }

  private selectSection(from, to) {
    this.jobs.forEach(dt => dt.selected = false);
    if (from === to) {
      this.jobs[from].selected = true;
    } else {
      for (let i = from; i <= to; i++) {
        this.jobs[i].selected = true;
      }
    }
  }

  changeContext() {
    if (this.contextSelectedItem == null) {
      return this.items;
    } else {
      return this.items.filter(dt => {
        if (this.contextSelectedItem.isPaused) {
          return dt.label !== 'Pause';
        } else {
          return dt.label !== 'Resume';
        }
      });
    }
  }

  cancel(): void {

  }

  navigateBack() {
    this.location.back();
  }

  loadDataOnScroll(event: LazyLoadEvent) {
  }

  @HostListener('wheel', ['$event']) onElementScroll(event) {
    this.contextMenu.hide();
  }
}
