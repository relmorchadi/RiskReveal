import {NgxsModule} from '@ngxs/store';
import {CORE_STATES} from './states';
import {WORKSPACE_STATES} from '../../workspace/store/states';
import {environment} from '../../../environments/environment';

export * from './actions';
export * from './states';


export const StoreModule = NgxsModule.forRoot([...CORE_STATES, ...WORKSPACE_STATES], {developmentMode: !environment.production});
