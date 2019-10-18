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

}
