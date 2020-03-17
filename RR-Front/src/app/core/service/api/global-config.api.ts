import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from '../../../shared/api';

@Injectable({
  providedIn: 'root'
})
export class GlobalConfigApi {
  protected URL = `${backendUrl()}user-preferences/`;

  constructor(private http: HttpClient) {
  }

  postGlobalConfig(data) {
    return this.http.post(`${this.URL}`, data);
  }

  getGlobalConfig() {
    return this.http.get(`${this.URL}`);
  }

  delGlobalConfig(userPreferenceId) {
    return this.http.delete(`${this.URL}`, {params: {userPreferenceId}});
  }

  getAllUsers() {
    return this.http.get(`${backendUrl() + 'user/all'}`);
  }

}
