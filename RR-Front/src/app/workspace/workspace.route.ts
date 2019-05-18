import {Routes} from '@angular/router';
import {
  WorkspaceCalibrationComponent,
  WorkspaceMainComponent,
  WorkspacePltBrowserComponent,
  WorkspaceProjectComponent,
  WorkspaceRiskLinkComponent
} from './containers';


export const workspaceRoutes: Routes = [
  {
    data: {title: 'RR- Workspace'}, path: '', component: WorkspaceMainComponent,
    children: [
      {path: ':wsId/:year', component: WorkspaceProjectComponent},
      {path: ':wsId/:year/RiskLink', component: WorkspaceRiskLinkComponent},
      {path: ':wsId/:year/Calibration', component: WorkspaceCalibrationComponent},
      {path: ':wsId/:year/PltBrowser', component: WorkspacePltBrowserComponent},
      {path: ':wsId/:year/PopOut', component: WorkspaceProjectComponent},
    ]
  }
];
