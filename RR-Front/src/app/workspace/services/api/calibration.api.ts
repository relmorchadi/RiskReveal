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
    return this.http.get(`${this.URL}defaultAdjustment/lookupDefaultAdjustment`, {params: {engineType, marketChannelId, pltEntityId, regionPerilId, targetRapId}});
  }

  loaGroupedPltsByPure(wsId: string, uwYear: number) {
    return this.http.get(`${this.URL}plts?wsId=${wsId}&uwYear=${uwYear}`);
  }

  loadEpMetrics(wsId: string, uwYear: number, userId: number, curveType: string) {
    return this.http.get(`${this.URL}epMetrics?workspaceContextCode=${wsId}&uwYear=${uwYear}&userId=${userId}&curveType=${curveType}`);
  }

}
