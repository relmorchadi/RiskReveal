import {AuthModel} from "../../model/auth.model";
import {Action, NgxsOnInit, Selector, State, StateContext, Store} from "@ngxs/store";
import * as fromHD from "../actions";
import {AuthenticationApi} from "../../service/api/authentication.api";
import {catchError, mergeMap, tap} from "rxjs/operators";
import produce from "immer";
import {of} from "rxjs";
import {DashboardModel} from "../../model/dashboard.model";
import {LoadMostUsedSavedSearch, LoadRecentSearch, LoadSavedSearch, LoadShortCuts} from "../actions";

const initiateState: AuthModel = {
  fullName: '',
  code: '',
  role: '',
  jwtToken: ''
};

@State<AuthModel>({
    name: 'AuthModel',
    defaults: initiateState
})
export class AuthState implements NgxsOnInit {
    ctx = null;

    constructor(private authAPI: AuthenticationApi, private store: Store) {}

    ngxsOnInit(ctx?: StateContext<any>): void | any {
        this.ctx = ctx;
        ctx.dispatch(new fromHD.AuthenticationAction());
    }

    @Selector()
    static getToken(state: AuthModel) {
        return state.jwtToken;
    }

    @Selector()
    static getUser(state: AuthModel) {
        return state.fullName;
    }

    @Action(fromHD.AuthenticationAction)
    authentication(ctx: StateContext<AuthModel>, {payload}: fromHD.AuthenticationAction) {
        return this.authAPI.Authentication().pipe(
            tap( (data: AuthModel)  => {
                ctx.patchState(produce(ctx.getState(), draft => {
                    draft.code = data.code;
                    draft.fullName = data.fullName;
                    draft.role = data.role;
                    draft.jwtToken = data.jwtToken;
                }));
                window.localStorage.setItem('token', data.jwtToken);
                this.store.dispatch(
                    [new LoadShortCuts(), new LoadRecentSearch(), new LoadSavedSearch(), new LoadMostUsedSavedSearch()]);
            }),
            catchError(err => {
                console.log(err);
                return of();
            })
        )
    }
}
