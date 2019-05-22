import {NgModule} from '@angular/core';
import {COMPONENTS} from './components'
import {CONTAINERS} from './containers';
import { DIRECTIVES} from './directives';
import {PIPES} from "./pipes";
import {RouterModule} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import {TableModule} from 'primeng/table';
import {NgMasonryGridModule} from 'ng-masonry-grid';
import {GridsterModule} from 'angular-gridster2';
import {workspaceRoutes} from "./workspace.route";
import {DndModule} from "ngx-drag-drop";
import {DragulaModule} from "ng2-dragula";

@NgModule({
  declarations: [
    ...COMPONENTS, ...CONTAINERS,
    ...PIPES, ...DIRECTIVES
  ],
  imports: [
    GridsterModule,
    SharedModule,
    TableModule,
    NgMasonryGridModule,
    DndModule,
    RouterModule.forChild(workspaceRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class WorkspaceModule {
}
