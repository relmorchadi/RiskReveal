import {NgModule} from '@angular/core';

import {NgxsModule, StateContext} from "@ngxs/store";
import {environment} from "../../environments/environment"
import {NgxsHmrLifeCycle, NgxsHmrSnapshot as Snapshot} from "@ngxs/hmr-plugin";
import {NgxsReduxDevtoolsPluginModule} from "@ngxs/devtools-plugin";
import {NgxsFormPluginModule} from "@ngxs/form-plugin";
import {RouterStateSerializer} from "@ngxs/router-plugin";
import {CustomRouterStateSerializer} from "./service/router/RouterStateSerializer"
import {CommonModule} from "@angular/common";
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import {en_US, NgZorroAntdModule, NZ_I18N} from "ng-zorro-antd";

registerLocaleData(en);

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    NgZorroAntdModule,
    NgxsFormPluginModule.forRoot(),
    NgxsModule.forRoot([], {developmentMode: !environment.production}),
    ...environment.production ? [] : [NgxsReduxDevtoolsPluginModule.forRoot({name: 'Risk Reveal DevTools'})]
  ],
  exports: [
    NgZorroAntdModule,
    NgxsFormPluginModule, NgxsModule
  ]
})
export class CoreModule implements NgxsHmrLifeCycle<Snapshot> {
  static forRoot() {
    return {
      ngModule: CoreModule,
      providers: [{provide: RouterStateSerializer, useClass: CustomRouterStateSerializer},
        {provide: RouterStateSerializer, useClass: CustomRouterStateSerializer}, { provide: NZ_I18N, useValue: en_US }],

    }
  }

  public hmrNgxsStoreOnInit(ctx: StateContext<Snapshot>, snapshot: Partial<Snapshot>) {
    console.log("[RiskReveal Core] : Init State")
    ctx.patchState(snapshot);
    console.log({snapshot})
  }

  public hmrNgxsStoreBeforeOnDestroy(ctx: StateContext<Snapshot>): Partial<Snapshot> {
    console.log("[RiskReveal Core]  : Save State before destroying")
    return ctx.getState();
  }
}
