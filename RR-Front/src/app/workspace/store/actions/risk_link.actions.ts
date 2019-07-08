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

export class PatchAddToBasketStateAction {
  static readonly type = '[Risk Link] Patch Add To Basket Button State';
  constructor() {}
}

export class PatchTargetFPAction {
  static readonly type = '[Risk Link] Patch Target For Financial Perspective';
  constructor(public payload: any) {}
}

export class ToggleRiskLinkEDMAndRDMAction {
  static readonly type = '[Risk Link] Toggle Risk Link EDM And RDM';
  constructor(public payload: any) {}
}

export class ToggleRiskLinkPortfolioAction {
  static readonly type = '[Risk Link] Toggle Analysis And Portfolio';
  constructor(public payload: any) {}
}

export class ToggleRiskLinkAnalysisAction {
  static readonly type = '[Risk Link] Toggle Analysis';
  constructor(public payload: any) {}
}

export class ToggleRiskLinkResultAction {
  static readonly type = '[Risk Link] Toggle Result';
  constructor(public payload: any) {}
}

export class ToggleRiskLinkSummaryAction {
  static readonly type = '[Risk Link] Toggle Summary';
  constructor(public payload: any) {}
}

export class ToggleRiskLinkFPStandardAction {
  static readonly type = '[Risk Link] Toggle Financial Perspective Standard';
  constructor(public payload: any) {}
}

export class ToggleRiskLinkFPAnalysisAction {
  static readonly type = '[Risk Link] Toggle Financial Perspective Analysis';
  constructor(public payload: any) {}
}

export class ToggleRiskLinkEDMAndRDMSelectedAction {
  static readonly type = '[Risk Link] Select Display Portfolio Or Analysis';
  constructor(public payload: any) {}
}

export class ToggleAnalysisForLinkingAction {
  static readonly type = '[Risk Link] Select Target RDM For Linking';
  constructor(public payload: any) {}
}


export class AddToBasketAction {
  static readonly type = '[Risk Link] Add Analysis and Portfolio To Basket';
  constructor() {}
}

export class DeleteFromBasketAction {
  static readonly type = '[Risk Link] Delete From Basket';
  constructor(public payload: any) {}
}

export class SelectRiskLinkEDMAndRDMAction {
  static readonly type = '[Risk Link] Select Risk Link EDM And RDM';
  constructor() {}
}

export class ApplyFinancialPerspectiveAction {
  static readonly type = '[Risk Link] Apply Financial Perspective For Analysis';
  constructor(public payload: any) {}
}

export class SearchRiskLinkEDMAndRDMAction {
  static readonly type = '[Risk Link] Search EDMs And RDMs';
  constructor(public payload: any) {}
}

export class LoadFinancialPerspectiveAction {
  static readonly type = '[Risk Link] Load Financial Perspective Data';
  constructor(public payload: any) {}
}

export class RemoveFinancialPerspectiveAction {
  static readonly type = '[Risk Link] Remove Financial Perspective';
  constructor(public payload: any) {}
}

export class SaveFinancialPerspectiveAction {
  static readonly type = '[Risk Link] Save Financial Perspective Data';
  constructor() {}
}

export class LoadAnalysisForLinkingAction {
  static readonly type = '[Risk Link] Load Analysis For Linking';
  constructor(public payload: any) {}
}

export class LoadPortfolioForLinkingAction {
  static readonly type = '[Risk Link] Load Portfolio For Linking';
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
