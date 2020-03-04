import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from "../../../shared/api";
import {collapseMotion} from "ng-zorro-antd/core/animation/collapse";

@Injectable({
  providedIn: 'root'
})
export class WsApi {

  private readonly api = backendUrl() + 'workspace/';
  private readonly URL = backendUrl() + 'workspace/tabs/';

  constructor(private _http: HttpClient) {
  }

  searchWorkspace(workspaceContextCode = '', workspaceContextUwYear = '', type = '') {
    return this._http.get(`${this.api}`, { params: {workspaceContextCode, workspaceContextUwYear, type} });
  }

  getAllUsers() {
    return this._http.get(`${backendUrl() + 'user/all'}`);
  }

  getOpenedTabs(userCode) {
    return this._http.get(`${this.URL}${userCode}`);
  }

  closeTab(closedTab) {
    return this._http.post(`${this.URL}close`, closedTab);
  }

  openTab(openedTab) {
    return this._http.post(`${this.URL}open`, openedTab);
  }
}
