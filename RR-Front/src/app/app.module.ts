import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {GatewayModule} from './entry-point/gateway.module';
import {EntryComponent} from './entry-point/entry.component';
import {CoreModule} from './core/core.module';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {DndModule} from 'ngx-drag-drop';


@NgModule({
  imports: [
    BrowserModule,
    CoreModule.forRoot(),
    GatewayModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    NgbModule,
    DndModule
  ],
  bootstrap: [EntryComponent],

})
export class AppModule {
}
