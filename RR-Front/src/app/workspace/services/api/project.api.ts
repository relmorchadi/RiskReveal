import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from "../../../shared/api";

@Injectable({
  providedIn: 'root'
})
export class ProjectApi {

  private readonly api = backendUrl() + 'workspace/projects';

  constructor(private http: HttpClient) {
  }

  createProject(data, wsId, uwy) {
    return this.http.post(`${this.api}`, data, {params: {wsId, uwy}});
  }

  updateProject(assignedTo, projectId, projectName, projectDescription) {
    return this.http.put(`${this.api}`, null,{params: {assignedTo, projectId, projectName, projectDescription}})
  }

  deleteProject(id) {
    return this.http.delete(`${this.api}/delete`, {params: {id}})
  }

}
