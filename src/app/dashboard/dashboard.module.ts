import {NgModule} from '@angular/core';
import {CONTAINERS, DashboardEntryComponent} from "./containers";
import {RouterModule} from "@angular/router";
import {SharedModule} from "../shared/shared.module";

const routes = [
  {path: '', component: DashboardEntryComponent}
]


@NgModule({
  declarations: [...CONTAINERS],
  imports: [
    SharedModule,
    RouterModule.forChild(routes)
  ]
})
export class DashboardModule {
}
