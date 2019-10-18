import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from "../../shared/api";

@Injectable({
  providedIn: 'root'
})
export class PltApi {

  protected URL = `${backendUrl()}plt`;

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

}
