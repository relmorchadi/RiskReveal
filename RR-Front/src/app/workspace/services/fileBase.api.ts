import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from '../../shared/api';

@Injectable({
  providedIn: 'root'
})
export class FileBaseApi {
  protected URL = `${backendUrl()}filebaseimport/`;

  constructor(private http: HttpClient) {
  }

  searchFilesList(): Observable<any> {
    return this.http.get(`${this.URL}files-list`);
  }

  searchFoldersList() {
    return this.http.get(`${this.URL}folders-list`);
  }

  searchReadFiles(fileNames): Observable<any> {
    return this.http.get(`${this.URL}read-file`, {params: {fileName: fileNames}});
  }
}
