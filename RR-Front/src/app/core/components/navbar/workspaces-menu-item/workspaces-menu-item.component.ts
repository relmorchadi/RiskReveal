import {Component, OnInit} from '@angular/core';
import {HelperService} from '../../../../shared/helper.service';
import {Router} from '@angular/router';
import {SearchService} from '../../../service/search.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import * as _ from 'lodash';
import {forkJoin, Observable, of} from 'rxjs';
import {Select, Store} from '@ngxs/store';
import {WorkspaceMain} from '../../../model/workspace-main';
import {WorkspaceMainState} from '../../../store/states/workspace-main.state';
import {NotificationService} from '../../../../shared/notification.service';
import {
  LoadWorkspacesAction,
  OpenNewWorkspacesAction,
  PatchWorkspaceMainStateAction
} from '../../../store/actions/workspace-main.action';

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
              private _fb: FormBuilder, private store: Store, private notificationService: NotificationService) {
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
    this.state.recentWs.filter(ws => ws.selected).forEach(ws => {
      window.open(`/workspace/${ws.workSpaceId}/${ws.uwYear}/PopOut`);
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
              this.router.navigate([`/workspace/${this.state.openedWs.workSpaceId}/${this.state.openedWs.uwYear}`]);
              this.visible = false;
              this.workspaces.forEach(ws => ws.selected = false);
            }
          }
        );
      }
    );
  }

  navigateToTab(value) {
    if (value.routing == 0) {
      this.router.navigate([`workspace/${value.workSpaceId}/${value.uwYear}`]);
    } else {
      this.router.navigate([`workspace/${value.workSpaceId}/${value.uwYear}/${value.routing}`]);
    }
  }

  redirectWorkspace() {
    if (this.state.openedTabs.data.length > 0) {
      this.navigateToTab(this.state.openedTabs.data[0]);
      this.store.dispatch(new PatchWorkspaceMainStateAction({key: 'openedWs', value: this.state.openedTabs.data[0]}));
    } else {
      this.notificationService.createNotification('Information',
        'There is no Opened Workspaces please try searching for some before!',
        'error', 'bottomRight', 4000);
    }
  }

  searchWorkspace(value) {
    const paginationOption = this.state.workspacePagination.paginationList.filter(page => page.id === value);
    console.log({shownElement: paginationOption[0].shownElement});
    this.store.dispatch(new PatchWorkspaceMainStateAction({key: 'appliedFilter', value: {shownElement: paginationOption[0].shownElement}}));
  }

}
