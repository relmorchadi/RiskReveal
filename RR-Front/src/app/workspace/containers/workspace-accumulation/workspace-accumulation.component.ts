import {ChangeDetectorRef, Component, EventEmitter, OnInit} from '@angular/core';
import {Select, Store} from '@ngxs/store';
import {PltMainState} from '../../store/states';
import {WorkspaceMainState} from '../../../core/store/states';
import {combineLatest} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {dataTable} from '../workspace-scope-completence/data';
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
    this.dataSource = dataTable.dataSource;
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

  patchState({wsIdentifier, data}: any): void {
    this.workspaceInfo = data;
    this.wsIdentifier = wsIdentifier;
  }

  pinWorkspace() {
    const {wsId, uwYear, workspaceName, programName, cedantName} = this.workspaceInfo;
    this.dispatch([
      new fromHeader.PinWs({
        wsId,
        uwYear,
        workspaceName,
        programName,
        cedantName
      }), new fromWs.MarkWsAsPinned({wsIdentifier: this.wsIdentifier})]);
  }

  unPinWorkspace() {
    const {wsId, uwYear} = this.workspaceInfo;
    this.dispatch([
      new fromHeader.UnPinWs({wsId, uwYear}),
      new fromWs.MarkWsAsNonPinned({wsIdentifier: this.wsIdentifier})
    ]);
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  protected detectChanges() {
    super.detectChanges();
  }

}
