import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import {HeaderStateModel} from '../../model/header-state.model';
import * as fromHeader from '../actions/header.action';
import produce from 'immer';
import * as _ from 'lodash';

import {DataTables} from '../../../shared/data/job-data-tables';
import {DataTableNotif} from '../../../shared/data/notification-data-tables';
import * as fromHD from '../actions';
import {mergeMap} from 'rxjs/operators';
import {of} from 'rxjs';
import {WorkspaceHeaderApi} from '../../service/api/workspace-header.api';

const initialState: HeaderStateModel = {
  workspaceHeader: {
    favorite: {data: {}, pageable: {}},
    assigned: {data: {}, pageable: {}},
    recent: {data: {}, pageable: {}},
    pinned: {data: {}, pageable: {}},
    statusCount: {}
  },
  jobManagerPopIn: {
    active: {
      keyword: '',
      items: _.merge({}, ...DataTables.tasks.map(dt => ({[dt.id]: {...dt}}))),
      filter: {date: '', type: ''}
    },
    completed: {
      keyword: '',
      items: _.merge({}, ...DataTables.tasks.map(dt => ({[dt.id]: {...dt}}))),
      filter: {date: '', type: ''}
    }
  },
  notificationPopIn: {
    all: {
      keyword: '',
      items: DataTableNotif.notification.all,
      filter: {}
    },
    errors: {
      keyword: '',
      items: {},
      filter: {}
    },
    warnings: {
      keyword: '',
      items: {},
      filter: {}
    },
    informational: {
      keyword: '',
      items: {},
      filter: {}
    }
  }
};

@State<HeaderStateModel>({
  name: 'headerState',
  defaults: initialState
})
export class HeaderState implements NgxsOnInit {


  ngxsOnInit(ctx?: StateContext<any>): void | any {
  }

  constructor(private wsHeaderApi: WorkspaceHeaderApi) {}

  /**
   * Header State Selectors
   */

  /***********************************
   *
   * Workspace Header Selectors
   *
   ***********************************/

  @Selector()
  static getFavoriteWs(state: HeaderStateModel) {
    return state.workspaceHeader.favorite.data;
  }

  @Selector()
  static getRecentWs(state: HeaderStateModel) {
    return state.workspaceHeader.recent.data;
  }

  @Selector()
  static getAssignedWs(state: HeaderStateModel) {
    return state.workspaceHeader.assigned.data;
  }

  @Selector()
  static getPinnedWs(state: HeaderStateModel) {
    return state.workspaceHeader.pinned.data;
  }

  @Selector()
  static getStatusCountWs(state: HeaderStateModel) {
    return state.workspaceHeader.statusCount;
  }

  /***********************************
   *
   * Job Header Selectors
   *
   ***********************************/

  @Selector()
  static getJobs(state: HeaderStateModel) {
    return state.jobManagerPopIn.active.items;
  }

  /***********************************
   *
   * Notif Header Selectors
   *
   ***********************************/

  @Selector()
  static getNotif(state: HeaderStateModel) {
    return state.notificationPopIn.all.items;
  }


  /***********************************
   *
   * Workspace Header Actions
   *
   ***********************************/

  @Action(fromHD.LoadWsStatusCount)
  getWsStatusCount(ctx: StateContext<HeaderStateModel>) {
    const state = ctx.getState();
    const userId = 1;
    return this.wsHeaderApi.getCount(userId).pipe(
      mergeMap(wsData => {
        return of(ctx.patchState(produce(ctx.getState(), draft => {
          draft.workspaceHeader.statusCount = wsData;
        })));
      })
    );
  }

  @Action(fromHD.LoadRecentWorkspace)
  getRecentWorkspaces(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.LoadRecentWorkspace) {
    const state = ctx.getState();
    const {offset, size, userId} = payload;
    return this.wsHeaderApi.getRecent(offset, size, userId).pipe(
      mergeMap(wsData => {
        console.log(wsData);
        return of(ctx.patchState(produce(ctx.getState(), draft => {
          draft.workspaceHeader.recent.data = _.map(wsData, item => ({...item, selected: false}));
        })));
      })
    );
  }

  @Action(fromHD.LoadFavoriteWorkspace)
  getFavoriteWorkspaces(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.LoadFavoriteWorkspace) {
    const state = ctx.getState();
    const {offset, size, userId} = payload;
    return this.wsHeaderApi.getFavorited(offset, size, userId).pipe(
      mergeMap(wsData => {
        return of(ctx.patchState(produce(ctx.getState(), draft => {
          draft.workspaceHeader.favorite.data = _.map(wsData, item => ({...item, selected: false}));
        })));
      })
    );
  }

  @Action(fromHD.LoadAssignedWorkspace)
  getAssignedWorkspaces(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.LoadAssignedWorkspace) {
    const state = ctx.getState();
    const {offset, size, userId} = payload;
    return this.wsHeaderApi.getAssigned(offset, size, userId).pipe(
      mergeMap(wsData => {
        return of(ctx.patchState(produce(ctx.getState(), draft => {
          draft.workspaceHeader.assigned.data = _.map(wsData, item => ({...item, selected: false}));
        })));
      })
    );
  }

