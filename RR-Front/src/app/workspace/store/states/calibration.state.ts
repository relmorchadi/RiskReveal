import {CalibrationModel} from "../../model";
import {Action, createSelector, NgxsOnInit, Selector, State, StateContext, Store} from "@ngxs/store";
import * as fromPlt from "../actions";
import {deselectAll, PatchCalibrationStateAction, selectRow} from "../actions";
import * as _ from "lodash";
import {
  ADJUSTMENT_TYPE,
  ADJUSTMENTS_ARRAY,
  LIST_OF_DISPLAY_PLTS,
  LIST_OF_PLTS,
  PURE,
  SYSTEM_TAGS
} from "../../containers/workspace-calibration/data";
import {PltApi} from "../../services/plt.api";
import {WorkspaceMain} from "../../../core/model";
import {WorkspaceMainState} from "../../../core/store/states";
import {CalibrationService} from "../../services/calibration.service";

const initiaState: CalibrationModel = {
  data: {},
  loading: false,
  filters: {
    systemTag: [],
    userTag: []
  },
  userTags: {},
  selectedPLT: [],
  extendPltSection: false,
  collapseTags: true,
  lastCheckedBool: false,
  firstChecked: '',
  adjustments: [],
  adjustmentApplication: {},
  adjustementType: _.assign([], [...ADJUSTMENT_TYPE]),
  pure: _.assign({}, {...PURE}),
  systemTags: _.assign([], [...SYSTEM_TAGS]),
  allAdjsArray: _.assign([], [...ADJUSTMENTS_ARRAY]),
  listOfPlts: _.assign([], [...LIST_OF_PLTS]),
  listOfDisplayPlts: _.assign([], [...LIST_OF_DISPLAY_PLTS]),
  pltColumns: [
    {fields: 'check', header: '', width: '1%', sorted: false, filtred: false, icon: null, extended: true},
    {fields: '', header: 'User Tags', width: '10%', sorted: false, filtred: false, icon: null, extended: true},
    {fields: 'pltId', header: 'PLT ID', width: '12%', sorted: true, filtred: true, icon: null, extended: true},
    {fields: 'pltName', header: 'PLT Name', width: '14%', sorted: true, filtred: true, icon: null, extended: true},
    {fields: 'peril', header: 'Peril', width: '7%', sorted: true, filtred: true, icon: null, extended: false},
    {
      fields: 'regionPerilCode',
      header: 'Region Peril Code',
      width: '13%',
      sorted: true,
      filtred: true,
      icon: null,
      extended: false
    },
    {
      fields: 'regionPerilName',
      header: 'Region Peril Name',
      width: '13%',
      sorted: true,
      filtred: true,
      icon: null,
      extended: false
    },
    {fields: 'grain', header: 'Grain', width: '9%', sorted: true, filtred: true, icon: null, extended: false},
    {
      fields: 'vendorSystem',
      header: 'Vendor System',
      width: '11%',
      sorted: true,
      filtred: true,
      icon: null,
      extended: false
    },
    {fields: 'rap', header: 'RAP', width: '9%', sorted: true, filtred: true, icon: null, extended: false},
    {fields: 'action', header: '', width: '3%', sorted: false, filtred: false, icon: "icon-focus-add", extended: true},
    {fields: 'action', header: '', width: '3%', sorted: false, filtred: false, icon: "icon-note", extended: true}
  ],
};

@State<CalibrationModel>({
  name: 'calibration',
  defaults: initiaState
})
export class CalibrationState implements NgxsOnInit {
  ctx = null;
  status = ['in progress', 'valid', 'locked', 'requires regeneration', 'failed'];

  constructor(private store$: Store, private pltApi: PltApi, private calibrationService: CalibrationService) {

  }

  ngxsOnInit(ctx?: StateContext<CalibrationState>): void | any {
    this.ctx = ctx;
  }

  @Selector()
  static getProjects() {
    return (state: any) => state.workspaceMain.openedWs.projects
  }

  @Selector()
  static getAdjustmentApplication(state: CalibrationModel) {
    return _.get(state, "adjustmentApplication")
  }

  static getPlts(wsIdentifier: string) {
    return createSelector([CalibrationState], (state: CalibrationModel) => _.keyBy(_.filter(_.get(state.data, `${wsIdentifier}`), e => !e.deleted), 'pltId'))
  }

  static getDeletedPlts(wsIdentifier: string) {
    return createSelector([CalibrationState], (state: CalibrationModel) => _.keyBy(_.filter(_.get(state.data, `${wsIdentifier}`), e => e.deleted), 'pltId'));
  }

  @Selector()
  static getUserTags(state: CalibrationModel) {
    return _.get(state, 'userTags', {})
  }

  static getLeftNavbarIsCollapsed() {
    return createSelector([WorkspaceMainState], (state: WorkspaceMain) => {
      return _.get(state, "leftNavbarIsCollapsed");
    });
  }

