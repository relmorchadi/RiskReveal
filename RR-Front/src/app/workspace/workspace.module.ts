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
import {CalendarModule, DragDropModule, DropdownModule, RadioButtonModule} from 'primeng/primeng';
import {ToastModule} from 'primeng/toast';
import {DndModule} from 'ng2-dnd';
import {DndModule as NgxDndNodule} from 'ngx-drag-drop';
import {AdjustmentPopUpComponent} from './components/calibration/adjustment-pop-up/adjustment-pop-up.component';
import {ScrollingModule} from '@angular/cdk/scrolling';
import {TreeModule} from 'primeng/tree';
import {AddRemovePopUpComponent} from './components/calibration/add-remove-pop-up/add-remove-pop-up.component';
import {RiskLinkResSummaryComponent} from './components/import/risk-link-res-summary/risk-link-res-summary.component';
import {PopUpPltTableComponent} from './components/calibration/add-remove-pop-up/pop-up-plt-table/pop-up-plt-table.component';
import {CalibrationMainTableComponent} from './components/calibration/calibration-main-table/calibration-main-table.component';
import {InuringCanvasTabComponent} from './components/inuring/inuring-canvas-tab/inuring-canvas-tab.component';
import {AttachPltPopUpComponent} from './components/scopeCompleteness/attachPlt-pop-up/attach-plt-pop-up/attach-plt-pop-up.component';
import {jsPlumbToolkitModule} from 'jsplumbtoolkit-angular';
import {jsPlumbToolkitDragDropModule} from 'jsplumbtoolkit-angular-drop';
import {Dialogs} from 'jsplumbtoolkit';
import {INURING_NODES} from './components/inuring/nodes';
import {EditContractPopUpComponent} from './components/inuring/edit-contract-pop-up/edit-contract-pop-up.component';
import {EditEdgePopUpComponent} from './components/inuring/edit-edge-pop-up/edit-edge-pop-up.component';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {NgxEchartsModule} from 'ngx-echarts';
import {CreateFacProjectPopupComponent} from './containers/workspace-project/create-fac-project-popup/create-fac-project-popup.component';
import { ParseIdPipe } from './pipes/parse-id.pipe';
import {ReturnPeriodPopUpComponent} from "./components/calibration-new/return-period-pop-up/return-period-pop-up.component";
import {GetMetricPipe} from "./pipes/get-metric.pipe";

@NgModule({
  entryComponents: [...COMPONENTS, ...CONTAINERS, ...INURING_NODES],
  declarations: [
    ...COMPONENTS, ...CONTAINERS,
    ...PIPES, ...DIRECTIVES, TagsComponent, LastAdjustmentMatrixComponent, AdjustmentPopUpComponent, AddRemovePopUpComponent, RiskLinkResSummaryComponent, PopUpPltTableComponent, CalibrationMainTableComponent, InuringCanvasTabComponent, AttachPltPopUpComponent, EditContractPopUpComponent, EditEdgePopUpComponent, CreateFacProjectPopupComponent, ParseIdPipe, ReturnPeriodPopUpComponent
  ],
  imports: [
    NgxEchartsModule,
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
    jsPlumbToolkitDragDropModule
  ],
  exports: [
    RouterModule
  ],
  providers: [
    ...SERVICE, GetMetricPipe
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
