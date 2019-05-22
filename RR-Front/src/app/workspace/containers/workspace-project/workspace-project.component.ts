import {Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute} from '@angular/router';

import {Observable} from 'rxjs';
import {Select, Store} from '@ngxs/store';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import {WorkspaceMainState} from '../../../core/store/states/workspace-main.state';

import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import {SelectProjectAction} from '../../../core/store/actions/workspace-main.action';

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

  constructor(private _helper: HelperService, private route: ActivatedRoute, private nzDropdownService: NzDropdownService, private store: Store) {
    console.log('init project');
  }

  ngOnInit() {
    this.state$.subscribe(value => this.state = _.merge({}, value));
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

}
