import {Action, createSelector, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import * as _ from 'lodash';
import {pltMainModel} from "../../model";
import * as fromPlt from '../actions'
import {forkJoin, from, of} from 'rxjs';
import {catchError, map, mapTo, mergeMap, tap} from 'rxjs/operators';
import {PltApi} from '../../services/plt.api';

const initiaState: pltMainModel = {
  data: {},
  loading: false,
  filters: {
    systemTag: [],
    userTag: []
  },
  userTags: {}
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

  @Selector()
  static getProjects() {
    return (state: any) => state.workspaceMain.openedWs.projects
  }

  @Selector()
    static getUserTags(state: pltMainModel){
    return _.get(state,'userTags',{})
  }

  @Selector()
    static  getSystemTags(state: pltMainModel){
    return _.get(state,'systemTags',{})
  }

  @Selector()
    static getDeletedPlts(state: pltMainModel){
    return _.keyBy(_.filter(_.get(state,'data',{}),e => e.deleted), 'pltId')
  }

  @Selector()
    static getPlts(state: pltMainModel) {
    return _.keyBy(_.filter(_.get(state,'data',{}),e => !e.deleted), 'pltId')
  }

  systemTagsMapping = {
    regionPerilCode: 'Region Peril',
    financialPerspective: 'financialPerspective',
    currency: 'Currency',
    sourceModellingVendor: 'Modelling Vendor',
    sourceModellingSystem: 'Model System',
    generatedFromDefaultAdjustement: 'Default',
    pltType: 'Loss Asset Type',
    originalTarget: 'Loss Asset Type',
    targetRapCode: 'Target RAP',
    xActAvailable: 'Published to Pricing',
    xActUsed: 'Used in Pricing',
    pltGrouping: 'Grouped',
    userOccurenceBasis: 'userOccurenceBasis'
  };

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
              ...data.plts.map(plt => ({[plt.pltId]: { ...plt, selected: false, visible: true,tagFilterActive: false,opened: false,deleted: false }}))
            ),
            filters: {
              systemTag: [],
              userTag: []
            }
          });
          return ctx.dispatch(new fromPlt.loadAllPltsSuccess({userTags: data.userTags}));
        }),
        catchError( err => ctx.dispatch(new fromPlt.loadAllPltsFail()))
      );
  }

  @Action(fromPlt.loadAllPltsSuccess)
  LoadAllPltsSuccess(ctx: StateContext<pltMainModel>, { payload }: fromPlt.loadAllPltsSuccess) {
    ctx.patchState({
      loading: false
    });

    ctx.dispatch(new fromPlt.constructUserTags({userTags: payload.userTags}))
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
      data: _.merge({}, state.data, {[payload.pltId]: {opened: true}})
    });
  }

  @Action(fromPlt.ClosePLTinDrawer)
  ClosePLTinDrawer(ctx: StateContext<pltMainModel>, { payload }: fromPlt.ClosePLTinDrawer) {
    const state = ctx.getState();
    const {
      pltId
    } = payload;

    ctx.patchState({
      data: _.merge({}, state.data,{[pltId]: {opened: false}} )
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


  reverseSystemTagsMapping = {
    grouped: {
      'Region Peril': 'regionPerilCode',
      'Currency': 'currency',
      'Modelling Vendor': 'sourceModellingVendor',
      'Model System': 'sourceModellingSystem',
      'Target RAP':'targetRapCode',
      'User Occurence Basis': 'userOccurrenceBasis',
      'Loss Asset Type': 'pltType',
    },
    nonGrouped: {
    }
  };

  @Action(fromPlt.FilterPlts)
  FilterPlts(ctx: StateContext<pltMainModel>, action: fromPlt.FilterPlts) {
    const state = ctx.getState();
    const {
      filters
    } = state;

    let newData = {};

    /*if(filters.systemTag.length > 0 ){
      _.forEach( state.data , (plt: any, k) => {
        if(plt.visible) {
          if (_.some(filters.systemTag, (systemTag) => {
            const key = _.keys(systemTag)[0];
            return plt[this.reverseSystemTagsMapping.grouped[systemTag[key]]] === key
          })) {
            newData[k] = {...plt, tagFilterActive: true};
          } else {
            newData[k] = {...plt, tagFilterActive: false};
          }
        }else{
          newData[k] = {...plt, tagFilterActive: false};
        }
      });
    }else{
      _.forEach(state.data, (plt, k) => {
        newData[k] = {...plt,visible: plt.visible, tagFilterActive: false};
      });
    }*/

    if (filters.userTag.length > 0) {
        _.forEach(state.data, (plt: any, k) => {
          if (_.some(filters.userTag, (userTag) => _.find(plt.userTags, tag => tag.tagId == userTag))) {
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

    ctx.setState({
      ...state,
      data: newData
    });
  }

  @Action(fromPlt.assignPltsToTag)
  assignPltsToTag(ctx: StateContext<pltMainModel>, { payload }: fromPlt.assignPltsToTag){

    return payload.type == 'many' ? forkJoin(..._.map(payload.tags,e => this.pltApi.assignPltsToTag({
        plts: payload.plts,
        wsId: payload.wsId,
        uwYear: payload.uwYear,
        tag: e
      }))).pipe(
      mergeMap( tags => from(tags)),
      map( (userTag) => {
        return ctx.dispatch(new fromPlt.assignPltsToTagSuccess({
          userTag,
          plts: payload.plts
        }))
      })
      )
      : this.pltApi.assignPltsToTag(payload).pipe(
      mergeMap( (userTag) => {
        return ctx.dispatch(new fromPlt.assignPltsToTagSuccess({
          userTag,
          plts: payload.plts
        }))
      }),
      catchError( () => of(new fromPlt.assignPltsToTagFail()))
    )

  }

  @Action(fromPlt.constructUserTags)
  constructUserTags(ctx: StateContext<pltMainModel>, { payload }: fromPlt.constructUserTags){
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

      uesrTagsSummary[tagId] = {tagId,...rest, selected: false, count: pltHeaders.length,pltHeaders}
    })

    ctx.patchState({
      userTags: uesrTagsSummary
    })

  }

  @Action(fromPlt.assignPltsToTagSuccess)
  assignPltsToTagSucess(ctx: StateContext<pltMainModel>, { payload }: fromPlt.assignPltsToTagSuccess){
    const {
      userTag: {
        pltHeaders,
        ...rest
      }
    } = payload;

    const {
      data,
      userTags
    } = ctx.getState()

    let newData = {};

    _.forEach(pltHeaders, (v,k) => {
      newData[v.id] = _.merge({},data[v.id],{userTags: [...data[v.id].userTags, rest] })
    })

    ctx.patchState({
      data: _.merge({},data, newData),
      userTags: {...userTags, [rest.tagId]: {...rest, selected: false,count: pltHeaders.length}}
    })
  }
  @Action(fromPlt.deleteUserTag)
  deleteUserTag(ctx: StateContext<pltMainModel>, { payload }: fromPlt.deleteUserTag){
    return this.pltApi.deleteUserTag(payload).pipe(
      mergeMap( () => ctx.dispatch(new fromPlt.deleteUserTagSuccess(payload)))
    )
  }

  @Action(fromPlt.deleteUserTagSuccess)
  deleteUserTagFromPlts(ctx: StateContext<pltMainModel>, { payload }: fromPlt.deleteUserTagSuccess){
    const {
      data,
      userTags
    } = ctx.getState();

    let newData= {};

    _.forEach(userTags[payload].pltHeaders, (plt) => {
      newData[plt.id] = {...data[plt.id], userTags: _.toArray(_.omit(data[plt.id].userTags, _.findIndex(data[plt.id].userTags, (userTag: any) => userTag.tagId == payload)))}
    })

    ctx.patchState({
      data: {...data,...newData},
      userTags: _.omit(userTags, payload)
    })
  }

  @Action(fromPlt.deletePlt)
  deletePlt(ctx: StateContext<pltMainModel>, { payload }: fromPlt.deletePlt){

    const {
      pltId
    } = payload;

    const {
      data
    } = ctx.getState();

    ctx.patchState({
      data: _.merge({}, data, {[pltId]: { ...data[pltId], deleted: true}})
    })

    /*return this.pltApi.deletePlt(payload.pltId).pipe(
      mergeMap(plt => ctx.dispatch(new fromPlt.deletePltSucess({
        pltId: payload.pltId
      }))),
      catchError(e => ctx.dispatch(new fromPlt.deletePltFail()))
    )*/

  }

   @Action(fromPlt.deletePltSucess)
   deletePltSuccess(ctx: StateContext<pltMainModel>, { payload }: fromPlt.deletePltSucess){

    const {
      pltId
    } = payload;

    const {
      data
    } = ctx.getState();

     ctx.patchState({
       data: _.merge({}, data, {[pltId]: { ...data[pltId], deleted: true}})
     })
   }

  @Action(fromPlt.renameTag)
  renameTag(ctx: StateContext<pltMainModel>, { payload }: fromPlt.renameTag){

    return this.pltApi.renameTag({...payload}).pipe(
      mergeMap( tag => ctx.dispatch(new fromPlt.renameTagSuccess(tag))),
      catchError(err => ctx.dispatch(new fromPlt.renameTagFail()))
    )
  }

  @Action(fromPlt.renameTagSuccess)
  renameTagSucces(ctx: StateContext<pltMainModel>, { payload }: fromPlt.renameTagSuccess){
    const {
      data,
      userTags
    } = ctx.getState();

    const {
      tagId,
      tagName
    } = payload;

    let newData = {};


    _.forEach(payload.pltHeaders, pltId => {
      const {
        id
      } = pltId;

      let index= _.findIndex(data[id].userTags, (tag: any) => tag.tagId === tagId);

      newData[id] = {...data[id], userTags: _.merge([],data[id].userTags, { [index]: {...data[id].userTags[index], tagName}})}
    })

    ctx.patchState({
      data: _.merge({},data, newData),
      userTags: _.merge({}, userTags, {
        [tagId] : {
          tagName
        }
      })
    })

  }

}

