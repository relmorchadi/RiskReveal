
export class PatchWorkspaceMainStateAction {
  static readonly type = '[Workspace Main] Patch State Workspace';
  constructor(public payload: any) {}
}

export class OpenWorkspaceMainAction {
  static readonly type = '[Workspace Main] Open Workspace';
  constructor(public payload: any) {}
}

export class CloseWorkspaceMainAction {
  static readonly type = '[Workspace Main] Close Workspace';
  constructor(public payload: any) {}
}
