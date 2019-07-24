import {Action, Selector, State, StateContext} from '@ngxs/store';

import * as _ from 'lodash';
import {RiskLinkModel} from '../../model/risk_link.model';
import {RiskApi} from '../../services/risk.api';
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
}
