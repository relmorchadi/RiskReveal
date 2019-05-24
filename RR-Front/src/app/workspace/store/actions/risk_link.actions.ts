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

export class SelectRiskLinkEDMAndRDMAction {
  static readonly type = '[Risk Link] Select Risk Link EDM And RDM';
  constructor() {}
}

export class ToggleRiskLinkEDMAndRDMAction {
  static readonly type = '[Risk Link] Toggle Risk Link EDM And RDM';
  constructor(public payload: any) {}
}

export class ToggleRiskLinkEDMAndRDMSelectedAction {
  static readonly type = '[Risk Link] Select Display Portfolio Or Analysis';
  constructor(public payload: any) {}
}

export class SelectRiskLinkAnalysisAndPortfolioAction {
  static readonly type = '[Risk Link] Select Analysis And Portfolio';
  constructor(public payload: any) {}
}

export class ToggleRiskLinkAnalysisAndPortfolioAction {
  static readonly type = '[Risk Link] Toggle Analysis And Portfolio';
  constructor(public payload: any) {}
}


export class SearchRiskLinkEDMAndRDMAction {
  static readonly type = '[Risk Link] Search EDMs And RDMs';
  constructor(public payload: any) {}
}

export class LoadRiskLinkDataAction {
  static readonly type = '[Risk Link] Load Risk Link Data';
  constructor() {}
}

export class LoadRiskLinkAnalysisDataAction {
  static readonly type = '[Risk Link] Load Risk Link Analysis Data';
  constructor(public payload: any) {}
}

export class LoadRiskLinkPortfolioDataAction {
  static readonly type = '[Risk Link] Load Risk Link portfolio Data';
  constructor(public payload: any) {}
}
