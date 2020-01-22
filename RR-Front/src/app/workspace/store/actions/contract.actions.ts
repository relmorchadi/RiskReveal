export class ToggleFacDivisonAction {
  static readonly type = '[Contract] Toggle Division Selection';
  constructor(public payload?: any) {}
}

export class LoadContractAction {
  static readonly type = '[Contract] Load Contract Data';
  constructor(public payload?: any) {}
}

export class LoadContractFacAction {
  static readonly type = '[Contract] Load Contract Fac Data';
  constructor(public payload?: any) {}
}
