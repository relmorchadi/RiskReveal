import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'tasks-menu-item',
  templateUrl: './tasks-menu-item.component.html',
  styleUrls: ['./tasks-menu-item.component.scss']
})
export class TasksMenuItemComponent implements OnInit {
  formatter = (_)=> ""
  constructor() { }

  ngOnInit() {
  }

}
