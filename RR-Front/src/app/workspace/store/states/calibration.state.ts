import {CalibrationModel} from "../../model";
import {Action, createSelector, NgxsOnInit, Selector, State, StateContext, Store} from "@ngxs/store";
import * as fromPlt from "../actions";
import {deselectAll, PatchCalibrationStateAction, selectRow} from "../actions";
import * as _ from "lodash";
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
} from "../../containers/workspace-calibration/data";
import {tap} from "rxjs/operators";
import {PltApi} from "../../services/plt.api";
import produce from "immer";
import {WorkspaceMain} from "../../../core/model";
import {WorkspaceMainState} from "../../../core/store/states";

const initiaState: CalibrationModel = {
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
  adjustementType: _.assign([], [...ADJUSTMENT_TYPE]),
  pure: _.assign({}, {...PURE}),
  systemTags: _.assign([], [...SYSTEM_TAGS]),
  allAdjsArray: _.assign([], [...ADJUSTMENTS_ARRAY]),
  listOfPlts: _.assign([], [...LIST_OF_PLTS]),
  listOfDisplayPlts: _.assign([], [...LIST_OF_DISPLAY_PLTS]),
  pltColumns: [
    {fields: 'check', header: '', width: '1%', sorted: false, filtred: false, icon: null, extended: true},
    {fields: '', header: 'User Tags', width: '10%', sorted: false, filtred: false, icon: null, extended: true},
    {fields: 'pltId', header: 'PLT ID', width: '12%', sorted: true, filtred: true, icon: null, extended: true},
    {fields: 'pltName', header: 'PLT Name', width: '14%', sorted: true, filtred: true, icon: null, extended: true},
    {fields: 'peril', header: 'Peril', width: '7%', sorted: true, filtred: true, icon: null, extended: false},
    {
      fields: 'regionPerilCode',
      header: 'Region Peril Code',
      width: '13%',
      sorted: true,
      filtred: true,
      icon: null,
      extended: false
    },
    {
      fields: 'regionPerilName',
      header: 'Region Peril Name',
      width: '13%',
      sorted: true,
      filtred: true,
      icon: null,
      extended: false
    },
    {fields: 'grain', header: 'Grain', width: '9%', sorted: true, filtred: true, icon: null, extended: false},
    {
      fields: 'vendorSystem',
      header: 'Vendor System',
      width: '11%',
      sorted: true,
      filtred: true,
      icon: null,
      extended: false
    },
    {fields: 'rap', header: 'RAP', width: '9%', sorted: true, filtred: true, icon: null, extended: false},
    {fields: 'action', header: '', width: '3%', sorted: false, filtred: false, icon: "icon-focus-add", extended: true},
    {fields: 'action', header: '', width: '3%', sorted: false, filtred: false, icon: "icon-note", extended: true}
  ],
};

@State<CalibrationModel>({
  name: 'calibration',
  defaults: initiaState
})
export class CalibrationState implements NgxsOnInit {
  ctx = null;

  constructor(private store$: Store, private pltApi: PltApi) {

  }

  ngxsOnInit(ctx?: StateContext<CalibrationState>): void | any {
    this.ctx = ctx;
  }

  @Selector()
  static getAdjustmentApplication(state: CalibrationModel) {
    return _.get(state, "adjustmentApplication")
  }

  static getLeftNavbarIsCollapsed() {
    return createSelector([WorkspaceMainState], (state: WorkspaceMain) => {
      return _.get(state, "leftNavbarIsCollapsed");
    });
  }


  @Action(fromPlt.initCalibrationData)
  initCalibrationData(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.initCalibrationData) {

    ctx.patchState({
      adjustementType: _.assign({}, ADJUSTMENT_TYPE),
      pure: _.assign({}, PURE),
      systemTags: _.assign({}, SYSTEM_TAGS),
      userTags: _.assign({}, USER_TAGS),
      allAdjsArray: _.assign({}, ADJUSTMENTS_ARRAY),
      listOfPlts: _.assign({}, LIST_OF_PLTS),
      listOfDisplayPlts: _.assign({}, LIST_OF_DISPLAY_PLTS),
      data: _.assign({}, DATA),
      pltColumns: _.assign({}, PLT_COLUMNS),
    });

  }

  @Action(fromPlt.setFilterCalibration)
  setFilterCalibration(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.setFilterCalibration) {
    const state = ctx.getState();

    ctx.patchState({
      filters: _.assign({}, state.filters, payload)
    });

  }

  @Action(fromPlt.extendPltSection)
  expandPltSection(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.extendPltSection) {

    ctx.patchState({
      extendPltSection: payload
    });

  }

  @Action(fromPlt.collapseTags)
  collapseTags(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.collapseTags) {

    ctx.patchState({
      collapseTags: payload
    });

  }

  @Action(fromPlt.saveAdjustment)
  saveAdjustment(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.saveAdjustment) {
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
    ctx.patchState({
      adjustments: [
        ...state.adjustments,
        newAdj,
      ]
    });

  }

