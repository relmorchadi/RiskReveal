import { Component, OnInit } from '@angular/core';
import {Select, Store} from '@ngxs/store';
import {PltMainState} from '../../store/states';
import {WorkspaceMainState} from '../../../core/store/states';
import {combineLatest, Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import * as _ from 'lodash';
import {ActivatedRoute} from '@angular/router';
import {data} from './data';

@Component({
  selector: 'app-workspace-scope-completence',
  templateUrl: './workspace-scope-completence.component.html',
  styleUrls: ['./workspace-scope-completence.component.scss']
})
export class WorkspaceScopeCompletenceComponent implements OnInit {
  check = true;
  @Select(PltMainState.getPlts) data$;
  @Select(WorkspaceMainState.getData) wsData$;

  unSubscribe$: Subject<void>;

  dataSource: any;

  workspace: any;
  index: any;
  workspaceUrl: any;

  constructor(private route: ActivatedRoute, private store: Store) {
    this.unSubscribe$ = new Subject<void>();
  }

  ngOnInit() {
    this.dataSource = data.dataSource;
    combineLatest(
      this.wsData$,
      this.route.params
    ).pipe(takeUntil(this.unSubscribe$))
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

}
