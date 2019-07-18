import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';

import * as _ from 'lodash';
import {RiskLinkModel} from '../../model/risk_link.model';
import {
  AddToBasketAction,
  ApplyFinancialPerspectiveAction, DeleteEdmRdmaction,
  DeleteFromBasketAction,
  LoadAnalysisForLinkingAction,
  LoadFinancialPerspectiveAction,
  LoadPortfolioForLinkingAction,
  LoadRiskLinkDataAction,
  PatchAddToBasketStateAction, PatchResultsAction,
  PatchRiskLinkAction,
  PatchRiskLinkCollapseAction,
  PatchRiskLinkDisplayAction,
  PatchRiskLinkFinancialPerspectiveAction,
  PatchTargetFPAction,
  RemoveFinancialPerspectiveAction,
  SaveFinancialPerspectiveAction,
  SearchRiskLinkEDMAndRDMAction,
  SelectRiskLinkEDMAndRDMAction,
  ToggleAnalysisForLinkingAction,
  ToggleRiskLinkAnalysisAction,
  ToggleRiskLinkEDMAndRDMAction,
  ToggleRiskLinkFPAnalysisAction,
  ToggleRiskLinkFPStandardAction,
  ToggleRiskLinkPortfolioAction,
  ToggleRiskLinkResultAction,
  ToggleRiskLinkSummaryAction
} from '../actions';
import {
  LoadRiskLinkAnalysisDataAction,
  LoadRiskLinkPortfolioDataAction,
  ToggleRiskLinkEDMAndRDMSelectedAction
} from '../actions/risk_link.actions';
import {catchError, mergeMap, switchMap} from 'rxjs/operators';
import {of} from 'rxjs/internal/observable/of';
import {RiskApi} from '../../services/risk.api';
import {forkJoin} from 'rxjs';
import {RiskLinkStateService} from '../../services/riskLink-action.service';

const initiaState: RiskLinkModel = {
  listEdmRdm: {
    data: null,
    dataSelected: [],
    selectedListEDMAndRDM: {edm: null, rdm: null},
    totalNumberElement: 0,
    searchValue: '',
    numberOfElement: 0
  },
  linking: {
    edm: null,
    rdm: null,
    autoLinks: null,
    linked: null,
    analysis: null,
    portfolio: null
  },
  display: {
    displayTable: false,
    displayImport: false,
  },
  collapse: {
    collapseHead: true,
    collapseAnalysis: true,
    collapseResult: true,
  },
  checked: {
    checkedARC: false,
    checkedPricing: false,
  },
  financialValidator: {
    rmsInstance: {data: ['AZU-P-RL17-SQL14', 'AZU-U-RL17-SQL14', 'AZU-U2-RL181-SQL16'], selected: 'AZU-P-RL17-SQL14'},
    financialPerspectiveELT: {
      data: ['Net Loss Pre Cat (RL)', 'Gross Loss (GR)', 'Net Cat (NC)'],
      selected: 'Net Loss Pre Cat (RL)'
    },
    targetCurrency: {
      data: ['Main Liability Currency (MLC)', 'Analysis Currency', 'User Defined Currency'],
      selected: 'Main Liability Currency (MLC)'
    },
    calibration: {data: ['Add calibration', 'item 1', 'item 2'], selected: 'Add calibration'},
  },
  financialPerspective: {
    rdm: {data: null, selected: null},
    analysis: null,
    treaty: null,
    standard: null,
    target: 'currentSelection'
  },
  analysis: null,
  portfolios: null,
  results: null,
  summaries: null,
  selectedEDMOrRDM: null,
  activeAddBasket: false
};

@State<RiskLinkModel>({
  name: 'RiskLinkModel',
  defaults: initiaState
})
export class RiskLinkState {
  ctx = null;

  constructor(private riskApi: RiskApi, private riskLinkFacade: RiskLinkStateService) {
  }

  /**
   * Selectors
   */
  @Selector()
  static getRiskLinkState(state: RiskLinkModel) {
    return state;
  }

  @Selector()
  static getFinancialValidator(state: RiskLinkModel) {
    return _.get(state, 'financialValidator', null);
  }

