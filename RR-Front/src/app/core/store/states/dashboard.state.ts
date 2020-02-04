import {DashboardModel} from "../../model/dashboard.model";
import {Action, NgxsOnInit, Selector, State, StateContext} from "@ngxs/store";
import * as fromHD from "../actions";
import {DashboardApi} from "../../service/api/dashboard.api";
import {catchError, mergeMap, tap} from "rxjs/operators";
import {of} from "rxjs";
import produce from "immer";
import * as _ from 'lodash';

const initiateState: DashboardModel = {
    config: null,
    tabs: null,
    dashboards: null,
    referenceWidget: {
        fac: null,
        treaty: null
    },
    data: {
        fac: null,
        treaty: null
    }
};

@State<DashboardModel>({
    name: 'dashboardModel',
    defaults: initiateState
})
export class DashboardState implements NgxsOnInit {
    ctx = null;

    constructor(private dashboardAPI: DashboardApi) {

    }

    ngxsOnInit(ctx?: StateContext<DashboardState>): void | any {
        this.ctx = ctx;
    }

    @Selector()
    static getFacData(state: DashboardModel) {
        return state.data.fac;
    }

    @Selector()
    static getDashboards(state: DashboardModel) {
        return state.dashboards;
    }

    @Selector()
    static getRefData(state: DashboardModel) {
        return state.referenceWidget;
    }

    @Action(fromHD.LoadDashboardsAction)
    loadDashboards(ctx: StateContext<DashboardModel>, {payload}: fromHD.LoadDashboardsAction) {
        const {userId} = payload;
        return this.dashboardAPI.getDashboards(userId).pipe(
            mergeMap(data => {
                ctx.patchState(produce(ctx.getState(), draft => {
                    let dashboards = [];
                    _.forEach(data, item => {
                        dashboards = [...dashboards, this._formatData(item)]
                    });
                    draft.dashboards = dashboards;
                }));
                return of();
            })
        )
    }

    @Action(fromHD.CreateNewDashboardAction)
    creatNewDashboard(ctx: StateContext<DashboardModel>, {payload}: fromHD.CreateNewDashboardAction) {
        const state = ctx.getState();
        return this.dashboardAPI.creatDashboards(payload).pipe(
            tap(data => {
                ctx.patchState(produce(ctx.getState(), draft => {
                    draft.dashboards = [...state.dashboards, this._formatData(data)];
                }));
            })
        )
    }

    @Action(fromHD.CreateWidgetAction)
    createWidgetForDash(ctx: StateContext<DashboardModel>, {payload}: fromHD.CreateWidgetAction) {
        const state = ctx.getState();
        const {selectedDashboard, widget} = payload;
        console.log(widget);
        return this.dashboardAPI.createWidget(widget).pipe(
            tap((data: any) => {
                const {userDashboardWidget, columns} = data;
                ctx.patchState(produce(ctx.getState(), draft => {
                    draft.dashboards = _.map(state.dashboards, item => {
                        if (item.id === selectedDashboard) {
                            return {...item, widgets: [...item.widgets, {...this._formatWidget(userDashboardWidget),
                                    columns: this._formatColumns(columns)}]
                            };
                        }
                        return item;
                    });
                }));
            })
        )
    }

    @Action(fromHD.DeleteDashboardAction)
    deleteDashboard(ctx: StateContext<DashboardModel>, {payload}: fromHD.DeleteDashboardAction) {
        const {dashboardId} = payload;
        const state = ctx.getState();
        return this.dashboardAPI.deleteDashboards(dashboardId).pipe(
            tap(data => {
                ctx.patchState(produce(ctx.getState(), draft => {
                    draft.dashboards = _.filter(state.dashboards, item => item.id !== dashboardId);
                }));
            })
        )
    }

    @Action(fromHD.DeleteWidgetAction)
    deleteWidget(ctx: StateContext<DashboardModel>, {payload}: fromHD.DeleteWidgetAction) {
        const {dashboardWidgetId, selectedDashboard} = payload;
        const state = ctx.getState();
        return this.dashboardAPI.deleteWidget(dashboardWidgetId).pipe(
            tap(data => {
                ctx.patchState(produce(ctx.getState(), draft => {
                    draft.dashboards = _.map(state.dashboards, item => {
                        if (item.id === selectedDashboard.id) {
                            return {...item, widget: _.filter(item.widget, widgetItem =>
                                    widgetItem.userDashboardWidgetId !== dashboardWidgetId)};
                        }
                        return item;
                    });
                }));
            })
        )
    }

