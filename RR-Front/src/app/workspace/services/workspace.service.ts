import {Injectable} from '@angular/core';
import {StateContext, Store} from "@ngxs/store";
import {WorkspaceModel} from "../model";
import * as fromWS from "../store/actions";
import {catchError, map, mergeMap} from "rxjs/operators";
import {WsApi} from "./workspace.api";
import * as fromHeader from "../../core/store/actions/header.action";
import produce from "immer";
import * as _ from "lodash";
import {Navigate} from "@ngxs/router-plugin";
import {HeaderState} from "../../core/store/states/header.state";
import {ADJUSTMENT_TYPE, ADJUSTMENTS_ARRAY} from "../containers/workspace-calibration/data";
import {EMPTY} from "rxjs";
import {WsProjectService} from "./ws-project.service";
import {defaultInuringState} from "./inuring.service";

@Injectable({
  providedIn: 'root'
})
export class WorkspaceService {

  constructor(private wsApi: WsApi, private store: Store, private wsProjectService: WsProjectService) {
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
    const {workspaceName, programName, cedantName, projects} = ws;
    const wsIdentifier = `${wsId}-${uwYear}`;
    console.log('this are projects', projects);
    (projects || []).length > 0 ? ws.projects = this._selectProject(projects, 0) : null;
    ctx.dispatch(new fromHeader.AddWsToRecent({wsId, uwYear, workspaceName, programName, cedantName}));
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content = _.merge(draft.content, {
        [wsIdentifier]: {
          wsId,
          uwYear,
          ...ws,
          projects,
          collapseWorkspaceDetail: true,
          route,
          leftNavbarCollapsed: false,
          isFavorite: false,
          plts: {},
          pltManager: {
            data: {},
            filters: {
              systemTag: [], userTag: []
            },
            openedPlt: {},
            userTags: {},
            cloneConfig: {},
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
            adjustementType: _.assign({}, ADJUSTMENT_TYPE),
            allAdjsArray: _.assign({}, ADJUSTMENTS_ARRAY),
          },
          riskLink: {
            listEdmRdm: {
              data: null,
              dataSelected: [],
              selectedListEDMAndRDM: {edm: null, rdm: null},
              totalNumberElement: 0,
              searchValue: '',
              numberOfElement: 0
            },
            linking: {
              edm: null,
              rdm: {data: null, selected: null},
              autoLinks: null,
              linked: [],
              analysis: null,
              portfolio: null,
              autoMode: false
            },
            display: {
              displayTable: false,
              displayImport: false,
            },
            collapse: {
              collapseHead: true,
              collapseAnalysis: true,
              collapseResult: true,
            },
            checked: {
              checkedARC: false,
              checkedPricing: false,
            },
            financialValidator: {
              rmsInstance: {
                data: ['AZU-P-RL17-SQL14', 'AZU-U-RL17-SQL14', 'AZU-U2-RL181-SQL16'],
                selected: 'AZU-P-RL17-SQL14'
              },
              financialPerspectiveELT: {
                data: ['Net Loss Pre Cat (RL)', 'Gross Loss (GR)', 'Net Cat (NC)'],
                selected: 'Net Loss Pre Cat (RL)'
              },
              targetCurrency: {
                data: ['Main Liability Currency (MLC)', 'Analysis Currency', 'User Defined Currency'],
                selected: 'Main Liability Currency (MLC)'
              },
              calibration: {data: ['Add calibration', 'item 1', 'item 2'], selected: 'Add calibration'},
            },
            financialPerspective: {
              rdm: {data: null, selected: null},
              analysis: null,
              treaty: null,
              standard: null,
              target: 'currentSelection'
            },
            analysis: null,
            portfolios: null,
            results: null,
            summaries: null,
            selectedEDMOrRDM: null,
            activeAddBasket: false
          },
          scopeOfCompletence: {
            data: {},
          },
          fileBaseImport: {
            folders: null,
            files: null,
            selectedFiles: null,
            importedPLTs: null
          },
          inuring: defaultInuringState
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

  toggleProjectSelection(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleProjectSelection) {
    const {wsIdentifier, projectIndex} = payload;
    return ctx.patchState(produce(ctx.getState(), draft => {
      const {projects} = draft.content[wsIdentifier];
      draft.content[wsIdentifier].projects = [...this._selectProject(projects, projectIndex)];
    }))
  }

  addNewProject(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.AddNewProject) {
    const {wsId, uwYear, id, project} = payload;
    const wsIdentifier = `${wsId}-${uwYear}`;
    return this.wsProjectService.addNewProject(project, wsId, uwYear, id)
      .pipe(map(p => {
        ctx.patchState(produce(ctx.getState(), draft => {
          p ? draft.content[wsIdentifier].projects.unshift(p) : null
        }));
        return ctx.dispatch(new fromWS.AddNewProjectSuccess(p));
      }), catchError(err => {
        ctx.dispatch(new fromWS.AddNewProjectFail({}));
        return EMPTY;
      }));
  }

  deleteProject(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.DeleteProject) {
    const {project, wsId, uwYear, id} = payload;
    const wsIdentifier = `${wsId}-${uwYear}`;
    return this.wsProjectService.deleteProject(project, wsId, uwYear)
      .pipe(catchError(err => {
        ctx.dispatch(new fromWS.DeleteProjectFails({}));
        return EMPTY;
      }))
      .subscribe((p) => {
        ctx.patchState(produce(ctx.getState(), draft => {
          p ? draft.content[wsIdentifier].projects = _.filter(draft.content[wsIdentifier].projects, e => e.projectId !== p.projectId) : null
        }));
        return ctx.dispatch(new fromWS.DeleteProjectSuccess(p));
      });
  }

  private _selectProject(projects: any, projectIndex: number): Array<any> {
    if (!_.isArray(projects))
      return [];
    _.filter(projects, p => p.selected === true).forEach(p => p.selected = false);
    projects[projectIndex].selected = true;
    return projects;
  }

  private _isFavorite({wsId, uwYear}): boolean {
    const favoriteWs = this.store.selectSnapshot(HeaderState.getFavorite);
    return _.findIndex(favoriteWs, item => item.wsId == wsId && item.uwYear == uwYear) !== -1;
  }

  private _isPinned({wsId, uwYear}): boolean {
    const pinnedWs = this.store.selectSnapshot(HeaderState.getPinned);
    return _.findIndex(pinnedWs, item => item.wsId == wsId && item.uwYear == uwYear) !== -1;
  }

}
