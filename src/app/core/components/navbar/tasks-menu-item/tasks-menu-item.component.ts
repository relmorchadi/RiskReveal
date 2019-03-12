import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'tasks-menu-item',
  templateUrl: './tasks-menu-item.component.html',
  styleUrls: ['./tasks-menu-item.component.scss']
})
export class TasksMenuItemComponent implements OnInit {
  formatter = (_)=> ""

  lastOnes= 4;

  readonly tasks = {
    active: [
      {progress: 75, name:'Import_P0093838', duration: '8 min remaining', isPaused: false },
      {progress: 50, name:'Calibration_PP8888887', duration: '32 min remaining', isPaused: false },
      {progress: 25, name:'Inuring_P009873-TYG', duration: '1 hour remaining', isPaused: false },
      {progress: 0, name:'Calibration_P7774673', duration: '4/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
      {progress: 0, name:'Inured_P009873-TGY', duration: '5/12 Pending', isPaused: true },
    ]
  };





  constructor() { }

  ngOnInit() {
  }

  toggleActiveTask(activeTask){

  }

}
