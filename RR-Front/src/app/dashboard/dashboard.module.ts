import {NgModule} from '@angular/core';
import {CONTAINERS, DashboardEntryComponent} from './containers';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import {SliderRightComponent} from './components/slider-right/slider-right.component';
import {RenewalContractScopeComponent} from './components/renewal-contract-scope/renewal-contract-scope.component';
import {GridsterModule} from 'angular-gridster2';
import {DataTableModule} from 'primeng/primeng';
import {TableModule} from 'primeng/table';
import {FacWidgetComponent} from './components/fac-widget/fac-widget.component';
import {FacChartWidgetComponent} from './components/fac-chart-widget/fac-chart-widget.component';
import {NgxEchartsModule} from 'ngx-echarts';

const routes = [
  {path: '', component: DashboardEntryComponent}
];


@NgModule({
  declarations: [...CONTAINERS, SliderRightComponent, RenewalContractScopeComponent, FacWidgetComponent, FacChartWidgetComponent],
  imports: [
    NgxEchartsModule,
    GridsterModule,
    SharedModule,
    DataTableModule,
    TableModule,
    RouterModule.forChild(routes)
  ]
})
export class DashboardModule {
}
