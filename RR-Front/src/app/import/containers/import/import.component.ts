import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Location} from "@angular/common";
import * as _ from "lodash";
import * as XLSX from 'xlsx';
import {AllModules} from "@ag-grid-enterprise/all-modules";
import {BulkImportApi} from "../../service/api/bulk-import.api";
import {BaseContainer} from "../../../shared/base";
import {Store} from "@ngxs/store";
import {Router} from "@angular/router";
import {GridApi, ColumnApi, ColDef} from 'ag-grid-community';
import {ErrorCellRenderer} from "../../../shared/components/grid/error-cell-renderer/error-cell-renderer.component";
import {ErrorValue} from "../../types/erroValue.type";
import {NotificationService} from "../../../shared/services/notification.service";

@Component({
  selector: 'app-import',
  templateUrl: './import.component.html',
  styleUrls: ['./import.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ImportContainer extends BaseContainer implements OnInit {

  public modules = AllModules;

  gridApi: GridApi;
  gridColumnApi: ColumnApi;
  gridParams: {
    rowModelType: 'serverSide' | 'infinite' | 'clientSide',
    rowData: any[],
    columnDefs: any[],
    defaultColDef: any,
    autoGroupColumnDef: any,
    getChildCount: Function,
    frameworkComponents: any,
    rowSelection: 'multiple' | 'single'
  };

  excelFile: File;
  file: {
    bulkImportFileId: number;
    fileName: string;
    filePath: string;
    hasPassedValidation: boolean;
    rowErrorsCount: number;
    userId: number
  };
  isFileRead: boolean;
  isFileValidated: boolean;

  headerErrors: TableErrorType[];

  headerErrorsPanel= {
    active: false,
    disabled: true,
    name: 'Header Errors',
    icon: 'double-right',
    customStyle: {
      background: 'white',
      'border-radius': '4px',
      'margin-bottom': '24px',
      border: '0px'
    }
  };

  isHistoryActive: boolean;

  historyGridApi: GridApi;
  historyGridColumnApi: ColumnApi;
  gridHParams: {
    columnDefs: ColDef[],
    defaultColDef: any;
  };

  constructor(
      _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,
      public location: Location,
      private api: BulkImportApi,
      private notification: NotificationService
  ) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.gridParams = {
      rowModelType: "clientSide",
      rowData: [],
      columnDefs: [],
      autoGroupColumnDef: null,
      defaultColDef: {
        resizable: true,
        sortable: true,
        enableRowGroup: false,
        floatingFilter: false,
        filter: 'agTextColumnFilter',
        cellRenderer: 'errorCellRenderer',
        cellStyle: function(params) {
          if( params.value instanceof ErrorValue) {
            return {color: 'white', backgroundColor: '#dc3545'};
          } else {
            return null;
          }
        }
      },
      frameworkComponents: {
        errorCellRenderer: ErrorCellRenderer
      },
      rowSelection: "multiple",
      getChildCount: () => {}
    };
    this.gridHParams = {
      columnDefs: [{ field: 'bulkImportFileId' }, { field: 'filePath' }, { field: 'fileName' }, { field: 'hasPassedValidation' }, {field: 'rowErrorsCount'}, { field: 'userId'}],
      defaultColDef: {
        minWidth: 100,
        resizable: true,
        sortable: false,
        columnGroupShow: 'open',
        enableRowGroup: false,
        floatingFilter: false,
        filter: false,
      }
    };
    this.isFileRead = false;
    this.isFileValidated = false;
    this.headerErrors = [];
  }

  ngOnInit() {
  }

  navigateBack() {
    this.location.back();
  }

  onGridReady(params) {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
  }

  onBeforeUpload = (file) => {
    this.headerErrors = [];

    this.excelFile = file;
    this.readFile(this.excelFile);
    this.startValidation(this.excelFile);

    return false;
  };

  onFileChange(evt) {
    this.headerErrors = [];

    this.excelFile = (evt.target).files[0];
    this.readFile(this.excelFile);
    this.startValidation(this.excelFile);

  }

  readFile(file) {
    const reader: FileReader = new FileReader();
    reader.onload = (e: any) => {
      /* read workbook */
      const bstr: string = e.target.result;
      const wb: XLSX.WorkBook = XLSX.read(bstr, {type: 'binary'});

      /* grab first sheet */
      const wsname: string = wb.SheetNames[0];
      const ws: XLSX.WorkSheet = wb.Sheets[wsname];
      const columns = [];
      const data = _.map(<any>(XLSX.utils.sheet_to_json(ws, {})), (row, i) => {
        let newRow = {};
        const initCols = _.toNumber(i) == 0;

        _.forEach(row, (v, headerName) => {
          let field = _.camelCase(headerName);
          if(initCols) {
            columns.push({
              field,
              headerName: headerName
            })
          }
          newRow[field] = v;
        });

        return newRow;
      });
      this.gridApi.setColumnDefs(columns);
      this.gridApi.setRowData(data);
      this.gridParams = {
        ...this.gridParams,
        rowData: data,
        columnDefs: columns
      };
      this.isFileRead = true;
      this.detectChanges();
    };
    reader.readAsBinaryString(file);
  }

  startValidation(file) {
    const formData = new FormData();
    formData.append('payload', file);
    this.api.uploadAndValidate(formData).subscribe( ({errors, file}) => {
      this.file = file;
      let data = this.gridParams.rowData;
      _.forEach(errors, (err: TableErrorType) => {
        if(err.columnIndex < 0) this.headerErrors.push(err);
        else {
          const {
            columnIndex,
            rowIndex,
             errorDescription
          } = err;

          const value = data[rowIndex - 1][this.gridParams.columnDefs[columnIndex].field];

          data[rowIndex - 1][this.gridParams.columnDefs[columnIndex].field] = new ErrorValue(
              errorDescription,
              rowIndex,
              value
          )
        }
      });
      this.gridApi.setRowData(data);
      this.isFileValidated= true;
      this.detectChanges();
    });
  }

  runImport() {
    if(!this.headerErrors.length && this.isFileRead && this.isFileValidated) {
      this.api.runImport({
        id: this.file.bulkImportFileId
      }).subscribe(() => {
        this.notification.createNotification(
            'Import',
            'Successfully added a JOB queue',
            'success',
            'bottomRight',
            1000
        )
      })
    } else {
      this.notification.createNotification(
          'File',
          'Please validate file errors then re-upload file',
              'warning',
              'bottomRight',
              1000
          )
    }
  }

  toggleHistory() {
    this.isHistoryActive = !this.isHistoryActive;
  }

  onHistoryGridReady(params) {
    this.historyGridApi = params.api;
    this.historyGridColumnApi = params.columnApi;
    let datasource = {
      getRows: (params) => {
        const {
          startRow,
          endRow
        } = params.request;
        this.api.history({
          records: endRow - startRow,
          page: Math.floor(endRow / ( endRow - startRow ))
        }).subscribe( ( {content,totalElements}: any) => {
          let response = {
            rows: content,
            lastRow: totalElements,
            success: true
          };
          if (response.success) {
            params.successCallback(response.rows, response.lastRow);
            this.historyGridApi.sizeColumnsToFit()
          } else {
            params.failCallback();
          }
        });
      },
    };
    params.api.setServerSideDatasource(datasource);
  }

  downloadTemplate() {
    let link = document.createElement('a');
    link.setAttribute('type', 'hidden');
    link.href = 'assets/SampleBulkImport.xlsx';
    link.download = 'SampleBulkImport.xlsx';
    document.body.appendChild(link);
    link.click();
    link.remove();
  }

  clearFile() {
    this.file = null;
    this.excelFile = null;
    this.isFileRead = false;
    this.isFileValidated = false;
    this.headerErrors = [];
    this.gridApi.setRowData([]);
  }
}
