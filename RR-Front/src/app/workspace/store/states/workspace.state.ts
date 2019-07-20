import {Action, createSelector, Selector, State, StateContext} from '@ngxs/store';
import * as _ from 'lodash';
import * as fromWS from '../actions'
import {PatchCalibrationStateAction} from '../actions'
import {WorkspaceMain} from "../../../core/model";
import {CalibrationService} from "../../services/calibration.service";
import {WorkspaceService} from "../../services/workspace.service";
import {WorkspaceModel} from "../../model";
import * as fromPlt from "../actions/plt_main.actions";
import {PltStateService} from "../../services/plt-state.service";

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

  constructor(private wsService: WorkspaceService, private pltStateService: PltStateService, private calibrationService: CalibrationService) {
  }


  /***********************************
   *
   * Workspace Selectors
   *
   ***********************************/
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
   * Calibration Selectors
   *
   ***********************************/
  @Selector()
  static getProjects() {
    return (state: any) => state.workspaceMain.openedWs.projects
  }

  @Selector()
  static getAttr(state: WorkspaceModel) {
    return (path) => _.get(state.content.wsIdentifier.Calibration, `${path}`);
  }

  @Selector()
  static getAdjustmentApplication(state: WorkspaceModel) {
    return _.get(state.content.wsIdentifier.Calibration, "adjustmentApplication")
  }

  static getPlts(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => {
      return _.keyBy(_.filter(_.get(state.content[wsIdentifier].calibration.data, `${wsIdentifier}`), e => !e.deleted), 'pltId')
    })
  }

  static getDeletedPlts(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => {
      return _.keyBy(_.filter(_.get(state.content[wsIdentifier].calibration.data, `${wsIdentifier}`), e => e.deleted), 'pltId')
    });
  }

  @Selector()
  static getUserTags(state: WorkspaceModel) {
    return _.get(state.content.wsIdentifier.Calibration, 'userTags', {})
  }

  static getLeftNavbarIsCollapsed() {
    return createSelector([WorkspaceState], (state: WorkspaceMain) => {
      return _.get(state, "leftNavbarIsCollapsed");
    });
  }


  /***********************************
   *
   * Workspace Actions
   *
   ***********************************/

  @Action(fromWS.LoadWS)
  loadWs(ctx: StateContext<WorkspaceModel>, payload: fromWS.LoadWS) {
    return this.wsService.loadWs(ctx, payload);
  }

  @Action(fromWS.LoadWsSuccess)
  loadWsSuccess(ctx: StateContext<WorkspaceModel>, payload: fromWS.LoadWsSuccess) {
    return this.wsService.loadWsSuccess(ctx, payload);
  }

  @Action(fromWS.openWS)
  openWorkspace(ctx: StateContext<WorkspaceModel>, payload: fromWS.openWS) {
    return this.wsService.openWorkspace(ctx, payload);
  }

  @Action(fromWS.OpenMultiWS)
  openMultipleWorkspaces(ctx: StateContext<WorkspaceModel>, payload: fromWS.OpenMultiWS) {
    return this.wsService.openMultipleWorkspaces(ctx, payload);
  }

  @Action(fromWS.SetCurrentTab)
  setCurrentTab(ctx: StateContext<WorkspaceModel>, payload: fromWS.SetCurrentTab) {
    return this.wsService.setCurrentTab(ctx, payload);
  }

  @Action(fromWS.CloseWS)
  closeWorkspace(ctx: StateContext<WorkspaceModel>, payload: fromWS.CloseWS) {
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


  @Action(fromPlt.setCloneConfig)
  setCloneConfig(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.setCloneConfig) {
    return this.pltStateService.setCloneConfig(ctx, payload);
  }

  @Action(fromPlt.loadAllPlts)
  LoadAllPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.loadAllPlts) {
    return this.pltStateService.LoadAllPlts(ctx, payload);
  }

  @Action(fromPlt.loadAllPltsSuccess)
  LoadAllPltsSuccess(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.loadAllPltsSuccess) {
    return this.pltStateService.loadAllPltsSuccess(ctx, payload);
  }

  @Action(fromPlt.ToggleSelectPlts)
  ptlManagerSelectPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.ToggleSelectPlts) {
    return this.pltStateService.selectPlts(ctx, payload);
  }

  @Action(fromPlt.OpenPLTinDrawer)
  OpenPltinDrawer(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.OpenPLTinDrawer) {
    return this.pltStateService.openPltInDrawer(ctx, payload);
  }

  @Action(fromPlt.ClosePLTinDrawer)
  ClosePLTinDrawer(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.ClosePLTinDrawer) {
    return this.pltStateService.closePLTinDrawer(ctx, payload);
  }

  @Action(fromPlt.setUserTagsFilters)
  pltManagerSetFilterPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.setUserTagsFilters) {
    return this.pltStateService.setFilterPlts(ctx, payload);
  }

  @Action(fromPlt.FilterPltsByUserTags)
  pltManagerFilterPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.FilterPltsByUserTags) {
    return this.pltStateService.filterPlts(ctx, payload);
  }

  @Action(fromPlt.setTableSortAndFilter)
  setTableSortAndFilter(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.setTableSortAndFilter) {
    return this.pltStateService.setTableSortAndFilter(ctx, payload);
  }

  @Action(fromPlt.constructUserTags)
  pltManagerConstructUserTags(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.constructUserTags) {
    return this.pltStateService.constructUserTags(ctx, payload);
  }

  @Action(fromPlt.createOrAssignTags)
  assignPltsToTag(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.createOrAssignTags) {
    return this.pltStateService.assignPltsToTag(ctx, payload);
  }

  @Action(fromPlt.CreateTagSuccess)
  createUserTagSuccess(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.CreateTagSuccess) {
    return this.pltStateService.createUserTagSuccess(ctx, payload);
  }

  @Action(fromPlt.assignPltsToTagSuccess)
  assignPltsToTagSucess(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.assignPltsToTagSuccess) {
    return this.pltStateService.assignPltsToTagSuccess(ctx, payload);
  }


  @Action(fromPlt.deleteUserTag)
  deleteUserTag(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.deleteUserTag) {
    return this.pltStateService.deleteUserTag(ctx, payload);
  }

  @Action(fromPlt.deleteUserTagSuccess)
  deleteUserTagFromPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.deleteUserTagSuccess) {
    return this.pltStateService.deleteUserTagFromPlts(ctx, payload);
  }

  @Action(fromPlt.deletePlt)
  deletePlt(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.deletePlt) {
    return this.pltStateService.deletePlt(ctx, payload);
  }

  @Action(fromPlt.deletePltSucess)
  deletePltSuccess(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.deletePltSucess) {

    const {
      pltId
    } = payload;

    const {
      // data
    } = ctx.getState();

    /*
     return of(JSON.parse(localStorage.getItem('deletedPlts')) || {})
       .pipe()*/
  }

  @Action(fromPlt.editTag)
  renameTag(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.editTag) {
    return this.pltStateService.renameTag(ctx, payload);
  }

  @Action(fromPlt.editTagSuccess)
  renameTagSucces(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.editTagSuccess) {
    return this.pltStateService.renameTagSuccess(ctx, payload);
  }

  @Action(fromPlt.restorePlt)
  restorePlt(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.restorePlt) {
    return this.pltStateService.restorePlt(ctx, payload);
  }

  /***********************************
   *
   * Calibration Actions
   *
   ***********************************/



  @Action(fromWS.loadAllPltsFromCalibration)
  loadAllPltsFromCalibration(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.loadAllPltsFromCalibration) {
    return this.calibrationService.loadAllPltsFromCalibration(ctx, payload)
  }

  @Action(fromWS.loadAllPltsFromCalibrationSuccess)
  loadAllPltsFromCalibrationSuccess(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.loadAllPltsFromCalibrationSuccess) {
    this.calibrationService.loadAllPltsFromCalibrationSuccess(ctx, payload)
  }

  @Action(fromWS.constructUserTagsFromCalibration)
  constructUserTags(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.constructUserTagsFromCalibration) {
    this.calibrationService.constructUserTags(ctx, payload)
  }

  @Action(fromWS.setUserTagsFiltersFromCalibration)
  setFilterPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.setUserTagsFiltersFromCalibration) {
    this.calibrationService.setFilterPlts(ctx, payload)
  }

  @Action(fromWS.FilterPltsByUserTagsFromCalibration)
  FilterPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.FilterPltsByUserTagsFromCalibration) {
    this.calibrationService.FilterPlts(ctx, payload)
  }

  @Action(fromWS.ToggleSelectPltsFromCalibration)
  SelectPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleSelectPltsFromCalibration) {
    this.calibrationService.SelectPlts(ctx, payload)
  }

  @Action(fromWS.calibrateSelectPlts)
  calibrateSelectPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.calibrateSelectPlts) {
    this.calibrationService.calibrateSelectPlts(ctx, payload)
  }

  @Action(fromWS.initCalibrationData)
  initCalibrationData(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.initCalibrationData) {
    this.calibrationService.initCalibrationData(ctx, payload)
  }

  @Action(fromWS.setFilterCalibration)
  setFilterCalibration(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.setFilterCalibration) {
    this.calibrationService.setFilterCalibration(ctx, payload)
  }

  @Action(fromWS.extendPltSection)
  expandPltSection(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.extendPltSection) {
    this.calibrationService.expandPltSection(ctx, payload)
  }

  @Action(fromWS.collapseTags)
  collapseTags(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.collapseTags) {
    this.calibrationService.collapseTags(ctx, payload)
  }

  @Action(fromWS.saveAdjustment)
  saveAdjustment(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.saveAdjustment) {
    this.calibrationService.saveAdjustment(ctx, payload)
  }

  @Action(fromWS.dropThreadAdjustment)
  dropThreadAdjustment(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.dropThreadAdjustment) {
    this.calibrationService.dropThreadAdjustment(ctx, payload)

  }

  @Action(fromWS.saveAdjModification)
  saveAdjModification(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.saveAdjModification) {
    this.calibrationService.saveAdjModification(ctx, payload)
  }

  @Action(fromWS.replaceAdjustement)
  replaceAdjustement(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.replaceAdjustement) {
    this.calibrationService.replaceAdjustement(ctx, payload)
  }

  @Action(fromWS.saveAdjustmentInPlt)
  saveAdjustmentInPlt(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.saveAdjustmentInPlt) {
    this.calibrationService.saveAdjustmentInPlt(ctx, payload)
  }


  @Action(fromWS.applyAdjustment)
  applyAdjustment(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.applyAdjustment) {
    this.calibrationService.applyAdjustment(ctx, payload)
  }

  @Action(fromWS.dropAdjustment)
  dropAdjustment(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.dropAdjustment) {
    this.calibrationService.dropAdjustment(ctx, payload)
  }

  @Action(fromWS.deleteAdjsApplication)
  deleteAdjsApplication(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.deleteAdjsApplication) {
    this.calibrationService.deleteAdjsApplication(ctx, payload)
  }

  @Action(fromWS.deleteAdjustment)
  deleteAdjustment(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.deleteAdjustment) {
    this.calibrationService.deleteAdjustment(ctx, payload)
  }

  @Action(fromWS.saveSelectedPlts)
  saveSelectedPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.saveSelectedPlts) {
    this.calibrationService.saveSelectedPlts(ctx, payload)
  }

  @Action(fromWS.saveAdjustmentApplication)
  saveAdjustmentApplication(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.saveAdjustmentApplication) {
    this.calibrationService.saveAdjustmentApplication(ctx, payload)
  }

  @Action(PatchCalibrationStateAction)
  patchSearchState(ctx: StateContext<WorkspaceState>, {payload}: PatchCalibrationStateAction) {
    this.calibrationService.patchSearchState(ctx, payload)
  }
}
