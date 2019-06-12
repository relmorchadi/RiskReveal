import {GeneralConfig} from '../../model';
import * as _ from 'lodash';
import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import {PatchSearchTargetAction} from '../actions';


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
        numberHistory: '',
      }
    },
    riskLink: {
      importPage: '',
      financialPerspectiveELT: {
        data: ['Net Loss Pre Cat (RL)', 'Gross Loss (GR)', 'Net Cat (NC)'],
        selected: ['Net Loss Pre Cat (RL)']
      },
      financialPerspectiveEPM: {
          data: ['Facultative Reinsurance Loss', 'Ground UP Loss (GU)', 'Variante Reinsurance Loss'],
          selected: 'Facultative Reinsurance Loss'
      },
      targetCurrency: {data: ['MLC', 'User Defined', 'Underlying Currency'], selected: 'MLC'},
      rmsInstance: {data: ['AZU-P-RL17-SQL14', 'AZU-P-RL17-SQL15'], selected: 'AZU-P-RL17-SQL14'},
    },
    contractOfInterest: {
      country: '',
      uwUnit: '',
    },
    epCurves: {
      returnPeriod: '',
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

/*  @Action(PatchSearchTargetAction)
  patchSearchTarget(ctx: StateContext<GeneralConfig>, {value}: PatchSearchTargetAction) {
    ctx.patchState(
      {general:  {
            ...ctx.getState().general, searchTarget: value
        }});
  }*/
}
