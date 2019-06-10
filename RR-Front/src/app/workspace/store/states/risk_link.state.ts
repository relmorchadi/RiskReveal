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
  SelectRiskLinkEDMAndRDMAction, ToggleRiskLinkPortfolioAction,
  ToggleRiskLinkEDMAndRDMAction, ToggleRiskLinkAnalysisAction
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
    totalNumberElement: 0,
    searchValue: '',
    numberOfElement: 0
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
    financialPerspectiveEPM: {
      data: ['Facultative Reinsurance Loss', 'Ground UP Loss (GU)', 'Variante Reinsurance Loss'],
      selected: 'Facultative Reinsurance Loss'
    },
    targetCurrency: {data: ['MLC', 'User Defined', 'Underling Currency'], selected: 'MLC'},
    calibration: {data: ['Add calibration', 'item 1', 'item 2'], selected: 'Add calibration'},
  },
  analysis: null,
  portfolios: null,
  selectedEDMOrRDM: null,
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
    return  (state: any) =>  _.get(state.RiskLinkModel, path, value);
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
    } else if (action === 'selectLink') {
      const {id, name} = RDM;
      const searchTerm = _.truncate(name, {length: name.length - 2, omission: ''});
/*      this.riskApi.searchRiskLinkPortfolio('', searchTerm).pipe(
        mergeMap(
          (data: any) =>
            of(
              null
            )
        )
      );*/
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

  @Action(ToggleRiskLinkPortfolioAction)
  toggleRiskLinkPortfolio(ctx: StateContext<RiskLinkModel>, {payload}: ToggleRiskLinkPortfolioAction) {
    const state = ctx.getState();
    const {action, value, item} = payload;
    console.log(action, value, item);
    const portfolios = _.toArray(_.get(state.portfolios, `${item.edmId}.data`));
    let newData = {};
    if (action === 'selectOne') {
      if (value) {
        // array = array.filter(data => data.id !== item && data.selected === true);
      } else {
        // array = array.filter(data => data.id == item || data.selected === true);
      }
      ctx.patchState({
        portfolios: {
          ...state.portfolios,
          [item.edmId]: {
            ...state.portfolios[item.edmId],
            data: {
              ...state.portfolios[item.edmId].data,
              [item.dataSourceId]: {
                ...state.portfolios[item.edmId].data[item.dataSourceId],
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
          [item.edmId]: {
            ...state.portfolios[item.edmId],
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
    const analysis = _.toArray(state.analysis.data);
    let newData = {};
    const dataSelected = state.analysis;
    if (action === 'selectOne') {
      if (value) {
        // array = array.filter(data => data.id !== item && data.selected === true);
      } else {
        // array = array.filter(data => data.id == item || data.selected === true);
      }
      ctx.patchState({
        analysis: {
          ...state.analysis,
          [item.rdmId]: {
            ...state.analysis[item.rdmId],
            data: {
              ...state.analysis[item.rdmId].data,
              [item.analysisId]: {
                ...state.portfolios[item.edmId].data[item.analysisId],
                selected: value
              }
            }
          },
        }
      });
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
          [item.rdmId]: {
            ...state.analysis[item.rdmId],
            data: newData
          }
        },
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
              analysis: _.merge(
                {}, state.analysis, {
                  [payload.id]: {
                    data: Object.assign({},
                      ...data.content.map(Analysis => ({
                          [Analysis.analysisId]: {
                            ...Analysis,
                            selected: false
                          }
                        }
                      ))),
                    selectedData: [],
                    lastSelectedIndex: null,
                    totalNumberElement: data.totalElements,
                    numberOfElement: data.size,
                    filter: {}
                  }
                }
              )
            }))
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
              portfolios: _.merge(
                {}, state.portfolios, {
                  [payload.id]: {
                    data: Object.assign({},
                      ...data.content.map(portfolio => ({
                          [portfolio.dataSourceId]: {
                            ...portfolio,
                            selected: false
                          }
                        }
                      ))),
                    selectedData: [],
                    lastSelectedIndex: null,
                    totalNumberElement: data.totalElements,
                    numberOfElement: data.size,
                    filter: {}
                  }
                }
              )
            }))
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
        },
        selectedEDMOrRDM: null,
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
        },
        selectedEDMOrRDM: type
      });
    }
  }

  @Action(SelectRiskLinkEDMAndRDMAction)
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
              scanned: true,
              selected: false,
            }
          });
        }
      });
    ctx.dispatch(new PatchRiskLinkDisplayAction({key: 'displayTable', value: false}));
    ctx.patchState({
      listEdmRdm: {
        ...state.listEdmRdm,
        selectedListEDMAndRDM: listSelected
      },
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
                    const validate = validator.length === 1;
                    return ({
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
                numberOfElement: data.size
              }
            }))
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
                numberOfElement: data.size
              }
            }))
      )
    );
  }
}
