import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Location} from "@angular/common";
import * as _ from "lodash";
import * as XLSX from 'xlsx';
import {AllModules} from "@ag-grid-enterprise/all-modules";
import {UploadXHRArgs} from "ng-zorro-antd";
import {BulkImportApi} from "../../service/api/bulk-import.api";
import {BaseContainer} from "../../../shared/base";
import {Store} from "@ngxs/store";
import {Router} from "@angular/router";
import {GridApi, ColumnApi} from 'ag-grid-community';
import {ErrorCellRenderer} from "../../../shared/components/grid/error-cell-renderer/error-cell-renderer.component";
import {ErrorValue} from "../../types/erroValue.type";

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

  file: File;
  fileUploaded: boolean;

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

  constructor(
      _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,
      public location: Location,
      private api: BulkImportApi,
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
    this.fileUploaded = false;
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

    this.file = file;
    this.readFile(this.file);
    this.startValidation(this.file);

    return false;
  };

  onFileChange(evt) {
    this.headerErrors = [];

    this.file = (evt.target).files[0];
    this.readFile(this.file);
    this.startValidation(this.file);

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
      console.log({
        data, columns
      })
      this.gridApi.setRowData(data);
      this.gridParams = {
        ...this.gridParams,
        rowData: data,
        columnDefs: columns
      };
      this.fileUploaded = true;
      this.detectChanges();
    };
    reader.readAsBinaryString(file);
  }

  startValidation(file) {
    const formData = new FormData();
    formData.append('payload', file);
    this.api.uploadAndValidate(formData).subscribe( ({errors}) => {
      console.log(errors)
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
      })
      this.gridApi.setRowData(data);
    });
  }

}
