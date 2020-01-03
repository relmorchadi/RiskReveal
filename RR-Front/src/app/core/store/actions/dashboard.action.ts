export class LoadDashboardFacDataAction {
    static readonly type = '[Dashboard] Dashboard Fac Load Data';
    constructor(public payload?: any) {}
}

export class LoadDashboardFacDataFailAction {
    static readonly type = '[Dashboard] Dashboard Fac Load Data Fail';
    constructor(public payload?: any) {}
}

export class LoadDashboardFacDataSuccessAction {
    static readonly type = '[Dashboard] Dashboard Fac Load Data Success';
    constructor(public payload?: any) {}
}
