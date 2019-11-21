export class ToggleRecentWsSelection {
  static readonly type = '[Workspace Header] Toggle Recent Selection Workspace';
  constructor(public payload?: any) {}
}

export class ToggleFavoriteWsSelection {
  static readonly type = '[Workspace Header] Toggle Favorite Selection Workspace';
  constructor(public payload?: any) {}
}

export class ToggleAssignedWsSelection {
  static readonly type = '[Workspace Header] Toggle Assigned Selection Workspace';
  constructor(public payload?: any) {}
}

export class TogglePinnedWsSelection {
  static readonly type = '[Workspace Header] Toggle Pinned Selection Workspace';
  constructor(public payload?: any) {}
}

export class ToggleFavoriteWsState {
  static readonly type = '[Workspace Header] Toggle State Favorite Workspace';
  constructor(public payload?: any) {}
}

export class TogglePinnedWsState {
  static readonly type = '[Workspace Header] Toggle State Pinned Workspace';
  constructor(public payload?: any) {}
}

export class SearchRecentWsAction {
  static readonly type = '[Workspace Header] Search Recent Workspace';
  constructor(public payload?: any) {}
}

export class SearchFavoriteWsAction {
  static readonly type = '[Workspace Header] Search Favorite Workspace';
  constructor(public payload?: any) {}
}

export class SearchAssignedWsAction {
  static readonly type = '[Workspace Header] Search Assigned Workspace';
  constructor(public payload?: any) {}
}

export class SearchPinnedWsAction {
  static readonly type = '[Workspace Header] Search Pinned Workspace';
  constructor(public payload?: any) {}
}

export class LoadFavoriteWorkspace {
  static readonly type = '[Workspace Header] Load Favorite Workspace';
  constructor(public payload?: any) {}
}

export class LoadAssignedWorkspace {
  static readonly type = '[Workspace Header] Load Assigned Workspace';
  constructor(public payload?: any) {}
}

export class LoadRecentWorkspace {
  static readonly type = '[Workspace Header] Load Recent Workspace';
  constructor(public payload?: any) {}
}

export class LoadPinnedWorkspace {
  static readonly type = '[Workspace Header] Load Pinned Workspace';
  constructor(public payload?: any) {}
}

export class LoadWsStatusCount {
  static readonly type = '[Workspace Header] Load Status For Workspaces';
  constructor(public payload?: any) {}
}


export class DeleteTask {
  static readonly type = '[Tasks PopIn] Delete Task';

  constructor(public payload: { id }) {
  }
}

export class PauseTask {
  static readonly type = '[Tasks PopIn] Pause Task';

  constructor(public payload: { id }) {
  }
}

export class ResumeTask {
  static readonly type = '[Tasks PopIn] Resume Task';

  constructor(public payload: { id }) {
  }
}

export class DeleteNotification {
  static readonly type = '[Notification PopIn] Delete Notifications';

  constructor(public payload: { target }) {
  }
}
