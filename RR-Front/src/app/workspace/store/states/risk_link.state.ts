import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';

import * as _ from 'lodash';
import {RiskLinkModel} from '../../model/risk_link.model';
import {
  LoadRiskLinkDataAction,
  PatchRiskLinkAction,
  PatchRiskLinkCollapseAction,
  PatchRiskLinkDisplayAction,
  PatchRiskLinkFinancialPerspectiveAction, SelectRiskLinkEDMAndRDM, ToggleRiskLinkEDMAndRDM
} from '../actions';
import {
  ToggleRiskLinkEDMAndRDMSelected,
  LoadRiskLinkAnalysisDataAction,
  LoadRiskLinkPortfolioDataAction
} from '../actions/risk_link.actions';
import {mergeMap} from 'rxjs/operators';
import {Observable} from "rxjs";
import {of} from 'rxjs/internal/observable/of';
import {RiskApi} from "../../services/risk.api";

const initiaState: RiskLinkModel = {
  listEdmRdm: {
    data: null,
    selectedListEDMAndRDM: null,
    selectedEDMOrRDM: null,
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

  @Action(ToggleRiskLinkEDMAndRDM)
  toggleRiskLinkEDMAndRDM(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkEDMAndRDM) {
    const state = ctx.getState();
    const action = payload.action;
    let array = _.toArray(state.listEdmRdm.data);
    let newData = {};
    if (action === 'selectOne') {
      const item = payload.RDM.id;
      const {selected} = state.listEdmRdm.data[item];
      ctx.patchState({
        listEdmRdm: {
          ...state.listEdmRdm,
          data: {
            ...state.listEdmRdm.data,
            [item]: {...state.listEdmRdm.data[item], selected: !selected}
          },
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
      }
      ctx.patchState({
        listEdmRdm: {
          ...state.listEdmRdm,
          data: newData
        }
      });
    }
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
                          number: 'FA0020553_01',
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

  @Action(ToggleRiskLinkEDMAndRDMSelected)
  ToggleRiskLinkEDMAndRDMSelected(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkEDMAndRDMSelected) {
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
      })
      ctx.dispatch(new PatchRiskLinkDisplayAction({key: 'displayTable', value: false}));
      ctx.patchState({
        listEdmRdm: {
          data: state.listEdmRdm.selectedListEDMAndRDM,
          selectedListEDMAndRDM: newDataSelected,
          selectedEDMOrRDM: null
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
          data: state.listEdmRdm.data,
          selectedListEDMAndRDM: {
            ...newDataSelected,
            [id]: {...newDataSelected[id], selected: true}
          },
          selectedEDMOrRDM: type
        },
      });
    }
  }

  @Action(SelectRiskLinkEDMAndRDM)
  selectRiskLinkEDMAndRDM(ctx: StateContext<RiskLinkModel>) {
    const state = ctx.getState();
    const listDataToArray = _.toArray(state.listEdmRdm.data);
    let rdmValidator = false, emdValidator = false;
    let listSelected = {};
    listDataToArray.forEach(
      dt => {
        if (dt.selected) {
          if (dt.type === 'rdm') {
            rdmValidator = true;
          }
          if (dt.type === 'edm') {
            emdValidator = true;
          }
          listSelected = _.merge(listSelected, {
            [dt.id]: {
              ...dt,
              scanned: true,
              selected: false,
            }
          });
        }
      }
    );
    ctx.dispatch(new PatchRiskLinkDisplayAction({key: 'displayTable', value: false}));
    ctx.patchState({
      listEdmRdm: {
        ...state.listEdmRdm,
        selectedListEDMAndRDM: listSelected
      },
      currentStep: (rdmValidator && emdValidator) ? 1 : 0
    });
  }

  @Action(LoadRiskLinkDataAction)
  loadRiskLinkData(ctx: StateContext<RiskLinkModel>) {
    const state = ctx.getState();
    return this.riskApi.searchRiskLinkData().pipe(
      mergeMap(
        data =>
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
                  )))
              }}))
      )
    );
  }
}
