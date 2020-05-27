import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CONTAINERS} from "./containers";
import {RoutingModule} from "./routing.config";
import {SharedModule} from "../shared/shared.module";
import {AgGridModule} from "@ag-grid-community/angular";
import {CustomBooleanFilter} from "../shared/components/grid/custom-boolean-filter/custom-boolean-filter.component";
import {CustomBooleanFloatingFilter} from "../shared/components/grid/custom-boolean-floating-filter/custom-boolean-floating-filter.component";
import {StatusCellRenderer} from "../shared/components/grid/status-cell-renderer/status-cell-renderer.component";
import {BooleanCellRenderer} from "../shared/components/grid/boolean-cell-renderer/boolean-cell-renderer.component";
import {DateCellRenderer} from "../shared/components/grid/date-cell-renderer/date-cell-renderer.component";
import {NumberCellRenderer} from "../shared/components/grid/number-cell-renderer/number-cell-renderer.component";
import {SERVICES} from "../shared/services";

@NgModule({
  declarations: [
      ...CONTAINERS
  ],
  imports: [
    CommonModule,
    RoutingModule,
    AgGridModule.withComponents([ CustomBooleanFilter, CustomBooleanFloatingFilter, StatusCellRenderer, BooleanCellRenderer, DateCellRenderer, NumberCellRenderer]),
    SharedModule
  ],
  providers: [
      ...SERVICES
  ]
})
export class ImportModule { }
