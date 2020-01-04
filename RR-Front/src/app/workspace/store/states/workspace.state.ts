import {Action, createSelector, Selector, State, StateContext} from '@ngxs/store';
import * as _ from 'lodash';
import * as fromWS from '../actions';
import {PatchCalibrationStateAction, SaveRPs} from '../actions';
import * as fromInuring from '../actions/inuring.actions';
import {CalibrationService} from '../../services/calibration.service';
import {WorkspaceService} from '../../services/workspace.service';
import {WorkspaceModel} from '../../model';
import {InuringService} from '../../services/inuring.service';
import * as fromPlt from '../actions/plt_main.actions';
import {PltStateService} from '../../services/store/plt-state.service';
import {RiskLinkStateService} from '../../services/riskLink-action.service';
import {FileBasedService} from '../../services/file-based.service';
import {Data} from '../../../shared/data/fac-data';
import {ScopeCompletenessService} from '../../services/scop-completeness.service';
import {ContractService} from "../../services/contract.service";
import {CalibrationNewService} from "../../services/calibration-new.service";


const initialState: WorkspaceModel = {
  content: {},
  currentTab: {
    index: 0,
    wsIdentifier: null,
  },
  facWs: {
    data: Data.facWs,
    sequence: 137
  },
  savedData: {
    riskLink: {
      edmrdmSelection: {}
    }
  },
  routing: '',
  loading: false
};

@State<WorkspaceModel>({
  name: 'workspace',
  defaults: initialState
})
export class WorkspaceState {

