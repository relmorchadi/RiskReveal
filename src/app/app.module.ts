import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {GatewayModule} from './entry-point/gateway.module'
import {MainComponent} from './entry-point/main.component';
import {CoreModule} from "./core/core.module";
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';


@NgModule({
  imports: [
    BrowserModule,
    CoreModule.forRoot(),
    GatewayModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
  ],
  providers: [],
  bootstrap: [MainComponent]
})
export class AppModule {
}
