import { Component } from '@angular/core';
import {ICellRendererAngularComp} from "@ag-grid-community/angular";
import {IAfterGuiAttachedParams, ICellRendererParams} from '@ag-grid-community/core';

@Component({
  selector: 'app-boolean-cell-renderer',
  templateUrl: './boolean-cell-renderer.component.html',
  styleUrls: ['./boolean-cell-renderer.component.scss']
})
export class BooleanCellRenderer implements ICellRendererAngularComp {
  params: any;

  afterGuiAttached(params?: IAfterGuiAttachedParams): void {
  }

  agInit(params: ICellRendererParams): void {
    this.params = params;
  }

  refresh(params: any): boolean {
    return false;
  }

}
