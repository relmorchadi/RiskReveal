import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl, utilityBackEndUrl} from "../../../shared/api";

@Injectable({
  providedIn: 'root'
})
export class CalibrationAPI {
  protected URL = `${utilityBackEndUrl().calibration}`;

  constructor(private http: HttpClient) {
  }

  loadBasis(): Observable<any> {
    return this.http.get(`${this.URL}basis`)
  }

  loadDefaultAdjustement(engineType, marketChannelId, pltEntityId, regionPerilId, targetRapId): Observable<any> {
    return this.http.get(`${this.URL}defaultadjustment/lookupdefaultadjustment`, {params: {engineType, marketChannelId, pltEntityId, regionPerilId, targetRapId}});
  }

}
