
export class PatchWorkspaceMainStateAction {
  static readonly type = '[Workspace Main] Patch State Workspace';
  constructor(public payload: any) {}
}

export class OpenWorkspaceMainAction {
  static readonly type = '[Workspace Main] Open Workspace';
  constructor(public payload: any) {}
}

export class OpenNewWorkspacesAction {
  static readonly type = '[Workspace Main] Open New Workspace';
  constructor(public payload: any) {}
}

export class CloseWorkspaceMainAction {
  static readonly type = '[Workspace Main] Close Workspace';
  constructor(public payload: any) {}
}

export class LoadWorkspacesAction {
  static readonly type = '[Workspace Main] Load Workspace From Local';
  constructor() {}
}
