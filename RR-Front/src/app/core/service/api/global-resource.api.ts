import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from '../../../shared/api';

@Injectable({
    providedIn: 'root'
})
export class GlobalResourceApi {
    protected URL = `${backendUrl()}global/`;

    constructor(private http: HttpClient) {
    }

    getEnvData() {
        return this.http.get(`${this.URL}env`, {responseType: 'text' as 'json'});
    }

    getVersionDAta() {
        return this.http.get(`${this.URL}version`, {responseType: 'text' as 'json'});
    }

}