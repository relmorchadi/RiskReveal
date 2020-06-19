import {NgModule} from '@angular/core';
import {CONTAINERS, WorkspaceJobManagerComponent} from './containers';
import {SharedModule} from '../shared/shared.module';
import {RouterModule, Routes} from '@angular/router';
import {AgGridModule} from "ag-grid-angular";
import {CustomBooleanFilter} from "../shared/components/grid/custom-boolean-filter/custom-boolean-filter.component";
import {CustomBooleanFloatingFilter} from "../shared/components/grid/custom-boolean-floating-filter/custom-boolean-floating-filter.component";
import {StatusCellRenderer} from "../shared/components/grid/status-cell-renderer/status-cell-renderer.component";
import {BooleanCellRenderer} from "../shared/components/grid/boolean-cell-renderer/boolean-cell-renderer.component";
import {DateCellRenderer} from "../shared/components/grid/date-cell-renderer/date-cell-renderer.component";
import {NumberCellRenderer} from "../shared/components/grid/number-cell-renderer/number-cell-renderer.component";
import {GroupedCellRenderer} from "../shared/components/grid/grouped-cell-renderer/grouped-cell-renderer.component";
import {DateTimeCellRenderer} from "../shared/components/grid/datetime-cell-renderer/datetime-cell-renderer.component";
import {TimeCellRenderer} from "../shared/components/grid/time-cell-renderer/time-cell-renderer.component";

const routes: Routes = [
  {path: '', component: WorkspaceJobManagerComponent}
];

@NgModule({
  declarations: [...CONTAINERS],
    imports: [
        SharedModule,
        RouterModule.forChild(routes),
        AgGridModule.withComponents([ CustomBooleanFilter, CustomBooleanFloatingFilter, StatusCellRenderer, BooleanCellRenderer, DateCellRenderer, NumberCellRenderer, GroupedCellRenderer,DateTimeCellRenderer,TimeCellRenderer])
    ],
  exports: []
})
export class JobManagerModule {
}
