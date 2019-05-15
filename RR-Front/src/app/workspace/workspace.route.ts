import {Routes} from "@angular/router";
import {
  WorkspaceMainComponent,
  WorkspacePltBrowserComponent,
  WorkspaceProjectComponent,
  WorkspaceRiskLinkComponent
} from "./containers";
import {WorkspaceCalibrationComponent} from "./containers/workspace-calibration/workspace-calibration.component";


export const workspaceRoutes: Routes = [
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
