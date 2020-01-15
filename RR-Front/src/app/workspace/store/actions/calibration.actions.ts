export class loadAllPltsFromCalibration {
  static readonly type = '[Calibration] Load All Plts Data';
  constructor(public payload?: any) {}
}

export class LoadAllAdjustmentApplication {
  static readonly type = '[Calibration] load All Adjustment Application';
  constructor(public payload?: any) {}
}

export class LoadAllDefaultAdjustmentApplication {
  static readonly type = '[Calibration] Load All Default Adjustment';
  constructor(public payload?: any) {}
}

export class LoadAllPltsFromCalibrationSuccess {
  static readonly type = '[Calibration] Load All Plts Success';
  constructor(public payload?: any) {}
}

export class loadAllPltsFromCalibrationFail {
  static readonly type = '[Calibration] Load All Plts Fail';
  constructor(public payload?: any) {}
}

export class constructUserTagsFromCalibration {
  static readonly type = '[Calibration] Construct User Tags';
  constructor(public payload?: any) {}
}

export class ToggleSelectPltsFromCalibration {
  static readonly type = '[Calibration] Toggle Select Plts';
  constructor(public payload?: any) {}
}

export class calibrateSelectPlts {
  static readonly type = '[Calibration] Toggle calibrate Plts';
  constructor(public payload?: any) {}
}

export class toCalibratePlts {
  static readonly type = '[Calibration] Save PLTs to calibrate';
  constructor(public payload?: any) {}
}

export class setUserTagsFiltersFromCalibration {
  static readonly type = '[Calibration] set Filter Plts'

  constructor(public payload?: any) {
  }
}

export class FilterPltsByUserTagsFromCalibration {
  static readonly type = '[Calibration] Filter Plts By UserTags'

  constructor(public payload?: any) {
  }
}
export class setFilterCalibration {
  static readonly type = '[Calibration] set filter';

  constructor(public payload?: any) {
  }
}

export class extendPltSection {
  static readonly type = '[Calibration] extend PLT section';

  constructor(public payload?: any) {
  }
}

export class ExtendStateToggleAction {
  static readonly type = '[Calibration] Extend Toggle State';
  constructor(public payload?: any) {}
}

export class collapseTags {
  static readonly type = '[Calibration] collapse Tags';
  constructor(public payload?: any) {}
}

export class saveAdjustment {
  static readonly type = '[Calibration] save adjustment';

  constructor(public payload?: any) {
  }
}

export class saveAdjModification {
  static readonly type = '[Calibration] save adjustment Modification';

  constructor(public payload?: any) {
  }
}

export class replaceAdjustement {
  static readonly type = '[Calibration] replace To All Adjustment';

  constructor(public payload?: any) {
  }
}

export class deleteAdjsApplication {
  static readonly type = '[Calibration] delete Adjustment Application';

  constructor(public payload?: any) {
  }
}

export class deleteAdjustment {
  static readonly type = '[Calibration] delete Adjustment';

  constructor(public payload?: any) {
  }
}

export class saveAdjustmentInPlt {
  static readonly type = '[Calibration] save adjustment in PLT';

  constructor(public payload?: any) {
  }
}

export class applyAdjustment {
  static readonly type = '[Calibration]apply Adjustment';

  constructor(public payload?: any) {
  }
}

export class dropAdjustment {
  static readonly type = '[Calibration]drop Adjustment';

  constructor(public payload?: any) {
  }
}

export class dropThreadAdjustment {
  static readonly type = '[Calibration]Drop Thread Adjustment';

  constructor(public payload?: any) {
  }
}

export class loadAdjsArray {
  static readonly type = '[Calibration]Load Template Adjustments';

  constructor(public payload?: any) {
  }
}

export class initCalibrationData {
  static readonly type = '[Calibration] initData';

  constructor(public payload?: any) {
  }
}


export class saveSelectedPlts {
  static readonly type = '[Calibration] save selected plts';

  constructor(public payload?: any) {
  }
}

export class saveAdjustmentApplication {
  static readonly type = '[Calibration] save adjustment application';

  constructor(public payload?: any) {
  }
}

export class PatchCalibrationStateAction {
  static readonly type = '[Calibration] Patch Calibration State';

  constructor(public payload: { key: string, value: any } | { key: string, value: any }[]) {
  }
}

export class selectRow {
  static readonly type = '[Calibration] Select Row';

  constructor(public payload: { event: any, datatable: any }) {
  }
}

export class deselectAll {
  static readonly type = '[Calibration] Deselect All';

  constructor(public payload: { lastChecked: any }) {
  }
}

//////////////////////
// NEW
// CALIBRATION
/////////////////////

export class LoadGroupedPltsByPure {
  static readonly type = '[Calibration] Load Grouped Plts By Pure';

  constructor(public payload: any) {
  }
}

export class LoadDefaultAdjustmentsInScope {
  static readonly type = '[Calibration] Load Default Adjustments In Scope';

  constructor(public payload: any) {
  }
}

export class LoadEpMetrics {
  static readonly type = '[Calibration] Load EpMetrics';

  constructor(public payload: any) {
  }
}

export class LoadCalibrationConstants {
  static readonly type = '[Calibration] Load Calibration Constants';

  constructor(public payload?: any) {
  }
}

export class ToggleSelectCalibPlts {
  static readonly  type = '[Calibration] Toggle Select Plts';
  constructor(public payload?: any) {}
}

export class SaveRPs {
  static readonly type = '[Calibration] Save RPs';

  constructor(public payload?: any) {
  }
}

export class DeleteRPs {
  static readonly type = '[Calibration] Delete RPs';

  constructor(public payload?: any) {
  }
}

export class SaveOrDeleteRPs {
  static readonly type = '[Calibration] SaveOrDeleteRPs RPs';

  constructor(public payload?: any) {
  }
}
