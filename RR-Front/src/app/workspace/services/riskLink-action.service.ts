import {StateContext} from '@ngxs/store';
import * as fromWs from '../store/actions';
import {
  LoadBasicAnalysisFacPerDivisionAction, LoadDivisionSelection,
  LoadPortfolioFacAction,
  PatchRiskLinkDisplayAction, SaveDivisionSelection,
  UpdateAnalysisAndPortfolioData
} from '../store/actions';
import * as _ from 'lodash';
import {catchError, count, mergeMap, switchMap} from 'rxjs/operators';
import {of} from 'rxjs/internal/observable/of';
import {RiskApi} from './api/risk.api';
import {empty, forkJoin} from 'rxjs';
import {Injectable} from '@angular/core';
import {WorkspaceModel} from '../model';
import produce from 'immer';


//const instanceId = 'RL18-1';
const instanceName = 'AZU-P1-RL18-SQL16';

const testSummary2 = {
  "analysis": [
    {
      "selected": false,
      "rlAnalysisId": 9,
      "rlModelDataSourceId": 1,
      "projectId": 18,
      "rdmId": 131,
      "rdmName": "AA2012_SyntheticCurve_R",
      "rlId": 19,
      "analysisName": "UK_Fire_Evt",
      "analysisDescription": "UK Fire Per Event",
      "defaultGrain": "UK_Fire_Evt",
      "analysisCurrency": "GBP",
      "rlExchangeRate": null,
      "region": "EU",
      "peril": "WS",
      "geoCode": "",
      "rpCode": "EUWS",
      "systemRegionPeril": "EUWS",
      "subPeril": "WS Wind",
      "defaultOccurrenceBasis": null,
      "regionName": null,
      // To be added fields
      "financialPerspectives": [],
      "targetCurrency": "EUR",
      "peqt": [],
      "unitMultiplier": 1,
      "proportion": 100,
      "targetRaps": [],
      "overrideReason": null,
      "occurrenceBasis": null
    },
    {
      "selected": false,
      "rlAnalysisId": 10,
      "rlModelDataSourceId": 1,
      "projectId": 18,
      "rdmId": 131,
      "rdmName": "AA2012_SyntheticCurve_R",
      "rlId": 20,
      "analysisName": "BE_ENG_All",
      "analysisDescription": "Belgium Engineering",
      "defaultGrain": "BE_ENG_All",
      "analysisCurrency": "EUR",
      "rlExchangeRate": null,
      "region": "EU",
      "peril": "WS",
      "geoCode": "",
      "rpCode": "EUWS",
      "systemRegionPeril": "EUWS",
      "subPeril": "WS Wind",
      "defaultOccurrenceBasis": null,
      "regionName": null,
      // To be added fields
      "financialPerspectives": [],
      "targetCurrency": "EUR",
      "peqt": [],
      "unitMultiplier": 1,
      "proportion": 100,
      "targetRaps": [],
      "overrideReason": null,
      "occurrenceBasis": null
    }
  ],
  "portfolios": [
    {
      "selected": false,
      "rlPortfolioId": 349,
      "entity": 1,
      "projectId": 10,
      "edmId": 130,
      "edmName": "AA2012_SyntheticCurve_E",
      "rlId": 1,
      "number": "PT_ENG_ALL_inEUR | PT_ENG_ALL_inEUR | EQ",
      "name": "PT_ENG_ALL_inEUR",
      "created": "2011-11-22T11:04:45.000+0000",
      "description": "",
      "type": "AGG",
      "peril": "EQ",
      "agSource": "",
      "agCedent": "PT_as_FIDELIDADE",
      "agCurrency": "EUR",
      "targetCurrency": "EUR",
      "unitMultiplier": 1,
      "proportion": 100,
      "tiv": 0.00,
      "importLocationLevel": false
    }
  ],
  "sourceEpHeaders": []
};

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
      ctx.dispatch([new LoadBasicAnalysisFacPerDivisionAction()]);
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
      const oldValue = state.content[wsIdentifier].riskLink.financialValidator.division.selected.divisionNumber;
      const data = [...state.content[wsIdentifier].riskLink.analysis, ...state.content[wsIdentifier].riskLink.portfolios];
      if (data.length > 0) {
        ctx.dispatch(new SaveDivisionSelection(oldValue));
      }
    }
    ctx.patchState(
      produce(ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.financialValidator = newValue;
      }));
    /*    if (payload.key === 'division') {
          ctx.dispatch(new UpdateAnalysisAndPortfolioData(payload.value));
        }*/
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
    const {action, RDM, source} = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
      switch (action) {
        case 'selectOne':
          const targetDatasource = draft.content[wsIdentifier].riskLink.listEdmRdm.data[RDM.rmsId];
          targetDatasource.selected = !targetDatasource.selected;
          break;

        case 'selectAll':
          _.forEach(draft.content[wsIdentifier].riskLink.listEdmRdm.data, (value, key) => {
            value.selected = true;
          });
          break;

        case 'unselectAll':
          _.forEach(draft.content[wsIdentifier].riskLink.listEdmRdm.data, (value, key) => {
            value.selected = false;
          });
          break;
      }

    }));
  }

  dataSourcesScan(ctx: StateContext<WorkspaceModel>, payload) {
    ctx.patchState(produce(ctx.getState(), draft => {
      const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
      draft.content[wsIdentifier].riskLink.display.displayListRDMEDM = true;
      _.toArray(draft.content[wsIdentifier].riskLink.listEdmRdm.data)
        .filter(item => item.selected)
        .forEach(selectedItem => {
          const key = selectedItem.rmsId;
          if (selectedItem.type == 'EDM') {
            draft.content[wsIdentifier].riskLink.selection.edms[key] = {...selectedItem, scanning: true}
          } else if (selectedItem.type == 'RDM') {
            draft.content[wsIdentifier].riskLink.selection.rdms[key] = {...selectedItem, scanning: true}
          }
        });
    }));
    ctx.dispatch(new fromWs.BasicScanEDMAndRDMAction(payload))
  }

  toggleRiskLinkPortfolio(ctx: StateContext<WorkspaceModel>, payload) {
    const {action, value, item, data} = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
      const {selection} = draft.content[wsIdentifier].riskLink;
      switch (action) {
        case 'selectOne':
          const targetPortfolioIndex = draft.content[wsIdentifier].riskLink.portfolios.findIndex(p => p.rlPortfolioId == item.rlPortfolioId);
          const targetPortfolio = draft.content[wsIdentifier].riskLink.portfolios[targetPortfolioIndex];
          draft.content[wsIdentifier].riskLink.portfolios[targetPortfolioIndex] = {
            ...targetPortfolio,
            selected: !targetPortfolio.selected
          };
          const edmId = selection.currentDataSource;
          if (draft.content[wsIdentifier].riskLink.portfolios[targetPortfolioIndex].selected) {
            draft.content[wsIdentifier].riskLink.selection.portfolios[edmId] =
              _.merge(selection.portfolios[edmId], {[item.rlPortfolioId]: item});
          } else {
            if (selection.portfolios[edmId]) {
              draft.content[wsIdentifier].riskLink.selection.portfolios[edmId] =
                _.omit(selection.portfolios[edmId], [item.rlPortfolioId]);
            }
          }
          break;

        case 'unSelectChunk':
          _.forEach(data, portfolioItem => {
            const targetPortfolioIndex = draft.content[wsIdentifier].riskLink.portfolios.findIndex(p => p.rlPortfolioId == portfolioItem.rlPortfolioId);
            const targetPortfolio = draft.content[wsIdentifier].riskLink.portfolios[targetPortfolioIndex];
            draft.content[wsIdentifier].riskLink.portfolios[targetPortfolioIndex] = {
              ...targetPortfolio,
              selected: false
            };
            const edmId = draft.content[wsIdentifier].riskLink.selection.currentDataSource;
            draft.content[wsIdentifier].riskLink.selection.portfolios[edmId] =
              _.omit(selection.portfolios[edmId], [portfolioItem.rlPortfolioId])
          });
          break;

        case 'selectChunk':
          _.forEach(data, portfolioItem => {
            const targetPortfolioIndex = draft.content[wsIdentifier].riskLink.portfolios.findIndex(p => p.rlPortfolioId == portfolioItem.rlPortfolioId);
            const targetPortfolio = draft.content[wsIdentifier].riskLink.portfolios[targetPortfolioIndex];
            draft.content[wsIdentifier].riskLink.portfolios[targetPortfolioIndex] = {
              ...targetPortfolio,
              selected: true
            };
            const edmId = draft.content[wsIdentifier].riskLink.selection.currentDataSource;
            draft.content[wsIdentifier].riskLink.selection.portfolios[edmId] =
              _.merge(selection.portfolios[edmId], {[portfolioItem.rlPortfolioId]: portfolioItem});
          });
          break;
      }
    }));
  }

  toggleRiskLinkAnalysis(ctx: StateContext<WorkspaceModel>, payload) {
    const {action, value, item, data} = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
      const {selection} = draft.content[wsIdentifier].riskLink;
      switch (action) {
        case 'selectOne':
          const targetAnalysisIndex = draft.content[wsIdentifier].riskLink.analysis.findIndex(a => a.rlAnalysisId == item.rlAnalysisId);
          const targetAnalysis = draft.content[wsIdentifier].riskLink.analysis[targetAnalysisIndex];
          draft.content[wsIdentifier].riskLink.analysis[targetAnalysisIndex] = {
            ...targetAnalysis,
            selected: !targetAnalysis.selected
          };
          const rdmId = selection.currentDataSource;
          if (draft.content[wsIdentifier].riskLink.analysis[targetAnalysisIndex].selected) {
            draft.content[wsIdentifier].riskLink.selection.analysis[rdmId] =
              _.merge(selection.analysis[rdmId], {[item.rlAnalysisId]: item})
          } else {
            if (selection.analysis[rdmId])
              draft.content[wsIdentifier].riskLink.selection.analysis[rdmId] =
                _.omit(selection.analysis[rdmId], [item.rlAnalysisId])
          }
          break;

        case 'unSelectChunk':
          _.forEach(data, analysisItem => {
            const targetAnalysisIndex = draft.content[wsIdentifier].riskLink.analysis.findIndex(a => a.rlAnalysisId == analysisItem.rlAnalysisId);
            const targetAnalysis = draft.content[wsIdentifier].riskLink.analysis[targetAnalysisIndex];
            draft.content[wsIdentifier].riskLink.analysis[targetAnalysisIndex] = {
              ...targetAnalysis,
              selected: false
            };
            const rdmId = draft.content[wsIdentifier].riskLink.selection.currentDataSource;
            draft.content[wsIdentifier].riskLink.selection.analysis[rdmId] =
              _.omit(selection.analysis[rdmId], [analysisItem.rlAnalysisId])
          });
          break;

        case 'selectChunk':
          _.forEach(data, analysisItem => {
            const targetAnalysisIndex = draft.content[wsIdentifier].riskLink.analysis.findIndex(a => a.rlAnalysisId == analysisItem.rlAnalysisId);
            const targetAnalysis = draft.content[wsIdentifier].riskLink.analysis[targetAnalysisIndex];
            draft.content[wsIdentifier].riskLink.analysis[targetAnalysisIndex] = {
              ...targetAnalysis,
              selected: true
            };
            const rdmId = draft.content[wsIdentifier].riskLink.selection.currentDataSource;
            draft.content[wsIdentifier].riskLink.selection.analysis[rdmId] =
              _.merge(selection.analysis[rdmId], {[analysisItem.rlAnalysisId]: analysisItem})
          });
          break;
      }
    }));
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
          item => {
            _.forEach(item.data, dt => {
              if (dt.selected) {
                dt.imported = true;
              }
            });
          });
        draft.content[wsIdentifier].riskLink.portfolios = _.forEach(draft.content[wsIdentifier].riskLink.portfolios,
          item => {
            _.forEach(item.data, dt => {
              if (dt.selected) {
                dt.imported = true;
              }
            });
          });
      })
    );

    ctx.dispatch(new fromWs.LoadDetailAnalysisFacAction(_.filter(dataAnalysis, (item: any) => item.typeWs === 'fac')));
    ctx.dispatch(new fromWs.LoadLinkingDataAction({
      analysis: _.toArray(resultsInfo),
      portfolios: _.toArray(summaryInfo)
    }));
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
      _.forEach(division, item => {
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

  saveDivisionSelection(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const selectedDivision = state.content[wsIdentifier].riskLink.financialValidator.division.selected.divisionNumber;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].riskLink.facSelection[payload] = {
        analysis: draft.content[wsIdentifier].riskLink.selection.analysis,
        portfolios: draft.content[wsIdentifier].riskLink.selection.portfolios,
      };
      draft.content[wsIdentifier].riskLink.selection = {
          ...draft.content[wsIdentifier].riskLink.selection,
          ...draft.content[wsIdentifier].riskLink.facSelection[selectedDivision]
      }
    }));
    ctx.dispatch(new LoadDivisionSelection());
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
      return {
        ...item, peqt: _.filter(list,
          dt => dt.analysisId === item.analysisId && dt.analysisName === item.analysisName)[0].selectedItems
      };
    });
    console.log(list, results);
    ctx.patchState(produce(
      ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.results.data = Object.assign({}, ...results.map(analysis => ({
            [analysis.analysisId + '-' + analysis.analysisName]: {
              ...analysis
            }
          })
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
          {
            [wsIdentifier]: {
              edm: Object.assign({}, ...edm.map(item => ({[item.id]: {...item, selected: false}}))),
              rdm: Object.assign({}, ...rdm.map(item => ({[item.id]: {...item, selected: false}})))
            }
          })
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

  basicScanEDMAndRDM(ctx: StateContext<WorkspaceModel>, payload) {
    const {selectedDS, projectId, instanceId} = payload;
    const state = ctx.getState();
    return this.riskApi.scanDatasources(selectedDS, projectId, instanceId, instanceName)
      .pipe(mergeMap((response: any[]) => {
          ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = _.get(draft.currentTab, 'wsIdentifier', null);
            const parsedResponse = this._parseBasicScanResponse(response);
            const edms = _.filter(_.toArray(state.content[wsIdentifier].riskLink.selection.edms), item =>
                _.includes(_.keys(parsedResponse.edms), `${item.rmsId}`));
            const rdms = _.filter(_.toArray(state.content[wsIdentifier].riskLink.selection.rdms), item =>
                _.includes(_.keys(parsedResponse.rdms), `${item.rmsId}`));
            console.log('answer', response, parsedResponse, rdms, edms);
            draft.content[wsIdentifier].riskLink.selection.edms = {...draft.content[wsIdentifier].riskLink.selection.edms, ...Object.assign(
              {}, ..._.map(edms, item =>
                ({[item.rmsId]: {...item, scanning: false, count: _.get(parsedResponse.edms, `${item.rmsId}.count`, 0)}}))
            )};
            draft.content[wsIdentifier].riskLink.selection.rdms = { ...draft.content[wsIdentifier].riskLink.selection.rdms,
              ...Object.assign({}, ..._.map(rdms, item =>
                ({[item.rmsId]: {...item, scanning: false, count: _.get(parsedResponse.rdms, `${item.rmsId}.count`, 0)}}))
            )};
          }));
          return of(response);
        }),
        catchError((err, response) => {
          console.log(err, response);
          return of(err);
        }));
  }

  private _parseBasicScanResponse(response: any[]) {
    const result = {edms: {}, rdms: {}};
    _.forEach(response, (item: any) => {
      if (item.type === 'EDM') {
        result.edms = _.merge(result.edms, {[item.rlId]: {rmsId: item.rlId, count: item.count}});
      } else if (item.type === 'RDM') {
        result.rdms = _.merge(result.edms, {[item.rlId]: {rmsId: item.rlId, count: item.count}});
      }
    });
    return result;
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
    const {rmsId, target} = payload;
    if (target === 'RDM') {
      ctx.patchState(produce(ctx.getState(), draft => {
        draft.content[wsIdentifier].riskLink.selection.rdms = _.omit(draft.content[wsIdentifier].riskLink.selection.rdms, rmsId);
      }));
    } else {
      ctx.patchState(
        produce(ctx.getState(), draft => {
          draft.content[wsIdentifier].riskLink.selection.edms = _.omit(draft.content[wsIdentifier].riskLink.selection.edms, rmsId);
        })
      );
    }
  }

  // deleteLink(ctx: StateContext<WorkspaceModel>, payload) {
  //   const state = ctx.getState();
  //   const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
  //   ctx.patchState(produce(ctx.getState(), draft => {
  //     draft.content[wsIdentifier].riskLink.linking.linked.splice(
  //       _.findIndex(draft.content[wsIdentifier].riskLink.linking.linked, payload), 1);
  //   }));
  // }

  // deleteInnerLink(ctx: StateContext<WorkspaceModel>, payload) {
  //   const state = ctx.getState();
  //   const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
  //   const {item, link, target} = payload;
  //   if (target === 'portfolio') {
  //     ctx.patchState(produce(ctx.getState(), draft => {
  //       const index = _.findIndex(draft.content[wsIdentifier].riskLink.linking.linked, link);
  //       if (draft.content[wsIdentifier].riskLink.linking.linked[index].portfolio.length === 1) {
  //         draft.content[wsIdentifier].riskLink.linking.linked.splice(
  //           _.findIndex(draft.content[wsIdentifier].riskLink.linking.linked, link), 1);
  //       } else {
  //         draft.content[wsIdentifier].riskLink.linking.linked[index].portfolio.splice(
  //           _.findIndex(draft.content[wsIdentifier].riskLink.linking.linked[index].portfolio, item), 1);
  //       }
  //     }));
  //   } else if (target === 'analysis') {
  //     ctx.patchState(produce(ctx.getState(), draft => {
  //       const index = _.findIndex(draft.content[wsIdentifier].riskLink.linking.linked, link);
  //       if (draft.content[wsIdentifier].riskLink.linking.linked[index].analysis.length === 1) {
  //         draft.content[wsIdentifier].riskLink.linking.linked.splice(
  //           _.findIndex(draft.content[wsIdentifier].riskLink.linking.linked, link), 1);
  //       } else {
  //         draft.content[wsIdentifier].riskLink.linking.linked[index].analysis.splice(
  //           _.findIndex(draft.content[wsIdentifier].riskLink.linking.linked[index].analysis, item), 1);
  //       }
  //     }));
  //   }
  // }

  // loadLinkingDataAction(ctx: StateContext<WorkspaceModel>, payload) {
  //   const state = ctx.getState();
  //   const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
  //   const {analysis, portfolios} = payload;
  //   const rdm = _.uniqBy(analysis.map(item => ({name: item.rdmName, id: item.rdmId, selected: false, type: 'rdm'})),
  //     (dt: any) => dt.id);
  //   const edm = _.uniqBy(portfolios.map(item => ({name: item.edmName, id: item.edmId, selected: false, type: 'edm'})),
  //     (dt: any) => dt.id);
  //   ctx.patchState(produce(ctx.getState(), draft => {
  //     draft.content[wsIdentifier].riskLink.linking = {
  //       ...draft.content[wsIdentifier].riskLink.linking,
  //       rdm: {
  //         data: Object.assign({}, ...rdm.map(item => ({[item.id]: {...item}}))),
  //         selected: null
  //       },
  //       edm: Object.assign({}, ...edm.map(item => ({[item.id]: {...item}}))),
  //       analysis: {
  //         data: Object.assign({}, ...analysis.map(dt => ({[dt.id]: {...dt, selected: false}}))),
  //         allChecked: false, indeterminate: false, numberOfElement: analysis.length
  //       },
  //       portfolio: {
  //         data: Object.assign({}, ...portfolios.map(dt => ({[dt.id]: {...dt, selected: false}}))),
  //         allChecked: false, indeterminate: false, numberOfElement: portfolios.length
  //       }
  //     };
  //   }));
  // }

  // loadFacData(ctx: StateContext<WorkspaceModel>) {
  //   const state = ctx.getState();
  //   const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
  //   return this.riskApi.searchFacData().pipe(
  //     mergeMap(
  //       (dt: any) => {
  //         const dataModel = dt.map(item => {
  //           return {
  //             id: item.databaseId,
  //             name: item.name,
  //             createDt: '19/01/2018 19:01:50',
  //             type: this._getTypeData(item.name) ? 'rdm' : 'edm',
  //             versionNum: 17,
  //             selected: item.name === 'SBS_RR_2019_08_MELANIE_E' || item.name === 'SBS_RR_2019_08_MELANIE_R',
  //             typeWs: 'fac',
  //             source: ''
  //           };
  //         });
  //         let selectedData = {
  //           rdm: Object.assign({},
  //             ..._.filter(dataModel, item => item.type === 'rdm' && item.selected).map((ds: any) =>
  //               ({[ds.id]: {...ds, selected: false, scanned: true}}))),
  //           edm: Object.assign({},
  //             ..._.filter(dataModel, item => item.type === 'edm' && item.selected).map((ds: any) =>
  //               ({[ds.id]: {...ds, selected: false, scanned: true}})))
  //         };
  //         if (!state.content[wsIdentifier].riskLink.synchronize) {
  //           selectedData = _.get(state.savedData.riskLink.edmrdmSelection, `${wsIdentifier}`, selectedData);
  //         }
  //         ctx.dispatch([
  //             new LoadPortfolioFacAction(_.toArray(selectedData.edm)),
  //             new PatchRiskLinkDisplayAction({key: 'displayListRDMEDM', value: true})
  //           ]
  //         );
  //         return of(ctx.patchState(produce(
  //           ctx.getState(), draft => {
  //             draft.content[wsIdentifier].riskLink.listEdmRdm = {
  //               ...draft.content[wsIdentifier].riskLink.listEdmRdm,
  //               data: _.merge({},
  //                 draft.content[wsIdentifier].riskLink.listEdmRdm.data,
  //                 Object.assign({}, ...dataModel.map(ds => ({[ds.id]: {...ds}})))
  //               ),
  //               selectedListEDMAndRDM: selectedData,
  //               totalNumberElement: draft.content[wsIdentifier].riskLink.listEdmRdm.totalNumberElement + dataModel.length,
  //               numberOfElement: draft.content[wsIdentifier].riskLink.listEdmRdm.numberOfElement + dataModel.length
  //             };
  //             draft.content[wsIdentifier].riskLink.synchronize = false;
  //           }))
  //         );
  //       }
  //     )
  //   );
  // }

  loadDivisionSelection(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const {wsIdentifier} = state.currentTab;
    ctx.patchState(produce(ctx.getState(), draft => {
      const currentSelection = _.get(state.content[wsIdentifier].riskLink.selection, 'currentDataSource', null);
      const currentDivision = state.content[wsIdentifier].riskLink.financialValidator.division.selected.divisionNumber;
      if (currentSelection !== null) {
        if (state.content[wsIdentifier].riskLink.selectedEDMOrRDM === 'RDM') {
          // console.log(_.get(state.content[wsIdentifier].riskLink.facSelection, currentDivision, {}));
          draft.content[wsIdentifier].riskLink.analysis = this._facDataFactor(state.content[wsIdentifier].riskLink.analysis,
              state.content[wsIdentifier].riskLink.facSelection[currentDivision].analysis, currentSelection, 'RDM'
          );
        } else {
          draft.content[wsIdentifier].riskLink.portfolios = this._facDataFactor(state.content[wsIdentifier].riskLink.portfolios,
              state.content[wsIdentifier].riskLink.facSelection[currentDivision].portfolios, currentSelection, 'EDM'
          );
        }
      }
    }))
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
    const {rmsId, type, projectId, instanceId} = payload;
    return this.riskApi.loadDataSourceContent(instanceId, projectId, rmsId, type)
      .pipe(mergeMap(data => {
          ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
            draft.content[wsIdentifier].riskLink.selectedEDMOrRDM = type;
            draft.content[wsIdentifier].riskLink.display.displayTable = true;
            const {analysis, portfolios} = draft.content[wsIdentifier].riskLink.selection;
            if (type == 'EDM') {
              draft.content[wsIdentifier].riskLink.portfolios = _.map(data, (item: any) => ({
                ...item,
                selected: (portfolios[rmsId] && portfolios[rmsId][item.rlPortfolioId]) != null
              }));
            } else if (type == 'RDM') {
              draft.content[wsIdentifier].riskLink.analysis = _.map(data, (item: any) => ({
                ...item,
                selected: (analysis[rmsId] && analysis[rmsId][item.rlAnalysisId]) != null
              }));
            }
            const selection = draft.content[wsIdentifier].riskLink.selection;
            draft.content[wsIdentifier].riskLink.selection = {
              ...selection,
              currentDataSource: rmsId
            };
          }));
          return of(data);
        }),
        catchError(err => {
          console.error("error while loading Datasource Content ", err);
          return of(err);
        })
      );
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
    const listSelected = {
      edm: state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.edm,
      rdm: state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.rdm
    };
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
    console.log('select Risk Link analysis');
    // const state = ctx.getState();
    // const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    // const listDataToArray = _.toArray(state.content[wsIdentifier].riskLink.listEdmRdm.data);
    // const listSelected = {edm: {}, rdm: {}};
    // ctx.patchState(produce(ctx.getState(), draft => {
    //   const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
    //   const dataSources= draft.content[wsIdentifier].riskLink.listEdmRdm.data;
    //
    // });
    // _.forEach(listDataToArray, dt => {
    //   if (dt.selected && dt.type === 'edm') {
    //     listSelected.edm = _.merge(listSelected.edm, {
    //       [dt.id]: {
    //         ...dt,
    //         scanned: true,
    //         selected: false,
    //       }
    //     });
    //   } else if (dt.selected && dt.type === 'rdm') {
    //     listSelected.rdm = _.merge(listSelected.rdm, {
    //       [dt.id]: {
    //         ...dt,
    //         scanned: true,
    //         selected: false,
    //       }
    //     });
    //   }
    // });
    // const filteredArray = listDataToArray.filter(e => e.source === 'link');
    // let count = 0;
    // if (filteredArray.length > 0) {
    //   forkJoin(
    //     filteredArray.map(dt => {
    //       const searchTerm = dt.name.substr(0, dt.name.lastIndexOf('_'));
    //       if (dt.name.length - searchTerm.length < 5) {
    //         return of([this.riskApi.searchRiskLinkData(), searchTerm]);
    //       }
    //       return of(null);
    //     })
    //   ).subscribe(data => {
    //     data.forEach(dt => {
    //       count = count + 1;
    //       if (dt !== null) {
    //         dt[0].subscribe(
    //           ks => {
    //             ks.content.forEach(ws => {
    //               const trim = ws.name.substr(0, ws.name.lastIndexOf('_'));
    //               if (trim === dt[1]) {
    //                 if (ws.type !== dt.type) {
    //                   if (ws.type === 'edm') {
    //                     listSelected.edm = _.merge({}, listSelected.edm, {
    //                       [ws.id]: {
    //                         ...ws,
    //                         typeWs: 'treaty',
    //                         scanned: true,
    //                         selected: false
    //                       }
    //                     });
    //                   } else {
    //                     listSelected.rdm = _.merge({}, listSelected.rdm, {
    //                       [ws.id]: {
    //                         ...ws,
    //                         typeWs: 'treaty',
    //                         scanned: true,
    //                         selected: false
    //                       }
    //                     });
    //                   }
    //                 }
    //               }
    //             });
    //             const mergedEDM = _.merge({}, listSelected.edm,
    //               state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.edm);
    //             const mergedRDM = _.merge({}, listSelected.rdm,
    //               state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.rdm);
    //             console.log(mergedEDM, mergedRDM);
    //             const filteredFacEDMRDM = _.filter([..._.toArray(mergedEDM), ..._.toArray(mergedRDM)], item => item.typeWs === 'fac');
    //             if (count === data.length) {
    //               ctx.patchState(produce(ctx.getState(), draft => {
    //                 draft.content[wsIdentifier].riskLink.listEdmRdm = {
    //                   ...draft.content[wsIdentifier].riskLink.listEdmRdm,
    //                   selectedListEDMAndRDM: {
    //                     edm: mergedEDM,
    //                     rdm: mergedRDM
    //                   },
    //                   data: this._update(draft.content[wsIdentifier].riskLink.listEdmRdm.data, listSelected)
    //                 };
    //                 draft.content[wsIdentifier].riskLink.financialPerspective = {
    //                   ...draft.content[wsIdentifier].riskLink.financialPerspective,
    //                   rdm: {data: mergedRDM, selected: null},
    //                 };
    //               }));
    //               // ctx.dispatch(new fromWs.PatchRiskLinkDisplayAction({key: 'displayTable', value: false}));
    //               // ctx.dispatch(new fromWs.LoadPortfolioForLinkingAction(_.toArray(mergedEDM)[0]));
    //               ctx.dispatch(new fromWs.LoadRiskLinkAnalysisDataAction(_.filter(_.toArray(mergedRDM), item => item.typeWs === 'treaty')));
    //               ctx.dispatch(new fromWs.LoadRiskLinkPortfolioDataAction(_.filter(_.toArray(mergedEDM), item => item.typeWs === 'treaty')));
    //               ctx.dispatch(new fromWs.SelectFacRiskLinkEDMAndRDMAction(filteredFacEDMRDM));
    //             }
    //           });
    //       }
    //     });
    //   });
    // } else {
    //   const mergedEDM = _.merge({}, listSelected.edm,
    //     state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.edm);
    //   const mergedRDM = _.merge({}, listSelected.rdm,
    //     state.content[wsIdentifier].riskLink.listEdmRdm.selectedListEDMAndRDM.rdm);
    //   const filteredFacEDMRDM = _.filter([..._.toArray(mergedEDM), ..._.toArray(mergedRDM)], item => item.typeWs === 'fac');
    //   ctx.patchState(produce(ctx.getState(), draft => {
    //       draft.content[wsIdentifier].riskLink.listEdmRdm = {
    //         ...draft.content[wsIdentifier].riskLink.listEdmRdm,
    //         selectedListEDMAndRDM: {
    //           edm: listSelected.edm,
    //           rdm: listSelected.rdm
    //         }
    //       };
    //       draft.content[wsIdentifier].riskLink.financialPerspective = {
    //         ...draft.content[wsIdentifier].riskLink.financialPerspective,
    //         rdm: {data: listSelected.rdm, selected: null},
    //       };
    //     }
    //     )
    //   );
    //   // ctx.dispatch(new fromWs.LoadPortfolioForLinkingAction(_.toArray(mergedEDM)[0]));
    //   ctx.dispatch(new fromWs.LoadRiskLinkAnalysisDataAction(_.filter(_.toArray(mergedRDM), item => item.typeWs === 'treaty')));
    //   ctx.dispatch(new fromWs.LoadRiskLinkPortfolioDataAction(_.filter(_.toArray(mergedEDM), item => item.typeWs === 'treaty')));
    //   ctx.dispatch(new fromWs.SelectFacRiskLinkEDMAndRDMAction(filteredFacEDMRDM));
    // }
  }

  /** SEARCH WITH KEYWORD OR PAGE OF EDM AND RDM */
  searchRiskLinkEDMAndRDM(ctx: StateContext<WorkspaceModel>, payload) {
    const {keyword, offset, size, instanceId} = payload;

    return this.riskApi.searchRiskLinkData(instanceId, keyword, offset, size).pipe(
      mergeMap(
        (ds: any) => {
          ctx.patchState(produce(ctx.getState(), draft => {
            console.log('this is ds', ds);
            const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
            const {riskLink} = draft.content[wsIdentifier];
            const selectedDataSources = [..._.keys(riskLink.selection.edms), ..._.keys(riskLink.selection.rdms)];
            const {content, numberOfElement, totalElements} = ds;
            draft.content[wsIdentifier].riskLink.listEdmRdm.data = _.merge({},
              ...content.map(item => ({
                [item.rmsId]: {
                  ...item,
                  selected: !!_.find(selectedDataSources, d => d == item.rmsId),
                }
              }))
            );
            draft.content[wsIdentifier].riskLink.listEdmRdm.numberOfElement = numberOfElement;
            draft.content[wsIdentifier].riskLink.listEdmRdm.totalElements = totalElements;
          }));
          return of(ds);
        })
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
  loadRiskLinkData(ctx: StateContext<WorkspaceModel>, payload) {
    const {type, carId} = payload;
    return this.riskApi.loadImportRefData(carId)
      .pipe(
        mergeMap(
          (refData: any) => {
            console.log(refData);
            return of(ctx.patchState(
              produce(
                ctx.getState(), draft => {
                  const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
                  draft.content[wsIdentifier].riskLink = {
                    ...draft.content[wsIdentifier].riskLink,
                    type,
                    listEdmRdm: {
                      ...draft.content[wsIdentifier].riskLink.listEdmRdm,
                      data: {},
                      searchValue: '',
                      totalNumberElement: 0,
                      numberOfElement: 0
                    },
                    selection: {
                      edms: {},
                      rdms: {},
                      analysis: {},
                      portfolios: {}
                    },
                    // summary: testSummary2,
                    summary: {
                      analysis: [],
                      portfolios: [],
                      sourceEpHeaders: []
                    },
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
                        data: refData.rmsInstances,
                        selected: refData.rmsInstances[0]
                      },
                      financialPerspectiveELT: {
                        data: refData.financialPerspectives,
                        selected: refData.financialPerspectives[0]
                      },
                      targetCurrency: {
                        data: refData.currencies,
                        selected: refData.currencies[0]
                      },
                      division: {data: refData.division, selected: ! _.isEmpty(refData.division) ? refData.division[0] : null },
                    },
                    facSelection: refData.division === null ? {} : Object.assign({}, ...refData.division.map(item => ({[item.divisionNumber]:  {analysis: {}, portfolios: {}}})))
                  }
                })
            ));
          }
        ),
        // mergeMap(dt => of(ctx.dispatch(new fromWs.SynchronizeEDMAndRDMSelectionAction())))
      );
  }


  private groupAnalysisPortfolioByDivision(riskLinkState) {
    const divisions = _.keys(riskLinkState.facSelection);
    let result = {analysis: {}, portfolios: {}};
    console.log('TOTO', {divisions, facSelection: riskLinkState.facSelection})
    _.forEach(divisions, division => {
      const analysis = _.flatMap(_.toArray(riskLinkState.facSelection[division].analysis), item => _.values(item));
      const portfolios = _.flatMap(_.toArray(riskLinkState.facSelection[division].portfolios), item => _.values(item));
      console.log("this is analysis and portfolios", {analysis, portfolios})
      _.forEach(analysis, a => {
        if (result.analysis[a.rlAnalysisId]) {
          result.analysis[a.rlAnalysisId].divisions.push(division);
        } else {
          result.analysis[a.rlAnalysisId] = {...a, divisions: [division]};
        }
      });
      _.forEach(portfolios, p => {
        if (result.portfolios[p.rlPortfolioId]) {
          result.portfolios[p.rlPortfolioId].divisions.push(division);
        } else {
          result.portfolios[p.rlPortfolioId] = {...p, divisions: [division]};
        }
      })
    });
    return result;
  }

  runDetailedScanForTreaty(ctx: StateContext<WorkspaceModel>, payload) {
    const {projectId, analysis, portfolios, instanceId} = payload;
    return this.riskApi.runDetailedScan(instanceId, projectId, analysis, portfolios)
      .pipe(
        mergeMap((res: any) => {
          ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = draft.currentTab.wsIdentifier;
            const {analysis, portfolios} = res;
            const financialPerspective = draft.content[wsIdentifier].riskLink.financialValidator.financialPerspectiveELT.selected.code;
            draft.content[wsIdentifier].riskLink.summary = {
              ...draft.content[wsIdentifier].riskLink.summary,
              analysis: _.map(analysis, item => ({
                ...item,
                selected: false,
                financialPerspectives: [financialPerspective],
                peqt: [],
                targetCurrency: item.analysisCurrency,
                targetRaps: [],
                unitMultiplier: 1,
                proportion: 100,
                occurrenceBasis: null,
                overrideReason: null,
                divisions: []
              })),
              portfolios: _.map(portfolios, p => ({
                ...p,
                selected: false,
                targetCurrency: p.agCurrency,
                unitMultiplier: 1,
                proportion: 100,
                importLocationLevel: false,
                divisions: []
              }))
            };
            draft.content[wsIdentifier].riskLink.display.displayImport = true;
            const rlAnalysisIds = _.map(analysis, a => a.rlAnalysisId);
            ctx.dispatch(new fromWs.LoadRegionPerilForAnalysis({rlAnalysisIds}));
          }));
          return of(res);
        })
        , catchError(err => {
          console.error('Error while doing the detailed scan', err);
          return of(err);
        }))
  }

  runDetailedScanForFAC(ctx: StateContext<WorkspaceModel>, payload) {
    const {projectId, analysis, portfolios, instanceId} = payload;
    return this.riskApi.runDetailedScan(instanceId, projectId, analysis, portfolios)
      .pipe(
        mergeMap((res: any) => {
          ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = draft.currentTab.wsIdentifier;
            const currentDivision = draft.content[wsIdentifier].riskLink.financialValidator.division.selected.divisionNumber;
            const {analysis, portfolios} = res;
            const financialPerspective = draft.content[wsIdentifier].riskLink.financialValidator.financialPerspectiveELT.selected.code;
            _.forEach(analysis, a => {
              let index = this.analysisExists(draft.content[wsIdentifier].riskLink.summary.analysis, a);
              if (index == -1) {
                draft.content[wsIdentifier].riskLink.summary.analysis.push(({
                  ...a,
                  selected: false,
                  financialPerspectives: [financialPerspective],
                  peqt: [],
                  targetCurrency: a.analysisCurrency,
                  targetRaps: [],
                  unitMultiplier: 1,
                  proportion: 100,
                  occurrenceBasis: null,
                  overrideReason: null,
                  divisions: [currentDivision]
                }));
              } else {
                draft.content[wsIdentifier].riskLink.summary.analysis[index].divisions.push(currentDivision);
                draft.content[wsIdentifier].riskLink.summary.analysis[index].divisions = _.uniq(
                  draft.content[wsIdentifier].riskLink.summary.analysis[index].divisions
                );
              }
            });

            _.forEach(portfolios, p => {
              let index = this.portfolioExists(draft.content[wsIdentifier].riskLink.summary.portfolios, p);
              if (index == -1) {
                draft.content[wsIdentifier].riskLink.summary.portfolios.push(({
                  ...p,
                  selected: false,
                  targetCurrency: p.agCurrency,
                  unitMultiplier: 1,
                  proportion: 100,
                  importLocationLevel: false,
                  divisions: [currentDivision]
                }));
              } else {
                draft.content[wsIdentifier].riskLink.summary.portfolios[index].divisions.push(currentDivision);
                draft.content[wsIdentifier].riskLink.summary.portfolios[index].divisions = _.uniq(
                  draft.content[wsIdentifier].riskLink.summary.portfolios[index].divisions
                );
              }
            });
            draft.content[wsIdentifier].riskLink.display.displayImport = true;
            const rlAnalysisIds = _.map(draft.content[wsIdentifier].riskLink.summary.analysis, a => a.rlAnalysisId);
            ctx.dispatch(new fromWs.LoadRegionPerilForAnalysis({rlAnalysisIds}));
          }));
          return of(res);
        })
        , catchError(err => {
          console.error('Error while doing the detailed scan', err);
          return of(err);
        }))
  }

  private portfolioExists(portfolioList, p) {
    return _.findIndex(portfolioList, (item: any) => item.rlPortfolioId == p.rlPortfolioId);
  }

  private analysisExists(analysisList, a) {
    return _.findIndex(analysisList, (item: any) => item.rlAnalysisId == a.rlAnalysisId);
  }

  loadAnalysisRegionPerils(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {rlAnalysisIds} = payload;
    return this.riskApi.loadAnalysisRegionPerils(rlAnalysisIds)
      .pipe(mergeMap(result => {
          ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = draft.currentTab.wsIdentifier;
            draft.content[wsIdentifier].riskLink.summary = {
              ...draft.content[wsIdentifier].riskLink.summary,
              regionPerils: result
            };
          }));
          return of(result);
        }),
        catchError(err => of(err))
      );
  }

  patchAnalysisResult(ctx, payload) {
    const {index, key, value} = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      const wsIdentifier = draft.currentTab.wsIdentifier;
      draft.content[wsIdentifier].riskLink.summary.analysis[index][key] = value;
    }))
  }

  patchPortfolioResult(ctx, payload) {
    const {index, key, value} = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      const wsIdentifier = draft.currentTab.wsIdentifier;
      draft.content[wsIdentifier].riskLink.summary.portfolios[index][key] = value;
    }))
  }

  triggerImport(ctx, payload) {
    const {projectId, instanceId, userId, analysisConfig, portfolioConfig} = payload;
    return this.riskApi.triggerImport(instanceId, projectId, userId, analysisConfig, portfolioConfig)
      .pipe(mergeMap(res => {
          console.log('Import done', res);
          alert('Import done successfully');
          return of(res);
        }),
        catchError(err => {
          alert('Error while doing the import');
          return of(err);
        })
      );
  }

  // private _facTraitement(ctx): boolean {
  //   const state = ctx.getState();
  //   const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
  //   return state.content[wsIdentifier].workspaceType === 'fac';
  // }

  private _facDataFactor(data, selection, id, scope) {
    if(_.includes(_.keys(selection), `${id}`)) {
        if (scope === 'RDM') {
            return _.map(data, item => {return {...item, selected: _.includes(_.keys(selection[id]), `${item.rlAnalysisId}`)}});
        } else {
            return _.map(data, item => {return {...item, selected: _.includes(_.keys(selection[id]), `${item.rlPortfolioId}`)}});
        }
    }
    return _.map(data, item => {return {...item, selected: false}});
  }

  private _update(source, list) {
    const mergedList = [..._.toArray(list.edm), ..._.toArray(list.rdm)].map(item => item.id);
    const newdata = _.toArray(source).map(item => {
      const exist = _.includes(mergedList, item.id);
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

  overrideAnalysisRegionPeril(ctx: StateContext<WorkspaceModel>, payload: any) {
    ctx.patchState(produce(ctx.getState(), draft => {
      const wsIdentifier = draft.currentTab.wsIdentifier;
      _.forEach(payload, (item, index) => {
        const analysis = draft.content[wsIdentifier].riskLink.summary.analysis[index];
        draft.content[wsIdentifier].riskLink.summary.analysis[index] = _.merge({}, analysis, item);
      });
    }))
  }

  overrideFinancialPerspective(ctx: StateContext<WorkspaceModel>, payload: any) {
    ctx.patchState(produce(ctx.getState(), draft => {
      const wsIdentifier = draft.currentTab.wsIdentifier;
      _.forEach(payload, (item, index) => {
        const analysis = draft.content[wsIdentifier].riskLink.summary.analysis[index];
        draft.content[wsIdentifier].riskLink.summary.analysis[index].financialPerspectives = item.financialPerspectives;
      });
    }))
  }

  loadSourceEpCurveHeaders(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {rlAnalysisId} = payload;
    return this.riskApi.loadSourceEpCurveHeaders(rlAnalysisId)
      .pipe(mergeMap(result => {
          ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = draft.currentTab.wsIdentifier;
            draft.content[wsIdentifier].riskLink.summary.sourceEpHeaders = result;
          }));
          return result;
        }),
        catchError(err => of(err))
      );
  }

  loadTargetRap(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {rlAnalysisId} = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      const wsIdentifier = draft.currentTab.wsIdentifier;
      draft.content[wsIdentifier].riskLink.summary.targetRaps = [];
    }));
    return this.riskApi.loadTargetRap(rlAnalysisId)
      .pipe(mergeMap(result => {
          ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = draft.currentTab.wsIdentifier;
            draft.content[wsIdentifier].riskLink.summary.targetRaps = result;
          }));
          return result;
        }),
        catchError(err => of(err))
      );
  }

  overrideTargetRaps(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {changes} = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      const wsIdentifier = draft.currentTab.wsIdentifier;
      _.forEach(
        draft.content[wsIdentifier].riskLink.summary.analysis,
        (analysis, index) => {
          draft.content[wsIdentifier].riskLink.summary.analysis[index].targetRaps = changes[analysis.rlAnalysisId] || analysis.targetRaps;
        });
    }));
  }

  clearTargetRaps(ctx: StateContext<WorkspaceModel>) {
    return ctx.patchState(produce(ctx.getState(), draft => {
      const wsIdentifier = draft.currentTab.wsIdentifier;
      draft.content[wsIdentifier].riskLink.summary.targetRaps = null;
    }));
  }


  rescanDataSource(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {datasource, projectId, instanceId} = payload;
    return this.riskApi.rescanDataSource(datasource, projectId, instanceId, instanceName)
      .pipe(mergeMap((response: any) => {
          ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = _.get(draft.currentTab, 'wsIdentifier', null);
            if (datasource.type == 'EDM') {
              draft.content[wsIdentifier].riskLink.selection.edms[datasource.rmsId] = {...datasource, ...response, scanning: false};
            } else if (datasource.type == 'RDM') {
              draft.content[wsIdentifier].riskLink.selection.rdms[datasource.rmsId] = {...datasource, ...response, scanning: false};
            }
          }));
          return of(response);
        }),
        catchError(err => {
          return of(err);
        }));
  }

  overrideOccurrenceBasis(ctx: StateContext<WorkspaceModel>, payload: any) {
    const {occurrenceBasis, analysisIndex} = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      const wsIdentifier = _.get(draft.currentTab, 'wsIdentifier', null);
      if (occurrenceBasis.scopeOfOverride == 'all') {
        draft.content[wsIdentifier].riskLink.summary.analysis =
          _.map(draft.content[wsIdentifier].riskLink.summary.analysis,
            item => ({
              ...item,
              occurrenceBasis: occurrenceBasis.occurrenceBasis,
              occurrenceBasisOverrideReason: occurrenceBasis.occurrenceBasisOverrideReason
            }));
      } else if (occurrenceBasis.scopeOfOverride == 'current') {
        draft.content[wsIdentifier].riskLink.summary.analysis[analysisIndex] = {
          ...draft.content[wsIdentifier].riskLink.summary.analysis[analysisIndex],
          occurrenceBasis: occurrenceBasis.occurrenceBasis,
          occurrenceBasisOverrideReason: occurrenceBasis.occurrenceBasisOverrideReason
        };
      }

    }));
  }

  loadDefaultDataSources(ctx: StateContext<WorkspaceModel>, payload: any){
    const {projectId, instanceId, userId} = payload;
    const state = ctx.getState();
    const wsIdentifier = _.get(state.currentTab, 'wsIdentifier', null);

    return this.riskApi.getDefaultDataSources(instanceId,projectId, userId)
        .pipe(mergeMap((response: any) => {
          if(_.isEmpty(response)) {
            ctx.patchState(produce(ctx.getState(),draft => {
              draft.content[wsIdentifier].riskLink.selection.edms = {};
              draft.content[wsIdentifier].riskLink.selection.rdms = {};
              draft.content[wsIdentifier].riskLink.display.displayListRDMEDM = false;
            }));
            return of(response);
          }
          let selectedDS= [];
          ctx.patchState(produce(ctx.getState(),draft => {
            let data = {edms: {}, rdms: {}};
            _.forEach(response, ds => {
              const value = {rmsId: ds.dataSourceId, name: ds.dataSourceName, type: ds.dataSourceType, scanning: true};
              if (value.type === 'EDM') {
                data.edms[value.rmsId] = value;
               } else if (value.type === 'RDM') {
                data.rdms[value.rmsId] = value;
              }
              selectedDS.push(value);
            });
            draft.content[wsIdentifier].riskLink.display.displayListRDMEDM = true;
            draft.content[wsIdentifier].riskLink.selection.edms = data.edms;
            draft.content[wsIdentifier].riskLink.selection.rdms = data.rdms;
          }));
          ctx.dispatch(new fromWs.BasicScanEDMAndRDMAction({instanceId: instanceId, selectedDS, projectId}));
          return of(response);
        }), catchError(err => of(err)));
  }

  saveDefaultDataSources(ctx: StateContext<WorkspaceModel>, payload: any) {
    let {empty} = payload;
    const state = ctx.getState();
    const {wsIdentifier} = state.currentTab;
    const projectId = _.get(_.find(state.content[wsIdentifier].projects, item => item.selected), 'projectId');
    const {instanceId} = state.content[wsIdentifier].riskLink.financialValidator.rmsInstance.selected;
    const {edms, rdms} = state.content[wsIdentifier].riskLink.selection;
    let dataSources = [];

    if (!empty) {
      dataSources = _.map(_.concat(_.values(edms), _.values(rdms)), item => ({
        dataSourceId: item.rmsId,
        dataSourceName: item.name,
        dataSourceType: item.type
      }));
    }

    return this.riskApi.saveDefaultDataSources(instanceId, projectId, dataSources, 1)
        .pipe( catchError(err => {
          if(err.status != 200)
            ctx.dispatch(new fromWs.SaveDefaultDataSourcesErrorAction(err));
          return of(err);
        }),
        mergeMap((response: any) => {
          if(_.isEmpty(dataSources)){
            ctx.patchState(produce(ctx.getState(), draft => {
              draft.content[wsIdentifier].riskLink.selection.analysis=[];
              draft.content[wsIdentifier].riskLink.selection.portfolioa=[];
              draft.content[wsIdentifier].riskLink.selection.edms={};
              draft.content[wsIdentifier].riskLink.selection.rdms={};
              draft.content[wsIdentifier].riskLink.display.displayListRDMEDM=false;
            }));
            ctx.dispatch(new fromWs.ClearDefaultDataSourcesSuccessAction(response));
          } else
            ctx.dispatch(new fromWs.SaveDefaultDataSourcesSuccessAction(response));
          return of();
        })
    );
  }

}
