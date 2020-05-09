import { Component, OnInit } from '@angular/core';
import {ICellRendererAngularComp} from "@ag-grid-community/angular";
import {ICellRendererParams, IAfterGuiAttachedParams} from '@ag-grid-community/core';
import {GeneralConfigState} from "../../../../core/store/states";
import {Select} from "@ngxs/store";

@Component({
  selector: 'app-number-cell-renderer',
  templateUrl: './number-cell-renderer.component.html',
  styleUrls: ['./number-cell-renderer.component.scss']
})
export class NumberCellRenderer implements ICellRendererAngularComp {
  params: any;

  numberConfig: {
    numberOfDecimals: number;
    decimalSeparator: string;
    decimalThousandSeparator: string;
    negativeFormat: string;
  };

  @Select(GeneralConfigState.getNumberFormatConfig) numberConfig$;

  constructor() {
    this.numberConfig$.subscribe(v => this.numberConfig =v);
  }

  afterGuiAttached(params?: IAfterGuiAttachedParams): void {
  }

  agInit(params: ICellRendererParams): void {
    this.params = params;
  }

  refresh(params: any): boolean {
    return false;
  }

}
