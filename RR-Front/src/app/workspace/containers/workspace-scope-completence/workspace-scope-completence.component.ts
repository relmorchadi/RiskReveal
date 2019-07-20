import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Select, Store} from '@ngxs/store';
import {PltMainState} from '../../store/states';
import {WorkspaceMainState} from '../../../core/store/states';
import {combineLatest} from 'rxjs';
import * as _ from 'lodash';
import {ActivatedRoute, Router} from '@angular/router';
import {data} from './data';
import {BaseContainer} from '../../../shared/base';

@Component({
  selector: 'app-workspace-scope-completence',
  templateUrl: './workspace-scope-completence.component.html',
  styleUrls: ['./workspace-scope-completence.component.scss']
})
export class WorkspaceScopeCompletenceComponent extends BaseContainer implements OnInit {
  check = true;
  @Select(PltMainState.getPlts) data$;
  @Select(WorkspaceMainState.getData) wsData$;

  dataSource: any;

  workspace: any;
  index: any;
  workspaceUrl: any;

  constructor(private route: ActivatedRoute, _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
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

  sortByRegionPeril() {
  }

  perilZone(peril) {
    if (peril === 'YY') return {peril: 'EQ', color: '#E70010'};
    if (peril === 'WS') return {peril: 'WS', color: '#7BBE31'};
    if (peril === 'FL') return {peril: 'FL', color: '#008694'};
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  protected detectChanges() {
    super.detectChanges();
  }

}
