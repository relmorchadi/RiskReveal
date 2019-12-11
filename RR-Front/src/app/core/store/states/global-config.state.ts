import {GeneralConfig} from '../../model';
import * as _ from 'lodash';
import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import * as fromGeneralConfig from '../actions';
import {Data} from '../../model/data';
import produce from "immer";
import {of} from "rxjs";
import {catchError, map, mergeMap} from "rxjs/operators";
import {GlobalConfigApi} from '../../service/api/global-config.api';

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
      },
      colors: []
    },
    riskLink: {
      importPage: '',
      financialPerspectiveELT: ['Net Loss Pre Cat (RL)'],
      financialPerspectiveEPM: 'Facultative Reinsurance Loss',
      targetCurrency: 'Main Liability Currency (MLC)',
      targetAnalysisCurrency: 'Main Liability Currency (MLC)',
      rmsInstance: 'AZU-P-RL17-SQL14',
    },
    contractOfInterest: {
      country: [],
      uwUnit: [],
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

  constructor(private globalAPI: GlobalConfigApi) {

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

  @Selector()
  static getDateConfig(state: GeneralConfig) {
    return state.general.dateFormat;
  }

  @Selector()
  static getColors(state: GeneralConfig) {
    return state.general.colors;
  }

  /**
   * Commands
   */

  @Action(fromGeneralConfig.PatchNumberFormatAction)
  patchSearchTarget(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.PatchNumberFormatAction) {
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

  @Action(fromGeneralConfig.PatchDateFormatAction)
  patchDateTarget(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.PatchDateFormatAction) {
    const state = ctx.getState();
    const {target, value} = payload;

    let newFormat = {...state.general.dateFormat};
    if (target === 'shortDate') { newFormat = {...newFormat, shortDate: value}; }
    if (target === 'longDate') { newFormat = {...newFormat, longDate: value}; }
    if (target === 'shortTime') { newFormat = {...newFormat, shortTime: value}; }
    if (target === 'longTime') { newFormat = {...newFormat, longTime: value}; }

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.general.dateFormat = newFormat;
    }));
  }

  @Action(fromGeneralConfig.PatchImportDataAction)
  patchImportData(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.PatchImportDataAction) {
    const state = ctx.getState();
    const {target, value} = payload;

    let newFormat = {...state.riskLink};
    if (target === 'importPage') { newFormat = {...newFormat, importPage: value}; }
    if (target === 'financialPerspectiveELT') { newFormat = {...newFormat, financialPerspectiveELT: value}; }
    if (target === 'financialPerspectiveEPM') { newFormat = {...newFormat, financialPerspectiveEPM: value}; }
    if (target === 'targetCurrency') { newFormat = {...newFormat, targetCurrency: value}; }
    if (target === 'targetAnalysisCurrency') { newFormat = {...newFormat, targetAnalysisCurrency: value}; }
    if (target === 'rmsInstance') { newFormat = {...newFormat, rmsInstance: value}; }

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.riskLink = newFormat
    }));
  }

  @Action(fromGeneralConfig.PatchWidgetDataAction)
  patchWidgetData(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.PatchWidgetDataAction) {
    const state = ctx.getState();
    const {target, value} = payload;

    let newFormat = {...state.contractOfInterest};
    if (target === 'country') { newFormat = {...newFormat, country: value}; }
    if (target === 'uwUnit') { newFormat = {...newFormat, uwUnit: value}; }

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.contractOfInterest = newFormat
    }));
  }

  @Action(fromGeneralConfig.PostNewConfigAction)
  postNewConfigDetail(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.PostNewConfigAction) {
    const state = ctx.getState();

    return this.globalAPI.postGlobalConfig(payload).pipe( map(prj =>
      ctx.dispatch(new fromGeneralConfig.PostNewConfigSuccessAction({}))),
      catchError(err => ctx.dispatch(new fromGeneralConfig.PostNewConfigFailAction({})))
      );
  }

  @Action(fromGeneralConfig.AddNewColors)
  addNewColors(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.AddNewColors) {
    const {
      colors
    } = payload;

    const {
      general
    } = ctx.getState();

    ctx.patchState(
      produce(ctx.getState(), draft => {
        draft.general.colors= general.colors.concat(colors);
      })
    )
  }

  @Action(fromGeneralConfig.RemoveColors)
  removeColors(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.RemoveColors) {
    const {
      colors
    } = payload;

    const {
      general
    } = ctx.getState();

    ctx.patchState(
      produce(ctx.getState(), draft => {
        draft.general.colors= _.filter(general.colors, color => !_.find(colors, clr => clr == color));
      })
    )
  }

  @Action(fromGeneralConfig.ReplaceColors)
  replaceColors(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.ReplaceColors) {
    const {
      colors
    } = payload;

    const {
      general
    } = ctx.getState();

    ctx.patchState(
      produce(ctx.getState(), draft => {

        _.forEach(general.colors, (color, i) => {
          let index= _.findIndex(colors, (clr: any) => clr.old == color);
          if( index > -1 ) {
            draft.general.colors[i]= colors[index].new;
          }
          index=-1;
        })
      })
    )
  }

  @Action(fromGeneralConfig.LoadColors)
  loadColors(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.LoadColors) {

    return of(JSON.parse(localStorage.getItem('colors')))
      .pipe(
        mergeMap((colors) => {

          if(colors) {
            ctx.patchState(produce(ctx.getState(), draft => {
              draft.general.colors = colors;
            }))
          }else {
            localStorage.setItem('colors', JSON.stringify([]));
          }

          return of(null);
        })
      )
  }
}
