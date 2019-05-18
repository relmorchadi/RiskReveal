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
import {SearchService} from '../../../core/service/search.service';

const initiaState: RiskLinkModel = {
  listEdmRdm: {
    data: null,
    selectedListEDMAndRDM: null,
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

  constructor(private _searchService: SearchService) {
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
    const item = payload.RDM.id;
    const {selected} = state.listEdmRdm.data[item];
    ctx.patchState({
      listEdmRdm: {
        data: {
          ...state.listEdmRdm.data,
          [item]: {...state.listEdmRdm.data[item],
            selected: !selected}
            },
        selectedListEDMAndRDM: state.listEdmRdm.selectedListEDMAndRDM
      }
    });
  }

  @Action(SelectRiskLinkEDMAndRDM)
  selectRiskLinkEDMAndRDM(ctx: StateContext<RiskLinkModel>) {
    const state = ctx.getState();
    const listDataToArray = _.toArray(state.listEdmRdm.data);
    let listSelected = {};
    listDataToArray.forEach(
      dt => {
        if (dt.selected) {
          listSelected = _.merge(listSelected, {
            [dt.id]: {
              ...dt,
              selected: false,
              scanned: true,
            }
          });
        }
      }
    );
    ctx.patchState({listEdmRdm: {data: state.listEdmRdm.data, selectedListEDMAndRDM: listSelected}});
  }

  @Action(LoadRiskLinkDataAction)
  loadRiskLinkData(ctx: StateContext<RiskLinkModel>) {
    const state = ctx.getState();
    let searchedData = {};
    this.searchRiskLink().subscribe(
      (content: any) => {
        content.content.map(
          data =>
            searchedData = _.merge(searchedData, {
              [data.id]: {
                ...data,
                selected: false,
                scanned: false,
                Reference: '0/13'
              }
            })
        );
        ctx.patchState({
          listEdmRdm: {
            data: searchedData,
            selectedListEDMAndRDM: state.listEdmRdm.selectedListEDMAndRDM
          }
        });
      }
    );
  }

  private searchRiskLink() {
    return this._searchService.searchRiskLinkData();
  }
}
