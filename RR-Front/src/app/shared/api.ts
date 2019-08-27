import {environment} from '../../environments/environment';

export const backendUrl: () => string = () => {
  const {hostname} = window.location;
  return environment.production ? `http://${hostname}:8880/risk-reveal/api/`
    : environment.API_URI;
};
