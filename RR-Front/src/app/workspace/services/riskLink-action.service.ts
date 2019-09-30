import {StateContext} from '@ngxs/store';
import * as fromWs from '../store/actions';
import {LoadBasicAnalysisFacAction, LoadPortfolioFacAction, PatchRiskLinkDisplayAction} from '../store/actions';
import * as _ from 'lodash';
import {catchError, mergeMap, switchMap} from 'rxjs/operators';
import {of} from 'rxjs/internal/observable/of';
import {RiskApi} from './risk.api';
import {forkJoin} from 'rxjs';
import {Injectable} from '@angular/core';
import {RiskLinkState} from '../store/states';
import {WorkspaceModel} from '../model';
import produce from 'immer';

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
    ctx.patchState(produce(ctx.getState(), draft => {
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
    const {action, value, item, from, to} = payload;
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
          draft.content[wsIdentifier].riskLink.portfolios[selectedPortfolio.id].indeterminate =
            !(numSelectedItems === 1 && value === false) && !(numSelectedItems === portfolios.length - 1 && value === true);
          draft.content[wsIdentifier].riskLink.portfolios[selectedPortfolio.id].allChecked =
            (numSelectedItems === portfolios.length - 1 && value === true);
        }));
      ctx.dispatch(new fromWs.PatchAddToBasketStateAction());
    } else if (action === 'chunk') {
      portfolios.forEach((dt: any, index) => {
        let pt;
        if (index <= to && index >= from) {
          pt = {
            [dt.dataSourceId]: {
              ...dt, selected: true
            }
          };
        } else {
          pt = {
            [dt.dataSourceId]: {
              ...dt, selected: false
            }
          };
        }
        newData = _.merge(newData, pt);
      });
      const newArray = _.toArray(newData);
      const filtredData = _.filter(newArray, (pts: any) => pts.selected);
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.portfolios[selectedPortfolio.id].data = newData;
          draft.content[wsIdentifier].riskLink.portfolios[selectedPortfolio.id].indeterminate =
            filtredData.length !== 0 && newArray.length !== filtredData.length;
          draft.content[wsIdentifier].riskLink.portfolios[selectedPortfolio.id].allChecked =
            filtredData.length === 0 || newArray.length === filtredData.length;
        }));
      ctx.dispatch(new fromWs.PatchAddToBasketStateAction());
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
          draft.content[wsIdentifier].riskLink.portfolios[selectedPortfolio.id].indeterminate = false;
          draft.content[wsIdentifier].riskLink.portfolios[selectedPortfolio.id].allChecked = selected;
        }));
      ctx.dispatch(new fromWs.PatchAddToBasketStateAction());
    }
  }

  toggleRiskLinkAnalysis(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {action, value, item, from, to} = payload;
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
          draft.content[wsIdentifier].riskLink.analysis[selectedAnalysis.id].indeterminate =
            !(numSelectedItems === 1 && value === false) && !(numSelectedItems === analysis.length - 1 && value === true);
          draft.content[wsIdentifier].riskLink.analysis[selectedAnalysis.id].allChecked =
            (numSelectedItems === analysis.length - 1 && value === true);
        }));
      ctx.dispatch(new fromWs.PatchAddToBasketStateAction());
    } else if (action === 'chunk') {
      analysis.forEach((st: any, index) => {
        let pt;
        if (index <= to && index >= from) {
          pt = {
            [st.analysisId]: {
              ...st, selected: true
            }
          };
        } else {
          pt = {
            [st.analysisId]: {
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
          draft.content[wsIdentifier].riskLink.analysis[selectedAnalysis.id].data = newData;
          draft.content[wsIdentifier].riskLink.analysis[selectedAnalysis.id].indeterminate =
            filtredData.length !== 0 && newArray.length !== filtredData.length;
          draft.content[wsIdentifier].riskLink.analysis[selectedAnalysis.id].allChecked =
            filtredData.length === 0 || newArray.length === filtredData.length;
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
      });
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.analysis[selectedAnalysis.id].data = newData;
          draft.content[wsIdentifier].riskLink.analysis[selectedAnalysis.id].indeterminate = false;
          draft.content[wsIdentifier].riskLink.analysis[selectedAnalysis.id].allChecked = selected;
        }));
      ctx.dispatch(new fromWs.PatchAddToBasketStateAction());
    }
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
          draft.content[wsIdentifier].riskLink.summaries.data[item.dataSourceId].selected = value;
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
            [st.dataSourceId]: {
              ...st, selected: true
            }
          };
        } else {
          pt = {
            [st.dataSourceId]: {
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
            ...summaries.map(dt => ({[dt.dataSourceId]: {...dt, selected: selected}})));
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
    const {action, wrapper, value, item, from, to} = payload;
    console.log(item);
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const linkAnalysis = _.toArray(state.content[wsIdentifier].riskLink.linking.analysis[wrapper.id].data);
    const numSelectedItems = _.filter(linkAnalysis, data => data.selected).length;
    if (action === 'selectOne') {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.linking.analysis[wrapper.id].data[item.id].selected = value;
          draft.content[wsIdentifier].riskLink.linking.analysis[wrapper.id].indeterminate =
            !(numSelectedItems === 1 && value === false) && !(numSelectedItems === linkAnalysis.length - 1 && value === true);
          draft.content[wsIdentifier].riskLink.linking.analysis[wrapper.id].allChecked =
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
          draft.content[wsIdentifier].riskLink.linking.analysis[wrapper.id].data = newData;
          draft.content[wsIdentifier].riskLink.linking.analysis[wrapper.id].indeterminate =
            filtredData.length !== 0 && newArray.length !== filtredData.length;
          draft.content[wsIdentifier].riskLink.linking.analysis[wrapper.id].allChecked =
            filtredData.length === 0 || newArray.length === filtredData.length;
        }));
    } else {
      let selected: boolean;
      action === 'selectAll' ? selected = true : selected = false;
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.linking.analysis[wrapper.id].data = Object.assign({},
            ...linkAnalysis.map(dt => ({[dt.id]: {...dt, selected: selected}})));
          draft.content[wsIdentifier].riskLink.linking.analysis[wrapper.id].indeterminate = false;
          draft.content[wsIdentifier].riskLink.linking.analysis[wrapper.id].allChecked = selected;
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
    let results = {data: {}, filter: {}, numberOfElement: 0, allChecked: false, indeterminate: false};
    let summary = {data: {}, filter: {}, numberOfElement: 0, allChecked: false, indeterminate: false};
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
            return ({[item.id]: {...item, selected: false}});
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
            [item.id]: {
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
            [item.id]: {
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
          [analysis.analysisId]: {
            ...analysis
          }})
        ));
      }
    ));
  }

  createLinking(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const portfolio = _.toArray(state.content[wsIdentifier].riskLink.linking.portfolio.data);
    const listAnalysis = _.toArray(state.content[wsIdentifier].riskLink.linking.analysis);
    const selectedPortfolios = _.filter(portfolio, item => item.selected);
    let selectedAnalysis = [];
    listAnalysis.forEach(item => {
      selectedAnalysis = [...selectedAnalysis, ..._.filter(_.toArray(item.data), analysis => analysis.selected)];
    });
    ctx.patchState(produce(
      ctx.getState(), draft => {
        if (selectedPortfolios.length > 0 && selectedPortfolios.length > 0) {
          draft.content[wsIdentifier].riskLink.linking.linked = [...draft.content[wsIdentifier].riskLink.linking.linked,
            {analysis: [...selectedAnalysis], portfolio: [...selectedPortfolios], selected: false, update: false}];
        }
      }
    ));
    console.log(payload);
    ctx.dispatch(new fromWs.ToggleAnalysisLinkingAction({action: 'unselectAll', wrapper: payload}));
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
            selected: item.name === 'SBS_RR_2019_08_SoonLing_E' || item.name === 'SBS_RR_2019_08_SoonLing_R',
            source: ''};
          });
          const selectedData = {
            rdm: Object.assign({},
              ..._.filter(dataModel, item => item.type === 'rdm' && item.selected).map((ds: any) =>
                ({[ds.id]: {...ds, selected: false, scanned: true}}))),
            edm: Object.assign({},
              ..._.filter(dataModel, item => item.type === 'edm' && item.selected).map((ds: any) =>
                ({[ds.id]: {...ds, selected: false, scanned: true}})))
          };
          console.log(_.toArray(selectedData.rdm), _.toArray(selectedData.edm));
          ctx.dispatch(new LoadBasicAnalysisFacAction(_.toArray(selectedData.rdm)));
          ctx.dispatch(new LoadPortfolioFacAction(_.toArray(selectedData.edm)));
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
            }))
          );
        }
      )
    );
  }

  loadBasicAnalysisFac(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    return forkJoin(
      payload.map(dt => this.riskApi.searchFacAnalysisBasic(dt.id, dt.name))
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
                        ...analysis,
                        selected: false
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

  loadDetailAnalysisFac(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
  }

  loadBasicPortfolioFac(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    return forkJoin(
      payload.map(dt => this.riskApi.searchFacPortfolio(dt.id, dt.name))
    ).pipe(
      switchMap(out => {
        let dataTable = {};
        out.forEach((dt: any, i) => {
            dataTable = {
              ...dataTable,
              [payload[i].id]: {
                data: Object.assign({},
                  ...dt.map(portfolio => ({
                      [portfolio.portId]: {
                        ...portfolio,
                        selected: false
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
          allChecked: false,
          indeterminate: false,
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
    return this.riskApi.searchRiskLinkAnalysis(payload.id, payload.name).pipe(
      mergeMap(dt => {
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
            allChecked: false,
            indeterminate: false,
            totalNumberElement: dt.totalElements,
            numberOfElement: dt.size,
            filter: {}
          }
        };
        return of(ctx.patchState(
          produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].riskLink.linking.rdm.selected = payload;
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
        draft.content[wsIdentifier].riskLink.linking.analysis = null;
      }
    }));
    const selectedRdms = _.filter(_.toArray(state.content[wsIdentifier].riskLink.linking.rdm.data),
      dt => dt.selected);
    if (selectedRdms.length === 0 && selected === false) {
      ctx.dispatch(new fromWs.LoadAnalysisForLinkingAction(payload.item));
    }
    if (_.get(currentSelection, 'id', null) === id && selected) {
      ctx.dispatch(new fromWs.LoadAnalysisForLinkingAction(selectedRdms[0]));
    }
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
                const mergedEDM = _.merge({}, listSelected.edm,
                  state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.edm);
                const mergedRDM = _.merge({}, listSelected.rdm,
                  state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.rdm);
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
                    draft.content[wsIdentifier].riskLink.linking = {
                      ...draft.content[wsIdentifier].riskLink.linking,
                      edm: listSelected.edm,
                      rdm: {data: listSelected.rdm, selected: null}
                    };
                    draft.content[wsIdentifier].riskLink.financialPerspective = {
                      ...draft.content[wsIdentifier].riskLink.financialPerspective,
                      rdm: {data: listSelected.rdm, selected: null},
                    };
                  }));
                  // ctx.dispatch(new fromWs.PatchRiskLinkDisplayAction({key: 'displayTable', value: false}));
                  ctx.dispatch(new fromWs.LoadPortfolioForLinkingAction(_.toArray(mergedEDM)[0]));
                  ctx.dispatch(new fromWs.LoadRiskLinkAnalysisDataAction(_.toArray(mergedRDM)));
                  ctx.dispatch(new fromWs.LoadRiskLinkPortfolioDataAction(_.toArray(mergedEDM)));
                }
              });
          }
        });
      });
    } else {
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
            rdm: {data: listSelected.rdm, selected: null}
          };
          draft.content[wsIdentifier].riskLink.financialPerspective = {
            ...draft.content[wsIdentifier].riskLink.financialPerspective,
            rdm: {data: listSelected.rdm, selected: null},
          };
        }
        )
      );
      ctx.dispatch(new fromWs.PatchRiskLinkDisplayAction({key: 'displayTable', value: false}));
      ctx.dispatch(new fromWs.LoadPortfolioForLinkingAction(_.filter(listDataToArray, et => et.type === 'edm' && et.selected)[0]));
      ctx.dispatch(new fromWs.LoadRiskLinkAnalysisDataAction(_.filter(listDataToArray, rt => rt.type === 'rdm' && rt.selected)));
      ctx.dispatch(new fromWs.LoadRiskLinkPortfolioDataAction(_.filter(listDataToArray, et => et.type === 'edm' && et.selected)));
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
                    selectedListEDMAndRDM: {edm: null, rdm: null},
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
                    calibration: {data: ['Add calibration', 'item 1', 'item 2'], selected: 'Add calibration'},
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
              }
            )
          ))
      )
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
}


