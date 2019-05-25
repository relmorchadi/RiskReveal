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

  searchRiskLinkData(Keyword = '', PageSize = '20', offset = '0', Page = '0'): Observable<any> {
    return this.http.get(`${this.URL}edm-rdm?keyword=${Keyword}&size=${PageSize}&page=${Page}`);
  }

  searchRiskLinkAnalysis(paramId, paramName): Observable<any> {
    console.log(paramId, paramName);
    this.http.get(`${this.URL}analysis?rdmId=${paramId}&rdmName=${paramName}&size=20`).subscribe(
      (dt: any) => console.log(dt.content)
    );
    return this.http.get(`${this.URL}analysis?size=20`);
  }

  searchRiskLinkPortfolio(paramId, paramName): Observable<any> {
    console.log(paramId, paramName , `${this.URL}portfolio?edmId=${paramId}&edmName=${paramName}`);
    this.http.get(`${this.URL}portfolio?edmId=${paramId}&edmName=${paramName}&size=20`).subscribe(
      (dt: any) => console.log(dt.content)
    );
    return this.http.get(`${this.URL}portfolio?size=20`, {params: {edmId: paramId, edmName: paramName}});
  }
}
