import {ColDef} from '@ag-grid-community/core';

export interface GridParams {
  rowModelType: 'clientSide' | 'infinite' | 'viewport' | 'serverSide';
  columnDefs: ColDef[];
  rowData: any[];
  defaultColDef: any;
  autoGroupColumnDef: any;
  groupDefaultExpanded: any;
}