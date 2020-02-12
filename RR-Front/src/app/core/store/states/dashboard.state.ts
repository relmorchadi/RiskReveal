import {DashboardModel} from "../../model/dashboard.model";
import {Action, NgxsOnInit, Selector, State, StateContext} from "@ngxs/store";
import * as fromHD from "../actions";
import {DashboardApi} from "../../service/api/dashboard.api";
import {catchError, mergeMap, tap} from "rxjs/operators";
import * as moment from "moment";
import {forkJoin, of} from "rxjs";
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
        fac: {},
        treaty: {},
        dataCounter: {}
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
    static getDataCounter(state: DashboardModel) {
        return state.data.dataCounter;
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
        const {identifier, pageNumber, carStatus} = payload;

        const dataParams = {
            carStatus,
            entity: 1,
            pageNumber: pageNumber,
            pageSize: 50,
            selectionList: '',
            sortSelectedAction: '',
            sortSelectedFirst: false,
            userCode: "DEV",
            userDashboardWidgetId: identifier
        };

        return this.dashboardAPI.getFacDashboardResources(dataParams).pipe(
            mergeMap((data: any) => {
                const fixData = _.map(this._formatDate(data.content), item => ({...item, carStatus: _.startCase(_.capitalize(item.carStatus))}));
                ctx.patchState(produce(ctx.getState(), draft => {
                    draft.data.fac[identifier] = _.orderBy(fixData, item => item.carRequestId, 'desc');
                    draft.data.dataCounter[identifier] = data.refCount;
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

    }

    private _formatDate(data) {
        return _.map(data, item => {
            const formatCDate = moment(item.creationDate).format('DD-MM-YYYY');
            const formatLDate = moment(item.lastUpdateDate).format('DD-MM-YYYY');
            return {...item, creationDate: formatCDate, lastUpdateDate: formatLDate}
        })
    }

}
