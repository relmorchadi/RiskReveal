
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {HelperService} from "../../shared/helper.service";

@Injectable({
  providedIn: 'root'
})
export class WorkspaceMainService {

  private readonly api = HelperService.getApiUrl() + 'workspace/projects/';

  constructor(private http: HttpClient) {
  }

  addNewProject(project: any, workspaceId, uwYear, id): Observable<any> {
      return this.http.post(`${this.api}`, {workspaceId, uwYear, id, project});
     // return of({ workspaceId, uwYear, projectId: project.projectName, name: project.projectName, ...project});
  }

  deleteProject(project: any, workspaceId, uwYear): Observable<any> {
     return this.http.post(`${this.api}delete`, {workspaceId, uwYear, project});
    // return of({ workspaceId, uwYear, ...project});
  }

}
