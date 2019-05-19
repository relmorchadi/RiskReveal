import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PltApi {

  protected URL = `${environment.API_URI}/plt`;

  constructor(private http: HttpClient) {
  }

  public getAllPlts(params?): Observable<any> {
    return this.http.get(`${this.URL}/`, {params: params});
  }

}
