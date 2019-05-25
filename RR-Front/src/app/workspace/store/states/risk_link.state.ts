import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';

import * as _ from 'lodash';
import {RiskLinkModel} from '../../model/risk_link.model';
import {
  LoadRiskLinkDataAction,
  PatchRiskLinkAction,
  PatchRiskLinkCollapseAction,
  PatchRiskLinkDisplayAction,
  PatchRiskLinkFinancialPerspectiveAction,
  SearchRiskLinkEDMAndRDMAction, SelectRiskLinkAnalysisAndPortfolioAction,
  SelectRiskLinkEDMAndRDMAction, ToggleRiskLinkAnalysisAndPortfolioAction,
  ToggleRiskLinkEDMAndRDMAction
} from '../actions';
import {
  ToggleRiskLinkEDMAndRDMSelectedAction,
  LoadRiskLinkAnalysisDataAction,
  LoadRiskLinkPortfolioDataAction
} from '../actions/risk_link.actions';
import {mergeMap, switchMap} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {of} from 'rxjs/internal/observable/of';
import {RiskApi} from '../../services/risk.api';

const initiaState: RiskLinkModel = {
  listEdmRdm: {
    data: null,
    dataSelected: [],
    selectedListEDMAndRDM: null,
    selectedEDMOrRDM: null,
    totalNumberElement: 0,
    searchValue: '',
    dataLength: 0
  },
  display: {
    displayDropdownRDMEDM: false,
    displayListRDMEDM: false,
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
    financialPerspectiveEPM: {
      data: ['Facultative Reinsurance Loss', 'Ground UP Loss (GU)', 'Variante Reinsurance Loss'],
      selected: 'Facultative Reinsurance Loss'
    },
    targetCurrency: {data: ['MLC (USD)', 'MLC (EUR)', 'YEN'], selected: 'MLC (USD)'},
    calibration: {data: ['Add calibration', 'item 1', 'item 2'], selected: 'Add calibration'},
  },
  selectedAnalysisAndPortoflio: {
    selectedAnalysis: {data: null, lastSelectedIndex: null},
    selectedPortfolio: {data: null, lastSelectedIndex: null},
  },
  currentStep: 0,
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

  @Action(ToggleRiskLinkEDMAndRDMAction)
  toggleRiskLinkEDMAndRDM(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkEDMAndRDMAction) {
    const state = ctx.getState();
    const {action, RDM} = payload;
    let array = _.toArray(state.listEdmRdm.data);
    let newData = {};
    if (action === 'selectOne') {
      const item = RDM.id;
      const {selected} = state.listEdmRdm.data[item];
      if (selected) {
        array = array.filter(data => data.id !== item && data.selected === true);
      } else {
        array = array.filter(data => data.id == item || data.selected === true);
      }
      console.log(array);
      ctx.patchState({
        listEdmRdm: {
          ...state.listEdmRdm,
          data: {
            ...state.listEdmRdm.data,
            [item]: {...state.listEdmRdm.data[item], selected: !selected}
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
            }
          });
        });
        array = [];
      }
      console.log(array);
      ctx.patchState({
        listEdmRdm: {
          ...state.listEdmRdm,
          data: newData,
          dataSelected: array
        }
      });
    }
  }

  @Action(ToggleRiskLinkAnalysisAndPortfolioAction)
  toggleRiskLinkAnalysisAndPortfolio(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkAnalysisAndPortfolioAction) {
    const state = ctx.getState();
    const {action, dataSource, item} = payload;
    let portfolios = _.toArray(state.selectedAnalysisAndPortoflio.selectedPortfolio.data);
    let analysis = _.toArray(state.selectedAnalysisAndPortoflio.selectedAnalysis.data);
    let newData = {};
    let dataSelected = null;
    if (action === 'selectOne') {
      if (dataSource === 'Analysis') {
        dataSelected = state.selectedAnalysisAndPortoflio.selectedPortfolio;
        const {selected} = dataSelected.data[item.id];
        if (selected) {
          // array = array.filter(data => data.id !== item && data.selected === true);
        } else {
          // array = array.filter(data => data.id == item || data.selected === true);
        }
        // console.log(array);
        ctx.patchState({
          selectedAnalysisAndPortoflio: {
            ...state.selectedAnalysisAndPortoflio,
            selectedAnalysis: {
              ...state.selectedAnalysisAndPortoflio.selectedAnalysis,
              data: {
                ...state.selectedAnalysisAndPortoflio.selectedAnalysis.data,
                [item.analysisId]: {
                  ...state.selectedAnalysisAndPortoflio.selectedAnalysis.data[item.analysisId],
                  selected: !selected
                }
              },
            },
          }
        });

      } else if (dataSource === 'portfolio') {
        dataSelected = state.selectedAnalysisAndPortoflio.selectedAnalysis;
        const {selected} = dataSelected.data[item.dataSourceId];
        if (selected) {
          // array = array.filter(data => data.id !== item && data.selected === true);
        } else {
          // array = array.filter(data => data.id == item || data.selected === true);
        }
        // console.log(array);
        ctx.patchState({
          selectedAnalysisAndPortoflio: {
            ...state.selectedAnalysisAndPortoflio,
            selectedPortfolio: {
              ...state.selectedAnalysisAndPortoflio.selectedPortfolio,
              data: {
                ...state.selectedAnalysisAndPortoflio.selectedPortfolio.data,
                [item.dataSourceId]: {
                  ...state.selectedAnalysisAndPortoflio.selectedPortfolio.data[item.dataSourceId],
                  selected: !selected
                }
              },
            },
          }
        });
      }
    } else {
      if (action === 'selectAll') {
        /*array.forEach(dt => {
          newData = _.merge(newData, {
            [dt.id]: {
              ...dt,
              selected: true,
            }
          });
          dt = {...dt, selected: true};
        });*/
      } else if (action === 'unselectAll') {
        /*array.forEach(dt => {
          newData = _.merge(newData, {
            [dt.id]: {
              ...dt,
              selected: false,
            }
          });
        });
        array = [];*/
      }
      // console.log(array);
      ctx.patchState({

      });
    }
  }

  @Action(SelectRiskLinkAnalysisAndPortfolioAction)
  selectRiskLinkAnalysisAndPortfolio(ctx: StateContext<RiskLinkModel>, {payload}: SelectRiskLinkAnalysisAndPortfolioAction) {
    const state = ctx.getState();

  }

  @Action(LoadRiskLinkAnalysisDataAction)
  loadRiskLinkAnalysisDataAction(ctx: StateContext<RiskLinkModel>, {payload}: LoadRiskLinkAnalysisDataAction) {
    const state = ctx.getState();
    return this.riskApi.searchRiskLinkAnalysis(payload.id, payload.name).pipe(
      mergeMap(
        (data: any) =>
          of(ctx.patchState(
            {
              selectedAnalysisAndPortoflio: {
                ...state.selectedAnalysisAndPortoflio,
                selectedAnalysis: {
                  data: Object.assign({},
                    ...data.content.map(Analysis => ({
                        [Analysis.analysisId]: {
                          ...Analysis,
                          selected: false
                        }
                      }
                    ))),
                  lastSelectedIndex: null
                }
              }}))
      )
    );
  }

  @Action(LoadRiskLinkPortfolioDataAction)
  loadRiskLinkPortfolioDataAction(ctx: StateContext<RiskLinkModel>, {payload}: LoadRiskLinkPortfolioDataAction) {
    const state = ctx.getState();
    return this.riskApi.searchRiskLinkPortfolio(payload.id, payload.name).pipe(
      mergeMap(
        data =>
          of(ctx.patchState(
            {
              selectedAnalysisAndPortoflio: {
                ...state.selectedAnalysisAndPortoflio,
                selectedPortfolio: {
                  data: Object.assign({},
                    ...data.content.map(portfolio => ({
                        [portfolio.dataSourceId]: {
                          ...portfolio,
                          selected: false
                        }
                      }
                    ))),
                  lastSelectedIndex: null
                }
              }}))
      )
    );
  }

  @Action(ToggleRiskLinkEDMAndRDMSelectedAction)
  ToggleRiskLinkEDMAndRDMSelected(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkEDMAndRDMSelectedAction) {
    const state = ctx.getState();
    let array = _.toArray(state.listEdmRdm.selectedListEDMAndRDM);
    const {id, type} = payload;
    const {selected} = state.listEdmRdm.selectedListEDMAndRDM[id];
    let newDataSelected = {};
    if (selected) {
      array.forEach(dt => {
        newDataSelected = _.merge(newDataSelected, {
          [dt.id]: {
            ...dt,
            selected: false,
          }
        });
      });
      ctx.dispatch(new PatchRiskLinkDisplayAction({key: 'displayTable', value: false}));
      ctx.patchState({
        listEdmRdm: {
          ...state.listEdmRdm,
          data: state.listEdmRdm.selectedListEDMAndRDM,
          selectedListEDMAndRDM: newDataSelected,
          selectedEDMOrRDM: null,
        },
      });
    } else {
      array.forEach(dt => {
        newDataSelected = _.merge(newDataSelected, {
          [dt.id]: {
            ...dt,
            selected: false,
          }
        });
      });
      if (type === 'rdm') {
        ctx.dispatch(new LoadRiskLinkAnalysisDataAction(payload));
      } else if (type === 'edm') {
        ctx.dispatch(new LoadRiskLinkPortfolioDataAction(payload));
      }
      ctx.dispatch(new PatchRiskLinkDisplayAction({key: 'displayTable', value: true}));
      ctx.patchState({
        listEdmRdm: {
          ...state.listEdmRdm,
          selectedListEDMAndRDM: {
            ...newDataSelected,
            [id]: {...newDataSelected[id], selected: true}
          },
          selectedEDMOrRDM: type
        },
      });
    }
  }

  @Action(SelectRiskLinkEDMAndRDMAction)
  selectRiskLinkEDMAndRDM(ctx: StateContext<RiskLinkModel>) {
    const state = ctx.getState();
    const listDataToArray = _.toArray(state.listEdmRdm.data);
    let rdmValidator = false, emdValidator = false;
    let listSelected = {};
    listDataToArray.forEach(
      dt => {
        if (dt.selected) {
          if (dt.type === 'rdm') { rdmValidator = true; }
          if (dt.type === 'edm') { emdValidator = true; }
          listSelected = _.merge(listSelected, {
            [dt.id]: {
              ...dt,
              scanned: true,
              selected: false,
            }
          });
        }});
    ctx.dispatch(new PatchRiskLinkDisplayAction({key: 'displayTable', value: false}));
    ctx.patchState({
      listEdmRdm: {
        ...state.listEdmRdm,
        selectedListEDMAndRDM: listSelected
      },
      currentStep: (rdmValidator && emdValidator) ? 1 : 0
    });
  }

  @Action(SearchRiskLinkEDMAndRDMAction)
  searchRiskLinkEDMAndRDM(ctx: StateContext<RiskLinkModel>, {payload}: SearchRiskLinkEDMAndRDMAction) {
    const state = ctx.getState();
    const {keyword, size} = payload;
    const array = state.listEdmRdm.dataSelected;
    return this.riskApi.searchRiskLinkData(keyword, size).pipe(
      mergeMap(
        (data: any) =>
          of(ctx.patchState(
            {
              listEdmRdm: {
                ...state.listEdmRdm,
                data: Object.assign({},
                  ...data.content.map(item => {
                    const validator = array.filter(vd => vd.id == item.id);
                    const validate =  validator.length === 1;
                    return({
                      [item.id]: {
                        ...item,
                        selected: validate,
                        scanned: false,
                        Reference: '0/13'
                      }
                    }
                  );
                  })),
                totalNumberElement: data.totalElements,
                searchValue: keyword,
                dataLength: data.size
              }}))
      )
    );
  }

  @Action(LoadRiskLinkDataAction)
  loadRiskLinkData(ctx: StateContext<RiskLinkModel>) {
    const state = ctx.getState();
    return this.riskApi.searchRiskLinkData().pipe(
      mergeMap(
        (data: any) =>
          of(ctx.patchState(
            {
              listEdmRdm: {
                ...state.listEdmRdm,
                data: Object.assign({},
                  ...data.content.map(item => ({
                      [item.id]: {
                        ...item,
                        selected: false,
                        scanned: false,
                        Reference: '0/13'
                      }
                    }
                  ))),
                searchValue: '',
                totalNumberElement: data.totalElements,
                dataLength: data.size
              }}))
      )
    );
  }
}
