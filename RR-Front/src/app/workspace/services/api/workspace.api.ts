import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from "../../../shared/api";

@Injectable({
  providedIn: 'root'
})
export class WsApi {

  private readonly api = backendUrl() + 'workspace/';

  constructor(private _http: HttpClient) {
  }

  searchWorkspace(workspaceContextCode = '', workspaceContextUwYear = '', type = '') {
    return this._http.get(`${this.api}`, { params: {workspaceContextCode, workspaceContextUwYear, type} });
  }

  getAllUsers() {
    return this._http.get(`${backendUrl() + 'user/all'}`);
  }

  getOpenedTabs(payload) {
    return this._http.post(`${this.api}tabs/`, payload);
  }

  getTabsCount() {
    return this._http.get(`${this.api}tabs/count`);
  }

  closeTab(payload) {
    return this._http.post(`${this.api}tabs/close`, payload)
  }

  openTab(payload) {
    return this._http.post(`${this.api}tabs/open`, payload)
  }

  selectTab(payload) {
    return this._http.post(`${this.api}tabs/select`, payload)
  }

  orderTabs(payload) {
    return this._http.post(`${this.api}tabs/order`, payload)
  }
}
