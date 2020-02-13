import {SearchNavBarState} from './search-nav-bar.state';
import {GeneralConfigState} from './global-config.state';
import {HeaderState} from './header.state';
import {DashboardState} from './dashboard.state';
import {AuthState} from "./auth.state";

export const CORE_STATES = [SearchNavBarState, GeneralConfigState, DashboardState, HeaderState, AuthState];

export * from './auth.state'
export * from './dashboard.state';
export * from './global-config.state';
export * from './search-nav-bar.state';
export * from './header.state';
