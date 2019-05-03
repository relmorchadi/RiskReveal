import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {NgZorroAntdModule} from 'ng-zorro-antd';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {COMPONENTS} from './components';
import {TableModule} from 'primeng/table';
import {RouterModule} from '@angular/router';
import {HighlightDirective} from './highlight.directive';


@NgModule({
  declarations: [...COMPONENTS, HighlightDirective],
  imports: [
    CommonModule,
    NgZorroAntdModule,
    ReactiveFormsModule,
    TableModule,
    FormsModule
  ],
  providers: [],
  exports: [
    CommonModule,
    NgZorroAntdModule,
    HighlightDirective,
    ReactiveFormsModule,
    FormsModule,
    ...COMPONENTS
  ]
})
export class SharedModule { }
