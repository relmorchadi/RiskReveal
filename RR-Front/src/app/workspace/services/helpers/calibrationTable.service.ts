import { Injectable } from '@angular/core';
import * as _ from 'lodash';

@Injectable({
  providedIn: 'root'
})
export class CalibrationTableService {

  public static frozenCols: any[];
  public epMetrics: any[];
  public adjustments: any[];
  public analysis: any[];

  constructor() {

    CalibrationTableService.frozenCols = [
      {type: "arrow", width: "45", unit: 'px'},
      {field: 'pltId', header: 'PLT Id', width: "90", unit: 'px'},
      {field: 'pltName', header: 'PLT Name', width: "180", unit: 'px'},
      {header: 'Peril',field: 'peril', icon:'', width: "135", unit: 'px', filter: true, sort: true}
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

  getColumns(view) {
    return this[view];
  }

  generateColumns = (arr) => _.map(arr, el => ({header: el,field: el, width: "550", icon:'', filter: false, sort: false, resizable: true}));

  setCols = (cols, view) => {
    this[view] = this.generateColumns(cols);
  };
}
