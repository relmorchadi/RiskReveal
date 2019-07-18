import {Action, Selector, State, StateContext} from '@ngxs/store';
import * as _ from 'lodash';
import {WorkspaceModel} from "../../model";
import * as fromWS from '../actions'
import {WorkspaceService} from "../../services/workspace.service";

const initialState: WorkspaceModel = {
  content: {},
  currentTab: {
    index: 0,
    wsIdentifier: null,
  },
  favorite: [],
  pinned: [],
  routing: '',
  loading: false
};

@State<WorkspaceModel>({
  name: 'workspace',
  defaults: initialState
})
export class WorkspaceState {

  constructor(private wsService:WorkspaceService) {
  }


  //Selectors
  @Selector()
  static getWorkspaces(state: WorkspaceModel) {
    return state.content;
  }

  @Selector()
  static getCurrentTab(state: WorkspaceModel) {
    return state.currentTab;
  }

  @Selector()
  static getLoading(state: WorkspaceModel) {
    return state.loading;
  }

  @Selector()
  static getRecentWs(state: WorkspaceModel) {
    return _.values(state.content).map(item => ({...item, selected: false}));
  }

  @Selector()
  static getFavorite(state: WorkspaceModel) {
    return state.favorite;
  }

  @Selector()
  static getPinned(state: WorkspaceModel) {
    return state.pinned;
  }


  /***********************************
   *
   * Workspace Actions
   *
   ***********************************/

  @Action(fromWS.loadWS)
  loadWs(ctx: StateContext<WorkspaceModel>, payload: fromWS.loadWS) {
    return this.wsService.loadWs(ctx, payload);
  }

  @Action(fromWS.loadWsSuccess)
  loadWsSuccess(ctx: StateContext<WorkspaceModel>, payload: fromWS.loadWsSuccess) {
    return this.wsService.loadWsSuccess(ctx, payload);
  }

  @Action(fromWS.openWS)
  openWorkspace(ctx: StateContext<WorkspaceModel>, payload: fromWS.openWS) {
    return this.wsService.openWorkspace(ctx, payload);
  }

  @Action(fromWS.openMultiWS)
  openMultipleWorkspaces(ctx: StateContext<WorkspaceModel>, payload : fromWS.openMultiWS) {
    return this.wsService.openMultipleWorkspaces(ctx, payload);
  }

  @Action(fromWS.setCurrentTab)
  setCurrentTab(ctx: StateContext<WorkspaceModel>, payload: fromWS.setCurrentTab) {
    return this.wsService.setCurrentTab(ctx, payload);
  }

  @Action(fromWS.closeWS)
  closeWorkspace(ctx: StateContext<WorkspaceModel>, payload: fromWS.closeWS) {
    return this.wsService.closeWorkspace(ctx, payload);
  }

  @Action(fromWS.ToggleWsDetails)
  toggleWsDetails(ctx: StateContext<WorkspaceModel>, payload: fromWS.ToggleWsDetails) {
    return this.wsService.toggleWsDetails(ctx, payload);
  }

  @Action(fromWS.ToggleWsLeftMenu)
  toggleWsLeftMenu(ctx: StateContext<WorkspaceModel>, payload: fromWS.ToggleWsLeftMenu) {
    return this.wsService.toggleWsLeftMenu(ctx, payload);
  }

  @Action(fromWS.UpdateWsRouting)
  updateWsRouting(ctx: StateContext<WorkspaceModel>, payload: fromWS.UpdateWsRouting) {
    return this.wsService.updateWsRouting(ctx, payload);
  }

  @Action(fromWS.MarkWsAsFavorite)
  markWsAsFavorite(ctx: StateContext<WorkspaceModel>, payload: fromWS.MarkWsAsFavorite) {
    return this.wsService.markWsAsFavorite(ctx, payload);
  }

  @Action(fromWS.MarkWsAsNonFavorite)
  markWsAsNonFavorite(ctx: StateContext<WorkspaceModel>, payload: fromWS.MarkWsAsNonFavorite) {
    return this.wsService.markWsAsNonFavorite(ctx, payload);
  }

  @Action(fromWS.MarkWsAsPinned)
  markWsAsPinned(ctx: StateContext<WorkspaceModel>, payload: fromWS.MarkWsAsPinned) {
    return this.wsService.markWsAsPinned(ctx, payload);
  }

  @Action(fromWS.MarkWsAsNonPinned)
  markWsAsNonPinned(ctx: StateContext<WorkspaceModel>, payload: fromWS.MarkWsAsNonPinned) {
    return this.wsService.markWsAsNonPinned(ctx, payload);
  }


  /***********************************
   *
   * Plt Manager Actions
   *
   ***********************************/


}
