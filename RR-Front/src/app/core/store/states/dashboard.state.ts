import {DashboardModel} from "../../model/dashboard.model";
import {Action, NgxsOnInit, Selector, State, StateContext} from "@ngxs/store";
import * as fromHD from "../actions";
import {DashboardApi} from "../../service/api/dashboard.api";
import {catchError, mergeMap, tap} from "rxjs/operators";
import * as moment from "moment";
import {of} from "rxjs";
import produce from "immer";
import * as _ from 'lodash';

const initiateState: DashboardModel = {
    config: null,
    tabs: {
        selectedDashboard: null,
        tabIndex: 0
    },
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
    static getSelectedDashboard(state: DashboardModel) {
        return state.tabs.selectedDashboard;
    }

    @Selector()
    static getRefData(state: DashboardModel) {
        return state.referenceWidget;
    }

    @Action(fromHD.LoadReferenceWidget)
    loadReferenceWidget(ctx: StateContext<DashboardModel>, {payload}: fromHD.LoadReferenceWidget) {
        return this.dashboardAPI.getReferenceWidget().pipe(
            mergeMap((data: any) => {
                console.log(data);
                ctx.patchState(produce(ctx.getState(), draft => {
                    draft.referenceWidget.fac = _.filter(this._formatDate(data), item => item.widgetMode === 'Fac');
                    draft.referenceWidget.treaty = _.filter(data, item => item.widgetMode === 'Treaty');
                }));
                return of();
            })
        )
    }

    @Action(fromHD.ChangeSelectedDashboard)
    changeSelected(ctx: StateContext<DashboardModel>, {payload}: fromHD.ChangeSelectedDashboard) {
        const {selectedDashboard} = payload;
        const state = ctx.getState();
        const tabIndex = _.get(payload, 'tabIndex', state.tabs.tabIndex);
        ctx.patchState(produce(ctx.getState(), draft => {
            draft.tabs = {selectedDashboard, tabIndex};
        }));
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
                const fixData = _.map(this._formatDate(data.content), item => ({...item, carStatus: _.startCase(_.capitalize(item.carStatus))}));
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

    private _formatDate(data) {
        return _.map(data, item => {
            const formatCDate = moment(item.creationDate).format('DD-MM-YYYY');
            const formatLDate = moment(item.lastUpdateDate).format('DD-MM-YYYY');
            return {...item, creationDate: formatCDate, lastUpdateDate: formatLDate}
        })
    }

}
