import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {NgZorroAntdModule} from 'ng-zorro-antd';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {COMPONENTS} from './components';
import {TableModule} from 'primeng/table';
import {RouterModule} from '@angular/router';
import {HighlightDirective} from './highlight.directive';
import {ContextMenuModule, MultiSelectModule} from 'primeng/primeng';
import { TableSortAndFilterPipe } from '../core/pipes/table-sort-and-filter.pipe';
import {KeysPipe} from './pipes/keys.pipe';
import { ShowLastPipe } from './pipes/show-last.pipe';
import { InputSearchPipe } from './pipes/input-search.pipe';
import {ColorChromeModule} from 'ngx-color/chrome';
import { ToArrayPipe } from './pipes/to-array.pipe';
import { LogPipe } from './pipes/log.pipe';


@NgModule({
  declarations: [...COMPONENTS, HighlightDirective, KeysPipe, ShowLastPipe, InputSearchPipe, ToArrayPipe, LogPipe],
  imports: [
    CommonModule,
    NgZorroAntdModule,
    ReactiveFormsModule,
    ContextMenuModule,
    TableModule,
    FormsModule
  ],
  providers: [],
  exports: [
    CommonModule,
    NgZorroAntdModule,
    HighlightDirective,
    ContextMenuModule,
    TableModule,
    ReactiveFormsModule,
    FormsModule,
    ...COMPONENTS,
    KeysPipe,
    ShowLastPipe,
    InputSearchPipe,
    ColorChromeModule,
    MultiSelectModule,
    ToArrayPipe,
    LogPipe
  ]
})
export class SharedModule { }
