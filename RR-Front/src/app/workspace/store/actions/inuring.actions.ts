
const namespace:string='Inuring';

export class OpenInuringPackage {
  static readonly type = `[${namespace}] Open inuring package`;
  constructor(public payload: any) {}
}

export class CloseInuringPackage {
  static readonly type = `[${namespace}] Close inuring package`;
  constructor(public payload: any) {}
}

export class AddInputNode {
  static readonly type = `[${namespace}] Add Input Node`;
  constructor(public payload: any) {}
}

export class AddJoinNode {
  static readonly type = `[${namespace}] Add Join Node`;
  constructor(public payload: any) {}
}
