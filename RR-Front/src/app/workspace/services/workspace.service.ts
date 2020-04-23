import {Injectable} from '@angular/core';
import {StateContext, Store} from '@ngxs/store';
import {WorkspaceModel} from '../model';
import * as fromWS from '../store/actions';
import {catchError, map, mergeMap, switchMap, tap} from 'rxjs/operators';
import {WsApi} from './api/workspace.api';
import produce from 'immer';
import * as _ from 'lodash';
import {Navigate, RouterNavigation} from '@ngxs/router-plugin';
import {ADJUSTMENT_TYPE, ADJUSTMENTS_ARRAY} from '../containers/workspace-calibration/data';
import {EMPTY, forkJoin, of} from 'rxjs';
import {defaultInuringState} from './inuring.service';
import {ProjectApi} from "./api/project.api";
import {RiskLink} from "../model/risk-link.model";

@Injectable({
  providedIn: 'root'
})
export class WorkspaceService {

  constructor(private wsApi: WsApi,
              private projectApi: ProjectApi,
              private store: Store) {
  }

  initWs(ctx: StateContext<WorkspaceModel>, payload) {
    const {wsId, uwYear, route, type, carSelected} = payload;
    const content = ctx.getState().content;
    return this.wsApi.getOpenedTabs({workspaceContextCode: wsId, workspaceUwYear: uwYear, screen: route, type: type || ''}).pipe(
        switchMap((tabs: any[]) =>  {
          const lookupWorkspaces = _.filter(tabs, t => !(content[t.workspaceContextCode + '-' + t.workspaceUwYear]));
          if(lookupWorkspaces.length) {
            return forkJoin(
                ..._.map(lookupWorkspaces, tab => this.wsApi.searchWorkspace(tab.workspaceContextCode, tab.workspaceUwYear, type ? type : ''))
            ).pipe(
                mergeMap(data => of(ctx.dispatch(new fromWS.OpenMultiWS({workspaces: _.filter(data, e => e), tabs, carSelected }))))
            )
          } else {
            return of(ctx.dispatch(new fromWS.OpenMultiWS({workspaces: [], tabs, carSelected })))
          }
        })
    );
  }

  loadWs(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadWS) {
    const {wsId, uwYear, route, type, carSelected} = payload;
    const redirect = _.get(payload, 'redirect', null);
    ctx.patchState({loading: true});
    return this.wsApi.searchWorkspace(wsId, uwYear, type)
      .pipe(
        tap(ws => {
          return ctx.dispatch(new fromWS.LoadWsSuccess({
          wsId, uwYear, ws, route, carSelected, redirect
        }))}),
        catchError(e => ctx.dispatch(new fromWS.LoadWsFail()))
      );
  }

