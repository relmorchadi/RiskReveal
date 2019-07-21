import {ErrorHandler, Injectable, NgModule} from '@angular/core';

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
import {STORAGE_ENGINE, StorageEngine} from '@ngxs/storage-plugin';
import {SharedModule} from '../shared/shared.module';
import {StoreModule} from './store';
import * as _ from 'lodash';


export class MyStorageEngine implements StorageEngine {
  get length(): number {
    return localStorage.length;
    // Your logic here
  }

  getItem(key: string): any {
    if (key == '@@STATE') {
      let state = JSON.parse(localStorage.getItem(key));
      return JSON.stringify(_.omit(state, ['router']));
    }
    return localStorage.getItem(key);
  }

  setItem(key: string, val: any): void {
    localStorage.setItem(key, val);
  }

  removeItem(key: string): void {
    localStorage.removeItem(key);
  }

  clear(): void {
    localStorage.clear();
  }
}

@Injectable()
class GlobalErrorHandler implements ErrorHandler {
  handleError(error: any): void {
    console.info('From error handler');
    console.error(error);
    throw error;
  }
}

registerLocaleData(en);

@NgModule({
  declarations: [...COMPONENTS, ...CONTAINERS, ...DIRECTIVES, ...PIPES],
  providers: [
    {provide: STORAGE_ENGINE, useClass: MyStorageEngine},
    {provide: ErrorHandler, useClass: GlobalErrorHandler}
  ],
  imports: [
    NgZorroAntdModule,
    RouterModule,
    FormsModule,
    SharedModule,
    NgxsRouterPluginModule.forRoot(),
    ReactiveFormsModule,
    NgxsFormPluginModule.forRoot(),
    /*    NgxsStoragePluginModule.forRoot(),*/
    StoreModule,
    ...environment.production ? [] : [NgxsReduxDevtoolsPluginModule.forRoot({name: 'Risk Reveal DevTools'})]
  ],
  exports: [
    NgZorroAntdModule,
    NgxsFormPluginModule, NgxsModule,
    ...COMPONENTS, ...CONTAINERS
  ]
})
export class CoreModule implements NgxsHmrLifeCycle<Snapshot> {
  static forRoot() {
    return {
      ngModule: CoreModule,
      providers: [{provide: RouterStateSerializer, useClass: CustomRouterStateSerializer},
        {provide: RouterStateSerializer, useClass: CustomRouterStateSerializer}, {provide: NZ_I18N, useValue: en_US}],
    };
  }

  public hmrNgxsStoreOnInit(ctx: StateContext<Snapshot>, snapshot: Partial<Snapshot>) {
    console.log('[RiskReveal Core] : Init State');
    ctx.patchState(snapshot);
    console.log({snapshot});
  }

  public hmrNgxsStoreBeforeOnDestroy(ctx: StateContext<Snapshot>): Partial<Snapshot> {
    console.log('[RiskReveal Core]  : Save State before destroying');
    return ctx.getState();
  }
}
