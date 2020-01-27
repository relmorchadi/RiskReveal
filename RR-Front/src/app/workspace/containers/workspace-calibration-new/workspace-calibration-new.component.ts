import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {BaseContainer} from "../../../shared/base";
import {StateSubscriber} from "../../model/state-subscriber";
import {Actions, ofActionCompleted, ofActionSuccessful, Store} from "@ngxs/store";
import {Router} from "@angular/router";
import * as fromWorkspaceStore from '../../store';
import * as _ from "lodash";
import {CalibrationTableService} from "../../services/helpers/calibrationTable.service";
import {WorkspaceState} from "../../store";
import {first, last, map, mergeMap, switchMap, take, takeWhile, tap} from "rxjs/operators";
import {Message} from "../../../shared/message";
import {CalibrationAPI} from "../../services/api/calibration.api";
import {combineLatest, Subscription} from "rxjs";
import {ExcelService} from "../../../shared/services/excel.service";
import produce from "immer";

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
  workspaceCurrency: string;
  workspaceEffectiveDate: Date;

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
    selectedCurrency: string,
    isExpanded: boolean,
    expandedRowKeys: any,
    isGrouped: boolean,
    isDeltaByAmount: boolean
    filterData: any,
    isExpandAll: boolean
  };
  constants: {
    financialUnits: string[],
    curveTypes: string[],
    currencies: string[]
  };
  columnsConfig: {
    frozenColumns: any[],
    frozenWidth: string,
    columns: any[],
    columnsLength: number
  };
  exchangeRates: any;

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
  /******** add remove pop up **********/
  isAddRemovePopUpVisible: boolean;
  addRemovePopUpConfig: {
    tableColumns: any[]
  };

  //Sub
  validationSub: Subscription;
  exchangeRatesSubscription: Subscription;
  private isExpanded: boolean = false;


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
      selectedFinancialUnit: "Unit",
      selectedCurrency: null,
      filterData: {},
      isExpandAll: false
    };
    this.exchangeRates= {};

    this.columnsConfig = {
      ...this.columnsConfig,
      frozenWidth: '275px'
    };

    this.constants = {
      curveTypes: ['OEP', 'OEP-TVAR', 'AEP', 'AEP-TVAR'],
      financialUnits: [ 'Unit', 'Thousands', 'Million', 'Billion'],
      currencies: []
    };

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

    this.isAddRemovePopUpVisible = false;
    this.addRemovePopUpConfig = {
      ...this.addRemovePopUpConfig,
      tableColumns: this.calibrationTableService.getAddRemovePopUpTableColumns()
    }

    setInterval(() => console.log(this.tableConfig), 3333);
  }

  ngOnInit() {
    this.workspaceCurrency= null;

    this.actions$.pipe(
        ofActionCompleted(fromWorkspaceStore.SaveOrDeleteRPs),
        this.unsubscribeOnDestroy
    ).subscribe(() => {
      this.cancelRPPopup();
      this.detectChanges();
    });

    this.actions$
        .pipe(
            ofActionSuccessful(fromWorkspaceStore.LoadGroupedPltsByPure),
            map( r => {
              let res= _.map(this.data, el => el.currencyCode);

              if(!_.find(['USD','EUR', 'CAD', 'GBP', 'SGD'], c => c === this.tableConfig.selectedCurrency)) {
                res= [...res, this.tableConfig.selectedCurrency];
              }

              return _.uniq(res);
            }),
            switchMap( currencies => this.calibrationApi.getExchangeRates(this.workspaceEffectiveDate, currencies))
        ).subscribe(exchangeRates => {
          this.exchangeRates = _.keyBy(exchangeRates, 'currencyCode');
          this.detectChanges();
    });

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
      this.calibrationTableService.init();

      console.log(this.columnsConfig);

      //SUB
      this.subscribeToColumns();
      this.subscribeToAdjustments(wsId + "-" + uwYear);
      this.subscribeToEpMetrics(wsId + "-" + uwYear);
      this.subscribeToConstants(wsId + "-" + uwYear);
      this.subscribeToWorkspaceCurrencyAndEffectiveDate(wsId + "-" + uwYear);

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
    this.dispatch(new fromWorkspaceStore.LoadCalibrationConstants());
    this.calibrationApi.loadCurrencies().subscribe(
        (currencies: any) => {
          this.constants = {
              ...this.constants,
            currencies
          }
        }
    )
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

  subscribeToWorkspaceCurrencyAndEffectiveDate(wsIdentifier: string) {
    this.select(WorkspaceState.getWorkspaceCurrency(wsIdentifier))
        .pipe(
            takeWhile(v => !_.isNil(v)),
            take(2),
            this.unsubscribeOnDestroy
        ).subscribe( currency => {
          this.workspaceCurrency = currency;
          this.tableConfig = {
              ...this.tableConfig,
            selectedCurrency: currency
          };
          this.detectChanges();
    });

    this.select(WorkspaceState.getWorkspaceEffectiveDate(wsIdentifier))
        .pipe(
            takeWhile(v => !_.isNil(v)),
            take(2),
            this.unsubscribeOnDestroy
        ).subscribe( date => {
      this.workspaceEffectiveDate = date;
      this.detectChanges();
    });
  }

  subscribeToColumns() {
    this.calibrationTableService.columnsConfig$.subscribe(config => {
      this.columnsConfig= config;
      console.log(config);
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
    if (this.data.length){
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
    this.calibrationTableService.init();
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
    this.calibrationTableService.getColumns(newView, this.tableConfig.isExpanded);
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

      case "Filter Plt Table":
        this.updateFilterData(action.payload);
        break;

      case "Export EP Metrics":
        this.exportEPMetrics();
        break;

      case "Delta Change":
        this.deltaChange(action.payload);
        break;

      case "Currency Change":
        this.currencyChange(action.payload);
        break;

      case "Resize Table Separator":
        this.tableSeparatorResize(action.payload);
        break;

      case "Resize frozen Column":
        this.resizeFrozenColumn(action.payload);
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

  tableSeparatorResize(delta){

    this.solotry(delta);

  }

  solotry(delta) {
    let res : {
      frozenWidth: string;
      frozenColumns: any[];
    } = { frozenWidth: '', frozenColumns: []};
    const head = _.slice(this.columnsConfig.frozenColumns,0, 3);
    const tail= this.columnsConfig.frozenColumns[this.columnsConfig.frozenColumns.length - 1];
    const headTailCols = [...head, tail];
    const minWidth = _.reduce(headTailCols, (acc, curr) => acc + _.toNumber(curr.width), 0);
    const frozenWidth = _.toNumber(_.trim(this.columnsConfig.frozenWidth, 'px'));
    const expandedMaxWidth = frozenWidth + delta - minWidth;

    const possibleExpandedCols = CalibrationTableService.frozenColsExpanded;

    let expandedWidth = 0;
    let i =0;

    for(; i < possibleExpandedCols.length; i++) {
      const currentColWidth = _.toNumber(possibleExpandedCols[i].width);
      if( expandedWidth + currentColWidth <= expandedMaxWidth) {
        expandedWidth = expandedWidth + currentColWidth;
      } else break;
    }

    let middle;

    if(expandedWidth == 0) {
      middle = [];
    } else {
      if( i == possibleExpandedCols.length) {
        middle = possibleExpandedCols;
      } else {
        middle = _.slice(possibleExpandedCols, 0, i + 1);
      }

      const lastCol = middle[middle.length - 1];
      const diff = Math.abs(expandedMaxWidth - expandedWidth);
      const newColMinWidth = _.toNumber(lastCol.minWidth);
      let newColWidth = _.toNumber(lastCol.width) + (expandedWidth < expandedMaxWidth ?  -diff: diff);

      middle[middle.length - 1] = {...lastCol, width : (newColWidth < newColMinWidth ? newColMinWidth : newColWidth) + ''};
    }
    const resCols = [...head, ...middle ,tail];
    res.frozenWidth= _.reduce(resCols, (acc, curr) => acc + _.toNumber(curr.width), 0) + 'px';
    res.frozenColumns= resCols;

    this.calibrationTableService.updateColumnsConfig({
      ...this.columnsConfig,
      ...res
    });

  }

  expandColumns() {
    this.tableConfig = {
      ...this.tableConfig,
      isExpanded: true
    };
    this.calibrationTableService.updateColumnsConfigCache(this.columnsConfig);
    this.calibrationTableService.getColumns(this.tableConfig.view, true);
  }

  expandColumnsOff() {
    this.tableConfig = {
      ...this.tableConfig,
      isExpanded: false
    };
    this.calibrationTableService.getColumns(this.tableConfig.view, false);
  }

  viewAdjustmentDetail(newAdjustment) {
    this.selectedAdjustment = {...newAdjustment};
    this.isAdjustmentPopUpVisible = true;
  }

  resizeFrozenColumn({delta, index}) {

    this.calibrationTableService.updateColumnsConfig({
      ...this.columnsConfig,
      frozenColumns: produce(this.columnsConfig.frozenColumns, draft => {
        draft[index].width = _.toNumber(draft[index].width) + delta + ''
      }),
      frozenWidth: ( _.toNumber(_.trimEnd(this.columnsConfig.frozenWidth, "px")) + delta ) + "px"
    })

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
    if (!_.keys(this.tableConfig.expandedRowKeys).length){
      _.forEach(this.data, (pure)=> {
        this.tableConfig.expandedRowKeys[pure.pltId] = true;
      });
      this.tableConfig= {
          ...this.tableConfig,
        isExpandAll: true
      };
    } else {
      this.tableConfig= {
        ...this.tableConfig,
        expandedRowKeys: {},
        isExpandAll: false
      };
    }

  }

  adaptPltPanelWidth(delta){
  let arr = this.columnsConfig.frozenWidth.split('p');
  let newWidth = Number(arr[0])  + delta;
  this.columnsConfig = {
    ...this.columnsConfig,
    frozenWidth: newWidth+'px',
    frozenColumns: this.calibrationTableService.getFrozenColumns(newWidth)
  }
  }

  private updateFilterData(payload: any) {
    this.tableConfig.filterData = payload;
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

  currencyChange(currency) {

    let res= _.map(this.data, el => el.currencyCode);

    if(!_.find(['USD','EUR', 'CAD', 'GBP', 'SGD'], c => c === currency)) {
      res= [...res, currency];
    }

    res = _.uniq(res);


    this.exchangeRatesSubscription = this.calibrationApi.getExchangeRates(this.workspaceEffectiveDate, res)
        .subscribe(exchangeRates => {
          this.exchangeRates = {...this.exchangeRates, ..._.keyBy(exchangeRates, 'currencyCode')};
          this.tableConfig = {
            ...this.tableConfig,
            selectedCurrency: currency
          };
          this.detectChanges();
          this.exchangeRatesSubscription && this.exchangeRatesSubscription.unsubscribe();
        });


  }

}
