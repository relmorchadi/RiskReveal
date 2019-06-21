import {Action, createSelector, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import * as _ from 'lodash';
import {pltMainModel} from "../../model";
import * as fromPlt from '../actions'
import {forkJoin, from, of} from 'rxjs';
import {catchError, map, mapTo, mergeMap, tap} from 'rxjs/operators';
import {PltApi} from '../../services/plt.api';
import * as moment from 'moment';

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
    return _.keyBy(_.filter(state.data,e => !e.deleted), 'pltId')
  }

  systemTagsMapping = {
    regionPerilCode: 'Region Peril',
    financialPerspective: 'financialPerspective',
    currency: 'Currency',
    sourceModellingVendor: 'Modelling Vendor',
    sourceModellingSystem: 'Model System',
    generatedFromDefaultAdjustement: 'Default',
    originalTarget: 'Loss Asset Type',
    targetRapCode: 'Target RAP',
    xActAvailable: 'Published to Pricing',
    xActUsed: 'Used in Pricing',
    pltGrouping: 'Grouped',
    userOccurenceBasis: 'userOccurenceBasis'
  };

  regions = ['DE','EU','JP'];

  getRegion(){
    return this.regions[_.random(0,3)]
  }

  @Action(fromPlt.loadAllPlts)
  LoadAllPlts(ctx: StateContext<pltMainModel>, {payload}: fromPlt.loadAllPlts) {
    const {
      params
    } = payload;

    ctx.patchState({
      loading: true
    });

    console.log('ls',JSON.parse(localStorage.getItem('deletedPlts')))

    const ls = JSON.parse(localStorage.getItem('deletedPlts')) || {};

    return this.pltApi.getAllPlts(params)
      .pipe(
        mergeMap( (data) => {
          ctx.patchState({
            data: Object.assign({},
              ...data.plts.map(plt => ({[plt.pltId]: {
                ...plt,
                  selected: false,
                  visible: true,
                  tagFilterActive: false,
                  regionDesc: this.getRegion(),
                  opened: false,
                  deleted: ls[plt.pltId] ? ls[plt.pltId].deleted : undefined,
                  deletedBy: ls[plt.pltId] ? ls[plt.pltId].deletedBy : undefined,
                  deletedAt: ls[plt.pltId] ? ls[plt.pltId].deletedAt : undefined,
              }}))
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
      plts
    } = payload;

    let inComingData = {};

    _.forEach(plts, (v, k) => {
      inComingData[k] = {
        selected: v.type
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

  @Action(fromPlt.setUserTagsFilters)
  setFilterPlts(ctx: StateContext<pltMainModel>, { payload }: fromPlt.setUserTagsFilters) {
    const state = ctx.getState();
    const{
      filters
    } = payload;

    ctx.patchState({
      filters: _.assign({}, state.filters, filters)
    });

    return ctx.dispatch(new fromPlt.FilterPltsByUserTags());

  }

  @Action(fromPlt.FilterPltsByUserTags)
  FilterPlts(ctx: StateContext<pltMainModel>, action: fromPlt.FilterPltsByUserTags) {
    const state = ctx.getState();
    const {
      filters
    } = state;

    let newData = {};

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

  @Action(fromPlt.setTableSortAndFilter)
  setTableSortAndFilter(ctx: StateContext<pltMainModel>, { payload }: fromPlt.setTableSortAndFilter){

    const {
      sortData,
      filterData
    } = payload;

    const {
      data
    } = ctx.getState();

    const sortDataKeys =_.keys(sortData);
    const filterDataKeys = _.keys(filterData)
    let res={...data};


    if(filterDataKeys.length > 0 ) {

      res = _.filter(data, row =>
        _.every(
          filterDataKeys,
          filteredCol => _.some(
            _.split(filterData[filteredCol],/[,;]/g), strs =>
              _.includes(_.toLower(_.toString(row[filteredCol])), _.toLower(_.toString(strs)))
          )
        ))
    }

    if(sortDataKeys.length > 0){
      res = _.orderBy(res, [...sortDataKeys], [..._.values(sortData)])
    }

    ctx.patchState({
      data: res
    })
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

  @Action(fromPlt.createOrAssignTags)
  assignPltsToTag(ctx: StateContext<pltMainModel>, { payload }: fromPlt.createOrAssignTags){

    switch (payload.type) {
      case 'assignOrRemove':
        return this.pltApi.assignPltsToTag(payload).pipe(
          tap( t=> console.log(t)),
          mergeMap( (tags) => {
            console.log(tags);
            return ctx.dispatch(new fromPlt.assignPltsToTagSuccess({
              ...payload,
              tags
            }))
          })
        );
      case 'createTag':
        return this.pltApi.creatUserTag(payload).pipe(
          mergeMap( (userTag) => {
            return ctx.dispatch(new fromPlt.CreateTagSuccess({
              userTag
            }))
          }),
          catchError( () => of(new fromPlt.assignPltsToTagFail()))
        )
    }
  }

  @Action(fromPlt.CreateTagSuccess)
  createUserTagSuccess(ctx: StateContext<pltMainModel>, { payload }: fromPlt.CreateTagSuccess) {
    console.log('state create tag');
    const {
      data,
      userTags
    } = ctx.getState()

    const {
      userTag
    } = payload;

    let newData= {};

    _.forEach(userTag.pltHeaders, header => {
      newData[header.id] = {...data[header.id], userTags: [...data[header.id].userTags, userTag] }
    })


    ctx.patchState({
      data: _.merge({}, data, newData),
      userTags: _.merge({}, userTags, { [userTag.tagId]: {...userTag, count: userTag.pltHeaders.length, selected: false} })
    })

  }

  @Action(fromPlt.assignPltsToTagSuccess)
  assignPltsToTagSucess(ctx: StateContext<pltMainModel>, { payload }: fromPlt.assignPltsToTagSuccess){

    const {
      data,
      userTags
    } = ctx.getState();

    const {
      tags
    } = payload;

    console.log(tags,userTags)

    let newData = {};

    let newTags = {};

    _.forEach(tags, tag => {
      const {
        pltHeaders
      }= tag;

      let toDel= _.differenceBy(userTags[tag.tagId].pltHeaders, pltHeaders);
      let toAdd= _.differenceBy(pltHeaders, userTags[tag.tagId].pltHeaders);

      console.log({
        tagId: tag.tagId,
        toDel,
        toAdd
      });


      _.forEach(toDel, ({id}) => {
        newData[id] = {...data[id], userTags: _.filter(data[id].userTags, tagItem => tagItem.tagId != tag.tagId) || []}
      });

      _.forEach(toAdd, ({id}) => {
        newData[id] = {...data[id], userTags: _.concat(data[id].userTags, tag)}
      });

      newTags[tag.tagId]= {...tag, selected: userTags[tag.tagId].selected, count: tag.pltHeaders.length};
    })

    ctx.patchState({
      data: {...data, ...newData},
      userTags: {...userTags, ...newTags}
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
      newData[plt.id] = {...data[plt.id], userTags: _.filter(data[plt.id].userTags, (userTag: any) => userTag.tagId !== payload )}
    })

    ctx.patchState({
      data: {...data,...newData},
      userTags: _.omit(userTags, payload)
    })
  }

  @Action(fromPlt.deletePlt)
  deletePlt(ctx: StateContext<pltMainModel>, { payload }: fromPlt.deletePlt){

    const {
      pltIds
    } = payload;

    const {
      data
    } = ctx.getState();

    let newData = {};
    let ls= JSON.parse(localStorage.getItem('deletedPlts')) || {};

    _.forEach(pltIds, k => {
      newData[k] = { ...data[k], deleted: true, selected: false}
      ls = _.merge({}, ls, {[k]: { deleted: true, deletedBy: 'DEV', deletedAt: moment.now()}})
    });

    ctx.patchState({
      data: _.merge({}, data, newData)
    })

    localStorage.setItem('deletedPlts', JSON.stringify(ls))
    console.log(ls);
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

    /*
     return of(JSON.parse(localStorage.getItem('deletedPlts')) || {})
       .pipe()*/
   }

  @Action(fromPlt.editTag)
  renameTag(ctx: StateContext<pltMainModel>, { payload }: fromPlt.editTag){

    return this.pltApi.editTag({...payload}).pipe(
      mergeMap( tag => ctx.dispatch(new fromPlt.editTagSuccess(tag))),
      catchError(err => ctx.dispatch(new fromPlt.editTagFail()))
    )
  }

  @Action(fromPlt.editTagSuccess)
  renameTagSucces(ctx: StateContext<pltMainModel>, { payload }: fromPlt.editTagSuccess){
    const {
      data,
      userTags
    } = ctx.getState();

    const {
      tagId,
      tagName,
      tagColor
    } = payload;

    let newData = {};

    console.log(payload.pltHeaders)
    _.forEach(payload.pltHeaders, pltId => {
      const {
        id
      } = pltId;

      console.log(id,data[id])

      let index= _.findIndex(data[id].userTags, (tag: any) => tag.tagId === tagId);

      newData[id] = {...data[id], userTags: _.merge([],data[id].userTags, { [index]: {...data[id].userTags[index], tagName,tagColor}})}
    })

    ctx.patchState({
      data: _.merge({},data, newData),
      userTags: _.merge({}, userTags, {
        [tagId] : {
          tagName,
          tagColor
        }
      })
    })

  }

  @Action(fromPlt.restorePlt)
  restorePlt(ctx: StateContext<pltMainModel>, { payload }: fromPlt.restorePlt){
    const {
      pltIds
    } = payload;

    const {
      data
    } = ctx.getState();

    let newData = {};
    let ls= JSON.parse(localStorage.getItem('deletedPlts')) || {};

    _.forEach(pltIds, k => {
      newData[k] = { ...data[k], deleted: false, selected: false}
      ls = _.omit(ls, `${k}`)
    });

    ctx.patchState({
      data: _.merge({}, data, newData)
    });

    localStorage.setItem('deletedPlts', JSON.stringify(ls));

  }

}

