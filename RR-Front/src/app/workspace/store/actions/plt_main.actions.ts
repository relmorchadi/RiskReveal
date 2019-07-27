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

export class FilterPltsByUserTags{
  static readonly  type = '[PLT Main] Filter Plts By UserTags'
  constructor(public payload?: any) {}
}

export class FilterPltsByStatus{
  static readonly  type = '[PLT Main] Filter Plts By Status'
  constructor(public payload?: any) {}
}

export class setUserTagsFilters{
  static readonly  type = '[PLT Main] set Filter Plts'
  constructor(public payload?: any) {}
}

export class setTableSortAndFilter{
static readonly  type = '[PLT Main] set Table Filter & Sort'
  constructor(public payload?: any) {}
}

export class createOrAssignTags {
  static readonly type = '[PLT Main] Create Or Assign Tags'
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

export class CreateTagSuccess {
  static readonly type = '[PLT Main] Create Tag Success'
  constructor(public payload?: any) {}
}

export class CreateTagFail {
  static readonly type = '[PLT Main] Create Tag Fail'
  constructor(public payload?: any) {}
}

export class constructUserTags {
  static readonly type = '[PLT Main] constructUserTags'
  constructor(public payload?: any) {}
}

export class deleteUserTag {
  static readonly type = '[PLT Main] delete User Tag'
  constructor(public payload?: any) {}
}

export class deleteUserTagSuccess {
  static readonly type = '[PLT Main] delete User Tag Success'
  constructor(public payload?: any) {}
}

export class deleteUserTagFail {
  static readonly type = '[PLT Main] delete User Tag Fail'
  constructor(public payload?: any) {}
}

export class deletePlt {
  static readonly type = '[PLT Main] delete Plt'
  constructor(public payload?: any) {}
}

export class deletePltSucess {
  static readonly type = '[PLT Main] delete Plt Sucess'
  constructor(public payload?: any) {}
}

export class deletePltFail {
  static readonly type = '[PLT Main] delete Plt Fail'
  constructor(public payload?: any) {}
}

export class editTag {
  static readonly type = '[PLT Main] Edit Tag'
  constructor(public payload?: any) {}
}

export class editTagSuccess {
  static readonly type = '[PLT Main] Rename Tag Success'
  constructor(public payload?: any) {}
}

export class editTagFail {
  static readonly type = '[PLT Main] Rename Tag Fail'
  constructor(public payload?: any) {}
}

export class restorePlt {
  static readonly type = '[PLT Main] Restore Plt'
  constructor(public payload?: any) {}
}

export class setCloneConfig {
  static readonly type = '[PLT Main] Clone Config';

  constructor(public payload?: any) {
  }
}
