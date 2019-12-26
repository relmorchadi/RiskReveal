import {environment} from '../../environments/environment';

export const backendUrl: () => string = () => {
  const {hostname} = window.location;
  return environment.production ? `http://${hostname}:8880/risk-reveal/api/`
    : environment.API_URI;
};

export const importUrl: () => string = () => {
  const {hostname} = window.location;
  return environment.production ? `http://${hostname}:9080/risk-reveal-import/api/`
    : environment.IMPORT_URI;
};

export const utilityBackEndUrl: () => any = () => {
  return {
    calibration: `http://localhost:8082/api/`
  }
};

export const calibrationUrl: () => string = () => {
  const {hostname} = window.location;
  return environment.production ? `http://${hostname}:8980/risk-reveal-adjustment/api/`
    : environment.CALIBRATION_URI;
};
