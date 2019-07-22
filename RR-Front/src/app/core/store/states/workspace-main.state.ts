import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import produce from 'immer';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import {
  AddNewProject, AddNewProjectFail, AddNewProjectSuccess,
  AppendNewWorkspaceMainAction,
  CloseWorkspaceMainAction, DeleteProject, DeleteProjectFail, DeleteProjectSuccess,
  LoadWorkspacesAction,
  OpenNewWorkspacesAction,
  PatchWorkspace,
  PatchWorkspaceMainStateAction,
  SelectProjectAction,
  setTabsIndex,
  SetWsRoutingAction,
} from '../actions/workspace-main.action';
import * as _ from 'lodash';
import {WorkspaceMainService} from '../../service/workspace-main.service';
import {catchError, tap} from "rxjs/operators";
import {EMPTY, Observable} from "rxjs";

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

  constructor(private workspaceMainService: WorkspaceMainService) {
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
  static getFavorite(state: WorkspaceMain) {
    return state.openedTabs.data.filter(dt => dt.favorite);
  }

  @Selector()
  static getPinged(state: WorkspaceMain) {
    return state.openedTabs.data.filter(dt => dt.pinged);
  }

  @Selector()
  static getData(state: WorkspaceMain) {
    return state.openedTabs.data;
  }

  @Selector()
  static getLoadingWS(state: WorkspaceMain) {
    return state.loading;
  }

  /*@Selector()
  static getCurrentWS(state: WorkspaceMain) {
    return state.openedWs;
  }*/

  @Selector()
  static getProjects(state: WorkspaceMain) {
    return state.openedWs.projects;
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
      if (ws.workSpaceId === payload.workSpaceId && ws.uwYear == payload.uwYear) {
        return null;
      } else {
        return ws;
      }
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
      const workSpaceMenuItem = JSON.parse(localStorage.getItem('workSpaceMenuItem')) || {};
      openedTabs = openedTabs.map(dt => ({
        ...dt,
        projects: dt.projects.map((prj, i) => ({...prj, selected: dt.projects.length > 0 && i == 0})),
        pinged: _.get(workSpaceMenuItem[dt.workSpaceId + '-' + dt.uwYear], 'pinged', false),
        favorite: _.get(workSpaceMenuItem[dt.workSpaceId + '-' + dt.uwYear], 'favorite', false)
      }))
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

  @Action(SetWsRoutingAction)
  selectWorkspace(ctx: StateContext<WorkspaceMain>, {payload}: SetWsRoutingAction) {
    const state = ctx.getState();

    let index = _.findIndex(state.openedTabs.data, ws => ws.workSpaceId === payload.workSpaceId && ws.uwYear == payload.uwYear)
    const projectFormat = payload.projects.map(prj => prj = {...prj, selected: false});
    if (projectFormat.length > 0) {
      projectFormat[0] = {...projectFormat[0], selected: true};
    }
    const opened = {...payload, projects: projectFormat};
    ctx.patchState({
      openedWs: opened,
      openedTabs: {
        data: _.merge([], state.openedTabs.data, {[index]: {...payload}}),
        tabsIndex: state.openedTabs.tabsIndex
      }
    });
  }

  @Action(SelectProjectAction)
  selectProjectAction(ctx: StateContext<WorkspaceMain>, {payload}: SelectProjectAction) {
    const state = ctx.getState();
    const {
      projectId,
      projectIndex
    } = payload;

    return ctx.patchState({
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
        openedTabs: {
          data: _.filter(state.openedTabs.data, ws => {
            if (ws.workSpaceId === payload.workSpaceId && ws.uwYear == payload.uwYear) {
              return null;
            } else {
              return ws;
            }
          }), tabsIndex: state.openedTabs.tabsIndex
        },
        openedWs: opened
      }
    );
  }

  @Action(PatchWorkspace)
  patchWorkspace(ctx: StateContext<WorkspaceMain>, {payload}: PatchWorkspace) {
    const {
      ws,
      key,
      value,
      k
    } = payload;

    const {
      openedTabs
    } = ctx.getState()

    console.log(ws);

    let attrs = {};

    _.forEach(key, (v, i) => {
      attrs[v] = value[i]
    })

    const index = k ? k : _.findIndex(openedTabs.data, wS => wS.workSpaceId == ws.workSpaceId && wS.uwYear == ws.uwYear);
    console.log(index);

    ctx.patchState({
      openedTabs: {
        ...openedTabs,
        data: _.merge([],
          openedTabs.data,
          {[index]: {...ws, ...attrs}})
      }
    })

  }

  @Action(LoadWorkspacesAction)
  loadWorkspaces(ctx: StateContext<WorkspaceMain>) {
    const state = ctx.getState();
    const recentlyOpenedWs = (JSON.parse(localStorage.getItem('usedWorkspaces')) || []);
    const currentOpenedWs = (JSON.parse(localStorage.getItem('workspaces')) || {data: []});
    const paginationList = this.makePagination(recentlyOpenedWs);
    const projects = _.get(currentOpenedWs.data[state.openedTabs.tabsIndex], 'projects', [])
    ctx.patchState({
      workspacePagination: paginationList,
      openedWs: {
        ...currentOpenedWs.data[state.openedTabs.tabsIndex],
        projects: projects.map((prj, i) => ({...prj, selected: projects.length > 0 && i == 0}))
      },
      openedTabs: {
        data: currentOpenedWs.data.map(ws => {
          const wsFromLocal = recentlyOpenedWs.find(item => item.workspaceId == ws.workSpaceId && item.uwYear == ws.uwYear); // JSON.parse(localStorage.getItem('workSpaceMenuItem'))[ws.workSpaceId+'-'+ws.uwYear]
          return ({
            ...ws,
            favorite: _.get(wsFromLocal, 'favorite', false),
            lastFModified: _.get(wsFromLocal, 'lastFModified', null),
            lastPModified: _.get(wsFromLocal, 'lastPModified', null),
            pinged: _.get(wsFromLocal, 'pinged', false)
          });
        }), tabsIndex: state.openedTabs.tabsIndex
      },
      recentWs: recentlyOpenedWs
    });
  }

  @Action(AddNewProject)
  addNewProject(ctx: StateContext<WorkspaceMain>, {payload}: any) {
    return this.workspaceMainService.addNewProject(payload.project, payload.workspaceId, payload.uwYear, payload.id)
      .pipe(catchError(err => {
        ctx.dispatch(new AddNewProjectFail({}));
        return EMPTY;
      }))
      .subscribe((result) => {
        const state = ctx.getState();
        if (result) {
          const i = _.findIndex(state.openedTabs.data, {'workSpaceId': payload.workspaceId, 'uwYear': payload.uwYear});
          ctx.setState(
            produce((draft) => {
              draft.openedTabs.data[i].projects.push(result);
              draft.openedWs.projects.push(result);
            }));
        }
        ctx.dispatch(new AddNewProjectSuccess(result));
      });
  }

  @Action(DeleteProject)
  deleteProject(ctx: StateContext<WorkspaceMain>, {payload}: any) {
    return this.workspaceMainService.deleteProject(payload.project, payload.workspaceId, payload.uwYear)
      .pipe(catchError(err => {
        ctx.dispatch(new DeleteProjectFail({}));
        return EMPTY;
      }))
      .subscribe((result) => {
        const state = ctx.getState();
        if (result) {
          const i = _.findIndex(state.openedTabs.data, {'workSpaceId': payload.workspaceId, 'uwYear': payload.uwYear});
          ctx.setState(
            produce((draft) => {
              draft.openedTabs.data[i].projects = _.filter(draft.openedTabs.data[i].projects, e => e.projectId !== result.projectId);
              draft.openedWs.projects = _.filter(draft.openedWs.projects, e => e.projectId !== result.projectId);
            }));
        }
        ctx.dispatch(new DeleteProjectSuccess(result));
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
