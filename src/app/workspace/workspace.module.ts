import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {COMPONENTS} from "./components"
import {CONTAINERS, WorkspaceMainComponent} from "./containers";
import {RouterModule} from "@angular/router";
import {SharedModule} from "../shared/shared.module";

const routes = [
  {path:'',component:WorkspaceMainComponent}
]

@NgModule({
  declarations: [
    ...COMPONENTS,...CONTAINERS
  ],
  imports: [
    SharedModule,
    RouterModule.forChild(routes)
  ],
  exports:[
    RouterModule
  ]
})
export class WorkspaceModule { }
