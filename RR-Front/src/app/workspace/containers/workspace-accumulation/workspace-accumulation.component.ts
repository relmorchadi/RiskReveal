import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Select, Store} from '@ngxs/store';
import {WorkspaceState} from '../../store/states';
import {combineLatest} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import * as _ from 'lodash';
import {BaseContainer} from '../../../shared/base';
import {StateSubscriber} from "../../model/state-subscriber";
import * as fromHeader from "../../../core/store/actions/header.action";
import * as fromWs from "../../store/actions";

@Component({
  selector: 'app-workspace-accumulation',
  templateUrl: './workspace-accumulation.component.html',
  styleUrls: ['./workspace-accumulation.component.scss']
})
export class WorkspaceAccumulationComponent extends BaseContainer implements OnInit, StateSubscriber {

  wsIdentifier;
  workspaceInfo: any;

  check = true;
  @Select(WorkspaceState.getPlts) data$;

  dataSource: any;

  workspace: any;
  index: any;
  workspaceUrl: any;

  constructor(private route: ActivatedRoute, _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    //   this.dataSource = dataTable.dataSource;
    combineLatest(
      this.route.params
    ).pipe(this.unsubscribeOnDestroy)
      .subscribe(([data, {wsId, year}]: any) => {
        this.workspaceUrl = {wsId, uwYear: year};
        this.workspace = _.find(data, dt => dt.workSpaceId == wsId && dt.uwYear == year);
        this.index = _.findIndex(data, (dt: any) => dt.workSpaceId == wsId && dt.uwYear == year);
      });
  }

  patchState({wsIdentifier, data}: any): void {
    this.workspaceInfo = data;
    this.wsIdentifier = wsIdentifier;
  }

  pinWorkspace() {
    this.dispatch([new fromHeader.TogglePinnedWsState({
      "userId": 1,
      "workspaceContextCode": this.workspace.wsId,
      "workspaceUwYear": this.workspace.uwYear
    })]);
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  protected detectChanges() {
    super.detectChanges();
  }

}
