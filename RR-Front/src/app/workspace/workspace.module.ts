import {NgModule} from '@angular/core';
import {COMPONENTS} from "./components"
import {CONTAINERS, WorkspaceMainComponent} from "./containers";
import {RouterModule, Routes} from '@angular/router';
import {SharedModule} from "../shared/shared.module";
import {WorkspaceRiskLinkComponent} from './containers/workspace-risk-link/workspace-risk-link.component';
import {HighlightDirective} from './highlight.directive';
import {TableModule} from 'primeng/table';
import {NgMasonryGridModule} from 'ng-masonry-grid';
import {WorkspaceComponent} from './workspace.component';
import {WorkspaceCalibrationComponent} from './containers/workspace-calibration/workspace-calibration.component';
import { CalibrationDirective } from './calibration.directive';

const routes: Routes = [
  {
    data: {title: 'RR- Workspace'}, path: '', component: WorkspaceComponent,
    children: [
      {path: '', component: WorkspaceMainComponent},
      {path: 'RiskLink', component: WorkspaceRiskLinkComponent, pathMatch: 'full'},
      {path: 'Calibration', component: WorkspaceCalibrationComponent, pathMatch: 'full'},
      {path: ':id', component: WorkspaceMainComponent},
    ]
  }
];

@NgModule({
  declarations: [
    ...COMPONENTS, ...CONTAINERS, WorkspaceRiskLinkComponent, HighlightDirective, WorkspaceMainComponent, WorkspaceComponent, WorkspaceCalibrationComponent, CalibrationDirective
  ],
  imports: [
    SharedModule,
    TableModule,
    NgMasonryGridModule,
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class WorkspaceModule {
}
