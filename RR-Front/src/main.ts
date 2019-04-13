import {ApplicationRef, enableProdMode as prodMode, NgModuleRef} from '@angular/core';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

import {AppModule} from './app/app.module';
import {environment} from './environments/environment';
//import {hmr} from "@ngxs/hmr-plugin";
import {createNewHosts} from "@angularclass/hmr";
import {hmr} from "@ngxs/hmr-plugin";


const disableLogging = () => {
  if (environment.production) {
    window.console.log = () => {
    }
    window.console.warn = () => {
    }
  }
}
const enableProdMode = () => {
  if (environment.production) prodMode()
}

const bootstrap = () => platformBrowserDynamic().bootstrapModule(AppModule);

export const hmrBootstrap = (module: any, bootstrap: () => Promise<NgModuleRef<any>>):Promise<any> => {
  let ngModule: NgModuleRef<any>;
  module.hot.accept();
  let boostrapPromise:any = hmr(module,bootstrap,{autoClearLogs:false}).then(mod => ngModule = mod);
  module.hot.dispose(() => {
    const appRef: ApplicationRef = ngModule.injector.get(ApplicationRef);
    const elements = appRef.components.map(c => c.location.nativeElement);
    const makeVisible = createNewHosts(elements);
    makeVisible();
  });
  return boostrapPromise;
};

if (environment.hmr) {
  if (module['hot']) {
    hmrBootstrap(module, bootstrap).catch(err => console.error(err));
  } else {
    console.error('HMR is not enabled for webpack-dev-server!');
    console.log('Are you using the --hmr flag for ng serve?');
  }
} else {
  disableLogging()
  enableProdMode()
  bootstrap().catch(console.error);
}

