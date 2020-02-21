import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../../../environments/environment";
import {backendUrl} from "../../../shared/api";

@Injectable({
    providedIn: 'root'
})
export class ContractApi {

    private readonly URL = backendUrl() + 'contract/';

    constructor(private _http: HttpClient) {}

    facData(projectId) {
        return this._http.get(`${this.URL}get-car-info`, {params: {projectId}});
    }

}
