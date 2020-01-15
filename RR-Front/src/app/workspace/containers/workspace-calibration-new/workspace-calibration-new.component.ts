import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {BaseContainer} from "../../../shared/base";
import {StateSubscriber} from "../../model/state-subscriber";
import {Actions, ofActionCompleted, Store} from "@ngxs/store";
import {Router} from "@angular/router";
import * as fromWorkspaceStore from '../../store';
import * as _ from "lodash";
import {CalibrationTableService} from "../../services/helpers/calibrationTable.service";
import {WorkspaceState} from "../../store";
import {first, take, takeWhile} from "rxjs/operators";
import {Message} from "../../../shared/message";
import {CalibrationAPI} from "../../services/api/calibration.api";
import {combineLatest, Subscription} from "rxjs";
import {ExcelService} from "../../../shared/services/excel.service";

@Component({
  selector: 'app-workspace-calibration-new',
  templateUrl: './workspace-calibration-new.component.html',
  styleUrls: ['./workspace-calibration-new.component.scss']
})
export class WorkspaceCalibrationNewComponent extends BaseContainer implements OnInit, OnDestroy, StateSubscriber {

  wsIdentifier: string;
  wsId: string;
  uwYear: number;
  workspaceType: string;

  data: any[];
  epMetrics: any;
  adjustments: any;
  adjustmentTypes: any[];
  basis: any[];
  status: any[];
  loading: boolean;

  //Table Config
  tableConfig: {
    view: 'adjustments' | 'analysis' | 'epMetrics',
    selectedCurveType: string,
    selectedFinancialUnit: string,
    isExpanded: boolean,
    expandedRowKeys: any,
    isGrouped: boolean,
    isDeltaByAmount: boolean
  };
  constants: {
    financialUnits: string[],
    curveTypes: string[]
  };
  columnsConfig: {
    frozenColumns: any[],
    frozenWidth: string,
    columns: any[],
    columnsLength: number
  };
  rowKeys: any;

  //POP-UPs
  selectedAdjustment: any;
  selectedStatusFilter: any;
  isAdjustmentPopUpVisible: boolean;

  isRPPopUpVisible: boolean;
  returnPeriodConfig: {
    currentRPs: number[],
    newlyAdded: number[],
    deletedRPs: number[],
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
    private calibrationApi: CalibrationAPI,
    private actions$: Actions,
    private excel: ExcelService
  ) {
    super(_baseRouter, _baseCdr, _baseStore);

    this.data = [];
    this.adjustmentTypes= [];
    this.basis= [];
    this.tableConfig = {
      view: "adjustments",
      selectedCurveType: "OEP",
      isGrouped: true,
      isExpanded: false,
      isDeltaByAmount: false,
      expandedRowKeys: {},
      selectedFinancialUnit: "Unit"
    };
    this.columnsConfig = {
      ...this.columnsConfig,
      frozenWidth: '530px'
    };

    this.constants = {
      curveTypes: ['OEP', 'OEP-TVAR', 'AEP', 'AEP-TVAR'],
      financialUnits: [ 'Unit', 'Thousands', 'Million', 'Billion']
    };
    this.rowKeys= {};

    this.isAdjustmentPopUpVisible= false;
    this.isRPPopUpVisible= false;
    this.returnPeriodConfig= {
      newlyAdded: [],
      currentRPs: [],
      deletedRPs: [],
      showSuggestion: false,
      returnPeriodInput: null,
      message: null
    };
    this.rpValidation= {
      isValid: false,
      lowerBound: null,
      upperBound: null
    };
    this.selectedStatusFilter= {};
  }

