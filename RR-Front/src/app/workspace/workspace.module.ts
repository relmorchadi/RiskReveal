import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {COMPONENTS} from './components';
import {
  CONTAINERS,
  WorkspaceMainComponent,
  WorkspaceRiskLinkComponent,
  WorkspaceProjectComponent,
  WorkspacePltBrowserComponent
} from './containers';
import {RouterModule, Routes} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import {HighlightDirective} from './highlight.directive';
import {TableModule} from 'primeng/table';
import {NgMasonryGridModule} from 'ng-masonry-grid';
import { FilterByBadgePipe } from './pipes/filter-by-badge.pipe';
import { FilterByUserTagPipe } from './pipes/filter-by-user-tag.pipe';
import { FilterBySystemTagPipe } from './pipes/filter-by-system-tag.pipe';
import { FilterByPathPipe } from './pipes/filter-by-path.pipe';
const routes: Routes = [
  {
    data: {title: 'RR- Workspace'}, path: '', component: WorkspaceMainComponent,
    children: [

      {path: '', component: WorkspaceProjectComponent},
      {path: 'RiskLink', component: WorkspaceRiskLinkComponent, pathMatch: 'full'},
      {path: 'PltBrowser', component: WorkspacePltBrowserComponent, pathMatch:'full'},
      {path: ':wsId/:year', component: WorkspaceProjectComponent},
    ]
  }
];

@NgModule({
  declarations: [...COMPONENTS, ...CONTAINERS, HighlightDirective, FilterByBadgePipe, FilterByUserTagPipe, FilterBySystemTagPipe, FilterByPathPipe
  ],
  imports: [
    SharedModule,
    TableModule,
    NgMasonryGridModule,
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule,
    HighlightDirective
  ]
})
export class WorkspaceModule {
}