  @Selector()
  static getFinancialValidatorAttr(path: string, value: any) {
    return (state: any) => _.get(state.RiskLinkModel, path, value);
  }

  @Selector()
  static getFinancialPerspective(state: RiskLinkModel) {
    return _.get(state, 'financialPerspective', null);
  }

  /**
   * Commands
   */
  @Action(PatchRiskLinkAction)
  patchRiskLinkState(ctx: StateContext<RiskLinkState>, {payload}: PatchRiskLinkAction) {
    this.riskLinkFacade.patchRiskLinkState(ctx, payload);
  }

  @Action(PatchRiskLinkCollapseAction)
  patchCollapseState(ctx: StateContext<RiskLinkModel>, {payload}: PatchRiskLinkCollapseAction) {
    this.riskLinkFacade.patchCollapseState(ctx, payload);
  }

  @Action(PatchRiskLinkDisplayAction)
  patchDisplayState(ctx: StateContext<RiskLinkModel>, {payload}: PatchRiskLinkDisplayAction) {
    this.riskLinkFacade.patchDisplayState(ctx, payload);
  }

  @Action(PatchRiskLinkFinancialPerspectiveAction)
  patchFinancialPerspectiveState(ctx: StateContext<RiskLinkModel>, {payload}: PatchRiskLinkFinancialPerspectiveAction) {
    this.riskLinkFacade.patchFinancialPerspectiveState(ctx, payload);
  }

  @Action(PatchAddToBasketStateAction)
  patchAddToBasketState(ctx: StateContext<RiskLinkModel>) {
    this.riskLinkFacade.patchAddToBasketState(ctx);
  }

  @Action(PatchTargetFPAction)
  patchTargetFP(ctx: StateContext<RiskLinkModel>, {payload}: PatchTargetFPAction) {
    this.riskLinkFacade.patchTargetFP(ctx, payload);
  }

  @Action(PatchResultsAction)
  patchResult(ctx: StateContext<RiskLinkModel>, {payload}: PatchResultsAction) {
    this.riskLinkFacade.patchResult(ctx, payload);
  }

  @Action(ToggleRiskLinkEDMAndRDMAction)
  toggleRiskLinkEDMAndRDM(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkEDMAndRDMAction) {
    this.riskLinkFacade.toggleRiskLinkEDMAndRDM(ctx, payload);
  }

  @Action(ToggleRiskLinkPortfolioAction)
  toggleRiskLinkPortfolio(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkPortfolioAction) {
    this.riskLinkFacade.toggleRiskLinkPortfolio(ctx, payload);
  }

  @Action(ToggleRiskLinkAnalysisAction)
  toggleRiskLinkAnalysis(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkAnalysisAction) {
    this.riskLinkFacade.toggleRiskLinkAnalysis(ctx, payload);
  }

  @Action(ToggleRiskLinkResultAction)
  toggleRiskLinkResult(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkResultAction) {
    this.riskLinkFacade.toggleRiskLinkResult(ctx, payload);
  }

  @Action(ToggleRiskLinkSummaryAction)
  toggleRiskLinkSummary(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkSummaryAction) {
    this.riskLinkFacade.toggleRiskLinkSummary(ctx, payload);
  }

  @Action(ToggleRiskLinkFPStandardAction)
  toggleRiskLinkFPStandard(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkFPStandardAction) {
    this.riskLinkFacade.toggleRiskLinkFPStandard(ctx, payload);
  }

  @Action(ToggleRiskLinkFPAnalysisAction)
  toggleRiskLinkFPAnalysis(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkFPAnalysisAction) {
    this.riskLinkFacade.toggleRiskLinkFPAnalysis(ctx, payload);
  }

  @Action(AddToBasketAction)
  addToBasket(ctx: StateContext<RiskLinkModel>) {
    this.riskLinkFacade.addToBasket(ctx);
  }

  @Action(ApplyFinancialPerspectiveAction)
  applyFinancialPerspective(ctx: StateContext<RiskLinkModel>, {payload}: ApplyFinancialPerspectiveAction) {
    this.riskLinkFacade.applyFinancialPerspective(ctx, payload);
  }

