import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import {Observable} from 'rxjs';

import {WorkspaceMain} from '../../../core/model/workspace-main';
import {
  CloseWorkspaceMainAction, LoadWorkspacesAction, OpenNewWorkspacesAction,
  AppendNewWorkspaceMainAction,
  PatchWorkspaceMainStateAction, SelectWorkspaceAction, SelectProjectAction, setTabsIndex,
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
  openedTabs: {data: [], tabsIndex: 0},
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

  @Selector()
  static getLoadingWS(state: WorkspaceMain){
    return state.loading;
  }

  @Selector()
  static getCurrentWS(state: WorkspaceMain){
    return state.openedWs;
  }

  /**
   * Commands
   */
  @Action(PatchWorkspaceMainStateAction)
  patchWorkspaceMainState(ctx: StateContext<WorkspaceMain>, {payload}: PatchWorkspaceMainStateAction) {
    if (_.isArray(payload))
      payload.forEach(item => ctx.patchState({[item.key]: item.value}));
    else
      ctx.patchState({[payload.key]: payload.value});
  }

  @Action(setTabsIndex)
  selectTabsIndex(ctx: StateContext<WorkspaceMain>, {payload}: setTabsIndex) {
    ctx.patchState({
      openedTabs: {
        ...ctx.getState().openedTabs,
        tabsIndex: payload.index
      }
    })
  }

  @Action(AppendNewWorkspaceMainAction)
  appendNewWorkspaces(ctx: StateContext<WorkspaceMain>, {payload}: AppendNewWorkspaceMainAction) {
    const state = ctx.getState();
    let recentlyOpenedWs = _.filter(state.recentWs, ws => {
      if (ws.workSpaceId === payload.workSpaceId && ws.uwYear == payload.uwYear) { return null; } else { return ws; }
    });
    recentlyOpenedWs.unshift(payload);
    recentlyOpenedWs = recentlyOpenedWs.map(ws => _.merge({}, ws, {selected: false}));
    recentlyOpenedWs[0].selected = true;
    recentlyOpenedWs = _.uniqWith(recentlyOpenedWs, _.isEqual);
    const paginationList = this.makePagination(recentlyOpenedWs);
    const projectFormat = payload.projects.map(prj => prj = {...prj, selected: false});
    if (projectFormat.length > 0) {
      projectFormat[0] = {...projectFormat[0], selected: true};
    }
    const openedTabs = [...state.openedTabs.data, _.merge({}, payload, {routing: ''})];
    const opened = {...payload, projects: projectFormat};
    ctx.patchState({
      workspacePagination: paginationList,
      openedWs: opened,
      openedTabs: {
        data: _.uniqWith(openedTabs, (x, y) => (x.workSpaceId === y.workSpaceId && x.uwYear == y.uwYear)),
        tabsIndex: state.openedTabs.data.length
      },
      recentWs: recentlyOpenedWs
    });
  }

  @Action(OpenNewWorkspacesAction)
  openNewWorkspaces(ctx: StateContext<WorkspaceMain>, {payload}: OpenNewWorkspacesAction) {
    const state = ctx.getState();
    let recentlyOpenedWs = [...state.recentWs];
    let openedTabs = [];
    let opened = _.merge({}, payload[payload.length - 1], {routing: ''});
    const projectFormat = opened.projects.map(prj => prj = {...prj, selected: false});
    projectFormat[0] = {...projectFormat[0], selected: true};
    opened = {...opened, projects: projectFormat};
    payload.forEach(data => {
      recentlyOpenedWs = _.filter(recentlyOpenedWs, ws => {
        if (ws.workSpaceId === data.workSpaceId && ws.uwYear == data.uwYear) {
          return null;
        } else {
          return ws;
        }
      });
      openedTabs = [...openedTabs, _.merge({}, data, {routing: ''})];
      openedTabs.map(dt => {
        dt.projects = dt.projects.map(prj => prj = {...prj, selected: false});
        if (dt.projects.length > 0) { dt.projects[0] = {...dt.projects[0], selected: true}; }
      });
    });
    recentlyOpenedWs.unshift(...payload);
    recentlyOpenedWs = recentlyOpenedWs.map(ws => _.merge({}, ws, {selected: false}));
    recentlyOpenedWs[0].selected = true;
    recentlyOpenedWs = _.uniqWith(recentlyOpenedWs, _.isEqual);
    const paginationList = this.makePagination(recentlyOpenedWs);
    ctx.patchState({
      workspacePagination: paginationList,
      openedWs: opened,
      openedTabs: {data: _.uniqWith(openedTabs, _.isEqual), tabsIndex: 0},
      recentWs: recentlyOpenedWs
    });
  }

  @Action(SelectWorkspaceAction)
  selectWorkspace(ctx: StateContext<WorkspaceMain>, {payload}: SelectWorkspaceAction) {
    const state = ctx.getState();
    let newState = state.openedTabs.data.map(ws => {
      if (ws.workSpaceId === payload.workSpaceId && ws.uwYear == payload.uwYear) {
        return payload;
      } else {
        return ws;
      }
    });
    const projectFormat = payload.projects.map(prj => prj = {...prj, selected: false});
    if (projectFormat.length > 0) {
      projectFormat[0] = {...projectFormat[0], selected: true};
    }
    const opened = {...payload, projects: projectFormat};
    ctx.patchState(
      {openedWs: opened, openedTabs: {data: newState, tabsIndex: state.openedTabs.tabsIndex}}
    );
  }

  @Action(SelectProjectAction)
  selectProjectAction(ctx: StateContext<WorkspaceMain>, {payload}: SelectProjectAction) {
    const state = ctx.getState();
    const {
      projectId
    } = payload;

    ctx.patchState({
      openedWs: _.merge({}, state.openedWs, {
        projects: _.map(state.openedWs.projects, pr => pr.projectId === projectId ? {
          ...pr,
          selected: !pr.selected
        } : {...pr, selected: false})
      })
    });
  }

  @Action(CloseWorkspaceMainAction)
  closeWorkspace(ctx: StateContext<WorkspaceMain>, {payload}: CloseWorkspaceMainAction) {
    const state = ctx.getState();
    let opened = null;
    if (payload.same) {
      const index = _.findIndex(state.openedTabs.data, ws => ws.workSpaceId === payload.workSpaceId && ws.uwYear == payload.uwYear);
      index === state.openedTabs.data.length - 1 ? opened = state.openedTabs.data[index - 1] : opened = state.openedTabs.data[index + 1];
    } else {
      opened = state.openedWs;
    }
    const projectFormat = opened.projects.map(prj => prj = {...prj, selected: false});
    opened = {...opened, projects: projectFormat};
    ctx.patchState(
      {
        openedTabs: { data: _.filter(state.openedTabs.data, ws => {
          if (ws.workSpaceId === payload.workSpaceId && ws.uwYear == payload.uwYear) {
            return null;
          } else {
            return ws;
          }
        }), tabsIndex: state.openedTabs.tabsIndex},
        openedWs: opened
      }
    );
  }

  @Action(LoadWorkspacesAction)
  loadWorkspaces(ctx: StateContext<WorkspaceMain>) {
    const state = ctx.getState();
    const recentlyOpenedWs = (JSON.parse(localStorage.getItem('usedWorkspaces')) || []);
    const currentOpenedWs = (JSON.parse(localStorage.getItem('workspaces')) || []);
    const paginationList = this.makePagination(recentlyOpenedWs);
    const projectFormat = currentOpenedWs.data[state.openedTabs.tabsIndex].projects.map(prj => prj = {...prj, selected: false});
    if (projectFormat.length > 0) {
      projectFormat[0] = {...projectFormat[0], selected: true};
    }
    const opened = {...currentOpenedWs.data[state.openedTabs.tabsIndex], projects: projectFormat};
    currentOpenedWs.data.map(dt => {
      dt.projects = dt.projects.map(prj => prj = {...prj, selected: false});
      if (dt.projects.length > 0) { dt.projects[0] = {...dt.projects[0], selected: true}; }
    });
    ctx.patchState({
      workspacePagination: paginationList,
      openedWs: opened,
      openedTabs: {data: currentOpenedWs.data, tabsIndex: state.openedTabs.tabsIndex},
      recentWs: recentlyOpenedWs
    });
  }

  private makePagination(recentlyOpenedWs) {
    const paginationList = [];
    if (recentlyOpenedWs.length > 0) {
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
