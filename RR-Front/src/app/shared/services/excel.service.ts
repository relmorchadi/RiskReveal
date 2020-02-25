import {Injectable} from '@angular/core';
import * as FileSaver from 'file-saver';
import * as XLSX from 'xlsx';
import * as _ from "lodash"

const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
const EXCEL_EXTENSION = '.xlsx';

@Injectable({
  providedIn: 'root'
})
export class ExcelService {

  constructor() {
  }

  public exportAsExcelFile(sheets: any[], excelFileName: string): void {
    const workbook: XLSX.WorkBook = {Sheets: {}, SheetNames: []};

    _.forEach(sheets, sheet => {
      XLSX.utils.book_append_sheet(workbook, XLSX.utils.json_to_sheet(sheet.sheetData), sheet.sheetName)
    });
    const excelBuffer: any = XLSX.write(workbook, {bookType: 'xlsx', type: 'array'});
    this.saveAsExcelFile(excelBuffer, excelFileName);
  }

  public saveAsExcelFile(buffer: any, fileName: string): void {
    const data: Blob = new Blob([buffer], {type: EXCEL_TYPE});
    FileSaver.saveAs(data, fileName + '-' + new Date().toLocaleString() + EXCEL_EXTENSION);
  }
}
