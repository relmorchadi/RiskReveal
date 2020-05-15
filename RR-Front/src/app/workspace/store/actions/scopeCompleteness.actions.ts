export class LoadScopeCompletenessData {
  static readonly type = '[ScopeCompleteness] Load Data Success';
  constructor(public payload?: any) {}
}

export class LoadScopeCompletenessPricingData {
  static readonly type = '[ScopeCompleteness] Load Data Pricing Success';
  constructor(public payload?: any) {}
}

export class LoadScopeCompletenessPendingData {
  static readonly type = '[ScopeCompleteness] Load Data Pending Success';
  constructor(public payload?: any) {}
}

export class LoadScopeCompletenessAccumulationInfo {
  static readonly type = '[ScopeCompleteness] Load Data Accumulation Info';
  constructor(public payload?: any) {}
}

export class LoadScopePLTsData {
  static readonly type = '[ScopeCompleteness] Load PLTs For Scope';
  constructor(public payload?: any) {}
}

export class PublishToPricingFacProject {
  static readonly type = '[ScopeCompleteness] Publish To Pricing Fac Project';
  constructor(public payload?: any) {}
}

export class PatchScopeOfCompletenessState {
  static readonly type = '[ScopeCompleteness] Patch Scope Of Completeness State';
  constructor(public payload?: any) {}
}

export class OverrideActiveAction {
  static readonly type = '[ScopeCompleteness] Override Region / RAP';
  constructor(public payload?: any) {}
}

export class OverrideDeleteAction {
  static readonly type = '[ScopeCompleteness] Delete Override';
  constructor(public payload?: any) {}
}

export class SelectScopeProject {
  static readonly type = '[ScopeCompleteness] Select Scope Project';
  constructor(public payload?: any) {}
}

export class AttachPLTsForScope {
  static readonly type = '[ScopeCompleteness] Attach PLT For Scope';
  constructor(public payload?: any) {}
}
