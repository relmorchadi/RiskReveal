import {Action, createSelector, NgxsOnInit, Select, Selector, State, StateContext, Store} from '@ngxs/store';
import * as _ from 'lodash';
import {WorkspaceModel} from "../../model";
import * as fromWS from '../actions'
import {WsApi} from '../../services/workspace.api';
import {catchError, mergeMap, take, takeUntil} from 'rxjs/operators';
import {Navigate} from '@ngxs/router-plugin';
import produce from "immer";
import * as fromHeader from "../../../core/store/actions/header.action";
import {HeaderState} from "../../../core/store/states/header.state";

const initialState: WorkspaceModel = {
  content: {},
  currentTab: {
    index: 0,
    wsIdentifier: null,
  },
  favorite: [],
  pinned: [],
  routing: '',
  loading: false
};

@State<WorkspaceModel>({
  name: 'workspace',
  defaults: initialState
})
export class WorkspaceState {

  constructor(private wsApi: WsApi, private store: Store) {
  }


  //Selectors
  @Selector()
  static getWorkspaces(state: WorkspaceModel) {
    return state.content;
  }

  @Selector()
  static getCurrentTab(state: WorkspaceModel) {
    return state.currentTab;
  }

  @Selector()
  static getLoading(state: WorkspaceModel) {
    return state.loading;
  }

  @Selector()
  static getRecentWs(state: WorkspaceModel) {
    return _.values(state.content).map(item => ({...item, selected: false}));
  }

  @Selector()
  static getFavorite(state: WorkspaceModel) {
    return state.favorite;
  }

  @Selector()
  static getPinned(state: WorkspaceModel) {
    return state.pinned;
  }

  //Actions
  @Action(fromWS.loadWS)
  loadWs(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.loadWS) {
    const {
      wsId,
      uwYear,
      route
    } = payload;

    ctx.patchState({
      loading: true
    });

    return this.wsApi.searchWorkspace(wsId, uwYear)
      .pipe(
        mergeMap(ws => ctx.dispatch(new fromWS.loadWsSuccess({
          wsId,
          uwYear,
          ws,
          route
        }))),
        catchError(e => ctx.dispatch(new fromWS.loadWsFail()))
      )
  }

