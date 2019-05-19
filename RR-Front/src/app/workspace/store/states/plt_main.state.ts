import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import * as _ from 'lodash';
import {pltMainModel} from "../../model";
import * as fromPlt from '../actions'
import {of} from "rxjs";
import {catchError, map, mergeMap, tap} from 'rxjs/operators';
import {PltApi} from '../../services/plt.api';

const initiaState: pltMainModel = {
  data: {},
  loading: false
};

@State<pltMainModel>({
  name: 'pltMainModel',
  defaults: initiaState
})
export class PltMainState implements NgxsOnInit {
  ctx = null;

  constructor(private pltApi: PltApi) {
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

  @Selector()
  static data(state: pltMainModel){
    return state.data;
  }

  @Selector()
    static getAttr(state: pltMainModel){
    return (path) => _.get(state, `${path}`)
  }
  userTags = [
    {tagId: '1', tagName: 'Pricing V1', tagColor: '#893eff', innerTagContent: '1', innerTagColor: '#ac78ff', selected: false},
    {tagId: '2', tagName: 'Pricing V2', tagColor: '#06b8ff', innerTagContent: '2', innerTagColor: '#51cdff', selected: false},
    {tagId: '3', tagName: 'Final Princing', tagColor: '#c38fff', innerTagContent: '5', innerTagColor: '#d5b0ff', selected: false}
  ];

  @Action(fromPlt.loadAllPlts)
  LoadAllPlts(ctx: StateContext<pltMainModel>, {payload}: fromPlt.loadAllPlts) {
    const {
      params
    }= payload;

    ctx.patchState({
      loading: true
    })

    return this.pltApi.getAllPlts(params)
      .pipe(
        mergeMap( (data) => {
          ctx.patchState({
            data: Object.assign({},
              ...data.content.map(plt => ({[plt.pltId]: { ..._.omit(plt, 'pltId'), selected: false, visible: true,userTags: _.range(_.random(3) || _.random(2)).map(i => this.userTags[i])}}) )
            )
          })
          return ctx.dispatch(new fromPlt.loadAllPltsSuccess())
        }),
        catchError( err => ctx.dispatch(new fromPlt.loadAllPltsFail()))
      )
  }

  @Action(fromPlt.loadAllPltsSuccess)
  LoadAllPltsSuccess(ctx: StateContext<pltMainModel>, action: fromPlt.loadAllPltsSuccess) {
    ctx.patchState({
      loading: false
    })
  }

  @Action(fromPlt.ToggleSelectPlts)
  SelectPlts(ctx: StateContext<pltMainModel>, { payload }: fromPlt.ToggleSelectPlts) {
    const state = ctx.getState();
    const {
      plts,
    } = payload;

    let inComingData = {};

    _.forEach(plts, (v, k) => {
      inComingData[k] = {
        selected: v.type === 'select'
      };
    });

    ctx.patchState({
      data: _.merge({}, state.data, inComingData)
    })

  }

  @Action(fromPlt.OpenPLTinDrawer)
  OpenPltinDrawer(ctx: StateContext<pltMainModel>, { payload }: fromPlt.OpenPLTinDrawer) {
    const state = ctx.getState();

    console.log(payload);
    ctx.patchState({
      data: _.merge({}, state.data,{[payload.pltId]: {opened: true,selected:true}})
    })
  }

  @Action(fromPlt.ClosePLTinDrawer)
  ClosePLTinDrawer(ctx: StateContext<pltMainModel>, { payload }: fromPlt.ClosePLTinDrawer) {
    const state = ctx.getState();

    let newData ={};

    _.forEach(state.data, (v,k) => {
      newData[k] = !v.opened ? newData[k] : _.omit(v, 'opened');
    })

    ctx.patchState({
      data: _.merge({}, state.data, newData)
    })
  }

  @Action(fromPlt.SortAndFilterPlts)
  SortAndFilterPlts(ctx: StateContext<pltMainModel>, { payload }: fromPlt.SortAndFilterPlts) {
    const state = ctx.getState();
    let res= _.merge({}, state.data);

    const {
      sortData,
      filterData
    } = payload;

    const sortDataKeys =_.keys(sortData);
    const filterDataKeys = _.keys(filterData);

    if(filterDataKeys.length > 0 ){
      _.forEach(filterDataKeys, (key) => {
        res = _.filter(res, (el) => _.includes(_.toLower(_.toString(el[key])), _.toLower(_.toString(filterData[key]))))
      })
    }

    if(sortDataKeys.length > 0){
      res = _.orderBy(res, [...sortDataKeys], [..._.values(sortData)])
    }
  }

}
