import { Injectable } from '@angular/core';
import * as _ from 'lodash';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CalibrationTableService {

  public static frozenColsHead: any[];
  public static frozenColsTail: any[];

  public static frozenCols: any[];
  public static frozenColsExpanded: any[];
  public epMetrics: any[];
  public adjustments: any[];
  public analysis: any[];

  public isFac: boolean;

  //Obs
  public columnsConfig$ = new BehaviorSubject({
    frozenColumns: [],
    frozenWidth: '',
    columns: [],
    columnsLength: 0
  });

  private columnsConfigCache = {
    frozenColumns: [],
    frozenWidth: '',
    columns: [],
    columnsLength: 0
  };

  public updateColumnsConfig = (v) => {
    this.columnsConfig$.next(v);
  };

  public updateColumnsConfigCache = v => this.columnsConfigCache = {
      ...v,
    frozenColumns: v.frozenColumns ? v.frozenColumns : this.columnsConfigCache.frozenColumns,
    frozenWidth: v.frozenWidth != '0px' ? v.frozenWidth : this.columnsConfigCache.frozenWidth,
  };

  constructor() {

    this.isFac= false;

    CalibrationTableService.frozenCols = [
      {field: 'arrow', type: "arrow", width: "45", unit: 'px', resizable: false, isFrozen: true},
      {field: 'pltId', header: 'PLT Id', width: "50", unit: 'px', resizable: true, isFrozen: true},
      {field: 'pltName', header: 'PLT Name', width: "120", unit: 'px', resizable: true, isFrozen: true}
    ];

    CalibrationTableService.frozenColsHead = [
      {field: 'arrow', type: "arrow", width: "45", unit: 'px', resizable: false, isFrozen: true},
      {field: 'pltId', header: 'PLT Id', width: "50", unit: 'px', resizable: true, isFrozen: true, filter: true, sortable: true},
      {field: 'pltName', header: 'PLT Name', width: "120", unit: 'px', resizable: true, isFrozen: true, filter: true, sortable: true}
    ];

    CalibrationTableService.frozenColsTail = [
      {header: 'Status', field: 'status', type: 'status', width: "40", unit: 'px', icon:'', filter: false, sortable: false, resizable: false, isFrozen: true}
    ];

    CalibrationTableService.frozenColsExpanded = [
      {header: 'Peril', field: 'peril', icon:'', width: "60", minWidth: "40", unit: 'px', filter: true, sortable: true, resizable: true, isFrozen: true},
      {field: 'regionPerilCode', header: 'Region Peril', width: "120", minWidth: "100", unit: 'px', filter: true, sortable: true, resizable: true, isFrozen: true},
      {header: 'Region Peril Name',field: 'regionPerilDesc', width: "120", minWidth: "100", unit: 'px', icon:'', filter: true, sortable: true, resizable: true, isFrozen: true},
      {field: 'grain', header: 'Grain', width: "60", minWidth: "40", unit: 'px', filter: true, sortable: true, resizable: true, isFrozen: true},
      {header: 'Vendor System',field: 'vendorSystem', width: "100", minWidth: "50", unit: 'px', icon:'', filter: true, sortable: true, resizable: true, isFrozen: true},
      {field: 'rap', header: 'RAP', width: "80", minWidth: "50", unit: 'px', filter: true, sortable: true, resizable: true, isFrozen: true}
    ];

    this.adjustments = [
      {header: 'Overall LMF',field: 'overallLmf', width: "40", unit: 'px', icon:'', filter: false, sortable: false},
      {header: 'Base',field: 'base', width: "40", unit: 'px', icon:'', filter: false, sortable: false},
      {header: 'Default',field: 'Default', width: "40", unit: 'px', icon:'', filter: false, sortable: false},
      {header: 'Client',field: 'client', width: "40", unit: 'px', icon:'', filter: false, sortable: false},
      {header: 'Inuring',field: 'inuring', width: "40", unit: 'px', icon:'', filter: false, sortable: false},
      {header: 'Post-Inuring',field: 'postInuring', width: "40", unit: 'px', icon:'', filter: false, sortable: false}
    ];

    this.analysis = [];

    this.epMetrics = [];

  }

  columnHandler = {
    "fac-epMetrics": (isExpanded) => {
      const frozenColumns = ( isExpanded ? null : this.columnsConfigCache.frozenColumns);
      const frozenWidth = ( isExpanded ? '0px' : this.columnsConfigCache.frozenWidth);
      let head, tail;
      if(isExpanded) {
         head = _.slice(this.columnsConfigCache.frozenColumns, 0, this.columnsConfigCache.frozenColumns.length - 1);
         tail = this.columnsConfigCache.frozenColumns[this.columnsConfigCache.frozenColumns.length - 1];
      }
      const columns = ( isExpanded ? [..._.uniqBy([...head, ...CalibrationTableService.frozenColsExpanded, tail], 'field'), ...this.epMetrics] : this.epMetrics );
      const columnsLength = columns ? columns.length : null;

      return ({
        frozenWidth,
        frozenColumns,
        columns,
        columnsLength
      })
    },
    "fac-adjustments": (isExpanded) => {
      const c = {header: 'Default',field: 'Default', width: "40", unit: 'px', icon:'', filter: false, sort: false}
      const frozenColumns = ( isExpanded ? null : this.columnsConfigCache.frozenColumns);
      const frozenWidth = ( isExpanded ? '0px' : this.columnsConfigCache.frozenWidth);
      let head, tail;
      if(isExpanded) {
        head = _.slice(this.columnsConfigCache.frozenColumns, 0, this.columnsConfigCache.frozenColumns.length - 1);
        tail = this.columnsConfigCache.frozenColumns[this.columnsConfigCache.frozenColumns.length - 1];
      }
      const columns = ( isExpanded ? [..._.uniqBy([...head, ...CalibrationTableService.frozenColsExpanded, tail], 'field'), c] : [c] );
      const columnsLength = columns ? columns.length : null;

      return ({
        frozenWidth,
        frozenColumns,
        columns,
        columnsLength
      })
    }

  };

  init = () =>  {
    const a = [...CalibrationTableService.frozenColsHead, ...CalibrationTableService.frozenColsTail];
    this.updateColumnsConfig({
      frozenColumns: a,
      frozenWidth: _.reduce(a, (acc, curr) => acc + _.toNumber(curr.width), 0) + 'px',
      columns: [{header: 'Default',field: 'Default', width: "40", unit: 'px', icon:'', filter: false, sort: false}],
      columnsLength: a.length
    });
    this.updateColumnsConfigCache(this.columnsConfig$.getValue())
  };

  getColumns(view, isExpanded) {
    try {
      const tmp = this.columnHandler[`${this.isFac ? "fac" : "treaty"}-${view}`](isExpanded);
      this.updateColumnsConfig(tmp);
    } catch(e) {
      this.updateColumnsConfig({
        frozenWidth: _.reduce(CalibrationTableService.frozenCols, (acc, curr) => acc + _.toNumber(curr), 0) + 'px',
        frozenColumns: CalibrationTableService.frozenCols,
        columns: this.analysis,
        columnsLength: CalibrationTableService.frozenCols.length
      })
    }
  }

  generateColumns = (arr) => _.map(arr, el => ({header: el,field: el, width: "100", icon:'', filter: false, sort: false, align: 'right', resizable:true}));

  setCols = (cols, view) => {
    this[view] = this.generateColumns(cols);
  };

  setWorkspaceType = (wsType) => this.isFac = wsType == "fac";

  getAddRemovePopUpTableColumns() {
        return CalibrationTableService.frozenColsExpanded;
    }

  getFrozenColumns = _.memoize((inputWidth) => {
      let width = 0;
      let result: any[] = CalibrationTableService.frozenColsExpanded;
      _.forEach(CalibrationTableService.frozenColsExpanded, (col, i)=>{
        width = width + Number(col.width);
        if (width > inputWidth){
          result = CalibrationTableService.frozenColsExpanded.slice(0, i-1)
          return false;
        }
      });
      result[result.length - 1].resizable = false;
      return  result;
    })

  onManageFrozenColumns = (newFrozenColumns) => {
    const frozenColumns = [ {field: 'arrow', type: "arrow", width: "45", unit: 'px', resizable: false, isFrozen: true}, ...newFrozenColumns];
    const frozenWidth = _.reduce(frozenColumns, (acc, curr) => acc + _.toNumber(curr.width), 0) + 'px';

    this.updateColumnsConfig({
        ...this.columnsConfig$.getValue(),
      frozenColumns,
      frozenWidth
    })
  }
}
