import {AuthModel} from "../../model/auth.model";
import {Action, NgxsOnInit, Selector, State, StateContext} from "@ngxs/store";
import * as fromHD from "../actions";
import {AuthenticationApi} from "../../service/api/authentication.api";
import {catchError, mergeMap, tap} from "rxjs/operators";
import produce from "immer";
import {of} from "rxjs";
import {DashboardModel} from "../../model/dashboard.model";

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

    constructor(private authAPI: AuthenticationApi) {}

    ngxsOnInit(ctx?: StateContext<any>): void | any {
        this.ctx = ctx;
    }

    @Selector()
    static getToken(state: AuthModel) {
        return state.jwtToken;
    }

    @Action(fromHD.AuthenticationAction)
    authentication(ctx: StateContext<AuthModel>, {payload}: fromHD.AuthenticationAction) {
        console.log('dispatched');
        return this.authAPI.Authentication().pipe(
            tap( (data: AuthModel)  => {
                ctx.patchState(produce(ctx.getState(), draft => {
                    draft.code = data.code;
                    draft.fullName = data.fullName;
                    draft.role = data.role;
                    draft.jwtToken = data.jwtToken;
                }));
                window.localStorage.setItem('token', data.jwtToken);
            }),
            catchError(err => {
                console.log(err);
                return of();
            })
        )
    }
}
