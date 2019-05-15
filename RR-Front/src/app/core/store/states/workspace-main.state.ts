import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import {Observable} from 'rxjs';

import {WorkspaceMain} from '../../../core/model/workspace-main';
import {
  CloseWorkspaceMainAction, LoadWorkspacesAction, OpenNewWorkspacesAction,
  AppendNewWorkspaceMainAction,
  PatchWorkspaceMainStateAction, SelectWorkspaceAction,
} from '../actions/workspace-main.action';
import * as _ from 'lodash';

const initiaState: WorkspaceMain = {
  leftNavbarIsCollapsed: false,
  collapseWorkspaceDetail: true,
  sliceValidator: true,
  loading: false,
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

  @Action(AppendNewWorkspaceMainAction)
  AppendNewWorkspaces(ctx: StateContext<WorkspaceMain>, {payload}: AppendNewWorkspaceMainAction) {
    const state = ctx.getState();
    let recentlyOpenedWs = _.filter(state.recentWs, ws => {
      if (ws.workSpaceId === payload.workSpaceId && ws.uwYear == payload.uwYear) {return null; } else {return ws; }
    });
    recentlyOpenedWs.unshift(payload);
    recentlyOpenedWs = recentlyOpenedWs.map(ws => _.merge({}, ws, {selected: false}));
    recentlyOpenedWs[0].selected = true;
    const openedWs = [...state.openedTabs, _.merge({}, payload, {routing: ''})];
    ctx.patchState({
      openedWs: payload,
      openedTabs: _.uniqWith(openedWs, (x, y) => (x.workSpaceId === y.workSpaceId && x.uwYear == y.uwYear)),
      recentWs: _.uniqWith(recentlyOpenedWs, _.isEqual )
    });
  }

  @Action(OpenNewWorkspacesAction)
  OpenNewWorkspaces(ctx: StateContext<WorkspaceMain>, {payload}: OpenNewWorkspacesAction) {
    const state = ctx.getState();
    let recentlyOpenedWs = [...state.recentWs];
    let openedWs = [];
    payload.forEach( data => {
        recentlyOpenedWs =  _.filter(recentlyOpenedWs, ws => {
          if (ws.workSpaceId === data.workSpaceId && ws.uwYear == data.uwYear) {return null; } else {return ws; }
        });
        openedWs = [...openedWs, _.merge({}, data, {routing: ''})];
    });
    recentlyOpenedWs.unshift(...payload);
    recentlyOpenedWs = recentlyOpenedWs.map(ws => _.merge({}, ws, {selected: false}));
    recentlyOpenedWs[0].selected = true;
    ctx.patchState({
      openedWs: _.merge({}, payload[payload.length - 1], {routing: ''}),
      openedTabs: _.uniqWith(openedWs, _.isEqual),
      recentWs: _.uniqWith(recentlyOpenedWs, _.isEqual )
    });
  }

  @Action(SelectWorkspaceAction)
  SelectWorkspace(ctx: StateContext<WorkspaceMain>, {payload}: SelectWorkspaceAction) {
    const state = ctx.getState();
    const newState = state.openedTabs.map(ws => {
      if (ws.workSpaceId === payload.workSpaceId && ws.uwYear == payload.uwYear) { return payload; } else { return ws; }
    });
    ctx.patchState(
      {openedWs: payload, openedTabs: newState}
    );
  }

  @Action(CloseWorkspaceMainAction)
  CloseWorkspace(ctx: StateContext<WorkspaceMain>, {payload}: CloseWorkspaceMainAction) {
    const state = ctx.getState();
    let opened = null;
    if (payload.same) {
      const index = _.findIndex(state.openedTabs, ws => ws.workSpaceId === payload.workSpaceId && ws.uwYear == payload.uwYear);
      index === state.openedTabs.length - 1 ? opened = state.openedTabs[index - 1] : opened = state.openedTabs[index + 1];
    } else {
      opened = state.openedWs;
    }
    ctx.patchState(
      {openedTabs: _.filter(state.openedTabs, ws => {
          if (ws.workSpaceId === payload.workSpaceId && ws.uwYear == payload.uwYear) { return null; } else {return ws; }}),
        openedWs: opened
      }
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
