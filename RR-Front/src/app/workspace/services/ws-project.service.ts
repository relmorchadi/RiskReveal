import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {backendUrl} from "../../shared/api";

@Injectable({
  providedIn: 'root'
})
export class WsProjectService {

  private readonly api = backendUrl() + 'workspace/projects/';

  constructor(private http: HttpClient) {
  }

  addNewProject(project: any, wsId, uwYear, id): Observable<any> {
      return this.http.post(`${this.api}`, {wsId, uwYear, id, project});
  }

  deleteProject(project: any, wsId, uwYear): Observable<any> {
     return this.http.post(`${this.api}delete`, {wsId, uwYear, project});
  }

}
