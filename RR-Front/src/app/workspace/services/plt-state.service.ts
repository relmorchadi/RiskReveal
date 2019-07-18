import { Injectable } from '@angular/core';
import {StateContext} from '@ngxs/store';
import * as fromPlt from '../store/actions/plt_main.actions';
import {catchError, mergeMap} from 'rxjs/operators';
import * as _ from 'lodash';
import {PltApi} from './plt.api';
import {pltMainModel} from '../model';
import {of} from 'rxjs';
import * as moment from 'moment';

@Injectable({
  providedIn: 'root'
})
export class PltStateService {

  constructor(private pltApi: PltApi) { }

  LoadAllPlts(ctx: StateContext<pltMainModel>, payload: any){
    const {
      wsIdentifier,
      params
    } = payload;


    ctx.patchState({
      data: {
        [params.workspaceId + '-' + params.uwy]: {
          ...ctx.getState().data[params.workspaceId + '-' + params.uwy],
          loading: true
        }
      }
    });

    console.log('ls', JSON.parse(localStorage.getItem('deletedPlts')));

    const ls = JSON.parse(localStorage.getItem('deletedPlts')) || {};

    return this.pltApi.getAllPlts(params)
      .pipe(
        mergeMap((data) => {
          ctx.patchState({
            data: Object.assign({},
              {
                ...ctx.getState().data,
                [params.workspaceId + '-' + params.uwy]: _.merge({},
                  ...data.plts.map(plt => ({
                    [plt.pltId]: {
                      ...plt,
                      selected: false,
                      visible: true,
                      tagFilterActive: false,
                      opened: false,
                      deleted: ls[plt.pltId] ? ls[plt.pltId].deleted : undefined,
                      deletedBy: ls[plt.pltId] ? ls[plt.pltId].deletedBy : undefined,
                      deletedAt: ls[plt.pltId] ? ls[plt.pltId].deletedAt : undefined,
                      status: this.status[this.getRandomInt()],
                      newPlt: Math.random() >= 0.5,
                      EPM: ['1,080,913', '151,893', '14.05%'],
                    }
                  })),{
                    filters: {
                      systemTag: [],
                      userTag: []
                    }
                  }
                ),
              }
            ),
          });
          return ctx.dispatch(new fromPlt.loadAllPltsSuccess({wsIdentifier: params.workspaceId + '-' + params.uwy,userTags: data.userTags}));
        }),
        catchError(err => ctx.dispatch(new fromPlt.loadAllPltsFail()))
      );
  }

