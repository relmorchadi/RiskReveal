import {NgModule} from '@angular/core';
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
import {CalendarModule, DialogModule, DragDropModule, DropdownModule, RadioButtonModule} from 'primeng/primeng';
import {ToastModule} from 'primeng/toast';
import {DndModule} from 'ng2-dnd';
import {DndModule as NgxDndNodule} from 'ngx-drag-drop';
import {AdjustmentPopUpComponent} from './components/calibration/adjustment-pop-up/adjustment-pop-up.component';
import {ScrollingModule} from '@angular/cdk/scrolling';
import {TreeModule} from 'primeng/tree';
import {AddRemovePopUpComponent} from './components/calibration/add-remove-pop-up/add-remove-pop-up.component';
import {RiskLinkResSummaryComponent} from './containers/workspace-risk-link/risk-link-res-summary/risk-link-res-summary.component';
import {PopUpPltTableComponent} from "./components/calibration/add-remove-pop-up/pop-up-plt-table/pop-up-plt-table.component";
import {CalibrationMainTableComponent} from './components/calibration/calibration-main-table/calibration-main-table.component';
import {DragDropModule as DragDropModuleAngular} from '@angular/cdk/drag-drop';
import { InuringCanvasTabComponent } from './components/inuring/inuring-canvas-tab/inuring-canvas-tab.component';
import {AttachPltPopUpComponent} from './components/scopeCompleteness/attachPlt-pop-up/attach-plt-pop-up/attach-plt-pop-up.component';
import { NodeCreationPopupComponent } from './components/inuring/node-creation-popup/node-creation-popup.component';
import { InuringGraphComponent } from './components/inuring/inuring-graph/inuring-graph.component';
import {jsPlumbToolkitModule} from 'jsplumbtoolkit-angular';
import {jsPlumbToolkitDragDropModule} from 'jsplumbtoolkit-angular-drop';
import { SimpleNodeComponent } from './components/inuring/simple-node/simple-node.component';
// import {Dialogs} from 'jsplumbtoolkit';

@NgModule({
  entryComponents: [...COMPONENTS, ...CONTAINERS, SimpleNodeComponent],
  declarations: [
    ...COMPONENTS, ...CONTAINERS,
    ...PIPES, ...DIRECTIVES, TagsComponent, LastAdjustmentMatrixComponent, AdjustmentPopUpComponent, AddRemovePopUpComponent, RiskLinkResSummaryComponent, PopUpPltTableComponent, CalibrationMainTableComponent, InuringCanvasTabComponent, AttachPltPopUpComponent, NodeCreationPopupComponent, InuringGraphComponent, SimpleNodeComponent
  ],
  imports: [
    GridsterModule,
    SharedModule,
    FormsModule,
    TreeModule,
    DialogModule,
    CalendarModule,
    VirtualScrollerModule,
    ReactiveFormsModule,
    NgMasonryGridModule,
    DndModule.forRoot(),
    DragDropModule,
    DragDropModuleAngular,
    NgxDndNodule,
    RadioButtonModule,
    RouterModule.forChild(workspaceRoutes),
    ToastModule,
    ScrollingModule,
    DropdownModule,
    jsPlumbToolkitModule, jsPlumbToolkitDragDropModule
  ],
  exports: [
    RouterModule
  ],
  providers: [
    ...SERVICE,
  ]
})
export class WorkspaceModule {
}
