import {NgModule} from '@angular/core';
import {CONTAINERS, DashboardEntryComponent} from "./containers";
import {RouterModule} from "@angular/router";
import {SharedModule} from "../shared/shared.module";
import { SliderRightComponent } from './components/slider-right/slider-right.component';

const routes = [
  {path: '', component: DashboardEntryComponent}
]


@NgModule({
  declarations: [...CONTAINERS, SliderRightComponent],
  imports: [
    SharedModule,
    RouterModule.forChild(routes)
  ]
})
export class DashboardModule {
}
