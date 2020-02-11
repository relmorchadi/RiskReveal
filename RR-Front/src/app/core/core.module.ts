import {ErrorHandler, NgModule} from '@angular/core';

import {NgxsModule, StateContext} from '@ngxs/store';
import {environment} from '../../environments/environment';
import {NgxsHmrLifeCycle, NgxsHmrSnapshot as Snapshot} from '@ngxs/hmr-plugin';
import {NgxsReduxDevtoolsPluginModule} from '@ngxs/devtools-plugin';
import {NgxsFormPluginModule} from '@ngxs/form-plugin';
import {NgxsRouterPluginModule, RouterStateSerializer} from '@ngxs/router-plugin';
import {CustomRouterStateSerializer} from './service/router/RouterStateSerializer'
import {registerLocaleData} from '@angular/common';
import en from '@angular/common/locales/en';
import {en_US, NgZorroAntdModule, NZ_I18N} from 'ng-zorro-antd';
import {COMPONENTS} from './components';
import {CONTAINERS} from './containers';
import {PIPES} from './pipes';
import {DIRECTIVES} from './directives';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgxsStoragePluginModule, STORAGE_ENGINE, StorageEngine} from '@ngxs/storage-plugin';
import {SharedModule} from '../shared/shared.module';
import {StoreModule} from './store';
import {GlobalErrorHandler, MyStorageEngine} from "./config";
import {FooterModule} from "../footer/footer.module";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {TokenInterceptor} from "./service/auth/token.interceptor";
import {JwtInterceptor} from "./service/auth/jwt.interceptor";


registerLocaleData(en);

@NgModule({
  declarations: [...COMPONENTS, ...CONTAINERS, ...DIRECTIVES, ...PIPES],
  providers: [
      {provide: ErrorHandler, useClass: GlobalErrorHandler},
  ],
    imports: [
        NgZorroAntdModule,
        RouterModule,
        FormsModule,
        SharedModule,
        NgxsRouterPluginModule.forRoot(),
        ReactiveFormsModule,
        NgxsFormPluginModule.forRoot(),
        StoreModule,
        ...environment.production ? [] : [NgxsReduxDevtoolsPluginModule.forRoot({name: 'Risk Reveal DevTools'})],
        FooterModule
    ],
  exports: [
    NgZorroAntdModule,
    NgxsFormPluginModule,
    NgxsModule,
    ...COMPONENTS, ...CONTAINERS
  ]
})
export class CoreModule {
  static forRoot() {
    return {
      ngModule: CoreModule,
      providers: [{provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
          {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
          {provide: RouterStateSerializer, useClass: CustomRouterStateSerializer},
          {provide: RouterStateSerializer, useClass: CustomRouterStateSerializer}, {provide: NZ_I18N, useValue: en_US}],
    };
  }
}
