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

  loadDefaultAdjustment(engineType, marketChannelId, pltEntityId, regionPerilId, targetRapId): Observable<any> {
    return this.http.get(`${this.URL}defaultAdjustment/lookupDefaultAdjustment`, {params: {engineType, marketChannelId, pltEntityId, regionPerilId, targetRapId}});
  }

  //New Calibration

  loaGroupedPltsByPure(wsId: string, uwYear: number) {
    return this.http.get(`${this.URL}plts?wsId=${wsId}&uwYear=${uwYear}`);
  }

  loadEpMetrics(workspaceContextCode, uwYear, userId, curveType, screen) {
    return this.http.get(`${this.URL}epMetrics`, { params: {workspaceContextCode, uwYear, userId, curveType, screen}});
  }

  loadSinglePltEpMetrics(pltHeaderId, userId, curveType, screen) {
    return this.http.get(`${this.URL}epMetrics/singlePLT`, { params: {pltHeaderId, userId, curveType, screen}});
  }

  loadSinglePLTSummaryStats(pltHeaderId) {
    return this.http.get(`${this.URL}epMetrics/singlePLTSummaryStats`, { params: {pltHeaderId}});
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

  loadAllAdjustmentStates() {
    return this.http.get(`${this.URL}state/all`);
  }

  loadDefaultRPs() {
    return this.http.get(`${this.URL}state/all`);
  }

  loadCurrencies() {
    return this.http.get(`${this.URL}constants/currency/all`);
  }

  validateRP(rp) {
    return this.http.get(`${this.URL}epMetrics/rp/validate`, { params: { rp }});
  }

  saveListOfRPsByUserId(rps: number[], userId: number, screen) {
    return this.http.post(`${this.URL}epMetrics/rp/save`, {rps, userId, screen});
  }

  deleteListOfRPsByUserId(userId, rps, screen) {
    return this.http.post(`${this.URL}epMetrics/rp/delete`, { userId, rps, screen });
  }

  getExchangeRates(effectiveDate, currencies) {
    return this.http.post(`${this.URL}exchangerate`, {effectiveDate, currencies});
  }

}
