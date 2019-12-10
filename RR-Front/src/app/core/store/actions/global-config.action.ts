export class PatchNumberFormatAction {
  static readonly type = '[Global Configuration] Patch Number Format';
  constructor(public payload?: any) {}
}

export class PatchDateFormatAction {
  static readonly type = '[Global Configuration] Patch Date Format';
  constructor(public payload?: any) {}
}

export class PatchGeneralRecentAction {
  static readonly type = '[Global Configuration] Patch Global Recent Format';
  constructor(public payload?: any) {}
}

export class PatchImportDataAction {
  static readonly type = '[Global Configuration] Import Data Action';
  constructor(public payload?: any) {}
}

export class PatchWidgetDataAction {
  static readonly type = '[Global Configuration] Widget Data Action';
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
