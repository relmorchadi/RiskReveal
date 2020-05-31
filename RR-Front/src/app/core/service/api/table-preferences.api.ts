import {Injectable} from "@angular/core";
import {backendUrl} from "../../../shared/api";
import {HttpClient} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class TablePreferencesApi {
    protected URL = `${backendUrl()}table-preferences`;

    constructor(private http: HttpClient) {
    }

    getTablePreference(uIPage: string, tableName: string) {
        return this.http.get(`${this.URL}` + "/" + uIPage + "/" + tableName);
    }
}
