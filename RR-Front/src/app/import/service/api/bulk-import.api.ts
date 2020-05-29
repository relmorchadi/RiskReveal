import {forkJoin, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl, importUrl} from "../../../shared/api";
import {map} from "rxjs/operators";
import * as _ from 'lodash'

@Injectable({
    providedIn: 'root'
})
export class BulkImportApi {
    protected URL = `${importUrl()}bulk-import/`;

    constructor(private http: HttpClient) {
    }

    uploadAndValidate(params: any): Observable<any> {
        return this.http.post(`${this.URL}upload-and-validate`, params);
    }

    runImport(params: any): Observable<any> {
      return this.http.post(`${this.URL}import`, {},{ params, responseType: 'text' });
    }
}
