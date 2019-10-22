import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class WsApi {

  private readonly api = environment.API_URI + 'workspace/';

  constructor(private _http: HttpClient) {
  }


  searchWorkspace(id = '', year = '') {
    return this._http.get(`${environment.API_URI + 'search/'}worspace/${id}/${year}`);
  }

  searchFacWidget() {
    return this._http.get(`${environment.API_URI}fac?size=50`);
  }

  postFacData(data) {
    return this._http.post(`${environment.API_URI}fac`, data);
  }

}
