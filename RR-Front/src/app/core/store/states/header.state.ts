import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import {HeaderStateModel} from '../../model/header-state.model';
import * as fromHeader from '../actions/header.action';
import produce from 'immer';
import * as _ from 'lodash';

import {DataTables} from '../../../shared/data/job-data-tables';
import {DataTableNotif} from '../../../shared/data/notification-data-tables';
import * as fromHD from '../actions';
import {catchError, map, mergeMap} from 'rxjs/operators';
import {EMPTY, of} from 'rxjs';
import {WorkspaceHeaderApi} from '../../service/api/workspace-header.api';
import {WorkspaceModel} from "../../../workspace/model";
import * as fromWs from "../../../workspace/store/actions";

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
    const {offset, size, userId, option} = payload;
    return this.wsHeaderApi.getRecent(offset, size, userId).pipe(
      mergeMap(wsData => {
        return of(ctx.patchState(produce(ctx.getState(), draft => {
          if (option === 'append') {
            draft.workspaceHeader.recent.data = [...draft.workspaceHeader.recent.data,
              ..._.map(wsData, item => ({...item, selected: false}))];
          } else {
            draft.workspaceHeader.recent.data = _.map(wsData, (item, key: any) => ({...item, selected: key === 0}));
          }
        })));
      })
    );
  }

  @Action(fromHD.LoadFavoriteWorkspace)
  getFavoriteWorkspaces(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.LoadFavoriteWorkspace) {
    const state = ctx.getState();
    const {offset, size, userId, option} = payload;
    return this.wsHeaderApi.getFavorited(offset, size, userId).pipe(
      mergeMap(wsData => {
        return of(ctx.patchState(produce(ctx.getState(), draft => {
          if (option === 'append') {
            draft.workspaceHeader.favorite.data = [...draft.workspaceHeader.favorite.data,
              ..._.map(wsData, item => ({...item, selected: false}))];
          } else {
            draft.workspaceHeader.favorite.data = _.map(wsData, (item, key: any) => ({...item, selected: key === 0}));
          }
        })));
      })
    );
  }

  @Action(fromHD.LoadAssignedWorkspace)
  getAssignedWorkspaces(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.LoadAssignedWorkspace) {
    const state = ctx.getState();
    const {offset, size, userId, option} = payload;
    return this.wsHeaderApi.getAssigned(offset, size, userId).pipe(
      mergeMap(wsData => {
        return of(ctx.patchState(produce(ctx.getState(), draft => {
          if (option === 'append') {
            draft.workspaceHeader.assigned.data = [...draft.workspaceHeader.assigned.data,
              ..._.map(wsData, item => ({...item, selected: false}))];
          } else {
            draft.workspaceHeader.assigned.data = _.map(wsData, (item, key: any) => ({...item, selected: key === 0}));
          }
        })));
      })
    );
  }

  @Action(fromHD.LoadPinnedWorkspace)
  getPinnedWorkspaces(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.LoadPinnedWorkspace) {
    const state = ctx.getState();
    const {offset, size, userId, option} = payload;
    return this.wsHeaderApi.getPinned(offset, size, userId).pipe(
      mergeMap(wsData => {
        return of(ctx.patchState(produce(ctx.getState(), draft => {
          if (option === 'append') {
            draft.workspaceHeader.pinned.data = [...draft.workspaceHeader.pinned.data,
              ..._.map(wsData, item => ({...item, selected: false}))
            ];
          } else {
            draft.workspaceHeader.pinned.data = _.map(wsData, (item, key: any) => ({...item, selected: key === 0}));
          }
        })));
      })
    );
  }

  @Action(fromHD.SearchRecentWsAction)
  searchRecentWs(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.SearchRecentWsAction) {
    const state = ctx.getState();
    const {offset, size, userId, search} = payload;
    return this.wsHeaderApi.getRecent(offset, size, userId, search).pipe(
      mergeMap(wsData => {
        return of(ctx.patchState(produce(ctx.getState(), draft => {
          const selectedData = _.filter(draft.workspaceHeader.recent.data, items => items.selected);
          draft.workspaceHeader.recent.data = _.map(wsData, (item, key: any) => ({...item, selected: key === 0}));
        })));
      })
    );
  }

  @Action(fromHD.SearchFavoriteWsAction)
  searchFavoriteWs(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.SearchFavoriteWsAction) {
    const state = ctx.getState();
    const {offset, size, userId, search} = payload;
    return this.wsHeaderApi.getFavorited(offset, size, userId, search).pipe(
      mergeMap(wsData => {
        return of(ctx.patchState(produce(ctx.getState(), draft => {
          draft.workspaceHeader.favorite.data = _.map(wsData, (item, key: any) => ({...item, selected: key === 0}));
        })));
      })
    );
  }

  @Action(fromHD.SearchAssignedWsAction)
  searchAssignedWs(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.SearchAssignedWsAction) {
    const state = ctx.getState();
    const {offset, size, userId, search} = payload;
    return this.wsHeaderApi.getAssigned(offset, size, userId, search).pipe(
      mergeMap(wsData => {
        return of(ctx.patchState(produce(ctx.getState(), draft => {
          draft.workspaceHeader.assigned.data = _.map(wsData, (item, key: any) => ({...item, selected: key === 0}));
        })));
      })
    );
  }

  @Action(fromHD.SearchPinnedWsAction)
  searchPinnedWs(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.SearchPinnedWsAction) {
    const state = ctx.getState();
    const {offset, size, userId, search} = payload;
    return this.wsHeaderApi.getPinned(offset, size, userId, search).pipe(
      mergeMap(wsData => {
        return of(ctx.patchState(produce(ctx.getState(), draft => {
          draft.workspaceHeader.pinned.data = _.map(wsData, (item, key: any) => ({...item, selected: key === 0}));
        })));
      })
    );
  }

  @Action(fromHD.ToggleFavoriteWsState)
  toggleStateFavoriteWorkspace(ctx: StateContext<WorkspaceModel>, {payload}: fromHD.ToggleFavoriteWsState) {
    const state = ctx.getState();
    console.log(state);
    return this.wsHeaderApi.toggleFavorite(payload).pipe(
      map(prj => ctx.dispatch(new fromWs.ToggleFavorite())),
      catchError(err => {
        console.log(err);
        return EMPTY;
      })
    );
  }

  @Action(fromHD.TogglePinnedWsState)
  toggleStatePinnedWorkspace(ctx: StateContext<WorkspaceModel>, {payload}: fromHD.TogglePinnedWsState) {
    return this.wsHeaderApi.togglePinned(payload).pipe(
      map(ws => ctx.dispatch(new fromWs.TogglePinned())), catchError(err => {
        return EMPTY;
      })
    );
  }

  @Action(fromHD.ToggleRecentWsSelection)
  toggleSelectionRecentWorkspace(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.ToggleRecentWsSelection) {
    const {item, selection, value} = payload;
    if (selection === 'single') {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.workspaceHeader.recent.data = _.map(draft.workspaceHeader.recent.data, itemR => {
          if (itemR.workspaceContextCode === item.workspaceContextCode && itemR.workspaceUwYear === item.workspaceUwYear) {
            return {...itemR, selected: value};
          } else {return {...itemR};
          }
        });
        draft.workspaceHeader.recent.pageable.selected = _.filter(draft.workspaceHeader.recent.data, items => items.selected);
      }));
    } else if (selection === 'all') {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.workspaceHeader.recent.data = _.map(draft.workspaceHeader.recent.data, itemR => ({...itemR, selected: value}));
        draft.workspaceHeader.recent.pageable.selected = _.filter(draft.workspaceHeader.recent.data, items => items.selected);
      }));
    } else if (selection === 'chunk') {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.workspaceHeader.recent.data = _.map(draft.workspaceHeader.recent.data, itemR => {
          const workspaceCode = _.map(item, ws => ws.workspaceContextCode);
          const wsUwYear = _.map(item, ws => ws.workspaceUwYear);
          if (_.includes(workspaceCode, itemR.workspaceContextCode) && _.includes(wsUwYear, itemR.workspaceUwYear)) {
            return {...itemR, selected: true};
          } else {
            return {...itemR, selected: false};
          }
        });
        draft.workspaceHeader.recent.pageable.selected = _.filter(draft.workspaceHeader.recent.data, items => items.selected);
      }));
    }
  }

  @Action(fromHD.ToggleFavoriteWsSelection)
  toggleSelectionFavoriteWorkspace(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.ToggleFavoriteWsSelection) {
    const {item, selection, value} = payload;
    if (selection === 'single') {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.workspaceHeader.favorite.data = _.map(draft.workspaceHeader.favorite.data, itemR => {
          if (itemR.workspaceContextCode === item.workspaceContextCode && itemR.workspaceUwYear === item.workspaceUwYear) {
            return {...itemR, selected: value};
          } else {return {...itemR};
          }
        });
        draft.workspaceHeader.favorite.pageable.selected = _.filter(draft.workspaceHeader.favorite.data, items => items.selected);
      }));
    } else if (selection === 'all') {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.workspaceHeader.favorite.data = _.map(draft.workspaceHeader.favorite.data, itemR => ({...itemR, selected: value}));
        draft.workspaceHeader.favorite.pageable.selected = _.filter(draft.workspaceHeader.favorite.data, items => items.selected);
      }));
    } else if (selection === 'chunk') {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.workspaceHeader.favorite.data = _.map(draft.workspaceHeader.favorite.data, itemR => {
          const workspaceCode = _.map(item, ws => ws.workspaceContextCode);
          const wsUwYear = _.map(item, ws => ws.workspaceUwYear);
          if (_.includes(workspaceCode, itemR.workspaceContextCode) && _.includes(wsUwYear, itemR.workspaceUwYear)) {
            return {...itemR, selected: true};
          } else {
            return {...itemR, selected: false};
          }
        });
        draft.workspaceHeader.favorite.pageable.selected = _.filter(draft.workspaceHeader.favorite.data, items => items.selected);
      }));
    }
  }

  @Action(fromHD.ToggleAssignedWsSelection)
  toggleSelectionAssignedWorkspace(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.ToggleAssignedWsSelection) {
    const {item, selection, value} = payload;
    if (selection === 'single') {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.workspaceHeader.assigned.data = _.map(draft.workspaceHeader.assigned.data, itemR => {
          if (itemR.workspaceContextCode === item.workspaceContextCode && itemR.workspaceUwYear === item.workspaceUwYear) {
            return {...itemR, selected: value};
          } else {return {...itemR};
          }
        });
        draft.workspaceHeader.assigned.pageable.selected = _.filter(draft.workspaceHeader.assigned.data, items => items.selected);
      }));
    } else if (selection === 'all') {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.workspaceHeader.assigned.data = _.map(draft.workspaceHeader.assigned.data, itemR => ({...itemR, selected: value}));
        draft.workspaceHeader.assigned.pageable.selected = _.filter(draft.workspaceHeader.assigned.data, items => items.selected);
      }));
    } else if (selection === 'chunk') {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.workspaceHeader.assigned.data = _.map(draft.workspaceHeader.assigned.data, itemR => {
          const workspaceCode = _.map(item, ws => ws.workspaceContextCode);
          const wsUwYear = _.map(item, ws => ws.workspaceUwYear);
          if (_.includes(workspaceCode, itemR.workspaceContextCode) && _.includes(wsUwYear, itemR.workspaceUwYear)) {
            return {...itemR, selected: true};
          } else {
            return {...itemR, selected: false};
          }
        });
        draft.workspaceHeader.assigned.pageable.selected = _.filter(draft.workspaceHeader.assigned.data, items => items.selected);
      }));
    }
  }

  @Action(fromHD.TogglePinnedWsSelection)
  toggleSelectionPinnedWorkspace(ctx: StateContext<HeaderStateModel>, {payload}: fromHD.TogglePinnedWsSelection) {
    const {item, selection, value} = payload;
    if (selection === 'single') {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.workspaceHeader.pinned.data = _.map(draft.workspaceHeader.pinned.data, itemR => {
          if (itemR.workspaceContextCode === item.workspaceContextCode && itemR.workspaceUwYear === item.workspaceUwYear) {
            return {...itemR, selected: value};
          } else {return {...itemR};
          }
        });
        draft.workspaceHeader.pinned.pageable.selected = _.filter(draft.workspaceHeader.pinned.data, items => items.selected);
      }));
    } else if (selection === 'all') {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.workspaceHeader.pinned.data = _.map(draft.workspaceHeader.pinned.data, itemR => ({...itemR, selected: value}));
        draft.workspaceHeader.pinned.pageable.selected = _.filter(draft.workspaceHeader.pinned.data, items => items.selected);
      }));
    } else if (selection === 'chunk') {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.workspaceHeader.pinned.data = _.map(draft.workspaceHeader.pinned.data, itemR => {
          const workspaceCode = _.map(item, ws => ws.workspaceContextCode);
          const wsUwYear = _.map(item, ws => ws.workspaceUwYear);
          if (_.includes(workspaceCode, itemR.workspaceContextCode) && _.includes(wsUwYear, itemR.workspaceUwYear)) {
            return {...itemR, selected: true};
          } else {
            return {...itemR, selected: false};
          }
        });
        draft.workspaceHeader.pinned.pageable.selected = _.filter(draft.workspaceHeader.pinned.data, items => items.selected);
      }));
    }
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
