import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {Location} from '@angular/common';
import {LazyLoadEvent} from 'primeng/api';
import * as _ from 'lodash';
import {
  AppendNewWorkspaceMainAction,
  PatchWorkspaceMainStateAction,
  SetWsRoutingAction
} from '../../../core/store/actions';
import {SearchService} from '../../../core/service';
import {Select, Store} from '@ngxs/store';
import {WorkspaceMainState} from '../../../core/store/states';
import {Observable} from 'rxjs';
import {WorkspaceMain} from '../../../core/model';
import {HelperService} from '../../../shared/helper.service';
import {ActivatedRoute, Router} from '@angular/router';

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

  listOfData = [
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
      pending: false,
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
      pending: false,
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
      jobOwner: 'Amina Cheref',
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

  constructor(public location: Location, private _searchService: SearchService, private store: Store,
              private helperService: HelperService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.state$.subscribe(value => this.state = _.merge({}, value));
    this.savedTask = [...this.listOfData];
  }

  private searchData(id, year) {
    return this._searchService.searchWorkspace(id || '', year || '2019');
  }

  expandRow(row, expand) {
    row.append = !row.append;
    return true;
  }

  resumeJob(id) {
    this.listOfData.map(dt => {
      if (this.selectedRows.length === 0) {
        if (dt.id === id) {
          dt.isPaused = false;
        }
      } else {
        const filtered = this.selectedRows.filter(st => st.id === id);
        if (filtered.length > 0) {
          this.selectedRows.map(st => {
            if (st.id === dt.id) {
              dt.isPaused = false;
            }
          });
        } else {
          if (dt.id === id) {
            dt.isPaused = false;
          }
        }
      }
    });
    this.listOfData = [..._.sortBy(_.filter(this.listOfData, (dt) => !dt.pending) , (dt) => dt.isPaused),
      ..._.filter(this.listOfData, (dt) => dt.pending)];
    this.savedTask = [...this.listOfData];
  }

  deleteJob(id) {
    this.listOfData = this.listOfData.filter(dt => dt.id !== id);
    this.savedTask = [...this.listOfData];
  }

  pauseJob(id): void {
    this.listOfData.map(dt => {
      if (this.selectedRows.length === 0) {
        if (dt.id === id) {
          dt.isPaused = true;
        }
      } else {
        const filtered = this.selectedRows.filter(st => st.id === id);
        if (filtered.length > 0) {
          this.selectedRows.map(st => {
            if (st.id === dt.id) {
              dt.isPaused = true;
            }
          });
        } else {
          if (dt.id === id) {
            dt.isPaused = true;
          }
        }
      }
    });
    this.listOfData = [..._.sortBy(_.filter(this.listOfData, (dt) => !dt.pending) , (dt) => dt.isPaused),
      ..._.filter(this.listOfData, (dt) => dt.pending)];
    this.savedTask = [...this.listOfData];
  }

  filterByUser(event) {
    console.log(event);
    event === 'all' ? this.savedTask = this.listOfData :
      this.savedTask = this.listOfData.filter(dt => dt.jobOwner === event);
  }

  uncheckRow(row) {
    row.selected = !row.selected;
    this.selectedRows = this.listOfData.filter(ws => ws.selected === true);
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

  filterByType(event) {
/*    event === 'all' ? this.savedTasksLocal.active = this.tasks.active :
      this.savedTasksLocal.active = this.tasks.active.filter(dt => dt.specific.type === event);*/
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
      this.listOfData.forEach(res => res.selected = false);
      this.lastSelectedIndex = index;
      row.selected = true;
    }
    this.selectedRows = this.listOfData.filter(ws => ws.selected === true);
  }

  private selectSection(from, to) {
    this.listOfData.forEach(dt => dt.selected = false);
    if (from === to) {
      this.listOfData[from].selected = true;
    } else {
      for (let i = from; i <= to; i++) {
        this.listOfData[i].selected = true;
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
