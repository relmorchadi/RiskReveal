import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {COMPONENTS} from './components'
import {
  CONTAINERS,
  WorkspaceMainComponent,
  WorkspacePltBrowserComponent,
  WorkspaceProjectComponent,
  WorkspaceRiskLinkComponent
} from './containers';
import {RouterModule, Routes} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import {TableModule} from 'primeng/table';
import {NgMasonryGridModule} from 'ng-masonry-grid';
import {WorkspaceCalibrationComponent} from './containers/workspace-calibration/workspace-calibration.component';
import {CalibrationDirective} from './calibration.directive';
import {ForNumberPipe} from './for-number.pipe';
import {ScrolltableDirective} from './scrolltable.directive';
import {GridsterModule} from 'angular-gridster2';


import {FilterByBadgePipe} from './pipes/filter-by-badge.pipe';
import {FilterByUserTagPipe} from './pipes/filter-by-user-tag.pipe';
import {FilterBySystemTagPipe} from './pipes/filter-by-system-tag.pipe';
import {FilterByPathPipe} from './pipes/filter-by-path.pipe';

const routes: Routes = [
  {
    data: {title: 'RR- Workspace'}, path: '', component: WorkspaceMainComponent,
    children: [
      {path: '', component: WorkspaceProjectComponent},
      {path: ':wsId/:year', component: WorkspaceProjectComponent},
      {path: ':wsId/:year/RiskLink', component: WorkspaceRiskLinkComponent},
      {path: ':wsId/:year/Calibration', component: WorkspaceCalibrationComponent},
      {path: ':wsId/:year/PltBrowser', component: WorkspacePltBrowserComponent},
    ]
  }
];

/* To importt : WorkspaceRiskLinkComponent, HighlightDirective,
 WorkspaceMainComponent, WorkspaceComponent, WorkspaceCalibrationComponent, CalibrationDirective
 */

@NgModule({
  declarations: [
    ...COMPONENTS, ...CONTAINERS,
    WorkspaceRiskLinkComponent,
    WorkspaceMainComponent,
    WorkspaceCalibrationComponent,
    CalibrationDirective,
    FilterByBadgePipe,
    FilterByUserTagPipe,
    FilterBySystemTagPipe,
    FilterByPathPipe
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
