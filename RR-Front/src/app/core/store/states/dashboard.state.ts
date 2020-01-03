import {DashboardModel} from "../../model/dashboard.model";
import {NgxsOnInit, State, StateContext} from "@ngxs/store";

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

    constructor() {

    }

    ngxsOnInit(ctx?: StateContext<DashboardState>): void | any {
        this.ctx = ctx;
    }

}
