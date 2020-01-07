import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from '../../../shared/api';
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class DashboardApi {
    protected URL = `${backendUrl()}dashboard/`;

    constructor(private http: HttpClient) {
    }

    getFacDashboardResources(filters): Observable<any>  {
        return this.http.post(`${this.URL}`, filters);
    }

}
