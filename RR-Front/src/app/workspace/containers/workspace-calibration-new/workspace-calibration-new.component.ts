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
import {Subscription} from "rxjs";

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
    filterData: any
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
    returnPeriodInput: number,
    showSuggestion: boolean,
    message: string
  };
  rpValidation: {
    isValid: boolean,
    upperBound: number,
    lowerBound: number
  };
  /******** add remove pop up **********/
  isAddRemovePopUpVisible: boolean;
  addRemovePopUpConfig: {
    tableColumns: any[]
  }

  //Sub
  validationSub: Subscription;
  private isExpanded: boolean = false;


  constructor(
    _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,
    private calibrationTableService: CalibrationTableService,
    private calibrationApi: CalibrationAPI,
    private actions$: Actions
  ) {
    super(_baseRouter, _baseCdr, _baseStore);

    this.data = [];
    this.adjustmentTypes= [];
    this.basis= [];
    this.tableConfig = {
      view: "adjustments",
      selectedCurveType: "OEP",
      selectedFinancialUnit: "Unit",
      isExpanded: false,
      expandedRowKeys: {},
      isGrouped: true,
      filterData: {}
    };
    this.columnsConfig = {
      ...this.columnsConfig,
      frozenWidth: '275px'
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

    this.isAddRemovePopUpVisible = false;
    this.addRemovePopUpConfig = {
      ...this.addRemovePopUpConfig,
      tableColumns: this.calibrationTableService.getAddRemovePopUpTableColumns()
    }
  }

  ngOnInit() {
    this.actions$
      .pipe(
        ofActionCompleted(fromWorkspaceStore.SaveRPs),
        this.unsubscribeOnDestroy
      ).subscribe( () => {
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
    if (this.data.length && !this.isExpanded){
      this.expandCollapseAllPlts();
    }
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
      case "Open Add Remove Pop Up":
        this.openAddRemovePopUp();
        break;
      case "Expand Collapse Plts":
        this.expandCollapseAllPlts();
        break;
      case "Expand Collapse Plt Panel":
        this.adaptPltPanelWidth(action.payload);
        break;
      case "Filter Plt Table":
        this.updateFilterData(action.payload);
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

      case "Cancel Changes":
        this.cancelRPPopup();
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
      frozenWidth: '275px',
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

  saveRPs() {
    this.dispatch(new fromWorkspaceStore.SaveRPs({
      rps: this.returnPeriodConfig.newlyAdded,
      userId: 1,
      wsId: this.wsId,
      uwYear: this.uwYear,
      curveType: this.tableConfig.selectedCurveType
    }))
  }

  cancelRPPopup() {
    this.isRPPopUpVisible = false;
    this.returnPeriodConfig= {
      ...this.returnPeriodConfig,
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


  private financialUnitChange(financialUnit) {
    this.tableConfig = {
      ...this.tableConfig,
      selectedFinancialUnit: financialUnit
    }
  }

  private openAddRemovePopUp() {
    this.isAddRemovePopUpVisible = true;
  }

  handleAddRemovePopUp(event: any) {
    let type = event.type;
    switch (type) {
      case 'Hide Add Remove Pop Up':
        this.isAddRemovePopUpVisible = false;
        break;

    }

  }

  expandCollapseAllPlts() {
    if (!this.isExpanded && this.tableConfig.expandedRowKeys != {}){
      _.forEach(this.data, (pure)=>{
        this.tableConfig.expandedRowKeys[pure.pltId] = true;
      })
    } else {
      this.tableConfig.expandedRowKeys = {};
    }
    console.log('expand all',this.tableConfig.expandedRowKeys)
    this.isExpanded = !this.isExpanded;
  }

  adaptPltPanelWidth(payload){
  let arr = this.columnsConfig.frozenWidth.split('p');
  let newWidth = Number(arr[0])  + payload.event.edges.right;
  this.columnsConfig = {
    ...this.columnsConfig,
    frozenWidth: newWidth+'px',
    frozenColumns: this.calibrationTableService.getFrozenColumns(newWidth)
  }
  }

  private updateFilterData(payload: any) {
    this.tableConfig.filterData = payload;
  }
}
