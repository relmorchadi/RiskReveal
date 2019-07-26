import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Select, Store} from '@ngxs/store';
import {WorkspaceState} from '../../store/states';
import {WorkspaceMainState} from '../../../core/store/states';
import {combineLatest} from 'rxjs';
import * as _ from 'lodash';
import {ActivatedRoute, Router} from '@angular/router';
import {dataTable} from './data';
import {BaseContainer} from '../../../shared/base';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from '../../../core/store/actions/header.action';
import * as fromWs from '../../store/actions';

@Component({
  selector: 'app-workspace-scope-completence',
  templateUrl: './workspace-scope-completence.component.html',
  styleUrls: ['./workspace-scope-completence.component.scss']
})
export class WorkspaceScopeCompletenceComponent extends BaseContainer implements OnInit, StateSubscriber {
  check = true;
  @Select(WorkspaceState.getPlts) data$;
  @Select(WorkspaceMainState.getData) wsData$;

  wsIdentifier;

  dataSource: any;

  workspaceInfo: any;

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
      .subscribe(([dtt, {wsId, year}]: any) => {
        this.workspaceUrl = {wsId, uwYear: year};
        this.workspace = _.find(dtt, dt => dt.workSpaceId == wsId && dt.uwYear == year);
        this.index = _.findIndex(dtt, (dt: any) => dt.workSpaceId == wsId && dt.uwYear == year);
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

  sortByRegionPeril() {
  }

  perilZone(peril) {
    if (peril === 'YY') {
      return {peril: 'EQ', color: '#E70010'};
    }
    if (peril === 'WS') {
      return {peril: 'WS', color: '#7BBE31'};
    }
    if (peril === 'FL') {
      return {peril: 'FL', color: '#008694'};
    }
  }

  ngOnDestroy(): void {
    this.destroy();
  }


}
