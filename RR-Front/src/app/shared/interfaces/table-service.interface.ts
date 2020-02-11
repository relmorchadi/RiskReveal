import { Observable } from "rxjs";

export interface TableServiceInterface {

  url: string;

  getData(params): Observable<any>;
  updateColumnFilter(body): Observable<any>;
  updateColumnSort(body): Observable<any>;
  updateColumnsOrderAndVisibility(body): Observable<any>;

  loadData();
  loadColumns();

}