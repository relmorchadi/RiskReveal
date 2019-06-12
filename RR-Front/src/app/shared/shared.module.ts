import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {NgZorroAntdModule} from 'ng-zorro-antd';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {COMPONENTS} from './components';
import {TableModule} from 'primeng/table';
import {HighlightDirective} from './highlight.directive';
import {ContextMenuModule, MultiSelectModule} from 'primeng/primeng';
import {KeysPipe} from './pipes/keys.pipe';
import { ShowLastPipe } from './pipes/show-last.pipe';
import { InputSearchPipe } from './pipes/input-search.pipe';
import {ColorChromeModule} from 'ngx-color/chrome';
import {PIPES} from './pipes';


@NgModule({
  declarations: [...COMPONENTS, ...PIPES],
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
    ...PIPES
  ]
})
export class SharedModule { }
