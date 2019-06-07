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
import {DndModule} from 'ngx-drag-drop';
import {SystemTagFilterPipe} from './pipes/system-tag-filter.pipe';
import {TagsComponent} from './components/calibration/tags/tags.component';
import {LastAdjustmentMatrixComponent} from './components/calibration/last-adjustment-matrix/last-adjustment-matrix.component';

@NgModule({
  declarations: [
    ...COMPONENTS, ...CONTAINERS,
    ...PIPES, ...DIRECTIVES, SystemTagFilterPipe, TagsComponent, LastAdjustmentMatrixComponent
  ],
  imports: [
    GridsterModule,
    SharedModule,
    FormsModule,
    VirtualScrollerModule,
    ReactiveFormsModule,
    NgMasonryGridModule,
    DndModule,
    RouterModule.forChild(workspaceRoutes)
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
