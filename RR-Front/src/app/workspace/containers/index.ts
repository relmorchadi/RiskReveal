import {WorkspaceMainComponent} from './workspace-main/workspace-main.component';
import {WorkspaceProjectComponent} from './workspace-project/workspace-project.component';
import {WorkspaceRiskLinkComponent} from './workspace-risk-link/workspace-risk-link.component';
import {WorkspacePltBrowserComponent} from "./workspace-plt-browser/workspace-plt-browser.component";

export const CONTAINERS = [
  WorkspaceMainComponent,
  WorkspaceProjectComponent,
  WorkspaceRiskLinkComponent,
  WorkspacePltBrowserComponent
];

export * from './workspace-risk-link/workspace-risk-link.component';
export * from './workspace-main/workspace-main.component';
export * from './workspace-project/workspace-project.component';
export * from './workspace-plt-browser/workspace-plt-browser.component';
