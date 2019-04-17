import {Component, OnInit} from '@angular/core';
import {HelperService} from '../../../../shared/helper.service';
import {Router} from '@angular/router';
import {SearchService} from '../../../service/search.service'
import {FormBuilder, FormGroup} from "@angular/forms";
import * as _ from 'lodash';

@Component({
  selector: 'workspaces-menu-item',
  templateUrl: './workspaces-menu-item.component.html',
  styleUrls: ['./workspaces-menu-item.component.scss']
})
export class WorkspacesMenuItemComponent implements OnInit {
  contractFilterFormGroup: FormGroup;
  workspaces: any = [];
  selectedWorkspace = null;
  selectedItems = [];
  lastOnes = 10;

  constructor(private _helperService: HelperService,private router:Router, private _searchService: SearchService,
              private _fb: FormBuilder) {
    this.setForm();
  }

  ngOnInit() {
    this.searchWorkspace('10');
  }

  setForm() {
    this.contractFilterFormGroup = this._fb.group({
      globalKeyword: [],
      workspaceId: [],
      workspaceName: [],
      year: [],
      treaty: [],
      cedantCode: [],
      cedant: [],
      country: []
    });
  }

  searchWorkspace(size: string = '10') {
    this.workspaces = [];
    this._searchService.searchContracts(this.contractFilterFormGroup.value, size)
      .subscribe((data: any) => {
        data.content.forEach(
          (element) => {
            const item = {id: element.id, name: element.workSpaceId, selected: false, year: element.uwYear};
            this.workspaces = [...this.workspaces, item];
          }
        );
        if (this.selectedItems.length === 0) {
          this.workspaces[0].selected = true;
          this.selectedItems.push(this.workspaces[0]);
        } else {
          this.workspaces.forEach((ws) => {
              this.selectedItems.forEach((si) => ws.id === si.id ? ws.selected = true : null);
            }
          );
        }
      });
  }

  updateWorkspaces(event) {
    this.searchWorkspace(event);
  }

  searchNewWorkspace(search) {
    // let selectedItems =[ ...this.workspaces.filter(ws => ws.selected)];
    this.contractFilterFormGroup.patchValue({treaty: search.target.value});
    this.searchWorkspace();
  }

  toggleWorkspace(workspace) {
    if (workspace.selected === true) {
      this.selectedItems.push(workspace);
    } else {
      this.selectedItems.filter(ws => ws.id !== workspace.id);
    }
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
      console.log({selectedItems})
      this._helperService
        .openWorkspaces.next(selectedItems.map(({name,...item}:any) =>({title:name,...item})));
      this.router.navigate(['/workspace'])
    }
  }
}
