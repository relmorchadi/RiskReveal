import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';

import * as _ from 'lodash';
import {RiskLinkModel} from '../../model/risk_link.model';
import {
  AddToBasketAction,
  ApplyFinancialPerspectiveAction,
  DeleteFromBasketAction,
  LoadAnalysisForLinkingAction,
  LoadFinancialPerspectiveAction,
  LoadPortfolioForLinkingAction,
  LoadRiskLinkDataAction,
  PatchAddToBasketStateAction,
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
    rmsInstance: {data: ['AZU-P-RL17-SQL14', 'AZU-P-RL17-SQL15'], selected: 'AZU-P-RL17-SQL14'},
    financialPerspectiveELT: {
      data: ['Net Loss Pre Cat (RL)', 'Gross Loss (GR)', 'Net Cat (NC)'],
      selected: 'Net Loss Pre Cat (RL)'
    },
    targetCurrency: {
      data: ['Main Liability Currency (MLC)', 'User Defined Currency', 'Underlying Currency'],
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
export class RiskLinkState implements NgxsOnInit {
  ctx = null;

  constructor(private riskApi: RiskApi) {
  }

  ngxsOnInit(ctx?: StateContext<RiskLinkState>): void | any {
    this.ctx = ctx;
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
    if (_.isArray(payload))
      payload.forEach(item => ctx.patchState({[item.key]: item.value}));
    else
      ctx.patchState({[payload.key]: payload.value});
  }

  @Action(PatchRiskLinkCollapseAction)
  patchCollapseState(ctx: StateContext<RiskLinkModel>, {payload}: PatchRiskLinkCollapseAction) {
    const state = ctx.getState();
    let value = null;
    if (payload.key === 'collapseAnalysis') {
      value = !state.collapse.collapseAnalysis;
    }
    if (payload.key === 'collapseResult') {
      value = !state.collapse.collapseResult;
    }
    if (payload.key === 'collapseHead') {
      value = !state.collapse.collapseHead;
    }
    const newValue = _.merge({}, state.collapse, {[payload.key]: value});
    ctx.patchState({collapse: newValue});
  }

  @Action(PatchRiskLinkDisplayAction)
  patchDisplayState(ctx: StateContext<RiskLinkModel>, {payload}: PatchRiskLinkDisplayAction) {
    const state = ctx.getState();
    const newValue = _.merge({}, state.display, {[payload.key]: payload.value});
    ctx.patchState({display: newValue});
  }

  @Action(PatchRiskLinkFinancialPerspectiveAction)
  patchFinancialPerspectiveState(ctx: StateContext<RiskLinkModel>, {payload}: PatchRiskLinkFinancialPerspectiveAction) {
    const state = ctx.getState();
    const newValue = _.merge({}, state.financialValidator, {
      [payload.key]: {
        data: state.financialValidator[payload.key].data,
        selected: payload.value
      }
    });
    ctx.patchState({financialValidator: newValue});
  }

  @Action(PatchAddToBasketStateAction)
  patchAddToBasketState(ctx: StateContext<RiskLinkModel>) {
    const state = ctx.getState();
    let analysis = _.toArray(state.analysis);
    let portfolio = _.toArray(state.portfolios);
    analysis = analysis.map(dt => _.toArray(dt.data));
    portfolio = portfolio.map(dt => _.toArray(dt.data));
    const data = analysis.concat(portfolio);
    let count = 0;
    data.forEach(dt => {
      count = count + _.filter(dt, ws => ws.selected).length
    });
    ctx.patchState({
      activeAddBasket: count > 0
    });
  }

  @Action(PatchTargetFPAction)
  patchTargetFP(ctx: StateContext<RiskLinkModel>, {payload}: PatchTargetFPAction) {
    const state = ctx.getState();
    ctx.patchState({
      financialPerspective: {
        ...state.financialPerspective,
        target: payload
      }
    });
  }

  @Action(ToggleRiskLinkEDMAndRDMAction)
  toggleRiskLinkEDMAndRDM(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkEDMAndRDMAction) {
    const state = ctx.getState();
    const {action, RDM, source} = payload;
    let array = _.toArray(state.listEdmRdm.data);
    let newData = {};
    if (action === 'selectOne') {
      const item = RDM.id;
      const {selected} = state.listEdmRdm.data[item];
      if (selected) {
        array = array.filter(dt => dt.id !== item && dt.selected === true);
      } else {
        array = array.filter(dt => dt.id == item || dt.selected === true);
      }

      ctx.patchState({
        listEdmRdm: {
          ...state.listEdmRdm,
          data: {
            ...state.listEdmRdm.data,
            [item]: {...state.listEdmRdm.data[item], selected: !selected, source: !selected ? source : ''}
          },
          dataSelected: array
        }
      });
    } else {
      if (action === 'selectAll') {
        array.forEach(dt => {
          newData = _.merge(newData, {
            [dt.id]: {
              ...dt,
              selected: true,
              source: source
            }
          });
          dt = {...dt, selected: true};
        });
      } else if (action === 'unselectAll') {
        array.forEach(dt => {
          newData = _.merge(newData, {
            [dt.id]: {
              ...dt,
              selected: false,
              source: ''
            }
          });
        });
        array = [];
      }
      ctx.patchState({
        listEdmRdm: {
          ...state.listEdmRdm,
          data: newData,
          dataSelected: array
        }
      });
    }
  }

  @Action(ToggleRiskLinkPortfolioAction)
  toggleRiskLinkPortfolio(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkPortfolioAction) {
    const state = ctx.getState();
    const {action, value, item} = payload;
    const selectedPortfolio = _.filter(_.toArray(state.listEdmRdm.selectedListEDMAndRDM.edm), dt => dt.selected)[0];
    const portfolios = _.toArray(state.portfolios[selectedPortfolio.id].data);
    let newData = {};
    if (action === 'selectOne') {
      ctx.patchState({
        portfolios: {
          ...state.portfolios,
          [selectedPortfolio.id]: {
            ...state.portfolios[selectedPortfolio.id],
            data: {
              ...state.portfolios[selectedPortfolio.id].data,
              [item.dataSourceId]: {
                ...state.portfolios[selectedPortfolio.id].data[item.dataSourceId],
                selected: value
              }
            }
          },
        }
      });
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      portfolios.forEach((dt: any) => {
          newData = _.merge(newData, {
            [dt.dataSourceId]: {
              ...dt,
              selected: selected
            }
          });
        }
      );
      ctx.patchState({
        portfolios: {
          ...state.portfolios,
          [selectedPortfolio.id]: {
            ...state.portfolios[selectedPortfolio.id],
            data: newData
          }
        },
      });

    }
  }

  @Action(ToggleRiskLinkAnalysisAction)
  toggleRiskLinkAnalysis(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkAnalysisAction) {
    const state = ctx.getState();
    const {action, value, item} = payload;
    const selectedAnalysis = _.filter(_.toArray(state.listEdmRdm.selectedListEDMAndRDM.rdm), dt => dt.selected)[0];
    const analysis = _.toArray(state.analysis[selectedAnalysis.id].data);
    let newData = {};
    if (action === 'selectOne') {
      ctx.patchState({
        analysis: {
          ...state.analysis,
          [selectedAnalysis.id]: {
            ...state.analysis[selectedAnalysis.id],
            data: {
              ...state.analysis[selectedAnalysis.id].data,
              [item.analysisId]: {
                ...state.analysis[selectedAnalysis.id].data[item.analysisId],
                selected: value
              }
            }
          },
        }
      });
      ctx.dispatch(new PatchAddToBasketStateAction());
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      analysis.forEach((st: any) => {
          newData = _.merge(newData, {
            [st.analysisId]: {
              ...st,
              selected: selected
            }
          });
        }
      );
      ctx.patchState({
        analysis: {
          ...state.analysis,
          [selectedAnalysis.id]: {
            ...state.analysis[selectedAnalysis.id],
            data: newData
          }
        },
      });
      ctx.dispatch(new PatchAddToBasketStateAction());
    }
  }

  @Action(ToggleRiskLinkResultAction)
  toggleRiskLinkResult(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkResultAction) {
    const state = ctx.getState();
    const {action, value, item} = payload;
    const result = _.toArray(state.results.data);
    if (action === 'selectOne') {
      ctx.patchState({
        results: {
          ...state.results,
          data: {...state.results.data,
            [item.id]: {...state.results.data[item.id], selected: value}
          }
        }
      });
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      ctx.patchState({
        results: {
          ...state.results,
          data: Object.assign({}, ...result.map(dt => ({[dt.id]: {...dt, selected: selected}})))
        },
      });
    }
  }

  @Action(ToggleRiskLinkSummaryAction)
  toggleRiskLinkSummary(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkSummaryAction) {
    const state = ctx.getState();
    const {action, value, item} = payload;
    const summaries = _.toArray(state.summaries.data);
    if (action === 'selectOne') {
      ctx.patchState({
        summaries: {
          ...state.summaries,
          data: {...state.summaries.data,
            [item.id]: {...state.summaries.data[item.id], selected: value}
          }
        }
      });
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      ctx.patchState({
        summaries: {
          ...state.summaries,
          data: Object.assign({}, ...summaries.map(dt => ({[dt.id]: {...dt, selected: selected}})))
        },
      });
    }
  }

  @Action(ToggleRiskLinkFPStandardAction)
  toggleRiskLinkFPStandard(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkFPStandardAction) {
    const state = ctx.getState();
    const {action, value, item} = payload;
    const fpStandard = _.toArray(state.financialPerspective.standard);
    if (action === 'selectOne') {
      ctx.patchState({
        financialPerspective: {
          ...state.financialPerspective,
          standard: {...state.financialPerspective.standard,
            [item.id]: {...state.financialPerspective.standard[item.id], selected: value}
          }
        }
      });
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      ctx.patchState({
        financialPerspective: {
          ...state.financialPerspective,
          standard: Object.assign({}, ...fpStandard.map(dt => ({[dt.id]: {...dt, selected: selected}})))
        },
      });
    }
  }

  @Action(ToggleRiskLinkFPAnalysisAction)
  toggleRiskLinkFPAnalysis(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkFPAnalysisAction) {
    const state = ctx.getState();
    const {action, value, item} = payload;
    const fpAnalysis = _.toArray(state.financialPerspective.analysis.data);
    if (action === 'selectOne') {
      ctx.patchState({
        financialPerspective: {
          ...state.financialPerspective,
          analysis: {...state.financialPerspective.analysis,
            data: {
              ...state.financialPerspective.analysis.data,
              [item.id]: {...state.financialPerspective.analysis.data[item.id], selected: value}
            }
          }
        }
      });
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      ctx.patchState({
        financialPerspective: {
          ...state.financialPerspective,
          analysis: {
            ...state.financialPerspective.analysis,
            data: Object.assign({}, ...fpAnalysis.map(dt => ({[dt.id]: {...dt, selected: selected}})))
          }
        },
      });
    }
  }

  @Action(AddToBasketAction)
  addToBasket(ctx: StateContext<RiskLinkModel>) {
    const state = ctx.getState();
    let analysis = _.toArray(state.analysis).map(dt => _.toArray(dt.data));
    let portfolio = _.toArray(state.portfolios).map(dt => _.toArray(dt.data));
    const selectAnalysis = analysis.map(dt => _.filter(dt, ws => ws.selected));
    const selectPortfolio = portfolio.map(dt => _.filter(dt, ws => ws.selected));
    let dataAnalysis = [];
    let dataPortfolio = [];
    selectAnalysis.forEach(dt => dataAnalysis = [...dataAnalysis, ...dt]);
    selectPortfolio.forEach(dt => dataPortfolio = [...dataPortfolio, ...dt]);
    let results = {data: {}, filter: {}, numberOfElement: 0};
    let summary = {data: {}, filter: {}, numberOfElement: 0};
    dataAnalysis.forEach(dt => {
      results = {
        ...results, data: {
          ...results.data,
          [dt.rdmId + dt.analysisId]: {
            ...dt,
            scanned: true,
            status: 100,
            unitMultiplier: 1,
            targetCurrency: 'USD',
            financialPerspective: ['GR'],
            occurrenceBasis: 'PerEvent',
            regionPeril: 'NAEQ',
            ty: true,
            peqt: '0/3',
            selected: false
          },
        },
        numberOfElement: results.numberOfElement + 1
      };
    });

    dataPortfolio.forEach(dt => {
      summary = {
        ...summary, data: {
          ...summary.data,
          [dt.edmId + dt.dataSourceId]: {
            ...dt,
            selected: false,
            scanned: true,
            status: 100,
            unitMultiplier: 1,
            proportion: 100,
            targetCurrency: 'USD',
            sourceCurrency: 'USD',
            exposedLocation: true
          },
        },
        numberOfElement: results.numberOfElement + 1
      };
    });

    ctx.patchState(
      {
        summaries: summary,
        results: results
      }
    );
  }

  @Action(ApplyFinancialPerspectiveAction)
  applyFinancialPerspective(ctx: StateContext<RiskLinkModel>, {payload}: ApplyFinancialPerspectiveAction) {
    const state = ctx.getState();
    if (payload === 'replace') {
      const fpApplied = _.filter(_.toArray(state.financialPerspective.standard), dt => dt.selected ).map(dt => dt.code);
      if (state.financialPerspective.target === 'currentSelection') {
        const selectedAnalysis = _.filter(_.toArray(state.financialPerspective.analysis.data), dt => dt.selected);
        const modif = Object.assign({}, ...selectedAnalysis.map(item => {
          return ({[item.id]: {...item, financialPerspective: fpApplied}});
        }));
        console.log(modif, state.financialPerspective.analysis.data);
        ctx.patchState({
          financialPerspective: {
            ...state.financialPerspective,
            analysis: {
              ...state.financialPerspective.analysis,
              data:  {...state.financialPerspective.analysis.data, ...modif}
            }
          }
        });
      } else {
        ctx.patchState({
          financialPerspective: {
            ...state.financialPerspective,
            analysis: {
              ...state.financialPerspective.analysis,
              data: Object.assign({}, ..._.toArray(state.financialPerspective.analysis.data).map(item => {
                return ({[item.id]: {...item, financialPerspective: fpApplied}});
              }))
            }
          }
        });
      }
    } else {
      const fpApplied: any = _.filter(_.toArray(state.financialPerspective.standard), dt => dt.selected ).map(dt => dt.code);
      if (state.financialPerspective.target === 'currentSelection') {
        const selectedAnalysis = _.filter(_.toArray(state.financialPerspective.analysis.data), dt => dt.selected);
        const modif = Object.assign({}, ...selectedAnalysis.map(item => {
          return ({[item.id]: {...item, financialPerspective: _.unionBy([...item.financialPerspective, ...fpApplied])}});
        }));
        ctx.patchState({
          financialPerspective: {
            ...state.financialPerspective,
            analysis: {
              ...state.financialPerspective.analysis,
              data: {...state.financialPerspective.analysis.data, ...modif}
            }
          }
        });
      } else {
        ctx.patchState({
          financialPerspective: {
            ...state.financialPerspective,
            analysis: {
              ...state.financialPerspective.analysis,
              data: Object.assign({}, ..._.toArray(state.financialPerspective.analysis.data).map(item => {
                return ({[item.id]: {...item, financialPerspective: _.unionBy([...item.financialPerspective, ...fpApplied])}});
              }))
            }
          }
        });
      }
    }
  }

  @Action(SaveFinancialPerspectiveAction)
  saveFinancialperspective(ctx: StateContext<RiskLinkModel>) {
    const state = ctx.getState();
    ctx.patchState({
      results: {
        ...state.results,
        data: Object.assign({}, ..._.toArray(state.financialPerspective.analysis.data).map(item => {
          return ({[item.id]: {...item, selected: false}});
        }))
      }
    });
  }

  @Action(RemoveFinancialPerspectiveAction)
  removeFinancialPerspective(ctx: StateContext<RiskLinkModel>, {payload}: RemoveFinancialPerspectiveAction) {
    const state = ctx.getState();
    const newData =  Object.assign({}, ...[payload.item].map(item => {
      return({[item.id]: {...item, financialPerspective: _.filter(item.financialPerspective, dt => dt !== payload.fp)}});
    }));
    console.log(newData);
    ctx.patchState({
      financialPerspective: {
        ...state.financialPerspective,
        analysis: {
          ...state.financialPerspective.analysis,
          data: {...state.financialPerspective.analysis.data, ...newData}
        }
      }
    });
  }

  @Action(DeleteFromBasketAction)
  deleteFromBasket(ctx: StateContext<RiskLinkModel>, {payload}: DeleteFromBasketAction) {
    const state = ctx.getState();
    const {id, scope} = payload;
    let newData = {};
    if (scope === 'summary') {
      const summary = _.filter(_.toArray(state.summaries.data), dt => dt.dataSourceId !== id);
      summary.forEach(dt => {
        newData = {...newData, [dt.dataSourceId]: {...dt}};
      });
      ctx.patchState(
        {
          summaries: {
            ...state.summaries,
            data: newData,
            numberOfElement: summary.length
          }
        }
      );
    } else if (scope === 'results') {
      const results = _.filter(_.toArray(state.results.data), dt => dt.analysisId !== id);
      results.forEach(dt => {
        newData = {...newData, [dt.analysisId]: {...dt}};
      });
      ctx.patchState(
        {
          results: {
            ...state.results,
            data: newData,
            numberOfElement: results.length
          }
        }
      );
    }
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
  ToggleRiskLinkEDMAndRDMSelected(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkEDMAndRDMSelectedAction) {
    const state = ctx.getState();
    let array;
    let selected;
    const {id, type} = payload;
    let newDataSelectedEDM = {};
    let newDataSelectedRDM = {};
    array = _.toArray(state.listEdmRdm.selectedListEDMAndRDM.edm);
    array.forEach(dt => {
      newDataSelectedEDM = _.merge(newDataSelectedEDM, {
        [dt.id]: {
          ...dt,
          selected: false,
        }
      });
    });
    array = _.toArray(state.listEdmRdm.selectedListEDMAndRDM.rdm);
    array.forEach(dt => {
      newDataSelectedRDM = _.merge(newDataSelectedRDM, {
        [dt.id]: {
          ...dt,
          selected: false,
        }
      });
    });
    type === 'edm'
      ? selected = state.listEdmRdm.selectedListEDMAndRDM.edm[id].selected
      : selected = state.listEdmRdm.selectedListEDMAndRDM.rdm[id].selected;
    ctx.dispatch(new PatchRiskLinkDisplayAction({key: 'displayTable', value: !selected}));
    if (type === 'edm') {
      ctx.patchState({
        listEdmRdm: {
          ...state.listEdmRdm,
          selectedListEDMAndRDM: {
            edm: {
              ...newDataSelectedEDM,
              [id]: {...newDataSelectedEDM[id], selected: !selected}
            },
            rdm: {...newDataSelectedRDM}
          },
        },
        selectedEDMOrRDM: type
      });
    } else {
      ctx.patchState({
        listEdmRdm: {
          ...state.listEdmRdm,
          selectedListEDMAndRDM: {
            edm: {...newDataSelectedEDM},
            rdm: {
              ...newDataSelectedRDM,
              [id]: {...newDataSelectedRDM[id], selected: !selected}
            }
          },
        },
        selectedEDMOrRDM: type
      });
    }
  }

  @Action(ToggleAnalysisForLinkingAction)
  ToggleAnalysisForLinking(ctx: StateContext<RiskLinkModel>, {payload}: ToggleAnalysisForLinkingAction) {
    const {linking} = ctx.getState();
    const {item: {id}} = payload;
    ctx.patchState(
      {
        linking: {
          ...linking,
          rdm: {...linking.rdm, [id]: {...linking.rdm[id], selected: !linking.rdm[id].selected}}
        }
      }
    );
  }

  /** ACTION ADDED EDM AND RDM */
  @Action(SelectRiskLinkEDMAndRDMAction)
  selectRiskLinkEDMAndRDM(ctx: StateContext<RiskLinkModel>) {
    const state = ctx.getState();
    const listDataToArray = _.toArray(state.listEdmRdm.data);
    const listSelected = {edm: {}, rdm: {}};
    _.forEach(listDataToArray, dt => {
      if (dt.selected && dt.type === 'edm') {
        console.log(state.portfolios, state.analysis);
        listSelected.edm = _.merge(listSelected.edm, {
          [dt.id]: {
            ...dt,
            scanned: true,
            selected: false,
          }
        });
      } else if (dt.selected && dt.type === 'rdm') {
        listSelected.rdm = _.merge(listSelected.rdm, {
          [dt.id]: {
            ...dt,
            scanned: true,
            selected: false,
          }
        });
      }
    });

    const filteredArray = listDataToArray.filter(e => e.source === 'link');
    let count = 0;
    if (filteredArray.length > 0) {
      forkJoin(
        filteredArray.map(dt => {
          const searchTerm = dt.name.substr(0, dt.name.lastIndexOf('_'));
          if (dt.name.length - searchTerm.length < 5) {
            return of([this.riskApi.searchRiskLinkData(searchTerm, '20'), searchTerm]);
          }
          return of(null);
        })
      ).subscribe(data => {
        data.forEach(dt => {
            count = count + 1;
            if (dt !== null) {
              dt[0].subscribe(
                ks => {
                  ks.content.forEach(ws => {
                    const trim = ws.name.substr(0, ws.name.lastIndexOf('_'));
                    if (trim === dt[1]) {
                      if (ws.type !== dt.type) {
                        if (ws.type === 'edm') {
                          listSelected.edm = _.merge({}, listSelected.edm, {
                            [ws.id]: {
                              ...ws,
                              scanned: true,
                              selected: false
                            }
                          });
                        } else {
                          listSelected.rdm = _.merge({}, listSelected.rdm, {
                            [ws.id]: {
                              ...ws,
                              scanned: true,
                              selected: false
                            }
                          });
                        }
                      }
                    }
                  });
                  if (count === data.length) {
                    ctx.patchState({
                      listEdmRdm: {
                        ...state.listEdmRdm,
                        selectedListEDMAndRDM: {
                          edm: listSelected.edm,
                          rdm: listSelected.rdm
                        }
                      },
                      linking: {
                        ...state.linking,
                        edm: listSelected.edm,
                        rdm: listSelected.rdm
                      },
                      financialPerspective: {
                        ...state.financialPerspective,
                        rdm: {data: listSelected.rdm, selected: null},
                      }
                    });
                    ctx.dispatch(new PatchRiskLinkDisplayAction({key: 'displayTable', value: false}));
                    ctx.dispatch(new LoadRiskLinkAnalysisDataAction(_.toArray(listSelected.rdm)));
                    ctx.dispatch(new LoadRiskLinkPortfolioDataAction(_.toArray(listSelected.edm)));
                  }
                }
              );
            }
          }
        );
      });
    } else {
      ctx.patchState({
        listEdmRdm: {
          ...state.listEdmRdm,
          selectedListEDMAndRDM: {
            edm: listSelected.edm,
            rdm: listSelected.rdm
          }
        },
        linking: {
          ...state.linking,
          edm: listSelected.edm,
          rdm: listSelected.rdm
        },
        financialPerspective: {
          ...state.financialPerspective,
          rdm: {data: listSelected.rdm, selected: null},
        }
      });
      ctx.dispatch(new PatchRiskLinkDisplayAction({key: 'displayTable', value: false}));
      ctx.dispatch(new LoadRiskLinkAnalysisDataAction(_.filter(listDataToArray, rt => rt.type === 'rdm' && rt.selected)));
      ctx.dispatch(new LoadRiskLinkPortfolioDataAction(_.filter(listDataToArray, et => et.type === 'edm' && et.selected)));
    }


  }

  /** SEARCH WITH KEYWORD OR PAGE OF EDM AND RDM */
  @Action(SearchRiskLinkEDMAndRDMAction)
  searchRiskLinkEDMAndRDM(ctx: StateContext<RiskLinkModel>, {payload}: SearchRiskLinkEDMAndRDMAction) {
    const state = ctx.getState();
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
    const state = ctx.getState();
    const FincancialPerspective = Object.assign({}, ...payload.map(item => ({[item.id]: {...item}})));
    ctx.patchState({
      financialPerspective: {
        ...state.financialPerspective,
        standard: FincancialPerspective,
        analysis: state.results
      }
    });
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
