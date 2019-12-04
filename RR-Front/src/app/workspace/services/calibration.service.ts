import {Injectable} from '@angular/core';
import {NgxsOnInit, StateContext, Store} from "@ngxs/store";
import * as fromPlt from "../store/actions";
import {applyAdjustment} from "../store/actions";
import {map} from "rxjs/operators";
import * as _ from "lodash";
import {PltApi} from "./api/plt.api";
import {
  ADJUSTMENT_APPLICATION,
  ADJUSTMENT_TYPE,
  ADJUSTMENTS_ARRAY,
  API_RESPONSE,
  DATA,
  LIST_OF_DISPLAY_PLTS,
  LIST_OF_PLTS,
  PLT_COLUMNS,
  PLT_DATA,
  PURE,
  SYSTEM_TAGS,
  USER_TAGS
} from "../containers/workspace-calibration/data";
import produce from "immer";
import {ActivatedRoute} from "@angular/router";
import {WorkspaceModel} from "../model";

@Injectable({
  providedIn: 'root'
})
export class CalibrationService implements NgxsOnInit {

  status = ['in progress', 'valid', 'locked', 'requires regeneration', 'failed', 'new'];
  pure = ['SPLTH-000735433', 'SPLTH-000735434', 'SPLTH-000735435'];
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

