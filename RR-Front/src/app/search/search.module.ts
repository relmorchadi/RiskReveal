import {NgModule} from '@angular/core';
import {SearchMainComponent} from './containers/search-main/search-main.component';
import {CONTAINERS} from './containers';
import {SharedModule} from '../shared/shared.module';
import {RouterModule, Routes} from '@angular/router';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { ContractFilterPipe } from './pipes/contract-filter.pipe';
import {NgxsModule} from "@ngxs/store";

const routes: Routes = [
  {path: '', component: SearchMainComponent}
];

@NgModule({
  declarations: [...CONTAINERS, ContractFilterPipe],
  imports: [
    SharedModule,
    ScrollingModule,
    NgxsModule,
    RouterModule.forChild(routes)
  ],
  exports: []
})
export class SearchModule {
}
