import { Component, OnInit } from '@angular/core';
import {Select, Store} from '@ngxs/store';
import {PltMainState} from '../../store/states';
import {WorkspaceMainState} from '../../../core/store/states';
import {combineLatest, Subject} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {data} from '../workspace-scope-completence/data';
import {takeUntil} from 'rxjs/operators';
import * as _ from 'lodash';

@Component({
  selector: 'app-workspace-accumulation',
  templateUrl: './workspace-accumulation.component.html',
  styleUrls: ['./workspace-accumulation.component.scss']
})
export class WorkspaceAccumulationComponent implements OnInit {

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

}
