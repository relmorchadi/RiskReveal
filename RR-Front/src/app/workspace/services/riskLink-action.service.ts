import {StateContext} from '@ngxs/store';
import * as fromWs from '../store/actions';
import {
  LoadBasicAnalysisFacAction, LoadBasicAnalysisFacPerDivisionAction,
  LoadPortfolioFacAction, LoadPortfolioFacPerDivisionAction,
  PatchRiskLinkDisplayAction, UpdateAnalysisAndPortfolioData
} from '../store/actions';
import * as _ from 'lodash';
import {catchError, mergeMap, switchMap} from 'rxjs/operators';
import {of} from 'rxjs/internal/observable/of';
import {RiskApi} from './api/risk.api';
import {forkJoin} from 'rxjs';
import {Injectable} from '@angular/core';
import {WorkspaceModel} from '../model';
import produce from 'immer';

@Injectable({
  providedIn: 'root'
})
export class RiskLinkStateService {
  divisionTag = {
    'Division N°1': '_01',
    'Division N°2': '_02',
    'Division N°3': '_03'
  };

  constructor(private riskApi: RiskApi) {
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
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].riskLink.collapse = newValue;
    }));
  }

  patchDisplayState(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const newValue = _.merge({}, state.content[wsIdentifier].riskLink.display, {[payload.key]: payload.value});
    if (payload.key === 'displayTable' && !state.content[wsIdentifier].riskLink.display.displayTable) {
      ctx.dispatch([new LoadBasicAnalysisFacPerDivisionAction(), new LoadPortfolioFacPerDivisionAction()]);
    }
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
    if (payload.key === 'division') {
      const oldValue = state.content[wsIdentifier].riskLink.financialValidator.division.selected;
      const analysis = state.content[wsIdentifier].riskLink.analysisFac;
      if (analysis !== null) {
        ctx.dispatch([
          new LoadBasicAnalysisFacPerDivisionAction({key: oldValue}),
          new LoadPortfolioFacPerDivisionAction({key: oldValue})]);
      } else {
        ctx.dispatch([new LoadBasicAnalysisFacPerDivisionAction(), new LoadPortfolioFacPerDivisionAction()]);
      }
    }
    ctx.patchState(
      produce(ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.financialValidator = newValue;
      }));
    if (payload.key === 'division') {
      ctx.dispatch(new UpdateAnalysisAndPortfolioData(payload.value));
    }
  }

  patchAddToBasketState(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    let analysis = _.toArray(state.content[wsIdentifier].riskLink.analysis);
    let portfolio = _.toArray(state.content[wsIdentifier].riskLink.portfolios);
    analysis = analysis.map(dt => _.toArray(dt.data));
    portfolio = portfolio.map(dt => _.toArray(dt.data));
    const data = [...analysis, ...portfolio];
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
    } else if (target === 'regionPeril') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.results.data = Object.assign({},
            ..._.toArray(draft.content[wsIdentifier].riskLink.results.data).map(dt => {
              return ({[dt.id]: {...dt, regionPeril: dt.override}});
            }));
        })
      );
    }
  }

  patchLinkingMode(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    ctx.patchState(produce(
      ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.linking.autoMode = !draft.content[wsIdentifier].riskLink.linking.autoMode;
      }
    ));
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
            {
              [item]: {
                ...draft.content[wsIdentifier].riskLink.listEdmRdm.data[item],
                selected: !selected,
                source: !selected ? source : ''
              }
            });
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
    const {action, value, item, from, to, data} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const selectedPortfolio = _.filter(_.toArray(state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.edm),
      dt => dt.selected)[0];
    const portfolios = _.toArray(state.content[wsIdentifier].riskLink.portfolios[selectedPortfolio.id].data);
    const numSelectedItems = _.filter(portfolios, items => items.selected).length;
    let newData = {};
    if (action === 'selectOne') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.portfolios[selectedPortfolio.id].data[item.dataSourceId].selected = value;
        }));
    } else if (action === 'chunk' || action === 'unSelectChunk') {
      let selectedItems = {};
      data.forEach(items => {
        selectedItems = _.merge({}, selectedItems, {[items.dataSourceId]: {...items, selected: action === 'chunk'}});
      });
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.portfolios[selectedPortfolio.id].data =
            _.merge({}, draft.content[wsIdentifier].riskLink.portfolios[selectedPortfolio.id].data, selectedItems);
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
      });
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.portfolios[selectedPortfolio.id].data = newData;
        }));
    }
    ctx.dispatch(new fromWs.PatchAddToBasketStateAction());
  }

  toggleRiskLinkAnalysis(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {action, value, item, from, to, data} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const selectedAnalysis = _.filter(_.toArray(state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.rdm),
      dt => dt.selected)[0];
    const analysis = _.toArray(state.content[wsIdentifier].riskLink.analysis[selectedAnalysis.id].data);
    const numSelectedItems = _.filter(analysis, items => items.selected).length;
    let newData = {};
    if (action === 'selectOne') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.analysis[selectedAnalysis.id].data[item.analysisId].selected = value;
        }));
    } else if (action === 'chunk' || action === 'unSelectChunk') {
      let selectedItems = {};
      data.forEach(items => {
        selectedItems = _.merge({}, selectedItems, {[items.analysisId]: {...items, selected: action === 'chunk'}});
      });
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.analysis[selectedAnalysis.id].data =
            _.merge({}, draft.content[wsIdentifier].riskLink.analysis[selectedAnalysis.id].data, selectedItems);
        }));
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
      });
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.analysis[selectedAnalysis.id].data = newData;
        }));
    }
    ctx.dispatch(new fromWs.PatchAddToBasketStateAction());
  }

  toggleRiskLinkResult(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {action, value, item, from, to} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const result = _.toArray(state.content[wsIdentifier].riskLink.results.data);
    const numSelectedItems = _.filter(result, items => items.selected).length;
    if (action === 'selectOne') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.results.data[item.id].selected = value;
          draft.content[wsIdentifier].riskLink.results.indeterminate =
            !(numSelectedItems === 1 && value === false) && !(numSelectedItems === result.length - 1 && value === true);
          draft.content[wsIdentifier].riskLink.results.allChecked =
            (numSelectedItems === result.length - 1 && value === true);
        }));
    } else if (action === 'chunk') {
      let newData = {};
      result.forEach((st: any, index) => {
        let pt;
        if (index <= to && index >= from) {
          pt = {
            [st.id]: {
              ...st, selected: true
            }
          };
        } else {
          pt = {
            [st.id]: {
              ...st, selected: false
            }
          };
        }
        newData = _.merge(newData, pt);
      });
      const newArray = _.toArray(newData);
      const filtredData = _.filter(newArray, (pts: any) => pts.selected);
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.results.data = newData;
          draft.content[wsIdentifier].riskLink.results.indeterminate =
            filtredData.length !== 0 && newArray.length !== filtredData.length;
          draft.content[wsIdentifier].riskLink.results.allChecked =
            filtredData.length === 0 || newArray.length === filtredData.length;
        }));
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.results.data = Object.assign({},
            ...result.map(dt => ({[dt.id]: {...dt, selected: selected}})));
          draft.content[wsIdentifier].riskLink.results.indeterminate = false;
          draft.content[wsIdentifier].riskLink.results.allChecked = selected;
        }));
    }
  }

  toggleRiskLinkSummary(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {action, value, item, to, from} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const summaries = _.toArray(state.content[wsIdentifier].riskLink.summaries.data);
    const numSelectedItems = _.filter(summaries, items => items.selected).length;
    if (action === 'selectOne') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.summaries.data[item.id].selected = value;
          draft.content[wsIdentifier].riskLink.summaries.indeterminate =
            !(numSelectedItems === 1 && value === false) && !(numSelectedItems === summaries.length - 1 && value === true);
          draft.content[wsIdentifier].riskLink.summaries.allChecked =
            (numSelectedItems === summaries.length - 1 && value === true);
        }));
    } else if (action === 'chunk') {
      let newData = {};
      summaries.forEach((st: any, index) => {
        let pt;
        if (index <= to && index >= from) {
          pt = {
            [st.id]: {
              ...st, selected: true
            }
          };
        } else {
          pt = {
            [st.id]: {
              ...st, selected: false
            }
          };
        }
        newData = _.merge(newData, pt);
      });
      const newArray = _.toArray(newData);
      const filtredData = _.filter(newArray, (pts: any) => pts.selected);
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.summaries.data = newData;
          draft.content[wsIdentifier].riskLink.summaries.indeterminate =
            filtredData.length !== 0 && newArray.length !== filtredData.length;
          draft.content[wsIdentifier].riskLink.summaries.allChecked =
            filtredData.length === 0 || newArray.length === filtredData.length;
        }));
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.summaries.data = Object.assign({},
            ...summaries.map(dt => ({[dt.id]: {...dt, selected: selected}})));
          draft.content[wsIdentifier].riskLink.summaries.indeterminate = false;
          draft.content[wsIdentifier].riskLink.summaries.allChecked = selected;
        }));
    }
  }

  toggleRiskLinkFPStandard(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {action, value, item, from, to} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const fpStandard = _.toArray(state.content[wsIdentifier].riskLink.financialPerspective.standard.data);
    const numSelectedItems = _.filter(fpStandard, items => items.selected).length;
    if (action === 'selectOne') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.financialPerspective.standard.data[item.id].selected = value;
          draft.content[wsIdentifier].riskLink.financialPerspective.standard.indeterminate =
            !(numSelectedItems === 1 && value === false) && !(numSelectedItems === fpStandard.length - 1 && value === true);
          draft.content[wsIdentifier].riskLink.financialPerspective.standard.allChecked =
            (numSelectedItems === fpStandard.length - 1 && value === true);
        }));
    } else if (action === 'chunk') {
      let newData = {};
      fpStandard.forEach((st: any, index) => {
        let pt;
        if (index <= to && index >= from) {
          pt = {
            [st.id]: {
              ...st, selected: true
            }
          };
        } else {
          pt = {
            [st.id]: {
              ...st, selected: false
            }
          };
        }
        newData = _.merge(newData, pt);
      });
      const newArray = _.toArray(newData);
      const filtredData = _.filter(newArray, (pts: any) => pts.selected);
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.financialPerspective.standard.data = newData;
          draft.content[wsIdentifier].riskLink.financialPerspective.standard.indeterminate =
            filtredData.length !== 0 && newArray.length !== filtredData.length;
          draft.content[wsIdentifier].riskLink.financialPerspective.standard.allChecked =
            filtredData.length === 0 || newArray.length === filtredData.length;
        }));
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.financialPerspective.standard.data = Object.assign({},
            ...fpStandard.map(dt => ({[dt.id]: {...dt, selected: selected}})));
          draft.content[wsIdentifier].riskLink.financialPerspective.standard.indeterminate = false;
          draft.content[wsIdentifier].riskLink.financialPerspective.standard.allChecked = selected;
        })
      );
    }
  }

  toggleRiskLinkFPAnalysis(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {action, value, item, from, to} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const fpAnalysis = _.toArray(state.content[wsIdentifier].riskLink.financialPerspective.analysis.data);
    const numSelectedItems = _.filter(fpAnalysis, items => items.selected).length;
    if (action === 'selectOne') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data[item.id].selected = value;
          draft.content[wsIdentifier].riskLink.financialPerspective.analysis.indeterminate =
            !(numSelectedItems === 1 && value === false) && !(numSelectedItems === fpAnalysis.length - 1 && value === true);
          draft.content[wsIdentifier].riskLink.financialPerspective.analysis.allChecked =
            (numSelectedItems === fpAnalysis.length - 1 && value === true);
        }));
    } else if (action === 'chunk') {
      let newData = {};
      fpAnalysis.forEach((st: any, index) => {
        let pt;
        if (index <= to && index >= from) {
          pt = {
            [st.id]: {
              ...st, selected: true
            }
          };
        } else {
          pt = {
            [st.id]: {
              ...st, selected: false
            }
          };
        }
        newData = _.merge(newData, pt);
      });
      const newArray = _.toArray(newData);
      const filtredData = _.filter(newArray, (pts: any) => pts.selected);
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data = newData;
          draft.content[wsIdentifier].riskLink.financialPerspective.analysis.indeterminate =
            filtredData.length !== 0 && newArray.length !== filtredData.length;
          draft.content[wsIdentifier].riskLink.financialPerspective.analysis.allChecked =
            filtredData.length === 0 || newArray.length === filtredData.length;
        }));
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data = Object.assign({},
            ...fpAnalysis.map(dt => ({[dt.id]: {...dt, selected: selected}})));
          draft.content[wsIdentifier].riskLink.financialPerspective.analysis.indeterminate = false;
          draft.content[wsIdentifier].riskLink.financialPerspective.analysis.allChecked = selected;
        }));
    }
  }

  toggleAnalysisLinking(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {action, value, item, from, to} = payload;
    console.log(item);
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const linkAnalysis = _.toArray(state.content[wsIdentifier].riskLink.linking.analysis.data);
    const numSelectedItems = _.filter(linkAnalysis, data => data.selected).length;
    if (action === 'selectOne') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.linking.analysis.data[item.id].selected = value;
          draft.content[wsIdentifier].riskLink.linking.analysis.indeterminate =
            !(numSelectedItems === 1 && value === false) && !(numSelectedItems === linkAnalysis.length - 1 && value === true);
          draft.content[wsIdentifier].riskLink.linking.analysis.allChecked =
            (numSelectedItems === linkAnalysis.length - 1 && value === true);
        }));
    } else if (action === 'chunk') {
      let newData = {};
      linkAnalysis.forEach((st: any, index) => {
        let pt;
        if (index <= to && index >= from) {
          pt = {
            [st.id]: {
              ...st, selected: true
            }
          };
        } else {
          pt = {
            [st.id]: {
              ...st, selected: false
            }
          };
        }
        newData = _.merge(newData, pt);
      });
      const newArray = _.toArray(newData);
      const filtredData = _.filter(newArray, (pts: any) => pts.selected);
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.linking.analysis.data = newData;
          draft.content[wsIdentifier].riskLink.linking.analysis.indeterminate =
            filtredData.length !== 0 && newArray.length !== filtredData.length;
          draft.content[wsIdentifier].riskLink.linking.analysis.allChecked =
            filtredData.length === 0 || newArray.length === filtredData.length;
        }));
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.linking.analysis.data = Object.assign({},
            ...linkAnalysis.map(dt => ({[dt.id]: {...dt, selected: selected}})));
          draft.content[wsIdentifier].riskLink.linking.analysis.indeterminate = false;
          draft.content[wsIdentifier].riskLink.linking.analysis.allChecked = selected;
        }));
    }
  }

  togglePortfolioLinking(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {action, value, item, from, to} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const linkPortfolio = _.toArray(state.content[wsIdentifier].riskLink.linking.portfolio.data);
    const numSelectedItems = _.filter(linkPortfolio, data => data.selected).length;
    if (action === 'selectOne') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.linking.portfolio.data[item.id].selected = value;
          draft.content[wsIdentifier].riskLink.linking.portfolio.indeterminate =
            !(numSelectedItems === 1 && value === false) && !(numSelectedItems === linkPortfolio.length - 1 && value === true);
          draft.content[wsIdentifier].riskLink.linking.portfolio.allChecked =
            (numSelectedItems === linkPortfolio.length - 1 && value === true);
        }));
    } else if (action === 'chunk') {
      let newData = {};
      linkPortfolio.forEach((st: any, index) => {
        let pt;
        if (index <= to && index >= from) {
          pt = {
            [st.id]: {
              ...st, selected: true
            }
          };
        } else {
          pt = {
            [st.id]: {
              ...st, selected: false
            }
          };
        }
        newData = _.merge(newData, pt);
      });
      const newArray = _.toArray(newData);
      const filtredData = _.filter(newArray, (pts: any) => pts.selected);
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.linking.portfolio.data = newData;
          draft.content[wsIdentifier].riskLink.linking.portfolio.indeterminate =
            filtredData.length !== 0 && newArray.length !== filtredData.length;
          draft.content[wsIdentifier].riskLink.linking.portfolio.allChecked =
            filtredData.length === 0 || newArray.length === filtredData.length;
        }));
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.linking.portfolio.data = Object.assign({},
            ...linkPortfolio.map(dt => ({[dt.id]: {...dt, selected: selected}})));
          draft.content[wsIdentifier].riskLink.linking.portfolio.indeterminate = false;
          draft.content[wsIdentifier].riskLink.linking.portfolio.allChecked = selected;
        }));
    }
  }

  addToBasket(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const analysis = _.toArray(state.content[wsIdentifier].riskLink.analysis).map(dt => _.toArray(dt.data));
    const portfolio = _.toArray(state.content[wsIdentifier].riskLink.portfolios).map(dt => _.toArray(dt.data));
    const selectAnalysis = analysis.map(dt => _.filter(dt, ws => ws.selected));
    const selectPortfolio = portfolio.map(dt => _.filter(dt, ws => ws.selected));
    let dataAnalysis = [];
    let dataPortfolio = [];
    selectAnalysis.forEach(dt => dataAnalysis = [...dataAnalysis, ...dt]);
    selectPortfolio.forEach(dt => dataPortfolio = [...dataPortfolio, ...dt]);
    let results = {data: {}};
    let summary = {data: {}};
    dataAnalysis.forEach(dt => {
      results = {
        ...results, data: {
          ...results.data,
          [dt.analysisId + '-' + dt.analysisName]: {
            ...dt,
            id: dt.analysisId + '-' + dt.analysisName,
            scanned: true,
            status: 100,
            unitMultiplier: 1,
            proportion: 100,
            targetCurrency: 'USD',
            financialPerspective: ['RL'],
            occurrenceBasis: 'PerEvent',
            regionPeril: 'EUET',
            division: _.uniq([..._.get(state.content[wsIdentifier].riskLink.results,
              `data.${dt.analysisId}-${dt.analysisName}.division`, []),
              state.content[wsIdentifier].riskLink.financialValidator.division.selected]),
            ty: true,
            peqt: [{title: 'RL_EUWS_Mv11.2_S-1003-LTR-Scor27c72u', selected: true},
              {title: 'RL_EUWS_Mv11.2_S-65-LTR', selected: false},
              {title: 'RL_EUWS_Mv11.2_S-66-LTR-Clue', selected: false}],
            override: 'EUET',
            reason: '',
            selected: false
          },
        }
      };
    });

    dataPortfolio.forEach(dt => {
      summary = {
        ...summary, data: {
          ...summary.data,
          [dt.dataSourceId + '-' + dt.dataSourceName]: {
            ...dt,
            id: dt.dataSourceId + '-' + dt.dataSourceName,
            selected: false,
            scanned: true,
            division: _.uniq([..._.get(state.content[wsIdentifier].riskLink.summaries,
              `data.${dt.dataSourceId}-${dt.dataSourceName}.division`, []),
              state.content[wsIdentifier].riskLink.financialValidator.division.selected]),
            status: 100,
            unitMultiplier: 1,
            proportion: 100,
            targetCurrency: 'USD',
            sourceCurrency: 'USD',
            exposedLocation: true
          },
        }
      };
    });
    const summaryInfo = _.merge({}, summary.data, _.get(state.content[wsIdentifier].riskLink.summaries, 'data', null));
    const resultsInfo = _.merge({}, results.data, _.get(state.content[wsIdentifier].riskLink.results, 'data', null));
    ctx.patchState(
      produce(ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.summaries = {
          data: summaryInfo,
          numberOfElement: _.toArray(summaryInfo).length,
          allChecked: false,
          indeterminate: _.get(draft.content[wsIdentifier].riskLink.summaries, 'indeterminate', false),
        };
        draft.content[wsIdentifier].riskLink.results = {
          data: resultsInfo,
          numberOfElement: _.toArray(resultsInfo).length,
          allChecked: false,
          indeterminate: _.get(draft.content[wsIdentifier].riskLink.results, 'indeterminate', false),
          isValid: this._isValidImport(ctx, _.toArray(resultsInfo)),
        };
        draft.content[wsIdentifier].riskLink.analysis = _.forEach(draft.content[wsIdentifier].riskLink.analysis,
            item => {_.forEach(item.data, dt => {if (dt.selected) {dt.imported = true; } });
        });
        draft.content[wsIdentifier].riskLink.portfolios = _.forEach(draft.content[wsIdentifier].riskLink.portfolios,
          item => {_.forEach(item.data, dt => {if (dt.selected) {dt.imported = true; } });
        });
      })
    );

    ctx.dispatch(new fromWs.LoadDetailAnalysisFacAction(_.filter(dataAnalysis, (item: any) => item.typeWs === 'fac')));
    ctx.dispatch(new fromWs.LoadLinkingDataAction({analysis: _.toArray(resultsInfo), portfolios: _.toArray(summaryInfo)}));
