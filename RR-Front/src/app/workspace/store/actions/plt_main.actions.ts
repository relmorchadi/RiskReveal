export class PatchPltMainStateAction {
  static readonly type = '[PLT Main] Patch State Plt';
  constructor(public payload: any) {}
}

export class LoadPltData {
  static readonly type = '[PLT Main] Load Plt Data'
  constructor(public payload?: any) {}
}

export class LoadPltDataSuccess {
  static readonly  type = '[PLT Main] Load Plt Data Success'
  constructor(public payload?: any) {}
}

export class LoadPltDataFail {
  static readonly  type = '[PLT Main] Load Plt Data Fail'
  constructor(public payload?: any) {}
}
