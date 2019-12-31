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
import {CalibrationAPI} from "../../services/api/calibration.api";
import {Subscription} from "rxjs";

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
  adjustmentTypes: any[];
  basis: any[];
  loading: boolean;

  //Table Config
  tableConfig: {
    view: 'adjustments' | 'analysis' | 'epMetrics',
    selectedCurveType: string,
    isExpanded: boolean,
    expandedRowKeys: any,
    isGrouped: boolean
  };
  columnsConfig: {
    frozenColumns: any[],
    frozenWidth: string,
    columns: any[],
    columnsLength: number
  };
  selectedCurveType: string;
  curveTypes: string[];
  rowKeys: any;

  //POP-UPs
  selectedAdjustment: any;
  isAdjustmentPopUpVisible: boolean;

  isRPPopUpVisible: boolean;
  returnPeriodConfig: {
    currentRPs: number[],
    newlyAdded: number[],
    returnPeriodInput: number,
    showSuggestion: boolean,
    message: string
  };
  rpValidation: {
    isValid: boolean,
    upperBound: number,
    lowerBound: number
  };

  //Sub
  validationSub: Subscription;


  constructor(
    _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,
    private calibrationTableService: CalibrationTableService,
    private calibrationApi: CalibrationAPI
  ) {
    super(_baseRouter, _baseCdr, _baseStore);

    this.data = [];
    this.adjustmentTypes= [];
    this.basis= [];
    this.tableConfig = {
      view: "adjustments",
      selectedCurveType: "OEP",
      isExpanded: false,
      expandedRowKeys: {},
      isGrouped: true
    };
    this.columnsConfig = {
      ...this.columnsConfig,
      frozenWidth: '530px'
    };
    this.selectedCurveType = 'OEP';
    this.curveTypes = ['OEP', 'AEP', 'OEP-TVAR', 'OEP-TVAR'];
    this.rowKeys= {};

    this.isAdjustmentPopUpVisible= false;
    this.isRPPopUpVisible= false;
    this.returnPeriodConfig= {
      newlyAdded: [],
      currentRPs: [],
      showSuggestion: false,
      returnPeriodInput: null,
      message: null
    };
    this.rpValidation= {
      isValid: false,
      lowerBound: null,
      upperBound: null
    }
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

    this.wsIdentifier = wsIdentifier;

    this.iniRouting(wsIdentifier, wsId, uwYear, workspaceType);
    this.initComponent(calibrationNew);

    this.detectChanges();
  }

  iniRouting(wsIdentifier, wsId, uwYear, workspaceType) {

    if( !this.wsId && wsId && !this.uwYear && uwYear ) {
      //INIT
      this.calibrationTableService.setWorkspaceType(workspaceType);
      this.columnsConfig = {
        ...this.columnsConfig,
        ...this.calibrationTableService.getColumns(this.tableConfig.view, this.tableConfig.isExpanded)
      };

      //SUB
      this.subscribeToAdjustments(wsId + "-" + uwYear);
      this.subscribeToEpMetrics(wsId + "-" + uwYear);
      this.subscribeToConstants(wsId + "-" + uwYear);

      //Others
      this.loadConstants();
      this.loadCalibrationPlts(wsId, uwYear);
      this.loadAdjustments(wsId, uwYear);
      this.loadEpMetrics(wsId, uwYear, 1, this.selectedCurveType);
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

  loadConstants() {
    this.dispatch(new fromWorkspaceStore.LoadCalibrationConstants())
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

  subscribeToAdjustments (wsIdentifier: string) {
    this.select(WorkspaceState.getAdjustments(wsIdentifier))
      .pipe(
        takeWhile(v => !_.isNil(v)),
        this.unsubscribeOnDestroy
      )
      .subscribe(adjustments => {
        this.adjustments = adjustments;

        this.detectChanges();
      })
  }

  subscribeToConstants(wsIdentifier: string) {
    this.select(WorkspaceState.getCalibrationConstants(wsIdentifier))
      .pipe(
        takeWhile(v => !_.isNil(v)),
        this.unsubscribeOnDestroy
      )
      .subscribe(({basis, adjustmentTypes}) => {
        this.basis = basis;
        this.adjustmentTypes = adjustmentTypes;

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

  initEpMetricsCols({cols, rps}) {
    this.returnPeriodConfig= {
      ...this.returnPeriodConfig,
      currentRPs: rps
    };
    this.calibrationTableService.setCols(
      cols,
      'epMetrics'
    );
    this.columnsConfig = {
      ...this.columnsConfig,
      ...this.calibrationTableService.getColumns(this.tableConfig.view, this.tableConfig.isExpanded)
    };
  }

  onViewChange(newView) {

    this.tableConfig = {
      ...this.tableConfig,
      view: newView
    };
    this.columnsConfig = {
      ...this.columnsConfig,
      ...this.calibrationTableService.getColumns(newView, this.tableConfig.isExpanded)
    };
  }

  toggleGrouping() {
    this.tableConfig = {
      ...this.tableConfig,
      isGrouped: !this.tableConfig.isGrouped,
      expandedRowKeys: this.tableConfig.isGrouped ? {} : this.tableConfig.expandedRowKeys
    };
  }

  handleTableActions(action: Message) {
    switch (action.type) {

      case 'View Change':
        this.onViewChange(action.payload);
        break;

      case 'Toggle Grouping':
        this.toggleGrouping();
        break;

      case "Expand columns OFF":
        this.expandColumnsOff();
        break;

      case "Expand columns ON":
        this.expandColumns();
        break;

      case "View Adjustment Detail":
        this.viewAdjustmentDetail(action.payload);
        break;

      case "Resize frozen Column":
        this.resizeFrozenColumn(action.payload);
        this.detectChanges();
        break;

      case "Row Expand change":
        this.rowExpandChange(action.payload);
        break;

      case "Open return periods manager":
        this.openRPManager();
        break;

      default:
        console.log(action);
    }
  }

  handleAdjustmentPopUp(action: Message) {
    switch (action.type) {
      case "Hide Adjustment Pop up":
        this.isAdjustmentPopUpVisible= false;
        this.selectedAdjustment = null;
        break;
      default:
        console.log(action);
    }
  }

  handleReturnPeriodPopUp(action: Message) {
    switch (action.type) {
      case "Return period popup input change":
        this.rpInputChange(action.payload);
        break;
      case "ADD Return period":
        this.addReturnPeriod(action.payload);
        break;
      case "Save return periods":
        this.saveRPs();
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
    this.columnsConfig = {
      ...this.columnsConfig,
      frozenWidth: '0px',
      ...this.calibrationTableService.getColumns(this.tableConfig.view, this.tableConfig.isExpanded)
    };
  }

  expandColumnsOff() {
    this.tableConfig = {
      ...this.tableConfig,
      isExpanded: false
    };
    this.columnsConfig = {
      ...this.columnsConfig,
      frozenWidth: '530px',
      ...this.calibrationTableService.getColumns(this.tableConfig.view, this.tableConfig.isExpanded)
    };
  }

  viewAdjustmentDetail(newAdjustment) {
    this.selectedAdjustment = {...newAdjustment};
    this.isAdjustmentPopUpVisible = true;
  }

  resizeFrozenColumn(delta) {
    this.columnsConfig = {
      ...this.columnsConfig,
      frozenWidth: ( _.toNumber(_.trimEnd(this.columnsConfig.frozenWidth, "px")) + delta ) + "px"
    }
  }

  rowExpandChange(rowExpandKeys) {
    this.tableConfig = {
      ...this.tableConfig,
      expandedRowKeys: rowExpandKeys
    }
  }

  openRPManager(){
    this.isRPPopUpVisible= true;
  }

  addReturnPeriod(rp) {

    this.validationSub = this.calibrationApi.validateRP(rp)
      .subscribe((validation: any) => {
        if(validation.isValid) {
          if(_.find([...this.returnPeriodConfig.currentRPs, ...this.returnPeriodConfig.newlyAdded], oldRp => oldRp == rp)) {
            this.returnPeriodConfig = {
              ...this.returnPeriodConfig,
              showSuggestion: false,
              returnPeriodInput: null,
              message: "Already exists"
            };
          } else {
            this.returnPeriodConfig = {
              ...this.returnPeriodConfig,
              showSuggestion: false,
              returnPeriodInput: null,
              newlyAdded: _.concat(this.returnPeriodConfig.newlyAdded, [rp])
            };
          }
        } else {
          this.returnPeriodConfig = {
            ...this.returnPeriodConfig,
            showSuggestion: true
          }
        }

        this.rpValidation = validation;
        this.detectChanges();

        this.validationSub && this.validationSub.unsubscribe()
      })
  }

  rpInputChange(newValue) {
    this.returnPeriodConfig = {
      ...this.returnPeriodConfig,
      returnPeriodInput: newValue,
    }
  }

  saveRPs() {
    this.dispatch(new fromWorkspaceStore.SaveRPs({
      rps: this.returnPeriodConfig.newlyAdded,
      userId: 1
    }))
  }
}
