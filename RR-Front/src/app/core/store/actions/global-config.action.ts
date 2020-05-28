export class PatchNumberFormatAction {
  static readonly type = '[Global Configuration] Patch Number Format';
  constructor(public payload?: any) {}
}

export class PatchTimeZoneAction {
  static readonly type = '[Global Configuration] Patch Time Zone';
  constructor(public payload?: any) {}
}

export class AddNewColors {
  static readonly type = '[Global Configuration Colors] Add new colors';
  constructor(public payload?: any) {}
}

export class RemoveColors {
  static readonly type = '[Global Configuration Colors] Remove colors';
  constructor(public payload?: any) {}
}

export class ReplaceColors {
  static readonly type = '[Global Configuration Colors] Replace colors';
  constructor(public payload: any) {}
}

export class LoadColors {
  static readonly type = '[Global Configuration Colors] Load colors';
  constructor(public payload: any) {}
}

export class LoadConfiguration {
  static readonly type = '[Global Configuration] Load Config';
  constructor(public payload?: any) {}
}

export class LoadAfterDelete {
  static readonly type = '[Global Configuration] Load Config After Delete';
  constructor(public payload?: any) {}
}

export class PostNewConfigAction {
  static readonly type = '[Global Configuration Colors] Post New Config';
  constructor(public payload: any) {}
}

export class PostNewConfigSuccessAction {
  static readonly type = '[Global Configuration Colors] Post New Config Success';
  constructor(public payload: any) {}
}

export class PostNewConfigFailAction {
  static readonly type = '[Global Configuration Colors] Post New Config Fail';
  constructor(public payload: any) {}
}

export class GetAllUsers {
  static readonly type = '[Global Configuration] Get All Users';
  constructor(public payload?: any) {}
}

export class GetTablePreference {
  static readonly type = '[Global Configuration] get Table Preference';
  constructor(public payload?: any) {}
}

