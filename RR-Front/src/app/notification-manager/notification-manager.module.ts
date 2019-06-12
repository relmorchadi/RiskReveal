import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CONTAINERS, NotificationManagerComponent } from './containers';
import { NgZorroAntdModule } from 'ng-zorro-antd';
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [
  {path: '', component: NotificationManagerComponent}
];

@NgModule({
  declarations: [NotificationManagerComponent],
  imports: [
    NgZorroAntdModule,
    CommonModule,
    RouterModule.forChild(routes)
  ]
})
export class NotificationManagerModule { }
