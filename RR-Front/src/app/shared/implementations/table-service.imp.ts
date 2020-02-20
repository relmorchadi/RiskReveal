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
    return this._http.get(`${this._url}columns`);
  }

  getData(params): Observable<any> {
    return this._http.get(`${this._url}`, { params });
  }

  getIDs(params): Observable<any> {
    return this._http.get(`${this._url}ids`, { params });
  }

  updateColumnWidth(body): Observable<any> {
    return this._http.post(`${this._url}columns/width`, body);
  }

  updateColumnFilter(body): Observable<any> {
    return this._http.post(`${this._url}columns/filter`, body);
  }

  updateColumnSort(body): Observable<any> {
    return this._http.post(`${this._url}columns/sort`, body);
  }

  updateColumnsOrderAndVisibility(body): Observable<any> {
    return this._http.post(`${this._url}columns/orderandvisibility`, body);
  }

  resetColumnFilter(body): Observable<any> {
    return this._http.post(`${this._url}columns/filter/reset`, body);
  }

  resetColumnSort(body): Observable<any> {
    return this._http.post(`${this._url}columns/sort/reset`, body);
  }

}
