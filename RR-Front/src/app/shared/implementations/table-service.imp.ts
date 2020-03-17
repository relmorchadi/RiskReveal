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

  constructor(private _http: HttpClient) {
    console.log('init', 1)
  }

  setUrl(url: string) {
    this._url = url;
  }

  public getColumns() {
    return this._http.get(`${this._url}columns`);
  }

  public getData(params): Observable<any> {
    return this._http.get(`${this._url}`, { params });
  }

  public getIDs(params): Observable<any> {
    return this._http.get(`${this._url}ids`, { params });
  }

  public updateColumnWidth(body): Observable<any> {
    return this._http.post(`${this._url}columns/width`, body);
  }

  public updateColumnFilter(body): Observable<any> {
    return this._http.post(`${this._url}columns/filter`, body);
  }

  public filterByProject(body): Observable<any> {
    return this._http.post(`${this._url}columns/filter/project`, body);
  }

  public updateColumnSort(body): Observable<any> {
    return this._http.post(`${this._url}columns/sort`, body);
  }

  public updateColumnsOrderAndVisibility(body): Observable<any> {
    return this._http.post(`${this._url}columns/orderandvisibility`, body);
  }

  public resetColumnFilter(body): Observable<any> {
    return this._http.post(`${this._url}columns/filter/reset`, body);
  }

  public resetColumnSort(body): Observable<any> {
    return this._http.post(`${this._url}columns/sort/reset`, body);
  }

}
