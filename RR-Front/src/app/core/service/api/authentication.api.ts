import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {proxyUrl} from '../../../shared/api';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationApi {
    protected URL = `${proxyUrl()}`;

    constructor(private http: HttpClient) {
    }

    Authentication() {
        return this.http.get(`${this.URL}login`);
    }

}
