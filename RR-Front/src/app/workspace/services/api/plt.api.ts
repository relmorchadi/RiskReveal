import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from "../../../shared/api";
import {GridRequest} from "../../../shared/types/grid-request.type";

@Injectable({
  providedIn: 'root'
})
export class PltApi {

  protected URL = `${backendUrl()}plt`;

  protected URLn = `${backendUrl()}plt-manager`;

  constructor(private http: HttpClient) {
  }

  public getAllPlts({workspaceId, uwy}): Observable<any> {
    return this.http.get(`${this.URL}/?wsId=${workspaceId}&uwYear=${uwy}`);
  }

  public delete(payload): Observable<any> {
    return this.http.post(`${this.URL}/delete`, payload);
  }

  public restore(payload): Observable<any> {
    return this.http.post(`${this.URL}/restore`, payload);
  }

  public getSummary(payload): Observable<any> {
    return this.http.get(`${this.URL}/detail/summary/?pltHeaderId=${payload}`);
  }

  public getGroupedPLTs(payload: GridRequest<any>) {
    return this.http.post(`${this.URLn}/grouped`, payload)
  }

  public saveTableConfig(payload: any) {
    return this.http.post(`${this.URLn}/config`, payload)
  }

  public checkConfig(params: any) {
    return this.http.get(`${this.URLn}/config/check`, { params })
  }

  public getConfig(params: any) {
    return this.http.get(`${this.URLn}/config`, { params })
  }

}
