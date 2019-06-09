import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import * as _ from 'lodash';

@Injectable({
  providedIn: 'root'
})
export class WsApi {

  private readonly api = environment.API_URI + 'workspace/';

  constructor(private _http: HttpClient) {
  }


  searchWorkspace(id = '', year = '') {
    return this._http.get(`${environment.API_URI + 'search/' }worspace/${id}/${year}`);
  }

}
