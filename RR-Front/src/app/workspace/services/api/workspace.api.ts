import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../../../environments/environment";
import {importUrl} from "../../../shared/api";

@Injectable({
  providedIn: 'root'
})
export class WsApi {

  private readonly api = environment.API_URI + 'workspace/';
  private readonly importAPI = importUrl() + 'import/';

  constructor(private _http: HttpClient) {
  }


  searchWorkspace(id = '', year = '') {
    return this._http.get(`${environment.API_URI + 'search/'}worspace/${id}/${year}`);
  }

  getDivision(carId) {
    console.log(carId);
    return this._http.get(`${this.importAPI}config/get-divisions-for-car`, {params: carId})
  }

}
