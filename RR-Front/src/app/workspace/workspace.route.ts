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
      {data: {title: 'RR- Workspace'}, path: ':wsId/:year', component: WorkspaceProjectComponent},
      {data: {title: 'RR- Risk Link'}, path: ':wsId/:year/RiskLink', component: WorkspaceRiskLinkComponent},
      {data: {title: 'RR- Calibration'}, path: ':wsId/:year/Calibration', component: WorkspaceCalibrationComponent},
      {data: {title: 'RR- Plt Browser'}, path: ':wsId/:year/PltBrowser', component: WorkspacePltBrowserComponent},
      {data: {title: 'RR- Workspace'}, path: ':wsId/:year/PopOut', component: WorkspaceProjectComponent},
    ]
  }
];
