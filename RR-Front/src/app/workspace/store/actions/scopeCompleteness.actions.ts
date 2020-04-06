export class LoadScopeCompletenessDataSuccess {
  static readonly type = '[ScopeCompleteness] Load Data Success';
  constructor(public payload?: any) {}
}

export class LoadScopeCompletenessPricingDataSuccess {
  static readonly type = '[ScopeCompleteness] Load Data Pricing Success';
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
