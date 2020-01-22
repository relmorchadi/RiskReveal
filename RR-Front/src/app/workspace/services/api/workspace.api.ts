import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../../../environments/environment";
import {backendUrl} from "../../../shared/api";

@Injectable({
  providedIn: 'root'
})
export class WsApi {

  private readonly api = backendUrl() + 'workspace/';

  constructor(private _http: HttpClient) {
  }

  searchWorkspace(workspaceContextCode = '', workspaceContextUwYear = '', type = 'TTY') {
    return this._http.get(`${this.api}`, { params: {workspaceContextCode, workspaceContextUwYear, type} });
  }

}
