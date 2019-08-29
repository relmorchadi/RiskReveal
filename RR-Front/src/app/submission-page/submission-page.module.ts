import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubmissionPageComponent } from './containers/submission-page/submission-page.component';
import {NgZorroAntdModule} from 'ng-zorro-antd';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [SubmissionPageComponent],
  imports: [
    NgZorroAntdModule,
    FormsModule,
    CommonModule
  ]
})
export class SubmissionPageModule { }
