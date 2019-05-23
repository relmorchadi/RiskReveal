import {NgModule} from '@angular/core';
import {CONTAINERS, WorkspaceJobManagerComponent} from './containers';
import {SharedModule} from '../shared/shared.module';
import {RouterModule, Routes} from '@angular/router';
import { ScrollingModule } from '@angular/cdk/scrolling';

const routes: Routes = [
  {path: '', component: WorkspaceJobManagerComponent}
];

@NgModule({
  declarations: [...CONTAINERS],
  imports: [
    SharedModule,
    ScrollingModule,
    RouterModule.forChild(routes)
  ],
  exports: []
})
export class JobManagerModule {
}
