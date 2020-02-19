import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {BaseContainer} from '../../../shared/base';
import {Store} from '@ngxs/store';
import {Router} from '@angular/router';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from "../../../core/store/actions/header.action";
import * as fromWs from "../../store/actions";
import {BehaviorSubject, Observable} from "rxjs";
import {ExposuresMainTableConfig} from "../../model/exposures-main-table-config.model";
import {ExposuresTableService} from "../../services/helpers/exposures-helpers/exposures-table.service";

@Component({
  selector: 'app-workspace-exposures',
  templateUrl: './workspace-exposures.component.html',
  styleUrls: ['./workspace-exposures.component.scss'],
  providers:[ExposuresTableService]
})
export class WorkspaceExposuresComponent extends BaseContainer implements OnInit, StateSubscriber {
  wsIdentifier;
  workspaceInfo: any;
  tableConfig: Observable<ExposuresMainTableConfig>;

  constructor(_baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,private exposuresTableService:ExposuresTableService) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.tableConfig = new BehaviorSubject<ExposuresMainTableConfig>(new ExposuresMainTableConfig());
  }

  ngOnInit() {
   this.tableConfig =  this.exposuresTableService.loadTableConfig()
  }

  patchState({wsIdentifier, data}: any): void {
    this.workspaceInfo = data;
    this.wsIdentifier = wsIdentifier;
  }

  pinWorkspace() {
    this.dispatch([new fromHeader.TogglePinnedWsState({
      "userId": 1,
      "workspaceContextCode": this.workspaceInfo.wsId,
      "workspaceUwYear": this.workspaceInfo.uwYear
    })]);
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  protected detectChanges() {
    super.detectChanges();
  }

  mainTableActionDispatcher($event: any) {
    this.exposuresTableService.sortTableColumn($event);
  }
}
