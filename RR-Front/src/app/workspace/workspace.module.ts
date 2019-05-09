import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {COMPONENTS} from "./components"
import {CONTAINERS, WorkspaceMainComponent, WorkspaceProjectComponent, WorkspaceRiskLinkComponent} from "./containers";
import {RouterModule, Routes} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import {HighlightDirective} from './highlight.directive';
import {TableModule} from 'primeng/table';
import {NgMasonryGridModule} from 'ng-masonry-grid';
import {WorkspaceCalibrationComponent} from './containers/workspace-calibration/workspace-calibration.component';
import { CalibrationDirective } from './calibration.directive';
import {ForNumberPipe} from "./for-number.pipe";
import { ScrolltableDirective } from './scrolltable.directive';
import {GridsterModule} from "angular-gridster2";


import { FilterByBadgePipe } from './pipes/filter-by-badge.pipe';
const routes: Routes = [
  {
    data: {title: 'RR- Workspace'}, path: '', component: WorkspaceMainComponent,
    children: [
      {path: '', component: WorkspaceProjectComponent},
      {path: 'RiskLink', component: WorkspaceRiskLinkComponent, pathMatch: 'full'},
      {path: 'Calibration', component: WorkspaceCalibrationComponent, pathMatch: 'full'},
      {path: 'PltBrowser', component: WorkspacePltBrowserComponent, pathMatch:'full'},
      {path: ':wsId/:year', component: WorkspaceProjectComponent},
    ]
  }
];

// To importt : WorkspaceRiskLinkComponent, HighlightDirective, WorkspaceMainComponent, WorkspaceComponent, WorkspaceCalibrationComponent, CalibrationDirective

@NgModule({
  declarations: [
    ...COMPONENTS, ...CONTAINERS, WorkspaceRiskLinkComponent, WorkspaceMainComponent, WorkspaceCalibrationComponent, CalibrationDirective, FilterByBadgePipe
  ],
  imports: [
    GridsterModule,
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
