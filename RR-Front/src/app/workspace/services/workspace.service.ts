import {Injectable} from '@angular/core';
import {StateContext, Store} from '@ngxs/store';
import {WorkspaceModel} from '../model';
import * as fromWS from '../store/actions';
import {catchError, map, mergeMap, switchMap} from 'rxjs/operators';
import {WsApi} from './api/workspace.api';
import produce from 'immer';
import * as _ from 'lodash';
import {Navigate} from '@ngxs/router-plugin';
import {ADJUSTMENT_TYPE, ADJUSTMENTS_ARRAY} from '../containers/workspace-calibration/data';
import {EMPTY, forkJoin, of} from 'rxjs';
import {defaultInuringState} from './inuring.service';
import {ProjectApi} from "./api/project.api";

@Injectable({
  providedIn: 'root'
})
export class WorkspaceService {

  constructor(private wsApi: WsApi,
              private projectApi: ProjectApi,
              private store: Store) {
  }

  loadWs(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadWS) {
    const {
      wsId, uwYear, route} = payload;
    ctx.patchState({loading: true});
    return this.wsApi.searchWorkspace(wsId, uwYear)
      .pipe(
        mergeMap(ws => {
          return ctx.dispatch(new fromWS.LoadWsSuccess({
          wsId, uwYear, ws, route
        }))}),
        catchError(e => ctx.dispatch(new fromWS.LoadWsFail()))
      );
  }

  loadWsSuccess(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadWsSuccess) {
    const {wsId, uwYear, ws, route} = payload;
    const {projects} = ws;
    const wsIdentifier = `${wsId}-${uwYear}`;
    (projects || []).length > 0 ? ws.projects = this._selectProject(projects, 0) : null;
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content = _.merge(draft.content, {
        [wsIdentifier]: {
          wsId,
          uwYear,
          ...ws,
          projects: _.map(projects, prj => {
            return prj.carRequestId === null ? {...prj, projectType: 'TREATY'} : {...prj, projectType: 'FAC'};
          }),
          workspaceType: _.includes(_.map(projects, prj => {return prj.carRequestId === null ? 'TREATY' : 'FAC'}), 'FAC') ? 'fac' : 'treaty',
          isPinned: false,
          collapseWorkspaceDetail: true,
          route,
          leftNavbarCollapsed: false,
          plts: {},
          pltManager: {
            data: {},
            deleted: {},
            filters: {
              systemTag: [], userTag: []
            },
            openedPlt: {},
            userTags: {},
            userTagManager: {
              usedInWs: {},
              suggested: {},
              allTags: {}
            },
            pltDetails: {
              summary: {}
            },
            cloneConfig: {},
            loading: false
          },
          contract: {
            treaty: {},
            fac: {},
            loading: false,
            typeWs: null,
          },
          calibrationNew: {
            plts: [],
            epMetrics: {
              cols: [],
              rps: []
            },
            adjustments: {},
            constants: {
              basis: [],
              adjustmentTypes: [],
              status: []
            },
            loading: false
          },
          calibration: {
            data: {},
            deleted: {},
            loading: false,
            filters: {
              systemTag: [],
              userTag: []
            },
            userTags: {},
            selectedPLT: [],
            extendPltSection: false,
            extendState: false,
            collapseTags: true,
            lastCheckedBool: false,
            firstChecked: '',
            adjustments: [],
            adjustmentApplication: {},
            defaultAdjustment: {},
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
              displayListRDMEDM: false,
              displayTable: false,
              displayImport: false,
            },
            collapse: {
              collapseHead: true,
              collapseAnalysis: true,
              collapseResult: true,
            },
            financialValidator: {
              rmsInstance: {data: [], selected: ''},
              financialPerspectiveELT: {data: [], selected: ''},
              targetCurrency: {data: [], selected: 'Main Liability Currency (MLC)'},
              division: {data: [], selected: 'Division N°1'},
            },
            financialPerspective: {
              rdm: {data: null, selected: null},
              analysis: null,
              treaty: null,
              standard: null,
              target: 'currentSelection'
            },
            analysis: [],
            portfolios: [],
            results: null,
            summaries: null,
            selection: null,
            facSelection: {
              'Division N°1': {analysis: {}, portfolios: {}},
              'Division N°2': {analysis: {}, portfolios: {}},
              'Division N°3': {analysis: {}, portfolios: {}},
            },
            importPLTs: {},
            selectedEDMOrRDM: null,
            activeAddBasket: false,
            synchronize: false
          },
          scopeOfCompletence: {
            data: {},
            wsType: null
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
      ctx.dispatch([new fromWS.SetCurrentTab({
        index: _.size(draft.content),
        wsIdentifier
      })]);
    }));
  }