    @Action(fromHD.UpdateDashboardAction)
    updateDashboard(ctx: StateContext<DashboardModel>, {payload}: fromHD.UpdateDashboardAction) {
        const state = ctx.getState();
        const {dashboardId, updatedDashboard} = payload;
        return this.dashboardAPI.updateDashboard(dashboardId, updatedDashboard).pipe(
            tap(data => {
                const frontData = {
                    ...updatedDashboard,
                    name: updatedDashboard.dashboardName
                };
                ctx.patchState(produce(ctx.getState(), draft => {
                    draft.dashboards = _.map(state.dashboards, item => {
                        return item.id === dashboardId ? _.merge({}, item, frontData) : item;
                    });
                }));
            })
        )
    }

    @Action(fromHD.LoadReferenceWidget)
    loadReferenceWidget(ctx: StateContext<DashboardModel>, {payload}: fromHD.LoadReferenceWidget) {
        return this.dashboardAPI.getReferenceWidget().pipe(
            mergeMap((data: any) => {
                console.log(data);
                ctx.patchState(produce(ctx.getState(), draft => {
                    draft.referenceWidget.fac = _.filter(data, item => item.widgetMode === 'Fac');
                    draft.referenceWidget.treaty = _.filter(data, item => item.widgetMode === 'Treaty');
                }));
                return of();
            })
        )
    }

    @Action(fromHD.LoadDashboardFacDataAction)
    loadDashboardFacData(ctx: StateContext<DashboardModel>, {payload}: fromHD.LoadDashboardFacDataAction) {
        const state = ctx.getState();
        const dataFilters = {
            filterConfig: payload || {},
            pageNumber: 0,
            size: 1000,
            sortConfig: []
        };
        return this.dashboardAPI.getFacDashboardResources(dataFilters).pipe(
            mergeMap((data: any) => {
                const fixData = _.map(data.content, item => ({...item, carStatus: _.startCase(_.capitalize(item.carStatus))}));
                ctx.patchState(produce(ctx.getState(), draft => {
                    draft.data.fac = {
                        new: _.filter(fixData, item => item.carStatus === 'New'),
                        inProgress: _.filter(fixData, item => item.carStatus === 'In Progress'),
                        archived: _.filter(fixData, item => item.carStatus !== 'New' && item.carStatus !== 'In Progress')
                    };
                }));
                return of(ctx.dispatch(new fromHD.LoadDashboardFacDataSuccessAction()));
            }),
            catchError(err => {
                return of( ctx.dispatch(new fromHD.LoadDashboardFacDataFailAction()));
            })
        )
    }

    @Action(fromHD.FilterFacData)
    filterFacData(ctx: StateContext<DashboardModel>, {payload}: fromHD.FilterFacData) {
        const {data, scope} = payload;
        const dataFilters = {
            filterConfig: data || {},
            pageNumber: 0,
            size: 50,
            sortConfig: []
        };
        return this.dashboardAPI.getFacDashboardResources(dataFilters).pipe(
            mergeMap((data: any) => {
                const fixData = _.map(data.content, item => ({...item, carStatus: _.startCase(_.capitalize(item.carStatus))}));
                ctx.patchState(produce(ctx.getState(), draft => {
                    switch(scope) {
                        case 'New':
                            draft.data.fac.new = _.filter(fixData, item => item.carStatus === 'New');
                            break;
                        case 'In Progress':
                            draft.data.fac.inProgress = _.filter(fixData, item => item.carStatus === 'In Progress');
                            break;
                        case 'archived':
                            draft.data.fac.archived = _.filter(fixData, item => item.carStatus !== 'New' && item.carStatus !== 'In Progress');
                            break;
                    }
                }));
                return of();
            }),
            catchError(err => {
                return of(err);
            })
        )
    }

    private _formatData(data) {
        const userDashboard = _.get(data, 'userDashboard', data);
        return {
            ...userDashboard,
            id: userDashboard.userDashboardId,
            name: userDashboard.dashboardName,
            widgets: _.map(_.get(data, 'widgets', []), (dashWidget: any) => {
                const widget = dashWidget.userDashboardWidget;
                return {
                    ...this._formatWidget(widget),
                    columns: this._formatColumns(dashWidget.columns)
                }
            }),
        }
    }

    private _formatWidget(widget) {
        return {
            ...widget,
            id: widget.userDashboardWidgetId,
            name: widget.userAssignedName,
            position: {
                cols: widget.colSpan,
                rows: widget.rowSpan,
                x: widget.colPosition,
                y: widget.rowPosition,
                minItemRows: widget.minItemRows,
                minItemCols: widget.minItemCols
            }
        }
    }

    private _formatColumns(columns) {
        return _.map(_.orderBy(columns, col => col.dashboardWidgetColumnOrder, 'asc'), item => ({
            ...item,
            columnId: item.userDashboardWidgetColumnId,
            WidgetId: item.userDashboardWidgetId,
            field: item.dashboardWidgetColumnId,
            header: item.columnHeader,
            width: item.dashboardWidgetColumnWidth + 'px',
            display: item.visible,
            filtered: true,
            sorted: true,
        }))
    }

}
