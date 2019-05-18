import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import * as _ from 'lodash';
import {pltMainModel} from "../../model";
import * as fromPlt from '../actions'
import {of} from "rxjs";
import {map, mergeMap} from "rxjs/operators";

const initiaState: pltMainModel = {
  data: []
};

@State<pltMainModel>({
  name: 'pltMainModel',
  defaults: initiaState
})
export class PltMainState implements NgxsOnInit {
  ctx = null;

  constructor() {
  }

  ngxsOnInit(ctx?: StateContext<PltMainState>): void | any {
    this.ctx = ctx;
  }

  /**
   * Selectors
   */
  @Selector()
  static getWorkspaceMainState(state: pltMainModel) {
    return state;
  }

  @Action(fromPlt.LoadPltData)
  LoadPltData(ctx: StateContext<pltMainModel>, action: fromPlt.LoadPltData) {
    return of(JSON.parse(localStorage.getItem('pltData')))
      .pipe(
        map( (data) => of(ctx.setState({data: _.map(data, e=> ({...e,selected: false}))})))
      )
  }

  @Action(fromPlt.ToggleSelectPlts)
  SelectPlts(ctx: StateContext<pltMainModel>, { payload }: fromPlt.ToggleSelectPlts) {
    const state = ctx.getState();
    const {
      plts,
    } = payload;

    let newData = {};

    _.forEach( plts,(v,k) => {
      newData[k] = {
        selected: v.type === 'select'
      }
    })

    ctx.patchState({
      data: _.map(state.data, (el,i) => newData[i] ? {...el,...newData[i]} : el )
    })

  }

}
