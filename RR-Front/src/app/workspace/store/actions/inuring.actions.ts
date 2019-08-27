
const namespace:string='Inuring';

export class OpenInuringPackage {
  static readonly type = `[${namespace}] Open inuring package`;
  constructor(public payload: any) {}
}

export class CloseInuringPackage {
  static readonly type = `[${namespace}] Close inuring package`;
  constructor(public payload: any) {}
}

