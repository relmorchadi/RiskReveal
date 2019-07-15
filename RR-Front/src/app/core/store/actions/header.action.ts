export class AddWsToRecent {
  static readonly type = '[Workspaces PopIn] Add workspace to recent';

  constructor(public payload: { wsId, uwYear, workspaceName, programName, cedantName }) {
  }
}

export class ToggleWsSelection {
  static readonly type = '[Workspaces PopIn] Toggle Ws selection';

  constructor(public payload: { context: string, index: number }) {
  }
}

export class ChangeWsSelection {
  static readonly type = '[Workspaces PopIn] Change Ws selection';

  constructor(public payload: { context: string, index: number, value: boolean }) {
  }
}

export class ApplySelectionToAll {
  static readonly type = '[Workspaces PopIn] Apply selection to all';

  constructor(public payload: { context: string, value: boolean }) {
  }
}

export class SelectRange {
  static readonly type = '[Workspaces PopIn] Select range';

  constructor(public payload: { context: string, from: number, to: number }) {
  }
}

export class AddWsToFavorite {
  static readonly type = '[Workspaces PopIn] Add workspace to favorite';

  constructor(public payload: { wsId, uwYear, workspaceName, programName, cedantName }) {
  }
}

export class DeleteWsFromFavorite {
  static readonly type = '[Workspaces PopIn] Delete workspace from favorite';

  constructor(public payload: { wsId, uwYear }) {
  }
}

export class PinWs {
  static readonly type = '[Workspaces PopIn] Pin Workspace';

  constructor(public payload: { wsId, uwYear, workspaceName, programName, cedantName }) {
  }
}

export class UnPinWs {
  static readonly type = '[Workspaces PopIn] UnPin Workspace';

  constructor(public payload: any) {
  }
}

