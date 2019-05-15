import {WorkspaceMainComponent} from './workspace-main/workspace-main.component';
import {WorkspaceProjectComponent} from './workspace-project/workspace-project.component';
import {WorkspaceRiskLinkComponent} from './workspace-risk-link/workspace-risk-link.component';
import {WorkspacePltBrowserComponent} from "./workspace-plt-browser/workspace-plt-browser.component";
import {WorkspaceCalibrationComponent} from "./workspace-calibration/workspace-calibration.component";

export const CONTAINERS = [
  WorkspaceMainComponent,
  WorkspaceProjectComponent,
  WorkspaceRiskLinkComponent,
  WorkspacePltBrowserComponent,
  WorkspaceCalibrationComponent
];

export * from './workspace-risk-link/workspace-risk-link.component';
export * from './workspace-main/workspace-main.component';
export * from './workspace-project/workspace-project.component';
export * from './workspace-plt-browser/workspace-plt-browser.component';
export * from './workspace-calibration/workspace-calibration.component';
