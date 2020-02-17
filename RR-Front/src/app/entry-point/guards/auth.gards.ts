import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {JwtHelperService} from "@auth0/angular-jwt";
import {AuthService} from "../../core/service/auth.service";
import {Store} from "@ngxs/store";
import {AuthenticationAction} from "../../core/store/actions";

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {
    constructor(private store: Store,
                private router: Router,
                private jwtHelper: JwtHelperService,
                private authService: AuthService) {

    }

    canActivate() {
        //const token = localStorage.getItem('token');
        //const claims:any = this.jwtHelper.decodeToken(token);
        this.store.dispatch(new AuthenticationAction());
        return true;
    }
}
