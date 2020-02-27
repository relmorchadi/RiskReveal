import {GeneralConfig} from '../../model';
import * as _ from 'lodash';
import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import * as fromGeneralConfig from '../actions';
import {Data} from '../../model/data';
import produce from "immer";
import {from, of} from "rxjs";
import {catchError, map, mergeMap, tap} from "rxjs/operators";
import {GlobalConfigApi} from '../../service/api/global-config.api';

const initiateState: GeneralConfig = {
  userPreferenceId: null,
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
    financialPerspectiveELT: [],
    financialPerspectiveEPM: '',
    targetCurrency: '',
    targetAnalysisCurrency: '',
    rmsInstance: '',
  },
  contractOfInterest: {
    country: [],
    uwUnit: [],
  },
  epCurves: {
    returnPeriod: {data: [], selected: []},
    display: '',
  },
  users: []
};

@State<GeneralConfig>({
  name: 'generalConfig',
  defaults: initiateState
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
    return (state: any) => _.get(state.RiskLinkModel, path, value);
  }

  @Selector()
  static getDateConfig(state: GeneralConfig) {
    return state.general.dateFormat;
  }

  @Selector()
  static getColors(state: GeneralConfig) {
    return state.general.colors;
  }

  @Selector()
  static getAllUsers(state:GeneralConfig) {
    return state.users;
  }

  @Selector()
  static getNumberFormatConfig(state: GeneralConfig) {
    return state.general.numberFormat;
  }

  /**
   * Commands
   */

  @Action(fromGeneralConfig.PatchNumberFormatAction)
  patchSearchTarget(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.PatchNumberFormatAction) {
    const state = ctx.getState();
    const {target, value} = payload;
    let newFormat = {...state.general.numberFormat};
    if (target === 'numberOfDecimals') {
      newFormat = {...newFormat, numberOfDecimals: value};
    }
    if (target === 'decimalSeparator') {
      newFormat = {...newFormat, decimalSeparator: value};
    }
    if (target === 'decimalThousandSeparator') {
      newFormat = {...newFormat, decimalThousandSeparator: value};
    }
    if (target === 'negativeFormat') {
      newFormat = {...newFormat, negativeFormat: value};
    }
    if (target === 'numberHistory') {
      newFormat = {...newFormat, numberHistory: value};
    }

    ctx.patchState(
      {
        general: {
          ...state.general, numberFormat: newFormat
        }
      });
  }

  @Action(fromGeneralConfig.PatchTimeZoneAction)
  patchTimeZone(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.PatchTimeZoneAction) {
    const state = ctx.getState();
    const {value} = payload;

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.general.timeZone = value;
    }));
  }

  @Action(fromGeneralConfig.PatchDateFormatAction)
  patchDateTarget(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.PatchDateFormatAction) {
    const state = ctx.getState();
    const {target, value} = payload;

    let newFormat = {...state.general.dateFormat};
    if (target === 'shortDate') {
      newFormat = {...newFormat, shortDate: value};
    }
    if (target === 'longDate') {
      newFormat = {...newFormat, longDate: value};
    }
    if (target === 'shortTime') {
      newFormat = {...newFormat, shortTime: value};
    }
    if (target === 'longTime') {
      newFormat = {...newFormat, longTime: value};
    }

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.general.dateFormat = newFormat;
    }));
  }

  @Action(fromGeneralConfig.PatchImportDataAction)
  patchImportData(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.PatchImportDataAction) {
    const state = ctx.getState();
    const {target, value} = payload;

    let newFormat = {...state.riskLink};
    if (target === 'importPage') {
      newFormat = {...newFormat, importPage: value};
    }
    if (target === 'financialPerspectiveELT') {
      newFormat = {...newFormat, financialPerspectiveELT: value};
    }
    if (target === 'financialPerspectiveEPM') {
      newFormat = {...newFormat, financialPerspectiveEPM: value};
    }
    if (target === 'targetCurrency') {
      newFormat = {...newFormat, targetCurrency: value};
    }
    if (target === 'targetAnalysisCurrency') {
      newFormat = {...newFormat, targetAnalysisCurrency: value};
    }
    if (target === 'rmsInstance') {
      newFormat = {...newFormat, rmsInstance: value};
    }

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.riskLink = newFormat
    }));
  }

  @Action(fromGeneralConfig.PatchWidgetDataAction)
  patchWidgetData(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.PatchWidgetDataAction) {
    const state = ctx.getState();
    const {target, value} = payload;

    let newFormat = {...state.contractOfInterest};
    if (target === 'country') {
      newFormat = {...newFormat, country: value};
    }
    if (target === 'uwUnit') {
      newFormat = {...newFormat, uwUnit: value};
    }

    ctx.patchState(produce(ctx.getState(), draft => {
      draft.contractOfInterest = newFormat
    }));
  }

  @Action(fromGeneralConfig.PostNewConfigAction)
  postNewConfigDetail(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.PostNewConfigAction) {
    const state = ctx.getState();
    let country = '';
    let uwUnit = '';
    let financialPerspective = '';
    _.forEach(state.riskLink.financialPerspectiveELT, item => financialPerspective = financialPerspective + item + ' ');
    _.forEach(state.contractOfInterest.country, item => country = country + item.countryCode + ' ');
    _.forEach(state.contractOfInterest.uwUnit, item => uwUnit = uwUnit + item.id + ' ');

    const newConfig = {
      shortDate: state.general.dateFormat.shortDate,
      longDate: state.general.dateFormat.longDate,
      shortTime: state.general.dateFormat.shortTime,
      longTime: state.general.dateFormat.longTime,
      timeZone: state.general.timeZone,
      numberOfDecimals: state.general.numberFormat.numberOfDecimals,
      decimalSeparator: state.general.numberFormat.decimalSeparator,
      decimalThousandSeparator: state.general.numberFormat.decimalThousandSeparator,
      negativeFormat: state.general.numberFormat.negativeFormat,
      numberHistory: state.general.numberFormat.numberHistory,
      colors: null,
      importPage: state.riskLink.importPage,
      financialPerspectiveELT: financialPerspective,
      financialPerspectiveEPM: state.riskLink.financialPerspectiveEPM,
      targetCurrency: state.riskLink.targetCurrency,
      targetAnalysisCurrency: state.riskLink.targetAnalysisCurrency,
      defaultRmsInstance: state.riskLink.rmsInstance,
      countryCode: country,
      // uwUnitId: uwUnit,
      userId: 1,
      returnPeriod: null,
      display: null,
    };
    if (state.userPreferenceId !== null) {
      return this.globalAPI.delGlobalConfig(state.userPreferenceId).pipe(
        map(data => {
          console.log(data);
          ctx.dispatch(new fromGeneralConfig.LoadAfterDelete(newConfig))
        }),
        catchError(delErr => ctx.dispatch(new fromGeneralConfig.PostNewConfigFailAction({}))));
    } else {
      return this.globalAPI.postGlobalConfig(newConfig).pipe(
        map(prj =>
          ctx.dispatch(new fromGeneralConfig.PostNewConfigSuccessAction({}))),
        catchError(err => ctx.dispatch(new fromGeneralConfig.PostNewConfigFailAction({})))
      );
    }
  }

  @Action(fromGeneralConfig.LoadAfterDelete)
  LoadAfterDelete(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.LoadConfiguration) {
    return this.globalAPI.postGlobalConfig(payload).pipe(
      map(prj =>
        ctx.dispatch(new fromGeneralConfig.PostNewConfigSuccessAction({}))),
      catchError(err => ctx.dispatch(new fromGeneralConfig.PostNewConfigFailAction({})))
    )
  }

  @Action(fromGeneralConfig.LoadConfiguration)
  loadConfig(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.LoadConfiguration) {
    return this.globalAPI.getGlobalConfig(1).pipe(
      mergeMap((data: any) => {
          return of(ctx.patchState(produce(ctx.getState(), draft => {
              draft.userPreferenceId = data.userPreferenceId;
              draft.general = {
                dateFormat: {
                  shortDate: data.shortTime,
                  longDate: data.longDate,
                  shortTime: data.shortTime,
                  longTime: data.longTime,
                },
                timeZone: data.timeZone,
                numberFormat: {
                  numberOfDecimals: data.numberOfDecimals,
                  decimalSeparator: data.decimalSeparator,
                  decimalThousandSeparator: data.decimalThousandSeparator,
                  negativeFormat: data.negativeFormat,
                  numberHistory: data.numberHistory,
                },
                colors: data.colors,
              };
              draft.riskLink = {
                importPage: data.importPage,
                financialPerspectiveELT: data.financialPerspectiveELT.trim().split(' ') || [],
                financialPerspectiveEPM: data.financialPerspectiveEPM,
                targetCurrency: data.targetCurrency,
                targetAnalysisCurrency: data.targetAnalysisCurrency,
                rmsInstance: data.defaultRmsInstance,
              };
              draft.contractOfInterest = {
                country:  data.countryCode !== null && data.countryCode !== '' ? data.countryCode.trim().split(' ') : [],
                uwUnit: data.uwUnitId !== null && data.uwUnitId !== 0 ? data.uwUnitId.trim().split(' ') : [],
              };
              draft.epCurves = {
                returnPeriod: null,
                display: null,
              };
            }
            ))
          );
        }
      )
    )
  }

  @Action(fromGeneralConfig.AddNewColors)
  addNewColors(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.AddNewColors) {
    const {colors} = payload;
    const {general} = ctx.getState();

    ctx.patchState(
      produce(ctx.getState(), draft => {
        draft.general.colors = general.colors.concat(colors);
      })
    )
  }

  @Action(fromGeneralConfig.RemoveColors)
  removeColors(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.RemoveColors) {
    const {colors} = payload;
    const {general} = ctx.getState();

    ctx.patchState(
      produce(ctx.getState(), draft => {
        draft.general.colors = _.filter(general.colors, color => !_.find(colors, clr => clr == color));
      })
    )
  }

  @Action(fromGeneralConfig.ReplaceColors)
  replaceColors(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.ReplaceColors) {
    const {colors} = payload;
    const {general} = ctx.getState();

    ctx.patchState(
      produce(ctx.getState(), draft => {

        _.forEach(general.colors, (color, i) => {
          let index = _.findIndex(colors, (clr: any) => clr.old == color);
          if (index > -1) {
            draft.general.colors[i] = colors[index].new;
          }
          index = -1;
        })
      })
    )
  }

  @Action(fromGeneralConfig.LoadColors)
  loadColors(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.LoadColors) {
    return of(JSON.parse(localStorage.getItem('colors')))
      .pipe(
        mergeMap((colors) => {

          if (colors) {
            ctx.patchState(produce(ctx.getState(), draft => {
              draft.general.colors = colors;
            }))
          } else {
            localStorage.setItem('colors', JSON.stringify([]));
          }

          return of(null);
        })
      )
  }

  @Action(fromGeneralConfig.GetAllUsers)
  getAllUsersAction(ctx: StateContext<GeneralConfig>) {
    return this.globalAPI.getAllUsers().pipe(
        tap(data => {
          ctx.patchState(produce(ctx.getState(), draft => {
            draft.users = data;
          }))
        })
    )
  }
}