  @Action(SaveFinancialPerspectiveAction)
  saveFinancialPerspective(ctx: StateContext<RiskLinkModel>) {
    this.riskLinkFacade.saveFinancialPerspective(ctx);
  }

  @Action(RemoveFinancialPerspectiveAction)
  removeFinancialPerspective(ctx: StateContext<RiskLinkModel>, {payload}: RemoveFinancialPerspectiveAction) {
    this.riskLinkFacade.removeFinancialPerspective(ctx, payload);
  }

  @Action(DeleteFromBasketAction)
  deleteFromBasket(ctx: StateContext<RiskLinkModel>, {payload}: DeleteFromBasketAction) {
    this.riskLinkFacade.deleteFromBasket(ctx, payload);
  }

  @Action(DeleteEdmRdmaction)
  deleteEdmRdm(ctx: StateContext<RiskLinkModel>, {payload}: DeleteEdmRdmaction) {
    this.riskLinkFacade.deleteEdmRdm(ctx, payload);
  }

  @Action(LoadRiskLinkAnalysisDataAction)
  loadRiskLinkAnalysisData(ctx: StateContext<RiskLinkModel>, {payload}: LoadRiskLinkAnalysisDataAction) {
    return this.riskLinkFacade.loadRiskLinkAnalysisData(ctx, payload);
  }

  @Action(LoadRiskLinkPortfolioDataAction)
  loadRiskLinkPortfolioData(ctx: StateContext<RiskLinkModel>, {payload}: LoadRiskLinkPortfolioDataAction) {
    console.log('portfolio');
    return this.riskLinkFacade.loadRiskLinkPortfolioData(ctx, payload);
  }

  @Action(LoadPortfolioForLinkingAction)
  loadPortfolioForLinking(ctx: StateContext<RiskLinkModel>, {payload}: LoadPortfolioForLinkingAction) {
    return this.riskLinkFacade.loadPortfolioForLinking(ctx, payload);
  }

  @Action(LoadAnalysisForLinkingAction)
  loadAnalysisForLinking(ctx: StateContext<RiskLinkModel>, {payload}: LoadAnalysisForLinkingAction) {
    return this.riskLinkFacade.loadAnalysisForLinking(ctx, payload);
  }

  @Action(ToggleRiskLinkEDMAndRDMSelectedAction)
  toggleRiskLinkEDMAndRDMSelected(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkEDMAndRDMSelectedAction) {
    this.riskLinkFacade.toggleRiskLinkEDMAndRDMSelected(ctx, payload);
  }

  @Action(ToggleAnalysisForLinkingAction)
  toggleAnalysisForLinking(ctx: StateContext<RiskLinkModel>, {payload}: ToggleAnalysisForLinkingAction) {
    this.riskLinkFacade.toggleAnalysisForLinking(ctx, payload);
  }

  /** ACTION ADDED EDM AND RDM */
  @Action(SelectRiskLinkEDMAndRDMAction)
  selectRiskLinkEDMAndRDM(ctx: StateContext<RiskLinkModel>) {
    this.riskLinkFacade.selectRiskLinkEDMAndRDM(ctx);
  }

  /** SEARCH WITH KEYWORD OR PAGE OF EDM AND RDM */
  @Action(SearchRiskLinkEDMAndRDMAction)
  searchRiskLinkEDMAndRDM(ctx: StateContext<RiskLinkModel>, {payload}: SearchRiskLinkEDMAndRDMAction) {
    return this.riskLinkFacade.searchRiskLinkEDMAndRDM(ctx, payload);
  }

  /** LOAD DATA FOR FINANCIAL PERSPECTIVE */
  @Action(LoadFinancialPerspectiveAction)
  loadFinancialPerspective(ctx: StateContext<RiskLinkModel>, {payload}: LoadFinancialPerspectiveAction) {
    this.riskLinkFacade.loadFinancialPerspective(ctx, payload);
  }

  /** LOAD DATA WHEN OPEN RISK LINK PAGE */
  @Action(LoadRiskLinkDataAction)
  loadRiskLinkData(ctx: StateContext<RiskLinkModel>) {
    return this.riskLinkFacade.loadRiskLinkData(ctx);
  }
}
