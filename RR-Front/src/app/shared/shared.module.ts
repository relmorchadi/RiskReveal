import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {NgZorroAntdModule} from "ng-zorro-antd";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {COMPONENTS} from "./components";
import {TableModule} from 'primeng/table';

@NgModule({
  declarations: [...COMPONENTS],
  imports: [
    CommonModule,
    NgZorroAntdModule,
    ReactiveFormsModule,
    TableModule,
    FormsModule
  ],
  providers:[],
  exports:[
    CommonModule,
    NgZorroAntdModule,
    ReactiveFormsModule,
    FormsModule,
    ...COMPONENTS
  ]
})
export class SharedModule { }
