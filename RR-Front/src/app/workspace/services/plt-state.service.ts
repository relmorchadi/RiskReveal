import {Injectable} from '@angular/core';
import {StateContext} from '@ngxs/store';
import * as fromPlt from '../store/actions/plt_main.actions';
import {catchError, mergeMap, tap} from 'rxjs/operators';
import * as _ from 'lodash';
import {PltApi} from './plt.api';
import {WorkspaceModel} from '../model';
import {of} from 'rxjs';
import * as moment from 'moment';
import produce from "immer";
import {WsApi} from "./workspace.api";
import * as fromWS from "../store/actions";
import * as fromHeader from "../../core/store/actions/header.action";
import {ADJUSTMENT_TYPE, ADJUSTMENTS_ARRAY} from "../containers/workspace-calibration/data";

@Injectable({
  providedIn: 'root'
})
export class PltStateService {

  constructor(private pltApi: PltApi,private wsApi: WsApi) {
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
    console.log(payload);
    return this.pltApi.getAllPlts(params)
      .pipe(
        mergeMap((data) => {
          console.log('Plts', data);
          ctx.patchState(produce(ctx.getState(), draft => {
            const deletedPlts = JSON.parse(localStorage.getItem('deletedPlts')) || {};
            //draft.content[wsIdentifier].plts = _.merge({}, ...data.plts.map(plt => ({[plt.pltId]: {...plt}})));
            draft.content[wsIdentifier].plts = _.merge({}, ...data.plts.map(plt => ({[plt.pltId]: plt})));
            draft.content[wsIdentifier].pltManager = {
              ...draft.content[wsIdentifier].pltManager,
              filters: {systemTag: [], userTag: []},
              data: _.merge({}, ...data.plts.map(plt => ({[plt.pltId]: this._appendPltMetaData(data,plt, deletedPlts)})))
            };
          }));
          return ctx.dispatch(new fromPlt.loadAllPltsSuccess({
            wsIdentifier: wsIdentifier,
            userTags: data.userTags
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
          console.log('this are projects', projects);
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
      wsIdentifier
    } = payload;

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.loading = false;
      draft.content[wsIdentifier].loading = false;
    }));

    return ctx.dispatch(new fromPlt.constructUserTags({wsIdentifier: payload.wsIdentifier, userTags: payload.userTags}))
  }

  selectPlts(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {
      plts,
      wsIdentifier
    } = payload;

    let inComingData = {};
    _.forEach(plts, (v, k) => {
      inComingData[k] = {
        selected: v.type
      };
    });

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.data = _.merge({}, draft.content[wsIdentifier].pltManager.data, inComingData);
    }));
  }

  openPltInDrawer(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {wsIdentifier, pltId} = payload;

    const {
      data
    } = ctx.getState().content[wsIdentifier].pltManager;

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.openedPlt = data[pltId];
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
        pltHeaders,
        ...rest
      } = payloadTag;

      uesrTagsSummary[tagId] = {tagId, ...rest, selected: userTags && userTags[tagId] ? userTags[tagId].selected : false, count: pltHeaders.length, pltHeaders}
    });

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.userTags = uesrTagsSummary;
    }));
  }

  /** @ToRefactor **/
  assignPltsToTag(ctx: StateContext<WorkspaceModel>, payload: any) {
    console.log(payload);
    switch (payload.type) {
      case 'assignOrRemove':
        return this.pltApi.assignPltsToTag(_.omit(payload, 'wsIdentifier')).pipe(
          mergeMap((tags) => {
            console.log(payload);
            return ctx.dispatch(new fromPlt.assignPltsToTagSuccess({
              workspaceId: payload.wsIdentifier.split('-')[0],
              uwYear: payload.wsIdentifier.split('-')[1]
            }));
          })
        );
      case 'createTag':
        return this.pltApi.creatUserTag(_.omit(payload, 'wsIdentifier')).pipe(
          mergeMap((userTag) => {
            return ctx.dispatch(new fromPlt.CreateTagSuccess({
              wsIdentifier: payload.wsIdentifier,
              userTag
            }))
          }),
          catchError(() => of(new fromPlt.assignPltsToTagFail()))
        )
    }
  }

  createUserTagSuccess(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {
      userTag,
      wsIdentifier
    } = payload;

    const {
      data,
      userTags
    } = ctx.getState().content[wsIdentifier].pltManager;

    let newData = {};

    _.forEach(userTag.pltHeaders, header => {
      newData[header.id] = {
        ...data[header.id],
        userTags: [...data[header.id].userTags, userTag]
      }
    });


    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.data = {...data, ...newData};
      draft.content[wsIdentifier].pltManager.userTags = {
        ...userTags,
        [userTag.tagId]: {...userTag, count: userTag.pltHeaders.length, selected: false}
      }
    }));
  }

  assignPltsToTagSuccess(ctx: StateContext<WorkspaceModel>, payload: any) {
    ctx.dispatch(new fromPlt.loadAllPlts({
      params: {workspaceId: payload.workspaceId, uwy: payload.uwYear}, wsIdentifier: payload.workspaceId
        + "-" + payload.uwYear
    }))
  }

  deleteUserTag(ctx: StateContext<WorkspaceModel>, payload: any) {
    return this.pltApi.deleteUserTag(payload.userTagId).pipe(
      mergeMap(() => ctx.dispatch(new fromPlt.deleteUserTagSuccess(payload)))
    )
  }

  deleteUserTagFromPlts(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {
      userTagId,
      wsIdentifier
    } = payload;

    const {
      data,
      userTags
    } = ctx.getState().content[wsIdentifier].pltManager;

    console.log(payload);
    console.log(data,userTags)

    let newData = {};

    _.forEach(userTags[userTagId].pltHeaders, (plt) => {
      newData[plt.id] = {
        ...data[plt.id],
        userTags: _.filter(data[plt.id].userTags, (userTag: any) => userTag.tagId !== userTagId)
      }
    });

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.data = {...data, ...newData};
      draft.content[wsIdentifier].pltManager.userTags = _.omit(userTags, userTagId)
    }));
  }

  deletePlt(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {
      pltIds,
      wsIdentifier
    } = payload;

    const {
      data
    } = ctx.getState().content[wsIdentifier].pltManager;

    let newData = {};
    let ls = JSON.parse(localStorage.getItem('deletedPlts')) || {};

    _.forEach(pltIds, k => {
      newData[k] = {...data[k], deleted: true, selected: false}
      ls = _.merge({}, ls, {[k]: {deleted: true, deletedBy: 'DEV', deletedAt: moment.now()}})
    });

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.data = {...data, ...newData}
    }));

    localStorage.setItem('deletedPlts', JSON.stringify(ls))
  }

  renameTag(ctx: StateContext<WorkspaceModel>, payload: any) {
    return this.pltApi.editTag(payload.tag).pipe(
      mergeMap(tag => ctx.dispatch(new fromPlt.editTagSuccess({
        wsIdentifier: payload.workspaceId + '-' + payload.uwy,
        tag
      }))),
      catchError(err => ctx.dispatch(new fromPlt.editTagFail()))
    )
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
    const {
      pltIds,
      wsIdentifier
    } = payload;

    const {
      data
    } = ctx.getState().content[wsIdentifier].pltManager;

    let newData = {};
    let ls = JSON.parse(localStorage.getItem('deletedPlts')) || {};

    _.forEach(pltIds, k => {

      newData[k] = {...data[k], deleted: false, selected: false}
      ls = _.omit(ls, `${k}`)
    });

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].pltManager.data = {...data, ...newData};
    }));

    localStorage.setItem('deletedPlts', JSON.stringify(ls));
  }

  private _appendPltMetaData(data,plt, deletedPlts = JSON.parse(localStorage.getItem('deletedPlts')) || {}) {
    return ({
      ...plt,
      selected: data[plt.pltId] ? data[plt.pltId].selected : undefined,
      visible: true,
      tagFilterActive: false,
      opened: false,
      deleted: deletedPlts[plt.pltId] ? deletedPlts[plt.pltId].deleted : undefined,
      deletedBy: deletedPlts[plt.pltId] ? deletedPlts[plt.pltId].deletedBy : undefined,
      deletedAt: deletedPlts[plt.pltId] ? deletedPlts[plt.pltId].deletedAt : undefined,
    });
  }

  mappingColumns= {
    'Published': 'isScorCurrent',
    'Priced': 'isScorDefault',
    'Accumulated': 'isScorGenerated'
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

    if(_.keys(activeStatus).length) {
      _.forEach(data, (plt,k) => {

        newData[k]= {...plt, visible: _.some(activeStatus, (v,statue) => plt[this.mappingColumns[statue]])};

      });

      ctx.patchState(produce(ctx.getState(), draft => {
        draft.content[wsIdentifier].pltManager.data = {...data, ...newData};
      }));
    }

  }

  addNewTag(ctx: StateContext<WorkspaceModel>, payload: any) {
    return this.pltApi.creatUserTag(payload)
      .pipe(
        mergeMap( userTag => of(userTag))
      )
  }

  deleteTag(ctx: StateContext<WorkspaceModel>, payload: any) {
    return this.pltApi.deleteUserTag(payload)
      .pipe(
        mergeMap( userTag => of(userTag))
      )
  }
}
