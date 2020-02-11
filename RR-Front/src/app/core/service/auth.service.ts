import { Injectable } from '@angular/core';
// import decode from 'jwt-decode';
import {Select, Store} from "@ngxs/store";
import {AuthState} from "../store/states/auth.state";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  @Select(AuthState.getToken) selector$;
  token: string;

  constructor(private store: Store) {
    this.selector$.subscribe(value => {
      this.token = value;
    })
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
