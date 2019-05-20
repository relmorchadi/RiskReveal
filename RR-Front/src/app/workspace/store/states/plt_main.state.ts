import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import * as _ from 'lodash';
import {pltMainModel} from "../../model";
import * as fromPlt from '../actions'
import {of} from "rxjs";
import {catchError, map, mergeMap, tap} from 'rxjs/operators';
import {PltApi} from '../../services/plt.api';

const initiaState: pltMainModel = {
  data: {},
  loading: false,
  filters: {
    systemTag: [],
    userTag: []
  }
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
  systemTags = [
    {tagId: '1', tagName: 'TC', tagColor: '#7bbe31', innerTagContent: '1', innerTagColor: '#a2d16f', selected: false},
    {
      tagId: '2',
      tagName: 'NATC-USM',
      tagColor: '#7bbe31',
      innerTagContent: '2',
      innerTagColor: '#a2d16f',
      selected: false
    },
    {
      tagId: '3',
      tagName: 'Post-Inured',
      tagColor: '#006249',
      innerTagContent: '9',
      innerTagColor: '#4d917f',
      selected: false
    },
    {
      tagId: '4',
      tagName: 'Pricing',
      tagColor: '#009575',
      innerTagContent: '0',
      innerTagColor: '#4db59e',
      selected: false
    },
    {
      tagId: '5',
      tagName: 'Accumulation',
      tagColor: '#009575',
      innerTagContent: '2',
      innerTagColor: '#4db59e',
      selected: false
    },
    {
      tagId: '6',
      tagName: 'Default',
      tagColor: '#06b8ff',
      innerTagContent: '1',
      innerTagColor: '#51cdff',
      selected: false
    },
    {
      tagId: '7',
      tagName: 'Non-Default',
      tagColor: '#f5a623',
      innerTagContent: '0',
      innerTagColor: '#f8c065',
      selected: false
    },
  ];

  @Action(fromPlt.loadAllPlts)
  LoadAllPlts(ctx: StateContext<pltMainModel>, {payload}: fromPlt.loadAllPlts) {
    const {
      params
    } = payload;

    ctx.patchState({
      loading: true
    });

    return this.pltApi.getAllPlts(params)
      .pipe(
        mergeMap( (data) => {
          ctx.patchState({
            data: Object.assign({},
              ...data.content.map(plt => ({[plt.pltId]: { ..._.omit(plt, 'pltId'), selected: false, visible: true,
                  userTag: _.range(_.random(3) || _.random(2)).map(i => this.userTags[i]),
                  systemTag: _.range(_.random(7) || _.random(5)).map(i => this.systemTags[i])
                }})
              )
            ),
            filters: {
              systemTag: [],
              userTag: []
            }
          });
          return ctx.dispatch(new fromPlt.loadAllPltsSuccess());
        }),
        catchError( err => ctx.dispatch(new fromPlt.loadAllPltsFail()))
      );
  }

  @Action(fromPlt.loadAllPltsSuccess)
  LoadAllPltsSuccess(ctx: StateContext<pltMainModel>, action: fromPlt.loadAllPltsSuccess) {
    ctx.patchState({
      loading: false
    });
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
    });

  }

  @Action(fromPlt.OpenPLTinDrawer)
  OpenPltinDrawer(ctx: StateContext<pltMainModel>, { payload }: fromPlt.OpenPLTinDrawer) {
    const state = ctx.getState();

    ctx.patchState({
      data: _.merge({}, state.data, {[payload.pltId]: {opened: true, selected: true}})
    });
  }

  @Action(fromPlt.ClosePLTinDrawer)
  ClosePLTinDrawer(ctx: StateContext<pltMainModel>, { payload }: fromPlt.ClosePLTinDrawer) {
    const state = ctx.getState();

    let newData = {};

    _.forEach(state.data, (v, k) => {
      newData[k] = !v.opened ? newData[k] : _.omit(v, 'opened');
    });

    ctx.patchState({
      data: _.merge({}, state.data, newData)
    });
  }

  @Action(fromPlt.setFilterPlts)
  setFilterPlts(ctx: StateContext<pltMainModel>, { payload }: fromPlt.setFilterPlts) {
    const state = ctx.getState();
    const{
      filters
    } = payload;

    ctx.patchState({
      filters: _.assign({}, state.filters, filters)
    });

    return ctx.dispatch(new fromPlt.FilterPlts());

  }

  @Action(fromPlt.FilterPlts)
  FilterPlts(ctx: StateContext<pltMainModel>, action: fromPlt.FilterPlts) {
    const state = ctx.getState();
    const {
      filters
    } = state;

    let newData = {};

    if ([...filters.systemTag, ...filters.userTag].length > 0) {
        _.forEach(state.data, (plt, k) => {
          if (_.some([...filters.userTag, ...filters.systemTag], (userTag) => _.find([...plt.userTag, ...plt.systemTag], tag => {
            console.log([...plt.userTag, ...plt.systemTag]);
            return tag.tagId == userTag;
          }))) {
            newData[k] = {...plt, visible: true};
          } else {
            newData[k] = {...plt, visible: false};
          }
        });
    } else {
      _.forEach(state.data, (plt, k) => {
        newData[k] = {...plt, visible: true};
      });
    }

    /*if(!_.some(_.values(filters), filterArrays => filterArrays.length > 0)){
      _.forEach(state.data, (plt,k) => {
        newData[k] = {...plt, visible: true};
      })
    }else{
      _.forEach(filters, (filterArray, filterKey) => {
        _.forEach(state.data, (plt,k) => {
          if(_.every(filters[filterKey], (userTag) => _.find(plt[filterKey], tag => tag.tagId == userTag))) {
            newData[k] = {...plt, visible: true};
          }else {
            newData[k] = {...plt, visible: false};
          }
        })
      })
    }*/

    _.forEach(newData, (v, k) => {
      console.log(newData[k].visible);
    });

    ctx.patchState({
      data: newData
    });
  }

}