  getRandomInt(min = 0, max = 5) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }

  getPercentage() {
    let random = Math.floor(Math.random() * 199) - 99;
    return random;
  }

  /*Load Plts*/
  loadAllPltsFromCalibration(ctx: StateContext<any>, payload: any) {

    const {params} = payload;
    this.ctx = ctx;
    this.prefix = params.workspaceId + '-' + params.uwy;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.data[this.prefix] = PLT_DATA;
      draft.content[this.prefix].calibration.filters = {
        systemTag: [],
        userTag: []
      }
    }));
    return ctx.dispatch(new fromPlt.loadAllPltsFromCalibrationSuccess({userTags: API_RESPONSE.userTags}));
    /*
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
                       EPM: [this.getRandomInt(900000, 2000000).toString(), this.getRandomInt(90000, 1000000), this.getPercentage()],
                       calibrate: true,
                       toCalibrate: true,
                       pureId:this.pure[this.getRandomInt(0,2)],
                       threads: [
                         {...ONE_PLT},
                         {...ONE_PLT},
                       ].map(thread => ({
                           ...thread,
                           pltId: "SPLTH-000"+this.getRandomInt(7000,8000),
                           selected: false,
                           visible: true,
                           tagFilterActive: false,
                           opened: false,
                           deleted: ls[thread.pltId] ? ls[thread.pltId].deleted : undefined,
                           deletedBy: ls[thread.pltId] ? ls[thread.pltId].deletedBy : undefined,
                           deletedAt: ls[thread.pltId] ? ls[thread.pltId].deletedAt : undefined,
                           status: this.status[this.getRandomInt()],
                           newPlt: Math.random() >= 0.5,
                           EPM: [this.getRandomInt(900000, 2000000).toString(), this.getRandomInt(90000, 1000000), this.getPercentage()],
                           calibrate: true,
                           toCalibrate: true,
                           pureId: plt.pltId,
                       }))
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
       );*/
  }

  /*Load Plts*/
  loadAllAdjustmentApplication(ctx: StateContext<any>, payload: any) {
    const {params} = payload;
    this.ctx = ctx;
    this.prefix = params.workspaceId + '-' + params.uwy;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.adjustmentApplication = ADJUSTMENT_APPLICATION;
    }));
  }

  loadAllPltsFromCalibrationSuccess(ctx: StateContext<any>, payload: any) {
    const state = ctx.getState();
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.loading = false;
      ctx.dispatch(new fromPlt.constructUserTagsFromCalibration({userTags: payload.userTags}))
    }));
    /* init adjustments application*/
    let plts = [];
    _.forEach(_.merge({}, state.content[this.prefix].calibration.data[this.prefix]), plt => {
      plts.push(plt);
    })
    console.log(plts);
    let threads = Array.prototype.concat.apply([], plts.map(row => row.threads));


    return ctx.dispatch(new applyAdjustment({
      adjustementType: null,
      adjustement: false,
      columnPosition: null,
      pltId: threads,
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

  FilterPlts(ctx: StateContext<any>, payload: any) {
    const state = ctx.getState();
    const {
      wsIdentifier
    } = payload;
    const {
      filters
    } = state.content[this.prefix].calibration;

    let newData = {};

    if (filters.userTag.length > 0) {
      _.forEach(state.content[this.prefix].calibration.data[wsIdentifier], (plt: any, k) => {
        if (_.some(filters.userTag, (userTag) => _.find(plt.userTags, tag => tag.tagId == userTag))) {
          newData[k] = {...plt, visible: true};
        } else {
          newData[k] = {...plt, visible: false};
        }
      });
    } else {
      _.forEach(state.content[this.prefix].calibration.data[wsIdentifier], (plt, k) => {
        newData[k] = {...plt, visible: true};
      });
    }
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.data = {[wsIdentifier]: newData}
    }));
  }

  /*Toggle Select Plts*/
  SelectPlts(ctx: StateContext<any>, payload: any) {
    const state = ctx.getState();
    const {
      plts,
      wsIdentifier
    } = payload;
    console.log(payload)
    const result = state.content[wsIdentifier].calibration.data[wsIdentifier];
    _.forEach(result, plt => {
      _.forEach(plt.threads, thread => {
        if (plts[thread.pltId])
          thread.selected = plts[thread.pltId].selected;
      })
    })
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.data[wsIdentifier] = _.merge({}, state.content[wsIdentifier].calibration.data[wsIdentifier], result)
    }));
  }

  calibrateSelectPlts(ctx: StateContext<any>, payload: any) {
    const state = ctx.getState();
    const plts = payload.plts;
    const workspace = payload.ws;
    console.log(plts);
    console.log(workspace);
    const result = state.content[workspace].calibration.data[workspace];
    _.forEach(result, plt => {
      _.forEach(plt.threads, thread => {
        if (plts[thread.pltId])
          thread.calibrate = plts[thread.pltId].calibrate;
      })
    });
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[workspace].calibration.data[workspace] = _.merge({}, state.content[workspace].calibration.data[workspace], result)
    }));
  }

  toCalibratePlts(ctx: StateContext<any>, payload: any) {
    const state = ctx.getState();
    const {
      plts,
      wsIdentifier
    } = payload;
    const result = state.content[this.prefix].calibration.data[this.prefix];
    _.forEach(result, plt => {
      _.forEach(plt.threads, thread => {
        if (plts[thread.pltId])
          thread.toCalibrate = plts[thread.pltId].toCalibrate;
      })
    });
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.data[this.prefix] = _.merge({}, state.content[this.prefix].calibration.data[this.prefix], result)
    }));
  }

  initCalibrationData(ctx: StateContext<any>, payload: any) {
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

  setFilterCalibration(ctx: StateContext<any>, payload: any) {
    const state = ctx.getState();

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.filters = _.assign({}, state.content[this.prefix].calibration.filters, payload)
    }));

  }

  expandPltSection(ctx: StateContext<any>, payload: any) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.extendPltSection = payload
    }));
  }

  extendStateToggle(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {scope} = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.extendState = scope === 'init' ? true : !draft.content[this.prefix].calibration.extendState;
    }))
  }

  collapseTags(ctx: StateContext<any>, payload: any) {
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.collapseTags = payload
    }));
  }

  saveAdjustment(ctx: StateContext<any>, payload: any) {
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
      draft.content[this.prefix].calibration.adjustments = [...state.content[this.prefix].calibration.adjustments, newAdj]
    }));

  }

  dropThreadAdjustment(ctx: StateContext<any>, payload: any) {
    let adjustmentArray = payload.adjustmentArray;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.adjustments = [...adjustmentArray]
    }));
  }

  loadAdjsArray(ctx: StateContext<any>, payload: any) {
    let adjustmentArray = payload.adjustmentArray;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.adjustments = [...adjustmentArray]
    }));
  }

  saveAdjModification(ctx: StateContext<any>, payload: any) {
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
    console.log("==================>", newAdj);
    let index = _.findIndex(state.content[this.prefix].calibration.adjustments, {id: newAdj.id});
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.adjustments[index] = newAdj
    }));

  }

  replaceAdjustement(ctx: StateContext<any>, payload: any) {
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
        pltId: value.pltId,
        adj: newAdj
      })
      index[value.pltId] = _.findIndex(state.adjustmentApplication, {pltId: value});
    })
    if (all) {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.content[this.prefix].calibration.adjustmentApplication = [..._.merge([], state.content[this.prefix].calibration.adjustmentApplication.adjustmentApplication, adjustmentApplication)]
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

  saveAdjustmentInPlt(ctx: StateContext<any>, payload: any) {
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

  applyAdjustment(ctx: StateContext<any>, payload: any) {

    const state = ctx.getState();
    let adjustement = payload.adjustement;
    let adjustementType = payload.adjustementType;
    let columnPosition = payload.columnPosition;
    let pltId = payload.pltId;
    if (adjustement) {
      if (pltId !== undefined) {
        let today = new Date();
        let numberAdjs = today.getMilliseconds() + today.getSeconds() + today.getHours();
        adjustement.ref = adjustement.id;
        adjustement.id = numberAdjs;
        if (adjustementType != null && adjustementType.id == 1) {
          adjustement.linear = false;
          adjustement.value = columnPosition;
        } else {
          adjustement.linear = true;
          adjustement.value = adjustementType ? adjustementType.abv : adjustement.value;
        }
        let adjustmentApplication = _.merge({}, state.content[this.prefix].calibration.adjustmentApplication);

        let newAdj = {...adjustement};
        _.forEach(pltId, (value) => {
          if (value.pltId !== undefined) {
            newAdj['id'] = value.pltId + '-' + adjustement['id'];
            if (adjustmentApplication[value.pltId] !== undefined) {
              adjustmentApplication[value.pltId].push({...newAdj});
            } else {
              adjustmentApplication[value.pltId] = [{...newAdj}]
            }
          }
        })
        console.log(adjustmentApplication);
        ctx.patchState(produce(ctx.getState(), draft => {
          draft.content[this.prefix].calibration.adjustmentApplication = {...adjustmentApplication}
        }));
      }
    } else {
      let adjustmentApplication = _.merge({}, state.content[this.prefix].calibration.adjustmentApplication);
      _.forEach(pltId, (value) => {
        if (value.pltId !== undefined) {
          if (adjustmentApplication[value.pltId] == undefined) {
            adjustmentApplication[value.pltId] = []
          }
        }
      });
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.content[this.prefix].calibration.adjustmentApplication = {...adjustmentApplication}
      }));
    }


  }

  dropAdjustment(ctx: StateContext<any>, payload: any) {
    let application = payload.application;
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.content[this.prefix].calibration.adjustmentApplication = application;
      }));
  }

  deleteAdjsApplication(ctx: StateContext<any>, payload: any) {
    const state = ctx.getState();
    let index = payload.index;
    let pltId = payload.pltId;
    let adjustmentApplication = _.merge([], state.content[this.prefix].calibration.adjustmentApplication);
    adjustmentApplication[pltId].splice(index, 1);
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.adjustmentApplication = [...adjustmentApplication]
    }));
  }

  deleteAdjustment(ctx: StateContext<any>, payload: any) {
    const state = ctx.getState();
    let adjustement = payload.adjustment;

    let index = _.findIndex(state.content[this.prefix].calibration.adjustments, adjustement);
    let adjustments = _.merge([], state.content[this.prefix].calibration.adjustments);

    adjustments.splice(index, 1);
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.adjustments = [...adjustments]
    }));
  }

  saveSelectedPlts(ctx: StateContext<any>, payload: any) {
    const state = ctx.getState();
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[this.prefix].calibration.selectedPLT = _.merge({}, payload)
    }));
  }

  saveAdjustmentApplication(ctx: StateContext<any>, payload: any) {
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
}
