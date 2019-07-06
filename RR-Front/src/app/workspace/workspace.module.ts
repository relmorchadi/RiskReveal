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
import {CalendarModule, DialogModule, DragDropModule, RadioButtonModule} from 'primeng/primeng';
import {ToastModule} from "primeng/toast";
import {DndModule} from 'ng2-dnd';
import {DndModule as NgxDndNodule} from 'ngx-drag-drop';
import {AdjustmentPopUpComponent} from './components/calibration/adjustment-pop-up/adjustment-pop-up.component';

@NgModule({
  declarations: [
    ...COMPONENTS, ...CONTAINERS,
    ...PIPES, ...DIRECTIVES, TagsComponent, LastAdjustmentMatrixComponent, AdjustmentPopUpComponent,
  ],
  imports: [
    GridsterModule,
    SharedModule,
    FormsModule,
    DialogModule,
    CalendarModule,
    VirtualScrollerModule,
    ReactiveFormsModule,
    NgMasonryGridModule,
    DndModule.forRoot(),
    DragDropModule,
    NgxDndNodule,
    RadioButtonModule,
    RouterModule.forChild(workspaceRoutes),
    ToastModule
  ],
  exports: [
    RouterModule
  ],
  providers: [
    ...SERVICE
  ]
})
export class WorkspaceModule {
}
