import {StateContext} from '@ngxs/store';
import * as fromWs from '../store/actions';
import {
    LoadBasicAnalysisFacPerDivisionAction, LoadDivisionSelection,
    SaveDivisionSelection

} from '../store/actions';
import * as _ from 'lodash';
import {catchError, count, mergeMap, switchMap} from 'rxjs/operators';
import {of} from 'rxjs/internal/observable/of';
import {RiskApi} from './api/risk.api';
import {Injectable} from '@angular/core';
import {WorkspaceModel} from '../model';
import produce from 'immer';
import {RiskLink} from "../model/risk-link.model";
import {RLAnalysisFilter} from "../model/rl-analysis-filter.model";
import {RlPortfolioFilter} from "../model/rl-portfolio-filter.model";


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
            const data = [...state.content[wsIdentifier].riskLink.analysis.data, ...state.content[wsIdentifier].riskLink.portfolios.data];
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
        let analysis = state.content[wsIdentifier].riskLink.analysis.data;
        let portfolio = state.content[wsIdentifier].riskLink.portfolios.data;
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
                        draft.content[wsIdentifier].riskLink.selection.edms[key] = {...selectedItem, scanning: true, instanceId: payload.instanceId}
                    } else if (selectedItem.type == 'RDM') {
                        draft.content[wsIdentifier].riskLink.selection.rdms[key] = {...selectedItem, scanning: true, instanceId: payload.instanceId}
                    }
                });
        }));
        ctx.dispatch(new fromWs.BasicScanEDMAndRDMAction(payload))
    }

    toggleRiskLinkAnalysis(ctx: StateContext<WorkspaceModel>, payload) {
        const {action, rlAnalysisId, data} = payload;
        ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
            switch (action) {
                case 'selectOne':
                    const {analysisIndex, targetAnalysis} = this.findAnalysis(draft.content[wsIdentifier].riskLink, rlAnalysisId);
                    draft.content[wsIdentifier].riskLink.analysis.data[analysisIndex].selected = !targetAnalysis.selected;
                    const rdmId = draft.content[wsIdentifier].riskLink.selection.currentDataSource;
                    if (targetAnalysis.selected) {
                        draft.content[wsIdentifier].riskLink.selection.analysis[rdmId] =
                            _.merge(draft.content[wsIdentifier].riskLink.selection.analysis[rdmId], {[rlAnalysisId]: targetAnalysis})
                    } else {
                        if (draft.content[wsIdentifier].riskLink.selection.analysis[rdmId])
                            draft.content[wsIdentifier].riskLink.selection.analysis[rdmId] =
                                _.omit(draft.content[wsIdentifier].riskLink.selection.analysis[rdmId], [rlAnalysisId])
                    }
                    break;

                case 'unSelectChunk':
                    _.forEach(data, id => {
                        const {analysisIndex} = this.findAnalysis(draft.content[wsIdentifier].riskLink, id);
                        draft.content[wsIdentifier].riskLink.analysis.data[analysisIndex].selected = false;
                        const rdmId = draft.content[wsIdentifier].riskLink.selection.currentDataSource;
                        draft.content[wsIdentifier].riskLink.selection.analysis[rdmId] =
                            _.omit(draft.content[wsIdentifier].riskLink.selection.analysis[rdmId], [id])
                    });
                    break;

                case 'selectChunk':
                    _.forEach(data, id => {
                        const {analysisIndex, targetAnalysis} = this.findAnalysis(draft.content[wsIdentifier].riskLink, id);
                        draft.content[wsIdentifier].riskLink.analysis.data[analysisIndex].selected = true;
                        const rdmId = draft.content[wsIdentifier].riskLink.selection.currentDataSource;
                        draft.content[wsIdentifier].riskLink.selection.analysis[rdmId] =
                            _.merge(draft.content[wsIdentifier].riskLink.selection.analysis[rdmId], {[id]: targetAnalysis})
                    });
                    break;
            }
        }));
    }

    toggleRiskLinkPortfolio(ctx: StateContext<WorkspaceModel>, payload) {
        const {action, rlPortfolioId, data} = payload;
        ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
            switch (action) {
                case 'selectOne':
                    const {portfolioIndex, targetPortfolio} = this.findPortfolio(draft.content[wsIdentifier].riskLink, rlPortfolioId);
                    draft.content[wsIdentifier].riskLink.portfolios.data[portfolioIndex].selected = !targetPortfolio.selected;
                    const edmId = draft.content[wsIdentifier].riskLink.selection.currentDataSource;
                    if (draft.content[wsIdentifier].riskLink.portfolios.data[portfolioIndex].selected) {
                        draft.content[wsIdentifier].riskLink.selection.portfolios[edmId] =
                            _.merge(draft.content[wsIdentifier].riskLink.selection.portfolios[edmId], {[rlPortfolioId]: targetPortfolio});
                    } else {
                        if (draft.content[wsIdentifier].riskLink.selection.portfolios[edmId]) {
                            draft.content[wsIdentifier].riskLink.selection.portfolios[edmId] =
                                _.omit(draft.content[wsIdentifier].riskLink.selection.portfolios[edmId], [rlPortfolioId]);
                        }
                    }
                    break;

                case 'unSelectChunk':
                    _.forEach(data, id => {
                        const {portfolioIndex} = this.findPortfolio(draft.content[wsIdentifier].riskLink, id);
                        draft.content[wsIdentifier].riskLink.portfolios.data[portfolioIndex].selected = false;
                        const edmId = draft.content[wsIdentifier].riskLink.selection.currentDataSource;
                        draft.content[wsIdentifier].riskLink.selection.portfolios[edmId] =
                            _.omit(draft.content[wsIdentifier].riskLink.selection.portfolios[edmId], [id])
                    });
                    break;

                case 'selectChunk':
                    _.forEach(data, id => {
                        const {portfolioIndex, targetPortfolio} = this.findPortfolio(draft.content[wsIdentifier].riskLink, id);
                        draft.content[wsIdentifier].riskLink.portfolios.data[portfolioIndex].selected = true;
                        const edmId = draft.content[wsIdentifier].riskLink.selection.currentDataSource;
                        draft.content[wsIdentifier].riskLink.selection.portfolios[edmId] =
                            _.merge(draft.content[wsIdentifier].riskLink.selection.portfolios[edmId], {[id]: targetPortfolio});
                    });
                    break;
            }
        }));
    }

    private findAnalysis(riskLinkState, rlAnalysisId): { targetAnalysis, analysisIndex } {
        const analysisIndex = _.findIndex(riskLinkState.analysis.data, (item: any) => item.rlAnalysisId == rlAnalysisId);
        return {
            analysisIndex,
            targetAnalysis: riskLinkState.analysis.data[analysisIndex]
        };
    }

    private findPortfolio(riskLinkState, rlPortfolioId): { targetPortfolio, portfolioIndex } {
        const portfolioIndex = _.findIndex(riskLinkState.portfolios.data, (item: any) => item.rlPortfolioId == rlPortfolioId);
        return {
            portfolioIndex,
            targetPortfolio: riskLinkState.portfolios.data[portfolioIndex]
        };
    }

    applyRegionPeril(ctx: StateContext<WorkspaceModel>, payload) {
        const state = ctx.getState();
        const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
        const {regionPeril, override, id} = payload.row;
        const {scope, value} = payload;
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
                        draft.content[wsIdentifier].riskLink.selection.edms = {
                            ...draft.content[wsIdentifier].riskLink.selection.edms, ...Object.assign(
                                {}, ..._.map(edms, item =>
                                    ({
                                        [item.rmsId]: {
                                            ...item,
                                            scanning: false,
                                            count: _.get(parsedResponse.edms, `${item.rmsId}.count`, 0),
                                            rlModelDataSourceId: _.get(parsedResponse.edms, `${item.rmsId}.rlModelDataSourceId`, undefined)
                                        }
                                    }))
                            )
                        };
                        draft.content[wsIdentifier].riskLink.selection.rdms = {
                            ...draft.content[wsIdentifier].riskLink.selection.rdms,
                            ...Object.assign({}, ..._.map(rdms, item =>
                                ({
                                    [item.rmsId]: {
                                        ...item,
                                        scanning: false,
                                        count: _.get(parsedResponse.rdms, `${item.rmsId}.count`, 0),
                                        rlModelDataSourceId: _.get(parsedResponse.rdms, `${item.rmsId}.rlModelDataSourceId`, undefined)
                                    }
                                }))
                            )
                        };
                    }));
                    return of(response);
                }),
                catchError((err, response) => {
                    console.error(err, response);
                    return of(err);
                }));
    }

    private _parseBasicScanResponse(response: any[]) {
        const result = {edms: {}, rdms: {}};
        _.forEach(response, (item: any) => {
            if (item.type === 'EDM') {
                result.edms = _.merge(result.edms, {
                    [item.rlId]: {
                        rmsId: item.rlId,
                        count: item.count,
                        rlModelDataSourceId: item.rlModelDataSourceId
                    }
                });
            } else if (item.type === 'RDM') {
                result.rdms = _.merge(result.edms, {
                    [item.rlId]: {
                        rmsId: item.rlId,
                        count: item.count,
                        rlModelDataSourceId: item.rlModelDataSourceId
                    }
                });
            }
        });
        return result;
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


    deleteEdmRdm(ctx: StateContext<WorkspaceModel>, payload) {
        const state = ctx.getState();
        const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
        const {rmsId, target} = payload;
        ctx.patchState(produce(ctx.getState(), draft => {
            if (target === 'RDM') {
                draft.content[wsIdentifier].riskLink.selection.rdms = _.omit(draft.content[wsIdentifier].riskLink.selection.rdms, rmsId);
            } else {
                draft.content[wsIdentifier].riskLink.selection.edms = _.omit(draft.content[wsIdentifier].riskLink.selection.edms, rmsId);
            }
            const dataTable = [..._.toArray(draft.content[wsIdentifier].riskLink.selection.rdms),
                ..._.toArray(draft.content[wsIdentifier].riskLink.selection.edms)];
            draft.content[wsIdentifier].riskLink.display.displayListRDMEDM = dataTable.length > 0;
            if (state.content[wsIdentifier].riskLink.selection.currentDataSource === rmsId) {
                draft.content[wsIdentifier].riskLink.selection.currentDataSource = null;
                draft.content[wsIdentifier].riskLink.display.displayTable = false;
            }
        }));
    }

    loadDivisionSelection(ctx: StateContext<WorkspaceModel>) {
        const state = ctx.getState();
        const {wsIdentifier} = state.currentTab;
        ctx.patchState(produce(ctx.getState(), draft => {
            const currentSelection = _.get(state.content[wsIdentifier].riskLink.selection, 'currentDataSource', null);
            const currentDivision = state.content[wsIdentifier].riskLink.financialValidator.division.selected.divisionNumber;
            if (currentSelection !== null) {
                if (state.content[wsIdentifier].riskLink.selectedEDMOrRDM === 'RDM') {
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

    getRiskLinkAnalysis(ctx: StateContext<WorkspaceModel>, payload) {
        const {rdmId, projectId, instanceId, paginationParams, userId, filter} = payload;
        ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
            draft.content[wsIdentifier].riskLink.analysis.loading = true;
        }));
        return this.riskApi.filterRlAnalysis(paginationParams, instanceId, projectId, rdmId, userId, filter)
            .pipe(mergeMap(({content, number, size, totalElements, last}: any) => {
                    ctx.patchState(produce(ctx.getState(), draft => {
                        const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
                        draft.content[wsIdentifier].riskLink.analysis = {
                            ...draft.content[wsIdentifier].riskLink.analysis,
                            data: _.map(content, item => ({
                                ...item,
                                selected: _.includes(_.keys(draft.content[wsIdentifier].riskLink.selection.analysis[rdmId]), `${item.rlAnalysisId}`)
                            })),
                            page: number,
                            size,
                            total: totalElements,
                            last,
                            loading: false
                        };
                    }));
                    return of(content);
                }),
                catchError(err => of(err)));
    }

    getRiskLinkPortfolios(ctx: StateContext<WorkspaceModel>, payload) {
        const {edmId, projectId, instanceId, paginationParams, userId, filter} = payload;
        ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
            draft.content[wsIdentifier].riskLink.portfolios.loading = true;
        }));
        return this.riskApi.filterRlPortfolios(paginationParams, instanceId, projectId, edmId, userId, filter)
            .pipe(mergeMap(({content, number, size, totalElements, last}: any) => {
                    ctx.patchState(produce(ctx.getState(), draft => {
                        const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
                        draft.content[wsIdentifier].riskLink.portfolios = {
                            ...draft.content[wsIdentifier].riskLink.portfolios,
                            data: _.map(content, item => ({
                                ...item,
                                selected: _.includes(_.keys(draft.content[wsIdentifier].riskLink.selection.portfolios[edmId]), `${item.rlPortfolioId}`)
                            })),
                            page: number,
                            size: size,
                            total: totalElements,
                            last,
                            loading: false
                        };
                    }));
                    return of(content);
                }),
                catchError(err => of(err)));

    }

    toggleRiskLinkEDMAndRDMSelected(ctx: StateContext<WorkspaceModel>, payload) {
        const {rmsId, type, projectId, instanceId} = payload;
        ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
            draft.content[wsIdentifier].riskLink.selectedEDMOrRDM = type;
            draft.content[wsIdentifier].riskLink.display.displayTable = true;
            draft.content[wsIdentifier].riskLink.selection = {
                ...draft.content[wsIdentifier].riskLink.selection,
                currentDataSource: rmsId
            };
            draft.content[wsIdentifier].riskLink.financialValidator.rmsInstance.selected= _.find(draft.content[wsIdentifier].riskLink.financialValidator.rmsInstance.data, item => item.instanceId == instanceId);
        }));

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

    /** SEARCH WITH KEYWORD OR PAGE OF EDM AND RDM */
    searchRiskLinkEDMAndRDM(ctx: StateContext<WorkspaceModel>, payload) {
        const {keyword, offset, size, instanceId} = payload;

        return this.riskApi.searchRiskLinkData(instanceId, keyword, offset, size).pipe(
            mergeMap(
                ({content, numberOfElement, totalElements, last}: any) => {
                    ctx.patchState(produce(ctx.getState(), draft => {
                        const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
                        const {riskLink} = draft.content[wsIdentifier];
                        const selectedDataSources = [..._.keys(riskLink.selection.edms), ..._.keys(riskLink.selection.rdms)];
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
                        draft.content[wsIdentifier].riskLink.listEdmRdm.last = last;
                    }));
                    return of(content);
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
                        return of(ctx.patchState(
                            produce(ctx.getState(), draft => {
                                const wsIdentifier = _.get(draft, 'currentTab.wsIdentifier');
                                let riskLinkContext: RiskLink = draft.content[wsIdentifier].riskLink;
                                riskLinkContext.setRefData(refData);
                                riskLinkContext.setType(type);
                                draft.content[wsIdentifier].riskLink = _.merge({}, riskLinkContext);
                            })
                        ));
                    }
                ),
                catchError(err => of(err))
            );
    }

    addToImportBasket(ctx: StateContext<WorkspaceModel>, payload) {
        const {analysis, portfolios } = payload;
        ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = draft.currentTab.wsIdentifier;
            const financialPerspective = draft.content[wsIdentifier].riskLink.financialValidator.financialPerspectiveELT.selected.code;
            const currentDivision = payload.division || _.get(draft.content[wsIdentifier].riskLink.financialValidator, 'division.selected.divisionNumber', null);
            _.forEach(analysis, a => {
                const targetAnalysis = draft.content[wsIdentifier].riskLink.summary.analysis[a.rlAnalysisId];
                if (targetAnalysis) {
                    draft.content[wsIdentifier].riskLink.summary.analysis[a.rlAnalysisId].isScanning = true;
                    if (currentDivision)
                        draft.content[wsIdentifier].riskLink.summary.analysis[a.rlAnalysisId].divisions = _.uniq([
                            ...targetAnalysis.divisions,
                            currentDivision
                        ]);
                } else {
                    draft.content[wsIdentifier].riskLink.summary.analysis[a.rlAnalysisId] = {
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
                        divisions: currentDivision ? [currentDivision] : [],
                        isScanning: true,
                        rpOccurrenceBasis: ''
                    };
                }
            });
            _.forEach(portfolios, p => {
                const targetPortfolio = draft.content[wsIdentifier].riskLink.summary.portfolios[p.rlPortfolioId];
                if (targetPortfolio) {
                    draft.content[wsIdentifier].riskLink.summary.portfolios[p.rlPortfolioId].isScanning = true;
                    if (currentDivision)
                        draft.content[wsIdentifier].riskLink.summary.portfolios[p.rlPortfolioId].divisions = _.uniq([
                            ...targetPortfolio.divisions,
                            currentDivision
                        ]);
                } else {
                    draft.content[wsIdentifier].riskLink.summary.portfolios[p.rlPortfolioId] = {
                        ...p,
                        selected: false,
                        targetCurrency: p.agCurrency,
                        unitMultiplier: 1,
                        proportion: 100,
                        importLocationLevel: false,
                        divisions: currentDivision ? [currentDivision] : [],
                        isScanning: true
                    };
                }
            });

        }));

    }

    runDetailedScan(ctx: StateContext<WorkspaceModel>, payload) {
        const {projectId, analysis, portfolios, instanceId} = payload;
        return this.riskApi.runDetailedScan(instanceId, projectId, analysis, portfolios)
            .pipe(
                mergeMap((res: any) => {
                    ctx.patchState(produce(ctx.getState(), draft => {
                        const wsIdentifier = draft.currentTab.wsIdentifier;
                        const {analysis, portfolios} = res;
                        _.forEach(analysis, a => {
                            draft.content[wsIdentifier].riskLink.summary.analysis[a.rlAnalysisId].rpCode = a.systemRegionPeril;
                            draft.content[wsIdentifier].riskLink.summary.analysis[a.rlAnalysisId].isScanning = false;
                            draft.content[wsIdentifier].riskLink.summary.analysis[a.rlAnalysisId].referenceTargetRaps= a.referenceTargetRaps;
                            draft.content[wsIdentifier].riskLink.summary.analysis[a.rlAnalysisId].targetRaps= _.filter(a.referenceTargetRaps || [] , item => item.default)
                        });
                        _.forEach(portfolios, p => {
                            draft.content[wsIdentifier].riskLink.summary.portfolios[p.rlPortfolioId].isScanning = false;
                        });
                        const summary = draft.content[wsIdentifier].riskLink.summary;
                        if (_.sum([..._.map(_.toArray(summary.analysis), a => a.isScanning), ..._.map(_.toArray(summary.portfolios), p => p.isScanning)]) == 0) {
                            // Means that the scanning is finished and all scanning status are false
                            draft.content[wsIdentifier].riskLink.display.displayImport = true;
                            const rlAnalysisIds = _.map(_.toArray(summary.analysis), a => a.rlAnalysisId);
                            ctx.dispatch(new fromWs.LoadRegionPerilForAnalysis({rlAnalysisIds}));
                        }
                    }));
                    return of(res);
                })
                , catchError(err => {
                    console.error('Error while doing the detailed scan', err);
                    let state = ctx.getState();
                    const wsIdentifier = state.currentTab.wsIdentifier;
                    const {analysis, portfolios} = state.content[wsIdentifier].riskLink.summary;
                    this.patchScanStatus(ctx, false);
                    return of(err);
                }))
    }

    private patchScanStatus(ctx: StateContext<WorkspaceModel>, value: boolean) {
        ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = draft.currentTab.wsIdentifier;
            _.forEach(_.keys(draft.content[wsIdentifier].riskLink.summary.analysis), analysisId => {
                draft.content[wsIdentifier].riskLink.summary.analysis[analysisId].isScanning = value;
            });
            _.forEach(_.keys(draft.content[wsIdentifier].riskLink.summary.portfolios), portfolioId => {
                draft.content[wsIdentifier].riskLink.summary.portfolios[portfolioId].isScanning = value;
            });
        }));
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

    patchAnalysisResult(ctx: StateContext<WorkspaceModel>, payload: any) {
        const {index, key, value} = payload;
        ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = draft.currentTab.wsIdentifier;
            draft.content[wsIdentifier].riskLink.summary.analysis[index][key] = value;
        }))
    }

    patchPortfolioResult(ctx: StateContext<WorkspaceModel>, payload: any) {
        const {index, key, value} = payload;
        ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = draft.currentTab.wsIdentifier;
            draft.content[wsIdentifier].riskLink.summary.portfolios[index][key] = value;
        }))
    }

    autoAttach(ctx: StateContext<WorkspaceModel>, payload: any) {
        const {divisionsIds, edmIds, rdmIds, wsId, projectId, instanceId} = payload;
        return this.riskApi.getAutoAttach(divisionsIds, edmIds, rdmIds, wsId)
            .pipe(mergeMap((resp) => {
                    let facSelection = {};
                    let allAnalysis= [];
                    let allPortfolios= [];
                    _.forEach(resp, (item: any, division) => {
                        const {rlAnalyses, rlPortfolios} = item;
                        facSelection[division] = {
                            analysis: this.groupByRdmId(rlAnalyses),
                            portfolios: this.groupByEdmId(rlPortfolios)
                        };
                        const analysis= _.map(rlAnalyses, item => ({...item, analysisId: item.rlId}));
                        const portfolios= _.map(rlPortfolios, item => ({
                            ...item,
                            currency: item.agCurrency,
                            portfolioId: item.rlId,
                            portfolioName: item.name,
                            portfolioType: item.type
                        }));
                        ctx.dispatch(new fromWs.AddToImportBasket({
                            analysis,
                            portfolios,
                            division
                        }));
                        allAnalysis.push(...analysis);
                        allPortfolios.push(...portfolios);
                    });
                    ctx.dispatch(new fromWs.RunDetailedScanAction({
                        instanceId,
                        projectId,
                        analysis: _.uniq(allAnalysis),
                        portfolios: _.uniq(allPortfolios)
                    }));

                    ctx.patchState(produce(ctx.getState(), draft => {
                        const wsIdentifier = draft.currentTab.wsIdentifier;
                        const currentDivision = draft.content[wsIdentifier].riskLink.financialValidator.division.selected.divisionNumber;
                        draft.content[wsIdentifier].riskLink.facSelection= _.merge(draft.content[wsIdentifier].riskLink.facSelection, facSelection);
                        draft.content[wsIdentifier].riskLink.selection=_.merge(draft.content[wsIdentifier].riskLink.selection,draft.content[wsIdentifier].riskLink.facSelection[currentDivision]);
                    }));
                    return of(resp);
                }),
                catchError(err => of(err)));

    }

    private groupByRdmId(analyses) {
        let result = {};
        _.forEach(analyses, a => {
            const {rdmId} = a;
            if (result[rdmId]) {
                result[rdmId] = _.merge(result[rdmId], {[a.rlAnalysisId]: {...a}});
            } else {
                result[rdmId] = {[a.rlAnalysisId]: {...a}};
            }
        });
        return result;
    }

    private groupByEdmId(portfolios) {
        let result = {};
        _.forEach(portfolios, p => {
            const {edmId} = p;
            if (result[edmId]) {
                result[edmId] = _.merge(result[edmId], {[p.rlPortfolioId]: {...p}});
            } else {
                result[edmId] = {[p.rlPortfolioId]: {...p}};
            }
        });
        return result;
    }

    triggerImport(ctx, payload) {
        const {projectId, instanceId, userId, analysisConfig, portfolioConfig} = payload;
        return this.riskApi.triggerImport(instanceId, projectId, userId, analysisConfig, portfolioConfig)
            .pipe(mergeMap(res => {
                    alert('Import done successfully');
                    return of(res);
                }),
                catchError(err => {
                    alert('Error while doing the import');
                    return of(err);
                })
            );
    }

    private _facDataFactor(data, selection, id, scope) {
        if (_.includes(_.keys(selection), `${id}`)) {
            if (scope === 'RDM') {
                return _.map(data, item => {
                    return {...item, selected: _.includes(_.keys(selection[id]), `${item.rlAnalysisId}`)}
                });
            } else {
                return _.map(data, item => {
                    return {...item, selected: _.includes(_.keys(selection[id]), `${item.rlPortfolioId}`)}
                });
            }
        }
        return _.map(data, item => {
            return {...item, selected: false}
        });
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

    overrideAnalysisRegionPeril(ctx: StateContext<WorkspaceModel>, payload: any) {
        ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = draft.currentTab.wsIdentifier;
            _.forEach(payload, (item, key) => {
                const analysis = draft.content[wsIdentifier].riskLink.summary.analysis[key];
                draft.content[wsIdentifier].riskLink.summary.analysis[key] = _.merge({}, analysis, item);
            });
        }))
    }

    overrideFinancialPerspective(ctx: StateContext<WorkspaceModel>, payload: any) {
        ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = draft.currentTab.wsIdentifier;
            _.forEach(payload, (item, key) => {
                draft.content[wsIdentifier].riskLink.summary.analysis[key].financialPerspectives = item.financialPerspectives;
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
                (analysis, key) => {
                    draft.content[wsIdentifier].riskLink.summary.analysis[key].targetRaps = changes[analysis.rlAnalysisId] || analysis.targetRaps;
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
                            draft.content[wsIdentifier].riskLink.selection.edms[datasource.rmsId] = {
                                ...datasource, ...response,
                                scanning: false
                            };
                        } else if (datasource.type == 'RDM') {
                            draft.content[wsIdentifier].riskLink.selection.rdms[datasource.rmsId] = {
                                ...datasource, ...response,
                                scanning: false
                            };
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

    loadSummaryOrDefaultDataSources(ctx: StateContext<WorkspaceModel>, payload: any) {
        const {projectId, instanceId, userId} = payload;
        return this.riskApi.getSummaryOrDefaultDataSources(instanceId, projectId, userId)
            .pipe(mergeMap(({isSummary, content}: any) => {
                if (_.size(content) > 0) {
                    if (isSummary) {
                        ctx.dispatch(new fromWs.LoadSummaryAction({content, projectId}));
                    } else {
                        ctx.dispatch(new fromWs.LoadDefaultDataSourcesAction({content, projectId, instanceId}));
                    }
                }
                return of(content);
            }), catchError(err => of(err)))
    }

    loadSummary(ctx: StateContext<WorkspaceModel>, payload: any) {
        const {content, projectId} = payload;
        this.addDataSourcesSelection(ctx, content, false);
        return this.riskApi.getAnalysisPortfoliosByProject(projectId).pipe(
            mergeMap(([analysis, portfolios]: any[]) => {
                ctx.patchState(produce(ctx.getState(), draft => {
                    const wsIdentifier = _.get(draft.currentTab, 'wsIdentifier', null);
                    draft.content[wsIdentifier].riskLink.summary.analysis =
                        _.merge({},
                            ..._.map(analysis, item => ({
                                [item.rlAnalysisId]: {
                                    ...item,
                                    peqt: item.targetRAPCodes,
                                    targetRaps: item.targetRAPCodes,
                                    selected: false,
                                    occurrenceBasis: null,
                                    overrideReason: null,
                                    rpOccurrenceBasis: null,
                                    isScanning: false,
                                    rpCode: item.targetRegionPeril,
                                    analysisCurrency: item.sourceCurrency
                                }
                            }))
                        );
                    draft.content[wsIdentifier].riskLink.summary.portfolios = _.merge({},
                        ..._.map(portfolios, item => ({
                            [item.rlPortfolioId]: {
                                ...item,
                                isScanning: false,
                                name: item.portfolioName,
                                number: item.portfolioNumber,
                                agCurrency: item.sourceCurrency
                            }
                        }))
                    );
                    _.forEach(content, ({dataSourceId, dataSourceName, dataSourceType, rlModelIdList}) => {
                        if (dataSourceType == 'RDM') {
                            draft.content[wsIdentifier].riskLink.selection.analysis[dataSourceId] = _.merge({},
                                ..._.map(rlModelIdList, id => ({
                                    [id]: {
                                        ...draft.content[wsIdentifier].riskLink.summary.analysis[id],
                                        rdmId: dataSourceId,
                                        rmdName: dataSourceName
                                    }
                                })))
                        } else if (dataSourceType == 'EDM') {
                            draft.content[wsIdentifier].riskLink.selection.portfolios[dataSourceId] = _.merge({},
                                ..._.map(rlModelIdList, id => ({
                                    [id]: {
                                        ...draft.content[wsIdentifier].riskLink.summary.portfolios[id],
                                        rdmId: dataSourceId,
                                        rmdName: dataSourceName
                                    }
                                })))
                        } else {
                            console.error('Unsupported DataSource Type', dataSourceType);
                        }
                    });
                    if ((analysis.length + portfolios.length) > 0)
                        draft.content[wsIdentifier].riskLink.display.displayImport = true;
                }));
                return of({analysis});
            }),
            catchError(err => of(err))
        )
    }

    loadDefaultDataSources(ctx: StateContext<WorkspaceModel>, payload: any) {
        const {content, projectId, instanceId} = payload;
        this.addDataSourcesSelection(ctx, content);
        ctx.dispatch(new fromWs.DatasourceScanAction({
            instanceId,
            selectedDS: _.map(content, ({dataSourceId, dataSourceType, dataSourceName}) => ({
                name: dataSourceName,
                rmsId: dataSourceId,
                type: dataSourceType
            })),
            projectId
        }));
        return of(content);
    }

    private addDataSourcesSelection(ctx: StateContext<WorkspaceModel>, dataSources, scanning = true) {
        ctx.patchState(produce(ctx.getState(), draft => {
            const wsIdentifier = _.get(draft.currentTab, 'wsIdentifier', null);
            if (!_.isEmpty(dataSources)) {
                let selectedDS = [];
                let data = {edms: {}, rdms: {}};
                _.forEach(dataSources, ds => {
                    const value = {
                        rmsId: ds.dataSourceId,
                        name: ds.dataSourceName,
                        type: ds.dataSourceType,
                        count: ds.modelCount,
                        scanning,
                        instanceId: ds.instanceId
                    };
                    if (value.type === 'EDM') {
                        data.edms[value.rmsId] = value;
                    } else if (value.type === 'RDM') {
                        data.rdms[value.rmsId] = value;
                    }
                    selectedDS.push(value);
                });
                draft.content[wsIdentifier].riskLink.selection.edms = data.edms;
                draft.content[wsIdentifier].riskLink.selection.rdms = data.rdms;
                draft.content[wsIdentifier].riskLink.display.displayListRDMEDM = true;
            }
        }));
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
            .pipe(catchError(err => {
                    if (err.status != 200)
                        ctx.dispatch(new fromWs.SaveDefaultDataSourcesErrorAction(err));
                    return of(err);
                }),
                mergeMap((response: any) => {
                    if (_.isEmpty(dataSources)) {
                        ctx.patchState(produce(ctx.getState(), draft => {
                            draft.content[wsIdentifier].riskLink.selection.analysis = {
                                data: [],
                                page: 0,
                                size: 40,
                                total: null,
                                last: null,
                                filter: new RLAnalysisFilter(),
                                loading: false
                            };
                            draft.content[wsIdentifier].riskLink.selection.portfolios = {
                                data: [],
                                page: 0,
                                size: 20,
                                total: null,
                                last: null,
                                filter: new RlPortfolioFilter(),
                                loading: false
                            };
                            draft.content[wsIdentifier].riskLink.selection.edms = {};
                            draft.content[wsIdentifier].riskLink.selection.rdms = {};
                            draft.content[wsIdentifier].riskLink.display.displayListRDMEDM = false;
                        }));
                        ctx.dispatch(new fromWs.ClearDefaultDataSourcesSuccessAction(response));
                    } else
                        ctx.dispatch(new fromWs.SaveDefaultDataSourcesSuccessAction(response));
                    return of();
                })
            );
    }

}
