import {GeneralConfig} from '../../model';
import * as _ from 'lodash';
import {Action, createSelector, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import * as fromGeneralConfig from '../actions';
import {Data} from '../../model/data';
import produce from "immer";
import {from, of} from "rxjs";
import {catchError, map, mergeMap, tap} from "rxjs/operators";
import {GlobalConfigApi} from '../../service/api/global-config.api';
import {TablePreferencesApi} from "../../service/api/table-preferences.api";
import {WorkspaceModel} from "../../../workspace/model";

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
  users: [],
  tablePreference: []
};

@State<GeneralConfig>({
  name: 'generalConfig',
  defaults: initiateState
})
export class GeneralConfigState implements NgxsOnInit {

  ctx = null;

  constructor(private globalAPI: GlobalConfigApi, private tablePreferencesApi: TablePreferencesApi) {

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
    return {...state.general.dateFormat, timeZone: state.general.timeZone};
  }

  @Selector()
  static getNumberFormatConfig(state: GeneralConfig) {
    return state.general.numberFormat;
  }

  @Selector()
  static getImportConfig(state: GeneralConfig) {
    return state.riskLink;
  }

  @Selector()
  static getColors(state: GeneralConfig) {
    return state.general.colors;
  }

  @Selector()
  static getAllUsers(state:GeneralConfig) {
    return state.users;
  }

  static getTablePreference(uIPage: string, tableName: string) {
    return createSelector([GeneralConfigState], (state: GeneralConfig) =>
        state.tablePreference);
  }


  /**
   * Commands
   */

  @Action(fromGeneralConfig.PostNewConfigAction)
  postNewConfigDetail(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.PostNewConfigAction) {
    const state = ctx.getState();
    console.log(payload);
    return this.globalAPI.postGlobalConfig(payload).pipe(
        map(prj =>
            ctx.dispatch(new fromGeneralConfig.PostNewConfigSuccessAction({}))),
        catchError(err => ctx.dispatch(new fromGeneralConfig.PostNewConfigFailAction({})))
    );
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
    return this.globalAPI.getGlobalConfig().pipe(
      mergeMap((data: any) => {
          return of(ctx.patchState(produce(ctx.getState(), draft => {
              draft.userPreferenceId = data.userPreferenceId;
              draft.general = {
                dateFormat: {
                  shortDate: data.shortDate,
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

  @Action(fromGeneralConfig.GetTablePreference)
  GetTablePreference(ctx: StateContext<GeneralConfig>, {payload}: fromGeneralConfig.GetTablePreference) {
    return this.tablePreferencesApi.getTablePreference(payload.uIPage, payload.tableName).pipe(
        tap(data => {
          ctx.patchState(produce(ctx.getState(), draft => {
            draft.tablePreference.push(data);
          }))
        })
    )
  }
}
