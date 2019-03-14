import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CONTAINERS, PltComparerMainComponent} from './containers';
import {COMPONENTS} from './components';
import {SharedModule} from '../shared/shared.module';
import {RouterModule} from '@angular/router';

const routes = [
  {path: '', component: PltComparerMainComponent}
];

@NgModule({
  declarations: [...CONTAINERS, ...COMPONENTS],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(routes)
  ]
})
export class PltComparerModule { }
