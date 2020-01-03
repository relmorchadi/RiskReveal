import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from '../../../shared/api';

@Injectable({
    providedIn: 'root'
})
export class DashboardApi {
    protected URL = `${backendUrl()}user-preferences/`;

    constructor(private http: HttpClient) {
    }

}
