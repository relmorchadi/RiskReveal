export class LoadReferenceWidget {
    static readonly type = '[Dashboard] Load Reference Widget For Dashboard';
    constructor(public payload?: any) {}
}

export class ChangeSelectedDashboard {
    static readonly type = '[Dashboard] Change Selected Dashboard';
    constructor(public payload?: any) {}
}

export class LoadDashboardFacDataAction {
    static readonly type = '[Dashboard] Dashboard Fac Load Data';
    constructor(public payload?: any) {}
}

export class LoadDashboardFacDataFailAction {
    static readonly type = '[Dashboard] Dashboard Fac Load Data Fail';
    constructor(public payload?: any) {}
}

export class FilterFacData {
    static readonly type = '[Dashboard] Filter Data';
    constructor(public payload?: any) {}
}
