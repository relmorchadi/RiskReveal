import {ChangeDetectorRef, Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {Location} from '@angular/common';
import {LazyLoadEvent} from 'primeng/api';
import * as _ from 'lodash';
import {SearchService} from '../../../core/service';
import {Select, Store} from '@ngxs/store';
import {AuthState, HeaderState} from '../../../core/store/states';
import {Observable} from 'rxjs';
import {HelperService} from '../../../shared/helper.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DeleteTask, PauseTask, ResumeTask} from '../../../core/store/actions/header.action';
import * as workspaceActions from '../../../workspace/store/actions/workspace.actions';
import {WorkspaceState} from "../../../workspace/store/states";
import {JobManagerService} from "../../../core/service/jobManager.service";
import {first, map} from "rxjs/operators";
import {AuthModel} from "../../../core/model/auth.model";
import {BaseContainer} from "../../../shared/base";

@Component({
  selector: 'app-workspace-job-manager',
  templateUrl: './workspace-job-manager.component.html',
  styleUrls: ['./workspace-job-manager.component.scss'],
  providers:[JobManagerService]
})
export class WorkspaceJobManagerComponent extends BaseContainer
    implements OnInit {
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
      command: () => { this.pauseJob(this.contextSelectedItem.id)
      }
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
  jobStatusItems = ['ALL','RUNNING','PENDING','PAUSED','SUCCEEDED','FAILED']

  savedTask: any =[];
  allTasks=[];
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
      field: 'status',
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
      field: 'submittedByUser',
      header: 'Job Owner',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'text',
      filterParam: 'jobOwner'
    },
    {
      field: 'jobTypeCode',
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
      field: 'submittedDate',
      header: 'Submitted Time',
      width: '110px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'date',
      filterParam: 'submittedTime'
    },
    {
      field: 'startedDate',
      header: 'Start Time',
      width: '110px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'date',
      filterParam: 'innerYear'
    },
    {
      field: 'elapsedTime',
      header: 'Elapsed Time',
      width: '110px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'time',
      filterParam: 'elapsedTime'
    },
    {
      field: 'finishedDate',
      header: 'Completion Time',
      width: '110px',
      display: true,
      sorted: false,
      filtered: true,
      type: 'date',
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
  selectedStatusFilter = 'ALL';


  @Select(HeaderState.getJobs) jobs$;
  @Select(AuthState.getUser) user$;
  jobs: any;

  constructor(private cdref : ChangeDetectorRef,public location: Location, private _searchService: SearchService, private store: Store,private jobManagerService:JobManagerService,
              private helperService: HelperService, private route: ActivatedRoute, private router: Router) {
    super(router,cdref,store)
  }

  ngOnInit() {
    super.ngOnInit();
    this.jobManagerService.getAllJobs().subscribe((jobs:any) => {
      console.log(jobs);
      this.savedTask = jobs.map(row => ({
        ...row,
        append:false,
        selected:false,
          elapsedTime: row.finishedDate ? this.calculateElapsedTime(row.finishedDate,row.startedDate) : '-'
      }));
      this.allTasks = this.savedTask;
      this.detectChanges();
    });

    // this.user$.subscribe( value => {
    //   this.Users = value;
    // })
    // this.jobs$.subscribe(value => {
    //   this.jobs = _.toArray(_.merge({}, value));
    //   this.savedTask = [..._.sortBy(_.filter(this.jobs, (dt) => !dt.pending), (dt) => dt.isPaused),
    //     ..._.filter(this.jobs, (dt) => dt.pending)];
    // });
  }

  calculateElapsedTime(finishedDate,startedDate) {

    let difference :any = new Date(0);
    let diffInSeconds = Math.abs((finishedDate - startedDate)/1000)
    difference.setSeconds( Math.floor( diffInSeconds % 60));
    difference.setMinutes(Math.floor(diffInSeconds/60));


    return difference.toISOString().substr(11, 8);
  }
  expandRow(row, expand) {
    console.log(expand);
    row.append = !row.append;
    return row.append;
  }

  resumeJob(id) {
    this.jobManagerService.resumeJob(id).subscribe( (result:any) => {
      if (result) {
        console.log(result);
        this.savedTask.find( row => row.jobId == id).status = result.status;
        this.detectChanges();
      }
    });
  }

  deleteJob(id) {
    this.jobManagerService.deleteJob(id).subscribe( (result:any) => {
      if (result) {
        this.savedTask = this.savedTask.filter( row => row.jobId != id);
        this.detectChanges();
      }
    });;
  }

  pauseJob(id) {
     this.jobManagerService.pauseJob(id).subscribe( (result:any) => {
       if (result) {
         this.savedTask.find( row => row.jobId == id).status = 'PAUSED';
         this.detectChanges();
       }
     });
  }

  filterByUser(event) {
    event === 'all' ? this.savedTask = this.jobs :
      this.savedTask = this.jobs.filter(dt => dt.jobOwner === event);
  }

  uncheckRow(row) {
    row.selected = !row.selected;
    this.selectedRows = this.savedTask.filter(ws => ws.selected === true);
  }

  openWorkspace(wsId, year, routerLink) {
    this.store.dispatch(new workspaceActions.OpenWS({wsId, uwYear: year, route: routerLink, type: 'treaty'}));
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
      this.savedTask.forEach(res => res.selected = false);
      this.lastSelectedIndex = index;
      row.selected = true;
    }
    this.selectedRows = this.savedTask.filter(ws => ws.selected === true);
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

  @HostListener('wheel', ['$event']) onElementScroll(event) {
    this.contextMenu.hide();
  }

  changeStatusFilter(status: string) {
    if(status == 'ALL') {
      this.savedTask = this.allTasks;
    }else {
      this.savedTask =  this.allTasks.filter(row => row.status == status);
    }
    this.selectedStatusFilter =  status;
    this.detectChanges();
  }

  openSource(type: string,rowData) {
    if (type == 'in') {
      this.router.navigate(['workspace/'+rowData.clientName+'/'+rowData.uwYear+'/RiskLink']);

    }else {
      window.open('workspace/'+rowData.clientName+'/'+rowData.uwYear+'/RiskLink')

    }
  }

  openDetailsPanel(item: any) {
    
  }
}
