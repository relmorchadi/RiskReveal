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
                ctx.patchState(produce(ctx.getState(), draft => {
                    draft.data.fac = {
                        new: _.filter(data.content, item => item.carStatus === 'NEW'),
                        inProgress: _.filter(data.content, item => item.carStatus === 'IN PROGRESS'),
                        archived: _.filter(data.content, item => item.carStatus !== 'NEW' && item.carStatus === 'IN PROGRESS')
                    };
                }));
                return of(ctx.dispatch(new fromHD.LoadDashboardFacDataSuccessAction()));
            }),
            catchError(err => {
                return of( ctx.dispatch(new fromHD.LoadDashboardFacDataFailAction()));
            })
        )
    }

}
