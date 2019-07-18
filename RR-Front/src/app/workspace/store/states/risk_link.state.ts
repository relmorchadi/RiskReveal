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
    const state = ctx.getState();
    return forkJoin(
      payload.map(dt => this.riskApi.searchRiskLinkAnalysis(dt.id, dt.name))
    ).pipe(
      switchMap(out => {
        let dataTable = {};
        out.forEach((dt: any, i) => {
            dataTable = {
              ...dataTable,
              [payload[i].id]: {
                data: Object.assign({},
                  ...dt.content.map(analysis => ({
                      [analysis.analysisId]: {
                        ...analysis,
                        selected: false
                      }
                    }
                  ))),
                selectedData: [],
                lastSelectedIndex: null,
                totalNumberElement: dt.totalElements,
                numberOfElement: dt.size,
                filter: {}
              }
            };
          }
        );
        console.log(state.listEdmRdm.selectedListEDMAndRDM.edm);
        return of(ctx.patchState(
          {
            analysis: dataTable,
          }
        ));
      }),
      catchError(err => {
        // @TODO Handle error case
        console.error('Failed to search Analysis Count');
        return of();
      })
    );
  }

  @Action(LoadRiskLinkPortfolioDataAction)
  loadRiskLinkPortfolioData(ctx: StateContext<RiskLinkModel>, {payload}: LoadRiskLinkPortfolioDataAction) {
    const state = ctx.getState();
    return forkJoin(
      payload.map(dt => this.riskApi.searchRiskLinkPortfolio(dt.id, dt.name))
    ).pipe(
      switchMap(out => {
        let dataTable = {};
        out.forEach((dt: any, i) => {
            dataTable = {
              ...dataTable,
              [payload[i].id]: {
                data: Object.assign({},
                  ...dt.content.map(portfolio => ({
                      [portfolio.dataSourceId]: {
                        ...portfolio,
                        selected: false
                      }
                    }
                  ))),
                selectedData: [],
                lastSelectedIndex: null,
                totalNumberElement: dt.totalElements,
                numberOfElement: dt.size,
                filter: {}
              }
            };
          }
        );
        return of(ctx.patchState(
          {
            portfolios: dataTable,
          }));
      }),
      catchError(err => {
        // @TODO Handle error case
        console.error('Failed to search contracts Count');
        return of();
      })
    );
  }

  @Action(LoadPortfolioForLinkingAction)
  loadPortfolioForLinking(ctx: StateContext<RiskLinkModel>, {payload}: LoadPortfolioForLinkingAction) {
    const state = ctx.getState();
    const {id, name} = payload;
    return this.riskApi.searchRiskLinkPortfolio(id, name).pipe(
      mergeMap(dt => {
        const dataTable = {
          data: Object.assign({},
            ...dt.content.map(portfolio => ({
                [portfolio.dataSourceId]: {
                  ...portfolio,
                  selected: false
                }
              }
            ))),
          selectedData: [],
          lastSelectedIndex: null,
          totalNumberElement: dt.totalElements,
          numberOfElement: dt.size,
          filter: {}
        };
        return of(ctx.patchState(
          {
            linking: {
              ...state.linking,
              portfolio: dataTable
            }
          }));
      }),
      catchError(err => {
        // @TODO Handle error case
        console.error('Failed to search Analysis Count');
        return of();
      })
    );
  }

  @Action(LoadAnalysisForLinkingAction)
  loadAnalysisForLinking(ctx: StateContext<RiskLinkModel>, {payload}: LoadAnalysisForLinkingAction) {
    const state = ctx.getState();

    this.riskApi.searchRiskLinkAnalysis(payload.id, payload.name).pipe(
      switchMap(dt => {
        const dataTable = {
          [payload.id]: {
            data: Object.assign({},
              ...dt.content.map(analysis => ({
                  [analysis.analysisId]: {
                    ...analysis,
                    selected: false
                  }
                }
              ))),
            lastSelectedIndex: null,
            totalNumberElement: dt.totalElements,
            numberOfElement: dt.size,
            filter: {}
          }
        };

        return of(ctx.patchState(
          {
            linking: {
              ...state.linking,
              analysis: dataTable
            }
          }));
      }),
      catchError(err => {
        // @TODO Handle error case
        console.error('Failed to search contracts Count');
        return of();
      })
    );
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
    const state = ctx.getState();
    console.log('state: ', state);
    const {keyword, size} = payload;
    const array = state.listEdmRdm.dataSelected;
    return this.riskApi.searchRiskLinkData(keyword, size).pipe(
      mergeMap(
        (ds: any) =>
          of(ctx.patchState(
            {
              listEdmRdm: {
                ...state.listEdmRdm,
                data: Object.assign({},
                  ...ds.content.map(item => {
                    const validator = array.filter(vd => vd.id == item.id);
                    const validate = validator.length === 1;
                    return ({
                        [item.id]: {
                          ...item,
                          selected: validate,
                          scanned: false,
                        }
                      }
                    );
                  })),
                totalNumberElement: ds.totalElements,
                searchValue: keyword,
                numberOfElement: ds.size
              }
            }))
      )
    );
  }

  /** LOAD DATA FOR FINANCIAL PERSPECTIVE */
  @Action(LoadFinancialPerspectiveAction)
  loadFinancialPerspective(ctx: StateContext<RiskLinkModel>, {payload}: LoadFinancialPerspectiveAction) {
    this.riskLinkFacade.loadFinancialPerspective(ctx, payload);
  }

  /** LOAD DATA WHEN OPEN RISK LINK PAGE */
  @Action(LoadRiskLinkDataAction)
  loadRiskLinkData(ctx: StateContext<RiskLinkModel>) {
    const state = ctx.getState();
    return this.riskApi.searchRiskLinkData().pipe(
      mergeMap(
        (ds: any) =>
          of(ctx.patchState(
            {
              listEdmRdm: {
                ...state.listEdmRdm,
                data: Object.assign({},
                  ...ds.content.map(item => ({
                      [item.id]: {
                        ...item,
                        selected: false,
                        source: '',
                      }
                    }
                  ))),
                searchValue: '',
                totalNumberElement: ds.totalElements,
                numberOfElement: ds.size
              },
              display: {displayImport: false, displayTable: false},
              results: null,
              summaries: null,
              analysis: null,
              portfolios: null
            }))
      )
    );
  }
}
