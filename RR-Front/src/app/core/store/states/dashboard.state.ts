import {DashboardModel} from "../../model/dashboard.model";
import {Action, NgxsOnInit, Selector, State, StateContext} from "@ngxs/store";
import * as fromHD from "../actions";
import {DashboardApi} from "../../service/api/dashboard.api";
import {catchError, mergeMap} from "rxjs/operators";
import {of} from "rxjs";
import produce from "immer";
import * as _ from 'lodash';

const initiateState: DashboardModel = {
    config: null,
    tabs: null,
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

    @Action(fromHD.LoadDashboardsAction)
    loadDashboards(ctx: StateContext<DashboardModel>, {payload}: fromHD.LoadDashboardsAction) {

    }

    @Action(fromHD.CreatNewDashboardAction)
    creatNewDashboard(ctx: StateContext<DashboardModel>, {payload}: fromHD.CreatNewDashboardAction) {

    }

    @Action(fromHD.DeleteDashboardAction)
    deleteDashboard(ctx: StateContext<DashboardModel>, {payload}: fromHD.DeleteDashboardAction) {

    }

    @Action(fromHD.UpdateDashboardAction)
    updateDashboard(ctx: StateContext<DashboardModel>, {payload}: fromHD.UpdateDashboardAction) {

    }

    @Action(fromHD.SaveDashboardStateAction)
    saveDashboard(ctx: StateContext<DashboardModel>, {payload}: fromHD.SaveDashboardStateAction) {

    }

    @Action(fromHD.LoadDashboardFacDataAction)
    loadDashboardFacData(ctx: StateContext<DashboardModel>, {payload}: fromHD.LoadDashboardFacDataAction) {
        const state = ctx.getState();
        const dataFilters = {
            filterConfig: payload || {},
            pageNumber: 0,
            size: 50,
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

}
