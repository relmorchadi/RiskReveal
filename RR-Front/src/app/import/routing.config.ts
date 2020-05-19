import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ImportContainer} from "./containers/import/import.component";

const routes: Routes = [
  {path: '', component: ImportContainer},
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  exports: [RouterModule]
})
export class RoutingModule { }