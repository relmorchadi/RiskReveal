import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Select, Store} from '@ngxs/store';
import {combineLatest} from 'rxjs';
import {dataTable} from '../workspace-scope-completence/data';
import * as _ from 'lodash';
import {WorkspaceState} from '../../store/states';
import {BaseContainer} from '../../../shared/base';
import * as fromHeader from '../../../core/store/actions/header.action';
import * as fromWs from '../../store/actions';

@Component({
  selector: 'app-workspace-inuring',
  templateUrl: './workspace-inuring.component.html',
  styleUrls: ['./workspace-inuring.component.scss']
})
export class WorkspaceInuringComponent extends BaseContainer implements OnInit {

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
    // this.dataSource = dataTable.dataSource;
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
