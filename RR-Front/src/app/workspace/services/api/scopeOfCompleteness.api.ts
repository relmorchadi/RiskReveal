import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from "../../../shared/api";

@Injectable({
    providedIn: 'root'
})
export class ScopeOfCompletenessApi {

    private readonly api = backendUrl();

    constructor(private _http: HttpClient) {
    }

    getData() {
        return this._http.get(`${this.api}`, { params: {} });
    }
}
