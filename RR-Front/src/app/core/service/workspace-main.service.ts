
import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import * as _ from 'lodash';
import {Observable, of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WorkspaceMainService {

  private readonly api = environment.API_URI + 'workspace/projects/';

  constructor(private http: HttpClient) {
  }

  addNewProject(project: any, workspaceId, uwYear): Observable<any> {
      return this.http.post(`${this.api}`, {workspaceId, uwYear, project});
     // return of({ workspaceId, uwYear, projectId: project.projectName, name: project.projectName, ...project});
  }

  deleteProject(project: any, workspaceId, uwYear): Observable<any> {
     return this.http.post(`${this.api}delete`, {workspaceId, uwYear, project});
    // return of({ workspaceId, uwYear, ...project});
  }

}