  loadWsSuccess(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadWsSuccess) {
    const {wsId, uwYear, ws, route, redirect} = payload;
    const state = ctx.getState();
    const carSelected = _.get(payload, 'carSelected', null);
    const {projects} = ws;
    const wsIdentifier = `${wsId}-${uwYear}`;
    const openedTab = {
      screen: route || 'projects',
      workspaceContextCode: wsId,
      workspaceUwYear: uwYear,
    };
    return this.wsApi.openTab(openedTab).pipe(tap(data => {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.content[wsIdentifier] = {
          wsId,
          uwYear,
          ...ws,
          route: route || 'projects',
          isPinned: false,
          collapseWorkspaceDetail: true,
          workspaceType: ws.marketChannel == 'FAC' ? 'fac' : 'treaty',
          leftNavbarCollapsed: false,
          plts: {},
          projects: _.map(projects.reverse(), (prj, index: any) => {
            prj.selected = carSelected !== null ? prj.projectId === carSelected : index === 0;
            prj.projectType = prj.carRequestId === null ? 'TREATY' : 'FAC';
            return prj;
          }),
          pltManager: {
            columns: [],
            data: {totalCount: 0, plts: []},
            selectedIds: {},
            initialized: false,
            pltDetails: {
              summary: {}
            },
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
          riskLink: new RiskLink(),
          scopeOfCompletence: {
            data: {
              targetRaps: [],
              regionPerils: [],
              scopeContext: null
            },
            pendingData: {
              accumulationPackageId: 0,
              accumulationPackageStatus: "",
              targetRaps: [],
              regionPerils: [],
            },
            override: {
              overrideAll: false,
              overrideRow: false,
              overrideInit: false,
              overrideCancelAll: false,
              overrideCancelRow: false,
              overrideCancelStart: false,
              removeOverrideUnable: false
            },
            overriddenRows: [],
            projects: [],
            plts: [],
            scopeContext: {
              accumulationStatus: 'Scope Only',
              filterBy: 'All',
              sortBy: 'Minimum Grain / RAP',
            }
          },
          fileBaseImport: {
            folders: null,
            files: null,
            selectedFiles: null,
            importedPLTs: null
          },
          inuring: defaultInuringState
        };
        draft.loading = false;
        draft.currentTab = {...draft.currentTab,
          index: _.findIndex(_.keys(draft.content), item => item === wsIdentifier),
          wsIdentifier: wsIdentifier
        }
      }));
      if (redirect == null) {
        ctx.dispatch(new Navigate([`workspace/${_.replace(wsIdentifier, '-', '/')}${route ? '/' + route : '/projects'}`]))
      }
    }));

  }

  openWorkspace(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.OpenWS) {
    const {wsId, uwYear, route, type} = payload;
    const carSelected = _.get(payload, 'carSelected', null);
    // const state = ctx.getState();
    // const wsIdentifier = wsId + '-' + uwYear;
    //
    // if (state.content[wsIdentifier]) {
    //   if (carSelected !== null) {
    //     ctx.patchState(produce(ctx.getState(), draft =>  {
    //       draft.content[wsIdentifier].projects = _.map(draft.content[wsIdentifier].projects, (prj, index: any) => {
    //         prj.selected = prj.projectId === carSelected;
    //         return prj;
    //       });
    //     }));
    //   }
    // }

    return ctx.dispatch(new fromWS.InitWorkspace({wsId, uwYear, route, type, carSelected}));
  }

  openMultipleWorkspaces(ctx: StateContext<WorkspaceModel>, {payload: {workspaces, tabs , carSelected}}: fromWS.OpenMultiWS) {
    let screenByTab = {};
    let selectedTab: any = null;

    console.log('multi:', workspaces, tabs);

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.loading = true;
    }));

    _.forEach(tabs, (tab, index) => {
      const wsIdentifier = tab.workspaceContextCode + '-' + tab.workspaceUwYear;
      screenByTab[wsIdentifier] =  tab.screen;

      if(tab.selected) {
        selectedTab = {
          openedTabs: null,
          index: tab.tabOrder,
          wsIdentifier
        };
      }
      const ws = _.find(workspaces, e => e.id == tab.workspaceContextCode && e.uwYear == tab.workspaceUwYear);

      if(!ctx.getState().content[wsIdentifier] && ws) {
       const {projects} = ws;

       ctx.patchState(produce(ctx.getState(), draft => {
          draft.content[wsIdentifier] = {
            wsId: ws.id,
            uwYear: ws.uwYear,
            ...ws,
            route: screenByTab[wsIdentifier] || 'projects',
            isPinned: false,
            collapseWorkspaceDetail: true,
            workspaceType: ws.marketChannel == 'FAC' ? 'fac' : 'treaty',
            leftNavbarCollapsed: false,
            plts: {},
            projects: _.map(projects.reverse(), (prj, index: any) => {
              prj.selected = carSelected !== null ? prj.projectId === carSelected : index === 0;
              prj.projectType = prj.carRequestId === null ? 'TREATY' : 'FAC';
              return prj;
            }),
            pltManager: {
              columns: [],
              data: {totalCount: 0, plts: []},
              selectedIds: {},
              initialized: false,
              pltDetails: {
                summary: {}
              },
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
            riskLink: new RiskLink(),
            scopeOfCompleteness: {
              data: {
                targetRaps: [],
                regionPerils: [],
                scopeContext: null
              },
              pendingData: {
                targetRaps: [],
                regionPerils: [],
              },
              override: {
                overrideAll: false,
                overrideRow: false,
                overrideInit: false,
                overrideCancelAll: false,
                overrideCancelRow: false,
                overrideCancelStart: false,
                removeOverrideUnable: false
              },
              overriddenRows: [],
              projects: [],
              plts: [],
              scopeContext: {
                accumulationStatus: 'Scope Only',
                filterBy: 'All',
                sortBy: 'Minimum Grain / RAP',
              }
            },
            fileBaseImport: {
              folders: null,
              files: null,
              selectedFiles: null,
              importedPLTs: null
            },
            inuring: defaultInuringState
          }
        }));
      }

    });

    if(carSelected && selectedTab && selectedTab.wsIdentifier && ctx.getState().content[selectedTab.wsIdentifier].projects) {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.content[selectedTab.wsIdentifier].projects = _.map(draft.content[selectedTab.wsIdentifier].projects.reverse(), (prj, index: any) => {
          prj.selected = carSelected !== null ? prj.projectId === carSelected : index === 0;
          prj.projectType = prj.carRequestId === null ? 'TREATY' : 'FAC';
          return prj;
        })
      }))
    }

    ctx.dispatch(new Navigate([`workspace/${_.replace(selectedTab.wsIdentifier, '-', '/')}${screenByTab[selectedTab.wsIdentifier] ? '/' + screenByTab[selectedTab.wsIdentifier] : '/projects'}`]));

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.currentTab = { ...selectedTab, openedTabs: tabs };
      draft.loading = false;
    }));

    //return ctx.dispatch(new fromWS.UpdateWsRouting(null, screenByTab[selectedTab.wsIdentifier] ? screenByTab[selectedTab.wsIdentifier] : 'projects'))
  }

  setCurrentTab(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.SetCurrentTab) {
    const {
      index, wsIdentifier, redirect
    } = payload;

    const workspaceContextCode = wsIdentifier.split('-')[0];
    const workspaceUwYear = wsIdentifier.split('-')[1];

    const tab = _.find(ctx.getState().currentTab.openedTabs, tab => tab.workspaceContextCode == workspaceContextCode && tab.workspaceUwYear == workspaceUwYear);
    if(!tab.selected) {
      return this.wsApi.selectTab(tab).pipe(
          tap(() => {
            ctx.patchState(produce(ctx.getState(), draft => {
              const ws = draft.content[wsIdentifier];
              const {route} = ws;
              draft.currentTab = {...draft.currentTab, index, wsIdentifier, openedTabs: _.map(draft.currentTab.openedTabs, tab => ({ ...tab, selected: tab.workspaceContextCode == workspaceContextCode && tab.workspaceUwYear == workspaceUwYear}))};
              if (redirect == null) {
                ctx.dispatch(new Navigate([`workspace/${_.replace(wsIdentifier, '-', '/')}${route ? '/' + route : '/projects'}`]))
              }
            }));
          })
      )
    }

  }

  closeWorkspace(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.CloseWS) {
    const {wsId, uwYear} = payload;
    const wsIdentifier = wsId + '-' + uwYear;
    const {currentTab, content} = ctx.getState();

    // To keep at least one tab open
    if (_.size(content) == 1)
      return;

    let oldSelectedTab = _.find(currentTab.openedTabs, t => t.selected);
    let closedTab = _.find(currentTab.openedTabs, t => t.workspaceContextCode == wsId && t.workspaceUwYear == uwYear);
    let newSelectedIndex = 0;

    if(oldSelectedTab.workspaceContextCode == closedTab.workspaceContextCode && oldSelectedTab.workspaceUwYear == closedTab.workspaceUwYear) {
      newSelectedIndex = closedTab.tabOrder === 0 ? closedTab.tabOrder : closedTab.tabOrder - 1;
    } else {
      newSelectedIndex = oldSelectedTab.tabOrder < closedTab.tabOrder ? oldSelectedTab.tabOrder : oldSelectedTab.tabOrder - 1;
    }

    const tabs = _.map(_.filter(currentTab.openedTabs, t => !(t.workspaceContextCode == wsId && t.workspaceUwYear == uwYear)), (tab, index) => ({...tab, tabOrder: _.toNumber(index), selected: newSelectedIndex == _.toNumber(index)}) );
    return this.wsApi.closeTab({toClose: closedTab, tabs}).pipe(
        switchMap( data => {
          const { workspaceContextCode, workspaceUwYear, screen } = tabs[newSelectedIndex];
          const newWsIdentifier = workspaceContextCode + '-' + workspaceUwYear;

          ctx.patchState(produce(ctx.getState(), draft => {
            draft.currentTab = {
              openedTabs: tabs,
              index: newSelectedIndex,
              wsIdentifier: newWsIdentifier
            };
            draft.content = _.omit(draft.content, `${wsIdentifier}`);
          }));

          return ctx.dispatch(new Navigate(screen ? [`workspace/${workspaceContextCode}/${workspaceUwYear}/${screen}`] : [`workspace/${workspaceContextCode}/${workspaceUwYear}`]))
        }),
        catchError(err => {
          return of();
        })
    );
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

  updateWsRouting(ctx: StateContext<WorkspaceModel>, {route}: fromWS.UpdateWsRouting) {
    const state = ctx.getState();
    const {wsIdentifier} = state.currentTab;
    const {wsId, uwYear} = state.content[wsIdentifier];
    const tabs = state.currentTab.openedTabs;
    let order = tabs.length;

    for(let i=0; i< tabs.length; i++) {
      const e = tabs[i];
      if(e.workspaceContextCode == wsId && e.workspaceUwYear == uwYear) {
        order = i;
        break;
      }
    }

    const openedTab = {
      screen: route,
      workspaceContextCode: wsId,
      workspaceUwYear: uwYear,
      order: order
    };

    return this.wsApi.openTab(openedTab).pipe(tap( data =>
        ctx.patchState(produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].route = route;
        }))
    ))

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
      draft.content[wsIdentifier].riskLink= _.merge({}, new RiskLink());
    }));
  }

  selectProject(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.SelectProject) {
    const {wsIdentifier, projectId} = payload;
    return ctx.patchState(produce(ctx.getState(), draft => {
      const {projects} = draft.content[wsIdentifier];
      draft.content[wsIdentifier].projects = _.map(projects || [], pr => ({...pr, selected: pr.projectId == projectId}) );
      draft.content[wsIdentifier].riskLink= _.merge({}, new RiskLink());
    }));
  }

  addNewProject(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.AddNewProject) {
    const {id, wsId, uwYear, project} = payload;
    const wsIdentifier = `${wsId}-${uwYear}`;

    return this.projectApi.createProject({...project}, wsId, uwYear)
      .pipe(map(prj => {
        ctx.patchState(produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].projects = _.map(draft.content[wsIdentifier].projects, item => ({...item, selected: false}));
          prj ? draft.content[wsIdentifier].projects.unshift({...prj, selected: true, projectType: 'TREATY'}) : null
        }));
        return ctx.dispatch(new fromWS.AddNewProjectSuccess(prj));
      }), catchError(err => {
        ctx.dispatch(new fromWS.AddNewProjectFail({}));
        return EMPTY;
      }));
  }

  updateProject(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {assignedTo, projectId, projectName, projectDescription, dueDate} = payload.data;
    const wsIdentifier = state.currentTab.wsIdentifier;
    return this.projectApi.updateProject(assignedTo, projectId, projectName, projectDescription, dueDate).pipe(
      map( (prj: any) => ctx.patchState(produce(ctx.getState(), draft => {
        prj ? draft.content[wsIdentifier].projects = _.map(draft.content[wsIdentifier].projects, item => {
          return item.projectId === projectId ? {...prj, projectType: prj.carRequestId === null ? 'TREATY' : 'FAC'
            , selected: item.selected, projectName, projectDescription, dueDate, assignedTo} : {...item};
        }) : null;
      }))
    ), catchError(err => {
        console.log('an error has occurred: ', err);
        return EMPTY;
      }))
  }

  deleteProject(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.DeleteProject) {
    const {projectId, wsId, uwYear} = payload;
    const state = ctx.getState();
    const wsIdentifier = `${wsId}-${uwYear}`;
    return this.projectApi.deleteProject(projectId.projectId)
      .pipe(catchError(err => {
        ctx.dispatch(new fromWS.DeleteProjectFails({}));
        return EMPTY;
      }))
      .subscribe((p) => {
        ctx.patchState(produce(ctx.getState(), draft => {
          const selectedPrj = _.find(state.content[wsIdentifier].projects, item => item.projectId === projectId.projectId);
          draft.content[wsIdentifier].projects = _.filter(draft.content[wsIdentifier].projects, e => e.projectId !== projectId.projectId)
          if (selectedPrj.selected) {
            draft.content[wsIdentifier].projects[0].selected = true;
          }
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
