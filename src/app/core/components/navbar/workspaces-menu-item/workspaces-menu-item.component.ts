import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'workspaces-menu-item',
  templateUrl: './workspaces-menu-item.component.html',
  styleUrls: ['./workspaces-menu-item.component.scss']
})
export class WorkspacesMenuItemComponent implements OnInit {
  workspaces: any = [
    {id: 1, name: 'Workspace 1', selected: false},
    {id: 2, name: 'Workspace 2', selected: false},
    {id: 3, name: 'Workspace 3', selected: false},
    {id: 4, name: 'Workspace 4', selected: false},
    {id: 5, name: 'Workspace 5', selected: false},
    {id: 6, name: 'Workspace 6', selected: false},
    {id: 7, name: 'Workspace 7', selected: false},
    {id: 8, name: 'Workspace 8', selected: false},
    {id: 9, name: 'Workspace 9', selected: false},
    {id: 10, name: 'Workspace 10', selected: false},
    {id: 11, name: 'Workspace 11', selected: false},
    {id: 12, name: 'Workspace 12', selected: false},
    {id: 13, name: 'Workspace 13', selected: false},
    {id: 14, name: 'Workspace 14', selected: false},
    {id: 15, name: 'Workspace 15', selected: false},
    {id: 16, name: 'Workspace 16', selected: false},
  ];
  selectedWorkspace = null;

  lastOnes= 1;

  constructor() {
  }

  ngOnInit() {
  }

  toggleWorkspace(workspace) {
    workspace.selected = !workspace.selected;
  }
}
