import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {COMPONENTS} from './components';
import {CONTAINERS, WorkspaceMainComponent, WorkspaceRiskLinkComponent, WorkspaceProjectComponent} from './containers';
import {RouterModule, Routes} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import {HighlightDirective} from './highlight.directive';
import {TableModule} from 'primeng/table';
import {NgMasonryGridModule} from 'ng-masonry-grid';
const routes: Routes = [
  {
    data: {title: 'RR- Workspace'}, path: '', component: WorkspaceMainComponent,
    children: [
      {path: '', component: WorkspaceProjectComponent},
      {path: 'RiskLink', component: WorkspaceRiskLinkComponent, pathMatch: 'full'},
      {path: ':id',  redirectTo: '/', pathMatch: 'full'},
    ]
  }
];

@NgModule({
  declarations: [...COMPONENTS, ...CONTAINERS, HighlightDirective
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
