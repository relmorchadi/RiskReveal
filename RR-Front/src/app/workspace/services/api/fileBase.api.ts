import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {importUrl} from "../../../shared/api";

@Injectable({
  providedIn: 'root'
})
export class FileBaseApi {
  protected URL = `${importUrl()}ihub/`;

  constructor(private http: HttpClient) {
  }

  searchFilesList(paths): Observable<any> {
    return this.http.get(`${this.URL}retrieveTextFiles`, {params: {path: paths}});
  }

    searchFoldersList(paths) {
        return this.http.get(`${this.URL}directoryListing`, {params: {path: paths}});
    }

    searchReadFiles(nameFiles): Observable<any> {
         return this.http.get(`${this.URL}read-metadata`, {params: {path: nameFiles}});
        //return this.http.get(`${this.URL}read-PLTdata`, {params: {nameFile: nameFiles}, responseType: 'text'});
   }

    /*persisteFileBasedImportConfig(projectId,filePaths,folderPath){
        return this.http.get(`${this.URL}persisteFileBasedImportConfig`, {params: {projectId,filePaths,folderPath}});
    }*/

    persisteFileBasedImportConfig(importLocked,projectId,selectedFileSourcePath,folderPath): Observable<any>{
        return this.http.post(`${this.URL}persisteFileBasedImportConfig`, {importLocked,projectId,selectedFileSourcePath}, {params: {folderPath}});
    }

    runFileBasedImport(instanceId,nonrmspicId,userId,projectId,fileImportSourceResultIds) {
        return this.http.get(`${this.URL}launchFileBasedImport`, {params: {instanceId:instanceId,nonrmspicId:nonrmspicId,userId:userId,projectId:projectId,fileImportSourceResultIds:fileImportSourceResultIds}});
    }

}