  constructor(private wsService: WorkspaceService,
              private contractService: ContractService,
              private pltStateService: PltStateService,
              private calibrationService: CalibrationService,
              private calibrationNewService: CalibrationNewService,
              private riskLinkFacade: RiskLinkStateService,
              private inuringService: InuringService,
              private fileBasedFacade: FileBasedService,
              private scopService: ScopeCompletenessService,
  ) {
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
  static getProjects(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return state.content[wsIdentifier].projects;
  }

  @Selector()
  static getCurrentWorkspaces(state: WorkspaceModel) {
    const wsId = state.currentTab.wsIdentifier;
    return state.content[wsId];
  }

  @Selector()
  static getCurrentTab(state: WorkspaceModel) {
    return state.currentTab;
  }

  @Selector()
  static getFacData(state: WorkspaceModel) {
    return state.facWs.data;
  }

  @Selector()
  static getFacSequence(state: WorkspaceModel) {
    return state.facWs.sequence;
  }

  @Selector()
  static getCurrentTabStatus(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return state.content[wsIdentifier].workspaceType;
  }

  @Selector()
  static getSelectedProject(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return _.filter(state.content[wsIdentifier].projects, item => item.selected)[0];
  }

  static getDeletedPltsForCalibration(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) =>
      _.keyBy(_.filter(_.get(state.content, `${wsIdentifier}.calibration.data.${wsIdentifier}`), e => e.deleted), 'pltId'));
  }

  static getPltsForCalibration(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) =>
      _.keyBy(_.filter(_.get(state.content, `${wsIdentifier}.calibration.data.${wsIdentifier}`), e => e.toCalibrate), 'pltId'))
  }

  static getPltsForCalibrationPopUp(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) =>
      _.keyBy(_.filter(_.get(state.content, `${wsIdentifier}.calibration.data.${wsIdentifier}`), e => !e.deleted), 'pltId'))
  }

  @Selector()
  static getLoading(state: WorkspaceModel) {
    return state.loading;
  }

  @Selector()
  static getLastWorkspace(state: WorkspaceModel) {
    return _.last(_.values(state.content));
  }

  status = ['in progress', 'valid', 'locked', 'requires regeneration', 'failed'];

  @Selector()
  static getCurrentWS(state: WorkspaceModel) {
    return state.content[state.currentTab.wsIdentifier];
  }

  /***********************************
   *
   * Contract Selectors
   *
   ***********************************/

  @Selector()
  static getContract(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return state.content[wsIdentifier].contract;
  }

  /***********************************
   *
   * Plt Selectors
   *
   ***********************************/

  @Selector()
  static getCloneConfig(state: WorkspaceModel) {
    return state.content[state.currentTab.wsIdentifier].pltManager['cloneConfig'];
  }

  @Selector()
  static getWorkspaceMainState(state: WorkspaceModel) {
    return state;
  }

  @Selector()
  static data(wsIdentifier: string) {
    return (state: WorkspaceModel) => state.content[wsIdentifier];
  }

  static getDeletedPltsForPlt(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => state.content[wsIdentifier].pltManager.deleted);
  }

  static getPltsForPlts(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => state.content[wsIdentifier].pltManager.data);
  }

  static getProjectsPlt(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => state.content[wsIdentifier].projects);
  }

  static getUserTagsPlt(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => state.content[wsIdentifier].pltManager.userTags);
  }

  static getOpenedPlt(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => state.content[wsIdentifier].pltManager.openedPlt);
  }

  static getUserTagManager(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => state.content[wsIdentifier].pltManager.userTagManager);
  }

  static getSummary(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => state.content[wsIdentifier].pltManager.pltDetails.summary);
  }

  /***********************************
   *
   * NEW Calibration Actions
   *
   ***********************************/

  static getEpMetrics(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => state.content[wsIdentifier].calibrationNew.epMetrics );
  }

  static getAdjustments(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => state.content[wsIdentifier].calibrationNew.adjustments );
  }

  @Selector()
  static getEpMetricsColumns(wsIdentifier: string, curveType: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => state.content[wsIdentifier].calibrationNew.epMetrics.cols );
  }

  //Higher State Order
  static getCalibrationConstants(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => state.content[wsIdentifier].calibrationNew.constants );
  }

  static getCalibrationStatus(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => state.content[wsIdentifier].calibrationNew.constants.status );

  }

  /***********************************
   *
   * Calibration Selectors
   *
   ***********************************/
  @Selector()
  static getExtentState(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return state.content[wsIdentifier].calibration.extendState;
  }

  @Selector()
  static getAttr(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return (path) => _.get(state.content[wsIdentifier].Calibration, `${path}`);
  }

  @Selector()
  static getAdjustmentApplication(state: WorkspaceModel) {
    return _.get(state.content.wsIdentifier.Calibration, 'adjustmentApplication');
  }

  static getPlts(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => {
      return _.keyBy(_.filter(_.get(state.content[wsIdentifier].calibration.data, `${wsIdentifier}`), e => !e.deleted), 'pltId')
    });
  }

  static getDeletedPlts(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => {
      return _.keyBy(_.filter(_.get(state.content[wsIdentifier].calibration.data, `${wsIdentifier}`), e => e.deleted), 'pltId')
    });
  }

  @Selector()
  static getUserTags(state: WorkspaceModel) {
    return _.get(state.content.wsIdentifier.Calibration, 'userTags', {});
  }

  static getLeftNavbarIsCollapsed() {
    return createSelector([WorkspaceState], (state: WorkspaceModel) => {
      return _.get(state, "leftNavbarIsCollapsed");
    });
  }

  /***********************************
   *
   * RiskLink Selectors
   *
   ***********************************/

  @Selector()
  static getRiskLinkState(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return state.content[wsIdentifier].riskLink;
  }

  @Selector()
  static getListEdmRdm(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return state.content[wsIdentifier].riskLink.listEdmRdm;
  }

  @Selector()
  static getFinancialValidator(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return _.get(state, 'financialValidator', null);
  }

  @Selector()
  static getFinancialValidatorAttr(path: string, value: any) {
    return (state: any) => _.get(state.RiskLinkModel, path, value);
  }

  @Selector()
  static getAnalysis(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return state.content[wsIdentifier].riskLink.analysis;
  }

  @Selector()
  static getPortfolios(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return state.content[wsIdentifier].riskLink.portfolios;
  }

  @Selector()
  static getSummaries(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return _.get(state.content[wsIdentifier].riskLink.summaries, 'data', null);
  }

  @Selector()
  static getResults(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return _.get(state.content[wsIdentifier].riskLink.results, 'data', null);
  }

  @Selector()
  static getValidResults(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return state.content[wsIdentifier].riskLink.results.isValid;
  }


  @Selector()
  static getSelectedAnalysisPortfolios(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    const {analysis, portfolios, edms, rdms} = state.content[wsIdentifier].riskLink.selection;
    return {
      analysis: _.flatten(
        _.keys(analysis).map(rdmId => _.map( _.toArray(analysis[rdmId]), item => ({
          rdmId,
          rdmName: rdms[rdmId].name,
          analysisId: item.rlId,
          analysisName: item.analysisName,
          rlAnalysisId: item.rlAnalysisId
        }) ))
      ),
      portfolios: _.flatten(
        _.keys(portfolios).map(edmId => _.map( _.toArray(portfolios[edmId]), item => ({
          edmId,
          edmName: edms[edmId].name,
          currency: item.agCurrency,
          portfolioId: item.rlId,
          portfolioName: item.name,
          portfolioType: item.type
        }) ))
      )
    };
  }

  @Selector()
  static getRiskLinkSummary(state: WorkspaceModel){
    const wsIdentifier = state.currentTab.wsIdentifier;
    return state.content[wsIdentifier].riskLink.summary;
  }

  @Selector()
  static getFlatSelectedAnalysisPortfolio(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    const {analysis, portfolios} = state.content[wsIdentifier].riskLink.selection;
    return [
      ..._.flatten(_.values(analysis).map(val => _.toArray(val))),
      ..._.flatten(_.values(portfolios).map(val => _.toArray(val)))
    ];
  }

  @Selector()
  static getLinkingData(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return state.content[wsIdentifier].riskLink.linking;
  }

  @Selector()
  static getFinancialPerspective(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return state.content[wsIdentifier].riskLink.financialPerspective;
  }

  @Selector()
  static getImportStatus(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return state.content[wsIdentifier].riskLink.importPLTs;
  }

  /***********************************
   *
   * Inuring Selectors
   *
   ***********************************/

  //TBD

  /***********************************
   *
   * File Based Selectors
   *
   ***********************************/

  @Selector()
  static getFileBasedData(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return state.content[wsIdentifier].fileBaseImport;
  }

  @Selector()
  static getFileBaseSelectedFiles(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return state.content[wsIdentifier].fileBaseImport.files;
  }

  /***********************************
   *
   * Scope And Completeness Selectors
   *
   ***********************************/

  @Selector()
  static getScopeCompletenessData(state: WorkspaceModel) {
    const wsIdentifier = state.currentTab.wsIdentifier;
    return state.content[wsIdentifier].scopeOfCompletence;
  }

  static getPltsForScopeCompleteness(wsIdentifier: string) {
    return createSelector([WorkspaceState], (state: WorkspaceModel) =>
      _.keyBy(_.get(state.content, `${wsIdentifier}.scopeOfCompletence.data`), 'pltId'));
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

  @Action(fromWS.OpenWS)
  openWorkspace(ctx: StateContext<WorkspaceModel>, payload: fromWS.OpenWS) {
    return this.wsService.openWorkspace(ctx, payload);
  }

  @Action(fromWS.CreateNewFac)
  createNewFac(ctx: StateContext<WorkspaceModel>, payload: fromWS.CreateNewFac) {
    this.wsService.createNewFac(ctx, payload);
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

  @Action(fromWS.ToggleFavorite)
  toggleFavorite(ctx: StateContext<WorkspaceModel>, payload: fromWS.ToggleFavorite) {
    return this.wsService.toggleFavorite(ctx, payload);
  }

  @Action(fromWS.TogglePinned)
  togglePinned(ctx: StateContext<WorkspaceModel>, payload: fromWS.TogglePinned) {
    return this.wsService.togglePinned(ctx, payload);
  }

  @Action(fromWS.ToggleProjectSelection)
  toggleProjectSelection(ctx: StateContext<WorkspaceModel>, payload: fromWS.ToggleProjectSelection) {
    return this.wsService.toggleProjectSelection(ctx, payload);
  }

  @Action(fromWS.AddNewProject)
  addNewProject(ctx: StateContext<WorkspaceModel>, payload: fromWS.AddNewProject) {
    console.log('add new project', payload);
    return this.wsService.addNewProject(ctx, payload);
  }

  @Action(fromWS.AddNewFacProject)
  addNewFacProject(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.AddNewFacProject) {
    return this.wsService.addNewFacProject(ctx, payload);
  }

  @Action(fromWS.EditProject)
  editProject(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.EditProject) {
    return this.wsService.updateProject(ctx, payload);
  }

  @Action(fromWS.DeleteProject)
  deleteProject(ctx: StateContext<WorkspaceModel>, payload: fromWS.DeleteProject) {
    return this.wsService.deleteProject(ctx, payload);
  }

  @Action(fromWS.DeleteFacProject)
  deleteFacProject(ctx: StateContext<WorkspaceModel>, payload: fromWS.DeleteFacProject) {
    return this.wsService.deleteFacProject(ctx, payload);
  }

  /***********************************
   *
   * Contract Actions
   *
   ***********************************/

  @Action(fromWS.LoadContractAction)
  loadContractData(ctx: StateContext<WorkspaceModel>) {
    this.contractService.loadContractData(ctx);
  }

  @Action(fromWS.ToggleFacDivisonAction)
  toggleFacDivision(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleFacDivisonAction) {
    this.contractService.toggleFacDivision(ctx, payload);
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

  @Action(fromWS.loadWorkSpaceAndPlts)
  loadWorkSpaceAndPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.loadWorkSpaceAndPlts) {
    return this.pltStateService.loadWorkSpaceAndPlts(ctx, payload);
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

  @Action(fromPlt.loadSummaryDetail)
  LoadSummaryDetail(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.loadSummaryDetail) {
    return this.pltStateService.loadSummaryDetail(ctx, payload);
  }

  @Action(fromPlt.loadSummaryDetailSuccess)
  LoadSummaryDetailSuccess(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.loadSummaryDetailSuccess) {
    return this.pltStateService.loadSummaryDetailSuccess(ctx, payload);
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

  @Action(fromPlt.CreateTagSuccess)
  createUserTagSuccess(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.CreateTagSuccess) {
    // return this.pltStateService.createUserTagSuccess(ctx, payload);
  }

  @Action(fromPlt.assignPltsToTagSuccess)
  assignPltsToTagSucess(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.assignPltsToTagSuccess) {
    // return this.pltStateService.assignPltsToTagSuccess(ctx, payload);
  }


  @Action(fromPlt.deleteUserTag)
  deleteUserTag(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.deleteUserTag) {
    return this.pltStateService.deleteUserTag(ctx, payload);
  }

  @Action(fromPlt.deleteUserTagSuccess)
  deleteUserTagFromPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.deleteUserTagSuccess) {
    // return this.pltStateService.deleteUserTagFromPlts(ctx, payload);
  }

  @Action(fromPlt.deletePlt)
  deletePlt(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.deletePlt) {
    return this.pltStateService.deletePlt(ctx, payload);
  }

  @Action(fromPlt.deletePltSucess)
  deletePltSuccess(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.deletePltSucess) {
    const {pltId} = payload;
    // const { data} = ctx.getState();
    /*return of(JSON.parse(localStorage.getItem('deletedPlts')) || {}).pipe()*/
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

  @Action(fromPlt.FilterByFalesely)
  filterPltsByStatus(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.FilterByFalesely) {
    this.pltStateService.filterPltsByStatus(ctx, payload);
  }

  @Action(fromPlt.AddNewTag)
  addNewTag(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.AddNewTag) {
    return this.pltStateService.addNewTag(ctx, payload);
  }

  @Action(fromPlt.DeleteTag)
  deleteTag(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.DeleteTag) {
    //this.pltStateService.deleteTag(ctx, payload);
  }

  @Action(fromPlt.GetTagsBySelection)
  getTagsBySelection(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.GetTagsBySelection) {
    return this.pltStateService.getTagsBySelection(ctx, payload);
  }

  @Action(fromPlt.AssignPltsToTag)
  assignPltsToTag(ctx: StateContext<WorkspaceModel>, {payload}: fromPlt.AssignPltsToTag) {
    return this.pltStateService.assignPltsToTag(ctx, payload);
  }

  /***********************************
   *
   * NEW Calibration Actions
   *
   ***********************************/

  @Action(fromWS.LoadGroupedPltsByPure)
  loadGroupedPltsByPure(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadGroupedPltsByPure) {
    return this.calibrationNewService.loadGroupedPltsByPure(ctx, payload);
  }

  @Action(fromWS.LoadDefaultAdjustmentsInScope)
  loadDefaultAdjustmentsInScope(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadDefaultAdjustmentsInScope) {
    return this.calibrationNewService.loadDefaultAdjustmentsInScope(ctx, payload);
  }

  @Action(fromWS.LoadEpMetrics)
  loadEpMetrics(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadEpMetrics) {
    return this.calibrationNewService.loadEpMetrics(ctx, payload);
  }

  @Action(fromWS.LoadCalibrationConstants)
  loadCalibrationConstants(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadCalibrationConstants) {
    return this.calibrationNewService.loadCalibrationConstants(ctx);
  }

  @Action(fromWS.ToggleSelectCalibPlts)
  ToggleSelectCalibPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleSelectCalibPlts){
    console.log(payload)
    return this.calibrationNewService.selectPlts(ctx, payload);
  }

  @Action(fromWS.SaveRPs)
  saveRPs(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.SaveRPs){
    return this.calibrationNewService.saveRPs(ctx, payload);
  }

  /***********************************
   *
   * Calibration Actions
   *
   ***********************************/

  @Action(fromWS.loadAllPltsFromCalibration)
  loadAllPltsFromCalibration(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.loadAllPltsFromCalibration) {
    return this.calibrationService.loadAllPltsFromCalibration(ctx, payload);
  }

  @Action(fromWS.LoadAllAdjustmentApplication)
  loadAllAdjustmentApplication(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadAllAdjustmentApplication) {
    return this.calibrationService.loadAllAdjustmentApplication(ctx, payload);
  }

  @Action(fromWS.LoadAllDefaultAdjustmentApplication)
  loadAllDefaultAdjustmentApplication(ctx: StateContext<WorkspaceModel>) {
    return this.calibrationService.loadAllDefaultAdjustment(ctx);
  }

  @Action(fromWS.LoadAllPltsFromCalibrationSuccess)
  loadAllPltsFromCalibrationSuccess(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadAllPltsFromCalibrationSuccess) {
    this.calibrationService.loadAllPltsFromCalibrationSuccess(ctx, payload);
  }

  @Action(fromWS.constructUserTagsFromCalibration)
  constructUserTags(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.constructUserTagsFromCalibration) {
    this.calibrationService.constructUserTags(ctx, payload);
  }

  @Action(fromWS.setUserTagsFiltersFromCalibration)
  setFilterPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.setUserTagsFiltersFromCalibration) {
    this.calibrationService.setFilterPlts(ctx, payload);
  }

  @Action(fromWS.FilterPltsByUserTagsFromCalibration)
  FilterPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.FilterPltsByUserTagsFromCalibration) {
    this.calibrationService.FilterPlts(ctx, payload);
  }

  @Action(fromWS.ToggleSelectPltsFromCalibration)
  SelectPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleSelectPltsFromCalibration) {
    this.calibrationService.SelectPlts(ctx, payload);
  }

  @Action(fromWS.calibrateSelectPlts)
  calibrateSelectPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.calibrateSelectPlts) {
    this.calibrationService.calibrateSelectPlts(ctx, payload);
  }

  @Action(fromWS.toCalibratePlts)
  toCalibratePlts(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.toCalibratePlts) {
    this.calibrationService.toCalibratePlts(ctx, payload);
  }

  @Action(fromWS.initCalibrationData)
  initCalibrationData(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.initCalibrationData) {
    this.calibrationService.initCalibrationData(ctx, payload);
  }

  @Action(fromWS.setFilterCalibration)
  setFilterCalibration(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.setFilterCalibration) {
    this.calibrationService.setFilterCalibration(ctx, payload);
  }

  @Action(fromWS.extendPltSection)
  expandPltSection(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.extendPltSection) {
    this.calibrationService.expandPltSection(ctx, payload);
  }

  @Action(fromWS.ExtendStateToggleAction)
  extendStateToggle(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ExtendStateToggleAction) {
    this.calibrationService.extendStateToggle(ctx, payload);
  }

  @Action(fromWS.collapseTags)
  collapseTags(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.collapseTags) {
    return this.calibrationService.collapseTags(ctx, payload);
  }

  @Action(fromWS.saveAdjustment)
  saveAdjustment(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.saveAdjustment) {
    this.calibrationService.saveAdjustment(ctx, payload);
  }

  @Action(fromWS.dropThreadAdjustment)
  dropThreadAdjustment(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.dropThreadAdjustment) {
    this.calibrationService.dropThreadAdjustment(ctx, payload);

  }

  @Action(fromWS.loadAdjsArray)
  loadAdjsArray(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.loadAdjsArray) {
    this.calibrationService.loadAdjsArray(ctx, payload);
  }

  @Action(fromWS.saveAdjModification)
  saveAdjModification(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.saveAdjModification) {
    this.calibrationService.saveAdjModification(ctx, payload);
  }

  @Action(fromWS.replaceAdjustement)
  replaceAdjustement(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.replaceAdjustement) {
    this.calibrationService.replaceAdjustement(ctx, payload);
  }

  @Action(fromWS.saveAdjustmentInPlt)
  saveAdjustmentInPlt(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.saveAdjustmentInPlt) {
    this.calibrationService.saveAdjustmentInPlt(ctx, payload);
  }


  @Action(fromWS.applyAdjustment)
  applyAdjustment(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.applyAdjustment) {
    this.calibrationService.applyAdjustment(ctx, payload);
  }

  @Action(fromWS.dropAdjustment)
  dropAdjustment(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.dropAdjustment) {
    this.calibrationService.dropAdjustment(ctx, payload);
  }

  @Action(fromWS.deleteAdjsApplication)
  deleteAdjsApplication(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.deleteAdjsApplication) {
    this.calibrationService.deleteAdjsApplication(ctx, payload);
  }

  @Action(fromWS.deleteAdjustment)
  deleteAdjustment(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.deleteAdjustment) {
    this.calibrationService.deleteAdjustment(ctx, payload);
  }

  @Action(fromWS.saveSelectedPlts)
  saveSelectedPlts(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.saveSelectedPlts) {
    this.calibrationService.saveSelectedPlts(ctx, payload);
  }

  @Action(fromWS.saveAdjustmentApplication)
  saveAdjustmentApplication(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.saveAdjustmentApplication) {
    this.calibrationService.saveAdjustmentApplication(ctx, payload);
  }

  @Action(PatchCalibrationStateAction)
  patchSearchState(ctx: StateContext<WorkspaceState>, {payload}: PatchCalibrationStateAction) {
    this.calibrationService.patchSearchState(ctx, payload);
  }


  // @Action(selectRow)
  // selectRow(ctx: StateContext<WorkspaceModel>, {payload}: selectRow) {
  //   this.calibrationService.selectRow(ctx, payload)
  // }
  //
  // @Action(deselectAll)
  // deselectAll(ctx: StateContext<WorkspaceModel>, {payload}: deselectAll) {
  //   this.calibrationService.deselectAll(ctx, payload)
  // }

  /***********************************
   *
   * RiskLink Actions
   *
   ***********************************/

  @Action(fromWS.PatchRiskLinkAction)
  patchRiskLinkState(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.PatchRiskLinkAction) {
    // this.riskLinkFacade.patchRiskLinkState(ctx, payload);
  }

  @Action(fromWS.PatchRiskLinkCollapseAction)
  patchCollapseState(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.PatchRiskLinkCollapseAction) {
    this.riskLinkFacade.patchCollapseState(ctx, payload);
  }

  @Action(fromWS.PatchRiskLinkDisplayAction)
  patchDisplayState(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.PatchRiskLinkDisplayAction) {
    this.riskLinkFacade.patchDisplayState(ctx, payload);
  }

  @Action(fromWS.PatchRiskLinkFinancialPerspectiveAction)
  patchFinancialPerspectiveState(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.PatchRiskLinkFinancialPerspectiveAction) {
    this.riskLinkFacade.patchFinancialPerspectiveState(ctx, payload);
  }

  @Action(fromWS.PatchAddToBasketStateAction)
  patchAddToBasketState(ctx: StateContext<WorkspaceModel>) {
    this.riskLinkFacade.patchAddToBasketState(ctx);
  }

  @Action(fromWS.PatchTargetFPAction)
  patchTargetFP(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.PatchTargetFPAction) {
    this.riskLinkFacade.patchTargetFP(ctx, payload);
  }

  @Action(fromWS.PatchResultsAction)
  patchResult(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.PatchResultsAction) {
    this.riskLinkFacade.patchResult(ctx, payload);
  }

  @Action(fromWS.PatchLinkingModeAction)
  patchLinkingMode(ctx: StateContext<WorkspaceModel>) {
    this.riskLinkFacade.patchLinkingMode(ctx);
  }

  @Action(fromWS.ToggleRiskLinkEDMAndRDMAction)
  toggleRiskLinkEDMAndRDM(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleRiskLinkEDMAndRDMAction) {
    this.riskLinkFacade.toggleRiskLinkEDMAndRDM(ctx, payload);
  }

  @Action(fromWS.DatasourceScanAction)
  dataSourcesScan(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.DatasourceScanAction) {
    return this.riskLinkFacade.dataSourcesScan(ctx, payload);
  }

  @Action(fromWS.ToggleRiskLinkPortfolioAction)
  toggleRiskLinkPortfolio(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleRiskLinkPortfolioAction) {
    this.riskLinkFacade.toggleRiskLinkPortfolio(ctx, payload);
  }

  @Action(fromWS.ToggleRiskLinkAnalysisAction)
  toggleRiskLinkAnalysis(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleRiskLinkAnalysisAction) {
    this.riskLinkFacade.toggleRiskLinkAnalysis(ctx, payload);
  }

  @Action(fromWS.ToggleRiskLinkResultAction)
  toggleRiskLinkResult(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleRiskLinkResultAction) {
    this.riskLinkFacade.toggleRiskLinkResult(ctx, payload);
  }

  @Action(fromWS.ToggleRiskLinkSummaryAction)
  toggleRiskLinkSummary(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleRiskLinkSummaryAction) {
    this.riskLinkFacade.toggleRiskLinkSummary(ctx, payload);
  }

  @Action(fromWS.ToggleRiskLinkFPStandardAction)
  toggleRiskLinkFPStandard(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleRiskLinkFPStandardAction) {
    this.riskLinkFacade.toggleRiskLinkFPStandard(ctx, payload);
  }

  @Action(fromWS.ToggleRiskLinkFPAnalysisAction)
  toggleRiskLinkFPAnalysis(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleRiskLinkFPAnalysisAction) {
    this.riskLinkFacade.toggleRiskLinkFPAnalysis(ctx, payload);
  }

  @Action(fromWS.ToggleAnalysisLinkingAction)
  toggleAnalysisLinking(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleAnalysisLinkingAction) {
    this.riskLinkFacade.toggleAnalysisLinking(ctx, payload);
  }

  @Action(fromWS.TogglePortfolioLinkingAction)
  togglePortfolioLinking(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.TogglePortfolioLinkingAction) {
    this.riskLinkFacade.togglePortfolioLinking(ctx, payload);
  }

  @Action(fromWS.AddToBasketAction)
  addToBasket(ctx: StateContext<WorkspaceModel>) {
    return this.riskLinkFacade.addToBasket(ctx);
  }

  @Action(fromWS.AddToBasketDefaultAction)
  addToBasketDefault(ctx: StateContext<WorkspaceModel>) {
    return this.riskLinkFacade.addToBasketDefault(ctx);
  }

  @Action(fromWS.TriggerImportAction)
  importRiskLinkMain(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.TriggerImportAction) {
    return this.riskLinkFacade.triggerImport(ctx, payload);
  }

  @Action(fromWS.ApplyFinancialPerspectiveAction)
  applyFinancialPerspective(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ApplyFinancialPerspectiveAction) {
    this.riskLinkFacade.applyFinancialPerspective(ctx, payload);
  }

  @Action(fromWS.ApplyRegionPerilAction)
  applyRegionPeril(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ApplyRegionPerilAction) {
    this.riskLinkFacade.applyRegionPeril(ctx, payload);
  }

  @Action(fromWS.SaveFinancialPerspectiveAction)
  saveFinancialPerspective(ctx: StateContext<WorkspaceModel>) {
    this.riskLinkFacade.saveFinancialPerspective(ctx);
  }

  @Action(fromWS.SaveDivisionSelection)
  saveDivisionSelection(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.SaveDivisionSelection) {
    this.riskLinkFacade.saveDivisionSelection(ctx, payload);
  }

  @Action(fromWS.SaveEditAnalysisAction)
  saveEditAnalysis(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.SaveEditAnalysisAction) {
    this.riskLinkFacade.saveEditAnalysis(ctx, payload);
  }

  @Action(fromWS.SaveEditPEQTAction)
  saveEditPeqt(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.SaveEditPEQTAction) {
    this.riskLinkFacade.saveEditPeqt(ctx, payload);
  }

  @Action(fromWS.SaveEDMAndRDMSelectionAction)
  saveEDMAndRDMSelection(ctx: StateContext<WorkspaceModel>) {
    this.riskLinkFacade.saveEDMAndRDMSelection(ctx);
  }

  @Action(fromWS.SynchronizeEDMAndRDMSelectionAction)
  synchronizeEDMAndRDMSelection(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.SynchronizeEDMAndRDMSelectionAction) {
    this.riskLinkFacade.synchronizeEDMAndRDMSelection(ctx);
  }

  @Action(fromWS.BasicScanEDMAndRDMAction)
  basicScanEDMAndRDM(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.BasicScanEDMAndRDMAction) {
    return this.riskLinkFacade.basicScanEDMAndRDM(ctx, payload);
  }

  @Action(fromWS.CreateLinkingAction)
  createLinking(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.CreateLinkingAction) {
    this.riskLinkFacade.createLinking(ctx, payload);
  }

  @Action(fromWS.UpdateStatusLinkAction)
  updateStatusLink(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.UpdateStatusLinkAction) {
    this.riskLinkFacade.updateStatusLink(ctx, payload);
  }

  @Action(fromWS.UpdateAnalysisAndPortfolioData)
  updateAnalysisAndPortfolioData(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.UpdateAnalysisAndPortfolioData) {
    this.riskLinkFacade.updateAnalysisAndPortfolioData(ctx, payload);
  }

  @Action(fromWS.RemoveFinancialPerspectiveAction)
  removeFinancialPerspective(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.RemoveFinancialPerspectiveAction) {
    this.riskLinkFacade.removeFinancialPerspective(ctx, payload);
  }

  @Action(fromWS.RemoveEDMAndRDMSelectionAction)
  removeEDMAndRDMSeletion(ctx: StateContext<WorkspaceModel>) {
    this.riskLinkFacade.removeEDMAndRDMSelection(ctx);
  }

  @Action(fromWS.DeleteFromBasketAction)
  deleteFromBasket(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.DeleteFromBasketAction) {
    this.riskLinkFacade.deleteFromBasket(ctx, payload);
  }

  @Action(fromWS.DeleteEdmRdmAction)
  deleteEdmRdm(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.DeleteEdmRdmAction) {
    this.riskLinkFacade.deleteEdmRdm(ctx, payload);
  }

  @Action(fromWS.DeleteLinkAction)
  deleteLink(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.DeleteLinkAction) {
    this.riskLinkFacade.deleteLink(ctx, payload);
  }

  @Action(fromWS.DeleteInnerLinkAction)
  deleteInnerLink(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.DeleteInnerLinkAction) {
    this.riskLinkFacade.deleteInnerLink(ctx, payload);
  }

  @Action(fromWS.LoadLinkingDataAction)
  loadLinkingData(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadLinkingDataAction) {
    this.riskLinkFacade.loadLinkingDataAction(ctx, payload);
  }

  @Action(fromWS.LoadFacDataAction)
  loadFacData(ctx: StateContext<WorkspaceModel>) {
    return this.riskLinkFacade.loadFacData(ctx);
  }

  @Action(fromWS.LoadDivisionSelection)
  loadDivisionSelection(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadDivisionSelection) {
    this.riskLinkFacade.loadDivisionSelection(ctx);
  }

  @Action(fromWS.LoadRiskLinkAnalysisDataAction)
  loadRiskLinkAnalysisData(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadRiskLinkAnalysisDataAction) {
    return this.riskLinkFacade.loadRiskLinkAnalysisData(ctx, payload);
  }

  @Action(fromWS.LoadRiskLinkPortfolioDataAction)
  loadRiskLinkPortfolioData(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadRiskLinkPortfolioDataAction) {
    return this.riskLinkFacade.loadRiskLinkPortfolioData(ctx, payload);
  }

  @Action(fromWS.ToggleRiskLinkEDMAndRDMSelectedAction)
  toggleRiskLinkEDMAndRDMSelected(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleRiskLinkEDMAndRDMSelectedAction) {
    return this.riskLinkFacade.toggleRiskLinkEDMAndRDMSelected(ctx, payload);
  }

  @Action(fromWS.ToggleAnalysisForLinkingAction)
  toggleAnalysisForLinking(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleAnalysisForLinkingAction) {
    this.riskLinkFacade.toggleAnalysisForLinking(ctx, payload);
  }

  /** ACTION ADDED EDM AND RDM */
  @Action(fromWS.SelectRiskLinkEDMAndRDMAction)
  selectRiskLinkEDMAndRDM(ctx: StateContext<WorkspaceModel>) {
    this.riskLinkFacade.selectRiskLinkEDMAndRDM(ctx);
  }

  @Action(fromWS.SelectFacRiskLinkEDMAndRDMAction)
  selectFacRiskLinkEDMAndRDM(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.SelectFacRiskLinkEDMAndRDMAction) {
    this.riskLinkFacade.selectMatchingFacEDMAndRDM(ctx, payload);
  }

  /** SEARCH WITH KEYWORD OR PAGE OF EDM AND RDM */
  @Action(fromWS.SearchRiskLinkEDMAndRDMAction)
  searchRiskLinkEDMAndRDM(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.SearchRiskLinkEDMAndRDMAction) {
    return this.riskLinkFacade.searchRiskLinkEDMAndRDM(ctx, payload);
  }

  /** LOAD DATA FOR FINANCIAL PERSPECTIVE */
  @Action(fromWS.LoadFinancialPerspectiveAction)
  loadFinancialPerspective(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadFinancialPerspectiveAction) {
    this.riskLinkFacade.loadFinancialPerspective(ctx, payload);
  }

  /** LOAD DATA WHEN OPEN RISK LINK PAGE */
  @Action(fromWS.LoadRiskLinkDataAction)
  loadRiskLinkData(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadRiskLinkDataAction) {
    return this.riskLinkFacade.loadRiskLinkData(ctx);
  }

  @Action(fromWS.RunDetailedScanAction)
  runDetailedScan(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadFinancialPerspectiveAction) {
    return this.riskLinkFacade.runDetailedScan(ctx, payload);
  }

  @Action(fromWS.PatchAnalysisResultAction)
  patchAnalysisResult(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.PatchAnalysisResultAction) {
    return this.riskLinkFacade.patchAnalysisResult(ctx, payload);
  }

  @Action(fromWS.PatchPortfolioResultAction)
  patchPortfolioResult(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.PatchPortfolioResultAction) {
    return this.riskLinkFacade.patchPortfolioResult(ctx, payload);
  }

  @Action(fromWS.OverrideAnalysisRegionPeril)
  overrideAnalysisRegionPeril(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.OverrideAnalysisRegionPeril){
    return this.riskLinkFacade.overrideAnalysisRegionPeril(ctx, payload);
  }

  @Action(fromWS.LoadSourceEpCurveHeaders)
  loadSourceEpCurveHeaders(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadSourceEpCurveHeaders){
    return this.riskLinkFacade.loadSourceEpCurveHeaders(ctx, payload);
  }
  @Action(fromWS.LoadTargetRaps)
  loadTargetRaps(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadTargetRaps){
    return this.riskLinkFacade.loadTargetRap(ctx, payload);
  }

  @Action(fromWS.OverrideTargetRaps)
  overrideTargetRaps(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.OverrideTargetRaps){
    return this.riskLinkFacade.overrideTargetRaps(ctx, payload);
  }

  @Action(fromWS.ClearTargetRaps)
  clearTargetRaps(ctx: StateContext<WorkspaceModel>){
    return this.riskLinkFacade.clearTargetRaps(ctx);
  }

  @Action(fromWS.OverrideFinancialPerspective)
  overrideFinancialPerspective(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.OverrideFinancialPerspective){
    return this.riskLinkFacade.overrideFinancialPerspective(ctx, payload);
  }

  @Action(fromWS.LoadRegionPerilForAnalysis)
  loadAnalysisRegionPerils(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadRegionPerilForAnalysis){
    return this.riskLinkFacade.loadAnalysisRegionPerils(ctx, payload);
  }

  @Action(fromWS.ReScanDataSource)
  reScanDataSource(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ReScanDataSource){
    return this.riskLinkFacade.rescanDataSource(ctx, payload);
  }
  @Action(fromWS.OverrideOccurrenceBasis)
  overrideOccurrenceBasis(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.OverrideOccurrenceBasis){
    return this.riskLinkFacade.overrideOccurrenceBasis(ctx, payload);
  }


  /***********************************
   *
   * Scope And Completeness Actions
   *
   ***********************************/
  @Action(fromWS.LoadScopeCompletenessDataSuccess)
  loadScopeCompletenessData(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadScopeCompletenessDataSuccess) {
    return this.scopService.loadScopeCompletenessData(ctx, payload);
  }

  @Action(fromWS.PublishToPricingFacProject)
  publishToPricingFac(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.PublishToPricingFacProject) {
    return this.scopService.publishToPricing(ctx, payload);
  }


  /***********************************
   *
   * File Based Actions
   *
   ***********************************/

  @Action(fromWS.LoadFileBasedFoldersAction)
  loadFileBasedFolders(ctx: StateContext<WorkspaceModel>) {
    return this.fileBasedFacade.loadFolderList(ctx);
  }

  @Action(fromWS.LoadFileBasedFilesAction)
  loadFileBasedFiles(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.LoadFileBasedFilesAction) {
    return this.fileBasedFacade.loadFilesList(ctx, payload);
  }

  @Action(fromWS.RemoveFileFromImportAction)
  removeFileFromImport(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.RemoveFileFromImportAction) {
    this.fileBasedFacade.removeFileFromImport(ctx, payload);
  }

  @Action(fromWS.ToggleFilesAction)
  toggleFiles(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.ToggleFilesAction) {
    this.fileBasedFacade.toggleFile(ctx, payload);
  }

  @Action(fromWS.TogglePltsAction)
  togglePlts(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.TogglePltsAction) {
    this.fileBasedFacade.togglePlts(ctx, payload);
  }

  @Action(fromWS.AddFileForImportAction)
  addForImport(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.AddFileForImportAction) {
    return this.fileBasedFacade.addToImport(ctx, payload);
  }

  /***********************************
   *
   * Inuring Actions
   *
   ***********************************/

  @Action(fromInuring.OpenInuringPackage)
  openInuringPackage(ctx: StateContext<WorkspaceModel>, payload: fromInuring.OpenInuringPackage) {
    return this.inuringService.openInuringPackage(ctx, payload);
  }

  @Action(fromInuring.CloseInuringPackage)
  closeInuringPackage(ctx: StateContext<WorkspaceModel>, payload: fromInuring.CloseInuringPackage) {
    return this.inuringService.closeInuringPackage(ctx, payload);
  }

  @Action(fromInuring.AddInuringPackage)
  addInuringPackage(ctx: StateContext<WorkspaceModel>, payload: fromInuring.AddInuringPackage) {
    return this.inuringService.addInuringPackage(ctx, payload);
  }

  @Action(fromInuring.AddInputNode)
  AddInputNode(ctx: StateContext<WorkspaceModel>, payload: fromInuring.AddInputNode) {
    return this.inuringService.AddInputNode(ctx, payload);
  }

  @Action(fromInuring.EditInputNode)
  EditInputNode(ctx: StateContext<WorkspaceModel>, payload: fromInuring.EditInputNode) {
    return this.inuringService.EditInputNode(ctx, payload);
  }

  @Action(fromInuring.DeleteInputNode)
  DeleteInputNode(ctx: StateContext<WorkspaceModel>, payload: fromInuring.DeleteInputNode) {
    return this.inuringService.DeleteInputNode(ctx, payload);
  }

  @Action(fromInuring.EditInuringPackage)
  editInuringPackage(ctx: StateContext<WorkspaceModel>, payload: fromInuring.EditInuringPackage) {
    return this.inuringService.editInuringPackage(ctx, payload);
  }

  @Action(fromInuring.DeleteInuringPackage)
  deleteInuringPackage(ctx: StateContext<WorkspaceModel>, payload: fromInuring.DeleteInuringPackage) {
    return this.inuringService.deleteInuringPackage(ctx, payload);
  }

}
