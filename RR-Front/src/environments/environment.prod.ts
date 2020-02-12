// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: true,
  API_URI:'http://dcvdevalmf1:8880/risk-reveal/api/',
  IMPORT_URI:'http://dcvdevalmf1:9080/risk-reveal-import/api/',
  CALIBRATION_URI: 'http://dcvdevalmf1:8980/risk-reveal-adjustment/',
  PROXY_URI: 'http://dcvdevalmf1:9280/risk-reveal-proxy/',
  hmr:false
};


/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
