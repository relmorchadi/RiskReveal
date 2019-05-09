import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {COMPONENTS} from "./components"
import {CONTAINERS, WorkspaceMainComponent} from "./containers";
import {RouterModule, Routes} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import {TableModule} from 'primeng/table';
import {NgMasonryGridModule} from 'ng-masonry-grid';
import {WorkspaceComponent} from './workspace.component';
import {WorkspaceCalibrationComponent} from './containers/workspace-calibration/workspace-calibration.component';
import { CalibrationDirective } from './calibration.directive';

const routes: Routes = [
  {
    data: {title: 'RR- Workspace'}, path: '', component: WorkspaceMainComponent,
    children: [
      {path: '', component: WorkspaceMainComponent},
      {path: 'RiskLink', component: WorkspaceRiskLinkComponent, pathMatch: 'full'},
      {path: 'Calibration', component: WorkspaceCalibrationComponent, pathMatch: 'full'},
      {path: ':wsId/:year', component: WorkspaceProjectComponent},
    ]
  }
];

// To importt : WorkspaceRiskLinkComponent, HighlightDirective, WorkspaceMainComponent, WorkspaceComponent, WorkspaceCalibrationComponent, CalibrationDirective

@NgModule({
  declarations: [...COMPONENTS, ...CONTAINERS
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
