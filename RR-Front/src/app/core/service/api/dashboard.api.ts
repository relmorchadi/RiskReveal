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

    getDashboards(userId): Observable<any> {
        return this.http.get(`${this.URL}`, {params: {userId}});
    }

    creatDashboards(data): Observable<any> {
        return this.http.post(`${this.URL}`, data);
    }

    deleteDashboards(): Observable<any> {
        return this.http.get(`${this.URL}`);
    }

    SaveDashboards(): Observable<any> {
        return this.http.get(`${this.URL}`);
    }

    UpdateDashboards(): Observable<any> {
        return this.http.get(`${this.URL}`);
    }

    getFacDashboardResources(filters): Observable<any>  {
        return this.http.post(`${this.URL}`, filters);
    }

}
