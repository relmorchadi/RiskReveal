import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { TableServiceInterface } from '../interfaces/table-service.interface';
import { HttpClient } from '@angular/common/http';
import { Data, Columns } from '../data/payload.data'
import {backendUrl} from "../api";

@Injectable()
export class TableServiceImp implements TableServiceInterface {

  url: string;
  _url: string;

  constructor(private _http: HttpClient) {}

  setUrl(url: string) {
    this._url = url;
  }

  getColumns() {
    console.log("working");
    return this._http.get(`${this._url}columns`);
  }

  getData(params): Observable<any> {
    return this._http.get(`${this._url}`, { params });
  }

  updateColumnFilter(body): Observable<any> {
    return this._http.post(`${this._url}filter`, body);
  }

  updateColumnSort(body): Observable<any> {
    return this._http.post(`${this._url}sort`, body);
  }

  updateColumnsOrderAndVisibility(body): Observable<any> {
    return this._http.post(`${this._url}orderandvisibility`, body);
  }

}