  openWorkspace(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.OpenWS) {
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
        route}));
    }
  }

  createNewFac(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.CreateNewFac) {
    const state = ctx.getState();
    ctx.patchState(produce(ctx.getState(), draft => {
      const newData = [payload, ...draft.facWs.data];
      draft.facWs.data = _.map(newData, item => {
        if (item.uwanalysisContractContractId === payload.uwanalysisContractContractId && item.uwanalysisContractYear === payload.uwanalysisContractYear) {
          return ((item.carStatus === 'New' || item.carStatus === 'In Progress') && item.id !== payload.id) ? {...item, carStatus: 'Canceled'} : {...item};
        } else {
          return {...item};
        }
      });
      draft.facWs.sequence = draft.facWs.sequence + 1;
    }));
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

  toggleFavorite(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleFavorite) {
    const state = ctx.getState();
    const wsIdentifier = state.currentTab.wsIdentifier;
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier] = {...draft.content[wsIdentifier], isFavorite: !draft.content[wsIdentifier].isFavorite};
    }));
  }

  togglePinned(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.TogglePinned) {
    const state = ctx.getState();
    const wsIdentifier = state.currentTab.wsIdentifier;
    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier] = {...draft.content[wsIdentifier], isPinned: !draft.content[wsIdentifier].isPinned};
    }));
  }

  toggleProjectSelection(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleProjectSelection) {
    const {wsIdentifier, projectIndex} = payload;
    return ctx.patchState(produce(ctx.getState(), draft => {
      const {projects} = draft.content[wsIdentifier];
      draft.content[wsIdentifier].projects = [...this._selectProject(projects, projectIndex)];
    }));
  }

  addNewProject(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.AddNewProject) {
    const {wsId, uwYear, project} = payload;
    const wsIdentifier = `${wsId}-${uwYear}`;
    return this.projectApi.createProject({...project, createdBy: 1}, wsId, uwYear)
      .pipe(map(prj => {
        ctx.patchState(produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].projects = _.map(draft.content[wsIdentifier].projects, item => ({...item, selected: false}));
          prj ? draft.content[wsIdentifier].projects.unshift({...prj, selected: true}) : null
        }));
        return ctx.dispatch(new fromWS.AddNewProjectSuccess(prj));
      }), catchError(err => {
        ctx.dispatch(new fromWS.AddNewProjectFail({}));
        return EMPTY;
      }));
  }

  addNewFacProject(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = state.currentTab.wsIdentifier;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].projects = [payload, ...draft.content[wsIdentifier].projects];
    }));
  }

  updateProject(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {assignedTo, projectId, projectName, projectDescription} = payload.data;
    const wsIdentifier = state.currentTab.wsIdentifier;
    return this.projectApi.updateProject(assignedTo, projectId, projectName, projectDescription).pipe(
      map( (prj: any) => ctx.patchState(produce(ctx.getState(), draft => {
        prj ? draft.content[wsIdentifier].projects = _.map(draft.content[wsIdentifier].projects, item => {
          return item.projectId === projectId ? {...prj, projectType: prj.carRequestId === null ? 'TREATY' : 'FAC'
            , selected: item.selected} : {...item};
        }) : null;
      }))
    ), catchError(err => {
        console.log('an error has occurred: ', err);
        return EMPTY;
      }))
  }

  deleteProject(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.DeleteProject) {
    const {projectId, wsId, uwYear} = payload;
    const wsIdentifier = `${wsId}-${uwYear}`;
    return this.projectApi.deleteProject(projectId.projectId)
      .pipe(catchError(err => {
        ctx.dispatch(new fromWS.DeleteProjectFails({}));
        return EMPTY;
      }))
      .subscribe((p) => {
        ctx.patchState(produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].projects = _.filter(draft.content[wsIdentifier].projects, e => e.projectId !== projectId.projectId)
        }));
        return ctx.dispatch(new fromWS.DeleteProjectSuccess(p));
      });
  }

  deleteFacProject(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = state.currentTab.wsIdentifier;
    const selected = _.filter(state.content[wsIdentifier].projects, item => item.selected && item.id === payload.payload.id);
    ctx.patchState(produce(ctx.getState(), draft => {
      if (selected.length > 0) {
        draft.content[wsIdentifier].projects = this._selectProject(
          _.filter(draft.content[wsIdentifier].projects, item => item.id !== payload.payload.id), 0
        );
      } else {
        draft.content[wsIdentifier].projects = _.filter(draft.content[wsIdentifier].projects, item => item.id !== payload.payload.id);
      }
    }));
  }

  private _selectProject(projects: any, projectIndex: number): Array<any> {
    if (!_.isArray(projects))
      return [];
    _.filter(projects, p => p.selected === true).forEach(p => p.selected = false);
    projects[projectIndex].selected = true;
    return projects;
  }

  private _isFavorite({wsId, uwYear}): boolean {
/*    const favoriteWs = this.store.selectSnapshot(HeaderState.getFavorite);
    return _.findIndex(favoriteWs, item => item.wsId == wsId && item.uwYear == uwYear) !== -1;*/
    return false;
  }

  private _isPinned({wsId, uwYear}): boolean {
/*    const pinnedWs = this.store.selectSnapshot(HeaderState.getPinned);
    return _.findIndex(pinnedWs, item => item.wsId == wsId && item.uwYear == uwYear) !== -1;*/
    return false;
  }

}
