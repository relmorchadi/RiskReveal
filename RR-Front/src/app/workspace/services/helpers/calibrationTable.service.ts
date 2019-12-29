import { Injectable } from '@angular/core';
import * as _ from 'lodash';

@Injectable({
  providedIn: 'root'
})
export class CalibrationTableService {

  public static frozenCols: any[];
  public static frozenColsExpanded: any[];
  public epMetrics: any[];
  public adjustments: any[];
  public analysis: any[];

  public isFac: boolean;
  constructor() {

    this.isFac= false;

    CalibrationTableService.frozenCols = [
      {type: "arrow", width: "45", unit: 'px', resizable: false, isFrozen: true},
      {field: 'pltId', header: 'PLT Id', width: "90", unit: 'px', resizable: true, isFrozen: true},
      {field: 'pltName', header: 'PLT Name', width: "180", unit: 'px', resizable: true, isFrozen: true},
      {header: 'Peril',field: 'peril', icon:'', width: "135", unit: 'px', filter: true, sort: true, resizable: true, isFrozen: true},
    ];

    CalibrationTableService.frozenColsExpanded = [
      {type: "arrow", width: "45", unit: 'px', resizable: false, isFrozen: true},
      {field: 'pltId', header: 'PLT Id', width: "90", unit: 'px', resizable: true, isFrozen: true},
      {field: 'pltName', header: 'PLT Name', width: "180", unit: 'px', resizable: true, isFrozen: true},
      {header: 'Peril',field: 'peril', icon:'', width: "135", unit: 'px', filter: true, sort: true, resizable: true, isFrozen: true},
      {field: 'regionPerilCode', header: 'Region Peril', width: "160", unit: 'px', filter: true, sort: true, resizable: true, isFrozen: true},
      {header: 'Region Peril Name',field: 'regionPerilDesc', width: "160", unit: 'px', icon:'', filter: true, sort: true, resizable: true, isFrozen: true},
      {field: 'grain', header: 'Grain', width: "80", unit: 'px', filter: true, sort: true, resizable: true, isFrozen: true},
      {header: 'Vendor System',field: 'vendorSystem', width: "160", unit: 'px', icon:'', filter: true, sort: true, resizable: true, isFrozen: true},
      {field: 'rap', header: 'RAP', width: "80", unit: 'px', filter: true, sort: true, resizable: true, isFrozen: true},
      {header: '',field: 'status',type: 'status', width: "80", unit: 'px', icon:'', filter: false, sort: false, resizable: false, isFrozen: true}
    ];

    this.adjustments = [
      {header: 'Overall LMF',field: 'overallLmf', width: "40", unit: 'px', icon:'', filter: false, sort: false},
      {header: 'Base',field: 'base', width: "40", unit: 'px', icon:'', filter: false, sort: false},
      {header: 'Default',field: 'default', width: "40", unit: 'px', icon:'', filter: false, sort: false},
      {header: 'Client',field: 'client', width: "40", unit: 'px', icon:'', filter: false, sort: false},
      {header: 'Inuring',field: 'inuring', width: "40", unit: 'px', icon:'', filter: false, sort: false},
      {header: 'Post-Inuring',field: 'postInuring', width: "40", unit: 'px', icon:'', filter: false, sort: false}
    ];

    this.analysis = [];

    this.epMetrics = [];

  }

  columnHandler = {
    "fac-epMetrics": (isExpanded) => {
      const frozenColumns = ( isExpanded ? null : CalibrationTableService.frozenCols);
      const columns = ( isExpanded ? [...CalibrationTableService.frozenColsExpanded, ...this.epMetrics] : this.epMetrics );
      const columnsLength = frozenColumns ? frozenColumns.length : null;

      return ({
        frozenColumns: frozenColumns,
        columns: columns,
        columnsLength: columnsLength
      })
    },
    "fac-adjustments": (isExpanded) => {
      const frozenColumns = ( isExpanded ? null : CalibrationTableService.frozenCols);
      const columns = ( isExpanded ? [...CalibrationTableService.frozenColsExpanded, ..._.slice(this.adjustments, 0, 2)] : _.slice(this.adjustments, 0, 2) );
      const columnsLength = frozenColumns ? frozenColumns.length : null;

      return ({
        frozenColumns: frozenColumns,
        columns: columns,
        columnsLength: columnsLength
      })
    },
    "treaty-epMetrics": (isExpanded) => {
      const frozenColumns = ( isExpanded ? null : CalibrationTableService.frozenCols);
      const columns = ( isExpanded ? [...CalibrationTableService.frozenColsExpanded, ...this.epMetrics] : this.epMetrics );
      const columnsLength = frozenColumns ? frozenColumns.length : null;

      return ({
        frozenColumns: frozenColumns,
        columns: columns,
        columnsLength: columnsLength
      })
    },
    "treaty-adjustments": (isExpanded) => {
      const frozenColumns = ( isExpanded ? null : CalibrationTableService.frozenCols );
      const columns = ( isExpanded ? [...CalibrationTableService.frozenColsExpanded, ...this.adjustments] : this.adjustments );
      const columnsLength = frozenColumns ? frozenColumns.length : null;

      return ({
        frozenColumns: frozenColumns,
        columns: columns,
        columnsLength: columnsLength
      })
    }

  };

  getColumns(view, isExpanded) {
    console.log(view);
    const columns = this.columnHandler[`${this.isFac ? "fac" : "treaty"}-${view}`](isExpanded);
    console.log(columns);
    return columns;
  }

  generateColumns = (arr) => _.map(arr, el => ({header: el,field: el, width: "550", icon:'', filter: false, sort: false}));

  setCols = (cols, view) => {
    this[view] = this.generateColumns(cols);
  };

  setWorkspaceType = (wsType) => this.isFac = wsType == "fac";
}
