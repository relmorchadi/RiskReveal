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

  colsReinstatement;
  colsRegionPeril;

  treatyData;

  reinstatementData;

  listStandardContent: any;

  listSecondaryContent;

  coveragesElement;

  @Select(WorkspaceState.getCurrentWorkspaces) ws$;
  ws: any;

  @Select(WorkspaceState.getContract) currentContract$;
  facDataInfo: any;
  treatyDataInfo: any;
  tabStatus: any;
  wsStatus: any;
  facData = null;

  contracts: any[];
  selectedContract: any;

  currentWsIdentifier: any;
  selectedProject: any;

  constructor(private route: ActivatedRoute, _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    this.dispatch([new LoadContractAction(),new fromWs.LoadContractFacAction()]);
    this.select(WorkspaceState.getCurrentTab)
      .pipe(this.unsubscribeOnDestroy)
      .subscribe(curTab => {
        this.currentWsIdentifier = curTab.wsIdentifier;
        this.detectChanges();
      });
    this.currentContract$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.treatyDataInfo = _.get(value, 'treaty', null);
      this.facData = _.get(value, 'fac', null);
      this.detectChanges();
    });
    this.ws$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.ws = _.merge({}, value);
      this.wsStatus = this.ws.workspaceType;
      if (this.wsStatus === 'fac') {
        // this.dispatch(new fromWs.LoadContractFacAction());
        this.selectedProject = _.filter(this.ws.projects, item => item.selected)[0];
        this.tabStatus = _.get(this.selectedProject, 'projectType', 'TREATY');
      } else {
        this.tabStatus = 'TREATY';
      }
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
    this.dispatch([new fromHeader.TogglePinnedWsState({
      "userId": 1,
      "workspaceContextCode": this.workspace.wsId,
      "workspaceUwYear": this.workspace.uwYear
    })]);
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

  selectFacDivison(row) {
    this.dispatch(new fromWs.ToggleFacDivisonAction(row));
  }

  filterSelection() {
    const selectedDivision: any = _.filter(this.facData.division , item => item.selected);
    if (selectedDivision.length > 0) {
      const facDataFiltered = _.filter(this.facData.regionPeril, item => {
        return _.includes(item.division, selectedDivision[0].divisionNo);
      });
      return facDataFiltered.length > 0 ? facDataFiltered : this.facData.regionPeril;
    } else {
      return this.facData.regionPeril;
    }
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  protected detectChanges() {
    super.detectChanges();
  }

}
