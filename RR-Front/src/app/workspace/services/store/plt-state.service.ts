import {Injectable} from '@angular/core';
import {StateContext} from '@ngxs/store';
import * as fromPlt from '../../store/actions/plt_main.actions';
import {catchError, mergeMap, tap} from 'rxjs/operators';
import * as _ from 'lodash';
import {PltApi} from '../api/plt.api';
import {WorkspaceModel} from '../../model';
import {of} from 'rxjs';
import * as moment from 'moment';
import produce from "immer";
import {WsApi} from "../api/workspace.api";
import {ADJUSTMENT_TYPE, ADJUSTMENTS_ARRAY} from "../../containers/workspace-calibration/data";
import {TagsApi} from "../api/tags.api";
import {loadAllPlts} from "../../store/actions";

@Injectable({
  providedIn: 'root'
})
export class PltStateService {

  constructor(private pltApi: PltApi,private wsApi: WsApi, private tagApi: TagsApi) {
  }

  status = ['in progress', 'valid', 'locked', 'requires regeneration', 'failed'];

  LoadAllPlts(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {
      wsIdentifier,
      params
    } = payload;

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.loading = true;
    }));
    return this.pltApi.getAllPlts(params)
      .pipe(
        mergeMap((data) => {

/*          ctx.patchState(produce(ctx.getState(), draft => {

            const {
              plts,
              deletedPlts,
              tags
            } = data;


            draft.content[wsIdentifier].plts = _.merge({}, ...plts.map(plt => ({[plt.pltId]: plt})));
            draft.content[wsIdentifier].pltManager = {
              ...draft.content[wsIdentifier].pltManager,
              filters: {systemTag: [], userTag: []},
              data: _.merge({}, ...plts.map(plt => ({[plt.pltId]: this._appendPltMetaData(draft.content[wsIdentifier].pltManager.data,plt)}))),
              deleted: _.merge({}, ...deletedPlts.map(plt => ({[plt.pltId]: this._appendPltMetaData(draft.content[wsIdentifier].pltManager.deleted,plt)})))
            };

          }));
  */

          return ctx.dispatch(new fromPlt.loadAllPltsSuccess({
            wsIdentifier: wsIdentifier,
            data: data
          }));
        }),
        catchError(err => ctx.dispatch(new fromPlt.loadAllPltsFail()))
      );
  }

  private _selectProject(projects: any, projectIndex: number): Array<any> {
    if (!_.isArray(projects))
      return [];
    _.filter(projects, p => p.selected === true).forEach(p => p.selected = false);
    projects[projectIndex].selected = true;
    return projects;
  }

  loadWorkSpaceAndPlts(ctx: StateContext<WorkspaceModel>, payload) {
    const {
      params
    } = payload;


    return this.wsApi.searchWorkspace(params.workspaceId,params.uwy)
      .pipe(
        mergeMap((ws: any) => {


          const {workspaceId, uwy } = params;
          const {workspaceName, programName, cedantName, projects} = ws;
          const wsIdentifier = `${workspaceId}-${uwy}`;
          (projects || []).length > 0 ? ws.projects= this._selectProject(projects, 0) : null;
          ctx.patchState(produce(ctx.getState(), draft => {
            draft.content = _.merge(draft.content, {
              [wsIdentifier]: {
                wsId: workspaceId,
                uwYear: uwy,
                ...ws,
                projects,
                collapseWorkspaceDetail: true,
                route: 'CloneData',
                leftNavbarCollapsed: false,
                isFavorite: false,
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
                fileBaseImport: {}
              }
            });
            draft.loading = false;
          }))
          return of(null);
        }),
        tap( () => ctx.dispatch( new fromPlt.loadAllPlts(payload)))
      )
  }

  setCloneConfig(ctx: StateContext<WorkspaceModel>, payload: any
  ) {
    const {
      wsIdentifier,
      cloneConfig
    } = payload;

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.cloneConfig = cloneConfig;
    }));
  }

  loadAllPltsSuccess(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {
      wsIdentifier,
      data
    } = payload;

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.loading = false;
      draft.content[wsIdentifier].pltManager.data = data;
      draft.content[wsIdentifier].loading = false;
    }));

    return ctx.dispatch(new fromPlt.constructUserTags({wsIdentifier: payload.wsIdentifier, userTags: payload.userTags}))
  }

  selectPlts(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {
      plts,
      wsIdentifier,
      forDelete
    } = payload;

    let inComingData = {};
    _.forEach(plts, (v, k) => {

      inComingData[k] = {
        selected: v.type
      };
    });

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager[forDelete ? 'deleted' : 'data'] = _.merge({}, draft.content[wsIdentifier].pltManager[forDelete ? 'deleted' : 'data'], inComingData);
    }));
  }

  openPltInDrawer(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {wsIdentifier, pltId} = payload;

    const {
      data
    } = ctx.getState().content[wsIdentifier].pltManager;

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.openedPlt = {...data[pltId]};
    }));
  }

  closePLTinDrawer(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {
      wsIdentifier
    } = payload;

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.openedPlt = {};
    }));
  }

  setFilterPlts(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {
      filters,
      wsIdentifier
    } = payload;

    ctx.patchState(produce(ctx.getState(), draft => {
      const oldFilters = draft.content[wsIdentifier].pltManager.filters;
      draft.content[wsIdentifier].pltManager.filters = {...oldFilters, ...filters};
    }));
    return ctx.dispatch(new fromPlt.FilterPltsByUserTags(payload));
  }

  filterPlts(ctx: StateContext<WorkspaceModel>, payload: any) {
    const state = ctx.getState();
    const {
      wsIdentifier
    } = payload;
    const {
      data,
      filters
    } = state.content[wsIdentifier].pltManager;

    let newData = {};

    if (filters.userTag.length > 0) {
      _.forEach(data, (plt: any, k) => {
        if (_.some(filters.userTag, (userTag) => _.find(plt.userTags, tag => tag.tagId == userTag))) {
          newData[k] = {...plt, visible: true};
        } else {
          newData[k] = {...plt, visible: false};
        }
      });
    } else {
      _.forEach(data, (plt, k) => {
        newData[k] = {...plt, visible: true};
      });
    }

    return ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.data = {...data, ...newData};
    }));
  }

  setTableSortAndFilter(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {
      sortData,
      filterData,
      wsIdentifier // ******* To double check *******
    } = payload;

    const {
      content
    } = ctx.getState();

    /**
     * @ToRefactor
     */

    const sortDataKeys = _.keys(sortData);
    const filterDataKeys = _.keys(filterData)
    let res = {...content[wsIdentifier].pltManager.data};


    if (filterDataKeys.length > 0) {

      res = _.filter(content[wsIdentifier].data, row =>
        _.every(
          filterDataKeys,
          filteredCol => _.some(
            _.split(filterData[filteredCol], /[,;]/g), strs =>
              _.includes(_.toLower(_.toString(row[filteredCol])), _.toLower(_.toString(strs)))
          )
        ))
    }

    if (sortDataKeys.length > 0) {
      res = _.orderBy(res, [...sortDataKeys], [..._.values(sortData)])
    }

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.data = res;
    }));
  }

  constructUserTags(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {
      wsIdentifier
    } = payload;

    const {
      userTags
    } = ctx.getState().content[wsIdentifier].pltManager.userTags;

    let uesrTagsSummary = {};

    _.forEach(payload.userTags, (payloadTag) => {

      const {
        tagId,
        ...rest
      } = payloadTag;

      uesrTagsSummary[tagId] = {tagId, ...rest, selected: userTags && userTags[tagId] ? userTags[tagId].selected : false}
    });


    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.userTags = uesrTagsSummary;
    }));
  }

  deletePlt(ctx: StateContext<WorkspaceModel>, payload: any) {
    const state = ctx.getState();
    const {
      request,
      params
    } = payload;

    const wsIdenrifier = state.currentTab.wsIdentifier;

    return this.pltApi.delete(request)
      .pipe(
        mergeMap(() => {
          return ctx.dispatch(new fromPlt.loadAllPlts({
            params: params,
            wsIdentifier: wsIdenrifier
          }))
        }),
        catchError(err => of(null))
      )
  }

  renameTag(ctx: StateContext<WorkspaceModel>, payload: any) {
    /*return this.pltApi.editTag(payload.tag).pipe(
      mergeMap(tag => ctx.dispatch(new fromPlt.editTagSuccess({
        wsIdentifier: payload.workspaceId + '-' + payload.uwy,
        tag
      }))),
      catchError(err => ctx.dispatch(new fromPlt.editTagFail()))
    )*/
  }

  renameTagSuccess(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {
      tag: {
        tagId,
        tagName,
        tagColor,
        pltHeaders
      },
      wsIdentifier
    } = payload;

    const {
      data,
      userTags
    } = ctx.getState().content[wsIdentifier].pltManager;

    let newData = {};

    _.forEach(pltHeaders, pltId => {
      const {
        id
      } = pltId;

      let index = _.findIndex(data[id].userTags, (tag: any) => tag.tagId === tagId);

      newData[id] = {
        ...data[id],
        userTags: _.merge([], data[id].userTags, {
          [index]: {
            ...data[id].userTags[index],
            tagName,
            tagColor
          }
        })
      }
    });

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.data = {...data, ...newData};
      draft.content[wsIdentifier].pltManager.userTags = _.merge({}, userTags, {[tagId]: {tagName, tagColor}});
    }));
  }

  restorePlt(ctx: StateContext<WorkspaceModel>, payload: any) {
    const state = ctx.getState();
    const {
      pltHeaderIds,
      params
    } = payload;

    const wsIdenrifier = state.currentTab.wsIdentifier;

    return this.pltApi.restore(pltHeaderIds)
      .pipe(
        mergeMap(() => {
          return ctx.dispatch(new fromPlt.loadAllPlts({
            params: params,
            wsIdentifier: wsIdenrifier
          }))
        }),
        catchError(err => of(null))
      )

  }

  private _appendPltMetaData(oldData,plt) {
    return ({
      ...plt,
      selected: oldData[plt.pltId] ? oldData[plt.pltId].selected : undefined,
      visible: true,
      tagFilterActive: false,
      opened: false
    });
  }

  filterPltsByStatus(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {
      wsIdentifier,
      status
    } = payload;

    const {
      data
    } = ctx.getState().content[wsIdentifier].pltManager;


    let newData= {};
    let activeStatus = {};

    _.forEach(status, (v,k) => {
      if(v.selected) {
        activeStatus[k]= v;
      }
    });

    const willFilter = _.keys(activeStatus).length;

    _.forEach(data, (plt,k) => {

      newData[k]= {...plt, visible: willFilter ? _.some(activeStatus, (v,statue) => plt[statue]) : true};

    });

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.data = {...data, ...newData};
    }));

  }

  addNewTag(ctx: StateContext<WorkspaceModel>, payload: any) {
    return this.tagApi.creatUserTag({
      ...payload,
      selectedPlts: _.map(payload.selectedPlts, plt => plt.pltId)
    })
      .pipe(
        mergeMap( userTag => of(userTag))
      )
  }

  getTagsBySelection(ctx: StateContext<WorkspaceModel>, payload: any) {
    return this.tagApi.getTagsBySelection(payload)
      .pipe(
        tap(res => {

          const {
            wsIdentifier
          } = ctx.getState().currentTab;

          ctx.patchState(produce(ctx.getState(), draft => {

            draft.content[wsIdentifier].pltManager.userTagManager = {
              allTags: _.keyBy(res.allTags, e => e.tagId),
              suggested: _.keyBy(res.suggested, e => e.tagId),
              usedInWs: _.keyBy(res.usedInWs, e => e.tagId)
            };

          }))

        })
      )
  }

  assignPltsToTag(ctx: StateContext<WorkspaceModel>, payload: any) {
    return this.tagApi.assignPltsToTag(payload)
      .pipe(
        mergeMap( res => {
          const {
            wsId,
            uwYear
          } = payload;

          return ctx.dispatch(new loadAllPlts({
            params: {
              workspaceId: wsId, uwy: uwYear
            },
            wsIdentifier: wsId + '-' + uwYear
          }));
        })
      )
  }

  deleteUserTag(ctx: StateContext<WorkspaceModel>, payload: any) {
    /*return this.pltApi.deleteUserTag(payload.userTagId).pipe(
      mergeMap(() => ctx.dispatch(new fromPlt.deleteUserTagSuccess(payload)))
    )*/
  }

  loadSummaryDetail(ctx: StateContext<WorkspaceModel>, payload: any) {

    const {
      wsIdentifier
    } = ctx.getState().currentTab;

    return this.pltApi.getSummary(payload)
      .pipe(
        mergeMap( summaryDetail => ctx.dispatch(new fromPlt.loadSummaryDetailSuccess({summaryDetail, wsIdentifier})))
      )
  }

  loadSummaryDetailSuccess(ctx: StateContext<WorkspaceModel>, payload: any) {

    const {
      summaryDetail,
      wsIdentifier
    } = payload;

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.pltDetails.summary = summaryDetail;
    }))
  }

  saveGlobalTableData(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {
      wsIdentifier,
      data
    } = payload;

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.data = data;
      draft.content[wsIdentifier].pltManager.initialized = true;
    }));
  }

  saveGlobalTableColumns(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {
      wsIdentifier,
      columns
    } = payload;

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.columns = columns;
      draft.content[wsIdentifier].pltManager.initialized = true;
    }));
  }

  saveGlobalTableSelection(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {
      wsIdentifier,
      selectedIds
    } = payload;

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.selectedIds = selectedIds;
      draft.content[wsIdentifier].pltManager.initialized = true;
    }));
  }
}
