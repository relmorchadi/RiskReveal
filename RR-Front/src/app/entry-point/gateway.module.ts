import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {EntryComponent} from "./entry.component";
import {MainComponent} from "../core/containers/main/main.component";

const routes: Routes = [{
  path: '', component: MainComponent, children:[
    {data: {title: 'RR- Workspace'}, path: 'workspace', loadChildren: '../workspace/workspace.module#WorkspaceModule'},
    {data: {title: 'RR- Dashboard'}, path: 'dashboard', loadChildren: '../dashboard/dashboard.module#DashboardModule'},
    {data: {title: 'RR- PLT Comparer'}, path: 'plt-comparer', loadChildren: '../plt-comparer/plt-comparer.module#PltComparerModule'},
    {data: {title: 'RR- Search'}, path: 'search', loadChildren: '../search/search.module#SearchModule'},
    {data: {title: 'RR- JOB Manager'}, path: 'jobManager', loadChildren: '../job-manager/job-manager.module#JobManagerModule'},
    {path: '**', redirectTo: 'dashboard'}
  ]},
];

@NgModule({
  declarations:[EntryComponent],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule, EntryComponent]
})
export class GatewayModule { }
