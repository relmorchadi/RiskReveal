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
import {ColorGithubModule} from 'ngx-color/github';
import {DragDropModule, DragDropModule as DragDropModuleAngular} from '@angular/cdk/drag-drop';
import {DIRECTIVES} from './directives';
import {SidebarModule} from 'primeng/sidebar';
import {AngularDraggableModule} from 'angular2-draggable';
import {SystemTagFilterPipe} from './pipes/system-tag-filter.pipe';
import {TableSortAndFilterPipe} from './pipes/table-sort-and-filter.pipe';
import {AngularResizedEventModule} from 'angular-resize-event';
import { TrimFormatPipe } from './pipes/trim-format.pipe';
import { TrimSecondaryFormatPipe } from './pipes/trim-secondary-format.pipe';

@NgModule({
  declarations: [...COMPONENTS, ...PIPES, ...DIRECTIVES, TrimFormatPipe, TrimSecondaryFormatPipe],
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
    MultiSelectModule,
    SidebarModule,
    DragDropModuleAngular,
    AngularDraggableModule,
    ColorGithubModule,
    AngularResizedEventModule
  ],
  providers: [TableSortAndFilterPipe, SystemTagFilterPipe],
  exports: [
    CommonModule,
    NgZorroAntdModule,
    HighlightDirective,
    ContextMenuModule,
    TableModule,
    ReactiveFormsModule,
    FormsModule,
    DialogModule,
    DragDropModule,
    DragDropModuleAngular,
    AngularDraggableModule,
    SidebarModule,
    ColorGithubModule,
    AngularResizedEventModule,
    ...COMPONENTS,
    ...PIPES
  ]
})
export class SharedModule { }
