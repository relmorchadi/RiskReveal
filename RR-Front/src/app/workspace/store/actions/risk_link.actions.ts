export class PatchRiskLinkAction {
  static readonly type = '[Risk Link] Patch State Risk Link';
  constructor(public payload: any) {}
}

export class PatchRiskLinkCollapseAction {
  static readonly type = '[Risk Link] Patch State Collapse Risk Link';
  constructor(public payload: any) {}
}

export class PatchRiskLinkDisplayAction {
  static readonly type = '[Risk Link] Patch State display Risk Link';
  constructor(public payload: any) {}
}

export class PatchRiskLinkFinancialPerspectiveAction {
  static readonly type = '[Risk Link] Patch State Financial Perspective Risk Link';
  constructor(public payload: any) {}
}

export class SelectRiskLinkEDMAndRDM {
  static readonly type = '[Risk Link] Select Risk Link EDM And RDM';
  constructor() {}
}

export class ToggleRiskLinkEDMAndRDM {
  static readonly type = '[Risk Link] Toggle Risk Link EDM And RDM';
  constructor(public payload: any) {}
}

export class LoadRiskLinkDataAction {
  static readonly type = '[Risk Link] Load Risk Link Data';
  constructor() {}
}
