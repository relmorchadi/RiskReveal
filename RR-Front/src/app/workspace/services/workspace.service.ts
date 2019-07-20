import {Injectable} from '@angular/core';
import {StateContext, Store} from "@ngxs/store";
import {WorkspaceModel} from "../model";
import * as fromWS from "../store/actions";
import {catchError, mergeMap} from "rxjs/operators";
import {WsApi} from "./workspace.api";
import * as fromHeader from "../../core/store/actions/header.action";
import produce from "immer";
import * as _ from "lodash";
import {Navigate} from "@ngxs/router-plugin";
import {HeaderState} from "../../core/store/states/header.state";

@Injectable({
  providedIn: 'root'
})
export class WorkspaceService {

  constructor(private wsApi: WsApi, private store: Store) {
  }

  loadWs(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadWS) {
    const {
      wsId,
      uwYear,
      route
    } = payload;
    ctx.patchState({loading: true});
    return this.wsApi.searchWorkspace(wsId, uwYear)
      .pipe(
        mergeMap(ws => ctx.dispatch(new fromWS.LoadWsSuccess({
          wsId,
          uwYear,
          ws,
          route
        }))),
        catchError(e => ctx.dispatch(new fromWS.LoadWsFail()))
      );
  }

  loadWsSuccess(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadWsSuccess) {
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
          isFavorite: false,
          pltManager: {
            data: {},
            filters: {
              systemTag: [], userTag: []
            },
            openedPlt: {},
            userTags: {},
            loading: false
          },
          calibration: {
            data: {},
            loading: false,
            filters: {
              systemTag: [],
              userTag: []
            },
            userTags: {},
            selectedPLT: [],
            extendPltSection: false,
            collapseTags: true,
            lastCheckedBool: false,
            firstChecked: '',
            adjustments: [],
            adjustmentApplication: {},
            adjustementType: [],
            pure: {},
            systemTags: [],
            allAdjsArray: [],
            listOfPlts: [],
            listOfDisplayPlts: [],
            pltColumns: [],
          },
          loading: false
        }
      });
      draft.loading = false;
      ctx.dispatch(new fromWS.SetCurrentTab({
        index: _.size(draft.content),
        wsIdentifier
      }));
    }));
  }

  openWorkspace(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.openWS) {
    const {wsId, uwYear, route} = payload;
    const state = ctx.getState();
    const wsIdentifier = wsId + '-' + uwYear;

    if (state.content[wsIdentifier]) {
      this.updateWsRouting(ctx, {wsId: wsIdentifier, route});
      return ctx.dispatch(new fromWS.SetCurrentTab({
        index: _.findIndex(_.toArray(state.content), ws => ws.wsId == wsId && ws.uwYear == uwYear),
        wsIdentifier
      }));
    } else {
      return ctx.dispatch(new fromWS.LoadWS({
        wsId,
        uwYear,
        route
      }));
    }
  }

  openMultipleWorkspaces(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.OpenMultiWS) {
    _.forEach(payload, item => {
      const {wsId, uwYear} = item;
      const state = ctx.getState();
      const wsIdentifier = wsId + '-' + uwYear;
      if (state.content[wsIdentifier]) {
        return ctx.dispatch(new fromWS.SetCurrentTab({
          index: _.findIndex(_.toArray(state.content), ws => ws.wsId == wsId && ws.uwYear == uwYear),
          wsIdentifier
        }));
      } else {
        return ctx.dispatch(new fromWS.LoadWS({wsId, uwYear}));
      }
    });
  }

  setCurrentTab(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.SetCurrentTab) {
    const {
      index,
      wsIdentifier,
    } = payload;
    return ctx.patchState(produce(ctx.getState(), draft => {
      const ws = draft.content[wsIdentifier];
      const {route} = ws;
      draft.currentTab = {...draft.currentTab, index, wsIdentifier};
      draft.content[wsIdentifier] = {...ws, isFavorite: this._isFavorite(ws), isPinned: this._isPinned(ws)};
      ctx.dispatch(new Navigate([`workspace/${_.replace(wsIdentifier, '-', '/')}${route ? '/' + route : '/projects'}`]))
    }));
  }

  closeWorkspace(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.CloseWS) {
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
        ctx.dispatch(new fromWS.SetCurrentTab({
          index: currentTab.index - 1,
          wsIdentifier: _.keys(content)[currentTab.index - 1]
        }));
      } else {
        ctx.dispatch(new fromWS.SetCurrentTab({
          index: currentTab.index,
          wsIdentifier: _.keys(content)[currentTab.index + 1]
        }));
      }
    } else {
      let i = _.findIndex(_.toArray(content), ws => ws.wsId + '-' + ws.uwYear == wsIdentifier);
      if (currentTab.index > i) {
        ctx.dispatch(new fromWS.SetCurrentTab({
          index: currentTab.index - 1,
          wsIdentifier: currentTab.wsIdentifier
        }));
      }
    }
    ctx.patchState({
      content: _.keyBy(_.filter(content, ws => ws.wsId + '-' + ws.uwYear != wsIdentifier), (el) => el.wsId + '-' + el.uwYear)
    });
  }

  toggleWsDetails(ctx: StateContext<WorkspaceModel>, {wsId}: fromWS.ToggleWsDetails) {
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsId].collapseWorkspaceDetail = !draft.content[wsId].collapseWorkspaceDetail;
    }));
  }

  toggleWsLeftMenu(ctx: StateContext<WorkspaceModel>, {wsId}: fromWS.ToggleWsLeftMenu) {
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsId].leftNavbarCollapsed = !draft.content[wsId].leftNavbarCollapsed;
    }));
  }

  updateWsRouting(ctx: StateContext<WorkspaceModel>, {wsId, route}: fromWS.UpdateWsRouting) {
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsId].route = route;
    }));
  }

  markWsAsFavorite(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.MarkWsAsFavorite) {
    const {wsIdentifier} = payload;
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier] = {...draft.content[wsIdentifier], isFavorite: true};
    }));
  }

  markWsAsNonFavorite(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.MarkWsAsNonFavorite) {
    const {wsIdentifier} = payload;
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier] = {...draft.content[wsIdentifier], isFavorite: false};
    }));
  }

  markWsAsPinned(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.MarkWsAsPinned) {
    const {wsIdentifier} = payload;
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier] = {...draft.content[wsIdentifier], isPinned: true};
    }));
  }

  markWsAsNonPinned(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.MarkWsAsNonPinned) {
    const {wsIdentifier} = payload;
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier] = {...draft.content[wsIdentifier], isPinned: false};
    }));
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
