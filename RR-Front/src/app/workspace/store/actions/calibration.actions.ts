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

export class collapseTags {
  static readonly type = '[Calibration] collapse Tags';

  constructor(public payload?: any) {
  }
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

