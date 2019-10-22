import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from "../../../shared/api";

@Injectable({
  providedIn: 'root'
})
export class FileBaseApi {
  protected URL = `${backendUrl()}filebaseimport/`;

  constructor(private http: HttpClient) {
  }

  searchFilesList(paths): Observable<any> {
    return this.http.get(`${this.URL}files-list`, {params: {path: paths}});
  }

  searchFoldersList(paths = '/') {
    return this.http.get(`${this.URL}folders-list`, {params: {path: paths}});
  }

  searchReadFiles(fileNames): Observable<any> {
    return this.http.get(`${this.URL}read-file`, {params: {fileName: fileNames}, responseType: 'text'});
  }
}
