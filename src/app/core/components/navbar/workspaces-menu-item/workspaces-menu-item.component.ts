import {Component, OnInit} from '@angular/core';
import {HelperService} from '../../../../shared/helper.service';
import {Router} from '@angular/router';

@Component({
  selector: 'workspaces-menu-item',
  templateUrl: './workspaces-menu-item.component.html',
  styleUrls: ['./workspaces-menu-item.component.scss']
})
export class WorkspacesMenuItemComponent implements OnInit {
  workspaces: any = [
    {id:1,name:'02PY376',selected: true,year:'2019'},
    {id:2,name:'02PY376',selected: true,year:'2018'},
    {id:3,name:'02PY376',selected: true,year:'2019'},
    {id:4,name:'06YE736',selected: true,year:'2018'},
    {id:5,name:'19IT762',selected: true,year:'2018'}
  ];
  selectedWorkspace = null;

  lastOnes = 1;

  constructor(private _helperService: HelperService,private router:Router) {
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
  w

  openWorkspaces() {
    let selectedItems =[ ...this.workspaces.filter(ws => ws.selected)];
    if ( selectedItems.length > 0) {
      console.log({selectedItems})
      this._helperService
        .openWorkspaces.next(selectedItems.map(({name,...item}:any) =>({title:name,...item})));
      this.router.navigate(['/workspace'])
    }
  }
}