  @Action(fromPlt.dropThreadAdjustment)
  dropThreadAdjustment(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.dropThreadAdjustment) {
    let adjustmentArray = payload.adjustmentArray;
    ctx.patchState({
      adjustments: [
        ...adjustmentArray
      ]
    });

  }

  @Action(fromPlt.saveAdjModification)
  saveAdjModification(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.saveAdjModification) {
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
    ctx.patchState({
      adjustments: [...
        _.merge([], state.adjustments, {[index]: newAdj})
      ]
    });

  }

  @Action(fromPlt.replaceAdjustement)
  replaceAdjustement(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.replaceAdjustement) {
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
      ctx.patchState({
        adjustmentApplication: [...
          _.merge([], state.adjustmentApplication, adjustmentApplication)
        ]
      });
    } else {
      console.log(index);
      ctx.patchState(produce(ctx.getState(), draft => {
        _.forEach(adjustmentApplication, (row) => {
          draft.adjustmentApplication[index[row.pltId]] = row;
        });
      }));
    }


    /*ctx.patchState(produce(ctx.getState(), draft => {
      draft.adjustments[12]=
    }));*/

  }

  @Action(fromPlt.saveAdjustmentInPlt)
  saveAdjustmentInPlt(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.saveAdjustmentInPlt) {
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
    ctx.patchState({
      adjustmentApplication: [
        ...state.adjustmentApplication,
        adjustmentApplication,
      ]
    });
  }


  @Action(fromPlt.applyAdjustment)
  applyAdjustment(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.applyAdjustment) {
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

      console.log('push')
      _.forEach(pltId, (value) => {
        if (adjustmentApplication[value] !== undefined) {
          adjustmentApplication[value].push(newAdj);
        } else {
          adjustmentApplication[value] = [newAdj]
        }
      })
    console.log(adjustmentApplication);
    ctx.patchState({
      adjustmentApplication: {...adjustmentApplication}
    });
  }

  @Action(fromPlt.dropAdjustment)
  dropAdjustment(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.dropAdjustment) {
    const state = ctx.getState();

    let pltId = payload.pltId;
    let newAdj = {...payload.adjustement};

    ctx.patchState({
      adjustmentApplication: [
        ...state.adjustmentApplication,
        {pltId: pltId, adj: newAdj}
      ]
    });
  }

  @Action(fromPlt.deleteAdjsApplication)
  deleteAdjsApplication(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.deleteAdjsApplication) {
    const state = ctx.getState();
    let index = payload.index;
    let pltId = payload.pltId;
    let adjustmentApplication = _.merge([], state.adjustmentApplication);
    adjustmentApplication[pltId].splice(index, 1);
    ctx.patchState({
      adjustmentApplication: [
        ...adjustmentApplication,
      ]
    });
  }

  @Action(fromPlt.deleteAdjustment)
  deleteAdjustment(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.deleteAdjustment) {
    const state = ctx.getState();
    let adjustement = payload.adjustment;

    let index = _.findIndex(state.adjustments, adjustement);
    let adjustments = _.merge([], state.adjustments);

    adjustments.splice(index, 1);
    ctx.patchState({
      adjustments: [
        ...adjustments,
      ]
    });
  }

  @Action(fromPlt.saveSelectedPlts)
  saveSelectedPlts(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.saveSelectedPlts) {
    const state = ctx.getState();

    ctx.patchState({
      selectedPLT: _.merge({}, payload)
    });

  }

  @Action(fromPlt.saveAdjustmentApplication)
  saveAdjustmentApplication(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.saveAdjustmentApplication) {
    const state = ctx.getState();

    ctx.patchState({
      adjustmentApplication: _.merge({}, payload)
    });

  }

  @Action(PatchCalibrationStateAction)
  patchSearchState(ctx: StateContext<CalibrationState>, {payload}: PatchCalibrationStateAction) {
    if (_.isArray(payload))
      payload.forEach(item => ctx.patchState({[item.key]: item.value}));
    else
      ctx.patchState({[payload.key]: payload.value});
  }


  @Action(selectRow)
  selectRow(ctx: StateContext<CalibrationModel>, {payload}: selectRow) {
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
        ctx.dispatch(new PatchCalibrationStateAction({key: 'firstChecked', value: state.pure.dataTable[0].thread[0]}));
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
            ctx.patchState({
              pure: pure
            })
            this.store$.dispatch(new PatchCalibrationStateAction({key: 'lastCheckedBool', value: false}));
          }
        )
      );
    }
    ctx.patchState({
      pure: pure
    })
    this.store$.dispatch(new PatchCalibrationStateAction({key: 'lastCheckedBool', value: false}));
  }

  @Action(deselectAll)
  deselectAll(ctx: StateContext<CalibrationModel>, {payload}: deselectAll) {
    let state = ctx.getState();
    let pure = _.merge({}, state.pure);
    const lastChecked = payload.lastChecked;
    this.store$.dispatch(new PatchCalibrationStateAction({key: 'firstChecked', value: lastChecked}));
    _.forIn(pure.dataTable, function (value, key) {
      _.forIn(value.thread, function (plt, key) {
        plt.checked = false;
      })
    })
    ctx.patchState({
      pure: pure
    });
  }

}
