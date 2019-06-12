import {WorkspaceMainState} from './workspace-main.state';
import {SearchNavBarState} from './search-nav-bar.state';
import {GeneralConfigState} from './global-config.state';


export const CORE_STATES = [WorkspaceMainState, SearchNavBarState, GeneralConfigState];

export * from './global-config.state';
export * from './workspace-main.state';
export * from './search-nav-bar.state';
