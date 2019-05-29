export class PatchPltMainStateAction {
  static readonly type = '[PLT Main] Patch State Plt';
  constructor(public payload: any) {}
}

export class InitPlts {
  static readonly type = '[PLT Main] Init Plts Data'
  constructor(public payload?: any) {}
}

export class loadAllPlts {
  static readonly type = '[PLT Main] Load All Plts Data'
  constructor(public payload?: any) {}
}

export class loadAllPltsSuccess {
  static readonly type = '[PLT Main] Load All Plts Success'
  constructor(public payload?: any) {}
}

export class loadAllPltsFail {
  static readonly type = '[PLT Main] Load All Plts Fail'
  constructor(public payload?: any) {}
}

export class LoadPltDataSuccess {
  static readonly  type = '[PLT Main] Load Plt Data Success';
  constructor(public payload?: any) {}
}

export class LoadPltDataFail {
  static readonly  type = '[PLT Main] Load Plt Data Fail';
  constructor(public payload?: any) {}
}

export class ToggleSelectPlts {
  static readonly  type = '[PLT Main] Toggle Select Plts';
  constructor(public payload?: any) {}
}

export class OpenPLTinDrawer {
  static readonly  type = '[PLT Main] Open plt in Drawer'
  constructor(public payload?: any) {}
}

export class ClosePLTinDrawer{
  static readonly  type = '[PLT Main] Close plt in Drawer'
  constructor(public payload?: any) {}
}

export class FilterPlts{
  static readonly  type = '[PLT Main] Filter Plts'
  constructor(public payload?: any) {}
}

export class setFilterPlts{
  static readonly  type = '[PLT Main] set Filter Plts'
  constructor(public payload?: any) {}
}

export class assignPltsToTag {
  static readonly type = '[PLT Main] Assign plts to Tag'
  constructor(public payload?: any) {}
}

export class assignPltsToTagSuccess {
  static readonly type = '[PLT Main] Assign plts to Tag Success'
  constructor(public payload?: any) {}
}

export class assignPltsToTagFail {
  static readonly type = '[PLT Main] Assign plts to Tag Fail'
  constructor(public payload?: any) {}
}

export class constructUserTags {
  static readonly type = '[PLT Main] constructUserTags'
  constructor(public payload?: any) {}
}
