
export class loadWS {
  static readonly type = '[Workspace] Load WS';
  constructor(public payload?: any) {}
}

export class loadWsSuccess {
  static readonly type = '[Workspace] Load WS Success';
  constructor(public payload?: any) {}
}

export class loadWsFail {
  static readonly type = '[Workspace] Load WS Fail';
  constructor(public payload?: any) {}
}

export class openWS {
  static readonly type = '[Workspace] OpenWS';
  constructor(public payload?: any) {}
}

export class closeWS {
  static readonly type = '[Workspace] CloseWS';
  constructor(public payload?: any) {}
}

export class setCurrentTab {
  static readonly type = '[Workspace] Set Current Tab'
  constructor(public payload?: any) {}
}

