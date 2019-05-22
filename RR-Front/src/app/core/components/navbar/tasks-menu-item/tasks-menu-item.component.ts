import {Component, OnInit} from '@angular/core';
import {SearchService} from '../../../service/search.service';
import * as _ from 'lodash';

@Component({
  selector: 'tasks-menu-item',
  templateUrl: './tasks-menu-item.component.html',
  styleUrls: ['./tasks-menu-item.component.scss']
})
export class TasksMenuItemComponent implements OnInit {

  formatter = (_) => '';
  visible: boolean;
  lastOnes = 1;
  savedtasks: any;
  readonly tasks = {
    active: [
      {
        progress: 75,
        name: 'ALABAMA INS.UA',
        year: '2018',
        description: 'Cat Program, 1st/4th 1year,2nd/3rd year ',
        duration: '1 min remaining',
        specific: {type: 'Import', id: 'P009873'},
        append: false,
        data: {
          workSpaceId: 'TP05413',
          uwYear: 2016,
          workspaceName: 'CEA Program: Private Placement',
          cedantCode: '72389',
          cedantName: 'CALIFORNIA EQ AUTHORITY',
        },
        isPaused: false
      },
      {
        progress: 50,
        name: 'ALLSTATES INCO',
        year: '2018',
        description: 'Nationwide ex NJ/FL, MY (2/3)',
        duration: '1 min remaining',
        specific: {type: 'Import', id: 'P009873'},
        append: false,
        data: {
          workSpaceId: 'TP05413',
          uwYear: 2016,
          workspaceName: 'CEA Program: Private Placement',
          cedantCode: '72389',
          cedantName: 'CALIFORNIA EQ AUTHORITY',
        },
        isPaused: false
      },
      {
        progress: 25,
        name: 'ALTE LEIPZIGER VERS',
        year: '2018',
        description: 'Property XL Bouquet',
        duration: '1 min remaining',
        specific: {type: 'Import', id: 'P009873'},
        append: false,
        data: {
          workSpaceId: 'TP05413',
          uwYear: 2016,
          workspaceName: 'CEA Program: Private Placement',
          cedantCode: '72389',
          cedantName: 'CALIFORNIA EQ AUTHORITY',
        },
        isPaused: false
      },
      {
        progress: 0,
        name: 'AMERICAN FAMILY MUTUAL',
        year: '2019',
        description: 'American Family Annual Cat XL',
        duration: '4/12 Pending',
        specific: {type: 'Calibration', id: 'P009873'},
        append: false,
        data: {
          workSpaceId: 'TP05413',
          uwYear: 2016,
          workspaceName: 'CEA Program: Private Placement',
          cedantCode: '72389',
          cedantName: 'CALIFORNIA EQ AUTHORITY',
        },
        isPaused: true
      },
      {
        progress: 0,
        name: 'AXA GLOBAL RE',
        year: '2019',
        description: 'XL Property CAT ESP',
        duration: '4/12 Pending',
        specific: {type: 'Calibration', id: 'P009873'},
        append: false,
        data: {
          workSpaceId: 'TP05413',
          uwYear: 2016,
          workspaceName: 'CEA Program: Private Placement',
          cedantCode: '72389',
          cedantName: 'CALIFORNIA EQ AUTHORITY',
        },
        isPaused: true
      },
      {
        progress: 0,
        name: 'CONCORDIA VERS',
        year: '2017',
        description: 'Elementar XL Programm',
        duration: '4/12 Pending',
        specific: {type: 'Import', id: 'P009873'},
        append: false,
        data: {
          workSpaceId: 'TP05413',
          uwYear: 2016,
          workspaceName: 'CEA Program: Private Placement',
          cedantCode: '72389',
          cedantName: 'CALIFORNIA EQ AUTHORITY',
        },
        isPaused: true
      },
      {
        progress: 0,
        name: 'AIG',
        year: '2017',
        description: 'AIG Cat XL',
        duration: '5/12 Pending',
        specific: {type: 'Import', id: 'P009873'},
        append: false,
        data: {
          workSpaceId: 'TP05413',
          uwYear: 2016,
          workspaceName: 'CEA Program: Private Placement',
          cedantCode: '72389',
          cedantName: 'CALIFORNIA EQ AUTHORITY',
        },
        isPaused: true
      },
      {
        progress: 0,
        name: 'AXA GLOBAL',
        year: '2018',
        description: 'XL Property CAT ESP',
        duration: '5/12 Pending',
        specific: {type: 'Import', id: 'P009873'},
        append: false,
        data: {
          workSpaceId: 'TP05413',
          uwYear: 2016,
          workspaceName: 'CEA Program: Private Placement',
          cedantCode: '72389',
          cedantName: 'CALIFORNIA EQ AUTHORITY',
        },
        isPaused: true
      },
    ]
  };


  constructor(private _searchService: SearchService) {
  }

  ngOnInit() {
    this._searchService.infodropdown.subscribe(dt => this.visible = this._searchService.getvisibleDropdown());
    this.savedtasks = this.tasks;
  }

  toggleActiveTask(activeTask) {
    activeTask.append = !activeTask.append;
  }

  searchJobs(event) {
    this.savedtasks.active = this.tasks.active.filter(text => _.includes(text.name, event.target.value));
  }

}
