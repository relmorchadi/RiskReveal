import { Observable } from "rxjs";

export interface TableServiceInterface {

  url: string;

  getColumns(): Observable<any>;
  getData(params): Observable<any>;
  getIDs(params): Observable<any>;
  updateColumnWidth(body): Observable<any>;
  updateColumnFilter(body): Observable<any>;
  resetColumnFilter(body): Observable<any>;
  filterByProject(body): Observable<any>;
  updateColumnSort(body): Observable<any>;
  resetColumnSort(body): Observable<any>;
  updateColumnsOrderAndVisibility(body): Observable<any>;

  setUrl(url);

}