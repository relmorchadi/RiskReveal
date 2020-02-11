import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { TableServiceInterface } from '../interfaces/table-service.interface';
import { HttpClient } from '@angular/common/http';
import { Data, Columns } from '../data/payload.data'

@Injectable()
export class TableServiceImp implements TableServiceInterface {

  url: string;
  _url: string;

  constructor(private _http: HttpClient) {}

  setUrl(url: string) {
    this._url = url;
  }

  loadData() {
    return Data;
  }

  loadColumns() {
    return Columns;
  }

  getData(params): Observable<any> {
    return this._http.get(`${this._url}data`, { params });
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
