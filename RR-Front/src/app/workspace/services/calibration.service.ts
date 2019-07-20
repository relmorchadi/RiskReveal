import {Injectable} from '@angular/core';
import {NgxsOnInit, StateContext, Store} from "@ngxs/store";
import * as fromPlt from "../store/actions";
import {deselectAll, PatchCalibrationStateAction} from "../store/actions";
import {catchError, map, mergeMap, tap} from "rxjs/operators";
import * as _ from "lodash";
import {PltApi} from "./plt.api";
import {
  ADJUSTMENT_TYPE,
  ADJUSTMENTS_ARRAY,
  DATA,
  LIST_OF_DISPLAY_PLTS,
  LIST_OF_PLTS,
  PLT_COLUMNS,
  PURE,
  SYSTEM_TAGS,
  USER_TAGS
} from "../containers/workspace-calibration/data";
import produce from "immer";
import {ActivatedRoute} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class CalibrationService implements NgxsOnInit {

  status = ['in progress', 'valid', 'locked', 'requires regeneration', 'failed'];
  ctx = null;
  prefix = null;

  constructor(private store$: Store, private pltApi: PltApi, private route$: ActivatedRoute) {

  }

  ngxsOnInit(ctx?: StateContext<any>): void | any {
    this.route$.params.pipe(
      map(({wsId, year}) => {
        this.prefix = wsId + '-' + year
      }));
  }
  getRandomInt(min = 0, max = 4) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }

  loadAllPltsFromCalibration(ctx: StateContext<any>, payload: any) {
    this.ctx = ctx;
    const {params} = payload;
    this.prefix = params.workspaceId + '-' + params.uwy;

    const ls = JSON.parse(localStorage.getItem('deletedPlts')) || {};
    return this.pltApi.getAllPlts(params)
      .pipe(
        mergeMap((data) => {
          ctx.patchState(produce(ctx.getState(), draft => {
            draft.content[this.prefix].calibration.data = Object.assign({},
              {
                ...ctx.getState().data,
                [this.prefix]: _.merge({},
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
                      calibrate: true
                    }
                  }))
                )
              });
            draft.content[this.prefix].calibration.filters = {
              systemTag: [],
              userTag: []
            }
          }));
          return ctx.dispatch(new fromPlt.loadAllPltsFromCalibrationSuccess({userTags: data.userTags}));
        }),
        catchError(err => ctx.dispatch(new fromPlt.loadAllPltsFromCalibrationFail()))
      );
  }

  loadAllPltsFromCalibrationSuccess(ctx: StateContext<any>, payload: any) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.loading = false;
      ctx.dispatch(new fromPlt.constructUserTagsFromCalibration({userTags: payload.userTags}))
    }));
  }

  constructUserTags(ctx: StateContext<any>, payload: any) {
    const {
      data,
      userTags
    } = ctx.getState()

    let uesrTagsSummary = {};


    _.forEach(payload.userTags, (payloadTag) => {

      const {
        tagId,
        pltHeaders,
        ...rest
      } = payloadTag

      uesrTagsSummary[tagId] = {tagId, ...rest, selected: false, count: pltHeaders.length, pltHeaders}
    })
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.userTags = uesrTagsSummary;
    }));

  }

  setFilterPlts(ctx: StateContext<any>, payload: any) {
    const state = ctx.getState();
    const {
      filters
    } = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.filters = _.assign({}, state.filters, filters);
    }));

    return ctx.dispatch(new fromPlt.FilterPltsByUserTagsFromCalibration(payload));

  }

  FilterPlts(ctx
               :
               StateContext<any>, payload
               :
               any
  ) {
    const state = ctx.getState();
    const {
      wsIdentifier
    } = payload;
    const {
      filters
    } = state;

    let newData = {};

    if (filters.userTag.length > 0) {
      _.forEach(state.data[wsIdentifier], (plt: any, k) => {
        if (_.some(filters.userTag, (userTag) => _.find(plt.userTags, tag => tag.tagId == userTag))) {
          newData[k] = {...plt, visible: true};
        } else {
          newData[k] = {...plt, visible: false};
        }
      });
    } else {
      _.forEach(state.data[wsIdentifier], (plt, k) => {
        newData[k] = {...plt, visible: true};
      });
    }
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.data = {[wsIdentifier]: newData}
    }));
  }

  SelectPlts(payload
               :
               any
  ) {
    const state = this.ctx.getState();
    console.log(payload)
    const {
      plts,
      wsIdentifier
    } = payload;


    console.log(plts);

    let inComingData = {};

    _.forEach(plts, (v, k) => {
      inComingData[k] = {
        selected: v.type
      };
    });
    this.ctx.patchState(produce(this.ctx.getState(), draft => {
      draft.content[this.prefix].calibration.data = _.merge({}, state.data, {[wsIdentifier]: inComingData})
    }));
  }

  calibrateSelectPlts(ctx: StateContext<any>, payload: any) {
    const state = ctx.getState();
    const {
      plts,
      wsIdentifier
    } = payload;

    console.log(plts);

    let inComingData = {};

    _.forEach(plts, (v, k) => {
      inComingData[k] = {
        calibrate: v.type
      };
    });
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.data = _.merge({}, state.data, {[wsIdentifier]: inComingData})
    }));
  }

  initCalibrationData(ctx
                        :
                        StateContext<any>, payload
                        :
                        any
  ) {

    ctx.patchState({
      content: {
        [this.prefix]: {
          calibration: {
            adjustementType: _.assign({}, ADJUSTMENT_TYPE),
            pure: _.assign({}, PURE),
            systemTags: _.assign({}, SYSTEM_TAGS),
            userTags: _.assign({}, USER_TAGS),
            allAdjsArray: _.assign({}, ADJUSTMENTS_ARRAY),
            listOfPlts: _.assign({}, LIST_OF_PLTS),
            listOfDisplayPlts: _.assign({}, LIST_OF_DISPLAY_PLTS),
            data: _.assign({}, DATA),
            pltColumns: _.assign({}, PLT_COLUMNS),
          }
        }
      }
    });

  }

  setFilterCalibration(ctx
                         :
                         StateContext<any>, payload
                         :
                         any
  ) {
    const state = ctx.getState();

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.filters = _.assign({}, state.filters, payload)
    }));

  }

  expandPltSection(ctx: StateContext<any>, payload: any) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.extendPltSection = payload
    }));
  }

  collapseTags(ctx: StateContext<any>, payload: any) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.collapseTags = payload
    }));
  }

  saveAdjustment(ctx
                   :
                   StateContext<any>, payload
                   :
                   any
  ) {
    const state = ctx.getState();
    let adjustement = payload.adjustement;
    let adjustementType = payload.adjustementType;
    let columnPosition = payload.columnPosition;
    let today = new Date();
    let numberAdjs = today.getMilliseconds() + today.getSeconds() + today.getHours();
    adjustement.id = numberAdjs;
    if (adjustementType.id == 1) {
      adjustement.linear = false;
      adjustement.value = columnPosition;
    } else {
      adjustement.linear = true;
      adjustement.value = adjustementType.abv;
    }
    let newAdj = {...adjustement};
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.adjustments = [...state.adjustments, newAdj]
    }));

  }

  dropThreadAdjustment(ctx
                         :
                         StateContext<any>, payload
                         :
                         any
  ) {
    let adjustmentArray = payload.adjustmentArray;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.adjustments = [...adjustmentArray]
    }));
  }

  saveAdjModification(ctx
                        :
                        StateContext<any>, payload
                        :
                        any
  ) {
    const state = ctx.getState();
    let adjustement = payload.adjustement;
    let adjustementType = payload.adjustementType;
    let columnPosition = payload.columnPosition;

    if (adjustementType.id == 1) {
      adjustement.linear = false;
      adjustement.value = columnPosition;
    } else {
      adjustement.linear = true;
      adjustement.value = adjustementType.abv;
    }
    let newAdj = {...adjustement};
    let index = _.findIndex(state.adjustments, {id: newAdj.id});
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.adjustments = [..._.merge([], state.adjustments, {[index]: newAdj})]
    }));

  }

  replaceAdjustement(ctx
                       :
                       StateContext<any>, payload
                       :
                       any
  ) {
    const state = ctx.getState();

    let adjustement = payload.adjustement;
    let adjustementType = payload.adjustementType;
    let columnPosition = payload.columnPosition;
    let pltId = payload.pltId;
    let all = payload.all;

    if (adjustementType.id == 1) {
      adjustement.linear = false;
      adjustement.value = columnPosition;
    } else {
      adjustement.linear = true;
      adjustement.value = adjustementType.abv;
    }
    let adjustmentApplication = [];
    let newAdj = {...adjustement};
    let index = {};
    _.forEach(pltId, (value) => {
      adjustmentApplication.push({
        pltId: value,
        adj: newAdj
      })
      index[value] = _.findIndex(state.adjustmentApplication, {pltId: value});
    })
    if (all) {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.content[this.prefix].calibration.adjustmentApplication = [..._.merge([], state.adjustmentApplication, adjustmentApplication)]
      }));
    } else {
      console.log(index);
      ctx.patchState(produce(ctx.getState(), draft => {
        _.forEach(adjustmentApplication, (row) => {
          draft.content[this.prefix].calibration.adjustmentApplication[index[row.pltId]] = row;
        });
      }));
    }


    /*ctx.patchState(produce(ctx.getState(), draft => {
      draft.adjustments[12]=
    }));*/

  }

  saveAdjustmentInPlt(ctx
                        :
                        StateContext<any>, payload
                        :
                        any
  ) {
    const state = ctx.getState();
    let adjustement = payload.adjustement;
    let adjustementType = payload.adjustementType;
    let columnPosition = payload.columnPosition;
    let pltId = payload.pltId;
    let today = new Date();
    let numberAdjs = today.getMilliseconds() + today.getSeconds() + today.getHours();
    adjustement.id = numberAdjs;
    if (adjustementType.id == 1) {
      adjustement.linear = false;
      adjustement.value = columnPosition;
    } else {
      adjustement.linear = true;
      adjustement.value = adjustementType.abv;
    }
    let newAdj = {...adjustement};
    let adjustmentApplication = {
      pltId: pltId,
      adj: newAdj
    }
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.adjustmentApplication = [...state.adjustmentApplication, adjustmentApplication,]
    }));
  }

  applyAdjustment(ctx
                    :
                    StateContext<any>, payload
                    :
                    any
  ) {
    const state = ctx.getState();
    let adjustement = payload.adjustement;
    let adjustementType = payload.adjustementType;
    let columnPosition = payload.columnPosition;
    let pltId = payload.pltId;
    let today = new Date();
    let numberAdjs = today.getMilliseconds() + today.getSeconds() + today.getHours();
    adjustement.ref = adjustement.id;
    adjustement.id = numberAdjs;
    if (adjustementType.id == 1) {
      adjustement.linear = false;
      adjustement.value = columnPosition;
    } else {
      adjustement.linear = true;
      adjustement.value = adjustementType.abv;
    }
    let adjustmentApplication = _.merge({}, state.adjustmentApplication);
    console.log(adjustmentApplication);
    let newAdj = {...adjustement};

    _.forEach(pltId, (value) => {
      if (adjustmentApplication[value] !== undefined) {
        adjustmentApplication[value].push(newAdj);
      } else {
        adjustmentApplication[value] = [newAdj]
      }
    })
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.adjustmentApplication = {...adjustmentApplication}
    }));
  }

  dropAdjustment(ctx
                   :
                   StateContext<any>, payload
                   :
                   any
  ) {
    const state = ctx.getState();

    let pltId = payload.pltId;
    let newAdj = {...payload.adjustement};

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.adjustmentApplication = [...state.adjustmentApplication, {
        pltId: pltId,
        adj: newAdj
      }]
    }));
  }

  deleteAdjsApplication(ctx
                          :
                          StateContext<any>, payload
                          :
                          any
  ) {
    const state = ctx.getState();
    let index = payload.index;
    let pltId = payload.pltId;
    let adjustmentApplication = _.merge([], state.adjustmentApplication);
    adjustmentApplication[pltId].splice(index, 1);
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.adjustmentApplication = [...adjustmentApplication]
    }));
  }

  deleteAdjustment(ctx
                     :
                     StateContext<any>, payload
                     :
                     any
  ) {
    const state = ctx.getState();
    let adjustement = payload.adjustment;

    let index = _.findIndex(state.adjustments, adjustement);
    let adjustments = _.merge([], state.adjustments);

    adjustments.splice(index, 1);
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.adjustments = [...adjustments]
    }));
  }

  saveSelectedPlts(ctx
                     :
                     StateContext<any>, payload
                     :
                     any
  ) {
    const state = ctx.getState();
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.selectedPLT = _.merge({}, payload)
    }));
  }

  saveAdjustmentApplication(ctx
                              :
                              StateContext<any>, payload
                              :
                              any
  ) {
    const state = ctx.getState();
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.adjustmentApplication = _.merge({}, payload)
    }));

  }

  patchSearchState(ctx: StateContext<any>, payload: any) {
    if (_.isArray(payload))
      payload.forEach(item => ctx.patchState({[item.key]: item.value}));
    else
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.content[this.prefix].calibration[payload.key] = payload.value
      }));
  }

  selectRow(ctx
              :
              StateContext<any>, payload
              :
              any
  ) {
    let state = ctx.getState();
    const event = payload.event;
    const datatable = payload.datatable;
    let pure = _.merge({}, state.pure);
    let index;
    if (event.ctrlKey) {
      console.log('ctrl')
      ctx.dispatch(new PatchCalibrationStateAction({key: 'lastCheckedBool', value: true}));
      _.forIn(pure.dataTable, function (value, key) {
        if (_.findIndex(value.thread, datatable) != -1) {
          index = _.findIndex(value.thread, datatable);
          if (value.thread[index].checked == false) {
            value.thread[index].checked = true;
          } else {
            value.thread[index].checked = false;
          }
        }
      })
    } else if (event.shiftKey) {
      console.log('shift')

      ctx.dispatch(new PatchCalibrationStateAction({key: 'lastCheckedBool', value: true}));
      let between = false;
      if (_.isNil(state.firstChecked)) {
        ctx.dispatch(new PatchCalibrationStateAction({
          key: 'firstChecked',
          value: state.pure.dataTable[0].thread[0]
        }));
      }
      let lastChecked = state.firstChecked;
      // this.store$.dispatch(new deselectAll({lastChecked:lastChecked}))
      if (lastChecked == datatable) {
        return;
      }
      _.forIn(pure.dataTable, function (value, key) {
        _.forIn(value.thread, function (plt, key) {
          if (between) {
            plt.checked = true
          }
          if (plt == lastChecked || plt == datatable) {
            plt.checked = true;
            between = !between;
          }
        })
      })
    } else {
      console.log('else')
      let checked = datatable.checked;
      let booShift = state.lastCheckedBool;
      console.log('booShift', booShift);
      console.log('checked', checked);
      return ctx.dispatch(new deselectAll({lastChecked: null})).pipe(
        tap(() => {
            ctx.dispatch(new PatchCalibrationStateAction({key: 'firstChecked', value: datatable}));
            _.forIn(pure.dataTable, function (value, key) {
              if (_.findIndex(value.thread, datatable) != -1) {
                index = _.findIndex(value.thread, datatable);
                if (!booShift) {
                  value.thread[index].checked = !checked;
                } else {
                  value.thread[index].checked = true;
                }
              }
            })
          ctx.patchState(produce(ctx.getState(), draft => {
            draft.content[this.prefix].calibration.pure = pure
          }));
            this.store$.dispatch(new PatchCalibrationStateAction({key: 'lastCheckedBool', value: false}));
          }
        )
      );
    }
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.pure = pure
    }));
    this.store$.dispatch(new PatchCalibrationStateAction({key: 'lastCheckedBool', value: false}));
  }

  deselectAll(ctx
                :
                StateContext<any>, payload
                :
                any
  ) {
    let state = ctx.getState();
    let pure = _.merge({}, state.pure);
    const lastChecked = payload.lastChecked;
    this.store$.dispatch(new PatchCalibrationStateAction({key: 'firstChecked', value: lastChecked}));
    _.forIn(pure.dataTable, function (value, key) {
      _.forIn(value.thread, function (plt, key) {
        plt.checked = false;
      })
    })
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.pure = pure
    }));
  }

}
