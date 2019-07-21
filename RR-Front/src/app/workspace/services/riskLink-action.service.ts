import {StateContext} from '@ngxs/store';
import * as fromWs from '../store/actions';
import * as _ from 'lodash';
import {RiskLinkModel} from '../model/risk_link.model';
import {
  LoadRiskLinkAnalysisDataAction,
  LoadRiskLinkPortfolioDataAction,
  PatchAddToBasketStateAction,
  PatchRiskLinkDisplayAction,
} from '../store/actions';
import {catchError, mergeMap, switchMap} from 'rxjs/operators';
import {of} from 'rxjs/internal/observable/of';
import {RiskApi} from './risk.api';
import {forkJoin} from 'rxjs';
import {Injectable} from '@angular/core';
import {RiskLinkState} from '../store/states';
import {tap} from "rxjs/internal/operators/tap";
import {WorkspaceModel} from "../model";
import produce from "immer";

@Injectable({
  providedIn: 'root'
})
export class RiskLinkStateService {

  constructor(private riskApi: RiskApi) {
  }

  patchRiskLinkState(ctx: StateContext<RiskLinkState>, payload) {
    if (_.isArray(payload))
      payload.forEach(item => ctx.patchState({[item.key]: item.value}));
    else
      ctx.patchState({[payload.key]: payload.value});
  }

