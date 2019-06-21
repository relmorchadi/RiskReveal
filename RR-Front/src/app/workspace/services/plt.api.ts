import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PltApi {

  protected URL = `${environment.API_URI}plt`;

  constructor(private http: HttpClient) {
  }

  public getAllPlts(params?): Observable<any> {
    return this.http.get(`${this.URL}/`, {params: params});
  }

  public assignPltsToTag(params?): Observable<any> {
    console.log(params);
    return this.http.post(`${this.URL}/assign-user-tag`, params)
  }

  deleteUserTag(params): Observable<any>{
    return this.http.delete(`${this.URL}/user-tag/${params}`)
  }

  editTag(params: any): Observable<any> {
    return this.http.put(`${this.URL}/user-tag`,params)
  }

  deletePlt(params: any): Observable<any> {
    return this.http.delete(`${this.URL}/plt`,params)
  }
}
