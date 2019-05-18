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
  appliedFilter: {shownElement: 10},
  workspacePagination: {paginationList: [], shownElement: 0, numberOfElement: 0},
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
    recentlyOpenedWs = _.uniqWith(recentlyOpenedWs, _.isEqual );
    const paginationList = this.makePagination(recentlyOpenedWs);
    const openedWs = [...state.openedTabs, _.merge({}, payload, {routing: ''})];
    ctx.patchState({
      workspacePagination: paginationList,
      openedWs: payload,
      openedTabs: _.uniqWith(openedWs, (x, y) => (x.workSpaceId === y.workSpaceId && x.uwYear == y.uwYear)),
      recentWs: recentlyOpenedWs
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
    recentlyOpenedWs = _.uniqWith(recentlyOpenedWs, _.isEqual );
    const paginationList = this.makePagination(recentlyOpenedWs);
    ctx.patchState({
      workspacePagination: paginationList,
      openedWs: _.merge({}, payload[payload.length - 1], {routing: ''}),
      openedTabs: _.uniqWith(openedWs, _.isEqual),
      recentWs: recentlyOpenedWs
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
    const recentlyOpenedWs = (JSON.parse(localStorage.getItem('usedWorkspaces')) || []);
    const currentOpenedWs = (JSON.parse(localStorage.getItem('workspaces')) || []);
    const paginationList = this.makePagination(recentlyOpenedWs)
    ctx.patchState( {
      workspacePagination: paginationList,
      openedWs: currentOpenedWs[0] || null,
      openedTabs: currentOpenedWs,
      recentWs: recentlyOpenedWs
    });
  }

   private makePagination(recentlyOpenedWs) {
    const paginationList = [];
    if (recentlyOpenedWs.length > 0 ) {
      paginationList.push({id: 0, shownElement: 10, value: 'Last 10'});
    }
    if (recentlyOpenedWs.length > 10) {
      paginationList.push({id: 1, shownElement: 50, value: 'Last 50'});
    }
    if (recentlyOpenedWs.length > 50) {
      paginationList.push({id: 2, shownElement: 100, value: 'Last 100'});
    }
    if (recentlyOpenedWs.length > 100) {
      paginationList.push({id: 3, shownElement: 150, value: 'Last 150'});
    }
    return {paginationList: paginationList, numberOfElement: recentlyOpenedWs.length};
  }

}
