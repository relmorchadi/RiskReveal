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

export class PatchResultsAction {
  static readonly type = '[Risk Link] Patch Result';
  constructor(public payload: any) {}
}

export class PatchTargetFPAction {
  static readonly type = '[Risk Link] Patch Target For Financial Perspective';
  constructor(public payload: any) {}
}

export class PatchLinkingModeAction {
  static readonly type = '[Risk Link] Patch Linking Mode';
  constructor() {}
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

export class ToggleAnalysisLinkingAction {
  static readonly type = '[Risk Link] Select Linked Analysis';
  constructor(public payload: any) {}
}

export class TogglePortfolioLinkingAction {
  static readonly type = '[Risk Link] Select linked Portfolios';
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

export class DeleteEdmRdmAction {
  static readonly type = '[Risk Link] Delete Edm Or Rdm';
  constructor(public payload: any) {}
}

export class DeleteLinkAction {
  static readonly type = '[Risk Link] Delete Linking Between Analysis';
  constructor(public payload: any) {}
}

export class DeleteInnerLinkAction {
  static readonly type = '[Risk Link] Delete Inner Link Component';
  constructor(public payload: any) {}
}

export class UpdateStatusLinkAction {
  static readonly type = '[Risk Link] Update Link Status';
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

export class ApplyRegionPerilAction {
  static readonly type = '[Risk Link] Apply Region Peril To All';
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

export class SaveEditAnalysisAction {
  static readonly type = '[Risk Link] Save Edit On Analysis';
  constructor(public payload: any) {}
}

export class SaveEditPEQTAction {
  static readonly type = '[Risk Link] Save PEQT Changes';
  constructor(public payload: any) {}
}

export class CreateLinkingAction {
  static readonly type = '[Risk Link] Create Linking';
  constructor(public payload: any) {}
}

export class LoadFacDataAction {
  static readonly type = '[Risk Link] Load EDM And RDM For Fac Workspace';
  constructor(public payload?: any) {}
}

export class LoadBasicAnalysisFacAction {
  static readonly type = '[Risk Link] Load Basic Analysis For Fac Ws';
  constructor(public payload: any) {}
}

export class LoadDetailAnalysisFacAction {
  static readonly type = '[Risk Link] Load Detail Analysis For Fac Ws';
  constructor(public payload: any) {}
}

export class LoadPortfolioFacAction {
  static readonly type = '[Risk Link] Load Portfolio For Fac Ws';
  constructor(public payload: any) {}
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
