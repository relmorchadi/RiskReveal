import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {NgZorroAntdModule} from "ng-zorro-antd";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    NgZorroAntdModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports:[
    CommonModule,
    NgZorroAntdModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class SharedModule { }
