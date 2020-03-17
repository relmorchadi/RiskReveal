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
import {ColumnsFormatterService} from "../../../shared/services/columnsFormatter.service";
import {FilterGroupedPltsPipe} from "../../pipes/filter-grouped-plts.pipe";
import {SortGroupedPltsPipe} from "../../pipes/sort-grouped-plts.pipe";
import {ExchangeRatePipe} from "../../../shared/pipes/exchange-rate.pipe";

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
    sortData: any,
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

  visibleFrozenColumns: any[];
  availableFrozenColumns: any[];
  exchangeRates: any;

  //POP-UPs
  selectedAdjustment: any;
  selectedStatusFilter: any;
  isAdjustmentPopUpVisible: boolean;
  isFrozenManageColumnsVisible: boolean;

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
    private excel: ExcelService,
    private formatter: ColumnsFormatterService,
    private filterGroupedPlts: FilterGroupedPltsPipe,
    private sortGroupedPlts: SortGroupedPltsPipe,
    private exchangeRatePipe: ExchangeRatePipe
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
      sortData: {},
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
    this.isFrozenManageColumnsVisible= false;
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
    };
  }

  ngOnInit() {
    this.workspaceCurrency= null;

    this.select(WorkspaceState.getSelectedProject).subscribe( project => {
      this.tableConfig = {
          ...this.tableConfig,
        filterData: project ? { ...this.tableConfig.filterData, projectId: project.projectId } : this.tableConfig.filterData
      };
      this.detectChanges();
    });

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
                res= _.filter([...res, this.tableConfig.selectedCurrency], curr => !_.isNil(curr));
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
      console.log(epMetrics);

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
      this.calibrationTableService.updateColumnsConfigCache(config);

      this.visibleFrozenColumns= _.slice(config.frozenColumns, 1);
      this.availableFrozenColumns= _.differenceBy(CalibrationTableService.frozenColsExpanded, config.frozenColumns, 'field');
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
    if(this.data.length > 0) this.expandAllPlts();
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
    if(this.tableConfig.view == 'epMetrics') this.calibrationTableService.getColumns('epMetrics', this.tableConfig.isExpanded);
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
    console.log(action);
    switch (action.type) {

      case "Open Column Manager":
        this.isFrozenManageColumnsVisible= true;
        break;

      case "Close Column Manager":
        this.isFrozenManageColumnsVisible= false;
        this.detectChanges();
        break;

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

      case "Collapse All Plts":
        this.collapseAllPlts();
        break;

      case "Expand All Plts":
        this.expandAllPlts();
        break;

      case "Filter Table":
        this.filterTable(action.payload);
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

      case "Sort Table":
        this.sortTable(action.payload);
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

    const frozenColumns = [...this.columnsConfig.frozenColumns];
    const frozenWidthPixel = this.columnsConfig.frozenWidth;
    this.calibrationTableService.updateColumnsConfig({
      ...this.columnsConfig,
      frozenColumns: null,
      frozenWidth: null
    });

    let res : {
      frozenWidth: string;
      frozenColumns: any[];
    } = { frozenWidth: '', frozenColumns: []};

    const head = _.slice(frozenColumns, 0, 3);
    const tail= _.slice(frozenColumns, frozenColumns.length - 1);
    const headTailCols = [...head, ...tail];
    const minWidth = _.reduce(headTailCols, (acc, curr) => acc + _.toNumber(curr.width), 0);
    const frozenWidth = _.toNumber(_.trim(frozenWidthPixel, 'px'));
    const expandedMaxWidth = frozenWidth + delta - minWidth;
    const possibleExpandedCols = _.uniqBy([..._.slice(frozenColumns,3, frozenColumns.length - 1), ...CalibrationTableService.frozenColsExpanded], 'field');

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
    }

    let resCols = [...head, ...middle , ...tail];
    // const lastCol = resCols[resCols.length - 2];
    // const diff = Math.abs(expandedMaxWidth - expandedWidth);
    // const newColMinWidth = _.toNumber(lastCol.minWidth);
    // let newColWidth = _.toNumber(lastCol.width) + (expandedWidth < expandedMaxWidth ?  -diff: diff);
    // //
    // resCols = _.map(resCols, (col, i)  => (_.toNumber(i) != (resCols.length - 2)) ?  col : {...lastCol, width : (newColWidth < newColMinWidth ? newColMinWidth : newColWidth) + ''});
    res.frozenWidth= _.reduce(resCols, (acc, curr) => acc + _.toNumber(curr.width), 0) + 'px';
    res.frozenColumns= resCols;

    const tmp = {
      ...this.columnsConfig,
      ...res
    };

    this.calibrationTableService.updateColumnsConfig(tmp);

  }

  resizeFrozenColumn({delta, index}) {
    let columnsConfig= this.columnsConfig;
    const frozenColumns= _.merge(columnsConfig.frozenColumns, {[index]: {
      width: _.toNumber(columnsConfig.frozenColumns[index].width) + delta + ''
    }});
    const frozenWidth= _.reduce(frozenColumns, (acc, curr) => acc + _.toNumber(curr.width), 0) + "px";

    columnsConfig = {
      ...columnsConfig,
      frozenColumns,
      frozenWidth
    };

    this.calibrationTableService.updateColumnsConfig(columnsConfig);
  }

  expandColumns() {
    this.tableConfig = {
      ...this.tableConfig,
      isExpanded: true
    };
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
    this.saveOrDeleteRPs();
  }

  saveOrDeleteRPs() {
    this.dispatch(new fromWorkspaceStore.SaveOrDeleteRPs({
      deletedRPs: this.returnPeriodConfig.deletedRPs,
      newlyAddedRPs: this.returnPeriodConfig.newlyAdded,
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

  collapseAllPlts() {
    this.tableConfig= {
      ...this.tableConfig,
      expandedRowKeys: {},
      isExpandAll: false
    };
  }

  expandAllPlts() {
    _.forEach(this.data, (pure)=> {
      this.tableConfig.expandedRowKeys[pure.pltId] = true;
    });
    this.tableConfig= {
      ...this.tableConfig,
      isExpandAll: true
    };
  }

  private filterTable(filter: any) {
    this.tableConfig = {
        ...this.tableConfig,
      filterData: filter
    }
  }

  sortTable(sorts: any) {
    this.tableConfig = {
        ...this.tableConfig,
      sortData: sorts
    }
  }

  exportEPMetrics() {
    let exportedList = [];
    let columns = [
        ..._.map(_.filter(this.columnsConfig.frozenColumns, col => col.header), c => ({field: c.field, header: c.header, type: 'text'})),
        ..._.map(this.columnsConfig.columns, c => ({field: c.field, header: c.header, type: c.type}))
    ];
    let columnsHeader = _.map(columns, 'header');
    let columnsType = _.map(columns, 'type');
    const columnsField =_.map(columns, 'field');
    let item = null;

    _.forEach(
        this.sortGroupedPlts.transform(
            this.filterGroupedPlts.transform(
                this.data,
                this.tableConfig.filterData
            ),
            this.tableConfig.sortData
        ), pure => {
          let pureMetrics = {};
          _.forEach(this.epMetrics[this.tableConfig.selectedCurveType][pure.pltId], (v,k) => {
            pureMetrics[k] = this.exchangeRatePipe.transform(v, this.exchangeRates, pure.currencyCode, this.tableConfig.selectedCurrency);
          })
          item = {..._.omit(pure, 'threads'), ...pureMetrics};
          exportedList.push(this.transformItem(columnsHeader, columnsField,columnsType, item))

          _.forEach(pure.threads, thread => {
            let metrics = {};
            _.forEach(this.epMetrics[this.tableConfig.selectedCurveType][thread.pltId], (v,k) => {
              metrics[k] = this.exchangeRatePipe.transform(v, this.exchangeRates, thread.currencyCode, this.tableConfig.selectedCurrency);
            })
            item = {..._.omit(thread, 'threads'), ...metrics};
            exportedList.push(this.transformItem(columnsHeader, columnsField,columnsType, item));
          })

    });

    if(item) {

      this.excel.exportAsExcelFile(
          [
              {
                sheetData: exportedList,
                sheetName: "Main",
                headerOptions: _.values(_.filter(_.map([...this.columnsConfig.frozenColumns, ...this.columnsConfig.columns], e => e.header), e => e))
              },
              {
                sheetData: _.map(this.tableConfig.filterData, (v,k) => ({
                  Filter: v,
                  Column: k == 'projectId' ? 'Project ID' : columnsHeader[_.findIndex(columnsField, e => e == k)]
                })),
                sheetName: "Filters",
                headerOptions: ["Column", "Filter"]
              }
              ],
          'EP Metrics-Calibration'
      )

    }
  }

  transformItem(columnsHeader, columnsField, columnsType, item) {
    let newItem= {};
    _.forEach(columnsField, (field, i) => {
      newItem[columnsHeader[i]] =  this.formatter.format(item[field], columnsType[i])
    });
    return newItem;
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
      res= _.filter([...res, currency], curr => !_.isNil(curr));
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

  handleManageColumnsActions(action) {
    switch (action.type) {

      case "Manage Frozen Columns":
        this.calibrationTableService.onManageFrozenColumns(action.payload);
        this.isFrozenManageColumnsVisible = false;
        break;

      case "Close Column Manager":
        this.isFrozenManageColumnsVisible= false;
        break;

      default:
        console.log(action);
    }
  }

}
