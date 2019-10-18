import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubmissionPageComponent } from './containers/submission-page/submission-page.component';
import { NgZorroAntdModule } from 'ng-zorro-antd';
import { FormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';

@NgModule({
  declarations: [SubmissionPageComponent],
  imports: [
    NgZorroAntdModule,
    FormsModule,
    TableModule,
    CommonModule
  ]
})
export class SubmissionPageModule { }
