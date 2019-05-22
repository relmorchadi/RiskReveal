import { Component, OnInit } from '@angular/core';
import {SearchService} from '../../../service/search.service';

@Component({
  selector: 'tasks-menu-item',
  templateUrl: './tasks-menu-item.component.html',
  styleUrls: ['./tasks-menu-item.component.scss']
})
export class TasksMenuItemComponent implements OnInit {

  formatter = (_) => '';
  visible: boolean;
  lastOnes = 1;

  readonly tasks = {
    active: [
      {progress: 75, name:'ALABAMA INS.UA', duration: '1 min remaining', sepecific: 'Import - P009873', isPaused: false },
      {progress: 50, name:'ALLSTATES INCO', duration: '1 min remaining', isPaused: false },
      {progress: 25, name:'ALTE LEIPZIGER VERS', duration: '1 min remaining', isPaused: false },
      {progress: 0, name:'Calibration_P7774673', duration: '4/12 Pending', isPaused: true },
      {progress: 0, name:'AMERICAN FAMILY MUTUAL', duration: '4/12 Pending', isPaused: true },
      {progress: 0, name:'AXA GLOBAL RE', duration: '4/12 Pending', isPaused: true },
      {progress: 0, name:'CONCORDIA VERS', duration: '4/12 Pending', isPaused: true },
      {progress: 0, name:'AIG', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'AXA GLOBAL', duration: '5/12 Pending', isPaused: true },
    ]
  };





  constructor(private _searchService: SearchService) { }

  ngOnInit() {
    this._searchService.infodropdown.subscribe( dt => this.visible = this._searchService.getvisibleDropdown());
  }

  toggleActiveTask(activeTask){

  }

}
