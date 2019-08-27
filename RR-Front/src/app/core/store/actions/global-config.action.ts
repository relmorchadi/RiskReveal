export class PatchNumberFormatAction {
  static readonly type = '[Global Configuration] Patch Number Format';
  constructor(public payload: any) {}
}

export class AddNewColors {
  static readonly type = '[Global Configuration Colors] Add new colors';
  constructor(public payload: any) {}
}

export class RemoveColors {
  static readonly type = '[Global Configuration Colors] Remove colors';
  constructor(public payload: any) {}
}


export class ReplaceColors {
  static readonly type = '[Global Configuration Colors] Replace colors';
  constructor(public payload: any) {}
}

export class LoadColors {
  static readonly type = '[Global Configuration Colors] Load colors';
  constructor(public payload: any) {}
}
