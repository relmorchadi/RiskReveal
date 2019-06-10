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

