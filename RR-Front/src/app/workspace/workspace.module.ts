import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {COMPONENTS} from './components';
import {CONTAINERS} from './containers';
import {DIRECTIVES} from './directives';
import {PIPES} from './pipes';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import {NgMasonryGridModule} from 'ng-masonry-grid';
import {GridsterModule} from 'angular-gridster2';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {SERVICE} from './services';
import {VirtualScrollerModule} from 'primeng/virtualscroller';
import {workspaceRoutes} from './workspace.route';
import {TagsComponent} from './components/calibration/tags/tags.component';
import {LastAdjustmentMatrixComponent} from './components/calibration/last-adjustment-matrix/last-adjustment-matrix.component';
import {CalendarModule, ConfirmationService, DragDropModule, DropdownModule, RadioButtonModule} from 'primeng/primeng';
import {ToastModule} from 'primeng/toast';
import {DndModule} from 'ng2-dnd';
import {DndModule as NgxDndNodule} from 'ngx-drag-drop';
import {AdjustmentPopUpComponent} from './components/calibration/adjustment-pop-up/adjustment-pop-up.component';
import {ScrollingModule} from '@angular/cdk/scrolling';
import {TreeModule} from 'primeng/tree';
import {AddRemovePopUpComponent} from './components/calibration/add-remove-pop-up/add-remove-pop-up.component';
import {PopUpPltTableComponent} from './components/calibration/add-remove-pop-up/pop-up-plt-table/pop-up-plt-table.component';
import {CalibrationMainTableComponent} from './components/calibration/calibration-main-table/calibration-main-table.component';
import {InuringCanvasTabComponent} from './components/inuring/inuring-canvas-tab/inuring-canvas-tab.component';
import {jsPlumbToolkitModule} from 'jsplumbtoolkit-angular';
import {jsPlumbToolkitDragDropModule} from 'jsplumbtoolkit-angular-drop';
import {Dialogs} from 'jsplumbtoolkit';
import {INURING_NODES} from './components/inuring/nodes';
import {EditContractPopUpComponent} from './components/inuring/edit-contract-pop-up/edit-contract-pop-up.component';
import {EditEdgePopUpComponent} from './components/inuring/edit-edge-pop-up/edit-edge-pop-up.component';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import { ParseIdPipe } from './pipes/parse-id.pipe';
import {ReturnPeriodPopUpComponent} from "./components/calibration-new/return-period-pop-up/return-period-pop-up.component";
import {GetMetricPipe} from "./pipes/get-metric.pipe";
import { AddRemovePopUpNewComponent } from './components/calibration-new/add-remove-pop-up-new/add-remove-pop-up-new.component';
import { PopUpPltTableNewComponent } from './components/calibration-new/pop-up-plt-table-new/pop-up-plt-table-new.component';
import { ResizableModule  } from 'angular-resizable-element';
import { ContextMenuModule } from "primeng/primeng";
import { CalibrationSortAndFilterPipe } from './pipes/calibration-sort-and-filter.pipe';
import { FrozenColumnsFilterPipe } from './pipes/frozen-columns-filter.pipe';
import {FilterGroupedPltsPipe} from "./pipes/filter-grouped-plts.pipe";
import {SortGroupedPltsPipe} from "./pipes/sort-grouped-plts.pipe";
import {GetDeltaPipe} from "./pipes/get-delta.pipe";
import {FinancialUnitPipe} from "./pipes/financial-unit.pipe";
import {ExchangeRatePipe} from "../shared/pipes/exchange-rate.pipe";
import { AttachPltsPopUpComponent } from './components/scopeCompleteness/attach-plts-pop-up/attach-plts-pop-up.component';
import {AgGridModule} from "@ag-grid-community/angular";
import {CustomBooleanFilter} from "../shared/components/grid/custom-boolean-filter/custom-boolean-filter.component";
import {CustomBooleanFloatingFilter} from "../shared/components/grid/custom-boolean-floating-filter/custom-boolean-floating-filter.component";
import {StatusCellRenderer} from "../shared/components/grid/status-cell-renderer/status-cell-renderer.component";
import {BooleanCellRenderer} from "../shared/components/grid/boolean-cell-renderer/boolean-cell-renderer.component";
import {DateCellRenderer} from "../shared/components/grid/date-cell-renderer/date-cell-renderer.component";
import {NumberCellRenderer} from "../shared/components/grid/number-cell-renderer/number-cell-renderer.component";

@NgModule({
  entryComponents: [...COMPONENTS, ...CONTAINERS, ...INURING_NODES],
  declarations: [
    ...COMPONENTS, ...CONTAINERS,
    FrozenColumnsFilterPipe,
    ...PIPES, ...DIRECTIVES, TagsComponent, LastAdjustmentMatrixComponent, AdjustmentPopUpComponent, AddRemovePopUpComponent, PopUpPltTableComponent, CalibrationMainTableComponent, InuringCanvasTabComponent, EditContractPopUpComponent, EditEdgePopUpComponent, ParseIdPipe, ReturnPeriodPopUpComponent, AddRemovePopUpNewComponent, PopUpPltTableNewComponent, CalibrationSortAndFilterPipe, AttachPltsPopUpComponent

  ],
  imports: [
    AgGridModule.withComponents([ CustomBooleanFilter, CustomBooleanFloatingFilter, StatusCellRenderer, BooleanCellRenderer, DateCellRenderer, NumberCellRenderer]),
    GridsterModule,
    SharedModule,
    FormsModule,
    TreeModule,
    CalendarModule,
    ConfirmDialogModule,
    VirtualScrollerModule,
    ReactiveFormsModule,
    NgMasonryGridModule,
    DndModule.forRoot(),
    DragDropModule,
    NgxDndNodule,
    RadioButtonModule,
    RouterModule.forChild(workspaceRoutes),
    ToastModule,
    ScrollingModule,
    DropdownModule,
    jsPlumbToolkitModule,
    jsPlumbToolkitDragDropModule,
    ResizableModule,
    ContextMenuModule,

  ],
  exports: [
    RouterModule
  ],
  providers: [
    ...SERVICE,...PIPES, GetMetricPipe, CalibrationSortAndFilterPipe, ConfirmationService, FilterGroupedPltsPipe, SortGroupedPltsPipe, GetDeltaPipe, FinancialUnitPipe, ExchangeRatePipe, FrozenColumnsFilterPipe
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WorkspaceModule {
  constructor() {
    Dialogs.initialize({
      selector: '.dlg'
    });
  }
}
