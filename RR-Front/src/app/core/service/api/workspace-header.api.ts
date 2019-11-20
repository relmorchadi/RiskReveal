import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from '../../../shared/api';

@Injectable({
  providedIn: 'root'
})
export class WorkspaceHeaderApi {
  protected URL = `${backendUrl()}workspace/`;

  constructor(private http: HttpClient) {
  }

  getRecent(offset, size, userId) {
    return this.http.get(`${this.URL}recent`, {params: {offset, size, userId}});
  }

  getFavorited(offset, size, userId) {
    return this.http.get(`${this.URL}favorite`, {params: {offset, size, userId}});
  }

  getAssigned(offset: any, size: any, userId: any) {
    return this.http.get(`${this.URL}assigned`, {params: {offset, size, userId}});
  }

  getPinned(offset, size, userId) {
    return this.http.get(`${this.URL}pinned`, {params: {offset, size, userId}});
  }

  getCount(userId) {
    return this.http.get(`${this.URL}count`, {params: {userId}});
  }

  toggleFavorite(data) {
    return this.http.post(`${this.URL}favorite`, data, {responseType: 'text' as 'json'});
  }

  togglePinned(data) {
    return this.http.post(`${this.URL}pinned`, data, {responseType: 'text' as 'json'});
  }

}
