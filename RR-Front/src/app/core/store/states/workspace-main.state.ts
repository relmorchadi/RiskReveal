import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import {WorkspaceMain} from '../../model/workspace-main';
import {Observable} from 'rxjs';
import {
  CloseWorkspaceMainAction, LoadWorkspacesAction, OpenNewWorkspacesAction,
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
  recentWs: []
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
    let recentlyOpenedWs = _.filter(state.recentWs, ws => {
      if (ws.workSpaceId === payload.workSpaceId && ws.uwYear == payload.uwYear) {return null; } else {return ws; }
    });
    recentlyOpenedWs.unshift(payload);
    recentlyOpenedWs = recentlyOpenedWs.map(ws => _.merge({}, ws, {selected: false}));
    recentlyOpenedWs[0].selected = true;
    const openedWs = _.uniqWith([...state.openedTabs, payload], _.isEqual );
    ctx.patchState({
      openedWs: payload,
      openedTabs: openedWs,
      recentWs: _.uniqWith(recentlyOpenedWs, _.isEqual )
    });
  }

  @Action(OpenNewWorkspacesAction)
  OpenNewWorkspaces(ctx: StateContext<WorkspaceMain>, {payload}: OpenNewWorkspacesAction) {
    const state = ctx.getState();
    let recentlyOpenedWs = [...state.recentWs];
    payload.forEach( data => {
        recentlyOpenedWs =  _.filter(recentlyOpenedWs, ws => {
          if (ws.workSpaceId === data.workSpaceId && ws.uwYear == data.uwYear) {return null; } else {return ws; }
        });
    });
    recentlyOpenedWs.unshift(...payload);
    recentlyOpenedWs = recentlyOpenedWs.map(ws => _.merge({}, ws, {selected: false}));
    recentlyOpenedWs[0].selected = true;
    ctx.patchState({
      openedTabs: payload,
      recentWs: _.uniqWith(recentlyOpenedWs, _.isEqual )
    });
  }

  @Action(CloseWorkspaceMainAction)
  CloseWorkspace(ctx: StateContext<WorkspaceMain>, {payload}: CloseWorkspaceMainAction) {
    const state = ctx.getState();
    ctx.patchState(
      {openedTabs: _.filter(state.openedTabs, ws => {
          if (ws.workSpaceId === payload.workSpaceId && ws.uwYear == payload.uwYear) { return null; } else {return ws; }})}
    );
  }

  @Action(LoadWorkspacesAction)
  LoadWorkspaces(ctx: StateContext<WorkspaceMain>) {
    ctx.patchState( {
      openedTabs: (JSON.parse(localStorage.getItem('workspaces')) || []),
      recentWs: (JSON.parse(localStorage.getItem('usedWorkspaces')) || [])
    });
  }

}
