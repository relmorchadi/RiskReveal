import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RiskApi {
  protected URL = `${environment.API_URI}/risk-link/`;

  constructor(private http: HttpClient) {
  }

  searchRiskLinkData(): Observable<any> {
    return this.http.get(`${environment.API_URI}edm-rdm`);
  }

  searchRiskLinkAnalysis(): Observable<any> {
    return this.http.get(`${environment.API_URI}analysis`);
  }

  searchRiskLinkPortfolio(): Observable<any> {
    return this.http.get(`${environment.API_URI}portfolio`);
  }
}
