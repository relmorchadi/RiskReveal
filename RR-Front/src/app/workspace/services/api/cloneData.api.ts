import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {backendUrl, calibrationUrl} from "../../../shared/api";

@Injectable({
    providedIn: 'root'
})
export class CloneDataApi {

    protected URL = `${calibrationUrl()}clone`;

    constructor(private http: HttpClient) {
    }

    public cloneData(body: any): Observable<any> {
        return this.http.post(`${this.URL}/clonePLT`, body);
    }

}
