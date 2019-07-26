import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NgZorroAntdModule} from 'ng-zorro-antd';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {COMPONENTS} from './components';
import {TableModule} from 'primeng/table';
import {HighlightDirective} from './highlight.directive';
import {ContextMenuModule, DialogModule, MultiSelectModule} from 'primeng/primeng';
import {ColorChromeModule} from 'ngx-color/chrome';
import {PIPES} from './pipes';
import {ColorSketchModule} from 'ngx-color/sketch';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {DIRECTIVES} from "./directives";


@NgModule({
  declarations: [...COMPONENTS, ...PIPES, ...DIRECTIVES],
  imports: [
    CommonModule,
    NgZorroAntdModule,
    ReactiveFormsModule,
    ContextMenuModule,
    DialogModule,
    TableModule,
    FormsModule,
    ColorChromeModule,
    ColorSketchModule,
    MultiSelectModule
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
    DragDropModule,
    ...COMPONENTS,
    ...PIPES
  ]
})
export class SharedModule { }
