import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {BaseContainer} from "../../../shared/base";
import {StateSubscriber} from "../../model/state-subscriber";
import {Store} from "@ngxs/store";
import {Router} from "@angular/router";
import * as fromWorkspaceStore from '../../store';
import * as _ from "lodash";
import {CalibrationTableService} from "../../services/helpers/calibrationTable.service";
import {WorkspaceState} from "../../store";
import {takeWhile} from "rxjs/operators";
import {Message} from "../../../shared/message";

@Component({
  selector: 'app-workspace-calibration-new',
  templateUrl: './workspace-calibration-new.component.html',
  styleUrls: ['./workspace-calibration-new.component.scss']
})
export class WorkspaceCalibrationNewComponent extends BaseContainer implements OnInit, OnDestroy, StateSubscriber {

  wsIdentifier: string;
  wsId: string;
  uwYear: string;
  workspaceType: string;

  data: any[];
  epMetrics: any;
  adjustments: any;
  loading: boolean;

  //Table Config
  tableConfig: {
    mode: 'default' | 'grouped',
    view: 'adjustments' | 'analysis' | 'epMetrics',
    selectedCurveType: string,
    isExpanded: boolean
  };
  columnsConfig: {
    frozenColumns: any[],
    columns: any[],
    columnsLength: number
  };
  curveTypes: string[];
  isGrouped: boolean;
  rowKeys: any;

  constructor(
    _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,
    private calibrationTableService: CalibrationTableService
  ) {
    super(_baseRouter, _baseCdr, _baseStore);

    this.data = [];
    this.tableConfig = {
      mode: "default",
      view: "epMetrics",
      selectedCurveType: "OEP",
      isExpanded: false
    };
    this.curveTypes = ['OEP', 'AEP', 'OEP-TVAR', 'OEP-TVAR'];
    this.isGrouped= false;
    this.rowKeys= {};
  }

  ngOnInit() {

  }

  ngOnDestroy(): void {
    this.destroy();
  }

  selectState = ({wsIdentifier, data : {wsId, uwYear, calibrationNew, workspaceType} }) => ({workspaceType,wsIdentifier, wsId, uwYear, calibrationNew});

  patchState(state): void {
    const {
      wsIdentifier,
      wsId, uwYear,
      calibrationNew,
      workspaceType
    } = this.selectState(state);

    this.iniRouting(wsIdentifier, wsId, uwYear, workspaceType);
    this.initComponent(calibrationNew);

    this.detectChanges();
  }

  iniRouting(wsIdentifier, wsId, uwYear, workspaceType) {

    if( !this.wsId && wsId && !this.uwYear && uwYear ) {
      //INIT
      console.log(workspaceType);
      this.calibrationTableService.setWorkspaceType(workspaceType);
      this.columnsConfig = this.calibrationTableService.getColumns(this.tableConfig.view, this.tableConfig.isExpanded);

      //SUBS
      this.subscribeToEpMetrics(wsId + "-" + uwYear);

      //Others
      this.loadCalibrationPlts(wsId, uwYear);
      this.loadAdjustments(wsId, uwYear);
      this.loadEpMetrics(wsId, uwYear, 1, 'OEP');
    }

    this.wsIdentifier = wsIdentifier;
    this.wsId = wsId;
    this.uwYear = uwYear;
  }

  loadCalibrationPlts(wsId: string, uwYear: number) {
    this.dispatch(new fromWorkspaceStore.LoadGroupedPltsByPure({wsId ,uwYear}));
  }

  loadAdjustments(wsId: string, uwYear: number) {
    this.dispatch(new fromWorkspaceStore.LoadDefaultAdjustmentsInScope({wsId, uwYear}))
  }

  loadEpMetrics(wsId: string, uwYear: number, userId: number, curveType: string) {
    this.dispatch(new fromWorkspaceStore.LoadEpMetrics({wsId, uwYear, userId, curveType}));
  }

  subscribeToEpMetrics (wsIdentifier: string) {
    this.select(WorkspaceState.getEpMetrics(wsIdentifier))
      .pipe(
        takeWhile(v => !_.isNil(v)),
        this.unsubscribeOnDestroy
      )
      .subscribe(epMetrics => {
      this.epMetrics = epMetrics;

      this.initEpMetricsCols(this.epMetrics);

      this.detectChanges();
    })
  }

  initComponent(state) {

    const {
      plts,
      adjustments,
      loading
    } = state;

    this.data = plts;
    this.adjustments = adjustments;
    this.loading = loading;
  }

  initEpMetricsCols({cols}) {
    this.calibrationTableService.setCols(
      cols,
      'epMetrics'
    );
    this.columnsConfig = this.calibrationTableService.getColumns(this.tableConfig.view, this.tableConfig.isExpanded)
  }

  onViewChange(newView) {

    this.tableConfig = {
      ...this.tableConfig,
      view: newView
    };
    this.columnsConfig = this.calibrationTableService.getColumns(newView, this.tableConfig.isExpanded)
  }

  setIsGrouped(newIsGrouped) {
    this.isGrouped = newIsGrouped;
  }

  handleTableActions(action: Message) {
    switch (action.type) {

      case 'View Change':

        this.onViewChange(action.payload);
        break;

      case 'IsGrouped Change':

        this.setIsGrouped(action.payload);
        break;

      case "Expand columns OFF":
        this.expandColumnsOff();
        break;

      case "Expand columns ON":
        this.expandColumns();
        break;

      default:
        console.log(action);
    }
  }

  expandColumns() {
    this.tableConfig = {
      ...this.tableConfig,
      isExpanded: true
    };
    this.columnsConfig = this.columnsConfig = this.calibrationTableService.getColumns(this.tableConfig.view, this.tableConfig.isExpanded)
  }

  expandColumnsOff() {
    this.tableConfig = {
      ...this.tableConfig,
      isExpanded: false
    };
    this.columnsConfig = this.columnsConfig = this.calibrationTableService.getColumns(this.tableConfig.view, this.tableConfig.isExpanded)
  }
}
