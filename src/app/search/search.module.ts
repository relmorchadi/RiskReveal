import {NgModule} from '@angular/core';
import {SearchMainComponent} from './containers/search-main/search-main.component';
import {CONTAINERS} from './containers';
import {SharedModule} from '../shared/shared.module';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {path: '', component: SearchMainComponent},
];

@NgModule({
  declarations: [...CONTAINERS],
  imports: [
    SharedModule,
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class SearchModule {
}
