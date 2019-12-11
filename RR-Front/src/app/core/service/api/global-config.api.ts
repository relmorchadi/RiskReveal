import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from '../../../shared/api';

@Injectable({
  providedIn: 'root'
})
export class GlobalConfigApi {
  protected URL = `${backendUrl()}globalConfig/`;

  constructor(private http: HttpClient) {
  }

  postGlobalConfig(data) {
    return this.http.post(`${this.URL}`, data);
  }

}
