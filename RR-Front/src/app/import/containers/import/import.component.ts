import { Component, OnInit } from '@angular/core';
import {Location} from "@angular/common";
import * as _ from "lodash";
import * as XLSX from 'xlsx';
import {AllModules} from "@ag-grid-enterprise/all-modules";

@Component({
  selector: 'app-import',
  templateUrl: './import.component.html',
  styleUrls: ['./import.component.scss']
})
export class ImportContainer implements OnInit {

  public modules = AllModules;

  gridApi: any;
  gridColumnApi: any;
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

  constructor(public location: Location) {
    this.gridParams = {
      rowModelType: "clientSide",
      rowData: [],
      columnDefs: [
        { headerName: "Workspace Reference", field: "workspaceContextCode" },
        { headerName: "Workspace Context", field: "workspaceContext" },
        { headerName: "UwYear", field: "workspaceUwYear" },
        { headerName: "Instance", field: "instance" },
        { headerName: "Type", field: "type" },
        { headerName: "Date Source Name", field: "dataSourceName" },
        { headerName: "Object Source Id", field: "objectSourceId" },
        { headerName: "Object Source Name", field: "objectSourceName" },
        { headerName: "Object Source Desc", field: "objectSourceDesc" },
        { headerName: "Region", field: "region" },
        { headerName: "Peril", field: "peril" },
        { headerName: "Currency", field: "currency" },
        { headerName: "Target Currency", field: "targetCurrency" },
        { headerName: "Financial Perspective", field: "financialPerspective" },
        { headerName: "Region Peril", field: "regionPeril" },
        { headerName: "Override Reason", field: "overrideReasonRegionPeril" },
        { headerName: "PEQT Id", field: "peqtId" },
        { headerName: "PEQT Name", field: "peqtName" },
        { headerName: "Occurrence Basis", field: "occurrenceBasisOccurrenceBasis" },
        { headerName: "Unit Multiplier", field: "unitMultiplier" },
        { headerName: "Unit Multiplier Basis", field: "unitMultiplierBasis" },
        { headerName: "Unit Multiplier Narrative", field: "unitMultiplierNarrative" },
        { headerName: "Proportion Pct", field: "proportionPct" },
        { headerName: "Proportion Pct Basis", field: "proportionPctBasis" },
        { headerName: "Proportion Pct Narrative", field: "proportionPctNarrative" },
        { headerName: "Section Division Id", field: "sectionDivisionId" },
        { headerName: "AutoPublish", field: "autoPublish" },
        { headerName: "AppendReplace", field: "appendReplace" },
      ],
      autoGroupColumnDef: null,
      defaultColDef: {
        resizable: true,
        sortable: true,
        enableRowGroup: false,
        floatingFilter: false,
        filter: 'agTextColumnFilter'
      },
      frameworkComponents: null,
      rowSelection: "multiple",
      getChildCount: () => {}
    }
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

  columnMapping = {
    workspaceReference: "workspaceContextCode",
    uWYear: "workspaceUwYear"
  };

  onFileChange(evt: any) {
    /* wire up file reader */
    const target: DataTransfer = <DataTransfer>(evt.target);
    if (target.files.length !== 1) throw new Error('Cannot use multiple files');
    const reader: FileReader = new FileReader();
    reader.onload = (e: any) => {
      /* read workbook */
      const bstr: string = e.target.result;
      const wb: XLSX.WorkBook = XLSX.read(bstr, {type: 'binary'});

      /* grab first sheet */
      const wsname: string = wb.SheetNames[0];
      const ws: XLSX.WorkSheet = wb.Sheets[wsname];

      /* save data */
      this.gridApi.setRowData(_.map(<any>(XLSX.utils.sheet_to_json(ws, {})), (row) => {
        let newRow = {};

        _.forEach(row, (v, k) => {
          let col = _.lowerFirst(k);
          newRow[this.columnMapping[col] ? this.columnMapping[col] : col] = v;
        });

        return newRow;
      }));

    };
    reader.readAsBinaryString(target.files[0]);
  }

}
