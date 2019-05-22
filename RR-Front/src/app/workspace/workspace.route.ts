import {Routes} from '@angular/router';
import {
  WorkspaceAccumulationComponent,
  WorkspaceActivityComponent,
  WorkspaceCalibrationComponent,
  WorkspaceCloneDataComponent,
  WorkspaceContractComponent,
  WorkspaceExposuresComponent,
  WorkspaceFileBaseImportComponent, WorkspaceInuringComponent,
  WorkspaceMainComponent,
  WorkspacePltBrowserComponent, WorkspacePricingComponent,
  WorkspaceProjectComponent, WorkspaceResultsComponent,
  WorkspaceRiskLinkComponent, WorkspaceScopeCompletenceComponent
} from './containers';


export const workspaceRoutes: Routes = [
  {
    data: {title: 'RR- Workspace'}, path: '', component: WorkspaceMainComponent,
    children: [
      {data: {title: 'RR- Workspace'}, path: ':wsId/:year', component: WorkspaceProjectComponent},
      {data: {title: 'RR- Risk Link'}, path: ':wsId/:year/RiskLink', component: WorkspaceRiskLinkComponent},
      {data: {title: 'RR- Calibration'}, path: ':wsId/:year/Calibration', component: WorkspaceCalibrationComponent},
      {data: {title: 'RR- Plt Browser'}, path: ':wsId/:year/PltBrowser', component: WorkspacePltBrowserComponent},
      {data: {title: 'RR- Accumulation'}, path: ':wsId/:year/Accumulation', component: WorkspaceAccumulationComponent},
      {data: {title: 'RR- Activity'}, path: ':wsId/:year/Activity', component: WorkspaceActivityComponent},
      {data: {title: 'RR- Clone Data'}, path: ':wsId/:year/CloneData', component: WorkspaceCloneDataComponent},
      {data: {title: 'RR- Contract'}, path: ':wsId/:year/Contract', component: WorkspaceContractComponent},
      {data: {title: 'RR- Exposures'}, path: ':wsId/:year/Exposures', component: WorkspaceExposuresComponent},
      {data: {title: 'RR- File Based Import'}, path: ':wsId/:year/FileBasedImport', component: WorkspaceFileBaseImportComponent},
      {data: {title: 'RR- Inuring'}, path: ':wsId/:year/Inuring', component: WorkspaceInuringComponent},
      {data: {title: 'RR- Pricing'}, path: ':wsId/:year/Pricing', component: WorkspacePricingComponent},
      {data: {title: 'RR- Results'}, path: ':wsId/:year/Results', component: WorkspaceResultsComponent},
      {data: {title: 'RR- Scope Completeness'}, path: ':wsId/:year/ScopeCompleteness', component: WorkspaceScopeCompletenceComponent},
      {data: {title: 'RR- Workspace'}, path: ':wsId/:year/PopOut', component: WorkspaceProjectComponent},

    ]
  }
];
