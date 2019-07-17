import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Select, Store} from '@ngxs/store';
import {combineLatest, Subject} from 'rxjs';
import {data} from '../workspace-scope-completence/data';
import {takeUntil} from 'rxjs/operators';
import * as _ from 'lodash';
import {PltMainState} from '../../store/states';
import {WorkspaceMainState} from '../../../core/store/states';
import {BaseContainer} from '../../../shared/base';

@Component({
  selector: 'app-workspace-inuring',
  templateUrl: './workspace-inuring.component.html',
  styleUrls: ['./workspace-inuring.component.scss']
})
export class WorkspaceInuringComponent extends BaseContainer implements OnInit {

  check = true;
  @Select(PltMainState.getPlts) data$;
  @Select(WorkspaceMainState.getData) wsData$;

  dataSource: any;

  workspace: any;
  index: any;
  workspaceUrl: any;

  constructor(private route: ActivatedRoute, _baseStore:Store,_baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    this.dataSource = data.dataSource;
    combineLatest(
      this.wsData$,
      this.route.params
    ).pipe(this.unsubscribeOnDestroy)
      .subscribe(([data, {wsId, year}]: any) => {
        this.workspaceUrl = {wsId, uwYear: year};
        this.workspace = _.find(data, dt => dt.workSpaceId == wsId && dt.uwYear == year);
        console.log(this.workspace);
        this.index = _.findIndex(data, (dt: any) => dt.workSpaceId == wsId && dt.uwYear == year);
      });
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  protected detectChanges() {
    super.detectChanges();
  }

}
