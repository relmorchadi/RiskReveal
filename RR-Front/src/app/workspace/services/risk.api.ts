import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from '../../shared/api';

@Injectable({
  providedIn: 'root'
})
export class RiskApi {
  protected URL = `${backendUrl()}risk-link/`;

  constructor(private http: HttpClient) {
  }

  searchRiskLinkData(Keyword = '', PageSize = '20', offset = '0', Page = '0'): Observable<any> {
    return this.http.get(`${this.URL}edm-rdm?keyword=${Keyword}&size=${PageSize}&page=${Page}`);
  }

  searchRiskLinkAnalysis(paramId, paramName): Observable<any> {
    return this.http.get(`${this.URL}analysis?size=20`, {params: {rdmName: paramName}});
  }

  searchRiskLinkPortfolio(paramId, paramName): Observable<any> {
    return this.http.get(`${this.URL}portfolio?size=20`, {params: {edmId: paramId, edmName: paramName}});
  }

  searchDetailAnalysis(paramId, paramName): Observable<any> {
    return this.http.get(`${this.URL}detailed-analysis-scan`, {params: {edmId: paramId, edmName: paramName}});
  }
}