/*    return forkJoin(
      dataAnalysis.map((item: any) => this.riskApi.searchDetailAnalysis(item.analysisId, item.analysisName))
    ).pipe(
      switchMap(out => {
        out.forEach((dt: any) => {
            results = {
              ...results, data: {
                ...results.data,
                [dt.analysisId]: {
                  ...results.data[dt.analysisId],
                  ...dt,
                }
              },
            };
          }
        );
        return of(
          ctx.patchState(
            produce(ctx.getState(), draft => {
              draft.content[wsIdentifier].riskLink.summaries = summary;
              draft.content[wsIdentifier].riskLink.results = results;
            })
          )
        );
      }),
      catchError( err => {
          console.log(err);
          return of(ctx.patchState(
            produce(ctx.getState(), draft => {
              draft.content[wsIdentifier].riskLink.summaries = summary;
              draft.content[wsIdentifier].riskLink.results = results;
            })
          ));
        }
      )
    );*/
  }

  addToBasketDefault(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const results = {};
    const summaries = {};
    const dataAnalysis = state.content[wsIdentifier].riskLink.analysisFac;
    const dataPortfolio = state.content[wsIdentifier].riskLink.portfolioFac;
    _.forEach(dataAnalysis, (division, key) => {
      _.forEach(division , item => {
        _.forEach(item.data, analysisItem => {
          if (analysisItem.selected) {
            results[analysisItem.analysisId + '-' + analysisItem.analysisName] = {
              ...analysisItem,
              id: analysisItem.analysisId + '-' + analysisItem.analysisName,
              scanned: true,
              status: 100,
              unitMultiplier: 1,
              proportion: 100,
              targetCurrency: 'USD',
              financialPerspective: ['RL'],
              occurrenceBasis: 'PerEvent',
              regionPeril: 'EUET',
              division: [key],
              ty: true,
              peqt: [{title: 'RL_EUWS_Mv11.2_S-1003-LTR-Scor27c72u', selected: true},
                {title: 'RL_EUWS_Mv11.2_S-65-LTR', selected: false},
                {title: 'RL_EUWS_Mv11.2_S-66-LTR-Clue', selected: false}],
              override: 'EUET',
              reason: '',
              selected: false
            };
          }
        });
      });
    });

    _.forEach(dataPortfolio, (division, key) => {
      _.forEach(division, item => {
        _.forEach(item.data, portfolioItem => {
          if (portfolioItem.selected) {
            summaries[portfolioItem.dataSourceId + '-' + portfolioItem.dataSourceName] = {
              ...portfolioItem,
              id: portfolioItem.dataSourceId + '-' + portfolioItem.dataSourceName,
              selected: false,
              scanned: true,
              division: [key],
              status: 100,
              unitMultiplier: 1,
              proportion: 100,
              targetCurrency: 'USD',
              sourceCurrency: 'USD',
              exposedLocation: true
            };
          }
        });
      });
    });

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].riskLink.results = {data: results};
      draft.content[wsIdentifier].riskLink.summaries = {data: summaries};
    }));

    ctx.dispatch(new fromWs.LoadDetailAnalysisFacAction(_.filter(_.toArray(results), (item: any) => item.typeWs === 'fac')));
    ctx.dispatch(new fromWs.LoadLinkingDataAction({analysis: _.toArray(results), portfolios: _.toArray(summaries)}));
  }

  importRiskLinkImport(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const selectedProject: any = _.filter(state.content[wsIdentifier].projects, item => item.selected)[0];
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].riskLink.importPLTs[selectedProject.projectId] = true;
    }));
  }

  applyFinancialPerspective(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const fP = state.content[wsIdentifier].riskLink.financialPerspective;
    if (payload === 'replace') {
      const fpApplied = _.filter(_.toArray(fP.standard.data), dt => dt.selected).map(dt => dt.code);
      if (fP.target === 'currentSelection') {
        const selectedAnalysis = _.filter(_.toArray(fP.analysis.data), dt => dt.selected);
        const modif = Object.assign({}, ...selectedAnalysis.map(item => {
          return ({[item.id]: {...item, financialPerspective: fpApplied}});
        }));
        ctx.patchState(
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data = {
              ...draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data, ...modif
            };
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
      const fpApplied: any = _.filter(_.toArray(fP.standard.data), dt => dt.selected).map(dt => dt.code);
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
              ...draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data, ...modif
            };
          }));
      } else {
        ctx.patchState(
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data = Object.assign({},
              ..._.toArray(draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data).map(item => {
                return ({
                  [item.id]: {
                    ...item,
                    financialPerspective: _.unionBy([...item.financialPerspective, ...fpApplied])
                  }
                });
              }));
          })
        );
      }
    }
  }

  applyRegionPeril(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const {regionPeril, override, id} = payload.row;
    const {scope, value} = payload;
    console.log(scope, value, id, override);
    if (scope === 'all') {
      ctx.patchState(produce(ctx.getState(), draft => {
        const matchingPeril =
          _.filter(_.toArray(draft.content[wsIdentifier].riskLink.results.data), item => item.regionPeril === regionPeril);
        draft.content[wsIdentifier].riskLink.results.data = _.merge(draft.content[wsIdentifier].riskLink.results.data,
          ...matchingPeril.map(item => ({[item.id]: {...item, override: override}})));
      }));
    } else if (scope === 'single') {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.results.data[id].override = value;
      }));
    }
  }

  saveFinancialPerspective(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    ctx.patchState(
      produce(ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.results.data = Object.assign({},
          ..._.toArray(draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data).map(item => {
            return ({[item.analysisId + '-' + item.analysisName]: {...item, selected: false}});
          })
        );
      })
    );
  }

  saveEditAnalysis(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const {occurrenceBasis, targetCurrency, unitMultiplier, proportion, scope} = payload;
    if (scope === 'currentSelection') {
      ctx.patchState(produce(ctx.getState(), draft => {
        const selectedData = _.filter(_.toArray(draft.content[wsIdentifier].riskLink.results.data),
          analysis => analysis.selected);
        const newData = Object.assign({}, ...selectedData.map(item => {
          return ({
            [item.analysisId + '-' + item.analysisName]: {
              ...item,
              occurrenceBasis: occurrenceBasis ? occurrenceBasis : item.occurrenceBasis,
              targetCurrency: targetCurrency ? targetCurrency : item.targetCurrency,
              unitMultiplier: unitMultiplier ? unitMultiplier : item.unitMultiplier,
              proportion: proportion ? proportion : item.proportion,
            }
          });
        }));
        draft.content[wsIdentifier].riskLink.results.data =
          _.merge({}, draft.content[wsIdentifier].riskLink.results.data, newData);
      }));
    } else {
      ctx.patchState(produce(ctx.getState(), draft => {
        const selectedData = _.toArray(draft.content[wsIdentifier].riskLink.results.data);
        const newData = Object.assign({}, ...selectedData.map(item => {
          return ({
            [item.analysisId + '-' + item.analysisName]: {
              ...item,
              occurrenceBasis: occurrenceBasis ? occurrenceBasis : item.occurrenceBasis,
              targetCurrency: targetCurrency ? targetCurrency : item.targetCurrency,
              unitMultiplier: unitMultiplier ? unitMultiplier : item.unitMultiplier,
              proportion: proportion ? proportion : item.proportion,
            }
          });
        }));
        draft.content[wsIdentifier].riskLink.results.data =
          _.merge({}, draft.content[wsIdentifier].riskLink.results.data, newData);
      }));
    }
  }

  saveEditPeqt(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const {data} = payload;
    let list = [];
    data.map(item => list = [...list, ...item.children]);
    let results = _.toArray(state.content[wsIdentifier].riskLink.results.data);
    results = results.map(item => {
      return {...item, peqt: _.filter(list,
            dt => dt.analysisId === item.analysisId && dt.analysisName === item.analysisName)[0].selectedItems};
    });
    console.log(list, results);
    ctx.patchState(produce(
      ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.results.data = Object.assign({}, ...results.map( analysis => ({
          [analysis.analysisId + '-' + analysis.analysisName]: {
            ...analysis
          }})
        ));
      }
    ));
  }

  saveEDMAndRDMSelection(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const edm = _.toArray(state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.edm);
    const rdm = _.toArray(state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.rdm);
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.savedData.riskLink = {
        ...draft.savedData.riskLink,
        edmrdmSelection: _.merge({}, draft.savedData.riskLink.edmrdmSelection,
          {[wsIdentifier]: {
            edm: Object.assign({}, ...edm.map(item => ({[item.id]: {...item, selected: false}}))),
            rdm: Object.assign({}, ...rdm.map(item => ({[item.id]: {...item, selected: false}})))
          }})
      };
    }));
  }

  synchronizeEDMAndRDMSelection(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    ctx.patchState(produce(ctx.getState(), draft => {
      const edm = _.toArray(_.get(draft.savedData.riskLink.edmrdmSelection, `${wsIdentifier}.edm`, {}));
      const rdm = _.toArray(_.get(draft.savedData.riskLink.edmrdmSelection, `${wsIdentifier}.rdm`, {}));
      draft.content[wsIdentifier].riskLink.display.displayListRDMEDM = [...edm, ...rdm].length > 0;
      draft.content[wsIdentifier].riskLink.display.displayTable = false;
      draft.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM =
        _.get(draft.savedData.riskLink.edmrdmSelection, `${wsIdentifier}`, {edm: {}, rdm: {}});
    }));
    const mergedEDM = _.get(state.savedData.riskLink.edmrdmSelection, `${wsIdentifier}.edm`, null);
    const mergedRDM = _.get(state.savedData.riskLink.edmrdmSelection, `${wsIdentifier}.rdm`, null);
    const filteredFacEDMRDM = _.filter([..._.toArray(mergedEDM), ..._.toArray(mergedRDM)], item => item.typeWs === 'fac');
    ctx.dispatch(new fromWs.LoadRiskLinkAnalysisDataAction(_.filter(_.toArray(mergedRDM), item => item.typeWs === 'treaty')));
    ctx.dispatch(new fromWs.LoadRiskLinkPortfolioDataAction(_.filter(_.toArray(mergedEDM), item => item.typeWs === 'treaty')));
    ctx.dispatch(new fromWs.SelectFacRiskLinkEDMAndRDMAction(filteredFacEDMRDM));
  }

  createLinking(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const portfolio = _.toArray(state.content[wsIdentifier].riskLink.linking.portfolio.data);
    const analysis = _.toArray(state.content[wsIdentifier].riskLink.linking.analysis.data);
    const selectedPortfolios = _.filter(portfolio, item => item.selected);
    const selectedAnalysis = _.filter(analysis, item => item.selected);
    ctx.patchState(produce(
      ctx.getState(), draft => {
        if (selectedPortfolios.length > 0 && selectedPortfolios.length > 0) {
          draft.content[wsIdentifier].riskLink.linking.linked = [...draft.content[wsIdentifier].riskLink.linking.linked,
            {analysis: [...selectedAnalysis], portfolio: [...selectedPortfolios], selected: false, update: false}];
        }
      }
    ));
    ctx.dispatch(new fromWs.ToggleAnalysisLinkingAction({action: 'unselectAll'}));
    ctx.dispatch(new fromWs.TogglePortfolioLinkingAction({action: 'unselectAll'}));
  }

  updateStatusLink(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const {link, select, updated} = payload;
    ctx.patchState(produce(
      ctx.getState(), draft => {
        const index = _.findIndex(draft.content[wsIdentifier].riskLink.linking.linked, link);
        draft.content[wsIdentifier].riskLink.linking.linked[index] = {
          ...draft.content[wsIdentifier].riskLink.linking.linked[index],
          selected: select, update: updated
        };
      }
    ));
  }

  updateAnalysisAndPortfolioData(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].riskLink.analysis =
        _.get(draft.content[wsIdentifier].riskLink.analysisFac, `${payload}`, draft.content[wsIdentifier].riskLink.analysis);
      draft.content[wsIdentifier].riskLink.portfolios =
        _.get(draft.content[wsIdentifier].riskLink.portfolioFac, `${payload}`, draft.content[wsIdentifier].riskLink.portfolios);
    }));
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
          ...draft.content[wsIdentifier].riskLink.financialPerspective.analysis.data, ...newData
        };
      })
    );
  }

  removeEDMAndRDMSelection(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.savedData.riskLink.edmrdmSelection = _.omit(draft.savedData.riskLink.edmrdmSelection, wsIdentifier);
      draft.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM = {
        edm: {}, rdm: {}
      };
      draft.content[wsIdentifier].riskLink.display = {
        displayTable: false,
        displayImport: false,
        displayListRDMEDM: false
      };
    }));
  }

  deleteFromBasket(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {id, scope} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    let newData = {};
    if (scope === 'summary') {
      const summary = _.filter(_.toArray(state.content[wsIdentifier].riskLink.summaries.data), dt => dt.id != id);
      summary.forEach(dt => {
        newData = {...newData, [dt.id]: {...dt}};
      });
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.summaries.data = newData;
          draft.content[wsIdentifier].riskLink.summaries.numberOfElement = summary.length;
        })
      );
    } else if (scope === 'results') {
      const results = _.filter(_.toArray(state.content[wsIdentifier].riskLink.results.data), dt => dt.id != id);
      results.forEach(dt => {
        newData = {...newData, [dt.id]: {...dt}};
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
      ctx.patchState(produce(ctx.getState(), draft => {
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

  deleteLink(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].riskLink.linking.linked.splice(
        _.findIndex(draft.content[wsIdentifier].riskLink.linking.linked, payload), 1);
    }));
  }

  deleteInnerLink(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const {item, link, target} = payload;
    if (target === 'portfolio') {
      ctx.patchState(produce(ctx.getState(), draft => {
        const index = _.findIndex(draft.content[wsIdentifier].riskLink.linking.linked, link);
        if (draft.content[wsIdentifier].riskLink.linking.linked[index].portfolio.length === 1) {
          draft.content[wsIdentifier].riskLink.linking.linked.splice(
            _.findIndex(draft.content[wsIdentifier].riskLink.linking.linked, link), 1);
        } else {
          draft.content[wsIdentifier].riskLink.linking.linked[index].portfolio.splice(
            _.findIndex(draft.content[wsIdentifier].riskLink.linking.linked[index].portfolio, item), 1);
        }
      }));
    } else if (target === 'analysis') {
      ctx.patchState(produce(ctx.getState(), draft => {
        const index = _.findIndex(draft.content[wsIdentifier].riskLink.linking.linked, link);
        if (draft.content[wsIdentifier].riskLink.linking.linked[index].analysis.length === 1) {
          draft.content[wsIdentifier].riskLink.linking.linked.splice(
            _.findIndex(draft.content[wsIdentifier].riskLink.linking.linked, link), 1);
        } else {
          draft.content[wsIdentifier].riskLink.linking.linked[index].analysis.splice(
            _.findIndex(draft.content[wsIdentifier].riskLink.linking.linked[index].analysis, item), 1);
        }
      }));
    }
  }

  loadLinkingDataAction(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const {analysis, portfolios} = payload;
    const rdm = _.uniqBy(analysis.map(item => ({name: item.rdmName, id: item.rdmId, selected: false, type: 'rdm'})),
      (dt: any) => dt.id);
    const edm =  _.uniqBy(portfolios.map(item => ({name: item.edmName, id: item.edmId, selected: false, type: 'edm'})),
      (dt: any) => dt.id);
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].riskLink.linking = {
        ...draft.content[wsIdentifier].riskLink.linking,
        rdm: {data: Object.assign({}, ...rdm.map(item => ({[item.id]: {...item}}))),
          selected: null
        },
        edm: Object.assign({}, ...edm.map(item => ({[item.id]: {...item}}))),
        analysis: {
          data: Object.assign({}, ...analysis.map(dt => ({[dt.id]: {...dt, selected: false}}))),
          allChecked: false, indeterminate: false, numberOfElement: analysis.length
        },
        portfolio: {
          data: Object.assign({}, ...portfolios.map(dt => ({[dt.id]: {...dt, selected: false}}))),
          allChecked: false, indeterminate: false, numberOfElement: portfolios.length
        }
      };
    }));
  }

  loadFacData(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    return this.riskApi.searchFacData().pipe(
      mergeMap(
        (dt: any) => {
          const dataModel = dt.map(item => { return {
            id: item.databaseId,
            name: item.name,
            createDt: '19/01/2018 19:01:50',
            type: this._getTypeData(item.name) ? 'rdm' : 'edm',
            versionNum: 17,
            selected: item.name === 'SBS_RR_2019_08_MELANIE_E' || item.name === 'SBS_RR_2019_08_MELANIE_R',
            typeWs: 'fac',
            source: ''};
          });
          let selectedData = {
            rdm: Object.assign({},
              ..._.filter(dataModel, item => item.type === 'rdm' && item.selected).map((ds: any) =>
                ({[ds.id]: {...ds, selected: false, scanned: true}}))),
            edm: Object.assign({},
              ..._.filter(dataModel, item => item.type === 'edm' && item.selected).map((ds: any) =>
                ({[ds.id]: {...ds, selected: false, scanned: true}})))
          };
          if (!state.content[wsIdentifier].riskLink.synchronize) {
            selectedData = _.get(state.savedData.riskLink.edmrdmSelection, `${wsIdentifier}`, selectedData);
          }
          ctx.dispatch([new LoadBasicAnalysisFacAction(_.toArray(selectedData.rdm)),
            new LoadPortfolioFacAction(_.toArray(selectedData.edm)),
            new PatchRiskLinkDisplayAction({key: 'displayListRDMEDM', value: true})
            ]
          );
          return of(ctx.patchState(produce(
            ctx.getState(), draft => {
              draft.content[wsIdentifier].riskLink.listEdmRdm = {
                ...draft.content[wsIdentifier].riskLink.listEdmRdm,
                data: _.merge({},
                  draft.content[wsIdentifier].riskLink.listEdmRdm.data,
                  Object.assign({}, ...dataModel.map(ds => ({[ds.id]: {...ds}})))
                ),
                selectedListEDMAndRDM: selectedData,
                totalNumberElement: draft.content[wsIdentifier].riskLink.listEdmRdm.totalNumberElement + dataModel.length,
                numberOfElement: draft.content[wsIdentifier].riskLink.listEdmRdm.numberOfElement + dataModel.length
              };
              draft.content[wsIdentifier].riskLink.synchronize = false;
            }))
          );
        }
      )
    );
  }

  loadBasicAnalysisFac(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const facId = state.content[wsIdentifier].wsId;
    return forkJoin(
      payload.map(dt => this.riskApi.searchFacAnalysisBasic(dt.id, dt.name, facId))
    ).pipe(
      switchMap(out => {
        let dataTable = {};
        out.forEach((dt: any, i) => {
            dataTable = {
              ...dataTable,
              [payload[i].id]: {
                data: Object.assign({},
                  ...dt.map(analysis => ({
                      [analysis.analysisId]: {
                        description: analysis.analysisDescription,
                        groupType: analysis.grouptypeName,
                        typeWs: 'fac',
                        ...analysis,
                        selected: this._getSelectionValue(ctx, analysis.analysisName)
                      }
                    }
                  ))),
                allChecked: false,
                indeterminate: false,
                totalNumberElement: dt.length,
                numberOfElement: dt.length,
                filter: {}
              }
            };
          }
        );
        return of(ctx.patchState(
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.analysis = _.merge({}, dataTable, draft.content[wsIdentifier].riskLink.analysis);
          })));
      }),
      catchError(err => {
        // @TODO Handle error case
        console.error(err);
        return of();
      })
    );
  }

  loadBasicAnalysisFacPerDivision(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const key = _.get(payload, 'key', null);
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const divisions = state.content[wsIdentifier].riskLink.financialValidator.division.data;
    const analysis = state.content[wsIdentifier].riskLink.analysis;
    const newDivisions = {};
    _.forEach(divisions, div => {
      newDivisions[div] = {};
      _.forEach(analysis, (item, analysisKey) => {
        newDivisions[div][analysisKey] = {...analysis[analysisKey], data: {}};
        _.forEach(item.data, analysisItem => {
          newDivisions[div][analysisKey].data[analysisItem.analysisId] = {
            ...analysisItem,
            selected: analysisItem.analysisName === state.content[wsIdentifier].wsId + this.divisionTag[div]
          };
        });
      });
    });
    console.log(analysis, this.divisionTag);
    if (key !== null) {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.analysisFac[key] = analysis;
        }));
    } else {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.analysisFac = _.merge({}, newDivisions, draft.content[wsIdentifier].riskLink.analysisFac);
        }));
    }
  }

  loadDetailAnalysisFac(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    return forkJoin(
      payload.map(dt => this.riskApi.searchFacAnalysisDetail(dt.analysisId, dt.analysisName))
    ).pipe(
      switchMap(out => {
        let dataTable = {};
        out.forEach((dt: any) => {
          const item = dt[0];
          dataTable = {
            ...dataTable,
            [item.analysisId + '-' + item.analysisName]: {
              ...state.content[wsIdentifier].riskLink.results.data[item.analysisId + '-' + item.analysisName],
              ...item,
              id: item.analysisId + '-' + item.analysisName,
              regionPeril: item.regionPerilCode,
              financialPerspective: ['GR'],
              occurrenceBasis: 'PerEvent',
              override: item.regionPerilCode,
              reason: '',
              selected: false
            }
          };
        });
        return of(ctx.patchState(produce(ctx.getState(), draft => {
          const newData =  _.merge({}, draft.content[wsIdentifier].riskLink.results.data, dataTable);
          draft.content[wsIdentifier].riskLink.results.data = newData;
          draft.content[wsIdentifier].riskLink.results.isValid = this._isValidImport(ctx, _.toArray(newData));
          })));
      }),
      catchError(err => {
        // @TODO Handle error case
        console.error(err);
        return of();
      })
    );
  }

  loadBasicPortfolioFac(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const facId = state.content[wsIdentifier].wsId;
    return forkJoin(
      payload.map(dt => this.riskApi.searchFacPortfolio(dt.id, dt.name, facId))
    ).pipe(
      switchMap(out => {
        let dataTable = {};
        out.forEach((dt: any, i) => {
            dataTable = {
              ...dataTable,
              [payload[i].id]: {
                data: Object.assign({},
                  ..._.uniqBy(dt, (item: any) => item.portName).map((portfolio: any) => ({
                      [portfolio.id]: {
                        id: portfolio.id,
                        dataSourceId: portfolio.id,
                        dataSourceName: portfolio.portName,
                        creationDate: portfolio.portCreatedDt,
                        descriptionType: portfolio.portDescr,
                        edmId: portfolio.edmId,
                        edmName: portfolio.edmName,
                        agCedent: portfolio.agCedant,
                        agCurrency: portfolio.agCcy,
                        agSource: portfolio.agSource,
                        number: portfolio.portNum,
                        peril: portfolio.peril,
                        portfolioId: portfolio.id,
                        type: portfolio.portType,
                        selected: this._getSelectionValue(ctx, portfolio.portName)
                      }}
                  ))
                ),
                allChecked: false,
                indeterminate: false,
                totalNumberElement: _.uniqBy(dt, (item: any) => item.portName).length,
                numberOfElement: _.uniqBy(dt, (item: any) => item.portName).length,
                filter: {}
              }
            };
          }
        );
        return of(ctx.patchState(
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.portfolios = _.merge({}, dataTable, draft.content[wsIdentifier].riskLink.portfolios);
          })));
      }),
      catchError(err => {
        // @TODO Handle error case
        console.error(err);
        return of();
      })
    );
  }

  loadBasicPortfolioFacPerDivision(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const key = _.get(payload, 'key', null);
    const divisions = state.content[wsIdentifier].riskLink.financialValidator.division.data;
    const portfolio = state.content[wsIdentifier].riskLink.portfolios;
    const newDivisions = {};
    _.forEach(divisions, div => {
      newDivisions[div] = {};
      _.forEach(portfolio, (item, portKey) => {
        newDivisions[div][portKey] = {...portfolio[portKey], data: {}};
        _.forEach(item.data, portfolioItem => {
          newDivisions[div][portKey].data[portfolioItem.id] = {
            ...portfolioItem,
            selected: portfolioItem.dataSourceName === state.content[wsIdentifier].wsId + this.divisionTag[div]
          };
        });
      });
    });
    if (key !== null) {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.portfolioFac[key] = portfolio;
        }));
    } else {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.portfolioFac = _.merge({}, newDivisions, draft.content[wsIdentifier].riskLink.portfolioFac);
        }));
    }
  }

  loadRiskLinkAnalysisData(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
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
                        typeWs: 'treaty',
                        imported: false,
                        selected: false
                      }
                    }
                  ))),
                allChecked: false,
                indeterminate: false,
                totalNumberElement: dt.totalElements,
                numberOfElement: dt.size,
                filter: {}
              }
            };
          }
        );
        return of(ctx.patchState(
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.analysis = _.merge({}, dataTable, draft.content[wsIdentifier].riskLink.analysis);
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
                        imported: false,
                        selected: false
                      }
                    }
                  ))),
                allChecked: false,
                indeterminate: false,
                totalNumberElement: dt.totalElements,
                numberOfElement: dt.size,
                filter: {}
              }
            };
          }
        );
        return of(ctx.patchState(
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.portfolios = _.merge({}, dataTable, draft.content[wsIdentifier].riskLink.portfolios);
          })));
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
      ctx.patchState(
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
    const selected = linking.rdm.data[id].selected;
    const currentSelection = linking.rdm.selected;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].riskLink.linking.rdm.data[id].selected = !selected;
      const selectedRdm = _.filter(_.toArray(draft.content[wsIdentifier].riskLink.linking.rdm.data),
        dt => dt.selected);
      if (selectedRdm.length === 0) {
        draft.content[wsIdentifier].riskLink.linking.rdm.selected = null;
        // draft.content[wsIdentifier].riskLink.linking.analysis = null;
      }
    }));
