import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RiskApi {
  protected URL = `${environment.API_URI}risk-link/`;

  constructor(private http: HttpClient) {
  }

  searchRiskLinkData(keyword = null): Observable<any> {
    if (keyword) {
      return this.http.get(`${this.URL}edm-rdm`, {params: {keyword}});
    }
    return this.http.get(`${this.URL}edm-rdm`);
  }

  searchRiskLinkAnalysis(paramId, paramName): Observable<any> {
    console.log(paramId, paramName);
    return this.http.get(`${this.URL}analysis`);
  }

  searchRiskLinkPortfolio(paramId, paramName): Observable<any> {
    console.log(paramId, paramName , `${this.URL}portfolio?edmId=${paramId}&edmName=${paramName}`);
    this.http.get(`${this.URL}portfolio?edmId=${paramId}&edmName=${paramName}`).subscribe(
      (dt: any) => console.log(dt.content)
    );
    return this.http.get(`${this.URL}portfolio`, {params: {edmId: paramId, edmName: paramName}});
  }
}
