import {ChangeDetectorRef, Component, EventEmitter, OnInit} from '@angular/core';
import {BaseContainer} from '../../../shared/base';
import {Store} from '@ngxs/store';
import {Router} from '@angular/router';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from '../../../core/store/actions/header.action';
import * as fromWs from '../../store/actions';

@Component({
  selector: 'app-workspace-results',
  templateUrl: './workspace-results.component.html',
  styleUrls: ['./workspace-results.component.scss']
})
export class WorkspaceResultsComponent extends BaseContainer implements OnInit, StateSubscriber {

  wsIdentifier;
  workspaceInfo: any;

  constructor(_baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
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
