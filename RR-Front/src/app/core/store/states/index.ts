import {WorkspaceMainState} from './workspace-main.state';
import {SearchNavBarState} from './search-nav-bar.state';
import {GeneralConfigState} from './global-config.state';
import {HeaderState} from './header.state';


export const CORE_STATES = [WorkspaceMainState, SearchNavBarState, GeneralConfigState, HeaderState];

export * from './global-config.state';
export * from './workspace-main.state';
export * from './search-nav-bar.state';
export * from './header.state';
