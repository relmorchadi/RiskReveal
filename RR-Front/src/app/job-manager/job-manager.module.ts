import {NgModule} from '@angular/core';
import {CONTAINERS, WorkspaceJobManagerComponent} from './containers';
import {SharedModule} from '../shared/shared.module';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {path: '', component: WorkspaceJobManagerComponent}
];

@NgModule({
  declarations: [...CONTAINERS],
  imports: [
    SharedModule,
    RouterModule.forChild(routes)
  ],
  exports: []
})
export class JobManagerModule {
}
