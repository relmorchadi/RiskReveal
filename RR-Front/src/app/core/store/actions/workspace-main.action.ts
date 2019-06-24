
export class PatchWorkspaceMainStateAction {
  static readonly type = '[Workspace Main] Patch State Workspace';
  constructor(public payload: any) {}
}

export class AppendNewWorkspaceMainAction {
  static readonly type = '[Workspace Main] Append New Workspaces';
  constructor(public payload: any) {}
}

export class OpenNewWorkspacesAction {
  static readonly type = '[Workspace Main] Open New Workspaces';
  constructor(public payload: any) {}
}

export class CloseWorkspaceMainAction {
  static readonly type = '[Workspace Main] Close Workspace';
  constructor(public payload: any) {}
}

export class SetWsRoutingAction {
  static readonly type = '[Workspace Main] Set workspace routing';
  constructor(public payload: any) {}
}

export class setTabsIndex {
  static readonly type = '[Workspace Main] Select Tabs Index';
  constructor(public payload: any) {}
}


export class SelectProjectAction {
  static readonly type = '[Workspace Project] Select Project';
  constructor(public payload: any) {}
}

export class LoadWorkspacesAction {
  static readonly type = '[Workspace Main] Load Workspace From Local';
  constructor() {}
}

export class PatchWorkspace {
  static readonly type = '[Workspace Main] Patch Workspace';
  constructor(public payload?: any) {}
}
export class AddNewProject {
  static readonly type = '[Workspace Main] Add new project';
  constructor(public payload?: any) {}
}

export class UpdateProject {
  static readonly type = '[Workspace Main] Update project';
  constructor(public payload?: any) {}
}

export class AddNewProjectSuccess {
  static readonly type = '[Workspace Main] Add new project success';
  constructor(public payload?: any) {}
}

export class AddNewProjectFail {
  static readonly type = '[Workspace Main] Add new project fail';
  constructor(public payload?: any) {}
}
