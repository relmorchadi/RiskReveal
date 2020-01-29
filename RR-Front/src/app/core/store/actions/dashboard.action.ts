export class LoadDashboardsAction {
    static readonly type = '[Dashboard] Dashboards For User';
    constructor(public payload?: any) {}
}

export class CreatNewDashboardAction {
    static readonly type = '[Dashboard] Create New Dashboard For User';
    constructor(public payload?: any) {}
}

export class DeleteDashboardAction {
    static readonly  type = '[Dashboard] Delete Dashboard For User';
    constructor(public payload?: any) {}
}

export class UpdateDashboardAction {
    static readonly type = '[Dashboard] Update Dashboard Data';
    constructor(public payload?: any) {}
}

export class SaveDashboardStateAction {
    static readonly type = '[Dashboard] Save Dashboard State';
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

export class LoadDashboardFacDataSuccessAction {
    static readonly type = '[Dashboard] Dashboard Fac Load Data Success';
    constructor(public payload?: any) {}
}

export class FilterFacData {
    static readonly type = '[Dashboard] Filter Data';
    constructor(public payload?: any) {}
}
