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
import {COMPONENTS} from "./components"
import {CONTAINERS} from "./containers";
import {RouterModule} from "@angular/router";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SharedModule} from '../shared/shared.module';
import { BoldKeywordPipe } from './pipes/bold-keyword.pipe';

registerLocaleData(en);

@NgModule({
  declarations: [...COMPONENTS,...CONTAINERS, BoldKeywordPipe],
  imports: [
    NgZorroAntdModule,
    RouterModule,
    FormsModule,
    SharedModule,
    ReactiveFormsModule,
    NgxsFormPluginModule.forRoot(),
    NgxsModule.forRoot([], {developmentMode: !environment.production}),
    ...environment.production ? [] : [NgxsReduxDevtoolsPluginModule.forRoot({name: 'Risk Reveal DevTools'})]
  ],
  exports: [
    NgZorroAntdModule,
    NgxsFormPluginModule, NgxsModule,
    ...COMPONENTS,...CONTAINERS
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
