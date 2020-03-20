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
        dataCounter: {},
        assignedFac: {},
        assignedDataCounter: {}
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
    static getAssignedData(state: DashboardModel) {
        return state.data.assignedFac;
    }

    @Selector()
    static getAssignedDataCounter(state: DashboardModel) {
        return state.data.assignedDataCounter;
    }

    @Selector()
    static getSelectedDashboard(state: DashboardModel) {
        return state.tabs.selectedDashboard;
    }

    @Selector()
    static getSelectedTab(state: DashboardModel) {
        return state.tabs.tabIndex;
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
        const {selectedDashboard, tabIndex} = payload;
        const state = ctx.getState();
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
            filterByAnalyst: false,
            pageSize: 50,
            selectionList: '',
            sortSelectedAction: '',
            sortSelectedFirst: false,
            userDashboardWidgetId: identifier
        };

        return this.dashboardAPI.getFacDashboardResources(dataParams).pipe(
            tap((data: any) => {
                const fixData = _.map(this._formatDate(data.content), item => ({...item, carStatus: _.startCase(_.capitalize(item.carStatus)), contractID: item.contractName, contractName: item.contractId}));
                ctx.patchState(produce(ctx.getState(), draft => {
                    draft.data.fac[identifier] = fixData;
                    draft.data.dataCounter[identifier] = data.refCount;
                }));
            }),
            catchError(err => {
                return of( ctx.dispatch(new fromHD.LoadDashboardFacDataFailAction()));
            })
        )
    }

    @Action(fromHD.LoadDashboardAssignedFacDataAction)
    loadDashboardFacDataForAnalyst(ctx: StateContext<DashboardModel>, {payload}: fromHD.LoadDashboardAssignedFacDataAction) {
        const state = ctx.getState();
        const {identifier, pageNumber, carStatus} = payload;

        const dataParams = {
            carStatus,
            entity: 1,
            pageNumber: pageNumber,
            filterByAnalyst: true,
            pageSize: 50,
            selectionList: '',
            sortSelectedAction: '',
            sortSelectedFirst: false,
            userDashboardWidgetId: identifier
        };

        return this.dashboardAPI.getFacDashboardResources(dataParams).pipe(
            tap((data: any) => {
                const fixData = _.map(this._formatDate(data.content), item => ({...item, carStatus: _.startCase(_.capitalize(item.carStatus))}));
                ctx.patchState(produce(ctx.getState(), draft => {
                    draft.data.assignedFac[identifier] = fixData;
                    draft.data.assignedDataCounter[identifier] = data.refCount;
                }));
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