  patchCollapseState(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    let value = null;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    if (payload.key === 'collapseAnalysis') {
      value = !state.content[wsIdentifier].riskLink.collapse.collapseAnalysis;
    }
    if (payload.key === 'collapseResult') {
      value = !state.content[wsIdentifier].riskLink.collapse.collapseResult;
    }
    if (payload.key === 'collapseHead') {
      value = !state.content[wsIdentifier].riskLink.collapse.collapseHead;
    }
    const newValue = _.merge({}, state.content[wsIdentifier].riskLink.collapse, {[payload.key]: value});
    ctx.patchState( produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].riskLink.collapse = newValue;
    }));
  }

  patchDisplayState(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const newValue = _.merge({}, state.content[wsIdentifier].riskLink.display, {[payload.key]: payload.value});
    ctx.patchState(
      produce(ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.display = newValue;
        }));
  }

  patchFinancialPerspectiveState(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const newValue = _.merge({}, state.content[wsIdentifier].riskLink.financialValidator, {
      [payload.key]: {
        data: state.content[wsIdentifier].riskLink.financialValidator[payload.key].data,
        selected: payload.value
      }
    });
    ctx.patchState(
      produce(ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.financialValidator = newValue;
      }));
  }

  patchAddToBasketState(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    let analysis = _.toArray(state.content[wsIdentifier].riskLink.analysis);
    let portfolio = _.toArray(state.content[wsIdentifier].riskLink.portfolios);
    analysis = analysis.map(dt => _.toArray(dt.data));
    portfolio = portfolio.map(dt => _.toArray(dt.data));
    const data = analysis.concat(portfolio);
    let count = 0;
    data.forEach(dt => {
      count = count + _.filter(dt, ws => ws.selected).length;
    });
    ctx.patchState(
      produce(ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.activeAddBasket = count > 0;
    }));
  }

  patchTargetFP(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    ctx.patchState(
      produce(ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.financialPerspective.target = payload;
      }));
  }

  patchResult(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {id, target, value, scope} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    if (target === 'unitMultiplier') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.results.data[id].unitMultiplier = value;
        }));
    } else if (target === 'regionPeril') {
      if (scope === 'Single') {
        ctx.patchState(
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.results.data[id].regionPeril = value;
          }));
      } else {
        ctx.patchState(
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.results.data = Object.assign({},
              ..._.toArray(draft.content[wsIdentifier].riskLink.results.data).map(dt => {
                return ({[dt.id]: {...dt, regionPeril: value}});
              })
            );
          }));
      }
    } else if (target === 'targetCurrency') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.results.data[id].targetCurrency = value;
        }));
    } else if (target === 'occurrenceBasis') {
      if (scope === 'Single') {
        ctx.patchState(
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.results.data[id].occurrenceBasis = value;
          }));
      } else {
        ctx.patchState(
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.results.data = Object.assign({},
              ..._.toArray(draft.content[wsIdentifier].riskLink.results.data).map(dt => {
                return ({[dt.id]: {...dt, occurrenceBasis: value}});
              })
            );
          }));
      }
    }
  }

  toggleRiskLinkEDMAndRDM(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {action, RDM, source} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    let array = _.toArray(state.content[wsIdentifier].riskLink.listEdmRdm.data);
    if (action === 'selectOne') {
      const item = RDM.id;
      const {selected} = state.content[wsIdentifier].riskLink.listEdmRdm.data[item];
      if (selected) {
        array = array.filter(dt => dt.id !== item && dt.selected === true);
      } else {
        array = array.filter(dt => dt.id == item || dt.selected === true);
      }

      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.listEdmRdm.data = _.merge({}, draft.content[wsIdentifier].riskLink.listEdmRdm.data,
            {[item]: {...draft.content[wsIdentifier].riskLink.listEdmRdm.data[item], selected: !selected, source: !selected ? source : ''}});
          draft.content[wsIdentifier].riskLink.listEdmRdm.dataSelected = array;
        })
      );
    } else {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.listEdmRdm.data = Object.assign({},
            ...array.map(item => ({
                [item.id]: {
                  ...item,
                  selected: action === 'selectAll',
                  source: action === 'selectAll' ? source : '',
                }
              }
            )));
          draft.content[wsIdentifier].riskLink.listEdmRdm.dataSelected = array;
        }));
    }
  }

  toggleRiskLinkPortfolio(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {action, value, item} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const selectedPortfolio = _.filter(_.toArray(state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.edm),
        dt => dt.selected)[0];
    const portfolios = _.toArray(state.content[wsIdentifier].riskLink.portfolios[selectedPortfolio.id].data);
    let newData = {};
    if (action === 'selectOne') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.portfolios[selectedPortfolio.id].data[item.dataSourceId].selected = value
        }));
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
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.portfolios[selectedPortfolio.id].data = newData;
        })
      );
    }
  }

  toggleRiskLinkAnalysis(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {action, value, item} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const selectedAnalysis = _.filter(_.toArray(state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.rdm),
        dt => dt.selected)[0];
    const analysis = _.toArray(state.content[wsIdentifier].riskLink.analysis[selectedAnalysis.id].data);
    let newData = {};
    if (action === 'selectOne') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.analysis[selectedAnalysis.id].data[item.analysisId].selected = value;
        }));
      ctx.dispatch(new fromWs.PatchAddToBasketStateAction());
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
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.analysis[selectedAnalysis.id].data = newData;
        }));
      ctx.dispatch(new fromWs.PatchAddToBasketStateAction());
    }
  }

  toggleRiskLinkResult(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {action, value, item} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const result = _.toArray(state.content[wsIdentifier].riskLink.results.data);
    if (action === 'selectOne') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.results.data[item.id].selected = value;
        }));
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.results.data = Object.assign({},
            ...result.map(dt => ({[dt.id]: {...dt, selected: selected}})));
        }));
    }
  }

  toggleRiskLinkSummary(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {action, value, item} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const summaries = _.toArray(state.content[wsIdentifier].riskLink.summaries.data);
    if (action === 'selectOne') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.summaries.data[item.id].selected = value;
        }));
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.summaries.data = Object.assign({},
            ...summaries.map(dt => ({[dt.id]: {...dt, selected: selected}})));
        }));
    }
  }

  toggleRiskLinkFPStandard(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {action, value, item} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const fpStandard = _.toArray(state.content[wsIdentifier].riskLink.financialPerspective.standard);
    if (action === 'selectOne') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.financialPerspective.standard[item.id].selected = value;
        }));
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.financialPerspective.standard = Object.assign({},
            ...fpStandard.map(dt => ({[dt.id]: {...dt, selected: selected}})));
        })
      );
    }
  }

  toggleRiskLinkFPAnalysis(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {action, value, item} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const fpAnalysis = _.toArray(state.content[wsIdentifier].riskLink.financialPerspective.analysis.data);
    if (action === 'selectOne') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data[item.id].selected = value;
        }));
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data = Object.assign({},
            ...fpAnalysis.map(dt => ({[dt.id]: {...dt, selected: selected}})));
        }));
    }
  }

  addToBasket(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    let analysis = _.toArray(state.content[wsIdentifier].riskLink.analysis).map(dt => _.toArray(dt.data));
    let portfolio = _.toArray(state.content[wsIdentifier].riskLink.portfolios).map(dt => _.toArray(dt.data));
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
          [dt.analysisId]: {
            ...dt,
            scanned: true,
            status: 100,
            unitMultiplier: 1,
            proportion: 100,
            targetCurrency: 'USD',
            financialPerspective: ['GR'],
            occurrenceBasis: 'PerEvent',
            regionPeril: 'EUET',
            ty: true,
            peqt: [{title: 'RL_EUWS_Mv11.2_S-1003-LTR-Scor27c72u', selected: false},
              {title: 'RL_EUWS_Mv11.2_S-65-LTR', selected: false},
              {title: 'RL_EUWS_Mv11.2_S-66-LTR-Clue', selected: false}],
            override: 'EUET',
            reason: '',
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
          [dt.dataSourceId]: {
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
      produce(ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.summaries = summary;
        draft.content[wsIdentifier].riskLink.results = results;
      })
    );
  }

  applyFinancialPerspective(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const fP = state.content[wsIdentifier].riskLink.financialPerspective;
    if (payload === 'replace') {
      const fpApplied = _.filter(_.toArray(fP.standard), dt => dt.selected).map(dt => dt.code);
      if (fP.target === 'currentSelection') {
        const selectedAnalysis = _.filter(_.toArray(fP.analysis.data), dt => dt.selected);
        const modif = Object.assign({}, ...selectedAnalysis.map(item => {
          return ({[item.id]: {...item, financialPerspective: fpApplied}});
        }));
        ctx.patchState(
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data = {
              ...draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data, ...modif};
          }));
      } else {
        ctx.patchState(
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data = Object.assign({},
              ..._.toArray(draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data).map(item => {
                return ({[item.id]: {...item, financialPerspective: fpApplied}});
              }));
          })
        );
      }
    } else {
      const fpApplied: any = _.filter(_.toArray(fP.standard), dt => dt.selected).map(dt => dt.code);
      if (fP.target === 'currentSelection') {
        const selectedAnalysis = _.filter(_.toArray(fP.analysis.data), dt => dt.selected);
        const modif = Object.assign({}, ...selectedAnalysis.map(item => {
          return ({
            [item.id]: {
              ...item, financialPerspective: _.unionBy([...item.financialPerspective, ...fpApplied])
            }
          });
        }));
        ctx.patchState(
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data = {
              ...draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data, ...modif};
          }));
      } else {
        ctx.patchState(
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data = Object.assign({},
              ..._.toArray(draft.content[wsIdentifier].riskLink.financialPerspective.analysis.analysis.data).map(item => {
                    return ({
                      [item.id]: {...item,
                        financialPerspective: _.unionBy([...item.financialPerspective, ...fpApplied])
                      }
                    });
              }));
          })
        );
      }
    }
  }

  saveFinancialPerspective(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    ctx.patchState(
      produce(ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.results.data = Object.assign({},
          ..._.toArray(draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data).map(item => {
              return ({[item.id]: {...item, selected: false}});
          })
        );
      })
    );
  }

  removeFinancialPerspective(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const newData = Object.assign({}, ...[payload.item].map(item => {
      return ({
        [item.id]: {
          ...item,
          financialPerspective: _.filter(item.financialPerspective, dt => dt !== payload.fp)
        }
      });
    }));
    ctx.patchState(
      produce(ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data = {
          ...draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data, ...newData};
      })
      );
  }

  deleteFromBasket(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {id, scope} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    let newData = {};
    if (scope === 'summary') {
      const summary = _.filter(_.toArray(state.content[wsIdentifier].riskLink.summaries.data), dt => dt.dataSourceId != id);
      summary.forEach(dt => {
        newData = {...newData, [dt.dataSourceId]: {...dt}};
      });
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.summaries.data = newData;
          draft.content[wsIdentifier].riskLink.summaries.numberOfElement = summary.length;
        })
      );
    } else if (scope === 'results') {
      const results = _.filter(_.toArray(state.content[wsIdentifier].riskLink.results.data), dt => dt.analysisId != id);
      results.forEach(dt => {
        newData = {...newData, [dt.analysisId]: {...dt}};
      });
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.results.data = newData;
          draft.content[wsIdentifier].riskLink.results.numberOfElement = results.length;
        })
      );
    }
  }

  deleteEdmRdm(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const {id, target} = payload;
    if (target === 'RDM') {
      ctx.patchState(produce (ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.rdm = Object.assign({},
          ..._.filter(_.toArray(draft.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.rdm),
            dt => dt.id !== id).map(ws => {
              return ({[ws.id]: {...ws}});
            })
          );
      }));
    } else {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.edm = Object.assign({},
            ..._.filter(_.toArray(draft.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.edm),
              dt => dt.id !== id).map(ws => {
                return ({[ws.id]: {...ws}});
            }));
          })
        );
    }
  }

  loadRiskLinkAnalysisData(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    return forkJoin(
      payload.map(dt => this.riskApi.searchRiskLinkAnalysis(dt.id, dt.name))
    ).pipe(
      tap(e => console.log(e)),
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
        return of(ctx.patchState(
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.analysis = dataTable;
          })
        ));
      }),
      catchError(err => {
        // @TODO Handle error case
        console.error('Failed to search Analysis Count');
        return of();
      })
    );
  }

  loadRiskLinkPortfolioData(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
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
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.portfolios = dataTable;
          })));
      }),
      catchError(err => {
        // @TODO Handle error case
        console.error('Failed to search contracts Count');
        return of();
      })
    );
  }

  loadPortfolioForLinking(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {id, name} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
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
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.linking = {
              ...draft.content[wsIdentifier].riskLink.linking,
                  portfolio: dataTable
            };
          })
        ));
      }),
      catchError(err => {
        // @TODO Handle error case
        console.error('Failed to search Analysis Count');
        return of();
      })
    );
  }

  loadAnalysisForLinking(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
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
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.linking = {
              ...draft.content[wsIdentifier].riskLink.linking,
              analysis: dataTable
            };
          })
        ));
      }),
      catchError(err => {
        // @TODO Handle error case
        console.error('Failed to search contracts Count');
        return of();
      })
    );
  }

  toggleRiskLinkEDMAndRDMSelected(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    let selected;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const listEdmRdm = state.content[wsIdentifier].riskLink.listEdmRdm;
    const {id, type} = payload;
    const newDataSelectedEDM = Object.assign({}, ..._.toArray(listEdmRdm.selectedListEDMAndRDM.edm).map(item => ({
      [item.id]: {
        ...item, selected: false
      }
    })));
    const newDataSelectedRDM = Object.assign({}, ..._.toArray(listEdmRdm.selectedListEDMAndRDM.rdm).map(item => ({
      [item.id]: {
        ...item, selected: false
      }
    })));
    type === 'edm'
      ? selected = listEdmRdm.selectedListEDMAndRDM.edm[id].selected
      : selected = listEdmRdm.selectedListEDMAndRDM.rdm[id].selected;
    ctx.dispatch(new PatchRiskLinkDisplayAction({key: 'displayTable', value: !selected}));
    if (type === 'edm') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.listEdmRdm = {
            ...draft.content[wsIdentifier].riskLink.listEdmRdm,
                selectedListEDMAndRDM: {
                edm: {
                ...newDataSelectedEDM,
                    [id]: {...newDataSelectedEDM[id], selected: !selected}
                },
                rdm: {...newDataSelectedRDM}
              },
            };
          draft.content[wsIdentifier].riskLink.selectedEDMOrRDM = type;
        })
        );
    } else {
      ctx.patchState (
      produce(ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.listEdmRdm = {
          ...draft.content[wsIdentifier].riskLink.listEdmRdm,
            selectedListEDMAndRDM: {
            edm: {...newDataSelectedEDM},
            rdm: {
            ...newDataSelectedRDM,
                [id]: {...newDataSelectedRDM[id], selected: !selected}
            }
            },
          };
        draft.content[wsIdentifier].riskLink.selectedEDMOrRDM = type;
        }
      ));
    }
  }

  toggleAnalysisForLinking(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const linking = state.content[wsIdentifier].riskLink.linking;
    const {item: {id}} = payload;
    ctx.patchState( produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].riskLink.linking.rdm[id].selected = !linking.rdm[id].selected;
    })
    );
  }

  /** ACTION ADDED EDM AND RDM */
  selectRiskLinkEDMAndRDM(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const listDataToArray = _.toArray(state.content[wsIdentifier].riskLink.listEdmRdm.data);
    const listSelected = {edm: {}, rdm: {}};
    _.forEach(listDataToArray, dt => {
      if (dt.selected && dt.type === 'edm') {
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
    /*
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
            } else {*/
    ctx.patchState(produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.listEdmRdm = {
              ...draft.content[wsIdentifier].riskLink.listEdmRdm,
              selectedListEDMAndRDM: {
                edm: listSelected.edm,
                rdm: listSelected.rdm
            }
          };
          draft.content[wsIdentifier].riskLink.linking = {
              ...draft.content[wsIdentifier].riskLink.linking,
              edm: listSelected.edm,
              rdm: listSelected.rdm
          };
          draft.content[wsIdentifier].riskLink.financialPerspective = {
          ...draft.content[wsIdentifier].riskLink.financialPerspective,
              rdm: {data: listSelected.rdm, selected: null},
          };
        }
      )
    );
    ctx.dispatch(new fromWs.PatchRiskLinkDisplayAction({key: 'displayTable', value: false}));
    ctx.dispatch(new fromWs.LoadRiskLinkAnalysisDataAction(_.filter(listDataToArray, rt => rt.type === 'rdm' && rt.selected)));
    ctx.dispatch(new fromWs.LoadRiskLinkPortfolioDataAction(_.filter(listDataToArray, et => et.type === 'edm' && et.selected)));
    /*    }*/


  }

  /** SEARCH WITH KEYWORD OR PAGE OF EDM AND RDM */
  searchRiskLinkEDMAndRDM(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {keyword, size} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const array = state.content[wsIdentifier].riskLink.listEdmRdm.dataSelected;
    return this.riskApi.searchRiskLinkData(keyword, size).pipe(
      mergeMap(
        (ds: any) =>
          of(ctx.patchState(produce(
            ctx.getState(), draft => {
              draft.content[wsIdentifier].riskLink.listEdmRdm = {
                ...draft.content[wsIdentifier].riskLink.listEdmRdm,
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
                };
              }
          )))
      )
    );
  }

  /** LOAD DATA FOR FINANCIAL PERSPECTIVE */
  loadFinancialPerspective(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].riskLink.financialPerspective = {
        ...draft.content[wsIdentifier].riskLink.financialPerspective,
            standard: Object.assign({}, ...payload.map(item => ({[item.id]: {...item}}))),
            analysis: {
          ...draft.content[wsIdentifier].riskLink.results,
              data: Object.assign({}, ..._.toArray(draft.content[wsIdentifier].riskLink.results.data).map(item => ({
              [item.id]: {
                ...item,
                selected: false
              }
            })))
          }
        };
      })
    );
  }

  /** LOAD DATA WHEN OPEN RISK LINK PAGE */
  loadRiskLinkData(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    return this.riskApi.searchRiskLinkData().pipe(
      mergeMap(
        (ds: any) =>
          of(ctx.patchState(
            produce(
              ctx.getState(), draft => {
                draft.content[wsIdentifier].riskLink = {
                  ...draft.content[wsIdentifier].riskLink, listEdmRdm: {
                ...draft.content[wsIdentifier].riskLink.listEdmRdm,
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
                  }};
              }
            )
          ))
      )
    );
  }
}
