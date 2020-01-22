import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class ContractApi {

    private readonly URL = environment.API_URI + 'contract/';

    constructor(private _http: HttpClient) {
    }

    facData(projectId) {
        return this._http.get(`${environment.API_URI}contract/get-car-info`, {params: {projectId}});
    }

}
