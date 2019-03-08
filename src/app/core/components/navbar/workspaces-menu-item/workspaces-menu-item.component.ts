import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'workspaces-menu-item',
  templateUrl: './workspaces-menu-item.component.html',
  styleUrls: ['./workspaces-menu-item.component.scss']
})
export class WorkspacesMenuItemComponent implements OnInit {
  workspaces: any = [
    {id: 1, name: 'Workspace 1'}, {id: 2, name: 'Workspace 2'}, {id: 3, name: 'Workspace 3'}, {id: 4, name: 'Workspace 4'},
  ];
  selectedWorkspace=null;
  constructor() { }

  ngOnInit() {
  }
  toggleWorkspace(workspace) {
    this.selectedWorkspace = workspace;
  }
}
