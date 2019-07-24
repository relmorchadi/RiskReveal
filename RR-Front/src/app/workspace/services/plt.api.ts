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

  public getAllPlts(params?): Observable<any> {
    return this.http.get(`${this.URL}/`, {params: params});
  }

  public assignPltsToTag(params?): Observable<any> {
    console.log(params);
    return this.http.post(`${this.URL}/assign-user-tag`, params)
  }

  public creatUserTag(params?): Observable<any> {
    console.log(params);
    return this.http.post(`${this.URL}/create-user-tag`, params)
  }

  public editTag(params: any): Observable<any> {
    console.log(params)
    return this.http.put(`${this.URL}/update-user-tag`,params)
  }

  deleteUserTag(params): Observable<any>{
    return this.http.delete(`${this.URL}/user-tag/${params}`)
  }

  deletePlt(params: any): Observable<any> {
    return this.http.delete(`${this.URL}/plt`,params)
  }
}
