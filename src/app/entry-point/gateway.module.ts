import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {EntryComponent} from "./entry.component";
import {MainComponent} from "../core/containers/main/main.component";

const routes: Routes = [{
  path:'',component:MainComponent
}];

@NgModule({
  declarations:[EntryComponent],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule,
  EntryComponent]
})
export class GatewayModule { }
