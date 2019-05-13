import {Component, OnInit} from '@angular/core';
import {HelperService} from '../../../../shared/helper.service';
import {Router} from '@angular/router';
import {SearchService} from '../../../service/search.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import * as _ from 'lodash';
import {forkJoin, Observable, of} from 'rxjs';
import {LoadWorkspacesAction, OpenNewWorkspacesAction, PatchWorkspaceMainStateAction} from '../../../store/actions';
import {Select, Store} from '@ngxs/store';
import {WorkspaceMainState} from '../../../store/states';
import {WorkspaceMain} from '../../../model/workspace-main';

@Component({
  selector: 'workspaces-menu-item',
  templateUrl: './workspaces-menu-item.component.html',
  styleUrls: ['./workspaces-menu-item.component.scss']
})
export class WorkspacesMenuItemComponent implements OnInit {
  contractFilterFormGroup: FormGroup;
  workspaces: any = [];
  selectedWorkspace = null;
  numberofElement: number;
  lastOnes = 0;
  visible: any;
  labels: any = [];

  @Select(WorkspaceMainState)
  state$: Observable<WorkspaceMain>;
  state: WorkspaceMain = null;

  constructor(private _helperService: HelperService, private router:Router, private _searchService: SearchService,
              private _fb: FormBuilder, private store: Store) {
    this.setForm();
  }

  ngOnInit() {
    this.store.dispatch(new LoadWorkspacesAction());
    this.state$.subscribe(value => this.state = _.merge({}, value));
    this._searchService.infodropdown.subscribe( dt => this.visible = this._searchService.getvisibleDropdown());
  }

  private searchData(id, year) {
    return this._searchService.searchWorkspace(id || '', year || '2019');
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

  searchNewWorkspace(search) {
    this.contractFilterFormGroup.patchValue({cedant: search.target.value});
  }

  toggleWorkspace(workspace) {
    workspace.selected = !workspace.selected;
  }

  popOutWorkspaces() {
    this.visible = false;
    console.log(this.state.recentWs);
    this.state.recentWs.filter(ws => ws.selected).forEach(ws => {
      window.open('/workspace/' + (ws.id || ws.workSpaceId) + '/' + (ws.year || ws.uwYear));
    });
  }

  async openWorkspaces() {
    const selectedItems = [ ...this.state.recentWs.filter(ws => ws.selected)];
    let workspaces = [];
    selectedItems.forEach(
      (SI) => {
        this.searchData(SI.workSpaceId, SI.uwYear).subscribe(
          (dt: any) => {
            const workspace = {
              workSpaceId: SI.workSpaceId,
              uwYear: SI.uwYear,
              selected: false,
              ...dt
            };
            workspaces = [workspace, ...workspaces];
            if (workspaces.length === selectedItems.length) {
              this.store.dispatch(new OpenNewWorkspacesAction(workspaces));
              this._helperService.updateWorkspaceItems();
              this._helperService.updateRecentWorkspaces();
              this.router.navigate(['/workspace']);
              this.visible = false;
              this.workspaces.forEach(ws => ws.selected = false);
            }
          }
        );
      }
    );

  }
}
