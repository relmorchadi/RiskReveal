import {CalibrationModel} from "../../model";
import {NgxsOnInit, State, StateContext} from "@ngxs/store";

const initiaState: CalibrationModel = {
  filters: {
    systemTags: [],
    userTags: []
  },
  selectedPLT: []
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


}
