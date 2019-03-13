import {Component, OnInit} from '@angular/core';
import {HelperService} from '../../../../shared/helper.service';

@Component({
  selector: 'workspaces-menu-item',
  templateUrl: './workspaces-menu-item.component.html',
  styleUrls: ['./workspaces-menu-item.component.scss']
})
export class WorkspacesMenuItemComponent implements OnInit {
  workspaces: any = [
    {name:'02PY376',selected: true,year:'2019'},
    {name:'02PY376',selected: true,year:'2018'},
    {name:'02PY376',selected: true,year:'2019'},
    {name:'06YE736',selected: true,year:'2018'},
    {name:'19IT762',selected: true,year:'2018'}
  ];
  selectedWorkspace = null;

  lastOnes = 1;

  constructor(private _helperService: HelperService) {
  }

  ngOnInit() {
  }

  toggleWorkspace(workspace) {
    workspace.selected = !workspace.selected;
  }

  popOutWorkspaces() {
    this.workspaces.filter(ws => ws.selected).forEach(ws => {
      window.open('/workspace/' + ws.id);
      console.log('try to open', ws);

    });
  }

  openWorkspaces() {
    let selectedItems =[ ...this.workspaces.filter(ws => ws.selected)];
    if ( selectedItems.length > 0) {
      this._helperService
        .openWorkspaces.next(selectedItems.map(ws => ws.id));
    }
  }
}
