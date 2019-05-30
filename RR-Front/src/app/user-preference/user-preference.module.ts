import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CONTAINERS, UserPreferenceComponent } from './containers';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {path: '', component: UserPreferenceComponent}
];

@NgModule({
  declarations: [...CONTAINERS],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ]
})
export class UserPreferenceModule { }
