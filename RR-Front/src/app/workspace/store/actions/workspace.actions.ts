export class loadWSFromLocalStorage {
  static readonly type = '[Workspace] Load WS from Local Storage';

  constructor() {
  }
}

export class loadWS {
  static readonly type = '[Workspace] Load WS';

  constructor(public payload?: any) {
  }
}

export class loadWsSuccess {
  static readonly type = '[Workspace] Load WS Success';

  constructor(public payload?: any) {
  }
}

export class loadWsFail {
  static readonly type = '[Workspace] Load WS Fail';

  constructor(public payload?: any) {
  }
}

export class openWS {
  static readonly type = '[Workspace] OpenWS';

  constructor(public payload?: any) {
  }
}

export class openMultiWS {
  static readonly type = '[Workspace] Multiple OpenWS';

  constructor(public payload?: any[]) {
  }
}

export class closeWS {
  static readonly type = '[Workspace] CloseWS';

  constructor(public payload?: any) {
  }
}

export class setCurrentTab {
  static readonly type = '[Workspace] Set Current Tab';

  constructor(public payload?: any) {
  }
}


export class ToggleWsDetails {
  static readonly type = '[Workspace] Toggle workspace details';

  constructor(public wsId: string) {
  }
}

export class ToggleWsLeftMenu {
  static readonly type = '[Workspace] Toggle workspace left menu';

  constructor(public wsId: string) {
  }
}

export class UpdateWsRouting {
  static readonly type = '[Workspace] Update workspace routing';

  constructor(public wsId: string, public route: string) {
  }
}


export class MarkWsAsFavorite {
  static readonly type = '[Workspace] Mark workspace as favorite';

  constructor(public payload: { wsIdentifier: string }) {
  }
}

export class MarkWsAsNonFavorite {
  static readonly type = '[Workspace] Mark workspace as non favorite';

  constructor(public payload: { wsIdentifier: string }) {
  }
}


export class MarkWsAsPinned {
  static readonly type = '[Workspace] Mark workspace as Pinned';

  constructor(public payload: { wsIdentifier: string }) {
  }
}

export class MarkWsAsNonPinned {
  static readonly type = '[Workspace] Mark workspace as non pinned';

  constructor(public payload: { wsIdentifier: string }) {
  }
}
