import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {Location} from '@angular/common';
import {LazyLoadEvent} from 'primeng/api';
import * as _ from 'lodash';

@Component({
  selector: 'app-workspace-job-manager',
  templateUrl: './workspace-job-manager.component.html',
  styleUrls: ['./workspace-job-manager.component.scss']
})
export class WorkspaceJobManagerComponent implements OnInit {
  loading = false;
  contextSelectedItem: any;
  Users = 'all';

  @ViewChild('dt') table;
  @ViewChild('cm') contextMenu;

  items = [
    {
      label: 'View Detail',
      icon: 'pi pi-eye',
      command: () => {
      }
    },
    {
      label: 'Select item',
      icon: 'pi pi-check',
      command: () => {
      }
    },
    {
      label: 'Open item',
      icon: 'pi pi-eject',
      command: () => {
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
/*    {
      field: 'checkbox',
      header: '',
      width: '25px',
      display: true,
      sorted: false,
      filtered: false,
      type: 'checkbox',
      class: 'icon-check_24px',
    },*/
    {
      field: 'state',
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
      width: '20px',
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
      status: '2019-01-03 T 09:57:10',
      startDate: '2019-01-03 T 09:57:10',
      completedDate: '2019-01-03 T 09:57:10',
    },
    {
      taskNumber: '2',
      taskName: 'Import Analysis ABC (ID 30) from "RDM Name"',
      status: '2019-01-03 T 09:57:10',
      startDate: '2019-01-03 T 09:57:10',
      completedDate: '2019-01-03 T 09:57:10',
    }
  ];

  listOfData = [
    {
      id: 1,
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      isPaused: false,
      priority: 'high',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10'
    },
    {
      id: 2,
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      isPaused: false,
      priority: 'medium',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10'
    },
    {
      id: 3,
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      isPaused: false,
      priority: 'high',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10'
    },
    {
      id: 4,
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Rim Benabbes',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      isPaused: false,
      priority: 'low',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10'
    },
    {
      id: 5,
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Rim Benabbes',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      jobType: 'Import',
      isPaused: false,
      priority: 'low',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10'
    },
    {
      id: 6,
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Rim Benabbes',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      isPaused: false,
      priority: 'high',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10'
    },
    {
      id: 7,
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      isPaused: false,
      priority: 'medium',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10'
    },
    {
      id: 8,
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      isPaused: false,
      priority: 'medium',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10'
    },
    {
      id: 9,
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Rim Benabbes',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      isPaused: false,
      priority: 'medium',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10'
    },
    {
      id: 10,
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Rim Benabbes',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      isPaused: false,
      priority: 'medium',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10'
    },
    {
      id: 11,
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      isPaused: false,
      priority: 'low',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10'
    },
    {
/*      selected: false,*/
      id: 12,
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      isPaused: false,
      priority: 'high',
      startTime: '2019-01-03 T 09:57:10',
      elapsedTime: '2019-01-03 T 09:57:10',
      completionTime: '2019-01-03 T 09:57:10',
      submittedTime: '2019-01-03 T 09:57:10'
    }
  ];

  constructor(public location: Location) {
  }

  ngOnInit() {
    this.savedTask = [...this.listOfData];
  }

  resumeJob(id) {
    this.listOfData.map(dt => {
      if (dt.id === id) {
        dt.isPaused = false;
      }
    });
    this.listOfData = _.sortBy(this.listOfData, (dt) => dt.isPaused);
    this.savedTask = [...this.listOfData];
  }

  deleteJob(id) {
    this.listOfData = this.listOfData.filter(dt => dt.id !== id);
    this.savedTask = [...this.listOfData];
  }

  pauseJob(id): void {
    this.listOfData.map(dt => {
      if (dt.id === id) {
        dt.isPaused = true;
      }
    });
    this.listOfData = _.sortBy(this.listOfData, (dt) => dt.isPaused);
    this.savedTask = [...this.listOfData];
  }

  filterByUser(event) {
    console.log(event);
    event === 'all' ? this.savedTask = this.listOfData :
      this.savedTask = this.listOfData.filter(dt => dt.jobOwner === event);
  }

  filterByType(event) {
/*    event === 'all' ? this.savedTasksLocal.active = this.tasks.active :
      this.savedTasksLocal.active = this.tasks.active.filter(dt => dt.specific.type === event);*/
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
