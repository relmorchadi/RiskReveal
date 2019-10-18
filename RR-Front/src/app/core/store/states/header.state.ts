import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import {HeaderStateModel} from '../../model/header-state.model';
import * as fromHeader from '../actions/header.action';
import produce from 'immer';
import * as _ from 'lodash';

import {DataTables} from '../../../shared/data/job-data-tables';
import {DataTableNotif} from '../../../shared/data/notification-data-tables';

const initialState: HeaderStateModel = {
  workspacePopIn: {
    recent: {
      keyword: '',
      items: [],
      pagination: 10
    },
    favorite: {
      keyword: '',
      items: [],
      pagination: 10
    },
    assigned: {
      keyword: '',
      items: [],
      pagination: 10
    },
    pinned: {
      keyword: '',
      items: [],
      pagination: 10
    }
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

  /**
   * Header State Selectors
   */
  @Selector()
  static getFavorite(state: HeaderStateModel) {
    return state.workspacePopIn.favorite.items;
  }

  @Selector()
  static getRecent(state: HeaderStateModel) {
    return state.workspacePopIn.recent.items;
  }

  @Selector()
  static getPinned(state: HeaderStateModel) {
    return state.workspacePopIn.pinned.items;
  }

  @Selector()
  static getJobs(state: HeaderStateModel) {
    return state.jobManagerPopIn.active.items;
  }

  @Selector()
  static getNotif(state: HeaderStateModel) {
    return state.notificationPopIn.all.items;
  }

  /**
   * Header State Commands
   */
  @Action(fromHeader.AddWsToRecent)
  addWsToRecent(ctx: StateContext<HeaderStateModel>, {payload}: fromHeader.AddWsToRecent) {
    ctx.patchState(produce(ctx.getState(), draft => {
      _.set(draft, 'workspacePopIn.recent.items', this._addItem(draft, 'workspacePopIn.recent.items', payload));
      draft.workspacePopIn.recent.items.forEach(dt => dt.selected = false);
      draft.workspacePopIn.recent.items[0].selected = true;
    }));
  }


  @Action(fromHeader.AddWsToFavorite)
  addWsToFavorite(ctx: StateContext<HeaderStateModel>, {payload}: fromHeader.AddWsToFavorite) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.workspacePopIn.favorite.items = this._addItem(draft, 'workspacePopIn.favorite.items', payload);
      // _.set(draft, 'workspacePopIn.favorite.items', );
    }));
  }

  @Action(fromHeader.DeleteWsFromFavorite)
  deleteWsFromFavorite(ctx: StateContext<HeaderStateModel>, {payload}: fromHeader.DeleteWsFromFavorite) {
    // @TODO delete workspace
    ctx.patchState(produce(ctx.getState(), draft => {
      _.set(draft, 'workspacePopIn.favorite.items',
        _.get(draft, 'workspacePopIn.favorite.items', []).filter(item => item.uwYear == payload.uwYear && item.wsId == payload.wsId));
    }));
  }


  @Action(fromHeader.PinWs)
  pinWs(ctx: StateContext<HeaderStateModel>, {payload}: fromHeader.PinWs) {
    ctx.patchState(produce(ctx.getState(), draft => {
      _.set(draft, 'workspacePopIn.pinned.items', this._addItem(draft, 'workspacePopIn.pinned.items', payload));
    }));
  }

  @Action(fromHeader.UnPinWs)
  unPinWs(ctx: StateContext<HeaderStateModel>, {payload}: fromHeader.UnPinWs) {
    ctx.patchState(produce(ctx.getState(), draft => {
      _.set(draft, 'workspacePopIn.pinned.items',
        _.get(draft, 'workspacePopIn.pinned.items', []).filter(item => item.uwYear == payload.uwYear && item.wsId == payload.wsId));
    }));
  }

  /**
   *  Header State Commands: Selection Behavior
   */
  @Action(fromHeader.ToggleWsSelection)
  toggleRecentWsSelection(ctx: StateContext<HeaderStateModel>, {payload}: fromHeader.ToggleWsSelection) {
    ctx.patchState(produce(ctx.getState(), draft => {
      const {context, index} = payload;
      const item = _.get(draft, context).items[index];
      _.get(draft, context).items[index] = {...item, selected: !item.selected};
    }));
  }

  @Action(fromHeader.ChangeWsSelection)
  changeRecentWsSelection(ctx: StateContext<HeaderStateModel>, {payload}: fromHeader.ChangeWsSelection) {
    ctx.patchState(produce(ctx.getState(), draft => {
      const {context, index} = payload;
      const item = _.get(draft, context).items[index];
      _.get(draft, context).items[index] = {...item, selected: payload.value};
    }));
  }

  @Action(fromHeader.ApplySelectionToAll)
  applySelectionToAll(ctx: StateContext<HeaderStateModel>, {payload}: fromHeader.ApplySelectionToAll) {
    let {context, value} = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      let items = _.get(draft, context).items;
      _.get(draft, context).items = items.map(item => ({...item, selected: value}));
    }));
  }

  @Action(fromHeader.SelectRange)
  selectRange(ctx: StateContext<HeaderStateModel>, {payload}: fromHeader.SelectRange) {
    let {context, from, to} = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      _.get(draft, context).items = _.get(draft, context).items.map(item => ({...item, selected: false}));
      if (from == to) {
        _.get(draft, context).items[from].selected = true;
      } else {
        _.range(from, to + 1).forEach(index => {
          _.get(draft, context).items[index].selected = true;
        });
      }
    }));
  }

  /**
   * Header Task Action
   */

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

  /**
   * Header Notification Action
   */

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
