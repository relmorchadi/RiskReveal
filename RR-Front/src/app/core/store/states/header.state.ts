import {Action, NgxsOnInit, Selector, State, StateContext} from "@ngxs/store";
import {HeaderStateModel} from "../../model/header-state.model";
import * as fromHeader from '../actions/header.action';
import produce from "immer";
import * as _ from 'lodash';

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
      items: [],
      filter: {date: '', type: ''}
    },
    completed: {
      keyword: '',
      items: [],
      filter: {date: '', type: ''}
    }
  },
  notificationPopIn: {
    all: {
      keyword: '',
      items: {},
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
    console.log('init')
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


  /**
   * Header State Commands
   */
  @Action(fromHeader.AddWsToRecent)
  addWsToRecent(ctx: StateContext<HeaderStateModel>, {payload}: fromHeader.AddWsToRecent) {
    ctx.patchState(produce(ctx.getState(), draft => {
      _.set(draft, 'workspacePopIn.recent.items', this._addItem(draft, 'workspacePopIn.recent.items', payload));
    }));
  }


  @Action(fromHeader.AddWsToFavorite)
  addWsToFavorite(ctx: StateContext<HeaderStateModel>, {payload}: fromHeader.AddWsToFavorite) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.workspacePopIn.favorite.items=this._addItem(draft, 'workspacePopIn.favorite.items', payload);
      // _.set(draft, 'workspacePopIn.favorite.items', );
    }));
  }

  @Action(fromHeader.DeleteWsFromFavorite)
  deleteWsFromFavorite(ctx: StateContext<HeaderStateModel>, {payload}: fromHeader.DeleteWsFromFavorite) {
    //@TODO delete workspace
    ctx.patchState(produce(ctx.getState(), draft => {
      _.set(draft, 'workspacePopIn.favorite.items', _.get(draft, 'workspacePopIn.favorite.items', []).filter(item => item.uwYear == payload.uwYear && item.wsId == payload.wsId));
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
      _.set(draft, 'workspacePopIn.pinned.items', _.get(draft, 'workspacePopIn.pinned.items', []).filter(item => item.uwYear == payload.uwYear && item.wsId == payload.wsId));
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
        _.range(from, to+1).forEach(index => {
          _.get(draft, context).items[index].selected = true;
        });
      }
    }));
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
