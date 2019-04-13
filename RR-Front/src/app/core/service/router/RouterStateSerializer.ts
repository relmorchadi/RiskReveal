import {RouterStateParams} from "./RouterState";
import {RouterStateSnapshot} from "@angular/router";
import {RouterStateSerializer} from "@ngxs/router-plugin";

export class CustomRouterStateSerializer implements RouterStateSerializer<RouterStateParams> {
  serialize(routerState: RouterStateSnapshot): RouterStateParams {
    const {
      url,
      root: { queryParams }
    } = routerState;

    let { root: route } = routerState;
    while (route.firstChild) {
      route = route.firstChild;
    }

    const { params } = route;

    return { url, params, queryParams };
  }
}
