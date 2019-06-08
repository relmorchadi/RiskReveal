import {CalibrationModel} from "../../model";
import {Action, NgxsOnInit, State, StateContext} from "@ngxs/store";
import * as fromPlt from "../actions";
import * as _ from "lodash";

const initiaState: CalibrationModel = {
  filters: {
    systemTags: [],
    userTags: []
  },
  selectedPLT: [],
  extendPltSection: false,
  collapseTags: true,
  adjustments: []
};

@State<CalibrationModel>({
  name: 'calibration',
  defaults: initiaState
})
export class CalibrationState implements NgxsOnInit {
  ctx = null;

  ngxsOnInit(ctx?: StateContext<CalibrationState>): void | any {
    this.ctx = ctx;
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
  saveAdjustement(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.saveAdjustment) {
    const state = ctx.getState();
    ctx.patchState({
      adjustments: [
        ...state.adjustments,
        payload,
      ]
    });

  }

  @Action(fromPlt.saveSelectedPlts)
  saveSelectedPlts(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.saveSelectedPlts) {
    // const state = ctx.getState();
    ctx.patchState({
      selectedPLT: payload
    });

  }

}
