import {Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute, Router} from '@angular/router';

import {combineLatest, Observable} from 'rxjs';
import {Select, Store} from '@ngxs/store';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import {WorkspaceMainState} from '../../../core/store/states/workspace-main.state';

import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import {PatchWorkspace, SelectProjectAction} from '../../../core/store/actions/workspace-main.action';
import * as moment from 'moment'

@Component({
  selector: 'app-workspace-project',
  templateUrl: './workspace-project.component.html',
  styleUrls: ['./workspace-project.component.scss']
})
export class WorkspaceProjectComponent implements OnInit {
  leftNavbarIsCollapsed = false;
  collapseWorkspaceDetail = true;
  componentSubscription: any = [];
  selectedPrStatus = '1';
  private dropdown: NzDropdownContextComponent;

  @Select(WorkspaceMainState)
  state$: Observable<WorkspaceMain>;
  state: WorkspaceMain = null;
  workspaceUrl: any;
  workspace: any;
  index: any;

  @Select(WorkspaceMainState.getData) data$;

  constructor(private _helper: HelperService, private route: ActivatedRoute,
              private nzDropdownService: NzDropdownService, private store: Store,
              private router: Router
  ) {
    console.log('init project');
  }

  ngOnInit() {
    this.state$.subscribe(value => {this.state = _.merge({}, value); this});

    combineLatest(
      this.data$,
      this.route.params
    ).subscribe( ([data, {wsId, year}]: any) => {

      this.workspaceUrl= {
        wsId,
        uwYear: year
      }

      this.workspace = _.find(data, dt => dt.workSpaceId == wsId  && dt.uwYear == year);
      this.index = _.findIndex(data, (dt: any) => dt.workSpaceId == wsId  && dt.uwYear == year);
    })
  }

  selectProject(project) {
    this.store.dispatch(new SelectProjectAction(project));
  }

  contextMenu($event: MouseEvent, template: TemplateRef<void>): void {
    this.dropdown = this.nzDropdownService.create($event, template);
  }

  close(e: NzMenuItemDirective): void {
    console.log(e);
    this.dropdown.close();
  }

  pinWorkspace() {
    this.store.dispatch(new PatchWorkspace({
      key: ['pinged','lastPModified'],
      value: [!this.workspace.pinged,moment().format('x')] ,
      ws: this.workspace,
      k: this.index
    }))

    let workspaceMenuItem = JSON.parse(localStorage.getItem('workSpaceMenuItem')) || {};

    if(this.workspace.pinged){
      workspaceMenuItem[this.workspace.workSpaceId + '-'+ this.workspace.uwYear] = {...this.workspace,pinged: true, lastPModified: moment().format('x')};
    }else{
      workspaceMenuItem = {...workspaceMenuItem, [this.workspace.workSpaceId + '-'+ this.workspace.uwYear]: _.omit(workspaceMenuItem[this.workspace.workSpaceId + '-'+ this.workspace.uwYear], ['pinged','lastPModified'])};
    }
    localStorage.setItem('workSpaceMenuItem',JSON.stringify(workspaceMenuItem));
  }
}
