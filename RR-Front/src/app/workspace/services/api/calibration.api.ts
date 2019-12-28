import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {calibrationUrl} from "../../../shared/api";

@Injectable({
  providedIn: 'root'
})
export class CalibrationAPI {
  protected URL = `${calibrationUrl()}`;

  constructor(private http: HttpClient) {
  }

  loadBasis(): Observable<any> {
    return this.http.get(`${this.URL}basis`)
  }

  loadDefaultAdjustement(engineType, marketChannelId, pltEntityId, regionPerilId, targetRapId): Observable<any> {
    return this.http.get(`${this.URL}defaultAdjustment/lookupDefaultAdjustment`, {params: {engineType, marketChannelId, pltEntityId, regionPerilId, targetRapId}});
  }

  //New Calibration

  loaGroupedPltsByPure(wsId: string, uwYear: number) {
    return this.http.get(`${this.URL}plts?wsId=${wsId}&uwYear=${uwYear}`);
  }

  loadEpMetrics(wsId: string, uwYear: number, userId: number, curveType: string) {
    return this.http.get(`${this.URL}epMetrics?workspaceContextCode=${wsId}&uwYear=${uwYear}&userId=${userId}&curveType=${curveType}`);
  }

  loadDefaultAdjustments(workspaceContextCode, uwYear) {
    return this.http.get(`${this.URL}defaultAdjustment`, { params: {workspaceContextCode, uwYear}});
  }

  loadAllBasis() {
    return this.http.get(`${this.URL}basis`);
  }

  loadAllAdjustmentTypes() {
    return this.http.get(`${this.URL}type/all`);
  }

}
