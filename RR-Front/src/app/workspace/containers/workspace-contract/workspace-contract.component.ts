import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {BaseContainer} from '../../../shared/base';
import {Select, Store} from '@ngxs/store';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from '../../../core/store/actions/header.action';
import * as fromWs from '../../store/actions';
import {UpdateWsRouting} from '../../store/actions';
import {Navigate} from "@ngxs/router-plugin";
import {WorkspaceState} from "../../store/states";
import * as _ from 'lodash';
import {ContractData} from './contract.data';

@Component({
  selector: 'app-workspace-contract',
  templateUrl: './workspace-contract.component.html',
  styleUrls: ['./workspace-contract.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class WorkspaceContractComponent extends BaseContainer implements OnInit, StateSubscriber {
  collapseHead = false;
  collapseLeft = false;
  collapseRight = false;

  wsIdentifier;
  workspace: any;

  hyperLinks: string[] = ['Projects', 'Contract', 'Activity'];
  hyperLinksRoutes: any = {
    'Projects': '/projects',
    'Contract': '/Contract',
    'Activity': '/Activity'
  };
  hyperLinksConfig: {
    wsId: string,
    uwYear: string
  };

  scrollableColsTreaty ;
  frozenColsTreaty;

  scrollableColsFac ;
  frozenColsFac ;

  colsReinstatement;

  treatyData;

  reinstatementData;

  listStandardContent;

  listSecondaryContent;

  coveragesElement;

  @Select(WorkspaceState.getWorkspaces) ws$;
  ws: any;

  @Select(WorkspaceState.getCurrentTab) currentTab$;
  tabStatus: any;
  facData: any;

  constructor(private route: ActivatedRoute, _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    this.ws$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.ws = _.merge({}, value);
      this.detectChanges();
    });
    this.route.params.pipe(this.unsubscribeOnDestroy).subscribe(({wsId, year}) => {
      this.hyperLinksConfig = {
        wsId,
        uwYear: year
      };
      this.tabStatus = this.ws[wsId + '-' + year].workspaceType;
      this.facData = _.filter(_.get(this.ws, `${wsId + '-' + year}.projects`, []), item => item.selected)[0];
    });
    this.scrollableColsTreaty = ContractData.scrollableColsTreaty;
    this.frozenColsTreaty = ContractData.frozenColsTreaty;
    this.scrollableColsFac = ContractData.scrollableColsFac;
    this.frozenColsFac = ContractData.frozenColsFac;
    this.colsReinstatement = ContractData.colsReinstatement;
    this.treatyData = ContractData.treatyData;
    this.reinstatementData = ContractData.reinstatementData;
    this.listStandardContent = ContractData.listStandardContent;
    this.listSecondaryContent = ContractData.listSecondaryContent;
    this.coveragesElement = ContractData.coveragesElement;
  }

  patchState({wsIdentifier, data}: any): void {
    this.workspace = data;
    this.wsIdentifier = wsIdentifier;
  }

  pinWorkspace() {
    const {wsId, uwYear, workspaceName, programName, cedantName} = this.workspace;
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
    const {wsId, uwYear} = this.workspace;
    this.dispatch([
      new fromHeader.UnPinWs({wsId, uwYear}),
      new fromWs.MarkWsAsNonPinned({wsIdentifier: this.wsIdentifier})
    ]);
  }

  changeCollapse() {
    this.collapseHead = !this.collapseHead;
  }

  navigateFromHyperLink({route}) {
    const {wsId, uwYear} = this.workspace;
    this.dispatch(
      [new UpdateWsRouting(this.wsIdentifier, route),
        new Navigate(route ? [`workspace/${wsId}/${uwYear}/${route}`] : [`workspace/${wsId}/${uwYear}/projects`])]
    );
  }


  ngOnDestroy(): void {
    this.destroy();
  }

  protected detectChanges() {
    super.detectChanges();
  }

}