  @Action(fromWS.loadWsSuccess)
  loadWsSuccess(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.loadWsSuccess) {
    const {wsId, uwYear, ws, route} = payload;
    const {workspaceName, programName, cedantName} = ws;
    const wsIdentifier = `${wsId}-${uwYear}`;
    ctx.dispatch(new fromHeader.AddWsToRecent({wsId, uwYear, workspaceName, programName, cedantName}));
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content = _.merge(draft.content, {
        [wsIdentifier]: {
          wsId,
          uwYear, ...ws,
          collapseWorkspaceDetail: true,
          route,
          leftNavbarCollapsed: false,
          isFavorite: false
        }
      });
      draft.loading = false;
      ctx.dispatch(new fromWS.setCurrentTab({
        index: _.size(draft.content),
        wsIdentifier
      }));
    }));
  }

  @Action(fromWS.openWS)
  openWorkspace(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.openWS) {
    const {wsId, uwYear, route} = payload;
    const state = ctx.getState();
    const wsIdentifier = wsId + '-' + uwYear;
    console.log('open ws handler', payload);

    if (state.content[wsIdentifier]) {
      this.updateWsRouting(ctx, {wsId: wsIdentifier, route})
      return ctx.dispatch(new fromWS.setCurrentTab({
        index: _.findIndex(_.toArray(state.content), ws => ws.wsId == wsId && ws.uwYear == uwYear),
        wsIdentifier
      }));
    } else {
      return ctx.dispatch(new fromWS.loadWS({
        wsId,
        uwYear,
        route
      }));
    }
  }

  @Action(fromWS.openMultiWS)
  openMultipleWorkspaces(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.openMultiWS) {
    _.forEach(payload, item => {
      const {wsId, uwYear} = item;
      const state = ctx.getState();
      const wsIdentifier = wsId + '-' + uwYear;
      if (state.content[wsIdentifier]) {
        return ctx.dispatch(new fromWS.setCurrentTab({
          index: _.findIndex(_.toArray(state.content), ws => ws.wsId == wsId && ws.uwYear == uwYear),
          wsIdentifier
        }));
      } else {
        return ctx.dispatch(new fromWS.loadWS({
          wsId,
          uwYear
        }));
      }
    });
  }

  @Action(fromWS.setCurrentTab)
  setCurrentTab(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.setCurrentTab) {
    const {
      index,
      wsIdentifier,
    } = payload;
    return ctx.patchState(produce(ctx.getState(), draft => {
      const ws = draft.content[wsIdentifier];
      const {route} = ws;
      console.log('TOTO', route);
      draft.currentTab = {...draft.currentTab, index, wsIdentifier};
      draft.content[wsIdentifier] = {...ws, isFavorite: this._isFavorite(ws), isPinned: this._isPinned(ws)}
      ctx.dispatch(new Navigate([`workspace/${_.replace(wsIdentifier, '-', '/')}${route ? '/' + route : '/projects'}`]))
    }));
  }

  @Action(fromWS.closeWS)
  closeWorkspace(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.closeWS) {
    const {
      wsIdentifier
    } = payload;

    const {
      currentTab,
      content
    } = ctx.getState();

    // To keep at least one tab open
    if (_.size(content) == 1)
      return;

    if (wsIdentifier == currentTab.wsIdentifier) {
      if (currentTab.index === _.size(content) - 1) {
        ctx.dispatch(new fromWS.setCurrentTab({
          index: currentTab.index - 1,
          wsIdentifier: _.keys(content)[currentTab.index - 1]
        }))
      } else {
        ctx.dispatch(new fromWS.setCurrentTab({
          index: currentTab.index,
          wsIdentifier: _.keys(content)[currentTab.index + 1]
        }))
      }
    } else {
      let i = _.findIndex(_.toArray(content), ws => ws.wsId + '-' + ws.uwYear == wsIdentifier)
      if (currentTab.index > i) {
        ctx.dispatch(new fromWS.setCurrentTab({
          index: currentTab.index - 1,
          wsIdentifier: currentTab.wsIdentifier
        }))
      }
    }
    ctx.patchState({
      content: _.keyBy(_.filter(content, ws => ws.wsId + '-' + ws.uwYear != wsIdentifier), (el) => el.wsId + '-' + el.uwYear)
    })
  }

  @Action(fromWS.ToggleWsDetails)
  toggleWsDetails(ctx: StateContext<WorkspaceModel>, {wsId}: fromWS.ToggleWsDetails) {
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsId].collapseWorkspaceDetail = !draft.content[wsId].collapseWorkspaceDetail;
    }));
  }

  @Action(fromWS.ToggleWsLeftMenu)
  toggleWsLeftMenu(ctx: StateContext<WorkspaceModel>, {wsId}: fromWS.ToggleWsLeftMenu) {
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsId].leftNavbarCollapsed = !draft.content[wsId].leftNavbarCollapsed;
    }));
  }

  @Action(fromWS.UpdateWsRouting)
  updateWsRouting(ctx: StateContext<WorkspaceModel>, {wsId, route}: fromWS.UpdateWsRouting) {
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsId].route = route;
    }));
  }

  @Action(fromWS.MarkWsAsFavorite)
  markWsAsFavorite(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.MarkWsAsFavorite) {
    const {wsIdentifier} = payload;
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier] = {...draft.content[wsIdentifier], isFavorite: true};
    }))
  }

  @Action(fromWS.MarkWsAsNonFavorite)
  markWsAsNonFavorite(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.MarkWsAsNonFavorite) {
    const {wsIdentifier} = payload;
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier] = {...draft.content[wsIdentifier], isFavorite: false};
    }))
  }

  @Action(fromWS.MarkWsAsPinned)
  markWsAsPinned(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.MarkWsAsPinned) {
    const {wsIdentifier} = payload;
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier] = {...draft.content[wsIdentifier], isPinned: true};
    }))
  }

  @Action(fromWS.MarkWsAsNonPinned)
  markWsAsNonPinned(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.MarkWsAsNonPinned) {
    const {wsIdentifier} = payload;
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier] = {...draft.content[wsIdentifier], isPinned: false};
    }))
  }

  private _isFavorite({wsId, uwYear}): boolean {
    const favoriteWs = this.store.selectSnapshot(HeaderState.getFavorite);
    return _.find(favoriteWs, item => item.wsId == wsId && item.uwYear == uwYear);
  }

  private _isPinned({wsId, uwYear}): boolean {
    const pinnedWs = this.store.selectSnapshot(HeaderState.getPinned);
    return _.find(pinnedWs, item => item.wsId == wsId && item.uwYear == uwYear);
  }

}