  @Action(fromPlt.loadAllPltsFromCalibration)
  loadAllPltsFromCalibration(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.loadAllPltsFromCalibration) {
    this.calibrationService.loadAllPltsFromCalibration(ctx, payload)
  }

  @Action(fromPlt.loadAllPltsFromCalibrationSuccess)
  loadAllPltsFromCalibrationSuccess(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.loadAllPltsFromCalibrationSuccess) {
    this.calibrationService.loadAllPltsFromCalibrationSuccess(ctx, payload)
  }

  @Action(fromPlt.constructUserTagsFromCalibration)
  constructUserTags(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.constructUserTagsFromCalibration) {
    this.calibrationService.constructUserTags(ctx, payload)
  }

  @Action(fromPlt.setUserTagsFiltersFromCalibration)
  setFilterPlts(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.setUserTagsFiltersFromCalibration) {
    this.calibrationService.setFilterPlts(ctx, payload)
  }

  @Action(fromPlt.FilterPltsByUserTagsFromCalibration)
  FilterPlts(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.FilterPltsByUserTagsFromCalibration) {
    this.calibrationService.FilterPlts(ctx, payload)
  }

  @Action(fromPlt.ToggleSelectPltsFromCalibration)
  SelectPlts(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.ToggleSelectPltsFromCalibration) {
    this.calibrationService.SelectPlts(payload)
  }

  @Action(fromPlt.calibrateSelectPlts)
  calibrateSelectPlts(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.calibrateSelectPlts) {
    this.calibrationService.calibrateSelectPlts(ctx, payload)
  }
  @Action(fromPlt.initCalibrationData)
  initCalibrationData(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.initCalibrationData) {
    this.calibrationService.initCalibrationData(ctx, payload)
  }

  @Action(fromPlt.setFilterCalibration)
  setFilterCalibration(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.setFilterCalibration) {
    this.calibrationService.setFilterCalibration(ctx, payload)
  }

  @Action(fromPlt.extendPltSection)
  expandPltSection(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.extendPltSection) {
    this.calibrationService.expandPltSection(ctx, payload)
  }

  @Action(fromPlt.collapseTags)
  collapseTags(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.collapseTags) {
    this.calibrationService.collapseTags(ctx, payload)
  }

  @Action(fromPlt.saveAdjustment)
  saveAdjustment(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.saveAdjustment) {
    this.calibrationService.saveAdjustment(ctx, payload)
  }

  @Action(fromPlt.dropThreadAdjustment)
  dropThreadAdjustment(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.dropThreadAdjustment) {
    this.calibrationService.dropThreadAdjustment(ctx, payload)

  }

  @Action(fromPlt.saveAdjModification)
  saveAdjModification(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.saveAdjModification) {
    this.calibrationService.saveAdjModification(ctx, payload)
  }

  @Action(fromPlt.replaceAdjustement)
  replaceAdjustement(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.replaceAdjustement) {
    this.calibrationService.replaceAdjustement(ctx, payload)
  }

  @Action(fromPlt.saveAdjustmentInPlt)
  saveAdjustmentInPlt(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.saveAdjustmentInPlt) {
    this.calibrationService.saveAdjustmentInPlt(ctx, payload)
  }


  @Action(fromPlt.applyAdjustment)
  applyAdjustment(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.applyAdjustment) {
    this.calibrationService.applyAdjustment(ctx, payload)
  }

  @Action(fromPlt.dropAdjustment)
  dropAdjustment(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.dropAdjustment) {
    this.calibrationService.dropAdjustment(ctx, payload)
  }

  @Action(fromPlt.deleteAdjsApplication)
  deleteAdjsApplication(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.deleteAdjsApplication) {
    this.calibrationService.deleteAdjsApplication(ctx, payload)
  }

  @Action(fromPlt.deleteAdjustment)
  deleteAdjustment(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.deleteAdjustment) {
    this.calibrationService.deleteAdjustment(ctx, payload)
  }

  @Action(fromPlt.saveSelectedPlts)
  saveSelectedPlts(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.saveSelectedPlts) {
    this.calibrationService.saveSelectedPlts(ctx, payload)

  }

  @Action(fromPlt.saveAdjustmentApplication)
  saveAdjustmentApplication(ctx: StateContext<CalibrationModel>, {payload}: fromPlt.saveAdjustmentApplication) {
    this.calibrationService.saveAdjustmentApplication(ctx, payload)
  }

  @Action(PatchCalibrationStateAction)
  patchSearchState(ctx: StateContext<CalibrationState>, {payload}: PatchCalibrationStateAction) {
    this.calibrationService.patchSearchState(ctx, payload)
  }


  @Action(selectRow)
  selectRow(ctx: StateContext<CalibrationModel>, {payload}: selectRow) {
    this.calibrationService.selectRow(ctx, payload)
  }

  @Action(deselectAll)
  deselectAll(ctx: StateContext<CalibrationModel>, {payload}: deselectAll) {
    this.calibrationService.deselectAll(ctx, payload)
  }

}
