import { Component, OnInit } from '@angular/core';
import {ICellRendererAngularComp} from "@ag-grid-community/angular";
import {ICellRendererParams, IAfterGuiAttachedParams} from '@ag-grid-community/core';
import {ErrorValue} from "../../../../import/types/erroValue.type";

@Component({
  selector: 'app-error-cell-renderer',
  templateUrl: './error-cell-renderer.component.html',
  styleUrls: ['./error-cell-renderer.component.scss']
})
export class ErrorCellRenderer implements ICellRendererAngularComp {
  params: any;
  isError: boolean;

  afterGuiAttached(params?: IAfterGuiAttachedParams): void {
  }

  agInit(params: ICellRendererParams): void {
    this.params = params;
    if( this.params.value instanceof ErrorValue) {
      this.isError = true;
    }
  }

  refresh(params: any): boolean {
    return false;
  }

}
