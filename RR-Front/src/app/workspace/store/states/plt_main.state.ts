import {State} from '@ngxs/store';
import {pltMainModel} from "../../model";
import {PltApi} from '../../services/plt.api';
import {PltStateService} from '../../services/plt-state.service';

const initiaState: pltMainModel = {
  data: {},
};

@State<pltMainModel>({
  name: 'pltMainModel',
  defaults: initiaState
})
export class PltMainState {

  constructor(private pltApi: PltApi, private pltFacade: PltStateService) {
  }

  /**
   * Selectors
   */
  /*@Selector()
  static getCloneConfig(wsIdentifier: string) {
    return (state: pltMainModel) => state.data[wsIdentifier]['cloneConfig'];
  }

  @Selector()
  static getWorkspaceMainState(state: pltMainModel) {
    return state;
  }

  @Selector()
  static data(wsIdentifier: string) {
    return (state: pltMainModel) => state.data[wsIdentifier];
  }

  status = ['in progress', 'valid', 'locked', 'requires regeneration', 'failed'];

  @Selector()
  static getProjectsPlt(wsIdentifier: string) {
    return (state: pltMainModel) => state[wsIdentifier].projects;
  }

  @Selector()
  static getAttrPlt(state: pltMainModel) {
    return (path) => _.get(state, `${path}`);
  }

  @Selector()
  static getUserTagsPlt(wsIdentifier: string) {
    return (state: pltMainModel) => _.get(state[wsIdentifier], 'userTags', {});
  }

  @Selector()
  static getOpenedPlt(wsIdentifier: string) {
    return (state: pltMainModel) => _.get(state[wsIdentifier], 'openedPlt', {});
  }

  @Selector()
  static getSystemTags(wsIdentifier: string) {
    return (state: pltMainModel) => state[wsIdentifier]['systemTags'];
  }


  static getDeletedPltsForPlt(wsIdentifier: string) {
    return createSelector([PltMainState], (state: pltMainModel) => _.keyBy(_.filter(_.get(state.data, `${wsIdentifier}`), e => e.deleted), 'pltId'));
  }


  static getPltsForPlts(wsIdentifier: string) {
    return createSelector([PltMainState], (state: pltMainModel) => _.keyBy(_.filter(_.get(state.data, `${wsIdentifier}`), e => !e.deleted), 'pltId'))
  }*/
  //
  //
  // @Action(fromPlt.setCloneConfig)
  // setCloneConfig(ctx: StateContext<pltMainModel>, {payload}: fromPlt.setCloneConfig) {
  //   return this.pltFacade.setCloneConfig(ctx, payload);
  // }
  //
  // @Action(fromPlt.loadAllPlts)
  // LoadAllPlts(ctx: StateContext<pltMainModel>, {payload}: fromPlt.loadAllPlts) {
  //   return this.pltFacade.LoadAllPlts(ctx, payload);
  // }
  //
  // @Action(fromPlt.loadAllPltsSuccess)
  // LoadAllPltsSuccess(ctx: StateContext<pltMainModel>, {payload}: fromPlt.loadAllPltsSuccess) {
  //   return this.pltFacade.LoadAllPltsSuccess(ctx, payload);
  // }
  //
  // @Action(fromPlt.ToggleSelectPlts)
  // SelectPlts(ctx: StateContext<pltMainModel>, {payload}: fromPlt.ToggleSelectPlts) {
  //   return this.pltFacade.SelectPlts(ctx, payload);
  // }
  //
  // @Action(fromPlt.OpenPLTinDrawer)
  // OpenPltinDrawer(ctx: StateContext<pltMainModel>, {payload}: fromPlt.OpenPLTinDrawer) {
  //   return this.pltFacade.OpenPltinDrawer(ctx, payload);
  // }
  //
  // @Action(fromPlt.ClosePLTinDrawer)
  // ClosePLTinDrawer(ctx: StateContext<pltMainModel>, {payload}: fromPlt.ClosePLTinDrawer) {
  //   return this.pltFacade.ClosePLTinDrawer(ctx,payload);
  // }
  //
  // @Action(fromPlt.setUserTagsFilters)
  // setFilterPlts(ctx: StateContext<pltMainModel>, {payload}: fromPlt.setUserTagsFilters) {
  //   return this.pltFacade.setFilterPlts(ctx,payload);
  // }
  //
  // @Action(fromPlt.FilterPltsByUserTags)
  // FilterPlts(ctx: StateContext<pltMainModel>, {payload}: fromPlt.FilterPltsByUserTags) {
  //   return this.pltFacade.filterPlts(ctx,payload);
  // }
  //
  // @Action(fromPlt.setTableSortAndFilter)
  // setTableSortAndFilter(ctx: StateContext<pltMainModel>, {payload}: fromPlt.setTableSortAndFilter) {
  //   return this.pltFacade.setTableSortAndFilter(ctx,payload);
  // }
  //
  // @Action(fromPlt.constructUserTags)
  // constructUserTags(ctx: StateContext<pltMainModel>, {payload}: fromPlt.constructUserTags) {
  //   return this.pltFacade.constructUserTags(ctx,payload);
  // }
  //
  // @Action(fromPlt.createOrAssignTags)
  // assignPltsToTag(ctx: StateContext<pltMainModel>, {payload}: fromPlt.createOrAssignTags) {
  //   return this.pltFacade.assignPltsToTag(ctx,payload);
  // }
  //
  // @Action(fromPlt.CreateTagSuccess)
  // createUserTagSuccess(ctx: StateContext<pltMainModel>, {payload}: fromPlt.CreateTagSuccess) {
  //   return this.pltFacade.createUserTagSuccess(ctx,payload);
  // }
  //
  // @Action(fromPlt.assignPltsToTagSuccess)
  // assignPltsToTagSucess(ctx: StateContext<pltMainModel>, {payload}: fromPlt.assignPltsToTagSuccess) {
  //   return this.pltFacade.assignPltsToTagSuccess(ctx,payload);
  // }
  //
  //
  // @Action(fromPlt.deleteUserTag)
  // deleteUserTag(ctx: StateContext<pltMainModel>, {payload}: fromPlt.deleteUserTag) {
  //   return this.pltFacade.deleteUserTag(ctx,payload);
  // }
  //
  // @Action(fromPlt.deleteUserTagSuccess)
  // deleteUserTagFromPlts(ctx: StateContext<pltMainModel>, {payload}: fromPlt.deleteUserTagSuccess) {
  //   return this.pltFacade.deleteUserTagFromPlts(ctx,payload);
  // }
  //
  // @Action(fromPlt.deletePlt)
  // deletePlt(ctx: StateContext<pltMainModel>, {payload}: fromPlt.deletePlt) {
  //   return this.pltFacade.deletePlt(ctx,payload);
  // }
  //
  // @Action(fromPlt.deletePltSucess)
  // deletePltSuccess(ctx: StateContext<pltMainModel>, {payload}: fromPlt.deletePltSucess) {
  //
  //   const {
  //     pltId
  //   } = payload;
  //
  //   const {
  //     data
  //   } = ctx.getState();

    /*
     return of(JSON.parse(localStorage.getItem('deletedPlts')) || {})
       .pipe()*/
  // }

  // @Action(fromPlt.editTag)
  // renameTag(ctx: StateContext<pltMainModel>, {payload}: fromPlt.editTag) {
  //   return this.pltFacade.renameTag(ctx,payload);
  // }
  //
  // @Action(fromPlt.editTagSuccess)
  // renameTagSucces(ctx: StateContext<pltMainModel>, {payload}: fromPlt.editTagSuccess) {
  //   return this.pltFacade.renameTagSuccess(ctx,payload);
  // }
  //
  // @Action(fromPlt.restorePlt)
  // restorePlt(ctx: StateContext<pltMainModel>, {payload}: fromPlt.restorePlt) {
  //   return this.pltFacade.restorePlt(ctx,payload);
  // }
}

