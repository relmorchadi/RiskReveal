import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-workspace-main',
  templateUrl: './workspace-main.component.html',
  styleUrls: ['./workspace-main.component.scss']
})
export class WorkspaceMainComponent implements OnInit {
  leftNavbarIsCollapsed:boolean = true;
  tabs = [ 1, 2, 3 ];
  constructor() { }

  ngOnInit() {
  }

}