  ngOnInit() {

    this.actions$.pipe(ofActionCompleted(fromWorkspaceStore.SaveOrDeleteRPs)).pipe(
        this.unsubscribeOnDestroy
    ).subscribe(() => {
      this.cancelRPPopup();
      this.detectChanges();
    })

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
      this.loadEpMetrics(wsId, uwYear, 1, this.tableConfig.selectedCurveType);
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
        take(2),
        this.unsubscribeOnDestroy
      )
      .subscribe(({basis, adjustmentTypes, status}) => {
        this.basis = basis;
        this.adjustmentTypes = adjustmentTypes;
        this.status = status;
        this.initFilterStatus(status);

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

  initFilterStatus(status) {
    if(_.keys(status).length) {
      _.forEach(status, st => {
        this.selectedStatusFilter[st.code] = true;
      })
    }
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

      case "Curve Type Change":
        this.curveTypeChange(action.payload);
        break;

      case "Toggle Status Filter":
        this.toggleStatusFilter(action.payload);
        break;

      case "Financial Unit Change":
        this.financialUnitChange(action.payload);
        break;

      case "Export EP Metrics":
        this.exportEPMetrics();
        break;

      case "Delta Change":
        this.deltaChange(action.payload);
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
        this.handleRPSave();
        break;

      case "Cancel Changes":
        this.cancelRPPopup();
        break;

      case "Delete RP":
        this.deleteRP(action.payload);
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
      frozenWidth: '1px',
      ...this.calibrationTableService.getColumns(this.tableConfig.view, this.tableConfig.isExpanded)
    };
  }

  expandColumnsOff() {
    this.tableConfig = {
      ...this.tableConfig,
      isExpanded: false
    };
    const a = this.calibrationTableService.getColumns(this.tableConfig.view, this.tableConfig.isExpanded);
    this.columnsConfig = {
      ...this.columnsConfig,
      frozenWidth: '530px',
      ...a
    };
    console.log(a, this.tableConfig, this.columnsConfig);
    this.detectChanges();
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

  handleRPSave() {
    /*this.saveRPs();
    this.deleteRPs();*/
    this.saveOrDeleteRPs();
  }

  saveRPs() {
    this.dispatch(new fromWorkspaceStore.SaveRPs({
      rps: this.returnPeriodConfig.newlyAdded,
      userId: 1,
      wsId: this.wsId,
      uwYear: this.uwYear,
      curveType: this.tableConfig.selectedCurveType
    }))
  }

  deleteRPs() {
    this.dispatch(new fromWorkspaceStore.DeleteRPs({
      rps: this.returnPeriodConfig.deletedRPs,
      userId: 1,
      wsId: this.wsId,
      uwYear: this.uwYear,
      curveType: this.tableConfig.selectedCurveType
    }))
  }

  saveOrDeleteRPs() {
    this.dispatch(new fromWorkspaceStore.SaveOrDeleteRPs({
      deletedRPs: this.returnPeriodConfig.deletedRPs,
      newlyAddedRPs: this.returnPeriodConfig.newlyAdded,
      userId: 1,
      wsId: this.wsId,
      uwYear: this.uwYear,
      curveType: this.tableConfig.selectedCurveType
    }))
  }

  cancelRPPopup() {
    if( this.isRPPopUpVisible ) {
      this.isRPPopUpVisible = false;
      this.returnPeriodConfig= {
        ...this.returnPeriodConfig,
        newlyAdded: [],
        deletedRPs: [],
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
  }

  curveTypeChange(curveType) {
    this.tableConfig = {
      ...this.tableConfig,
      selectedCurveType: curveType
    };

    this.loadEpMetrics(this.wsId, this.uwYear, 1, curveType);

  }

  toggleStatusFilter({value, status}) {
    if(value) {
      this.selectedStatusFilter= {
        ...this.selectedStatusFilter,
        [status]: true
      }
    } else {
      this.selectedStatusFilter = _.omit(this.selectedStatusFilter, [`${status}`]);
    }
  }

  financialUnitChange(financialUnit) {
    this.tableConfig = {
      ...this.tableConfig,
      selectedFinancialUnit: financialUnit
    }
  }

  exportEPMetrics() {
    let exportedList = [];

    _.forEach(this.data, pure => {

      exportedList.push({..._.omit(pure, 'threads'), ...this.epMetrics[this.tableConfig.selectedCurveType][pure.pltId]});

      _.forEach(pure.threads, thread => {
        exportedList.push({..._.omit(thread, 'threads'), ...this.epMetrics[this.tableConfig.selectedCurveType][thread.pltId]});
      })

    });

    this.excel.exportAsExcelFile(
        exportedList,
        'epMetrics'
    )
  }

  deltaChange(newDelta) {
    this.tableConfig = {
        ...this.tableConfig,
      isDeltaByAmount: newDelta
    }
  }

  deleteRP(rp) {
    this.returnPeriodConfig = {
      ...this.returnPeriodConfig,
      deletedRPs: [...this.returnPeriodConfig.deletedRPs, _.toNumber(rp)]
    }
  }

}
