export class InitWorkspace {
  static readonly type = '[Workspace] Init Opened Workspaces For User';
  constructor(public payload?: any) {}
}

export class loadWorkSpaceAndPlts {
  static readonly type = '[Workspace] Load Workspace And Plts';
  constructor(public payload?: any) {}
}

export class LoadWS {
  static readonly type = '[Workspace] Load WS';
  constructor(public payload?: any) {}
}

export class LoadWsSuccess {
  static readonly type = '[Workspace] Load WS Success';
  constructor(public payload?: any) {}
}

export class LoadWsFail {
  static readonly type = '[Workspace] Load WS Fail';
  constructor(public payload?: any) {}
}

export class OpenWS {
  static readonly type = '[Workspace] OpenWS';
  constructor(public payload?: any) {}
}

export class OpenMultiWS {
  static readonly type = '[Workspace] Multiple OpenWS';
  constructor(public payload?: any) {}
}

export class CloseWS {
  static readonly type = '[Workspace] CloseWS';
  constructor(public payload?: any) {}
}

export class SetCurrentTab {
  static readonly type = '[Workspace] Set Current Tab';
  constructor(public payload?: any) {}
}

export class ToggleWsDetails {
  static readonly type = '[Workspace] Toggle workspace details';
  constructor(public wsId: string) {}
}

export class ToggleWsLeftMenu {
  static readonly type = '[Workspace] Toggle workspace left menu';
  constructor(public payload?: any) {}
}

export class UpdateWsRouting {
  static readonly type = '[Workspace] Update workspace routing';
  constructor(public wsId: string, public route: string) {}
}

export class ToggleFavorite {
  static readonly type = '[Workspace] Toggle Workspace Favorite State';
  constructor(public payload?: any) {}
}

export class TogglePinned {
  static readonly type = '[Workspace] Toggle Workspace Pinned State';
  constructor(public payload?: any) {}
}

export class ToggleProjectSelection {
  static readonly type = '[Workspace] Toggle project selection';
  constructor(public payload: { wsIdentifier: string, projectIndex: number }) {}

}

export class SelectProject {
  static readonly type = '[Workspace] Select Project';

  constructor(public payload: { wsIdentifier: string, projectId: number }) {
  }
}

export class AddNewFacProject {
  static readonly type = '[Workspace] Add new Fac project';
  constructor(public payload?: any) {}
}

export class AddNewProject {
  static readonly type = '[Workspace] Add new project';
  constructor(public payload: { project, wsId, uwYear, id }) {}
}

export class AddNewProjectSuccess {
  static readonly type = '[Workspace] Add new project Success';
  constructor(public payload: any) {}
}

export class AddNewProjectFail {
  static readonly type = '[Workspace] Add new project Fails';
  constructor(public payload: any) {}
}

export class EditProject {
  static readonly type = '[Workspace] Edit project ';
  constructor(public payload: any) {}
}

export class DeleteFacProject {
  static readonly type = '[Workspace] Delete Fac project';
  constructor(public payload: any) {}
}

export class DeleteProject {
  static readonly type = '[Workspace] Delete project';
  constructor(public payload: any) {}
}

export class DeleteProjectSuccess {
  static readonly type = '[Workspace] Delete project Success';
  constructor(public payload: any) {}
}

export class DeleteProjectFails {
  static readonly type = '[Workspace] Delete project fails';

  constructor(public payload: any) {
  }
}

export class LoadProjectByWorkspace {
  static readonly type = '[Workspace] Load project by workspace';

  constructor(public payload: any) {
  }
}

export class LoadProjectByWorkspaceSuccess {
  static readonly type = '[Workspace] Load project by workspace success';

  constructor(public payload: any) {
  }
}

export class LoadProjectByWorkspaceFail {
  static readonly type = '[Workspace] Load project by workspace fail';

  constructor(public payload: any) {
  }
}