  @Action(fromHD.LoadPinnedWorkspace)
  getPinnedWorkspaces(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.LoadPinnedWorkspace) {
    const state = ctx.getState();
    const {offset, size, userId} = payload;
    return this.wsHeaderApi.getPinned(offset, size, userId).pipe(
      mergeMap(wsData => {
        return of(ctx.patchState(produce(ctx.getState(), draft => {
          draft.workspaceHeader.assigned.data = _.map(wsData, item => ({...item, selected: false}));
        })));
      })
    );
  }

  @Action(fromHD.ToggleFavoriteWsState)
  toggleStateFavoriteWorkspace(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.ToggleFavoriteWsState) {
    const {offset, size, userId, data} = payload;
    this.wsHeaderApi.toggleFavorite(data);
    return this.wsHeaderApi.getFavorited(offset, size, userId).pipe(
      mergeMap(wsData => {
        return of(ctx.patchState(produce(ctx.getState(), draft => {
          draft.workspaceHeader.assigned.data = _.map(wsData, item => ({...item, selected: false}));
        })));
      })
    );
  }

  @Action(fromHD.ToggleFavoriteWsState)
  toggleStatePinnedWorkspace(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.ToggleFavoriteWsState) {
    const {offset, size, userId, data} = payload;
    this.wsHeaderApi.togglePinned(data);
    return this.wsHeaderApi.getPinned(offset, size, userId).pipe(
      mergeMap(wsData => {
        return of(ctx.patchState(produce(ctx.getState(), draft => {
          draft.workspaceHeader.assigned.data = _.map(wsData, item => ({...item, selected: false}));
        })));
      })
    );
  }

  @Action(fromHD.ToggleFavoriteWsState)
  toggleSelectionRecentWorkspace(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.ToggleFavoriteWsState) {
  }

  @Action(fromHD.ToggleFavoriteWsState)
  toggleSelectionFavoriteWorkspace(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.ToggleFavoriteWsState) {
  }

  @Action(fromHD.ToggleFavoriteWsState)
  toggleSelectionAssignedWorkspace(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.ToggleFavoriteWsState) {
  }

  @Action(fromHD.ToggleFavoriteWsState)
  toggleSelectionPinnedWorkspace(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.ToggleFavoriteWsState) {
  }


  /***********************************
   *
   * Workspace Header Actions
   *
   ***********************************/

  @Action(fromHeader.DeleteTask)
  deleteTask(ctx: StateContext<HeaderStateModel>, {payload}: fromHeader.DeleteTask) {
    const {id} = payload;
    ctx.patchState(produce(
      ctx.getState(), draft => {
        _.set(draft, 'jobManagerPopIn.active.items',
          _.get(draft, 'jobManagerPopIn.active.items', []).filter(item => item.id !== id));
      }
    ));
  }

  @Action(fromHeader.PauseTask)
  pauseTask(ctx: StateContext<HeaderStateModel>, {payload}: fromHeader.PauseTask) {
    const {id} = payload;
    const state = ctx.getState();
    console.log(
      [..._.sortBy(_.filter(_.toArray(state.jobManagerPopIn.active.items), (dt) => !dt.pending), (dt) => dt.isPaused),
        ..._.filter(_.toArray(state.jobManagerPopIn.active.items), (dt) => dt.pending)]);
    ctx.patchState(produce(
      ctx.getState(), draft => {
        draft.jobManagerPopIn.active.items[id].isPaused = true;
      }
    ));
  }

  @Action(fromHeader.ResumeTask)
  resumeTask(ctx: StateContext<HeaderStateModel>, {payload}: fromHeader.ResumeTask) {
    const {id} = payload;
    ctx.patchState(produce(
      ctx.getState(), draft => {
        draft.jobManagerPopIn.active.items[id].isPaused = false;
      }
    ));
  }

  /***********************************
   *
   * Workspace Header Actions
   *
   ***********************************/

  @Action(fromHeader.DeleteNotification)
  deleteNotification(ctx: StateContext<HeaderStateModel>, {payload}: fromHeader.DeleteNotification) {
    const {target} = payload;
    ctx.patchState(produce(
      ctx.getState(), draft => {
        if (target === 'all') {
          _.set(draft, 'notificationPopIn.all.items', []);
        } else {
          _.set(draft, 'notificationPopIn.all.items',
            _.get(draft, 'notificationPopIn.all.items', []).filter(item => item.type !== target));
        }
      }
    ));
  }

  private _addItem(state, target, value): any[] {
    return _.concat(
      this._appendMetaData(value),
      _.toArray(
        _.omitBy(
          _.get(state, target, []),
          item => item.uwYear == value.uwYear && item.wsId == value.wsId)
      ));
  }

  private _appendMetaData(item) {
    return _.merge(item, {selected: false});
  }

}
