import {GeneralConfig} from '../../model';
import * as _ from 'lodash';
import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import {PatchNumberFormatAction} from '../actions';
import {Data} from '../../model/data';


const initiaState: GeneralConfig = {
    general: {
      dateFormat: {
        shortDate: 'DD/MM/YYYY',
        longDate: 'DDDD MMMM YYYY',
        shortTime: 'HH:MM',
        longTime: 'HH:MM:SS',
      },
      timeZone: '',
      numberFormat: {
        numberOfDecimals: 2,
        decimalSeparator: '.',
        decimalThousandSeparator: ',',
        negativeFormat: 'simple',
        numberHistory: '',
      }
    },
    riskLink: {
      importPage: '',
      financialPerspectiveELT: {
        data: [{label: 'Net Loss Pre Cat (RL)', value: 'Net Loss Pre Cat (RL)'},
          {label: 'Gross Loss (GR)', value: 'Gross Loss (GR)'},
          {label: 'Net Cat (NC)', value: 'Net Cat (NC)'}],
        selected: ['Net Loss Pre Cat (RL)']
      },
      financialPerspectiveEPM: {
          data: ['Facultative Reinsurance Loss', 'Ground UP Loss (GU)', 'Variante Reinsurance Loss'],
          selected: 'Facultative Reinsurance Loss'
      },
      targetCurrency: {
        data: ['Main Liability Currency (MLC)', 'Underlying Loss Currency', 'User Defined Currency'],
        selected: 'Main Liability Currency (MLC)'},
      targetAnalysisCurrency: {
        data: ['Main Liability Currency (MLC)', 'Underlying Loss Currency', 'User Defined Currency'],
        selected: 'Main Liability Currency (MLC)'},
      rmsInstance: {data: ['AZU-P-RL17-SQL14', 'AZU-P-RL17-SQL15'], selected: 'AZU-P-RL17-SQL14'},
    },
    contractOfInterest: {
      country: {data: Data.countries, selected: []},
      uwUnit: {data: Data.uwUnit, selected: []},
    },
    epCurves: {
      returnPeriod: {data: [], selected: []},
      display: '',
    }
};

@State<GeneralConfig>({
  name: 'generalConfig',
  defaults: initiaState
})
export class GeneralConfigState implements NgxsOnInit {

  ctx = null;

  constructor() {

  }

  ngxsOnInit(ctx?: StateContext<GeneralConfigState>): void | any {
    this.ctx = ctx;
  }

  /**
   * Selectors
   */
  @Selector()
  static getGeneralConfigState(state: GeneralConfig) {
    return state;
  }

  @Selector()
  static getGeneralConfigAttr(path: string, value: any) {
    return  (state: any) =>  _.get(state.RiskLinkModel, path, value);
  }

  /**
   * Commands
   */

  @Action(PatchNumberFormatAction)
  patchSearchTarget(ctx: StateContext<GeneralConfig>, {payload}: PatchNumberFormatAction) {
    const state = ctx.getState();
    const {target, value} = payload;
    let newFormat = {...state.general.numberFormat};
    if (target === 'numberOfDecimals') { newFormat = {...newFormat, numberOfDecimals: value}; }
    if (target === 'decimalSeparator') { newFormat = {...newFormat, decimalSeparator: value}; }
    if (target === 'decimalThousandSeparator') { newFormat = {...newFormat, decimalThousandSeparator: value}; }
    if (target === 'negativeFormat') { newFormat = {...newFormat, negativeFormat: value}; }
    if (target === 'numberHistory') { newFormat = {...newFormat, numberHistory: value}; }

    ctx.patchState(
      {general:  {
            ...state.general, numberFormat: newFormat
        }});
  }
}
