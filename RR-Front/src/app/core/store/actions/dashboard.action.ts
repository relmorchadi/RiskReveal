export class LoadDashboardsAction {
    static readonly type = '[Dashboard] Dashboards For User';
    constructor(public payload?: any) {}
}

export class LoadReferenceWidget {
    static readonly type = '[Dashboard] Load Reference Widget For Dashboard';
    constructor(public payload?: any) {}
}

export class CreateNewDashboardAction {
    static readonly type = '[Dashboard] Create New Dashboard For User';
    constructor(public payload?: any) {}
}

export class CreateWidgetAction {
    static readonly type = '[Dashboard] Create Widget For Dashboard';
    constructor(public payload?: any) {}
}

export class DeleteDashboardAction {
    static readonly  type = '[Dashboard] Delete Dashboard For User';
    constructor(public payload?: any) {}
}

export class DeleteWidgetAction {
    static readonly type = '[Dashboard] Delete Widget From Dashboard';
    constructor(public payload?: any) {}
}

export class DeleteAllWidgetByRefAction {
    static readonly type = '[Dashboard] Delete All Widget By Reference From Dashboard';
    constructor(public payload?: any) {}
}

export class DuplicateWidgetAction {
    static readonly type = '[Dashboard] Duplicate Widget Into Dashboard';
    constructor(public payload?: any) {}
}

export class UpdateDashboardAction {
    static readonly type = '[Dashboard] Update Dashboard Data';
    constructor(public payload?: any) {}
}

export class UpdateWidgetAction {
    static readonly type = '[Dashboard] Update Widget Data';
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
