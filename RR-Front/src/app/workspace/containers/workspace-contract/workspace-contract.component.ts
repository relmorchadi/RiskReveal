import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {BaseContainer} from '../../../shared/base';
import {Select, Store} from '@ngxs/store';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from '../../../core/store/actions/header.action';
import * as fromWs from '../../store/actions';
import {LoadContractAction, UpdateWsRouting} from '../../store/actions';
import {Navigate} from "@ngxs/router-plugin";
import {WorkspaceState} from "../../store/states";
import * as _ from 'lodash';
import {ContractData} from './contract.data';

@Component({
  selector: 'app-workspace-contract',
  templateUrl: './workspace-contract.component.html',
  styleUrls: ['./workspace-contract.component.scss']
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
  colsRegionPeril;

  treatyData;

  reinstatementData;

  listStandardContent: any;

  listSecondaryContent;

  coveragesElement;

  @Select(WorkspaceState.getWorkspaces) ws$;
  ws: any;

  @Select(WorkspaceState.getContract) currentContract$;
  facDataInfo: any;
  treatyDataInfo: any;
  tabStatus: any;
  facData: any;

  contracts: any[];
  selectedContract: any;

  constructor(private route: ActivatedRoute, _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    this.dispatch(new LoadContractAction());
    this.ws$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.ws = _.merge({}, value);
      this.detectChanges();
    });
    this.currentContract$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
        this.tabStatus = _.get(value, 'typeWs', null);
        this.treatyDataInfo = _.get(value, 'treaty', null);
        this.facDataInfo = _.get(value, 'fac', []);
        this.contracts = this.facDataInfo.map(item => { return {id: item.id}; });
        this.selectedContract = this.contracts[0].id;
        this.facData = _.filter(this.facDataInfo, item => item.id === this.selectedContract)[0];
        this.detectChanges();
    });
    this.route.params.pipe(this.unsubscribeOnDestroy).subscribe(({wsId, year}) => {
      this.hyperLinksConfig = {
        wsId,
        uwYear: year
      };
    });
    this.scrollableColsTreaty = ContractData.scrollableColsTreaty;
    this.frozenColsTreaty = ContractData.frozenColsTreaty;
    this.scrollableColsFac = ContractData.scrollableColsFac;
    this.frozenColsFac = ContractData.frozenColsFac;
    this.colsReinstatement = ContractData.colsReinstatement;
    this.colsRegionPeril = ContractData.colsRegionPeril;
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

  selectContract(value) {
    this.selectedContract = value;
    this.facData = _.filter(this.facDataInfo, item => item.id === this.selectedContract)[0];
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  protected detectChanges() {
    super.detectChanges();
  }

}
