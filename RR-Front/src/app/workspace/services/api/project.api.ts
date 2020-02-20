import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ProjectApi {

  private readonly api = environment.API_URI + 'workspace/projects';

  constructor(private http: HttpClient) {
  }

  createProject(data, wsId, uwy) {
    return this.http.post(`${this.api}`, data, {params: {wsId, uwy}});
  }

  updateProject(assignedTo, projectId, projectName, projectDescription, dueDate) {
    return this.http.put(`${this.api}`, {assignedTo, projectId, projectName, projectDescription, dueDate })
  }

  deleteProject(id) {
    return this.http.delete(`${this.api}/delete`, {params: {id}})
  }

}
