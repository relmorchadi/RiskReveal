import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {EntryComponent} from "./entry.component";
import {MainComponent} from "../core/containers/main/main.component";

const routes: Routes = [{
  path:'',component:MainComponent,children:[
    {path:'workspace',loadChildren:'../workspace/workspace.module#WorkspaceModule'},
    {path:'dashboard',loadChildren:'../dashboard/dashboard.module#DashboardModule'},
    {path:'**',redirectTo:'workspace'}
  ]},
];

@NgModule({
  declarations:[EntryComponent],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule,
  EntryComponent]
})
export class GatewayModule { }
