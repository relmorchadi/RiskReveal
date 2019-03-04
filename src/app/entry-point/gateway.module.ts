import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MainComponent} from "./main.component";

const routes: Routes = [];

@NgModule({
  declarations:[MainComponent],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule,
  MainComponent]
})
export class GatewayModule { }