/*    const selectedRdms = _.filter(_.toArray(state.content[wsIdentifier].riskLink.linking.rdm.data),
      dt => dt.selected);
    if (selectedRdms.length === 0 && selected === false) {
      ctx.dispatch(new fromWs.LoadAnalysisForLinkingAction(payload.item));
    }
    if (_.get(currentSelection, 'id', null) === id && selected) {
      ctx.dispatch(new fromWs.LoadAnalysisForLinkingAction(selectedRdms[0]));
    }*/
  }

  selectMatchingFacEDMAndRDM(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const listDataToArray = _.toArray(state.content[wsIdentifier].riskLink.listEdmRdm.data);
    const FacEDMAndRDM = _.filter(listDataToArray, item => item.typeWs === 'fac');
    let dataTable = {};
    payload.forEach(item => {
      dataTable = _.merge(dataTable, {[item.id]: {...item}});
      const trim = item.name.substr(0, item.name.lastIndexOf('_'));
      const valide = _.filter(FacEDMAndRDM,
          dt => dt.name.substr(0, dt.name.lastIndexOf('_')) === trim && dt.type !== item.type);
      if (valide.length > 0) {
        dataTable = _.merge(dataTable, {[valide[0].id]: {...valide[0]}});
      }
    });
    const listSelected = {edm: state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.edm,
      rdm: state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.rdm};
    _.forEach(_.toArray(dataTable), (dt: any) => {
      if (dt.type === 'edm') {
        listSelected.edm = _.merge(listSelected.edm, {
          [dt.id]: {
            ...dt,
            scanned: true,
            selected: false,
          }
        });
      } else if (dt.type === 'rdm') {
        listSelected.rdm = _.merge(listSelected.rdm, {
          [dt.id]: {
            ...dt,
            scanned: true,
            selected: false,
          }
        });
      }
    });
    const mergedEDM = _.merge({}, listSelected.edm,
      state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.edm);
    const mergedRDM = _.merge({}, listSelected.rdm,
      state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.rdm);
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].riskLink.listEdmRdm = {
        ...draft.content[wsIdentifier].riskLink.listEdmRdm,
        selectedListEDMAndRDM: {
          edm: mergedEDM,
          rdm: mergedRDM
        },
        data: this._update(draft.content[wsIdentifier].riskLink.listEdmRdm.data, listSelected)
      };
      draft.content[wsIdentifier].riskLink.linking = {
        ...draft.content[wsIdentifier].riskLink.linking,
        edm: mergedEDM,
        rdm: {data: mergedRDM, selected: null}
      };
      draft.content[wsIdentifier].riskLink.financialPerspective = {
        ...draft.content[wsIdentifier].riskLink.financialPerspective,
        rdm: {data: mergedRDM, selected: null},
      };
    }));
    ctx.dispatch(new fromWs.LoadBasicAnalysisFacAction(_.toArray(mergedRDM)));
    ctx.dispatch(new fromWs.LoadPortfolioFacAction(_.toArray(mergedEDM)));
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
                            typeWs: 'treaty',
                            scanned: true,
                            selected: false
                          }
                        });
                      } else {
                        listSelected.rdm = _.merge({}, listSelected.rdm, {
                          [ws.id]: {
                            ...ws,
                            typeWs: 'treaty',
                            scanned: true,
                            selected: false
                          }
                        });
                      }
                    }
                  }
                });
                const mergedEDM = _.merge({}, listSelected.edm,
                  state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.edm);
                const mergedRDM = _.merge({}, listSelected.rdm,
                  state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.rdm);
                console.log(mergedEDM, mergedRDM);
                const filteredFacEDMRDM = _.filter([..._.toArray(mergedEDM), ..._.toArray(mergedRDM)], item => item.typeWs === 'fac');
                if (count === data.length) {
                  ctx.patchState(produce(ctx.getState(), draft => {
                    draft.content[wsIdentifier].riskLink.listEdmRdm = {
                      ...draft.content[wsIdentifier].riskLink.listEdmRdm,
                      selectedListEDMAndRDM: {
                        edm: mergedEDM,
                        rdm: mergedRDM
                      },
                      data: this._update(draft.content[wsIdentifier].riskLink.listEdmRdm.data, listSelected)
                    };
                    draft.content[wsIdentifier].riskLink.financialPerspective = {
                      ...draft.content[wsIdentifier].riskLink.financialPerspective,
                      rdm: {data: mergedRDM, selected: null},
                    };
                  }));
                  // ctx.dispatch(new fromWs.PatchRiskLinkDisplayAction({key: 'displayTable', value: false}));
                  // ctx.dispatch(new fromWs.LoadPortfolioForLinkingAction(_.toArray(mergedEDM)[0]));
                  ctx.dispatch(new fromWs.LoadRiskLinkAnalysisDataAction(_.filter(_.toArray(mergedRDM), item => item.typeWs === 'treaty')));
                  ctx.dispatch(new fromWs.LoadRiskLinkPortfolioDataAction(_.filter(_.toArray(mergedEDM), item => item.typeWs === 'treaty')));
                  ctx.dispatch(new fromWs.SelectFacRiskLinkEDMAndRDMAction(filteredFacEDMRDM));
                }
              });
          }
        });
      });
    } else {
      const mergedEDM = _.merge({}, listSelected.edm,
        state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.edm);
      const mergedRDM = _.merge({}, listSelected.rdm,
        state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.rdm);
      const filteredFacEDMRDM = _.filter([..._.toArray(mergedEDM), ..._.toArray(mergedRDM)], item => item.typeWs === 'fac');
      ctx.patchState(produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.listEdmRdm = {
            ...draft.content[wsIdentifier].riskLink.listEdmRdm,
            selectedListEDMAndRDM: {
              edm: listSelected.edm,
              rdm: listSelected.rdm
            }
          };
          draft.content[wsIdentifier].riskLink.financialPerspective = {
            ...draft.content[wsIdentifier].riskLink.financialPerspective,
            rdm: {data: listSelected.rdm, selected: null},
          };
        }
        )
      );
      // ctx.dispatch(new fromWs.LoadPortfolioForLinkingAction(_.toArray(mergedEDM)[0]));
      ctx.dispatch(new fromWs.LoadRiskLinkAnalysisDataAction(_.filter(_.toArray(mergedRDM), item => item.typeWs === 'treaty')));
      ctx.dispatch(new fromWs.LoadRiskLinkPortfolioDataAction(_.filter(_.toArray(mergedEDM), item => item.typeWs === 'treaty')));
      ctx.dispatch(new fromWs.SelectFacRiskLinkEDMAndRDMAction(filteredFacEDMRDM));
    }
  }

  /** SEARCH WITH KEYWORD OR PAGE OF EDM AND RDM */
  searchRiskLinkEDMAndRDM(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {keyword, size} = payload;
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const array = state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM;
    const arrayCong = [..._.toArray(array.edm), ..._.toArray(array.rdm)];
    console.log(arrayCong);
    return this.riskApi.searchRiskLinkData(keyword, size).pipe(
      mergeMap(
        (ds: any) =>
          of(ctx.patchState(produce(
            ctx.getState(), draft => {
              draft.content[wsIdentifier].riskLink.listEdmRdm = {
                ...draft.content[wsIdentifier].riskLink.listEdmRdm,
                data: Object.assign({},
                  ...ds.content.map(item => {
                    const validator = arrayCong.filter(vd => vd.id == item.id);
                    const validate = validator.length > 0;
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
          standard: {
            data: Object.assign({}, ...payload.map(item => ({[item.id]: {...item}}))),
            allChecked: false,
            indeterminate: false
          },
          analysis: {
            ...draft.content[wsIdentifier].riskLink.results,
            data: Object.assign({}, ..._.toArray(draft.content[wsIdentifier].riskLink.results.data).map(item => ({
              [item.id]: {...item, selected: false}
            }))),
            allChecked: false,
            indeterminate: false
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
        (ds: any) => {
          return of(ctx.patchState(
            produce(
              ctx.getState(), draft => {
                draft.content[wsIdentifier].riskLink = {
                  ...draft.content[wsIdentifier].riskLink, listEdmRdm: {
                    ...draft.content[wsIdentifier].riskLink.listEdmRdm,
                    data: Object.assign({},
                      ...ds.content.map(item => ({
                          [item.id]: {
                            ...item,
                            typeWs: 'treaty',
                            selected: false,
                            source: '',
                          }
                        }
                      ))),
                    searchValue: '',
                    totalNumberElement: ds.totalElements,
                    numberOfElement: ds.size
                  }
                };
                draft.content[wsIdentifier].riskLink = {
                  ...draft.content[wsIdentifier].riskLink,
                  linking: {
                    edm: null,
                    rdm: {data: null, selected: null},
                    autoLinks: null,
                    linked: [],
                    analysis: null,
                    portfolio: null
                  },
                  financialValidator: {
                    rmsInstance: {
                      data: ['AZU-P-RL17-SQL14', 'AZU-U-RL17-SQL14', 'AZU-U2-RL181-SQL16'],
                      selected: this._facTraitement(ctx) ? 'AZU-U-RL17-SQL14' : 'AZU-P-RL17-SQL14'
                    },
                    financialPerspectiveELT: {
                      data: ['Net Loss Pre Cat (RL)', 'Gross Loss (GR)', 'Net Cat (NC)'],
                      selected: this._facTraitement(ctx) ? 'Gross Loss (GR)' : 'Net Loss Pre Cat (RL)'
                    },
                    targetCurrency: {
                      data: ['Main Liability Currency (MLC)', 'Analysis Currency', 'User Defined Currency'],
                      selected: 'Main Liability Currency (MLC)'
                    },
                    division: {data: ['Division N°1', 'Division N°2', 'Division N°3'], selected: 'Division N°1'},
                  },
                  display: {
                    displayListRDMEDM: false,
                    displayTable: false,
                    displayImport: false,
                  },
                  analysis: null,
                  portfolios: null,
                  analysisFac: null,
                  portfolioFac: null,
                  results: null,
                  summaries: null,
                  selectedEDMOrRDM: null,
                  activeAddBasket: false,
                  synchronize: false,
                };
              }
            )
          ));
        }
      ),
      mergeMap(dt => of(ctx.dispatch(new fromWs.SynchronizeEDMAndRDMSelectionAction())))
    );
  }

  private _facTraitement(ctx): boolean {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    return state.content[wsIdentifier].workspaceType === 'fac';
  }

  private _update(source, list) {
    const mergedList = [..._.toArray(list.edm), ..._.toArray(list.rdm)].map(item => item.id);
    const newdata = _.toArray(source).map(item => {
      const exist = _.includes(mergedList , item.id);
      if (exist) {
        return {...item, selected: true};
      } else {
        return {...item, selected: false};
      }
    });
    return Object.assign({}, ...newdata.map(item => ({
        [item.id]: {...item}
      }
    )));
  }

  private _getTypeData(item) {
    const lastIndex = item.slice(-2);
    return lastIndex === '_R';
  }

  private _getSelectionValue(ctx, value) {
    const state = ctx.getState();
    const wsIdentifier = state.currentTab.wsIdentifier;
    return value === state.content[wsIdentifier].wsId +
      this.divisionTag[state.content[wsIdentifier].riskLink.financialValidator.division.selected];
  }

  private _getSelectionValueCostume(ctx, value, division) {
    const state = ctx.getState();
    const wsIdentifier = state.currentTab.wsIdentifier;
    return value === state.content[wsIdentifier].wsId + this.divisionTag[division];
  }

  private _isValidImport(ctx, data) {
    let validationData = [];
    _.forEach(data, item => {
      _.forEach(item.division, itemDiv => {
        validationData = [...validationData, {regionPeril: item.regionPeril, division: itemDiv}];
      });
    });
    // const valid = _.uniqBy(validationData, item => item.regionPeril);
    const validComp = _.uniqBy(validationData, item => item.division && item.regionPeril);
    console.log(validationData, validComp);
    // const divisions = _.uniq(_.flatten(_.map(data, item => item.division)));
    return validComp.length >= validationData.length;
  }

}