  getRandomInt(min = 0, max = 4) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }

  status = ['in progress', 'valid', 'locked', 'requires regeneration', 'failed'];

  setCloneConfig(ctx: StateContext<pltMainModel>, payload: any) {
    const {
      wsIdentifier,
      cloneConfig
    } = payload;

    ctx.patchState({
      data: {
        [wsIdentifier] : {
          ...ctx.getState().data[wsIdentifier],
          cloneConfig: cloneConfig
        }
      }
    })
  }

  LoadAllPltsSuccess(ctx: StateContext<pltMainModel>, payload: any) {
    const {
      wsIdentifier
    } = payload;

    ctx.patchState({
      data: {
        [wsIdentifier] : {
          ...ctx.getState().data[wsIdentifier],
          loading: false
        }
      }
    })

    ctx.dispatch(new fromPlt.constructUserTags({wsIdentifier: payload.wsIdentifier,userTags: payload.userTags}))
  }

  SelectPlts(ctx: StateContext<pltMainModel>, payload: any) {
    const state = ctx.getState();
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

    ctx.patchState({
      data: _.merge({}, state.data, {[wsIdentifier]: inComingData})
    });
  }

  OpenPltinDrawer(ctx: StateContext<pltMainModel>, payload: any) {
    const state = ctx.getState();

    ctx.patchState({
      data: _.merge({}, state.data, {[payload.wsIdentifier]: {[payload.pltId]: {opened: true}}})
    });
  }

  ClosePLTinDrawer(ctx: StateContext<pltMainModel>, payload: any) {
    const state = ctx.getState();
    const {
      pltId,
      wsIdentifier
    } = payload;

    ctx.patchState({
      data: _.merge({}, state.data, {[wsIdentifier]: {[pltId]: {opened: false}}})
    });
  }

  setFilterPlts(ctx: StateContext<pltMainModel>, payload: any) {
    const state = ctx.getState();
    const {
      filters,
      wsIdentifier
    } = payload;

    ctx.patchState({
      data: {
        [wsIdentifier]: {
          ...state.data[wsIdentifier],
          filters: _.assign({}, state.data[wsIdentifier].filters, filters)
        }
      }
    });

    return ctx.dispatch(new fromPlt.FilterPltsByUserTags(payload));
  }

  filterPlts(ctx: StateContext<pltMainModel>, payload: any) {
    const state = ctx.getState();
    const {
      wsIdentifier
    } = payload;
    const {
      data
    } = state;

    let newData = {};

    if (data[wsIdentifier].filters.userTag.length > 0) {
      _.forEach(data[wsIdentifier], (plt: any, k) => {
        if (_.some(data[wsIdentifier].filters.userTag, (userTag) => _.find(plt.userTags, tag => tag.tagId == userTag))) {
          newData[k] = {...plt, visible: true};
        } else {
          newData[k] = {...plt, visible: false};
        }
      });
    } else {
      _.forEach(data[wsIdentifier], (plt, k) => {
        newData[k] = {...plt, visible: true};
      });
    }

    ctx.setState({
      data: {...data, [wsIdentifier]: newData}
    });
  }

  setTableSortAndFilter(ctx: StateContext<pltMainModel>, payload: any) {
    const {
      sortData,
      filterData
    } = payload;

    const {
      data
    } = ctx.getState();

    const sortDataKeys = _.keys(sortData);
    const filterDataKeys = _.keys(filterData)
    let res = {...data};


    if (filterDataKeys.length > 0) {

      res = _.filter(data, row =>
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

    ctx.patchState({
      data: res
    })
  }

  constructUserTags(ctx: StateContext<pltMainModel>, payload: any) {
    const {
      data,
    } = ctx.getState()

    const {
      wsIdentifier
    } = payload;

    let uesrTagsSummary = {};


    _.forEach(payload.userTags, (payloadTag) => {

      const {
        tagId,
        pltHeaders,
        ...rest
      } = payloadTag

      uesrTagsSummary[tagId] = {tagId, ...rest, selected: false, count: pltHeaders.length, pltHeaders}
    })

    ctx.patchState({
      data: {
        [wsIdentifier]: {
          ...data[wsIdentifier],
          userTags: uesrTagsSummary
        }
      }
    })
  }

  assignPltsToTag(ctx: StateContext<pltMainModel>, payload: any) {
    switch (payload.type) {
      case 'assignOrRemove':
        return this.pltApi.assignPltsToTag(_.omit(payload, 'wsIdentifier')).pipe(
          mergeMap((tags) => {
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

  createUserTagSuccess(ctx: StateContext<pltMainModel>, payload: any) {
    const {
      data
    } = ctx.getState()

    const {
      userTag,
      wsIdentifier
    } = payload;

    let newData = {};

    _.forEach(userTag.pltHeaders, header => {
      newData[header.id] = {
        ...data[wsIdentifier][header.id],
        userTags: [...data[wsIdentifier][header.id].userTags, userTag]
      }
    })

    ctx.patchState({
      data: _.merge({}, data, {
        [wsIdentifier]: {
          newData,
          userTags: _.merge({}, data[wsIdentifier].userTags, {
            [userTag.tagId]: {
              ...userTag,
              count: userTag.pltHeaders.length,
              selected: false
            }
          })
        }
      }),
    })
  }

  assignPltsToTagSuccess(ctx: StateContext<pltMainModel>, payload: any) {
    ctx.dispatch(new fromPlt.loadAllPlts({params: {workspaceId: payload.workspaceId, uwy: payload.uwYear}}))
  }

  deleteUserTag(ctx: StateContext<pltMainModel>, payload: any) {
    return this.pltApi.deleteUserTag(payload.userTagId).pipe(
      mergeMap(() => ctx.dispatch(new fromPlt.deleteUserTagSuccess(payload)))
    )
  }

  deleteUserTagFromPlts(ctx: StateContext<pltMainModel>, payload: any) {
    const {
      data
    } = ctx.getState();

    const {
      userTagId,
      wsIdentifier
    } = payload;

    let newData = {};

    _.forEach(data[wsIdentifier].userTags[userTagId].pltHeaders, (plt) => {
      newData[plt.id] = {
        ...data[wsIdentifier][plt.id],
        userTags: _.filter(data[wsIdentifier][plt.id].userTags, (userTag: any) => userTag.tagId !== userTagId)
      }
    })
    console.log(newData);

    ctx.patchState({
      data: {...data, ...{[wsIdentifier]: {...data[wsIdentifier], ...newData, userTags: _.omit(data[wsIdentifier].userTags, userTagId)}}},
    })
  }

  deletePlt(ctx: StateContext<pltMainModel>, payload: any) {
    const {
      pltIds,
      wsIdentifier
    } = payload;

    const {
      data
    } = ctx.getState();

    let newData = {};
    let ls = JSON.parse(localStorage.getItem('deletedPlts')) || {};

    _.forEach(pltIds, k => {
      newData[k] = {...data[wsIdentifier][k], deleted: true, selected: false}
      ls = _.merge({}, ls, {[k]: {deleted: true, deletedBy: 'DEV', deletedAt: moment.now()}})
    });

    ctx.patchState({
      data: _.merge({}, data, {[wsIdentifier]: newData})
    })

    localStorage.setItem('deletedPlts', JSON.stringify(ls))
  }

  renameTag(ctx: StateContext<pltMainModel>, payload: any) {
    return this.pltApi.editTag(payload.tag).pipe(
      mergeMap(tag => ctx.dispatch(new fromPlt.editTagSuccess({
        wsIdentifier: payload.workspaceId + '-' + payload.uwy,
        tag
      }))),
      catchError(err => ctx.dispatch(new fromPlt.editTagFail()))
    )
  }

  renameTagSuccess(ctx: StateContext<pltMainModel>, payload: any) {
    const {
      data
    } = ctx.getState();

    const {
      tag: {
        tagId,
        tagName,
        tagColor,
        pltHeaders
      },
      wsIdentifier
    } = payload;

    let newData = {};

    _.forEach(pltHeaders, pltId => {
      const {
        id
      } = pltId;

      let index = _.findIndex(data[wsIdentifier][id].userTags, (tag: any) => tag.tagId === tagId);

      newData[id] = {
        ...data[wsIdentifier][id],
        userTags: _.merge([], data[wsIdentifier][id].userTags, {
          [index]: {
            ...data[wsIdentifier][id].userTags[index],
            tagName,
            tagColor
          }
        })
      }
    })

    ctx.patchState({
      data: _.merge({}, data, {[wsIdentifier]: {...data[wsIdentifier], ...newData, userTags: _.merge({},
            data[wsIdentifier].userTags,
            {
              [tagId]: {
                tagName,
                tagColor
              }
            })
      }}),
    })
  }

  restorePlt(ctx: StateContext<pltMainModel>, payload: any) {
    const {
      pltIds,
      wsIdentifier
    } = payload;

    const {
      data
    } = ctx.getState();

    let newData = {};
    let ls = JSON.parse(localStorage.getItem('deletedPlts')) || {};

    _.forEach(pltIds, k => {
      newData[k] = {...data[wsIdentifier][k], deleted: false, selected: false}
      ls = _.omit(ls, `${k}`)
    });

    ctx.patchState({
      data: _.merge({}, data, {[wsIdentifier]: newData})
    });

    localStorage.setItem('deletedPlts', JSON.stringify(ls));
  }
}
