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
  Users = '1';

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
      field: 'startTimeDate',
      header: 'Start Time Date',
      width: '110px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'text',
      filterParam: 'innerYear'
    },
    {
      field: 'elapsedTimeDate',
      header: 'Elapsed Time Data',
      width: '110px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'text',
      filterParam: 'elapsedTimeDate'
    },
    {
      field: 'completionTimeDate',
      header: 'Completion Time Data',
      width: '110px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'text',
      filterParam: 'completionTimeDate'
    },
    {
      field: 'submittedTimeDate',
      header: 'Submitted Time Data',
      width: '110px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'text',
      filterParam: 'submittedTimeDate'
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
      filterParam: 'innerCedantName'
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
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      priority: true,
      startTimeDate: '2019-01-03 T 09:57:10',
      elapsedTimeDate: '2019-01-03 T 09:57:10',
      completionTimeDate: '2019-01-03 T 09:57:10',
      submittedTimeDate: '2019-01-03 T 09:57:10'
    },
    {
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      priority: false,
      startTimeDate: '2019-01-03 T 09:57:10',
      elapsedTimeDate: '2019-01-03 T 09:57:10',
      completionTimeDate: '2019-01-03 T 09:57:10',
      submittedTimeDate: '2019-01-03 T 09:57:10'
    },
    {
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      priority: false,
      startTimeDate: '2019-01-03 T 09:57:10',
      elapsedTimeDate: '2019-01-03 T 09:57:10',
      completionTimeDate: '2019-01-03 T 09:57:10',
      submittedTimeDate: '2019-01-03 T 09:57:10'
    },
    {
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      priority: true,
      startTimeDate: '2019-01-03 T 09:57:10',
      elapsedTimeDate: '2019-01-03 T 09:57:10',
      completionTimeDate: '2019-01-03 T 09:57:10',
      submittedTimeDate: '2019-01-03 T 09:57:10'
    },
    {
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      jobType: 'Import',
      priority: false,
      startTimeDate: '2019-01-03 T 09:57:10',
      elapsedTimeDate: '2019-01-03 T 09:57:10',
      completionTimeDate: '2019-01-03 T 09:57:10',
      submittedTimeDate: '2019-01-03 T 09:57:10'
    },
    {
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      priority: true,
      startTimeDate: '2019-01-03 T 09:57:10',
      elapsedTimeDate: '2019-01-03 T 09:57:10',
      completionTimeDate: '2019-01-03 T 09:57:10',
      submittedTimeDate: '2019-01-03 T 09:57:10'
    },
    {
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      priority: false,
      startTimeDate: '2019-01-03 T 09:57:10',
      elapsedTimeDate: '2019-01-03 T 09:57:10',
      completionTimeDate: '2019-01-03 T 09:57:10',
      submittedTimeDate: '2019-01-03 T 09:57:10'
    },
    {
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      priority: true,
      startTimeDate: '2019-01-03 T 09:57:10',
      elapsedTimeDate: '2019-01-03 T 09:57:10',
      completionTimeDate: '2019-01-03 T 09:57:10',
      submittedTimeDate: '2019-01-03 T 09:57:10'
    },
    {
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      priority: true,
      startTimeDate: '2019-01-03 T 09:57:10',
      elapsedTimeDate: '2019-01-03 T 09:57:10',
      completionTimeDate: '2019-01-03 T 09:57:10',
      submittedTimeDate: '2019-01-03 T 09:57:10'
    },
    {
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      priority: false,
      startTimeDate: '2019-01-03 T 09:57:10',
      elapsedTimeDate: '2019-01-03 T 09:57:10',
      completionTimeDate: '2019-01-03 T 09:57:10',
      submittedTimeDate: '2019-01-03 T 09:57:10'
    },
    {
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      priority: true,
      startTimeDate: '2019-01-03 T 09:57:10',
      elapsedTimeDate: '2019-01-03 T 09:57:10',
      completionTimeDate: '2019-01-03 T 09:57:10',
      submittedTimeDate: '2019-01-03 T 09:57:10'
    },
    {
/*      selected: false,*/
      state: 75,
      jobId: '001',
      jobOwner: 'Amina Cheref',
      jobType: 'Import',
      context: {data: 'ALABAMA INS.UA - 2019', year: 2018, program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'},
      priority: false,
      startTimeDate: '2019-01-03 T 09:57:10',
      elapsedTimeDate: '2019-01-03 T 09:57:10',
      completionTimeDate: '2019-01-03 T 09:57:10',
      submittedTimeDate: '2019-01-03 T 09:57:10'
    }
  ];

  constructor(public location: Location) {
  }

  ngOnInit() {
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
