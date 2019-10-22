import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from "../../../shared/api";

@Injectable({
  providedIn: 'root'
})
export class TagsApi {

  protected URL = `${backendUrl()}tags`;

  constructor(private http: HttpClient) {
  }

  getTagsBySelection(params: any): Observable<any> {
    return this.http.post(`${this.URL}/getSelection`, params);
  }

  public creatUserTag(params?): Observable<any> {
    return this.http.post(`${this.URL}`, params)
  }

  public assignPltsToTag(params?): Observable<any> {
    //console.log(params);
    return this.http.post(`${this.URL}/assign`, params)
    //return of(null);
  }


}
