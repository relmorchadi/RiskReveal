import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {COMPONENTS} from "./components"
import {CONTAINERS, WorkspaceMainComponent} from "./containers";
import {RouterModule, Routes} from '@angular/router';
import {SharedModule} from "../shared/shared.module";
import { WorkspaceRiskLinkComponent } from './containers/workspace-risk-link/workspace-risk-link.component';
import { HighlightDirective } from './highlight.directive';
import { TableModule }  from 'primeng/table';

const routes: Routes = [
  {data: {title: 'RR- Workspace'}, path: '', component: WorkspaceMainComponent},
  {data: {title: 'RR- Workspace'}, path: 'RiskLink', component: WorkspaceRiskLinkComponent},
  // {data: {title: 'RR- Workspace'}, path: ':id', component: WorkspaceMainComponent},
];

@NgModule({
  declarations: [
    ...COMPONENTS, ...CONTAINERS, WorkspaceRiskLinkComponent, HighlightDirective
  ],
  imports: [
    SharedModule,
    TableModule,
    RouterModule.forChild(routes)
  ],
  exports:[
    RouterModule
  ]
})
export class WorkspaceModule { }
