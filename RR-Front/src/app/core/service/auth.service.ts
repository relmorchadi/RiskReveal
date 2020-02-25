import { Injectable } from '@angular/core';
// import decode from 'jwt-decode';
import {Select, Store} from "@ngxs/store";
import * as _ from 'lodash';
import {AuthState} from "../store/states/auth.state";
import {AuthenticationApi} from "./api/authentication.api";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  @Select(AuthState.getToken) selector$;
  token: string;

  constructor(private store: Store, private authAPI: AuthenticationApi) {
    this.selector$.subscribe(value => {
      this.token = value;
    });
  }

  public getToken() {
    this.token = window.localStorage.getItem('token');
    return this.token;
  }

  public isAuthenticated(): boolean {
    // get the token
    const token = this.getToken();
    return false;
    // return a boolean reflecting
    // whether or not the token is expired
    // return tokenNotExpired(null, token);
  }
}
