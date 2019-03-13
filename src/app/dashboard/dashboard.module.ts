import {NgModule} from '@angular/core';
import {CONTAINERS, DashboardEntryComponent} from "./containers";
import {RouterModule} from "@angular/router";
import {SharedModule} from "../shared/shared.module";
import { SliderRightComponent } from './components/slider-right/slider-right.component';
import { RenewalContractScopeComponent } from './components/renewal-contract-scope/renewal-contract-scope.component';
import {GridsterModule} from 'angular-gridster2';

const routes = [
  {path: '', component: DashboardEntryComponent}
]


@NgModule({
  declarations: [...CONTAINERS, SliderRightComponent, RenewalContractScopeComponent],
  imports: [
    GridsterModule,
    SharedModule,
    RouterModule.forChild(routes)
  ]
})
export class DashboardModule {
}
