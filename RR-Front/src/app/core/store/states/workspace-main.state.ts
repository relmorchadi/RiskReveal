import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import {WorkspaceMain} from '../../model/workspace-main';
import {Observable} from 'rxjs';
import {
  CloseWorkspaceMainAction,
  OpenWorkspaceMainAction,
  PatchWorkspaceMainStateAction,
} from '../actions';
import * as _ from 'lodash';

const initiaState: WorkspaceMain = {
  leftNavbarIsCollapsed: false,
  collapseWorkspaceDetail: true,
  sliceValidator: true,
  loading: false,
  wsId: null,
  year: null,
  openedWs: null,
  openedTabs: [],
};

@State<WorkspaceMain>({
  name: 'workspaceMain',
  defaults: initiaState
})
export class WorkspaceMainState implements NgxsOnInit {
  ctx = null;

  constructor() {

  }

  ngxsOnInit(ctx?: StateContext<WorkspaceMainState>): void | any {
    this.ctx = ctx;
  }

  /**
   * Selectors
   */
  @Selector()
  static getWorkspaceMainState(state: WorkspaceMain) {
    return state;
  }

  /**
   * Commands
   */
  @Action(PatchWorkspaceMainStateAction)
  patchWorkspaceMainState(ctx: StateContext<WorkspaceMainState>, {payload}: PatchWorkspaceMainStateAction) {
    if (_.isArray(payload))
      payload.forEach(item => ctx.patchState({[item.key]: item.value}));
    else
      ctx.patchState({[payload.key]: payload.value});
  }

  @Action(OpenWorkspaceMainAction)
  OpenWorkspace(ctx: StateContext<WorkspaceMain>, {payload}: OpenWorkspaceMainAction) {
    const state = ctx.getState();
    ctx.patchState({
      openedWs: payload,
      openedTabs: [...state.openedTabs, payload]
    });
  }

  @Action(CloseWorkspaceMainAction)
  CloseWorkspace(ctx: StateContext<WorkspaceMain>, {payload}: CloseWorkspaceMainAction) {
    const state = ctx.getState();
    ctx.patchState(
      {openedTabs: state.openedTabs.filter(dt => dt !== payload)}
    );
  }

}
