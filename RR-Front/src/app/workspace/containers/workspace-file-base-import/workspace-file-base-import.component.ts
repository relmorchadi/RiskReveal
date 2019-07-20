import {ChangeDetectorRef, Component, EventEmitter, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Store} from '@ngxs/store';
import {BaseContainer} from '../../../shared/base';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from '../../../core/store/actions/header.action';
import * as fromWs from '../../store/actions';

@Component({
  selector: 'app-workspace-file-base-import',
  templateUrl: './workspace-file-base-import.component.html',
  styleUrls: ['./workspace-file-base-import.component.scss']
})
export class WorkspaceFileBaseImportComponent extends BaseContainer implements OnInit, StateSubscriber {

  actionsEmitter: EventEmitter<any>;
  wsIdentifier;
  workspaceInfo: any;

  hyperLinks: string[] = ['Risk link', 'File-based'];
  hyperLinksRoutes: any = {
    'Risk link': '/RiskLink',
    'File-based': '/FileBasedImport'
  };
  hyperLinksConfig: {
    wsId: string,
    uwYear: string
  };

  constructor(private route: ActivatedRoute, _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.actionsEmitter = new EventEmitter();
  }

  ngOnInit() {
    this.route.params.pipe(this.unsubscribeOnDestroy).subscribe(({wsId, year}) => {
      this.hyperLinksConfig = {
        wsId,
        uwYear: year
      };
    });
  }

  patchState({wsIdentifier, data}: any): void {
    this.workspaceInfo = data;
    console.log('this is ws data', data);
    this.wsIdentifier = wsIdentifier;
  }

  pinWorkspace() {
    const {wsId, uwYear, workspaceName, programName, cedantName} = this.workspaceInfo;
    this.actionsEmitter.emit([
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
    this.actionsEmitter.emit([
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
