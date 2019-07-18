import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CONTAINERS, UserPreferenceComponent} from './containers';
import {RouterModule, Routes} from '@angular/router';
import {NgZorroAntdModule} from 'ng-zorro-antd';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {MultiSelectModule} from 'primeng/multiselect';
import {TimezonePickerModule} from 'ng2-timezone-selector';
import {FormsModule} from '@angular/forms';

const routes: Routes = [
  {path: '', component: UserPreferenceComponent}
];

@NgModule({
  declarations: [...CONTAINERS],
  imports: [
    NgZorroAntdModule,
    FormsModule,
    MultiSelectModule,
    TimezonePickerModule,
    CommonModule,
    RouterModule.forChild(routes)
  ],
  exports: [
    NgZorroAntdModule
    ]
})
export class UserPreferenceModule { }
